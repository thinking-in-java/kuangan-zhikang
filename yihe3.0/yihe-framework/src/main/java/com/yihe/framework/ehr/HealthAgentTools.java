package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Fixed business tools, parameterized SQL, actor-derived tenancy. No SQL/shell/URL tool. */
@Service
public class HealthAgentTools {
    @org.springframework.beans.factory.annotation.Autowired AiToolSettings management;
    final EhrRepository db;final EhrService ehr;final HealthAgentStore store;
    @org.springframework.beans.factory.annotation.Autowired AgentOverviewService overview;
    public HealthAgentTools(EhrRepository db,EhrService ehr,HealthAgentStore store){this.db=db;this.ehr=ehr;this.store=store;}
    static Map<String,Object> string(String description){return map("type","string","description",description,"maxLength",100);}
    static Map<String,Object> integer(String description,int max){return map("type","integer","description",description,"minimum",1,"maximum",max);}
    static Map<String,Object> spec(String name,String description,Map<String,Object> props){return map("type","function","name",name,"description",description,"parameters",map("type","object","properties",props,"required",new ArrayList<>(props.keySet()),"additionalProperties",false),"strict",true);}
    List<Object> catalog(boolean writes){List<Object> tools=new ArrayList<>();
        tools.add(spec("search_employees","按姓名、工号或班组搜索当前单位职工。返回分页结果，重名不可擅自选取。",map("keyword",string("姓名、工号或班组；空字符串查询全部"),"page",integer("页码，从1开始",10000))));
        tools.add(spec("get_employee_record","查看指定职工基本资料、已确认风险及缺失材料，ID必须来自查询结果或当前服务对象。",map("record_id",string("健康档案ID"))));
        tools.add(spec("list_exam_reports","分页查看某职工的历次报告目录及审核状态。",map("record_id",string("健康档案ID"),"page",integer("页码",10000))));
        tools.add(spec("get_exam_report","查看一份报告原始结果、结论、建议和证据页码；检查项目分页，每页80项。未审核结果不是正式诊断。",map("report_id",string("报告ID"),"revision",integer("报告版本号",10000),"page",integer("检查项目页码",10000))));
        tools.add(spec("get_health_statistics","查询当前单位真实档案总数、已确认风险分布、班组及年龄统计；不把未评估当低风险。",map()));
        if(writes){tools.add(spec("get_digital_worker_overview","汇总当前单位五位数字员工今日任务、积压、人工审核及最近动态。任务数字不是风险职工人数，不得虚构自动执行。",map()));
            tools.add(spec("list_digital_worker_tasks","查询五位数字员工现有业务任务，可按员工和状态筛选。返回真实业务处理链接；此工具只查询，不派活、不审核、不发送通知。",map("worker",string("archive/assess/follow/plan/care，不限传空字符串"),"status",string("queued/in_progress/pending_review/completed/failed/cancelled，不限传all"),"page",integer("任务页码",10000))));}
        tools.add(spec("find_abnormal_results","按已有报告的原始异常标记查询异常项目线索和聚合统计。不能据此确诊基础病。仅查当前版本，标明审核状态；每页30条。",map("team",string("班组关键字，不限则空字符串"),"indicator",string("项目名称，如血糖/血压/肺功能；不限则空字符串"),"reviewed_only",map("type","boolean","description","是否只查医生已确认报告"),"page",integer("异常记录页码",10000))));
        if(writes){tools.add(spec("prepare_employee_delete","仅为用户明确要求删除的一个职工生成待确认预览。绝不立即执行；关联体检报告将联动删除，原系统资料保留。",map("record_id",string("已明确定位的健康档案ID"),"reason",map("type","string","maxLength",600,"description","用户要求删除的原因"))));
            Map<String,Object> update=map("record_id",string("目标健康档案ID"));for(String f:new String[]{"name","code","sex","birth","phone","org","team","job","exposure"})update.put(f,map("type",Arrays.asList("string","null"),"maxLength",f.equals("exposure")?4000:255,"description",f+"的新值，不修改传null；sex只允许男/女/未填写，birth为YYYY-MM-DD"));
            tools.add(spec("prepare_employee_update","用户明确要求更正职工基础资料时生成变更预览，需人工确认；不能改报告、医学结论或风险评估。",update));}
        return tools;
    }
    @SuppressWarnings("unchecked") List<Object> catalogFor(EhrActor a){List<Object> available=new ArrayList<>();for(Object item:catalog(a.doctor)){String name=String.valueOf(((Map<String,Object>)item).get("name"));if(management.allowed(a,name))available.add(item);}return available;}
    @SuppressWarnings("unchecked") void validate(String name,Map<String,Object> args,boolean writes){Map<String,Object> schema=null;for(Object t:catalog(writes)){Map<String,Object> m=(Map<String,Object>)t;if(name.equals(m.get("name")))schema=(Map<String,Object>)m.get("parameters");}require(schema!=null,403,"未开放此工具");Map<String,Object> props=(Map<String,Object>)schema.get("properties");require(props.keySet().equals(args.keySet()),422,"工具参数缺失或包含未声明字段");
        for(String k:props.keySet()){Map<String,Object> rule=(Map<String,Object>)props.get(k);Object v=args.get(k),type=rule.get("type");if(v==null){require(type instanceof List,422,"工具参数不能为空："+k);continue;}if("integer".equals(type)){require(v instanceof Number&&((Number)v).doubleValue()==((Number)v).intValue()&&num(v)>=1&&num(v)<=num(rule.get("maximum")),422,"工具整数范围不合法："+k);}else if("boolean".equals(type))require(v instanceof Boolean,422,"工具参数必须是布尔值："+k);else require(v instanceof String&&((String)v).length()<=num(rule.get("maxLength")),422,"工具字符串长度不合法："+k);}
    }
    Map<String,Object> employee(EhrActor a,String record){ehr.profile(a,record,false);Map<String,Object> p=db.one(ehr.projection()+" AND r.record_id=?",a.scope,a.mode,record);p.remove("phone");p.remove("birth");p.remove("legacyUserCode");return p;}
    Object execute(EhrActor a,String cid,String tid,String message,String selected,String name,Map<String,Object> b,List<Object> sources){return executeConfirmed(a,cid,tid,message,selected,name,b,sources,false);}
    @SuppressWarnings("unchecked") Object executeConfirmed(EhrActor a,String cid,String tid,String message,String selected,String name,Map<String,Object> b,List<Object> sources,boolean confirmed){management.enforce(a,name,confirmed);validate(name,b,a.doctor);String rid=text(b,"record_id");
        if(name.equals("search_employees")){Map<String,String> q=new LinkedHashMap<>();q.put("search",text(b,"keyword"));q.put("page",str(b.get("page")));q.put("limit","20");Map<String,Object> result=ehr.list(a,q);for(Map<String,Object> p:(List<Map<String,Object>>)result.get("items")){p.remove("phone");p.remove("birth");p.remove("legacyUserCode");}return result;}
        if(name.equals("get_employee_record")){Map<String,Object> p=employee(a,rid);Map<String,Object> h=db.one("SELECT h.* FROM a_health_reports h JOIN a_report_series s ON s.report_id=h.report_id AND s.current_revision=h.revision WHERE h.record_id=? AND h.scope_code=? AND h.data_mode=? ORDER BY h.created_at DESC LIMIT 1",rid,a.scope,a.mode);p.put("missing",ehr.missing(ehr.profile(a,rid,false),h));sources.add(map("kind","record","recordId",rid,"label",str(p.get("name"))+" · "+str(p.get("code"))));return p;}
        if(name.equals("list_exam_reports")){ehr.profile(a,rid,false);int page=num(b.get("page"));long total=db.count("SELECT COUNT(*) FROM a_health_reports WHERE record_id=? AND scope_code=? AND data_mode=?",rid,a.scope,a.mode);List<Map<String,Object>> rows=db.all("SELECT report_id reportId,revision,review_status reviewStatus,exam_date_verified examDate,exam_date_raw examDateRaw,institution_raw institution,created_at createdAt FROM a_health_reports WHERE record_id=? AND scope_code=? AND data_mode=? ORDER BY created_at DESC,report_id,revision DESC LIMIT 20 OFFSET ?",rid,a.scope,a.mode,(page-1)*20);return map("total",total,"items",rows,"page",page,"limit",20);}
        if(name.equals("get_exam_report")){Map<String,Object> r=ehr.report(a,text(b,"report_id"),num(b.get("revision")),false);List<Object> items=(List<Object>)r.get("items");int start=Math.min(items.size(),(num(b.get("page"))-1)*80);Map<String,Object> p=employee(a,str(r.get("recordId")));Map<String,Object> out=map("employee",p,"reportId",r.get("id"),"revision",r.get("revision"),"examDate",r.get("examDate"),"examDateRaw",r.get("examDateRaw"),"institution",r.get("institution"),"reviewStatus",r.get("reviewStatus"),"published",r.get("published"),"conclusions",r.get("conclusions"),"advice",r.get("advice"),"itemsTotal",items.size(),"page",b.get("page"),"limit",80,"items",items.subList(start,Math.min(start+80,items.size())),"note","结论和建议均为原文。未审核报告仅供核对；不得根据单项异常直接确诊疾病。");sources.add(map("kind","report","recordId",r.get("recordId"),"reportId",r.get("id"),"revision",r.get("revision"),"label",str(p.get("name"))+" · 体检报告 V"+r.get("revision"),"files",r.get("files")));return out;}
        if(name.equals("get_health_statistics"))return ehr.statistics(a);
        if(name.equals("get_digital_worker_overview"))return overview.overview(a);
        if(name.equals("list_digital_worker_tasks")){Map<String,String> query=new LinkedHashMap<>();query.put("period","all");query.put("worker",text(b,"worker"));query.put("status",text(b,"status"));query.put("page",str(b.get("page")));query.put("limit","20");return overview.list(a,query);}
        if(name.equals("find_abnormal_results"))return abnormal(a,b);
        if(name.equals("prepare_employee_delete"))return store.prepare(a,cid,tid,rid,"delete_employee",map("reason",text(b,"reason")),message,selected);
        if(name.equals("prepare_employee_update")){Map<String,Object> p=ehr.profile(a,rid,false);Map<String,Object> payload=map("rowVersion",p.get("row_version"));String[][] fields={{"name","employee_name"},{"code","employee_no"},{"sex","gender"},{"birth","birth_date"},{"phone","phone"},{"org","organization_name"},{"team","team_name"},{"job","position_name"},{"exposure","exposure_history"}};Map<String,Object> changes=new LinkedHashMap<>();for(String[] f:fields){Object value=b.get(f[0]);payload.put(f[0],value==null?p.get(f[1]):value);if(value!=null&&!str(value).equals(str(p.get(f[1]))))changes.put(f[0],map("from",p.get(f[1]),"to",value));}payload.put("legacyUserCode",p.get("legacy_user_code"));payload.put("diff",changes);require(!changes.isEmpty(),422,"没有需要修改的字段");Map<String,Object> action=store.prepare(a,cid,tid,rid,"update_employee",payload,message,selected);action.put("diff",changes);return action;}
        throw new EhrException(403,"未开放此工具");
    }
    Object abnormal(EhrActor a,Map<String,Object> b){
        String joins=" FROM a_health_items i JOIN a_health_reports h ON h.report_id=i.report_id AND h.revision=i.revision JOIN a_report_series s ON s.report_id=h.report_id AND s.current_revision=h.revision JOIN a_health_records r ON r.record_id=h.record_id JOIN a_employee_roster_profiles p ON p.employee_id=r.employee_id AND p.scope_code=r.scope_code AND p.data_mode=r.data_mode JOIN a_report_evidence e ON e.evidence_id=i.primary_evidence_id AND e.report_id=i.report_id AND e.revision=i.revision WHERE h.scope_code=? AND h.data_mode=? AND p.deleted_at IS NULL AND h.review_status<>'rejected' AND (TRIM(i.abnormal_flag_raw) IN ('H','L','HH','LL','↑','↓','+','++','+++','阳性','异常','偏高','偏低') OR i.abnormal_flag_raw REGEXP '升高|降低|偏高|偏低|超标')";
        List<Object> args=new ArrayList<>(Arrays.asList(a.scope,a.mode));
        if(Boolean.TRUE.equals(b.get("reviewed_only")))joins+=" AND h.review_status='confirmed'";
        for(String[] f:new String[][]{{"team","p.team_name"},{"indicator","i.original_field_name"}}){String v=text(b,f[0]);if(!v.isEmpty()){joins+=" AND "+f[1]+" LIKE ? ESCAPE '!'";args.add("%"+like(v)+"%");}}
        long total=db.count("SELECT COUNT(*)"+joins,args.toArray()),people=db.count("SELECT COUNT(DISTINCT r.record_id)"+joins,args.toArray());
        List<Map<String,Object>> groups=db.all("SELECT i.original_field_name indicator,COUNT(*) resultCount,COUNT(DISTINCT r.record_id) employeeCount"+joins+" GROUP BY i.original_field_name ORDER BY resultCount DESC LIMIT 30",args.toArray());
        List<Object> paged=new ArrayList<>(args);paged.add((num(b.get("page"))-1)*30);
        List<Map<String,Object>> rows=db.all("SELECT r.record_id recordId,p.employee_name name,p.employee_no code,p.team_name team,h.report_id reportId,h.revision,h.exam_date_verified examDate,h.exam_date_raw examDateRaw,h.review_status reviewStatus,i.original_field_name indicator,i.result_raw value,i.unit_raw unit,i.reference_range_raw referenceRange,i.abnormal_flag_raw abnormal,e.page_number page,e.evidence_id evidenceId"+joins+" ORDER BY h.created_at DESC,h.report_id,i.ordinal LIMIT 30 OFFSET ?",paged.toArray());
        return map("totalResults",total,"affectedEmployees",people,"groups",groups,"items",rows,"page",b.get("page"),"limit",30,"basis","当前报告版本中已有的明确异常标记；没有标记的项目未重新计算。多份报告可重复出现同一职工。属于关注线索，不是基础病确诊或正式预警；未审核记录须医生核验。");
    }
}
