package com.yihe.framework.ehr;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrController.ok;
@RestController
@RequestMapping("/ehr/api/employee-center/import")
public class EmployeeImportController {
    private final EmployeeImportService imports;private final EhrAccess access;private final EmployeeImportAttemptService attempts;
    public EmployeeImportController(EmployeeImportService imports,EhrAccess access,EmployeeImportAttemptService attempts){this.imports=imports;this.access=access;this.attempts=attempts;}
    @ModelAttribute public void headers(HttpServletResponse response){response.setHeader("Cache-Control","private, no-store");}
    @PostMapping("/commit") public Object commit(HttpServletRequest r,@RequestBody Map<String,Object> b){EhrActor a=access.web(r);attempts.begin(a,b);try{Map<String,Object> result=imports.commit(a,b);try{attempts.finish(a,EhrAccess.text(b,"requestKey"),true,"");}catch(RuntimeException ignored){}return ok(result);}catch(RuntimeException failure){try{attempts.finish(a,EhrAccess.text(b,"requestKey"),false,failure instanceof EhrException?failure.getMessage():"导入未完成，请核对资料或联系管理员");}catch(RuntimeException ignored){}throw failure;}}
    @GetMapping("/history") public Object history(HttpServletRequest r,@RequestParam(defaultValue="1") int page,@RequestParam(defaultValue="10") int limit,@RequestParam Map<String,String> query){return ok(imports.history(access.web(r),page,limit,query));}
    @GetMapping("/history/{batchId}") public Object detail(HttpServletRequest r,@PathVariable String batchId){return ok(imports.detail(access.web(r),batchId));}
}
