package com.yihe.framework.ehr;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrController.ok;

@RestController
@RequestMapping("/ehr/api/agent")
public class HealthAgentController {
    final EhrAccess auth;final HealthAgentService agent;final HealthAgentStore store;final HealthAgentSettings settings;final EhrService ehr;
    public HealthAgentController(EhrAccess auth,HealthAgentService agent,HealthAgentStore store,HealthAgentSettings settings,EhrService ehr){this.auth=auth;this.agent=agent;this.store=store;this.settings=settings;this.ehr=ehr;}
    @GetMapping("/context") public Object context(HttpServletRequest r){EhrActor a=auth.web(r);Map<String,Object> c=settings.status();c.put("dataMode",a.mode);c.put("canModify",a.doctor);c.put("statistics",ehr.statistics(a));return ok(c);}
    @GetMapping("/conversations") public Object list(HttpServletRequest r){return ok(store.list(auth.web(r)));}
    @PostMapping("/conversations") public Object create(HttpServletRequest r){return ok(store.create(auth.web(r)));}
    @GetMapping("/conversations/{id}") public Object detail(HttpServletRequest r,@PathVariable String id){return ok(store.detail(auth.web(r),id));}
    @DeleteMapping("/conversations/{id}") public Object delete(HttpServletRequest r,@PathVariable String id){return ok(store.delete(auth.web(r),id));}
    @PostMapping("/chat") public Object chat(HttpServletRequest r,@RequestBody Map<String,Object> body){return ok(agent.chat(auth.web(r),body));}
    @PostMapping("/actions/{id}/resolve") public Object resolve(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object> body){return ok(store.resolve(auth.web(r),id,body));}
}
