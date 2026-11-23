package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Nine task workflows share provenance and review, not a generic paragraph template. */
@Service
public class DigitalWorkerTaskResults {
    private final EhrRepository db;private final EhrService ehr;private final DigitalWorkerArchiveContext archives;
    private final DigitalWorkerOriginalCheck originals;private final DigitalWorkerModelClient model;
    public DigitalWorkerTaskResults(EhrRepository db,EhrService ehr,DigitalWorkerArchiveContext archives,DigitalWorkerOriginalCheck originals,DigitalWorkerModelClient model){this.db=db;this.ehr=ehr;this.archives=archives;this.originals=originals;this.model=model;}
    static Map<String,Object> fact(String label,Object value){return map("label",label,"value",value==null||str(value).isEmpty()?"未记录":value);}
    static Map<String,Object> section(String title,String kind,Object rows){return map("title",title,"kind",kind,"rows",rows);}
    static Map<String,Object> action(String action,String owner,String timing,String evidence){return map("action",action,"owner",owner,"timing",timing,"completionEvidence",evidence);}
    static String digestContext(EhrService ehr,Map<String,Object> context){return hex(digest(bytes(ehr.encode(new TreeMap<>(context)))));}
    private String value(Map<String,Object> context,String key,String fallback){String value=text(context,key);return value.isEmpty()?fallback:value;}
    static boolean flagged(Map<String,Object> row){String f=text(row,"abnormal");return Arrays.asList("H","L","HH","LL","↑","↓","+","++","+++","阳性","异常","偏高","偏低").contains(f)||f.matches(".*(升高|降低|偏高|偏低|超标).*" );}

