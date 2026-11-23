package com.yihe.framework.ehr;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import static com.yihe.framework.ehr.EhrAccess.map;
/** Private configuration; public status omits credentials and local paths. */
@Component
public class ModelRecognitionSettings {
    @Value("${ehr.recognition.provider:worker}") String provider;
    @Value("${ehr.recognition.config-file:D:/update/.config/model-recognition.json}") String configFile;
    @Value("${ehr.recognition.worker-token-file:D:/update/.config/model-worker.token}") String tokenFile;
    boolean isModel(){return "model".equals(provider);}
    @SuppressWarnings("unchecked") Map<String,Object> read(){try{Path p=Paths.get(configFile);if(Files.isRegularFile(p)&&Files.size(p)<16384)return new ObjectMapper().readValue(Files.readAllBytes(p),Map.class);}catch(Exception ignored){}return Collections.emptyMap();}
    boolean enabled(){return isModel()&&Boolean.TRUE.equals(read().get("enabled"));}
    String token(){try{Path p=Paths.get(tokenFile);return Files.isRegularFile(p)?new String(Files.readAllBytes(p),java.nio.charset.StandardCharsets.UTF_8).trim():"";}catch(Exception ignored){return "";}}
    private int bounded(Map<String,Object> config,String key,int fallback,int maximum){try{return Math.max(1,Math.min(maximum,Integer.parseInt(String.valueOf(config.getOrDefault(key,fallback)))));}catch(Exception invalid){return fallback;}}
    public Map<String,Object> status(){Map<String,Object> c=read();return map("provider",isModel()?"model":"worker","enabled",enabled(),"model",isModel()?c.get("model"):null,"maxPages",c.getOrDefault("maxPages",40),"concurrentReports",bounded(c,"concurrentReports",3,8),"maxTotalRequests",bounded(c,"maxTotalRequests",12,24),"automaticRedaction",false,"reviewRequired",true,"legacyWorkerPaused",isModel());}
}
