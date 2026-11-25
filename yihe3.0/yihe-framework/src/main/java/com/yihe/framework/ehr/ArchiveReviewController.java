package com.yihe.framework.ehr;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import static com.yihe.framework.ehr.EhrAccess.map;
@RestController
@RequestMapping("/ehr/api/report-center/review")
public class ArchiveReviewController {
    final EhrAccess auth;final ArchiveReviewService service;
    public ArchiveReviewController(EhrAccess auth,ArchiveReviewService service){this.auth=auth;this.service=service;}
    @GetMapping public Object list(HttpServletRequest r,@RequestParam Map<String,String> query){return map("code",0,"data",service.list(auth.web(r),query));}
    @GetMapping("/{id}/{revision}") public Object detail(HttpServletRequest r,@PathVariable("id") String id,@PathVariable("revision") int revision){return map("code",0,"data",service.detail(auth.web(r),id,revision));}
    @PostMapping("/{id}/{revision}/actions") public Object act(HttpServletRequest r,@PathVariable("id") String id,@PathVariable("revision") int revision,@RequestBody Map<String,Object> body){return map("code",0,"data",service.act(auth.web(r),id,revision,body));}
    @PostMapping("/batch-actions") public Object batch(HttpServletRequest r,@RequestBody Map<String,Object> body){return map("code",0,"data",service.batch(auth.web(r),body));}
}
