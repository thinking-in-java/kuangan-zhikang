package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Unified counts, source identities, scope isolation and filters against rollback-only SQL fixtures. */
public class DigitalWorkerHubChecks {
    static int checks;
    static void check(String name,boolean ok){if(!ok)throw new AssertionError(name);checks++;System.out.println("PASS "+name);}
    static void reject(String name,Runnable call){try{call.run();throw new AssertionError(name);}catch(EhrException e){check(name,e.status==422||e.status==403);}}
    @SuppressWarnings("unchecked") static Map<String,Object> summary(Map<String,Object> result){return (Map<String,Object>)result.get("summary");}
    @SuppressWarnings("unchecked") static List<Map<String,Object>> items(Map<String,Object> result){return (List<Map<String,Object>>)result.get("items");}
    @SuppressWarnings("unchecked") public static void main(String[] args){
        DriverManagerDataSource ds=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));EhrRepository db=new EhrRepository(ds);EhrService ehr=new EhrService(db);DigitalWorkerTaskService tasks=new DigitalWorkerTaskService(db,new DigitalWorkerArchiveContext(db,ehr));DigitalWorkerTaskHub hub=new DigitalWorkerTaskHub(db);
        String run=id().substring(0,8);EhrActor a=new EhrActor("LOCAL-PREVIEW","HUB_"+run,"synthetic",true,false),cross=new EhrActor(a.user,"OTHER_"+run,"synthetic",true,false),real=new EhrActor("no-legacy-doctor-"+run,a.scope.substring(4),"real",true,false);
        new TransactionTemplate(new DataSourceTransactionManager(ds)).execute(tx->{try{
            Map<String,Object> p=HealthAgentChecks.fixture(ehr,a,"HUB_"+run);String record=text(p,"id");String[] workers={"archive","assess","follow","plan","care"},states={"queued","in_progress","pending_review","completed","cancelled"};
            for(String worker:workers)for(String state:states){Map<String,Object> task=tasks.create(a,map("worker",worker,"type","routine_followup","title","合成 "+worker+" "+state,"recordId",record,"priority","pending_review".equals(state)?"high":"normal","requestKey",id()));db.exec("UPDATE a_digital_worker_tasks SET task_status=? WHERE task_id=?",state,task.get("id"));}
            for(String state:new String[]{"queued","running","retry_wait","succeeded","failed","cancelled"}){
                String report=HealthAgentChecks.report(db,ehr,a,record),job=id();db.insert("a_extraction_jobs",ehr.scoped(a,"job_id",job,"report_id",report,"revision",1,"request_key",id(),"pipeline_version","hub-fixture","status","uploaded"));
                Map<String,Object> q=ehr.scoped(a,"job_id",job,"dispatch_state",state,"available_at",now(),"created_at",now(),"updated_at",now());if("running".equals(state))q.putAll(map("current_attempt_id",id(),"worker_id","synthetic-worker","lease_token_hash",digest(bytes("synthetic")),"lease_until","2099-01-01 00:00:00"));db.insert("a_ocr_dispatch_tasks",q);
            }
            Map<String,Object> all=hub.list(a,new HashMap<>());Map<String,Object> stats=summary(all);check("all_five_workers_and_ocr_in_one_pool",num(all.get("total"))==31&&num(stats.get("total"))==31);
            check("global_active_count_includes_pending_review_and_retry",num(stats.get("active"))==18&&num(stats.get("pending_review"))==5);
            Map<String,Object> by=(Map<String,Object>)all.get("byWorker");for(String worker:workers)check("worker_totals_"+worker,num(((Map<?,?>)by.get(worker)).get("total"))==(worker.equals("archive")?11:5));
            Map<?,?> source=(Map<?,?>)all.get("bySource");check("source_counts_keep_independent_work",num(source.get("assignment"))==25&&num(source.get("ocr"))==6&&num(source.get("alert"))==0);
            check("summary_agrees_with_list",summary(hub.summary(a)).equals(stats));
            check("filtered_status_does_not_destroy_status_facets",num(hub.list(a,Collections.singletonMap("status","pending_review")).get("total"))==5&&num(summary(hub.list(a,Collections.singletonMap("status","pending_review"))).get("total"))==31);
            Map<String,String> q=new HashMap<>();q.put("worker","care");q.put("status","pending_review");check("worker_and_status_combination",num(hub.list(a,q).get("total"))==1);q.clear();q.put("source","ocr");q.put("status","queued");check("ocr_retry_wait_normalized_but_native_state_kept",num(hub.list(a,q).get("total"))==2&&items(hub.list(a,q)).stream().anyMatch(row->"retry_wait".equals(row.get("nativeStatus"))));
            q.clear();q.put("status","failed");check("failed_ocr_visible",num(hub.list(a,q).get("total"))==1);q.clear();q.put("priority","high");check("priority_filter",num(hub.list(a,q).get("total"))==5);q.clear();q.put("type","routine_followup");check("type_filter",num(hub.list(a,q).get("total"))==25);
            q.clear();q.put("search",text(p,"code"));check("employee_code_search",num(hub.list(a,q).get("total"))==31);q.put("search","智能体隔离测试");check("employee_name_search",num(hub.list(a,q).get("total"))==31);q.put("search","合成 care");check("title_search",num(hub.list(a,q).get("total"))==5);q.put("search","%");check("wildcard_search_escaped",num(hub.list(a,q).get("total"))==0);
            q.clear();q.put("limit","4");q.put("page","1");Set<String> ids=new HashSet<>();for(Map<String,Object> row:items(hub.list(a,q)))ids.add(row.get("source")+":"+row.get("id"));q.put("page","2");List<Map<String,Object>> second=items(hub.list(a,q));check("stable_paginated_source_identity",second.size()==4&&second.stream().noneMatch(row->ids.contains(row.get("source")+":"+row.get("id"))));
            check("cross_org_tasks_and_ocr_not_visible",num(hub.list(cross,new HashMap<>()).get("total"))==0);check("real_mode_does_not_read_synthetic_tasks",num(hub.list(real,new HashMap<>()).get("total"))==0);
            reject("bad_worker_rejected",()->hub.list(a,Collections.singletonMap("worker","sql")));reject("bad_status_rejected",()->hub.list(a,Collections.singletonMap("status","broken")));reject("bad_sort_rejected",()->hub.list(a,Collections.singletonMap("sort","id;drop")));reject("bad_page_rejected",()->hub.list(a,Collections.singletonMap("page","-1")));reject("bad_type_rejected",()->hub.list(a,Collections.singletonMap("type","unknown")));reject("mobile_hub_denied",()->hub.summary(new EhrActor(a.user,a.scope.substring(4),a.mode,false,true)));
            long before=db.count("SELECT COUNT(*) FROM a_digital_worker_tasks WHERE scope_code=?",a.scope);hub.summary(a);hub.list(a,new HashMap<>());check("projection_does_not_create_shadow_tasks",before==db.count("SELECT COUNT(*) FROM a_digital_worker_tasks WHERE scope_code=?",a.scope));
            db.exec("UPDATE a_employee_roster_profiles SET deleted_at=UTC_TIMESTAMP(6) WHERE employee_id=?",p.get("employee_id"));Map<String,Object> deleted=hub.list(a,new HashMap<>());check("deleted_employee_task_audit_retained_without_identity_or_original_access",num(deleted.get("total"))==31&&items(deleted).stream().allMatch(row->row.get("memberName")==null)&&items(hub.list(a,Collections.singletonMap("source","ocr"))).stream().allMatch(row->num(row.get("canOpen"))==0));
            return null;
        }finally{tx.setRollbackOnly();}});
        check("hub_fixtures_rolled_back",db.count("SELECT COUNT(*) FROM a_digital_worker_tasks WHERE scope_code=?",a.scope)==0);System.out.println("PASS Digital worker hub checks: "+checks+"; all database writes rolled back");
    }
}
