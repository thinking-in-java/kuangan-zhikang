package com.yihe.framework.ehr;

import java.time.LocalDate;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrController.ok;

/** Read-only workbench projection, using the same doctor membership as legacy alerts. */
@RestController
@RequestMapping("/ehr/api/workbench")
public class WorkbenchQueryController {
    private final EhrAccess access;
    private final EhrRepository db;
    public WorkbenchQueryController(EhrAccess access, EhrRepository db) { this.access=access; this.db=db; }

    private static String value(Map<String,String> query, String key) {
        String value=query.getOrDefault(key, "").trim();
        if(value.length()>100) throw new EhrException(422,"筛选条件过长");
        return value;
    }
    private static int number(String value, int fallback, int max) {
        if(value.isEmpty()) return fallback;
        try { int n=Integer.parseInt(value); if(n>0&&n<=max) return n; } catch(NumberFormatException ignored) { }
        throw new EhrException(422,"分页参数无效");
    }
    private static LocalDate date(String value) {
        if(!value.matches("\\d{4}-\\d{2}-\\d{2}")) throw new EhrException(422,"日期格式无效");
        try { return LocalDate.parse(value); } catch(RuntimeException error) { throw new EhrException(422,"日期无效"); }
    }

    @GetMapping("/alerts")
    @Transactional(readOnly=true)
    public Object alerts(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String,String> query) {
        response.setHeader("Cache-Control","private, no-store");
        EhrActor actor=access.web(request);
        if(actor.mobile) throw new EhrException(403,"工作台仅供后台管理使用");
        int page=number(value(query,"page"),1,100000), limit=number(value(query,"limit"),10,100);
        String search=value(query,"search"),type=value(query,"type"),status=value(query,"status"),owner=value(query,"owner"),from=value(query,"from"),to=value(query,"to");
        if(!status.isEmpty()&&!Arrays.asList("0","1","2").contains(status)) throw new EhrException(422,"处置状态无效");
        if(!from.isEmpty()) date(from);
        if(!to.isEmpty()) date(to);
        if(!from.isEmpty()&&!to.isEmpty()&&date(from).isAfter(date(to))) throw new EhrException(422,"结束日期不能早于开始日期");
        Map<String,Object> summary=map("total",0L,"high",null,"medium",null,"low",null,"completed",0L);
        if(!actor.doctor||!"real".equals(actor.mode)) return ok(map("items",Collections.emptyList(),"total",0L,"summary",summary,"types",Collections.emptyList(),"owners",Collections.emptyList(),"page",page,"limit",limit));
        String sql=" FROM yh_he_threshold_alarm a JOIN yh_user_reginfo c ON c.user_code=a.MEMBER_ID JOIN yh_he_threshold_type d ON d.id=a.RECORD_ID LEFT JOIN yh_user_reginfo u ON u.user_code=a.ACCEPT_OPERATOR_ID WHERE EXISTS (SELECT 1 FROM yh_user_make_card mc JOIN yh_user_admin_info ai ON ai.id=mc.doctor WHERE mc.user_code=c.user_code AND ai.user_code=?)";
        List<Object> args=new ArrayList<>();args.add(actor.user);
        summary.put("total",db.count("SELECT COUNT(*)"+sql,args.toArray()));
        summary.put("completed",db.count("SELECT COUNT(*)"+sql+" AND a.STATUS='2'",args.toArray()));
        List<String> types=new ArrayList<>(),owners=new ArrayList<>();
        for(Map<String,Object> row:db.all("SELECT DISTINCT d.name label"+sql+" ORDER BY d.name",args.toArray())) if(row.get("label")!=null)types.add(String.valueOf(row.get("label")));
        for(Map<String,Object> row:db.all("SELECT DISTINCT COALESCE(u.name,u.user_name,a.ACCEPT_OPERATOR_ID) label"+sql+" AND a.ACCEPT_OPERATOR_ID IS NOT NULL AND a.ACCEPT_OPERATOR_ID<>''",args.toArray())) if(row.get("label")!=null)owners.add(String.valueOf(row.get("label")));
        if(!search.isEmpty()) {sql+=" AND (c.name LIKE ? ESCAPE '!' OR c.user_code LIKE ? ESCAPE '!')";String term="%"+EhrService.like(search)+"%";args.add(term);args.add(term);}
        if(!type.isEmpty()) {sql+=" AND d.name=?";args.add(type);}
        if(!status.isEmpty()) {sql+=" AND a.STATUS=?";args.add(status);}
        if(!owner.isEmpty()) {sql+=" AND COALESCE(u.name,u.user_name,a.ACCEPT_OPERATOR_ID)=?";args.add(owner);}
        if(!from.isEmpty()) {sql+=" AND a.TIME>=?";args.add(from+" 00:00:00");}
        if(!to.isEmpty()) {sql+=" AND a.TIME<?";args.add(date(to).plusDays(1).toString()+" 00:00:00");}
        // Legacy alerts have no confirmed risk grade; never infer one from patient values.
        if(!value(query,"risk").isEmpty()) sql+=" AND 1=0";
        long total=db.count("SELECT COUNT(*)"+sql,args.toArray());
        args.add(limit);args.add((page-1)*limit);
        List<Map<String,Object>> items=db.all("SELECT CAST(a.ID AS CHAR) id,a.TIME time,c.name memberName,c.user_code memberCode,d.name type,a.CONTENT content,a.STATUS status,COALESCE(u.name,u.user_name,a.ACCEPT_OPERATOR_ID,'') owner,NULL risk"+sql+" ORDER BY a.TIME DESC,a.ID DESC LIMIT ? OFFSET ?",args.toArray());
        return ok(map("items",items,"total",total,"summary",summary,"types",types,"owners",owners,"page",page,"limit",limit));
    }
}
