package com.yihe.framework.ehr;

import java.util.*;
import java.io.IOException;
import java.nio.file.Path;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;
import org.springframework.core.io.FileSystemResource;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrController.ok;
import static com.yihe.framework.ehr.EhrService.*;

@RestController
@RequestMapping("/ehr/api/communication")
public class CommunicationController {
    final EhrAccess access;final CommunicationService service;final CommunicationFiles files;final CommunicationAi ai;final CommunicationHistory history;
    public CommunicationController(EhrAccess access,CommunicationService service,CommunicationFiles files,CommunicationAi ai,CommunicationHistory history){this.access=access;this.service=service;this.files=files;this.ai=ai;this.history=history;}
    @GetMapping("/history") public Object history(HttpServletRequest r,@RequestParam Map<String,String> q){return ok(history.list(access.web(r),q));}
    @GetMapping("/history/statistics") public Object statistics(HttpServletRequest r,@RequestParam Map<String,String> q){return ok(history.statistics(access.web(r),q));}
    @GetMapping("/history/{cid}/detail") public Object detail(HttpServletRequest r,@PathVariable String cid){return ok(history.detail(access.web(r),cid));}
    @GetMapping("/history/{cid}/plans") public Object plans(HttpServletRequest r,@PathVariable String cid){return ok(history.plans(access.web(r),cid));}
    @PostMapping("/history/{cid}/category") public Object category(HttpServletRequest r,@PathVariable String cid,@RequestBody Map<String,Object> b){return ok(history.category(access.web(r),cid,text(b,"category")));}
    @PostMapping("/history/{cid}/ai-summary") public Object aiSummary(HttpServletRequest r,@PathVariable String cid){return ok(ai.summarize(access.web(r),cid));}
    @GetMapping("/history/export") public ResponseEntity<byte[]> export(HttpServletRequest r,@RequestParam Map<String,String> q){return ResponseEntity.ok().header("Cache-Control","no-store").header("Content-Disposition","attachment; filename=communication-history.csv").contentType(new MediaType("text","csv",java.nio.charset.StandardCharsets.UTF_8)).body(history.export(access.web(r),q));}
    @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");r.setHeader("X-Content-Type-Options","nosniff");}
    @GetMapping("/context") public Object context(HttpServletRequest r){EhrActor a=access.web(r);return ok(map("user",a.user,"name",service.actorName(a),"role",a.doctor?"doctor":"manager","counts",service.counts(a),"ai",ai.status(),"pollAfterMs",3000));}
    @GetMapping("/conversations") public Object list(HttpServletRequest r,@RequestParam Map<String,String> q){return ok(service.list(access.web(r),q));}
    @PostMapping("/conversations") public Object create(HttpServletRequest r,@RequestBody Map<String,Object> b){return ok(service.create(access.web(r),text(b,"recordId")));}
    @GetMapping("/conversations/{cid}/messages") public Object messages(HttpServletRequest r,@PathVariable String cid,@RequestParam(defaultValue="0") long before,@RequestParam(defaultValue="0") long after){return ok(service.messages(access.web(r),cid,before,after));}
    @PostMapping("/conversations/{cid}/messages") public Object send(HttpServletRequest r,@PathVariable String cid,@RequestBody Map<String,Object> b){return ok(service.send(access.web(r),cid,b));}
    @PostMapping("/conversations/{cid}/read") public Object read(HttpServletRequest r,@PathVariable String cid,@RequestBody Map<String,Object> b){return ok(service.read(access.web(r),cid,num(b.get("seq"))));}
    @PostMapping("/conversations/{cid}/actions") public Object action(HttpServletRequest r,@PathVariable String cid,@RequestBody Map<String,Object> b){return ok(service.action(access.web(r),cid,b));}
    @GetMapping("/conversations/{cid}/health") public Object health(HttpServletRequest r,@PathVariable String cid){return ok(service.context(access.web(r),cid));}
    @GetMapping("/conversations/{cid}/summary") public Object summary(HttpServletRequest r,@PathVariable String cid){return ok(service.summary(access.web(r),cid));}
    @PostMapping("/conversations/{cid}/drafts") public Object draft(HttpServletRequest r,@PathVariable String cid,@RequestBody Map<String,Object> b){return ok(ai.draft(access.web(r),cid,b));}
    @PostMapping("/conversations/{cid}/attachments") public Object upload(HttpServletRequest r,@PathVariable String cid,@RequestParam("file") MultipartFile file)throws IOException{return ok(files.upload(access.web(r),cid,file));}
    @GetMapping("/conversations/{cid}/attachments/{fid}") public ResponseEntity<FileSystemResource> download(HttpServletRequest r,@PathVariable String cid,@PathVariable String fid)throws IOException{return file(files,access.web(r),cid,fid);}
    @DeleteMapping("/conversations/{cid}/attachments/{fid}") public Object remove(HttpServletRequest r,@PathVariable String cid,@PathVariable String fid)throws IOException{return ok(files.remove(access.web(r),cid,fid));}
    static ResponseEntity<FileSystemResource> file(CommunicationFiles files,EhrActor a,String cid,String fid)throws IOException{Map<String,Object> f=files.metadata(a,cid,fid);Path p=files.download(a,cid,fid);String mime=str(f.get("mime_type"));return ResponseEntity.ok().header("Cache-Control","private, no-store").header("X-Content-Type-Options","nosniff").header("Content-Security-Policy","default-src 'none'; sandbox").header("Content-Disposition",ContentDisposition.builder(mime.startsWith("image/")?"inline":"attachment").filename(str(f.get("original_name")),java.nio.charset.StandardCharsets.UTF_8).build().toString()).contentType(MediaType.parseMediaType(mime)).body(new FileSystemResource(p.toFile()));}
}
