package com.yihe.framework.ehr;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import static com.yihe.framework.ehr.EhrAccess.*;

@RestController
@RequestMapping("/ehr/api")
public class EhrController {
    private final EhrAccess auth;private final EhrService service;private final ReportRemovalService removal;
    public EhrController(EhrAccess auth,EhrService service,ReportRemovalService removal){this.auth=auth;this.service=service;this.removal=removal;}
    static Map<String,Object> ok(Object value){return map("code",0,"data",value);}
    @GetMapping("/context") public Object context(HttpServletRequest r){return ok(auth.context(r));}
    @DeleteMapping("/reports/{id}/{revision}") public Object removeReport(HttpServletRequest r,@PathVariable String id,@PathVariable int revision,@RequestHeader("If-Match") String etag,@RequestBody Map<String,Object> body){return ok(removal.remove(auth.web(r),id,revision,etag,body));}
    @GetMapping("/records") public Object list(HttpServletRequest r,@RequestParam Map<String,String> q){return ok(service.list(auth.web(r),q));}
    @GetMapping("/statistics") public Object stats(HttpServletRequest r){return ok(service.statistics(auth.web(r)));}
    @PostMapping("/records") public Object add(HttpServletRequest r,@RequestBody Map<String,Object> b){return ok(service.upsert(auth.web(r),null,b));}
    @GetMapping("/records/{id}") public Object get(HttpServletRequest r,@PathVariable String id){return ok(service.detail(auth.web(r),id));}
    @PutMapping("/records/{id}") public Object edit(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object> b){return ok(service.upsert(auth.web(r),id,b));}
    @DeleteMapping("/records/{id}") public Object remove(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object> b){service.remove(auth.web(r),id,b);return ok(map("deleted",true));}
    @PostMapping("/records/import") public Object batch(HttpServletRequest r,@RequestBody List<Map<String,Object>> b){return ok(service.importRows(auth.web(r),b));}
    @PostMapping("/reports/batch-actions") public Object batchActions(HttpServletRequest r,@RequestBody List<Map<String,Object>> b){return ok(service.batchActions(auth.web(r),b));}
    @PostMapping("/records/{id}/reports") public Object upload(HttpServletRequest r,@PathVariable String id,@RequestParam("file") MultipartFile file,@RequestHeader("Idempotency-Key") String key,@RequestParam(defaultValue="backend_file") String channel)throws IOException{return ok(service.upload(auth.web(r),id,file,key,channel));}
    @GetMapping("/reports/{id}/{revision}") public Object report(HttpServletRequest r,@PathVariable String id,@PathVariable int revision){return ok(service.report(auth.web(r),id,revision,true));}
    @PutMapping("/reports/{id}/{revision}") public Object save(HttpServletRequest r,@PathVariable String id,@PathVariable int revision,@RequestHeader("If-Match") String etag,@RequestBody Map<String,Object> b){return ok(service.saveDraft(auth.web(r),id,revision,b,etag));}
    @PostMapping("/reports/{id}/{revision}/actions") public Object action(HttpServletRequest r,@PathVariable String id,@PathVariable int revision,@RequestBody Map<String,Object> b){return ok(service.transition(auth.web(r),id,revision,b));}
    @PostMapping("/reports/{id}/{revision}/revisions") public Object revise(HttpServletRequest r,@PathVariable String id,@PathVariable int revision,@RequestBody Map<String,Object> b){return ok(service.revise(auth.web(r),id,revision,text(b,"note")));}
    @GetMapping("/files/{id}") public ResponseEntity<FileSystemResource> file(HttpServletRequest r,@PathVariable String id){Path p=service.download(auth.web(r),id);return attachment(p,service.filename(id));}
    static ResponseEntity<FileSystemResource> attachment(Path p,String name){return ResponseEntity.ok().header("X-Content-Type-Options","nosniff").header("Cache-Control","private, no-store").header("Content-Disposition",ContentDisposition.builder("attachment").filename(name,StandardCharsets.UTF_8).build().toString()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(new FileSystemResource(p.toFile()));}
    @GetMapping("/records/export") public ResponseEntity<byte[]> export(HttpServletRequest r,@RequestParam Map<String,String> q){EhrActor a=auth.web(r);List<Map<String,Object>> items=new ArrayList<>();q.put("limit","100");for(int page=1;page<=50;page++){q.put("page",String.valueOf(page));Map<String,Object> result=service.list(a,q);@SuppressWarnings("unchecked")List<Map<String,Object>> rows=(List<Map<String,Object>>)result.get("items");if(((Number)result.get("total")).longValue()>5000)throw new EhrException(422,"单次导出最多5000条，请缩小筛选范围");items.addAll(rows);if(items.size()>=((Number)result.get("total")).longValue())break;}
        StringBuilder csv=new StringBuilder("\ufeff姓名,工号,手机号,单位,班组,岗位,风险,状态,完整度\r\n");for(Map<String,Object> p:items){int i=0;for(String k:new String[]{"name","code","phone","org","team","job","risk","status","complete"}){if(i++>0)csv.append(',');String v=EhrService.str(p.get(k));if(v.matches("^[=+@\\-].*"))v="'"+v;csv.append('"').append(v.replace("\"","\"\"")).append('"');}csv.append("\r\n");}service.audit(a,null,null,null,"records_exported","导出条数："+items.size());return ResponseEntity.ok().header("Content-Disposition","attachment; filename=health-records.csv").header("Cache-Control","no-store").contentType(new MediaType("text","csv",StandardCharsets.UTF_8)).body(csv.toString().getBytes(StandardCharsets.UTF_8));}
}
