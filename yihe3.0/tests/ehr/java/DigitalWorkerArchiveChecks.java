package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Isolated MySQL fixtures, no real reports or external model requests; all writes rolled back. */
public class DigitalWorkerArchiveChecks {
    static int count;
    static void check(String name,boolean ok) {if(!ok)throw new AssertionError(name);count++;System.out.println("PASS "+name);}
    static void reject(String name,int status,Runnable operation) {
        try {operation.run();throw new AssertionError(name);}catch(EhrException error){check(name,error.status==status);}
    }
    static Map<String,Object> body(String record) {return map("worker","archive","type","archive_completion","title","合成档案核对任务","memberCode","","recordId",record,"priority","normal","instructions","合成测试","requestKey",id());}
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        DriverManagerDataSource source=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));
        EhrRepository db=new EhrRepository(source);EhrService ehr=new EhrService(db);
        DigitalWorkerArchiveContext archives=new DigitalWorkerArchiveContext(db,ehr);
        DigitalWorkerTaskService tasks=new DigitalWorkerTaskService(db,archives);
        DigitalWorkerModelClient model=new DigitalWorkerModelClient(new ModelRecognitionSettings()) {
            public Map<String,Object> status(){return map("configured",true);}
        };
        DigitalWorkerAiQueue queue=new DigitalWorkerAiQueue(db,model,archives);
        java.lang.reflect.Field mode=DigitalWorkerAiQueue.class.getDeclaredField("mode");mode.setAccessible(true);mode.set(queue,"synthetic");
        Map<String,Object> live=db.one("SELECT user_code,Org_Code org FROM yh_user_reginfo WHERE user_code='LOCAL-PREVIEW' AND User_Type=4 AND lock_flag=2");
        if(live==null)throw new AssertionError("Run only on the isolated preview database with its preview doctor");
        EhrActor actor=new EhrActor(str(live.get("user_code")),str(live.get("org")),"synthetic",true,false);
        EhrActor cross=new EhrActor(actor.user,"ARCHIVE_OTHER","synthetic",true,false);
        long before=db.count("SELECT COUNT(*) FROM a_digital_worker_tasks WHERE scope_code=? AND data_mode='synthetic'",actor.scope);
        new TransactionTemplate(new DataSourceTransactionManager(source)).execute(tx->{try{
            Map<String,Object> profile=HealthAgentChecks.fixture(ehr,actor,"ARC_"+id().substring(0,8));String record=str(profile.get("id"));
            String report=HealthAgentChecks.report(db,ehr,actor,record);
            db.exec("UPDATE a_health_reports SET exam_date_verified='2026-09-20',institution_raw='合成测试医院' WHERE report_id=?",report);
            String evidence=str(db.one("SELECT primary_evidence_id id FROM a_health_items WHERE report_id=?",report).get("id"));
            db.insert("a_health_conclusions",map("report_id",report,"revision",1,"conclusion_id",id(),"primary_evidence_id",evidence,"evidence_ids",ehr.encode(Arrays.asList(evidence)),"original_field_name","结论","original_text","合成资料不用于诊疗","completeness","complete","review_status","needs_review"));
            ehr.completeness(actor,record);
            Map<String,Object> snapshot=archives.read(actor,record);
            check("actual_archive_rules_show_complete",((List<?>)snapshot.get("missing")).isEmpty()&&((Number)snapshot.get("checkedCompleteness")).doubleValue()==100);
            check("candidate_status_not_falsely_archived",!"archived".equals(snapshot.get("status")));
            Map<String,Object> facts=archives.modelFacts(snapshot);
            Map<String,Object> factsWithoutTime=new LinkedHashMap<>(facts);factsWithoutTime.remove("capturedAt");
            check("model_context_does_not_send_identifiers_or_clinical_values",!facts.containsKey("name")&&!facts.containsKey("code")&&!facts.containsKey("recordId")&&!ehr.encode(factsWithoutTime).contains("8.2"));
            reject("cross_org_archive_context_denied",404,()->archives.read(cross,record));
            reject("cross_org_task_creation_denied",404,()->tasks.create(cross,body(record)));
            Map<String,Object> task=tasks.create(actor,body(""));String taskId=str(task.get("id"));
            check("name_in_title_does_not_auto_link",task.get("recordId")==null);
            Map<String,Object> unlinkedRequest=queue.request(actor,taskId,map("version",task.get("version")));
            Map<String,Object> first=queue.claim();Map<String,Object> unlinked=queue.modelInput(first);
            check("unlinked_input_explicit",Boolean.FALSE.equals(((Map<?,?>)unlinked.get("archive_context")).get("linked")));
            queue.finish(str(unlinkedRequest.get("attemptId")),map("model","fixture","output","合成旧草稿","usage","{}"),null);
            task=tasks.detail(actor,taskId);Object oldVersion=task.get("version");
            Map<String,Object> linked=tasks.linkArchive(actor,taskId,map("recordId",record,"version",oldVersion));
            check("link_old_draft_requires_new_review",linked.get("status").equals("queued")&&Boolean.TRUE.equals(((Map<?,?>)linked.get("ai")).get("outdated")));
            reject("stale_link_version_rejected",409,()->tasks.linkArchive(actor,taskId,map("recordId",record,"version",oldVersion)));
            Map<String,Object> request=queue.request(actor,taskId,map("version",linked.get("version")));
            check("duplicate_generation_not_queued_twice",request.get("attemptId").equals(queue.request(actor,taskId,map("version",linked.get("version"))).get("attemptId")));
            reject("cannot_switch_archive_during_model_request",409,()->tasks.linkArchive(actor,taskId,map("recordId",record,"version",tasks.detail(actor,taskId).get("version"))));
            Map<String,Object> attempt=queue.claim();Map<String,Object> input=queue.modelInput(attempt);
            Object busyVersion=tasks.detail(actor,taskId).get("version");
            reject("active_original_check_cannot_be_submitted_for_review",409,()->tasks.act(actor,taskId,map("version",busyVersion,"action","submit","note","不能跳过正在执行的原件核对")));
            check("model_receives_actual_completeness_snapshot",((Number)((Map<?,?>)input.get("archive_context")).get("checkedCompleteness")).doubleValue()==100);
            Map<String,Object> spoof=new LinkedHashMap<>(attempt);spoof.put("requested_by","missing-user");
            reject("revoked_doctor_access_rejected_before_model",403,()->queue.modelInput(spoof));
            queue.finish(str(request.get("attemptId")),map("model","fixture","output","按系统规则资料齐备，等待医生审核","usage","{}"),null);
            Map<String,Object> ready=tasks.detail(actor,taskId);Map<String,Object> ai=(Map<String,Object>)ready.get("ai");
            check("source_snapshot_persisted_with_draft",record.equals(((Map<?,?>)ai.get("archiveContext")).get("recordId"))&&!Boolean.TRUE.equals(ai.get("outdated")));
            check("model_result_still_requires_review",ready.get("status").equals("pending_review"));
            Object reviewVersion=ready.get("version");String attemptId=str(request.get("attemptId"));
            db.exec("UPDATE a_digital_worker_ai_attempts SET verification_json=? WHERE attempt_id=?",ehr.encode(map("state","blocked")),attemptId);
            reject("blocked_original_check_cannot_be_approved",409,()->tasks.act(actor,taskId,map("version",reviewVersion,"action","approve","note","合成审核","originalReviewed",true)));
            Map<String,Object> checked=map("state","partial","reportId",report,"revision",1,"reportDigest",archives.reportDigest(actor,record,report,1));
            db.exec("UPDATE a_digital_worker_ai_attempts SET verification_json=? WHERE attempt_id=?",ehr.encode(checked),attemptId);
            reject("original_review_confirmation_required",422,()->tasks.act(actor,taskId,map("version",reviewVersion,"action","approve","note","合成审核")));
            db.exec("UPDATE a_health_reports SET institution_raw='变更后的合成机构' WHERE report_id=?",report);
            reject("changed_report_invalidates_review",409,()->tasks.act(actor,taskId,map("version",reviewVersion,"action","approve","note","合成审核","originalReviewed",true)));
            checked.put("reportDigest",archives.reportDigest(actor,record,report,1));
            db.exec("UPDATE a_digital_worker_ai_attempts SET verification_json=? WHERE attempt_id=?",ehr.encode(checked),attemptId);
            check("explicit_review_can_complete_task",tasks.act(actor,taskId,map("version",reviewVersion,"action","approve","note","仅为合成夹具，已人工复核","originalReviewed",true)).get("status").equals("completed"));
            db.exec("UPDATE a_employee_roster_profiles SET deleted_at=UTC_TIMESTAMP(6) WHERE employee_id=?",profile.get("employee_id"));
            reject("deleted_archive_cannot_be_sent_to_model",403,()->queue.modelInput(attempt));
            check("unavailable_archive_not_silently_replaced",Boolean.TRUE.equals(((Map<?,?>)tasks.detail(actor,taskId).get("archive")).get("unavailable")));
            return null;
        }finally{tx.setRollbackOnly();}});
        check("fixtures_rolled_back",db.count("SELECT COUNT(*) FROM a_digital_worker_tasks WHERE scope_code=? AND data_mode='synthetic'",actor.scope)==before);
        System.out.println("PASS Digital worker archive checks: "+count+"; no external calls; all writes rolled back");
    }
}
