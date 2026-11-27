package com.yihe.framework.ehr;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrController.ok;

/** Scoped, versioned intervention workflow. No notification or clinical conclusion is inferred. */
@RestController
@RequestMapping("/ehr/api/intervention")
public class InterventionCenterApi {
    private final EhrAccess access;private final EhrService ehr;private final EhrRepository db;
    private final ObjectMapper json=new ObjectMapper();
    private static final List<String> KEYS=Arrays.asList("plans","schedules","tasks","revisits","alerts","care","projects","templates","settings");
    @Value("${ehr.storage-root:${EHR_STORAGE_ROOT:D:/update}}") private String storage;
    public InterventionCenterApi(EhrAccess access,EhrService ehr,EhrRepository db){this.access=access;this.ehr=ehr;this.db=db;}
    @ModelAttribute public void privateHeaders(HttpServletResponse response){response.setHeader("Cache-Control","private, no-store");}
    private Path file(EhrActor actor){return Paths.get(storage,".health-intervention",Base64.getUrlEncoder().withoutPadding().encodeToString(digest(bytes(actor.scope+":"+actor.mode)))+".json");}
    @SuppressWarnings("unchecked") private Map<String,Object> read(EhrActor actor)throws IOException {
        Path path=file(actor);if(!Files.exists(path)){Map<String,Object> state=map("version",0,"audit",new ArrayList<>());for(String key:KEYS)state.put(key,new ArrayList<>());return state;}
        if(Files.size(path)>4194304)throw new EhrException(500,"干预中心资料过大，请联系管理员");return json.readValue(Files.readAllBytes(path),Map.class);
    }
    @GetMapping("/state") public synchronized Object state(HttpServletRequest request)throws IOException{return ok(read(access.web(request)));}
    @GetMapping("/reviewers") public Object reviewers(HttpServletRequest request){EhrActor a=access.web(request);return ok(map("items",db.all("SELECT user_code id,CONVERT(user_name USING utf8mb4) name FROM yh_user_reginfo WHERE Org_Code=? AND User_Type=4 AND lock_flag=2 ORDER BY user_name,user_code",a.scope.substring(4))));}
    @GetMapping("/assistant-evidence") public Object assistantEvidence(HttpServletRequest request,@RequestParam String recordId){
        EhrActor a=access.web(request);ehr.profile(a,recordId,false);
        return ok(map("assessments",db.all("SELECT assessment_id id,risk_level level,risk_score score,rule_version ruleVersion,assessment_type type,assessment_time assessedAt FROM a_health_risk_assessments WHERE record_id=? AND scope_code=? AND data_mode=? AND assessment_status='confirmed' ORDER BY assessment_time DESC,assessment_id DESC LIMIT 20",recordId,a.scope,a.mode),"followups",db.all("SELECT f.task_id id,f.status,f.due_at dueAt,f.report_id reportId,f.revision FROM a_followup_tasks f JOIN a_health_reports h ON h.report_id=f.report_id AND h.revision=f.revision WHERE h.record_id=? AND h.scope_code=? AND h.data_mode=? ORDER BY f.created_at DESC LIMIT 20",recordId,a.scope,a.mode)));
    }
    private static final String[] ASSISTANT_KEYS={"summary","metrics","goals","lifestyle","diet","exercise","followups","alerts","review"};
    private static final String[] ASSISTANT_TITLES={"一、健康问题总结","二、重点关注指标","三、管理目标","四、生活方式建议","五、饮食建议","六、运动建议","七、复查与随访","八、预警条件","九、审核与确认"};
    private void assistantValidate(EhrActor a,String key,Map<String,Object> r,Map<String,Object> prior){
        if(!"plans".equals(key)||Objects.equals(r,prior))return;
        boolean oldAssistant=prior!=null&&EhrService.num(prior.get("assistantSectionVersion"))==1;
        if(oldAssistant&&EhrService.num(r.get("assistantSectionVersion"))!=1)throw new EhrException(422,"完整方案章节不能移除");
        if(EhrService.num(r.get("assistantSectionVersion"))!=1)return;
        if(EhrService.num(r.get("structuredVersion"))!=1||text(r,"recordId").isEmpty())throw new EhrException(422,"完整方案须关联真实职工和结构化流程");
        Object raw=r.get("assistantSections");if(!(raw instanceof Map)||((Map<?,?>)raw).size()!=9)throw new EhrException(422,"完整方案需保留九个章节");
        Map<?,?> sections=(Map<?,?>)raw;StringBuilder document=new StringBuilder();int length=0;
        for(int i=0;i<ASSISTANT_KEYS.length;i++){Object section=sections.get(ASSISTANT_KEYS[i]);if(!(section instanceof String)||((String)section).length()>3000)throw new EhrException(422,"方案章节格式或长度无效");String content=((String)section).trim();length+=content.length();if(i>0)document.append("\n\n");document.append(ASSISTANT_TITLES[i]).append("\n").append(content);}
        if(length==0||length>12000||!document.toString().equals(r.get("content")))throw new EhrException(422,"方案全文与章节不一致或超过长度限制");
        if(text(r,"assistantRequest").length()>1500||text(r,"assistantOrigin").length()>200)throw new EhrException(422,"生成需求或来源过长");
        Object changes=r.get("assistantChanges");if(!(changes instanceof List)||((List<?>)changes).size()>30)throw new EhrException(422,"修改记录格式无效");
        for(Object change:(List<?>)changes){if(!(change instanceof Map))throw new EhrException(422,"修改记录格式无效");Map<?,?> c=(Map<?,?>)change;for(String f:Arrays.asList("time","actor","action","note"))if(value(c,f).length()>2000)throw new EhrException(422,"修改记录过长");}
        if(oldAssistant&&!Arrays.asList("draft","rejected").contains(text(prior,"status"))&&!"draft".equals(text(r,"status")))for(String f:Arrays.asList("assistantSections","content","title","goals","measures","problems","reviewerId","nextDue","frequency","confirmationMethods","notes","assistantSources","assistantOrigin","assistantRequest","aiTaskId"))if(!Objects.equals(prior.get(f),r.get(f)))throw new EhrException(409,"已提交方案需转回草稿后修改");
        Object source=r.get("assistantSources");if(!(source instanceof Map))throw new EhrException(422,"需保留方案资料引用");Map<?,?> refs=(Map<?,?>)source;
        if(!text(r,"recordId").equals(value(refs,"recordId")))throw new EhrException(422,"方案资料与职工不一致");
        if(prior==null||!Objects.equals(prior.get("assistantSources"),source)){
            Object reports=refs.get("reports"),assessments=refs.get("assessments");if(!(reports instanceof List)||((List<?>)reports).size()>10||!(assessments instanceof List)||((List<?>)assessments).size()>20)throw new EhrException(422,"资料引用格式无效");
            for(Object report:(List<?>)reports){if(!(report instanceof Map))throw new EhrException(422,"报告引用无效");Map<?,?> ref=(Map<?,?>)report;if(db.count("SELECT COUNT(*) FROM a_health_reports WHERE report_id=? AND revision=? AND record_id=? AND scope_code=? AND data_mode=? AND review_status='confirmed'",value(ref,"id"),ref.get("revision"),text(r,"recordId"),a.scope,a.mode)!=1)throw new EhrException(403,"报告不属于此职工的已确认授权资料");}
            for(Object assessment:(List<?>)assessments)if(db.count("SELECT COUNT(*) FROM a_health_risk_assessments WHERE assessment_id=? AND record_id=? AND scope_code=? AND data_mode=? AND assessment_status='confirmed'",assessment,text(r,"recordId"),a.scope,a.mode)!=1)throw new EhrException(403,"评估不属于此职工的已确认授权资料");
        }
        String task=text(r,"aiTaskId");if(!task.isEmpty()&&(prior==null||!task.equals(text(prior,"aiTaskId")))&&db.count("SELECT COUNT(*) FROM a_digital_worker_tasks WHERE task_id=? AND record_id=? AND scope_code=? AND data_mode=? AND worker_id='plan'",task,text(r,"recordId"),a.scope,a.mode)!=1)throw new EhrException(403,"AI任务不属于此职工或当前授权范围");
        if("active".equals(text(r,"status"))&&prior!=null&&"awaiting_confirmation".equals(text(prior,"status"))){Object c=r.get("assistantConfirmation");if(!(c instanceof Map))throw new EhrException(422,"发布需登记实际职工确认");Map<?,?> receipt=(Map<?,?>)c;if(!Boolean.TRUE.equals(receipt.get("verified"))||!Arrays.asList("miniapp","offline").contains(value(receipt,"method"))||value(receipt,"receipt").trim().isEmpty()||value(receipt,"receipt").length()>1500||!a.user.equals(value(receipt,"actor")))throw new EhrException(422,"请登记已核实的实际确认方式与本人反馈");hubActualTime(value(receipt,"confirmedAt"));}
    }

