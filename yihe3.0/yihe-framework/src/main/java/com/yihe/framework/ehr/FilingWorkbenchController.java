package com.yihe.framework.ehr;
import java.util.*;import javax.servlet.http.*;import org.springframework.web.bind.annotation.*;import static com.yihe.framework.ehr.EhrAccess.map;
@RestController @RequestMapping("/ehr/api/report-center/filing")
public class FilingWorkbenchController {
 final EhrAccess auth;final FilingWorkbenchService service;
 public FilingWorkbenchController(EhrAccess auth,FilingWorkbenchService service){this.auth=auth;this.service=service;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 @GetMapping public Object list(HttpServletRequest r,@RequestParam Map<String,String> q){return map("code",0,"data",service.list(auth.web(r),q));}
 @GetMapping("/{id}") public Object detail(HttpServletRequest r,@PathVariable("id") String id,@RequestParam(value="kind",defaultValue="occupational") String kind,@RequestParam(value="version",required=false) Integer version){return map("code",0,"data",service.detail(auth.web(r),id,kind,version));}
 @GetMapping("/{id}/fill-proposal") public Object proposal(HttpServletRequest r,@PathVariable("id") String id,@RequestParam(value="kind",defaultValue="occupational") String kind){return map("code",0,"data",service.proposal(auth.web(r),id,kind));}
 @PostMapping("/{id}/actions") public Object act(HttpServletRequest r,@PathVariable("id") String id,@RequestParam(value="kind",defaultValue="occupational") String kind,@RequestBody Map<String,Object> b){return map("code",0,"data",service.act(auth.web(r),id,kind,b));}
 @PostMapping("/batch-fill") public Object batch(HttpServletRequest r,@RequestBody Map<String,Object> b){return map("code",0,"data",service.batch(auth.web(r),b));}
}
