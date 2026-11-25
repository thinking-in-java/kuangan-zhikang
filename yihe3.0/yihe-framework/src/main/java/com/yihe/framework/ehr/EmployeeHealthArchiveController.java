package com.yihe.framework.ehr;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrAccess.map;
@RestController
@RequestMapping("/ehr/api/report-center/employees")
public class EmployeeHealthArchiveController {
 final EhrAccess auth;final EmployeeHealthArchiveService service;
 public EmployeeHealthArchiveController(EhrAccess auth,EmployeeHealthArchiveService service){this.auth=auth;this.service=service;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 @GetMapping public Object list(HttpServletRequest r,@RequestParam Map<String,String> q){return map("code",0,"data",service.list(auth.web(r),q));}
 @GetMapping("/overview") public Object overview(HttpServletRequest r){return map("code",0,"data",service.overview(auth.web(r)));}
 @GetMapping("/{id}") public Object detail(HttpServletRequest r,@PathVariable("id") String id){return map("code",0,"data",service.detail(auth.web(r),id));}
}
