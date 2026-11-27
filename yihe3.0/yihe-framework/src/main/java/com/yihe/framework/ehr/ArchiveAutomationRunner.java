package com.yihe.framework.ehr;
import java.util.*;import java.util.concurrent.*;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.Value;
@Configuration public class ArchiveAutomationRunner {
 final ArchiveAutomationService service;final EhrRepository db;
 @Value("${ehr.data-mode:real}") String mode;
 public ArchiveAutomationRunner(ArchiveAutomationService s,EhrRepository d){service=s;db=d;}
 @Bean(destroyMethod="shutdownNow") public ScheduledExecutorService archiveAutomationExecutor(){ScheduledExecutorService e=Executors.newSingleThreadScheduledExecutor(r->{Thread t=new Thread(r,"archive-xiaogu-workflow");t.setDaemon(true);return t;});e.scheduleWithFixedDelay(this::tick,5,3,TimeUnit.SECONDS);return e;}
 void tick(){try{for(Map<String,Object> r:db.all("SELECT report_id,revision FROM a_archive_automation WHERE data_mode=? AND workflow_state NOT IN ('completed','cancelled') AND (retry_after IS NULL OR retry_after<=UTC_TIMESTAMP(6)) ORDER BY updated_at,report_id LIMIT 20",mode)){String id=String.valueOf(r.get("report_id"));int rev=EhrService.num(r.get("revision"));try{service.process(id,rev);}catch(Exception error){service.failure(id,rev,error instanceof EhrException?error.getMessage():"自动整理暂未完成，请稍后重试");org.slf4j.LoggerFactory.getLogger(getClass()).warn("Archive workflow {} revision {} deferred: {}",id,rev,error.getClass().getSimpleName());}}}catch(Exception e){org.slf4j.LoggerFactory.getLogger(getClass()).warn("Archive automation queue unavailable; will retry");}}
}
