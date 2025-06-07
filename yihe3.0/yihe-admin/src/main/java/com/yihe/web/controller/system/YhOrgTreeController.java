package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.ibm.icu.text.SimpleDateFormat;
import com.yihe.common.config.Global;
import com.yihe.common.core.controller.BaseController;
import com.yihe.common.json.JSONObject.JSONArray;
import com.yihe.common.utils.RestfulDemo;
import com.yihe.common.utils.StringUtils;
import com.yihe.framework.shiro.service.SysPasswordService;
import com.yihe.system.domain.YhOrgTree;
import com.yihe.system.domain.YhOrgTreeVojo;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserRolePower;
import com.yihe.system.mapper.YhDiseaseAssessTotalReport2Mapper;
import com.yihe.system.mapper.YhOrgTreeMapper;
import com.yihe.system.mapper.YhUserReginfoMapper;
import com.yihe.system.mapper.YhUserRolePowerMapper;
import com.yihe.system.service.YhOrgTreeService;
import com.yihe.system.service.YhUserAdminInfoService;
import com.yihe.system.service.YhUserMakeCardService;
import com.yihe.system.service.YhUserSettingService;
import com.yihe.system.utils.MenuTree;
import com.yihe.system.utils.MenuTreeVojo;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.*;

/**
 * YhOrgTreecontroller
 * Created by dong on 2020/3/18.
 */
