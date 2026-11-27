package com.yihe.framework.ehr;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import static com.yihe.framework.ehr.EhrAccess.*;

/** Pure scheduling: creates future preparation records, never contact receipts or medical findings. */
final class FollowupRuleEngine {
    private static String s(Map<?,?> m,String k){Object v=m.get(k);return v==null?"":String.valueOf(v);}
    @SuppressWarnings("unchecked") static int apply(Map<String,Object> state,List<Map<String,Object>> people,LocalDate today,Set<String> validOwners){
        List<Map<String,Object>> settings=(List<Map<String,Object>>)(List<?>)state.get("settings"),schedules=(List<Map<String,Object>>)(List<?>)state.get("schedules"),plans=(List<Map<String,Object>>)(List<?>)state.get("plans");
        int created=0;Set<String> existing=new HashSet<>();for(Map<String,Object> row:schedules)if(!s(row,"ruleId").isEmpty())existing.add(s(row,"ruleId")+"|"+s(row,"recordId")+"|"+s(row,"due"));
        for(Map<String,Object> rule:settings){if(!"followup-rule".equals(s(rule,"type"))||!Boolean.TRUE.equals(rule.get("enabled")))continue;
            int n=0;rule.put("lastRunAt",now());rule.put("lastError","");
            if(!validOwners.contains(s(rule,"_owner"))||!validOwners.contains("doctor:"+s(rule,"ownerId"))){rule.put("lastError","规则经办人或执行人已停用、变更单位或无权使用后台");continue;}
            try{LocalDate start=LocalDate.parse(s(rule,"startDate")),end=today.plusDays(7);int step=EhrService.num(rule.get("intervalDays"));if(step<1)continue;long days=ChronoUnit.DAYS.between(start,today);LocalDate due=start;if(days>0){if("once".equals(s(rule,"cadence")))continue;due=start.plusDays(((days+step-1)/step)*step);}
                for(;!due.isAfter(end);due=due.plusDays(step)){
                    for(Map<String,Object> person:people){if(schedules.size()>=3000||created>=500){rule.put("lastError","本轮达到计划容量限制，请归档或分批处理");break;}
                        if(!matches(rule,person,plans))continue;String key=s(rule,"id")+"|"+s(person,"id")+"|"+due;if(!existing.add(key))continue;
                        String problem="综合管理",planId="";for(Map<String,Object> plan:plans)if(!s(plan,"id").startsWith("sample-")&&s(person,"id").equals(s(plan,"recordId"))){planId=s(plan,"id");if(!s(plan,"problem").isEmpty())problem=s(plan,"problem");break;}
                        Map<String,Object> row=map("id",UUID.randomUUID().toString(),"recordId",person.get("id"),"name",person.get("name"),"code",person.get("code"),"org",person.get("org"),"risk",person.getOrDefault("risk","unknown"),"planId",planId,"ruleId",rule.get("id"),"title",rule.get("title"),"problem",problem,"type",rule.get("followupType"),"content",rule.get("content"),"due",due.toString(),"deadline",due.toString(),"channels",Collections.singletonList(s(rule,"channel")),"channel",rule.get("channel"),"remindDays",3,"ownerId",rule.get("ownerId"),"owner",rule.get("owner"),"priority","normal","status","pending","source","自动创建规则","structuredVersion",2,"createdAt",now(),"updatedAt",now(),"logs",new ArrayList<>(Collections.singletonList(map("time",now(),"actor",rule.get("_owner"),"action","规则自动创建计划","note","只创建计划，尚未联系职工或发送消息"))));
                        schedules.add(row);n++;created++;
                    }
                    if("once".equals(s(rule,"cadence"))||schedules.size()>=3000||created>=500)break;
                }
            }catch(RuntimeException ex){rule.put("lastError","规则配置无效，请编辑后保存");}
            rule.put("lastCreated",n);
        }
        return created;
    }
    @SuppressWarnings("unchecked") static boolean matches(Map<String,Object> rule,Map<String,Object> person,List<Map<String,Object>> plans){
        String scope=s(rule,"scopeKind");if("people".equals(scope)&&!((List<?>)rule.getOrDefault("recordIds",Collections.emptyList())).contains(person.get("id")))return false;if("org".equals(scope)&&!s(rule,"org").equals(s(person,"org")))return false;
        Object raw=rule.get("conditions");if(!(raw instanceof List)||((List<?>)raw).isEmpty())return false;List<Boolean> tests=new ArrayList<>();
        for(Object value:(List<?>)raw){if(!(value instanceof Map))return false;Map<?,?> c=(Map<?,?>)value;String field=s(c,"field"),expected=s(c,"value");if(expected.trim().isEmpty())return false;boolean match=false;
            if("problem".equals(field)){for(Map<String,Object> plan:plans)if(!s(plan,"id").startsWith("sample-")&&s(person,"id").equals(s(plan,"recordId"))&&(s(plan,"problem").contains(expected)||String.valueOf(plan.getOrDefault("problems","")).contains(expected))){match=true;break;}}
            else if(Arrays.asList("risk","team","job").contains(field))match=expected.equals(s(person,field));tests.add(match);
        }
        return "any".equals(s(rule,"relation"))?tests.contains(true):!tests.contains(false);
    }
}
