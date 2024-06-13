package com.yihe.web.controller.system;


import com.github.pagehelper.PageInfo;
import com.yihe.common.core.controller.BaseController;
import com.yihe.common.core.page.TableDataInfo;
import com.yihe.system.domain.SysLogininfor;
import com.yihe.system.service.ISysLogininforService;
import com.yihe.system.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录日志
 */
@RestController
@RequestMapping("/monitor/logininfor")
public class SysLogininforController  extends BaseController {
    @Autowired
    private ISysLogininforService iSysLogininforService;

    @GetMapping("/list")
    public TableDataInfo findLoginfo(SysLogininfor logininfor) {
        startPage();
        List<SysLogininfor> list = iSysLogininforService.selectLogininforList(logininfor);
        return getDataTable(list);
    }
}