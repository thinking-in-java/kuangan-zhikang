package com.yihe.framework.ehr;

import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.*;
import static com.yihe.framework.ehr.EhrAccess.map;

/** Current report versions, scoped exactly like the existing health archive API. */
@RestController
@RequestMapping("/ehr/api/report-center")
public class ReportCenterController {
    @Autowired EhrService ehr;
    @PostMapping("/uploads") public Object upload(HttpServletRequest request,@RequestParam("file") MultipartFile file,@RequestHeader("Idempotency-Key") String key,@RequestParam(defaultValue="backend_file") String channel)throws IOException {
        return map("code",0,"data",ehr.upload(auth.web(request),null,file,key,channel));
    }
    @Autowired ArchiveAutomationService automation;
    @GetMapping("/{id}/{revision}/automation") public Object automationStatus(HttpServletRequest request,@PathVariable String id,@PathVariable int revision){return map("code",0,"data",automation.status(auth.web(request),id,revision));}
    @PostMapping("/{id}/{revision}/automation") public Object resumeAutomation(HttpServletRequest request,@PathVariable String id,@PathVariable int revision){EhrActor a=auth.web(request);return map("code",0,"data",automation.register(a,id,revision));}
    final EhrAccess auth; final EhrRepository db;
    public ReportCenterController(EhrAccess auth,EhrRepository db){this.auth=auth;this.db=db;}
    @GetMapping public Object list(HttpServletRequest request,@RequestParam Map<String,String> q){
        EhrActor a=auth.web(request);
        if(a.mobile)throw new EhrException(403,"仅供后台管理使用");
        int page=EhrService.bounded(q.get("page"),1,1,100000),limit=EhrService.bounded(q.get("limit"),10,1,100);
        String sql="SELECT h.report_id id,h.revision,r.record_id recordId,p.employee_name employeeName,p.employee_no employeeCode,p.organization_name organization,COALESCE(h.exam_date_verified,h.exam_date_raw) examDate,h.review_status reviewStatus,(SELECT x.archive_status FROM a_report_archive_entries x WHERE x.report_id=h.report_id AND x.revision=h.revision) archiveStatus,(SELECT x.quality_status FROM a_report_archive_entries x WHERE x.report_id=h.report_id AND x.revision=h.revision) qualityStatus,"+
            "(SELECT CONVERT(u.original_file_name USING utf8mb4) FROM a_report_upload_metadata u WHERE u.source_file_id=s.source_file_id ORDER BY u.uploaded_at DESC LIMIT 1) fileName,"+
            "(SELECT u.upload_channel FROM a_report_upload_metadata u WHERE u.source_file_id=s.source_file_id ORDER BY u.uploaded_at DESC LIMIT 1) channel,"+
            "s.source_file_id sourceId,h.created_at uploadedAt,"+
            "CASE WHEN EXISTS(SELECT 1 FROM a_report_publications pub WHERE pub.report_id=h.report_id AND pub.revision=h.revision) THEN 'archived' "+
            "WHEN h.review_status='confirmed' THEN 'reviewed' "+
            "WHEN EXISTS(SELECT 1 FROM a_report_archive_entries x WHERE x.report_id=h.report_id AND x.revision=h.revision AND x.archive_status='archived') THEN 'auto_archived' "+
            "WHEN EXISTS(SELECT 1 FROM a_report_archive_entries x WHERE x.report_id=h.report_id AND x.revision=h.revision AND x.archive_status='partial') THEN 'partial_archived' "+
            "WHEN h.review_status='rejected' THEN 'failed' "+
            "WHEN (SELECT j.status FROM a_extraction_jobs j WHERE j.report_id=h.report_id AND (j.revision=h.revision OR j.revision IS NULL) ORDER BY j.created_at DESC,j.job_id DESC LIMIT 1)='pending_review' THEN 'pending_review' "+
            "WHEN (SELECT t.dispatch_state FROM a_ocr_dispatch_tasks t JOIN a_extraction_jobs j ON j.job_id=t.job_id WHERE j.report_id=h.report_id ORDER BY t.updated_at DESC,t.job_id DESC LIMIT 1)='running' THEN 'running' "+
            "WHEN (SELECT t.dispatch_state FROM a_ocr_dispatch_tasks t JOIN a_extraction_jobs j ON j.job_id=t.job_id WHERE j.report_id=h.report_id ORDER BY t.updated_at DESC,t.job_id DESC LIMIT 1)='failed' THEN 'failed' "+
            "WHEN h.record_id IS NULL AND EXISTS(SELECT 1 FROM a_ehr_ocr_receipts o JOIN a_extraction_jobs j ON j.job_id=o.job_id WHERE j.report_id=h.report_id AND j.revision=h.revision) THEN 'pending_match' WHEN EXISTS(SELECT 1 FROM a_health_items i WHERE i.report_id=h.report_id AND i.revision=h.revision) THEN 'pending_check' ELSE 'queued' END status "+
            "FROM a_health_reports h JOIN a_report_series s ON s.report_id=h.report_id AND s.current_revision=h.revision "+
            "LEFT JOIN a_health_records r ON r.record_id=h.record_id AND r.scope_code=h.scope_code AND r.data_mode=h.data_mode "+
            "LEFT JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode "+
            "WHERE h.scope_code=? AND h.data_mode=? AND p.deleted_at IS NULL AND (r.record_id IS NOT NULL OR (h.record_id IS NULL AND EXISTS(SELECT 1 FROM a_health_archive_audit_logs l WHERE l.report_id=h.report_id AND l.revision=h.revision AND l.scope_code=h.scope_code AND l.data_mode=h.data_mode AND l.action='report_intake_uploaded')))";
        List<Object> args=new ArrayList<>(Arrays.asList(a.scope,a.mode));
        String filtered="SELECT * FROM ("+sql+") reports WHERE 1=1";
        for(String key:new String[]{"from","to"}){
            String value=q.get(key);if(value!=null&&!value.isEmpty()){
                try{java.time.LocalDate.parse(value);}catch(Exception e){throw new EhrException(422,"日期格式不正确");}
                filtered+=" AND examDate"+(key.equals("from")?">=?":"<=?");args.add(value);
            }
        }
        for(String key:new String[]{"organization","channel"}){
            String value=q.get(key);if(value!=null&&!value.isEmpty()){filtered+=" AND "+key+"=?";args.add(value);}
        }
        String search=q.get("search");if(search!=null&&!search.trim().isEmpty()){
            if(search.length()>100)throw new EhrException(422,"搜索内容过长");
            filtered+=" AND (employeeName LIKE ? ESCAPE '!' OR employeeCode LIKE ? ESCAPE '!' OR fileName LIKE ? ESCAPE '!')";
            for(int i=0;i<3;i++)args.add("%"+EhrService.like(search.trim())+"%");
        }
        List<Map<String,Object>> facets=db.all("SELECT status,COUNT(*) count FROM ("+filtered+") f GROUP BY status",args.toArray());
        Map<String,Object> counts=new LinkedHashMap<>();long all=0;
        for(Map<String,Object> row:facets){counts.put(String.valueOf(row.get("status")),row.get("count"));all+=((Number)row.get("count")).longValue();}
        counts.put("all",all);
        String status=q.get("status");if(status!=null&&!status.isEmpty()&&!status.equals("all")){
            if(status.equals("recognized"))filtered+=" AND status IN ('pending_match','pending_check','pending_review','reviewed','archived','auto_archived','partial_archived')";
            else if(status.equals("pending_match")){filtered+=" AND status=?";args.add(status);}
            else {
                if(!Arrays.asList("queued","running","pending_check","pending_review","reviewed","archived","auto_archived","partial_archived","failed").contains(status))throw new EhrException(422,"状态不正确");
                filtered+=" AND status=?";args.add(status);
            }
        }
        long total=db.count("SELECT COUNT(*) FROM ("+filtered+") f",args.toArray());
        List<Object> paged=new ArrayList<>(args);paged.add(limit);paged.add((page-1)*limit);
        List<Map<String,Object>> items=db.all(filtered+" ORDER BY uploadedAt DESC,id DESC LIMIT ? OFFSET ?",paged.toArray());
        List<Map<String,Object>> units=db.all("SELECT DISTINCT organization_name name FROM a_employee_roster_profiles WHERE scope_code=? AND data_mode=? AND deleted_at IS NULL ORDER BY organization_name",a.scope,a.mode);
        return map("code",0,"data",map("items",items,"total",total,"page",page,"limit",limit,"counts",counts,"organizations",units));
    }
}
