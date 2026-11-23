package com.yihe.web.controller.system;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yihe.common.core.controller.BaseController;
import com.yihe.system.domain.EquipmentInfoVOJO;
import com.yihe.system.domain.YhDicAssessmentScreen;
import com.yihe.system.domain.YhHeThresholdAlarm;
import com.yihe.system.domain.YhHeThresholdAlarmVOJO;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.impl.YhHeThresholdAlarmServiceImpl;
import com.yihe.system.service.impl.YhUserApiServiceImpl;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.Api;

/**
 * YhHeThresholdAlarmcontroller
 * Created by royiwen on 2020/3/27.
 */
@Controller
@RequestMapping("/YhHeThresholdAlarm")
@Api(value = "YhHeThresholdAlarm控制器", description = "YhHeThresholdAlarm管理")
public class YhHeThresholdAlarmController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhHeThresholdAlarmController.class);
	
    @Autowired
   private  YhHeThresholdAlarmServiceImpl yhHeThresholdAlarmServiceImpl;
    
    @Autowired
    private YhUserApiServiceImpl YhUserApiServiceImpl;
	
    @RequestMapping(value = "/findThresholdList", method = RequestMethod.GET)
   	@ResponseBody
    public Result findThresholdList(@RequestParam(required = false) Integer alertId,
            @RequestParam(required = false) String nameLike,
   			@RequestParam(required = false) String nametype,
   			@RequestParam(required = false) String status,
   			@RequestParam(required = false) String startTime, @RequestParam(required = false) String strTime,
			@RequestParam(required = false) String endTime,
   			@RequestParam(required = false,defaultValue = "1") int page,@RequestParam(required = false,defaultValue = "5") int limit) {
   		System.out.println("nameLike---"+nameLike);
   		System.out.println("page---"+page);
   		System.out.println("nametype---"+nametype);
   		System.out.println("status---"+status);
   		System.out.println("limit---"+limit);
   		if (StringUtil.isNotEmpty(startTime)) {
			String[] split = startTime.split(" - ");
			strTime = split[0];
			endTime = split[1];
		}
   		Subject subject = SecurityUtils.getSubject();
   		YhUserReginfo upmsUser = (YhUserReginfo)subject.getPrincipal();
   		if(null==upmsUser){
   			return Result.error();
   		}
   		String usercode = upmsUser.getUserCode();
   		
        PageInfo<YhHeThresholdAlarmVOJO> listpage = yhHeThresholdAlarmServiceImpl.findYhHeThresholdAlarmByDoctor(usercode,nameLike, nametype, status, strTime, endTime, page, limit, alertId);
   		List<YhHeThresholdAlarmVOJO> list = listpage.getList();
   		Map<String,Object> rsMap = new HashMap<String,Object>();
           rsMap.put("count",listpage.getTotal());
           rsMap.put("code", 0);
           rsMap.put("msg", "ok");
           rsMap.put("data", list);
           return Result.ok(rsMap);
   		
   	}
    
    @RequestMapping(value = "findThresholdById",method =RequestMethod.GET )
	public String findThresholdById(String id,Model model){
    	Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
    	YhHeThresholdAlarmVOJO selectByPrimaryKey = yhHeThresholdAlarmServiceImpl.findYhHeThresholdTypeByIdInfo(id);
    	if(null!=selectByPrimaryKey.getBirthDate()){
    		int ageByBirth = getAgeByBirth(selectByPrimaryKey.getBirthDate());
        	selectByPrimaryKey.setAge(ageByBirth);
    	}
    	
    /*	//如果已经受理 查询受理人信息
    	YhUserReginfo selectUserRegInfoByUserCode =new YhUserReginfo();
    	if(StringUtil.isNotEmpty(selectByPrimaryKey.getAcceptUsercode())){
    		String acceptUsercode = selectByPrimaryKey.getAcceptUsercode();
    		selectUserRegInfoByUserCode = YhUserApiServiceImpl.selectUserRegInfoByUserCode(acceptUsercode);
    	}*/
    	
    	model.addAttribute("data", selectByPrimaryKey);
    	model.addAttribute("principal", principal);
		return "devicesSettingUp/alarmInformationedit";
	}
    
    
    @RequestMapping(value = "/modifyInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result modifyInfo(@RequestBody YhHeThresholdAlarmVOJO record) {
    	if(null==record.getId()){
    		return Result.error();
    	}
    	 System.out.println(record.toString());
		// 查询用户信息
    	yhHeThresholdAlarmServiceImpl.updateByPrimaryKeySelectiveBYVOJO(record);
		return Result.ok();
	}
    
    private static int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
           return 0;
        }
    }

}