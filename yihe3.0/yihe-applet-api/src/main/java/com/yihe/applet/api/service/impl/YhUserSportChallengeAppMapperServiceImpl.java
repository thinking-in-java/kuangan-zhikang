/**
 * 
 */
package com.yihe.applet.api.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;
import com.yihe.applet.api.domain.SportEncouraeImgVojo;
import com.yihe.applet.api.domain.YhSportEncourageApp;
import com.yihe.applet.api.domain.YhUserSportChRecordApp;
import com.yihe.applet.api.domain.YhUserSportChallengeApp;
import com.yihe.applet.api.mapper.YhUserSportChRecordAppMapper;
import com.yihe.applet.api.mapper.YhUserSportChallengeAppMapper;
import com.yihe.applet.api.service.YhSportEncourageAppService;
import com.yihe.applet.api.service.YhUserSportChallengeAppMapperService;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhUserReginfoMapper;

/**
 * @author dong
 *
 */
@Service
public class YhUserSportChallengeAppMapperServiceImpl implements YhUserSportChallengeAppMapperService{

	@Autowired
	YhUserSportChallengeAppMapper YhUserSportChallengeAppMapper;
	
	@Autowired
	YhUserSportChRecordAppMapper yhUserSportChRecordAppMapper;
	
	 @Autowired
	 YhSportEncourageAppService yhSportEncourageAppService;

	 @Autowired
	 YhUserReginfoMapper yhUserReginfoMapper;
	
	@Override
	public int insertSelective(YhUserSportChallengeApp record) {
		// TODO Auto-generated method stub
		return YhUserSportChallengeAppMapper.insertSelective(record);
	}

	
	@Override
	public Map<String, Object> selectByCodeDnow(String userCode) {
		// TODO Auto-generated method stub
		Map<String, Object> map=new HashMap<String, Object>();
		YhUserSportChallengeApp chall=YhUserSportChallengeAppMapper.selectByCodeDnow(userCode);
		if(chall==null) {
			map.put("type", 1);
		}else {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			   String chFiaishTime = formatter.format(chall.getChFiaishTime());
			   chall.setBackups(chFiaishTime);
			map.put("type", 2);
			map.put("chall", chall);
			Date date=new Date();
			int diff=differentDaysByMillisecond(chall.getChBeginTime(),date);//2个时间之间的天数
			map.put("diff", (diff+1));//第几天
			List<YhUserSportChRecordApp> list=yhUserSportChRecordAppMapper.showChidRecord(chall.getChId());
			map.put("accomplish", list.size());//完成几天
			  Date currentTime = new Date();
			   String dateString = formatter.format(currentTime);
			 Map<String, Object> map1=new HashMap<String, Object>();
			 map1.put("chId", chall.getChId());
			 map1.put("uploadTome", dateString);
			YhUserSportChRecordApp cord=yhUserSportChRecordAppMapper.showChidRecordMap(map1);
			if(cord==null) {
				map.put("step",0);//步数
			}else {
				map.put("step",cord.getStep());//步数
			}
			
			
			
			List<YhSportEncourageApp> enco=yhSportEncourageAppService.selectByChidSall(chall.getChId());
	        map.put("number",enco.size());//助威次数
	        List<SportEncouraeImgVojo> imgvo=new ArrayList<SportEncouraeImgVojo>();
	        if(enco.size()>10) {//头像
	        	for (int i = 0; i < 10; i++) {
	        		SportEncouraeImgVojo vojo=new SportEncouraeImgVojo();
	        		YhUserReginfo reginfo=yhUserReginfoMapper.findYhUserReginfo(enco.get(i).getUserCode());
	        		if(reginfo.getPostCode()==null) {
	        			if(reginfo.getGender()==1) {
	        				vojo.setImg("https://qny.guyijk.com/xiaochengxu/toxiangnan.png");
	        			}else {
	        				vojo.setImg("https://qny.guyijk.com/xiaochengxu/toxiangnv.png");
	        			}
	        			
	        		}else {
	        			vojo.setImg(reginfo.getPostCode());
	        		}
	        		vojo.setName(reginfo.getName());
	        		imgvo.add(vojo);
	        		
				}
	        }else {
	        	 for (YhSportEncourageApp yhSportEncourageApp : enco) {
	        		 SportEncouraeImgVojo vojo=new SportEncouraeImgVojo();
		        		YhUserReginfo reginfo=yhUserReginfoMapper.findYhUserReginfo(yhSportEncourageApp.getUserCode());
		        		if(reginfo.getPostCode()==null) {
		        			vojo.setImg("https://qny.guyijk.com/xiaochengxu/toxiangnan.png");
		        		}else {
		        			vojo.setImg(reginfo.getPostCode());
		        		}
		        		vojo.setName(reginfo.getName());
		        		imgvo.add(vojo);
				}
	        }
	        map.put("topNavs",imgvo);//头像
			
		}
		return map;
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


	@Override
	public List<YhUserSportChallengeApp> selectByCodeXnow(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return YhUserSportChallengeAppMapper.selectByCodeXnow(map);
	}


	@Override
	public YhUserSportChallengeApp selectByPrimaryKey(Integer chId) {
		// TODO Auto-generated method stub
		return YhUserSportChallengeAppMapper.selectByPrimaryKey(chId);
	}


	@Override
	public int selectByWhetherCount(int chId) {
		// TODO Auto-generated method stub
		return yhUserSportChRecordAppMapper.selectByWhetherCount(chId);
	}


	@Override
	public int updateByPrimaryKeySelective(YhUserSportChallengeApp record) {
		// TODO Auto-generated method stub
		return YhUserSportChallengeAppMapper.updateByPrimaryKeySelective(record);
	}


	@Override
	public int showChWhetherNow(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return YhUserSportChallengeAppMapper.showChWhetherNow(map);
	}

	
}
