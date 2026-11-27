package com.yihe.framework.ehr;
import java.util.*;import java.util.concurrent.*;import javax.annotation.*;import org.springframework.stereotype.Component;
import static com.yihe.framework.ehr.EhrAccess.*;import static com.yihe.framework.ehr.EhrService.*;

/** Bounded reconciliation: archive/report, roster, exam review and rule changes are inputs. */
@Component public class OccupationalAutomationService {
 final OccupationalRiskService service;final EhrRepository db;final EhrService ehr;final DigitalWorkerModelClient client;final DigitalWorkerSettings settings;final ScheduledExecutorService scheduler=Executors.newSingleThreadScheduledExecutor(r->{Thread t=new Thread(r,"occupational-auto-reconcile");t.setDaemon(true);return t;});
 @org.springframework.beans.factory.annotation.Value("${ehr.data-mode:real}") String mode="real";
 volatile long lastTick;volatile int queued;volatile boolean error;int offset;
 @org.springframework.beans.factory.annotation.Autowired public OccupationalAutomationService(OccupationalRiskService service,EhrRepository db,EhrService ehr,DigitalWorkerModelClient client){this.service=service;this.db=db;this.ehr=ehr;this.client=client;this.settings=new DigitalWorkerSettings(db);}
 public OccupationalAutomationService(OccupationalRiskService service){this(service,service.db,service.ehr,service.client);}
 @PostConstruct public void start(){scheduler.scheduleWithFixedDelay(this::tick,30,30,TimeUnit.SECONDS);}
 public synchronized void tick(){int added=0;try{if(ehr.candidateOnly||!Boolean.TRUE.equals(client.status().get("configured")))return;
  List<Map<String,Object>> candidates=db.all("SELECT DISTINCT r.record_id recordId,r.scope_code scope,(SELECT CAST(MIN(u.user_code) AS CHAR) FROM yh_user_reginfo u WHERE u.lock_flag=2 AND u.User_Type=4 AND CONCAT('org:',u.Org_Code)=r.scope_code) principal FROM a_health_records r JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode JOIN a_health_reports h ON h.record_id=r.record_id AND h.scope_code=r.scope_code AND h.data_mode=r.data_mode JOIN a_report_series s ON s.report_id=h.report_id AND s.current_revision=h.revision WHERE r.data_mode=? AND p.deleted_at IS NULL AND (EXISTS(SELECT 1 FROM a_report_archive_entries x WHERE x.report_id=h.report_id AND x.revision=h.revision AND x.scope_code=r.scope_code AND x.data_mode=r.data_mode AND x.archive_status IN ('archived','partial')) OR EXISTS(SELECT 1 FROM a_report_publications x WHERE x.report_id=h.report_id AND x.revision=h.revision AND x.scope_code=r.scope_code AND x.data_mode=r.data_mode)) ORDER BY r.record_id LIMIT 5 OFFSET ?",mode,offset);
  offset=candidates.size()<5?0:offset+5;
  for(Map<String,Object> row:candidates){if(added>=2)break;String scope=str(row.get("scope")),principal=str(row.get("principal"));if(principal.isEmpty()||!scope.startsWith("org:"))continue;EhrActor actor=new EhrActor(principal,scope.substring(4),mode,true,false);try{if(reconcile(actor,str(row.get("recordId"))))added++;}catch(EhrException unavailable){/* Fail closed; other authorized records can proceed. */}}
  queued=added;error=false;lastTick=System.currentTimeMillis();
 }catch(RuntimeException failure){error=true;lastTick=System.currentTimeMillis();}}
 @SuppressWarnings("unchecked") boolean reconcile(EhrActor actor,String record){
  Map<String,Object> rules=service.rules(actor),doc=(Map<String,Object>)rules.get("document");if(Boolean.FALSE.equals(doc.get("autoEvaluation")))return false;
  Map<String,Object> config=settings.get(actor,"assess");settings.requireAi(config);settings.admit(actor,"assess","assessment",config);
  int year=java.time.LocalDate.now().getYear();Map<String,Object> prior=db.one("SELECT * FROM a_occupational_risk_tasks WHERE scope_code=? AND data_mode=? AND record_id=? AND assessment_year=? ORDER BY created_at DESC,task_id DESC LIMIT 1",actor.scope,actor.mode,record,year);
  if(prior!=null&&Arrays.asList("queued","running").contains(prior.get("ai_state")))return false;
  Map<String,Object> source=service.source(actor,record);String digest=service.sourceHash(source);
  if(prior!=null&&OccupationalScreening.FORMAT.equals(prior.get("report_format"))&&digest.equals(prior.get("source_digest"))&&num(prior.get("rules_version"))==num(rules.get("version")))return false;
  service.enqueue(actor,map("year",year,"requestKey",id(),"records",Arrays.asList(record)));return true;
 }
 public Map<String,Object> health(){return map("lastTick",lastTick==0?null:lastTick,"schedulerReady",lastTick>0&&System.currentTimeMillis()-lastTick<90000&&!error,"schedulerError",error,"queuedLastScan",queued,"scanSeconds",30,"batchLimit",2,"sourcePolicy","已入档且有可信检查数据自动接单；岗位、报告、体检评估或规则变化后重评；相同来源失败不无限重试");}
 @PreDestroy public void stop(){scheduler.shutdownNow();}
}
