package com.yihe.framework.ehr;
import java.util.*;import javax.servlet.http.*;import org.springframework.web.bind.annotation.*;import static com.yihe.framework.ehr.EhrAccess.*;
@RestController @RequestMapping("/ehr/api/questionnaires") public class QuestionnaireController {
 final EhrAccess auth;final QuestionnaireService service;
 public QuestionnaireController(EhrAccess auth,QuestionnaireService service){this.auth=auth;this.service=service;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 @GetMapping public Object list(HttpServletRequest r,@RequestParam Map<String,String>q){return map("code",0,"data",service.list(auth.web(r),q));}
 @PostMapping public Object save(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.save(auth.web(r),b));}
 @GetMapping("/assets") public Object assets(HttpServletRequest r,@RequestParam("kind")String kind){return map("code",0,"data",service.assets(auth.web(r),kind));}
 @PostMapping("/assets") public Object saveAsset(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.saveAsset(auth.web(r),b));}
 @PostMapping("/publish") public Object publish(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.publish(auth.web(r),b));}
 @PostMapping("/batch-actions") public Object batch(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.batchAction(auth.web(r),b));}
 @GetMapping("/history") public Object history(HttpServletRequest r){return map("code",0,"data",service.history(auth.web(r)));}
 @GetMapping("/{id}") public Object detail(HttpServletRequest r,@PathVariable("id")String id){return map("code",0,"data",service.detail(auth.web(r),id));}
 @PostMapping("/{id}/actions") public Object action(HttpServletRequest r,@PathVariable("id")String id,@RequestBody Map<String,Object>b){return map("code",0,"data",service.action(auth.web(r),id,b));}
 @GetMapping("/assignments/{id}") public Object form(HttpServletRequest r,@PathVariable("id")String id){return map("code",0,"data",service.answerForm(auth.web(r),id));}
 @PostMapping("/assignments/{id}/submit") public Object submit(HttpServletRequest r,@PathVariable("id")String id,@RequestBody Map<String,Object>b){return map("code",0,"data",service.submit(auth.web(r),id,b));}
 @PostMapping("/responses/{id}/review") public Object review(HttpServletRequest r,@PathVariable("id")String id,@RequestBody Map<String,Object>b){return map("code",0,"data",service.review(auth.web(r),id,b));}
}
