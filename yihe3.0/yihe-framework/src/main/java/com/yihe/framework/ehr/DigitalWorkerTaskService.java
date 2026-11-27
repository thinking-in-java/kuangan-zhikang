package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import static com.yihe.framework.ehr.EhrAccess.*;

@Service
public class DigitalWorkerTaskService {
    private final EhrRepository db;
    private final DigitalWorkerArchiveContext archives;
    private final DigitalWorkerSettings settings;
    private static final List<String> WORKERS = Arrays.asList("archive","assess","follow","plan","care");
    private static final List<String> TYPES = Arrays.asList("visit_invitation","routine_followup","alert_handling",
        "abnormal_followup","plan_adjustment","report_interpretation","archive_completion","assessment","complaint_followup");
    private static final List<String> STATUSES = Arrays.asList("queued","in_progress","pending_review","completed","cancelled");
    private static final List<String> PRIORITIES = Arrays.asList("normal","high","urgent");
    private static final String FIELDS = "task_id id,worker_id worker,task_type type,title,member_code memberCode," +
        "record_id recordId,priority,instructions,task_status status,result_note resultNote,created_by createdBy," +
        "row_version version,created_at createdAt,updated_at updatedAt";

    public DigitalWorkerTaskService(EhrRepository db,DigitalWorkerArchiveContext archives) { this.db=db;this.archives=archives;this.settings=new DigitalWorkerSettings(db); }

