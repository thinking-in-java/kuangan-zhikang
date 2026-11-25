package com.yihe.framework.ehr;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrController.ok;
/** Read-only archive audit projection, restricted to the authenticated enterprise scope. */
@RestController @RequestMapping("/ehr/api/system-audit")
public class SystemAuditApi {
 private final EhrAccess access;private final EhrRepository db;
 public SystemAuditApi(EhrAccess access,EhrRepository db){this.access=access;this.db=db;}
 @GetMapping public Object list(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,String> q){
  EhrActor a=access.web(request);response.setHeader("Cache-Control","private, no-store");
  if(!a.doctor&&!org.apache.shiro.SecurityUtils.getSubject().isPermitted("upms:log:read"))throw new EhrException(403,"当前账号无审计日志查看权限");
  int page=number(q.get("page"),1,100000),limit=number(q.get("limit"),10,50);
  String group="CASE WHEN action LIKE '%binding%' OR action LIKE '%bound%' THEN 'identity' WHEN action LIKE '%rule%' OR action LIKE '%template%' OR action LIKE '%config%' THEN 'configuration' WHEN action LIKE '%ai%' OR action LIKE '%generated%' OR action LIKE '%ocr%' THEN 'ai' ELSE 'data' END";
  String where=" FROM a_health_archive_audit_logs WHERE scope_code=? AND data_mode=?";List<Object> args=new ArrayList<>(Arrays.asList(a.scope,a.mode));
  String search=q.getOrDefault("search","").trim();if(search.length()>100)throw new EhrException(422,"搜索内容最多100字");if(!search.isEmpty()){where+=" AND (audit_id LIKE ? OR action LIKE ? OR reason LIKE ? OR record_id LIKE ? OR CONVERT(actor_ref USING utf8mb4) LIKE ?)";for(int i=0;i<5;i++)args.add("%"+search.replace("!","!!").replace("%","!%").replace("_","!_")+"%");where=where.replace(" LIKE ?"," LIKE ? ESCAPE '!'");}
  String category=q.getOrDefault("category","");if(!category.isEmpty()){if(!Arrays.asList("identity","configuration","ai","data").contains(category))throw new EhrException(422,"分类无效");where+=" AND ("+group+")=?";args.add(category);}
  String tab=q.getOrDefault("tab","operations");if("login".equals(tab)){where+=" AND action LIKE '%login%'";}else if("ai".equals(tab)){where+=" AND (action LIKE '%ai%' OR action LIKE '%generated%' OR action LIKE '%ocr%')";}else if("changes".equals(tab)){where+=" AND (action LIKE '%saved%' OR action LIKE '%deleted%' OR action LIKE '%updated%')";}else if("data".equals(tab)){where+=" AND (action LIKE '%report%' OR action LIKE '%record%' OR action LIKE '%profile%' OR action LIKE '%export%')";}else if(!"operations".equals(tab))throw new EhrException(422,"日志类型无效");
  for(String dateKey:new String[]{"from","to"}){String v=q.getOrDefault(dateKey,"");if(!v.isEmpty()){try{java.time.LocalDate d=java.time.LocalDate.parse(v);where+=" AND occurred_at"+("from".equals(dateKey)?">=?":"<?");args.add(("from".equals(dateKey)?d:d.plusDays(1)).toString()+" 00:00:00");}catch(Exception e){throw new EhrException(422,"日期格式应为YYYY-MM-DD");}}}
  long total=db.count("SELECT COUNT(*)"+where,args.toArray());List<Object> paged=new ArrayList<>(args);paged.add(limit);paged.add((page-1)*limit);
  String direction="asc".equals(q.get("order"))?"ASC":"DESC";
  List<Map<String,Object>> rows=db.all("SELECT audit_id id,DATE_FORMAT(occurred_at,'%Y-%m-%dT%H:%i:%sZ') time,CONVERT(actor_ref USING utf8mb4) COLLATE utf8mb4_unicode_ci actor,CONVERT(action USING utf8mb4) COLLATE utf8mb4_unicode_ci action,CONVERT(reason USING utf8mb4) COLLATE utf8mb4_unicode_ci summary,record_id recordId,report_id reportId,"+group+" category"+where+" ORDER BY occurred_at "+direction+",audit_id "+direction+" LIMIT ? OFFSET ?",paged.toArray());
  return ok(map("items",rows,"total",total,"source","real","scope",a.scope,"notice","真实数据来自当前组织的健康档案审计表；该表不提供全平台登录日志、操作IP、客户端或原始请求参数。"));
 }
 private int number(String v,int fallback,int max){if(v==null)return fallback;try{int n=Integer.parseInt(v);if(n<1||n>max)throw new Exception();return n;}catch(Exception e){throw new EhrException(422,"分页参数无效");}}
}
