package com.yihe.framework.ehr;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import static com.yihe.framework.ehr.EhrAccess.map;
@RestController
@RequestMapping("/ehr/api/report-center/matching")
public class ReportMatchingController {
    final EhrAccess auth;final ReportMatchingService service;
    public ReportMatchingController(EhrAccess auth,ReportMatchingService service){this.auth=auth;this.service=service;}
    @GetMapping public Object list(HttpServletRequest request,@RequestParam Map<String,String> query){return map("code",0,"data",service.list(auth.web(request),query));}
    @GetMapping("/employees") public Object employees(HttpServletRequest request,@RequestParam(value="search",defaultValue="") String search){return map("code",0,"data",service.searchEmployees(auth.web(request),search));}
    @GetMapping("/{id}/{revision}") public Object detail(HttpServletRequest request,@PathVariable("id") String id,@PathVariable("revision") int revision){return map("code",0,"data",service.detail(auth.web(request),id,revision));}
    @PostMapping("/{id}/{revision}") public Object save(HttpServletRequest request,@PathVariable("id") String id,@PathVariable("revision") int revision,@RequestHeader("If-Match") String etag,@RequestBody Map<String,Object> body){return map("code",0,"data",service.save(auth.web(request),id,revision,etag,body));}
}
