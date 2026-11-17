package com.yihe.framework.ehr;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrController.ok;
@RestController
public class MiniappBindingBatchController {
    final MiniappIdentityService identity;final EhrAccess access;
    public MiniappBindingBatchController(MiniappIdentityService identity,EhrAccess access){this.identity=identity;this.access=access;}
    @PostMapping("/ehr/api/binding-codes/batch") public Object issue(HttpServletRequest r,@RequestBody List<Map<String,Object>> rows){return ok(identity.issueBatch(access.web(r),rows));}
}
