package com.yihe.framework.ehr;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.FileSystemResource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import static com.yihe.framework.ehr.EhrController.ok;
@RestController @RequestMapping("/ehr")
public class EhrOcrController {
    final EhrOcrService ocr;final EhrAccess access;final EhrService ehr;final OcrWorkerQueue queue;
    public EhrOcrController(EhrOcrService ocr,EhrAccess access,EhrService ehr,OcrWorkerQueue queue){this.ocr=ocr;this.access=access;this.ehr=ehr;this.queue=queue;}
    @PostMapping("/api/reports/{id}/{revision}/ocr") public Object start(HttpServletRequest r,@PathVariable String id,@PathVariable int revision){return ok(ocr.start(access.web(r),id,revision));}
    @GetMapping("/integration/jobs/{jobId}/input") public Object input(@RequestHeader(value="Authorization",required=false) String auth,@PathVariable String jobId){ocr.authorize(auth);return ok(ocr.input(jobId));}
    @PostMapping("/integration/jobs/{jobId}/result") public Object result(@RequestHeader(value="Authorization",required=false) String auth,@PathVariable String jobId,@RequestBody Map<String,Object> body){ocr.authorize(auth);return ok(queue.legacyComplete(jobId,body));}
    @GetMapping("/integration/jobs/{jobId}/files/{fileId}") public ResponseEntity<FileSystemResource> file(@RequestHeader(value="Authorization",required=false) String auth,@PathVariable String jobId,@PathVariable String fileId){ocr.authorize(auth);Map<String,Object> j=ocr.job(jobId,false);if(!ocr.hasFile(jobId,fileId))throw new EhrException(404,"原件未关联此任务");return EhrController.attachment(ehr.download(ocr.actor(j),fileId),ehr.filename(fileId));}
}
