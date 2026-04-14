/**
 *
 */
package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ibm.icu.util.Calendar;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.yihe.common.config.Global;
import com.yihe.common.json.JSONObject.JSONArray;
import com.yihe.common.utils.DateUtils;
import com.yihe.common.utils.RestfulDemo;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.*;
import com.yihe.system.service.*;
import com.yihe.system.utils.JSONModelConverter;
import com.yihe.system.utils.MD5Util;
import com.yihe.system.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 一体机上传
 * @author dong
 *
 */
@Controller
public class SonkaMachineController {


    @Autowired
    protected YhUserDeviceMapper yhUserDeviceMapper;

    @Autowired
    protected YhUserReginfoMapper yhUserReginfoMapper;

    @Autowired
    protected YhUserMakeCardMapper yhUserMakeCardMapper;

    @Autowired
    protected YhUserAdminInfoMapper yhUserAdminInfoMapper;

    @Autowired
    protected YhUserRoleService yhUserRoleService;

    @Autowired
    protected YhUserRolePowerService yhUserRolePowerService;

    @Autowired
    protected YHUserUbaMapper yHUserUbaMapper;

    @Autowired
    protected YHUserTemperatureMapper yHUserTemperatureMapper;

    @Autowired
    protected YHUserOxygenMapper yHUserOxygenMapper;

    @Autowired
    protected YHUserBmiMapper yHUserBmiMapper;

    @Autowired
    protected YHUserEcgMapper yHUserEcgMapper;

    @Autowired
    protected YhUserWaistlineMapper yhUserWaistlineMapper;

    @Autowired
    protected YHUserBloodsugerMapper yHUserBloodsugerMapper;

    @Autowired
    protected YHUserBloodfatMapper yHUserBloodfatMapper;

    @Autowired
    protected YHUserBloodPressureMapper yHUserBloodPressureMapper;

    @Autowired
    protected YHUserRenalfunctionMapper yHUserRenalfunctionMapper;

    @Autowired
    protected YHUserBodyFatMapper yHUserBodyFatMapper;

    @Autowired
    private YhSysConfigService yhSysConfigService;

    @Autowired
    YhTaskServiceAPIService yhtaskservuceapiservice;

    @Autowired
    YhUserEnrolinfoMapper yhUserEnrolinfoMapper;

    @Autowired
    YhUserPersonageConditionMapper yhUserPersonageConditionMapper;


    @Autowired
    YhOrgTreeMapper yhOrgTreeMapper;

    @Autowired
    YHUserHeartRateMapper yHUserHeartRateMapper;


    @Autowired
    YhHeThresholdService yhHeThresholdService;

    @Autowired
    YhOrgFacilityMapper yhOrgFacilityMapper;

    @Autowired
    private YhOrgTreeService YhOrgTreeService;


    @Autowired
    YhUserArteryMapper YhUserArteryMapper;

    @Autowired
    private RedisTemplate<String, String> stringTemplate;


    /**
     * 获取医生信息接口
     */
    @RequestMapping(value = "/getShowDoctor", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getShowDoctor(HttpServletRequest request, HttpServletResponse response, String orgCode) {
        String reponseJsonString = "";
        if (orgCode == null || orgCode.equals("")) {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"机构id不能为空\",\"errorCode\":\"0001\"}";
        } else {
            List<YhUserReginfo> list = yhUserReginfoMapper.showOrgCodedoctorFacility(orgCode);//查询当前机构下的所有医生
            List<YhUserAdminInfo> list2 = yhUserReginfoMapper.showOrgCodeDoctorById(orgCode); //所有在机构下留有注册卡归属记录的医生

            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < list.size(); i++) {
                JSONObject jo1 = new JSONObject();
                YhUserReginfo yhUserReginfo = list.get(i);
                YhUserAdminInfo info = yhUserAdminInfoMapper.selectByPrimaryKeyByusercode(yhUserReginfo.getUserCode());//根据医生code查询ading中的id
                if (info == null) {//0张注册卡
                    jo1.put("name", yhUserReginfo.getName() + "(0)");
                    jo1.put("userCode", yhUserReginfo.getUserCode());
                    jo1.put("userName", yhUserReginfo.getUserName());
                    jo1.put("orgCode", yhUserReginfo.getOrgCode());
                } else {
                    int s = yhUserMakeCardMapper.showCountDoctor(String.valueOf(info.getId()),orgCode);
                    int i1 = yhUserAdminInfoMapper.selectRegisterByDoctor(info.getId());
                    if((s==0) && (i1==0)) {
                        jo1.put("name", yhUserReginfo.getName() + "(" + s + ")");
                        jo1.put("userCode", yhUserReginfo.getUserCode());
                        jo1.put("userName", yhUserReginfo.getUserName());
                        jo1.put("orgCode", yhUserReginfo.getOrgCode());
                    }else {
                        list.remove(yhUserReginfo);
                    }
                }
                if (!(jo1.isEmpty())){
                    jsonArray.add(jo1);
                }
            }

            for (YhUserAdminInfo yhUserAdminInfo1 : list2) {
                JSONObject jo2 = new JSONObject();
                jo2.put("userCode", yhUserAdminInfo1.getUserCode());
                YhUserAdminInfo info = yhUserAdminInfoMapper.selectByPrimaryKeyByusercode(yhUserAdminInfo1.getUserCode());//根据医生code查询ading中的id

                if (info == null) {//0张注册卡
                    jo2.put("name", yhUserAdminInfo1.getName() + "(0)");
                } else {
                    int s = yhUserMakeCardMapper.showCountDoctor(String.valueOf(info.getId()),orgCode);
                    jo2.put("name", yhUserAdminInfo1.getName() + "(" +s + ")");
                }
                jo2.put("userName", yhUserAdminInfo1.getName());
                jo2.put("orgCode", yhUserAdminInfo1.getOrgCode());
                jsonArray.add(jo2);
            }

            reponseJsonString = "{\"success\":\"true\",\"message\":\"获取医生信息成功\",\"listDoctor\":" + jsonArray.toString() + "}";
        }
        return reponseJsonString;

        //以下代码仅供版本回退使用
//        String reponseJsonString = "";
//        if (orgCode == null || orgCode.equals("")) {
//            reponseJsonString = "{\"success\":\"false\",\"message\":\"机构id不能为空\",\"errorCode\":\"0001\"}";
//        } else {
//            List<YhUserReginfo> list = yhUserReginfoMapper.showOrgCodedoctorFacility(orgCode);//查询当前机构下的所有医生
//            //获取此机构的父机构下所有医生
//            String orgName = "";
//            String orgName1 = getRootNode(orgCode, orgName);
//            String[] strArr = orgName1.split(",");
//            for (int i = 0; i < strArr.length; i++) {
//                List<YhUserReginfo> list1 = yhUserReginfoMapper.showOrgCodedoctorFacility(strArr[i]);//查询当前机构下的所有医生
//                for (YhUserReginfo yhUserReginfo : list1) {
//                    list.add(yhUserReginfo);
//                }
//            }
//
//            JSONArray jsonArray = new JSONArray();
//            /*
//             * JSONObject jo = new JSONObject(); jo.put("userCode", null); jo.put("name",
//             * "无"); jo.put("userName", "无"); jo.put("orgCode", null); jsonArray.add(jo);
//             */
//
//            for (YhUserReginfo yhUserReginfo : list) {
//                JSONObject jo1 = new JSONObject();
//                jo1.put("userCode", yhUserReginfo.getUserCode());
//                YhUserAdminInfo info = yhUserAdminInfoMapper.selectByPrimaryKeyByusercode(yhUserReginfo.getUserCode());//根据医生code查询ading中的id
//                if (info == null) {//0张注册卡
//                    jo1.put("name", yhUserReginfo.getName() + "(0)");
//                } else {
//                    int s = yhUserMakeCardMapper.showCountDoctor(String.valueOf(info.getId()));
//                    jo1.put("name", yhUserReginfo.getName() + "(" + s + ")");
//                }
//
//                jo1.put("userName", yhUserReginfo.getUserName());
//                jo1.put("orgCode", yhUserReginfo.getOrgCode());
//                jsonArray.add(jo1);
//            }
//
//
//            reponseJsonString = "{\"success\":\"true\",\"message\":\"获取医生信息成功\",\"listDoctor\":" + jsonArray.toString() + "}";
//        }
//        return reponseJsonString;
    }


    /**
     * 获取参数机构所有上级机构的编号，直到总机构，每个机构编号逗号隔开
     * @param orgCode 机构编号
     * @param orguser 父级机构编号
     * @return String 参数机构所有上级机构的编号
     */
    private String getRootNode(String orgCode, String orguser) {
        //查询机构信息
        YhOrgTree org = yhOrgTreeMapper.selectByPrimaryKey(orgCode);
        if (!org.getSuperOrgCode().equals("0")) {
            //如果不是总机构
            String name = orguser + org.getSuperOrgCode() + ",";
            return getRootNode(org.getSuperOrgCode(), name);
        }
        return orguser;
    }


