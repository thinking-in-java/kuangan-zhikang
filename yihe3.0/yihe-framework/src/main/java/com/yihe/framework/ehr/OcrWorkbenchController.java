package com.yihe.framework.ehr;

import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Edits report candidates and evidence only; never overwrites roster identities. */
@RestController
@RequestMapping("/ehr/api/report-center/ocr")
public class OcrWorkbenchController {
    final EhrAccess auth; final EhrRepository db; final EhrService ehr; final OcrWorkerQueue queue;
    public OcrWorkbenchController(EhrAccess auth,EhrRepository db,EhrService ehr,OcrWorkerQueue queue){this.auth=auth;this.db=db;this.ehr=ehr;this.queue=queue;}
    @GetMapping("/{id}/{revision}") public Object get(HttpServletRequest request,@PathVariable("id") String id,@PathVariable("revision") int revision){return map("code",0,"data",read(auth.web(request),id,revision));}
    public Map<String,Object> read(EhrActor actor,String id,int revision){
        require(!actor.mobile,403,"仅供后台管理使用");
        Map<String,Object> report=ehr.report(actor,id,revision,true),person=ehr.profile(actor,str(report.get("recordId")),false);
        List<Map<String,Object>> logs=db.all("SELECT audit_id id,action,reason note,CONVERT(actor_ref USING utf8mb4) actor,occurred_at time,redacted_diff changes FROM a_health_archive_audit_logs WHERE scope_code=? AND data_mode=? AND report_id=? AND revision=? ORDER BY occurred_at DESC,audit_id DESC LIMIT 100",actor.scope,actor.mode,id,revision);
        for(Map<String,Object> log:logs)log.put("changes",ehr.decode(log.get("changes")));
        Map<String,Object> task=null;if(report.get("ocrTask") instanceof Map){Object job=((Map<?,?>)report.get("ocrTask")).get("jobId");if(job!=null)task=queue.detail(actor,str(job));}
        List<Map<String,Object>> normalized=db.all("SELECT item_id id,result_normalized value,unit_normalized unit FROM a_health_items WHERE report_id=? AND revision=? ORDER BY ordinal",id,revision);
        return map("report",report,"normalized",normalized,"employee",map("name",person.get("employee_name"),"code",person.get("employee_no"),"sex",person.get("gender"),"team",person.get("team_name"),"job",person.get("position_name")),"logs",logs,"task",task,"editable",!Boolean.TRUE.equals(report.get("published"))&&!Arrays.asList("confirmed","rejected").contains(str(report.get("reviewStatus")))&&((List<?>)report.get("reviews")).isEmpty());
    }
    @SuppressWarnings("unchecked")
    static Map<String,Object> comparable(Map<String,Object> report){
        Map<String,Object> result=new LinkedHashMap<>();
        for(Map<String,Object> field:(List<Map<String,Object>>)report.get("fieldValues"))result.put("基本信息/"+str(field.get("field_key")),field.get("value_raw"));
        int i=0;for(Map<String,Object> item:(List<Map<String,Object>>)report.get("items")){i++;for(String k:new String[]{"name","value","unit","referenceRange","abnormal","page"})result.put("检查项目/"+i+"/"+k,item.get(k));}
        result.put("完整结论",report.get("conclusions")==null?null:((List<Map<String,Object>>)report.get("conclusions")).stream().map(c->str(c.get("text"))).collect(java.util.stream.Collectors.joining("\n")));
        result.put("医师建议",report.get("advice")==null?null:((List<Map<String,Object>>)report.get("advice")).stream().map(c->str(c.get("text"))).collect(java.util.stream.Collectors.joining("\n")));
        return result;
    }
    @SuppressWarnings("unchecked")
    @PutMapping("/{id}/{revision}") @Transactional
    public Object save(HttpServletRequest request,@PathVariable("id") String id,@PathVariable("revision") int revision,@RequestHeader("If-Match") String etag,@RequestBody Map<String,Object> body){return map("code",0,"data",save(auth.web(request),id,revision,etag,body));}
    @SuppressWarnings("unchecked")
    @Transactional public Map<String,Object> save(EhrActor actor,String id,int revision,String etag,Map<String,Object> body){
        require(!actor.mobile,403,"仅供后台管理使用");
        Map<String,Object> before=ehr.report(actor,id,revision,false);
        require(!Boolean.TRUE.equals(before.get("published"))&&!Arrays.asList("confirmed","rejected").contains(str(before.get("reviewStatus")))&&((List<?>)before.get("reviews")).isEmpty(),409,"已审核或已归档版本只读，请先建立修订版本");
        // Validate every extra field before any write; all writes share the existing transaction.
        Map<String,String> fields=new LinkedHashMap<>();
        for(String key:new String[]{"sex","age","employee_number","workshop","job_type"}){
            if(body.containsKey(key))fields.put(key,ehr.raw(body,key,key.equals("age")?10:100).trim());
        }
        if(fields.containsKey("sex"))require(Arrays.asList("男","女","未填写","").contains(fields.get("sex")),422,"报告性别格式不正确");
        if(fields.containsKey("age")&&!fields.get("age").isEmpty())require(fields.get("age").matches("(?:[0-9]|[1-9][0-9]|1[01][0-9]|120)(?:岁)?"),422,"报告年龄应为0至120岁");
        ehr.saveDraft(actor,id,revision,body,etag);
        Map<String,Object> raw=ehr.rawReport(actor,id,revision,true);List<Object> values=new ArrayList<>();
        for(Object value:(List<?>)ehr.decode(raw.get("field_values")))if(!fields.containsKey(str(((Map<?,?>)value).get("field_key"))))values.add(value);
        String source=str(db.one("SELECT source_file_id FROM a_report_revision_sources WHERE report_id=? AND revision=? ORDER BY source_file_id LIMIT 1",id,revision).get("source_file_id"));
        Map<String,String> labels=new LinkedHashMap<>();labels.put("sex","性别");labels.put("age","年龄");labels.put("employee_number","工号");labels.put("workshop","部门 / 班组");labels.put("job_type","岗位");
        for(Map.Entry<String,String> field:fields.entrySet()){
            String evidence=ehr.evidence(actor,id,revision,source,ehr.evidencePage(body.get("metadataPage")),labels.get(field.getKey()),field.getValue());
            values.add(map("field_key",field.getKey(),"original_field_name",labels.get(field.getKey()),"value_raw",field.getValue(),"value_normalized",null,"normalization_rule_version",null,"evidence_ids",Arrays.asList(evidence),"review_status","needs_review"));
        }
        db.exec("UPDATE a_health_reports SET field_values=?,age_raw=COALESCE(?,age_raw) WHERE report_id=? AND revision=?",ehr.encode(values),fields.get("age"),id,revision);
        Map<String,Object> left=comparable(before),right=comparable(ehr.report(actor,id,revision,false));List<Map<String,Object>> changed=new ArrayList<>();Set<String> keys=new LinkedHashSet<>(left.keySet());keys.addAll(right.keySet());
        for(String key:keys)if(!str(left.get(key)).equals(str(right.get(key))))changed.add(map("field",key,"before",left.get(key),"after",right.get(key)));
        db.insert("a_health_archive_audit_logs",ehr.scoped(actor,"audit_id",id(),"record_id",raw.get("record_id"),"report_id",id,"revision",revision,"actor_ref",bytes(actor.user),"action","ocr_workbench_saved","request_key",id(),"reason","OCR工作台按原件核对并保存候选；未修改职工花名册、未自动审核归档","redacted_diff",ehr.encode(map("fieldChanges",changed)),"occurred_at",now()));
        return read(actor,id,revision);
    }
}
