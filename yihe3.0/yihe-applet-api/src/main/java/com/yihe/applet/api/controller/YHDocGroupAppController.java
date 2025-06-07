package com.yihe.applet.api.controller;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.DoctorChatGroupVOJO;
import com.yihe.system.service.YHDocGroupService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 聊天群组
 */
@Controller
@RequestMapping("/api/YHDocGroupAppController")
public class YHDocGroupAppController {
    @Autowired
    YHDocGroupService yHDocGroupService;

    /**
     * 小程序健管师端 查询群组列表
     * @param doctor 医生id
     * @param gname 群组查询名
     * @param pagenum 分页
     * @param pagesize
     * @return 列表
     */
    @RequestMapping(value = "/findGroupInfoByDoctor")
    @ResponseBody
    public Result findGroupInfoByDoctor(String doctor, String gname,int pagenum, int pagesize){
        if(StringUtil.isEmpty(doctor)){
            return Result.error();
        }
        PageInfo<DoctorChatGroupVOJO> groupInfoByDoctor = yHDocGroupService.findGroupInfoByDoctor(doctor, gname, pagenum, pagesize);
        return Result.ok(groupInfoByDoctor.getList());
    }


}
