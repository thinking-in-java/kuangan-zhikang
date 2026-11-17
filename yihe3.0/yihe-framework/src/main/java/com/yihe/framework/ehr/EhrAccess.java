package com.yihe.framework.ehr;
import com.yihe.framework.util.ShiroUtils;
import com.yihe.framework.shiro.service.SysPasswordService;
import com.yihe.system.domain.YhUserReginfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.shiro.SecurityUtils;
import javax.servlet.http.HttpServletRequest;
import java.security.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

@Service
public class EhrAccess {
    private final EhrRepository db; private final SysPasswordService passwords;
    @Value("${ehr.data-mode:real}") private String mode;
    public EhrAccess(EhrRepository db,SysPasswordService passwords){this.db=db;this.passwords=passwords;}
    public static String now(){return LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));}
    public static byte[] digest(byte[] b){try{return MessageDigest.getInstance("SHA-256").digest(b);}catch(Exception e){throw new IllegalStateException(e);}}
    public static byte[] bytes(String s){return s.getBytes(java.nio.charset.StandardCharsets.UTF_8);}
    public static Map<String,Object> map(Object... a){Map<String,Object> m=new LinkedHashMap<>();for(int i=0;i<a.length;i+=2)m.put((String)a[i],a[i+1]);return m;}
    public static String text(Map<String,Object> m,String k){Object x=m.get(k);return x==null?"":String.valueOf(x).trim();}
    private String mode(){if(!"real".equals(mode)&&!"synthetic".equals(mode))throw new IllegalStateException("Invalid EHR data mode");return mode;}
    public EhrActor web(HttpServletRequest req){
        String origin=req.getHeader("Origin");if(origin!=null){try{java.net.URI uri=java.net.URI.create(origin);if(uri.getAuthority()==null||!uri.getAuthority().equalsIgnoreCase(req.getHeader("Host")))throw new EhrException(403,"不允许跨站访问档案接口");}catch(IllegalArgumentException e){throw new EhrException(403,"来源不合法");}}
        YhUserReginfo u=ShiroUtils.getSysUser();
        if(u==null)throw new EhrException(401,"登录已失效，请重新登录");
        Map<String,Object> live=db.one("SELECT Org_Code org_code,User_Type user_type,lock_flag FROM yh_user_reginfo WHERE user_code=?",u.getUserCode());
        if(live==null||((Number)live.get("lock_flag")).intValue()!=2)throw new EhrException(401,"账号已停用");
        int type=((Number)live.get("user_type")).intValue();if(type==5)throw new EhrException(403,"此接口仅供后台管理使用");
        EhrActor a=new EhrActor(u.getUserCode(),(String)live.get("org_code"),mode(),type==4,false);
        if(!"GET".equals(req.getMethod())&&!"HEAD".equals(req.getMethod())){
            Object token=SecurityUtils.getSubject().getSession().getAttribute("ehr.csrf");String sent=req.getHeader("X-CSRF-Token");
            if(token==null||sent==null||!MessageDigest.isEqual(bytes(token.toString()),bytes(sent)))throw new EhrException(403,"请求校验失败，请刷新页面重试");
        }
        return a;
    }
    public Map<String,Object> context(HttpServletRequest req){EhrActor a=web(req);Object token=SecurityUtils.getSubject().getSession().getAttribute("ehr.csrf");if(token==null){token=random();SecurityUtils.getSubject().getSession().setAttribute("ehr.csrf",token);}return map("user",a.user,"scope",a.scope,"dataMode",a.mode,"canReview",a.doctor,"csrfToken",token,"uploadMaxBytes",20971520,"ocrEnabled",false);}
    private static String random(){byte[] b=new byte[32];new SecureRandom().nextBytes(b);return Base64.getUrlEncoder().withoutPadding().encodeToString(b);}
    // Imported enterprise records retain original unit provenance. Personal users
    // follow only their explicit legacy-user binding, validated against the org tree.
    private String mobileOwner(String user,String unit){
        List<Map<String,Object>> rows=db.all("SELECT p.scope_code FROM a_employee_roster_profiles p JOIN a_health_records r ON r.employee_id=p.employee_id AND r.scope_code=p.scope_code AND r.data_mode=p.data_mode WHERE p.legacy_user_code=? AND p.data_mode=? AND p.deleted_at IS NULL",user,mode());
        if(rows.isEmpty())return unit;
        if(rows.size()!=1)throw new EhrException(409,"本人档案存在重复关联，请联系管理员核对");
        String scope=String.valueOf(rows.get(0).get("scope_code"));
        if(!scope.startsWith("org:")||!db.organizationWithin(scope.substring(4),unit))throw new EhrException(403,"本人档案与当前单位归属不一致，请联系管理员核对");
        return scope.substring(4);
    }
    public Map<String,Object> login(Map<String,Object> body){
        String name=text(body,"username"),pwd=text(body,"password");if(name.length()>100||pwd.length()>200||name.isEmpty()||pwd.isEmpty())throw new EhrException(401,"账号或密码错误");
        Map<String,Object> u=db.one("SELECT user_code,user_name,user_pwd,Org_Code org_code,User_Type user_type,lock_flag FROM yh_user_reginfo WHERE user_name=?",name);
        if(u==null||!Integer.valueOf(2).equals(((Number)u.get("lock_flag")).intValue()))throw new EhrException(401,"账号或密码错误，或账号已停用");
        int type=((Number)u.get("user_type")).intValue();if(type!=4&&type!=5)throw new EhrException(403,"请使用个人或医生账号登录");
        YhUserReginfo model=new YhUserReginfo();model.setUserName(name);model.setUserPwd((String)u.get("user_pwd"));
        try{passwords.validate(model,pwd);}catch(RuntimeException e){throw new EhrException(401,"账号或密码错误，或重试次数过多");}
        EhrActor a=new EhrActor((String)u.get("user_code"),mobileOwner((String)u.get("user_code"),(String)u.get("org_code")),mode(),false,true);
        String token=random(),expires=LocalDateTime.now(ZoneOffset.UTC).plusHours(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        db.insert("a_ehr_mobile_sessions",map("session_id",UUID.randomUUID().toString(),"token_hash",digest(bytes(token)),"legacy_user_code",a.user,"scope_code",a.scope,"data_mode",a.mode,"expires_at",expires,"created_at",now()));
        return map("token",token,"tokenType","Bearer","expiresAt",expires+"Z","expiresIn",7200);
    }
    public EhrActor mobile(HttpServletRequest req){
        String h=req.getHeader("Authorization");if(h==null||!h.startsWith("Bearer ")||h.length()>150)throw new EhrException(401,"请先登录健康档案接口");
        Map<String,Object> s=db.one("SELECT s.*,u.Org_Code org_code,u.lock_flag FROM a_ehr_mobile_sessions s JOIN yh_user_reginfo u ON u.user_code=s.legacy_user_code WHERE s.token_hash=? AND s.revoked=0 AND s.expires_at>UTC_TIMESTAMP(6)",digest(bytes(h.substring(7))));
        if(s==null||((Number)s.get("lock_flag")).intValue()!=2)throw new EhrException(401,"令牌已失效");
        EhrActor a=new EhrActor((String)s.get("legacy_user_code"),mobileOwner((String)s.get("legacy_user_code"),(String)s.get("org_code")),mode(),false,true);
        if(!a.scope.equals(s.get("scope_code"))||!a.mode.equals(s.get("data_mode")))throw new EhrException(401,"组织或会话模式已变化，请重新登录");return a;
    }
    public void logout(HttpServletRequest req){mobile(req);db.exec("UPDATE a_ehr_mobile_sessions SET revoked=1 WHERE token_hash=?",digest(bytes(req.getHeader("Authorization").substring(7))));}
}
