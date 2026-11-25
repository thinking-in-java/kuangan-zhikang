package com.yihe.framework.ehr;
import java.util.*;import javax.servlet.http.*;import org.springframework.web.bind.annotation.*;import static com.yihe.framework.ehr.EhrAccess.*;
@RestController @RequestMapping("/ehr/mobile/questionnaires") public class QuestionnaireMobileController {
 final EhrAccess auth;final QuestionnaireService service;public QuestionnaireMobileController(EhrAccess auth,QuestionnaireService service){this.auth=auth;this.service=service;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 @GetMapping public Object list(HttpServletRequest r){return map("code",0,"data",service.mobileList(auth.mobile(r)));}
 @GetMapping("/{id}") public Object form(HttpServletRequest r,@PathVariable("id")String id){return map("code",0,"data",service.answerForm(auth.mobile(r),id));}
 @PostMapping("/{id}/submit") public Object submit(HttpServletRequest r,@PathVariable("id")String id,@RequestBody Map<String,Object>b){return map("code",0,"data",service.submit(auth.mobile(r),id,b));}
}
