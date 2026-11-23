package com.yihe.framework.ehr;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.FileSystemResource;
import static com.yihe.framework.ehr.EhrController.*;

@RestController
public class OcrWorkerController {
    private final OcrWorkerQueue queue;private final EhrAccess access;private final EhrService ehr;
    public OcrWorkerController(OcrWorkerQueue queue,EhrAccess access,EhrService ehr){this.queue=queue;this.access=access;this.ehr=ehr;}
    @ModelAttribute public void privateHeaders(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");r.setHeader("X-Content-Type-Options","nosniff");}
    void auth(HttpServletRequest r){queue.authorize(r.getHeader("Authorization"));}
    String lease(HttpServletRequest r){return r.getHeader("X-OCR-Lease");}
    @PostMapping("/ehr/worker/tasks/claim") public Object claim(HttpServletRequest r,@RequestBody Map<String,Object> b){auth(r);for(int i=0;i<3;i++){try{return ok(queue.claim(b));}catch(org.springframework.dao.TransientDataAccessException e){if(i==2)throw new EhrException(503,"队列繁忙，请保留领取requestKey稍后重试");}}throw new IllegalStateException();}
    @GetMapping("/ehr/worker/tasks/{id}/input") public Object input(HttpServletRequest r,@PathVariable String id){auth(r);return ok(queue.input(id,lease(r)));}
    @GetMapping("/ehr/worker/tasks/{id}/files/{file}") public ResponseEntity<FileSystemResource> file(HttpServletRequest r,@PathVariable String id,@PathVariable String file){auth(r);return attachment(queue.file(id,lease(r),file),ehr.filename(file));}
    @PostMapping("/ehr/worker/tasks/{id}/heartbeat") public Object heartbeat(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object> b){auth(r);return ok(queue.heartbeat(id,lease(r),b));}
    @PostMapping("/ehr/worker/tasks/{id}/result") public Object result(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object> b){auth(r);try{return ok(queue.complete(id,lease(r),b));}catch(EhrException e){if(e.status==422||e.status==413)queue.rejectedResult(id,lease(r),e.getMessage());throw e;}}
    @PostMapping("/ehr/worker/tasks/{id}/failure") public Object failure(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object> b){auth(r);return ok(queue.failure(id,lease(r),b));}
    @GetMapping("/ehr/worker/dictionary") public Object dictionary(HttpServletRequest r){auth(r);return ok(queue.dictionary());}
    @GetMapping("/ehr/worker/environment") public Object environment(HttpServletRequest r){auth(r);return ok(queue.environment());}
    @GetMapping("/ehr/worker/tasks/{id}/receipt") public Object receipt(HttpServletRequest r,@PathVariable String id){auth(r);return ok(queue.receipt(id,lease(r)));}
    @GetMapping("/ehr/api/ocr/tasks") public Object list(HttpServletRequest r,@RequestParam Map<String,String> q){return ok(queue.list(access.web(r),q));}
    @GetMapping("/ehr/api/ocr/summary") public Object summary(HttpServletRequest r){return ok(queue.summary(access.web(r)));}
    @GetMapping("/ehr/api/ocr/tasks/{id}") public Object detail(HttpServletRequest r,@PathVariable String id){return ok(queue.detail(access.web(r),id));}
    @PostMapping("/ehr/api/ocr/tasks/{id}/retry") public Object retry(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object> b){return ok(queue.control(access.web(r),id,b,true));}
    @PostMapping("/ehr/api/ocr/tasks/{id}/cancel") public Object cancel(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object> b){return ok(queue.control(access.web(r),id,b,false));}
}
