package com.yihe.framework.ehr;

import java.util.*;
import java.nio.file.*;
import java.io.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Actual database, synthetic isolated organisation; every write rolls back. No external model requests. */
public class CommunicationChecks {
    static int checks;
    static void check(String name,boolean ok){if(!ok)throw new AssertionError(name);checks++;System.out.println("PASS "+name);}
    static void reject(String name,int code,Runnable r){try{r.run();throw new AssertionError(name);}catch(EhrException e){check(name,e.status==code);}}
    static class Client extends HealthAgentClient {
        int calls;String payload;Client(){super(new HealthAgentSettings(new ModelRecognitionSettings()));}
        public Map<String,Object> respond(List<Object> input,List<Object> tools,String rules,long deadline){calls++;payload=input.toString();check("draft_has_no_business_tools",tools.isEmpty());return map("output",Arrays.asList(map("type","message","content",Arrays.asList(map("type","output_text","text","这是合成测试回复，请人工核对。")))));}
    }
    static class Upload implements MultipartFile {
        final byte[] data;final String name;
        Upload(String name,String content){this.name=name;this.data=bytes(content);}
        public String getName(){return "file";}public String getOriginalFilename(){return name;}public String getContentType(){return "application/octet-stream";}
        public boolean isEmpty(){return data.length==0;}public long getSize(){return data.length;}public byte[] getBytes(){return data;}public InputStream getInputStream(){return new ByteArrayInputStream(data);}
        public void transferTo(File f)throws IOException{Files.write(f.toPath(),data);}
    }
    static Map<String,Object> send(String content,String key){return map("text",content,"requestId",key);}
    static Map<String,Object> action(CommunicationService s,EhrActor a,String cid,String name){return s.action(a,cid,map("action",name,"version",s.view(a,s.owned(a,cid,false)).get("version")));}
    @SuppressWarnings("unchecked") public static void main(String[] args)throws Exception {
        ((ch.qos.logback.classic.Logger)org.slf4j.LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME)).setLevel(ch.qos.logback.classic.Level.WARN);
        DriverManagerDataSource ds=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));
        EhrRepository db=new EhrRepository(ds);EhrService ehr=new EhrService(db);CommunicationService s=new CommunicationService(db,ehr);DataSourceTransactionManager tm=new DataSourceTransactionManager(ds);
        String run=id().substring(0,8),scope="COMM_"+run;EhrActor doctor=new EhrActor("comm-doctor",scope,"synthetic",true,false),manager=new EhrActor("comm-manager",scope,"synthetic",false,false),cross=new EhrActor(doctor.user,"OTHER_"+run,"synthetic",true,false),real=new EhrActor(doctor.user,scope,"real",true,false);
        Path folder=Files.createTempDirectory("comm-check-");CommunicationFiles files=new CommunicationFiles(s,db);files.storage=folder.toString();Client client=new Client();CommunicationAi ai=new CommunicationAi(s,db,ehr,client,tm);final String[] fileId={null};
        try{new TransactionTemplate(tm).execute(tx->{try{
            Map<String,Object> p=ehr.upsert(doctor,null,map("name","会话隔离测试","code","COMM_"+run,"sex","未填写","org","隔离测试单位","team","测试班组","job","测试岗位"));String record=str(p.get("id"));
            Map<String,Object> p2=ehr.upsert(doctor,null,map("name","另一个隔离职工","code","OTHER_"+run,"sex","未填写","org","隔离测试单位"));String otherRecord=str(p2.get("id"));
            EhrActor employee=new EhrActor("wx:comm-test",scope,"synthetic",false,true,record),otherEmployee=new EhrActor("wx:other",scope,"synthetic",false,true,otherRecord);
            String cid=str(s.create(employee,record).get("id"));check("staff_and_employee_share_one_conversation",cid.equals(s.create(doctor,record).get("id")));
            reject("cross_unit_denied",404,()->s.messages(cross,cid,0,0));reject("cross_mode_denied",404,()->s.messages(real,cid,0,0));reject("other_employee_denied",404,()->s.messages(otherEmployee,cid,0,0));reject("employee_inbox_denied",403,()->s.list(employee,new HashMap<>()));
            reject("staff_must_takeover",409,()->s.send(doctor,cid,send("hello","before_takeover")));
            Map<String,Object> em=s.send(employee,cid,send("会话隔离测试 工号COMM_"+run+"，请问报告在哪里查看？手机13812345678","employee_first"));
            check("employee_role_server_derived",em.get("role").equals("employee"));check("unread_and_pending_real_counters",num(s.counts(doctor).get("unread"))==1&&num(s.counts(doctor).get("pending"))==1);
            s.read(doctor,cid,num(em.get("seq")));s.read(doctor,cid,0);check("read_cursor_never_rewinds",num(s.view(doctor,s.owned(doctor,cid,false)).get("unread"))==0);check("other_staff_unread_independent",num(s.view(manager,s.owned(manager,cid,false)).get("unread"))==1);
            reject("read_future_denied",422,()->s.read(doctor,cid,999));action(s,doctor,cid,"takeover");reject("takeover_conflict_denied",409,()->action(s,manager,cid,"takeover"));reject("employee_takeover_denied",403,()->action(s,employee,cid,"takeover"));
            Map<String,Object> answer=s.send(doctor,cid,map("text","已收到，正在核对。","requestId","staff_reply_1","sender_role","employee"));check("doctor_role_not_spoofable",answer.get("role").equals("doctor"));check("reply_clears_pending",num(s.counts(doctor).get("pending"))==0);
            check("retry_is_idempotent",answer.get("id").equals(s.send(doctor,cid,send("已收到，正在核对。","staff_reply_1")).get("id")));reject("same_key_different_text_denied",409,()->s.send(doctor,cid,send("不同文本","staff_reply_1")));
            Map<String,Object> health=s.context(doctor,cid);check("real_profile_missing_data_not_fabricated",((Map<?,?>)health.get("profile")).get("code").equals(p.get("code"))&&health.get("latestReport")==null&&((List<?>)health.get("assessments")).isEmpty());
            Map<String,Object> d=ai.draft(doctor,cid,map("purpose","general"));check("draft_does_not_send",db.count("SELECT COUNT(*) FROM a_service_messages WHERE conversation_id=? AND sender_role='ai_assisted'",cid)==0);check("basic_identity_redacted",!client.payload.contains("13812345678")&&!client.payload.contains("COMM_"+run)&&!client.payload.contains("会话隔离测试"));
            s.send(employee,cid,send("还有其他问题","employee_second"));reject("stale_draft_cannot_send",409,()->s.send(doctor,cid,map("text",d.get("text"),"requestId","draft_stale","draftId",d.get("id"))));
            Map<String,Object> fresh=ai.draft(doctor,cid,map("purpose","report"));s.send(doctor,cid,map("text",fresh.get("text"),"requestId","draft_fresh","draftId",fresh.get("id")));check("human_confirmed_ai_reply_label",db.count("SELECT COUNT(*) FROM a_service_messages WHERE conversation_id=? AND sender_role='ai_assisted'",cid)==1);
            Map<String,Object> f=files.upload(doctor,cid,new Upload("记录.txt","合成附件内容"));fileId[0]=str(f.get("id"));check("private_file_written",Files.exists(files.path(fileId[0])));
            reject("unsent_file_private",404,()->files.metadata(employee,cid,fileId[0]));
            s.send(doctor,cid,map("text","请查看附件","requestId","attachment_send","attachmentIds",Arrays.asList(fileId[0])));check("sent_file_visible_to_employee",files.metadata(employee,cid,fileId[0])!=null);check("download_matches_original",new String(Files.readAllBytes(files.download(employee,cid,fileId[0])),java.nio.charset.StandardCharsets.UTF_8).equals("合成附件内容"));
            reject("cannot_reuse_attachment",422,()->s.send(doctor,cid,map("text","test","requestId","attachment_reuse","attachmentIds",Arrays.asList(fileId[0]))));
            try{files.remove(doctor,cid,fileId[0]);throw new AssertionError("sent file removed");}catch(EhrException e){check("sent_file_cannot_remove",e.status==409);}
            try{files.upload(doctor,cid,new Upload("fake.png","not a png"));throw new AssertionError("type spoof accepted");}catch(EhrException e){check("mime_spoof_denied",e.status==422);}
            action(s,doctor,cid,"release");action(s,manager,cid,"takeover");Map<String,Object> m=s.send(manager,cid,send("管理员回复","manager_reply"));check("manager_reply_supported",m.get("role").equals("manager"));
            action(s,manager,cid,"close");check("closed_filter",num(s.list(doctor,new HashMap<String,String>(){{put("filter","closed");}}).get("total"))==1);reject("closed_cannot_staff_send",409,()->s.send(manager,cid,send("test","closed_send")));
            org.springframework.aop.framework.ProxyFactory pf=new org.springframework.aop.framework.ProxyFactory(s);pf.setProxyTargetClass(true);pf.addAdvice(new org.springframework.transaction.interceptor.TransactionInterceptor(tm,new org.springframework.transaction.annotation.AnnotationTransactionAttributeSource()));CommunicationService proxy=(CommunicationService)pf.getProxy();CommunicationHistory history=new CommunicationHistory(proxy,db,ehr);
            reject("staff_cannot_forge_satisfaction",403,()->history.rate(doctor,cid,5));reject("rating_upper_bound",422,()->history.rate(employee,cid,6));reject("rating_lower_bound",422,()->history.rate(employee,cid,0));history.rate(employee,cid,4);reject("rating_cannot_repeat",409,()->history.rate(employee,cid,5));
            history.category(doctor,cid,"report");Map<String,String> filter=new HashMap<>();filter.put("category","report");
            check("history_category_filter",num(history.list(doctor,filter).get("total"))==1);filter.put("category","review");check("history_category_excludes_other_types",num(history.list(doctor,filter).get("total"))==0);filter.clear();
            Map<String,Object> stats=history.statistics(doctor,filter);check("history_counts_actual_messages",num(stats.get("conversations"))==1&&num(stats.get("aiReplies"))==1&&num(stats.get("humanReplies"))==3&&num(stats.get("transferred"))==1);check("history_actual_satisfaction",num(stats.get("ratedConversations"))==1&&((Number)stats.get("satisfaction")).doubleValue()==100.0);check("history_response_time_has_samples",num(stats.get("responseSamples"))>=1&&stats.get("averageSeconds")!=null);
            db.exec("UPDATE a_service_messages SET created_at='2026-11-25 01:00:00' WHERE conversation_id=?",cid);db.exec("UPDATE a_service_messages SET created_at='2026-11-24 15:59:59' WHERE message_id=?",em.get("id"));filter.put("start","2026-11-24");filter.put("end","2026-11-24");Map<String,Object> oneDay=history.list(doctor,filter);check("history_beijing_day_and_preview",num(oneDay.get("total"))==1&&((Map<?,?>)((List<?>)oneDay.get("items")).get(0)).get("preview").equals(em.get("text")));filter.put("source","ai");check("history_source_respects_date_window",num(history.list(doctor,filter).get("total"))==0);filter.clear();
            reject("history_unit_isolated",404,()->history.detail(cross,cid));reject("history_mobile_denied",403,()->history.detail(employee,cid));check("history_other_unit_empty",num(history.list(cross,filter).get("total"))==0);
            filter.put("start","2099-01-01");check("history_date_filters_messages",num(history.list(doctor,filter).get("total"))==0);filter.put("start","2026-99-99");reject("history_invalid_date_rejected",422,()->history.list(doctor,filter));filter.clear();filter.put("source","ai");check("history_ai_source_filter",num(history.list(doctor,filter).get("total"))==1);filter.clear();
            Map<String,Object> detail=history.detail(doctor,cid);check("history_details_participants_and_category",((List<?>)detail.get("participants")).size()>=3&&detail.get("category").equals("report"));check("history_no_fabricated_plans",((List<?>)history.plans(doctor,cid).get("items")).isEmpty());
            Map<String,Object> aiSummary=ai.summarize(doctor,cid);check("history_ai_summary_readonly",str(aiSummary.get("text")).contains("合成测试回复")&&num(history.statistics(doctor,filter).get("aiReplies"))==1);
            String csv=new String(history.export(doctor,filter),java.nio.charset.StandardCharsets.UTF_8);check("history_csv_has_bom_and_text",csv.startsWith("\uFEFF")&&csv.contains("记录.txt")&&csv.contains("AI辅助人工回复"));check("csv_formula_injection_defended",CommunicationHistory.csv("=1+1").startsWith("\"'="));
            s.send(employee,cid,send("再次咨询","employee_reopen"));check("employee_reopens_closed_service",s.view(doctor,s.owned(doctor,cid,false)).get("status").equals("open"));
            for(int i=0;i<52;i++)s.send(employee,cid,send("分页消息"+i,"pagination_"+i));List<Map<String,Object>> latest=(List<Map<String,Object>>)s.messages(doctor,cid,0,0).get("items");check("message_window_bounded",latest.size()==50);Map<String,Object> older=s.messages(doctor,cid,num(latest.get(0).get("seq")),0);check("history_cursor_has_no_overlap",!((List<?>)older.get("items")).isEmpty()&&num(((Map<?,?>)((List<?>)older.get("items")).get(((List<?>)older.get("items")).size()-1)).get("seq"))<num(latest.get(0).get("seq")));
            Map<String,String> search=new HashMap<>();search.put("search","再次咨询");check("searches_message_keyword",num(s.list(doctor,search).get("total"))==1);
            db.exec("UPDATE a_employee_roster_profiles SET deleted_at=UTC_TIMESTAMP(6) WHERE employee_id=?",p.get("employee_id"));reject("deleted_employee_access_revoked",404,()->s.messages(employee,cid,0,0));check("deleted_employee_hidden_from_inbox",num(s.counts(doctor).get("total"))==0);
            return null;
        }catch(IOException e){throw new RuntimeException(e);}finally{tx.setRollbackOnly();}});
            check("all_database_fixtures_rolled_back",db.count("SELECT COUNT(*) FROM a_service_conversations WHERE scope_code=?","org:"+scope)==0&&db.count("SELECT COUNT(*) FROM a_employee_roster_profiles WHERE scope_code=?","org:"+scope)==0);
            check("rollback_removes_private_attachment",fileId[0]!=null&&!Files.exists(files.path(fileId[0])));
            System.out.println("PASS Communication checks: "+checks+"; database fixtures rolled back; no external model request");
        }finally{Files.deleteIfExists(folder.resolve("communication"));Files.deleteIfExists(folder);}
    }
}
