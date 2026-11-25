package com.yihe.web.controller.system;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequiresUser
@RequestMapping("/manage/employees")
public class EmployeeCenterController {
    @GetMapping("/{page:list|groups|tags|import|bindings|organization}")
    public String page(@PathVariable String page,Model model,HttpServletResponse response){response.setHeader("Cache-Control","no-store");model.addAttribute("employeePage",page);return "employee-center/index";}
}
