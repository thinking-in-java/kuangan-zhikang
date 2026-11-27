package com.yihe.framework.ehr;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Real SQL and OCR receipt contracts in the preview database; all writes roll back. */
public class ReportIntakeChecks {
 static int checks;
 static void check(String name,boolean ok){if(!ok)throw new AssertionError(name);checks++;System.out.println("PASS "+name);}
 static void reject(String name,int code,Runnable call){try{call.run();throw new AssertionError(name);}catch(EhrException e){check(name,e.status==code);}}
 static void set(Object target,String field,Object value)throws Exception{java.lang.reflect.Field f=target.getClass().getDeclaredField(field);f.setAccessible(true);f.set(target,value);}
 static class Picture implements MultipartFile {
  final byte[] data;Picture()throws Exception{BufferedImage i=new BufferedImage(2,2,BufferedImage.TYPE_INT_RGB);i.setRGB(0,0,new Random().nextInt());ByteArrayOutputStream o=new ByteArrayOutputStream();ImageIO.write(i,"png",o);data=o.toByteArray();}
  public String getName(){return "file";}public String getOriginalFilename(){return "intake-contract-fixture.png";}public String getContentType(){return "image/png";}public boolean isEmpty(){return false;}public long getSize(){return data.length;}public byte[] getBytes(){return data;}public InputStream getInputStream(){return new ByteArrayInputStream(data);}public void transferTo(File file)throws IOException{Files.write(file.toPath(),data);}
 }
 @SuppressWarnings("unchecked") public static void main(String[] args)throws Exception{
  if(!System.getenv("EHR_JDBC_URL").contains("/shayu_3_preview?"))throw new IllegalArgumentException("Preview database required");
  DriverManagerDataSource ds=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));EhrRepository db=new EhrRepository(ds);EhrService ehr=new EhrService(db);ehr.storage=Files.createTempDirectory("report-intake-check-").toString();EhrOcrService ocr=new EhrOcrService(db,ehr);set(ocr,"runtimeMode","synthetic");OcrWorkerQueue queue=new OcrWorkerQueue(db,ehr,ocr);set(queue,"mode","synthetic");ReportMatchingService matching=new ReportMatchingService(db,ehr);ReportRemovalService removal=new ReportRemovalService(db,ehr);
  EhrActor actor=new EhrActor("intake-contract-test","INTAKE_"+id().substring(0,8),"synthetic",false,false),cross=new EhrActor(actor.user,"OTHER_INTAKE","synthetic",true,false),mobile=new EhrActor(actor.user,actor.scope.substring(4),"synthetic",false,true);
  ReportCenterChecks.Access access=new ReportCenterChecks.Access(db);access.actor=actor;ReportCenterController center=new ReportCenterController(access,db);center.ehr=ehr;
  new TransactionTemplate(new DataSourceTransactionManager(ds)).execute(tx->{try{
   Map<String,Object> person=ehr.upsert(actor,null,map("name","原姓名","code","INTAKE-001","org","测试煤矿","team","测试班组","job","测试工种","sex","男","birth","1980-05-12","phone","13800000000"));String record=str(person.get("id"));Map<String,Object> original=new LinkedHashMap<>(ehr.profile(actor,record,false));Picture picture=new Picture();String key=id();Map<String,Object> report=ehr.upload(actor,null,picture,key,"backend_file");String rid=str(report.get("id")),job=str(report.get("jobId"));
   check("upload_does_not_require_or_invent_employee",report.get("recordId")==null);
   check("unassigned_report_is_eligible_for_existing_ocr_queue",queue.eligible(job));check("unassigned_report_is_visible_in_task_center",num(queue.list(actor,new HashMap<>()).get("total"))==1);
   check("source_file_downloads_before_matching",Files.isRegularFile(ehr.download(actor,str(((Map<?,?>)((List<?>)report.get("files")).get(0)).get("id")))));
   check("idempotent_upload_creates_one_report",rid.equals(ehr.upload(actor,null,picture,key,"backend_file").get("id"))&&db.count("SELECT COUNT(*) FROM a_health_reports WHERE scope_code=?",actor.scope)==1);
   reject("mobile_cannot_read_unassigned_report",404,()->ehr.report(mobile,rid,1,false));reject("cross_scope_cannot_read_unassigned_report",404,()->ehr.report(cross,rid,1,false));reject("mobile_cannot_upload_unassigned",403,()->{try{ehr.upload(mobile,null,picture,id(),"miniapp");}catch(IOException e){throw new RuntimeException(e);}});
   check("unassigned_source_listed_in_upload_center",((Number)ReportCenterChecks.list(center,new HashMap<>()).get("total")).intValue()==1);
   Map<String,Object> selection=map("action","confirm","recordId",record,"profileVersion",original.get("row_version"),"requestKey",id(),"identityVerified",true,"reason","已对照原件核验报告身份及工号");Map<String,Object> current=matching.detail(actor,rid,1);
   reject("cannot_confirm_before_ocr_finishes",409,()->matching.save(actor,rid,1,str(current.get("etag")),selection));
   OcrRosterIdentityChecks.Fixture f=new OcrRosterIdentityChecks.Fixture();f.report=rid;f.source=str(((Map<?,?>)((List<?>)report.get("files")).get(0)).get("id"));f.job=job;
   Map<String,Object> body=OcrRosterIdentityChecks.withSex(OcrRosterIdentityChecks.body(f,"原姓名","30岁"),"男");List<Object> fields=(List<Object>)body.get("field_values");Object evidence=((Map<?,?>)((List<?>)body.get("evidence")).get(0)).get("evidence_id");for(String[] v:new String[][]{{"employee_number","INTAKE-001"},{"job_type","测试工种"}})fields.add(map("field_key",v[0],"original_field_name",v[0],"value_raw",v[1],"evidence_ids",Arrays.asList(evidence),"review_status","needs_review"));
   queue.legacyComplete(job,body);report=ehr.report(actor,rid,1,false);
   check("ocr_finishes_without_binding_employee",report.get("recordId")==null&&"succeeded".equals(((Map<?,?>)report.get("ocrTask")).get("state")));
   check("occupational_evidence_waits_for_confirmed_identity",db.count("SELECT COUNT(*) FROM a_occupational_profiles WHERE report_id=? AND employee_id IS NULL",rid)==1);
   Map<String,String> pending=new HashMap<>();pending.put("status","pending_match");check("pending_matching_filter_has_actual_rows",((Number)ReportCenterChecks.list(center,pending).get("total")).intValue()==1);
   Map<String,Object> detail=matching.detail(actor,rid,1);check("ocr_identity_generates_real_employee_candidate",num(detail.get("candidateCount"))==1&&"exact".equals(detail.get("matchStatus")));
   selection.put("action","selection");Map<String,Object> draft=matching.save(actor,rid,1,str(detail.get("etag")),selection);check("draft_persists_without_binding",ehr.report(actor,rid,1,false).get("recordId")==null&&record.equals(draft.get("savedSelection")));
   selection.put("action","confirm");selection.put("requestKey",id());reject("stale_match_etag_is_rejected",409,()->matching.save(actor,rid,1,"old-etag",selection));selection.put("identityVerified",false);reject("human_identity_check_required",422,()->matching.save(actor,rid,1,str(draft.get("etag")),selection));selection.put("identityVerified",true);
   matching.save(actor,rid,1,str(draft.get("etag")),selection);Map<String,Object> bound=ehr.report(actor,rid,1,false);
   check("confirmed_match_binds_correct_employee",record.equals(bound.get("recordId")));
   check("confirmed_match_links_occupational_evidence",db.count("SELECT COUNT(*) FROM a_occupational_profiles WHERE report_id=? AND employee_id=?",rid,original.get("employee_id"))==1);
   check("no_roster_identity_changes",ehr.encode(original).equals(ehr.encode(ehr.profile(actor,record,false))));
   check("confirmation_never_reviews_or_publishes",!Boolean.TRUE.equals(bound.get("published"))&&"needs_review".equals(bound.get("reviewStatus")));
   check("upload_replay_after_binding_stays_idempotent",rid.equals(ehr.upload(actor,null,picture,key,"backend_file").get("id")));
   Map<String,Object> unassigned=ehr.upload(actor,null,new Picture(),id(),"backend_file");String removeId=str(unassigned.get("id"));Map<String,Object> request=map("reason","隔离测试移除未匹配报告","requestKey",id());removal.remove(actor,removeId,1,str(unassigned.get("etag")),request);reject("removed_intake_is_not_resurrected",404,()->ehr.report(actor,removeId,1,false));check("removal_replay_is_idempotent",Boolean.TRUE.equals(removal.remove(actor,removeId,1,str(unassigned.get("etag")),request).get("removed")));check("removed_intake_hidden_from_task_center",num(queue.list(actor,new HashMap<>()).get("total"))==1);
   return null;
  }catch(Exception e){throw new RuntimeException(e);}finally{tx.setRollbackOnly();}});
  check("all_database_fixtures_rolled_back",db.count("SELECT COUNT(*) FROM a_health_reports WHERE scope_code=?",actor.scope)==0);try(java.util.stream.Stream<Path> paths=Files.list(Paths.get(ehr.storage))){check("staged_source_files_cleaned_after_rollback",paths.count()==0);}Files.delete(Paths.get(ehr.storage));System.out.println("PASS report intake checks: "+checks);
 }
}
