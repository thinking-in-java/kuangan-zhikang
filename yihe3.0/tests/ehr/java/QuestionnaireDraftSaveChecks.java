package com.yihe.framework.ehr;

import java.util.*;
import java.nio.file.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Runs against the configured database inside one rollback-only transaction. */
public class QuestionnaireDraftSaveChecks {
 static void check(boolean condition,String message){if(!condition)throw new AssertionError(message);}
 @SuppressWarnings("unchecked") public static void main(String[] args)throws Exception{
  Map<String,Object> c=new ObjectMapper().readValue(Files.readAllBytes(Paths.get(args[0])),Map.class);
  DriverManagerDataSource ds=new DriverManagerDataSource(str(c.get("url")),str(c.get("username")),str(c.get("password")));
  EhrRepository db=new EhrRepository(ds);EhrService ehr=new EhrService(db);QuestionnaireWorkflow service=new QuestionnaireWorkflow(db,ehr,new QuestionnaireService(db,ehr));
  new TransactionTemplate(new DataSourceTransactionManager(ds)).execute(tx->{try{
   Map<String,Object> original=db.one("SELECT * FROM a_questionnaires WHERE questionnaire_id=?","015ffce8-eb1d-5cb1-ab76-6ddae6588dbf");
   EhrActor a=new EhrActor(str(original.get("updated_by")),str(original.get("scope_code")).substring(4),str(original.get("data_mode")),true,false);
   String catalog=str(service.state(a,false).get("document_json"));long revision=num(service.state(a,false).get("row_version"));
   Map<String,Object> doc=(Map<String,Object>)ehr.decode(original.get("document_json"));Map<String,Object> plan=new LinkedHashMap<>((Map<String,Object>)doc.get("centerConfig"));
   String pid=UUID.randomUUID().toString();plan.put("id",pid);plan.put("name","Draft save rollback verification");plan.remove("version");
   Map<String,Object> body=map("version",revision,"requestKey",UUID.randomUUID().toString(),"plans",Arrays.asList(plan));
   check(ehr.encode(body).length()<20000,"Request must not include question bank");
   Object result=service.savePlans(a,body);Map<String,Object> saved=db.one("SELECT * FROM a_questionnaires WHERE questionnaire_id=?",pid);
   check(saved!=null&&"draft".equals(saved.get("questionnaire_status")),"Created draft");
   Map<String,Object> frozen=(Map<String,Object>)ehr.decode(saved.get("document_json"));check(((List<?>)frozen.get("questions")).size()==16,"Server builds complete PHQ-9 and GAD-7 questions");
   check(catalog.equals(str(service.state(a,false).get("document_json"))),"Question bank unchanged");
   check(ehr.encode(result).equals(ehr.encode(service.savePlans(a,body))),"Idempotent replay");
   check(num(service.state(a,false).get("row_version"))==revision+1,"Replay must not increment revision");
   plan.put("version",saved.get("row_version"));plan.put("name","Edited rollback verification");
   service.savePlans(a,map("version",revision+1,"requestKey",UUID.randomUUID().toString(),"plans",Arrays.asList(plan)));
   check("Edited rollback verification".equals(db.one("SELECT title FROM a_questionnaires WHERE questionnaire_id=?",pid).get("title")),"Edit saved");
   boolean stale=false;try{service.savePlans(a,map("version",revision+2,"requestKey",UUID.randomUUID().toString(),"plans",Arrays.asList(plan)));}catch(EhrException expected){stale=true;}check(stale,"Reject stale plan version");
   check(catalog.equals(str(service.state(a,false).get("document_json"))),"Catalog unchanged after edit");
   check(db.count("SELECT COUNT(*) FROM a_questionnaire_releases WHERE questionnaire_id=?",pid)==0,"No publication or assignments");
   // Deleting a published/archived plan stops pending assignments, but preserves responses.
   String release=UUID.randomUUID().toString(),assignment=UUID.randomUUID().toString(),response=UUID.randomUUID().toString();
   String record=str(db.one("SELECT r.record_id"+ehr.baseSql()+" LIMIT 1",a.scope,a.mode).get("record_id"));
   db.insert("a_questionnaire_releases",map("release_id",release,"scope_code",a.scope,"data_mode",a.mode,"questionnaire_id",pid,"revision",1,"document_json",saved.get("document_json"),"release_status","active","published_by",a.user,"published_at",now()));
   db.insert("a_questionnaire_assignments",map("assignment_id",assignment,"release_id",release,"record_id",record,"scope_code",a.scope,"data_mode",a.mode,"assigned_by",a.user,"assigned_at",now()));
   db.insert("a_questionnaire_responses",map("response_id",response,"assignment_id",assignment,"scope_code",a.scope,"data_mode",a.mode,"answers_json","{}","total_score",0,"submitted_by",a.user,"submission_channel","proxy","source_description","Rollback verification","submitted_at",now()));
   db.exec("UPDATE a_questionnaires SET questionnaire_status='active' WHERE questionnaire_id=?",pid);
   QuestionnaireService surveys=new QuestionnaireService(db,ehr);Object version=db.one("SELECT row_version FROM a_questionnaires WHERE questionnaire_id=?",pid).get("row_version");
   boolean unconfirmed=false;try{surveys.action(a,pid,map("requestKey",UUID.randomUUID().toString(),"action","delete","version",version));}catch(EhrException expected){unconfirmed=true;}check(unconfirmed,"Published deletion requires explicit confirmation");
   Map<String,Object> deletion=map("requestKey",UUID.randomUUID().toString(),"action","delete","version",version,"confirmDelete",true);
   Object deleted=surveys.action(a,pid,deletion);check(ehr.encode(deleted).equals(ehr.encode(surveys.action(a,pid,deletion))),"Deletion replay is idempotent");
   check("deleted".equals(db.one("SELECT questionnaire_status FROM a_questionnaires WHERE questionnaire_id=?",pid).get("questionnaire_status")),"Soft delete plan");
   check("archived".equals(db.one("SELECT release_status FROM a_questionnaire_releases WHERE release_id=?",release).get("release_status")),"Close released forms");
   check(db.count("SELECT COUNT(*) FROM a_questionnaire_responses WHERE response_id=?",response)==1,"Preserve historical response");
   check(db.count("SELECT COUNT(*) FROM a_questionnaire_assignments WHERE assignment_id=?",assignment)==1,"Preserve assignment audit trail");
   boolean hidden=false;try{surveys.owned(a,pid,false);}catch(EhrException expected){hidden=true;}check(hidden,"Deleted plan absent from management");
   check(catalog.equals(str(service.state(a,false).get("document_json"))),"Deletion does not change catalog");
   System.out.println("PASS draft save and published deletion: confirmation, idempotency, hidden plan, stopped release, preserved answer and assignment; transaction rolled back");
  }finally{tx.setRollbackOnly();}return null;});
 }
}
