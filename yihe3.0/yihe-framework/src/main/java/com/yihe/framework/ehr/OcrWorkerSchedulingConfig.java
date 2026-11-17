package com.yihe.framework.ehr;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.util.concurrent.*;
@Configuration
public class OcrWorkerSchedulingConfig {
    // The local profile intentionally disables legacy @Scheduled business jobs.
    // Run only our lease housekeeping through the transactional queue proxy.
    @Bean(destroyMethod="shutdown")
    public ScheduledExecutorService ocrLeaseReaper(OcrWorkerQueue queue,ReportFileCleanup cleanup){
        ScheduledExecutorService executor=Executors.newSingleThreadScheduledExecutor(r->{Thread t=new Thread(r,"ocr-lease-reaper");t.setDaemon(true);return t;});
        executor.scheduleWithFixedDelay(()->{try{queue.reclaimExpired();}catch(Exception e){org.slf4j.LoggerFactory.getLogger(OcrWorkerSchedulingConfig.class).warn("OCR lease housekeeping failed; it will retry on the next cycle");}},15,5,TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(()->{try{cleanup.run();}catch(Exception e){org.slf4j.LoggerFactory.getLogger(OcrWorkerSchedulingConfig.class).warn("Report file erasure will retry on the next cycle");}},10,3,TimeUnit.SECONDS);return executor;
    }
}
