package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import static com.yihe.framework.ehr.EhrAccess.*;

/** Read-only projection of existing work. No shadow tasks or fabricated execution state. */
@Service
public class DigitalWorkerTaskHub {
    private final EhrRepository db;
    private static final List<String> WORKERS=Arrays.asList("archive","assess","follow","plan","care");
    private static final List<String> STATES=Arrays.asList("queued","in_progress","pending_review","completed","failed","cancelled","unknown");
    private static final List<String> SOURCES=Arrays.asList("assignment","ocr","alert");
    public DigitalWorkerTaskHub(EhrRepository db){this.db=db;}
    private static String string(String sql){return "CONVERT("+sql+" USING utf8mb4) COLLATE utf8mb4_unicode_ci";}
    private static String columns(String... expressions){List<String> result=new ArrayList<>();String[] names={"id","source","worker","type","title","status","nativeStatus","priority","recordId","memberCode","memberName"};for(int i=0;i<names.length;i++)result.add(string(expressions[i])+" "+names[i]);return String.join(",",result);}
    String union(EhrActor actor,List<Object> args){
        if(actor.mobile)throw new EhrException(403,"任务总览仅供后台管理使用");
        args.addAll(Arrays.asList(actor.scope,actor.mode,actor.scope,actor.mode));
        String assignment="SELECT "+columns("t.task_id","'assignment'","t.worker_id","t.task_type","t.title","t.task_status","t.task_status","t.priority","t.record_id","t.member_code","p.employee_name")+
            ",t.created_at createdAt,t.updated_at updatedAt,NULL percent,1 canOpen FROM a_digital_worker_tasks t LEFT JOIN a_health_records r ON r.record_id=t.record_id AND r.scope_code=t.scope_code AND r.data_mode=t.data_mode LEFT JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode AND p.deleted_at IS NULL WHERE t.scope_code=? AND t.data_mode=?";
        String ocr="SELECT "+columns("q.job_id","'ocr'","'archive'","'report_ocr'","'体检报告 OCR 识别'","CASE q.dispatch_state WHEN 'queued' THEN 'queued' WHEN 'retry_wait' THEN 'queued' WHEN 'running' THEN 'in_progress' WHEN 'succeeded' THEN 'completed' WHEN 'failed' THEN 'failed' WHEN 'cancelled' THEN 'cancelled' ELSE 'unknown' END","q.dispatch_state","'normal'","r.record_id","p.employee_no","p.employee_name")+
            ",q.created_at createdAt,q.updated_at updatedAt,q.progress_percent percent,CASE WHEN p.employee_id IS NULL THEN 0 ELSE 1 END canOpen FROM a_ocr_dispatch_tasks q LEFT JOIN a_extraction_jobs j ON j.job_id=q.job_id AND j.scope_code=q.scope_code AND j.data_mode=q.data_mode LEFT JOIN a_health_reports h ON h.report_id=j.report_id AND h.revision=j.revision AND h.scope_code=q.scope_code AND h.data_mode=q.data_mode LEFT JOIN a_health_records r ON r.record_id=h.record_id AND r.scope_code=h.scope_code AND r.data_mode=h.data_mode LEFT JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode AND p.deleted_at IS NULL WHERE q.scope_code=? AND q.data_mode=?";
        String sql=assignment+" UNION ALL "+ocr;
        // Legacy device alarms have no real/synthetic column. Never include them in synthetic mode.
        if(actor.doctor&&"real".equals(actor.mode)){
            args.add(actor.user);
            sql+=" UNION ALL SELECT "+columns("a.ID","'alert'","'assess'","'indicator_alert'","CONCAT('指标预警 · ',d.name)","CASE a.STATUS WHEN '0' THEN 'queued' WHEN '1' THEN 'in_progress' WHEN '2' THEN 'completed' ELSE 'unknown' END","a.STATUS","'normal'","NULL","c.user_code","c.name")+
                ",a.TIME createdAt,COALESCE(a.ARCHIVE_TIME,a.ACCEPT_TIME,a.TIME) updatedAt,NULL percent,1 canOpen FROM yh_he_threshold_alarm a JOIN yh_user_reginfo c ON c.user_code=a.MEMBER_ID JOIN yh_he_threshold_type d ON d.id=a.RECORD_ID WHERE EXISTS (SELECT 1 FROM yh_user_make_card mc JOIN yh_user_admin_info ai ON ai.id=mc.doctor WHERE mc.user_code=c.user_code AND ai.user_code=?)";
        }
        return "("+sql+") hub";
    }
    private String filter(Map<String,String> query,List<Object> args){
        String where=" WHERE 1=1";
        for(String[] field:new String[][]{{"worker","worker"},{"source","source"},{"priority","priority"},{"type","type"}}){String value=query.get(field[0]);if(value==null||value.isEmpty())continue;
            boolean valid=field[0].equals("worker")?WORKERS.contains(value):field[0].equals("source")?SOURCES.contains(value):field[0].equals("priority")?Arrays.asList("normal","high","urgent").contains(value):Arrays.asList("report_ocr","indicator_alert").contains(value)||DigitalWorkerTaskProfiles.all().stream().anyMatch(p->value.equals(p.get("type")));
            if(!valid)throw new EhrException(422,"任务筛选条件无效");where+=" AND "+field[1]+"=?";args.add(value);
        }
        String search=query.getOrDefault("search","").trim();if(search.length()>100)throw new EhrException(422,"搜索关键词最多100字");
        if(!search.isEmpty()){where+=" AND (title LIKE ? ESCAPE '!' OR memberCode LIKE ? ESCAPE '!' OR memberName LIKE ? ESCAPE '!' OR id LIKE ? ESCAPE '!')";String keyword="%"+EhrService.like(search)+"%";args.addAll(Arrays.asList(keyword,keyword,keyword,keyword));}
        String from=query.getOrDefault("createdFrom", ""),to=query.getOrDefault("createdTo", "");
        if(!from.isEmpty()) {date(from);where+=" AND createdAt>=?";args.add(from+" 00:00:00");}
        if(!to.isEmpty()) {where+=" AND createdAt<?";args.add(date(to).plusDays(1).toString()+" 00:00:00");}
        if(!from.isEmpty()&&!to.isEmpty()&&date(from).isAfter(date(to)))throw new EhrException(422,"结束日期不能早于开始日期");
        return where;
    }
    private java.time.LocalDate date(String value) {
        if(!value.matches("\\d{4}-\\d{2}-\\d{2}"))throw new EhrException(422,"日期格式无效");
        try{return java.time.LocalDate.parse(value);}catch(RuntimeException error){throw new EhrException(422,"日期无效");}
    }
    private String status(String value,List<Object> args){
        if(value==null||value.isEmpty()||"all".equals(value))return "";
        if("active".equals(value))return " AND status IN ('queued','in_progress','pending_review')";
        if("finished".equals(value))return " AND status IN ('completed','failed','cancelled')";
        if(!STATES.contains(value))throw new EhrException(422,"任务状态无效");args.add(value);return " AND status=?";
    }
    private int page(String raw,int fallback,int max){if(raw==null||raw.isEmpty())return fallback;try{int n=Integer.parseInt(raw);if(n<1||n>max)throw new NumberFormatException();return n;}catch(NumberFormatException error){throw new EhrException(422,"分页参数无效");}}
    private Map<String,Object> empty(){Map<String,Object> m=map("total",0L,"active",0L);for(String state:STATES)m.put(state,0L);return m;}
    private void add(Map<String,Object> summary,String state,long count){summary.put("total",((Number)summary.get("total")).longValue()+count);summary.put(state,((Number)summary.get(state)).longValue()+count);if(Arrays.asList("queued","in_progress","pending_review").contains(state))summary.put("active",((Number)summary.get("active")).longValue()+count);}
    private Map<String,Object> aggregate(String from,String where,List<Object> args){
        Map<String,Object> summary=empty(),byWorker=new LinkedHashMap<>(),bySource=new LinkedHashMap<>(),bySourceActive=new LinkedHashMap<>();for(String w:WORKERS)byWorker.put(w,empty());for(String s:SOURCES){bySource.put(s,0L);bySourceActive.put(s,0L);}
        for(Map<String,Object> row:db.all("SELECT worker,source,status,COUNT(*) count FROM "+from+where+" GROUP BY worker,source,status",args.toArray())){String state=text(row,"status");long count=((Number)row.get("count")).longValue();add(summary,state,count);@SuppressWarnings("unchecked") Map<String,Object> worker=(Map<String,Object>)byWorker.get(text(row,"worker"));if(worker!=null)add(worker,state,count);String source=text(row,"source");bySource.put(source,((Number)bySource.get(source)).longValue()+count);if(Arrays.asList("queued","in_progress","pending_review").contains(state))bySourceActive.put(source,((Number)bySourceActive.get(source)).longValue()+count);}
        return map("summary",summary,"byWorker",byWorker,"bySource",bySource,"bySourceActive",bySourceActive,"capturedAt",now());
    }
    @Transactional(readOnly=true,isolation=Isolation.REPEATABLE_READ)
    public Map<String,Object> summary(EhrActor actor){List<Object> args=new ArrayList<>();String from=union(actor,args);return aggregate(from,"",args);}
    @Transactional(readOnly=true,isolation=Isolation.REPEATABLE_READ)
    public Map<String,Object> list(EhrActor actor,Map<String,String> query){
        List<Object> args=new ArrayList<>();String from=union(actor,args),where=filter(query,args);Map<String,Object> result=aggregate(from,where,args);
        where+=status(query.get("status"),args);long total=db.count("SELECT COUNT(*) FROM "+from+where,args.toArray());
        int page=page(query.get("page"),1,100000),limit=page(query.get("limit"),12,100);String sort=query.getOrDefault("sort","updated");
        String order="updated".equals(sort)?"updatedAt DESC,source,id DESC":"created".equals(sort)?"createdAt DESC,source,id DESC":"priority".equals(sort)?"FIELD(priority,'urgent','high','normal'),updatedAt DESC,source,id DESC":null;
        if(order==null)throw new EhrException(422,"排序方式无效");args.add(limit);args.add((page-1)*limit);
        result.putAll(map("items",db.all("SELECT * FROM "+from+where+" ORDER BY "+order+" LIMIT ? OFFSET ?",args.toArray()),"total",total,"page",page,"limit",limit));return result;
    }
}
