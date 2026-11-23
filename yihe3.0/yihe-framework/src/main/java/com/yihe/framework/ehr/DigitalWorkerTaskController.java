package com.yihe.framework.ehr;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrController.ok;

@RestController
@RequestMapping("/ehr/api/digital-worker-tasks")
public class DigitalWorkerTaskController {
    private final EhrAccess access;
    private final DigitalWorkerTaskService tasks;
    private final DigitalWorkerAiQueue ai;
    private final DigitalWorkerModelClient client;
    private final EhrService ehr;
    private final EhrRepository db;
    public DigitalWorkerTaskController(EhrAccess access,DigitalWorkerTaskService tasks,DigitalWorkerAiQueue ai,DigitalWorkerModelClient client,EhrService ehr,EhrRepository db) {
        this.ehr=ehr;this.db=db;
        this.ai=ai;
        this.client=client;
        this.access=access;
        this.tasks=tasks;
    }
    @ModelAttribute
    public void privateHeaders(HttpServletResponse response) {
        response.setHeader("Cache-Control","private, no-store");
    }
    @GetMapping
    public Object list(HttpServletRequest request,@RequestParam Map<String,String> query) {
        return ok(tasks.list(access.web(request),query));
    }
    @GetMapping("/model-status")
    public Object modelStatus(HttpServletRequest request) {
        access.web(request);
        Map<String,Object> status=client.status();
        return ok(EhrAccess.map("configured",status.get("configured"),"reviewRequired",true));
    }
    @GetMapping("/catalog")
    public Object catalog(HttpServletRequest request){access.web(request);return ok(EhrAccess.map("profiles",DigitalWorkerTaskProfiles.all()));}
    @PostMapping("/{id}/context")
    public Object saveContext(HttpServletRequest request,@PathVariable String id,@RequestBody Map<String,Object> body){return ok(tasks.saveContext(access.web(request),id,body));}
    @PostMapping("/{id}/generate")
    public Object generate(HttpServletRequest request,@PathVariable String id,@RequestBody Map<String,Object> body) {
        return ok(ai.request(access.web(request),id,body));
    }
    @PostMapping("/{id}/archive")
    public Object linkArchive(HttpServletRequest request,@PathVariable String id,@RequestBody Map<String,Object> body) {
        return ok(tasks.linkArchive(access.web(request),id,body));
    }
    @GetMapping("/{id}")
    public Object detail(HttpServletRequest request,@PathVariable String id) {
        return ok(tasks.detail(access.web(request),id));
    }
    @GetMapping("/{id}/originals/{sourceId}")
    public org.springframework.http.ResponseEntity<org.springframework.core.io.FileSystemResource> original(HttpServletRequest request,@PathVariable String id,@PathVariable String sourceId) throws java.io.IOException {
        EhrActor actor=access.web(request);Map<String,Object> task=tasks.detail(actor,id);
        if(task.get("recordId")==null||db.count("SELECT COUNT(*) FROM a_report_revision_sources r JOIN a_health_reports h ON h.report_id=r.report_id AND h.revision=r.revision WHERE r.source_file_id=? AND h.record_id=? AND h.scope_code=? AND h.data_mode=?",sourceId,task.get("recordId"),actor.scope,actor.mode)==0)
            throw new EhrException(404,"原件不属于当前关联档案或没有访问权限");
        java.nio.file.Path file=ehr.download(actor,sourceId);byte[] signature=new byte[8];
        try(java.io.InputStream in=java.nio.file.Files.newInputStream(file)){in.read(signature);}
        String mime=signature[0]=='%'&&signature[1]=='P'&&signature[2]=='D'&&signature[3]=='F'?"application/pdf":
            (signature[0]&255)==137&&signature[1]=='P'?"image/png":(signature[0]&255)==255&&(signature[1]&255)==216?"image/jpeg":null;
        if(mime==null)throw new EhrException(422,"此原件类型暂不支持在线预览");
        return org.springframework.http.ResponseEntity.ok().header("Content-Disposition","inline; filename=report-original."+(mime.equals("application/pdf")?"pdf":mime.equals("image/png")?"png":"jpg"))
            .header("X-Content-Type-Options","nosniff").header("Cache-Control","private, no-store")
            .contentType(org.springframework.http.MediaType.parseMediaType(mime)).body(new org.springframework.core.io.FileSystemResource(file.toFile()));
    }
    @PostMapping
    public Object create(HttpServletRequest request,@RequestBody Map<String,Object> body) {
        return ok(tasks.create(access.web(request),body));
    }
    @PostMapping("/{id}/actions")
    public Object act(HttpServletRequest request,@PathVariable String id,@RequestBody Map<String,Object> body) {
        return ok(tasks.act(access.web(request),id,body));
    }
}
