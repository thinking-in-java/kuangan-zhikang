package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Remove an unreviewed upload from the employee's business view; preserve evidence. */
@Service
public class ReportRemovalService {
    private final EhrRepository db;private final EhrService ehr;
    public ReportRemovalService(EhrRepository db,EhrService ehr){this.db=db;this.ehr=ehr;}
    @Transactional public Map<String,Object> remove(EhrActor a,String id,int revision,String etag,Map<String,Object> body){
        require(!a.mobile,403,"个人端不能移除后台报告");
        String key=text(body,"requestKey"),note=ehr.safe(body,"reason",600);
        require(key.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"),422,"requestKey须为UUID");
        require(!note.isEmpty(),422,"请填写移除原因");
        Map<String,Object> previous=db.one("SELECT record_id,redacted_diff FROM a_health_archive_audit_logs WHERE scope_code=? AND data_mode=? AND action='report_removed' AND request_key=?",a.scope,a.mode,key);
        if(previous!=null){
            @SuppressWarnings("unchecked")Map<String,Object> result=(Map<String,Object>)ehr.decode(previous.get("redacted_diff"));
            require(id.equals(result.get("removedReportId"))&&revision==num(result.get("revision")),409,"操作幂等键已用于其他报告");
            if(previous.get("record_id")!=null)ehr.profile(a,str(previous.get("record_id")),false);return map("removed",true,"reportId",id,"recordId",previous.get("record_id"));
        }
        Map<String,Object> report=ehr.rawReport(a,id,revision,false);String record=str(report.get("record_id"));
        if(!record.isEmpty())ehr.profile(a,record,true);report=ehr.rawReport(a,id,revision,true);
        Map<String,Object> series=db.one("SELECT current_revision FROM a_report_series WHERE report_id=? AND scope_code=? AND data_mode=? FOR UPDATE",id,a.scope,a.mode);
        require(series!=null&&num(series.get("current_revision"))==revision,409,"报告版本已变化，请刷新后再移除");
        require(etag!=null&&etag.equals(hex(digest(bytes(ehr.encode(ehr.snapshot(id,revision)))))),409,"报告已变化，请刷新后核对再移除");
        require(db.count("SELECT COUNT(*) FROM a_review_records WHERE report_id=?",id)==0&&db.count("SELECT COUNT(*) FROM a_report_publications WHERE report_id=?",id)==0,409,"已有审核或归档记录，不能移除，请按修订流程处理");
        require(db.count("SELECT COUNT(*) FROM a_health_reports WHERE report_id=? AND record_id IS NOT NULL AND record_id<>?",id,record)==0,409,"历史版本关联其他职工，请联系管理员核验");
        ehr.cancelOcrTasks(id,null,"报告已由后台移除："+note);
        db.exec("UPDATE a_report_series SET current_revision=NULL WHERE report_id=?",id);
        db.exec("UPDATE a_health_reports SET record_id=NULL WHERE report_id=?",id);
        db.insert("a_health_archive_audit_logs",ehr.scoped(a,"audit_id",id(),"record_id",record.isEmpty()?null:record,"actor_ref",bytes(a.user),"action","report_removed","request_key",key,"reason",note,
            "redacted_diff",ehr.encode(map("removedReportId",id,"revision",revision,"oldRecordId",record)),"occurred_at",now()));
        ehr.completeness(a,record);
        return map("removed",true,"reportId",id,"recordId",record);
    }
}
