package com.yihe.framework.ehr;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.FileSystemResource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.io.IOException;
import static com.yihe.framework.ehr.EhrController.ok;
import static com.yihe.framework.ehr.EhrAccess.map;

@RestController @RequestMapping("/ehr/mobile")
public class EhrMobileController {
    private final EhrAccess auth;private final EhrService service;
    public EhrMobileController(EhrAccess auth,EhrService service){this.auth=auth;this.service=service;}
    @PostMapping("/session") public Object login(@RequestBody Map<String,Object> b){return ok(auth.login(b));}
    @DeleteMapping("/session") public Object logout(HttpServletRequest r){auth.logout(r);return ok(map("loggedOut",true));}
    @GetMapping("/me") public Object me(HttpServletRequest r){return ok(service.me(auth.mobile(r)));}
    @PostMapping("/reports") public Object upload(HttpServletRequest r,@RequestParam("file") MultipartFile file,@RequestHeader("Idempotency-Key") String key)throws IOException{EhrActor a=auth.mobile(r);Map<String,Object> self=service.me(a);return ok(service.upload(a,(String)self.get("id"),file,key,"miniapp"));}
    @GetMapping("/reports/{id}/{revision}") public Object report(HttpServletRequest r,@PathVariable String id,@PathVariable int revision){return ok(service.report(auth.mobile(r),id,revision,true));}
    @GetMapping("/files/{id}") public ResponseEntity<FileSystemResource> file(HttpServletRequest r,@PathVariable String id){return EhrController.attachment(service.download(auth.mobile(r),id),service.filename(id));}
}
