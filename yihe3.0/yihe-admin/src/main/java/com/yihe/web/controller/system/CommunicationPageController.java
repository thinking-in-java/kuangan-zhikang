package com.yihe.web.controller.system;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
@RequiresUser
public class CommunicationPageController {
    @GetMapping("/manage/communication/conversations") public String conversations(){return "communication/conversations";}
    @GetMapping("/manage/communication/history") public String history(){return "communication/history";}
    @GetMapping("/manage/communication/auto-replies") public String autoReplies(){return "communication/auto-replies";}
    @GetMapping("/manage/communication/knowledge") public String knowledge(){return "communication/knowledge";}
    @GetMapping("/manage/communication/analytics") public String analytics(){return "communication/analytics";}
}
