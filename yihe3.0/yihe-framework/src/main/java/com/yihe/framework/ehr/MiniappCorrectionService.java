package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

@Service
public class MiniappCorrectionService {
    private final EhrRepository db;private final EhrService ehr;private final MiniappIdentityService identity;
    public MiniappCorrectionService(EhrRepository db,EhrService ehr,MiniappIdentityService identity){this.db=db;this.ehr=ehr;this.identity=identity;}
    String key(Map<String,Object> b,String field){String k=text(b,field);require(k.matches("[0-9a-fA-F-]{36}"),422,field+"须为UUID");return k;}
    String reason(Map<String,Object> b,String field){String v=ehr.raw(b,field,600).trim();require(!v.isEmpty(),422,"请填写更正原因或审核说明");return v;}
    @SuppressWarnings("unchecked")
    Map<String,Object> changes(Map<String,Object> b){
        require(b.get("changes") instanceof Map,422,"changes须为待修改字段对象");Map<String,Object> values=(Map<String,Object>)b.get("changes"),result=new TreeMap<>();
        require(!values.isEmpty()&&values.size()<=6,422,"请选择1至6个更正字段");
        for(String f:values.keySet()){
            require(Arrays.asList("organization","team","position","phone","gender","birthDate").contains(f),422,"不支持修改字段："+f+"；姓名或工号不符请联系企业管理员");
            String v=ehr.raw(values,f,f.equals("organization")?255:f.equals("phone")?30:f.equals("birthDate")||f.equals("gender")?10:100).trim();
            if(f.equals("organization"))require(!v.isEmpty(),422,"单位名称不能为空");
            if(f.equals("gender"))require(Arrays.asList("男","女","未填写").contains(v),422,"性别不合法");
            if(f.equals("phone"))require(v.isEmpty()||v.matches("[+0-9 ()-]{5,30}"),422,"联系电话格式不合法");
            if(f.equals("birthDate")){require(!v.isEmpty(),422,"出生日期不能为空");ehr.date(v);}
            result.put(f,v);
        }
        return result;
    }
    String column(String f){return f.equals("organization")?"organization_name":f.equals("team")?"team_name":f.equals("position")?"position_name":f.equals("gender")?"gender":f.equals("birthDate")?"birth_date":"phone";}
    Map<String,Object> present(Map<String,Object> row){
        return map("id",row.get("correction_id"),"recordId",row.get("record_id"),"status",row.get("request_status"),"rowVersion",row.get("row_version"),"profileRowVersion",row.get("profile_row_version"),"before",ehr.decode(row.get("before_snapshot")),"changes",ehr.decode(row.get("requested_changes")),"reason",row.get("request_reason"),"createdAt",row.get("created_at"),"reviewedAt",row.get("reviewed_at"),"reviewNote",row.get("review_note"),"after",ehr.decode(row.get("after_snapshot")),"nextAction","pending".equals(row.get("request_status"))?"WAIT_FOR_REVIEW":"VERIFY_EMPLOYEE_AGAIN");
    }
    @Transactional
    public Map<String,Object> submit(Map<String,Object> session,Map<String,Object> b){
        Object user=session.get("mini_user_id");String mode=identity.dataMode();identity.userLock(user);
        require(identity.binding(user)==null,409,"此接口仅处理绑定前资料更正，已绑定用户请联系企业管理员");
        String requestKey=key(b,"requestKey"),note=reason(b,"reason"),token=text(b,"confirmationToken");Map<String,Object> values=changes(b);
        require(!token.isEmpty()&&token.length()<=128,422,"缺少有效的本人信息确认凭据");
        Map<String,Object> prior=db.one("SELECT * FROM a_miniapp_correction_requests WHERE mini_user_id=? AND data_mode=? AND request_key=? FOR UPDATE",user,mode,requestKey);
        if(prior!=null){
            Map<String,Object> ch=db.one("SELECT challenge_hash FROM a_miniapp_binding_challenges WHERE challenge_id=?",prior.get("challenge_id"));
            require(note.equals(prior.get("request_reason"))&&values.equals(ehr.decode(prior.get("requested_changes")))&&Arrays.equals(digest(bytes(token)),(byte[])ch.get("challenge_hash")),409,"幂等键已被其他申请内容使用");return present(prior);
        }
        Map<String,Object> ch=db.one("SELECT h.*,c.record_id FROM a_miniapp_binding_challenges h JOIN a_employee_binding_codes c ON c.binding_code_id=h.binding_code_id WHERE h.mini_user_id=? AND h.data_mode=? AND h.challenge_hash=? AND h.used_at IS NULL AND h.expires_at>UTC_TIMESTAMP(6)",user,mode,digest(bytes(token)));
        require(ch!=null,422,"确认凭据不属于当前用户或已过期，请重新核验");
        String record=str(ch.get("record_id"));Map<String,Object> p=identity.roster(record,true);
        require(db.one("SELECT challenge_id FROM a_miniapp_binding_challenges WHERE challenge_id=? AND used_at IS NULL AND expires_at>UTC_TIMESTAMP(6) FOR UPDATE",ch.get("challenge_id"))!=null,409,"确认凭据已失效，请重新核验");
        require(db.one("SELECT binding_code_id FROM a_employee_binding_codes WHERE binding_code_id=? AND code_status='issued' AND expires_at>UTC_TIMESTAMP(6) FOR UPDATE",ch.get("binding_code_id"))!=null,409,"原验证码已使用、撤销或过期");
        require(db.count("SELECT COUNT(*) FROM a_miniapp_employee_bindings WHERE record_id=? AND data_mode=? AND binding_status='active'",record,mode)==0,409,"该职工已绑定，请联系管理员核对");
        require(num(ch.get("profile_row_version"))==num(p.get("row_version"))&&Arrays.equals((byte[])ch.get("profile_digest"),identity.profileDigest(p)),409,"后台资料已更新，请重新核验后申请");
        require(!identity.pendingCorrection(user,record),409,"已有待审核更正申请，请等待后台处理");
        boolean different=false;for(String f:values.keySet())if(!str(values.get(f)).equals(str(p.get(column(f)))))different=true;require(different,422,"更正内容与当前资料相同");
        String id=id();db.insert("a_miniapp_correction_requests",map("correction_id",id,"scope_code",p.get("scope_code"),"data_mode",mode,"mini_user_id",user,"record_id",record,"challenge_id",ch.get("challenge_id"),"request_key",requestKey,"profile_row_version",p.get("row_version"),"before_snapshot",ehr.encode(identity.preview(p)),"requested_changes",ehr.encode(values),"request_reason",note,"created_at",now()));
        db.exec("UPDATE a_miniapp_binding_challenges h JOIN a_employee_binding_codes c ON c.binding_code_id=h.binding_code_id SET h.expires_at=UTC_TIMESTAMP(6) WHERE c.record_id=? AND c.data_mode=? AND h.used_at IS NULL",record,mode);
        ehr.audit(new EhrActor("wx:"+user,str(p.get("scope_code")).substring(4),mode,false,false),record,null,null,"profile_correction_requested","职工申请更正，等待医生核对；未修改名册，申请编号 "+id);
        return present(db.one("SELECT * FROM a_miniapp_correction_requests WHERE correction_id=?",id));
    }
    public Map<String,Object> mine(Map<String,Object> session,Map<String,String> q){
        String where=" WHERE mini_user_id=? AND data_mode=?";Object[] args={session.get("mini_user_id"),identity.dataMode()};int page=bounded(q.get("page"),1,1,100000),limit=bounded(q.get("limit"),10,1,100);
        List<Object> paged=new ArrayList<>(Arrays.asList(args));paged.add(limit);paged.add((page-1)*limit);List<Object> items=new ArrayList<>();for(Map<String,Object> r:db.all("SELECT * FROM a_miniapp_correction_requests"+where+" ORDER BY created_at DESC,correction_id DESC LIMIT ? OFFSET ?",paged.toArray()))items.add(present(r));return map("items",items,"total",db.count("SELECT COUNT(*) FROM a_miniapp_correction_requests"+where,args),"page",page,"limit",limit);
    }
    public Map<String,Object> queue(EhrActor a,Map<String,String> q){
        String where=" WHERE scope_code=? AND data_mode=?";List<Object> args=new ArrayList<>(Arrays.asList(a.scope,a.mode));String status=q.get("status"),record=q.get("recordId");
        if(status!=null&&!status.isEmpty()&&!status.equals("all")){require(Arrays.asList("pending","approved","rejected").contains(status),422,"更正申请状态无效");where+=" AND request_status=?";args.add(status);}
        if(record!=null&&!record.isEmpty()){ehr.profile(a,record,false);where+=" AND record_id=?";args.add(record);}
        int page=bounded(q.get("page"),1,1,100000),limit=bounded(q.get("limit"),10,1,100);long total=db.count("SELECT COUNT(*) FROM a_miniapp_correction_requests"+where,args.toArray());args.add(limit);args.add((page-1)*limit);List<Object> items=new ArrayList<>();for(Map<String,Object> r:db.all("SELECT * FROM a_miniapp_correction_requests"+where+" ORDER BY created_at DESC,correction_id DESC LIMIT ? OFFSET ?",args.toArray()))items.add(present(r));return map("items",items,"total",total,"page",page,"limit",limit);
    }
    @SuppressWarnings("unchecked")
    @Transactional
    public Map<String,Object> review(EhrActor a,String id,Map<String,Object> b){
        require(a.doctor,403,"仅医生可审核信息更正");String decision=text(b,"decision"),note=reason(b,"note"),key=key(b,"requestKey");require(Arrays.asList("approve","reject").contains(decision),422,"decision应为approve或reject");
        Map<String,Object> r=db.one("SELECT * FROM a_miniapp_correction_requests WHERE correction_id=? AND scope_code=? AND data_mode=?",id,a.scope,a.mode);require(r!=null,404,"申请不存在或无权访问");
        Map<String,Object> p=ehr.profile(a,str(r.get("record_id")),true);r=db.one("SELECT * FROM a_miniapp_correction_requests WHERE correction_id=? FOR UPDATE",id);
        String status=decision.equals("approve")?"approved":"rejected";
        if(!"pending".equals(r.get("request_status"))){require(key.equals(r.get("review_request_key"))&&status.equals(r.get("request_status"))&&note.equals(r.get("review_note"))&&Arrays.equals(bytes(a.user),(byte[])r.get("reviewed_by_ref")),409,"申请已处理，请刷新查看结果");return present(r);}
        require(num(b.get("rowVersion"))==num(r.get("row_version")),409,"申请版本已变化，请刷新");
        String record=str(r.get("record_id"));Object after=null;
        if(status.equals("approved")){
            require(num(r.get("profile_row_version"))==num(p.get("row_version")),409,"申请后后台资料已变化，请核对后驳回，让职工重新申请");
            require(db.count("SELECT COUNT(*) FROM a_miniapp_employee_bindings WHERE record_id=? AND data_mode=? AND binding_status='active'",record,a.mode)==0,409,"职工绑定状态已变化，请核查后处理");
            Map<String,Object> body=map("name",p.get("employee_name"),"code",p.get("employee_no"),"org",p.get("organization_name"),"team",p.get("team_name"),"job",p.get("position_name"),"sex",p.get("gender"),"birth",p.get("birth_date"),"phone",p.get("phone"),"exposure",p.get("exposure_history"),"legacyUserCode",p.get("legacy_user_code"),"rowVersion",p.get("row_version"));
            Map<String,Object> values=(Map<String,Object>)ehr.decode(r.get("requested_changes"));
            for(String f:values.keySet())body.put(f.equals("organization")?"org":f.equals("position")?"job":f.equals("gender")?"sex":f.equals("birthDate")?"birth":f,values.get(f));
            ehr.upsert(a,record,body);after=ehr.encode(identity.preview(ehr.profile(a,record,false)));
        }
        db.exec("UPDATE a_miniapp_correction_requests SET request_status=?,row_version=row_version+1,reviewed_by_ref=?,reviewed_at=UTC_TIMESTAMP(6),review_note=?,review_request_key=?,after_snapshot=? WHERE correction_id=?",status,bytes(a.user),note,key,after,id);
        db.exec("UPDATE a_miniapp_binding_challenges h JOIN a_employee_binding_codes c ON c.binding_code_id=h.binding_code_id SET h.expires_at=UTC_TIMESTAMP(6) WHERE c.record_id=? AND c.data_mode=? AND h.used_at IS NULL",record,a.mode);
        ehr.audit(a,record,null,null,"profile_correction_"+status,"更正申请 "+id+"："+note);
        return present(db.one("SELECT * FROM a_miniapp_correction_requests WHERE correction_id=?",id));
    }
}
