package com.yihe.framework.ehr;

import java.util.*;
import java.security.SecureRandom;
import java.time.*;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

@Service
public class MiniappIdentityService {
    private final EhrRepository db;private final EhrService ehr;private final MiniappWechatClient wx;private final MiniappRateLimiter rate;
    private final SecureRandom random=new SecureRandom();
    @Value("${ehr.data-mode:real}") String mode;
    @Value("${ehr.wechat.consent-version:2026-11-v1}") String consentVersion;
    public MiniappIdentityService(EhrRepository db,EhrService ehr,MiniappWechatClient wx,MiniappRateLimiter rate){this.db=db;this.ehr=ehr;this.wx=wx;this.rate=rate;}
    public String dataMode(){return mode;}

    String token(){byte[] b=new byte[32];random.nextBytes(b);return Base64.getUrlEncoder().withoutPadding().encodeToString(b);}
    String code(){String chars="23456789ABCDEFGHJKLMNPQRSTUVWXYZ";StringBuilder s=new StringBuilder();for(int i=0;i<16;i++){if(i>0&&i%4==0)s.append('-');s.append(chars.charAt(random.nextInt(chars.length())));}return s.toString();}
    String normalize(String s){return s.toUpperCase(Locale.ROOT).replace("-","").replace(" ","");}
    void limit(String key,int window,int max){require(rate.increment(mode+":"+key,window)<=max,429,"请求过于频繁，请稍后再试");}
    public void limitLogin(HttpServletRequest r){limit("wx-login-ip:"+r.getRemoteAddr(),60,60);}
    public void limitVerify(HttpServletRequest r,Map<String,Object> session){limit("binding-user:"+session.get("mini_user_id"),900,8);limit("binding-ip:"+r.getRemoteAddr(),900,120);}
    public Map<String,Object> config(){return map("wechatConfigured",wx.configured(),"consentVersion",consentVersion,"bindingRequired",true,"loginTokenExpiresIn",7200,"verificationCodeLength",16);}

    @Transactional
    public Map<String,Object> login(Map<String,Object> body){
        require(Boolean.TRUE.equals(body.get("privacyAccepted"))&&consentVersion.equals(text(body,"consentVersion")),422,"请先阅读并勾选当前版本用户协议和隐私政策");
        String code=text(body,"code");require(!code.isEmpty()&&code.length()<=256,422,"请提供wx.login获取的临时code");
        Map<String,Object> identity=wx.exchange(code);String uid=id();Object union=identity.get("unionid");
        require(union==null||(union instanceof String&&((String)union).length()<=128),401,"微信身份响应不合法");
        db.exec("INSERT INTO a_miniapp_users(mini_user_id,data_mode,app_id,open_id,union_id,consent_version,consent_at,created_at,last_login_at) VALUES(?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE last_login_at=VALUES(last_login_at),consent_version=VALUES(consent_version),consent_at=VALUES(consent_at)",uid,mode,wx.applicationId(),identity.get("openid"),union,consentVersion,now(),now(),now());
        Map<String,Object> user=db.one("SELECT * FROM a_miniapp_users WHERE data_mode=? AND app_id=? AND open_id=? FOR UPDATE",mode,wx.applicationId(),identity.get("openid"));
        require("active".equals(user.get("user_status")),403,"小程序账号已停用");
        String raw=token(),expires=LocalDateTime.now(ZoneOffset.UTC).plusHours(2).toString().replace('T',' ');
        db.insert("a_miniapp_sessions",map("mini_session_id",id(),"mini_user_id",user.get("mini_user_id"),"data_mode",mode,"token_hash",digest(bytes(raw)),"expires_at",expires,"created_at",now()));
        Map<String,Object> result=state(user);result.putAll(map("isNewUser",uid.equals(user.get("mini_user_id")),"token",raw,"tokenType","Bearer","expiresIn",7200,"expiresAt",expires.replace(' ','T')+"Z"));return result;
    }

