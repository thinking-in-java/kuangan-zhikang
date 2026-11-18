package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Actual MySQL, isolated scope, all fixtures rolled back; deterministic model transport. */
public class HealthAgentChecks {
    static int checks;static void check(String label,boolean b){if(!b)throw new AssertionError(label);checks++;System.out.println("PASS "+label);}
    static void reject(String label,int status,Runnable r){try{r.run();throw new AssertionError(label);}catch(EhrException e){check(label,e.status==status);}}
    static class Settings extends HealthAgentSettings{Settings(){super(new ModelRecognitionSettings());}Map<String,Object> validated(){return map("enabled",true,"apiKey","synthetic","baseUrl","https://example.test/v1","model","synthetic");}}
    static class Client extends HealthAgentClient{
        int calls=0;Client(){super(new Settings());}
        public Map<String,Object> respond(List<Object> input,List<Object> tools,String rules,long deadline){calls++;if(calls==1)return map("output",Arrays.asList(map("type","function_call","name","search_employees","call_id","probe_call","arguments","{\"keyword\":\"AGENT_\",\"page\":1}")));
            check("function_call_output_round_trip",input.stream().anyMatch(x->x instanceof Map&&"function_call_output".equals(((Map<?,?>)x).get("type"))&&"probe_call".equals(((Map<?,?>)x).get("call_id"))));return map("output",Arrays.asList(map("type","message","content",Arrays.asList(map("type","output_text","text","已读取隔离测试职工档案。")))));}
    }
    static Map<String,Object> fixture(EhrService ehr,EhrActor a,String code){return ehr.upsert(a,null,map("name","智能体隔离测试","code",code,"sex","未填写","org","隔离测试单位","team","测试班组","job","测试岗位"));}
    static String report(EhrRepository db,EhrService ehr,EhrActor a,String record){String report=id(),source=id();db.insert("a_report_sources",ehr.scoped(a,"source_file_id",source,"sha256",digest(bytes(source)),"object_ref",bytes("agent-fixture"),"page_count",1));db.insert("a_report_series",ehr.scoped(a,"report_id",report,"source_file_id",source));db.insert("a_health_reports",ehr.scoped(a,"report_id",report,"revision",1,"record_id",record,"report_kind","individual","field_values","[]","dictionary_version","2.0.0","review_status","unreviewed"));db.insert("a_report_revision_sources",ehr.scoped(a,"report_id",report,"revision",1,"source_file_id",source));db.exec("UPDATE a_report_series SET current_revision=1 WHERE report_id=?",report);
        String evidence=ehr.evidence(a,report,1,source,1,"测试血糖","测试血糖 8.2 mmol/L H");db.insert("a_health_items",map("report_id",report,"revision",1,"item_id",id(),"ordinal",1,"primary_evidence_id",evidence,"evidence_ids",ehr.encode(Arrays.asList(evidence)),"original_field_name","测试血糖","result_raw","8.2","unit_raw","mmol/L","reference_range_raw","3.9-6.1","abnormal_flag_raw","H","field_values","[]","mapping_status","unmapped","review_status","needs_review"));return report;}
    @SuppressWarnings("unchecked") public static void main(String[] args){DriverManagerDataSource ds=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));EhrRepository db=new EhrRepository(ds);EhrService ehr=new EhrService(db);ehr.reportDeletion=new ReportDeletionService(db);HealthAgentStore store=new HealthAgentStore(db,ehr);HealthAgentTools tools=new HealthAgentTools(db,ehr,store);String run=id().substring(0,8);EhrActor a=new EhrActor("agent-test-user","AGENT_"+run,"synthetic",true,false),other=new EhrActor("other-user","AGENT_"+run,"synthetic",true,false),cross=new EhrActor("agent-test-user","OTHER_"+run,"synthetic",true,false),read=new EhrActor(a.user,a.scope.substring(4),a.mode,false,false);
        new TransactionTemplate(new DataSourceTransactionManager(ds)).execute(status->{try{
            Map<String,Object> p=fixture(ehr,a,"AGENT_"+run);String record=str(p.get("id")),cid=str(store.create(a).get("id"));
            check("tool_catalog_has_eight_strict_tools",tools.catalog(true).size()==8);
            reject("unknown_tool_denied",403,()->tools.validate("execute_sql",map(),true));reject("extra_arguments_denied",422,()->tools.validate("search_employees",map("keyword","","page",1,"sql","DROP"),true));reject("page_bounds_enforced",422,()->tools.validate("search_employees",map("keyword","","page",0),true));reject("read_account_has_no_write_tools",403,()->tools.validate("prepare_employee_delete",map("record_id",record,"reason","test"),false));reject("conversation_owner_isolated",404,()->store.detail(other,cid));reject("conversation_scope_isolated",404,()->store.detail(cross,cid));reject("employee_scope_isolated",404,()->tools.execute(cross,cid,id(),"","","get_employee_record",map("record_id",record),new ArrayList<>()));
            String report=report(db,ehr,a,record);Map<String,Object> b=map("team","","indicator","","reviewed_only",false,"page",1),ab=(Map<String,Object>)tools.abnormal(a,b);check("abnormal_result_actual_sql",num(ab.get("totalResults"))==1&&num(ab.get("affectedEmployees"))==1);
            b.put("reviewed_only",true);check("unreviewed_not_counted_as_confirmed",num(((Map<?,?>)tools.abnormal(a,b)).get("totalResults"))==0);
            Map<String,Object> reportData=(Map<String,Object>)tools.execute(a,cid,id(),"","","get_exam_report",map("report_id",report,"revision",1,"page",1),new ArrayList<>());check("report_values_and_evidence_retained",((List<?>)reportData.get("items")).size()==1&&"unreviewed".equals(reportData.get("reviewStatus")));
            Client client=new Client();HealthAgentService service=new HealthAgentService(store,tools,client,ehr);Map<String,Object> request=map("conversationId",cid,"requestId","request_test_1","message","查询测试职工","recordId","");Map<String,Object> reply=service.chat(a,request);check("tool_loop_saved",str(reply.get("text")).contains("隔离测试")&&client.calls==2);service.chat(a,request);check("same_request_id_idempotent",client.calls==2);request.put("message","different");reject("idempotency_payload_change_rejected",409,()->service.chat(a,request));
            String tid=str(reply.get("turnId"));reject("write_intent_required",422,()->store.prepare(a,cid,tid,record,"delete_employee",map("reason","test"),"查看档案",record));reject("read_account_cannot_prepare",403,()->store.prepare(read,cid,tid,record,"delete_employee",map("reason","test"),"删除档案",record));
            Map<String,Object> action=store.prepare(a,cid,tid,record,"delete_employee",map("reason","隔离测试"),"删除档案",record);String aid=str(action.get("id"));check("preview_does_not_delete",ehr.profile(a,record,false)!=null&&num(((Map<?,?>)action.get("target")).get("reportCount"))==1);
            reject("wrong_confirmation_code_denied",422,()->store.resolve(a,aid,map("confirm",true,"employeeCode","wrong")));reject("other_owner_cannot_confirm",404,()->store.resolve(other,aid,map("confirm",true,"employeeCode",p.get("code"))));reject("read_account_cannot_confirm",403,()->store.resolve(read,aid,map("confirm",true,"employeeCode",p.get("code"))));
            db.exec("UPDATE a_employee_roster_profiles SET row_version=row_version+1 WHERE employee_id=?",p.get("employee_id"));reject("stale_snapshot_denied",409,()->store.resolve(a,aid,map("confirm",true,"employeeCode",p.get("code"))));db.exec("UPDATE a_employee_roster_profiles SET row_version=row_version-1 WHERE employee_id=?",p.get("employee_id"));
            store.resolve(a,aid,map("confirm",false));reject("cancelled_action_cannot_execute",409,()->store.resolve(a,aid,map("confirm",true,"employeeCode",p.get("code"))));
            String tid2=str(store.begin(a,cid,"action_test_2","删除档案",record).get("turnId"));Map<String,Object> next=store.prepare(a,cid,tid2,record,"delete_employee",map("reason","隔离测试"),"删除档案",record);String aid2=str(next.get("id"));db.exec("UPDATE a_agent_actions SET expires_at=DATE_SUB(UTC_TIMESTAMP(6),INTERVAL 1 SECOND) WHERE action_id=?",aid2);reject("expired_confirmation_denied",409,()->store.resolve(a,aid2,map("confirm",true,"employeeCode",p.get("code"))));db.exec("UPDATE a_agent_actions SET expires_at=DATE_ADD(UTC_TIMESTAMP(6),INTERVAL 10 MINUTE) WHERE action_id=?",aid2);
            Map<String,Object> done=store.resolve(a,aid2,map("confirm",true,"employeeCode",p.get("code")));check("confirmed_delete_executes",done.get("status").equals("confirmed")&&db.count("SELECT COUNT(*) FROM a_health_reports WHERE report_id=?",report)==0);check("confirmed_delete_idempotent",store.resolve(a,aid2,map("confirm",true,"employeeCode",p.get("code"))).equals(done));
            check("delete_audited",db.count("SELECT COUNT(*) FROM a_health_archive_audit_logs WHERE record_id=? AND action='agent_action_confirmed'",record)==1);
            String dc=str(store.create(a).get("id"));
            String dt=str(store.begin(a,dc,"delete_fixture","删除测试对话",record).get("turnId"));
            reject("other_owner_cannot_delete_conversation",404,()->store.delete(other,dc));
            reject("other_scope_cannot_delete_conversation",404,()->store.delete(cross,dc));
            reject("running_conversation_cannot_delete",409,()->store.delete(a,dc));
            store.toolLog(dt,"search_employees",map("keyword","test"),"ok",1);
            store.finish(dc,dt,map("text","test"),new ArrayList<>(),true);
            // A pending business preview must disappear with the conversation, without executing it.
            Map<String,Object> keep=fixture(ehr,a,"KEEP_"+run);String kr=str(keep.get("id"));
            String ka=str(store.prepare(a,dc,dt,kr,"delete_employee",map("reason","测试"),"删除档案",kr).get("id"));
            check("conversation_delete_receipt",Boolean.TRUE.equals(store.delete(a,dc).get("deleted")));
            check("conversation_children_removed",db.count("SELECT COUNT(*) FROM a_agent_turns WHERE conversation_id=?",dc)==0&&db.count("SELECT COUNT(*) FROM a_agent_actions WHERE conversation_id=?",dc)==0&&db.count("SELECT COUNT(*) FROM a_agent_tool_logs WHERE turn_id=?",dt)==0);
            reject("deleted_conversation_cannot_read",404,()->store.detail(a,dc));
            reject("deleted_conversation_cannot_send",404,()->store.begin(a,dc,"after_delete","test",""));
            reject("deleted_action_cannot_confirm",404,()->store.resolve(a,ka,map("confirm",true,"employeeCode",keep.get("code"))));
            check("conversation_delete_preserves_employee",ehr.profile(a,kr,false)!=null);
            return null;
        }finally{status.setRollbackOnly();}});
        check("fixtures_rolled_back",db.count("SELECT COUNT(*) FROM a_employee_roster_profiles WHERE scope_code=?",a.scope)==0&&db.count("SELECT COUNT(*) FROM a_agent_conversations WHERE scope_code=?",a.scope)==0);System.out.println("PASS Health agent checks: "+checks+"; all database writes rolled back");
    }
}
