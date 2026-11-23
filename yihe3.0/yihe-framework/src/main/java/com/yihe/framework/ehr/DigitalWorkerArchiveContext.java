package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Read the same scoped completeness rules as the archive UI, without modifying records. */
@Service
public class DigitalWorkerArchiveContext {
    private final EhrRepository db;
    private final EhrService ehr;
    public DigitalWorkerArchiveContext(EhrRepository db,EhrService ehr) {this.db=db;this.ehr=ehr;}
    String encode(Object value) {return ehr.encode(value);}
    Object decode(Object value) {return ehr.decode(value);}
    String reportDigest(EhrActor actor,String recordId,String reportId,int revision) {
        Map<String,Object> report=ehr.rawReport(actor,reportId,revision,false);
        if(!Objects.equals(recordId,report.get("record_id")))throw new EhrException(403,"核对报告与当前职工不一致");
        if(db.count("SELECT COUNT(*) FROM a_report_series WHERE report_id=? AND current_revision=?",reportId,revision)==0)throw new EhrException(409,"报告已有新版本，请重新核对");
        return hex(digest(bytes(ehr.encode(ehr.snapshot(reportId,revision)))));
    }

    public Map<String,Object> read(EhrActor actor,String recordId) {
        if (recordId==null||recordId.isEmpty()) return map("linked",false,"capturedAt",now());
        Map<String,Object> profile=ehr.profile(actor,recordId,false);
        Map<String,Object> info=db.one(ehr.projection()+" AND r.record_id=?",actor.scope,actor.mode,recordId);
        Map<String,Object> report=db.one("SELECT h.* FROM a_health_reports h JOIN a_report_series s "+
            "ON s.report_id=h.report_id AND s.current_revision=h.revision WHERE h.record_id=? AND h.scope_code=? AND h.data_mode=? "+
            "ORDER BY h.created_at DESC,h.report_id DESC LIMIT 1",recordId,actor.scope,actor.mode);
        List<String> missing=ehr.missing(profile,report);
        Map<String,Object> result=map("linked",true,"recordId",recordId,"name",profile.get("employee_name"),
            "code",profile.get("employee_no"),"status",info.get("status"),"systemCompleteness",info.get("complete"),
            "checkedCompleteness",Math.round((9-missing.size())*1000.0/9)/10.0,"missing",missing,
            "rule","ehr-required-documents-v1","capturedAt",now(),"report",null);
        if (report!=null) {
            String id=str(report.get("report_id"));int rev=num(report.get("revision"));
            List<Map<String,Object>> files=db.all("SELECT s.page_count pageCount FROM a_report_revision_sources r "+
                "JOIN a_report_sources s ON s.source_file_id=r.source_file_id WHERE r.report_id=? AND r.revision=?",id,rev);
            result.put("report",map("reportId",id,"revision",rev,"reviewStatus",report.get("review_status"),
                "published",db.count("SELECT COUNT(*) FROM a_report_publications WHERE report_id=? AND revision=?",id,rev)>0,
                "examDate",report.get("exam_date_verified"),"institution",report.get("institution_raw"),
                "sourceFiles",files.size(),"pageCount",files.stream().mapToInt(f->num(f.get("pageCount"))).sum(),
                "itemCount",db.count("SELECT COUNT(*) FROM a_health_items WHERE report_id=? AND revision=?",id,rev),
                "completeConclusions",db.count("SELECT COUNT(*) FROM a_health_conclusions WHERE report_id=? AND revision=? "+
                    "AND completeness='complete' AND original_text IS NOT NULL AND TRIM(original_text)<>''",id,rev),
                "adviceCount",db.count("SELECT COUNT(*) FROM a_health_advice WHERE report_id=? AND revision=?",id,rev)));
        }
        return result;
    }

    /** Only structural completeness facts go to the model. Names/codes and report contents stay local. */
    @SuppressWarnings("unchecked")
    public Map<String,Object> modelFacts(Map<String,Object> snapshot) {
        if (!Boolean.TRUE.equals(snapshot.get("linked"))) return map("linked",false);
        Map<String,Object> facts=new LinkedHashMap<>();
        for (String key:Arrays.asList("linked","status","systemCompleteness","checkedCompleteness","missing","rule","capturedAt"))
            facts.put(key,snapshot.get(key));
        Map<String,Object> report=(Map<String,Object>)snapshot.get("report");
        if (report!=null) {
            Map<String,Object> summary=new LinkedHashMap<>();
            for (String key:Arrays.asList("revision","reviewStatus","published","examDate","institution","sourceFiles","pageCount","itemCount","completeConclusions","adviceCount"))
                summary.put(key,report.get(key));
            facts.put("latestReport",summary);
        }
        return facts;
    }
}
