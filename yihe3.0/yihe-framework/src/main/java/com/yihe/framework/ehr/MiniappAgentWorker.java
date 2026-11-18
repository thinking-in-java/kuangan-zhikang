package com.yihe.framework.ehr;

import java.util.*;
import java.util.concurrent.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/** Separate bounded pool; queued turns survive restart and never enter the OCR queue. */
@Component
public class MiniappAgentWorker {
    final MiniappAgentStore store;final MiniappAgentService agent;final MiniappIdentityService identity;
    @Value("${ehr.miniapp-agent.enabled:true}") boolean enabled=true;
    @Value("${ehr.miniapp-agent.workers:2}") int workers=2;
    ThreadPoolExecutor pool;ScheduledExecutorService timer;Semaphore slots;
    public MiniappAgentWorker(MiniappAgentStore store,MiniappAgentService agent,MiniappIdentityService identity){this.store=store;this.agent=agent;this.identity=identity;}
    @PostConstruct public void start(){if(!enabled)return;workers=Math.max(1,Math.min(4,workers));slots=new Semaphore(workers);pool=new ThreadPoolExecutor(workers,workers,0,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(workers),r->{Thread t=new Thread(r,"miniapp-health-agent");t.setDaemon(true);return t;});timer=Executors.newSingleThreadScheduledExecutor(r->{Thread t=new Thread(r,"miniapp-agent-dispatch");t.setDaemon(true);return t;});timer.scheduleWithFixedDelay(()->{try{tick();}catch(Exception e){org.slf4j.LoggerFactory.getLogger(getClass()).warn("Personal agent dispatch unavailable; check migration and database");}},12,2,TimeUnit.SECONDS);}
    void tick(){store.expire(identity.dataMode());while(slots.tryAcquire()){boolean submitted=false;try{Map<String,Object> job=store.claim(identity.dataMode());if(job==null)return;pool.execute(()->{try{agent.process(job);}catch(Exception e){org.slf4j.LoggerFactory.getLogger(getClass()).warn("Personal agent worker interrupted; task will expire");}finally{slots.release();}});submitted=true;}finally{if(!submitted)slots.release();}}}
    @PreDestroy public void stop(){if(timer!=null)timer.shutdownNow();if(pool!=null)pool.shutdownNow();}
}
