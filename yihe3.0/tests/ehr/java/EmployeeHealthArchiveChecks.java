package com.yihe.framework.ehr;
import java.util.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;
public class EmployeeHealthArchiveChecks {
 static void check(String name,boolean ok){if(!ok)throw new AssertionError(name);System.out.println("PASS "+name);}
 static void rejects(String name,int status,Runnable fn){try{fn.run();throw new AssertionError(name);}catch(EhrException e){check(name,e.status==status);}}
 @SuppressWarnings("unchecked") public static void main(String[] args){
 if(!System.getenv("EHR_JDBC_URL").contains("/shayu_3_preview?"))throw new IllegalArgumentException("preview only");
 DriverManagerDataSource source=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));EhrRepository db=new EhrRepository(source);EhrService ehr=new EhrService(db);EmployeeHealthArchiveService service=new EmployeeHealthArchiveService(db,ehr);String org="EMP_"+id().substring(0,8);EhrActor actor=new EhrActor("archive-read",org,"synthetic",true,false),other=new EhrActor("other",org+"X","synthetic",true,false),mode=new EhrActor("other-mode",org,"real",true,false),mobile=new EhrActor("mobile",org,"synthetic",false,true);
 new TransactionTemplate(new DataSourceTransactionManager(source)).execute(tx->{try{
 OcrRosterIdentityChecks.Fixture f=OcrRosterIdentityChecks.fixture(db,ehr,actor,"1980-12-31","backend_file");String before=ehr.encode(ehr.snapshot(f.report,1)),profile=ehr.encode(ehr.profile(actor,f.record,false));
 check("scoped_employee_list",num(service.list(actor,new HashMap<>()).get("total"))==1);
 check("other_organization_empty",num(service.list(other,new HashMap<>()).get("total"))==0);
 check("other_data_mode_empty",num(service.list(mode,new HashMap<>()).get("total"))==0);
 rejects("cross_scope_detail_denied",404,()->service.detail(other,f.record));rejects("cross_mode_detail_denied",404,()->service.detail(mode,f.record));rejects("mobile_list_denied",403,()->service.list(mobile,new HashMap<>()));rejects("mobile_detail_denied",403,()->service.detail(mobile,f.record));rejects("mobile_overview_denied",403,()->service.overview(mobile));
 Map<String,Object> summary=service.overview(actor),d=service.detail(actor,f.record);
 check("overview_counts_real_profile",num(summary.get("total"))==1&&num(summary.get("withReport"))==1);
 check("no_invented_high_risk",num(summary.get("high"))==0&&num(summary.get("critical"))==0);
 check("no_invented_followup",num(summary.get("pendingFollowups"))==0&&((List<?>)d.get("followups")).isEmpty());
 check("no_invented_assessments",((List<?>)d.get("assessments")).isEmpty());check("no_invented_services",((List<?>)d.get("serviceTasks")).isEmpty());
 check("actual_report_data_exposed",((List<?>)d.get("reports")).size()==1);check("candidate_not_published",((List<?>)d.get("publishedReports")).isEmpty());
 check("search_literal_injection_not_executed",num(service.list(actor,Collections.singletonMap("search","%' OR 1=1 --")).get("total"))==0);
 check("reads_preserve_report_snapshot",before.equals(ehr.encode(ehr.snapshot(f.report,1))));check("reads_preserve_roster",profile.equals(ehr.encode(ehr.profile(actor,f.record,false))));
 }finally{tx.setRollbackOnly();}return null;});
 }
}
