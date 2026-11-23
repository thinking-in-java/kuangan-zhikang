package com.yihe.framework.ehr;

import java.util.*;
import java.util.concurrent.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DigitalWorkerAiRunner {
    private final DigitalWorkerAiQueue queue;
    private final DigitalWorkerModelClient client;
    private final DigitalWorkerOriginalCheck originals;
    private final DigitalWorkerTaskResults taskResults;
    public DigitalWorkerAiRunner(DigitalWorkerAiQueue queue,DigitalWorkerModelClient client,DigitalWorkerOriginalCheck originals,DigitalWorkerTaskResults taskResults) {this.queue=queue;this.client=client;this.originals=originals;this.taskResults=taskResults;}
    @Bean(destroyMethod="shutdownNow")
    public ScheduledExecutorService digitalWorkerAiExecutor() {
        ScheduledExecutorService executor=Executors.newSingleThreadScheduledExecutor(r->{
            Thread thread=new Thread(r,"digital-worker-model");thread.setDaemon(true);return thread;
        });
        executor.scheduleWithFixedDelay(this::runOne,5,3,TimeUnit.SECONDS);
        return executor;
    }
    @SuppressWarnings("unchecked")
    private void runOne() {
        try {
            for (Map<String,Object> expired:queue.expired()) queue.finish(String.valueOf(expired.get("attempt_id")),null,"MODEL_WORKER_INTERRUPTED");
            if (!Boolean.TRUE.equals(client.status().get("configured"))) return;
            Map<String,Object> attempt=queue.claim();
            if (attempt==null) return;
            Map<String,Object> input=(Map<String,Object>)attempt.get("input");
            if (input==null||!"in_progress".equals(input.get("task_status"))||
                ((Number)input.get("row_version")).intValue()!=((Number)attempt.get("task_version")).intValue()) {
                queue.finish(String.valueOf(attempt.get("attempt_id")),null,"MODEL_TASK_CHANGED");
                return;
            }
            Map<String,Object> result=null;
            String failure=null;
            try {Map<String,Object> prepared=queue.modelInput(attempt);result=DigitalWorkerOriginalCheck.applies(prepared)?originals.verify(prepared):taskResults.generate(prepared);}
            catch (EhrException error) {
                failure=error.getMessage().matches("MODEL_[A-Z0-9_]+")?error.getMessage():"MODEL_CONFIGURATION";
            }
            queue.finish(String.valueOf(attempt.get("attempt_id")),result,failure);
        } catch (Exception error) {
            org.slf4j.LoggerFactory.getLogger(getClass()).warn("Digital worker AI queue will retry after a processing failure");
        }
    }
}
