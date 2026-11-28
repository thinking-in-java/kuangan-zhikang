package com.yihe.framework.ehr;
import java.util.*;import javax.servlet.http.*;import org.springframework.web.bind.annotation.*;import static com.yihe.framework.ehr.EhrAccess.*;
@RestController @RequestMapping("/ehr/api/psychology-worker")
public class PsychologyWorkerController {
 final EhrAccess auth;final PsychologyCoordinator service;
 public PsychologyWorkerController(EhrAccess a,PsychologyCoordinator s){auth=a;service=s;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 Object ok(Object x){return map("code",0,"data",x);}
 @GetMapping public Object summary(HttpServletRequest r){return ok(service.overview(auth.web(r)));}
 @GetMapping("/people") public Object people(HttpServletRequest r,@RequestParam Map<String,String>q){return ok(service.people(auth.web(r),q));}
 @GetMapping("/scales") public Object scales(HttpServletRequest r){return ok(service.scales(auth.web(r)));}
 @GetMapping("/policies") public Object policies(HttpServletRequest r){return ok(service.policies(auth.web(r)));}
 @PostMapping("/policies") public Object policy(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(service.savePolicy(auth.web(r),b));}
 @PostMapping("/policies/{id}/run") public Object run(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return ok(service.runPolicy(auth.web(r),id,b));}
 @PostMapping("/scan") public Object scan(HttpServletRequest r){return ok(service.scan(auth.web(r)));}
 @GetMapping("/items") public Object items(HttpServletRequest r,@RequestParam Map<String,String>q){return ok(service.items(auth.web(r),q));}
 @GetMapping("/items/{id}") public Object item(HttpServletRequest r,@PathVariable String id){return ok(service.detail(auth.web(r),id));}
 @GetMapping("/records/{id}/timeline") public Object timeline(HttpServletRequest r,@PathVariable String id){return ok(service.timeline(auth.web(r),id));}
 @PostMapping("/items/{id}/actions") public Object act(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return ok(service.act(auth.web(r),id,b));}
 @PostMapping("/items/{id}/handoff") public Object handoff(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return ok(service.handoff(auth.web(r),id,b));}
 @PostMapping("/items/{id}/suggestions") public Object suggest(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return ok(service.suggest(auth.web(r),id,b));}
 @PostMapping("/handoffs/{id}/feedback") public Object feedback(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return ok(service.feedback(auth.web(r),id,b));}
}
