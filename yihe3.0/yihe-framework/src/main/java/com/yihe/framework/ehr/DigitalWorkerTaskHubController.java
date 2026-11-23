package com.yihe.framework.ehr;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrController.ok;

@RestController
@RequestMapping("/ehr/api/digital-worker-hub")
public class DigitalWorkerTaskHubController {
    private final EhrAccess access;private final DigitalWorkerTaskHub hub;
    public DigitalWorkerTaskHubController(EhrAccess access,DigitalWorkerTaskHub hub){this.access=access;this.hub=hub;}
    @ModelAttribute public void headers(HttpServletResponse response){response.setHeader("Cache-Control","private, no-store");}
    @GetMapping public Object list(HttpServletRequest request,@RequestParam Map<String,String> query){return ok(hub.list(access.web(request),query));}
    @GetMapping("/summary") public Object summary(HttpServletRequest request){return ok(hub.summary(access.web(request)));}
}
