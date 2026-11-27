package com.yihe.framework.ehr;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrController.ok;
import static com.yihe.framework.ehr.EhrAccess.*;

@RestController
@RequestMapping("/ehr/api/records/{recordId}")
public class MiniappBindingAdminController {
    private final MiniappIdentityService identity;private final EhrAccess access;
    public MiniappBindingAdminController(MiniappIdentityService identity,EhrAccess access){this.identity=identity;this.access=access;}
    @GetMapping("/binding") public Object metadata(HttpServletRequest req,@PathVariable String recordId){return ok(identity.metadata(access.web(req),recordId));}
    @PostMapping("/binding-code") public Object issue(HttpServletRequest req,@PathVariable String recordId,@RequestBody Map<String,Object> b){return ok(identity.issue(access.web(req),recordId,b));}
    @DeleteMapping("/binding-code") public Object revoke(HttpServletRequest req,@PathVariable String recordId,@RequestParam(required=false) String codeId){if(codeId==null)identity.revokeCode(access.web(req),recordId);else identity.revokeCode(access.web(req),recordId,codeId);return ok(map("revoked",true));}
    @PostMapping("/binding/revoke") public Object unbind(HttpServletRequest req,@PathVariable String recordId,@RequestBody Map<String,Object> b){identity.unbind(access.web(req),recordId,text(b,"reason"));return ok(map("unbound",true,"sessionsRevoked",true));}
}
