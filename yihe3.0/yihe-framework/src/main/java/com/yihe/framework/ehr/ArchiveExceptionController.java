package com.yihe.framework.ehr;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

@RestController
@RequestMapping("/ehr/api/report-center/exceptions")
public class ArchiveExceptionController {
 final EhrAccess auth;final EhrRepository db;final EhrService ehr;final ArchiveAutomationService automation;
 public ArchiveExceptionController(EhrAccess auth,EhrRepository db,EhrService ehr,ArchiveAutomationService automation){this.auth=auth;this.db=db;this.ehr=ehr;this.automation=automation;}
 @GetMapping public Object list(HttpServletRequest request,@RequestParam Map<String,String> q){
  EhrActor a=auth.web(request);require(!a.mobile,403,"请使用后台异常处理工作台");int page=bounded(q.get("page"),1,1,100000),limit=bounded(q.get("limit"),20,1,100);String state=q.getOrDefault("status","active");require(Arrays.asList("active","open","acknowledged","resolved","all").contains(state),422,"异常状态不正确");
  String base=" FROM a_archive_exception_tasks x JOIN a_health_reports h ON h.report_id=x.report_id AND h.revision=x.revision JOIN a_report_series s ON s.report_id=x.report_id AND s.current_revision=x.revision LEFT JOIN a_health_records r ON r.record_id=h.record_id AND r.scope_code=h.scope_code AND r.data_mode=h.data_mode LEFT JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode WHERE x.scope_code=? AND x.data_mode=? AND p.deleted_at IS NULL AND (r.record_id IS NOT NULL OR (h.record_id IS NULL AND EXISTS(SELECT 1 FROM a_health_archive_audit_logs l WHERE l.report_id=h.report_id AND l.revision=h.revision AND l.scope_code=h.scope_code AND l.data_mode=h.data_mode AND l.action='report_intake_uploaded')))";
  Map<String,Object> counts=db.one("SELECT COUNT(*) total,SUM(x.task_status='open') open,SUM(x.task_status='acknowledged') acknowledged,SUM(x.task_status IN ('resolved','medical_resolved')) resolved,SUM(x.severity='critical' AND x.task_status NOT IN ('resolved','medical_resolved')) critical"+base,a.scope,a.mode);List<Object> args=new ArrayList<>(Arrays.asList(a.scope,a.mode));
  String filtered=base;if(state.equals("active"))filtered+=" AND x.task_status NOT IN ('resolved','medical_resolved')";else if(!state.equals("all")){filtered+=" AND x.task_status=?";args.add(state);}String report=q.getOrDefault("report","");if(!report.isEmpty()){filtered+=" AND x.report_id=?";args.add(report);}
  long total=db.count("SELECT COUNT(*)"+filtered,args.toArray());args.add(limit);args.add((page-1)*limit);
  return map("code",0,"data",map("items",db.all("SELECT x.exception_id id,x.report_id reportId,x.revision,x.category,x.severity,x.item_id itemId,x.message,x.task_status status,x.created_at createdAt,x.acknowledged_by acknowledgedBy,p.employee_name name,p.employee_no code"+filtered+" ORDER BY x.severity='critical' DESC,x.task_status='open' DESC,x.created_at DESC,x.exception_id LIMIT ? OFFSET ?",args.toArray()),"total",total,"page",page,"limit",limit,"counts",counts,"canAcknowledgeMedical",a.doctor));
 }
 @PostMapping("/{id}/actions") @Transactional public Object action(HttpServletRequest request,@PathVariable String id,@RequestBody Map<String,Object> body){
  EhrActor a=auth.web(request);require(!a.mobile,403,"个人端不能处理后台异常");Map<String,Object> x=db.one("SELECT * FROM a_archive_exception_tasks WHERE exception_id=? AND scope_code=? AND data_mode=? FOR UPDATE",id,a.scope,a.mode);require(x!=null,404,"异常任务不存在或无权限");String report=str(x.get("report_id"));int revision=num(x.get("revision"));Map<String,Object> h=ehr.rawReport(a,report,revision,false);require(db.count("SELECT COUNT(*) FROM a_report_series WHERE report_id=? AND current_revision=?",report,revision)==1,409,"已有新版本，请刷新异常队列");String action=text(body,"action");
  if(action.equals("acknowledge")){if("medical".equals(x.get("category")))require(a.doctor,403,"危急值提醒须由医疗人员确认接收");String note=text(body,"note");require(!note.isEmpty()&&note.length()<=600,422,"请填写600字以内处理说明");require(!"resolved".equals(x.get("task_status")),409,"异常已由数据复核自动消除");db.exec("UPDATE a_archive_exception_tasks SET task_status='acknowledged',acknowledged_by=?,acknowledged_at=UTC_TIMESTAMP(6),updated_at=UTC_TIMESTAMP(6) WHERE exception_id=?",a.user,id);ehr.audit(a,h.get("record_id")==null?null:str(h.get("record_id")),report,revision,"archive_exception_ack","已接收异常任务："+note+"；未自动更正字段或签署医疗审核");}
  else if(action.equals("resolve_medical")){require(a.doctor&&"medical".equals(x.get("category")),403,"仅医疗人员可记录医疗异常处置");String note=text(body,"note");require(note.length()>=10&&note.length()<=600,422,"请填写10至600字的处置依据和结果");require(Boolean.TRUE.equals(body.get("handled")),422,"请明确确认已由医疗人员完成该异常处置");Map<String,Object> details=(Map<String,Object>)ehr.decode(x.get("details_json"));details.put("medicalDisposition",note);details.put("resolvedBy",a.user);details.put("resolvedAt",now());db.exec("UPDATE a_archive_exception_tasks SET task_status='medical_resolved',details_json=?,acknowledged_by=?,acknowledged_at=UTC_TIMESTAMP(6),updated_at=UTC_TIMESTAMP(6) WHERE exception_id=?",ehr.encode(details),a.user,id);ehr.audit(a,h.get("record_id")==null?null:str(h.get("record_id")),report,revision,"archive_medical_disposition",note+"；仅记录该异常处置，不替代整份报告医疗签署");}
  else if(action.equals("recheck")){automation.register(a,report,revision);db.exec("UPDATE a_archive_automation SET retry_after=NULL WHERE report_id=? AND revision=?",report,revision);}
  else throw new EhrException(422,"仅支持接收异常或重新检查；须实际更正原始数据后由系统消除异常");
  return map("code",0,"data",map("accepted",true,"action",action));
 }
}