    public Map<String,Object> session(HttpServletRequest req){
        String h=req.getHeader("Authorization");require(h!=null&&h.startsWith("Bearer ")&&h.length()<=150,401,"请先使用微信登录");
        Map<String,Object> s=db.one("SELECT u.*,s.mini_session_id FROM a_miniapp_sessions s JOIN a_miniapp_users u ON u.mini_user_id=s.mini_user_id AND u.data_mode=s.data_mode WHERE s.token_hash=? AND s.data_mode=? AND s.revoked=0 AND s.expires_at>UTC_TIMESTAMP(6)",digest(bytes(h.substring(7))),mode);
        require(s!=null&&"active".equals(s.get("user_status"))&&wx.applicationId().equals(s.get("app_id")),401,"微信登录已失效，请重新登录");return s;
    }
    public void logout(Map<String,Object> s){db.exec("UPDATE a_miniapp_sessions SET revoked=1 WHERE mini_session_id=?",s.get("mini_session_id"));}
    Map<String,Object> binding(Object user){return db.one("SELECT * FROM a_miniapp_employee_bindings WHERE mini_user_id=? AND data_mode=? AND binding_status='active'",user,mode);}
    Map<String,Object> userLock(Object user){Map<String,Object> u=db.one("SELECT mini_user_id,user_status FROM a_miniapp_users WHERE mini_user_id=? AND data_mode=? FOR UPDATE",user,mode);require(u!=null&&"active".equals(u.get("user_status")),401,"账号已失效");return u;}
    Map<String,Object> roster(String record,boolean lock){Map<String,Object> p=db.one("SELECT p.*,r.record_id FROM a_health_records r JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode WHERE r.record_id=? AND r.data_mode=? AND p.deleted_at IS NULL"+(lock?" FOR UPDATE":""),record,mode);require(p!=null,409,"职工档案已停用，请联系企业管理员");return p;}
    String mask(Object value){String p=str(value);return p.length()>=7?p.substring(0,3)+"****"+p.substring(p.length()-4):p.isEmpty()?"未填写":"****";}
    Map<String,Object> preview(Map<String,Object> p){return map("employeeId",p.get("employee_id"),"recordId",p.get("record_id"),"name",p.get("employee_name"),"employeeNo",p.get("employee_no"),"gender",p.get("gender"),"birthDate",p.get("birth_date"),"organization",p.get("organization_name"),"team",p.get("team_name"),"position",p.get("position_name"),"phoneMasked",mask(p.get("phone")),"rowVersion",p.get("row_version"));}
    byte[] profileDigest(Map<String,Object> p){return digest(bytes(ehr.encode(preview(p))));}
    // Call after locking the roster: a current read must see corrections committed while waiting.
    boolean pendingCorrection(Object user,Object record){return db.one("SELECT correction_id FROM a_miniapp_correction_requests WHERE data_mode=? AND request_status='pending' AND (mini_user_id=? OR record_id=?) LIMIT 1 FOR UPDATE",mode,user,record)!=null;}
    public Map<String,Object> state(Map<String,Object> s){
        Object user=s.get("mini_user_id");Map<String,Object> b=binding(user);String status="UNBOUND";
        if(b!=null)status="BOUND";
        else if(db.count("SELECT COUNT(*) FROM a_miniapp_correction_requests WHERE mini_user_id=? AND data_mode=? AND request_status='pending'",user,mode)>0)status="CORRECTION_PENDING";
        else if(db.count("SELECT COUNT(*) FROM a_miniapp_binding_challenges h JOIN a_employee_binding_codes c ON c.binding_code_id=h.binding_code_id WHERE h.mini_user_id=? AND h.data_mode=? AND h.used_at IS NULL AND h.expires_at>UTC_TIMESTAMP(6) AND c.code_status='issued' AND c.expires_at>UTC_TIMESTAMP(6)",user,mode)>0)status="PENDING_CONFIRMATION";
        Map<String,Object> result=map("miniUserId",user,"bindingState",status,"canEnterHome",status.equals("BOUND"),"needIdentityVerification",!status.equals("BOUND"),"nextAction",status.equals("BOUND")?"ENTER_HOME":status.equals("CORRECTION_PENDING")?"WAIT_FOR_REVIEW":status.equals("PENDING_CONFIRMATION")?"CONFIRM_OR_VERIFY_AGAIN":"VERIFY_EMPLOYEE","consentVersion",consentVersion);
        if(b!=null)result.put("employee",preview(roster(str(b.get("record_id")),false)));return result;
    }

