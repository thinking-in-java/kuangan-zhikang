package com.yihe.framework.ehr;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrController.ok;
@RestController
@RequestMapping("/ehr/api/communication/auto-replies")
public class AutoReplyController {
 final EhrAccess access;final AutoReplyRules rules;final AutoReplyEngine engine;
 public AutoReplyController(EhrAccess access,AutoReplyRules rules,AutoReplyEngine engine){this.access=access;this.rules=rules;this.engine=engine;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");r.setHeader("X-Content-Type-Options","nosniff");}
 @GetMapping public Object view(HttpServletRequest r){return ok(rules.view(access.web(r)));}
 @PostMapping("/initialize") public Object init(HttpServletRequest r){return ok(rules.initialize(access.web(r)));}
 @PostMapping("/rules") public Object add(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(rules.upsert(access.web(r),null,b));}
 @PutMapping("/rules/{id}") public Object edit(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return ok(rules.upsert(access.web(r),id,b));}
 @PostMapping("/batch") public Object batch(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(rules.batch(access.web(r),b));}
 @PostMapping("/import") public Object imports(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(rules.importRules(access.web(r),b));}
 @PutMapping("/settings") public Object settings(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(rules.settings(access.web(r),b));}
 @PostMapping("/publish") public Object publish(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(rules.publish(access.web(r),b));}
 @GetMapping("/releases") public Object releases(HttpServletRequest r){return ok(rules.releases(access.web(r)));}
 @PostMapping("/rollback") public Object rollback(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(rules.rollback(access.web(r),b));}
 @PostMapping("/test") public Object test(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(engine.test(access.web(r),b));}
}
