package com.yihe.framework.ehr;

import java.util.*;
import java.time.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Short transactions only. Never hold database locks while waiting for a model. */
@Service
public class HealthAgentStore {
    final EhrRepository db;final EhrService ehr;
    public HealthAgentStore(EhrRepository db,EhrService ehr){this.db=db;this.ehr=ehr;}
    Map<String,Object> owned(EhrActor a,String cid){Map<String,Object> c=db.one("SELECT * FROM a_agent_conversations WHERE conversation_id=? AND scope_code=? AND data_mode=? AND owner_user=? AND client_type='admin'",cid,a.scope,a.mode,a.user);require(c!=null,404,"对话不存在或无权访问");return c;}
    Map<String,Object> lockOwned(EhrActor a,String cid){Map<String,Object> c=db.one("SELECT * FROM a_agent_conversations WHERE conversation_id=? AND scope_code=? AND data_mode=? AND owner_user=? AND client_type='admin' FOR UPDATE",cid,a.scope,a.mode,a.user);require(c!=null,404,"对话不存在或无权访问");return c;}
    @Transactional public Map<String,Object> delete(EhrActor a,String cid){
        lockOwned(a,cid);
        require(db.count("SELECT COUNT(*) FROM a_agent_turns WHERE conversation_id=? AND turn_status='running'",cid)==0,409,"该对话仍在处理中，请完成后再删除");
        db.exec("DELETE l FROM a_agent_tool_logs l JOIN a_agent_turns t ON t.turn_id=l.turn_id WHERE t.conversation_id=?",cid);
        db.exec("DELETE FROM a_agent_actions WHERE conversation_id=?",cid);
        db.exec("DELETE FROM a_agent_turns WHERE conversation_id=?",cid);
        db.exec("DELETE FROM a_agent_conversations WHERE conversation_id=?",cid);
        return map("id",cid,"deleted",true);
    }
    public Map<String,Object> create(EhrActor a){String cid=id();db.insert("a_agent_conversations",map("conversation_id",cid,"scope_code",a.scope,"data_mode",a.mode,"owner_user",a.user,"title","新的健康对话","created_at",now(),"updated_at",now()));return map("id",cid,"title","新的健康对话");}
    public Object list(EhrActor a){return db.all("SELECT conversation_id id,title,created_at createdAt,updated_at updatedAt FROM a_agent_conversations WHERE scope_code=? AND data_mode=? AND owner_user=? AND client_type='admin' ORDER BY updated_at DESC LIMIT 60",a.scope,a.mode,a.user);}
    public Object actionStatus(String cid){return db.all("SELECT action_id,action_type,action_status,result_json FROM a_agent_actions WHERE conversation_id=? ORDER BY created_at DESC LIMIT 10",cid);}
    public void toolLog(String tid,String name,Map<String,Object> args,String outcome,long ms){db.insert("a_agent_tool_logs",map("tool_log_id",id(),"turn_id",tid,"tool_name",name.substring(0,Math.min(64,name.length())),"arguments_json",ehr.encode(args),"outcome",outcome,"duration_ms",ms,"created_at",now()));}
    @SuppressWarnings("unchecked") public Map<String,Object> detail(EhrActor a,String cid){owned(a,cid);List<Object> messages=new ArrayList<>();Object last=null;for(Map<String,Object> t:db.all("SELECT turn_id,user_message,response_json,turn_status FROM (SELECT * FROM a_agent_turns WHERE conversation_id=? ORDER BY created_at DESC LIMIT 80) x ORDER BY created_at",cid)){messages.add(map("role","user","text",t.get("user_message")));if(t.get("response_json")!=null){last=ehr.decode(t.get("response_json"));messages.add(map("role","ai","text",((Map<?,?>)last).get("text")));}}
        return map("id",cid,"messages",messages,"actions",actions(a,cid),"lastResult",last);}
    @Transactional public Map<String,Object> begin(EhrActor a,String cid,String key,String message,String selected){
        lockOwned(a,cid);String hash=hex(digest(bytes(ehr.encode(map("message",message,"selected",selected)))));
        Map<String,Object> old=db.one("SELECT * FROM a_agent_turns WHERE conversation_id=? AND request_key=?",cid,key);
        if(old!=null){require(hash.equals(old.get("request_hash")),409,"同一请求编号不能提交不同消息");require(!"running".equals(old.get("turn_status")),409,"该消息正在处理，请稍后查询会话");return map("cached",ehr.decode(old.get("response_json")));}
        String tid=id();int updated=db.exec("UPDATE a_agent_conversations SET lease_turn_id=?,lease_until=DATE_ADD(UTC_TIMESTAMP(6),INTERVAL 240 SECOND),updated_at=UTC_TIMESTAMP(6),title=CASE WHEN title='新的健康对话' THEN ? ELSE title END WHERE conversation_id=? AND (lease_until IS NULL OR lease_until<UTC_TIMESTAMP(6))",tid,message.substring(0,Math.min(40,message.length())),cid);
        require(updated==1,409,"此对话已有正在执行的请求，请稍后再发");
        db.exec("UPDATE a_agent_turns SET turn_status='failed',completed_at=UTC_TIMESTAMP(6),response_json=? WHERE conversation_id=? AND turn_status='running'",ehr.encode(map("text","上一次请求因服务中断未完成，请重新提问。","failed",true)),cid);
        db.insert("a_agent_turns",map("turn_id",tid,"conversation_id",cid,"request_key",key,"request_hash",hash,"user_message",message,"selected_record_id",selected.isEmpty()?null:selected,"turn_status","running","created_at",now()));return map("turnId",tid);
    }
    @SuppressWarnings("unchecked") List<Object> history(String cid,String tid){List<Object> input=new ArrayList<>();int size=0;List<Map<String,Object>> prior=db.all("SELECT user_message,transcript_json FROM (SELECT * FROM a_agent_turns WHERE conversation_id=? AND turn_id<>? AND turn_status='completed' ORDER BY created_at DESC LIMIT 6) x ORDER BY created_at DESC",cid,tid);Collections.reverse(prior);
        for(Map<String,Object> t:prior){String raw=str(t.get("transcript_json"));if(size+raw.length()>180000)continue;Object transcript=ehr.decode(t.get("transcript_json"));if(!(transcript instanceof List))continue;input.add(map("role","user","content",t.get("user_message")));input.addAll((List<Object>)transcript);size+=raw.length();}return input;}
    @Transactional public void finish(String cid,String tid,Map<String,Object> response,List<Object> transcript,boolean success){
        db.exec("UPDATE a_agent_turns SET turn_status=?,response_json=?,transcript_json=?,completed_at=UTC_TIMESTAMP(6) WHERE turn_id=? AND turn_status='running'",success?"completed":"failed",ehr.encode(response),ehr.encode(transcript),tid);
        db.exec("UPDATE a_agent_conversations SET lease_turn_id=NULL,lease_until=NULL,updated_at=UTC_TIMESTAMP(6) WHERE conversation_id=? AND lease_turn_id=?",cid,tid);
    }
    public List<Map<String,Object>> actions(EhrActor a,String cid){owned(a,cid);List<Map<String,Object>> out=new ArrayList<>();for(Map<String,Object> row:db.all("SELECT * FROM a_agent_actions WHERE conversation_id=? AND action_status='pending' AND expires_at>UTC_TIMESTAMP(6) ORDER BY created_at",cid))out.add(publicAction(row));return out;}
    Map<String,Object> publicAction(Map<String,Object> r){return map("id",r.get("action_id"),"type",r.get("action_type"),"status",r.get("action_status"),"target",ehr.decode(r.get("snapshot_json")),"changes",ehr.decode(r.get("payload_json")),"expiresAt",str(r.get("expires_at"))+"Z");}
    Map<String,Object> snapshot(EhrActor a,String record,boolean lock){Map<String,Object> p=ehr.profile(a,record,lock);List<Map<String,Object>> reports=db.all("SELECT report_id,revision FROM a_health_reports WHERE record_id=? AND scope_code=? AND data_mode=? ORDER BY report_id,revision"+(lock?" FOR UPDATE":""),record,a.scope,a.mode);List<Object> contents=new ArrayList<>();Set<String> ids=new HashSet<>();for(Map<String,Object> r:reports){ids.add(str(r.get("report_id")));contents.add(ehr.snapshot(str(r.get("report_id")),num(r.get("revision"))));}
        return map("recordId",record,"name",p.get("employee_name"),"code",p.get("employee_no"),"rowVersion",p.get("row_version"),"reportCount",ids.size(),"reportVersions",reports.size(),"fingerprint",hex(digest(bytes(ehr.encode(contents)))));}
    @Transactional public Map<String,Object> prepare(EhrActor a,String cid,String tid,String record,String type,Map<String,Object> payload,String message,String selected){
        require(a.doctor&&!a.mobile,403,"当前账号没有智能体修改职工档案权限");lockOwned(a,cid);Map<String,Object> s=snapshot(a,record,true);
        boolean intent=type.equals("delete_employee")?message.matches("(?si).*(删除|移除|删掉|delete).*"):message.matches("(?si).*(修改|更新|更正|改为|改成|update).*");
        require(!message.matches("(?s).*(不要|别|禁止|不准|取消)(删除|移除|删掉|修改|更新).*"),422,"你已要求不执行该操作，未生成预览");
        require(intent,422,"仅在你明确提出相应修改或删除要求时生成操作预览");
        require(message.contains(str(s.get("name")))||message.contains(str(s.get("code")))||record.equals(selected),422,"请明确职工姓名、工号，或先选择服务对象");
        if(type.equals("delete_employee")){require(!text(payload,"reason").isEmpty()&&text(payload,"reason").length()<=600,422,"删除原因不能为空且最多600字");}
        List<Map<String,Object>> old=db.all("SELECT * FROM a_agent_actions WHERE turn_id=? AND record_id=? AND action_type=? AND action_status='pending'",tid,record,type);if(!old.isEmpty())return publicAction(old.get(0));
        String aid=id();db.insert("a_agent_actions",map("action_id",aid,"conversation_id",cid,"turn_id",tid,"record_id",record,"action_type",type,"payload_json",ehr.encode(payload),"snapshot_json",ehr.encode(s),"expires_at",LocalDateTime.now(ZoneOffset.UTC).plusMinutes(10).toString().replace('T',' '),"created_at",now()));
        return publicAction(db.one("SELECT * FROM a_agent_actions WHERE action_id=?",aid));
    }
    @SuppressWarnings("unchecked") @Transactional public Map<String,Object> resolve(EhrActor a,String aid,Map<String,Object> body){
        require(a.doctor&&!a.mobile,403,"当前账号没有智能体修改职工档案权限");
        Map<String,Object> row=db.one("SELECT x.* FROM a_agent_actions x JOIN a_agent_conversations c ON c.conversation_id=x.conversation_id WHERE x.action_id=? AND c.scope_code=? AND c.data_mode=? AND c.owner_user=? FOR UPDATE",aid,a.scope,a.mode,a.user);require(row!=null,404,"操作不存在或没有访问权限");
        boolean confirm=Boolean.TRUE.equals(body.get("confirm"));
        if("confirmed".equals(row.get("action_status"))){require(confirm,409,"操作已执行");return (Map<String,Object>)ehr.decode(row.get("result_json"));}
        require("pending".equals(row.get("action_status")),409,"操作已取消");
        if(!confirm){db.exec("UPDATE a_agent_actions SET action_status='cancelled',resolved_at=UTC_TIMESTAMP(6) WHERE action_id=?",aid);return map("status","cancelled","text","已取消，未修改档案。");}
        require(db.count("SELECT COUNT(*) FROM a_agent_actions WHERE action_id=? AND expires_at>UTC_TIMESTAMP(6)",aid)==1,409,"操作预览已过期，请重新发起");
        Map<String,Object> expected=(Map<String,Object>)ehr.decode(row.get("snapshot_json"));String record=str(row.get("record_id"));
        require(str(expected.get("code")).equals(text(body,"employeeCode")),422,"请填写预览中职工的工号以确认操作");
        Map<String,Object> live=snapshot(a,record,true);for(String k:new String[]{"recordId","name","code","rowVersion","reportCount","reportVersions","fingerprint"})require(str(live.get(k)).equals(str(expected.get(k))),409,"职工资料或报告已变化，请重新生成预览");
        Map<String,Object> payload=(Map<String,Object>)ehr.decode(row.get("payload_json"));
        if("delete_employee".equals(row.get("action_type")))ehr.remove(a,record,map("rowVersion",live.get("rowVersion"),"reason",text(payload,"reason")));
        else ehr.upsert(a,record,payload);
        String text="delete_employee".equals(row.get("action_type"))?"已删除职工档案及其关联体检报告；原系统职工资料保留。":"职工基础资料已更新；报告原文未改写。";
        Map<String,Object> result=map("status","confirmed","recordId",record,"type",row.get("action_type"),"text",text);
        db.exec("UPDATE a_agent_actions SET action_status='confirmed',result_json=?,resolved_at=UTC_TIMESTAMP(6) WHERE action_id=?",ehr.encode(result),aid);
        ehr.audit(a,record,null,null,"agent_action_confirmed","人工确认智能体操作："+row.get("action_type")+"；操作ID："+aid);return result;
    }
}
