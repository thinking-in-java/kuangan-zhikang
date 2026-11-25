package com.yihe.web.controller.system;

import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import com.yihe.framework.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiresUser
public class HealthWorkbenchController {
    @GetMapping("/manage/workbench/{page:todos|alerts}")
    public String list(@PathVariable String page, HttpServletResponse response, Model model) {
        response.setHeader("Cache-Control", "no-store");
        model.addAttribute("displayName", ShiroUtils.getLoginName());
        model.addAttribute("workbenchPage", page);
        return "workbench/list";
    }

    @GetMapping("/manage/workbench")
    public String index(HttpServletResponse response, Model model) {
        response.setHeader("Cache-Control", "no-store");
        model.addAttribute("displayName", ShiroUtils.getLoginName());
        return "workbench/index";
    }
}
