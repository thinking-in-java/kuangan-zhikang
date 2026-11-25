package com.yihe.framework.ehr;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import static com.yihe.framework.ehr.EhrController.ok;
import static com.yihe.framework.ehr.EhrService.*;
import static com.yihe.framework.ehr.EhrAccess.*;
@RestController
@RequestMapping("/ehr/api/communication/knowledge")
public class ServiceKnowledgeController {
 final EhrAccess auth;final ServiceKnowledge kb;
 public ServiceKnowledgeController(EhrAccess auth,ServiceKnowledge kb){this.auth=auth;this.kb=kb;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");r.setHeader("X-Content-Type-Options","nosniff");}
 @GetMapping public Object list(HttpServletRequest r,@RequestParam Map<String,String>q){return ok(kb.list(auth.web(r),q));}
 @PostMapping("/initialize") public Object initialize(HttpServletRequest r){return ok(kb.initialize(auth.web(r)));}
 @PostMapping("/documents") public Object add(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(kb.save(auth.web(r),null,b));}
 @GetMapping("/documents/{id}") public Object detail(HttpServletRequest r,@PathVariable String id){return ok(kb.detail(auth.web(r),id));}
 @PutMapping("/documents/{id}") public Object edit(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return ok(kb.save(auth.web(r),id,b));}
 @PostMapping("/documents/{id}/action") public Object action(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return ok(kb.action(auth.web(r),id,b));}
 @GetMapping("/documents/{id}/history") public Object history(HttpServletRequest r,@PathVariable String id){return ok(kb.history(auth.web(r),id));}
 @PostMapping("/documents/{id}/restore") public Object restore(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return ok(kb.restore(auth.web(r),id,b));}
 @PostMapping("/batch") public Object batch(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(kb.batch(auth.web(r),b));}
 @PostMapping("/import") public Object imports(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(kb.imports(auth.web(r),b));}
 @GetMapping("/export") public Object export(HttpServletRequest r){return ok(map("documents",kb.export(auth.web(r))));}
 @PostMapping("/categories") public Object categoryAdd(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(kb.saveCategory(auth.web(r),null,b));}
 @PutMapping("/categories/{id}") public Object categoryEdit(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){return ok(kb.saveCategory(auth.web(r),id,b));}
 @DeleteMapping("/categories/{id}") public Object categoryRemove(HttpServletRequest r,@PathVariable String id,@RequestBody Map<String,Object>b){kb.deleteCategory(auth.web(r),id,num(b.get("version")));return ok(map("deleted",true));}
 @GetMapping("/settings") public Object settings(HttpServletRequest r){return ok(kb.settings(auth.web(r)));}
 @PutMapping("/settings") public Object configure(HttpServletRequest r,@RequestBody Map<String,Object>b){return ok(kb.configure(auth.web(r),b));}
 @PostMapping("/retrieve") public Object retrieve(HttpServletRequest r,@RequestBody Map<String,Object>b){String q=str(b.get("question")).trim();require(!q.isEmpty(),422,"请输入测试问题");return ok(map("items",kb.retrieve(auth.web(r),q),"note","本地词法检索测试，不调用模型，不计入AI引用量"));}
}
