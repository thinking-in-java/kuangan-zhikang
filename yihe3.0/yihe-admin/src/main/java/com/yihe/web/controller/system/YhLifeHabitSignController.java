/**
 * 
 */
package com.yihe.web.controller.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ibm.icu.text.SimpleDateFormat;
import com.yihe.system.domain.HabitContentVojo;
import com.yihe.system.domain.HabitDiaryVojo;
import com.yihe.system.domain.YHUserSportDiaryVOJO;
import com.yihe.system.domain.YhLifeHabitPersonDict;
import com.yihe.system.domain.YhLifeHabitSign;
import com.yihe.system.domain.YhSportDiary;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhLifeHabitPersonDictMapper;
import com.yihe.system.mapper.YhLifeHabitSignMapper;
import com.yihe.system.service.HabitDiaryVojoService;
import com.yihe.system.service.YhUserSportsGuideService;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 习惯日志管理
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhLifeHabitSignController")
public class YhLifeHabitSignController {

	  @Autowired
	  private YhLifeHabitSignMapper yhLifeHabitSignMapper;
	  
	  @Autowired
	  private YhLifeHabitPersonDictMapper yhLifeHabitPersonDictMapper;
	  
	  
	  @Autowired
	  private HabitDiaryVojoService habitDiaryVojoService;
	  
	  

	     @RequestMapping(value = "/selectHabitDiaryUserAll", method = RequestMethod.GET)
	 	@ResponseBody
	 	public Map<String,Object> selectHabitDiaryUserAll(@RequestParam(required = false) String nameLike,
				@RequestParam(required = false) String usernameLike, @RequestParam(required = false) String type,
				@RequestParam(required = false) String strTime, @RequestParam(required = false) String endTime,
				@RequestParam(required = false) String startTime,
				@RequestParam(required = false) String standard,
				@RequestParam(required = false, defaultValue = "1") int page,
				@RequestParam(required = false, defaultValue = "5") int limit) {

			if (StringUtil.isNotEmpty(startTime)) {
				String[] split = startTime.split(" - ");
				strTime = split[0];
				endTime = split[1];
			}
			
			
			
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
			String usercode = principal.getUserCode();
	 		PageInfo<HabitDiaryVojo> selectHabitDiaryUserAll = habitDiaryVojoService.selectHabitDiaryUserAll(usercode,nameLike,usernameLike,strTime,endTime,standard, page, limit);
	 		List<HabitDiaryVojo> list = selectHabitDiaryUserAll.getList();
	 		for (HabitDiaryVojo habitDiaryVojo : list) {
	 			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	 			String dateString = formatter.format(habitDiaryVojo.getDiaryTime());
	 			Map<String,Object> map2 = new HashMap<String,Object>();
	 			map2.put("signidDate", dateString);
	 			map2.put("userCode", habitDiaryVojo.getUsercode());
	 			int signdate=yhLifeHabitSignMapper.selectBySignDate(map2).size();//当天的总记录数
	 			int nul=yhLifeHabitPersonDictMapper.selectBynullDict1(map2).size();//每天都要打卡的习惯数
	 			//int dict=yhLifeHabitPersonDictMapper.selectBydict(map2).size();//当前用户打卡的记录数
	 			habitDiaryVojo.setSchedule(signdate+"/"+(nul));
	 			habitDiaryVojo.setComplete(signdate);
	 			habitDiaryVojo.setSum((nul));
	 			if(signdate>=nul){
	 				habitDiaryVojo.setFinish("是");
	             }else{
	            	 habitDiaryVojo.setFinish("否");
	             }
	 			
	 			
			}
	 		Map<String, Object> rsMap = new HashMap<String, Object>();
			rsMap.put("count", selectHabitDiaryUserAll.getTotal());
			rsMap.put("code", 0);
			rsMap.put("msg", "ok");
			rsMap.put("data", list);
			return rsMap;
	}
	  
	  
	  
