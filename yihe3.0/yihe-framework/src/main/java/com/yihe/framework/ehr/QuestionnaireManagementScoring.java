package com.yihe.framework.ehr;

import java.util.*;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Explicit nonclinical management indices. Frozen releases retain their own rules. */
public final class QuestionnaireManagementScoring {
 private QuestionnaireManagementScoring(){}
 static final Set<String> DIMENSIONS=new HashSet<>(Arrays.asList("behavior","understanding","confidence","guidance","resources","barriers","access","satisfaction","demand","unscored"));
 static boolean managed(Map<String,Object> q){return q.get("scoringRule") instanceof Map&&"management".equals(((Map<?,?>)q.get("scoringRule")).get("mode"));}
 @SuppressWarnings("unchecked") static Map<String,Object> rule(Map<String,Object> q){return managed(q)?(Map<String,Object>)q.get("scoringRule"):Collections.emptyMap();}
 static Map<String,Object> validateRule(Map<String,Object> q){
  Map<String,Object> r=rule(q);if(r.isEmpty())return r;
  Object maximum=r.get("maxScore");String aggregation=str(r.get("aggregation"));
  require(DIMENSIONS.contains(str(r.get("dimension")))&&Arrays.asList("single","sum","none").contains(aggregation)&&maximum instanceof Number&&Double.isFinite(((Number)maximum).doubleValue())&&((Number)maximum).doubleValue()>=0&&((Number)maximum).doubleValue()<=100&&r.get("higherIsBetter") instanceof Boolean,422,"管理评分规则无效");
  require("none".equals(aggregation)||((Number)maximum).doubleValue()>0,422,"计分题须配置有效满分");
  require(!"sum".equals(aggregation)||"multiple".equals(q.get("type")),422,"累加计分须使用多选题");
  return map("mode","management","version",str(r.get("version")),"dimension",str(r.get("dimension")),"dimensionLabel",str(r.get("dimensionLabel")),"aggregation",aggregation,"maxScore",maximum,"higherIsBetter",r.get("higherIsBetter"),"note",str(r.get("note")));
 }
 static List<?> chosen(Object raw){return raw instanceof List?(List<?>)raw:raw==null?Collections.emptyList():Collections.singletonList(raw);}
 static Map<String,Object> item(Map<String,Object> q,Object raw){
  Map<String,Object> r=rule(q);List<?> selected=chosen(raw);List<String> labels=new ArrayList<>();
  double score=0;int found=0;boolean excluded=false,exclusive=false;
  for(Map<String,Object> o:QuestionnaireScoring.rows(q.get("options")))if(selected.contains(o.get("id"))){found++;labels.add(str(o.get("label")));score+=((Number)o.get("score")).doubleValue();excluded|=Boolean.TRUE.equals(o.get("excluded"));exclusive|=Boolean.TRUE.equals(o.get("exclusive"));}
  if(!"text".equals(q.get("type"))){require(found==selected.size()&&new HashSet<>(selected).size()==selected.size(),422,"管理题目选项无效或重复");require(!exclusive||selected.size()==1,422,"“无困难、条件不足、不适用、不愿回答”等互斥选项不可与其他选项同时选择");require(!excluded||selected.size()==1||"none".equals(r.get("aggregation")),422,"不计分选项不可混选");}
  boolean scored=!"none".equals(r.get("aggregation")),answered=raw!=null&&!selected.isEmpty()&&!(raw instanceof String&&str(raw).trim().isEmpty()),valid=scored&&answered&&!excluded;
  if("text".equals(q.get("type"))&&raw!=null)labels=Collections.singletonList(str(raw));
  double maximum=((Number)r.get("maxScore")).doubleValue();
  return map("id",q.get("id"),"text",q.get("title"),"answer",labels,"score",valid?Math.min(score,maximum):null,"maxScore",maximum,"eligible",scored,"answered",answered,"excluded",answered&&excluded,"management",true,"dimension",r.get("dimension"),"exclusionReason",!scored?"本题仅记录偏好、意愿或文字，不计分":!answered?"未作答":excluded?"不适用、不愿回答或无法判断，排除评分分母":"");
 }
 static List<Object> groups(String name,Object version,Object source,List<Object> questions){
  Map<String,List<Object>> byDimension=new LinkedHashMap<>();Map<String,Map<String,Object>> rules=new HashMap<>();
  for(Object value:questions){@SuppressWarnings("unchecked") Map<String,Object> q=(Map<String,Object>)value;Map<String,Object> r=validateRule(q);require(!r.isEmpty(),422,"管理调查缺少逐题评分规则");String dimension=str(r.get("dimension"));byDimension.computeIfAbsent(dimension,k->new ArrayList<>()).add(q);rules.put(dimension,r);}
  List<Object> out=new ArrayList<>();for(Map.Entry<String,List<Object>> group:byDimension.entrySet()){Map<String,Object> r=rules.get(group.getKey());out.add(map("name",name+" · "+str(r.get("dimensionLabel")),"parentScale",name,"version",version,"source",source,"method","management","dimension",group.getKey(),"dimensionLabel",r.get("dimensionLabel"),"higherIsBetter",r.get("higherIsBetter"),"questions",group.getValue(),"bands",Collections.emptyList()));}return out;
 }
 static Map<String,Object> scoreScale(Map<String,Object> scale,Map<String,Object> answers,List<Object> items){
  double sum=0,maximum=0;int eligible=0,valid=0,answered=0,excluded=0;
  for(Map<String,Object> q:QuestionnaireScoring.rows(scale.get("questions"))){Map<String,Object> item=item(q,answers.get(str(q.get("id"))));item.put("scale",scale.get("name"));items.add(item);if(Boolean.TRUE.equals(item.get("eligible"))){eligible++;if(Boolean.TRUE.equals(item.get("answered")))answered++;if(Boolean.TRUE.equals(item.get("excluded")))excluded++;if(item.get("score") instanceof Number){valid++;sum+=((Number)item.get("score")).doubleValue();maximum+=((Number)item.get("maxScore")).doubleValue();}}}
  Double score=maximum>0?Math.round(sum/maximum*1000d)/10d:null;double coverage=eligible>0?(double)valid/eligible:0;String severity;
  boolean sufficient=coverage>=0.5,positive=Boolean.TRUE.equals(scale.get("higherIsBetter"));
  if(eligible==0)severity="不计分，仅记录偏好、意愿或文字建议";
  else if(score==null)severity="无有效计分答案，不判定为0分或正常";
  else if(!sufficient)severity="有效计分题不足50%，仅展示分数，不分档";
  else {int band=score<=25?0:score<=50?1:score<=75?2:3;severity=(positive?new String[]{"待重点完善","建议加强","基本具备","较充分"}:new String[]{"较少","一般","较多","较集中"})[band]+"（项目管理参考，非临床分级）";}
  return map("name",scale.get("name"),"parentScale",scale.get("parentScale"),"version",scale.get("version"),"source",scale.get("source"),"score",score,"maxScore",eligible>0?100:0,"rawScore",sum,"validMaxScore",maximum,"risk","unknown","severity",severity,"complete",answered==eligible,"management",true,"dimension",scale.get("dimension"),"higherIsBetter",positive,"eligibleCount",eligible,"validCount",valid,"excludedCount",excluded,"coverage",Math.round(coverage*1000d)/10d,"sufficient",sufficient,"employeeScoreHidden",false);
 }
}
