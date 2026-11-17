package com.yihe.framework.ehr;

import java.util.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrController.ok;

@RestController
public class MiniappCorrectionController {
    private final MiniappCorrectionService service;private final MiniappIdentityService identity;private final EhrAccess access;
    public MiniappCorrectionController(MiniappCorrectionService service,MiniappIdentityService identity,EhrAccess access){this.service=service;this.identity=identity;this.access=access;}
    @ModelAttribute public void privateHeaders(HttpServletResponse response){response.setHeader("Cache-Control","private, no-store");response.setHeader("X-Content-Type-Options","nosniff");}
    @PostMapping("/ehr/miniapp/binding/correction-requests") public Object submit(HttpServletRequest req,@RequestBody Map<String,Object> b){Map<String,Object> s=identity.session(req);identity.limit("correction-user:"+s.get("mini_user_id"),900,8);return ok(service.submit(s,b));}
    @GetMapping("/ehr/miniapp/binding/correction-requests") public Object mine(HttpServletRequest req,@RequestParam Map<String,String> q){return ok(service.mine(identity.session(req),q));}
    @GetMapping("/ehr/api/correction-requests") public Object queue(HttpServletRequest req,@RequestParam Map<String,String> q){return ok(service.queue(access.web(req),q));}
    @PostMapping("/ehr/api/correction-requests/{id}/review") public Object review(HttpServletRequest req,@PathVariable String id,@RequestBody Map<String,Object> b){return ok(service.review(access.web(req),id,b));}
}
