package com.yihe.framework.ehr;
import java.util.Map;import javax.servlet.http.*;import org.springframework.web.bind.annotation.*;import static com.yihe.framework.ehr.EhrController.ok;
@RestController @RequestMapping("/ehr/api/agents") public class AgentOverviewController {
 final EhrAccess access;final AgentOverviewService service;public AgentOverviewController(EhrAccess access,AgentOverviewService service){this.access=access;this.service=service;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 @GetMapping("/overview")public Object overview(HttpServletRequest r){return ok(service.overview(access.web(r)));}
 @GetMapping("/tasks")public Object tasks(HttpServletRequest r,@RequestParam Map<String,String>q){return ok(service.list(access.web(r),q));}
}
