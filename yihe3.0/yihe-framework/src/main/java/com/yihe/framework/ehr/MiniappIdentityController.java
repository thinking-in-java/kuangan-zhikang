package com.yihe.framework.ehr;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.FileSystemResource;
import static com.yihe.framework.ehr.EhrController.*;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.require;

@RestController
@RequestMapping("/ehr/miniapp")
public class MiniappIdentityController {
    private final MiniappIdentityService identity;private final EhrService ehr;private final MiniappReportService reports;
    public MiniappIdentityController(MiniappIdentityService identity,EhrService ehr,MiniappReportService reports){this.identity=identity;this.ehr=ehr;this.reports=reports;}
    @ModelAttribute public void privateHeaders(HttpServletResponse response){response.setHeader("Cache-Control","private, no-store");response.setHeader("Pragma","no-cache");response.setHeader("X-Content-Type-Options","nosniff");}
    @GetMapping("/config") public Object config(){return ok(identity.config());}
    @PostMapping("/wechat/login") public Object login(HttpServletRequest req,@RequestBody Map<String,Object> body){identity.limitLogin(req);return ok(identity.login(body));}
    @GetMapping("/me") public Object me(HttpServletRequest req){return ok(identity.state(identity.session(req)));}
    @DeleteMapping("/session") public Object logout(HttpServletRequest req){identity.logout(identity.session(req));return ok(map("loggedOut",true));}
    @PostMapping("/binding/verify") public Object verify(HttpServletRequest req,@RequestBody Map<String,Object> body){Map<String,Object> s=identity.session(req);identity.limitVerify(req,s);return ok(identity.verify(s,body));}
    @PostMapping("/binding/confirm") public Object confirm(HttpServletRequest req,@RequestBody Map<String,Object> body){Map<String,Object> s=identity.session(req);return ok(identity.confirm(s,body));}
    @GetMapping("/health-record") public Object record(HttpServletRequest req){return ok(identity.healthRecord(identity.session(req)));}
    @GetMapping("/reports") public Object reports(HttpServletRequest req,@RequestParam Map<String,String> query){return ok(reports.list(identity.actor(identity.session(req)),query.getOrDefault("status","all"),query));}
    @GetMapping("/reports/approved") public Object approved(HttpServletRequest req,@RequestParam Map<String,String> query){return reportTab(req,query,"approved");}
    @GetMapping("/reports/pending") public Object pending(HttpServletRequest req,@RequestParam Map<String,String> query){return reportTab(req,query,"pending");}
    private Object reportTab(HttpServletRequest req,Map<String,String> query,String tab){require(!query.containsKey("status")||tab.equals(query.get("status")),422,"status与列表地址不一致");return ok(reports.list(identity.actor(identity.session(req)),tab,query));}
    @PostMapping("/reports") public Object upload(HttpServletRequest req,@RequestParam("file") MultipartFile file,@RequestHeader("Idempotency-Key") String key)throws IOException{EhrActor a=identity.actor(identity.session(req));return ok(ehr.upload(a,a.boundRecordId,file,key,"miniapp"));}
    @GetMapping("/reports/{id}/{revision}") public Object report(HttpServletRequest req,@PathVariable String id,@PathVariable int revision){EhrActor a=identity.actor(identity.session(req));Map<String,Object> r=ehr.report(a,id,revision,true);r.put("candidate",!Boolean.TRUE.equals(r.get("published")));return ok(r);}
    @GetMapping("/files/{id}") public ResponseEntity<FileSystemResource> file(HttpServletRequest req,@PathVariable String id){EhrActor a=identity.actor(identity.session(req));return attachment(ehr.download(a,id),ehr.filename(id));}
}
