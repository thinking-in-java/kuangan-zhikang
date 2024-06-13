/**
 * 
 */
package com.yihe.applet.api.controller;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.text.SimpleDateFormat;
import com.yihe.applet.api.domain.YhEventEnrollApp;
import com.yihe.applet.api.domain.YhEventRegisterApp;
import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.mapper.YhEventEnrollAppMapper;
import com.yihe.applet.api.mapper.YhEventRegisterAppMapper;
import com.yihe.applet.api.service.YhUserReginfoAppService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;

/**
 * 精彩活动报名
 * @author dong
 *
 */
@Controller
@RequestMapping("/api/YhEventRegisterAppController")
@Api(value = "YhEventRegisterAppController控制器", description = "YhEventRegisterAppController管理")
public class YhEventRegisterAppController {
	@Autowired
	YhEventRegisterAppMapper yhEventRegisterAppMapper;
	
	@Autowired
	YhUserReginfoAppService YhUserReginfoService;
	/**
	 * 把出生日期字符串转换为日期格式。
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static  Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }
	
	/**
	 * 计算年龄
	 * @param birthDay
	 * @return
	 * @throws Exception
	 */
	public static  int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance(); 
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay); 
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
        int age = yearNow - yearBirth;   //计算整岁数
            if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            }else{
                age--;//当前月份在生日之前，年龄减一
            } 
         } 
            return age; 
        }
	
	@RequestMapping(value = "/insertregister")
	@ResponseBody
	public Result insertregister(int id,String userCode,String mobile,String birthDate,String name,byte gender) throws Exception {
		
		YhUserReginfoApp yhUserReginfo = YhUserReginfoService.findYhUserReginfo(userCode);
		
		 int  age = getAge(yhUserReginfo.getBirthDate());           //由出生日期获得年龄***
		 Date date=new Date();
		YhEventRegisterApp record=new YhEventRegisterApp();
		record.setUserCode(userCode);
		record.setAge(age);
		record.setPhone(yhUserReginfo.getMobile());
		record.setGender(gender);
		record.setUserName(yhUserReginfo.getName());
		record.setEventId(id);
		record.setRegisterDate(date);
		int lisy=yhEventRegisterAppMapper.insertSelective(record);
		return Result.ok(lisy);
	} 
}
