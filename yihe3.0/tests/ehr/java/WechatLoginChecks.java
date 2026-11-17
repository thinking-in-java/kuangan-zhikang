package com.yihe.framework.ehr;

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import static com.yihe.framework.ehr.EhrAccess.map;

/** Offline checks only: no HTTP server, real WeChat identity, or database writes. */
public class WechatLoginChecks {
    static int checks;
    static void check(String name,boolean condition){if(!condition)throw new AssertionError(name);checks++;System.out.println("PASS "+name);}
    static MiniappWechatClient client(Path file,String mode){MiniappWechatClient c=new MiniappWechatClient();c.appId="";c.appSecret="";c.credentialsFile=file.toString();c.mode=mode;c.testUrl="";return c;}
    static class Repo extends EhrRepository {
        Map<String,Object> user;String state="UNBOUND";int sessions;
        Repo(){super(new DriverManagerDataSource());}
        @Override public int exec(String sql,Object... args){
            if(sql.startsWith("INSERT INTO a_miniapp_users")&&user==null)user=map("mini_user_id",args[0],"data_mode",args[1],"app_id",args[2],"open_id",args[3],"user_status","active");
            return 1;
        }
        @Override public Map<String,Object> one(String sql,Object...args){
            if(sql.contains("SELECT * FROM a_miniapp_users"))return user;
            if(sql.contains("FROM a_miniapp_employee_bindings"))return state.equals("BOUND")?map("record_id","unit-record"):null;
            if(sql.contains("FROM a_health_records"))return map("employee_id","unit-employee","record_id","unit-record","employee_name","Unit Fixture","employee_no","UNIT001","phone","13800000000","scope_code","org:unit");
            throw new AssertionError("Unexpected repository read");
        }
        @Override public long count(String sql,Object...args){return state.equals("CORRECTION_PENDING")&&sql.contains("a_miniapp_correction_requests")||state.equals("PENDING_CONFIRMATION")&&sql.contains("a_miniapp_binding_challenges")?1:0;}
        @Override public void insert(String table,Map<String,Object> fields){if(!table.equals("a_miniapp_sessions"))throw new AssertionError("Unexpected write");if(!(fields.get("token_hash") instanceof byte[]))throw new AssertionError("Token must be hashed");sessions++;}
    }
    static class UnitWx extends MiniappWechatClient {
        UnitWx(){appId="wx0000000000000000";appSecret="00000000000000000000000000000000";mode="synthetic";}
        @Override public Map<String,Object> exchange(String code){return map("openid","unit-only-not-a-real-identity");}
    }
    public static void main(String[] args)throws Exception{
        Path file=Files.createTempFile("wechat-private-config-unit-",".json");
        try{
            Files.write(file,"{\"appId\":\"wx0000000000000000\",\"appSecret\":\"00000000000000000000000000000000\"}".getBytes(StandardCharsets.UTF_8));
            MiniappWechatClient c=client(file,"real");c.loadPrivateConfiguration();check("real_config_loads_private_file",c.configured()&&c.applicationId().equals("wx0000000000000000"));
            c=client(file,"synthetic");c.loadPrivateConfiguration();check("synthetic_never_imports_real_credentials",!c.configured()&&c.applicationId().isEmpty());
            c=client(file,"real");c.appId="wx1111111111111111";c.appSecret="11111111111111111111111111111111";c.loadPrivateConfiguration();check("explicit_environment_pair_takes_precedence",c.configured()&&c.applicationId().equals("wx1111111111111111"));
            c=client(file,"real");c.appId="wx1111111111111111";c.loadPrivateConfiguration();check("partial_environment_does_not_mix_secret_sources",!c.configured());
            Files.write(file,"not valid json".getBytes(StandardCharsets.UTF_8));c=client(file,"real");c.loadPrivateConfiguration();check("malformed_private_config_fails_closed",!c.configured());
            Files.write(file,new byte[5000]);c=client(file,"real");c.loadPrivateConfiguration();check("oversized_private_config_fails_closed",!c.configured());
            c=client(file,"real");c.appId="invalid";c.appSecret="00000000000000000000000000000000";check("malformed_real_app_id_rejected",!c.configured());
            c=client(file,"real");c.appId="wx0000000000000000";c.appSecret="short";check("malformed_real_secret_rejected",!c.configured());
            Repo repo=new Repo();MiniappIdentityService identity=new MiniappIdentityService(repo,new EhrService(repo),new UnitWx(),null);identity.mode="synthetic";identity.consentVersion="unit-consent";
            try{identity.login(map("code","unit-code","privacyAccepted",false,"consentVersion","unit-consent"));throw new AssertionError("Expected consent rejection");}catch(EhrException e){check("consent_required_before_registration",e.status==422&&repo.user==null&&repo.sessions==0);}
            Map<String,Object> request=map("code","unit-code","privacyAccepted",true,"consentVersion","unit-consent");Map<String,Object> first=identity.login(request);
            check("first_login_creates_user_and_requires_identity",Boolean.TRUE.equals(first.get("isNewUser"))&&Boolean.TRUE.equals(first.get("needIdentityVerification"))&&Boolean.FALSE.equals(first.get("canEnterHome"))&&"VERIFY_EMPLOYEE".equals(first.get("nextAction")));
            Map<String,Object> again=identity.login(request);check("repeat_unbound_login_reuses_user_and_requires_identity",Boolean.FALSE.equals(again.get("isNewUser"))&&Objects.equals(first.get("miniUserId"),again.get("miniUserId"))&&Boolean.FALSE.equals(again.get("canEnterHome")));
            repo.state="BOUND";Map<String,Object> bound=identity.login(request);check("bound_login_enters_home",Boolean.FALSE.equals(bound.get("isNewUser"))&&Boolean.TRUE.equals(bound.get("canEnterHome"))&&Boolean.FALSE.equals(bound.get("needIdentityVerification"))&&"ENTER_HOME".equals(bound.get("nextAction"))&&bound.containsKey("employee"));
            repo.state="PENDING_CONFIRMATION";Map<String,Object> pending=identity.state(repo.user);check("pending_confirmation_does_not_enter_home",Boolean.FALSE.equals(pending.get("canEnterHome"))&&"CONFIRM_OR_VERIFY_AGAIN".equals(pending.get("nextAction")));
            repo.state="CORRECTION_PENDING";pending=identity.state(repo.user);check("pending_correction_waits_for_review",Boolean.FALSE.equals(pending.get("canEnterHome"))&&"WAIT_FOR_REVIEW".equals(pending.get("nextAction")));
            check("credentials_and_wechat_identity_not_in_response",!bound.containsKey("openid")&&!bound.containsKey("session_key")&&!bound.containsKey("appSecret"));
            check("login_issues_distinct_short_lived_tokens",!Objects.equals(first.get("token"),again.get("token"))&&Integer.valueOf(7200).equals(bound.get("expiresIn"))&&repo.sessions==3);
            System.out.println("PASS WeChat configuration/login checks: "+checks);
        }finally{Files.deleteIfExists(file);}
    }
}