    /**
     * 获取机构信息接口
     */
    @RequestMapping(value = "/getShowOrgCode", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String showOrgCodeBydeviceid(HttpServletRequest request, HttpServletResponse response, String deviceId) {
        String reponseJsonString = "";
        if (deviceId == null || deviceId.equals("")) {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"设备id不能为空\",\"errorCode\":\"0001\"}";
        } else {
            //查询设备id绑定的机构
            YhOrgFacility org = yhOrgFacilityMapper.showOrgCodeBydeviceid(deviceId);
            String orgcode = "10000";//默认值
            if (org != null) {
                orgcode = org.getOrgCode();
            }
            //查询这个机构以及此机构的所有子机构信息
            List<YhOrgTreeVojo> upmsPermissionst = YhOrgTreeService.selectalltreeOrg(orgcode);
            JSONArray jsonArray = new JSONArray();
            for (YhOrgTreeVojo yhOrgTreeVojo : upmsPermissionst) {
                JSONObject jo = new JSONObject();
                jo.put("id", yhOrgTreeVojo.getId());
                jo.put("name", yhOrgTreeVojo.getName());
                jo.put("superOrgCode", yhOrgTreeVojo.getSuperOrgCode());
                jo.put("children", yhOrgTreeVojo.getChildren());
                jo.put("open", yhOrgTreeVojo.getOpen());
                jo.put("value", yhOrgTreeVojo.getValue());
                jsonArray.add(jo);
            }
            reponseJsonString = "{\"success\":\"true\",\"message\":\"获取机构信息成功\",\"YhOrg\":" + jsonArray.toString() + "}";
        }
        return reponseJsonString;
    }


    /**
     * 获取用户信息接口
     */
    @RequestMapping(value = "/getUserInfo", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getUserInfo(HttpServletRequest request, HttpServletResponse response, String mobile, String idCard) {
        String reponseJsonString = "";
        String deviceId = request.getHeader("deviceId");//设备id
        String timestamp = request.getHeader("timestamp");//时间
        String sign = request.getHeader("sign");//密钥
        String ver = request.getHeader("ver");//版本号

        StringBuffer sb = new StringBuffer();
        sb.append("deviceId=").append(deviceId).append("&");
        sb.append("timestamp=").append(timestamp).append("&");
        sb.append("ver=").append(ver);
        String ss = MD5Util.byteArrayToHexString(sb.toString().getBytes());

        if (deviceId == null) {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"设备id不能为空\",\"errorCode\":\"0001\"}";
        } else if (timestamp == null) {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"时间不能为空\",\"errorCode\":\"0002\"}";
        } else if (sign == null) {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"密钥不能为空\",\"errorCode\":\"0003\"}";
        } else if (ver == null) {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"版本号不能为空\",\"errorCode\":\"0004\"}";
        } else if ((mobile == null || mobile.equals("")) && (idCard == null || idCard.equals(""))) {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"身份证与手机号不能同时为空\",\"errorCode\":\"0007\"}";
        } else if ((ss).equals(sign)) {
            //要修改为登录账号查询用户信息
            YhUserReginfo wellRegUserinfo = null;
            if (mobile != null && !mobile.equals("")) {
                YhUserReginfo yhUserReginfo = yhUserReginfoMapper.showmobileByUserName(mobile);
                if (null != yhUserReginfo) {
                    wellRegUserinfo = yhUserReginfo;
                } else {
                    YhUserReginfo wellRegUserinfo1 = yhUserReginfoMapper.showmobile(mobile);
                    if (null != wellRegUserinfo1) {
                        wellRegUserinfo = wellRegUserinfo1;
                    }
                }
            } else {
                //通过身份证号查询用户信息
                YhUserReginfo yhUserReginfo = yhUserReginfoMapper.showCardId(idCard);
                if (null != yhUserReginfo) {
                    wellRegUserinfo = yhUserReginfo;
                }
            }

            if (wellRegUserinfo == null) {
                reponseJsonString = "{\"success\":\"false\",\"message\":\"该账号未注册\",\"errorCode\":\"0006\"}";
            } else if (wellRegUserinfo.getUserType() == 5) {
                int gender;
                if (wellRegUserinfo.getGender() == 2) {
                    gender = 0;
                } else {
                    gender = 1;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(wellRegUserinfo.getBirthDate());
                reponseJsonString = "{\"success\":\"true\",\"message\":\"获取用户信息成功\",\"username\":\"" + wellRegUserinfo.getUserName() + "\", \"name\":\"" + wellRegUserinfo.getName() + "\",\"birthDate\":\"" + dateString + "\",\"gender\":\"" + gender + "\",\"mobile\":\"" + wellRegUserinfo.getMobile() + "\"}";

            } else {
                if (wellRegUserinfo.getUserType() == 4) {
                    reponseJsonString = "{\"success\":\"false\",\"message\":\"登录的是健管师账号，数据上传后无法查看\",\"errorCode\":\"00010\"}";
                } else {
                    reponseJsonString = "{\"success\":\"false\",\"message\":\"登录的是管理员账号，数据上传后无法查看\",\"errorCode\":\"00010\"}";
                }
            }
        } else {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"密钥不正确\",\"errorCode\":\"0005\"}";
        }
        return reponseJsonString;

    }

    /**
     * 注册接口
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/register", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String register(HttpServletRequest request, HttpServletResponse response, WellRegUserinfoVoJo uservojo)
            throws ParseException, UnsupportedEncodingException {
        String reponseJsonString = "";
        YhUserReginfo welluser = new YhUserReginfo();
        HttpSession session = request.getSession();
        String deviceId = request.getHeader("deviceId");//设备id
        if (uservojo.getName() == null || uservojo.getName().equals("")) {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"用户名不能为空\",\"errorCode\":\"0003\"}";
        } else if (uservojo.getBirthDate() == null || uservojo.getBirthDate().equals("")) {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"出生年月不能为空\",\"errorCode\":\"0004\"}";
        } else if (uservojo.getMobile() == null || uservojo.getMobile().equals("")) {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"手机号码不能为空\",\"errorCode\":\"0005\"}";
        } else if (uservojo.getGender() == null || uservojo.getGender().equals("")) {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"性别不能为空\",\"errorCode\":\"0006\"}";
        } else if (uservojo.getOrgCode() == null || uservojo.getOrgCode().equals("")) {
            reponseJsonString = "{\"success\":\"false\",\"message\":\"机构id不能为空\",\"errorCode\":\"0006\"}";
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            if (uservojo.getJobno() == null || uservojo.getJobno().equals("")) {
                map.put("username", uservojo.getName());
            } else {
                map.put("username", uservojo.getJobno());
            }

            map.put("birthDate", uservojo.getBirthDate());
            YhUserReginfo count = yhUserReginfoMapper.inquire(map);
            if (count != null) {
                reponseJsonString = "{\"success\":\"true\",\"message\":\"注册成功\",\"errorCode\":\"0000\"}";
                if (!count.getMobile().equals(uservojo.getMobile())) {
                    int num = yhUserReginfoMapper.selectphone(uservojo.getMobile());
                    if (num > 0) {
                        reponseJsonString = "{\"success\":\"false\",\"message\":\"手机号码已存在\",\"errorCode\":\"0001\"}";
                    } else {
                        welluser.setUserCode(count.getUserCode());// usercode
                        welluser.setMobile(uservojo.getMobile());
                        int c = yhUserReginfoMapper.updateByUserCodeNew(welluser);
                    }
                }
            } else {
                int num = yhUserReginfoMapper.selectphone(uservojo.getMobile());
                if (num > 0) {
                    reponseJsonString = "{\"success\":\"false\",\"message\":\"手机号码已存在\",\"errorCode\":\"0001\"}";
                } else {
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    //如果是八局用户
                    if (uservojo.getJobno() != null && !uservojo.getJobno().equals("")) {
                        map1.put("username", uservojo.getJobno());
                        int count1 = yhUserReginfoMapper.showchushen(map1);
                        if (count1 > 0) {
                            reponseJsonString = "{\"success\":\"false\",\"message\":\"用户名已存在\",\"errorCode\":\"0002\"}";
                            return reponseJsonString;
                        }
                    } else {
                        Map<String, Object> m = new HashMap<String, Object>();
                        m.put("username", uservojo.getMobile());
                        int count1 = yhUserReginfoMapper.showchushen(m);
                        if (count1 > 0) {
                            reponseJsonString = "{\"success\":\"false\",\"message\":\"手机号码已经被使用\",\"errorCode\":\"0002\"}";
                            return reponseJsonString;
                        }
                    }

                    String UserCode = yhUserReginfoMapper.selectuser();// 取最大的usercode

                    String CardId = "";//注册卡号
                    if (uservojo.getDoctorid() != null) {//如果选择了医生
                        YhUserAdminInfo info = yhUserAdminInfoMapper.selectByPrimaryKeyByusercode(uservojo.getDoctorid());//根据医生code查询ading中的id
                        if (info == null) {
                            YhUserMakeCard OrgCardId = yhUserMakeCardMapper.showByOrgCardId(uservojo.getOrgCode());//查询该机构下没有分配医生的未用注册卡
                            if (OrgCardId != null) {//如果当前机构下没有分配医生的未用注册卡
                                CardId = OrgCardId.getEnrolCardId();
                            } else {
                                YhUserMakeCard makecard = yhUserMakeCardMapper.showByOrgIdMakeCar(uservojo.getOrgCode());//查询当前机构下是否还有注册卡
                                if (makecard == null) {
                                    reponseJsonString = "{\"success\":\"false\",\"message\":\"注册卡已用完\",\"errorCode\":\"0008\"}";
                                    return reponseJsonString;
                                } else {
                                    CardId = makecard.getEnrolCardId();//取出该机构已分配医生的注册卡
                                    makecard.setDoctor(info.getId());
                                    yhUserMakeCardMapper.updateByPrimaryKeySelective(makecard);//把该注册卡修改成当前医生
                                }
                            }
                        } else {
                            String DoctorCardId = yhUserMakeCardMapper.showByCardId(String.valueOf(info.getId()));//查询当前医生下未使用注册卡
                            if (DoctorCardId != null) {//如果当前医生还有剩余未用注册卡
                                CardId = DoctorCardId;
                            } else {//当前医生没有空余注册卡
                                YhUserMakeCard OrgCardId = yhUserMakeCardMapper.showByOrgCardId(uservojo.getOrgCode());//查询该机构下没有分配医生的未用注册卡
                                if (OrgCardId != null) {//如果当前机构下没有分配医生的未用注册卡
                                    CardId = OrgCardId.getEnrolCardId();
                                } else {
                                    YhUserMakeCard makecard = yhUserMakeCardMapper.showByOrgIdMakeCar(uservojo.getOrgCode());//查询当前机构下是否还有注册卡
                                    if (makecard == null) {
                                        reponseJsonString = "{\"success\":\"false\",\"message\":\"注册卡已用完\",\"errorCode\":\"0008\"}";
                                        return reponseJsonString;
                                    } else {
                                        CardId = makecard.getEnrolCardId();//取出该机构已分配医生的注册卡
                                        makecard.setDoctor(info.getId());
                                        yhUserMakeCardMapper.updateByPrimaryKeySelective(makecard);//把该注册卡修改成当前医生
                                    }
                                }
                            }
                        }
                    } else {//没有选择医生
                        String orgDoctorCardid = yhUserMakeCardMapper.selctByOrgDoctorCardId(uservojo.getOrgCode());//查询当前机构下有健康师的注册卡
                        if (orgDoctorCardid != null) {
                            CardId = orgDoctorCardid;
                        } else {
                            YhUserMakeCard OrgCardId = yhUserMakeCardMapper.showByOrgCardId(uservojo.getOrgCode());//查询该机构下没有分配医生的未用注册卡
                            if (OrgCardId == null) {//如果该机构下还是没有分配医生的未用注册卡
                                reponseJsonString = "{\"success\":\"false\",\"message\":\"注册卡已用完\",\"errorCode\":\"0008\"}";
                                return reponseJsonString;
                            } else {
                                CardId = OrgCardId.getEnrolCardId();//取出该机构已分配医生的注册卡
                                OrgCardId.setDoctor(1);//系统默认医生id
                                yhUserMakeCardMapper.updateByPrimaryKeySelective(OrgCardId);//把该注册卡修改成系统默认医生
                            }
                        }
                    }


                    int userid = Integer.valueOf(UserCode) + 1;// userCode加上r

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date data = sdf.parse(uservojo.getBirthDate());

                    YhUserMakeCard card = yhUserMakeCardMapper.selectByPrimaryKey(CardId);
                    if (card.getDoctor() == null) {
                        YhUserAdminInfo admin = yhUserAdminInfoMapper.showid();
                        card.setDoctor(admin.getId());
                    }
                    YhOrgTree tree = yhOrgTreeMapper.selectByPrimaryKey(card.getOrgId());//当前机构
                    if (tree != null) {
                        if (tree.getAddDate() != null) {
                            card.setEndTime(tree.getAddDate());
                        }
                    }
                    card.setEnrolCardId(CardId);
                    card.setUserCode(String.valueOf(userid));// usercode
                    card.setBeginTime(df.parse(df.format(new Date())));// 去当前时间
                    //card.setOrgId("10000");// 单位编码
                    yhUserMakeCardMapper.updateByPrimaryKeySelective(card);// 更新


                    welluser.setUserCode(String.valueOf(userid));// usercode
                    welluser.setUserType(5);// 3，机构，4，医生，5，用户
                    if (uservojo.getJobno() == null || uservojo.getJobno().equals("")) {
                        welluser.setUserName(uservojo.getMobile());// 登录用户名
                        String nameAndPwd = new Md5Hash(uservojo.getMobile() + "12345678").toHex();
                        welluser.setUserPwd(nameAndPwd);
                    } else {
                        welluser.setUserName(uservojo.getJobno());// 登录用户名
                        String nameAndPwd = new Md5Hash(uservojo.getJobno() + "12345678").toHex();
                        welluser.setUserPwd(nameAndPwd);
                    }

                    int gender;
                    if (uservojo.getGender() == 0) {
                        gender = 2;
                        welluser.setPostCode("https://qny.guyijk.com/xiaochengxu/toxiangnv.png");
                    } else {
                        gender = 1;
                        welluser.setPostCode("https://qny.guyijk.com/xiaochengxu/toxiangnan.png");
                    }
                    welluser.setGender(gender);// 性别
                    welluser.setBirthDate(data);// 出生日期
                    // welluser.setEmail(email);//邮箱
                    welluser.setProvinceId("0");// 默认值s
                    welluser.setCityId("0");// 默认值
                    welluser.setAreaId("0");// 默认值
                    welluser.setRegTime(df.parse(df.format(new Date())));// 取当前时间
                    welluser.setLoginTimes(0);
                    if (uservojo.getName() == null || uservojo.getName().equals("")) {
                        welluser.setName(uservojo.getJobno());// 真实姓名
                    } else {
                        welluser.setName(uservojo.getName());// 真实姓名
                    }
                    welluser.setAddress("");// 地址
                    welluser.setMobile(uservojo.getMobile());// 电话号码
                    welluser.setLockFlag(2);
                    welluser.setRegSource(5);
                    welluser.setCertType(0);
                    welluser.setOrgCode(card.getOrgId());// 单位编码
                    welluser.setEnrolCardId(CardId);
                    welluser.setCardidentity(uservojo.getIdCard());
                    yhUserReginfoMapper.insertSelective(welluser);


                    //往用户信息扩展表插入一条数据 userCode Evaluateflag
                    YhUserEnrolinfo enrolinfo = yhUserEnrolinfoMapper.selectByPrimaryKeyByUsercode(String.valueOf(userid));
                    if (enrolinfo == null) {
                        YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
                        yhUserEnrolinfo.setUserCode(String.valueOf(userid));
                        yhUserEnrolinfo.setEvaluateflag(0);
                        yhUserEnrolinfoMapper.insertSelective(yhUserEnrolinfo);
                    }

                    //查询角色表
                    Integer roleId = yhUserRoleService.selectRoleId("用户");
                    //用户角色关联表插入id和code
                    YhUserRolePower yhUserRolePower = new YhUserRolePower();
                    yhUserRolePower.setRoleid(roleId);
                    yhUserRolePower.setUserid(String.valueOf(userid));
                    yhUserRolePowerService.inssert(yhUserRolePower);

                    JSONArray jsonArray = new JSONArray();
                    JSONObject postData = new JSONObject();

                    JSONObject operationinfo = new JSONObject();
                    operationinfo.put("operator", "1");
                    postData.put("operationinfo", operationinfo);

                    JSONObject mainTable = new JSONObject();
                    mainTable.put("xm", welluser.getName());
                    mainTable.put("xb", welluser.getGender() - 1);
                    mainTable.put("csrq", DateUtils.dateTime(welluser.getBirthDate()));
                    mainTable.put("brdh", welluser.getMobile());
                    mainTable.put("sfzh", welluser.getCardidentity());
                    //查询客户绑定id
                    String oaTreeId = yhOrgTreeMapper.queryOaTreeId2(welluser.getEnrolCardId());
                    if (null != oaTreeId && !"".equals(oaTreeId)) {
                        mainTable.put("gzdw", oaTreeId);
                    }
                    postData.put("mainTable", mainTable);
                    jsonArray.add(postData);
                    String s = RestfulDemo.doAction("http://" + Global.getOaIPAddress() + "/api/cube/restful/interface/saveOrUpdateModeData/sysncPersionInfo", jsonArray);
                    reponseJsonString = "{\"success\":\"true\",\"message\":\"注册成功\",\"errorCode\":\"0000\"}";
                }

            }

        }
        return reponseJsonString;
    }

    /****
     * 获取一体机数据
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getBodyCheckData", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String HandleGetDatasFromDevice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long beginTime = System.currentTimeMillis();
        // 获取request传输过来的字符流
        BufferedReader bf;
        String jsonString = "{\"DoctorId\":\"\",\"DoctorName\":\"\",\"Vision\":null,\"MachineId\":\"SKT818010007\",\"UnitNo\":\"\",\"UnitName\":\"\",\"MacAddr\":\"\",\"RecordNo\":\"1801000720190221084246\",\"MeasureTime\":\"2019-02-21 08:42:48\",\"DeviceType\":\"SK-T8\",\"LoginType\":\"1\",\"Member\":{\"Name\":\"成运良\",\"Mobile\":\"13307329303\",\"IdCode\":\"43030419680915213X\",\"Age\":\"50\",\"Sex\":\"1\",\"Address\":\"\",\"Birthday\":\"1968-09-15\",\"UserIcon\":null,\"Nation\":\"\",\"StartDate\":\"1900/1/1 0:00:00\",\"EndDate\":\"1900/1/1 0:00:00\",\"Department\":\"\",\"BarCode\":\"\",\"IcCode\":\"\",\"SocialCode\":\"\",\"UserID\":\"147\",\"SchoolName\":\"\",\"ClassName\":\"\",\"ClassNO\":\"\",\"IconAddress\":\"\"},\"Height\":{\"Height\":\"181\",\"Weight\":\"95.7\",\"BMI\":\"29.2\",\"IdealWeight\":\"73.7\",\"Result\":\"2\"},\"Fat\":null,\"BloodPressure\":null,\"Bo\":null,\"Ecg\":null,\"PEEcg\":null,\"Temperature\":null,\"Whr\":null,\"BloodSugar\":null,\"Ua\":null,\"Chol\":null,\"BloodFat\":{\"TChol\":\"4.39\",\"HdlChol\":\"0.78\",\"Trig\":\"3.85\",\"CalcLdl\":\"1.84\",\"Result\":\"0\"},\"Cardiovascular\":null,\"BMD\":null,\"Alcohol\":null,\"Hb\":null,\"Lung\":null,\"MicAetery\":null,\"Artery\":null,\"ArteryAve\":null,\"LungFunction\":null,\"Urinalysis\":null}";
        try {
            request.setCharacterEncoding("UTF-8");
            bf = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            // 循环读取字符流所有字符
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            jsonString = sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String reponseJsonString = "{\"success\":\"true\",\"message\":\"上传成功\"}";
        System.out.println(jsonString);
        JSONObject json = JSONObject.parseObject(jsonString);

        /*
         * if(json!=null){ JSONObject memeberObj =
         * json.getJSONObject("Member");//读取json中的数据
         *
         * String mobile = (String) memeberObj.get("Mobile");// 电话 String name =
         * (String) memeberObj.get("Name");// 姓名 String idCardId = (String)
         * memeberObj.get("IdCode");// 身份证号 String sex = (String)
         * memeberObj.get("sex");// 身份证号
         *
         * int dh=wellRegUserinfoService.selectphone(mobile); if(dh!=0){ int
         * xm=wellRegUserinfoService.selectphone(name);
         *
         * //WellRegUserinfo userinfo=new WellRegUserinfo();
         * //wellRegUserinfoService.save(userinfo); } }
         */
        String deviceId = "一体机:" + json.getString("MachineId");
        String userCode = getUserCode(json);
        if (!StringUtils.isEmpty(userCode)) {
            ArteryAve(userCode, deviceId, json);
            Urinalysis(userCode, deviceId, json);
            addPEecg(userCode, deviceId, json, request);
            addBloodPressure(userCode, deviceId, json);
            addBloodSugar(userCode, deviceId, json, request);
            addHeightAndWeight(userCode, deviceId, json);
            addOxygen(userCode, deviceId, json);
            addBodyFat(userCode, deviceId, json);
            addTemperature(userCode, deviceId, json, request);

            ReviceDataFromPlatemp temp = convertToMyData(userCode, deviceId, json, request);
            // 填写必填10项
            reponseJsonString = healthQuestionRedisData(temp, userCode);

        }
        // 执行时长(毫秒)
        long responseTime = System.currentTimeMillis() - beginTime;
        return reponseJsonString;
    }


    public String healthQuestionRedisData(ReviceDataFromPlatemp reviceDataFromPlatemp, String userCode)
            throws JsonProcessingException {

        String reponseJsonString = "{\"success\":\"true\",\"message\":\"上传成功\"}";
        List<NewRiskReportContente> conList = new ArrayList<>();
        List<ConList> conList2 = reviceDataFromPlatemp.getConlist();
        for (int i = 0; i < conList2.size(); i++) {
            if (conList2.get(i).getConKey() != null) {
                NewRiskReportContente con = new NewRiskReportContente();
                con.setConKey(conList2.get(i).getConKey());
                con.setConValue(conList2.get(i).getConValue());
                con.setPos(i);
                conList.add(con);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String modelToJSONString = mapper.writeValueAsString(conList);
        ValueOperations<String, String> valueTemplate = stringTemplate.opsForValue();
        valueTemplate.set("physical" + userCode, modelToJSONString, 604800, TimeUnit.SECONDS);
        //String si = super.redis.set("physical" + userCode, modelToJSONString);
        //redisSave(reviceDataFromPlatemp, userCode);
        return reponseJsonString;
    }


    /***
     * 将必填的10项
     *
     * @param userCode
     *
     * @param json
     * @throws Exception
     */
    private ReviceDataFromPlatemp convertToMyData(String userCode, String deviceId, JSONObject json, HttpServletRequest request) throws Exception {
        ReviceDataFromPlatemp tmp = new ReviceDataFromPlatemp();
        // 我要获取当前的日期
        Date date = new Date();
        // 设置要获取到什么样的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        // 获取String类型的时间
        String createdate = sdf.format(date);
        JSONObject memeberObj = json.getJSONObject("Member");
        if (memeberObj != null) {
            String mobile = (String) memeberObj.get("Mobile");// 电话
            String name = (String) memeberObj.get("Name");// 姓名
            String idCardId = (String) memeberObj.get("IdCode");// 身份证号
            //Integer sex = memeberObj.getInteger("sex");// 身份证号
            tmp.setPhone(mobile);
            //tmp.setSex(sex.toString());
            tmp.setUserName(name);
        }

        JSONObject minFat = (JSONObject) json.get("Height"); // 人体成分
        String height = "";
        String weight = "";
        if (minFat != null) {
            // 通过用户id查找E_User_Id
            height = (String) minFat.get("Height");// 身高
            weight = (String) minFat.get("Weight");// 体重

            int i = yhtaskservuceapiservice.selectbmicount(userCode);
            if (i == 0) {
                YhJzServiceBodydataSwitch ok = yhtaskservuceapiservice.queryswitchbyid1(1, 1, "体重");
                if (ok.getFirst_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(1);
                    task.setService_content(ok.getService_content());
                    task.setService_record("体重:" + weight);
                    task.setReturnvisit(ok.getReturnvisit());
                    task.setChildnodes(ok.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }
            } else {
                YhJzServiceBodydataSwitch ok1 = yhtaskservuceapiservice.queryswitchbyid1(2, 1, "体重");
                if (ok1.getEvery_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok1.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(2);
                    task.setService_content(ok1.getService_content());
                    task.setService_record("体重:" + weight);
                    task.setReturnvisit(ok1.getReturnvisit());
                    task.setChildnodes(ok1.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }

            }

            /*
             * WellUserBmi uub = new WellUserBmi();
             * uub.setHeight(Float.valueOf(Float.parseFloat(height)));
             * uub.setWeight(Float.valueOf(Float.parseFloat(weight)));
             * uub.setIs_Del(Integer.valueOf(2)); uub.setEUserId(E_User_Id);
             *
             * uub.setEntryTime(createdate);// Data.getNowDate()
             * uub.setDataSource(deviceId); uub.setEnv(deviceId);
             */
            //wellUserBmiMapper.save(uub);

        }

        JSONObject whrObj = json.getJSONObject("Whr");
        String waistline = "";// 腰围
        String hipline = "";// 臀围
        if (whrObj != null) {
            waistline = (String) whrObj.get("Waistline");// 腰围
            hipline = (String) whrObj.get("Hipline");// 臀围


            YhUserWaistline wuw = new YhUserWaistline();
            wuw.setWaistline(Float.parseFloat(waistline));
            wuw.setIsDel(2);
            wuw.seteUserId(Integer.valueOf(userCode));
            wuw.setEntryTime(now);
            yhUserWaistlineMapper.insertSelective(wuw);
        }

        JSONObject bloodSugarObj = json.getJSONObject("BloodSugar");
        String bloodSugar = "";// 餐前血糖
        if (bloodSugarObj != null) {
            bloodSugar = (String) bloodSugarObj.get("BloodSugar");// 餐前血糖
//			WellUserBloodsuger uubs = new WellUserBloodsuger();
//			uubs.setBs(Float.valueOf(Float.parseFloat(bloodSugar)));
//			uubs.setDel(Integer.valueOf(2));
//			uubs.setE_id(E_User_Id);
//			uubs.setEntryTime(now);
//			uubs.setEnv(deviceId);
//			uubs.setDataSource(deviceId);
            //Reportanemergency.giveanalarm(userCode, bloodSugar, deviceId, "blood_sugar", "血糖");
            //wellUserBloodsugerMapper.save(uubs);

            int i = yhtaskservuceapiservice.selectbloodsuger(userCode);
            if (i == 0) {
                YhJzServiceBodydataSwitch ok = yhtaskservuceapiservice.queryswitchbyid1(1, 1, "血糖");
                if (ok.getFirst_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(1);
                    task.setService_content(ok.getService_content());
                    task.setService_record("血糖:" + bloodSugar);
                    task.setReturnvisit(ok.getReturnvisit());
                    task.setChildnodes(ok.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }
            } else {
                YhJzServiceBodydataSwitch ok1 = yhtaskservuceapiservice.queryswitchbyid1(2, 1, "血糖");
                if (ok1.getEvery_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok1.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(2);
                    task.setService_content(ok1.getService_content());
                    task.setService_record("血糖:" + bloodSugar);
                    task.setReturnvisit(ok1.getReturnvisit());
                    task.setChildnodes(ok1.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }

            }
        }
        YHUserBloodfat uubf = new YHUserBloodfat();
        JSONObject cholObj = json.getJSONObject("Chol");
        JSONObject bloodFat = json.getJSONObject("BloodFat");
        //BloodfatFunction record=new BloodfatFunction();
        String chol = "";// 总胆固醇
        if (bloodFat != null) {
            chol = (String) bloodFat.get("TChol");// 总胆固醇
            uubf.setCholesterin(Float.parseFloat(chol));
            //	record.setCholesterol(chol);
            //Reportanemergency.giveanalarm(userCode, chol, deviceId, "tc", "总胆固醇");
        }
        if (StringUtils.isEmpty(chol)) {
            if (cholObj != null) {
                chol = (String) cholObj.get("Chol");
            }
        }

        String hdlChol = "";// 高密度蛋白
        String calcLdl = "";// 低密度蛋白
        String trig = "";// 甘油三脂
        if (bloodFat != null) {

            hdlChol = (String) bloodFat.get("HdlChol");// 高密度蛋白
            calcLdl = (String) bloodFat.get("CalcLdl");// 低密度蛋白
            trig = (String) bloodFat.get("Trig");// 甘油三脂
            boolean trigmatches = trig.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
            boolean hdlCholmatches = hdlChol.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
            boolean calcLdlmatches = calcLdl.matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?");
            if (trigmatches) {
                uubf.setTring(Float.parseFloat(trig));
                //record.setTg(trig);
            } else {
                uubf.setTring(0f);
                //record.setTg("0");
            }
            if (hdlCholmatches) {
                uubf.setHdl(Float.parseFloat(hdlChol));
                //record.setHdlc(hdlChol);
            } else {
                uubf.setHdl(0f);
                //record.setHdlc("0");
            }
            if (calcLdlmatches) {
                uubf.setLdl(Float.parseFloat(calcLdl));
                //record.setLdlc(calcLdl);
            } else {
                uubf.setLdl(0f);
                //record.setLdlc("0");
            }

            uubf.setIsDel(2);
            uubf.seteUserId(Integer.valueOf(userCode));
            uubf.setEntryTime(createdate);
            uubf.setDataSource(deviceId);
            yHUserBloodfatMapper.insertSelective(uubf);

            int i = yhtaskservuceapiservice.selectbloodfat(userCode);
            if (i == 0) {
                YhJzServiceBodydataSwitch ok = yhtaskservuceapiservice.queryswitchbyid1(1, 1, "血脂四项");
                if (ok.getFirst_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(1);
                    task.setService_content(ok.getService_content());
                    task.setService_record("血脂四项:" + "总胆固醇/" + chol + ",甘油三脂/" + trig + ",高密度蛋白/" + hdlChol + ",低密度蛋白/" + calcLdl);
                    task.setReturnvisit(ok.getReturnvisit());
                    task.setChildnodes(ok.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }
            } else {
                YhJzServiceBodydataSwitch ok1 = yhtaskservuceapiservice.queryswitchbyid1(2, 1, "血脂四项");
                if (ok1.getEvery_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok1.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(2);
                    task.setService_content(ok1.getService_content());
                    task.setService_record("血脂四项:" + "总胆固醇/" + chol + ",甘油三脂/" + trig + ",高密度蛋白/" + hdlChol + ",低密度蛋白/" + calcLdl);
                    task.setReturnvisit(ok1.getReturnvisit());
                    task.setChildnodes(ok1.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }

            }
        }

        JSONObject bloodPressure = json.getJSONObject("BloodPressure");
        String highPressure = "";// 收缩压
        String lowPressure = "";// 舒张压
        String pulse = "";// 心率
        if (bloodPressure != null) {
            highPressure = bloodPressure.getString("HighPressure");// 收缩压
            lowPressure = bloodPressure.getString("LowPressure");// 舒张压
            pulse = bloodPressure.getString("Pulse");// 心率


            YHUserHeartRate rate = new YHUserHeartRate();
            rate.setHeartRate(Integer.valueOf(pulse));
            rate.setEntryTime(createdate);
            rate.setIsDel(2);
            rate.setEnv("常规测量");
            rate.setDataSource("一体机录入");
            rate.seteUserId(userCode);
            yHUserHeartRateMapper.insertSelective(rate);


            int i = yhtaskservuceapiservice.selectbloodpressurecount(userCode);
            if (i == 0) {
                YhJzServiceBodydataSwitch ok = yhtaskservuceapiservice.queryswitchbyid1(1, 1, "血压");
                if (ok.getFirst_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(1);
                    task.setService_content(ok.getService_content());
                    task.setService_record("血压:" + highPressure + "/" + lowPressure);
                    task.setReturnvisit(ok.getReturnvisit());
                    task.setChildnodes(ok.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }
            } else {
                YhJzServiceBodydataSwitch ok1 = yhtaskservuceapiservice.queryswitchbyid1(2, 1, "血压");
                if (ok1.getEvery_switch() == 1) {
                    YhManageTask task = new YhManageTask();
                    task.setUsercode(userCode);
                    Date d = null;
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    d = sd.parse(createdate);
                    long rightTime = (long) (d.getTime() + ok1.getService_date() * 60 * 60 * 1000); //把当前得到的时间用date.getTime()的方法写成时间戳的形式，再加上8小时对应的毫秒数
                    String newtime = sd.format(rightTime);//把得到的新的时间戳再次格式化成时间的格式
                    task.setService_waiting_time(newtime);
                    task.setService_type(2);
                    task.setService_content(ok1.getService_content());
                    task.setService_record("血压:" + highPressure + "/" + lowPressure);
                    task.setReturnvisit(ok1.getReturnvisit());
                    task.setChildnodes(ok1.getId());
                    task.setChildtag("1");
                    yhtaskservuceapiservice.insertperson(task);
                }

            }
            /*
             * WellUserBloodpressure uubp = new WellUserBloodpressure();
             *
             * uubp.setSbp(Integer.valueOf(Integer.parseInt(highPressure)));
             * uubp.setDbp(Integer.valueOf(Integer.parseInt(lowPressure)));
             * uubp.setDel(Integer.valueOf(2)); uubp.setXinlv(Integer.valueOf(pulse));
             * uubp.setE_id(E_User_Id); uubp.setEntryTime(now); uubp.setTestHj(deviceId);
             * uubp.setType(deviceId); //userBloodpressureMapper.save(uubp);
             * Reportanemergency.giveanalarm(userCode, highPressure, deviceId,
             * "blood_pressure_sys", "收缩压"); Reportanemergency.giveanalarm(userCode,
             * lowPressure, deviceId, "blood_pressure_dia", "舒张压");
             * Reportanemergency.giveanalarm(userCode, pulse, deviceId, "pulse", "心率");
             */
        }

        JSONObject bo = json.getJSONObject("Bo");
        String oxygen = "";// 血氧
        if (bo != null) {
            oxygen = (String) bo.get("Oxygen");// 血氧
        }

        JSONObject BMD = json.getJSONObject("BMD");
        String t_score = "";// 骨密度T值
        if (BMD != null) {
            t_score = (String) BMD.get("TSCORE");// 骨密度T值
        }

        JSONObject HBOBJ = json.getJSONObject("HB");
        String hb = "";// 糖化血红蛋白
        if (HBOBJ != null) {
            hb = (String) HBOBJ.get("Hb");// 糖化血红蛋白
        }

        JSONObject TEMobj = json.getJSONObject("Temperature");
        String tem = "";// 糖化血红蛋白
        if (TEMobj != null) {
            tem = (String) TEMobj.get("tem");// 糖化血红蛋白
        }
        JSONObject uaObj = json.getJSONObject("Ua");
        String ua = "";// 血尿酸
        if (uaObj != null) {
            ua = uaObj.getString("Ua");// 血尿酸
            if (StringUtil.isNotEmpty(ua)) {
                double s = Double.valueOf(ua) * 1000;
                YHUserRenalfunction rf = new YHUserRenalfunction();
                rf.setUricAcid(String.valueOf(s));
                rf.setInerttime(createdate);
                rf.setUserId(Integer.valueOf(userCode));
                rf.setDataSource("一体机");
                yHUserRenalfunctionMapper.insertSelective(rf);
                System.out.println("随诊包肾功能尿酸insert++");
            }
        }

        YhUserPersonageCondition list = yhUserPersonageConditionMapper.showcondition(userCode);
        YhUserPersonageCondition cond = new YhUserPersonageCondition();
        if (height != null && !height.equals("")) {
            cond.setStature(height);//身高
        }

        if (weight != null && !weight.equals("")) {
            cond.setWeight(weight);//体重
        }

        if (waistline != null && !waistline.equals("")) {
            cond.setWaistline(waistline);//腰围
        }

        if (hipline != null && !hipline.equals("")) {
            cond.setHipline(hipline);//臀围
        }

        if (height.equals("") && weight.equals("") && waistline.equals("") && hipline.equals("")) {
            System.out.println("----");
        } else {
            if (list == null) {
                cond.setUserCode(userCode);
                yhUserPersonageConditionMapper.insertSelective(cond);
            } else {
                cond.setId(list.getId());
                yhUserPersonageConditionMapper.updateByPrimaryKeySelective(cond);
            }

        }


        JSONObject LEUObj = json.getJSONObject("leu");
        String leu = "";// 血尿酸
        if (LEUObj != null) {
            leu = (String) uaObj.get("leu");// 血尿酸
        }

        JSONObject BPMObj = json.getJSONObject("bpm");
        String bpm = "";// 血尿酸
        if (BPMObj != null) {
            bpm = (String) uaObj.get("bpm");// 血尿酸
        }


        if (StringUtil.isNotEmpty(trig)) {
            yhHeThresholdService.selectBythresType("trig", userCode, trig, "甘油三脂", request);
        }
        if (StringUtil.isNotEmpty(calcLdl)) {
            yhHeThresholdService.selectBythresType("ldl-c", userCode, calcLdl, "低密度蛋白", request);
        }
        if (StringUtil.isNotEmpty(hdlChol)) {
            yhHeThresholdService.selectBythresType("hdl-c", userCode, hdlChol, "高密度蛋白", request);
        }
        if (StringUtil.isNotEmpty(ua)) {
            yhHeThresholdService.selectBythresType("blood_uric_acid", userCode, ua, "血尿酸", request);//男
        }
        if (StringUtil.isNotEmpty(hb)) {
            yhHeThresholdService.selectBythresType("Hba1c_and_protein", userCode, hb, "糖化血红蛋白", request);
        }
        if (StringUtil.isNotEmpty(lowPressure)) {
            yhHeThresholdService.selectBythresType("blood_pressure_dia", userCode, lowPressure, "舒张压", request);
        }
        if (StringUtil.isNotEmpty(highPressure)) {
            yhHeThresholdService.selectBythresType("blood_pressure_sys", userCode, highPressure, "收缩压", request);
        }
        if (StringUtil.isNotEmpty(chol)) {
            yhHeThresholdService.selectBythresType("tc", userCode, chol, "总胆固醇", request);
        }

        if (StringUtil.isNotEmpty(pulse)) {
            yhHeThresholdService.selectBythresType("pulse", userCode, pulse, "心率", request);
        }
        if (StringUtil.isNotEmpty(oxygen)) {
            yhHeThresholdService.selectBythresType("boold_oxygen", userCode, oxygen, "血氧", request);
        }


        List<ConList> conList = new ArrayList<ConList>();
        ConList list1 = new ConList();
        list1.setConKey("height");
        list1.setConValue(height);
        list1.setPos(180);
        conList.add(list1);

        ConList list18 = new ConList();
        list18.setConKey("bpm");
        list18.setConValue(bpm);
        list18.setPos(202);
        conList.add(list18);

        ConList list2 = new ConList();
        list2.setConKey("weight");
        list2.setConValue(weight);
        list2.setPos(182);
        conList.add(list2);

        ConList list3 = new ConList();
        list3.setConKey("fbg");
        list3.setConValue(bloodSugar);
        list3.setPos(181);
        conList.add(list3);

        ConList list4 = new ConList();
        list4.setConKey("waist");
        list4.setConValue(waistline);
        list4.setPos(183);
        conList.add(list4);

        ConList list5 = new ConList();
        list5.setConKey("cholesterol");
        list5.setConValue(chol);
        list5.setPos(184);
        conList.add(list5);

        ConList list6 = new ConList();
        list6.setConKey("tg");
        list6.setConValue(trig);
        list6.setPos(185);
        conList.add(list6);

        ConList list7 = new ConList();
        list7.setConKey("hdlc");
        list7.setConValue(hdlChol);
        list7.setPos(186);
        conList.add(list7);

        ConList list8 = new ConList();
        list8.setConKey("ldlc");
        list8.setConValue(calcLdl);
        list8.setPos(187);
        conList.add(list8);

        ConList list9 = new ConList();
        list9.setConKey("sbp");
        list9.setConValue(highPressure);
        list9.setPos(188);
        conList.add(list9);

        ConList list10 = new ConList();
        list10.setConKey("dbp");
        list10.setConValue(lowPressure);
        list10.setPos(189);
        conList.add(list10);

        ConList list11 = new ConList();
        list11.setConKey("heart_rate");
        list11.setConValue(pulse);
        list11.setPos(190);
        conList.add(list11);

        ConList list12 = new ConList();
        list12.setConKey("t_score");
        list12.setConValue(t_score);
        list12.setPos(191);
        conList.add(list12);

        ConList list13 = new ConList();
        list13.setConKey("hba1c");
        list13.setConValue(hb);
        list13.setPos(192);
        conList.add(list13);

        ConList list14 = new ConList();
        list14.setConKey("bua");
        list14.setConValue(ua);
        list14.setPos(198);
        conList.add(list14);

        ConList list15 = new ConList();
        list14.setConKey("hipline");
        list14.setConValue(hipline);
        list14.setPos(199);
        conList.add(list15);

        ConList list16 = new ConList();
        list14.setConKey("temperature");
        list14.setConValue(tem);
        list14.setPos(200);
        conList.add(list16);

        ConList list17 = new ConList();
        list14.setConKey("leu");
        list14.setConValue(leu);
        list14.setPos(201);
        conList.add(list17);

        tmp.setConlist(conList);

        return tmp;
    }


    /**
     * 兆和动脉硬化
     *
     * @param userCode
     * @param json
     */
    private void ArteryAve(String userCode, String deviceId, JSONObject json) {
        String data_Source = deviceId;
        String env = "常规";
        int SBP = 0;
        int DBP = 0;
        int Pulse = 0;
        int AVI = 0;
        int API = 0;
        int CSBP = 0;
        int CAPP = 0;
        JSONObject ave = (JSONObject) json.get("ArteryAve"); // 兆和动脉硬化
        if (ave != null) {
            SBP = ave.getInteger("SBP");
            DBP = ave.getInteger("DBP");
            Pulse = ave.getInteger("Pulse");
            AVI = ave.getInteger("AVI");
            API = ave.getInteger("API");
            CSBP = ave.getInteger("CSBP");
            CAPP = ave.getInteger("CAPP");
        } else {
            return;
        }
        YhUserArtery artery = new YhUserArtery();
        artery.setSbp(SBP);
        artery.setDbp(DBP);
        artery.setPulse(Pulse);
        artery.setAvi(AVI);
        artery.setApi(API);
        artery.setCsbp(CSBP);
        artery.setCapp(CAPP);
        artery.setUsesrCode(userCode);
        try {
            YhUserArteryMapper.insertSelective(artery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加体温 进数据库
     *
     * @param userCode
     * @param json
     * @throws ParseException
     */
    private void addTemperature(String userCode, String deviceId, JSONObject json, HttpServletRequest request) throws ParseException {
        String data_Source = deviceId;
        String env = "常规";
        Integer is_Del = 2;
        String bodyPart = "耳温";
        float earWd = 0f;
        JSONObject temperature = (JSONObject) json.get("Temperature"); // 温度
        if (temperature != null) {
            String stringTemperature = temperature.getString("Temperature");
            earWd = Float.parseFloat(stringTemperature);
            yhHeThresholdService.selectBythresType("temperature", userCode, String.valueOf(earWd), "体温", request);
        } else {
            return;
        }


//		String date = json.getString("MeasureTime"); // 时间
//		if (date == null || "".equals(date)) {
//			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化当前系统日期
//			date = dateFm.format(new java.util.Date());
//		}

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String entry_Time = sdf.format(date);
        YHUserTemperature temperatureAllInOne = new YHUserTemperature();
        temperatureAllInOne.setTemperature(earWd);
        temperatureAllInOne.setEntryTime(entry_Time);
        temperatureAllInOne.setIsDel(is_Del);
        temperatureAllInOne.seteUserId(userCode);
        temperatureAllInOne.setDataSource(env);
        try {
            yHUserTemperatureMapper.insertSelective(temperatureAllInOne);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 体脂率
     *
     * @param userCode
     * @param json
     */
    private void addBodyFat(String userCode, String deviceId, JSONObject json) {
        Integer E_User_Id = Integer.valueOf(userCode);
        String data_Source = deviceId;
        String env = "常规";
        Integer is_Del = 2;
        float fatFloat = 0f;
        float musclFloat = 0f;
        float BmclFloat = 0f;
        float VisceraFloat = 0f;
        float waterFloat = 0f;
        float heat = 0f;
        float proteinFloat = 0f;
        float BasicMetabolismFloat = 0f;

        /*
         * String date = json.getString("MeasureTime"); // 时间 if (date == null ||
         * "".equals(date)) { SimpleDateFormat dateFm = new
         * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化当前系统日期 date =
         * dateFm.format(new java.util.Date()); }
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String date = sdf.format(date1);
        JSONObject bodyFat = (JSONObject) json.get("Fat"); // 体脂

        JSONObject heightObject = (JSONObject) json.get("Height");// 人体成分

        if (bodyFat != null) {
            String fat = bodyFat.getString("FatRate");
            if (fat == null || fat == "") {
            } else {
                fatFloat = Float.parseFloat(fat); // fat_rate
            }

            String muscl = bodyFat.getString("Muscle");
            if (muscl == null || muscl == "") { // 为空了
            } else {
                Float musclFloatl = Float.parseFloat(muscl); // muscle
                Float weight = 1.0f;
                if (heightObject != null) {
                    weight = Float.parseFloat(heightObject.getString("Weight"));// 体重
                }
                Float musclFloattaichang = (musclFloatl * 100 / weight);
                musclFloat = YiheRoud.round(musclFloattaichang.doubleValue()).floatValue();
            }

            String Bmc = bodyFat.getString("Bmc");// bones
            if (Bmc == null || Bmc == "") { // 为空了
            } else {
                BmclFloat = Float.parseFloat(Bmc);
            }

            String Viscera = bodyFat.getString("Viscera");// visceral_level
            if (Viscera == null || Viscera == "") { // 为空了
                System.out.println("内脏脂肪等级" + Bmc);
            } else {
                VisceraFloat = Float.parseFloat(Viscera);
            }

            String WaterRate = bodyFat.getString("WaterRate");// water
            if (WaterRate == null || WaterRate == "") { // 为空了
                System.out.println("体水占比" + WaterRate);
            } else {
                waterFloat = Float.parseFloat(WaterRate);
            }

            String Protein = bodyFat.getString("Protein");// protein
            if (Protein == null || Protein == "") {
                System.out.println("蛋白质含量" + WaterRate);
            } else {
                proteinFloat = Float.parseFloat(Protein);
            }

            BasicMetabolismFloat = Float.parseFloat(bodyFat.getString("BasicMetabolism")); // 基础代谢

        }

        JSONObject MinFat = (JSONObject) json.get("MinFat"); // 体脂

        if (MinFat != null) {
            fatFloat = Float.parseFloat(MinFat.getString("FatRate")); // 体脂

            String BasicMetabolism = MinFat.getString("BasicMetabolism");
            BasicMetabolismFloat = Float.parseFloat(BasicMetabolism); // 基础代谢
        }

        if (MinFat == null && bodyFat == null) {
            return;
        }

        YHUserBodyFat wellUserBodyFat = new YHUserBodyFat(fatFloat, musclFloat, BmclFloat, VisceraFloat, waterFloat,
                heat, proteinFloat, BasicMetabolismFloat, date, is_Del, E_User_Id, data_Source);
        try {
            yHUserBodyFatMapper.insertSelective(wellUserBodyFat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 血氧饱和度
     *
     * @param userCode
     * @param json
     * @throws ParseException
     */
    private void addOxygen(String userCode, String deviceId, JSONObject json) throws ParseException {
        Integer E_User_Id = 0;
        String data_Source = deviceId;
        String env = "常规";
        Integer is_Del = 2;
        float oxygen = 0f;
        int heart = 0;
        JSONObject oxy = (JSONObject) json.get("Bo"); // 血氧
        if (oxy != null) {
            String oxygenString = oxy.getString("Oxygen");
            oxygen = Float.parseFloat(oxygenString); // 血氧
            String heartRate = oxy.getString("Bpm"); // 脉率值
            if (!heartRate.equals("")) {
                heart = Integer.parseInt(heartRate);
            }
        } else {
            return;
        }
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化当前系统日期
//		if (date == null || "".equals(date)) {
//
//		}else {
//			date = dateFm.format(date);
//		}

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String entry_Time = sdf.format(date);


        YHUserOxygen oxygenAllInOne = new YHUserOxygen(oxygen, heart, entry_Time, is_Del, userCode, env, deviceId);
        try {
            yHUserOxygenMapper.insertSelective(oxygenAllInOne);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加到身高体重
     *
     * @param userCode
     * @param json
     */
    private void addHeightAndWeight(String userCode, String deviceId, JSONObject json) {
        String data_Source = deviceId;
        String env = "随机";
        Integer is_Del = 2;
        JSONObject HeightObject = (JSONObject) json.get("Height");// 人体成分
        Float height = 0f;
        Float weight = 0f;
        if (HeightObject != null) {
            height = Float.parseFloat(HeightObject.getString("Height"));// 身高
            weight = Float.parseFloat(HeightObject.getString("Weight"));// 体重
        } else {
            return;
        }
//		String entry_Time = json.getString("MeasureTime");
//		if (entry_Time == null || "".equals(entry_Time)) {
//			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化当前系统日期
//			entry_Time = dateFm.format(new java.util.Date());
//		}

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String entry_Time = sdf.format(date);
        YHUserBmi wellUserBmi = new YHUserBmi(height, weight, entry_Time,
                is_Del, Integer.valueOf(userCode), env, deviceId);

        // 执行插入数据
        Integer ok = 0;
        try {
            yHUserBmiMapper.insertSelective(wellUserBmi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加到血糖数据
     *
     * @param userCode
     * @param json
     * @throws ParseException
     */
    private void addBloodSugar(String userCode, String deviceId, JSONObject json, HttpServletRequest request) throws ParseException {
        String data_Source = deviceId;
        String env = "空腹";
        Integer is_Del = 2;
        JSONObject bloodSugar = json.getJSONObject("BloodSugar");
        String bloodSugar1 = "";// 餐前血糖

        Float bs = 0f;// 收缩压
        Integer BloodsugarType = 3;
//		String entry_Time = json.getString("MeasureTime");
//		if (entry_Time == null || "".equals(entry_Time)) {
//			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化当前系统日期
//			entry_Time = dateFm.format(new java.util.Date());
//		}

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String entry_Time = sdf.format(date);
        if (bloodSugar != null) {
            bloodSugar1 = (String) bloodSugar.get("BloodSugar");// 餐前血糖
            bs = bloodSugar.getFloat("BloodSugar");// 血糖
            BloodsugarType = bloodSugar.getInteger("BloodsugarType");
        } else {
            return;
        }
        if (BloodsugarType == 1) {
            env = "空腹";
            if (StringUtil.isNotEmpty(bloodSugar1)) {
                yhHeThresholdService.selectBythresType("blood_sugar", userCode, bloodSugar1, "空腹血糖", request);
            }
        } else {
            env = "餐后";
            if (StringUtil.isNotEmpty(bloodSugar1)) {
                yhHeThresholdService.selectBythresType("blood_sugar2", userCode, bloodSugar1, "餐后血糖", request);
            }
        }
        //Date date = new Date();

        YHUserBloodsuger uubs = new YHUserBloodsuger();
        uubs.setBs(bs);
        uubs.setIsDel(Integer.valueOf(2));
        uubs.seteUserId(userCode);
        uubs.setEntryTime(entry_Time);
        uubs.setEnv(env);
        uubs.setDataSource(deviceId);

        // 执行插入数据
        Integer ok = 0;
        try {
            ok = yHUserBloodsugerMapper.insertSelective(uubs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加到血压数据
     *
     * @param userCode
     * @param json
     * @throws ParseException
     */
    private void addBloodPressure(String userCode, String deviceId, JSONObject json) throws ParseException {
        String type = deviceId;
        String test_hj = "常规测量";
        Integer is_Del = 2;
        JSONObject bloodPressure = json.getJSONObject("BloodPressure");
        Integer sbp = 0;// 收缩压
        Integer dbp = 0;// 舒张压
        Integer pulse = 0;// 心率
        //String entry_Time = json.getString("MeasureTime");
        if (bloodPressure != null) {
            sbp = bloodPressure.getInteger("HighPressure");// 收缩压
            dbp = bloodPressure.getInteger("LowPressure");// 舒张压
            pulse = bloodPressure.getInteger("Pulse");// 心率
        } else {
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String entry_Time = sdf.format(date);

//		if (entry_Time == null || "".equals(entry_Time)) {
//			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化当前系统日期
//			entry_Time = dateFm.format(new java.util.Date());
//
//		}
//		YHUserHeartRate rate=new YHUserHeartRate();
//		rate.setHeartRate(Integer.valueOf(pulse));
//		rate.setEntryTime(entry_Time);
//		rate.setIsDel(2);
//		rate.setEnv("常规测量");
//		rate.setDataSource("血压计录入");
//		rate.seteUserId(Integer.valueOf(userCode));
//		yHUserHeartRateMapper.insertSelective(rate);

        YHUserBloodPressure wellUserBloodpressure = new YHUserBloodPressure(sbp, dbp, entry_Time, is_Del, userCode,
                pulse, test_hj, type);

        // 执行插入数据
        try {
            yHUserBloodPressureMapper.insertSelective(wellUserBloodpressure);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加到心电图
     *
     * @param userCode
     * @param json
     * @throws Exception
     */
    private void addPEecg(String userCode, String deviceId, JSONObject json, HttpServletRequest request) throws Exception {
        String data_Source = deviceId;
        String env = "常规";
        Integer is_Del = 2;
        JSONObject pEEcg = (JSONObject) json.get("PEEcg");// 人体成分

        JSONObject ecg = (JSONObject) json.get("Ecg");

        Integer pulse = 0;
        Integer resultCode = 0;
        String resurt_text = "";
        Integer tr = 0;
        Integer sr = 0;
        String ecg_img = "";
        if (ecg != null) {
            Integer pulses = pEEcg.getInteger("Hr");// 心率
            if (pulses == null) {

            } else {
                pulse = pulses;
            }

            resultCode = pEEcg.getInteger("Analysis");
        }
        if (pEEcg != null) {
            Integer pulses = pEEcg.getInteger("Hr");// 心率
            if (pulses == null) {

            } else {
                pulse = pulses;
            }
            ecg_img = pEEcg.getString("EcgImg"); // 转化成base64

            resurt_text = pEEcg.getString("Result");
        } else {
            return;
        }
//		String entry_Time = json.getString("MeasureTime");
//		if (entry_Time == null || "".equals(entry_Time)) {
//			SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 格式化当前系统日期
//			entry_Time = dateFm.format(new java.util.Date());
//		}

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String entry_Time = sdf.format(date);

        String xd_Value = "";
        String pig_path = "";
        byte[] image2byte = generateImage(ecg_img);
        String key = UUID.randomUUID().toString().replaceAll("\\-", "");
        String url = put64image(image2byte, key, request);
        if (StringUtils.isNotBlank(url)) { // 不为空
            pig_path = url;
        }
        YHUserEcg wellUserPEecg = new YHUserEcg(tr, sr, entry_Time, is_Del, Integer.valueOf(userCode), pulse, env, data_Source,
                resultCode, xd_Value, pig_path, resurt_text);
        // 执行插入数据
        Integer ok = 0;
        try {
            yHUserEcgMapper.insertSelective(wellUserPEecg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * base64转MultipartFile文件
     *
     * @param base64
     * @return
     */
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[0]);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //base64方式上传
    public String put64image(byte[] base64, String key1, HttpServletRequest request) throws Exception {
        YhSysConfig conf = yhSysConfigService.showConfig();
        String file64 = Base64.encodeToString(base64, 0);
        MultipartFile file = base64ToMultipart(file64);
        if (conf == null) {//等于0调用本地上传图片
            String dicName = "UpFile";//服务器上传文件夹名称
            String newFileName = file.getOriginalFilename();
            newFileName = newFileName.substring(0, newFileName.lastIndexOf("."));
            String picurl = saveFile(file, newFileName, dicName, request);
            return picurl;
        } else {
            //先把String对象转换成json对象
            net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(conf.getValue());


            String img = "";

            //MultipartFile转换File完整版
            InputStream ins = base64ToMultipart(file64).getInputStream();
            //构造一个带指定Zone对象的配置类
            Configuration cfg = new Configuration(Zone.autoZone());
            //...其他参数参考类注释
            UploadManager uploadManager = new UploadManager(cfg);
            //...生成上传凭证，然后准备上传
            String accessKey = (String) object.get("qiniuAccessKey");
            String secretKey = (String) object.get("qiniuSecretKey");
            String bucket = (String) object.get("qiniuBucketName");
//		//默认不指定key的情况下，以文件内容的hash值作为文件名
            String key = UUID.randomUUID().toString().replaceAll("\\-", "");
            try {
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                try {
                    Response response = uploadManager.put(ins, key, upToken, null, null);
                    //解析上传成功的结果
                    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    if (base64ToMultipart(file64).getSize() == 0) {
                        //news.setImg("");
                    } else {
                        img = (String) object.get("qiniuDomain") + "/" + putRet.key;
                        System.out.println((String) object.get("qiniuDomain") + "/" + putRet.key);

                    }

                } catch (QiniuException ex) {
                    Response r = ex.response;
                    System.err.println(r.toString());
                }
                //sysuser=1;
            } catch (Exception ex) {
                //sysuser=0;
                //ignore
                return null;
            }
            return img;

        }


    }


    public static String saveFile(MultipartFile filedata, String newFileName, String dicName, HttpServletRequest request) {
        // TODO Auto-generated method stub

        //获取Tomcat服务器所在的路径
        String pic_path;
        String tomcat_path = System.getProperty("catalina.home");
        System.out.println(tomcat_path);
        //获取Tomcat服务器所在路径的最后一个文件目录
        String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("/") + 1, tomcat_path.length());
        System.out.println(bin_path + "--------------获取Tomcat服务器所在路径的最后一个文件目录");

        //若最后一个文件目录为bin目录，则服务器为手动启动
        if (("bin").equals(bin_path)) {//手动启动Tomcat时获取路径为：D:\Software\Tomcat-8.5\bin
            //获取保存上传图片的文件路径
            pic_path = tomcat_path.substring(0, System.getProperty("catalina.home").lastIndexOf("/")) + "/webapps/";
        } else {//服务中自启动Tomcat时获取路径为：D:\Software\Tomcat-8.5
            pic_path = tomcat_path + "/webapps/";
        }


        Calendar cale = null;
        cale = Calendar.getInstance();
        String year = String.valueOf(cale.get(Calendar.YEAR));
        String month = String.valueOf(cale.get(Calendar.MONTH) + 1);
        String saveFilePath = dicName + "/" + year + "/" + month + "/";
        /* 构建文件目录 */
        File fileDir = new File(pic_path + saveFilePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        // 上传的文件名
        String filename = filedata.getOriginalFilename();
        // 文件的扩张名
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
        try {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String imgPath = saveFilePath + uuid + ".gif";

            // String url2=request.getScheme()+"://"+ request.getServerName()+":"+ request.getLocalPort();//+request.getRequestURI();
            String url2 = request.getScheme() + "://" + request.getServerName();//+request.getRequestURI();
            String imgPath1 = url2 + "/" + imgPath;
            // System.out.println(pathval + imgPath);打印图片位置
            FileOutputStream out = new FileOutputStream(pic_path + imgPath);
            // 写入文件
            out.write(filedata.getBytes());
            out.flush();
            out.close();
            return imgPath1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public byte[] generateImage(String imgStr) { // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) {
            // 图像数据为空
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            return b;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 图片到byte数组
    public static byte[] image2byte(String path) {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }


    /**
     * 添加到尿液分析
     *
     * @param userCode
     * @param json
     * @throws ParseException
     */
    private void Urinalysis(String userCode, String deviceId, JSONObject json) throws ParseException {
        String type = deviceId;
        String test_hj = "常规";
        JSONObject Urinalysis1 = json.getJSONObject("PhysicalData");
        if (Urinalysis1 != null) {
            JSONObject Urinalysis = json.getJSONObject("PhysicalData").getJSONObject("Urinalysis");
            if (Urinalysis != null) {

                String uro = "";
                int URO = (int) Urinalysis.get("URO");// 尿胆原
                if (URO == 0) {
                    uro = "-";
                } else {
                    uro = "+" + URO;
                }

                String bld = "";
                int BLD = (int) Urinalysis.get("BLD");// 潜血
                if (BLD == 0) {
                    bld = "-";
                } else if (BLD == 1) {
                    bld = "+-";
                } else {
                    bld = "+" + (BLD - 1);
                }

                String bil = "";
                int BIL = (int) Urinalysis.get("BIL");// 胆红素
                if (BIL == 0) {
                    bil = "-";
                } else {
                    bil = "+" + BIL;
                }

                String ket = "";
                int KET = (int) Urinalysis.get("KET");// 酮体
                if (KET == 0) {
                    ket = "-";
                } else if (KET == 1) {
                    ket = "+-";
                } else {
                    ket = "+" + (KET - 1);
                }

                String leu = "";
                int LEU = (int) Urinalysis.get("LEU");// 白细胞
                if (LEU == 0) {
                    leu = "-";
                } else if (LEU == 1) {
                    leu = "+-";
                } else {
                    leu = "+" + (LEU - 1);
                }

                String glu = "";
                int GLU = (int) Urinalysis.get("GLU");// 葡萄糖
                if (GLU == 0) {
                    glu = "-";
                } else if (GLU == 1) {
                    glu = "+-";
                } else {
                    glu = "+" + (GLU - 1);
                }

                String pro = "";
                int PRO = (int) Urinalysis.get("PRO");// 蛋白质
                if (PRO == 0) {
                    pro = "-";
                } else if (PRO == 1) {
                    pro = "+-";
                } else {
                    pro = "+" + (PRO - 1);
                }

                String ph = "";
                int PH = (int) Urinalysis.get("PH");// 酸碱度
                if (PH == 0) {
                    ph = "5.0";
                } else if (PH == 1) {
                    ph = "6.0";
                } else {
                    ph = String.valueOf((PH - 1) * 0.5 + 6.0);
                }

                String nit = "";
                int NIT = (int) Urinalysis.get("NIT");// 亚硝酸盐
                if (NIT == 0) {
                    nit = "-";
                } else {
                    nit = "+";
                }

                int SG = (int) Urinalysis.get("SG");// 比重
                String sg = String.valueOf(SG * 0.005 + 1);

                String vc = "";
                int VC = (int) Urinalysis.get("VC");// 维生素
                if (VC == 0) {
                    vc = "-";
                } else if (VC == 1) {
                    vc = "+-";
                } else {
                    vc = "+" + (VC - 1);
                }

                int MAL = (int) Urinalysis.get("MAL");// 微白蛋白
                int CR = (int) Urinalysis.get("CR");// 肌酐
                int UCA = (int) Urinalysis.get("UCA");// 钙离子
                int Result = (int) Urinalysis.get("Result");// 结果
                // SimpleDateFormat formatter = new
                // SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String dateStr = sdf.format(date);
                // System.out.println(uro+"--"+bld+"--"+bil+"--"+ket+"--"+leu+"--"+glu+"--"+pro+"--"+ph+"--"+nit+"--"+sg+"--"+vc+"--"+MAL+"--"+CR+"--"+UCA+"--"+Result);
                YHUserUba wellUserUba = new YHUserUba();
                wellUserUba.setEnv(test_hj);
                wellUserUba.setAdddate(dateStr);
                wellUserUba.setGlucose(glu);
                wellUserUba.setBilirubin(bil);
                wellUserUba.setKetone(ket);
                wellUserUba.setProportion(sg);
                wellUserUba.setRedbloodcell(bld);
                wellUserUba.setPh(ph);
                wellUserUba.setUrineprotein(pro);
                wellUserUba.setUrobilinogen(uro);
                wellUserUba.setNitrite(nit);
                wellUserUba.setWhitebloodcell(leu);
                wellUserUba.seteUserId(Integer.valueOf(userCode));
                wellUserUba.setDataSource(type);
                wellUserUba.setVitamin(vc);

                yHUserUbaMapper.insertSelective(wellUserUba);

            }
        }

    }


    /**
     * 从JSON中获取UserCode
     *
     * @return
     * @throws ParseException
     */
    private String getUserCode(JSONObject json) throws ParseException {
        String userCode = "";
        if (json == null) {
            return "";
        }
        JSONObject memeberObj = json.getJSONObject("Member");
        if (memeberObj != null) {
            String Name = (String) memeberObj.get("Name");// 姓名
            String IdCode = (String) memeberObj.get("IdCode");// 身份证
            String mobile = (String) memeberObj.get("Mobile");// 电话
            String Sex = (String) memeberObj.get("Sex");// 性别
            String Address = (String) memeberObj.get("Address");// 身份证地址
            String Birthday = (String) memeberObj.get("Birthday");// 生日
            Map<String, Object> map = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(mobile)) { // 不为空
                userCode = yhUserReginfoMapper.findByMoblie(mobile);
            }
        }

        return userCode;
    }


    /**
     * 血压接口
     *
     * @author apple
     * @param Sbp
     * @param Dbp
     * @param XinLv
     * @param E_USer_ID
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value = "/device/bloodpressureInterface", method = RequestMethod.POST)
    @ResponseBody
    public String getBloodpressure(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
//		String entry_Time = request.getParameter("entryTime");
//
//		boolean alarmFlag = false;
//		if(StringUtils.isEmpty(entry_Time)){
//
//	        alarmFlag = true;
//		}
        String entry_Time = request.getParameter("entryTime");
        if (StringUtils.isEmpty(entry_Time)) {
            Date date = new Date();
            SimpleDateFormat
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            entry_Time = sdf.format(date);
        } else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            entry_Time = sdf.format(date);
        }
        Integer is_Del = 2;
        String test_hj = "常规测量";
        String type = "血压计传输";
        String dbp1 = request.getParameter("Dbp");
        String sbp1 = request.getParameter("Sbp");
        String xinlv = request.getParameter("XinLv");
        String strType = request.getParameter("type");
        Integer pulse = 0;
        Integer dbp = 0;
        Integer sbp = 0;
        if ("0".equals(strType)) {
            type = "血压计传输";
            if (StringUtils.isEmpty(dbp1) || StringUtils.isEmpty(sbp1) || "0".equals(dbp1) || "0".equals(sbp1)) {
                result.put("code", 0);
                return JSONModelConverter.modelToJSONString(result);
            }
            dbp = Integer.parseInt(dbp1);
            sbp = Integer.parseInt(sbp1);
            pulse = Integer.parseInt(xinlv);
        } else {
            type = "手环/手表传输";
            dbp = 0;
            sbp = 0;
            pulse = Integer.parseInt(xinlv);
        }
        String terminalId = request.getParameter("terminalId");
        try {
            // 通过设备id查到用户id
            String userCode = yhUserDeviceMapper.selectNewDeviceUserCode(terminalId);
            // 把所有参数封装到weel_user_bloodpressure;
            Integer ok = 0;
            if(StringUtil.isNotEmpty(userCode)) {
                YHUserBloodPressure wellUserBloodpressure = new YHUserBloodPressure(sbp, dbp, entry_Time, is_Del, userCode,
                        pulse, test_hj, type);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("sbp", sbp);
                map.put("dbp", dbp);
                map.put("xinlv", pulse);
                map.put("entry_Time", entry_Time);
                map.put("E_User_Id", userCode);

                int total = yHUserBloodPressureMapper.selectUserBloodPressureCountByUserIdAndTime(map);
                if (total == 0) {
                    // 执行插入数据
                    try {
                        ok = yHUserBloodPressureMapper.insertSelective(wellUserBloodpressure);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            result.put("code", ok);
        } catch (Exception e) {

        }
        return JSONModelConverter.modelToJSONString(result);
    }


    /**
     * 血糖接口
     *
     * @author apple
     * @param bs
     * @param terminalId
     * @param request
     * @param response
     * @return String
     */
    @RequestMapping(value = "/device/bloodsugerInterface", method = RequestMethod.POST)
    @ResponseBody
    public String getBloodsuger(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String entry_Time = request.getParameter("testTime");
        if (StringUtils.isEmpty(entry_Time)) {
            Date date = new Date();
            SimpleDateFormat
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            entry_Time = sdf.format(date);
        } else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            entry_Time = sdf.format(date);

        }
        Float bs = Float.parseFloat(request.getParameter("bs"));
        Double d_bsDouble = Double.parseDouble(request.getParameter("bs"));
        String terminalId = request.getParameter("terminalId");
        Integer is_Del = 2;
        String env = "空腹";
        String data_Source = "血糖仪传输";
        try {
            // 通过设备id查到用户id
            String userCode = yhUserDeviceMapper.selectNewDeviceUserCode(terminalId);
            // 执行插入数据
            Integer ok = 0;
            if(StringUtil.isNotEmpty(userCode)) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date shijian = formatter.parse(entry_Time);
                // 把所有参数封装到weel_user_bloodpressure;
                YHUserBloodsuger uubs = new YHUserBloodsuger();
                uubs.setBs(bs);
                uubs.setIsDel(Integer.valueOf(2));
                uubs.seteUserId(userCode);
                uubs.setEntryTime(entry_Time);
                uubs.setEnv(env);
                uubs.setDataSource(data_Source);

                try {
                    ok = yHUserBloodsugerMapper.insertSelective(uubs);
                    //healthyAlarmDataDas.addBloodSugarThresholdAlarm(d_bsDouble, userCode, terminalId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            result.put("code", ok);
        } catch (Exception e) {

        }
        return JSONModelConverter.modelToJSONString(result);
    }


}
