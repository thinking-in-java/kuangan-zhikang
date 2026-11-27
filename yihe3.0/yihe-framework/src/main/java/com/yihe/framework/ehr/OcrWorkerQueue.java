package com.yihe.framework.ehr;

import java.util.*;
import java.time.*;
import java.nio.file.*;
import java.security.MessageDigest;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** A-side durable scheduling and fenced receipt adapter. Contains no OCR engine. */
@Service
public class OcrWorkerQueue {
    @org.springframework.beans.factory.annotation.Autowired(required=false) ModelRecognitionSettings recognition;
    private final EhrRepository db;private final EhrService ehr;private final EhrOcrService ocr;
    @Value("${ehr.data-mode:real}") private String mode;
    @Value("${ehr.ocr.worker-token:${EHR_OCR_WORKER_TOKEN:}}") private String workerToken;
    @Value("${ehr.ocr.worker-token-file:${EHR_OCR_WORKER_TOKEN_FILE:}}") private String tokenFile;
    @Value("${ehr.candidate-only:false}") private boolean candidateOnly;
    @Value("${ehr.ocr.lease-seconds:600}") private int configuredLease;
    public OcrWorkerQueue(EhrRepository db,EhrService ehr,EhrOcrService ocr){this.db=db;this.ehr=ehr;this.ocr=ocr;}
    String secret(){if(!workerToken.trim().isEmpty())return workerToken.trim();try{String file=tokenFile==null||tokenFile.trim().isEmpty()?("synthetic".equals(mode)?"D:/update/.config/ocr-worker.synthetic.token":"D:/update/.config/ocr-worker.token"):tokenFile;Path p=Paths.get(file);if(Files.isRegularFile(p)&&Files.size(p)<=512)return new String(Files.readAllBytes(p),java.nio.charset.StandardCharsets.UTF_8).trim();}catch(Exception ignored){}return "";}
    boolean authorized(String h,String key){return key.length()>=32&&h!=null&&h.startsWith("Bearer ")&&h.length()<=600&&MessageDigest.isEqual(bytes(key),bytes(h.substring(7)));}
    public void authorize(String h){String key=secret();require(key.length()>=32,503,"OCR工作服务密钥尚未配置");require(authorized(h,key)||(recognition!=null&&recognition.isModel()&&authorized(h,recognition.token())),401,"工作服务身份校验失败");}
    public void authorizeModel(String h){require(recognition!=null&&recognition.isModel()&&authorized(h,recognition.token()),401,"模型适配服务身份校验失败");}
    int leaseSeconds(){return Math.max("synthetic".equals(mode)?5:30,Math.min(900,configuredLease));}
    String later(int seconds){return LocalDateTime.now(ZoneOffset.UTC).plusSeconds(seconds).toString().replace('T',' ');}
    String uuidKey(Map<String,Object> b){String s=text(b,"requestKey");require(s.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"),422,"requestKey须为UUID");return s;}
    String leaseToken(String attempt,String worker){try{Mac mac=Mac.getInstance("HmacSHA256");mac.init(new SecretKeySpec(bytes(secret()),"HmacSHA256"));return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(bytes(mode+"|"+attempt+"|"+worker)));}catch(Exception e){throw new IllegalStateException("Unable to create worker lease");}}
    Map<String,Object> task(String id,boolean lock){Map<String,Object> q=db.one("SELECT q.*,(q.lease_until IS NOT NULL AND q.lease_until<=UTC_TIMESTAMP(6)) lease_expired FROM a_ocr_dispatch_tasks q WHERE q.job_id=? AND q.data_mode=?"+(lock?" FOR UPDATE":""),id,mode);require(q!=null,404,"识别调度任务不存在");return q;}
    void lease(Map<String,Object> q,String token,boolean replay){require(token!=null&&token.length()<=128&&q.get("lease_token_hash")!=null&&MessageDigest.isEqual(digest(bytes(token)),(byte[])q.get("lease_token_hash")),401,"任务租约校验失败");boolean success="succeeded".equals(q.get("dispatch_state"));require(replay&&success||"running".equals(q.get("dispatch_state"))&&num(q.get("lease_expired"))==0,409,"任务租约已过期、已取消或已由其他服务接管");}
    boolean eligible(String id){return db.count("SELECT COUNT(*) FROM a_extraction_jobs j JOIN a_health_reports h ON h.report_id=j.report_id AND h.revision=j.revision JOIN a_report_series s ON s.report_id=h.report_id AND s.current_revision=h.revision LEFT JOIN a_health_records r ON r.record_id=h.record_id LEFT JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id WHERE j.job_id=? AND j.data_mode=? AND j.status='uploaded' AND p.deleted_at IS NULL AND (r.record_id IS NOT NULL OR (h.record_id IS NULL AND EXISTS(SELECT 1 FROM a_health_archive_audit_logs l WHERE l.report_id=h.report_id AND l.revision=h.revision AND l.scope_code=h.scope_code AND l.data_mode=h.data_mode AND l.action='report_intake_uploaded'))) AND NOT EXISTS(SELECT 1 FROM a_review_records v WHERE v.report_id=h.report_id AND v.revision=h.revision) AND j.job_id=(SELECT n.job_id FROM a_extraction_jobs n WHERE n.report_id=j.report_id AND n.revision=j.revision ORDER BY n.created_at DESC,n.job_id DESC LIMIT 1)",id,mode)==1;}
    void closeAttempt(Map<String,Object> q,String outcome,String code,String message){if(q.get("current_attempt_id")!=null)db.exec("UPDATE a_ocr_dispatch_attempts SET outcome=?,finished_at=UTC_TIMESTAMP(6),error_code=?,error_message=? WHERE attempt_id=? AND outcome='running'",outcome,code,message,q.get("current_attempt_id"));}
    void expire(Map<String,Object> q){closeAttempt(q,"expired","LEASE_EXPIRED","工作服务心跳超时");boolean exhausted=num(q.get("attempt_count"))>=num(q.get("max_attempts"));db.exec("UPDATE a_ocr_dispatch_tasks SET dispatch_state=?,lease_until=NULL,available_at=?,last_error_code='LEASE_EXPIRED',last_error_message='工作服务心跳超时',updated_at=UTC_TIMESTAMP(6) WHERE job_id=?",exhausted?"failed":"retry_wait",later(0),q.get("job_id"));}
    @Transactional(isolation=Isolation.READ_COMMITTED) public void reclaimExpired(){for(Map<String,Object> q:db.all("SELECT * FROM a_ocr_dispatch_tasks WHERE data_mode=? AND dispatch_state='running' AND lease_until<=UTC_TIMESTAMP(6) ORDER BY lease_until LIMIT 50 FOR UPDATE /*!80001 SKIP LOCKED */",mode))expire(q);}
    Map<String,Object> claimReply(Map<String,Object> q,String token){String id=str(q.get("job_id"));return map("task",map("jobId",id,"scope_code",q.get("scope_code"),"data_mode",q.get("data_mode"),"attemptId",q.get("current_attempt_id"),"leaseToken",token,"leaseExpiresAt",q.get("lease_until"),"leaseSeconds",leaseSeconds(),"heartbeatSeconds",Math.max(1,leaseSeconds()/3),"inputPath","/ehr/worker/tasks/"+id+"/input","progressPath","/ehr/worker/tasks/"+id+"/heartbeat","resultPath","/ehr/worker/tasks/"+id+"/result","failurePath","/ehr/worker/tasks/"+id+"/failure"));}
    @Transactional(isolation=Isolation.READ_COMMITTED) public Map<String,Object> claim(Map<String,Object> b){
        if(recognition!=null&&recognition.isModel())return map("task",null,"retryAfterSeconds",30,"paused",true,"provider","model");
        return claimPartition(b,false);
    }
    @Transactional(isolation=Isolation.READ_COMMITTED) public Map<String,Object> claimForModel(Map<String,Object> b){
        require(recognition!=null&&recognition.enabled(),503,"模型识别尚未启用");return claimPartition(b,true);
    }
    Map<String,Object> claimPartition(Map<String,Object> b,boolean model){
        String worker=text(b,"workerId"),key=uuidKey(b);require(worker.matches("[A-Za-z0-9_.-]{1,80}"),422,"workerId应为1至80位字母数字、点、横线或下划线");
        Map<String,Object> old=db.one("SELECT attempt_id,job_id FROM a_ocr_dispatch_attempts WHERE data_mode=? AND worker_id=? AND claim_request_key=?",mode,worker,key);
        if(old!=null){Map<String,Object> q=task(str(old.get("job_id")),true);require(old.get("attempt_id").equals(q.get("current_attempt_id"))&&"running".equals(q.get("dispatch_state"))&&num(q.get("lease_expired"))==0,409,"领取请求已处理且原租约已结束，请使用新的requestKey");return claimReply(q,leaseToken(str(old.get("attempt_id")),worker));}
        // Find candidates without locking a sorted range, then lock one primary key.
        // An ORDER BY/LIMIT locking scan can lock other eligible rows before sorting.
        String partition=model?" AND job_id IN (SELECT job_id FROM a_extraction_jobs WHERE pipeline_version='model-vision-v1')":" AND job_id IN (SELECT job_id FROM a_extraction_jobs WHERE pipeline_version<>'model-vision-v1')";
        for(Map<String,Object> candidate:db.all("SELECT job_id FROM a_ocr_dispatch_tasks WHERE data_mode=?"+partition+" AND ((dispatch_state IN ('queued','retry_wait') AND available_at<=UTC_TIMESTAMP(6)) OR (dispatch_state='running' AND lease_until<=UTC_TIMESTAMP(6))) ORDER BY available_at,created_at,job_id LIMIT 100",mode)){
            Map<String,Object> q=db.one("SELECT * FROM a_ocr_dispatch_tasks WHERE job_id=? AND data_mode=? AND ((dispatch_state IN ('queued','retry_wait') AND available_at<=UTC_TIMESTAMP(6)) OR (dispatch_state='running' AND lease_until<=UTC_TIMESTAMP(6))) FOR UPDATE /*!80001 SKIP LOCKED */",candidate.get("job_id"),mode);
            if(q==null)continue;String id=str(q.get("job_id"));
            if("running".equals(q.get("dispatch_state"))){expire(q);q=task(id,true);if("failed".equals(q.get("dispatch_state")))continue;}
            if(!eligible(id)){closeAttempt(q,"cancelled","SUPERSEDED","报告已修改、停用或有更新任务");db.exec("UPDATE a_ocr_dispatch_tasks SET dispatch_state='cancelled',lease_until=NULL,last_error_code='SUPERSEDED',last_error_message='报告已修改、停用或有更新任务',updated_at=UTC_TIMESTAMP(6) WHERE job_id=?",id);continue;}
            String attempt=id(),raw=leaseToken(attempt,worker);int number=num(q.get("attempt_count"))+1;
            db.exec("UPDATE a_ocr_dispatch_tasks SET dispatch_state='running',attempt_count=?,current_attempt_id=?,worker_id=?,lease_token_hash=?,lease_until=?,progress_stage='queued',progress_percent=0,last_error_code=NULL,last_error_message=NULL,updated_at=UTC_TIMESTAMP(6) WHERE job_id=?",number,attempt,worker,digest(bytes(raw)),later(leaseSeconds()),id);
            db.insert("a_ocr_dispatch_attempts",map("attempt_id",attempt,"job_id",id,"data_mode",mode,"attempt_number",number,"claim_request_key",key,"worker_id",worker,"started_at",now(),"heartbeat_at",now()));return claimReply(task(id,false),raw);
        }
        return map("task",null,"retryAfterSeconds",3);
    }
    @Transactional public Map<String,Object> heartbeat(String id,String token,Map<String,Object> b){
        Map<String,Object> q=task(id,true);lease(q,token,false);String stage=text(b,"stage");List<String> stages=Arrays.asList("queued","pdf_parse","ocr","field_extraction","item_matching","validation");int percent=num(b.get("percent"));require(stages.contains(stage)&&percent>=0&&percent<=95,422,"进度stage无效或percent不在0至95之间");require(stages.indexOf(stage)>=stages.indexOf(str(q.get("progress_stage")))&&percent>=num(q.get("progress_percent")),409,"同次尝试的进度不能倒退");
        db.exec("UPDATE a_ocr_dispatch_tasks SET progress_stage=?,progress_percent=?,lease_until=?,updated_at=UTC_TIMESTAMP(6) WHERE job_id=?",stage,percent,later(leaseSeconds()),id);db.exec("UPDATE a_ocr_dispatch_attempts SET progress_stage=?,progress_percent=?,heartbeat_at=UTC_TIMESTAMP(6) WHERE attempt_id=?",stage,percent,q.get("current_attempt_id"));return publicTask(task(id,false));
    }
    public Map<String,Object> input(String id,String token){Map<String,Object> q=task(id,false);lease(q,token,false);Map<String,Object> in=ocr.input(id);@SuppressWarnings("unchecked")List<Map<String,Object>> files=(List<Map<String,Object>>)in.get("files");for(Map<String,Object> f:files)f.put("downloadPath","/ehr/worker/tasks/"+id+"/files/"+f.get("id"));in.put("resultPath","/ehr/worker/tasks/"+id+"/result");in.remove("callbackPath");return in;}
    public Map<String,Object> environment(){return map("data_mode",mode,"queuePartition",mode,"candidateOnly",candidateOnly,"contract_version","2.0.0","dictionary_version","2.0.0","leaseSeconds",leaseSeconds(),"heartbeatSeconds",Math.max(1,leaseSeconds()/3));}
    @Transactional(readOnly=true) public Map<String,Object> receipt(String id,String token){
        Map<String,Object> q=task(id,false);lease(q,token,true);Map<String,Object> j=ocr.job(id,false);
        Map<String,Object> receipt=db.one("SELECT received_at,HEX(payload_sha256) payloadSha256 FROM a_ehr_ocr_receipts WHERE job_id=?",id);
        Map<String,Object> h=db.one("SELECT review_status,field_values FROM a_health_reports WHERE report_id=? AND revision=?",j.get("report_id"),j.get("revision"));
        String rid=str(j.get("report_id"));int rev=num(j.get("revision"));Map<String,Object> counts=new LinkedHashMap<>();
        counts.put("fieldValues",h==null?0:((List<?>)ehr.decode(h.get("field_values"))).size());
        for(String name:new String[]{"health_items","report_evidence","health_conclusions","health_advice"})counts.put(name,db.count("SELECT COUNT(*) FROM a_"+name+" WHERE report_id=? AND revision=?",rid,rev));
        return map("jobId",id,"reportId",rid,"revision",rev,"scope_code",j.get("scope_code"),"data_mode",mode,"state",q.get("dispatch_state"),"accepted",receipt!=null,"receipt",receipt,"reviewStatus",h==null?null:h.get("review_status"),"published",db.count("SELECT COUNT(*) FROM a_report_publications WHERE report_id=? AND revision=?",rid,rev)>0,"counts",counts);
    }
    public Path file(String id,String token,String file){Map<String,Object> q=task(id,false);lease(q,token,false);Map<String,Object> j=ocr.job(id,false);require(db.count("SELECT COUNT(*) FROM a_report_revision_sources WHERE report_id=? AND revision=? AND source_file_id=?",j.get("report_id"),j.get("revision"),file)==1,404,"原件不属于该任务");return ehr.download(ocr.actor(j),file);}
    Map<String,Object> lockReport(String id){Map<String,Object> j=ocr.job(id,false);ehr.rawReport(ocr.actor(j),str(j.get("report_id")),num(j.get("revision")),true);return j;}
    void success(String id,Map<String,Object> q){db.exec("UPDATE a_ocr_dispatch_tasks SET dispatch_state='succeeded',progress_stage='finished',progress_percent=100,lease_until=NULL,last_error_code=NULL,last_error_message=NULL,updated_at=UTC_TIMESTAMP(6) WHERE job_id=?",id);if(q.get("current_attempt_id")!=null)db.exec("UPDATE a_ocr_dispatch_attempts SET outcome='succeeded',progress_stage='finished',progress_percent=100,finished_at=UTC_TIMESTAMP(6) WHERE attempt_id=? AND outcome='running'",q.get("current_attempt_id"));}
    @Transactional public Object complete(String id,String token,Map<String,Object> b){Map<String,Object> j=lockReport(id);Map<String,Object> q=task(id,true);lease(q,token,true);ocr.validateContext(j,b,true);ocr.result(id,b);if(!"succeeded".equals(q.get("dispatch_state")))success(id,q);return ehr.report(ocr.actor(j),str(j.get("report_id")),num(j.get("revision")),false);}
    @Transactional public Object legacyComplete(String id,Map<String,Object> b){lockReport(id);Map<String,Object> q=db.one("SELECT * FROM a_ocr_dispatch_tasks WHERE job_id=? AND data_mode=? FOR UPDATE",id,mode);require(q==null||Arrays.asList("queued","retry_wait","succeeded").contains(str(q.get("dispatch_state"))),409,"任务已由工作服务领取或已取消，请使用有效租约回传");Object r=ocr.result(id,b);if(q!=null)success(id,q);return r;}
    @Transactional public Map<String,Object> failure(String id,String token,Map<String,Object> b){
        Map<String,Object> q=task(id,true);String code=text(b,"errorCode"),message=ehr.raw(b,"message",600);require(code.matches("[A-Z0-9_]{1,64}"),422,"errorCode应为1至64位大写字母数字或下划线");
        if(Arrays.asList("retry_wait","failed").contains(str(q.get("dispatch_state")))&&q.get("lease_token_hash")!=null&&token!=null&&MessageDigest.isEqual(digest(bytes(token)),(byte[])q.get("lease_token_hash"))){Map<String,Object> a=db.one("SELECT error_code,error_message,outcome FROM a_ocr_dispatch_attempts WHERE attempt_id=?",q.get("current_attempt_id"));require(a!=null&&"failed".equals(a.get("outcome"))&&code.equals(a.get("error_code"))&&message.equals(a.get("error_message")),409,"失败回传已处理，不能更改内容");return publicTask(q);}
        lease(q,token,false);boolean retry=Boolean.TRUE.equals(b.get("retryable"))&&num(q.get("attempt_count"))<num(q.get("max_attempts"));closeAttempt(q,"failed",code,message);db.exec("UPDATE a_ocr_dispatch_tasks SET dispatch_state=?,lease_until=NULL,available_at=?,last_error_code=?,last_error_message=?,updated_at=UTC_TIMESTAMP(6) WHERE job_id=?",retry?"retry_wait":"failed",later(Math.min(60,5*num(q.get("attempt_count")))),code,message,id);return publicTask(task(id,false));
    }
    @Transactional public void rejectedResult(String id,String token,String validationMessage){
        Map<String,Object> q=task(id,true);lease(q,token,true);
        // A rejected conflicting replay must not change an already accepted result.
        if("succeeded".equals(q.get("dispatch_state")))return;
        String reason=str(validationMessage).replaceAll("[\\r\\n\\t]"," ").trim();
        if(reason.isEmpty())reason="请检查回传内容";
        String message="返回结果未通过A端数据契约校验："+reason;
        if(message.length()>600)message=message.substring(0,600);
        closeAttempt(q,"failed","INVALID_RESULT",message);
        db.exec("UPDATE a_ocr_dispatch_tasks SET dispatch_state='failed',lease_until=NULL,last_error_code='INVALID_RESULT',last_error_message=?,updated_at=UTC_TIMESTAMP(6) WHERE job_id=?",message,id);
        org.slf4j.LoggerFactory.getLogger(OcrWorkerQueue.class).warn("OCR result rejected, jobId={}, reason={}",id,reason);
    }
    Map<String,Object> publicTask(Map<String,Object> q){return map("jobId",q.get("job_id"),"state",q.get("dispatch_state"),"stage",q.get("progress_stage"),"percent",q.get("progress_percent"),"attemptCount",q.get("attempt_count"),"maxAttempts",q.get("max_attempts"),"availableAt",q.get("available_at"),"leaseExpiresAt",q.get("lease_until"),"leaseExpired",num(q.get("lease_expired"))==1,"errorCode",q.get("last_error_code"),"errorMessage",q.get("last_error_message"),"createdAt",q.get("created_at"),"updatedAt",q.get("updated_at"));}
    public Map<String,Object> detail(EhrActor a,String id){Map<String,Object> j=ocr.job(id,false);ehr.rawReport(a,str(j.get("report_id")),num(j.get("revision")),false);Map<String,Object> r=publicTask(task(id,false));r.putAll(map("reportId",j.get("report_id"),"revision",j.get("revision"),"businessStage",j.get("status"),"attempts",db.all("SELECT attempt_number attemptNumber,worker_id workerId,outcome,progress_stage stage,progress_percent percent,started_at startedAt,heartbeat_at heartbeatAt,finished_at finishedAt,error_code errorCode,error_message errorMessage FROM a_ocr_dispatch_attempts WHERE job_id=? ORDER BY attempt_number DESC",id)));return r;}
    public Map<String,Object> summary(EhrActor actor) {
        String sql = "SELECT COUNT(*) total, " +
            "COALESCE(SUM(dispatch_state='succeeded'),0) succeeded, " +
            "COALESCE(SUM(dispatch_state='failed'),0) failed, " +
            "COALESCE(SUM(dispatch_state IN ('queued','retry_wait')),0) pending, " +
            "COALESCE(SUM(dispatch_state='running'),0) running, " +
            "COALESCE(SUM(dispatch_state='succeeded' AND " +
            "updated_at>=CONVERT_TZ(DATE(CONVERT_TZ(UTC_TIMESTAMP(),'+00:00','+08:00')),'+08:00','+00:00')),0) todayCompleted " +
            "FROM a_ocr_dispatch_tasks WHERE scope_code=? AND data_mode=?";
        return db.one(sql, actor.scope, actor.mode);
    }

    public Map<String,Object> list(EhrActor a,Map<String,String> filters){String state=filters.get("state"),where=" WHERE q.scope_code=? AND q.data_mode=? AND EXISTS(SELECT 1 FROM a_extraction_jobs x JOIN a_health_reports h ON h.report_id=x.report_id AND h.revision=x.revision LEFT JOIN a_health_records r ON r.record_id=h.record_id LEFT JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id WHERE x.job_id=q.job_id AND p.deleted_at IS NULL AND (r.record_id IS NOT NULL OR (h.record_id IS NULL AND EXISTS(SELECT 1 FROM a_report_series s JOIN a_health_archive_audit_logs l ON l.report_id=s.report_id AND l.revision=s.current_revision WHERE s.report_id=h.report_id AND s.current_revision=h.revision AND l.scope_code=h.scope_code AND l.data_mode=h.data_mode AND l.action='report_intake_uploaded'))))";List<Object> args=new ArrayList<>(Arrays.asList(a.scope,a.mode));if("active".equals(state))where+=" AND q.dispatch_state IN ('queued','running','retry_wait')";else if("waiting".equals(state))where+=" AND q.dispatch_state IN ('queued','retry_wait')";else if("finished".equals(state))where+=" AND q.dispatch_state IN ('succeeded','failed','cancelled')";else if(state!=null&&!state.isEmpty()&&!state.equals("all")){require(Arrays.asList("queued","running","retry_wait","succeeded","failed","cancelled").contains(state),422,"任务状态无效");where+=" AND q.dispatch_state=?";args.add(state);}int page=bounded(filters.get("page"),1,1,100000),limit=bounded(filters.get("limit"),10,1,100);long total=db.count("SELECT COUNT(*) FROM a_ocr_dispatch_tasks q"+where,args.toArray());args.add(limit);args.add((page-1)*limit);List<Object> items=new ArrayList<>();for(Map<String,Object> q:db.all("SELECT q.*,j.report_id,j.revision,p.employee_name,p.employee_no,h.record_id FROM a_ocr_dispatch_tasks q JOIN a_extraction_jobs j ON j.job_id=q.job_id JOIN a_health_reports h ON h.report_id=j.report_id AND h.revision=j.revision LEFT JOIN a_health_records r ON r.record_id=h.record_id LEFT JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id"+where+" ORDER BY q.created_at DESC,q.job_id DESC LIMIT ? OFFSET ?",args.toArray())){Map<String,Object> r=publicTask(q);r.putAll(map("reportId",q.get("report_id"),"revision",q.get("revision"),"recordId",q.get("record_id"),"employeeName",q.get("employee_name"),"employeeNo",q.get("employee_no")));items.add(r);}return map("items",items,"total",total,"page",page,"limit",limit);}
    @Transactional public Map<String,Object> control(EhrActor a,String id,Map<String,Object> b,boolean retry){require(!a.mobile,403,"仅后台可调度识别任务");Map<String,Object> j=ocr.job(id,false);ehr.rawReport(a,str(j.get("report_id")),num(j.get("revision")),true);Map<String,Object> q=task(id,true);String key=uuidKey(b),note=ehr.raw(b,"note",600).trim();require(!note.isEmpty(),422,"请填写操作说明");if(retry){if(key.equals(q.get("last_retry_key")))return detail(a,id);require("failed".equals(q.get("dispatch_state"))&&eligible(id),409,"只有当前未修改、未审核且失败的任务可重试");if(recognition!=null&&recognition.isModel())db.exec("UPDATE a_extraction_jobs SET pipeline_version='model-vision-v1' WHERE job_id=?",id);db.exec("UPDATE a_ocr_dispatch_tasks SET dispatch_state='queued',max_attempts=attempt_count+3,available_at=UTC_TIMESTAMP(6),current_attempt_id=NULL,lease_token_hash=NULL,lease_until=NULL,worker_id=NULL,progress_stage='queued',progress_percent=0,last_retry_key=?,updated_at=UTC_TIMESTAMP(6) WHERE job_id=?",key,id);}else{require(Arrays.asList("queued","running","retry_wait").contains(str(q.get("dispatch_state"))),409,"任务已结束，不能取消");closeAttempt(q,"cancelled","ADMIN_CANCELLED",note);db.exec("UPDATE a_ocr_dispatch_tasks SET dispatch_state='cancelled',lease_until=NULL,last_error_code='ADMIN_CANCELLED',last_error_message=?,updated_at=UTC_TIMESTAMP(6) WHERE job_id=?",note,id);}ehr.audit(a,null,str(j.get("report_id")),num(j.get("revision")),retry?"ocr_task_retried":"ocr_task_cancelled",note);return detail(a,id);}
    public Object dictionary(){return map("version","2.0.0","standardFields",db.all("SELECT field_key,field_name,field_category,definition FROM a_standard_field_dictionary WHERE dictionary_version='2.0.0' ORDER BY source_row"),"medicalItems",db.all("SELECT item_code,standard_name,semantic_scope,status FROM a_medical_item_dictionary WHERE dictionary_version='2.0.0' AND status<>'retired' ORDER BY item_code"),"normalizationPolicy","原值、单位和参考范围保留原文；未批准的转换和临床分类不能自动确认");}
}
