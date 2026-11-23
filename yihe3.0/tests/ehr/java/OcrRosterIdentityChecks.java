package com.yihe.framework.ehr;

import java.time.*;
import java.util.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Isolated MySQL callback integration; all fixture writes roll back, no model requests. */
public class OcrRosterIdentityChecks {
    static int checks;
    static void check(String label,boolean ok){if(!ok)throw new AssertionError(label);checks++;System.out.println("PASS "+label);}
    static class Fixture {
        String record,report,job,source;Map<String,Object> original;
    }
    static Fixture fixture(EhrRepository db,EhrService ehr,EhrActor actor,String birth,String channel){
        Fixture f=new Fixture();Map<String,Object> person=ehr.upsert(actor,null,map("name","原姓名","code","IDENTITY_"+id(),"sex","男","org","隔离测试煤矿","team","原班组","job","原岗位","phone","13800000000","birth",birth));
        f.record=str(person.get("id"));f.original=ehr.profile(actor,f.record,false);addReport(f,db,ehr,actor,channel);return f;
    }
    static void addReport(Fixture f,EhrRepository db,EhrService ehr,EhrActor actor,String channel){
        f.report=HealthAgentChecks.report(db,ehr,actor,f.record);f.source=str(db.one("SELECT source_file_id FROM a_report_series WHERE report_id=?",f.report).get("source_file_id"));f.job=id();
        db.insert("a_extraction_jobs",ehr.scoped(actor,"job_id",f.job,"report_id",f.report,"revision",1,"request_key",id(),"pipeline_version","identity-fixture-v1","status","uploaded","created_at",now(),"updated_at",now()));
        db.insert("a_report_upload_metadata",ehr.scoped(actor,"upload_id",id(),"source_file_id",f.source,"request_key",id(),"upload_channel",channel,"original_file_name","synthetic-identity.png","mime_type","image/png","file_size",1,"uploader_ref",bytes(actor.user),"uploaded_at",now()));
    }
    static Map<String,Object> body(Fixture f,String name,String age){
        String evidence=id();List<Object> fields=new ArrayList<>();
        if(name!=null)fields.add(map("field_key","full_name","original_field_name","姓名","value_raw",name,"evidence_ids",Arrays.asList(evidence),"review_status","needs_review"));
        if(age!=null)fields.add(map("field_key","age","original_field_name","年龄","value_raw",age,"evidence_ids",Arrays.asList(evidence),"review_status","needs_review"));
        return map("contract_version","2.0.0","report_id",f.report,"revision",1,"report_kind","individual","age_raw",age,
            "evidence",Arrays.asList(map("evidence_id",evidence,"source_file_id",f.source,"page_number",1,"coordinate_system","object_only","bbox",null,"original_label","姓名年龄","raw_fragment","synthetic identity evidence","extraction_method","ocr","extractor_version","identity-fixture-v1")),
            "field_values",fields,"items",new ArrayList<>(),"conclusions",new ArrayList<>(),"advice",new ArrayList<>());
    }
    @SuppressWarnings("unchecked") static Map<String,Object> withSex(Map<String,Object> body,String sex) {
        List<Object> fields=(List<Object>)body.get("field_values");
        Object evidence=((Map<?,?>)((List<?>)body.get("evidence")).get(0)).get("evidence_id");
        fields.add(map("field_key","sex","original_field_name","性别","value_raw",sex,"evidence_ids",Arrays.asList(evidence),"review_status","needs_review"));return body;
    }
    @SuppressWarnings("unchecked") public static void main(String[] args)throws Exception{
        if(!System.getenv("EHR_JDBC_URL").contains("/shayu_3_preview?"))throw new IllegalArgumentException("Isolated preview database required");
        DriverManagerDataSource ds=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));
        EhrRepository db=new EhrRepository(ds);EhrService ehr=new EhrService(db);EhrOcrService ocr=new EhrOcrService(db,ehr);
        java.lang.reflect.Field mode=EhrOcrService.class.getDeclaredField("runtimeMode");mode.setAccessible(true);mode.set(ocr,"synthetic");
        EhrActor actor=new EhrActor("identity-fixture","IDENTITY_"+id().substring(0,8),"synthetic",true,false);
        new TransactionTemplate(new DataSourceTransactionManager(ds)).execute(tx->{try{
            for(String channel:Arrays.asList("backend_file","phone_photo","miniapp")) {
                Fixture f=fixture(db,ehr,actor,"1980-12-31",channel);Map<String,Object> payload=withSex(body(f,"报告不同姓名","30岁"),"女");
                Map<String,Object> report=(Map<String,Object>)ocr.result(f.job,payload);
                check(channel+"_ocr_preserves_entire_roster",ehr.encode(f.original).equals(ehr.encode(ehr.profile(actor,f.record,false))));
                check(channel+"_raw_report_identity_retained",ehr.encode(report.get("fieldValues")).contains("报告不同姓名")&&ehr.encode(report.get("fieldValues")).contains("女")&&"30岁".equals(db.one("SELECT age_raw FROM a_health_reports WHERE report_id=?",f.report).get("age_raw")));
                check(channel+"_identity_conflict_requires_review","conflict".equals(((Map<?,?>)((List<?>)report.get("qualityChecks")).get(0)).get("identityStatus")));
                check(channel+"_not_approved_or_published","needs_review".equals(report.get("reviewStatus"))&&!Boolean.TRUE.equals(report.get("published")));
                ocr.result(f.job,payload);
                check(channel+"_replay_cannot_mutate_roster",ehr.encode(f.original).equals(ehr.encode(ehr.profile(actor,f.record,false))));
                check(channel+"_no_automatic_sync_audit",db.count("SELECT COUNT(*) FROM a_health_archive_audit_logs WHERE record_id=? AND action='ocr_roster_identity_synced'",f.record)==0);
            }
            Fixture manual=fixture(db,ehr,actor,"1982-06-18","backend_file");
            Map<String,Object> report=(Map<String,Object>)ocr.result(manual.job,withSex(body(manual,"原姓名","25"),"女"));
            Map<String,Object> draft=ehr.saveDraft(actor,manual.report,1,map("fullName","原姓名","examDate","2026-11-14","institution","合成检查机构","formalConclusion","仅用于隔离回归检查","advice","","items",Arrays.asList(map("name","检查项目原名","value","原始结果","unit","","page",1))),str(report.get("etag")));
            check("manual_candidate_save_preserves_roster",ehr.encode(manual.original).equals(ehr.encode(ehr.profile(actor,manual.record,false))));
            ehr.transition(actor,manual.report,1,map("action","submit","requestKey",id(),"note","隔离提交验证"));
            check("submit_report_preserves_roster",ehr.encode(manual.original).equals(ehr.encode(ehr.profile(actor,manual.record,false))));
            check("submit_keeps_original_birth_and_gender","1982-06-18".equals(ehr.profile(actor,manual.record,false).get("birth_date"))&&"男".equals(ehr.profile(actor,manual.record,false).get("gender")));
            Map<String,Object> prior=ehr.profile(actor,manual.record,false);
            ehr.upsert(actor,manual.record,map("name","人工核对姓名","code",prior.get("employee_no"),"org",prior.get("organization_name"),"sex","女","birth","1995-06-18","phone",prior.get("phone"),"team",prior.get("team_name"),"job",prior.get("position_name"),"rowVersion",prior.get("row_version")));
            check("authorized_manual_edit_still_available","人工核对姓名".equals(ehr.profile(actor,manual.record,false).get("employee_name"))&&"女".equals(ehr.profile(actor,manual.record,false).get("gender"))&&"1995-06-18".equals(ehr.profile(actor,manual.record,false).get("birth_date")));
            check("no_report_automatically_published",db.count("SELECT COUNT(*) FROM a_report_publications WHERE scope_code=?",actor.scope)==0);
            return null;
        }finally{tx.setRollbackOnly();}});
        check("all_fixtures_rolled_back",db.count("SELECT COUNT(*) FROM a_employee_roster_profiles WHERE scope_code=?",actor.scope)==0);
        System.out.println("PASS OCR roster identity preservation checks: "+checks+"; synthetic data rolled back; no model requests");
    }
}