@Controller
@RequestMapping("/YhOrgTree")
@Api(value = "YhOrgTree控制器", description = "YhOrgTree管理")
public class YhOrgTreeController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhOrgTreeController.class);

    @Autowired
    private YhOrgTreeService YhOrgTreeService;
    @Autowired
    YhUserSettingService yhusersettingservice;
    @Autowired
    YhUserAdminInfoService yhUserAdminInfoService;

    @Autowired
    private YhUserMakeCardService yhUserMakeCardService;

    @Autowired
    private YhUserReginfoMapper yhUserReginfoMapper;

    @Autowired
    private YhUserRolePowerMapper yhUserRolePowerMapper;

    @Autowired
    YhOrgTreeMapper yhOrgTreeMapper;

    @Autowired
    YhDiseaseAssessTotalReport2Mapper yhDiseaseAssessTotalReport2Mapper;//评估报告_基本信息

    @ApiOperation(value = "根据orgcode查询当前机构")
    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.POST)
    @ResponseBody
    public Result selectByPrimaryKey(String orgcode) throws ParseException {
        YhOrgTree info = YhOrgTreeService.selectByPrimaryKeyshowinfo(orgcode);
        if (info.getSuperOrgCode() != null && !info.getSuperOrgCode().equals("0")) {
            YhOrgTree info1 = YhOrgTreeService.selectByPrimaryKeyshowinfo(info.getSuperOrgCode());
            info.setOrgsuperiorName(info1.getOrgName());
            if (info.getAddDate() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(info.getAddDate());
                info.setAddDate1(dateString);
            }
            if (info1.getAddDate() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(info1.getAddDate());
                info.setAddDate2(dateString);
            }

        }

        return Result.ok(info);
    }


    @ApiOperation(value = "查询用户名是否存在")
    @RequestMapping(value = "/selectMobileAndName", method = RequestMethod.POST)
    @ResponseBody
    public Result selectMobileAndName(String userName) {
        YhUserReginfo info = yhUserReginfoMapper.selectMobileAndName(userName);
        return Result.ok(info);
    }


    @ApiOperation(value = "修改结构")
    @RequestMapping(value = "/clientamend", method = RequestMethod.POST)
    @ResponseBody
    public Result clientamend(YhOrgTree tree) {
        int ser = 0;
        if (tree.getUserName() != null && !tree.getUserName().equals("")) {
            Date data = new Date();
            YhUserReginfo info = new YhUserReginfo();
            String userCode = yhUserReginfoMapper.selectMaxUserCode1(1);
            info.setUserCode("OR" + String.valueOf(Integer.parseInt(userCode.substring(2)) + 1));
            info.setUserName(tree.getUserName());
            info.setName(tree.getName());
            info.setOrgCode(tree.getOrgCode());
            String userName = tree.getUserName();
            String userPwd = tree.getPassword();
            SysPasswordService sysPasswordService = new SysPasswordService();
            sysPasswordService.encryptPassword(userName, userPwd);
            String nameAndPwd = new Md5Hash(userName + userPwd).toHex();
            info.setUserPwd(nameAndPwd);
            info.setUserType(1);
            info.setMobile(tree.getNumber());
            info.setGender(1);
            info.setRegTime(data);
            yhUserReginfoMapper.insertSelective(info);

            YhUserRolePower power = new YhUserRolePower();
            power.setRoleid(Integer.valueOf(tree.getModules()));
            power.setUserid("OR" + String.valueOf(Integer.parseInt(userCode.substring(2)) + 1));
            yhUserRolePowerMapper.insertSelective(power);

            tree.setUserCode("OR" + String.valueOf(Integer.parseInt(userCode.substring(2)) + 1));
            if (tree.getAddDate() != null) {
                YhOrgTreeService.setlasttime(tree);
            }
        } else {
            if (tree.getAddDate() != null) {
                YhOrgTreeService.setlasttime(tree);
            }
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject map = new JSONObject();

        JSONObject operationinfo = new JSONObject();
        operationinfo.put("operator", "1");
        map.put("operationinfo",operationinfo);

        JSONObject mainTable = new JSONObject();
        mainTable.put("khmcwb", tree.getOrgName());
        if(StringUtils.isNotEmpty(tree.getName())){
            mainTable.put("khlxr", tree.getName());
        }
        if(StringUtils.isNotEmpty(tree.getNumber())){
            mainTable.put("lxdh", tree.getNumber());
        }
        mainTable.put("gzdw", tree.getOrgCode());
        mainTable.put("khmc", YhOrgTreeService.queryOaTreeId(tree.getOrgCode()));
        map.put("mainTable", mainTable);
        jsonArray.add(map);
        String s = RestfulDemo.doAction("http://" + Global.getOaIPAddress() + "/api/cube/restful/interface/saveOrUpdateModeData/syncCustomer", jsonArray);
        JSONObject jsonObject = JSONObject.parseObject(s);
        if(jsonObject.getInteger("status").equals(1)){
            ser = YhOrgTreeService.updateByPrimaryKeySelective(tree);
            return Result.ok(ser);
        }else{
            return Result.error("保存异常");
        }
    }


    @ApiOperation(value = "增加结构")
    @RequestMapping(value = "/clientadd", method = RequestMethod.POST)
    @ResponseBody
    public Result clientadd(YhOrgTree tree) {
        String code = YhOrgTreeService.selectChildnodeMax(tree.getSuperOrgCode());
        String orgCode = "";
        int ser = 0;
        if (tree.getUserName() != null && !tree.getUserName().equals("")) {
            if (code == null || code.equals("")) {
                orgCode = tree.getSuperOrgCode() + "" + 10000;
            } else {
                orgCode = String.valueOf(Long.valueOf(code) + 1);
            }
            Date data = new Date();
            YhUserReginfo info = new YhUserReginfo();
            String userCode = yhUserReginfoMapper.selectMaxUserCode1(1);
            info.setUserCode("OR" + String.valueOf(Integer.parseInt(userCode.substring(2)) + 1));
            info.setUserName(tree.getUserName());
            info.setName(tree.getName());
            info.setOrgCode(orgCode);
            info.setBirthDate(data);
            String userName = tree.getUserName();
            String userPwd = tree.getPassword();
            SysPasswordService sysPasswordService = new SysPasswordService();
            sysPasswordService.encryptPassword(userName, userPwd);
            String nameAndPwd = new Md5Hash(userName + userPwd).toHex();
            info.setUserPwd(nameAndPwd);
            info.setUserType(1);
            info.setMobile(tree.getNumber());
            info.setGender(1);
            info.setRegTime(data);
            yhUserReginfoMapper.insertSelective(info);

            YhUserRolePower power = new YhUserRolePower();
            power.setRoleid(Integer.valueOf(tree.getModules()));
            power.setUserid("OR" + String.valueOf(Integer.parseInt(userCode.substring(2)) + 1));
            yhUserRolePowerMapper.insertSelective(power);

            tree.setOrgCode(orgCode);
            tree.setUserCode("OR" + String.valueOf(Integer.parseInt(userCode.substring(2)) + 1));
            ser= YhOrgTreeService.insertSelective(tree);
        } else {
            if (code == null || code.equals("")) {
                orgCode = tree.getSuperOrgCode() + "" + 10000;
            } else {
                String newCode = code.substring(code.length()-2);
                String addCode = String.valueOf(Integer.parseInt(newCode)+1);
                String fritsCode;
                if(Integer.parseInt(addCode)>9){
                    fritsCode = code.substring(0, code.length() - 2);
                }else {
                    fritsCode = code.substring(0, code.length() - 1);
                }
                orgCode= fritsCode +addCode;
            }
            tree.setOrgCode(orgCode);
            ser= YhOrgTreeService.insertSelective(tree);
        }
//        JSONArray jsonArray = new JSONArray();
//        JSONObject map = new JSONObject();
//
//        JSONObject operationinfo = new JSONObject();
//        operationinfo.put("operator", "1");
//        map.put("operationinfo",operationinfo);
//
//        JSONObject mainTable = new JSONObject();
//        mainTable.put("khmcwb", tree.getOrgName());
//        if(StringUtils.isNotEmpty(tree.getName())){
//            mainTable.put("khlxr", tree.getName());
//        }
//        if(StringUtils.isNotEmpty(tree.getNumber())){
//            mainTable.put("lxdh", tree.getNumber());
//        }
//        mainTable.put("gzdw", orgCode);
//        map.put("mainTable", mainTable);
//        jsonArray.add(map);
//        System.out.println("http://" + Global.getOaIPAddress() + "/api/cube/restful/interface/saveOrUpdateModeData/syncCustomer"+"-----------");
//        String s = RestfulDemo.doAction("http://" + Global.getOaIPAddress() + "/api/cube/restful/interface/saveOrUpdateModeData/syncCustomer", jsonArray);
//        JSONObject jsonObject = JSONObject.parseObject(s);
//        if(jsonObject.getInteger("status").equals(1)){
//            tree.setOaTreeId(jsonObject.getJSONObject("datajson").getJSONArray("data").getJSONObject(0).getString("billid"));
//            ser = YhOrgTreeService.insertSelective(tree);
//            return Result.ok(ser);
//        }else{
//            return Result.error("保存异常");
//        }
        return Result.ok(ser);
    }


    @ApiOperation(value = "客户信息查找")
    @RequestMapping(value = "/showtissue", method = RequestMethod.POST)
    @ResponseBody
    public Result showtissue(String tree) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
        String username = yh.getUserName();
        String orgcode = yhusersettingservice.selectbyusername(username);


        List<YhOrgTree> upmsPermissions = YhOrgTreeService.selectBYLikeCodeByNew(orgcode);

        MenuTree menuTree = new MenuTree(upmsPermissions);
        upmsPermissions = menuTree.builTree(orgcode);

        JSONArray jsonArray = new JSONArray();

        for (YhOrgTree yhOrgTreeVojo : upmsPermissions) {

            JSONObject jo = new JSONObject();
            jo.put("name", yhOrgTreeVojo.getOrgName());
            jo.put("manage", yhOrgTreeVojo.getName());
            jo.put("asset_num", yhOrgTreeVojo.getAsset_num());
            jo.put("hasnum", yhOrgTreeVojo.getHasnum());
            jo.put("hasdise", yhOrgTreeVojo.getHasdise());
            jo.put("highrisk", yhOrgTreeVojo.getHighrisk());
            jo.put("health", yhOrgTreeVojo.getHealth());
            if (yhOrgTreeVojo.getSuperOrgCode().equals("0")) {
                jo.put("orgCode", "10000");
            } else {
                jo.put("orgCode", yhOrgTreeVojo.getOrgCode());
            }
            if (yhOrgTreeVojo.getChildren().size() != 0) {
                JSONArray jsonArray1 = jsonChildren(yhOrgTreeVojo.getChildren());
                jo.put("children", jsonArray1);
            }
            jsonArray.add(jo);
        }
        String reponseJsonString = "{\"children\":" + jsonArray.toString() + "}";

        return Result.ok(JSONObject.parseObject(reponseJsonString));
    }


    @ApiOperation(value = "客户信息查找")
    @RequestMapping(value = "/showlistTree", method = RequestMethod.POST)
    @ResponseBody
    public Result showlistTree(String tree) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
        String username = yh.getUserName();
        String orgcode = yhusersettingservice.selectbyusername(username);


        List<YhOrgTree> upmsPermissions = YhOrgTreeService.selectBYLikeCode(orgcode);
        upmsPermissions.get(0).setSpread(true);
        MenuTree menuTree = new MenuTree(upmsPermissions);
        upmsPermissions = menuTree.builTree(orgcode);
        return Result.ok(upmsPermissions);
    }

    public JSONArray jsonChildren(List<YhOrgTree> tree) {
        JSONArray jsonArray1 = new JSONArray();
        for (YhOrgTree YhOrgTree : tree) {
            JSONObject jo1 = new JSONObject();
            jo1.put("name", YhOrgTree.getOrgName());
            jo1.put("manage", YhOrgTree.getName());
            jo1.put("asset_num", YhOrgTree.getAsset_num());
            jo1.put("hasnum", YhOrgTree.getHasnum());
            jo1.put("hasdise", YhOrgTree.getHasdise());
            jo1.put("highrisk", YhOrgTree.getHighrisk());
            jo1.put("health", YhOrgTree.getHealth());
            if (YhOrgTree.getSuperOrgCode().equals("0")) {
                jo1.put("orgCode", "10000");
            } else {
                //jo1.put("orgCode",YhOrgTree.getSuperOrgCode());
                jo1.put("orgCode", YhOrgTree.getOrgCode());
            }
            if (YhOrgTree.getChildren().size() != 0) {
                JSONArray jsonArray = jsonChildren(YhOrgTree.getChildren());
                jo1.put("children", jsonArray);
            }
            jsonArray1.add(jo1);
        }
        return jsonArray1;
    }

    @ApiOperation(value = "机构下拉框树结构查询")
    @RequestMapping(value = "/showPullDownTree", method = RequestMethod.GET)
    @ResponseBody
    public List<YhOrgTreeVojo> showpulldown() {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
        String username = yh.getUserName();
        String orgcode = yhusersettingservice.selectbyusername(username);
        List<YhOrgTreeVojo> upmsPermissionst = YhOrgTreeService.selectalltreeOrg(orgcode);
        int i = 0;
        for (YhOrgTreeVojo yhOrgTreeVojo : upmsPermissionst) {
            i++;
            if (i == 1) {
                yhOrgTreeVojo.setOpen(true);
            } else {
                yhOrgTreeVojo.setOpen(false);
            }
        }
        MenuTreeVojo menuTree1 = new MenuTreeVojo(upmsPermissionst);
        upmsPermissionst = menuTree1.builTree(orgcode);
        return upmsPermissionst;
    }

}