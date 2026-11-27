package com.yihe.framework.ehr;
import java.util.*;import java.util.concurrent.*;import java.util.concurrent.atomic.AtomicInteger;import javax.annotation.PreDestroy;import javax.annotation.PostConstruct;import org.springframework.stereotype.Component;
import static com.yihe.framework.ehr.EhrAccess.*;import static com.yihe.framework.ehr.EhrService.*;
@Component
public class ChronicRiskWorker {
 final ChronicRiskService service;final DigitalWorkerModelClient client;final AtomicInteger active=new AtomicInteger();final ExecutorService pool=Executors.newFixedThreadPool(2,r->{Thread t=new Thread(r,"chronic-risk-analysis");t.setDaemon(true);return t;});final ScheduledExecutorService scheduler=Executors.newSingleThreadScheduledExecutor(r->{Thread t=new Thread(r,"chronic-risk-poller");t.setDaemon(true);return t;});volatile long lastTick;volatile boolean healthy;volatile boolean schedulerError;
 @org.springframework.beans.factory.annotation.Value("${ehr.data-mode:real}") String mode="real";
 public ChronicRiskWorker(ChronicRiskService service,DigitalWorkerModelClient client){this.service=service;this.client=client;}
 @PostConstruct public void start(){scheduler.scheduleWithFixedDelay(this::tick,0,2,TimeUnit.SECONDS);}
 public void tick(){try{while(active.get()<2&&!pool.isShutdown()){Map<String,Object> job=service.claim(mode);if(job==null)break;active.incrementAndGet();pool.submit(()->{try{process(job);}finally{active.decrementAndGet();}});}healthy=true;schedulerError=false;lastTick=System.currentTimeMillis();}catch(RuntimeException e){healthy=false;schedulerError=true;}}
 void process(Map<String,Object> task){long started=System.currentTimeMillis();Map<String,Object> result=null;String error=null;try{result=client.generate(service.modelInput(task));}catch(EhrException e){error=e.getMessage().matches("[A-Z0-9_]{1,80}")?e.getMessage():"AI_ACCESS_OR_CONFIG_UNAVAILABLE";}catch(RuntimeException e){error="AI_REQUEST_FAILED";}try{service.finishResponse(task,result,error,System.currentTimeMillis()-started);}catch(RuntimeException e){service.completionFailure(task,System.currentTimeMillis()-started);}}
 public Map<String,Object> health(){return map("configured",client.status().get("configured"),"schedulerReady",healthy&&System.currentTimeMillis()-lastTick<15000,"schedulerError",schedulerError,"active",active.get(),"concurrency",2,"lastTick",lastTick==0?null:lastTick);}
 @PreDestroy public void stop(){scheduler.shutdownNow();pool.shutdownNow();}
}
