package com.yihe.framework.ehr;
import java.util.*;import javax.servlet.http.*;import org.springframework.web.bind.annotation.*;import static com.yihe.framework.ehr.EhrAccess.*;
@RestController @RequestMapping("/ehr/api/worker-orders")
public class WorkerCollaborationController {
 final EhrAccess auth;final WorkerCollaborationService service;
 public WorkerCollaborationController(EhrAccess a,WorkerCollaborationService s){auth=a;service=s;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 @GetMapping public Object list(HttpServletRequest r,@RequestParam Map<String,String>q){return map("code",0,"data",service.list(auth.web(r),q));}
 @GetMapping("/{id}") public Object detail(HttpServletRequest r,@PathVariable String id){return map("code",0,"data",service.detail(auth.web(r),id));}
 @PostMapping public Object create(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.create(auth.web(r),b));}
 @PostMapping("/{id}/actions") public Object act(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return map("code",0,"data",service.act(auth.web(r),id,b));}
}
