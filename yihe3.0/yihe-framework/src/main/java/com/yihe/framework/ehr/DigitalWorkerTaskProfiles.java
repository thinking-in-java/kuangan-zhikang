package com.yihe.framework.ehr;

import java.util.*;
import static com.yihe.framework.ehr.EhrAccess.*;

/** Product workflow definitions. References guide organisation of work, not clinical thresholds. */
public final class DigitalWorkerTaskProfiles {
    private DigitalWorkerTaskProfiles(){}
    static Map<String,Object> field(String key,String label,String type,Object... options) {return map("key",key,"label",label,"type",type,"options",Arrays.asList(options));}
    static Map<String,Object> source(String title,String url){return map("title",title,"url",url);}
    static final Map<String,Object> NHC=source("国家卫健委：2025年基本公共卫生服务工作","https://www.nhc.gov.cn/jws/c100073/202506/14a23782324542f59137bbf24a1c988f.shtml");
    static final Map<String,Object> AHRQ=source("AHRQ：健康沟通、随访和行动计划工具（第三版）","https://www.ahrq.gov/sites/default/files/publications2/files/health-literacy-toolkit-third-edition.pdf");
    static final Map<String,Object> LAB=source("MedlinePlus：理解化验结果与本报告参考范围","https://medlineplus.gov/lab-tests/how-to-understand-your-lab-results/");
    static final Map<String,Object> NICE=source("NICE NG197：共同决策与风险沟通","https://www.nice.org.uk/guidance/ng197/chapter/recommendations");
    static final Map<String,Object> COMPLAINT=source("英国公共服务监察专员：医疗投诉处理标准","https://www.ombudsman.org.uk/handling-complaint/complaint-standards/nhs-complaint-standards");
    static List<Map<String,Object>> contactFields(){return new ArrayList<>(Arrays.asList(
        field("contactOutcome","实际联系结果","select","not_contacted|尚未联系","no_answer|未接通","reached|已联系","declined|拒绝本次随访"),
        field("contactMethod","实际联系渠道","select","phone|电话","visit|当面","miniapp|小程序","other|其他"),
        field("contactAt","实际联系时间","datetime-local"),field("feedback","职工实际反馈","textarea"),
        field("measurements","本次测量或复查结果（注明日期、单位与来源）","textarea"),field("nextDueDate","约定下次跟进日期","date")));}
    static Map<String,Object> profile(String type,String name,String title,String purpose,String worker,List<Map<String,Object>> fields,List<String> outputs,Object... refs){return map("type",type,"name",name,"resultTitle",title,"purpose",purpose,"recommendedWorker",worker,"fields",fields,"outputs",outputs,"references",Arrays.asList(refs));}
    public static List<Map<String,Object>> all(){List<Map<String,Object>> profiles=new ArrayList<>();
        profiles.add(profile("visit_invitation","随访问询（发会员）","问询内容与发送准备","生成可审核的会员问询内容和问题清单；没有发送回执就保持未发送。","follow",Arrays.asList(field("topic","本次问询重点","textarea"),field("channel","期望发送渠道","select","miniapp|小程序","phone|电话人工联系"),field("preferredTime","职工方便联系的时段","text")),Arrays.asList("接收对象","问询内容","待收集信息","实际发送状态"),AHRQ,NHC));
        profiles.add(profile("routine_followup","常规随访执行","随访记录与下次安排","区分随访准备和已发生的联系，记录真实反馈及下一步安排。","follow",contactFields(),Arrays.asList("上次随访","本次联系事实","健康反馈","下次安排"),NHC,AHRQ));
        profiles.add(profile("alert_handling","异常预警处置","异常依据与处置清单","核验报告原有异常标记、审核状态和处置记录，不用单项异常自动确诊。","assess",Arrays.asList(field("symptoms","当前症状或补充情况（人工记录）","textarea"),field("actionsTaken","已经实际完成的处置与回执","textarea"),field("handlingOwner","负责人员","text"),field("nextDueDate","约定复核日期","date")),Arrays.asList("异常依据","资料可信度","实际处置记录","待医生确认事项"),NHC,LAB));
        profiles.add(profile("abnormal_followup","异常随访","异常随访与复查闭环","围绕已标记异常追踪实际反馈、复查和转诊信息；没有回执不声称闭环。","follow",contactFields(),Arrays.asList("关联异常","联系与反馈","复查信息","未闭环事项"),NHC,AHRQ));
        profiles.add(profile("plan_adjustment","慢病方案调整","方案调整对照与审核","以原方案、本人目标和真实随访资料形成调整选项，不自动改药或执行干预。","plan",Arrays.asList(field("currentPlan","当前正在执行的方案及来源","textarea"),field("goals","本人希望达成的目标","textarea"),field("barriers","执行困难或不良体验","textarea"),field("measurements","近期测量或复查结果（含日期和单位）","textarea"),field("nextDueDate","约定复核日期","date")),Arrays.asList("原方案","调整理由","待审核调整选项","监测与复核"),NHC,NICE,AHRQ));
        profiles.add(profile("report_interpretation","报告解读","体检报告逐项解读","读取真实录入结果、报告原件及原文建议，解释已知信息并标明待核实部分。","assess",Arrays.asList(field("concerns","本次重点希望解读的问题","textarea")),Arrays.asList("报告与原件","逐项结果","历史可比性","原文结论和建议"),LAB,NICE));
        profiles.add(profile("archive_completion","档案补全","原件核对结果","保留原件逐页读取、逐项对照、差异与无法核实清单。","archive",Collections.emptyList(),Arrays.asList("原件指纹与页码","逐项对照","确定差异","疑似漏录与待复核"),LAB));
        profiles.add(profile("assessment","综合评估","健康资料与评估依据","汇总已确认风险、报告线索与职业背景；没有已确认评估就不生成分数或风险等级。","assess",Arrays.asList(field("knownHistory","本人已确认的病史及信息来源","textarea"),field("symptoms","本次实际反馈的症状或情况","textarea"),field("goals","本人关注的问题和目标","textarea")),Arrays.asList("已有评估","报告线索","职业背景","不足资料及下一步"),NICE,NHC,LAB));
        List<Map<String,Object>> complaint=new ArrayList<>(Arrays.asList(field("complaintText","投诉事实与原话（注明来源）","textarea"),field("expectedResolution","本人希望解决的问题","textarea"),field("handlingOwner","实际负责人员","text"),field("reply","已经实际给出的答复或处理回执","textarea"),field("contactOutcome","实际联系结果","select","not_contacted|尚未联系","no_answer|未接通","reached|已联系","declined|拒绝本次联系"),field("contactAt","实际联系时间","datetime-local"),field("nextDueDate","约定答复或回访日期","date")));
        profiles.add(profile("complaint_followup","会员投诉跟进","投诉事实、答复与跟进","记录投诉、诉求、调查依据和真实回执，不凭模型文字宣称投诉已解决。","follow",complaint,Arrays.asList("投诉与诉求","实际答复","负责人与约定时间","待跟进事项"),COMPLAINT,AHRQ));
        return profiles;
    }
    public static Map<String,Object> get(String type){return all().stream().filter(p->type.equals(p.get("type"))).findFirst().orElseThrow(()->new EhrException(422,"任务类型无效"));}
    @SuppressWarnings("unchecked") public static Map<String,Object> context(String type,Object raw){
        if(raw==null)return new TreeMap<>();if(!(raw instanceof Map))throw new EhrException(422,"本次任务资料格式无效");
        Map<String,Object> result=new TreeMap<>();Map<?,?> input=(Map<?,?>)raw;
        for(Map<String,Object> field:(List<Map<String,Object>>)get(type).get("fields")){
            String key=text(field,"key");Object v=input.get(key);if(v==null)continue;
            if(!(v instanceof String))throw new EhrException(422,"请检查本次任务资料："+field.get("label"));
            String value=((String)v).trim();if(value.length()>2000||value.indexOf(0)>=0)throw new EhrException(422,"本次任务资料过长或格式无效");
            if(!value.isEmpty()&&"select".equals(field.get("type"))&&((List<String>)field.get("options")).stream().noneMatch(o->o.split("\\|",2)[0].equals(value)))throw new EhrException(422,"联系渠道或状态无效");
            if(!value.isEmpty()&&"date".equals(field.get("type")))try{java.time.LocalDate.parse(value);}catch(Exception error){throw new EhrException(422,"请使用有效日期");}
            if(!value.isEmpty()&&"datetime-local".equals(field.get("type")))try{java.time.LocalDateTime.parse(value);}catch(Exception error){throw new EhrException(422,"请使用有效联系时间");}
            if(!value.isEmpty())result.put(key,value);
        }
        for(Object key:input.keySet())if(!result.containsKey(key)&&!((List<Map<String,Object>>)get(type).get("fields")).stream().anyMatch(f->Objects.equals(f.get("key"),key)))throw new EhrException(422,"本次任务资料包含未支持的字段");
        if("reached".equals(result.get("contactOutcome"))){
            if(text(result,"contactAt").isEmpty())throw new EhrException(422,"已联系记录需要填写实际联系时间");
            if(java.time.LocalDateTime.parse(text(result,"contactAt")).isAfter(java.time.LocalDateTime.now(java.time.ZoneOffset.ofHours(8)).plusMinutes(5)))throw new EhrException(422,"实际联系时间不能在未来");
        }
        return result;
    }
}
