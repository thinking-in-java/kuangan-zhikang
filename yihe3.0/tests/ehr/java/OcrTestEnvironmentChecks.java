package com.yihe.framework.ehr;

import java.nio.file.*;
import java.util.*;
import java.lang.reflect.Field;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import static com.yihe.framework.ehr.EhrAccess.map;

/** Offline isolation checks: no HTTP server, task claims, uploads, or database access. */
public final class OcrTestEnvironmentChecks {
    static int checks;
    static void set(Object o,String key,Object value)throws Exception{Field f=o.getClass().getDeclaredField(key);f.setAccessible(true);f.set(o,value);}
    static void check(String name,boolean ok){if(!ok)throw new AssertionError(name);checks++;System.out.println("PASS "+name);}
    static void reject(String name,Runnable action,int status){try{action.run();throw new AssertionError(name);}catch(EhrException e){check(name,e.status==status);}}
    public static void main(String[] args)throws Exception{
        EhrRepository repo=new EhrRepository(new DriverManagerDataSource());EhrService ehr=new EhrService(repo);
        OcrWorkerQueue queue=new OcrWorkerQueue(repo,ehr,new EhrOcrService(repo,ehr));
        set(queue,"workerToken","");set(queue,"tokenFile","");set(queue,"configuredLease",600);set(queue,"candidateOnly",true);set(queue,"mode","synthetic");
        String test=new String(Files.readAllBytes(Paths.get("F:/upload/.config/ocr-worker.synthetic.token")),"US-ASCII").trim();
        String real=new String(Files.readAllBytes(Paths.get("F:/upload/.config/ocr-worker.token")),"US-ASCII").trim();
        check("test_token_distinct_from_production",test.length()>=32&&!test.equals(real));
        queue.authorize("Bearer "+test);check("synthetic_token_authorized",true);
        reject("production_token_rejected_by_synthetic",()->queue.authorize("Bearer "+real),401);
        Map<String,Object> env=queue.environment();check("environment_declares_synthetic_candidate_only","synthetic".equals(env.get("data_mode"))&&Boolean.TRUE.equals(env.get("candidateOnly")));
        check("lease_and_heartbeat_are_600_and_200",Integer.valueOf(600).equals(env.get("leaseSeconds"))&&Integer.valueOf(200).equals(env.get("heartbeatSeconds")));
        set(queue,"mode","real");queue.authorize("Bearer "+real);check("real_token_remains_authorized",true);
        reject("synthetic_token_rejected_by_production",()->queue.authorize("Bearer "+test),401);
        ehr.candidateOnly=true;EhrActor a=new EhrActor("offline-check","10000","synthetic",true,false);
        reject("test_environment_cannot_approve",()->ehr.transition(a,"not-created",1,map("action","approve","requestKey",UUID.randomUUID().toString(),"note","offline guard check")),403);
        reject("test_environment_cannot_publish",()->ehr.transition(a,"not-created",1,map("action","archive","requestKey",UUID.randomUUID().toString(),"note","offline guard check")),403);
        System.out.println("PASS "+checks+" offline isolation checks; no task created");
    }
}
