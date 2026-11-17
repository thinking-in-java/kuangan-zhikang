package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Actual MySQL queries and business transitions; all fixtures roll back. No login bypass endpoint. */
public final class MiniappReportChecks {
    static int checks;
    static EhrRepository db;
    static EhrService ehr;
    static MiniappReportService lists;
    static String run=UUID.randomUUID().toString(),record;
    static EhrActor doctor,employee;
    static void check(String name,boolean value){if(!value)throw new AssertionError(name);checks++;System.out.println("PASS "+name);}
    static Map<String,Object> list(String tab){return lists.list(employee,tab,Collections.emptyMap());}
    static List<Map<String,Object>> items(Map<String,Object> result){return (List<Map<String,Object>>)result.get("items");}
    static void rejected(String name,Runnable call,int code){try{call.run();throw new AssertionError(name);}catch(EhrException e){check(name,e.status==code);}}
    static Map<String,Object> report(String target,String mode,String queue){
        String rid=id(),src=id(),job=id();
        Map<String,Object> scope=map("scope_code",doctor.scope,"data_mode",mode);
        Map<String,Object> s=new LinkedHashMap<>(scope);s.putAll(map("source_file_id",src,"sha256",digest(bytes(src)),"object_ref",bytes("fixture-"+src),"page_count",2));db.insert("a_report_sources",s);
        s=new LinkedHashMap<>(scope);s.putAll(map("report_id",rid,"source_file_id",src));db.insert("a_report_series",s);
        s=new LinkedHashMap<>(scope);s.putAll(map("report_id",rid,"revision",1,"record_id",target,"report_kind","individual","field_values","[]","dictionary_version","2.0.0","review_status","unreviewed"));db.insert("a_health_reports",s);
        s=new LinkedHashMap<>(scope);s.putAll(map("report_id",rid,"revision",1,"source_file_id",src));db.insert("a_report_revision_sources",s);
        db.exec("UPDATE a_report_series SET current_revision=1 WHERE report_id=?",rid);
        s=new LinkedHashMap<>(scope);s.putAll(map("job_id",job,"report_id",rid,"revision",1,"request_key",id(),"pipeline_version","test-miniapp-list","status","uploaded"));db.insert("a_extraction_jobs",s);
        s=new LinkedHashMap<>(scope);s.putAll(map("job_id",job,"dispatch_state",queue,"progress_percent","running".equals(queue)?30:0,"available_at",now(),"created_at",now(),"updated_at",now()));
        if("running".equals(queue))s.putAll(map("current_attempt_id",id(),"worker_id","synthetic-fixture","lease_token_hash",digest(bytes(job)),"lease_until","2099-01-01 00:00:00"));db.insert("a_ocr_dispatch_tasks",s);
        s=new LinkedHashMap<>(scope);s.putAll(map("upload_id",id(),"source_file_id",src,"request_key",id(),"upload_channel","backend_file","original_file_name","synthetic.pdf","mime_type","application/pdf","file_size",100,"uploader_ref",bytes("test")));db.insert("a_report_upload_metadata",s);
        return map("id",rid,"job",job);
    }
    static void approve(String rid){
        Map<String,Object> r=ehr.report(doctor,rid,1,false);
        r=ehr.saveDraft(doctor,rid,1,map("fullName","Miniapp list fixture","examDate","2026-11-15","institution","Fixture institution","formalConclusion","Synthetic conclusion","items",Arrays.asList(map("name","Fixture item","value","Fixture raw value","page",1))),str(r.get("etag")));
        ehr.transition(doctor,rid,1,map("action","submit","requestKey",id(),"note","Synthetic test"));
        r=ehr.report(doctor,rid,1,false);
        ehr.transition(doctor,rid,1,map("action","approve","requestKey",id(),"note","Synthetic test","identityVerified",true,"etag",r.get("etag")));
    }
    public static void main(String[] args){
        DriverManagerDataSource source=new DriverManagerDataSource(System.getenv("EHR_CHECK_JDBC"),System.getenv("EHR_CHECK_USER"),System.getenv("EHR_CHECK_PASSWORD"));
        db=new EhrRepository(source);ehr=new EhrService(db);lists=new MiniappReportService(db,ehr);
        TransactionTemplate tx=new TransactionTemplate(new DataSourceTransactionManager(source));
        tx.execute(status->{try{
            doctor=new EhrActor("synthetic-miniapp-lists","10000","synthetic",true,false);
            record=str(ehr.upsert(doctor,null,map("name","Miniapp list fixture","code","ML_"+run.substring(0,12),"sex","未填写","org","Fixture unit","team","Fixture team","job","Fixture job")).get("id"));
            employee=new EhrActor("wx:synthetic-fixture","10000","synthetic",false,true,record);
            check("empty_tabs_return_zero",num(list("all").get("total"))==0&&items(list("pending")).isEmpty()&&items(list("approved")).isEmpty());
            String queued=str(report(record,"synthetic","queued").get("id"));
            String processing=str(report(record,"synthetic","running").get("id"));
            String failed=str(report(record,"synthetic","failed").get("id"));
            String passed=str(report(record,"synthetic","queued").get("id"));approve(passed);
            String published=str(report(record,"synthetic","queued").get("id"));approve(published);
            ehr.transition(doctor,published,1,map("action","archive","requestKey",id(),"note","Synthetic archive"));
            Map<String,Object> all=list("all"),a=list("approved"),p=list("pending");
            check("approved_contains_reviewed_and_archived",num(a.get("total"))==2);
            Set<String> approvedStates=new HashSet<>();for(Map<String,Object> row:items(a))approvedStates.add(str(row.get("status")));
            check("approved_and_archived_labels_are_distinct",approvedStates.equals(new HashSet<>(Arrays.asList("approved","archived"))));
            check("pending_excludes_confirmed_reports",num(p.get("total"))==3);
            Set<String> states=new HashSet<>();for(Map<String,Object> row:items(p))states.add(str(row.get("status")));
            check("pending_exposes_queued_processing_failed",states.equals(new HashSet<>(Arrays.asList("queued","processing","failed"))));
            check("tab_counts_consistent",num(all.get("total"))==5&&num(((Map<String,Object>)all.get("counts")).get("pending"))==3);
            Map<String,Object> page=lists.list(employee,"all",Collections.singletonMap("pageSize","2"));
            check("pagination_limits_rows_and_has_more",items(page).size()==2&&Boolean.TRUE.equals(page.get("hasMore")));
            Map<String,String> third=new HashMap<>();third.put("pageSize","2");third.put("page","3");
            check("last_page_has_one_row",items(lists.list(employee,"all",third)).size()==1&&!Boolean.TRUE.equals(lists.list(employee,"all",third).get("hasMore")));
            check("list_contains_metadata_without_clinical_candidates",items(all).stream().allMatch(x->x.containsKey("detailPath")&&x.containsKey("fileName")&&!x.containsKey("fieldValues")&&!x.containsKey("items")));
            String other=str(ehr.upsert(doctor,null,map("name","Other fixture","code","MO_"+run.substring(0,12),"sex","未填写","org","Fixture unit","team","Fixture team","job","Fixture job")).get("id"));
            report(other,"synthetic","queued");
            check("another_employee_report_not_visible",num(list("all").get("total"))==5);
            report(null,"real","queued");
            check("other_data_mode_not_visible",num(list("all").get("total"))==5);
            Map<String,Object> detached=report(record,"synthetic","succeeded");
            db.exec("UPDATE a_report_series SET current_revision=NULL WHERE report_id=?",detached.get("id"));
            db.exec("UPDATE a_health_reports SET record_id=NULL WHERE report_id=?",detached.get("id"));
            check("removed_report_not_visible",num(list("all").get("total"))==5);
            ehr.revise(doctor,queued,1,"Synthetic revision");
            check("old_versions_not_duplicated",num(list("all").get("total"))==5&&items(list("all")).stream().anyMatch(x->queued.equals(x.get("reportId"))&&num(x.get("revision"))==2));
            String returned=str(report(record,"synthetic","queued").get("id"));
            ehr.transition(doctor,returned,1,map("action","return","requestKey",id(),"note","Synthetic missing material"));
            check("returned_new_revision_has_returned_label",items(list("pending")).stream().anyMatch(x->returned.equals(x.get("reportId"))&&"returned".equals(x.get("status"))&&num(x.get("revision"))==2));
            String rejectedReport=str(report(record,"synthetic","queued").get("id"));
            ehr.transition(doctor,rejectedReport,1,map("action","reject","requestKey",id(),"note","Synthetic rejection"));
            check("rejected_report_stays_in_pending_tab_with_label",items(list("pending")).stream().anyMatch(x->rejectedReport.equals(x.get("reportId"))&&"rejected".equals(x.get("status"))));
            rejected("unknown_status_is422",()->list("wrong"),422);
            rejected("zero_page_is422",()->lists.list(employee,"all",Collections.singletonMap("page","0")),422);
            rejected("overlarge_page_size_is422",()->lists.list(employee,"all",Collections.singletonMap("pageSize","51")),422);
            rejected("caller_cannot_supply_other_record_id",()->lists.list(employee,"all",Collections.singletonMap("recordId",other)),422);
            rejected("unbound_actor_is403",()->lists.list(new EhrActor("test","10000","synthetic",false,true),"all",Collections.emptyMap()),403);
            rejected("cross_scope_actor_is404",()->lists.list(new EhrActor("test","99999","synthetic",false,true,record),"all",Collections.emptyMap()),404);
            return null;
        }finally{status.setRollbackOnly();}});
        check("all_database_fixtures_rolled_back",db.count("SELECT COUNT(*) FROM a_employee_roster_profiles WHERE employee_no IN (?,?)","ML_"+run.substring(0,12),"MO_"+run.substring(0,12))==0);
        System.out.println("PASS Miniapp report lists: "+checks+" checks; transaction rolled back");
    }
}
