package com.yihe.framework.ehr;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import org.springframework.dao.DuplicateKeyException;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Persists actual request lifecycle independently of the all-or-nothing roster transaction. */
@Service
public class EmployeeImportAttemptService {
    private final EhrRepository db;private final EhrService ehr;
    public EmployeeImportAttemptService(EhrRepository db,EhrService ehr){this.db=db;this.ehr=ehr;}
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    @SuppressWarnings("unchecked")
    public void begin(EhrActor a,Map<String,Object> body){
        require(!a.mobile,403,"个人端不能导入企业花名册");String request=text(body,"requestKey"),filename=text(body,"filename");Object rows=body.get("rows");
        require(request.matches("[A-Za-z0-9_-]{16,96}"),422,"导入请求标识无效");require(!filename.isEmpty()&&filename.length()<=200,422,"文件名称无效");require(rows instanceof List&&!((List<?>)rows).isEmpty()&&((List<?>)rows).size()<=5000,422,"每批导入1至5000条");
        String fingerprint=Base64.getEncoder().encodeToString(digest(bytes(ehr.encode(body))));
        Map<String,Object> data=map("filename",filename,"total",((List<?>)rows).size(),"status","processing","requestDigest",fingerprint);
        try{db.insert("a_health_archive_audit_logs",ehr.scoped(a,"audit_id",id(),"record_id",null,"report_id",null,"revision",null,"actor_ref",bytes(a.user),"action","employee_import_attempt","request_key",request,"reason","已接收职工导入请求","redacted_diff",ehr.encode(data),"occurred_at",now()));}
        catch(DuplicateKeyException duplicate){Map<String,Object> prior=db.one("SELECT redacted_diff FROM a_health_archive_audit_logs WHERE scope_code=? AND data_mode=? AND action='employee_import_attempt' AND request_key=? FOR UPDATE",a.scope,a.mode,request);require(prior!=null,409,"导入请求冲突");Map<String,Object> saved=(Map<String,Object>)ehr.decode(prior.get("redacted_diff"));require(fingerprint.equals(saved.get("requestDigest")),409,"同一导入请求不能提交不同内容");
            if(!"success".equals(saved.get("status")))db.exec("UPDATE a_health_archive_audit_logs SET redacted_diff=JSON_SET(redacted_diff,'$.status','processing','$.error','') WHERE scope_code=? AND data_mode=? AND action='employee_import_attempt' AND request_key=?",a.scope,a.mode,request);
        }
    }
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void finish(EhrActor a,String request,boolean success,String reason){
        String note=reason==null?"":reason.substring(0,Math.min(600,reason.length()));
        db.exec("UPDATE a_health_archive_audit_logs SET redacted_diff=JSON_SET(redacted_diff,'$.status',?,'$.error',?,'$.finishedAt',?),reason=? WHERE scope_code=? AND data_mode=? AND action='employee_import_attempt' AND request_key=? AND COALESCE(JSON_UNQUOTE(JSON_EXTRACT(redacted_diff,'$.status')),'')<>'success'",success?"success":"failed",success?"":note,now().replace(' ','T')+"Z",success?"导入事务已完成":note,a.scope,a.mode,request);
    }
}
