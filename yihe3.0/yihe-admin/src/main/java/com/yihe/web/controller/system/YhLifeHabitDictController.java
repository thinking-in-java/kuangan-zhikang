/**
 * 
 */
package com.yihe.web.controller.system;

import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhLifeHabitDictMapper;
import com.yihe.system.mapper.YhLifeHabitPersonDictMapper;
import com.yihe.system.mapper.YhLifeHabitSignMapper;
import com.yihe.system.mapper.YhUserHabitGuideMapper;
import com.yihe.system.service.YhFoodCookBookService;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 习惯管理
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhLifeHabitDictController")
public class YhLifeHabitDictController {

	@Autowired
	YhLifeHabitDictMapper yhLifeHabitDictMapper;
	
	@Autowired
	YhUserHabitGuideMapper yhUserHabitGuideMapper;
	
	@Autowired
	  private YhLifeHabitPersonDictMapper yhLifeHabitPersonDictMapper;
	
	@Autowired
	YhLifeHabitSignMapper yhLifeHabitSignMapper;
	
	@Autowired
	 private YhFoodCookBookService yhFoodCookBookService;
	
	/**
	 * 计算2个日期之间相差的  以年、月、日为单位，各自计算结果是多少
	 * 比如：2011-02-02 到  2017-03-02
	 *                                以年为单位相差为：6年
	 *                                以月为单位相差为：73个月
	 *                                以日为单位相差为：2220天
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static int dayCompare(Date fromDate,Date toDate){
	    Calendar  from  =  Calendar.getInstance();
	    from.setTime(fromDate);
	    Calendar  to  =  Calendar.getInstance();
	    to.setTime(toDate);
	    //只要年月
	    int fromYear = from.get(Calendar.YEAR);
	    int fromMonth = from.get(Calendar.MONTH);

	    int toYear = to.get(Calendar.YEAR);
	    int toMonth = to.get(Calendar.MONTH);

	    int year = toYear  -  fromYear;
	    int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
	    int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));
	    return year;
	}
	
	public static int dayCompare1(Date fromDate,Date toDate){
	    Calendar  from  =  Calendar.getInstance();
	    from.setTime(fromDate);
	    Calendar  to  =  Calendar.getInstance();
	    to.setTime(toDate);
	    //只要年月
	    int fromYear = from.get(Calendar.YEAR);
	    int fromMonth = from.get(Calendar.MONTH);

	    int toYear = to.get(Calendar.YEAR);
	    int toMonth = to.get(Calendar.MONTH);

	    int year = toYear  -  fromYear;
	    int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
	    int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));
	    return day;
	}
	
	
	
	
	
	@ApiOperation(value = "习惯分析结果")
    @RequestMapping(value = "/showHabitAnalyze", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> showHabitAnalyze(int id) throws ParseException{
		YhUserHabitGuide guide=yhUserHabitGuideMapper.selectByPrimaryKey(id);//查询分析报告
		int nut=dayCompare(guide.getBirthdate(), guide.getNewlyTime());//年龄
		String userName=guide.getUserName();//姓名
		int age=guide.getGender();//性别
		String userCode=guide.getUserCode();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userCode", guide.getUserCode());
		map.put("signidDate", guide.getStartTime());
		map.put("signidDate1", guide.getEndTime());
		Map<String,Object> map3 = new HashMap<String,Object>();
		map3.put("userCode", guide.getUserCode());
		map3.put("signidDate", guide.getStartTime());
		map3.put("signidDate1", guide.getEndTime());
		
		List<FoodArray> array=new ArrayList<FoodArray>();
		List<YhLifeHabitSign> Cartogram=yhLifeHabitSignMapper.showCartogram(map3);
		for (YhLifeHabitSign yhLifeHabitSign : Cartogram) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
 			String dateString = formatter.format(yhLifeHabitSign.getSignidDate());
 			Map<String,Object> map2 = new HashMap<String,Object>();
 			map2.put("signidDate", dateString);
 			map2.put("userCode", userCode);
 			int signdate=yhLifeHabitSignMapper.selectBySignDate(map2).size();//当天的总记录数
 			int nul=yhLifeHabitPersonDictMapper.selectBynullDict(userCode).size();//每天都要打卡的习惯数
 			int dict=yhLifeHabitPersonDictMapper.selectBydict(map2).size();//当前用户打卡的记录数
 			int zong=nul+dict;
			FoodArray foodArray=new FoodArray();
			foodArray.setTime(dateString);
			foodArray.setRecommend(String.valueOf(zong));
			foodArray.setNotoverweight(String.valueOf(signdate));
			foodArray.setBeenoverweight("0");
			array.add(foodArray);
		}
		
		
		int food=0;//饮食
 		int motion=0;//运动
 		int sleep=0;//睡眠
 		int elsew=0;//其它
 		int lifeadd=0;//是否补卡
		List<YhLifeHabitSign> sing=yhLifeHabitSignMapper.showpunch(map3);
		int signpunch=yhLifeHabitSignMapper.ShowDaysStatisticspunch(map3);//总持续打卡天数
		int food1=0;//饮食
 		int motion1=0;//运动
 		int sleep1=0;//睡眠
 		int elsew1=0;//其它
 		List<YhLifeHabitSign> showmyhabit=yhLifeHabitSignMapper.showmyhabit(map3);
 		for (YhLifeHabitSign yhLifeHabitSign : showmyhabit) {
 			if(yhLifeHabitSign.getCategoryName().equals("饮食")) {
				food1++;
			}else if(yhLifeHabitSign.getCategoryName().equals("运动")) {
				motion1++;
			}else if(yhLifeHabitSign.getCategoryName().equals("睡眠")) {
				sleep1++;
			}else if(yhLifeHabitSign.getCategoryName().equals("其它")) {
				elsew1++;
			}
		}
		List<YhLifeHabitSign> accumulativenum=yhLifeHabitSignMapper.accumulativenum(map3);//累计天数
		for (YhLifeHabitSign yhLifeHabitSign : sing) {
			if(yhLifeHabitSign.getCategoryName().equals("饮食")) {
				food++;
			}else if(yhLifeHabitSign.getCategoryName().equals("运动")) {
				motion++;
			}else if(yhLifeHabitSign.getCategoryName().equals("睡眠")) {
				sleep++;
			}else if(yhLifeHabitSign.getCategoryName().equals("其它")) {
				elsew++;
			}
			
			lifeadd+=yhLifeHabitSign.getLifeAdd();
		}
		
		List<YhLifeHabitPersonDict> list=yhLifeHabitPersonDictMapper.showdictdate(map);//查询出系统推荐项
		List<RecommendVojo> vojo=new ArrayList<RecommendVojo>();
		if(list!=null && !list.isEmpty()) {
			for (YhLifeHabitPersonDict yhLifeHabitPersonDict : list) {
				RecommendVojo mmen=new RecommendVojo();
				mmen.setHabitName(yhLifeHabitPersonDict.getHabitName());
				if(yhLifeHabitPersonDict.getBeginTime()!=null) {
					SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		  			Date time = ft.parse(yhLifeHabitPersonDict.getBeginTime());
		  			Date time1 = ft.parse(yhLifeHabitPersonDict.getFinishTim());
					int num=dayCompare1(time,time1);
					mmen.setSum(num);
				}
				Map<String,Object> map1 = new HashMap<String,Object>();
				map1.put("habitId", yhLifeHabitPersonDict.getHabitId());
				map1.put("userCode", guide.getUserCode());
				List<YhLifeHabitSign> complete=yhLifeHabitSignMapper.showSignCount(map1);//总完成天数
				if(complete!=null && !complete.isEmpty()) {
					mmen.setComplete(complete.size());
					Map<String,Object> map2 = new HashMap<String,Object>();
					map2.put("habitId", yhLifeHabitPersonDict.getHabitId());
					map2.put("userCode", guide.getUserCode());
					map2.put("signidDate", complete.get(0).getSignidDate());
					map2.put("signidDate1", complete.get(complete.size()-1).getSignidDate());
					int sustain=yhLifeHabitSignMapper.ShowDaysStatistics(map2);
					mmen.setSustain(sustain);
				}
				vojo.add(mmen);
			}
		}
		
	
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String time = ft.format(guide.getStartTime());
		String time1 = ft.format(guide.getEndTime());
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("nut", nut);
		map1.put("userName", userName);
		map1.put("age", age);
		map1.put("userCode", userCode);
		map1.put("vojo", vojo);
		map1.put("startTime", time);//开始时间
		map1.put("endTime", time1);//结束时间
		map1.put("punch", sing.size());//打卡数
 		map1.put("lifeadd", lifeadd);//补卡数
 		map1.put("signpunch", signpunch);//总持续打卡天数
 		map1.put("accumulativenum", accumulativenum.size());//累计天数
 		map1.put("showmyhabit", showmyhabit.size());
 		map1.put("food1", food1);
 		map1.put("motion1", motion1);
 		map1.put("sleep1", sleep1);
 		map1.put("elsew1", elsew1);
 		
		map1.put("food", food);
 		map1.put("motion", motion);
 		map1.put("sleep", sleep);
 		map1.put("elsew", elsew);
 		
 		map1.put("array", array);//能耗统计图
 		
		return map1;
	}
	
	
	 /**
 	 * 查询用户查看运动分析报告
 	 * @param page
 	 * @param limit
 	 * @return
 	 */
      @ApiOperation(value = "查询用户查看习惯分析报告")
      @RequestMapping(value = "/selectHabitlyze", method = RequestMethod.POST)
  	@ResponseBody
  	public Map<String,Object> selectHabitlyze(Integer page,Integer limit,String usercode){
  		Map<String,Object> map1 = new HashMap<String,Object>();
  		Pages pa = new Pages();
  		pa.setCurPage(page);
  		pa.setMaxResult(limit);
  		map1.put("page", pa.getFirstRows());
  		map1.put("limit", pa.getMaxResult());
  		map1.put("userCode", usercode);
  		
  		Map<String,Object> map = new HashMap<String,Object>();
  		List<YhUserHabitGuide> list = yhUserHabitGuideMapper.selectHabitlyze(map1);
  		for (YhUserHabitGuide yhUserHabitGuide : list) {
  			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
  			String time = ft.format(yhUserHabitGuide.getStartTime());
  			String time1 = ft.format(yhUserHabitGuide.getEndTime());
  			yhUserHabitGuide.setSource(time+"~"+time1);
		}
  		map.put("code", 20000);
  		map.put("msg", "");
  		map.put("count",yhUserHabitGuideMapper.selectHabitlyzeConut(map1));
  		map.put("data", list);
  		return map;
  	}
      
      
	public static void main(String[] args) {
		String str = "1,2,3";
        String[] strArr = str.split(",");
        System.out.println(strArr.length); //这里输出3
        for (int i = 0; i < strArr.length; ++i){
        System.out.println(strArr[i]);//这里输出a b c
        }
	}
	
	
	@ApiOperation(value = "删除习惯")
	   @RequestMapping(value = "/deletehabitId", method = RequestMethod.POST)
		@ResponseBody
	   public Result  deleteDictId(int id) { 
		 YhLifeHabitDict book=new YhLifeHabitDict();
		 book.setId(id);
		 book.setRemindTime("1");
		 int i=yhLifeHabitDictMapper.updateByPrimaryKeySelective(book);
	       return Result.ok(i);
	   }
	
	
	 @ApiOperation(value = "根据id查询习惯")
	   @RequestMapping(value = "/selecthabitId", method = RequestMethod.POST)
		@ResponseBody
	   public Result  selecthabitId(int id) { 
		 YhLifeHabitDict book=yhLifeHabitDictMapper.selectByPrimaryKey(id);
	       return Result.ok(book);
	   }
	
	
	@ApiOperation(value = "增加/修改习惯")
	   @RequestMapping(value = "/inserthabit", method = RequestMethod.POST)
		@ResponseBody
	   public Result  inserthabit(YhLifeHabitDict book) { 
		Date date=new Date();
		String[] strArr = book.getBeginTime().split(" - ");
		book.setBeginTime(strArr[0]);
		book.setFinishTime(strArr[1]);
		book.setLifeTime(date);
		 Subject subject = SecurityUtils.getSubject();
		 YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		 String usercode = principal.getUserCode();
		 book.setOrgCode(usercode);
		 int i;
		 if(book.getId()==0) {
			 i=yhLifeHabitDictMapper.insertSelective(book);
		 }else {
			 i=yhLifeHabitDictMapper.updateByPrimaryKeySelective(book);
		 }
	       return Result.ok(i);
	   }
	