    @SuppressWarnings("unchecked")
    public Map<String,Object> generate(Map<String,Object> input){
        String type=text(input,"task_type"),recordId=text(input,"record_id");Map<String,Object> profile=DigitalWorkerTaskProfiles.get(type);
        Map<String,Object> context=input.get("task_context_json")==null?new TreeMap<>():(Map<String,Object>)ehr.decode(input.get("task_context_json"));
        String scope=text(input,"scope_code");EhrActor actor=new EhrActor(text(input,"requested_by"),scope.substring(4),text(input,"data_mode"),true,false);
        List<Map<String,Object>> sections=new ArrayList<>(),gaps=new ArrayList<>(),evidence=new ArrayList<>(),risk=new ArrayList<>(),followups=new ArrayList<>(),reports=new ArrayList<>();
        Map<String,Object> archive=(Map<String,Object>)input.get("archive_snapshot"),report=null,previous=null,bundle=map("available",false,"reason","本任务未请求读取原件","sources",Collections.emptyList(),"pages",Collections.emptyList());
        Map<String,Object> person=null;String reportDigest=null;List<Map<String,Object>> items=new ArrayList<>(),abnormal=new ArrayList<>();
        if(!recordId.isEmpty()){
            person=ehr.profile(actor,recordId,false);
            reports=db.all("SELECT h.report_id reportId,h.revision,h.exam_date_verified examDate,h.institution_raw institution,h.review_status reviewStatus FROM a_health_reports h JOIN a_report_series s ON s.report_id=h.report_id AND s.current_revision=h.revision WHERE h.record_id=? AND h.scope_code=? AND h.data_mode=? ORDER BY h.created_at DESC,h.report_id DESC LIMIT 2",recordId,actor.scope,actor.mode);
            risk=db.all("SELECT assessment_id id,risk_level level,risk_score score,rule_version ruleVersion,assessment_type type,assessment_time assessedAt FROM a_health_risk_assessments WHERE record_id=? AND scope_code=? AND data_mode=? AND assessment_status='confirmed' ORDER BY assessment_time DESC,assessment_id DESC LIMIT 5",recordId,actor.scope,actor.mode);
            followups=db.all("SELECT f.task_id id,f.status,f.due_at dueAt,f.report_id reportId,f.revision FROM a_followup_tasks f JOIN a_health_reports h ON h.report_id=f.report_id AND h.revision=f.revision WHERE h.record_id=? AND h.scope_code=? AND h.data_mode=? ORDER BY f.created_at DESC LIMIT 20",recordId,actor.scope,actor.mode);
            if(!reports.isEmpty()){
                Map<String,Object> selected=reports.get(0);report=ehr.report(actor,text(selected,"reportId"),num(selected.get("revision")),false);
                reportDigest=archives.reportDigest(actor,recordId,text(report,"id"),num(report.get("revision")));
                items=(List<Map<String,Object>>)report.get("items");
                Map<String,Map<String,Object>> quotes=new HashMap<>();for(Map<String,Object> quote:(List<Map<String,Object>>)report.get("fieldEvidence"))quotes.put(text(quote,"id"),quote);
                for(Map<String,Object> raw:items){Map<String,Object> row=new LinkedHashMap<>(raw);Map<String,Object> q=quotes.get(text(row,"evidenceId"));row.put("sourceId",q==null?null:q.get("sourceId"));row.put("quote",q==null?null:q.get("quote"));row.put("hasFlag",flagged(row));row.put("reportId",report.get("id"));row.put("revision",report.get("revision"));row.put("reviewed","confirmed".equals(report.get("reviewStatus")));evidence.add(row);if(flagged(row))abnormal.add(row);}
                if(reports.size()>1)previous=ehr.report(actor,text(reports.get(1),"reportId"),num(reports.get(1).get("revision")),false);
                if(Arrays.asList("report_interpretation","assessment","alert_handling","abnormal_followup","plan_adjustment").contains(type))bundle=originals.sourceBundle(actor,text(report,"id"),num(report.get("revision")));
            }
        }
        boolean linked=person!=null,contacted="reached".equals(context.get("contactOutcome"));String state="ready_for_review",execution="not_executed",message="";
        if(!linked&&!"complaint_followup".equals(type)){state="blocked";gaps.add(fact("职工档案","请先按姓名或工号明确关联职工"));}
        if(report==null&&Arrays.asList("report_interpretation","alert_handling","abnormal_followup","plan_adjustment","assessment").contains(type)){if(!"blocked".equals(state))state="needs_info";gaps.add(fact("报告依据","未读取到当前职工报告，不能生成个体化临床结论"));}
        if(report!=null&&!"confirmed".equals(report.get("reviewStatus"))&&Arrays.asList("report_interpretation","alert_handling","abnormal_followup","plan_adjustment","assessment").contains(type)){state="needs_info";gaps.add(fact("报告审核","当前结果尚未审核，不得当成正式诊断或处置依据"));}
        if(linked&&"report_interpretation".equals(type)&&!Boolean.TRUE.equals(bundle.get("available"))){state="needs_info";gaps.add(fact("报告原件",bundle.get("reason")));}
        Map<String,Object> contact=map("not_contacted","尚未联系","no_answer","未接通","reached","已联系（人工记录）","declined","拒绝本次联系");
        List<Map<String,Object>> contactFacts=Arrays.asList(fact("实际联系结果",contact.getOrDefault(text(context,"contactOutcome"),"尚未联系")),fact("实际联系渠道",map("phone","电话","visit","当面","miniapp","小程序","other","其他").getOrDefault(text(context,"contactMethod"),"未记录")),fact("联系时间（北京时间）",context.get("contactAt")),fact("实际反馈",context.get("feedback")),fact("测量/复查资料（人工记录）",context.get("measurements")),fact("下次约定日期",context.get("nextDueDate")));
        switch(type){
        case "visit_invitation":
            execution="not_sent";message="您好，为便于持续健康管理，请在方便时反馈以下信息：\n1. 最近有哪些不适或需要帮助的问题？\n2. 是否已按约定完成复查或随访？如有，请说明日期和来源。\n3. 目前执行健康管理建议有哪些困难？\n4. 哪个时段方便工作人员与您联系？";
            if(!text(context,"topic").isEmpty())message+="\n本次重点："+text(context,"topic");
            sections.add(section("问询与发送状态","facts",Arrays.asList(fact("接收对象",linked?person.get("employee_name"):null),fact("期望渠道",map("miniapp","小程序","phone","电话人工联系").getOrDefault(text(context,"channel"),"未选择")),fact("发送状态","未发送；本模块没有发送通道回执"),fact("联系时段",context.get("preferredTime")))));
            sections.add(section("待审核的会员问询内容","message",message));
            sections.add(section("发送前与反馈后的处理","actions",Arrays.asList(action("核对接收对象与授权后审核问询内容","当前医生","发送前","审核记录"),action("在已授权的实际渠道发送并保存回执","负责联系人员","审核后、与职工约定","真实发送回执；复制文本不代表发送"),action("收到反馈后录入随访事实","负责随访人员","收到反馈时","反馈原话、时间及记录人"))));break;
        case "routine_followup":case "abnormal_followup":
            execution=contacted?"contact_recorded":value(context,"contactOutcome","not_contacted");
            if(!contacted){state="needs_info";gaps.add(fact("执行事实","尚未记录已联系，结果是随访准备清单，不是已完成随访"));}
            if("abnormal_followup".equals(type))sections.add(section("本次需跟进的报告标记","evidence",abnormal));
            sections.add(section("本次随访事实","facts",contactFacts));sections.add(section("原系统关联随访任务","followups",followups));
            sections.add(section("围绕本次情况需确认的问题","questions",Arrays.asList("本次反馈是否对应当前职工，发生或测量时间是什么？","是否完成此前复查或就诊，能否提供原始结果或回执？","哪些建议已经执行，存在什么困难？","本人能否复述下一步安排，是否同意下次联系时间？")));
            sections.add(section("未闭环事项","actions",Arrays.asList(action("核对本人反馈与复查资料","负责随访人员",value(context,"nextDueDate","日期待与职工约定"),"真实联系记录与原始结果"),action("将需专业判断的问题提交医生核验","当前医生","形成建议前","医生审核记录"))));break;
        case "alert_handling":
            execution=text(context,"actionsTaken").isEmpty()?"not_disposed":"disposition_recorded";if(text(context,"actionsTaken").isEmpty()){if(!"blocked".equals(state))state="needs_info";gaps.add(fact("实际处置","尚无已发生的处置和回执，请据实补充后复核"));}
            sections.add(section("报告已有异常标记（不等同危急值或确诊）","evidence",abnormal));
            sections.add(section("本次处置事实","facts",Arrays.asList(fact("当前情况（人工补充）",context.get("symptoms")),fact("已实际完成的处置",context.get("actionsTaken")),fact("负责人员",context.get("handlingOwner")),fact("约定复核日期",context.get("nextDueDate")))));
            sections.add(section("处置清单","actions",Arrays.asList(action("核对原件、单位、参考范围及审核状态","当前医生","作出判断前","原件页码与审核记录"),action("结合当前症状判断是否需要立即转诊或进一步核验","授权医生","人工评估后确定；系统不自动设定危急阈值","人工处置记录"),action("追踪处置和复查回执","负责人员",value(context,"nextDueDate","待设置"),"真实回执，不能仅凭生成文字闭环"))));break;
        case "plan_adjustment":
            execution="not_applied";if(text(context,"currentPlan").isEmpty()){state="needs_info";gaps.add(fact("原方案","未提供正在执行的方案，不能声称已调整方案"));}
            sections.add(section("调整前后对照","comparison",Arrays.asList(map("topic","当前方案","before",value(context,"currentPlan","未提供"),"proposal","先补充原方案及来源后，由授权人员确定是否调整","basis","人工补充，不能从体检报告猜测现用方案","status","待审核、未应用"),map("topic","本人目标与执行困难","before",value(context,"barriers","未记录执行困难"),"proposal",value(context,"goals","待与本人共同确认目标"),"basis","本人意愿与实际随访资料","status","待共同确认"),map("topic","监测与复核","before",value(context,"measurements","未提供近期测量或复查结果"),"proposal",value(context,"nextDueDate","复核日期待人工约定"),"basis","本次实际测量与医生审核","status","未执行"))));
            sections.add(section("需结合报告复核的线索","evidence",abnormal));
            sections.add(section("应用前待办","actions",Arrays.asList(action("核对现方案、本人目标与执行困难","当前医生","形成方案前","原方案和随访记录"),action("讨论可行选项后通过原健康干预模块审核应用","授权干预人员","本人参与和医生审核后","原业务模块审批及应用回执"))));break;
        case "report_interpretation":
            execution="interpretation_prepared";sections.add(section("体检报告逐项结果","evidence",evidence));
            sections.add(section("报告结论原文","paragraphs",report==null?Collections.emptyList():report.get("conclusions")));sections.add(section("报告医师建议原文","paragraphs",report==null?Collections.emptyList():report.get("advice")));
            sections.add(section("历史结果可比性","comparison",trends(evidence,report,previous)));break;
        case "assessment":
            execution="assessment_prepared";if(risk.isEmpty())gaps.add(fact("综合风险等级","没有已确认的评估记录；不生成风险分数或默认判为低风险"));
            sections.add(section("已有确认评估","risks",risk));sections.add(section("需核验的报告线索","evidence",abnormal));
            sections.add(section("综合评估维度","facts",Arrays.asList(fact("已确认风险记录",risk.size()),fact("岗位",linked?person.get("position_name"):null),fact("职业接触信息",linked?person.get("exposure_history"):null),fact("病史与来源（人工补充）",context.get("knownHistory")),fact("当前反馈（人工补充）",context.get("symptoms")),fact("本人目标",context.get("goals")))));
            sections.add(section("下一步评估工作","actions",Arrays.asList(action("核对报告与职业背景，补全不足资料","当前医生","形成正式评估前","可追溯报告及接触资料"),action("由授权专业人员按适用规则评估并审核","授权评估人员","人工确定","正式规则版本、审核人和评估记录"))));break;
        case "complaint_followup":
            execution=text(context,"reply").isEmpty()?"not_replied":"reply_recorded";if(text(context,"complaintText").isEmpty()){state="needs_info";gaps.add(fact("投诉事实","请录入投诉原话、时间与来源，不能从任务标题推定投诉事实"));}
            if(text(context,"reply").isEmpty()||!contacted){state="needs_info";gaps.add(fact("实际跟进","尚未记录已联系及实际答复，不能视为投诉跟进完成"));}
            sections.add(section("投诉事实与期望","facts",Arrays.asList(fact("投诉原话与来源",context.get("complaintText")),fact("本人希望解决的问题",context.get("expectedResolution")),fact("实际负责人员",context.get("handlingOwner")),fact("约定答复日期",context.get("nextDueDate")),fact("已经实际给出的答复",context.get("reply")),fact("联系结果",contact.getOrDefault(text(context,"contactOutcome"),"尚未联系")),fact("联系时间",context.get("contactAt")))));
            sections.add(section("投诉跟进清单","actions",Arrays.asList(action("确认投诉事实、范围及本人希望解决的问题",value(context,"handlingOwner","负责人待设置"),"开始跟进时","投诉原话和相关记录"),action("核实相关记录，形成有依据的答复","实际负责人员",value(context,"nextDueDate","与本人约定"),"调查依据、答复内容及回执"),action("回访本人是否收到答复和仍有何诉求","负责跟进人员","答复后约定","本人实际反馈；不自动标记满意或已解决"))));break;
        default:throw new EhrException(422,"此任务类型应使用原件核对流程");
        }
        if(text(context,"nextDueDate").isEmpty()&&Arrays.asList("routine_followup","abnormal_followup","complaint_followup","plan_adjustment").contains(type))gaps.add(fact("跟进时间","尚未约定下次日期"));
        List<Map<String,Object>> interpretItems=new ArrayList<>(abnormal.subList(0,Math.min(30,abnormal.size())));for(Map<String,Object> row:evidence)if("report_interpretation".equals(type)&&interpretItems.size()<30&&!interpretItems.contains(row))interpretItems.add(row);
        Map<String,Object> ai=Collections.emptyMap();boolean modelUsed=false;
        boolean nonClinical=Arrays.asList("visit_invitation","routine_followup","complaint_followup").contains(type);
        if(!"blocked".equals(state)&&(nonClinical||report!=null&&"confirmed".equals(report.get("reviewStatus")))&&(!"report_interpretation".equals(type)||Boolean.TRUE.equals(bundle.get("available")))){
            try{ai=model.taskInsights(map("taskType",type,"purpose",profile.get("purpose"),"instructions",input.get("instructions"),"manualFacts",context,"items",nonClinical?Collections.emptyList():interpretItems,"conclusions",nonClinical?Collections.emptyList():report.get("conclusions"),"advice",nonClinical?Collections.emptyList():report.get("advice"),"originalPages","report_interpretation".equals(type)?bundle.get("pages"):Collections.emptyList()));modelUsed=true;}
            catch(EhrException error){gaps.add(fact("辅助解读","模型未返回可用内容；事实数据和工作清单仍保留"));}
        }
        sections.addAll(insights(ai,evidence));

        if(contacted&&Arrays.asList("routine_followup","abnormal_followup").contains(type)&&text(context,"feedback").isEmpty()){state="needs_info";gaps.add(fact("实际反馈","已联系，但尚未记录职工反馈"));}
        if(!gaps.isEmpty())sections.add(section("资料缺口与待确认事项","facts",gaps));
        Map<String,Object> result=map("kind","task_result","schemaVersion",1,"taskType",type,"title",profile.get("resultTitle"),"state",state,"executionState",execution,"reviewRequired",true,
            "summary","blocked".equals(state)?"尚未关联职工，不能生成针对个人的处理结论。":summary(type,execution,contacted,evidence.size(),abnormal.size()),"generatedAt",java.time.OffsetDateTime.now(java.time.ZoneOffset.ofHours(8)).toString(),"sections",sections,
            "memberMessage",message,"references",profile.get("references"),"sourceReportId",report==null?null:report.get("id"),"sourceRevision",report==null?null:report.get("revision"),"sourceReportDigest",reportDigest,"contextDigest",digestContext(ehr,context),
            "sources",bundle.get("sources"),"originalRead",bundle.get("available"),"originalNote",bundle.get("reason"),"reportReviewed",report!=null&&"confirmed".equals(report.get("reviewStatus")),
            "sourceContext",map("recordId",recordId,"name",linked?person.get("employee_name"):null,"code",linked?person.get("employee_no"):null,"examDate",report==null?null:report.get("examDate"),"institution",report==null?null:report.get("institution")),
            "manualFacts",context,"manualFactsRecordedAt",input.get("context_saved_at"),"manualFactsRecordedBy",input.get("context_saved_by"),"modelUsed",modelUsed);
        return map("model",modelUsed?"task-workflow/"+text(model.status(),"model"):"task-workflow-v1","output",result.get("summary"),"usage","{}","taskResult",result);
    }

