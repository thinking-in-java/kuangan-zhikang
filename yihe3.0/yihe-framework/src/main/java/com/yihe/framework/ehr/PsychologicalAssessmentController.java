package com.yihe.framework.ehr;
import java.util.*;import javax.servlet.http.*;import org.springframework.web.bind.annotation.*;import static com.yihe.framework.ehr.EhrAccess.*;import static com.yihe.framework.ehr.EhrService.*;
@RestController @RequestMapping("/ehr/api/assessment/psychological") public class PsychologicalAssessmentController {
 final EhrAccess auth;final PsychologicalAssessmentService service;final PsychologicalAssessmentWorker worker;
 public PsychologicalAssessmentController(EhrAccess auth,PsychologicalAssessmentService service,PsychologicalAssessmentWorker worker){this.auth=auth;this.service=service;this.worker=worker;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 @GetMapping public Object list(HttpServletRequest r,@RequestParam Map<String,String> q){Map<String,Object>d=service.list(auth.web(r),q);d.put("engine",worker.health());return map("code",0,"data",d);}
 @GetMapping("/rules") public Object rules(HttpServletRequest r){return map("code",0,"data",service.rules(auth.web(r)));}
 @PostMapping("/rules") public Object saveRules(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.saveRules(auth.web(r),b));}
 @PostMapping("/measurements") public Object register(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.register(auth.web(r),b));}
 @GetMapping("/tasks") public Object tasks(HttpServletRequest r){return map("code",0,"data",service.tasks(auth.web(r)));}
 @PostMapping("/batches") public Object enqueue(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.enqueue(auth.web(r),b));}
 @GetMapping("/records/{id}") public Object detail(HttpServletRequest r,@PathVariable("id")String id,@RequestParam("year")int year,@RequestParam(value="task",required=false)String task){return map("code",0,"data",service.detail(auth.web(r),id,service.year(year),task));}
 @PostMapping("/tasks/{id}") public Object action(HttpServletRequest r,@PathVariable("id")String id,@RequestBody Map<String,Object>b){return map("code",0,"data",service.action(auth.web(r),id,b));}
}