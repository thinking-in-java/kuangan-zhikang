package com.yihe.applet.api.controller;

import com.yihe.framework.shiro.service.SysPasswordService;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhUserMakeCardService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.service.YhUserSettingService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 健管师端小程序注册管理
 */
@Controller
@RequestMapping("/api/YHDocGroupAppController")
public class YhDoctorRegController {
    @Autowired
    YhUserMakeCardService yhUserMakeCardService;
    @Autowired
    YhUserSettingService yhUserSettingService;
    @Autowired
    YhUserReginfoService yhUserReginfoService;

    /**
     * 健管师修改密码
     * @param YhUserReginfo
     * @return
     */
    @RequestMapping(value = "/updatepwd", method = RequestMethod.POST)
    @ResponseBody
    public int updatepwd(YhUserReginfo YhUserReginfo) {
        if(null==YhUserReginfo||StringUtil.isEmpty(YhUserReginfo.getUserCode())||StringUtil.isEmpty(YhUserReginfo.getUserName())){
            return 0;
        }

        String userPwd = YhUserReginfo.getUserPwd();
        String userName = YhUserReginfo.getUserName();
        SysPasswordService sysPasswordService = new SysPasswordService();
        sysPasswordService.encryptPassword(userName, userPwd);
        String nameAndPwd = new Md5Hash(userName + userPwd).toHex();
        YhUserReginfo.setUserPwd(nameAndPwd);
        YhUserReginfo.setUserCode(YhUserReginfo.getUserCode());
        YhUserReginfo.setChangePwdLastTime(new Date());
        int update = yhUserReginfoService.updateByPrimaryKeySelective(YhUserReginfo);
        return update;

    }

    /**
     * 查询手机号是否存在
     */
    @RequestMapping(value = "/phoneDoesitExist")
    @ResponseBody
    public Result phoneDoesitExist(String phone, Integer userType) {
        if (StringUtil.isEmpty(phone) || null == userType) {
            return Result.error("参数错误");
        }

        YhUserReginfo yhUserReginfo = yhUserReginfoService.selectMobileAndName3(phone, userType);
        if (null != yhUserReginfo) {
            return Result.error("手机号已存在");
        }
        return Result.ok();
    }

    /**
     * 修改用户手机号
     */
    @RequestMapping(value = "/modifyUserPhoneNumber")
    @ResponseBody
    public Result modifyUserPhoneNumber(YhUserReginfo record) {
        if (null == record || StringUtil.isEmpty(record.getMobile()) || StringUtil.isEmpty(record.getUserCode())) {
            return Result.error("参数错误");
        }

        YhUserReginfo yhUserReginfo = yhUserReginfoService.selectMobileAndName3(record.getMobile(), record.getUserType());
        if (null != yhUserReginfo) {
            return Result.error("手机号已存在");
        }


        return Result.ok(yhUserReginfoService.updateByPrimaryKeySelectives(record));
    }

    /***
     * 注册管理列表页
     * @param params
     * @return
     */
    @RequestMapping(value = "/findMakInfoByDoctor")
    @ResponseBody
    public Result findMakInfoByDoctor(@RequestParam Map<String, Object> params,
                                      @RequestParam(defaultValue = "1") int pagenum,
                                      @RequestParam(defaultValue = "10") int pagesize) {

        return Result.ok(yhUserMakeCardService.queryYhUserMakeCardByCriteriaWithPageByDoctorXcx(params, pagenum, pagesize).getList());
    }

    /**
     * 给注册卡生成激活码
     *
     * @param code 注册卡id
     * @return 返回激活码 生成失败返回""空字符串
     */
    @RequestMapping(value = "/generateActivationCode")
    @ResponseBody
    public Result generateActivationCode(String code) {
        return Result.ok(yhUserSettingService.cardIdActivationByDoctor(code));
    }

    /**
     * 锁定账号
     */
    @RequestMapping(value = "/updateLockFlag", method = RequestMethod.POST)
    @ResponseBody
    public int updateuserLockFlag(String userCode, String lockFlag) {
        int status = yhUserReginfoService.updateuserStatus(userCode, lockFlag);
        return status;

    }

    /**
     * 使用注册卡注册
     */
    @RequestMapping(value = "/registrationBycard", method = RequestMethod.POST)
    @ResponseBody
    public Result registrationBycard(YhUserReginfo yhUserReginfo) throws Exception {
        if (null == yhUserReginfo || StringUtil.isEmpty(yhUserReginfo.getUserName()) || StringUtil.isEmpty(yhUserReginfo.getUserPwd())) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (null == yhUserReginfo) {
                map.put("code", 500);
                map.put("msg", "参数异常");
                return Result.ok(map);
            }
        }


        return Result.ok(yhUserMakeCardService.registrationByCard(yhUserReginfo));
    }


}