    private String summary(String type,String execution,boolean contacted,int total,int abnormal){
        switch(type){case "visit_invitation":return "已准备会员问询内容与反馈清单，尚未发送。";case "routine_followup":return contacted?"已整理人工录入的联系与反馈，待审核随访记录。":"已准备随访问题和工作清单；尚未记录实际联系。";case "abnormal_followup":return "已汇总 "+abnormal+" 条报告标记和异常随访事项；复查回执需据实补充。";case "alert_handling":return "已汇总 "+abnormal+" 条原报告标记，处置是否完成以人工记录和回执为准。";case "plan_adjustment":return "已整理原方案、本人目标及待审核调整事项，尚未应用。";case "report_interpretation":return "已读取 "+total+" 条录入项目，其中 "+abnormal+" 条有原报告异常标记；原件读取与审核状态另列。";case "assessment":return "已汇总报告线索、职业背景和已有评估，未自动生成风险等级。";default:return "已整理投诉事实、答复与跟进事项；是否解决以本人反馈和审核为准。";}
    }
    @SuppressWarnings("unchecked") private List<Map<String,Object>> insights(Map<String,Object> ai,List<Map<String,Object>> evidence){
        List<Map<String,Object>> sections=new ArrayList<>(),explanations=new ArrayList<>();Set<String> ids=new HashSet<>();for(Map<String,Object> row:evidence)ids.add(text(row,"id"));
        if(ai.get("explanations") instanceof List)for(Object raw:(List<?>)ai.get("explanations"))if(raw instanceof Map){Map<String,Object> row=(Map<String,Object>)raw;String text=text(row,"text");if(ids.contains(text(row,"evidenceId"))&&text.length()<=600&&!text.matches("(?s).*(已发送|已通知|已改药|已确诊|加量|减量|停药|换药|处方).*"))explanations.add(map("evidenceId",row.get("evidenceId"),"text",text));}
        if(!explanations.isEmpty())sections.add(section("针对报告项目的辅助说明（待审核）","explanations",explanations));
        for(String[] pair:new String[][]{{"questions","本次可补充询问的问题"},{"reviewNotes","需要专业人员确认的事项"},{"options","可讨论的方案选项（未应用）"}}){List<String> lines=new ArrayList<>();if(ai.get(pair[0]) instanceof List)for(Object raw:(List<?>)ai.get(pair[0]))if(raw instanceof String&&((String)raw).length()<=600&&!((String)raw).matches("(?s).*(已发送|已通知|已改药|已确诊|加量|减量|停药|换药|处方).*")){if(lines.size()<8)lines.add((String)raw);}if(!lines.isEmpty())sections.add(section(pair[1],"questions",lines));}
        return sections;
    }
    @SuppressWarnings("unchecked") private List<Map<String,Object>> trends(List<Map<String,Object>> current,Map<String,Object> report,Map<String,Object> previous){
        List<Map<String,Object>> rows=new ArrayList<>();if(previous==null){rows.add(map("topic","历史报告","before","未读取到另一份当前版本报告","proposal","本次不生成改善或恶化趋势","basis","需要可比的历史结果","status","不可判断"));return rows;}
        for(Map<String,Object> item:current){Map<String,Object> older=((List<Map<String,Object>>)previous.get("items")).stream().filter(i->(!text(item,"itemCode").isEmpty()&&Objects.equals(i.get("itemCode"),item.get("itemCode")))||Objects.equals(i.get("name"),item.get("name"))).findFirst().orElse(null);if(older==null)continue;
            boolean comparable="confirmed".equals(previous.get("reviewStatus"))&&Objects.equals(report.get("institution"),previous.get("institution"))&&Objects.equals(item.get("unit"),older.get("unit"))&&Objects.equals(item.get("referenceRange"),older.get("referenceRange"));
            rows.add(map("topic",item.get("name"),"before",older.get("value"),"proposal",item.get("value"),"basis","上次 "+previous.get("examDate")+" / 本次 "+report.get("examDate"),"status",comparable?"条件表面一致，仍需核对检测方法；不自动推断趋势":"历史报告未审核或机构、单位、参考范围不一致，不直接比较"));if(rows.size()>=40)break;
        }return rows;
    }
}
