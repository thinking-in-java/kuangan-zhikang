package com.yihe.framework.ehr;

import java.util.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrController.ok;
import static com.yihe.framework.ehr.EhrService.require;
import static com.yihe.framework.ehr.EhrService.str;

@RestController
@RequestMapping("/ehr/miniapp/agent")
public class MiniappAgentController {
    final MiniappIdentityService identity;final MiniappAgentStore store;final MiniappAgentService agent;
    public MiniappAgentController(MiniappIdentityService identity,MiniappAgentStore store,MiniappAgentService agent){this.identity=identity;this.store=store;this.agent=agent;}
    @ModelAttribute public void privateHeaders(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");r.setHeader("Pragma","no-cache");r.setHeader("X-Content-Type-Options","nosniff");}
    MiniappAgentStore.Person person(HttpServletRequest r){Map<String,Object> s=identity.session(r);return store.person(str(s.get("mini_user_id")),identity.dataMode());}
    int number(Map<String,String> q,String key,int fallback,int max){return MiniappReportService.number(q.get(key),fallback,1,max);}
    void paging(Map<String,String> q){require(q.keySet().stream().allMatch(k->Arrays.asList("page","pageSize").contains(k)),422,"仅允许page和pageSize分页参数");}
    @GetMapping("/context") public Object context(HttpServletRequest r){return ok(agent.context(person(r)));}
    @PostMapping("/conversations") public Object create(HttpServletRequest r,@RequestBody(required=false) Map<String,Object> body){require(body==null||body.isEmpty(),422,"创建对话不接受客户端身份字段");return ok(store.create(person(r)));}
    @GetMapping("/conversations") public Object list(HttpServletRequest r,@RequestParam Map<String,String> q){paging(q);return ok(store.list(person(r),number(q,"page",1,100000),number(q,"pageSize",20,50)));}
    @GetMapping("/conversations/{id}") public Object detail(HttpServletRequest r,@PathVariable String id,@RequestParam Map<String,String> q){paging(q);return ok(store.detail(person(r),id,number(q,"page",1,100000),number(q,"pageSize",10,30)));}
    @DeleteMapping("/conversations/{id}") public Object delete(HttpServletRequest r,@PathVariable String id){return ok(store.delete(person(r),id));}
    @PostMapping("/chat") public ResponseEntity<?> chat(HttpServletRequest r,@RequestBody Map<String,Object> body){Map<String,Object> result=agent.submit(person(r),body);return ResponseEntity.status(Arrays.asList("queued","running").contains(str(result.get("status")))?HttpStatus.ACCEPTED:HttpStatus.OK).body(ok(result));}
    @GetMapping("/messages/{id}") public Object result(HttpServletRequest r,@PathVariable String id){return ok(store.result(person(r),id));}
    @ExceptionHandler(EhrException.class) public ResponseEntity<?> error(EhrException e){String prefix=e.getMessage().split("[：:]",2)[0];String code=prefix.matches("[A-Z][A-Z0-9_]{2,64}")?prefix:e.status==401?"LOGIN_REQUIRED":e.status==403?"ACCESS_DENIED":e.status==404?"NOT_FOUND":e.status==409?"CONFLICT":e.status==422?"INVALID_REQUEST":e.status==429?"RATE_LIMITED":e.status==503?"AGENT_UNAVAILABLE":"AGENT_ERROR";return ResponseEntity.status(e.status).body(map("code",e.status,"errorCode",code,"msg",e.getMessage()));}
}