    @Transactional
    public Map<String,Object> verify(Map<String,Object> s,Map<String,Object> body){
        Object user=s.get("mini_user_id");userLock(user);require(binding(user)==null,409,"已绑定职工身份，不能自助切换为他人");
        require(db.count("SELECT COUNT(*) FROM a_miniapp_correction_requests WHERE mini_user_id=? AND data_mode=? AND request_status='pending'",user,mode)==0,409,"更正申请待审核，请等待后台处理");
        String name=text(body,"name"),number=text(body,"employeeNo"),secret=normalize(text(body,"verificationCode"));
        require(!name.isEmpty()&&name.length()<=100&&!number.isEmpty()&&number.length()<=96&&secret.matches("[23456789ABCDEFGHJKLMNPQRSTUVWXYZ]{16}"),422,"姓名、工号或企业身份验证码不匹配或已失效");
        Map<String,Object> code=db.one("SELECT * FROM a_employee_binding_codes WHERE data_mode=? AND code_hash=? AND code_status='issued' AND expires_at>UTC_TIMESTAMP(6)",mode,digest(bytes(secret)));
        require(code!=null,422,"姓名、工号或企业身份验证码不匹配或已失效");
        Map<String,Object> p=roster(str(code.get("record_id")),true);
        require(!pendingCorrection(user,p.get("record_id")),409,"该职工资料正在核对更正，请等待后台处理");
        code=db.one("SELECT * FROM a_employee_binding_codes WHERE binding_code_id=? AND code_status='issued' AND expires_at>UTC_TIMESTAMP(6) FOR UPDATE",code.get("binding_code_id"));require(code!=null,422,"姓名、工号或企业身份验证码不匹配或已失效");
        require(name.equals(p.get("employee_name"))&&number.equals(p.get("employee_no")),422,"姓名、工号或企业身份验证码不匹配或已失效");
        require(db.count("SELECT COUNT(*) FROM a_miniapp_employee_bindings WHERE record_id=? AND data_mode=? AND binding_status='active'",p.get("record_id"),mode)==0,409,"该职工已有有效绑定，请联系企业管理员核对");
        String challenge=token();LocalDateTime expiry=LocalDateTime.now(ZoneOffset.UTC).plusMinutes(10),codeExpiry=Timestamp.valueOf(str(code.get("expires_at"))).toLocalDateTime();if(codeExpiry.isBefore(expiry))expiry=codeExpiry;
        db.insert("a_miniapp_binding_challenges",map("challenge_id",id(),"mini_user_id",user,"data_mode",mode,"binding_code_id",code.get("binding_code_id"),"challenge_hash",digest(bytes(challenge)),"profile_row_version",p.get("row_version"),"profile_digest",profileDigest(p),"expires_at",expiry.toString().replace('T',' '),"created_at",now()));
        return map("bindingState","PENDING_CONFIRMATION","confirmationToken",challenge,"expiresAt",expiry.toString()+"Z","employee",preview(p),"notice","请核对后台提供的本人资料；发现错误请联系企业管理员，不能在此修改身份字段");
    }