    private String value(Map<?,?> row,String key){Object v=row.get(key);return v==null?"":String.valueOf(v);}
    @SuppressWarnings("unchecked") private void validate(EhrActor actor,String key,Map<String,Object> row,Map<String,Object> previous,Set<String> checked){
        String id=text(row,"id"),record=text(row,"recordId");if(!id.matches("[A-Za-z0-9_-]{1,96}"))throw new EhrException(422,"记录标识无效");
        if(!record.isEmpty()&&checked.add(record))ehr.profile(actor,record,false);
        if(previous!=null&&!text(previous,"recordId").isEmpty()&&!Objects.equals(text(previous,"recordId"),record))throw new EhrException(422,"已关联的职工不能变更，请另建记录");
        if(!record.isEmpty()&&"tasks".equals(key)&&"completed".equals(text(row,"status"))&&text(row,"completionEvidence").isEmpty())throw new EhrException(422,"任务完成需填写实际完成依据");
        if(!record.isEmpty()&&"alerts".equals(key)&&Arrays.asList("resolved","closed").contains(text(row,"status"))&&text(row,"handlingEvidence").isEmpty())throw new EhrException(422,"预警关闭需记录实际处置依据");
        if(!"plans".equals(key)||record.isEmpty())return;
        String state=text(row,"status"),before=previous==null?"":text(previous,"status");
        if(!Arrays.asList("draft","pending_review","awaiting_confirmation","active","completed","paused","rejected").contains(state))throw new EhrException(422,"方案状态无效");
        if(previous==null&&!"draft".equals(state)&&!("pending_review".equals(state)&&EhrService.num(row.get("structuredVersion"))==1))throw new EhrException(422,"新方案须保存草稿或填写完整表单后提交审核");
        if(EhrService.num(row.get("structuredVersion"))==1){
            if(text(row,"title").trim().isEmpty()||text(row,"title").length()>120||text(row,"goals").length()>500||text(row,"measures").length()>1000||text(row,"notes").length()>500)throw new EhrException(422,"方案字段为空或超过长度限制");
            if(!Arrays.asList("weekly","biweekly","monthly","quarterly","custom").contains(text(row,"frequency")))throw new EhrException(422,"随访频率无效");
            String due=text(row,"nextDue");if(!due.isEmpty()){try{java.time.LocalDate.parse(due);}catch(Exception ex){throw new EhrException(422,"随访日期无效");}}
            Object rawProblems=row.get("problems"),rawMethods=row.get("confirmationMethods");
            if(!(rawProblems instanceof List)||((List<?>)rawProblems).size()>8||!(rawMethods instanceof List)||((List<?>)rawMethods).size()>2)throw new EhrException(422,"健康问题或确认方式无效");
            for(Object issue:(List<?>)rawProblems)if(!Arrays.asList("高血压","血糖异常","血脂异常","体重管理","心理压力","睡眠问题","职业防护","综合管理").contains(issue))throw new EhrException(422,"健康问题无效");
            for(Object method:(List<?>)rawMethods)if(!Arrays.asList("miniapp","offline").contains(method))throw new EhrException(422,"确认方式无效");
            String reviewer=text(row,"reviewerId");if(!reviewer.isEmpty()&&db.count("SELECT COUNT(*) FROM yh_user_reginfo WHERE user_code=? AND Org_Code=? AND User_Type=4 AND lock_flag=2",reviewer,actor.scope.substring(4))!=1)throw new EhrException(422,"请选择本单位有效医生");
            if("pending_review".equals(state)){if(((List<?>)rawProblems).isEmpty()||text(row,"goals").trim().isEmpty()||text(row,"measures").trim().isEmpty()||due.isEmpty()||reviewer.isEmpty()||((List<?>)rawMethods).isEmpty())throw new EhrException(422,"提交审核前需补全健康问题、目标、措施、随访、审核人与确认方式");if(java.time.LocalDate.parse(due).isBefore(java.time.LocalDate.now(java.time.ZoneId.of("Asia/Shanghai"))))throw new EhrException(422,"下次随访日期不能早于今天");}
        }
        if(previous!=null&&!Arrays.asList("draft","rejected").contains(before)&&!"draft".equals(state)&&(!Objects.equals(previous.get("content"),row.get("content"))||!Objects.equals(previous.get("title"),row.get("title"))))throw new EhrException(409,"修改已提交的方案内容需先转回草稿");
        if(EhrService.num(row.get("structuredVersion"))==1&&"pending_review".equals(before)&&Arrays.asList("awaiting_confirmation","rejected").contains(state)&&!actor.user.equals(text(row,"reviewerId")))throw new EhrException(403,"此方案需由指定审核医生处理");
        if(before.equals(state))return;
        Map<String,List<String>> transitions=new HashMap<>();
        transitions.put("draft",Arrays.asList("pending_review"));transitions.put("pending_review",Arrays.asList("awaiting_confirmation","rejected"));transitions.put("rejected",Arrays.asList("draft","pending_review"));transitions.put("awaiting_confirmation",Arrays.asList("active","draft"));transitions.put("active",Arrays.asList("paused","completed","draft"));transitions.put("paused",Arrays.asList("active","draft"));transitions.put("completed",Collections.emptyList());
        if(previous!=null&&!transitions.getOrDefault(before,Collections.emptyList()).contains(state))throw new EhrException(409,"方案状态不允许直接跳转，请按审核流程操作");
        if("awaiting_confirmation".equals(state)&&(!actor.doctor||text(row,"reviewNote").isEmpty()))throw new EhrException(403,"方案需医生审核并填写意见");
        if("active".equals(state)&&text(row,"confirmationNote").isEmpty())throw new EhrException(422,"请记录职工实际确认的时间、方式和反馈");
    }
    @PutMapping("/state") public synchronized Object save(HttpServletRequest request,@RequestBody Map<String,Object> body)throws IOException {
        EhrActor actor=access.web(request);Map<String,Object> old=read(actor);
        if(EhrService.num(body.get("version"))!=EhrService.num(old.get("version")))throw new EhrException(409,"资料已被其他人员修改，请刷新后重试");
        Map<String,Object> next=map("version",EhrService.num(old.get("version"))+1);List<Object> audit=new ArrayList<>();Object priorAudit=old.get("audit");if(priorAudit instanceof List)audit.addAll((List<?>)priorAudit);Set<String> checked=new HashSet<>();
        for(String key:KEYS){Object raw=body.get(key);if(!(raw instanceof List)||((List<?>)raw).size()>3000)throw new EhrException(422,"资料列表无效或超过3000条");
            Map<String,Map<String,Object>> previous=new HashMap<>();for(Object o:(List<?>)old.get(key))if(o instanceof Map){@SuppressWarnings("unchecked")Map<String,Object> row=(Map<String,Object>)o;previous.put(text(row,"id"),row);}
            Set<String> ids=new HashSet<>();for(Object o:(List<?>)raw){if(!(o instanceof Map))throw new EhrException(422,"记录格式错误");@SuppressWarnings("unchecked")Map<String,Object> row=(Map<String,Object>)o;String id=text(row,"id");if(!ids.add(id))throw new EhrException(422,"记录标识重复");validate(actor,key,row,previous.get(id),checked);assistantValidate(actor,key,row,previous.get(id));followupValidate(actor,key,row,previous.get(id),body);taskValidate(actor,key,row,previous.get(id),body);hubValidate(actor,key,row,previous.get(id),body,checked);if(!Objects.equals(previous.get(id),row))audit.add(map("module",key,"recordId",id,"title",row.getOrDefault("title",row.getOrDefault("name","记录")),"actor",actor.user,"time",now(),"action",previous.containsKey(id)?"updated":"created","status",row.get("status")));}
            if(!ids.containsAll(previous.keySet()))throw new EhrException(422,"历史记录不能直接删除，请使用停用或关闭操作");next.put(key,raw);
        }
        if(audit.size()>500)audit=new ArrayList<>(audit.subList(audit.size()-500,audit.size()));next.put("audit",audit);next.put("updatedAt",now());
        writeState(actor,next);return ok(next);
    }
    private void followupValidate(EhrActor a,String key,Map<String,Object> r,Map<String,Object> prior,Map<String,Object> state){
        if("schedules".equals(key)&&EhrService.num(r.get("structuredVersion"))==2){
            if(text(r,"recordId").isEmpty())throw new EhrException(422,"随访计划必须关联真实职工");String status=text(r,"status");
            if(!Arrays.asList("draft","pending","active","completed","paused","cancelled").contains(status))throw new EhrException(422,"随访状态无效");
            for(String field:Arrays.asList("title","problem","content","notes","notice")){int max="content".equals(field)?2000:("notes".equals(field)||"notice".equals(field)?500:200);if(text(r,field).length()>max)throw new EhrException(422,"随访字段过长："+field);}
            if(text(r,"title").isEmpty())throw new EhrException(422,"请填写随访目标");if(!"draft".equals(status)&&(text(r,"content").isEmpty()||text(r,"problem").isEmpty()||text(r,"due").isEmpty()||text(r,"deadline").isEmpty()||text(r,"ownerId").isEmpty()))throw new EhrException(422,"随访创建资料不完整");
            for(String f:Arrays.asList("due","deadline"))if(!text(r,f).isEmpty())try{java.time.LocalDate.parse(text(r,f));}catch(Exception ex){throw new EhrException(422,"随访日期无效");}
            if(!text(r,"deadline").isEmpty()&&!text(r,"due").isEmpty()&&text(r,"deadline").compareTo(text(r,"due"))<0)throw new EhrException(422,"完成时限不能早于计划日期");
            if(prior==null&&!text(r,"due").isEmpty()&&java.time.LocalDate.parse(text(r,"due")).isBefore(java.time.LocalDate.now(java.time.ZoneOffset.ofHours(8))))throw new EhrException(422,"新随访计划不能安排在过去");
            if(prior==null&& !Arrays.asList("draft","pending").contains(status))throw new EhrException(422,"新随访计划须为草稿或待执行");
            if(prior!=null&&Arrays.asList("completed","cancelled").contains(text(prior,"status"))&&!Objects.equals(prior,r))throw new EhrException(422,"已结束随访计划不能直接修改");
            if("completed".equals(status)&&(text(r,"completionEvidence").isEmpty()||!"reached".equals(text(r,"lastOutcome"))))throw new EhrException(422,"随访完成必须填写实际联系反馈与完成依据");
            if("completed".equals(status)){Object rawLogs=r.get("logs");boolean evidence=false;if(rawLogs instanceof List)for(Object entry:(List<?>)rawLogs)if(entry instanceof Map){Map<?,?> log=(Map<?,?>)entry;if("reached".equals(value(log,"outcome"))&&!value(log,"note").trim().isEmpty())try{java.time.LocalDateTime contact=java.time.LocalDateTime.parse(value(log,"contactAt"));if(!contact.isAfter(java.time.LocalDateTime.now(java.time.ZoneOffset.ofHours(8)).plusMinutes(5)))evidence=true;}catch(Exception ignored){}}if(!evidence)throw new EhrException(422,"完成随访需提供有效的实际联系时间与反馈记录");}
            Object channels=r.get("channels");if(!(channels instanceof List)||((List<?>)channels).size()>4||(!"draft".equals(status)&&((List<?>)channels).isEmpty()))throw new EhrException(422,"随访提醒方式无效");for(Object channel:(List<?>)channels)if(!Arrays.asList("短信","微信","系统消息","电话").contains(channel))throw new EhrException(422,"随访提醒方式无效");
            if(!text(r,"ownerId").isEmpty()&&db.count("SELECT COUNT(*) FROM yh_user_reginfo WHERE user_code=? AND Org_Code=? AND User_Type=4 AND lock_flag=2",text(r,"ownerId"),a.scope.substring(4))!=1)throw new EhrException(422,"随访执行人须为本单位启用医生");
            for(String ref:Arrays.asList("planId","revisitId","careId")){if(text(r,ref).isEmpty())continue;Object source=state.get("planId".equals(ref)?"plans":"careId".equals(ref)?"care":"revisits");boolean exists=false;if(source instanceof List)for(Object item:(List<?>)source)if(item instanceof Map){Map<?,?> m=(Map<?,?>)item;if(text(r,ref).equals(value(m,"id"))&&text(r,"recordId").equals(value(m,"recordId"))&&!value(m,"id").startsWith("sample-")){exists=true;break;}}if(!exists)throw new EhrException(422,"关联方案或复查任务不属于当前职工");}
        }
        if(!"settings".equals(key)||!"followup-rule".equals(text(r,"type")))return;
        boolean changed=!Objects.equals(prior,r);r.put("_scope",a.scope);r.put("_mode",a.mode);r.put("_owner",prior!=null&&!changed?prior.get("_owner"):a.user);
        if(text(r,"title").isEmpty()||text(r,"title").length()>120||text(r,"content").isEmpty()||text(r,"content").length()>2000)throw new EhrException(422,"自动创建规则名称或内容无效");
        if(!Arrays.asList(7,14,30,90).contains(EhrService.num(r.get("intervalDays")))||!Arrays.asList("repeat","once").contains(text(r,"cadence"))||!Arrays.asList("all","any").contains(text(r,"relation"))||!Arrays.asList("all","people","org").contains(text(r,"scopeKind")))throw new EhrException(422,"规则周期、范围或条件关系无效");
        try{java.time.LocalDate date=java.time.LocalDate.parse(text(r,"startDate"));if(prior==null&&date.isBefore(java.time.LocalDate.now(java.time.ZoneOffset.ofHours(8))))throw new IllegalArgumentException();}catch(Exception ex){throw new EhrException(422,"生效日期无效或早于今天");}
        Object raw=r.get("conditions");if(!(raw instanceof List)||((List<?>)raw).isEmpty()||((List<?>)raw).size()>8)throw new EhrException(422,"需配置1至8个规则条件");for(Object v:(List<?>)raw){if(!(v instanceof Map))throw new EhrException(422,"规则条件无效");Map<?,?> c=(Map<?,?>)v;if(!Arrays.asList("problem","risk","team","job").contains(value(c,"field"))||value(c,"value").trim().isEmpty()||value(c,"value").length()>200)throw new EhrException(422,"规则条件无效");}
        if(!(r.get("enabled") instanceof Boolean)||!Arrays.asList("短信","微信","系统消息","电话").contains(text(r,"channel")))throw new EhrException(422,"规则状态或渠道无效");
        if(db.count("SELECT COUNT(*) FROM yh_user_reginfo WHERE user_code=? AND Org_Code=? AND User_Type=4 AND lock_flag=2",text(r,"ownerId"),a.scope.substring(4))!=1)throw new EhrException(422,"规则执行人须为本单位启用医生");
        Object ids=r.get("recordIds");if(!(ids instanceof List)||((List<?>)ids).size()>5000||("people".equals(text(r,"scopeKind"))&&((List<?>)ids).isEmpty()))throw new EhrException(422,"指定职工范围无效");for(Object id:(List<?>)ids)ehr.profile(a,String.valueOf(id),false);
        if("org".equals(text(r,"scopeKind"))&&text(r,"org").isEmpty())throw new EhrException(422,"请选择指定单位");
    }
    private void writeState(EhrActor actor,Map<String,Object> next)throws IOException{
        byte[] bytes=json.writeValueAsBytes(next);if(bytes.length>4194304)throw new EhrException(422,"干预资料超过4 MiB，请归档后再保存");Path target=file(actor);Files.createDirectories(target.getParent());Path temp=Files.createTempFile(target.getParent(),"intervention-",".tmp");
        try{Files.write(temp,bytes);try{Files.move(temp,target,StandardCopyOption.ATOMIC_MOVE,StandardCopyOption.REPLACE_EXISTING);}catch(AtomicMoveNotSupportedException error){Files.move(temp,target,StandardCopyOption.REPLACE_EXISTING);}}finally{Files.deleteIfExists(temp);}
    }
    @SuppressWarnings("unchecked") private Map<String,Object> runRules(EhrActor actor)throws IOException{
        Map<String,Object> data=read(actor);List<Map<String,Object>> settings=(List<Map<String,Object>>)(List<?>)data.get("settings");boolean active=settings.stream().anyMatch(x->"followup-rule".equals(text(x,"type"))&&Boolean.TRUE.equals(x.get("enabled")));if(!active)return map("state",data,"created",0);
        Set<String> owners=new HashSet<>();for(Map<String,Object> user:db.all("SELECT user_code id,User_Type type FROM yh_user_reginfo WHERE Org_Code=? AND lock_flag=2 AND User_Type<>5",actor.scope.substring(4))){owners.add(value(user,"id"));if(EhrService.num(user.get("type"))==4)owners.add("doctor:"+value(user,"id"));}
        // Context is server stamped. Never execute a configuration copied from another scope.
        List<Map<String,Object>> excluded=new ArrayList<>();for(Map<String,Object> r:settings)if("followup-rule".equals(text(r,"type"))&&(!actor.scope.equals(text(r,"_scope"))||!actor.mode.equals(text(r,"_mode")))){excluded.add(r);r.put("enabled",false);r.put("lastError","规则上下文已失效，请重新保存");}
        List<Map<String,Object>> people=db.all(ehr.projection()+" ORDER BY r.record_id LIMIT 5000",actor.scope,actor.mode);
        int created=FollowupRuleEngine.apply(data,people,java.time.LocalDate.now(java.time.ZoneOffset.ofHours(8)),owners);
        if(created>0){data.put("version",EhrService.num(data.get("version"))+1);data.put("updatedAt",now());List<Object> audit=(List<Object>)data.get("audit");audit.add(map("module","schedules","recordId","rules-runtime","actor",actor.user,"time",now(),"action","auto_created","title","规则新建 "+created+" 项随访计划"));if(audit.size()>500)data.put("audit",new ArrayList<>(audit.subList(audit.size()-500,audit.size())));}
        writeState(actor,data);return map("state",data,"created",created);
    }
    @PostMapping("/followup-rules/run") public synchronized Object runFollowupRules(HttpServletRequest request)throws IOException{return ok(runRules(access.web(request)));}
    @org.springframework.scheduling.annotation.Scheduled(fixedDelay=60000,initialDelay=30000)
    @SuppressWarnings("unchecked") public synchronized void scheduledFollowupRules(){
        Path dir=Paths.get(storage,".health-intervention");if(!Files.isDirectory(dir))return;
        try(java.nio.file.DirectoryStream<Path> files=Files.newDirectoryStream(dir,"*.json")){int scanned=0;for(Path path:files){if(++scanned>500)break;try{if(Files.size(path)>4194304)continue;Map<String,Object> state=json.readValue(Files.readAllBytes(path),Map.class);Object settings=state.get("settings");if(!(settings instanceof List))continue;for(Object item:(List<?>)settings){if(!(item instanceof Map))continue;Map<String,Object> r=(Map<String,Object>)item;if(!"followup-rule".equals(text(r,"type"))||!Boolean.TRUE.equals(r.get("enabled")))continue;String scope=text(r,"_scope"),mode=text(r,"_mode");if(!scope.startsWith("org:")||!Arrays.asList("real","synthetic").contains(mode))continue;EhrActor a=new EhrActor(text(r,"_owner"),scope.substring(4),mode,false,false);if(!file(a).toAbsolutePath().normalize().equals(path.toAbsolutePath().normalize()))continue;runRules(a);break;}}catch(Exception ex){org.slf4j.LoggerFactory.getLogger(InterventionCenterApi.class).warn("Followup rule check failed for a scoped state file: {}",ex.getClass().getSimpleName());}}}catch(IOException ex){org.slf4j.LoggerFactory.getLogger(InterventionCenterApi.class).warn("Followup rule storage not readable");}
    }