	  /**
	   * 查询习惯日志内容
	   * @param userCode
	   * @param signidDate
	   * @return
	   */
	  @ApiOperation(value = "查询习惯日志内容")
	     @RequestMapping(value = "/selectByhabitontent", method = RequestMethod.POST)
	 	@ResponseBody
	 	public List<HabitContentVojo> selectByhabitContent(String userCode,String signidDate){
		  List<HabitContentVojo> cont=new ArrayList<HabitContentVojo>();
			Map<String,Object> map2 = new HashMap<String,Object>();
			map2.put("signidDate", signidDate);
			map2.put("userCode", userCode);
			List<YhLifeHabitPersonDict> nul=yhLifeHabitPersonDictMapper.selectBynullDict(userCode);//每天都要打卡的习惯数
			if(nul!=null && !nul.isEmpty()) {
				for (YhLifeHabitPersonDict yhLifeHabitPersonDict : nul) {
					 HabitContentVojo vojo=new HabitContentVojo();
					 vojo.setHabitId(yhLifeHabitPersonDict.getHabitId());
					vojo.setName(yhLifeHabitPersonDict.getHabitName());
					vojo.setWhether(2);
					cont.add(vojo);
				}
			}
			List<YhLifeHabitPersonDict> dict=yhLifeHabitPersonDictMapper.selectBydict(map2);//当前用户打卡的记录数
			if(dict!=null && !dict.isEmpty()) {
				for (YhLifeHabitPersonDict yhLifeHabitPersonDict : dict) {
					 HabitContentVojo vojo=new HabitContentVojo();
						vojo.setName(yhLifeHabitPersonDict.getHabitName());
						vojo.setHabitId(yhLifeHabitPersonDict.getHabitId());
						vojo.setWhether(2);
						cont.add(vojo);
				}
			}

			  List<YhLifeHabitSign> signdate=yhLifeHabitSignMapper.selectBySignDate(map2);//当天的录数
			  if(signdate!=null && !signdate.isEmpty()) {
				  for (HabitContentVojo habitContentVojo : cont) {
						for (YhLifeHabitSign yhLifeHabitSign : signdate) {
							if(habitContentVojo.getHabitId()==yhLifeHabitSign.getHabitId()) {
								habitContentVojo.setWhether(1);
							}
						}
					}
			  }
			return cont;
		  
	  }
	  
	  
	  
	  @ApiOperation(value = "查询用户查看习惯记录分类")
	     @RequestMapping(value = "/selectBySign", method = RequestMethod.POST)
	 	@ResponseBody
	 	public Map<String,Object> selectBySign(String userCode,String signidDate){
		  Map<String,Object> map1 = new HashMap<String,Object>();
	 		map1.put("userCode", userCode);
	 		map1.put("signidDate", signidDate);
	 		int food=0;//饮食
	 		int motion=0;//运动
	 		int sleep=0;//睡眠
	 		int elsew=0;//其它
	 		
	 		int matinal=0;//清晨
	 		int forenoon=0;//上午
	 		int afternoon=0;//下午
	 		int noon=0;//中午
	 		int night=0;//晚上
	 		int late=0;//深夜
	 		int dawn=0;//凌晨
	 		 
	 		List<YhLifeHabitSign> list = yhLifeHabitSignMapper.selectBySign(map1);
	 		for (YhLifeHabitSign yhLifeHabitSign : list) {
				if(yhLifeHabitSign.getCategoryName().equals("饮食")) {
					food++;
				}else if(yhLifeHabitSign.getCategoryName().equals("运动")) {
					motion++;
				}else if(yhLifeHabitSign.getCategoryName().equals("睡眠")) {
					sleep++;
				}else if(yhLifeHabitSign.getCategoryName().equals("其它")) {
					elsew++;
				}
			    SimpleDateFormat df = new SimpleDateFormat("HH");
		        String str = df.format(yhLifeHabitSign.getIncreaseTime());
		        int a = Integer.parseInt(str);
		        if(a >=5 && a<=8) {//清晨
		        	matinal++;
		        }else if(a >=8 && a<=12) {//上午
		        	forenoon++;
		        }else if(a >=12 && a<=14) {//中午
		        	noon++;
		        }else if(a >=14 && a<=18) {//下午
		        	afternoon++;
		        }else if(a >=18 && a<=22) {//晚上
		        	night++;
		        }else if(a >=22 && a<=24) {//深夜
		        	late++;
		        }else if(a >=0 && a<=5) {//凌晨
		        	dawn++;
		        }	
		       
			}
	 		
	 		Map<String,Object> map = new HashMap<String,Object>();
	 		map.put("food", food);
	 		map.put("motion", motion);
	 		map.put("sleep", sleep);
	 		map.put("elsew", elsew);
	 		
	 		map.put("matinal", matinal);
	 		map.put("forenoon", forenoon);
	 		map.put("noon", noon);
	 		map.put("afternoon", afternoon);
	 		map.put("night", night);
	 		map.put("late", late);
	 		map.put("dawn", dawn);
			return map;
	  }
	  
	  
	  
