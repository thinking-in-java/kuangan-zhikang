package com.yihe.framework.ehr;

import java.util.*;
import java.nio.file.*;
import java.time.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Explicit opt-in live-model test. All business writes roll back; no real employee is used. */
@SuppressWarnings("unchecked")
public class PsychologyEndToEndChecks {
 static final Map<String,Object> evidence=new LinkedHashMap<>();
 static void check(boolean value,String label){if(!value)throw new AssertionError(label);System.out.println("PASS "+label);}
 public static void main(String[] args)throws Exception {
  ObjectMapper json=new ObjectMapper();Map<String,Object> config=json.readValue(Files.readAllBytes(Paths.get(args[0])),Map.class);
  DriverManagerDataSource ds=new DriverManagerDataSource(str(config.get("url")),str(config.get("username")),str(config.get("password")));
  EhrRepository db=new EhrRepository(ds);EhrService ehr=new EhrService(db);QuestionnaireService qs=new QuestionnaireService(db,ehr);
  QuestionnaireWorkflow wf=new QuestionnaireWorkflow(db,ehr,qs);WorkerCollaborationService orders=new WorkerCollaborationService(db,ehr,qs);
  DigitalWorkerModelClient model=new DigitalWorkerModelClient();java.lang.reflect.Field field=DigitalWorkerModelClient.class.getDeclaredField("configFile");field.setAccessible(true);field.set(model,args[1]);
  check(Boolean.TRUE.equals(model.status().get("configured")),"Existing AI configuration available");
  PsychologyCoordinator psych=new PsychologyCoordinator(db,ehr,wf,qs,orders,model);
  DataSourceTransactionManager manager=new DataSourceTransactionManager(ds);
  String marker="psych-e2e-"+id();evidence.put("testScope",marker);evidence.put("dataMode","synthetic");evidence.put("clinicalReview",false);evidence.put("reviewDescription","仅验证审核权限与状态流转，合成答卷不构成真实医生诊断");
  try {
   new TransactionTemplate(manager).execute(tx->{try {
    Map<String,Object> doctor=db.one("SELECT user_code FROM yh_user_reginfo WHERE User_Type=4 AND lock_flag=2 LIMIT 1");
    EhrActor a=new EhrActor(str(doctor.get("user_code")),marker,"synthetic",true,false);
    String employeeCode="TEST-"+id().substring(0,8);
    String record=str(ehr.upsert(a,null,map("name","心理流程测试职工（合成）","code",employeeCode,"sex","男","birth","1990-01-01","org","隔离联调单位","team","测试班组","job","测试岗位","exposure","合成数据，不对应真实人员")).get("id"));
    check(!record.isEmpty(),"Created isolated synthetic employee");evidence.put("recordId",record);
    wf.bootstrap(a);String plan=id();String start=LocalDateTime.now(ZoneId.of("Asia/Shanghai")).minusMinutes(2).withNano(0).toString(),end=LocalDateTime.now(ZoneId.of("Asia/Shanghai")).plusDays(1).withNano(0).toString();
    Map<String,Object> p=map("id",plan,"name","心理小谷完整流程测试（合成答卷）","scales",Arrays.asList("PHQ-9","GAD-7"),"scale","PHQ-9、GAD-7","audience","隔离测试职工","time",start,"endTime",end,"aiEnabled",true,"wizard",map("settings",map("aiReport",true,"alerts",true,"intervention",false),"release",map("range","custom","channels",Arrays.asList("system"),"time",start,"endTime",end,"reminder",false,"access",Arrays.asList("employee","doctor"),"openAfter","review")));
    wf.savePlans(a,map("requestKey",id(),"version",wf.state(a,false).get("row_version"),"plans",Arrays.asList(p)));
    wf.publish(a,plan,map("records",Arrays.asList(record),"requestKey",id()));
    String assignment=str(db.one("SELECT x.assignment_id id FROM a_questionnaire_assignments x JOIN a_questionnaire_releases l ON l.release_id=x.release_id WHERE l.questionnaire_id=?",plan).get("id"));
    check(db.count("SELECT COUNT(*) FROM a_questionnaire_deliveries WHERE assignment_id=? AND delivery_kind='issued'",assignment)==1,"Issued one assessment with in-app delivery");
    evidence.put("questionnaireId",plan);evidence.put("assignmentId",assignment);
    EhrActor self=new EhrActor("synthetic-self",marker,"synthetic",false,true,record);
    Map<String,Object> form=(Map<String,Object>)qs.answerForm(self,assignment),doc=(Map<String,Object>)((Map<?,?>)form.get("assignment")).get("document"),answers=new LinkedHashMap<>();
    List<Map<String,Object>> questions=QuestionnaireScoring.rows(doc.get("questions"));check(questions.size()==16,"Frozen PHQ-9 and GAD-7 contain 16 questions");
    int i=0;for(Map<String,Object> q:questions){answers.put(str(q.get("id")),i<5||i>=9&&i<12?"o1":"o0");i++;}
    Map<String,Object> request=map("answers",answers,"consent",true,"requestKey",id());String response=str(((Map<?,?>)qs.submit(self,assignment,request)).get("id"));evidence.put("responseId",response);
    check(str(((Map<?,?>)qs.submit(self,assignment,request)).get("id")).equals(response),"Duplicate answer submission is idempotent");
    Map<String,Object> job=db.one("SELECT * FROM a_questionnaire_processing WHERE response_id=?",response);
    check("queued".equals(job.get("ai_state")),"Submission enqueues AI analysis");evidence.put("scoring",ehr.decode(job.get("score_json")));
    QuestionnaireAnalysisWorker worker=new QuestionnaireAnalysisWorker(db,ehr,model,manager);
    try {
     String payload=ehr.encode(worker.input(job));check(!payload.contains(record)&&!payload.contains(employeeCode)&&!payload.contains("心理流程测试职工"),"AI payload excludes employee identity");
     job.put("claim_token",id());db.exec("UPDATE a_questionnaire_processing SET ai_state='running',claim_token=?,started_at=UTC_TIMESTAMP(6),attempts=attempts+1 WHERE response_id=?",job.get("claim_token"),response);
     long before=System.currentTimeMillis();worker.run(job);evidence.put("aiElapsedSeconds",(System.currentTimeMillis()-before)/1000.0);
    }finally{worker.stop();}
    Map<String,Object> processed=db.one("SELECT ai_state,ai_output,ai_error FROM a_questionnaire_processing WHERE response_id=?",response);evidence.put("ai",processed);
    boolean modelPassed="completed".equals(processed.get("ai_state"));evidence.put("modelPassed",modelPassed);
    if(modelPassed)check(true,"Real configured model returned persisted analysis");
    else {check("failed".equals(processed.get("ai_state"))&&processed.get("ai_output")==null,"AI failure is recorded without fabricated analysis");System.out.println("UPSTREAM BLOCKED: "+processed.get("ai_error")+"; continuing scoring and review workflow only");}
    String item=PsychologyCoordinator.stable(a.scope+":"+a.mode+":response:"+response);evidence.put("psychItemId",item);
    check(((Map<?,?>)qs.answerForm(self,assignment)).get("result")==null,"Unreviewed results are withheld from employee");
    try{psych.handoff(a,item,map("requestKey",id(),"version",psych.item(a,item,false).get("row_version"),"professionalConfirmed",true,"concerns","测试审核门禁","dueAt",end));throw new AssertionError("Unreviewed transfer allowed");}catch(EhrException expected){check(expected.getMessage().contains("审核"),"Unreviewed handoff is rejected");}
    psych.act(a,item,map("requestKey",id(),"version",psych.item(a,item,false).get("row_version"),"action","review","decision","confirmed","riskLevel","low","sourceVerified",true,"note","隔离测试审核：核对16个合成选项及分量表评分；仅验证流程，不作临床结论"));
    check(((Map<?,?>)qs.answerForm(self,assignment)).get("result")!=null,"Reviewed result becomes available to synthetic employee");
    Map<String,Object> handoff=(Map<String,Object>)psych.handoff(a,item,map("requestKey",id(),"version",psych.item(a,item,false).get("row_version"),"professionalConfirmed",true,"concerns","合成流程测试：请方案小谷接收评估资料并提交测试处理意见，不发送通知或执行随访","dueAt",end));evidence.put("handoff",handoff);String order=str(handoff.get("orderId"));
    check("queued".equals(orders.owned(a,order,false).get("order_state")),"Plan work order enters queue");
    for(String action:Arrays.asList("claim","start","submit","accept")){
     Map<String,Object> current=orders.owned(a,order,false);orders.act(a,order,map("requestKey",id(),"version",current.get("row_version"),"actingWorker",action.equals("accept")?"care":"plan","action",action,"note","隔离测试："+action,"professionalConfirmed",true,"result",map("testOnly",true,"summary","已接收合成评估并核对来源，测试流转完成，不代表已生成正式干预方案")));
    }
    psych.feedback(a,str(handoff.get("id")),map("requestKey",id(),"version",1,"actingWorker","plan","state","completed","note","隔离测试：工单已提交、验收；无真实员工服务执行"));
    Map<String,Object> detail=(Map<String,Object>)orders.detail(a,order);evidence.put("order",detail);
    check("completed".equals(orders.owned(a,order,false).get("order_state")),"Plan claim, start, submit and sender acceptance complete");
    check(db.count("SELECT COUNT(*) FROM a_worker_orders WHERE parent_id=? AND target_worker='follow'",order)==0,"Psychology does not create followup execution tasks");
    evidence.put("businessWorkflowPassed",true);evidence.put("success",modelPassed);
   }finally{tx.setRollbackOnly();}return null;});
  }catch(Throwable error){evidence.put("success",false);evidence.put("error",error.getClass().getSimpleName()+": "+error.getMessage());throw error;
  }finally{
   evidence.put("rolledBack",db.count("SELECT COUNT(*) FROM a_employees WHERE scope_code=?","org:"+marker)==0);
   Files.write(Paths.get(args[2]),json.writerWithDefaultPrettyPrinter().writeValueAsBytes(evidence));
   System.out.println("Evidence saved; business data rolled back: "+evidence.get("rolledBack"));
  }
 }
}
