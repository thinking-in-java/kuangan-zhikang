package com.yihe.web.controller.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserTemperature;
import com.yihe.system.service.YHUserTemperatureService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YHUserTemperature")
public class YhUserTemperatureController {

	@Autowired
	YHUserTemperatureService yHUserTemperatureService;
	
	@ApiOperation(value = "分页查询体重")
	@ResponseBody
	@RequestMapping(value="/page",method=RequestMethod.GET)
	public Map<String,Object> queryUserDate(String userCode,int page,int limit){
        //查询集合
		PageInfo<YHUserTemperature> data = yHUserTemperatureService.queryUserByCriteriaWithPage(userCode, page, limit);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("data",data.getList());
        result .put("count", data.getTotal());
        result.put("msg", "");
        result.put("code", 20000);
		return result;
	}
	@ApiOperation(value = "查询体温日期")
	@ResponseBody
	@RequestMapping(value="/selectTemperature",method=RequestMethod.GET)
	public Map<String,Object> selectTemperature(String userCode,int page,int limit){
        //查询集合
	 PageInfo<YHUserTemperature> data = yHUserTemperatureService.selectTemperature(userCode,page,limit);
	 Map<String,Object> result = new HashMap<String, Object>();
	 	result.put("data",data.getList());
	 	result .put("count", data.getTotal());
	 	result.put("msg", "");
	 	result.put("code", 20000);
		return result;
	}
	
    /**
     * 周集合
     * */
    public static List<String> weekListInfo(){
        List<String> list=new ArrayList<>();
        for (int i = -30; i <= 0; i++) {
            String week="";
            Calendar cal = Calendar.getInstance();
//n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
            int n = i;
            String monday;
            cal.add(Calendar.DATE, n*7);
//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
            cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
            monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
           // System.out.println(monday);
            week=week+monday;

            Calendar cal2 = Calendar.getInstance();
            int m = i+1;
            String riday;
            cal2.add(Calendar.DATE, m*7);
//想周几，这里就传几Calendar.MONDAY（TUESDAY...）
            cal2.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
            riday = new SimpleDateFormat("yyyy-MM-dd").format(cal2.getTime());
            //System.out.println(riday);
            week=week+"~"+riday;
            list.add(week);
        }

return  list;
    }
    public static List<String> getTimeListForFanweiMonth() {
        List<String> timeList = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            if (i < 10) {
                timeList.add("2019-0" + i);
            } else {
                timeList.add("2019-" + i);
            }
        }
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        for (int i = 2019 + 1; i <= year; i++) {
            if (i == year) {
                for (int j = 1; j < month + 1; j++) {
                    if (j < 10) {
                        timeList.add(i + "-0" + j);
                    } else {
                        timeList.add(i + "-" + j);
                    }
                }
            } else {
                for (int k = 1; k < 13; k++) {
                    if (k < 10) {
                        timeList.add(i + "-0" + k);
                    } else {
                        timeList.add(i + "-" + k);
                    }
                }
            }

        }

        return timeList;

    }
    @RequestMapping(value = "/getTimeListForXcx2",method=RequestMethod.GET)
    @ResponseBody
    public Result getTimeListForXcx(String usercode) {
        Map<String, Object> map = new HashMap<>();
        //周历
        map.put("week", weekListInfo());
        map.put("month", getTimeListForFanweiMonth());
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int leng= year-2019+1;
        String[] arr = new String[leng];
        for (int i = 2019,k=0; i <= year; i++,k++) {
            arr[k]=String.valueOf(i);
        }
        map.put("year", arr);

        return Result.ok(map);
    }
	
	
}
