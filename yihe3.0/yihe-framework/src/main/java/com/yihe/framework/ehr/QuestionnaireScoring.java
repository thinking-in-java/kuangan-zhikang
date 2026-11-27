package com.yihe.framework.ehr;
import java.util.*;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;
/** Deterministic scoring of immutable questionnaire releases. Model output never changes scores. */
@SuppressWarnings("unchecked") public final class QuestionnaireScoring {
 private QuestionnaireScoring(){}
 static List<Map<String,Object>> rows(Object value){List<Map<String,Object>> out=new ArrayList<>();if(value instanceof List)for(Object o:(List<?>)value){require(o instanceof Map,422,"配置列表格式无效");out.add((Map<String,Object>)o);}return out;}
 static int rank(String l){return Arrays.asList("unknown","low","mild","medium","high","critical").indexOf(l);}
 public static Map<String,Object> score(Map<String,Object> doc,Map<String,Object> answers){
  List<Object> results=new ArrayList<>(),items=new ArrayList<>();boolean urgent=false;String overall="unknown";
  for(Map<String,Object> scale:rows(doc.get("scoringScales"))){double sum=0,max=0;int count=0;boolean complete=true;
   for(Map<String,Object> q:rows(scale.get("questions"))){Object raw=answers.get(str(q.get("id")));double value=0,highest=0;List<?> chosen=raw instanceof List?(List<?>)raw:raw==null?Collections.emptyList():Collections.singletonList(raw);List<String> labels=new ArrayList<>();for(Map<String,Object> o:rows(q.get("options"))){double v=Double.parseDouble(str(o.get("score")));highest=Math.max(highest,v);if(chosen.contains(o.get("id"))){value+=v;labels.add(str(o.get("label")));}}
    if(raw==null)complete=false;sum+=value;max+=highest;count++;items.add(map("id",q.get("id"),"text",q.get("title"),"answer",labels,"score",value,"scale",scale.get("name")));
    if("PHQ-9".equals(scale.get("name"))&&str(q.get("id")).endsWith("09")&&value>0)urgent=true;
   }
   if("average".equals(scale.get("method"))&&count>0){sum/=count;max/=count;}
   String level="unknown",severity="未配置分级，需专业解释";
   if(complete)for(Map<String,Object> band:rows(scale.get("bands"))){double lo=Double.parseDouble(str(band.get("min"))),hi=Double.parseDouble(str(band.get("max")));if(sum>=lo&&sum<=hi){level=str(band.get("level"));severity=str(band.get("note"));break;}}
   if(rank(level)>rank(overall))overall=level;
   results.add(map("name",scale.get("name"),"version",scale.get("version"),"source",scale.get("source"),"score",sum,"maxScore",max,"risk",level,"severity",severity,"complete",complete));
  }
  return map("scales",results,"items",items,"risk",overall,"urgent",urgent,"basis","冻结发布版本逐量表计分；不将不同量表分数相加判定疾病；医生审核独立记录");
 }
}
