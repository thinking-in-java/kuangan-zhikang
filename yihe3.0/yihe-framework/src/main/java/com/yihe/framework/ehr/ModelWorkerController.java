package com.yihe.framework.ehr;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrController.ok;
/** Dedicated model-worker claim route; a B-worker credential cannot claim here. */
@RestController
public class ModelWorkerController {
    private final OcrWorkerQueue queue;private final EhrAccess auth;private final ModelRecognitionSettings settings;
    public ModelWorkerController(OcrWorkerQueue queue,EhrAccess auth,ModelRecognitionSettings settings){this.queue=queue;this.auth=auth;this.settings=settings;}
    @PostMapping("/ehr/model-worker/tasks/claim") public Object claim(HttpServletRequest r,@RequestBody Map<String,Object> b){queue.authorizeModel(r.getHeader("Authorization"));return ok(queue.claimForModel(b));}
    @GetMapping("/ehr/api/recognition") public Object status(HttpServletRequest r){auth.web(r);return ok(settings.status());}
}
