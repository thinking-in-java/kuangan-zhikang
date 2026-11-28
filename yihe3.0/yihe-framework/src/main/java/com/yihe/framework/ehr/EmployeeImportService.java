package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DuplicateKeyException;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Atomic employee creation with a persisted, scoped, idempotent import receipt. */
@Service
public class EmployeeImportService {
    private final EhrRepository db;private final EhrService ehr;
    public EmployeeImportService(EhrRepository db,EhrService ehr){this.db=db;this.ehr=ehr;}
    private static final String ACTION="employee_import_batch";
    @SuppressWarnings("unchecked")
    private Map<String,Object> receipt(Map<String,Object> audit,boolean replayed){
        Map<String,Object> data=(Map<String,Object>)ehr.decode(audit.get("redacted_diff"));
        return map("batchId",audit.get("audit_id"),"filename",data.get("filename"),"imported",data.get("imported"),"recordIds",data.get("recordIds"),"time",audit.get("time"),"replayed",replayed);
    }
    private Map<String,Object> find(EhrActor a,String request){return db.one("SELECT *,DATE_FORMAT(occurred_at,'%Y-%m-%dT%H:%i:%sZ') time FROM a_health_archive_audit_logs WHERE scope_code=? AND data_mode=? AND action=? AND request_key=? FOR UPDATE",a.scope,a.mode,ACTION,request);}
    @Transactional
    @SuppressWarnings("unchecked")
    public Map<String,Object> commit(EhrActor a,Map<String,Object> body){
        require(!a.mobile,403,"个人端不能导入企业花名册");
        String request=text(body,"requestKey"),filename=text(body,"filename").trim();
        require(request.matches("[A-Za-z0-9_-]{16,96}"),422,"导入请求标识无效");
        require(!filename.isEmpty()&&filename.length()<=200,422,"文件名称不能为空且不能超过200字");
        Object raw=body.get("rows");require(raw instanceof List&&!((List<?>)raw).isEmpty()&&((List<?>)raw).size()<=5000,422,"每批导入1至5000条");
        List<Map<String,Object>> rows=new ArrayList<>();Set<String> codes=new HashSet<>();
        for(Object value:(List<?>)raw){
            require(value instanceof Map,422,"导入行格式无效");Map<?,?> input=(Map<?,?>)value;Map<String,Object> row=new TreeMap<>();
            for(String k:Arrays.asList("identityNumber","name","code","phone","org","team","job","sex","birth","exposure")){
                Object field=input.get(k);require(field==null||field instanceof String,422,"导入字段必须为文本");row.put(k,field==null?"":((String)field).trim());
            }
            if(text(row,"sex").isEmpty())row.put("sex","未填写");
            require(!text(row,"name").isEmpty()&&!text(row,"code").isEmpty()&&!text(row,"org").isEmpty(),422,"姓名、工号和单位必须填写");
            require(codes.add(text(row,"code")),422,"文件中存在重复工号，请先修正");rows.add(row);
        }
        String fingerprint=Base64.getEncoder().encodeToString(digest(bytes(ehr.encode(map("filename",filename,"rows",rows))))),batch=id();
        Map<String,Object> data=map("filename",filename,"requestDigest",fingerprint,"imported",rows.size(),"recordIds",new ArrayList<>());
        try{db.insert("a_health_archive_audit_logs",ehr.scoped(a,"audit_id",batch,"record_id",null,"report_id",null,"revision",null,"actor_ref",bytes(a.user),"action",ACTION,"request_key",request,"reason","批量新增 "+rows.size()+" 位职工","redacted_diff",ehr.encode(data),"occurred_at",now()));}
        catch(DuplicateKeyException duplicate){Map<String,Object> prior=find(a,request);require(prior!=null,409,"导入请求冲突，请刷新后核对记录");Map<String,Object> saved=(Map<String,Object>)ehr.decode(prior.get("redacted_diff"));require(fingerprint.equals(saved.get("requestDigest")),409,"同一导入请求不能提交不同内容，请核对上次导入结果");return receipt(prior,true);}
        List<Object> ids=new ArrayList<>();
        for(Map<String,Object> row:rows){
            require(db.count("SELECT COUNT(*) FROM a_employee_roster_profiles WHERE scope_code=? AND data_mode=? AND employee_no=?",a.scope,a.mode,text(row,"code"))==0,409,"工号 "+text(row,"code")+" 已存在，请刷新名单后重新校验");
            ids.add(ehr.upsert(a,null,row).get("id"));
        }
        data.put("recordIds",ids);db.exec("UPDATE a_health_archive_audit_logs SET redacted_diff=? WHERE audit_id=? AND scope_code=? AND data_mode=?",ehr.encode(data),batch,a.scope,a.mode);
        return receipt(find(a,request),false);
    }
    private String historySource(){
        String joined=" FROM a_health_archive_audit_logs h LEFT JOIN a_health_archive_audit_logs b ON h.action='employee_import_attempt' AND b.action='employee_import_batch' AND b.scope_code=h.scope_code AND b.data_mode=h.data_mode AND b.request_key=h.request_key LEFT JOIN yh_user_reginfo ur ON CONVERT(ur.user_code USING utf8mb4) COLLATE utf8mb4_unicode_ci=CONVERT(h.actor_ref USING utf8mb4) COLLATE utf8mb4_unicode_ci WHERE h.scope_code=? AND h.data_mode=? AND (h.action='employee_import_attempt' OR (h.action='employee_import_batch' AND NOT EXISTS(SELECT 1 FROM a_health_archive_audit_logs x WHERE x.scope_code=h.scope_code AND x.data_mode=h.data_mode AND x.request_key=h.request_key AND x.action='employee_import_attempt')))";
        return "SELECT COALESCE(b.audit_id,h.audit_id) batchId,CONVERT(h.actor_ref USING utf8mb4) actor,COALESCE(NULLIF(CONVERT(ur.user_name USING utf8mb4),''),CONVERT(h.actor_ref USING utf8mb4)) actorName,h.occurred_at rawTime,DATE_FORMAT(h.occurred_at,'%Y-%m-%dT%H:%i:%sZ') time,JSON_UNQUOTE(JSON_EXTRACT(h.redacted_diff,'$.filename')) filename,COALESCE(CAST(JSON_UNQUOTE(JSON_EXTRACT(h.redacted_diff,'$.total')) AS UNSIGNED),CAST(JSON_UNQUOTE(JSON_EXTRACT(h.redacted_diff,'$.imported')) AS UNSIGNED),0) totalRows,COALESCE(CAST(JSON_UNQUOTE(JSON_EXTRACT(b.redacted_diff,'$.imported')) AS UNSIGNED),CAST(JSON_UNQUOTE(JSON_EXTRACT(h.redacted_diff,'$.imported')) AS UNSIGNED),0) imported,CASE WHEN b.audit_id IS NOT NULL OR h.action='employee_import_batch' THEN 'success' ELSE COALESCE(JSON_UNQUOTE(JSON_EXTRACT(h.redacted_diff,'$.status')),'processing') END status,JSON_UNQUOTE(JSON_EXTRACT(h.redacted_diff,'$.error')) error"+joined;
    }
    private String filteredHistory(EhrActor a,Map<String,String> q,List<Object> args){
        args.add(a.scope);args.add(a.mode);String sql="SELECT * FROM ("+historySource()+") v WHERE 1=1";
        String from=q.getOrDefault("from",""),to=q.getOrDefault("to","");
        try{if(!from.isEmpty())java.time.LocalDate.parse(from);if(!to.isEmpty())java.time.LocalDate.parse(to);}catch(java.time.DateTimeException ex){throw new EhrException(422,"日期格式应为yyyy-MM-dd");}
        require(from.isEmpty()||to.isEmpty()||from.compareTo(to)<=0,422,"开始日期不能晚于结束日期");
        if(!from.isEmpty()){sql+=" AND rawTime>=CONVERT_TZ(CONCAT(?,' 00:00:00'),'+08:00','+00:00')";args.add(from);}
        if(!to.isEmpty()){sql+=" AND rawTime<CONVERT_TZ(CONCAT(DATE_ADD(?,INTERVAL 1 DAY),' 00:00:00'),'+08:00','+00:00')";args.add(to);}
        String actor=q.getOrDefault("actor",""),search=q.getOrDefault("search",""),status=q.getOrDefault("status","");
        require(search.length()<=200&&actor.length()<=256,422,"查询条件过长");
        if(!actor.isEmpty()){sql+=" AND actor=?";args.add(actor);}if(!search.isEmpty()){sql+=" AND filename LIKE ? ESCAPE '!'";args.add("%"+like(search)+"%");}
        if(!status.isEmpty()){require(Arrays.asList("processing","success","failed").contains(status),422,"导入状态无效");sql+=" AND status=?";args.add(status);}return sql;
    }
    public Map<String,Object> history(EhrActor a,int page,int limit){return history(a,page,limit,new HashMap<>());}
    public Map<String,Object> history(EhrActor a,int page,int limit,Map<String,String> query){
        require(!a.mobile,403,"个人端不能查询企业导入记录");page=Math.max(1,Math.min(page,100000));limit=Math.max(1,Math.min(100,limit));List<Object> args=new ArrayList<>();String sql=filteredHistory(a,query,args);long total=db.count("SELECT COUNT(*) FROM ("+sql+") t",args.toArray());List<Object> paged=new ArrayList<>(args);paged.add(limit);paged.add((page-1)*limit);
        List<Map<String,Object>> items=db.all(sql+" ORDER BY rawTime DESC,batchId DESC LIMIT ? OFFSET ?",paged.toArray());
        Map<String,String> ungrouped=new HashMap<>(query);ungrouped.remove("status");List<Object> groupArgs=new ArrayList<>();String groupSql=filteredHistory(a,ungrouped,groupArgs);
        List<Map<String,Object>> counts=db.all("SELECT status,COUNT(*) count FROM ("+groupSql+") t GROUP BY status",groupArgs.toArray());
        List<Map<String,Object>> actors=db.all("SELECT DISTINCT CONVERT(actor_ref USING utf8mb4) id,(SELECT CONVERT(u.user_name USING utf8mb4) FROM yh_user_reginfo u WHERE CONVERT(u.user_code USING utf8mb4) COLLATE utf8mb4_unicode_ci=CONVERT(actor_ref USING utf8mb4) COLLATE utf8mb4_unicode_ci LIMIT 1) name FROM a_health_archive_audit_logs WHERE scope_code=? AND data_mode=? AND action IN ('employee_import_batch','employee_import_attempt') ORDER BY id",a.scope,a.mode);
        return map("items",items,"total",total,"page",page,"limit",limit,"counts",counts,"actors",actors);
    }
    @SuppressWarnings("unchecked")
    public Map<String,Object> detail(EhrActor a,String batch){
        require(!a.mobile,403,"个人端不能查询企业导入记录");
        Map<String,Object> audit=db.one("SELECT *,DATE_FORMAT(occurred_at,'%Y-%m-%dT%H:%i:%sZ') time FROM a_health_archive_audit_logs WHERE audit_id=? AND scope_code=? AND data_mode=? AND action=?",batch,a.scope,a.mode,ACTION);if(audit==null){Map<String,Object> pending=db.one("SELECT audit_id batchId,request_key requestKey,DATE_FORMAT(occurred_at,'%Y-%m-%dT%H:%i:%sZ') time,redacted_diff FROM a_health_archive_audit_logs WHERE audit_id=? AND scope_code=? AND data_mode=? AND action='employee_import_attempt'",batch,a.scope,a.mode);require(pending!=null,404,"导入批次不存在或无权访问");Map<String,Object> completed=db.one("SELECT audit_id id FROM a_health_archive_audit_logs WHERE scope_code=? AND data_mode=? AND action=? AND request_key=?",a.scope,a.mode,ACTION,pending.remove("requestKey"));if(completed!=null)return detail(a,str(completed.get("id")));Map<String,Object> data=(Map<String,Object>)ehr.decode(pending.remove("redacted_diff"));pending.putAll(data);pending.remove("requestDigest");pending.put("imported",0);pending.put("items",new ArrayList<>());return pending;}
        Map<String,Object> result=receipt(audit,false);List<?> ids=(List<?>)result.remove("recordIds");List<Map<String,Object>> items=new ArrayList<>();
        if(ids!=null&&!ids.isEmpty()){List<Object> args=new ArrayList<>(Arrays.asList(a.scope,a.mode));args.addAll(ids);items=db.all("SELECT r.record_id id,p.employee_name name,p.employee_no code,p.organization_name org,p.team_name team,p.position_name job"+ehr.baseSql()+" AND r.record_id IN ("+String.join(",",Collections.nCopies(ids.size(),"?"))+") ORDER BY p.employee_no",args.toArray());}
        result.put("items",items);return result;
    }
}
