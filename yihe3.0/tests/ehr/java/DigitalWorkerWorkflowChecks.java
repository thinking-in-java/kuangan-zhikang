package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Real scoped SQL, synthetic records, deterministic model/original fixtures; every write rolls back. */
public class DigitalWorkerWorkflowChecks {
    static int count;static List<Map<String,Object>> snapshots=new ArrayList<>();
    static void check(String label,boolean ok){if(!ok)throw new AssertionError(label);count++;System.out.println("PASS "+label);}
    static void reject(String label,int status,Runnable op){try{op.run();throw new AssertionError(label);}catch(EhrException error){check(label,error.status==status);}}
    static class Model extends DigitalWorkerModelClient {
        boolean fail;int calls;
        Model(){super(new ModelRecognitionSettings());}
        public Map<String,Object> status(){return map("configured",true,"model","fixture");}
        @SuppressWarnings("unchecked") public Map<String,Object> taskInsights(Map<String,Object> facts){calls++;if(fail)throw new EhrException(503,"MODEL_UNAVAILABLE");List<Map<String,Object>> items=(List<Map<String,Object>>)facts.get("items");return map("explanations",Arrays.asList(map("evidenceId",(items.isEmpty()?"absent":items.get(0).get("id")),"text","应结合原报告和实际反馈，由专业人员核验。"),map("evidenceId","invented","text","无来源说明"),map("evidenceId",(items.isEmpty()?"absent":items.get(0).get("id")),"text","已发送\n处方")),"questions",Arrays.asList("复查结果的日期与来源是什么？"),"reviewNotes",Arrays.asList("已通知本人"),"options",Arrays.asList("结合本人目标讨论可行的生活管理方式。"));}
    }
    static class Originals extends DigitalWorkerOriginalCheck {
        boolean available=true;
        Originals(EhrRepository db,EhrService ehr,Model model){super(db,ehr,model);}
        public Map<String,Object> sourceBundle(EhrActor actor,String report,int rev){return map("available",available,"reason",available?null:"合成测试：原件缺失","sources",Collections.emptyList(),"pages",Collections.emptyList());}
    }
    @SuppressWarnings("unchecked") static Map<String,Object> result(DigitalWorkerTaskResults service,EhrService ehr,EhrActor actor,String record,String type,Map<String,Object> context){return (Map<String,Object>)service.generate(map("task_type",type,"record_id",record,"scope_code",actor.scope,"data_mode",actor.mode,"requested_by",actor.user,"task_context_json",ehr.encode(context),"instructions","合成测试" )).get("taskResult");}
    @SuppressWarnings("unchecked") static List<Map<String,Object>> section(Map<String,Object> result,String kind){return (List<Map<String,Object>>)((List<Map<String,Object>>)result.get("sections")).stream().filter(row->kind.equals(row.get("kind"))).findFirst().get().get("rows");}
    @SuppressWarnings("unchecked") public static void main(String[] args){
        check("nine_distinct_profiles",DigitalWorkerTaskProfiles.all().size()==9&&DigitalWorkerTaskProfiles.all().stream().map(p->p.get("resultTitle")).distinct().count()==9);
        reject("unknown_task_context_field_rejected",422,()->DigitalWorkerTaskProfiles.context("routine_followup",map("sql","SELECT")));
        reject("bad_contact_status_rejected",422,()->DigitalWorkerTaskProfiles.context("routine_followup",map("contactOutcome","sent")));
        reject("contacted_requires_time",422,()->DigitalWorkerTaskProfiles.context("routine_followup",map("contactOutcome","reached")));
        reject("future_contact_not_a_fact",422,()->DigitalWorkerTaskProfiles.context("routine_followup",map("contactOutcome","reached","contactAt","2099-01-01T10:00")));
        reject("invalid_date_rejected",422,()->DigitalWorkerTaskProfiles.context("plan_adjustment",map("nextDueDate","2026-99-99")));
        check("valid_contact_context_kept",DigitalWorkerTaskProfiles.context("routine_followup",map("contactOutcome","reached","contactAt","2026-11-18T10:00","feedback","本人反馈暂未复查")).size()==3);
        DriverManagerDataSource source=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));EhrRepository db=new EhrRepository(source);EhrService ehr=new EhrService(db);DigitalWorkerArchiveContext archives=new DigitalWorkerArchiveContext(db,ehr);Model model=new Model();Originals originals=new Originals(db,ehr,model);DigitalWorkerTaskResults service=new DigitalWorkerTaskResults(db,ehr,archives,originals,model);DigitalWorkerTaskService tasks=new DigitalWorkerTaskService(db,archives);
        Map<String,Object> live=db.one("SELECT user_code,Org_Code org FROM yh_user_reginfo WHERE user_code='LOCAL-PREVIEW' AND User_Type=4 AND lock_flag=2");if(live==null)throw new AssertionError("Use isolated preview DB");EhrActor actor=new EhrActor(text(live,"user_code"),text(live,"org"),"synthetic",true,false);
        long before=db.count("SELECT COUNT(*) FROM a_digital_worker_tasks WHERE scope_code=?",actor.scope);
        new TransactionTemplate(new DataSourceTransactionManager(source)).execute(tx->{try{
            String record=text(HealthAgentChecks.fixture(ehr,actor,"WF_"+id().substring(0,8)),"id");String report=MiniappAgentChecks.report(db,ehr,actor,record,true);
            Map<String,Object> interpretation=result(service,ehr,actor,record,"report_interpretation",map());
            check("report_result_has_real_items_and_original_read",section(interpretation,"evidence").size()==1&&Boolean.TRUE.equals(interpretation.get("originalRead"))&&Boolean.TRUE.equals(interpretation.get("reportReviewed")));
            check("explanations_require_existing_item_and_disallow_fake_actions",section(interpretation,"explanations").size()==1);
            check("no_unfounded_model_review_note",!ehr.encode(interpretation).contains("已通知本人"));
            check("report_has_raw_conclusion_and_comparability",((List<Map<String,Object>>)interpretation.get("sections")).stream().anyMatch(s->"paragraphs".equals(s.get("kind")))&&section(interpretation,"comparison").size()==1);
            for(Map<String,Object> profile:DigitalWorkerTaskProfiles.all()){
                String type=text(profile,"type");if("archive_completion".equals(type))continue;Map<String,Object> r=result(service,ehr,actor,record,type,map());
                snapshots.add(map("id",id(),"worker",profile.get("recommendedWorker"),"type",type,"title","合成界面测试 · "+profile.get("name"),"status","pending_review","priority","normal","memberCode","SYNTHETIC","recordId",record,"instructions","隔离合成数据，仅用于界面回归验证","version",1,"taskContext",map(),"events",Collections.emptyList(),"archive",map("linked",false),"canManage",true,"profile",profile,"ai",map("state","succeeded","taskResult",r,"output",r.get("summary"))));
                check("structured_workflow_"+type,"task_result".equals(r.get("kind"))&&type.equals(r.get("taskType"))&&!((List<?>)r.get("sections")).isEmpty()&&Boolean.TRUE.equals(r.get("reviewRequired")));
            }
            check("invitation_never_claims_sent","not_sent".equals(result(service,ehr,actor,record,"visit_invitation",map()).get("executionState")));
            check("uncontacted_followup_needs_actual_facts","needs_info".equals(result(service,ehr,actor,record,"routine_followup",map()).get("state")));
            Map<String,Object> actual=DigitalWorkerTaskProfiles.context("routine_followup",map("contactOutcome","reached","contactAt","2026-11-18T10:00","feedback","本人表示尚未复查","nextDueDate","2026-11-25"));
            Map<String,Object> follow=result(service,ehr,actor,record,"routine_followup",actual);
            check("actual_followup_ready_and_provenance_explicit","ready_for_review".equals(follow.get("state"))&&"contact_recorded".equals(follow.get("executionState"))&&ehr.encode(follow).contains("本人表示尚未复查"));
            check("contact_without_feedback_needs_info","needs_info".equals(result(service,ehr,actor,record,"routine_followup",map("contactOutcome","reached","contactAt","2026-11-18T10:00")).get("state")));
            check("plan_without_current_plan_not_ready","needs_info".equals(result(service,ehr,actor,record,"plan_adjustment",map()).get("state")));
            check("plan_never_auto_applied","not_applied".equals(result(service,ehr,actor,record,"plan_adjustment",map("currentPlan","合成当前方案")).get("executionState")));
            check("no_confirmed_assessment_not_default_low",section(result(service,ehr,actor,record,"assessment",map()),"risks").isEmpty());
            check("complaint_requires_facts","needs_info".equals(result(service,ehr,actor,record,"complaint_followup",map()).get("state")));
            check("complaint_facts_not_auto_replied","not_replied".equals(result(service,ehr,actor,record,"complaint_followup",map("complaintText","合成本人反映预约未得到答复")).get("executionState")));
            originals.available=false;int calls=model.calls;
            check("missing_original_prevents_ready_interpretation","needs_info".equals(result(service,ehr,actor,record,"report_interpretation",map()).get("state"))&&model.calls==calls);originals.available=true;
            check("unlinked_subject_blocked","blocked".equals(result(service,ehr,actor,"","assessment",map()).get("state")));
            EhrActor cross=new EhrActor(actor.user,"WF_OTHER","synthetic",true,false);
            reject("cross_org_workflow_cannot_read_report",404,()->result(service,ehr,cross,record,"report_interpretation",map()));
            model.fail=true;Map<String,Object> fallback=result(service,ehr,actor,record,"report_interpretation",map());check("model_failure_keeps_source_facts_truthful",!Boolean.TRUE.equals(fallback.get("modelUsed"))&&section(fallback,"evidence").size()==1);model.fail=false;
            String unreviewed=text(HealthAgentChecks.fixture(ehr,actor,"UW_"+id().substring(0,8)),"id");HealthAgentChecks.report(db,ehr,actor,unreviewed);calls=model.calls;check("unreviewed_report_not_clinical_ready","needs_info".equals(result(service,ehr,actor,unreviewed,"report_interpretation",map()).get("state"))&&model.calls==calls);
            Map<String,Object> create=map("worker","follow","type","routine_followup","title","合成随访任务","recordId",record,"priority","normal","requestKey",id(),"taskContext",actual);
            Map<String,Object> task=tasks.create(actor,create);String taskId=text(task,"id");check("manual_context_persisted",actual.equals(task.get("taskContext"))&&task.get("contextSavedAt")!=null);
            Map<String,Object> changed=new TreeMap<>(actual);changed.put("feedback","已补充原始反馈");Object oldVersion=task.get("version");task=tasks.saveContext(actor,taskId,map("version",oldVersion,"taskContext",changed));check("context_save_updates_version",num(task.get("version"))>num(oldVersion)&&changed.equals(task.get("taskContext")));
            reject("stale_context_save_rejected",409,()->tasks.saveContext(actor,taskId,map("version",oldVersion,"taskContext",changed)));
            Map<String,Object> savedResult=result(service,ehr,actor,record,"routine_followup",changed);String attempt=id();db.exec("UPDATE a_digital_worker_tasks SET task_status='pending_review' WHERE task_id=?",taskId);
            db.insert("a_digital_worker_ai_attempts",map("attempt_id",attempt,"task_id",taskId,"request_state","succeeded","task_version",task.get("version"),"requested_by",actor.user,"model_name","fixture","output_text","合成随访记录","usage_json","{}","result_json",ehr.encode(savedResult),"created_at",now(),"updated_at",now()));
            Object version=task.get("version");reject("result_review_requires_explicit_ack",422,()->tasks.act(actor,taskId,map("version",version,"action","approve","note","合成审核")));
            savedResult.put("state","needs_info");db.exec("UPDATE a_digital_worker_ai_attempts SET result_json=? WHERE attempt_id=?",ehr.encode(savedResult),attempt);
            reject("missing_facts_cannot_be_approved",409,()->tasks.act(actor,taskId,map("version",version,"action","approve","note","合成审核","workflowReviewed",true)));
            savedResult.put("state","ready_for_review");db.exec("UPDATE a_digital_worker_ai_attempts SET result_json=? WHERE attempt_id=?",ehr.encode(savedResult),attempt);
            db.exec("UPDATE a_digital_worker_tasks SET task_context_json=? WHERE task_id=?",ehr.encode(actual),taskId);
            reject("changed_manual_facts_invalidates_review",409,()->tasks.act(actor,taskId,map("version",version,"action","approve","note","合成审核","workflowReviewed",true)));
            db.exec("UPDATE a_digital_worker_tasks SET task_context_json=? WHERE task_id=?",ehr.encode(changed),taskId);
            check("explicit_review_completes_task_record",tasks.act(actor,taskId,map("version",version,"action","approve","note","仅审核合成随访事实记录","workflowReviewed",true)).get("status").equals("completed"));
            check("review_does_not_create_notifications_or_change_report",archives.reportDigest(actor,record,report,1).equals(savedResult.get("sourceReportDigest")));
            return null;
        }finally{tx.setRollbackOnly();}});
        check("workflow_fixtures_rolled_back",before==db.count("SELECT COUNT(*) FROM a_digital_worker_tasks WHERE scope_code=?",actor.scope));
        if(System.getenv("EHR_WORKFLOW_FIXTURES")!=null)try{java.nio.file.Files.write(java.nio.file.Paths.get(System.getenv("EHR_WORKFLOW_FIXTURES")),bytes(ehr.encode(snapshots)));}catch(Exception error){throw new RuntimeException(error);}
        System.out.println("PASS Digital worker workflow checks: "+count+"; all writes rolled back; no external requests");
    }
}
