package com.yihe.framework.ehr;

import java.util.*;
import java.nio.file.*;
import java.net.URI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Independent agent overrides; reuse the already configured Responses provider when omitted. */
@Component
public class HealthAgentSettings {
    final ModelRecognitionSettings recognition;
    @Value("${ehr.agent.config-file:D:/update/.config/health-agent.json}") String configFile;
    public HealthAgentSettings(ModelRecognitionSettings recognition){this.recognition=recognition;}
    @SuppressWarnings("unchecked") Map<String,Object> read(){
        Map<String,Object> c=new LinkedHashMap<>(recognition.read());
        try{Path p=Paths.get(configFile);if(Files.isRegularFile(p)&&Files.size(p)<16384)c.putAll(new ObjectMapper().readValue(Files.readAllBytes(p),Map.class));}
        catch(Exception e){throw new EhrException(503,"智能体配置无法读取，请管理员检查配置文件");}
        String key=System.getenv("EHR_AGENT_API_KEY");if(key!=null&&!key.isEmpty())c.put("apiKey",key);
        return c;
    }
    Map<String,Object> validated(){
        Map<String,Object> c=read();
        require(Boolean.TRUE.equals(c.get("enabled"))&&!text(c,"apiKey").isEmpty()&&!text(c,"model").isEmpty(),503,"数字健康师尚未配置大模型接口");
        try{URI u=URI.create(text(c,"baseUrl"));require("https".equals(u.getScheme())&&u.getHost()!=null&&u.getUserInfo()==null&&u.getQuery()==null&&u.getFragment()==null,503,"模型服务地址须为HTTPS接口地址");}
        catch(IllegalArgumentException e){throw new EhrException(503,"模型接口地址不合法");}
        return c;
    }
    Map<String,Object> status(){Map<String,Object> c=read();return map("enabled",Boolean.TRUE.equals(c.get("enabled"))&&!text(c,"apiKey").isEmpty(),"model",c.get("model"),"apiStyle","responses","confirmationRequired",true);}
}
