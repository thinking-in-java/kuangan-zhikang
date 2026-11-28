package com.yihe.framework.ehr;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.yihe.framework.ehr.EhrAccess.*;
/** Offline scoring and freeze regression checks; never publishes or inserts employee responses. */
@SuppressWarnings("unchecked") public class QuestionnaireManagementChecks {
 static int checks;static void check(String message,boolean ok){if(!ok)throw new AssertionError(message);checks++;}
 static void rejected(Runnable operation){boolean rejected=false;try{operation.run();}catch(EhrException e){rejected=true;}check("invalid combination rejected",rejected);}
 static Map<String,Object> find(List<Map<String,Object>> rows,String id){return rows.stream().filter(q->id.equals(q.get("id"))).findFirst().get();}
 public static void main(String[] args)throws Exception{
  ObjectMapper json=new ObjectMapper();Map<String,Object> pack=json.readValue(QuestionnaireManagementChecks.class.getResourceAsStream("/questionnaire-supplement.json"),Map.class);
  EhrRepository db=new EhrRepository(new org.springframework.jdbc.datasource.DriverManagerDataSource()){@Override public List<Map<String,Object>> all(String sql,Object...args){if(!sql.startsWith("SELECT h.rule_id"))throw new AssertionError(sql);return Collections.emptyList();}};
  EhrService ehr=new EhrService(db);QuestionnaireWorkflow workflow=new QuestionnaireWorkflow(db,ehr,new QuestionnaireService(db,ehr));
  List<Map<String,Object>> bank=QuestionnaireScoring.rows(pack.get("questions"));int rated=0,unrated=0;
  for(Map<String,Object> source:bank){if(!String.valueOf(source.get("id")).startsWith("KB27-"))continue;Map<String,Object> q=workflow.canonicalQuestion(source,String.valueOf(source.get("id")));Map<String,Object> rule=QuestionnaireManagementScoring.rule(q);check("rule survives freeze",!rule.isEmpty());
   if("none".equals(rule.get("aggregation"))){unrated++;check("preference unscored",QuestionnaireManagementScoring.item(q,"text".equals(q.get("type"))?"建议":"o0").get("score")==null);}
   else{rated++;for(Map<String,Object> option:QuestionnaireScoring.rows(q.get("options"))){Map<String,Object> item=QuestionnaireManagementScoring.item(q,option.get("id"));check("excluded values absent",Boolean.TRUE.equals(option.get("excluded"))?item.get("score")==null:item.get("score") instanceof Number);}}
  }
  check("1620 scored and 540 descriptive",rated==1620&&unrated==540);
  Map<String,Object> behavior=workflow.canonicalQuestion(find(bank,"KB27-chemical-01-01"),"b"),another=workflow.canonicalQuestion(find(bank,"KB27-chemical-02-01"),"b2"),third=workflow.canonicalQuestion(find(bank,"KB27-chemical-03-01"),"b3");
  check("always 3",QuestionnaireManagementScoring.item(behavior,"o0").get("score").equals(3d));check("never 0",QuestionnaireManagementScoring.item(behavior,"o3").get("score").equals(0d));check("NA excluded",QuestionnaireManagementScoring.item(behavior,"o4").get("score")==null);
  Map<String,Object> resource=workflow.canonicalQuestion(find(bank,"KB27-chemical-01-05"),"r"),barriers=workflow.canonicalQuestion(find(bank,"KB27-chemical-01-06"),"d"),preference=workflow.canonicalQuestion(find(bank,"KB27-chemical-01-10"),"p");
  check("resources sums four",QuestionnaireManagementScoring.item(resource,Arrays.asList("o0","o1","o2","o3")).get("score").equals(4d));
  check("barriers two",QuestionnaireManagementScoring.item(barriers,Arrays.asList("o0","o1")).get("score").equals(2d));
  rejected(()->QuestionnaireManagementScoring.item(resource,Arrays.asList("o0","o4")));rejected(()->QuestionnaireManagementScoring.item(barriers,Arrays.asList("o0","o5")));rejected(()->QuestionnaireManagementScoring.item(barriers,Arrays.asList("o0","o6")));rejected(()->QuestionnaireManagementScoring.item(barriers,Arrays.asList("o0","o0")));rejected(()->QuestionnaireManagementScoring.item(preference,Arrays.asList("o1","o5")));
  Map<String,Object> scale=(Map<String,Object>)QuestionnaireManagementScoring.groups("TEST","v","source",Arrays.asList((Object)behavior,another,third)).get(0);List<Object> items=new ArrayList<>();
  Map<String,Object> result=QuestionnaireManagementScoring.scoreScale(scale,map("b","o0","b2","o1","b3","o4"),items);
  check("normalize valid denominator",result.get("score").equals(83.3d));check("excluded denominator",result.get("validMaxScore").equals(6d));check("unknown clinical risk","unknown".equals(result.get("risk")));check("exclude count",result.get("excludedCount").equals(1));
  result=QuestionnaireManagementScoring.scoreScale(scale,map("b","o4","b2","o5","b3","o4"),new ArrayList<>());check("all excluded not zero",result.get("score")==null);
  result=QuestionnaireManagementScoring.scoreScale(scale,map("b","o0"),new ArrayList<>());check("low coverage no classification",!Boolean.TRUE.equals(result.get("sufficient"))&&result.get("severity").toString().contains("不分档"));
  EhrActor actor=new EhrActor("CHECK","CHECK","synthetic",true,false);Map<String,Object> frozen=workflow.build(actor,map("name","Offline","scales",Arrays.asList("MINE-SUPPORT-CHEMICAL")),pack,true);
  check("18 questions",QuestionnaireScoring.rows(frozen.get("questions")).size()==18);check("6 management dimensions including notes",QuestionnaireScoring.rows(frozen.get("scoringScales")).size()==6);
  Map<String,Object> scored=QuestionnaireScoring.score(frozen,Collections.emptyMap());check("no inferred clinical risk","unknown".equals(scored.get("risk")));for(Map<String,Object>s:QuestionnaireScoring.rows(scored.get("scales")))check("missing data null",s.get("score")==null);
  Map<String,Object> customGroup=map("id","custom","questions",Arrays.asList(find(bank,"KB27-chemical-01-05"),find(bank,"KB27-chemical-02-05")));
  Map<String,Object> customOnly=workflow.build(actor,map("name","Offline bank","wizard",map("groups",Arrays.asList(customGroup))),pack,true);
  check("bank only selection scored",QuestionnaireScoring.rows(customOnly.get("scoringScales")).size()==1);
  check("same dimension pooled",QuestionnaireScoring.rows(QuestionnaireScoring.rows(customOnly.get("scoringScales")).get(0).get("questions")).size()==2);
  Map<String,Object> standard=json.readValue(QuestionnaireManagementChecks.class.getResourceAsStream("/questionnaire-catalog.json"),Map.class);
  Map<String,Object> standardDoc=workflow.build(actor,map("name","Standard regression","scales",Arrays.asList("PHQ-9","GAD-7")),standard,true),standardAnswers=new HashMap<>();
  for(Map<String,Object> q:QuestionnaireScoring.rows(standardDoc.get("questions")))standardAnswers.put(q.get("id").toString(),"o3");
  Map<String,Object> standardResult=QuestionnaireScoring.score(standardDoc,standardAnswers);
  List<Map<String,Object>> standardScales=QuestionnaireScoring.rows(standardResult.get("scales"));
  check("PHQ9 max remains 27",standardScales.get(0).get("score").equals(27d));check("GAD7 max remains 21",standardScales.get(1).get("score").equals(21d));check("PHQ9 urgent signal retained",Boolean.TRUE.equals(standardResult.get("urgent")));
  System.out.println("PASS "+checks+" management scoring and freeze checks");
 }
}
