package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Shared service inbox: organisation and employee ownership are always resolved by the server. */
@Service
public class CommunicationService {
    final EhrRepository db; final EhrService ehr;
    @org.springframework.beans.factory.annotation.Autowired(required=false) AutoReplyEngine autoReplies;
    public CommunicationService(EhrRepository db,EhrService ehr){this.db=db;this.ehr=ehr;}
    static String uuid(String value){require(value!=null&&value.matches("[a-f0-9-]{36}"),422,"编号格式不正确");return value;}
    Map<String,Object> owned(EhrActor a,String cid,boolean lock){
        uuid(cid);Map<String,Object> c=db.one("SELECT * FROM a_service_conversations WHERE conversation_id=? AND scope_code=? AND data_mode=?"+(lock?" FOR UPDATE":""),cid,a.scope,a.mode);
        require(c!=null,404,"会话不存在或没有访问权限");ehr.profile(a,str(c.get("record_id")),false);return c;
    }
    void staff(EhrActor a){require(!a.mobile,403,"此操作仅供医生或健康管理员使用");}
    void writable(EhrActor a,Map<String,Object> c){require("open".equals(c.get("service_status")),409,"会话已结束，请先重新开启");if(!a.mobile)require(a.user.equals(c.get("assignee_user")),409,"请先接管会话，当前处理人才能发送回复");}
    String actorName(EhrActor a){if(a.mobile)return "职工";Map<String,Object> u=db.one("SELECT COALESCE(NULLIF(name,''),user_name) name FROM yh_user_reginfo WHERE user_code=?",a.user);return u==null?(a.doctor?"医生":"健康管理员"):str(u.get("name"));}
    @Transactional public Map<String,Object> create(EhrActor a,String record){
        // One shared lock serialises first contact from web and miniapp.
        ehr.profile(a,uuid(record),true);Map<String,Object> prior=db.one("SELECT conversation_id FROM a_service_conversations WHERE scope_code=? AND data_mode=? AND record_id=?",a.scope,a.mode,record);
        if(prior!=null)return view(a,owned(a,str(prior.get("conversation_id")),false));
        String cid=id();db.insert("a_service_conversations",map("conversation_id",cid,"scope_code",a.scope,"data_mode",a.mode,"record_id",record,"created_at",now(),"updated_at",now()));return view(a,owned(a,cid,false));
    }
    Map<String,Object> view(EhrActor a,Map<String,Object> c){
        Map<String,Object> p=ehr.profile(a,str(c.get("record_id")),false);
        long read=db.count("SELECT COALESCE(MAX(last_read_seq),0) FROM a_service_reads WHERE conversation_id=? AND reader_user=?",c.get("conversation_id"),a.user);
        long unread=db.count("SELECT COUNT(*) FROM a_service_messages WHERE conversation_id=? AND seq>? AND sender_user<>? AND sender_role<>'system'"+(!a.mobile?" AND sender_role='employee'":""),c.get("conversation_id"),read,a.user);
        return map("id",c.get("conversation_id"),"recordId",c.get("record_id"),"name",p.get("employee_name"),"code",p.get("employee_no"),"team",p.get("team_name"),"job",p.get("position_name"),"status",c.get("service_status"),"assignee",c.get("assignee_user"),"mine",a.user.equals(c.get("assignee_user")),"unread",unread,"awaitingReply",((Number)c.get("last_employee_seq")).longValue()>((Number)c.get("last_staff_seq")).longValue(),"lastSeq",c.get("last_seq"),"preview",c.get("last_preview"),"version",c.get("row_version"),"updatedAt",c.get("updated_at"));
    }
    public Map<String,Object> list(EhrActor a,Map<String,String> q){
        staff(a);int page=bounded(q.get("page"),1,1,100000),size=bounded(q.get("limit"),30,1,50);String filter=q.getOrDefault("filter","open"),search=q.getOrDefault("search","").trim();require(search.length()<=100,422,"搜索条件过长");require(Arrays.asList("open","all","unread","pending","mine","closed").contains(filter),422,"会话筛选条件无效");
        List<Object> args=new ArrayList<>(Arrays.asList(a.scope,a.mode));
        String from=" FROM a_service_conversations c JOIN a_health_records r ON r.record_id=c.record_id AND r.scope_code=c.scope_code AND r.data_mode=c.data_mode JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode WHERE c.scope_code=? AND c.data_mode=? AND p.deleted_at IS NULL";
        if(!search.isEmpty()){from+=" AND (p.employee_name LIKE ? ESCAPE '!' OR p.employee_no LIKE ? ESCAPE '!' OR EXISTS(SELECT 1 FROM a_service_messages sm WHERE sm.conversation_id=c.conversation_id AND sm.body_text LIKE ? ESCAPE '!'))";for(int i=0;i<3;i++)args.add("%"+like(search)+"%");}
        if(!filter.equals("all"))from+=" AND c.service_status='"+(filter.equals("closed")?"closed":"open")+"'";
        if(filter.equals("pending"))from+=" AND c.last_employee_seq>c.last_staff_seq";
        if(filter.equals("mine")){from+=" AND c.assignee_user=?";args.add(a.user);}
        if(filter.equals("unread")){from+=" AND EXISTS(SELECT 1 FROM a_service_messages m WHERE m.conversation_id=c.conversation_id AND m.sender_role='employee' AND m.seq>COALESCE((SELECT last_read_seq FROM a_service_reads rd WHERE rd.conversation_id=c.conversation_id AND rd.reader_user=?),0))";args.add(a.user);}
        long total=db.count("SELECT COUNT(*)"+from,args.toArray());List<Object> paging=new ArrayList<>(args);paging.add(size);paging.add((page-1)*size);List<Object> items=new ArrayList<>();for(Map<String,Object> c:db.all("SELECT c.*"+from+" ORDER BY c.updated_at DESC,c.conversation_id DESC LIMIT ? OFFSET ?",paging.toArray()))items.add(view(a,c));
        return map("items",items,"total",total,"page",page,"limit",size);
    }
    public Map<String,Object> counts(EhrActor a){staff(a);return db.one("SELECT COUNT(*) total,COALESCE(SUM(c.service_status='open'),0) `open`,COALESCE(SUM(c.service_status='open' AND c.last_employee_seq>c.last_staff_seq),0) pending,COALESCE(SUM(c.assignee_user=? AND c.service_status='open'),0) mine,COALESCE(SUM(EXISTS(SELECT 1 FROM a_service_messages m WHERE m.conversation_id=c.conversation_id AND m.sender_role='employee' AND m.seq>COALESCE((SELECT last_read_seq FROM a_service_reads rd WHERE rd.conversation_id=c.conversation_id AND rd.reader_user=?),0))),0) unread FROM a_service_conversations c JOIN a_health_records r ON r.record_id=c.record_id JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.deleted_at IS NULL WHERE c.scope_code=? AND c.data_mode=?",a.user,a.user,a.scope,a.mode);}
    Map<String,Object> message(Map<String,Object> m){Object sender=m.get("sender_user");Map<String,Object> user="employee".equals(m.get("sender_role"))||"system".equals(m.get("sender_role"))?null:db.one("SELECT COALESCE(NULLIF(name,''),user_name) name FROM yh_user_reginfo WHERE user_code=?",sender);return map("id",m.get("message_id"),"seq",m.get("seq"),"role",m.get("sender_role"),"sender",sender,"senderName",user==null?null:user.get("name"),"text",m.get("body_text"),"createdAt",m.get("created_at"),"attachments",db.all("SELECT attachment_id id,original_name name,mime_type mime,byte_size size FROM a_service_attachments WHERE message_id=?",m.get("message_id")));}
    public Map<String,Object> messages(EhrActor a,String cid,long before,long after){Map<String,Object> c=owned(a,cid,false);require(before>=0&&after>=0&&!(before>0&&after>0),422,"消息游标无效");List<Map<String,Object>> rows;
        if(after>0)rows=db.all("SELECT * FROM a_service_messages WHERE conversation_id=? AND seq>? ORDER BY seq LIMIT 50",cid,after);
        else{rows=before>0?db.all("SELECT * FROM a_service_messages WHERE conversation_id=? AND seq<? ORDER BY seq DESC LIMIT 50",cid,before):db.all("SELECT * FROM a_service_messages WHERE conversation_id=? ORDER BY seq DESC LIMIT 50",cid);Collections.reverse(rows);}
        List<Object> items=new ArrayList<>();for(Map<String,Object> m:rows)items.add(message(m));long first=rows.isEmpty()?0:((Number)rows.get(0).get("seq")).longValue(),last=rows.isEmpty()?after:((Number)rows.get(rows.size()-1).get("seq")).longValue();return map("conversation",view(a,c),"items",items,"hasEarlier",first>1,"lastReturnedSeq",last,"hasMore",last<((Number)c.get("last_seq")).longValue());
    }
    @Transactional public Map<String,Object> read(EhrActor a,String cid,long seq){Map<String,Object> c=owned(a,cid,true);require(seq>=0&&seq<=((Number)c.get("last_seq")).longValue(),422,"已读游标无效");db.exec("INSERT INTO a_service_reads(conversation_id,reader_user,last_read_seq,updated_at) VALUES(?,?,?,UTC_TIMESTAMP(6)) ON DUPLICATE KEY UPDATE last_read_seq=GREATEST(last_read_seq,VALUES(last_read_seq)),updated_at=UTC_TIMESTAMP(6)",cid,a.user,seq);return map("readThrough",seq);}
    void system(EhrActor a,Map<String,Object> c,String text){long seq=((Number)c.get("last_seq")).longValue()+1;db.insert("a_service_messages",map("message_id",id(),"conversation_id",c.get("conversation_id"),"seq",seq,"sender_user",a.user,"sender_role","system","body_text",text,"request_key",id(),"payload_sha256",hex(digest(bytes(text))),"created_at",now()));db.exec("UPDATE a_service_conversations SET last_seq=?,updated_at=UTC_TIMESTAMP(6) WHERE conversation_id=?",seq,c.get("conversation_id"));}
    @Transactional public Map<String,Object> action(EhrActor a,String cid,Map<String,Object> body){staff(a);Map<String,Object> c=owned(a,cid,true);require(num(body.get("version"))==num(c.get("row_version")),409,"会话已被更新，请刷新后重试");String action=text(body,"action"),owner=str(c.get("assignee_user"));
        if(action.equals("takeover")){require("open".equals(c.get("service_status")),409,"请先重新开启会话");require(owner.isEmpty()||owner.equals(a.user),409,"已有其他同事接管，请先联系处理人释放会话");if(owner.equals(a.user))return view(a,c);db.exec("UPDATE a_service_conversations SET assignee_user=?,row_version=row_version+1 WHERE conversation_id=?",a.user,cid);system(a,c,actorName(a)+"已接管会话，后续由人工回复。");}
        else if(action.equals("release")||action.equals("close")){require(owner.equals(a.user),409,"只有当前处理人可以释放或结束会话");db.exec("UPDATE a_service_conversations SET assignee_user=NULL,service_status=?,row_version=row_version+1 WHERE conversation_id=?",action.equals("close")?"closed":"open",cid);system(a,c,actorName(a)+(action.equals("close")?"已结束本次服务。":"已释放会话，等待同事接管。"));}
        else if(action.equals("reopen")){require("closed".equals(c.get("service_status")),409,"会话已经开启");db.exec("UPDATE a_service_conversations SET service_status='open',row_version=row_version+1 WHERE conversation_id=?",cid);system(a,c,actorName(a)+"重新开启会话。");}
        else throw new EhrException(422,"会话操作无效");return view(a,owned(a,cid,false));
    }
    @SuppressWarnings("unchecked") @Transactional public Map<String,Object> send(EhrActor a,String cid,Map<String,Object> body){
        Map<String,Object> c=owned(a,cid,true);String text=ehr.safe(body,"text",4000),key=ehr.safe(body,"requestId",96),draft=text(body,"draftId");require(key.matches("[A-Za-z0-9_-]{8,96}"),422,"请提供有效的发送请求编号");Object rawFiles=body.get("attachmentIds");require(rawFiles==null||rawFiles instanceof List,422,"附件编号应为数组");List<String> files=new ArrayList<>();if(rawFiles!=null)for(Object f:(List<?>)rawFiles)files.add(uuid(str(f)));require(files.size()<=5&&new HashSet<>(files).size()==files.size(),422,"每条消息最多5个不同附件");require(!text.isEmpty()||!files.isEmpty(),422,"请输入消息或选择附件");
        String hash=hex(digest(bytes(ehr.encode(map("text",text,"files",files,"draft",draft)))));Map<String,Object> prior=db.one("SELECT * FROM a_service_messages WHERE conversation_id=? AND sender_user=? AND request_key=?",cid,a.user,key);if(prior!=null){require(hash.equals(prior.get("payload_sha256")),409,"同一请求编号不能发送不同消息");return message(prior);}
        if(a.mobile&&"closed".equals(c.get("service_status"))){db.exec("UPDATE a_service_conversations SET service_status='open' WHERE conversation_id=?",cid);c.put("service_status","open");}writable(a,c);
        String role=a.mobile?"employee":a.doctor?"doctor":"manager";
        if(!draft.isEmpty()){staff(a);Map<String,Object> d=db.one("SELECT * FROM a_service_reply_drafts WHERE draft_id=? AND conversation_id=? AND author_user=? FOR UPDATE",uuid(draft),cid,a.user);require(d!=null&&"ready".equals(d.get("draft_state")),409,"回复草稿不存在或已发送");require(((Number)d.get("based_on_seq")).longValue()==((Number)c.get("last_seq")).longValue(),409,"会话有新消息，请重新生成或移除旧草稿标记后核对回复");role="ai_assisted";}
        for(String fid:files){Map<String,Object> f=db.one("SELECT * FROM a_service_attachments WHERE attachment_id=? AND conversation_id=? AND uploader_user=? AND message_id IS NULL FOR UPDATE",fid,cid,a.user);require(f!=null,422,"附件不可用或不属于本次会话");}
        String mid=id();long seq=((Number)c.get("last_seq")).longValue()+1;db.insert("a_service_messages",map("message_id",mid,"conversation_id",cid,"seq",seq,"sender_user",a.user,"sender_role",role,"body_text",text,"request_key",key,"payload_sha256",hash,"draft_id",draft.isEmpty()?null:draft,"created_at",now()));
        for(String fid:files)db.exec("UPDATE a_service_attachments SET message_id=? WHERE attachment_id=?",mid,fid);if(!draft.isEmpty())db.exec("UPDATE a_service_reply_drafts SET draft_state='sent' WHERE draft_id=?",draft);
        db.exec("UPDATE a_service_conversations SET last_seq=?,"+(a.mobile?"last_employee_seq":"last_staff_seq")+"=?,last_preview=?,row_version=row_version+1,updated_at=UTC_TIMESTAMP(6) WHERE conversation_id=?",seq,seq,text.isEmpty()?"[附件]":text.substring(0,Math.min(150,text.length())),cid);
        if(a.mobile&&autoReplies!=null)autoReplies.enqueue(a,cid,mid);
        return message(db.one("SELECT * FROM a_service_messages WHERE message_id=?",mid));
    }
    public Map<String,Object> context(EhrActor a,String cid){staff(a);Map<String,Object> c=owned(a,cid,false);String record=str(c.get("record_id"));Map<String,Object> p=db.one(ehr.projection()+" AND r.record_id=?",a.scope,a.mode,record);
        List<Map<String,Object>> risks=db.all("SELECT assessment_id id,assessment_time time,risk_level level,assessment_type type,assessment_status status FROM a_health_risk_assessments WHERE record_id=? AND scope_code=? AND data_mode=? ORDER BY assessment_time DESC LIMIT 8",record,a.scope,a.mode);
        Map<String,Object> report=db.one("SELECT h.report_id id,h.revision,h.exam_date_verified date,h.institution_raw institution,h.review_status status FROM a_health_reports h JOIN a_report_series s ON s.report_id=h.report_id AND s.current_revision=h.revision WHERE h.record_id=? AND h.scope_code=? AND h.data_mode=? ORDER BY h.exam_date_verified DESC,h.created_at DESC LIMIT 1",record,a.scope,a.mode);
        List<Map<String,Object>> items=report==null?Collections.emptyList():db.all("SELECT original_field_name name,result_raw value,unit_raw unit,reference_range_raw referenceRange,abnormal_flag_raw flag FROM a_health_items WHERE report_id=? AND revision=? AND original_field_name REGEXP '血压|收缩压|舒张压|血糖|BMI|体质指数|肺功能|FEV|FVC|听力|心率|脉搏' ORDER BY ordinal LIMIT 30",report.get("id"),report.get("revision"));
        List<Map<String,Object>> follow=db.all("SELECT f.task_id id,f.status,f.due_at due,a.text_raw note FROM a_followup_tasks f JOIN a_health_reports h ON h.report_id=f.report_id AND h.revision=f.revision LEFT JOIN a_health_advice a ON a.advice_id=f.advice_id AND a.report_id=f.report_id AND a.revision=f.revision WHERE h.record_id=? AND h.scope_code=? AND h.data_mode=? ORDER BY f.due_at DESC LIMIT 8",record,a.scope,a.mode);
        return map("profile",p,"assessments",risks,"latestReport",report,"indicators",items,"followups",follow,"serviceEvents",db.all("SELECT body_text text,created_at time FROM a_service_messages WHERE conversation_id=? AND sender_role='system' ORDER BY seq DESC LIMIT 12",cid));
    }
    public Map<String,Object> summary(EhrActor a,String cid){Map<String,Object> c=owned(a,cid,false);return db.one("SELECT COUNT(*) messages,COALESCE(SUM(sender_role='employee'),0) employeeMessages,COALESCE(SUM(sender_role IN ('doctor','manager','ai_assisted')),0) staffMessages,COALESCE(SUM(sender_role='auto_reply'),0) automaticMessages,MIN(created_at) firstAt,MAX(created_at) latestAt FROM a_service_messages WHERE conversation_id=?",c.get("conversation_id"));}
}