    private void require(boolean valid,int code,String message) {
        if (!valid) throw new EhrException(code,message);
    }
    private String field(Map<String,Object> body,String key,int limit,boolean mandatory) {
        Object value=body.get(key);
        require(value==null||value instanceof String,422,"字段格式无效："+key);
        String result=value==null?"":((String)value).trim();
        require(result.indexOf(0)<0&&result.length()<=limit&&(!mandatory||!result.isEmpty()),422,"请检查字段："+key);
        return result;
    }
    private String uuid(String value) {
        require(value.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"),422,"请求编号无效");
        return value.toLowerCase(Locale.ROOT);
    }
    private int number(String value,int fallback,int max) {
        if (value==null||value.isEmpty()) return fallback;
        try {
            int result=Integer.parseInt(value);
            require(result>0&&result<=max,422,"分页参数无效");
            return result;
        } catch (NumberFormatException error) { throw new EhrException(422,"分页参数无效"); }
    }
    private void doctor(EhrActor actor) {
        require(actor.doctor&&!actor.mobile,403,"派活和处理任务需要医生权限");
    }
    public Map<String,Object> list(EhrActor actor,Map<String,String> filters) {
        String where=" WHERE scope_code=? AND data_mode=?";
        List<Object> args=new ArrayList<>(Arrays.asList(actor.scope,actor.mode));
        String worker=filters.get("worker"),status=filters.get("status"),priority=filters.get("priority");
        if (worker!=null&&!worker.isEmpty()) {
            require(WORKERS.contains(worker),422,"数字员工无效");
            where+=" AND worker_id=?"; args.add(worker);
        }
        if ("finished".equals(status)) where+=" AND task_status IN ('completed','cancelled')";
        else if ("working".equals(status)) where+=" AND task_status IN ('in_progress','pending_review')";
        else if (status!=null&&!status.isEmpty()) {
            require(STATUSES.contains(status),422,"任务状态无效");
            where+=" AND task_status=?"; args.add(status);
        }
        if (priority!=null&&!priority.isEmpty()) {
            require(PRIORITIES.contains(priority),422,"优先级无效");
            where+=" AND priority=?"; args.add(priority);
        }
        int page=number(filters.get("page"),1,100000),limit=number(filters.get("limit"),10,100);
        long total=db.count("SELECT COUNT(*) FROM a_digital_worker_tasks"+where,args.toArray());
        args.add(limit); args.add((page-1)*limit);
        List<Map<String,Object>> items=db.all("SELECT "+FIELDS+" FROM a_digital_worker_tasks"+where+
            " ORDER BY FIELD(priority,'urgent','high','normal'),created_at DESC,task_id DESC LIMIT ? OFFSET ?",args.toArray());
        String summaryWhere=" WHERE scope_code=? AND data_mode=?";
        List<Object> summaryArgs=new ArrayList<>(Arrays.asList(actor.scope,actor.mode));
        if (worker!=null&&!worker.isEmpty()) { summaryWhere+=" AND worker_id=?"; summaryArgs.add(worker); }
        Map<String,Object> summary=db.one("SELECT COALESCE(SUM(task_status='queued'),0) pending,"+
            "COALESCE(SUM(task_status='pending_review'),0) review,"+
            "COALESCE(SUM(task_status='completed' AND updated_at>=CONVERT_TZ(DATE(CONVERT_TZ(UTC_TIMESTAMP(),'+00:00','+08:00')),'+08:00','+00:00')),0) todayCompleted"+
            " FROM a_digital_worker_tasks"+summaryWhere,summaryArgs.toArray());
        return map("items",items,"total",total,"page",page,"limit",limit,"canManage",actor.doctor,"summary",summary);
    }
    public Map<String,Object> detail(EhrActor actor,String id) {
        return detail(actor,id,false);
    }
    private Map<String,Object> detail(EhrActor actor,String id,boolean locked) {
        String lock=locked ? " FOR UPDATE" : "";
        Map<String,Object> row=db.one("SELECT "+FIELDS+" FROM a_digital_worker_tasks WHERE task_id=? AND scope_code=? AND data_mode=?"+lock,
            uuid(id),actor.scope,actor.mode);
        require(row!=null,404,"任务不存在或无权访问");
        row.put("events",db.all("SELECT action,actor,from_status fromStatus,to_status toStatus,note,created_at time "+
            "FROM a_digital_worker_task_events WHERE task_id=? ORDER BY created_at,event_id"+lock,id));
        row.put("ai",db.one("SELECT attempt_id id,request_state state,model_name model,output_text output,error_code errorCode,"+
            "archive_context_json archiveContext,verification_json verification,result_json taskResult,worker_settings_json workerSettings,created_at createdAt,updated_at updatedAt FROM a_digital_worker_ai_attempts WHERE task_id=? ORDER BY created_at DESC,attempt_id DESC LIMIT 1"+lock,id));
        Map<String,Object> contextRow=db.one("SELECT task_context_json,context_saved_at,context_saved_by FROM a_digital_worker_tasks WHERE task_id=?",id);
        Map<String,Object> taskContext=contextRow.get("task_context_json")==null?new TreeMap<>():(Map<String,Object>)archives.decode(contextRow.get("task_context_json"));
        row.put("taskContext",taskContext);row.put("contextSavedAt",contextRow.get("context_saved_at"));row.put("contextSavedBy",contextRow.get("context_saved_by"));row.put("profile",DigitalWorkerTaskProfiles.get(String.valueOf(row.get("type"))));
        Map<String,Object> ai=(Map<String,Object>)row.get("ai");
        if(ai!=null&&ai.get("workerSettings")!=null)ai.put("workerSettings",archives.decode(ai.get("workerSettings")));
        if (ai!=null&&ai.get("archiveContext")!=null) ai.put("archiveContext",archives.decode(ai.get("archiveContext")));
        if (ai!=null&&ai.get("verification")!=null) ai.put("verification",archives.decode(ai.get("verification")));
        if (ai!=null&&ai.get("taskResult")!=null) ai.put("taskResult",archives.decode(ai.get("taskResult")));
        if (ai!=null) {
            Object snapshot=ai.get("archiveContext");
            String snapshotId=snapshot instanceof Map?text((Map<String,Object>)snapshot,"recordId"):"";
            ai.put("outdated",!Objects.equals(text(row,"recordId"),snapshotId));
            if(ai.get("taskResult") instanceof Map){Map<String,Object> result=(Map<String,Object>)ai.get("taskResult");if(!Objects.equals(result.get("contextDigest"),EhrService.hex(digest(bytes(archives.encode(new TreeMap<>(taskContext)))))))ai.put("outdated",true);}
        }
        try { row.put("archive",archives.read(actor,(String)row.get("recordId"))); }
        catch (EhrException error) {row.put("archive",map("linked",false,"unavailable",true));}
        row.put("workerConfig",settings.get(actor,String.valueOf(row.get("worker"))));
        Map<String,Object> automatic=db.one("SELECT report_id reportId,revision,workflow_state state,match_reason reason FROM a_archive_automation WHERE task_id=? AND scope_code=? AND data_mode=?",id,actor.scope,actor.mode);row.put("automation",automatic);
        row.put("canManage",actor.doctor&&automatic==null);
        return row;
    }
    private void event(String id,EhrActor actor,String action,String from,String to,String note) {
        db.insert("a_digital_worker_task_events",map("event_id",UUID.randomUUID().toString(),"task_id",id,
            "actor",actor.user,"action",action,"from_status",from,"to_status",to,"note",note,"created_at",now()));
    }
    @Transactional(isolation=Isolation.READ_COMMITTED)
    public Map<String,Object> create(EhrActor actor,Map<String,Object> body) {
        doctor(actor);
        String worker=field(body,"worker",16,true),type=field(body,"type",32,true);
        String title=field(body,"title",120,true),member=field(body,"memberCode",100,false);
        String priority=field(body,"priority",16,true),instructions=field(body,"instructions",2000,false);
        String key=uuid(field(body,"requestKey",36,true));
        Map<String,Object> taskContext=DigitalWorkerTaskProfiles.context(type,body.get("taskContext"));String contextJson=archives.encode(taskContext);
        require(WORKERS.contains(worker)&&TYPES.contains(type)&&PRIORITIES.contains(priority),422,"员工、任务类型或优先级无效");
        String recordId=field(body,"recordId",36,false);
        if (!recordId.isEmpty()) {
            Map<String,Object> record=archives.read(actor,recordId=uuid(recordId));
            member=String.valueOf(record.get("code"));
        } else if (!member.isEmpty()) {
            List<Map<String,Object>> records=db.all("SELECT r.record_id FROM a_health_records r JOIN a_employee_roster_profiles p "+
                "ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode "+
                "WHERE r.scope_code=? AND r.data_mode=? AND (p.employee_no=? OR p.legacy_user_code=?) AND p.deleted_at IS NULL",
                actor.scope,actor.mode,member,member);
            require(records.size()==1,422,"该会员编码未匹配到唯一的授权档案，请核对编码");
            recordId=String.valueOf(records.get(0).get("record_id"));
        } else recordId=null;
        String payload=worker+"\0"+type+"\0"+title+"\0"+member+"\0"+priority+"\0"+instructions+"\0"+recordId+"\0"+contextJson;
        byte[] hash=digest(bytes(payload));
        Map<String,Object> config=settings.lock(actor,worker);
        Map<String,Object> prior=db.one("SELECT task_id,payload_sha256 FROM a_digital_worker_tasks WHERE scope_code=? AND data_mode=? AND created_by=? AND request_key=? FOR UPDATE",actor.scope,actor.mode,actor.user,key);
        if(prior!=null){require(Arrays.equals(hash,(byte[])prior.get("payload_sha256")),409,"该请求编号已用于其他任务");return detail(actor,String.valueOf(prior.get("task_id")),true);}
        settings.admit(actor,worker,type,config);
        String id=UUID.randomUUID().toString();
        db.exec("INSERT INTO a_digital_worker_tasks "+
            "(task_id,scope_code,data_mode,worker_id,task_type,title,member_code,record_id,priority,instructions,"+
            "task_context_json,context_saved_at,context_saved_by,created_by,request_key,payload_sha256,created_at,updated_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,UTC_TIMESTAMP(6),UTC_TIMESTAMP(6)) "+
            "ON DUPLICATE KEY UPDATE task_id=task_id",
            id,actor.scope,actor.mode,worker,type,title,member,recordId,priority,instructions,contextJson,taskContext.isEmpty()?null:now(),taskContext.isEmpty()?null:actor.user,actor.user,key,hash);
        Map<String,Object> saved=db.one("SELECT task_id,payload_sha256 FROM a_digital_worker_tasks "+
            "WHERE scope_code=? AND data_mode=? AND created_by=? AND request_key=? FOR UPDATE",
            actor.scope,actor.mode,actor.user,key);
        require(Arrays.equals(hash,(byte[])saved.get("payload_sha256")),409,"该请求编号已用于其他任务，请重新打开派活窗口");
        String savedId=String.valueOf(saved.get("task_id"));
        if (id.equals(savedId)) event(id,actor,"created",null,"queued","任务已派发，等待人工处理");
        return detail(actor,savedId,true);
    }
    @Transactional
    public Map<String,Object> saveContext(EhrActor actor,String id,Map<String,Object> body){
        doctor(actor);id=uuid(id);Object version=body.get("version");
        Map<String,Object> task=db.one("SELECT task_type,task_status,row_version FROM a_digital_worker_tasks WHERE task_id=? AND scope_code=? AND data_mode=? FOR UPDATE",id,actor.scope,actor.mode);
        require(task!=null,404,"任务不存在或无权访问");require(version instanceof Number&&((Number)version).doubleValue()==((Number)task.get("row_version")).intValue(),409,"任务已更新，请重新打开详情");
        String from=String.valueOf(task.get("task_status"));require(!Arrays.asList("completed","cancelled").contains(from),409,"已结束任务不能修改事实记录，请创建新任务");
        require(db.count("SELECT COUNT(*) FROM a_digital_worker_ai_attempts WHERE active_task_id=?",id)==0,409,"任务正在处理，请等待结果后补充资料");
        Map<String,Object> context=DigitalWorkerTaskProfiles.context(String.valueOf(task.get("task_type")),body.get("taskContext"));String to="pending_review".equals(from)?"queued":from;
        db.exec("UPDATE a_digital_worker_tasks SET task_context_json=?,context_saved_at=UTC_TIMESTAMP(6),context_saved_by=?,task_status=?,row_version=row_version+1,updated_at=UTC_TIMESTAMP(6) WHERE task_id=?",archives.encode(context),actor.user,to,id);
        event(id,actor,"context_saved",from,to,"已保存本次任务事实记录；需重新生成结果后审核");return detail(actor,id,true);
    }
    @Transactional
    public Map<String,Object> linkArchive(EhrActor actor,String id,Map<String,Object> body) {
        doctor(actor);id=uuid(id);
        Object expected=body.get("version");
        require(expected instanceof Number&&((Number)expected).doubleValue()==((Number)expected).intValue(),422,"任务版本无效");
        Map<String,Object> task=db.one("SELECT task_status,row_version,record_id FROM a_digital_worker_tasks "+
            "WHERE task_id=? AND scope_code=? AND data_mode=? FOR UPDATE",id,actor.scope,actor.mode);
        require(task!=null,404,"任务不存在或无权访问");
        require(((Number)task.get("row_version")).intValue()==((Number)expected).intValue(),409,"任务已更新，请重新打开详情");
        String from=String.valueOf(task.get("task_status"));
        require(!Arrays.asList("completed","cancelled").contains(from),409,"已结束的任务不能更改关联档案");
        require(db.count("SELECT COUNT(*) FROM a_digital_worker_ai_attempts WHERE active_task_id=?",id)==0,409,"AI 正在处理，请等待结束后关联档案");
        String recordId=uuid(field(body,"recordId",36,true));
        Map<String,Object> archive=archives.read(actor,recordId);
        if (Objects.equals(recordId,task.get("record_id"))) return detail(actor,id,true);
        String to="pending_review".equals(from)?"queued":from;
        db.exec("UPDATE a_digital_worker_tasks SET record_id=?,member_code=?,task_status=?,result_note=?,row_version=row_version+1,updated_at=UTC_TIMESTAMP(6) WHERE task_id=?",
            recordId,archive.get("code"),to,"已关联职工档案；原 AI 草稿如未使用该档案，须重新生成并审核",id);
        event(id,actor,"archive_linked",from,to,"已关联职工工号 "+archive.get("code")+"；未修改健康档案");
        return detail(actor,id,true);
    }
    @Transactional
    public Map<String,Object> act(EhrActor actor,String id,Map<String,Object> body) {
        doctor(actor);
        require(db.count("SELECT COUNT(*) FROM a_archive_automation WHERE task_id=? AND scope_code=? AND data_mode=?",id,actor.scope,actor.mode)==0,409,"系统整理任务由档案小谷自动执行，请在报告中心处理身份异常、审核或归档");
        id=uuid(id);
        String action=field(body,"action",24,true),note=field(body,"note",2000,true);
        Object expected=body.get("version");
        require(expected instanceof Number&&((Number)expected).doubleValue()==((Number)expected).intValue(),422,"任务版本无效");
        Map<String,Object> row=db.one("SELECT task_status,row_version,record_id,task_type,task_context_json FROM a_digital_worker_tasks "+
            "WHERE task_id=? AND scope_code=? AND data_mode=? FOR UPDATE",id,actor.scope,actor.mode);
        require(row!=null,404,"任务不存在或无权访问");
        require(((Number)row.get("row_version")).intValue()==((Number)expected).intValue(),409,"任务已被更新，请重新打开详情");
        String from=String.valueOf(row.get("task_status")),to;
        if(Arrays.asList("submit","approve").contains(action))
            require(db.count("SELECT COUNT(*) FROM a_digital_worker_ai_attempts WHERE active_task_id=?",id)==0,409,"原件核对仍在进行，请等待结果后审核");
        if("approve".equals(action)) {
            Map<String,Object> latest=db.one("SELECT verification_json,result_json FROM a_digital_worker_ai_attempts WHERE task_id=? ORDER BY created_at DESC,attempt_id DESC LIMIT 1",id);
            if(latest!=null&&latest.get("result_json")!=null){
                Map<String,Object> result=(Map<String,Object>)archives.decode(latest.get("result_json"));require("ready_for_review".equals(result.get("state")),409,"工作资料不足或尚未完成，请补充实际记录并重新处理");
                require(Boolean.TRUE.equals(body.get("workflowReviewed")),422,"请确认已核验结果依据与实际执行状态");
                Map<String,Object> context=row.get("task_context_json")==null?new TreeMap<>():(Map<String,Object>)archives.decode(row.get("task_context_json"));
                require(Objects.equals(result.get("contextDigest"),EhrService.hex(digest(bytes(archives.encode(new TreeMap<>(context)))))),409,"本次事实记录已更新，请重新处理后审核");
                if(result.get("sourceReportDigest")!=null)require(Objects.equals(result.get("sourceReportDigest"),archives.reportDigest(actor,String.valueOf(row.get("record_id")),text(result,"sourceReportId"),((Number)result.get("sourceRevision")).intValue())),409,"报告已更新，请重新读取后审核");
            }
            if(latest!=null&&latest.get("verification_json")!=null) {
                Map<String,Object> verification=(Map<String,Object>)archives.decode(latest.get("verification_json"));
                require(!"blocked".equals(verification.get("state")),409,"原件核对尚未完成，请先恢复原件或识别服务并重新核对");
                require(Boolean.TRUE.equals(body.get("originalReviewed")),422,"请确认已按原件复核差异和待核实项");
                require(Objects.equals(verification.get("reportDigest"),archives.reportDigest(actor,String.valueOf(row.get("record_id")),text(verification,"reportId"),((Number)verification.get("revision")).intValue())),409,"报告已更新，请重新核对后再审核");
            }
        }
        if ("start".equals(action)&&"queued".equals(from)) to="in_progress";
        else if ("submit".equals(action)&&"in_progress".equals(from)) to="pending_review";
        else if ("approve".equals(action)&&"pending_review".equals(from)) to="completed";
        else if ("return".equals(action)&&"pending_review".equals(from)) to="in_progress";
        else if ("cancel".equals(action)&&!Arrays.asList("completed","cancelled").contains(from)) to="cancelled";
        else throw new EhrException(409,"当前任务状态不支持此操作");
        db.exec("UPDATE a_digital_worker_tasks SET task_status=?,result_note=?,row_version=row_version+1,updated_at=UTC_TIMESTAMP(6) "+
            "WHERE task_id=? AND scope_code=? AND data_mode=?",to,note,id,actor.scope,actor.mode);
        event(id,actor,action,from,to,note);
        return detail(actor,id,true);
    }
}
