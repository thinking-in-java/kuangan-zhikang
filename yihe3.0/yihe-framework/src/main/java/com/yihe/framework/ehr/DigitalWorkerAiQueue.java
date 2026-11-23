package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import static com.yihe.framework.ehr.EhrAccess.*;

@Service
public class DigitalWorkerAiQueue {
    private final EhrRepository db;
    private final DigitalWorkerModelClient client;
    private final DigitalWorkerArchiveContext archives;
    private final DigitalWorkerSettings settings;
    @org.springframework.beans.factory.annotation.Value("${ehr.data-mode:real}") private String mode;
    public DigitalWorkerAiQueue(EhrRepository db,DigitalWorkerModelClient client,DigitalWorkerArchiveContext archives) {this.db=db;this.client=client;this.archives=archives;this.settings=new DigitalWorkerSettings(db);}
    private void event(String task,String actor,String action,String from,String to,String note) {
        db.insert("a_digital_worker_task_events",map("event_id",UUID.randomUUID().toString(),"task_id",task,
            "actor",actor,"action",action,"from_status",from,"to_status",to,"note",note,"created_at",now()));
    }
    @Transactional(isolation=Isolation.READ_COMMITTED)
    public Map<String,Object> request(EhrActor actor,String id,Map<String,Object> body) {
        if (!actor.doctor||actor.mobile) throw new EhrException(403,"AI 处理需要医生权限");
        Map<String,Object> hint=db.one("SELECT worker_id FROM a_digital_worker_tasks WHERE task_id=? AND scope_code=? AND data_mode=?",id,actor.scope,actor.mode);
        if(hint==null)throw new EhrException(404,"任务不存在或无权访问");
        Map<String,Object> config=settings.lock(actor,text(hint,"worker_id"));settings.requireAi(config);
        Map<String,Object> task=db.one("SELECT * FROM a_digital_worker_tasks WHERE task_id=? AND scope_code=? AND data_mode=? FOR UPDATE",
            id,actor.scope,actor.mode);
        if (task==null) throw new EhrException(404,"任务不存在或无权访问");
        if (!Boolean.TRUE.equals(client.status().get("configured"))) throw new EhrException(503,"数字员工独立服务尚未配置，任务已保存，可稍后重试 AI 处理");
        Map<String,Object> active=db.one("SELECT attempt_id,request_state FROM a_digital_worker_ai_attempts WHERE active_task_id=?",id);
        if (active!=null) return map("attemptId",active.get("attempt_id"),"status",active.get("request_state"));
        Object version=body.get("version");
        if (!(version instanceof Number)||((Number)version).doubleValue()!=((Number)task.get("row_version")).intValue())
            throw new EhrException(409,"任务已更新，请重新打开详情");
        String before=String.valueOf(task.get("task_status"));
        if (!Arrays.asList("queued","in_progress","pending_review").contains(before)) throw new EhrException(409,"当前状态不允许请求处理");
        Map<String,Object> archive=archives.read(actor,(String)task.get("record_id"));
        int next=((Number)task.get("row_version")).intValue()+1;
        db.exec("UPDATE a_digital_worker_tasks SET task_status='in_progress',row_version=?,updated_at=UTC_TIMESTAMP(6) WHERE task_id=?",next,id);
        String attempt=UUID.randomUUID().toString();
        db.insert("a_digital_worker_ai_attempts",map("attempt_id",attempt,"task_id",id,"active_task_id",id,
            "requested_by",actor.user,"task_version",next,"request_state","queued","archive_context_json",archives.encode(archive),"worker_settings_json",archives.encode(config),"created_at",now(),"updated_at",now()));
        event(id,actor.user,"ai_requested",before,"in_progress","已请求数字员工独立处理服务，结果仍需审核");
        return map("attemptId",attempt,"status","queued");
    }
    @Transactional(isolation=Isolation.READ_COMMITTED)
    public Map<String,Object> claim() {
        Map<String,Object> row=db.one("SELECT a.* FROM a_digital_worker_ai_attempts a JOIN a_digital_worker_tasks t ON t.task_id=a.task_id WHERE a.request_state='queued' AND t.data_mode=? "+
            "ORDER BY a.created_at,a.attempt_id LIMIT 1 FOR UPDATE",mode);
        if (row==null) return null;
        db.exec("UPDATE a_digital_worker_ai_attempts SET request_state='running',lease_until=DATE_ADD(UTC_TIMESTAMP(6),INTERVAL 15 MINUTE),"+
            "updated_at=UTC_TIMESTAMP(6) WHERE attempt_id=?",row.get("attempt_id"));
        row.put("input",db.one("SELECT worker_id,task_type,title,instructions,task_status,row_version,record_id,scope_code,data_mode,task_context_json,context_saved_at,context_saved_by FROM a_digital_worker_tasks WHERE task_id=?",row.get("task_id")));
        return row;
    }
    private boolean authorized(String user,String scope) {
        return db.count("SELECT COUNT(*) FROM yh_user_reginfo WHERE user_code=? AND lock_flag=2 AND User_Type=4 AND CONCAT('org:',Org_Code)=?",user,scope)>0;
    }
    @SuppressWarnings("unchecked")
    public Map<String,Object> modelInput(Map<String,Object> attempt) {
        Map<String,Object> input=new LinkedHashMap<>((Map<String,Object>)attempt.get("input"));
        String scope=text(input,"scope_code");
        if (!authorized(text(attempt,"requested_by"),scope)) throw new EhrException(403,"MODEL_ACCESS_REVOKED");
        Object raw=attempt.get("archive_context_json");
        Map<String,Object> snapshot=raw==null?map("linked",false):(Map<String,Object>)archives.decode(raw);
        if (!text(input,"record_id").equals(text(snapshot,"recordId"))) throw new EhrException(409,"MODEL_ARCHIVE_CHANGED");
        if (!text(input,"record_id").isEmpty()) {
            try { archives.read(new EhrActor(text(attempt,"requested_by"),scope.substring(4),text(input,"data_mode"),true,false),text(input,"record_id")); }
            catch (EhrException error) {throw new EhrException(403,"MODEL_ARCHIVE_UNAVAILABLE");}
        }
        Object policy=attempt.get("worker_settings_json");
        Map<String,Object> workerPolicy=policy==null?null:(Map<String,Object>)archives.decode(policy);
        if(workerPolicy!=null&&!text(workerPolicy,"instructions").isEmpty())input.put("instructions",text(input,"instructions")+"\n岗位处理要求（仅辅助说明，不改变证据或审核规则）："+text(workerPolicy,"instructions"));
        input.put("worker_settings",workerPolicy);
        input.put("archive_context",archives.modelFacts(snapshot));
        input.put("archive_snapshot",snapshot);
        input.put("requested_by",attempt.get("requested_by"));
        input.put("task_id",attempt.get("task_id"));
        return input;
    }
    @Transactional
    public void finish(String attemptId,Map<String,Object> result,String failure) {
        Map<String,Object> hint=db.one("SELECT task_id FROM a_digital_worker_ai_attempts WHERE attempt_id=?",attemptId);
        if (hint==null) return;
        String id=String.valueOf(hint.get("task_id"));
        Map<String,Object> task=db.one("SELECT task_status,row_version,scope_code,record_id,data_mode FROM a_digital_worker_tasks WHERE task_id=? FOR UPDATE",id);
        Map<String,Object> attempt=db.one("SELECT * FROM a_digital_worker_ai_attempts WHERE attempt_id=? FOR UPDATE",attemptId);
        if (!"running".equals(attempt.get("request_state"))) return;
        boolean current=task!=null&&"in_progress".equals(task.get("task_status"))&&
            ((Number)task.get("row_version")).intValue()==((Number)attempt.get("task_version")).intValue();
        if (current&&!authorized(text(attempt,"requested_by"),text(task,"scope_code"))) {result=null;failure="MODEL_ACCESS_REVOKED";}
        if (current&&!text(task,"record_id").isEmpty()&&db.count("SELECT COUNT(*) FROM a_health_records r JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id "+
            "AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode WHERE r.record_id=? AND r.scope_code=? AND r.data_mode=? AND p.deleted_at IS NULL",
            task.get("record_id"),task.get("scope_code"),task.get("data_mode"))==0) {result=null;failure="MODEL_ARCHIVE_UNAVAILABLE";}
        String state=!current?"discarded":failure==null?"succeeded":"failed";
        db.exec("UPDATE a_digital_worker_ai_attempts SET request_state=?,active_task_id=NULL,lease_until=NULL,model_name=?,"+
            "output_text=?,usage_json=?,verification_json=?,result_json=?,error_code=?,updated_at=UTC_TIMESTAMP(6) WHERE attempt_id=?",state,
            result==null?null:result.get("model"),current&&result!=null?result.get("output"):null,
            result==null?null:result.get("usage"),current&&result!=null&&result.get("verification")!=null?archives.encode(result.get("verification")):null,current&&result!=null&&result.get("taskResult")!=null?archives.encode(result.get("taskResult")):null,failure,attemptId);
        String actor=String.valueOf(attempt.get("requested_by"));
        String from=String.valueOf(task.get("task_status"));
        if (!current) {
            event(id,actor,"ai_discarded",from,from,"任务状态或版本已变化，AI 结果未应用");
            return;
        }
        String to=failure==null?"pending_review":"queued";
        String note=failure==null?(result!=null&&(result.get("verification")!=null||result.get("taskResult")!=null)?String.valueOf(result.get("output")):"AI 已生成处理结果，等待医生审核"):"AI 处理失败，可重试（"+failure+"）";
        db.exec("UPDATE a_digital_worker_tasks SET task_status=?,result_note=?,row_version=row_version+1,updated_at=UTC_TIMESTAMP(6) WHERE task_id=?",
            to,note,id);
        event(id,actor,failure==null?"ai_generated":"ai_failed","in_progress",to,note);
    }
    public List<Map<String,Object>> expired() {
        return db.all("SELECT a.attempt_id FROM a_digital_worker_ai_attempts a JOIN a_digital_worker_tasks t ON t.task_id=a.task_id "+
            "WHERE a.request_state='running' AND a.lease_until<UTC_TIMESTAMP(6) AND t.data_mode=? LIMIT 20",mode);
    }
}
