package com.yihe.framework.ehr;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.*;
import java.nio.file.*;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import static com.yihe.framework.ehr.EhrService.*;

@Service
public class MiniappWechatClient {
    @Value("${ehr.wechat.app-id:${EHR_WECHAT_APP_ID:}}") String appId;
    @Value("${ehr.wechat.app-secret:${EHR_WECHAT_APP_SECRET:}}") String appSecret;
    @Value("${ehr.wechat.test-url:${EHR_WECHAT_TEST_URL:}}") String testUrl;
    @Value("${ehr.data-mode:real}") String mode;
    @Value("${ehr.wechat.credentials-file:${EHR_WECHAT_CREDENTIALS_FILE:F:/upload/.config/wechat-miniapp.json}}") String credentialsFile;

    @PostConstruct public void loadPrivateConfiguration(){
        // Never import real application secrets into a synthetic instance.
        // Explicit environment/property credentials take precedence as a pair.
        if(!"real".equals(mode)||!appId.trim().isEmpty()||!appSecret.trim().isEmpty())return;
        try{
            Path path=Paths.get(credentialsFile);if(!Files.isRegularFile(path))return;
            if(Files.size(path)>4096)throw new IllegalArgumentException();
            Map<?,?> values=new ObjectMapper().readValue(Files.readAllBytes(path),Map.class);
            Object id=values.get("appId"),secret=values.get("appSecret");
            if(!(id instanceof String)||!(secret instanceof String))throw new IllegalArgumentException();
            appId=((String)id).trim();appSecret=((String)secret).trim();
        }catch(Exception ignored){
            appId="";appSecret="";
            // JSON parsing errors can contain the secret. Do not log the exception.
            org.slf4j.LoggerFactory.getLogger(MiniappWechatClient.class).warn("微信私有配置文件无法读取或格式不正确，微信登录暂不可用");
        }
    }
    public String applicationId(){return appId;}
    public boolean configured(){return "real".equals(mode)?appId.matches("wx[0-9a-fA-F]{16}")&&appSecret.matches("[0-9a-fA-F]{32}"):!appId.trim().isEmpty()&&!appSecret.trim().isEmpty();}

    @SuppressWarnings("unchecked")
    public Map<String,Object> exchange(String code){
        require(configured(),503,"微信小程序AppID/AppSecret尚未配置");
        String endpoint="https://api.weixin.qq.com/sns/jscode2session";
        if(!testUrl.isEmpty()){
            URI u=URI.create(testUrl);
            require("synthetic".equals(mode)&&Arrays.asList("127.0.0.1","localhost","::1").contains(u.getHost())&&Arrays.asList("http","https").contains(u.getScheme()),503,"测试微信服务只允许隔离模式的本机地址");
            endpoint=testUrl;
        }
        Map<String,Object> result;
        try{
            SimpleClientHttpRequestFactory f=new SimpleClientHttpRequestFactory();f.setConnectTimeout(3000);f.setReadTimeout(8000);
            URI uri=UriComponentsBuilder.fromHttpUrl(endpoint).queryParam("appid",appId).queryParam("secret",appSecret).queryParam("js_code",code).queryParam("grant_type","authorization_code").build().encode().toUri();
            String response=new RestTemplate(f).getForObject(uri,String.class);
            result=new ObjectMapper().readValue(response,Map.class);
        }catch(Exception ignored){
            // Exceptions can contain the request URL, including appSecret. Do not log them.
            throw new EhrException(503,"微信登录服务暂不可用，请重新获取登录凭证后重试");
        }
        Object error=result.get("errcode");
        String errorCode=error==null?"0":String.valueOf(error);
        if(Arrays.asList("40013","40125","41002","41004").contains(errorCode))throw new EhrException(503,"WECHAT_CONFIGURATION_INVALID：微信应用参数无效，请联系管理员核对AppID和AppSecret");
        if(Arrays.asList("-1","45011","45009").contains(errorCode))throw new EhrException(503,"微信登录服务繁忙，请稍后重新获取登录凭证");
        require(error==null||"0".equals(String.valueOf(error)),401,"微信登录凭证无效、已使用或暂不可用，请重新调用wx.login");
        require(result.get("openid") instanceof String&&!str(result.get("openid")).isEmpty()&&str(result.get("openid")).length()<=128,401,"微信登录身份校验失败");
        // session_key is intentionally discarded; it is never stored or returned.
        return result;
    }
}
