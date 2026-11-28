package com.yihe.framework.ehr;
import java.util.*;import java.util.concurrent.*;import javax.annotation.*;
import org.springframework.stereotype.Component;import org.springframework.transaction.PlatformTransactionManager;import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrService.*;
/** No model call in this scheduler: it dispatches only explicitly approved assessment policies. */
@Component public class PsychologyCoordinatorWorker {
 final PsychologyCoordinator service;final EhrRepository db;final TransactionTemplate tx;
 final ScheduledExecutorService clock=Executors.newSingleThreadScheduledExecutor();
 public PsychologyCoordinatorWorker(PsychologyCoordinator s,EhrRepository d,PlatformTransactionManager m){service=s;db=d;tx=new TransactionTemplate(m);}
 @PostConstruct public void start(){clock.scheduleWithFixedDelay(this::tick,20,60,TimeUnit.SECONDS);}
 void tick(){try{
  if(db.count("SELECT COUNT(*) FROM information_schema.tables WHERE table_schema=DATABASE() AND table_name='a_psych_worker_policies'")==0)return;
  for(Map<String,Object>p:db.all("SELECT policy_id FROM a_psych_worker_policies WHERE enabled=1 AND next_run_at<=CONVERT_TZ(UTC_TIMESTAMP(),'+00:00','+08:00') ORDER BY next_run_at LIMIT 10")){
   String pid=str(p.get("policy_id"));try{tx.execute(t->{Map<String,Object>locked=db.one("SELECT * FROM a_psych_worker_policies WHERE policy_id=? AND enabled=1 AND next_run_at<=CONVERT_TZ(UTC_TIMESTAMP(),'+00:00','+08:00') FOR UPDATE",pid);if(locked!=null)service.executePolicy(locked);return null;});}
   catch(RuntimeException ex){db.exec("UPDATE a_psych_worker_policies SET last_error='POLICY_EXECUTION_FAILED_REQUIRES_REVIEW',enabled=0,row_version=row_version+1,updated_at=UTC_TIMESTAMP(6) WHERE policy_id=?",pid);}
  }
  for(Map<String,Object>s:db.all("SELECT scope_code,data_mode FROM a_questionnaire_center_state")){String scope=str(s.get("scope_code"));if(!scope.startsWith("org:"))continue;EhrActor a=new EhrActor("system:psychology",scope.substring(4),str(s.get("data_mode")),true,false);tx.execute(t->{service.reconcile(a);return null;});}
 }catch(RuntimeException ex){org.slf4j.LoggerFactory.getLogger(getClass()).warn("Psychology coordinator tick failed: {}",ex.getClass().getSimpleName());}}
 @PreDestroy public void stop(){clock.shutdownNow();}
}
