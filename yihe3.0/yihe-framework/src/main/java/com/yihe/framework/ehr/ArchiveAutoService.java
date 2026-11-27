package com.yihe.framework.ehr;

import java.util.*;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Administrative archive ledger. Called inside ArchiveAutomationService's transaction. */
public class ArchiveAutoService {
 final EhrRepository db;final EhrService ehr;
 public ArchiveAutoService(EhrRepository db,EhrService ehr){this.db=db;this.ehr=ehr;}
 static Object ordered(Object value){if(value instanceof Map){Map<String,Object> result=new TreeMap<>();((Map<?,?>)value).forEach((k,v)->result.put(str(k),ordered(v)));return result;}if(value instanceof List){List<Object> result=new ArrayList<>();for(Object item:(List<?>)value)result.add(ordered(item));return result;}return value;}
 public Map<String,Object> status(EhrActor a,String report,int revision){
  Map<String,Object> r=db.one("SELECT x.archive_status archiveStatus,x.identity_status identityStatus,x.ocr_status ocrStatus,x.quality_status qualityStatus,x.archive_time archiveTime,x.archive_version archiveVersion,x.source_digest sourceDigest,x.quality_json quality,CASE WHEN h.review_status='confirmed' THEN 'confirmed' WHEN h.review_status='rejected' THEN 'rejected' ELSE 'pending' END medicalReviewStatus FROM a_report_archive_entries x JOIN a_health_reports h ON h.report_id=x.report_id AND h.revision=x.revision AND h.record_id=x.record_id WHERE x.report_id=? AND x.revision=? AND x.scope_code=? AND x.data_mode=?",report,revision,a.scope,a.mode);
  if(r!=null)r.put("quality",ehr.decode(r.get("quality")));return r;
 }
 public void save(EhrActor a,Map<String,Object> h,Map<String,Object> quality){
  String report=str(h.get("report_id"));int revision=num(h.get("revision"));Map<String,Object> snapshot=ehr.snapshot(report,revision);String digest=hex(digest(bytes(ehr.encode(ordered(map("snapshot",snapshot,"quality",quality)))))),q=ehr.encode(quality);Map<String,Object> prior=db.one("SELECT * FROM a_report_archive_entries WHERE report_id=? AND revision=? FOR UPDATE",report,revision);
  boolean changed=prior==null||!digest.equals(prior.get("source_digest"))||!quality.equals(ehr.decode(prior.get("quality_json")));int version=prior==null?1:num(prior.get("archive_version"))+(changed?1:0);
  if(!changed)return;
  db.insert("a_report_archive_versions",map("report_id",report,"revision",revision,"archive_version",version,"source_digest",digest,"snapshot_json",ehr.encode(snapshot),"quality_json",q,"created_at",now()));
  if(prior==null)db.insert("a_report_archive_entries",map("report_id",report,"revision",revision,"scope_code",a.scope,"data_mode",a.mode,"record_id",h.get("record_id"),"archive_status",quality.get("archiveStatus"),"identity_status","matched","ocr_status","completed","quality_status",quality.get("qualityStatus"),"archive_time",now(),"archive_version",version,"source_digest",digest,"quality_json",q,"updated_at",now()));
  else db.exec("UPDATE a_report_archive_entries SET record_id=?,archive_status=?,quality_status=?,archive_version=?,source_digest=?,quality_json=?,updated_at=UTC_TIMESTAMP(6) WHERE report_id=? AND revision=?",h.get("record_id"),quality.get("archiveStatus"),quality.get("qualityStatus"),version,digest,q,report,revision);
  ehr.audit(a,str(h.get("record_id")),report,revision,"report_auto_archived","档案小谷自动保存原件关联和结构化指标，行政入档版本"+version+"；医疗审核状态未改变");
 }
 @SuppressWarnings("unchecked") public void syncIssues(EhrActor a,String report,int revision,List<Map<String,Object>> issues){
  Set<String> keys=new HashSet<>();for(Map<String,Object> i:issues){String key=str(i.get("key"));keys.add(key);Map<String,Object> old=db.one("SELECT exception_id,task_status,details_json FROM a_archive_exception_tasks WHERE report_id=? AND revision=? AND issue_key=?",report,revision,key);
   if(old==null){db.insert("a_archive_exception_tasks",map("exception_id",id(),"report_id",report,"revision",revision,"scope_code",a.scope,"data_mode",a.mode,"issue_key",key,"category",i.get("category"),"severity",i.get("severity"),"item_id",i.get("itemId"),"message",i.get("message"),"details_json",ehr.encode(i),"created_at",now(),"updated_at",now()));if("critical".equals(i.get("severity")))ehr.audit(a,null,report,revision,"archive_medical_alert",str(i.get("message")));}
   else if("medical_resolved".equals(old.get("task_status"))&&i.get("observationDigest")!=null&&i.get("observationDigest").equals(((Map<?,?>)ehr.decode(old.get("details_json"))).get("observationDigest")))continue;
   else db.exec("UPDATE a_archive_exception_tasks SET task_status=IF(task_status IN ('resolved','medical_resolved'),'open',task_status),message=?,details_json=?,updated_at=UTC_TIMESTAMP(6) WHERE exception_id=?",i.get("message"),ehr.encode(i),old.get("exception_id"));
  }
  for(Map<String,Object> old:db.all("SELECT exception_id,issue_key FROM a_archive_exception_tasks WHERE report_id=? AND revision=? AND task_status NOT IN ('resolved','medical_resolved')",report,revision))if(!keys.contains(str(old.get("issue_key"))))db.exec("UPDATE a_archive_exception_tasks SET task_status='resolved',updated_at=UTC_TIMESTAMP(6) WHERE exception_id=?",old.get("exception_id"));
 }
 public List<Map<String,Object>> issues(EhrActor a,String report,int revision){return db.all("SELECT exception_id id,category,severity,item_id itemId,message,task_status status,acknowledged_by acknowledgedBy,acknowledged_at acknowledgedAt,created_at createdAt FROM a_archive_exception_tasks WHERE report_id=? AND revision=? AND scope_code=? AND data_mode=? AND task_status NOT IN ('resolved','medical_resolved') ORDER BY severity='critical' DESC,created_at,exception_id",report,revision,a.scope,a.mode);}
}
