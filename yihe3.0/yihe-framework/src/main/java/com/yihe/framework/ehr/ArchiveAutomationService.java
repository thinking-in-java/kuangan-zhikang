package com.yihe.framework.ehr;

import java.util.*;
import java.time.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Evidence-based archive preparation; never approves, publishes or rewrites roster identities. */
@Service
public class ArchiveAutomationService {
 @org.springframework.beans.factory.annotation.Autowired(required=false) ArchiveOriginalCheckQueue originalQueue;
 static final String POLICY="archive-pipeline-v5";
 final EhrRepository db;final EhrService ehr;final ReportMatchingService matching;final AiExamService assessment;final ArchiveAutoService archives;final ArchiveQualityService quality;
 public ArchiveAutomationService(EhrRepository db,EhrService ehr,ReportMatchingService matching,AiExamService assessment){this.db=db;this.ehr=ehr;this.matching=matching;this.assessment=assessment;this.archives=new ArchiveAutoService(db,ehr);this.quality=new ArchiveQualityService(db,ehr);}
 EhrActor actor(Map<String,Object> row){return new EhrActor("archive-xiaogu",str(row.get("scope_code")).substring(4),str(row.get("data_mode")),false,false);}
 @Transactional public Map<String,Object> register(EhrActor a,String report,int revision){
  require(!a.mobile,403,"自动档案处理仅供后台使用");Map<String,Object> h=ehr.rawReport(a,report,revision,true);
  Map<String,Object> prior=db.one("SELECT * FROM a_archive_automation WHERE report_id=? AND revision=? FOR UPDATE",report,revision);if(prior!=null)return status(a,report,revision);
  String task=id(),key=UUID.nameUUIDFromBytes(bytes("archive:"+report+":"+revision)).toString();
  db.insert("a_digital_worker_tasks",map("task_id",task,"scope_code",a.scope,"data_mode",a.mode,"worker_id","archive","task_type","archive_completion","title","体检报告自动识别与档案整理","member_code","","record_id",h.get("record_id"),"priority","normal","instructions","系统流程：识别、身份匹配、查重、资料检查；档案自动保存，医疗判断由医生审核","task_status","in_progress","created_by",a.user,"request_key",key,"payload_sha256",digest(bytes(key)),"created_at",now(),"updated_at",now()));
  db.insert("a_archive_automation",map("report_id",report,"revision",revision,"scope_code",a.scope,"data_mode",a.mode,"task_id",task,"requested_by",a.user,"matched_fields","[]","conflict_fields","[]","created_at",now(),"updated_at",now()));
  event(a,report,revision,task,"waiting_ocr","已创建档案小谷任务，持续跟进报告识别与整理，不自动修改职工基础资料");return status(a,report,revision);
 }
 public Map<String,Object> status(EhrActor a,String report,int revision){ehr.rawReport(a,report,revision,false);Map<String,Object> r=db.one("SELECT task_id taskId,workflow_state state,match_score matchScore,match_status matchStatus,match_reason reason,matched_fields matchedFields,conflict_fields conflictFields,decision_mode decisionMode,confirmed_by confirmedBy,confirmed_at confirmedAt,result_json result,input_digest inputDigest,last_error error,assessment_batch_id assessmentBatchId,updated_at updatedAt FROM a_archive_automation WHERE report_id=? AND revision=? AND scope_code=? AND data_mode=?",report,revision,a.scope,a.mode);if(r!=null){for(String key:Arrays.asList("matchedFields","conflictFields","result"))r.put(key,ehr.decode(r.get(key)));r.put("archive",archives.status(a,report,revision));r.put("exceptions",archives.issues(a,report,revision));if(originalQueue!=null)r.put("originalCheck",originalQueue.status(a,report,revision));}return r;}
 void event(EhrActor a,String report,int revision,String task,String state,String note){
  String target=Arrays.asList("auto_archived","partial_archived").contains(state)?"completed":Arrays.asList("completed","cancelled").contains(state)?state:Arrays.asList("needs_identity","needs_materials","waiting_review","blocked").contains(state)?"pending_review":"in_progress";
  Map<String,Object> old=db.one("SELECT task_status,result_note FROM a_digital_worker_tasks WHERE task_id=?",task);if(old==null)return;
  if(target.equals(old.get("task_status"))&&note.equals(old.get("result_note")))return;
  db.exec("UPDATE a_digital_worker_tasks SET task_status=?,result_note=?,row_version=row_version+1,updated_at=UTC_TIMESTAMP(6) WHERE task_id=?",target,note,task);
  db.insert("a_digital_worker_task_events",map("event_id",id(),"task_id",task,"actor","archive-xiaogu","action","archive_automation","from_status",old.get("task_status"),"to_status",target,"note",note,"created_at",now()));
  if(db.count("SELECT COUNT(*) FROM a_health_reports WHERE report_id=? AND revision=? AND scope_code=? AND data_mode=?",report,revision,a.scope,a.mode)>0)ehr.audit(a,null,report,revision,"archive_auto_"+state,note);
 }
 static boolean fullEvidence(String value){return value!=null&&!value.isEmpty()&&!value.contains("*")&&!value.contains("•");}
 @SuppressWarnings("unchecked") boolean sourceBacked(String report,int revision,Map<String,Object> row,Map<String,Object> candidate){
  Set<String> keys=new HashSet<>(Arrays.asList("full_name","sex","identity_number","phone"));Set<String> backed=new HashSet<>();Object parsed=ehr.decode(row.get("field_values"));if(!(parsed instanceof List))return false;
  for(Object object:(List<?>)parsed){Map<String,Object> f=(Map<String,Object>)object;String key=str(f.get("field_key"));if("gender".equals(key))key="sex";if(!keys.contains(key))continue;String v=str(f.get("value_raw")).trim();if(!fullEvidence(v))continue;Object refs=f.get("evidence_ids");if(!(refs instanceof List))continue;for(Object ref:(List<?>)refs){Map<String,Object> e=db.one("SELECT raw_fragment FROM a_report_evidence WHERE report_id=? AND revision=? AND evidence_id=?",report,revision,ref);if(e!=null&&java.util.regex.Pattern.compile("(?<![A-Za-z0-9])"+java.util.regex.Pattern.quote(v)+"(?![A-Za-z0-9])",java.util.regex.Pattern.CASE_INSENSITIVE).matcher(str(e.get("raw_fragment"))).find())backed.add(key);}}
  return backed.containsAll(Arrays.asList("full_name","sex"))&&((Boolean.TRUE.equals(candidate.get("nameIdentityMatch"))&&backed.contains("identity_number"))||(Boolean.TRUE.equals(candidate.get("namePhoneMatch"))&&backed.contains("phone")));
 }
 static Map<String,Object> decision(List<Map<String,Object>> candidates){
  List<Map<String,Object>> strong=new ArrayList<>();for(Map<String,Object> c:candidates)if(Boolean.TRUE.equals(c.get("autoEligible")))strong.add(c);
  Map<String,Object> best=candidates.isEmpty()?null:candidates.get(0);boolean auto=strong.size()==1;Map<String,Object> selected=auto?strong.get(0):best;
  String state=selected==null?"unmatched":Boolean.TRUE.equals(selected.get("identityConflict"))?"conflict":auto?"auto_candidate":num(selected.get("score"))>=90?"recommended":num(selected.get("score"))>=85?"manual_confirmation":"insufficient";
  return map("candidate",selected,"auto",auto,"status",state);
 }
 @Transactional(isolation=Isolation.READ_COMMITTED) @SuppressWarnings("unchecked") public void process(String report,int revision){
  Map<String,Object> hint=db.one("SELECT * FROM a_archive_automation WHERE report_id=? AND revision=?",report,revision);if(hint==null)return;EhrActor a=actor(hint);Map<String,Object> h;
  try{h=ehr.rawReport(a,report,revision,true);}catch(EhrException e){if(e.status==404){db.exec("UPDATE a_archive_automation SET workflow_state='cancelled',updated_at=UTC_TIMESTAMP(6) WHERE report_id=? AND revision=?",report,revision);event(a,report,revision,str(hint.get("task_id")),"cancelled","报告已移除或职工档案不可访问，自动任务停止");return;}throw e;}
  Map<String,Object> w=db.one("SELECT * FROM a_archive_automation WHERE report_id=? AND revision=? FOR UPDATE",report,revision);if(w==null||Arrays.asList("completed","cancelled").contains(str(w.get("workflow_state"))))return;String task=str(w.get("task_id"));
  if(db.count("SELECT COUNT(*) FROM a_report_series WHERE report_id=? AND current_revision=?",report,revision)==0){db.exec("UPDATE a_archive_automation SET workflow_state='cancelled' WHERE report_id=? AND revision=?",report,revision);event(a,report,revision,task,"cancelled","已有新报告版本，旧版本自动任务停止");return;}
  if(!Boolean.TRUE.equals(new DigitalWorkerSettings(db).get(a,"archive").get("acceptingTasks"))){update(w,"paused","档案小谷已暂停接单，任务保留等待恢复",null);event(a,report,revision,task,"waiting_ocr","档案小谷已暂停接单，任务保留等待恢复");return;}
  if("rejected".equals(h.get("review_status"))){update(w,"blocked","报告已退回，请更正后再处理",null);event(a,report,revision,task,"blocked","报告已退回，档案小谷保留原件并等待更正");return;}
  // Existing medically confirmed reports retain their signed snapshots; administrative state is separate.
  if("confirmed".equals(h.get("review_status"))&&archives.status(a,report,revision)!=null ){Map<String,Object> entry=archives.status(a,report,revision);archives.save(a,h,(Map<String,Object>)entry.get("quality"));handoff(a,h,w);return;}
  Map<String,Object> q=db.one("SELECT q.dispatch_state FROM a_ocr_dispatch_tasks q JOIN a_extraction_jobs j ON j.job_id=q.job_id WHERE j.report_id=? AND j.revision=? ORDER BY j.created_at DESC,j.job_id DESC LIMIT 1",report,revision);
  if(q!=null&&Arrays.asList("queued","running","retry_wait").contains(str(q.get("dispatch_state"))))return;
  if(q!=null&&Arrays.asList("failed","cancelled").contains(str(q.get("dispatch_state")))){archives.syncIssues(a,report,revision,Arrays.asList(ArchiveQualityService.issue("ocr_failed","ocr","blocking",null,"OCR识别失败或取消，原件保留，请重新识别或补录")));update(w,"blocked","OCR识别未完成，请在任务详情重试或核对原件补录",null);event(a,report,revision,task,"blocked","识别失败或被取消，已转异常处理；原件保留");return;}
  if(db.count("SELECT COUNT(*) FROM a_ehr_ocr_receipts o JOIN a_extraction_jobs j ON j.job_id=o.job_id WHERE j.report_id=? AND j.revision=?",report,revision)==0&&db.count("SELECT COUNT(*) FROM a_health_items WHERE report_id=? AND revision=?",report,revision)==0)return;
  Map<String,Object> detail=matching.detail(a,report,revision);List<Map<String,Object>> candidates=matching.candidates(matching.identity(h.get("field_values")),matching.preparedEmployees(a));Map<String,Object> d=decision(candidates),c=(Map<String,Object>)d.get("candidate");boolean confirmed=Arrays.asList("confirmed","archived","locked").contains(str(detail.get("matchStatus")))&&h.get("record_id")!=null;String reason;
  boolean auto=Boolean.TRUE.equals(d.get("auto"))&&c!=null&&sourceBacked(report,revision,h,c);
  if(auto&&!confirmed&&h.get("record_id")!=null&&!str(c.get("id")).equals(h.get("record_id"))){auto=false;d.put("status","rebind_required");}
  if(auto&&!confirmed){detail=matching.save(a,report,revision,str(detail.get("etag")),map("action","confirm","recordId",c.get("id"),"profileVersion",c.get("profileVersion"),"requestKey",UUID.nameUUIDFromBytes(bytes("archive-auto:"+report+":"+revision+":"+detail.get("etag"))).toString(),"identityVerified",true,"reason","档案小谷按"+POLICY+"核对原件证据：姓名、性别及完整身份证号或完整手机号一致且唯一匹配；仅自动关联，未审核医疗结果"));confirmed=true;db.exec("UPDATE a_archive_automation SET decision_mode='auto',confirmed_by='archive-xiaogu',confirmed_at=UTC_TIMESTAMP(6) WHERE report_id=? AND revision=?",report,revision);event(a,report,revision,task,"matched","已按原件证据及当前匹配规则自动关联职工");}
  else if(confirmed&&w.get("decision_mode")==null){Map<String,Object> m=matching.saved(a,report,revision,false);db.exec("UPDATE a_archive_automation SET decision_mode='manual',confirmed_by=?,confirmed_at=? WHERE report_id=? AND revision=?",m==null?"existing-clinical-review":m.get("updated_by"),m==null?now():m.get("updated_at"),report,revision);}
  reason=confirmed?"身份关联已确认，档案小谷继续整理报告并准备医生审核":"rebind_required".equals(d.get("status"))?"报告当前关联另一职工，需医生明确确认改绑；档案小谷未自动更改现有关联":c==null?"未找到可匹配职工，请人工核对身份":Boolean.TRUE.equals(c.get("identityConflict"))?"报告与基础资料存在身份冲突，已暂停自动关联":Boolean.TRUE.equals(d.get("auto"))?"匹配姓名、性别及身份证号或手机号尚缺可追溯原件证据，需人工核验":"姓名、性别与身份证号或完整手机号尚未同时一致，或匹配到多个职工，请人工确认";
  db.exec("UPDATE a_archive_automation SET match_score=?,match_status=?,match_reason=?,matched_fields=?,conflict_fields=?,last_error=NULL,updated_at=UTC_TIMESTAMP(6) WHERE report_id=? AND revision=?",c==null?null:c.get("score"),confirmed?"confirmed":d.get("status"),reason,ehr.encode(c==null?Collections.emptyList():c.get("matchedFields")),ehr.encode(c==null?Collections.emptyList():c.get("conflictFields")),report,revision);
  if(!confirmed){List<Map<String,Object>> issues=quality.medicalAlerts(report,revision);issues.add(ArchiveQualityService.issue("identity","identity","blocking",null,reason));archives.syncIssues(a,report,revision,issues);update(w,"needs_identity",reason,null);event(a,report,revision,task,"needs_identity",reason);return;}
  h=ehr.rawReport(a,report,revision,true);String record=str(h.get("record_id"));Map<String,Object> profile=ehr.profile(a,record,true);
  // An unambiguous ISO date is prepared for the final doctor's review; never invent a date.
  if(h.get("exam_date_verified")==null){String raw=str(h.get("exam_date_raw"));try{if(raw.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")&& !LocalDate.parse(raw).isAfter(LocalDate.now(ZoneOffset.UTC))){db.exec("UPDATE a_health_reports SET exam_date_verified=? WHERE report_id=? AND revision=?",raw,report,revision);h=ehr.rawReport(a,report,revision,false);}}catch(java.time.format.DateTimeParseException ignored){}}
  Map<String,Object> existingArchive=archives.status(a,report,revision);
  if(existingArchive!=null&&Arrays.asList("archived","partial").contains(existingArchive.get("archiveStatus"))){Map<String,Object> priorQuality=(Map<String,Object>)existingArchive.get("quality");String etag=hex(digest(bytes(ehr.encode(ehr.snapshot(report,revision)))));if(ArchiveQualityService.RULE.equals(priorQuality.get("rule"))&&etag.equals(priorQuality.get("inputEtag"))&&quality.originalStamp(a,report,revision).equals(priorQuality.get("originalStamp"))&&quality.verificationDigest(report,revision,etag,str(priorQuality.get("originalStamp"))).equals(priorQuality.get("verificationDigest"))){requestOriginalCheck(a,report,revision,priorQuality);handoff(a,h,w);return;}}
  Map<String,Object> checked=quality.inspect(a,h);archives.syncIssues(a,report,revision,(List<Map<String,Object>>)checked.get("issues"));
  db.exec("UPDATE a_digital_worker_tasks SET record_id=?,member_code=? WHERE task_id=?",record,profile.get("employee_no"),task);
  if(Boolean.TRUE.equals(checked.get("blocked"))){db.exec("UPDATE a_report_archive_entries SET archive_status='withheld',quality_status='blocked',quality_json=?,updated_at=UTC_TIMESTAMP(6) WHERE report_id=? AND revision=?",ehr.encode(checked),report,revision);update(w,"needs_materials","原件已保存，存在日期、重复或报告类型异常，等待核验后自动入档",checked);event(a,report,revision,task,"needs_materials","只处理阻塞异常，不自动签署医疗审核");return;}
  archives.save(a,h,checked);
  requestOriginalCheck(a,report,revision,checked);
  handoff(a,h,w);
 }
 void requestOriginalCheck(EhrActor a,String report,int revision,Map<String,Object> checked){if(originalQueue!=null&&num(checked.get("trustedItems"))+num(checked.get("notReportedItems"))<num(checked.get("totalItems")))originalQueue.request(a,report,revision,str(checked.get("inputEtag")),str(checked.get("originalStamp")));else if(originalQueue!=null&&num(checked.get("modelCrosscheckedItems"))==0)db.exec("UPDATE a_archive_original_checks SET check_status='cancelled',error_message='已通过原文规则核验，无需重复模型复核',finished_at=UTC_TIMESTAMP(6) WHERE report_id=? AND revision=? AND input_etag=? AND scope_code=? AND data_mode=? AND check_status IN ('queued','running')",report,revision,checked.get("inputEtag"),a.scope,a.mode);}
 @SuppressWarnings("unchecked") void handoff(EhrActor a,Map<String,Object> h,Map<String,Object> w){
  String report=str(h.get("report_id")),task=str(w.get("task_id"));int revision=num(h.get("revision"));Map<String,Object> entry=archives.status(a,report,revision);if(entry==null)return;
  Map<String,Object> checked=(Map<String,Object>)entry.get("quality");Map<String,Object> result=new LinkedHashMap<>(checked);result.put("archiveStatus",entry.get("archiveStatus"));result.put("archiveVersion",entry.get("archiveVersion"));result.put("medicalReviewStatus",entry.get("medicalReviewStatus"));result.put("policy",POLICY);
  List<?> trusted=(List<?>)checked.get("trustedItemIds");String source=str(entry.get("sourceDigest"));
  if(!trusted.isEmpty()&&(w.get("assessment_batch_id")==null||!source.equals(w.get("input_digest"))||!AiHealthReport.FORMAT.equals(((Map<?,?>)Optional.ofNullable(ehr.decode(w.get("result_json"))).orElse(Collections.emptyMap())).get("assessmentFormat")))){
   try{Map<String,Object> batch=assessment.enqueueArchive(a,report,revision,source);db.exec("UPDATE a_archive_automation SET assessment_batch_id=?,input_digest=? WHERE report_id=? AND revision=?",batch.get("batchId"),source,report,revision);w.put("assessment_batch_id",batch.get("batchId"));result.put("assessmentState","queued");result.put("assessmentFormat",AiHealthReport.FORMAT);}
   catch(EhrException deferred){result.put("assessmentState","waiting_service");result.put("assessmentMessage",deferred.getMessage());}
  }else result.put("assessmentState",trusted.isEmpty()?"waiting_trusted_data":"queued");
  if(AiHealthReport.FORMAT.equals(((Map<?,?>)Optional.ofNullable(ehr.decode(w.get("result_json"))).orElse(Collections.emptyMap())).get("assessmentFormat"))&&!result.containsKey("assessmentFormat"))result.put("assessmentFormat",AiHealthReport.FORMAT);
  Map<String,Object> assessmentTask=db.one("SELECT task_id,ai_state,error_code FROM a_ai_exam_tasks WHERE batch_id=? AND scope_code=? AND data_mode=? ORDER BY created_at DESC,task_id DESC LIMIT 1",w.get("assessment_batch_id"),a.scope,a.mode);
  if(assessmentTask!=null){result.put("assessmentTaskId",assessmentTask.get("task_id"));result.put("assessmentState",assessmentTask.get("ai_state"));result.put("assessmentError",assessmentTask.get("error_code"));}
  boolean partial="partial".equals(entry.get("archiveStatus"));String state=partial?"partial_archived":"auto_archived",reason=partial?"原报告及可信数据已自动入档，仅异常字段待核验；医疗结论未签署":"已自动归档入库；医疗审核独立进行";
  if(Arrays.asList("queued","running").contains(result.get("assessmentState")))reason+="；已衔接评估小谷";if("waiting_trusted_data".equals(result.get("assessmentState")))reason+="；等待可信指标后自动衔接评估";
  if("completed".equals(result.get("assessmentState")))reason+="；评估小谷已生成AI健康评估报告，待医生审核";if("failed".equals(result.get("assessmentState")))reason+="；评估小谷执行失败，请查看模型错误并重试";if("waiting_service".equals(result.get("assessmentState")))reason+="；评估服务待恢复，自动重试";
  if("confirmed".equals(h.get("review_status")))reason="档案已自动入库，医生已完成医疗审核；评估结果仍按权限签署";
  update(w,state,reason,result);event(a,report,revision,task,state,reason);
 }

 void update(Map<String,Object> w,String state,String reason,Map<String,Object> result){db.exec("UPDATE a_archive_automation SET workflow_state=?,match_reason=?,result_json=COALESCE(?,result_json),last_error=NULL,retry_after=DATE_ADD(UTC_TIMESTAMP(6),INTERVAL 10 SECOND),updated_at=UTC_TIMESTAMP(6) WHERE report_id=? AND revision=?",state,reason,result==null?null:ehr.encode(result),w.get("report_id"),w.get("revision"));}
 @Transactional public void failure(String report,int revision,String error){String safe=error==null?"处理失败，请稍后重试":error.substring(0,Math.min(900,error.length()));db.exec("UPDATE a_archive_automation SET last_error=?,retry_after=DATE_ADD(UTC_TIMESTAMP(6),INTERVAL 30 SECOND),updated_at=UTC_TIMESTAMP(6) WHERE report_id=? AND revision=?",safe,report,revision);}
}
