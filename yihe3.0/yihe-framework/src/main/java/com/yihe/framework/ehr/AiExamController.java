package com.yihe.framework.ehr;
import java.util.*;import javax.servlet.http.*;import org.springframework.web.bind.annotation.*;import static com.yihe.framework.ehr.EhrAccess.*;
@RestController @RequestMapping("/ehr/api/assessment/exam")
public class AiExamController {
 final EhrAccess auth;final AiExamService service;final AiExamWorker worker;
 public AiExamController(EhrAccess auth,AiExamService service,AiExamWorker worker){this.auth=auth;this.service=service;this.worker=worker;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 @GetMapping public Object list(HttpServletRequest r,@RequestParam Map<String,String> q){EhrActor a=auth.web(r);Map<String,Object> data=service.list(a,q);Map<String,Object> engine=worker.health(),config=service.policy(a);engine.put("aiEnabled",config.get("aiEnabled"));engine.put("acceptingTasks",config.get("acceptingTasks"));data.put("engine",engine);return map("code",0,"data",data);}
 @GetMapping("/{report}/{revision}") public Object detail(HttpServletRequest r,@PathVariable("report") String report,@PathVariable("revision") int revision,@RequestParam(value="task",required=false) String task){return map("code",0,"data",service.selectedDetail(auth.web(r),report,revision,task));}
 @GetMapping("/batches/{id}/detail") public Object batchDetail(HttpServletRequest r,@PathVariable("id") String id){return map("code",0,"data",service.batchDetail(auth.web(r),id));}
 @PostMapping("/batches") public Object enqueue(HttpServletRequest r,@RequestBody Map<String,Object> b){return map("code",0,"data",service.enqueue(auth.web(r),b));}
 @PostMapping("/publish-batch") public Object publish(HttpServletRequest r,@RequestBody Map<String,Object> b){return map("code",0,"data",service.publishBatch(auth.web(r),b));}
 @PostMapping("/tasks/{id}/actions") public Object action(HttpServletRequest r,@PathVariable("id") String id,@RequestBody Map<String,Object> b){return map("code",0,"data",service.action(auth.web(r),id,b));}
 @PostMapping("/batches/{id}/cancel") public Object cancel(HttpServletRequest r,@PathVariable("id") String id,@RequestBody Map<String,Object> b){return map("code",0,"data",service.cancelBatch(auth.web(r),id,text(b,"note")));}
}
