package com.yihe.framework.ehr;
import java.util.*;import javax.servlet.http.*;import org.springframework.web.bind.annotation.*;import static com.yihe.framework.ehr.EhrAccess.*;
@RestController @RequestMapping("/ehr/api/questionnaire-center") public class QuestionnaireWorkflowController {
 final EhrAccess auth;final QuestionnaireWorkflow service;
 public QuestionnaireWorkflowController(EhrAccess auth,QuestionnaireWorkflow service){this.auth=auth;this.service=service;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 @GetMapping public Object get(HttpServletRequest r){return map("code",0,"data",service.workspace(auth.web(r)));}
 @PostMapping("/bootstrap") public Object bootstrap(HttpServletRequest r){return map("code",0,"data",service.bootstrap(auth.web(r)));}
 @PostMapping public Object save(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.save(auth.web(r),b));}
 @PostMapping("/plans/{id}/publish") public Object publish(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return map("code",0,"data",service.publish(auth.web(r),id,b));}
 @PostMapping("/plans/{id}/remind") public Object remind(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return map("code",0,"data",service.remind(auth.web(r),id,b));}
 @PostMapping("/responses/batch") public Object batch(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.batch(auth.web(r),b));}
 @PostMapping("/responses/{id}/actions") public Object action(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return map("code",0,"data",service.operate(auth.web(r),id,b));}
}