    @SuppressWarnings("unchecked") private void taskValidate(EhrActor a,String key,Map<String,Object> r,Map<String,Object> prior,Map<String,Object> state){
        if(!"tasks".equals(key)||EhrService.num(r.get("taskVersion"))!=1)return;
        String status=text(r,"status");if(!Arrays.asList("pending","active","unanswered","manual","completed","paused","cancelled").contains(status))throw new EhrException(422,"随访任务状态无效");
        if(text(r,"recordId").isEmpty()||text(r,"title").isEmpty()||text(r,"content").isEmpty()||text(r,"problem").isEmpty())throw new EhrException(422,"任务必须关联真实职工并填写名称、健康问题和内容");
        if(text(r,"title").length()>120||text(r,"content").length()>2000||text(r,"problem").length()>200||text(r,"notes").length()>500||text(r,"aiDraft").length()>12000)throw new EhrException(422,"任务字段超过长度限制");
        try{java.time.LocalDate due=java.time.LocalDate.parse(text(r,"due"));if((prior==null||!text(prior,"due").equals(text(r,"due")))&&due.isBefore(java.time.LocalDate.now(java.time.ZoneOffset.ofHours(8))))throw new IllegalArgumentException();if(prior==null||!text(r,"time").isEmpty())java.time.LocalTime.parse(text(r,"time"));}catch(Exception ex){throw new EhrException(422,"任务日期或时点无效，新任务不能安排在过去");}
        if(!Arrays.asList("短信","微信","系统消息","电话").contains(text(r,"channel")))throw new EhrException(422,"任务随访方式无效");
        if(db.count("SELECT COUNT(*) FROM yh_user_reginfo WHERE user_code=? AND Org_Code=? AND User_Type=4 AND lock_flag=2",text(r,"ownerId"),a.scope.substring(4))!=1)throw new EhrException(422,"请选择本单位启用医生作为执行人");
        if(prior==null&&!"pending".equals(status))throw new EhrException(422,"新任务须以待执行状态创建");
        if(prior!=null&&Arrays.asList("completed","cancelled").contains(text(prior,"status"))&&!Objects.equals(prior,r))throw new EhrException(422,"已结束任务不能直接修改");
        for(String ref:Arrays.asList("scheduleId","planId")){if(text(r,ref).isEmpty())continue;Object source=state.get("scheduleId".equals(ref)?"schedules":"plans");boolean exists=false;if(source instanceof List)for(Object item:(List<?>)source)if(item instanceof Map){Map<?,?> row=(Map<?,?>)item;if(text(r,ref).equals(value(row,"id"))&&text(r,"recordId").equals(value(row,"recordId"))&&!value(row,"id").startsWith("sample-")){if(prior==null&&"scheduleId".equals(ref)&&Arrays.asList("completed","cancelled","draft","paused").contains(value(row,"status")))throw new EhrException(422,"关联随访计划当前不可创建任务");exists=true;break;}}if(!exists)throw new EhrException(422,"关联计划或方案不属于当前职工");}
        Object raw=r.get("logs");if(!(raw instanceof List)||((List<?>)raw).size()>1000)throw new EhrException(422,"任务记录格式无效或超过1000条");List<?> logs=(List<?>)raw;
        if(prior!=null&&prior.get("logs") instanceof List){List<?> old=(List<?>)prior.get("logs");if(logs.size()<old.size()||!logs.subList(0,old.size()).equals(old))throw new EhrException(422,"已有执行记录不能删除或改写");}
        Map<?,?> lastContact=null;for(Object entry:logs){if(!(entry instanceof Map))throw new EhrException(422,"执行记录格式无效");Map<?,?> log=(Map<?,?>)entry;String outcome=value(log,"outcome"),contact=value(log,"contactAt");if(!outcome.isEmpty()&&!Arrays.asList("reached","no_answer","declined","recorded").contains(outcome))throw new EhrException(422,"联系结果无效");if(!contact.isEmpty())try{if(java.time.LocalDateTime.parse(contact).isAfter(java.time.LocalDateTime.now(java.time.ZoneOffset.ofHours(8)).plusMinutes(5)))throw new IllegalArgumentException();}catch(Exception ex){throw new EhrException(422,"实际联系或提醒时间无效，不能在未来");}if(!outcome.isEmpty())lastContact=log;}
        if("completed".equals(status)&&(text(r,"completionEvidence").isEmpty()||!"reached".equals(text(r,"lastOutcome"))||lastContact==null||!"reached".equals(value(lastContact,"outcome"))||value(lastContact,"contactAt").isEmpty()||value(lastContact,"note").trim().isEmpty()))throw new EhrException(422,"完成任务须提供实际联系时间、真实回复及完成依据");
        if("unanswered".equals(status)&&(!"no_answer".equals(text(r,"lastOutcome"))||lastContact==null||!"no_answer".equals(value(lastContact,"outcome"))||value(lastContact,"contactAt").isEmpty()||value(lastContact,"note").trim().isEmpty()))throw new EhrException(422,"未回复状态必须有实际未接通记录");
        if("manual".equals(status)&&text(r,"manualReason").isEmpty())throw new EhrException(422,"人工介入须填写原因与安排");
    }

