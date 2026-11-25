package com.yihe.framework.ehr;
import java.util.*;import javax.servlet.http.*;import org.springframework.web.bind.annotation.*;import static com.yihe.framework.ehr.EhrAccess.*;import static com.yihe.framework.ehr.EhrService.*;
@RestController @RequestMapping("/ehr/api/assessment/comprehensive") public class ComprehensiveHealthController {
 final EhrAccess auth;final ComprehensiveHealthService service;final ComprehensiveHealthRunner runner;
 public ComprehensiveHealthController(EhrAccess auth,ComprehensiveHealthService service,ComprehensiveHealthRunner runner){this.auth=auth;this.service=service;this.runner=runner;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 @GetMapping("/{id}") public Object detail(HttpServletRequest r,@PathVariable("id") String id,@RequestParam(value="analysis",required=false) String analysis){Map<String,Object> d=service.detail(auth.web(r),id,analysis);d.put("engine",runner.status());return map("code",0,"data",d);}
 @PostMapping("/{id}/generate") public Object generate(HttpServletRequest r,@PathVariable("id") String id,@RequestBody Map<String,Object> body){return map("code",0,"data",runner.generate(auth.web(r),id,body));}
 @PostMapping("/{id}/review") public Object review(HttpServletRequest r,@PathVariable("id") String id,@RequestBody Map<String,Object> body){return map("code",0,"data",service.review(auth.web(r),id,body));}
 @PostMapping("/{id}/publish/{review}") public Object publish(HttpServletRequest r,@PathVariable("id") String id,@PathVariable("review") String review,@RequestBody Map<String,Object> b){return map("code",0,"data",service.publish(auth.web(r),id,review,b));}
 @PostMapping("/{id}/cancel/{analysis}") public Object cancel(HttpServletRequest r,@PathVariable("id") String id,@PathVariable("analysis") String analysis,@RequestBody Map<String,Object> b){return map("code",0,"data",service.cancel(auth.web(r),id,analysis,num(b.get("version")),text(b,"note")));}
}