	@ApiOperation(value = "查询习惯名称是否存在")
	@RequestMapping(value = "/selctbyhabitName", method = RequestMethod.GET)
	@ResponseBody
	public int selctCategoryName(String habitName,int id) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("habitName", habitName);
		map.put("id", id);
		int show = yhLifeHabitDictMapper.selctbyhabitName(map);
		return show;
	}
	
	
	   /**
	 	 *查询习惯
	 	 * @param page
	 	 * @param limit
	 	 * @return
	 	 */
	      @ApiOperation(value = "查询习惯")
	      @RequestMapping(value = "/selectbyhabit", method = RequestMethod.POST)
	  	@ResponseBody
	  	public Map<String,Object> selectfoodanalyze(Integer page,Integer limit,String habitType,String habitName){
	  		Map<String,Object> map1 = new HashMap<String,Object>();
	  		Pages pa = new Pages();
	  		pa.setCurPage(page);
	  		pa.setMaxResult(limit);
	  		map1.put("page", pa.getFirstRows());
	  		map1.put("limit", pa.getMaxResult());
	  		map1.put("habitType", habitType);
	  		map1.put("habitName", habitName);
	  		Map<String,Object> map = new HashMap<String,Object>();
	  		Subject subject = SecurityUtils.getSubject();
			 YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
			 String orgCode = principal.getOrgCode();
			 List<String> strings = yhFoodCookBookService.findjigouInfo(orgCode);
			 List<String> doctorInfo = yhFoodCookBookService.findDoctorInfo(orgCode);
	  		List<YhLifeHabitDict> list = yhLifeHabitDictMapper.selectbyhabit(map1);
	  		for (YhLifeHabitDict yhLifeHabitDict : list) {
	  			if(yhLifeHabitDict.getPeriod()==null) {
	  				yhLifeHabitDict.setExpect("--");
	  			}else {
	  				String str = yhLifeHabitDict.getPeriod();
	  		        String[] strArr = str.split(",");
	  		        if(strArr.length==7) {
	  		        	yhLifeHabitDict.setExpect("每日");
	  		        }else {
	  		        	String expe="每逢";
	  		        	 for (int i = 0; i < strArr.length; ++i){
	  		  		        if(strArr[i].equals("1")) {
		  		  		        if(i==strArr.length-1) {
		  		  		        expe+="周一";
		  		  		        }else {
		  		  		        expe+="周一/";
		  		  		        }
	  		  		        }else if(strArr[i].equals("2")) {
	  		  		        	if(i==strArr.length-1) {
			  		  		        expe+="周二";
			  		  		        }else {
			  		  		        expe+="周二/";
			  		  		      }
		  		  		    }else if(strArr[i].equals("3")) {
			  		  		    if(i==strArr.length-1) {
			  		  		        expe+="周三";
			  		  		        }else {
			  		  		        expe+="周三/";
			  		  		        }
		  		  		    }else if(strArr[i].equals("4")) {
			  		  		    if(i==strArr.length-1) {
			  		  		        expe+="周四";
			  		  		        }else {
			  		  		        expe+="周四/";
			  		  		        }
		  		  		    }else if(strArr[i].equals("5")) {
			  		  		    if(i==strArr.length-1) {
			  		  		        expe+="周五";
			  		  		        }else {
			  		  		        expe+="周五/";
			  		  		        }
		  		  		    }else if(strArr[i].equals("6")) {
			  		  		    if(i==strArr.length-1) {
			  		  		        expe+="周六";
			  		  		        }else {
			  		  		        expe+="周六/";
			  		  		        }
		  		  		    }else if(strArr[i].equals("7")) {
			  		  		    if(i==strArr.length-1) {
			  		  		        expe+="周日";
			  		  		        }else {
			  		  		        expe+="周日/";
			  		  		        }
		  		  		    }
	  		  		      }
	  		        	yhLifeHabitDict.setExpect(expe);
	  		        }
	  		       
	  			}
	  			
	  			
	  			String userCode = yhLifeHabitDict.getOrgCode();
				 if(StringUtil.isNotEmpty(userCode)){
					 for (int j = 0; j < strings.size(); j++) {
						 if(userCode.equals(strings.get(j))){
							 yhLifeHabitDict.setQuanxian(1);
						 }
					 }
					 for (int k = 0; k < doctorInfo.size(); k++) {
						 if(userCode.equals(doctorInfo.get(k))){
							 yhLifeHabitDict.setQuanxian(1);
						 }
					 }
				 }
	  			
			}
	  		map.put("code", 20000);
	  		map.put("msg", "");
	  		map.put("count",yhLifeHabitDictMapper.selectbyhabitCount(map1));
	  		map.put("data", list);
	  		return map;
	  	}
}
