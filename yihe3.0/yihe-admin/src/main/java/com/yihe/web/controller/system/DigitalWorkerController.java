package com.yihe.web.controller.system;


import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiresUser
@RequestMapping("/manage/digital-workers")
public class DigitalWorkerController
{
    @GetMapping("/workbench")
    public String workbench()
    {
        return "digital-workers/workbench";
    }

    @GetMapping("/{page:overview|dispatch|records|settings}")
    public String page(@PathVariable String page, Model model, HttpServletResponse response)
    {
        response.setHeader("Cache-Control", "no-store");
        model.addAttribute("workerPage", page);
        return "digital-workers/index";
    }
}
