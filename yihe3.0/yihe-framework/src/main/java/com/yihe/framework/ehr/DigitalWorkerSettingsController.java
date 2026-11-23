package com.yihe.framework.ehr;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrController.ok;
@RestController
@RequestMapping("/ehr/api/digital-worker-settings")
public class DigitalWorkerSettingsController {
    private final EhrAccess access;private final DigitalWorkerSettings settings;
    public DigitalWorkerSettingsController(EhrAccess access,DigitalWorkerSettings settings){this.access=access;this.settings=settings;}
    @ModelAttribute public void headers(HttpServletResponse response){response.setHeader("Cache-Control","private, no-store");}
    @GetMapping public Object all(HttpServletRequest request){return ok(settings.all(access.web(request)));}
    @PostMapping("/{worker}") public Object save(HttpServletRequest request,@PathVariable String worker,@RequestBody Map<String,Object> body){return ok(settings.save(access.web(request),worker,body));}
}
