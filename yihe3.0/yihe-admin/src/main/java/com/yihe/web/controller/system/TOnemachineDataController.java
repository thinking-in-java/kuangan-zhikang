package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.yihe.common.core.controller.BaseController;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.common.core.page.TableDataInfo;
import com.yihe.common.utils.DateUtils;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.*;
import com.yihe.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/TOnemachineData")
public class TOnemachineDataController extends BaseController {

    @Autowired
    private TOnemachineDataService tOnemachineDataService;
    @Autowired
    private YhUserReginfoService yhUserReginfoService;
    @Autowired
    private YHUserTemperatureService yHUserTemperatureService;
    @Autowired
    private YHUserOxygenService yHUserOxygenService;
    @Autowired
    private YHUserBmiService yHUserBmiService;
    @Autowired
    private YHUserBloodPressureService yHUserBloodPressureService;
    @Autowired
    private YHUserBloodsugerService yHUserBloodsugerService;
    @Autowired
    private YHUserBodyFatService yHUserBodyFatService;
    @Autowired
    private YHUserBloodfatService yHUserBloodfatService;
    @Autowired
    private YHUserRenalfunctionService yHUserRenalfunctionService;
    @Autowired
    private YHUserHeartRateService yHUserHeartRateService;
    @Autowired
    private yHUserHemoglobinService yHUserHemoglobinService;

    @PostMapping("/saveMachineData")
    @ResponseBody
    public AjaxResult saveMachineData(@RequestBody JSONObject jsonObject){
        if(jsonObject == null){
            return AjaxResult.error();
        }
        String cardNum = jsonObject.getString("cardNum");
        //根据身份证查询用户
        YhUserReginfo reginfo = yhUserReginfoService.selectByCardNum(cardNum);
        if(StringUtils.isNull(reginfo)){
            reginfo = yhUserReginfoService.selectMobileAndName2(cardNum);
            if(StringUtils.isNull(reginfo)){
                return AjaxResult.error("未找到用户信息");
            }
        }
        TOnemachineData data = TOnemachineData.chang(jsonObject);
        data.setUserCode(reginfo.getUserCode());
        tOnemachineDataService.insertSelective(data);
        saveData(jsonObject, reginfo.getUserCode());
        return AjaxResult.success();
    }

    @GetMapping("/queryOnemachine")
    @ResponseBody
    public TableDataInfo queryOnemachine(String userCode){
        startPage();
        List<TOnemachineData> data = tOnemachineDataService.queryOnemachine(userCode);
        return getDataTable(data);
    }

    @GetMapping("/onemachineList")
    public String onemachineList(Model model, String userCode) {
        model.addAttribute("userCode", userCode);
        return "personalUser/healthRecord/onemachineList";
    }

    @GetMapping("/selectByPrimaryKey")
    public String selectByPrimaryKey(Model model, Long id){
        TOnemachineData data = tOnemachineDataService.selectByPrimaryKey(id);
        model.addAttribute("data", data);
        return "personalUser/healthRecord/onemachineData";
    }

