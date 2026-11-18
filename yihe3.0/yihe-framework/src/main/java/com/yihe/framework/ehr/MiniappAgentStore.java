package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Personal ownership is derived from an active binding, never from request/model identity fields. */
@Service
public class MiniappAgentStore {
    final EhrRepository db;final EhrService ehr;
    @Value("${ehr.miniapp-agent.per-minute:6}") int perMinute=6;
    @Value("${ehr.miniapp-agent.per-day:100}") int perDay=100;
    @Value("${ehr.miniapp-agent.queue-limit:100}") int queueLimit=100;
    public MiniappAgentStore(EhrRepository db,EhrService ehr){this.db=db;this.ehr=ehr;}
    static final class Person {
        final String user,binding,record,scope,mode;final EhrActor actor;
        Person(Map<String,Object> r){user=str(r.get("mini_user_id"));binding=str(r.get("binding_id"));record=str(r.get("record_id"));scope=str(r.get("scope_code"));mode=str(r.get("data_mode"));actor=new EhrActor("wx:"+user,scope.substring(4),mode,false,true,record);}
    }
    Person person(String user,String mode){
        Map<String,Object> r=db.one("SELECT b.* FROM a_miniapp_employee_bindings b JOIN a_miniapp_users u ON u.mini_user_id=b.mini_user_id AND u.data_mode=b.data_mode JOIN a_health_records r ON r.record_id=b.record_id AND r.scope_code=b.scope_code AND r.data_mode=b.data_mode JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode WHERE b.mini_user_id=? AND b.data_mode=? AND b.binding_status='active' AND u.user_status='active' AND p.deleted_at IS NULL",user,mode);
        require(r!=null,403,"EMPLOYEE_BINDING_REQUIRED：请先验证并确认职工身份，或联系管理员核对绑定");return new Person(r);
    }
    void check(Person p){Person live=person(p.user,p.mode);require(p.binding.equals(live.binding)&&p.record.equals(live.record)&&p.scope.equals(live.scope),403,"EMPLOYEE_BINDING_CHANGED：职工身份已变化，请重新进入健康师");}
    Map<String,Object> owned(Person p,String cid,boolean lock){
        check(p);Map<String,Object> row=db.one("SELECT * FROM a_agent_conversations WHERE conversation_id=? AND client_type='miniapp' AND owner_user=? AND binding_id=? AND subject_record_id=? AND scope_code=? AND data_mode=?"+(lock?" FOR UPDATE":""),cid,p.actor.user,p.binding,p.record,p.scope,p.mode);
        require(row!=null,404,"对话不存在或无权访问");return row;
    }
    @Transactional public Map<String,Object> create(Person p){
        check(p);String cid=id();db.insert("a_agent_conversations",map("conversation_id",cid,"client_type","miniapp","owner_user",p.actor.user,"binding_id",p.binding,"subject_record_id",p.record,"scope_code",p.scope,"data_mode",p.mode,"title","新的健康对话","created_at",now(),"updated_at",now()));return map("id",cid,"title","新的健康对话");
    }
    public Map<String,Object> reportCounts(Person p){check(p);return db.one("SELECT COUNT(*) total,COALESCE(SUM(h.review_status='confirmed'),0) approved FROM a_health_reports h JOIN a_report_series s ON s.report_id=h.report_id AND s.current_revision=h.revision WHERE h.record_id=? AND h.scope_code=? AND h.data_mode=?",p.record,p.scope,p.mode);}
    public Map<String,Object> list(Person p,int page,int size){check(p);Object[] args={p.actor.user,p.binding,p.record,p.scope,p.mode};String where=" FROM a_agent_conversations WHERE client_type='miniapp' AND owner_user=? AND binding_id=? AND subject_record_id=? AND scope_code=? AND data_mode=?";long total=db.count("SELECT COUNT(*)"+where,args);List<Object> paged=new ArrayList<>(Arrays.asList(args));paged.add(size);paged.add((page-1)*size);
        return map("items",db.all("SELECT conversation_id id,title,CONCAT(DATE_FORMAT(created_at,'%Y-%m-%dT%H:%i:%s.%f'),'Z') createdAt,CONCAT(DATE_FORMAT(updated_at,'%Y-%m-%dT%H:%i:%s.%f'),'Z') updatedAt"+where+" ORDER BY updated_at DESC,conversation_id DESC LIMIT ? OFFSET ?",paged.toArray()),"page",page,"pageSize",size,"total",total,"hasMore",(long)page*size<total);
    }
    @SuppressWarnings("unchecked") Map<String,Object> message(Map<String,Object> t){
        Map<String,Object> result=map("conversationId",t.get("conversation_id"),"messageId",t.get("turn_id"),"status",t.get("turn_status"));
        if(t.get("response_json")!=null)result.putAll((Map<String,Object>)ehr.decode(t.get("response_json")));
        if(Arrays.asList("queued","running").contains(str(t.get("turn_status"))))result.put("pollAfterMs",1500);
        return result;
    }
    public Map<String,Object> result(Person p,String tid){check(p);Map<String,Object> t=db.one("SELECT t.* FROM a_agent_turns t JOIN a_agent_conversations c ON c.conversation_id=t.conversation_id WHERE t.turn_id=? AND c.client_type='miniapp' AND c.owner_user=? AND c.binding_id=? AND c.subject_record_id=? AND c.scope_code=? AND c.data_mode=?",tid,p.actor.user,p.binding,p.record,p.scope,p.mode);require(t!=null,404,"消息不存在或无权访问");return message(t);}
    public Map<String,Object> detail(Person p,String cid,int page,int size){Map<String,Object> c=owned(p,cid,false);long total=db.count("SELECT COUNT(*) FROM a_agent_turns WHERE conversation_id=?",cid);List<Map<String,Object>> rows=db.all("SELECT * FROM a_agent_turns WHERE conversation_id=? ORDER BY created_at DESC,turn_id DESC LIMIT ? OFFSET ?",cid,size,(page-1)*size);Collections.reverse(rows);List<Object> messages=new ArrayList<>();for(Map<String,Object> row:rows){Map<String,Object> m=message(row);m.put("question",row.get("user_message"));messages.add(m);}return map("id",cid,"title",c.get("title"),"items",messages,"page",page,"pageSize",size,"total",total,"hasMore",(long)page*size<total);}
    void quota(Person p,boolean daily,int limit){
        java.time.LocalDateTime utc=java.time.LocalDateTime.now(java.time.ZoneOffset.UTC);
        String window=(daily?utc.plusHours(8).toLocalDate().atStartOfDay().minusHours(8):utc.withSecond(0).withNano(0)).toString().replace('T',' ');
        byte[] key=digest(bytes("mini-agent:"+p.mode+":"+(daily?"day:":"minute:")+p.user));
        db.exec("INSERT INTO a_miniapp_rate_limits(bucket_key,window_start,attempt_count) VALUES(?,?,1) ON DUPLICATE KEY UPDATE attempt_count=IF(window_start=VALUES(window_start),attempt_count+1,1),window_start=VALUES(window_start)",key,window);
        long count=((Number)db.one("SELECT attempt_count FROM a_miniapp_rate_limits WHERE bucket_key=? FOR UPDATE",key).get("attempt_count")).longValue();
        require(count<=limit,429,daily?"DAILY_LIMIT：今日提问额度已用完，请明天再试":"RATE_LIMITED：提问过于频繁，请稍后再试");
    }
    @Transactional public Map<String,Object> submit(Person p,String cid,String key,String question,Map<String,Object> context){
        // Serializes submissions across all conversations of this miniapp user.
        db.one("SELECT mini_user_id FROM a_miniapp_users WHERE mini_user_id=? AND data_mode=? FOR UPDATE",p.user,p.mode);owned(p,cid,true);
        String hash=hex(digest(bytes(ehr.encode(map("message",question,"context",new TreeMap<>(context))))));Map<String,Object> old=db.one("SELECT * FROM a_agent_turns WHERE conversation_id=? AND request_key=?",cid,key);
        if(old!=null){require(hash.equals(old.get("request_hash")),409,"REQUEST_ID_CONFLICT：同一requestId不能提交不同问题或报告上下文");return message(old);}
        String join=" FROM a_agent_turns t JOIN a_agent_conversations c ON c.conversation_id=t.conversation_id WHERE c.client_type='miniapp' AND c.owner_user=? AND c.data_mode=?";
        require(db.count("SELECT COUNT(*)"+join+" AND t.turn_status IN ('queued','running')",p.actor.user,p.mode)==0,409,"MESSAGE_IN_PROGRESS：你已有问题正在处理，请等待完成");
        require(perDay>0,429,"DAILY_LIMIT：今日提问额度已用完，请明天再试");require(perMinute>0,429,"RATE_LIMITED：提问过于频繁，请稍后再试");quota(p,false,perMinute);quota(p,true,perDay);
        require(db.count("SELECT COUNT(*) FROM a_agent_turns t JOIN a_agent_conversations c ON c.conversation_id=t.conversation_id WHERE c.client_type='miniapp' AND c.data_mode=? AND t.turn_status='queued'",p.mode)<queueLimit,429,"QUEUE_FULL：健康师正在处理较多问题，请稍后再试");
        String tid=id();db.insert("a_agent_turns",map("turn_id",tid,"conversation_id",cid,"request_key",key,"request_hash",hash,"user_message",question,"selected_record_id",p.record,"turn_status","queued","request_context_json",ehr.encode(context),"created_at",now()));
        db.exec("UPDATE a_agent_conversations SET title=CASE WHEN title='新的健康对话' THEN ? ELSE title END,updated_at=UTC_TIMESTAMP(6),lease_turn_id=? WHERE conversation_id=?",question.substring(0,Math.min(40,question.length())),tid,cid);
        return map("conversationId",cid,"messageId",tid,"status","queued","pollAfterMs",1500);
    }
    @Transactional public Map<String,Object> delete(Person p,String cid){
        owned(p,cid,true);require(db.count("SELECT COUNT(*) FROM a_agent_turns WHERE conversation_id=? AND turn_status IN ('queued','running')",cid)==0,409,"MESSAGE_IN_PROGRESS：对话仍在处理中，请完成后删除");
        db.exec("DELETE l FROM a_agent_tool_logs l JOIN a_agent_turns t ON t.turn_id=l.turn_id WHERE t.conversation_id=?",cid);db.exec("DELETE FROM a_agent_actions WHERE conversation_id=?",cid);db.exec("DELETE FROM a_agent_turns WHERE conversation_id=?",cid);db.exec("DELETE FROM a_agent_conversations WHERE conversation_id=?",cid);return map("id",cid,"deleted",true);
    }
    @Transactional public Map<String,Object> claim(String mode){
        Map<String,Object> t=db.one("SELECT t.* FROM a_agent_turns t JOIN a_agent_conversations c ON c.conversation_id=t.conversation_id WHERE c.client_type='miniapp' AND c.data_mode=? AND t.turn_status='queued' ORDER BY t.created_at,t.turn_id LIMIT 1 FOR UPDATE",mode);if(t==null)return null;String token=id();db.exec("UPDATE a_agent_turns SET turn_status='running',run_token=?,started_at=UTC_TIMESTAMP(6),deadline_at=DATE_ADD(UTC_TIMESTAMP(6),INTERVAL 180 SECOND) WHERE turn_id=? AND turn_status='queued'",token,t.get("turn_id"));t.put("run_token",token);t.put("turn_status","running");return t;
    }
    Person forJob(Map<String,Object> job){Map<String,Object> c=db.one("SELECT * FROM a_agent_conversations WHERE conversation_id=? AND client_type='miniapp'",job.get("conversation_id"));require(c!=null,404,"对话已删除");String owner=str(c.get("owner_user"));require(owner.startsWith("wx:"),403,"个人对话身份异常");Person p=person(owner.substring(3),str(c.get("data_mode")));owned(p,str(job.get("conversation_id")),false);return p;}
    @SuppressWarnings("unchecked") List<Object> history(String cid,String tid){List<Map<String,Object>> rows=db.all("SELECT user_message,transcript_json FROM a_agent_turns WHERE conversation_id=? AND turn_id<>? AND turn_status='completed' ORDER BY created_at DESC LIMIT 4",cid,tid);Collections.reverse(rows);List<Object> out=new ArrayList<>();int length=0;for(Map<String,Object> r:rows){String raw=str(r.get("transcript_json"));if(length+raw.length()>90000)continue;Object transcript=ehr.decode(raw);if(transcript instanceof List){out.add(map("role","user","content",r.get("user_message")));out.addAll((List<Object>)transcript);length+=raw.length();}}return out;}
    @Transactional public boolean finish(Person p,Map<String,Object> job,Map<String,Object> result,List<Object> transcript){
        // Lock the binding through commit. An old binding can never publish a new answer.
        if(p!=null){Map<String,Object> live=db.one("SELECT binding_status FROM a_miniapp_employee_bindings WHERE binding_id=? FOR UPDATE",p.binding);require(live!=null&&"active".equals(live.get("binding_status")),403,"EMPLOYEE_BINDING_CHANGED：绑定已变化");check(p);}
        boolean failed="failed".equals(result.get("status"));return db.exec("UPDATE a_agent_turns SET turn_status=?,response_json=?,transcript_json=?,completed_at=UTC_TIMESTAMP(6) WHERE turn_id=? AND run_token=? AND turn_status='running' AND deadline_at>UTC_TIMESTAMP(6)",failed?"failed":"completed",ehr.encode(result),ehr.encode(transcript),job.get("turn_id"),job.get("run_token"))==1;
    }
    @Transactional public void expire(String mode){
        String response=ehr.encode(map("status","failed","errorCode","TASK_INTERRUPTED","message","本次处理超时或服务中断，请使用新的requestId重新提问。"));
        db.exec("UPDATE a_agent_turns t JOIN a_agent_conversations c ON c.conversation_id=t.conversation_id SET t.turn_status='failed',t.response_json=?,t.completed_at=UTC_TIMESTAMP(6) WHERE c.client_type='miniapp' AND c.data_mode=? AND ((t.turn_status='running' AND t.deadline_at<UTC_TIMESTAMP(6)) OR (t.turn_status='queued' AND t.created_at<DATE_SUB(UTC_TIMESTAMP(6),INTERVAL 10 MINUTE)))",response,mode);
    }
}
