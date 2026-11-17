package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Metadata for the authenticated employee's current report versions. */
@Service
public class MiniappReportService {
    private final EhrRepository db;
    private final EhrService ehr;
    public MiniappReportService(EhrRepository db,EhrService ehr){this.db=db;this.ehr=ehr;}

    @Transactional(readOnly=true)
    public Map<String,Object> list(EhrActor actor,String status,Map<String,String> query){
        require(actor.mobile&&actor.boundRecordId!=null,403,"请先绑定职工身份");
        ehr.profile(actor,actor.boundRecordId,false);
        require(Arrays.asList("all","approved","pending").contains(status),422,"status仅支持all、approved、pending");
        // Employee identity must always be derived from the active binding, not query parameters.
        for(String k:query.keySet())require(Arrays.asList("status","page","pageSize").contains(k),422,"不支持查询参数："+k);
        int page=number(query.get("page"),1,1,100000),pageSize=number(query.get("pageSize"),10,1,50);
        String from=" FROM a_health_reports h JOIN a_report_series s ON s.report_id=h.report_id AND s.current_revision=h.revision";
        String where=" WHERE h.scope_code=? AND h.data_mode=? AND h.record_id=?";
        Object[] owner={actor.scope,actor.mode,actor.boundRecordId};
        String approved="(h.review_status='confirmed' AND EXISTS(SELECT 1 FROM a_review_records v WHERE v.report_id=h.report_id AND v.revision=h.revision AND v.decision='confirm'))";
        long all=db.count("SELECT COUNT(*)"+from+where,owner);
        long passed=db.count("SELECT COUNT(*)"+from+where+" AND "+approved,owner);
        long total="approved".equals(status)?passed:"pending".equals(status)?all-passed:all;
        String filter="approved".equals(status)?" AND "+approved:"pending".equals(status)?" AND NOT "+approved:"";
        String fields="SELECT h.report_id reportId,h.revision,h.review_status reviewStatus,h.exam_date_raw examDateRaw,h.exam_date_verified examDateVerified,h.institution_raw institutionRaw,h.created_at uploadedAt,"+
            "u.original_file_name fileName,u.mime_type mimeType,u.file_size fileSize,src.page_count pageCount,"+
            "j.job_id jobId,j.status stage,q.dispatch_state queueState,q.progress_stage progressStage,q.progress_percent progressPercent,q.last_error_code errorCode,q.last_error_message errorMessage,"+
            approved+" approved,EXISTS(SELECT 1 FROM a_report_publications pub WHERE pub.report_id=h.report_id AND pub.revision=h.revision) published,"+
            "(SELECT MAX(v.created_at) FROM a_review_records v WHERE v.report_id=h.report_id AND v.revision=h.revision AND v.decision='confirm') reviewedAt,"+
            "(SELECT v.decision FROM a_review_records v WHERE v.report_id=h.report_id AND (v.revision=h.revision OR (v.revision=h.prior_revision AND v.decision='return')) ORDER BY v.created_at DESC,v.review_id DESC LIMIT 1) reviewDecision";
        String joins=" LEFT JOIN a_report_sources src ON src.source_file_id=s.source_file_id"+
            " LEFT JOIN a_extraction_jobs j ON j.job_id=(SELECT j2.job_id FROM a_extraction_jobs j2 WHERE j2.report_id=h.report_id AND j2.revision=h.revision ORDER BY j2.created_at DESC,j2.job_id DESC LIMIT 1)"+
            " LEFT JOIN a_ocr_dispatch_tasks q ON q.job_id=j.job_id"+
            " LEFT JOIN a_report_upload_metadata u ON u.upload_id=(SELECT u2.upload_id FROM a_report_upload_metadata u2 WHERE u2.source_file_id=s.source_file_id ORDER BY (u2.request_key=j.request_key) DESC,u2.uploaded_at DESC,u2.upload_id DESC LIMIT 1)";
        List<Map<String,Object>> items=db.all(fields+from+joins+where+filter+" ORDER BY h.created_at DESC,h.report_id DESC LIMIT ? OFFSET ?",actor.scope,actor.mode,actor.boundRecordId,pageSize,(page-1)*pageSize);
        for(Map<String,Object> row:items){
            boolean isApproved=truth(row.get("approved")),published=truth(row.get("published"));
            row.put("approved",isApproved);row.put("published",published);row.put("candidate",!isApproved);
            row.put("tab",isApproved?"approved":"pending");
            String code=state(row,isApproved,published);row.put("status",code);row.put("statusText",label(code));
            row.put("title",str(row.get("fileName")).isEmpty()?"体检报告":row.get("fileName"));
            row.put("progressPercent",isApproved?100:row.get("progressPercent")==null?0:row.get("progressPercent"));
            row.put("detailPath","/ehr/miniapp/reports/"+row.get("reportId")+"/"+row.get("revision"));
            row.put("canRetry",false); // Worker retries remain a backend operation.
        }
        return map("status",status,"page",page,"pageSize",pageSize,"total",total,"hasMore",(long)page*pageSize<total,
            "counts",map("all",all,"approved",passed,"pending",all-passed),"items",items);
    }
    static int number(String raw,int fallback,int min,int max){
        if(raw==null)return fallback;
        try{require(raw.matches("[0-9]{1,6}"),422,"分页参数必须是正整数");int n=Integer.parseInt(raw);require(n>=min&&n<=max,422,"分页参数超出范围");return n;}
        catch(NumberFormatException e){throw new EhrException(422,"分页参数必须是正整数");}
    }
    static boolean truth(Object value){return Boolean.TRUE.equals(value)||value instanceof Number&&((Number)value).intValue()!=0;}
    static String state(Map<String,Object> r,boolean approved,boolean published){
        if(approved)return published?"archived":"approved";
        String decision=str(r.get("reviewDecision")),queue=str(r.get("queueState"));
        if("reject".equals(decision)||"rejected".equals(r.get("reviewStatus")))return "rejected";
        if("return".equals(decision)||"returned".equals(r.get("reviewStatus")))return "returned";
        if("failed".equals(queue)||"failed".equals(r.get("stage")))return "failed";
        if("cancelled".equals(queue))return "cancelled";
        if("running".equals(queue))return "processing";
        if("queued".equals(queue)||"retry_wait".equals(queue))return "queued";
        return "pending_review";
    }
    static String label(String s){
        switch(s){case "archived":return "已审核·已归档";case "approved":return "已审核·待归档";
        case "queued":return "等待识别";case "processing":return "正在识别";case "failed":return "识别失败";
        case "cancelled":return "识别已停止";case "returned":return "已退回·待完善";case "rejected":return "审核未通过";
        default:return "待审核";}
    }
}