    @Transactional
    public Map<String,Object> confirm(Map<String,Object> s,Map<String,Object> body){
        Object user=s.get("mini_user_id");userLock(user);require(Boolean.TRUE.equals(body.get("confirmed")),422,"请明确勾选资料与本人身份一致");
        String token=text(body,"confirmationToken");require(!token.isEmpty()&&token.length()<=128,422,"缺少个人信息确认凭据");
        Map<String,Object> challenge=db.one("SELECT * FROM a_miniapp_binding_challenges WHERE mini_user_id=? AND data_mode=? AND challenge_hash=?",user,mode,digest(bytes(token)));
        require(challenge!=null,422,"确认凭据不属于当前用户或已失效");
        Map<String,Object> code=db.one("SELECT * FROM a_employee_binding_codes WHERE binding_code_id=?",challenge.get("binding_code_id"));
        Map<String,Object> p=roster(str(code.get("record_id")),true);Map<String,Object> old=binding(user);
        if(old!=null){require(old.get("record_id").equals(p.get("record_id"))&&challenge.get("used_at")!=null&&code.get("binding_code_id").equals(old.get("binding_code_id")),409,"已绑定其他身份或确认凭据已过期");return state(s);}
        require(!pendingCorrection(user,p.get("record_id")),409,"更正申请待审核，暂不能确认绑定");
        require(db.count("SELECT COUNT(*) FROM a_miniapp_binding_challenges WHERE challenge_id=? AND used_at IS NULL AND expires_at>UTC_TIMESTAMP(6)",challenge.get("challenge_id"))==1,409,"确认已过期，请重新验证身份");
        code=db.one("SELECT * FROM a_employee_binding_codes WHERE binding_code_id=? AND code_status='issued' AND expires_at>UTC_TIMESTAMP(6) FOR UPDATE",code.get("binding_code_id"));require(code!=null,409,"验证码已使用、撤销或过期，请重新验证");
        require(num(challenge.get("profile_row_version"))==num(p.get("row_version"))&&Arrays.equals((byte[])challenge.get("profile_digest"),profileDigest(p)),409,"后台资料已发生变化，请重新验证并核对最新资料");
        db.insert("a_miniapp_employee_bindings",map("binding_id",id(),"mini_user_id",user,"scope_code",p.get("scope_code"),"data_mode",mode,"record_id",p.get("record_id"),"binding_code_id",code.get("binding_code_id"),"confirmed_digest",profileDigest(p),"confirmed_at",now()));
        db.exec("UPDATE a_employee_binding_codes SET code_status='consumed',consumed_by=?,consumed_at=UTC_TIMESTAMP(6) WHERE binding_code_id=?",user,code.get("binding_code_id"));
        db.exec("UPDATE a_miniapp_binding_challenges SET used_at=UTC_TIMESTAMP(6) WHERE challenge_id=?",challenge.get("challenge_id"));
        ehr.audit(new EhrActor("wx:"+user,str(p.get("scope_code")).substring(4),mode,false,false),str(p.get("record_id")),null,null,"wechat_employee_bound","个人验证码核验后，用户确认后台资料；未新建或覆盖职工档案");return state(s);
    }

    public EhrActor actor(Map<String,Object> s){Map<String,Object> b=binding(s.get("mini_user_id"));require(b!=null,403,"EMPLOYEE_BINDING_REQUIRED：请先验证并确认职工身份");Map<String,Object> p=roster(str(b.get("record_id")),false);return new EhrActor("wx:"+s.get("mini_user_id"),str(p.get("scope_code")).substring(4),mode,false,true,str(b.get("record_id")));}
    public Map<String,Object> healthRecord(Map<String,Object> s){EhrActor a=actor(s);Map<String,Object> result=ehr.detail(a,a.boundRecordId);result.put("reports",result.get("publishedReports"));result.remove("logs");return result;}

