package com.yihe.framework.ehr;
import java.util.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;
public class ReportMatchingChecks {
    static int checks;
    static void check(String label,boolean ok){if(!ok)throw new AssertionError(label);checks++;System.out.println("PASS "+label);}
    static void reject(String label,int status,Runnable action){try{action.run();throw new AssertionError(label);}catch(EhrException e){check(label,e.status==status);}}
    static List<Object> fields(String name,String code){List<Object> result=new ArrayList<>();result.add(map("field_key","full_name","value_raw",name));if(!code.isEmpty())result.add(map("field_key","employee_number","value_raw",code));result.add(map("field_key","identity_number","value_raw","612799198001010019"));return result;}
    @SuppressWarnings("unchecked") public static void main(String[] args){
        if(!System.getenv("EHR_JDBC_URL").contains("/shayu_3_preview?"))throw new IllegalArgumentException("Isolated preview database required");
        DriverManagerDataSource ds=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));EhrRepository db=new EhrRepository(ds);EhrService ehr=new EhrService(db);ReportMatchingService service=new ReportMatchingService(db,ehr);
        EhrActor actor=new EhrActor("matching-fixture","MATCH_"+id().substring(0,8),"synthetic",true,false),reader=new EhrActor("matching-reader",actor.scope.substring(4),"synthetic",false,false);
        new TransactionTemplate(new DataSourceTransactionManager(ds)).execute(tx->{try{
            OcrRosterIdentityChecks.Fixture f=OcrRosterIdentityChecks.fixture(db,ehr,actor,"1980-12-31","backend_file");Map<String,Object> second=ehr.upsert(actor,null,map("name","原姓名","code","MATCH_B_"+id(),"sex","女","org","隔离测试煤矿","team","另一班组","job","另一岗位"));String target=str(second.get("id"));Map<String,Object> secondOriginal=ehr.profile(actor,target,false);
            db.exec("UPDATE a_health_reports SET field_values=? WHERE report_id=?",ehr.encode(fields("原姓名","")),f.report);
            Map<String,Object> detail=service.detail(actor,f.report,1);check("same_name_is_ambiguous_not_exact","similar".equals(detail.get("matchStatus"))&&num(detail.get("candidateCount"))==2);
            check("identity_number_masked",!ehr.encode(detail).contains("612799198001010019")&&ehr.encode(detail).contains("********"));
            check("read_does_not_auto_rebind",f.record.equals(ehr.rawReport(actor,f.report,1,false).get("record_id")));
            db.exec("UPDATE a_health_reports SET field_values=? WHERE report_id=?",ehr.encode(fields("原姓名",str(f.original.get("employee_no")))),f.report);detail=service.detail(actor,f.report,1);
            check("unique_employee_number_disambiguates_names","exact".equals(detail.get("matchStatus")));
            check("rules_are_scored_not_model_confidence",!detail.containsKey("confidence")&&((List<Map<String,Object>>)detail.get("candidates")).get(0).containsKey("evidence"));
            Map<String,Object> select=map("action","selection","recordId",target,"profileVersion",secondOriginal.get("row_version"),"reason","隔离测试保存人工候选","requestKey",id());String originalTag=str(detail.get("etag"));service.save(actor,f.report,1,originalTag,select);
            check("selection_does_not_change_binding",f.record.equals(ehr.rawReport(actor,f.report,1,false).get("record_id")));
            service.save(actor,f.report,1,originalTag,select);check("request_replay_idempotent",db.count("SELECT COUNT(*) FROM a_health_archive_audit_logs WHERE report_id=? AND action='report_match_selection'",f.report)==1);
            Map<String,Object> changed=new LinkedHashMap<>(select);changed.put("reason","不同请求内容");reject("same_key_different_payload_rejected",409,()->service.save(actor,f.report,1,originalTag,changed));
            Map<String,Object> fresh=service.detail(actor,f.report,1);Map<String,Object> confirm=map("action","confirm","recordId",target,"profileVersion",secondOriginal.get("row_version"),"reason","已根据隔离测试原件核验职工身份并确认更正关联","requestKey",id());String tag=str(fresh.get("etag"));
            reject("stale_matching_etag_rejected",409,()->service.save(actor,f.report,1,originalTag,confirm));
            reject("explicit_identity_confirmation_required",422,()->service.save(actor,f.report,1,tag,confirm));confirm.put("identityVerified",true);
            reject("conflicting_identifier_requires_override",422,()->service.save(actor,f.report,1,tag,confirm));confirm.put("override",true);confirm.put("allowRebind",true);
            reject("rebind_requires_doctor",403,()->service.save(reader,f.report,1,tag,confirm));
            ehr.enqueueOcr(actor,f.job);reject("active_ocr_blocks_confirmation",409,()->service.save(actor,f.report,1,tag,confirm));db.exec("DELETE FROM a_ocr_dispatch_tasks WHERE job_id=?",f.job);
            confirm.put("profileVersion",num(secondOriginal.get("row_version"))+1);reject("stale_employee_version_rejected",409,()->service.save(actor,f.report,1,tag,confirm));confirm.put("profileVersion",secondOriginal.get("row_version"));
            String originalFields=str(ehr.rawReport(actor,f.report,1,false).get("field_values"));Map<String,Object> matched=service.save(actor,f.report,1,tag,confirm);
            check("explicit_rebind_persisted",target.equals(ehr.rawReport(actor,f.report,1,false).get("record_id"))&&"confirmed".equals(matched.get("matchStatus")));
            check("report_raw_identity_preserved",originalFields.equals(str(ehr.rawReport(actor,f.report,1,false).get("field_values"))));
            check("both_roster_profiles_unchanged",ehr.encode(f.original).equals(ehr.encode(ehr.profile(actor,f.record,false)))&&ehr.encode(secondOriginal).equals(ehr.encode(ehr.profile(actor,target,false))));
            check("binding_audit_retained",str(db.one("SELECT redacted_diff FROM a_health_archive_audit_logs WHERE report_id=? AND action='report_match_confirm'",f.report).get("redacted_diff")).contains("bindingChanged"));
            db.exec("UPDATE a_employee_roster_profiles SET row_version=row_version+1 WHERE employee_id=?",second.get("employee_id"));check("employee_changes_invalidate_confirmation",!"confirmed".equals(service.detail(actor,f.report,1).get("matchStatus")));db.exec("UPDATE a_employee_roster_profiles SET row_version=row_version-1 WHERE employee_id=?",second.get("employee_id"));
            Map<String,Object> unmatched=map("action","unmatched","reason","隔离测试保留原件待重新核验","requestKey",id());service.save(actor,f.report,1,str(service.detail(actor,f.report,1).get("etag")),unmatched);
            check("unmatched_mark_keeps_report_and_current_binding",target.equals(ehr.rawReport(actor,f.report,1,false).get("record_id"))&&"none".equals(service.detail(actor,f.report,1).get("matchStatus")));
            reject("cross_org_detail_denied",404,()->service.detail(new EhrActor("other","OTHER_MATCH","synthetic",true,false),f.report,1));
            reject("mobile_list_denied",403,()->service.list(new EhrActor("mobile",actor.scope.substring(4),"synthetic",false,true),new HashMap<>()));
            Map<String,String> q=new HashMap<>();q.put("search","%' OR 1=1 --");check("search_cannot_inject_sql",num(service.list(actor,q).get("total"))==0);q.clear();q.put("status","invalid");reject("invalid_status_rejected",422,()->service.list(actor,q));
            q.clear();q.put("from","not-a-date");reject("invalid_date_rejected",422,()->service.list(actor,q));
            db.exec("UPDATE a_health_reports SET review_status='confirmed',reviewed_snapshot_ref=?,exam_date_verified='2026-01-01' WHERE report_id=?",bytes("isolated-match-lock"),f.report);check("reviewed_report_locked",Boolean.TRUE.equals(service.detail(actor,f.report,1).get("locked")));
            reject("reviewed_report_matching_write_denied",409,()->service.save(actor,f.report,1,str(service.detail(actor,f.report,1).get("etag")),map("action","flag","reason","隔离测试不允许修改已审核关联","requestKey",id())));
            check("no_automatic_review_or_publication",db.count("SELECT COUNT(*) FROM a_review_records WHERE report_id=?",f.report)==0&&db.count("SELECT COUNT(*) FROM a_report_publications WHERE report_id=?",f.report)==0);
            check("no_ocr_requests_created",db.count("SELECT COUNT(*) FROM a_ocr_dispatch_tasks WHERE job_id=?",f.job)==0);
            return null;
        }finally{tx.setRollbackOnly();}});System.out.println("PASS report matching checks: "+checks+"; all fixtures rolled back, no model requests");
    }
}
