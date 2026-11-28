package com.yihe.framework.ehr;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.yihe.framework.ehr.EhrAccess.*;

/** Offline checks: no database, employee answers, model calls or notifications. */
@SuppressWarnings("unchecked") public class QuestionnaireSupplementChecks {
 static int passed;
 static void check(String name,boolean value){if(!value)throw new AssertionError(name);passed++;System.out.println("PASS "+name);}
 public static void main(String[] args)throws Exception{
  ObjectMapper mapper=new ObjectMapper();
  Map<String,Object> pack=mapper.readValue(QuestionnaireSupplementChecks.class.getResourceAsStream("/questionnaire-supplement.json"),Map.class);
  List<Map<String,Object>> qs=QuestionnaireScoring.rows(pack.get("questions")),ss=QuestionnaireScoring.rows(pack.get("scales"));
  check("2239 additions",qs.size()==2239);check("18 new scales and surveys",ss.size()==18);
  Set<String> ids=new HashSet<>(),texts=new HashSet<>();int original=0;
  for(Map<String,Object> q:qs){check("unique id "+q.get("id"),ids.add(String.valueOf(q.get("id"))));
   if(!Boolean.TRUE.equals(q.get("standard"))){original++;check("original stem unique",texts.add(String.valueOf(q.get("text"))));check("source recorded",String.valueOf(q.get("source")).startsWith("https://"));check("declared nonclinical scoring",QuestionnaireManagementScoring.managed(q)&&Boolean.FALSE.equals(q.get("clinicalUse")));for(Map<String,Object> o:QuestionnaireScoring.rows(q.get("optionItems")))check("valid management score",((Number)o.get("score")).doubleValue()>=0&&((Number)o.get("score")).doubleValue()<=5);}
  }
  check("2160 original survey items",original==2160);
  for(Map<String,Object> s:ss){List<Map<String,Object>> items=new ArrayList<>();for(Map<String,Object> q:qs)if(s.get("name").equals(q.get("scale")))items.add(q);check("count matches "+s.get("name"),items.size()==((Number)s.get("count")).intValue());
   if(!s.get("name").toString().startsWith("DASS"))continue;
   Map<String,Object> answers=new LinkedHashMap<>();List<Map<String,Object>> canonical=new ArrayList<>();for(Map<String,Object> q:items){List<Object> choices=new ArrayList<>();int i=0;for(Map<String,Object> o:QuestionnaireScoring.rows(q.get("optionItems")))choices.add(map("id","o"+i++,"label",o.get("label"),"score",o.get("score")));canonical.add(map("id",q.get("id"),"title",q.get("text"),"options",choices));answers.put(q.get("id").toString(),"o3");}
   List<Object> scales=new ArrayList<>();Set<Integer> covered=new HashSet<>();for(Map<String,Object> sub:QuestionnaireScoring.rows(s.get("subscales"))){List<Object> selected=new ArrayList<>();for(Object n:(List<?>)sub.get("itemOrders")){int index=((Number)n).intValue();check("subscale partition",covered.add(index));selected.add(canonical.get(index-1));}scales.add(map("name",sub.get("name"),"multiplier",sub.get("multiplier"),"questions",selected,"bands",Collections.emptyList(),"employeeScoreHidden",true));}
   check("all items partitioned",covered.size()==items.size());Map<String,Object> result=QuestionnaireScoring.score(map("scoringScales",scales),answers);
   check("three separate dimensions",QuestionnaireScoring.rows(result.get("scales")).size()==3);
   check("no invented DASS risk","unknown".equals(result.get("risk")));
   for(Map<String,Object> x:QuestionnaireScoring.rows(result.get("scales"))){check("each dimension max 42",((Number)x.get("score")).doubleValue()==42);check("respondent score withheld",Boolean.TRUE.equals(x.get("employeeScoreHidden")));}
   answers.clear();result=QuestionnaireScoring.score(map("scoringScales",scales),answers);for(Map<String,Object>x:QuestionnaireScoring.rows(result.get("scales")))check("missing answers not complete",!Boolean.TRUE.equals(x.get("complete")));
  }
  Map<String,Object> phq=new ObjectMapper().readValue(QuestionnaireSupplementChecks.class.getResourceAsStream("/questionnaire-catalog.json"),Map.class);
  check("existing PHQ9 retained",QuestionnaireScoring.rows(phq.get("questions")).stream().filter(q->"PHQ-9".equals(q.get("scale"))).count()==9);
  check("restricted instruments retained",QuestionnaireScoring.rows(phq.get("scales")).stream().filter(s->Boolean.TRUE.equals(s.get("restricted"))).count()==3);
  EhrRepository offline=new EhrRepository(new org.springframework.jdbc.datasource.DriverManagerDataSource()){
   @Override public List<Map<String,Object>> all(String sql,Object... values){if(!sql.startsWith("SELECT h.rule_id"))throw new AssertionError("Unexpected database read");return Collections.emptyList();}
  };
  EhrService ehr=new EhrService(offline);QuestionnaireWorkflow workflow=new QuestionnaireWorkflow(offline,ehr,new QuestionnaireService(offline,ehr));
  EhrActor actor=new EhrActor("CHECK","CHECK","synthetic",true,false);
  for(String name:Arrays.asList("DASS-21","DASS-42","PHQ-4")){
   Map<String,Object> plan=map("name","Offline draft","scales",Arrays.asList(name),"aiEnabled",true,"wizard",map("release",map("openAfter","immediate")));
   Map<String,Object> frozen=workflow.build(actor,plan,phq,true);
   check("actual freeze keeps source item count "+name,QuestionnaireScoring.rows(frozen.get("questions")).size()==(name.equals("DASS-21")?21:name.equals("DASS-42")?42:4));
   check("actual freeze preserves instructions",!String.valueOf(QuestionnaireScoring.rows(frozen.get("questions")).get(0).get("description")).isEmpty());
   check("actual freeze separates subscales",QuestionnaireScoring.rows(frozen.get("scoringScales")).size()==(name.equals("PHQ-4")?2:3));
   if(name.startsWith("DASS")){Map<String,Object> config=(Map<String,Object>)frozen.get("centerConfig");check("DASS disables automatic model interpretation",Boolean.FALSE.equals(config.get("aiEnabled")));Map<String,Object> wizard=(Map<String,Object>)config.get("wizard");check("DASS requires reviewed feedback","review".equals(((Map<?,?>)wizard.get("release")).get("openAfter")));}
  }
  System.out.println("TOTAL "+passed+" checks passed");
 }
}
