/**
 * 
 */
package com.yihe.applet.api.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.SportEncouraeImgVojo;
import com.yihe.applet.api.domain.YhSportEncourageApp;
import com.yihe.applet.api.domain.YhUserSportChRecordApp;
import com.yihe.applet.api.domain.YhUserSportChallengeApp;
import com.yihe.applet.api.mapper.YhUserSportChRecordAppMapper;
import com.yihe.applet.api.service.YhSportEncourageAppService;
import com.yihe.applet.api.util.common.util.Result;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhUserReginfoMapper;

import io.swagger.annotations.Api;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/YhSportEncourageAppController")
@Api(value = "YhSportEncourageAppController控制器", description = "YhSportEncourageAppController管理")
public class YhSportEncourageAppController {

	 @Autowired
	 YhSportEncourageAppService yhSportEncourageAppService;
	 
	 @Autowired
	YhUserSportChRecordAppMapper yhUserSportChRecordAppMapper;
	 
	 @Autowired
	 YhUserReginfoMapper yhUserReginfoMapper;
	 
	 
	 	/**
	 	 * 增加助威
	 	 * @param chid
	 	 * @param userCode
	 	 * @return
	 	 */
		@RequestMapping(value = "/addcheer")
		@ResponseBody
		public Result addcheer(int chid,String userCode) {
			YhSportEncourageApp enc=new YhSportEncourageApp();
			enc.setChId(chid);
			enc.setUserCode(userCode);
			enc.setTimeAdd(new Date());
			int i=yhSportEncourageAppService.insertSelective(enc);
			return Result.ok(i);
		}
	 
		/**
		 * 查询还在执行的数据
		 * @param userCode
		 * @return
		 */
		@RequestMapping(value = "/selectByDnow")
		@ResponseBody
		public Result selectByDnow(int curPage,int pageSize,String userCode) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("curPage", (curPage-1)*pageSize);
			map.put("pageSize", pageSize);
			List<YhUserSportChallengeApp>  selectByPrimaryKey = yhSportEncourageAppService.selectByDnow(map);
			Date date=new Date();
			for (YhUserSportChallengeApp yhUserSportChallengeApp : selectByPrimaryKey) {
				
				if(yhUserSportChallengeApp.getPostCode()==null) {
        			if(yhUserSportChallengeApp.getGender()==1) {
        				yhUserSportChallengeApp.setPostCode("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
        			}else {
        				yhUserSportChallengeApp.setPostCode("http://xcxqny.yhy.ren/xiaochengxu/toxiangnv.png");
        			}
        		}
				
				
				int diff=differentDaysByMillisecond(yhUserSportChallengeApp.getChBeginTime(),date);//2个时间之间的天数
				yhUserSportChallengeApp.setBackups(String.valueOf((diff+1)));//第几天
				yhUserSportChallengeApp.setBfb(String.valueOf(((diff+1)/7d)*100)+"%");
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		        String dateString = formatter.format(date);
		        Map<String, Object> map1=new HashMap<String, Object>();
		        map1.put("chId", yhUserSportChallengeApp.getChId());
		        map1.put("uploadTome", dateString);
		        YhUserSportChRecordApp list=yhUserSportChRecordAppMapper.showChidRecordMap(map1);
		        if(list==null) {
		        	yhUserSportChallengeApp.setChAverageStep("0");//当天步数
		        }else {
		        	yhUserSportChallengeApp.setChAverageStep(list.getStep());//当天步数
		        }
		        Map<String, Object> map2=new HashMap<String, Object>();
		        map2.put("userCode", userCode);
		        map2.put("timeAdd", dateString);
		        map2.put("chId", yhUserSportChallengeApp.getChId());
		        int count=yhSportEncourageAppService.showEncourageNum(map2);
		        if(count==0) {
		        	yhUserSportChallengeApp.setBackups1("0");//今日是否助威0没有
		        }else {
		        	yhUserSportChallengeApp.setBackups1("1");//今日是否助威1有
		        }
		        
		        
		        List<YhSportEncourageApp> enco=yhSportEncourageAppService.selectByChidSall(yhUserSportChallengeApp.getChId());
		        yhUserSportChallengeApp.setBackups2(String.valueOf(enco.size()));//助威次数
		        List<SportEncouraeImgVojo> imgvo=new ArrayList<SportEncouraeImgVojo>();
		        if(enco.size()>10) {//头像
		        	for (int i = 0; i < 10; i++) {
		        		SportEncouraeImgVojo vojo=new SportEncouraeImgVojo();
		        		YhUserReginfo reginfo=yhUserReginfoMapper.findYhUserReginfo(enco.get(i).getUserCode());
		        		if(reginfo.getPostCode()==null) {
		        			if(reginfo.getGender()==1) {
		        				vojo.setImg("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
		        			}else {
		        				vojo.setImg("http://xcxqny.yhy.ren/xiaochengxu/toxiangnv.png");
		        			}
		        		}else {
		        			vojo.setImg(reginfo.getPostCode());
		        		}
		        		imgvo.add(vojo);
					}
		        }else {
		        	 for (YhSportEncourageApp yhSportEncourageApp : enco) {
		        		 SportEncouraeImgVojo vojo=new SportEncouraeImgVojo();
			        		YhUserReginfo reginfo=yhUserReginfoMapper.findYhUserReginfo(yhSportEncourageApp.getUserCode());
			        		if(reginfo.getPostCode()==null) {
			        			vojo.setImg("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
			        		}else {
			        			vojo.setImg(reginfo.getPostCode());
			        		}
			        		imgvo.add(vojo);
					}
		        }
		        
		        yhUserSportChallengeApp.setImg(imgvo);
		        
			}
			return Result.ok(selectByPrimaryKey);
		}
		
		
		/**
		* 通过时间秒毫秒数判断两个时间的间隔
		* @param date1
		* @param date2
		* @return
		*/
		public static int differentDaysByMillisecond(Date date1,Date date2)
		{
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
		return days;
		}
}
