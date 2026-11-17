package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Report erasure belongs to the employee deletion transaction; file erasure follows commit. */
@Service
public class ReportDeletionService {
    private final EhrRepository db;
    public ReportDeletionService(EhrRepository db){this.db=db;}
    @Transactional public int forRecord(EhrActor actor,String record,String reason){
        List<Map<String,Object>> reports=db.all("SELECT DISTINCT report_id FROM a_health_reports WHERE record_id=? AND scope_code=? AND data_mode=? ORDER BY report_id",record,actor.scope,actor.mode);
        for(Map<String,Object> report:reports){
            String id=str(report.get("report_id"));
            require(db.count("SELECT COUNT(*) FROM a_health_reports WHERE report_id=? AND record_id IS NOT NULL AND record_id<>?",id,record)==0,409,"报告历史关联其他职工，请先核对关联后再删除，避免误删他人报告");
            db.exec("CALL a_delete_health_report(?,?,?,?,?)",id,actor.scope,actor.mode,bytes(actor.user),reason);
        }
        // No stale completeness/risk snapshot may survive a removed employee's reports.
        db.exec("DELETE f FROM a_health_risk_factors f JOIN a_health_risk_assessments a ON a.assessment_id=f.assessment_id WHERE a.record_id=?",record);
        db.exec("DELETE s FROM a_health_risk_report_sources s JOIN a_health_risk_assessments a ON a.assessment_id=s.assessment_id WHERE a.record_id=?",record);
        db.exec("DELETE FROM a_health_risk_assessments WHERE record_id=?",record);
        db.exec("DELETE FROM a_health_record_completeness WHERE record_id=?",record);
        return reports.size();
    }
}
