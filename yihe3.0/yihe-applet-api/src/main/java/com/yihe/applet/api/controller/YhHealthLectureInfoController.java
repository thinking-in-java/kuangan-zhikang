package com.yihe.applet.api.controller;

import com.github.pagehelper.PageInfo;
import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.service.*;
import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.service.YhUserMakeCardService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * YhHealthLectureInfocontroller Created by dyb on 2020/1/6.
 */
@Controller
@RequestMapping("/api/YhHealthLectureInfo")
@Api(value = "YhHealthLectureInfo控制器", description = "YhHealthLectureInfo管理")
public class YhHealthLectureInfoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhHealthLectureInfoController.class);
	@Autowired
	YhUserReginfoAppService YhUserReginfoAppService;
	@Autowired
	YhHealthLectureInfoAppService yhHealthLectureInfoService;
	@Autowired
	YhHealthLectureCommentAppService yhHealthLectureCommentService;

	@Autowired
	YhHealthLectureCategoryAppService yhHealthLectureCategoryService;
	@Autowired
	YhHealthLectureWatchAppService  yhHealthLectureWatchAppService;
	@Autowired
	YhHabitPraiseService  YhHabitPraiseService;
	@Autowired
	YhOrgTreeAppService yhOrgTreeAppService;
	
	@Autowired
	YhUserMakeCardService yhUserMakeCardService;


	/**
	 * 健康大课堂搜索功能 搜索的范围（标题、标签、内容中的文字）String value, Integer curPage, Integer pageSize
	 */
	@RequestMapping(value = "/findInfoAllLike")
	@ResponseBody
	public Result findInfoAllLike(String likeValue, Integer curPage, Integer pageSize) {
		if (null == likeValue || null == curPage || pageSize == null) {
			return Result.error();
		}

		PageInfo<YhHealthLectureInfoApp> findhealthLectureTitleAndTipsAndInstruction = yhHealthLectureInfoService
				.findhealthLectureTitleAndTipsAndInstruction(likeValue, curPage, pageSize);
		return Result.ok(findhealthLectureTitleAndTipsAndInstruction.getList());
	}
	@RequestMapping(value = "/findInfoAllLikeByFoodSchool")
	@ResponseBody
	public Result findInfoAllLike(Integer curPage, Integer pageSize) {
		if (null == curPage || pageSize == null) {
			return Result.error();
		}
		
		PageInfo<YhHealthLectureInfoApp> findhealthLectureTitleAndTipsAndInstruction = yhHealthLectureInfoService
				.findhealthLectureByFoodSchool( curPage, pageSize);
		return Result.ok(findhealthLectureTitleAndTipsAndInstruction.getList());
	}
	/**
	 * 查询视频是否删除
	 * @param infoid 
	 * @return
	 */
	@RequestMapping(value = "/Isdelete")
	@ResponseBody
	public Result selectIsdeleteByParam (Integer infoid) {
		YhHealthLectureInfoApp selectByPrimaryKey = yhHealthLectureInfoService.selectByPrimaryKey(infoid);
		return Result.ok(selectByPrimaryKey);
		
	}  
	/**
	 * 首页查询最新三条健康大课堂数据
	 */
	@RequestMapping(value = "/findInfoLimitNew3")
	@ResponseBody
	public Result findInfoLimitNew3() {

		return Result.ok(yhHealthLectureInfoService.findInfoLimitNew3());
	}


	@RequestMapping(value = "/findInfoComponents")
	@ResponseBody
	public Result findInfoComponents(String cateName,Integer timeOrder,String titleLike,Integer type,Integer categoryId, Integer curPage, Integer pageSize,String userCode) {
		if (null == categoryId || null == curPage || null == pageSize) {
			return Result.error();
		}
		YhHealthLectureCategoryApp selectByPrimaryKey = yhHealthLectureCategoryService.selectByPrimaryKey(categoryId);
		if (null == selectByPrimaryKey || null == selectByPrimaryKey.getParentId()) {
			return Result.error();
		}
		List<YhHealthLectureInfoApp> selectByExample = new ArrayList<YhHealthLectureInfoApp>();
		if ("0".equals(selectByPrimaryKey.getParentId())) {
			selectByExample = yhHealthLectureInfoService.selectByExampleLimitParentComponents(cateName,userCode,timeOrder,titleLike,type,categoryId,
					(curPage - 1) * pageSize, pageSize);
		} else {
			selectByExample = yhHealthLectureInfoService.selectByExampleLimit3Components(cateName,userCode,timeOrder,titleLike,type,categoryId, (curPage - 1) * pageSize,
					pageSize);
		}

		for (int i = 0; i < selectByExample.size(); i++) {
			String tips = selectByExample.get(i).getTips();
			String title = selectByExample.get(i).getTitle();
			Integer classId = selectByExample.get(i).getInfoid();

			YhHealthLectureWatchApp selectclassIdByParam = yhHealthLectureWatchAppService.selectclassIdByParam(userCode, classId);
			if(selectclassIdByParam !=null) {
				selectByExample.get(i).setLooker(1);
			}else {
				selectByExample.get(i).setLooker(2);
			}
			if (null != title) {
				if (title.length() > 10) {
					selectByExample.get(i).setTitle(title.substring(0, 9) + "...");
				}
			}
			if (null != tips && !"".equals(tips)) {
				String[] split = tips.split(",");
				selectByExample.get(i).setTipsStr(split);
			}
		}
		return Result.ok(selectByExample);
	}


	@RequestMapping(value = "/findInfoAll")
	@ResponseBody
	public Result findInfoAll(Integer categoryId, Integer curPage, Integer pageSize,String userCode,Integer type,Integer timeOrder,String title) {
		if (null == categoryId || null == curPage || null == pageSize) {
			return Result.error();
		}
		YhHealthLectureCategoryApp selectByPrimaryKey = yhHealthLectureCategoryService.selectByPrimaryKey(categoryId);
		if (null == selectByPrimaryKey || null == selectByPrimaryKey.getParentId()) {
			return Result.error();
		}
		List<YhHealthLectureInfoApp> selectByExample = new ArrayList<YhHealthLectureInfoApp>();
		YhUserMakeCard yhUserMakeCard = yhUserMakeCardService.selectOrgIdbyParam(userCode);

		List<String> strings = yhOrgTreeAppService.allImmediateParentNodes(yhUserMakeCard.getOrgId(),new ArrayList<>());


		if ("0".equals(selectByPrimaryKey.getParentId())) {
			selectByExample = yhHealthLectureInfoService.selectByExampleLimitParent(categoryId,
					(curPage - 1) * pageSize, pageSize,userCode,type,timeOrder,title,strings);
		} else {
			selectByExample = yhHealthLectureInfoService.selectByExampleLimit3(categoryId, (curPage - 1) * pageSize,
					pageSize,userCode,type,timeOrder,title,strings);
		}

		for (int i = 0; i < selectByExample.size(); i++) {
			String tips = selectByExample.get(i).getTips();
			String title2 = selectByExample.get(i).getTitle();
			Integer classId = selectByExample.get(i).getInfoid();
			
			YhHealthLectureWatchApp selectclassIdByParam = yhHealthLectureWatchAppService.selectclassIdByParam(userCode, classId);
			if(selectclassIdByParam !=null) {
				selectByExample.get(i).setLooker(1);
			}else {
				selectByExample.get(i).setLooker(2);
			}
			if (null != title2) {
				/*if (title2.length() > 10) {
					selectByExample.get(i).setTitle(title2.substring(0, 9) + "...");
				}*/
			}
				if (null != tips && !"".equals(tips)) {
					selectByExample.get(i).setTips(tips);
				/*String[] split = tips.split(",");
				selectByExample.get(i).setTipsStr(split);*/
				}
		}
		return Result.ok(selectByExample);
	}

	@RequestMapping(value = "/findInfoAllByIdLimit")
	@ResponseBody	
	public Result findInfoAllByIdLimit(Integer categoryId, Integer curPage, Integer pageSize) {
		if (null == categoryId || null == curPage || null == pageSize) {
			return Result.error();
		}
		List<YhHealthLectureInfoApp> selectByExample = yhHealthLectureInfoService.selectByExampleLimit4(categoryId,
				(curPage - 1) * pageSize, pageSize);

		return Result.ok(selectByExample);
	}

	/*@RequestMapping(value = "/findInfoAllById")
	@ResponseBody
	public Result findInfoAllById(Integer categoryId) throws ParseException {
		if (null == categoryId) {
			return Result.error();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
		YhHealthLectureInfoApp selectByPrimaryKey = yhHealthLectureInfoService.selectByPrimaryKey(categoryId);
		// 查询评论
		
		List<YhHealthLectureCommentApp> selectAllparam = yhHealthLectureCommentService.selectAllparam(categoryId);
		
	
		if (null != selectAllparam) {
			selectByPrimaryKey.setComList(selectAllparam);
	
		}
		if (null != selectAllparam && selectAllparam.size() > 3) {
			((YhHealthLectureCommentApp) selectAllparam).setIsExceed(false);
		} else {
			((YhHealthLectureCommentApp) selectAllparam).setIsExceed(true);
		}
		if (null != ((YhHealthLectureInfoApp) selectAllparam).getAddinfotime()) {
			Date date = format.parse(((YhHealthLectureInfoApp) selectAllparam).getAddinfotime());
			((Object) selectAllparam).setAddinfotimeStr(format(date));
		}
		return Result.ok(selectAllparam);
	}
	*/

	@RequestMapping(value = "/findInfoAllById")
	@ResponseBody
	public Result findInfoAllById(Integer categoryId) throws ParseException {
		if (null == categoryId) {
			return Result.error();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
		YhHealthLectureInfoApp selectByPrimaryKey = yhHealthLectureInfoService.selectByPrimaryKey(categoryId);
		// 查询评论
		List<YhHealthLectureCommentApp> selectAllparam = yhHealthLectureCommentService.selectAllparam(categoryId);

		if (null != selectAllparam) {
			selectByPrimaryKey.setComList(selectAllparam);

		}
		if (null != selectAllparam && selectAllparam.size() > 3) {
			selectByPrimaryKey.setIsExceed(false);
		} else {
			selectByPrimaryKey.setIsExceed(true);
		}
		if (null != selectByPrimaryKey.getAddinfotime()) {
			Date date = format.parse(selectByPrimaryKey.getAddinfotime());
			selectByPrimaryKey.setAddinfotimeStr(format(date));
		}
		return Result.ok(selectByPrimaryKey);
	} 

	@RequestMapping(value = "/findInfoAllComById")
	@ResponseBody
	public Result findInfoAllComById(Integer categoryId,Integer type) throws ParseException {
		if (null == categoryId) {
			return Result.error();
		}
		// 查询评论

		List<YhHealthLectureCommentApp> selectByExample = yhHealthLectureCommentService.findCommentBylId(categoryId,type);
		if (null != selectByExample && selectByExample.size() > 0) {
			for (int i = 0; i < selectByExample.size(); i++) {
				// 查询评论楼中楼

				List<YhHealthLectureCommentApp> selectByExample2 = yhHealthLectureCommentService
						.findCommentByPid(selectByExample.get(i).getCommentid());
				if (null != selectByExample2 && selectByExample2.size() > 3) {
					selectByExample.get(i).setIsExceed(false);
				} else {
					selectByExample.get(i).setIsExceed(true);
				}
				
				selectByExample.get(i).setComsList(selectByExample2);
				String userCode = selectByExample.get(i).getUsercode();
				YhUserReginfoApp findYhUserReginfo = YhUserReginfoAppService.findYhUserReginfo(userCode);
				if(findYhUserReginfo.getGender()==1) {
					selectByExample.get(i).setDefaultImg("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
				}
				
				if(findYhUserReginfo.getGender()==2) {
					selectByExample.get(i).setDefaultImg("http://xcxqny.yhy.ren/xiaochengxu/toxiangnv.png");
				}
				
			}
		}

		return Result.ok(selectByExample);
	}
	
	
	
	@RequestMapping(value = "/findInfoAllComById1")
	@ResponseBody
	public Result findInfoAllComById1(Integer categoryId,Integer type,int curPage,int pageSize,String usercode) throws ParseException {
		if (null == categoryId) {
			return Result.error();
		}
		// 查询评论
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("lid", categoryId);
		map.put("status", type);
		if(curPage==0 && pageSize==0) {
			map.put("curPage", 0);
			map.put("pageSize", 10);
		}else {
			map.put("curPage", (curPage-1)*pageSize);
			map.put("pageSize", pageSize);
		}
		List<YhHealthLectureCommentApp> selectByExample = yhHealthLectureCommentService.findCommentBylId1(map);
		if (null != selectByExample && selectByExample.size() > 0) {
			for (int i = 0; i < selectByExample.size(); i++) {
				// 查询评论楼中楼

				List<YhHealthLectureCommentApp> selectByExample2 = yhHealthLectureCommentService
						.findCommentByPid(selectByExample.get(i).getCommentid());
				if (null != selectByExample2 && selectByExample2.size() > 3) {
					selectByExample.get(i).setIsExceed(false);
				} else {
					selectByExample.get(i).setIsExceed(true);
				}
				
				selectByExample.get(i).setComsList(selectByExample2);
				String userCode = selectByExample.get(i).getUsercode();
				YhUserReginfoApp findYhUserReginfo = YhUserReginfoAppService.findYhUserReginfo(userCode);
				if(findYhUserReginfo.getGender()==1) {
					selectByExample.get(i).setDefaultImg("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
				}
				
				if(findYhUserReginfo.getGender()==2) {
					selectByExample.get(i).setDefaultImg("http://xcxqny.yhy.ren/xiaochengxu/toxiangnv.png");
				}
				
				
				int dotnum=YhHabitPraiseService.showCountParise(String.valueOf(selectByExample.get(i).getCommentid()));
				selectByExample.get(i).setDotnum(String.valueOf(dotnum));
				
				if(usercode!=null) {
					Map<String, Object> map1=new HashMap<String, Object>(); 
					map1.put("commentid", selectByExample.get(i).getCommentid());
					map1.put("userCode", usercode);
					int zongnum=YhHabitPraiseService.showCountPariseCode(map1);
					if(zongnum>=1) {
						selectByExample.get(i).setWhetherwo(true);
					}else {
						selectByExample.get(i).setWhetherwo(false);
					}
				}
				
			}
		}

		return Result.ok(selectByExample);
	}
	

	@RequestMapping(value = "/selectByPrimaryKey")
	@ResponseBody
	public Result selectByPrimaryKey(String categoryId) {
		return Result.ok(yhHealthLectureInfoService.selectByPrimaryKey(Integer.parseInt(categoryId)));
	}

	@RequestMapping(value = "/findInfoAllByLiKeLimit")
	@ResponseBody
	public Result findInfoAllByLiKeLimit(String like, Integer curPage, Integer pageSize) {
		if (null == like || null == curPage || null == pageSize) {
			return Result.error();
		}
		List<YhHealthLectureInfoApp> selectByExample = yhHealthLectureInfoService.selectByExampleLimitAndLike(like,
				(curPage - 1) * pageSize, pageSize);

		return Result.ok(selectByExample);
	}

	private static final long ONE_MINUTE = 60000L;
	private static final long ONE_HOUR = 3600000L;
	private static final long ONE_DAY = 86400000L;
	private static final long ONE_WEEK = 604800000L;

	private static final String ONE_SECOND_AGO = "秒前";
	private static final String ONE_MINUTE_AGO = "分钟前";
	private static final String ONE_HOUR_AGO = "小时前";
	private static final String ONE_DAY_AGO = "天前";
	private static final String ONE_MONTH_AGO = "月前";
	private static final String ONE_YEAR_AGO = "年前";

	private static final String FORMATE_DATE_STR = null;

	private static long toSeconds(long date) {
		return date / 1000L;
	}

	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}

	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}

	private static long toDays(long date) {
		return toHours(date) / 24L;
	}

	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}

	private static long toYears(long date) {

		return toMonths(date) / 365L;
	}

	public static String format(Date date) {
		long delta = new Date().getTime() - date.getTime();
		if (delta < 1L * ONE_MINUTE) {
			long seconds = toSeconds(delta);
			return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
		}
		if (delta < 45L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}
		if (delta < 48L * ONE_HOUR) {
			return "昨天";
		}
		if (delta < 30L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		}
		if (delta < 12L * 4L * ONE_WEEK) {
			long months = toMonths(delta);
			return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
		} else {
			long years = toYears(delta);
			return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
		}
	}

	public static Long dateDiff(String startTime, String endTime, String format, String str) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		// 获得两个时间的毫秒时间差异
		try {
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh + day * 24;// 计算差多少小时
			min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒
			// 输出结果
			System.out.println(
					"时间相差：" + day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟" + sec + "秒。");
			System.out.println("hour=" + hour + ",min=" + min);
			if (str.equalsIgnoreCase("h")) {
				return hour;
			} else {
				return min;
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (str.equalsIgnoreCase("h")) {
			return hour;
		} else {
			return min;
		}
	}

	public static void main(String[] args) throws ParseException {
		String times = "2020-06-28 08:21:04";
		String times2 = "2020-06-28 08:24:51";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(times);
		Date date2 = sdf.parse(times2);
			long hour=0;//小时
			long min=0;//分钟
		 long sec = 0;  //秒
			long nd = 1000 * 24 * 60 * 60;
			long nh = 1000 * 60 * 60;
			long nm = 1000 * 60;
			long ns = 1000;// 一秒钟的毫秒数     
			// long ns = 1000;
			// 获得两个时间的毫秒时间差异
			long diff = date2.getTime() - date.getTime();
			// 计算差多少小时
			 hour+= diff % nd / nh;
			// 计算差多少分钟
			min+= diff % nd % nh / nm;
			
		sec += diff % nd % nh % nm / ns;// 计算差多少秒     
				
		System.out.println("分钟"+min);
		System.out.println("秒"+sec);
	}
	
	/* 
	 * 将时间戳转换为时间
	 */
	/*	public static String stampToDate(String s) {
			String res;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long lt = new Long(s);
			Date date = new Date(lt);
			res = simpleDateFormat.format(date);
			return res;
		}*/
	/*	public static int getAge(Date dateOfBirth) {
			
			       int age = 0;
			
			       Calendar born = Calendar.getInstance();
			
			       Calendar now = Calendar.getInstance();
			
			       if (dateOfBirth != null) {
			
			           now.setTime(new Date());
			
			           born.setTime(dateOfBirth);
			
			           if (born.after(now)) {
			
			               throw new IllegalArgumentException("年龄不能超过当前日期");
			
			           }
			           age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			
			           int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
			
			           int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
			
			           System.out.println("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);
			
			           if (nowDayOfYear < bornDayOfYear) {
			
			               age -= 1;
			
			           }
			
			       }
			
			       return age;
			
			   }*/
		
		
		
	/*   public static void main(String[] args) throws Exception {
	
	       SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
	
	       java.util.Date mydate = myFormatter.parse("2013-06-15");
	
	       System.out.println(getAge(mydate));
	
	   }*/

}