	  /**
	     * 查询用户查看习惯记录内容统计图
	     * @param usercode
	     * @param ctime
	     * @return
	     */
	     @ApiOperation(value = "查询用户查看习惯记录内容统计图")
	     @RequestMapping(value = "/selectHabitSignCtime", method = RequestMethod.POST)
	 	@ResponseBody
	 	public Map<String,Object> selectSportDiaryCtime(String userCode,String signidDate,String standard){
	 		Map<String,Object> map1 = new HashMap<String,Object>();
	 		map1.put("userCode", userCode);
	 		if(signidDate!=null && !signidDate.equals("")) {
	 			String[] strArr = signidDate.split(" - ");
	 		map1.put("signidDate", strArr[0]);
	 		map1.put("signidDate1", strArr[1]);
	 		}
	 		
	 		int nul=yhLifeHabitPersonDictMapper.selectBynullDict(userCode).size();//每天都要打卡的习惯数
	 		if(standard!=null && !standard.equals("")) {
	 			
	 			if(standard.equals("1")) {
	 				map1.put("standard", standard);
	 	 			map1.put("nul", nul);
	 			}else {
	 				map1.put("standard1", standard);
	 	 			map1.put("nul", nul);
	 			}
	 			
	 		}
	 		
	 		
	 		List<YhLifeHabitSign> list = yhLifeHabitSignMapper.selectHabitSignCtime(map1);
	 		Collections.reverse(list);
	 		String[] cTime = new String[list.size()];
	 		String[] energyLevel = new String[list.size()];
	 		String[] energyKcal = new String[list.size()];
	 		int i=0;
	 		for (YhLifeHabitSign yhLifeHabitSign : list) {
	 			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	 			String dateString = formatter.format(yhLifeHabitSign.getSignidDate());
	 			Map<String,Object> map2 = new HashMap<String,Object>();
	 			map2.put("signidDate", dateString);
	 			map2.put("userCode", userCode);
	 			int signdate=yhLifeHabitSignMapper.selectBySignDate(map2).size();//当天的总记录数
	 			int dict=yhLifeHabitPersonDictMapper.selectBydict(map2).size();//当前用户打卡的记录数
	 			int zong=nul+dict;
	 			i++;
	 			cTime[i-1]=""+dateString+"";
	 			energyLevel[i-1]=""+zong+"";
	 			energyKcal[i-1]=""+signdate+"";
			}
	 		Map<String,Object> map = new HashMap<String,Object>();
	 		map.put("cTime", cTime);
	 		map.put("energyLevel", energyLevel);
	 		map.put("energyKcal",energyKcal);
	 		return map;
	 	}
	  
	  
	/**
	 * 查询用户查看习惯记录内容
	 * @param page
	 * @param limit
	 * @param usercode 用户id
	 * @param selectSignDiary 时间
	 * @return
	 */
     @ApiOperation(value = "查询用户查看习惯记录内容")
     @RequestMapping(value = "/selectSignDiary", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectSportDiary(Integer page,Integer limit,String userCode,String signidDate,String standard){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		Pages pa = new Pages();
 		pa.setCurPage(page);
 		pa.setMaxResult(limit);
 		map1.put("page", pa.getFirstRows());
 		map1.put("limit", pa.getMaxResult());
 		map1.put("userCode", userCode);
 		if(signidDate!=null && !signidDate.equals("")) {
 			String[] strArr = signidDate.split(" - ");
 		map1.put("signidDate", strArr[0]);
 		map1.put("signidDate1", strArr[1]);
 		}
 		int nul=yhLifeHabitPersonDictMapper.selectBynullDict(userCode).size();//每天都要打卡的习惯数
 		
 		if(standard!=null && !standard.equals("")) {
 			
 			if(standard.equals("1")) {
 				map1.put("standard", standard);
 	 			map1.put("nul", nul);
 			}else {
 				map1.put("standard1", standard);
 	 			map1.put("nul", nul);
 			}
 			
 		}
 		
 		Map<String,Object> map = new HashMap<String,Object>();
 		List<YhLifeHabitSign> list = yhLifeHabitSignMapper.selectSignDiary(map1);
 		for (YhLifeHabitSign yhLifeHabitSign : list) {
 			
 			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
 			String dateString = formatter.format(yhLifeHabitSign.getSignidDate());
 			Map<String,Object> map2 = new HashMap<String,Object>();
 			map2.put("signidDate", dateString);
 			map2.put("userCode", userCode);
 			int signdate=yhLifeHabitSignMapper.selectBySignDate(map2).size();//当天的总记录数
 			
 			int dict=yhLifeHabitPersonDictMapper.selectBydict(map2).size();//当前用户打卡的记录数
 			yhLifeHabitSign.setSchedule(signdate+"/"+(nul+dict));
 			yhLifeHabitSign.setComplete(signdate);
 			yhLifeHabitSign.setSum((nul+dict));
		}
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",yhLifeHabitSignMapper.selectSignDiaryCount(map1).size());
 		map.put("data", list);
 		return map;
 	}
}
