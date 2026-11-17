package com.yihe.framework.ehr;
import java.nio.file.*;
import java.util.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import static com.yihe.framework.ehr.EhrAccess.map;
/** Provider switch, credentials and candidate metadata; no database access. */
public class ModelRoutingChecks {
    static int checks;
    static void check(boolean v){if(!v)throw new AssertionError();checks++;}
    static void reject(Runnable r,int code){try{r.run();throw new AssertionError();}catch(EhrException e){check(e.status==code);}}
    public static void main(String[] args)throws Exception{
        Path c=Files.createTempFile("model-config-check",".json"),t=Files.createTempFile("model-token-check",".txt");
        try{
            String key="test-model-token-0000000000000000000000000000";Files.write(t,key.getBytes("UTF-8"));Files.write(c,"{\"enabled\":false,\"model\":\"example\",\"apiKey\":\"never-expose\"}".getBytes("UTF-8"));
            ModelRecognitionSettings settings=new ModelRecognitionSettings();settings.provider="model";settings.configFile=c.toString();settings.tokenFile=t.toString();
            EhrRepository repo=new EhrRepository(new DriverManagerDataSource());EhrService service=new EhrService(repo);OcrWorkerQueue q=new OcrWorkerQueue(repo,service,new EhrOcrService(repo,service));q.recognition=settings;
            check(Boolean.TRUE.equals(q.claim(map("workerId","b-worker","requestKey",UUID.randomUUID().toString())).get("paused")));
            reject(()->q.claimForModel(map()),503);reject(()->q.authorizeModel("Bearer wrong-key"),401);
            q.authorizeModel("Bearer "+key);check(true);
            Map<String,Object> status=settings.status();check(!status.containsKey("apiKey")&&!status.containsKey("configFile")&&!status.toString().contains("never-expose"));
            check(Boolean.TRUE.equals(status.get("legacyWorkerPaused"))&&Boolean.TRUE.equals(status.get("reviewRequired")));
            check(Boolean.FALSE.equals(status.get("automaticRedaction")));
            System.out.println("PASS "+checks+" model routing checks; no task claimed or database accessed");
        }finally{Files.deleteIfExists(c);Files.deleteIfExists(t);}
    }
}
