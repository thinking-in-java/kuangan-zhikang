package com.yihe.framework.ehr;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrController.ok;
import static com.yihe.framework.ehr.EhrService.*;

/** Employee API for the independently developed miniapp; no client-supplied employee identity. */
@RestController
@RequestMapping("/ehr/miniapp/communication")
public class MiniappCommunicationController {
    final MiniappIdentityService identity;final MiniappAgentStore people;final CommunicationService service;final CommunicationFiles files;
    public MiniappCommunicationController(MiniappIdentityService identity,MiniappAgentStore people,CommunicationService service,CommunicationFiles files){this.identity=identity;this.people=people;this.service=service;this.files=files;}
    EhrActor actor(HttpServletRequest r){Map<String,Object> s=identity.session(r);return people.person(str(s.get("mini_user_id")),identity.dataMode()).actor;}
    @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
    @PostMapping("/conversation") public Object create(HttpServletRequest r,@RequestBody(required=false) Map<String,Object> body){require(body==null||body.isEmpty(),422,"身份由微信绑定确定，请勿传入职工编号");EhrActor a=actor(r);return ok(service.create(a,a.boundRecordId));}
    @GetMapping("/conversations/{cid}/messages") public Object messages(HttpServletRequest r,@PathVariable String cid,@RequestParam(defaultValue="0") long before,@RequestParam(defaultValue="0") long after){return ok(service.messages(actor(r),cid,before,after));}
    @PostMapping("/conversations/{cid}/messages") public Object send(HttpServletRequest r,@PathVariable String cid,@RequestBody Map<String,Object> body){return ok(service.send(actor(r),cid,body));}
    @PostMapping("/conversations/{cid}/read") public Object read(HttpServletRequest r,@PathVariable String cid,@RequestBody Map<String,Object> body){return ok(service.read(actor(r),cid,num(body.get("seq"))));}
    @org.springframework.beans.factory.annotation.Autowired CommunicationHistory history;
    @org.springframework.beans.factory.annotation.Autowired AutoReplyEngine autoReplies;
    @PostMapping("/conversations/{cid}/handoff") public Object handoff(HttpServletRequest r,@PathVariable String cid){return ok(autoReplies.handoff(actor(r),cid));}
    @PostMapping("/conversations/{cid}/rating") public Object rating(HttpServletRequest r,@PathVariable String cid,@RequestBody Map<String,Object> body){EhrActor a=actor(r);Object score=body.get("score");require(score instanceof Number&&((Number)score).doubleValue()==((Number)score).intValue(),422,"评分须为1至5的整数");return ok(history.rate(a,cid,((Number)score).intValue()));}
    @PostMapping("/conversations/{cid}/attachments") public Object upload(HttpServletRequest r,@PathVariable String cid,@RequestParam("file") MultipartFile file)throws IOException{return ok(files.upload(actor(r),cid,file));}
    @GetMapping("/conversations/{cid}/attachments/{fid}") public org.springframework.http.ResponseEntity<org.springframework.core.io.FileSystemResource> download(HttpServletRequest r,@PathVariable String cid,@PathVariable String fid)throws IOException{return CommunicationController.file(files,actor(r),cid,fid);}
    @DeleteMapping("/conversations/{cid}/attachments/{fid}") public Object remove(HttpServletRequest r,@PathVariable String cid,@PathVariable String fid)throws IOException{return ok(files.remove(actor(r),cid,fid));}
}