    @Transactional
    public Map<String,Object> issue(EhrActor a,String record,Map<String,Object> body){
        require(a.doctor,403,"仅医生账号可签发企业身份验证码");Map<String,Object> p=ehr.profile(a,record,true);
        require(db.count("SELECT COUNT(*) FROM a_miniapp_employee_bindings WHERE record_id=? AND data_mode=? AND binding_status='active'",record,a.mode)==0,409,"该职工已经绑定；换绑需先由后台核对并解除");
        String key=text(body,"requestKey");require(!key.isEmpty()&&key.length()<=96,422,"缺少签发幂等键requestKey");require(db.count("SELECT COUNT(*) FROM a_employee_binding_codes WHERE scope_code=? AND data_mode=? AND issue_request_key=?",a.scope,a.mode,key)==0,409,"签发请求已处理，明文不会重复返回；需要时请用新请求重新生成");
        int seconds=body.get("expiresIn")==null?86400:num(body.get("expiresIn"));require(seconds>=300&&seconds<=604800,422,"验证码有效期应为300至604800秒");
        db.exec("UPDATE a_employee_binding_codes SET code_status='revoked',revoked_at=UTC_TIMESTAMP(6) WHERE record_id=? AND data_mode=? AND code_status='issued'",record,a.mode);
        String raw=code(),id=id(),expires=LocalDateTime.now(ZoneOffset.UTC).plusSeconds(seconds).toString().replace('T',' ');
        db.insert("a_employee_binding_codes",map("binding_code_id",id,"scope_code",a.scope,"data_mode",a.mode,"record_id",record,"code_hash",digest(bytes(normalize(raw))),"issue_request_key",key,"issued_by_ref",bytes(a.user),"issued_at",now(),"expires_at",expires));
        ehr.audit(a,record,null,null,"binding_code_issued","签发个人专属一次性验证码，旧码已撤销，明文不写入审计");
        return map("codeId",id,"recordId",record,"name",p.get("employee_name"),"employeeNo",p.get("employee_no"),"verificationCode",raw,"expiresAt",expires.replace(' ','T')+"Z","displayOnce",true);
    }
    public Map<String,Object> metadata(EhrActor a,String record){
        ehr.profile(a,record,false);
        String codeProjection="SELECT binding_code_id codeId,code_status status,DATE_FORMAT(issued_at,'%Y-%m-%dT%H:%i:%sZ') issuedAt,DATE_FORMAT(expires_at,'%Y-%m-%dT%H:%i:%sZ') expiresAt,DATE_FORMAT(consumed_at,'%Y-%m-%dT%H:%i:%sZ') consumedAt,DATE_FORMAT(revoked_at,'%Y-%m-%dT%H:%i:%sZ') revokedAt,(expires_at<=UTC_TIMESTAMP(6)) expired FROM a_employee_binding_codes WHERE record_id=? AND scope_code=? AND data_mode=?";
        String bindingProjection="SELECT b.binding_id bindingId,b.binding_status status,DATE_FORMAT(b.confirmed_at,'%Y-%m-%dT%H:%i:%sZ') confirmedAt,DATE_FORMAT(b.revoked_at,'%Y-%m-%dT%H:%i:%sZ') revokedAt,b.revoke_reason reason,DATE_FORMAT(u.last_login_at,'%Y-%m-%dT%H:%i:%sZ') lastLoginAt FROM a_miniapp_employee_bindings b JOIN a_miniapp_users u ON u.mini_user_id=b.mini_user_id AND u.data_mode=b.data_mode WHERE b.record_id=? AND b.scope_code=? AND b.data_mode=?";
        List<Map<String,Object>> codes=db.all(codeProjection+" ORDER BY issued_at DESC,binding_code_id DESC LIMIT 100",record,a.scope,a.mode);
        List<Map<String,Object>> bindings=db.all(bindingProjection+" ORDER BY b.confirmed_at DESC,b.binding_id DESC LIMIT 100",record,a.scope,a.mode);
        Map<String,Object> current=db.one(bindingProjection+" AND b.binding_status='active'",record,a.scope,a.mode);
        String eventWhere=" FROM a_health_archive_audit_logs WHERE record_id=? AND scope_code=? AND data_mode=? AND action IN ('binding_code_issued','binding_code_revoked','wechat_employee_bound','wechat_employee_unbound')";
        List<Map<String,Object>> events=db.all("SELECT audit_id id,action,reason note,CONVERT(actor_ref USING utf8mb4) actor,DATE_FORMAT(occurred_at,'%Y-%m-%dT%H:%i:%sZ') time"+eventWhere+" ORDER BY occurred_at DESC,audit_id DESC LIMIT 100",record,a.scope,a.mode);
        boolean truncated=db.count("SELECT COUNT(*) FROM a_employee_binding_codes WHERE record_id=? AND scope_code=? AND data_mode=?",record,a.scope,a.mode)>100||db.count("SELECT COUNT(*) FROM a_miniapp_employee_bindings WHERE record_id=? AND scope_code=? AND data_mode=?",record,a.scope,a.mode)>100||db.count("SELECT COUNT(*)"+eventWhere,record,a.scope,a.mode)>100;
        return map("bound",current!=null,"canManage",a.doctor,"currentBinding",current,"lastCode",codes.isEmpty()?null:codes.get(0),"codes",codes,"bindings",bindings,"events",events,"truncated",truncated);
    }
    @Transactional public List<Object> issueBatch(EhrActor a,List<Map<String,Object>> entries){require(!entries.isEmpty()&&entries.size()<=100,422,"每批签发1至100名职工");entries.sort(Comparator.comparing(e->text(e,"recordId")));Set<String> seen=new HashSet<>();List<Object> result=new ArrayList<>();for(Map<String,Object> e:entries){String record=text(e,"recordId");require(seen.add(record),422,"批次内存在重复档案");result.add(issue(a,record,e));}return result;}
    @Transactional public void revokeCode(EhrActor a,String record,String expectedCode){
        require(a.doctor,403,"仅医生账号可撤销");ehr.profile(a,record,true);
        require(!expectedCode.isEmpty()&&expectedCode.length()<=96,422,"缺少待撤销验证码标识");
        int changed=db.exec("UPDATE a_employee_binding_codes SET code_status='revoked',revoked_at=UTC_TIMESTAMP(6) WHERE binding_code_id=? AND record_id=? AND scope_code=? AND data_mode=? AND code_status='issued'",expectedCode,record,a.scope,a.mode);
        require(changed==1,409,"该验证码已使用或撤销，请刷新记录核对");
        ehr.audit(a,record,null,null,"binding_code_revoked","后台撤销所选未消费的身份验证码");
    }
    @Transactional public void revokeCode(EhrActor a,String record){require(a.doctor,403,"仅医生账号可撤销");ehr.profile(a,record,true);int changed=db.exec("UPDATE a_employee_binding_codes SET code_status='revoked',revoked_at=UTC_TIMESTAMP(6) WHERE record_id=? AND scope_code=? AND data_mode=? AND code_status='issued'",record,a.scope,a.mode);if(changed>0)ehr.audit(a,record,null,null,"binding_code_revoked","后台撤销未消费的身份验证码");}
    @Transactional public void unbind(EhrActor a,String record,String reason){require(a.doctor,403,"仅医生账号可解除绑定");require(!reason.trim().isEmpty()&&reason.length()<=600,422,"解除绑定须填写核对原因");ehr.profile(a,record,true);Map<String,Object> b=db.one("SELECT * FROM a_miniapp_employee_bindings WHERE record_id=? AND data_mode=? AND binding_status='active' FOR UPDATE",record,a.mode);require(b!=null,409,"职工当前没有有效微信绑定");db.exec("UPDATE a_miniapp_employee_bindings SET binding_status='revoked',revoked_at=UTC_TIMESTAMP(6),revoke_reason=? WHERE binding_id=?",reason,b.get("binding_id"));db.exec("UPDATE a_miniapp_sessions SET revoked=1 WHERE mini_user_id=? AND data_mode=?",b.get("mini_user_id"),a.mode);revokeCode(a,record);ehr.audit(a,record,null,null,"wechat_employee_unbound",reason);}
}
