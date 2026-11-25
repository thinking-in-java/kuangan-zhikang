package com.yihe.framework.ehr;
import java.util.*;
import org.springframework.stereotype.Service;
import static com.yihe.framework.ehr.EhrAccess.map;
import static com.yihe.framework.ehr.EhrService.*;
@Service
public class EmployeeHealthArchiveService {
 final EhrRepository db;final EhrService ehr;
 public EmployeeHealthArchiveService(EhrRepository db,EhrService ehr){this.db=db;this.ehr=ehr;}
 void allowed(EhrActor a){require(!a.mobile,403,"请使用企业管理端访问职工档案");}
 public Map<String,Object> list(EhrActor a,Map<String,String> q){allowed(a);return ehr.list(a,q);}
 public Map<String,Object> overview(EhrActor a){allowed(a);Map<String,Object> s=ehr.statistics(a);
 String joins=" FROM a_health_reports h JOIN a_report_series rs ON rs.report_id=h.report_id AND rs.current_revision=h.revision JOIN a_health_records r ON r.record_id=h.record_id JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode WHERE h.scope_code=? AND h.data_mode=? AND p.deleted_at IS NULL";
 s.put("withReport",db.count("SELECT COUNT(DISTINCT h.record_id)"+joins,a.scope,a.mode));
 s.put("monthReports",db.count("SELECT COUNT(*)"+joins+" AND DATE_FORMAT(CONVERT_TZ(h.created_at,'+00:00','+08:00'),'%Y-%m')=DATE_FORMAT(CONVERT_TZ(UTC_TIMESTAMP(),'+00:00','+08:00'),'%Y-%m')",a.scope,a.mode));
 s.put("pendingFollowups",db.count("SELECT COUNT(*) FROM a_followup_tasks f JOIN a_health_reports h ON h.report_id=f.report_id AND h.revision=f.revision JOIN a_health_records r ON r.record_id=h.record_id JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode WHERE h.scope_code=? AND h.data_mode=? AND p.deleted_at IS NULL AND f.status NOT IN ('completed','cancelled','done','closed')",a.scope,a.mode));
 s.put("organizations",db.all("SELECT DISTINCT p.organization_name name"+ehr.baseSql()+" ORDER BY p.organization_name",a.scope,a.mode));return s;}
 public Map<String,Object> detail(EhrActor a,String record){allowed(a);Map<String,Object> d=ehr.detail(a,record);
 d.put("assessments",db.all("SELECT assessment_id id,assessment_time time,risk_score score,risk_level level,assessment_type type,reviewed_at reviewedAt FROM a_health_risk_assessments WHERE scope_code=? AND data_mode=? AND record_id=? AND assessment_status='confirmed' ORDER BY assessment_time DESC LIMIT 30",a.scope,a.mode,record));
 d.put("followups",db.all("SELECT f.task_id id,f.status,f.due_at dueAt,h.report_id reportId,h.revision FROM a_followup_tasks f JOIN a_health_reports h ON h.report_id=f.report_id AND h.revision=f.revision WHERE h.scope_code=? AND h.data_mode=? AND h.record_id=? ORDER BY f.created_at DESC LIMIT 100",a.scope,a.mode,record));
 d.put("serviceTasks",db.all("SELECT task_id id,worker_id worker,task_type type,title,task_status status,result_note result,created_at createdAt FROM a_digital_worker_tasks WHERE scope_code=? AND data_mode=? AND record_id=? ORDER BY created_at DESC LIMIT 100",a.scope,a.mode,record));return d;}
}
