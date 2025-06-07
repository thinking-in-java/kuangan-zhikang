package com.yihe.applet.api.controller;

import com.yihe.common.core.domain.AjaxResult;
import com.yihe.system.service.YhAssessIntroduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/YhAssessIntroduceApi")
public class YhAssessIntroduceApiController {
    @Autowired
    private YhAssessIntroduceService yhAssessIntroduceService;

    @RequestMapping("/getByIntroduceName")
    public AjaxResult getByIntroduceName(String name){
        return AjaxResult.success(yhAssessIntroduceService.getByIntroduceName(name));
    }
}
