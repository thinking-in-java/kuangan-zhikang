package com.yihe.framework.ehr;
import java.util.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;
public class OcrWorkbenchChecks {
    static int checks;
    static void check(String name,boolean ok){if(!ok)throw new AssertionError(name);checks++;System.out.println("PASS "+name);}
    @SuppressWarnings("unchecked") public static void main(String[] args){
        if(!System.getenv("EHR_JDBC_URL").contains("/shayu_3_preview?"))throw new IllegalArgumentException("Isolated preview database required");
        DriverManagerDataSource ds=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));EhrRepository db=new EhrRepository(ds);EhrService ehr=new EhrService(db);EhrOcrService ocr=new EhrOcrService(db,ehr);OcrWorkerQueue queue=new OcrWorkerQueue(db,ehr,ocr);OcrWorkbenchController controller=new OcrWorkbenchController(null,db,ehr,queue);
        EhrActor actor=new EhrActor("workbench-fixture","OCR_DESK_"+id().substring(0,8),"synthetic",true,false);
        new TransactionTemplate(new DataSourceTransactionManager(ds)).execute(tx->{try{
            OcrRosterIdentityChecks.Fixture f=OcrRosterIdentityChecks.fixture(db,ehr,actor,"1980-12-31","backend_file");
            Map<String,Object> initial=controller.read(actor,f.report,1),report=(Map<String,Object>)initial.get("report");String etag=str(report.get("etag"));
            check("scoped_report_and_employee_loaded",initial.get("employee")!=null&&Boolean.TRUE.equals(initial.get("editable")));
            check("confidence_not_fabricated",!initial.containsKey("confidence")&&!report.containsKey("confidence"));
            check("no_fake_ocr_task",initial.get("task")==null);
            Map<String,Object> body=map("fullName","原姓名","sex","女","age","30","employee_number","REPORT-TEST","workshop","报告班组","job_type","报告岗位","examDate","2026-01-01","institution","隔离机构","metadataPage",1,"conclusionPage",1,"formalConclusion","隔离测试结论","advice","隔离测试建议","items",Arrays.asList(map("name","隔离指标","value","6.8","unit","mmol/L","referenceRange","3.9-6.1","abnormal","↑","page",1,"itemCode","")));
            Map<String,Object> saved=controller.save(actor,f.report,1,etag,body),updated=(Map<String,Object>)saved.get("report");
            check("report_metadata_saved",ehr.encode(updated.get("fieldValues")).contains("REPORT-TEST")&&ehr.encode(updated.get("fieldValues")).contains("报告班组"));
            check("report_age_and_sex_saved",ehr.encode(updated.get("fieldValues")).contains("女")&&"30".equals(db.one("SELECT age_raw FROM a_health_reports WHERE report_id=?",f.report).get("age_raw")));
            check("roster_name_gender_birth_unchanged",ehr.encode(f.original).equals(ehr.encode(ehr.profile(actor,f.record,false))));
            check("raw_result_and_reference_saved",ehr.encode(updated.get("items")).contains("6.8")&&ehr.encode(updated.get("items")).contains("3.9-6.1"));
            Map<String,Object> log=db.one("SELECT redacted_diff FROM a_health_archive_audit_logs WHERE report_id=? AND action='ocr_workbench_saved'",f.report);
            check("field_before_after_audited",str(log.get("redacted_diff")).contains("fieldChanges")&&str(log.get("redacted_diff")).contains("before")&&str(log.get("redacted_diff")).contains("after"));
            check("new_etag_returned",!etag.equals(updated.get("etag")));
            try{controller.save(actor,f.report,1,etag,body);throw new AssertionError("stale save accepted");}catch(EhrException expected){check("stale_etag_rejected",true);}
            Map<String,Object> invalid=new LinkedHashMap<>(body);invalid.put("age","300");try{controller.save(actor,f.report,1,str(updated.get("etag")),invalid);throw new AssertionError();}catch(EhrException expected){check("invalid_age_rejected",true);}
            check("invalid_save_keeps_current_etag",updated.get("etag").equals(((Map<String,Object>)controller.read(actor,f.report,1).get("report")).get("etag")));
            EhrActor other=new EhrActor("other","OTHER_ORG","synthetic",true,false);try{controller.read(other,f.report,1);throw new AssertionError();}catch(EhrException expected){check("cross_org_read_denied",true);}
            EhrActor mobile=new EhrActor("mobile",actor.scope.substring(4),"synthetic",false,true);try{controller.read(mobile,f.report,1);throw new AssertionError();}catch(EhrException expected){check("mobile_read_denied",true);}
            db.exec("UPDATE a_health_reports SET review_status='confirmed',reviewed_snapshot_ref=? WHERE report_id=? AND revision=1",bytes("isolated-readonly-fixture"),f.report);
            check("approved_report_readonly",Boolean.FALSE.equals(controller.read(actor,f.report,1).get("editable")));
            try{controller.save(actor,f.report,1,str(updated.get("etag")),body);throw new AssertionError();}catch(EhrException expected){check("approved_report_server_save_denied",true);}
            check("no_automatic_publication",db.count("SELECT COUNT(*) FROM a_report_publications WHERE report_id=?",f.report)==0);
            check("no_automatic_review",db.count("SELECT COUNT(*) FROM a_review_records WHERE report_id=?",f.report)==0);
            check("no_model_requests_enqueued",db.count("SELECT COUNT(*) FROM a_ocr_dispatch_tasks WHERE job_id=?",f.job)==0);
            return null;
        }finally{tx.setRollbackOnly();}});
        System.out.println("PASS OCR workbench checks: "+checks+"; fixtures rolled back, no model requests");
    }
}
