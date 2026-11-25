package com.yihe.framework.ehr;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
public class ReportCenterChecks {
    static int checks;
    static void check(boolean value,String label){if(!value)throw new AssertionError(label);checks++;System.out.println("PASS "+label);}
    @SuppressWarnings("unchecked") static Map<String,Object> list(ReportCenterController c,Map<String,String> q){return (Map<String,Object>)((Map<String,Object>)c.list(null,q)).get("data");}
    static class Access extends EhrAccess {
        EhrActor actor=new EhrActor("report-center-test","10000","real",true,false);
        Access(EhrRepository db){super(db,null);}
        @Override public EhrActor web(HttpServletRequest request){return actor;}
    }
    @SuppressWarnings("unchecked") public static void main(String[] args){
        DriverManagerDataSource source=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));
        EhrRepository db=new EhrRepository(source);Access auth=new Access(db);ReportCenterController c=new ReportCenterController(auth,db);
        Map<String,Object> all=list(c,new HashMap<>());long total=((Number)all.get("total")).longValue();List<Map<String,Object>> items=(List<Map<String,Object>>)all.get("items");
        check(total>0&&items.size()==10,"current_scoped_reports_paginated");
        Map<String,Object> counts=(Map<String,Object>)all.get("counts");check(((Number)counts.get("all")).longValue()==total,"status_counts_agree_with_total");
        long sum=0;for(Map.Entry<String,Object> entry:counts.entrySet())if(!entry.getKey().equals("all"))sum+=((Number)entry.getValue()).longValue();check(sum==total,"status_facets_are_exclusive");
        Map<String,String> q=new HashMap<>();q.put("page","2");List<Map<String,Object>> second=(List<Map<String,Object>>)list(c,q).get("items");check(!items.get(0).get("id").equals(second.get(0).get("id")),"stable_second_page");
        q.clear();q.put("search",String.valueOf(items.get(0).get("employeeCode")));Map<String,Object> searched=list(c,q);check(((Number)searched.get("total")).longValue()>0,"employee_code_search");
        q.put("search","%' OR 1=1 --");check(((Number)list(c,q).get("total")).longValue()==0,"search_bound_and_wildcards_escaped");
        for(String status:new String[]{"queued","running","recognized","pending_match","pending_check","pending_review","archived","failed"}){q.clear();q.put("status",status);Map<String,Object> result=list(c,q);check(result.get("total")!=null,"status_filter_"+status);}
        q.clear();q.put("channel","not_existing");check(((Number)list(c,q).get("total")).longValue()==0,"channel_filter");
        q.clear();q.put("organization","not_existing");check(((Number)list(c,q).get("total")).longValue()==0,"organization_filter");
        q.clear();q.put("from","2099-01-01");check(((Number)list(c,q).get("total")).longValue()==0,"exam_date_filter");
        q.clear();q.put("status","bad");try{list(c,q);throw new AssertionError();}catch(EhrException expected){check(true,"invalid_status_rejected");}
        q.clear();q.put("from","not-a-date");try{list(c,q);throw new AssertionError();}catch(EhrException expected){check(true,"invalid_date_rejected");}
        auth.actor=new EhrActor("report-center-test","UNAUTHORIZED_TEST_ORG","real",true,false);check(((Number)list(c,new HashMap<>()).get("total")).longValue()==0,"cross_organization_isolation");
        auth.actor=new EhrActor("report-center-test","10000","real",false,true);try{list(c,new HashMap<>());throw new AssertionError();}catch(EhrException expected){check(true,"mobile_access_denied");}
        System.out.println("PASS report center checks: "+checks+"; read-only, no business writes");
    }
}
