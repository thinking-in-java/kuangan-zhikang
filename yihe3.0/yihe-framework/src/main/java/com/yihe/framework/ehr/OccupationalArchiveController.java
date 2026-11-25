package com.yihe.framework.ehr;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrAccess.map;
@RestController
@RequestMapping("/ehr/api/report-center/occupational")
public class OccupationalArchiveController {
 final EhrAccess auth;final OccupationalArchiveService service;
 public OccupationalArchiveController(EhrAccess auth,@org.springframework.beans.factory.annotation.Qualifier("occupationalArchiveService") OccupationalArchiveService service){this.auth=auth;this.service=service;}
 @ModelAttribute public void headers(HttpServletResponse response){response.setHeader("Cache-Control","private, no-store");}
 @GetMapping("/{id}") public Object detail(HttpServletRequest r,@PathVariable("id") String id,@RequestParam(value="version",required=false) Integer version){return map("code",0,"data",service.detail(auth.web(r),id,version));}
 @PostMapping("/{id}/actions") public Object act(HttpServletRequest r,@PathVariable("id") String id,@RequestBody Map<String,Object> body){return map("code",0,"data",service.act(auth.web(r),id,body));}
}
