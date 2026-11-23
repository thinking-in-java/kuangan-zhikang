package com.yihe.framework.ehr;
import java.util.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Configuration has real admission/AI effects. Isolated SQL fixtures, no external model calls. */
public class DigitalWorkerSettingsChecks {
    static int count;
    static void check(String name,boolean ok){if(!ok)throw new AssertionError(name);count++;System.out.println("PASS "+name);}
    static void reject(String name,int code,Runnable call){try{call.run();throw new AssertionError(name);}catch(EhrException error){check(name,error.status==code);}}
    static Map<String,Object> payload(Map<String,Object> config){Map<String,Object> body=new LinkedHashMap<>();for(String key:DigitalWorkerSettings.FIELDS)body.put(key,config.get(key));body.put("version",config.get("version"));return body;}
    static Map<String,Object> task(){return map("worker","care","type","complaint_followup","title","合成配置验证任务","priority","normal","requestKey",id());}
    @SuppressWarnings("unchecked") public static void main(String[] args)throws Exception{
        DriverManagerDataSource ds=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));EhrRepository db=new EhrRepository(ds);EhrService ehr=new EhrService(db);DigitalWorkerSettings settings=new DigitalWorkerSettings(db);DigitalWorkerArchiveContext archives=new DigitalWorkerArchiveContext(db,ehr);DigitalWorkerTaskService tasks=new DigitalWorkerTaskService(db,archives);DigitalWorkerModelClient model=new DigitalWorkerModelClient(new ModelRecognitionSettings()){public Map<String,Object> status(){return map("configured",true);}};DigitalWorkerAiQueue queue=new DigitalWorkerAiQueue(db,model,archives);
        String org="CFG_"+id().substring(0,8);EhrActor actor=new EhrActor("LOCAL-PREVIEW",org,"synthetic",true,false),viewer=new EhrActor(actor.user,org,"synthetic",false,false),cross=new EhrActor(actor.user,"CFG_OTHER","synthetic",true,false);
        new TransactionTemplate(new DataSourceTransactionManager(ds)).execute(tx->{try{
            db.exec("UPDATE yh_user_reginfo SET Org_Code=? WHERE user_code='LOCAL-PREVIEW'",org);
            Map<String,Object> initial=settings.get(actor,"care");check("defaults_preserve_existing_behavior",Boolean.TRUE.equals(initial.get("acceptingTasks"))&&Boolean.TRUE.equals(initial.get("aiEnabled"))&&((List<?>)initial.get("allowedTypes")).size()==9);
            check("five_configurable_workers",((List<?>)settings.all(actor).get("items")).size()==5);check("read_only_actor_cannot_configure",Boolean.FALSE.equals(settings.all(viewer).get("canConfigure")));
            reject("viewer_write_denied",403,()->settings.save(viewer,"care",payload(initial)));reject("mobile_config_denied",403,()->settings.all(new EhrActor(actor.user,org,"synthetic",false,true)));
            Map<String,Object> invalid=payload(initial);invalid.put("apiKey","do-not-store");reject("global_secret_fields_rejected",422,()->settings.save(actor,"care",invalid));
            Map<String,Object> noTypes=payload(initial);noTypes.put("allowedTypes",Collections.emptyList());reject("empty_allowed_types_rejected",422,()->settings.save(actor,"care",noTypes));
            Map<String,Object> badDefault=payload(initial);badDefault.put("allowedTypes",Arrays.asList("complaint_followup"));reject("default_must_be_allowed",422,()->settings.save(actor,"care",badDefault));
            Map<String,Object> fraction=payload(initial);fraction.put("maxActiveTasks",1.5);reject("fractional_quota_rejected",422,()->settings.save(actor,"care",fraction));
            Map<String,Object> auto=payload(initial);auto.put("aiEnabled",false);reject("ai_default_requires_ai_enabled",422,()->settings.save(actor,"care",auto));
            Map<String,Object> body=payload(initial);body.put("acceptingTasks",false);Map<String,Object> paused=settings.save(actor,"care",body);
            check("save_persisted_and_versioned",num(paused.get("version"))==1&&!Boolean.TRUE.equals(settings.get(actor,"care").get("acceptingTasks"))&&actor.user.equals(paused.get("updatedBy")));
            check("cross_org_default_isolation",Boolean.TRUE.equals(settings.get(cross,"care").get("acceptingTasks")));check("real_synthetic_config_isolation",Boolean.TRUE.equals(settings.get(new EhrActor(actor.user,org,"real",true,false),"care").get("acceptingTasks")));
            reject("stale_config_version_denied",409,()->settings.save(actor,"care",payload(initial)));reject("paused_worker_cannot_take_new_task",409,()->tasks.create(actor,task()));
            body=payload(paused);body.putAll(map("acceptingTasks",true,"maxActiveTasks",1,"allowedTypes",Arrays.asList("complaint_followup"),"defaultType","complaint_followup","defaultPriority","high","defaultUseAi",false,"instructions","只列出有来源的事实"));Map<String,Object> ready=settings.save(actor,"care",body);
            Map<String,Object> taskBody=task(),first=tasks.create(actor,taskBody);String taskId=text(first,"id");check("task_detail_exposes_saved_worker_policy",Boolean.FALSE.equals(((Map<?,?>)first.get("workerConfig")).get("defaultUseAi")));
            check("default_ai_option_does_not_auto_enqueue",db.count("SELECT COUNT(*) FROM a_digital_worker_ai_attempts WHERE task_id=?",taskId)==0);reject("worker_quota_enforced",409,()->tasks.create(actor,task()));
            Map<String,Object> differentType=task();differentType.put("type","archive_completion");reject("allowed_type_enforced",422,()->tasks.create(actor,differentType));
            check("retry_is_idempotent_even_at_capacity",taskId.equals(tasks.create(actor,taskBody).get("id")));
            body=payload(ready);body.put("acceptingTasks",false);Map<String,Object> pauseWithTask=settings.save(actor,"care",body);check("pause_preserves_existing_tasks",tasks.detail(actor,taskId).get("status").equals("queued")&&taskId.equals(tasks.create(actor,taskBody).get("id")));
            Map<String,Object> request=queue.request(actor,taskId,map("version",first.get("version")));String attemptId=text(request,"attemptId");Map<String,Object> attempt=db.one("SELECT * FROM a_digital_worker_ai_attempts WHERE attempt_id=?",attemptId);attempt.put("input",db.one("SELECT * FROM a_digital_worker_tasks WHERE task_id=?",taskId));
            Map<String,Object> input=queue.modelInput(attempt);check("request_snapshots_processing_instructions",text(input,"instructions").contains("只列出有来源的事实")&&attempt.get("worker_settings_json")!=null);
            Map<String,Object> disable=payload(pauseWithTask);disable.put("aiEnabled",false);reject("cannot_disable_ai_with_active_request",409,()->settings.save(actor,"care",disable));
            Map<String,Object> changed=payload(pauseWithTask);changed.put("instructions","之后请求使用的新要求");Map<String,Object> next=settings.save(actor,"care",changed);check("active_request_keeps_previous_policy",text(queue.modelInput(attempt),"instructions").contains("只列出有来源的事实")&&!text(queue.modelInput(attempt),"instructions").contains("之后请求"));
            db.exec("UPDATE a_digital_worker_ai_attempts SET request_state='failed',active_task_id=NULL WHERE attempt_id=?",attemptId);
            Map<String,Object> off=payload(next);off.put("aiEnabled",false);settings.save(actor,"care",off);reject("disabled_ai_request_rejected",409,()->queue.request(actor,taskId,map("version",tasks.detail(actor,taskId).get("version"))));
            check("configuration_changes_audited",db.count("SELECT COUNT(*) FROM a_digital_worker_settings_events WHERE scope_code=? AND data_mode='synthetic' AND worker_id='care'",actor.scope)==5);
            check("no_secret_or_permission_override_saved",!ehr.encode(settings.get(actor,"care")).contains("apiKey")&&Boolean.TRUE.equals(settings.all(actor).get("reviewRequired")));
            return null;
        }finally{tx.setRollbackOnly();}});
        check("settings_fixtures_rolled_back",db.count("SELECT COUNT(*) FROM a_digital_worker_settings WHERE scope_code=?",actor.scope)==0&&db.count("SELECT COUNT(*) FROM a_digital_worker_tasks WHERE scope_code=?",actor.scope)==0);System.out.println("PASS Digital worker settings checks: "+count+"; all writes rolled back; no external calls");
    }
}
