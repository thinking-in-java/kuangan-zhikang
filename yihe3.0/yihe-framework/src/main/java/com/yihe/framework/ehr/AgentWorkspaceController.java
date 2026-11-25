package com.yihe.framework.ehr;
import java.util.*;import javax.servlet.http.*;import org.springframework.web.bind.annotation.*;import static com.yihe.framework.ehr.EhrController.ok;
@RestController @RequestMapping("/ehr/api/agent-workspaces")public class AgentWorkspaceController {
 final EhrAccess access;final AgentWorkspaceService service;public AgentWorkspaceController(EhrAccess access,AgentWorkspaceService service){this.access=access;this.service=service;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 @GetMapping public Object workspace(HttpServletRequest r,@RequestParam Map<String,String>q){return ok(service.workspace(access.web(r),q));}
 @GetMapping("/task")public Object detail(HttpServletRequest r,@RequestParam("source") String source,@RequestParam("id") String id){return ok(service.detail(access.web(r),source,id));}
}