    private void hubDate(String value,boolean required,boolean notFuture){if(value.isEmpty()){if(required)throw new EhrException(422,"缺少业务日期");return;}try{java.time.LocalDate d=java.time.LocalDate.parse(value);if(notFuture&&d.isAfter(java.time.LocalDate.now(java.time.ZoneOffset.ofHours(8))))throw new IllegalArgumentException();}catch(Exception ex){throw new EhrException(422,"业务日期无效或实际日期在未来");}}
    private void hubActualTime(String value){try{if(java.time.LocalDateTime.parse(value).isAfter(java.time.LocalDateTime.now(java.time.ZoneOffset.ofHours(8)).plusMinutes(5)))throw new IllegalArgumentException();}catch(Exception ex){throw new EhrException(422,"实际发生时间无效或在未来");}}
    @SuppressWarnings("unchecked") private void hubValidate(EhrActor a,String key,Map<String,Object> r,Map<String,Object> prior,Map<String,Object> state,Set<String> checked){
        if(!Arrays.asList("revisits","alerts","care","projects").contains(key)||EhrService.num(r.get("hubVersion"))!=1)return;
        if(text(r,"title").isEmpty()||text(r,"title").length()>120||text(r,"content").length()>2000||text(r,"notes").length()>500||text(r,"problem").length()>200)throw new EhrException(422,"业务名称或内容为空、超长");
        String status=text(r,"status");List<String> states="revisits".equals(key)?Arrays.asList("pending","completed","cancelled"):"alerts".equals(key)?Arrays.asList("pending","processing","resolved","closed"):"care".equals(key)?Arrays.asList("active","observation","referred","completed","cancelled"):Arrays.asList("draft","active","completed","cancelled");if(!states.contains(status))throw new EhrException(422,"业务状态无效");
        if(db.count("SELECT COUNT(*) FROM yh_user_reginfo WHERE user_code=? AND Org_Code=? AND User_Type=4 AND lock_flag=2",text(r,"ownerId"),a.scope.substring(4))!=1)throw new EhrException(422,"负责人员须为本单位启用医生");
        Object raw=r.get("logs");if(!(raw instanceof List)||((List<?>)raw).size()>1000)throw new EhrException(422,"业务记录格式无效");List<?> logs=(List<?>)raw;if(prior!=null&&prior.get("logs") instanceof List){List<?> before=(List<?>)prior.get("logs");if(logs.size()<before.size()||!logs.subList(0,before.size()).equals(before))throw new EhrException(422,"已有操作记录不能删除或改写");}
        boolean reached=false,handled=false;for(Object entry:logs){if(!(entry instanceof Map))throw new EhrException(422,"操作记录格式无效");Map<?,?> log=(Map<?,?>)entry;if(value(log,"note").length()>4000)throw new EhrException(422,"记录说明过长");if(!value(log,"contactAt").isEmpty())hubActualTime(value(log,"contactAt"));if("consultation".equals(value(log,"kind"))&&"reached".equals(value(log,"outcome"))&&!value(log,"contactAt").isEmpty()&&!value(log,"note").trim().isEmpty())reached=true;if("handling".equals(value(log,"kind"))&&!value(log,"contactAt").isEmpty()&&!value(log,"note").trim().isEmpty())handled=true;}
        if(!"projects".equals(key)){
            if(text(r,"recordId").isEmpty()||text(r,"problem").isEmpty())throw new EhrException(422,"业务必须关联真实职工及关注事项");hubDate(text(r,"due"),true,false);
            if(prior==null&&text(r,"due").compareTo(java.time.LocalDate.now(java.time.ZoneOffset.ofHours(8)).toString())<0)throw new EhrException(422,"新业务计划日期不能早于今天");
            if(!text(r,"planId").isEmpty()){boolean valid=false;for(Object entry:(List<?>)state.get("plans"))if(entry instanceof Map){Map<?,?> plan=(Map<?,?>)entry;if(text(r,"planId").equals(value(plan,"id"))&&text(r,"recordId").equals(value(plan,"recordId"))&&!value(plan,"id").startsWith("sample-")){valid=true;break;}}if(!valid)throw new EhrException(422,"关联健康方案不属于当前职工");}
        }
        if("revisits".equals(key)){
            if(text(r,"metric").isEmpty()||text(r,"unit").isEmpty()||text(r,"metric").length()>100||text(r,"unit").length()>60)throw new EhrException(422,"请核对指标名称和单位");
            for(String f:Arrays.asList("before","after"))if(!text(r,f).isEmpty())try{if(!Double.isFinite(Double.parseDouble(text(r,f))))throw new IllegalArgumentException();}catch(Exception ex){throw new EhrException(422,"复查结果必须为有效数值");}
            if(!text(r,"before").isEmpty()){hubDate(text(r,"beforeDate"),true,true);if(text(r,"beforeSource").isEmpty())throw new EhrException(422,"前次结果缺少资料来源");}
            if("completed".equals(status)){if(text(r,"after").isEmpty()||text(r,"resultSource").isEmpty()||text(r,"resultNote").isEmpty())throw new EhrException(422,"完成复查需实际结果、资料来源和复核说明");hubDate(text(r,"resultDate"),true,true);if(!text(r,"beforeDate").isEmpty()&&text(r,"beforeDate").compareTo(text(r,"resultDate"))>0)throw new EhrException(422,"前次检查不能晚于本次检查");}
            if(!Arrays.asList("unknown","improved","partial","unchanged","worsened").contains(text(r,"outcome")))throw new EhrException(422,"复查效果评估无效");if(!"unknown".equals(text(r,"outcome"))&&(prior==null||!Objects.equals(prior.get("outcome"),r.get("outcome")))&&!a.doctor)throw new EhrException(403,"医学效果评估需医生记录");
            if(prior!=null&&!text(prior,"after").isEmpty()&&(!text(prior,"metric").equals(text(r,"metric"))||!text(prior,"unit").equals(text(r,"unit"))))throw new EhrException(422,"已有本次结果时不能直接替换指标或单位，请另建复查项目");
            if(text(r,"adjustment").length()>1500||text(r,"resultNote").length()>1000||text(r,"resultSource").length()>500||text(r,"beforeSource").length()>500)throw new EhrException(422,"复查说明过长");if(prior!=null&&!Objects.equals(prior.get("adjustment"),r.get("adjustment"))&&!a.doctor)throw new EhrException(403,"方案调整建议需医生记录");
        }else if("alerts".equals(key)){
            if(text(r,"content").isEmpty()||text(r,"type").isEmpty()||text(r,"type").length()>100)throw new EhrException(422,"请补全预警实际内容与类型");if(prior==null&&!"pending".equals(status))throw new EhrException(422,"新预警须以待处理状态登记");
            if(Arrays.asList("resolved","closed").contains(status)&&(!handled||text(r,"handlingEvidence").isEmpty()))throw new EhrException(422,"已处置或关闭需实际处置时间及依据");if("closed".equals(status)&&(prior==null||!"closed".equals(text(prior,"status")))&&!a.doctor)throw new EhrException(403,"关闭预警需医生复核");if(prior!=null&&"closed".equals(text(prior,"status"))&&!Objects.equals(prior,r))throw new EhrException(422,"已关闭预警不能直接修改");
        }else if("care".equals(key)){
            if(text(r,"concern").isEmpty()||text(r,"concern").length()>2000||text(r,"content").isEmpty()||text(r,"evaluation").length()>2000)throw new EhrException(422,"请填写真实关怀关注点、安排与有效评估说明");
            if(!text(r,"lastDue").isEmpty()&&!reached)throw new EhrException(422,"最近联系日期需实际联系记录支持");
            if("referred".equals(status)){Object referral=r.get("referral");if(!(referral instanceof Map))throw new EhrException(422,"转介资料不完整");Map<?,?> ref=(Map<?,?>)referral;if(!Boolean.TRUE.equals(ref.get("consent"))||value(ref,"institution").trim().isEmpty()||value(ref,"contact").trim().isEmpty()||value(ref,"receipt").trim().isEmpty()||value(ref,"note").trim().isEmpty())throw new EhrException(422,"转介须记录本人意愿、接收机构与实际接收安排");hubActualTime(value(ref,"time"));}
            if("completed".equals(status)&&(!reached||text(r,"evaluation").isEmpty()))throw new EhrException(422,"完成关怀阶段需实际咨询记录及效果依据");if(prior!=null&&!Objects.equals(prior.get("evaluation"),r.get("evaluation"))&&!a.doctor)throw new EhrException(403,"关怀效果评估需医生复核");
        }else{
            hubDate(text(r,"start"),true,false);hubDate(text(r,"end"),true,false);if(text(r,"end").compareTo(text(r,"start"))<0||text(r,"content").isEmpty())throw new EhrException(422,"项目周期或实施内容无效");
            Object members=r.get("memberIds"),entries=r.get("entries");if(!(members instanceof List)||((List<?>)members).isEmpty()||((List<?>)members).size()>5000||!(entries instanceof List)||((List<?>)entries).size()>2000)throw new EhrException(422,"项目覆盖职工或参与记录格式无效");Set<String> ids=new HashSet<>();for(Object id:(List<?>)members)if(!(id instanceof String)||!ids.add((String)id))throw new EhrException(422,"覆盖职工无效或重复");List<String> unchecked=new ArrayList<>();for(String id:ids)if(!checked.contains(id))unchecked.add(id);
            if(!unchecked.isEmpty()){List<Object> args=new ArrayList<>();args.add(a.scope);args.add(a.mode);args.addAll(unchecked);String marks=String.join(",",Collections.nCopies(unchecked.size(),"?"));List<Map<String,Object>> scoped=db.all("SELECT r.record_id id"+ehr.baseSql()+" AND r.record_id IN ("+marks+")",args.toArray());Set<String> scopedIds=new HashSet<>();for(Map<String,Object> person:scoped)scopedIds.add(value(person,"id"));if(!scopedIds.equals(new HashSet<>(unchecked)))throw new EhrException(403,"项目覆盖范围包含不可访问职工");checked.addAll(unchecked);}
            if(prior!=null&&prior.get("entries") instanceof List){List<?> previous=(List<?>)prior.get("entries");if(((List<?>)entries).size()<previous.size()||!((List<?>)entries).subList(0,previous.size()).equals(previous))throw new EhrException(422,"实际参与与积分历史不能删除或改写");}
            int baseP=prior==null?0:EhrService.num(prior.get(EhrService.num(prior.get("hubVersion"))==1?"legacyParticipants":"participants")),baseD=prior==null?0:EhrService.num(prior.get(EhrService.num(prior.get("hubVersion"))==1?"legacyDone":"done")),basePoints=prior==null?0:EhrService.num(prior.get(EhrService.num(prior.get("hubVersion"))==1?"legacyPoints":"points"));r.put("legacyParticipants",baseP);r.put("legacyDone",baseD);r.put("legacyPoints",basePoints);
            int participants=baseP,done=baseD,points=basePoints;Set<String> entryIds=new HashSet<>();for(Object entry:(List<?>)entries){if(!(entry instanceof Map))throw new EhrException(422,"实际参与记录无效");Map<?,?> event=(Map<?,?>)entry;if(!ids.contains(value(event,"recordId"))||!entryIds.add(value(event,"id"))||value(event,"id").isEmpty()||!Boolean.TRUE.equals(event.get("verified"))||value(event,"note").trim().isEmpty()||value(event,"note").length()>1500)throw new EhrException(422,"参与记录须关联覆盖职工并填写真实依据");String kind=value(event,"kind");if(!Arrays.asList("attendance","completion","points").contains(kind))throw new EhrException(422,"参与记录事项无效");hubActualTime(value(event,"occurredAt"));Object score=event.get("points");if(!(score instanceof Number)||!Double.isFinite(((Number)score).doubleValue())||((Number)score).doubleValue()!=((Number)score).intValue()||Math.abs(((Number)score).intValue())>1000)throw new EhrException(422,"积分须为-1000至1000的整数");points+=((Number)score).intValue();if(!"points".equals(kind))participants++;if("completion".equals(kind))done++;}
            if(points<0)throw new EhrException(422,"累计积分不能小于0");r.put("participants",participants);r.put("done",done);r.put("points",points);
            if(text(r,"effect").length()>2000)throw new EhrException(422,"项目效果说明过长");if("completed".equals(status)&&(((List<?>)entries).isEmpty()||text(r,"effect").isEmpty()))throw new EhrException(422,"项目完成需实际参与记录和效果依据");
        }
    }

}
