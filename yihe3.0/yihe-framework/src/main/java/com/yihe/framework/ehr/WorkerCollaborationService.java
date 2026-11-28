package com.yihe.framework.ehr;

import java.util.*;
import java.time.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Authorised operators act for a named worker; model output never authorises a transition. */
@Service @SuppressWarnings("unchecked")
public class WorkerCollaborationService {
 final EhrRepository db; final EhrService ehr; final QuestionnaireService receipts;
 static final Set<String> WORKERS=new HashSet<>(Arrays.asList("archive","assess","care","plan","follow"));
 public WorkerCollaborationService(EhrRepository db,EhrService ehr,QuestionnaireService receipts){this.db=db;this.ehr=ehr;this.receipts=receipts;}
 void doctor(EhrActor a){require(!a.mobile&&a.doctor,403,"协作工单仅供本单位授权专业人员操作");}
 String field(Map<String,Object>b,String k,int n,boolean r){return receipts.field(b,k,n,r);}
 static boolean route(String from,String to,String kind){
  return WORKERS.contains(from)&&WORKERS.contains(to)&&(
   to.equals("archive")&&!from.equals("archive")&&kind.equals("archive_update")||
   from.equals("archive")&&to.equals("assess")&&kind.equals("physical_assessment")||
   from.equals("archive")&&to.equals("care")&&kind.equals("psychological_assessment")||
   from.equals("assess")&&to.equals("plan")&&kind.equals("physical_plan")||
   from.equals("care")&&to.equals("plan")&&kind.equals("psychological_plan")||
   from.equals("plan")&&to.equals("follow")&&kind.equals("followup_execution")||
   from.equals("follow")&&to.equals("plan")&&kind.equals("execution_feedback")||
   from.equals("plan")&&to.equals("care")&&kind.equals("psychological_reassessment"));
 }
 Map<String,Object> owned(EhrActor a,String id,boolean lock){doctor(a);Map<String,Object>r=db.one("SELECT * FROM a_worker_orders WHERE order_id=? AND scope_code=? AND data_mode=?"+(lock?" FOR UPDATE":""),id,a.scope,a.mode);require(r!=null,404,"工单不存在或不在授权范围");ehr.profile(a,str(r.get("record_id")),false);return r;}
 void event(EhrActor a,Map<String,Object>r,String worker,String action,String from,String to,Object detail){db.insert("a_worker_order_events",map("event_id",id(),"order_id",r.get("order_id"),"scope_code",a.scope,"data_mode",a.mode,"action",action,"actor",a.user,"acting_worker",worker,"from_state",from,"to_state",to,"detail_json",ehr.encode(detail),"created_at",now()));}
 @Transactional public Object create(EhrActor a,Map<String,Object>b){
  doctor(a);Map<String,Object>receipt=receipts.receipt(a,"worker-order-create",b);if(receipt.get("result_json")!=null)return ehr.decode(receipt.get("result_json"));
  String from=field(b,"sourceWorker",16,true),to=field(b,"targetWorker",16,true),kind=field(b,"kind",40,true),record=field(b,"recordId",36,true),parent=field(b,"parentId",36,false),report=field(b,"reportId",36,false);
  require(route(from,to,kind),422,"派单超出岗位职责：心理评估只能移交方案中心，不得直接执行随访");ehr.profile(a,record,false);
  if(!parent.isEmpty()){Map<String,Object>p=owned(a,parent,true);require(from.equals(p.get("target_worker"))&&record.equals(p.get("record_id"))&&Arrays.asList("awaiting_acceptance","completed").contains(p.get("order_state")),409,"上游工单必须已提交或验收，且员工、接单岗位一致");}
  if(!report.isEmpty())require(db.count("SELECT COUNT(*) FROM a_health_reports WHERE report_id=? AND scope_code=? AND data_mode=?",report,a.scope,a.mode)==1,404,"报告不可访问");
  LocalDateTime due;try{due=LocalDateTime.parse(field(b,"dueAt",32,true));}catch(Exception e){throw new EhrException(422,"截止时间须为北京时间 yyyy-MM-ddTHH:mm");}require(due.isAfter(LocalDateTime.now(ZoneId.of("Asia/Shanghai"))),422,"截止时间必须晚于当前时间");
  String priority=field(b,"priority",16,true);require(Arrays.asList("normal","high","urgent").contains(priority),422,"优先级无效");Object payload=b.getOrDefault("payload",Collections.emptyMap());require(payload instanceof Map&&ehr.encode(payload).length()<=50000,422,"交接数据必须为对象且不超过50000字符");
  if(kind.equals("psychological_plan")){
   Map<?,?>p=(Map<?,?>)payload;
   require(db.count("SELECT COUNT(*) FROM a_psych_worker_items i JOIN a_questionnaire_responses s ON s.response_id=i.response_id AND s.scope_code=i.scope_code AND s.data_mode=i.data_mode WHERE i.item_id=? AND i.record_id=? AND i.scope_code=? AND i.data_mode=? AND s.response_id=? AND s.review_state='confirmed' AND (i.emergency=0 OR i.emergency_ack_by IS NOT NULL)",p.get("sourceItemId"),record,a.scope,a.mode,p.get("responseId"))==1,409,"心理方案派单必须关联本人的已审核评估，并先处置紧急信号");
  }
  String id=id(),state=Boolean.TRUE.equals(b.get("dispatch"))?"queued":"draft";Map<String,Object>r=map("order_id",id,"order_no","WO-"+id.replace("-","").toUpperCase(Locale.ROOT),"scope_code",a.scope,"data_mode",a.mode,"parent_id",parent.isEmpty()?null:parent,"record_id",record,"report_id",report.isEmpty()?null:report,"source_worker",from,"target_worker",to,"order_kind",kind,"title",field(b,"title",120,true),"instructions",field(b,"instructions",2000,true),"priority",priority,"due_at",due.toString().replace('T',' '),"order_state",state,"payload_json",ehr.encode(payload),"created_by",a.user,"emergency",Boolean.TRUE.equals(b.get("emergency"))?1:0,"created_at",now(),"updated_at",now());db.insert("a_worker_orders",r);event(a,r,from,"created",null,state,map("requiresProfessionalReview",true));return receipts.finish(a,b,map("id",id,"state",state,"version",1));
 }
 @Transactional public Object detail(EhrActor a,String id){Map<String,Object>r=owned(a,id,false);r.put("payload",ehr.decode(r.remove("payload_json")));r.put("result",ehr.decode(r.remove("result_json")));r.put("events",db.all("SELECT action,actor,acting_worker,from_state,to_state,detail_json,created_at FROM a_worker_order_events WHERE order_id=? AND scope_code=? AND data_mode=? ORDER BY created_at,event_id",id,a.scope,a.mode));ehr.audit(a,str(r.get("record_id")),null,null,"worker_order_accessed","查看协作工单 "+id);return r;}
 @Transactional public Object list(EhrActor a,Map<String,String>q){doctor(a);String worker=q.getOrDefault("worker",""),tab=q.getOrDefault("tab","hall");require(worker.isEmpty()||WORKERS.contains(worker),422,"岗位无效");require(Arrays.asList("hall","queued","mine","sent","acceptance","history","all").contains(tab),422,"工单视图无效");String where=" WHERE o.scope_code=? AND o.data_mode=?";List<Object>args=new ArrayList<>(Arrays.asList(a.scope,a.mode));if(!worker.isEmpty()){where+=tab.equals("sent")?" AND o.source_worker=?":tab.equals("acceptance")?" AND o.source_worker=?":" AND o.target_worker=?";args.add(worker);}if(tab.equals("queued")||tab.equals("hall"))where+=" AND o.order_state='queued'";if(tab.equals("mine")){where+=" AND o.claimed_by=? AND o.order_state IN ('claimed','in_progress','returned')";args.add(a.user);}if(tab.equals("acceptance"))where+=" AND o.order_state='awaiting_acceptance'";if(tab.equals("history"))where+=" AND o.order_state IN ('completed','cancelled','failed')";String search=q.getOrDefault("search","").trim();require(search.length()<=100,422,"关键词过长");if(!search.isEmpty()){where+=" AND (o.title LIKE ? ESCAPE '!' OR p.employee_name LIKE ? ESCAPE '!' OR p.employee_no LIKE ? ESCAPE '!')";String s="%"+like(search)+"%";args.addAll(Arrays.asList(s,s,s));}int page=1,limit=20;try{page=Integer.parseInt(q.getOrDefault("page","1"));limit=Integer.parseInt(q.getOrDefault("limit","20"));}catch(Exception e){throw new EhrException(422,"分页无效");}require(page>0&&page<100000&&limit>0&&limit<=100,422,"分页无效");String join=" FROM a_worker_orders o JOIN a_health_records r ON r.record_id=o.record_id AND r.scope_code=o.scope_code AND r.data_mode=o.data_mode JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode AND p.deleted_at IS NULL";long total=db.count("SELECT COUNT(*)"+join+where,args.toArray());args.add(limit);args.add((page-1)*limit);return map("items",db.all("SELECT o.order_id,o.order_no,o.source_worker,o.target_worker,o.order_kind,o.title,o.order_state,o.priority,o.record_id,o.row_version,o.claimed_by,o.due_at,o.created_at,p.employee_name,p.employee_no,(o.due_at<CONVERT_TZ(UTC_TIMESTAMP(),'+00:00','+08:00') AND o.order_state NOT IN ('completed','cancelled')) overdue"+join+where+" ORDER BY FIELD(o.priority,'urgent','high','normal'),o.created_at DESC LIMIT ? OFFSET ?",args.toArray()),"total",total,"page",page,"limit",limit);}
 @Transactional public Object act(EhrActor a,String id,Map<String,Object>b){
  doctor(a);Map<String,Object>receipt=receipts.receipt(a,"worker-order-action-"+id,b);if(receipt.get("result_json")!=null)return ehr.decode(receipt.get("result_json"));Map<String,Object>r=owned(a,id,true);require(b.get("version") instanceof Number&&num(b.get("version"))==num(r.get("row_version")),409,"工单已更新，请刷新");String action=field(b,"action",32,true),acting=field(b,"actingWorker",16,true),before=str(r.get("order_state")),after=before,note=field(b,"note",2000,true);boolean sender=acting.equals(r.get("source_worker")),receiver=acting.equals(r.get("target_worker"));require(sender||receiver,403,"该岗位不是工单参与方");
  switch(action){
   case "dispatch":require(sender&&before.equals("draft"),409,"仅待派发工单可发出");after="queued";break;
   case "claim":require(receiver&&before.equals("queued"),409,"工单已被领取或不可领取");after="claimed";db.exec("UPDATE a_worker_orders SET claimed_by=? WHERE order_id=?",a.user,id);break;
   case "start":require(receiver&&a.user.equals(r.get("claimed_by"))&&Arrays.asList("claimed","returned").contains(before),409,"请先领取本人任务");after="in_progress";break;
   case "submit":require(receiver&&a.user.equals(r.get("claimed_by"))&&before.equals("in_progress"),409,"只能提交本人执行中任务");Object result=b.get("result");require(result instanceof Map&&!((Map<?,?>)result).isEmpty()&&ehr.encode(result).length()<=50000,422,"请提交结构化执行结果，最多50000字符");db.exec("UPDATE a_worker_orders SET result_json=? WHERE order_id=?",ehr.encode(result),id);after="awaiting_acceptance";break;
   case "accept":require(sender&&before.equals("awaiting_acceptance"),409,"仅发单岗位可验收已提交结果");require(Boolean.TRUE.equals(b.get("professionalConfirmed")),422,"请明确确认已由专业人员核验结果");require(num(r.get("emergency"))==0||r.get("emergency_ack_by")!=null,409,"紧急信号未启动人工响应，禁止结案");db.exec("UPDATE a_worker_orders SET accepted_by=? WHERE order_id=?",a.user,id);after="completed";break;
   case "return":require(sender&&before.equals("awaiting_acceptance"),409,"仅待验收结果可退回");after="returned";break;
   case "fail":require(receiver&&a.user.equals(r.get("claimed_by"))&&before.equals("in_progress"),409,"仅本人执行中任务可记录失败");after="failed";break;
   case "retry":require((sender||a.user.equals(r.get("claimed_by")))&&before.equals("failed"),409,"只能重试失败任务");after="queued";db.exec("UPDATE a_worker_orders SET claimed_by=NULL WHERE order_id=?",id);break;
   case "cancel":require(sender&&!Arrays.asList("completed","cancelled").contains(before),409,"仅发单岗位可取消未完成任务");require(num(r.get("emergency"))==0||r.get("emergency_ack_by")!=null,409,"紧急工单未人工处置不可取消");after="cancelled";break;
   case "reassign":require(sender&&Arrays.asList("draft","queued","failed","returned").contains(before),409,"当前状态不可转派");String target=field(b,"targetWorker",16,true);require(route(str(r.get("source_worker")),target,str(r.get("order_kind"))),422,"禁止跨岗位职责转派");db.exec("UPDATE a_worker_orders SET target_worker=?,claimed_by=NULL WHERE order_id=?",target,id);after="queued";break;
   case "emergency_ack":require(num(r.get("emergency"))==1&&Boolean.TRUE.equals(b.get("contactConfirmed")),422,"请确认已启动人工响应并记录处置");db.exec("UPDATE a_worker_orders SET emergency_ack_by=? WHERE order_id=?",a.user,id);break;
   default:throw new EhrException(422,"工单操作无效");
  }
  db.exec("UPDATE a_worker_orders SET order_state=?,row_version=row_version+1,updated_at=UTC_TIMESTAMP(6) WHERE order_id=?",after,id);event(a,r,acting,action,before,after,map("note",note));return receipts.finish(a,b,map("id",id,"state",after,"version",num(r.get("row_version"))+1));
 }
}
