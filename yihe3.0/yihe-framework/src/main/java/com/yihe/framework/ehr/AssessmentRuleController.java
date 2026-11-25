package com.yihe.framework.ehr;
import java.util.*;import javax.servlet.http.*;import org.springframework.web.bind.annotation.*;import static com.yihe.framework.ehr.EhrAccess.*;
@RestController @RequestMapping("/ehr/api/assessment/rules") public class AssessmentRuleController {
 final EhrAccess auth;final AssessmentRuleService service;public AssessmentRuleController(EhrAccess auth,AssessmentRuleService service){this.auth=auth;this.service=service;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 @GetMapping public Object list(HttpServletRequest r,@RequestParam Map<String,String>q){return map("code",0,"data",service.list(auth.web(r),q));}
 @PostMapping public Object save(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.save(auth.web(r),b));}
 @PostMapping("/import") public Object imp(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.importRules(auth.web(r),b));}
 @PostMapping("/simulate") public Object simulate(HttpServletRequest r,@RequestBody Map<String,Object>b){return map("code",0,"data",service.simulate(auth.web(r),b));}
 @GetMapping("/{id}") public Object detail(HttpServletRequest r,@PathVariable("id")String id,@RequestParam(value="revision",required=false)Integer revision){return map("code",0,"data",service.detail(auth.web(r),id,revision));}
 @PostMapping("/{id}/actions") public Object action(HttpServletRequest r,@PathVariable("id")String id,@RequestBody Map<String,Object>b){return map("code",0,"data",service.action(auth.web(r),id,b));}
}