    public void saveData(JSONObject jsonObject, String userCode){
        if(StringUtils.isNotEmpty(jsonObject.getString("temperature"))){
            YHUserTemperature temperature = new YHUserTemperature();
            temperature.setTemperature(jsonObject.getFloat("temperature"));
            temperature.setEntryTime(jsonObject.getString("gmtCreate"));
            temperature.setIsDel(2);
            temperature.seteUserId(userCode);
            temperature.setDataSource("一体机");
            yHUserTemperatureService.insertSelective(temperature);
        }
        if (StringUtils.isNotEmpty(jsonObject.getString("oxygenSaturation"))){
            YHUserOxygen oxygen = new YHUserOxygen();
            oxygen.setOxygen(jsonObject.getFloat("oxygenSaturation"));
            oxygen.setHeartRate(jsonObject.getIntValue("electrocardiogramValue"));
            oxygen.setEntryTime(jsonObject.getString("gmtCreate"));
            oxygen.seteUserId(userCode);
            oxygen.setIsDel(2);
            oxygen.setTestHj("常规测量");
            oxygen.setType("一体机");
            yHUserOxygenService.insertSelective(oxygen);
        }
        if (StringUtils.isNotEmpty(jsonObject.getString("height")) || StringUtils.isNotEmpty(jsonObject.getString("weight"))){
            YHUserBmi bmi = new YHUserBmi();
            bmi.setuHeight(jsonObject.getFloat("height"));
            bmi.setuWeight(jsonObject.getFloat("weight"));
            bmi.setEntryTime(jsonObject.getString("gmtCreate"));
            bmi.seteUserId(Integer.valueOf(userCode));
            bmi.setIsDel(2);
            bmi.setEnv("常规测量");
            bmi.setDataSource("一体机");
            yHUserBmiService.saveBmi(bmi);
        }
        if (StringUtils.isNotEmpty(jsonObject.getString("diastolicPressure")) || StringUtils.isNotEmpty(jsonObject.getString("systolicPressure"))){
            YHUserBloodPressure pressure = new YHUserBloodPressure();
            pressure.setSbp(jsonObject.getInteger("systolicPressure"));
            pressure.setDbp(jsonObject.getInteger("diastolicPressure"));
            pressure.setEntryTime(jsonObject.getString("gmtCreate"));
            pressure.seteUserId(userCode);
            pressure.setIsDel(2);
            pressure.setTestHj("常规测量");
            pressure.setType("一体机");
            yHUserBloodPressureService.insertSelective(pressure);
        }
        if (StringUtils.isNotEmpty(jsonObject.getString("bloodSugar"))){
            YHUserBloodsuger bloodsuger = new YHUserBloodsuger();
            bloodsuger.setBs(jsonObject.getFloat("bloodSugar"));
            bloodsuger.setEntryTime(jsonObject.getString("gmtCreate"));
            bloodsuger.seteUserId(userCode);
            bloodsuger.setIsDel(2);
            bloodsuger.setEnv("空腹");
            bloodsuger.setDataSource("一体机");
            yHUserBloodsugerService.insertSelective(bloodsuger);
        }
        if (StringUtils.isNotEmpty(jsonObject.getString("ffatp")) || StringUtils.isNotEmpty(jsonObject.getString("fmuscle"))){
            YHUserBodyFat fat = new YHUserBodyFat();
            fat.setFatRate(jsonObject.getFloat("ffatp"));
            fat.setMuscle(jsonObject.getFloat("fmuscle"));
            fat.setBones(0f);
            fat.setEntryTime(jsonObject.getString("gmtCreate"));
            fat.seteUserId(Integer.valueOf(userCode));
            fat.setIsDel(2);
            fat.setType("一体机");
            fat.setSource(1);
            yHUserBodyFatService.insertSelective(fat);
        }
        if (StringUtils.isNotEmpty(jsonObject.getString("cholesterin")) || StringUtils.isNotEmpty(jsonObject.getString("ftrig")) ||
            StringUtils.isNotEmpty(jsonObject.getString("fhdl")) || StringUtils.isNotEmpty(jsonObject.getString("fldl"))){
            YHUserBloodfat bloodfat = new YHUserBloodfat();
            bloodfat.setCholesterin(getFloat(jsonObject.getString("cholesterin")));
            bloodfat.setTring(getFloat(jsonObject.getString("ftrig")));
            bloodfat.setHdl(getFloat(jsonObject.getString("fhdl")));
            bloodfat.setLdl(getFloat(jsonObject.getString("fldl")));
            bloodfat.setEntryTime(jsonObject.getString("gmtCreate"));
            bloodfat.seteUserId(Integer.valueOf(userCode));
            bloodfat.setIsDel(2);
            bloodfat.setDataSource("一体机");
            yHUserBloodfatService.insertSelective(bloodfat);
        }
        if (StringUtils.isNotEmpty(jsonObject.getString("uricAcid"))){
            YHUserRenalfunction renalfunction = new YHUserRenalfunction();
            renalfunction.setCreatinine("0");
            renalfunction.setUreaNitrogen("0");
            renalfunction.setUricAcid(jsonObject.getString("uricAcid"));
            renalfunction.setUserId(Integer.valueOf(userCode));
            renalfunction.setInerttime(jsonObject.getString("gmtCreate"));
            renalfunction.setDataSource("一体机");
            renalfunction.setIsDel(2);
            yHUserRenalfunctionService.insertSelective(renalfunction);
        }else if (StringUtils.isNotEmpty(jsonObject.getString("electrocardiogramValue"))){
            YHUserHeartRate heartRate = new YHUserHeartRate();
            heartRate.setHeartRate(jsonObject.getInteger("electrocardiogramValue"));
            heartRate.setEntryTime(jsonObject.getString("gmtCreate"));
            heartRate.seteUserId(userCode);
            heartRate.setDataSource("一体机");
            heartRate.setIsDel(2);
            heartRate.setEnv("常规测量");
            yHUserHeartRateService.insertSelective(heartRate);
        }else if (StringUtils.isNotEmpty(jsonObject.getString("hb"))){
            YhUserHemoglobin hemoglobin = new YhUserHemoglobin();
            hemoglobin.setHeHb(jsonObject.getFloat("hb"));
            hemoglobin.setHeTyep("一体机");
            try {
                hemoglobin.setHeData(new SimpleDateFormat(DateUtils.YYYY_MM_DD_HH_MM_SS).parse(jsonObject.getString("gmtCreate")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hemoglobin.setUserCode(userCode);
            yHUserHemoglobinService.insertSelective(hemoglobin);
        }
    }

    private Float getFloat(String value){
        if(StringUtils.isNotEmpty(value)){
            return Float.parseFloat(value.replace(">", "").replace("<", ""));
        }
        return null;
    }
}
