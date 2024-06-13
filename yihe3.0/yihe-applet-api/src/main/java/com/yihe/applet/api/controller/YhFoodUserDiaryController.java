package com.yihe.applet.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.icu.math.BigDecimal;
import com.yihe.applet.api.domain.*;
import com.yihe.applet.api.mapper.YhFoodCategoryApiMapper;
import com.yihe.applet.api.mapper.YhFoodCookbookCollectionMapper;
import com.yihe.applet.api.mapper.YhFoodCookedMapper;
import com.yihe.applet.api.mapper.YhUserFoodWeeklyMapper;
import com.yihe.applet.api.service.YhFoodCookbook2Service;
import com.yihe.applet.api.service.YhFoodSrecipeRecordAppService;
import com.yihe.applet.api.service.impl.*;
import com.yihe.applet.api.util.QiNiuUtil;
import com.yihe.applet.api.util.common.util.BaiDuDish;
import com.yihe.applet.api.util.common.util.DateTest;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhFoodUserScrecipeMapper;
import com.yihe.system.service.impl.YhFoodSrecipeServiceImpl;
import com.yihe.system.service.impl.YhFoodUserDiaryServiceImpl;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * YhSportDiarycontroller Created by dong on 2020/2/24.
 */
@Controller
@RequestMapping("/api/YhFoodUserDiary")
@Api(value = "YhFoodDiary控制器", description = "YhFoodDiary管理")
public class YhFoodUserDiaryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhFoodUserDiaryController.class);

	@Autowired
	YhFoodUserDiaryServiceImpl YhFoodUserDiaryServiceImpl;

	@Autowired
	YhFoodSrecipeRecordAppServiceImpl YhFoodSrecipeRecordAppServiceImpl;

	@Autowired
	YhFoodSrecipeServiceImpl YhFoodSrecipeServiceImpl;
	@Autowired
	private YhFoodUserScrecipeMapper yhFoodUserScrecipeMapper;

	@Autowired
	private YhUserReginfoAppServiceImpl YhUserReginfoAppServiceImpl;
	
	@Autowired
	private YhFoodCookbookCollectionMapper YhFoodCookbookCollectionMapper;
	
	@Autowired
	private YhFoodCookbook2Service YhFoodCookbook2Service;
	
	@Autowired
	YhFoodSrecipeApiNewServiceImpl YhFoodSrecipeApiNewServiceImpl;


	@Autowired
	private YhHealthLectureInfoAppServiceImpl YhHealthLectureInfoAppServiceImpl;
	@Autowired
	private YhFoodCookbook2ServiceImpl YhFoodCookbook2ServiceImpl;

	@Autowired
	YhFoodHotSearchServiceImpl YhFoodHotSearchServiceImpl;
	@Autowired
	com.yihe.system.service.impl.YhUserFoodGuideServiceImpl YhUserFoodGuideServiceImpl;

	@Autowired
	YhFoodCookedMapper YhFoodCookedMapper;
	
	@Autowired
	YhUserFoodWeeklyMapper YhUserFoodWeeklyMapper;
	
	@Autowired
	YhFoodCategoryApiMapper YhFoodCategoryApiMapper;
	
	@Autowired
	YhHealthyPlanServiceImpl YhHealthyPlanServiceImpl;
	@Autowired
	YhFoodSrecipeRecordAppService yhFoodSrecipeRecordAppService;
	@Autowired
	com.yihe.system.mapper.YhDiseaseCategoryMapper YhDiseaseCategoryMapper;
	@Autowired
	com.yihe.system.mapper.YhDiseaseAssessTotalReport2Mapper YhDiseaseAssessTotalReport2Mapper;
	@Autowired
	com.yihe.system.service.impl.YhQuestionCheckJsonServiceImpl YhQuestionCheckJsonServiceImpl;



	/**
	 * 根据食物id查询食物详情
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getFiles")
	@ResponseBody
	public Result getFiles2()  {
		getFiles("E:\\foodImg");
		return Result.ok();

	}



	/**
	 * @Author：
	 * @Description：获取某个目录下所有直接下级文件，不包括目录下的子目录的下的文件，所以不用递归获取
	 * @Date：
	 */
	public   List<String> getFiles(String path) {
		List<String> files = new ArrayList<String>();
		File file = new File(path);
		File[] tempList = file.listFiles();

		int j=0;
		List<Integer> list=new ArrayList<Integer>();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {

				files.add(tempList[i].toString());
				//文件名，不包含路径
				String fileName = tempList[i].getName();
				System.out.println(path+"\\"+fileName);
				String foodImg = QiNiuUtil.upload(path + "\\" + fileName);

				File tmpFile=new File(path+"\\"+fileName);
				String fileName2=tmpFile.getName();
				String foodid = fileName2.substring(0, fileName2.lastIndexOf("."));
				YhFoodCookbook2 yhFoodCookbook2 = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(foodid));
				if(null!=yhFoodCookbook2){
					YhFoodCookbook2 record=new YhFoodCookbook2();
					record.setfCId(Integer.valueOf(foodid));
					record.setfCImg(foodImg);
					int i1 = YhFoodCookbook2ServiceImpl.updateByPrimaryKeySelective(record);
					if(0==i1){
						list.add(Integer.valueOf(foodid));
					}
					j=j+i1;
				}else{
					list.add(Integer.valueOf(foodid));
				}

			}
			if (tempList[i].isDirectory()) {
				//这里就不递归了，
			}
			System.out.println("共"+tempList.length+"个文件;");
			System.out.println("替换"+j+"个文件");
			System.out.println("失败"+list.toString());
			System.out.println("失败"+list.size());

		}
		return files;
	}


	/**
	 * 根据食物id查询食物详情
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/addYhUserFoodWeekly")
	@ResponseBody
	public Result addYhUserFoodWeekly() throws ParseException {
			YhFoodSrecipeApiNewServiceImpl.addYhUserFoodWeekly();
		return Result.error();
		
	}
	/**
	 * 根据食物id查询食物详情
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/addYhUserFoodWeekly4")
	@ResponseBody
	public Result addYhUserFoodWeekly4() throws ParseException {
		YhFoodSrecipeApiNewServiceImpl.addYhUserFoodWeekly4();
		return Result.error();
		
	}
	/**
	 * 根据食物id查询食物详情
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/addYhUserFoodWeekly3")
	@ResponseBody
	public Result addYhUserFoodWeekly3(String determine) throws ParseException {
		if("确定已经操作数据库".equals(determine)){
			YhFoodSrecipeApiNewServiceImpl.addYhUserFoodWeekly3();
			return Result.ok();
		}
		return Result.error();
		
	}
	/**
	 * 根据食物id查询食物详情
	 */
	@RequestMapping(value = "/findFoodfindEnergyNum")
	@ResponseBody
	public Result findFoodfindEnergyNum(Integer sexInt, Integer ageInt,double height,double weight,Integer sport) {
		
		 //得到计算出来的每日能量推荐
		double findEnergyNum = yhFoodSrecipeRecordAppService.findEnergyNum(sexInt, ageInt, height, weight, String.valueOf(sport));
		BigDecimal b = new BigDecimal(findEnergyNum);
		float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return Result.ok(f1);
	}
	
	/**
	 * 模糊查询五条食物名字
	 * @param fCName
	 * @return
	 */
	@RequestMapping(value = "/findFoodName")
	@ResponseBody
	public  List<YhFoodCookbook2> findFoodName(String fCName){
		List<YhFoodCookbook2> findFoodName = YhFoodCookbook2Service.findFoodName(fCName);
		return findFoodName;
		
	}
	
	/**
	 * 菜品识别
	 * 
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequestMapping(value = "/dishesIdentification")
	@ResponseBody
	public List<YhFoodCookbook2> dishesIdentification(HttpSession session,
			@RequestParam(value = "file", required = false) MultipartFile file)
			throws IllegalStateException, IOException {
		String path = session.getServletContext().getRealPath("/") + file.getOriginalFilename();

		file.transferTo(new File(path));
		// 得到百度返回的数据
		String dish = BaiDuDish.dish(path);
		if (StringUtil.isEmpty(dish)) {
			// 删除本地
			File f = new File(path);
			f.delete();
			//
			System.out.println(dish);
			return null;

		} else {
			List<YhFoodCookbook2> list = new ArrayList<YhFoodCookbook2>();
			JSONObject jsonObject = JSONObject.parseObject(dish);
			if (null == jsonObject) {
				return null;
			}
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			if (null == jsonArray) {
				return null;
			}
			for (int i = 0; i < jsonArray.size(); i++) {
				System.out.println(jsonArray.getJSONObject(i).get("name"));

				String name = (String) jsonArray.getJSONObject(i).get("name");// 食物名称
				if ("非菜".equals(name)) {
					continue;
				}
				String calorie = (String) jsonArray.getJSONObject(i).get("calorie");// 食物每100克热量

				YhFoodCookbook2 selectBYshowName = YhFoodCookbook2Service.selectBYshowName(name);
				if (null != selectBYshowName) {
					String getfCName = selectBYshowName.getfCName();
					String ellipsis = ellipsis(getfCName);
					selectBYshowName.setfCName(ellipsis);
					list.add(selectBYshowName);
				}
				//食物库没有
				/* else {
					YhFoodCookbook2 foodinfo = new YhFoodCookbook2();
					foodinfo.setfCName(name);
					if (null == calorie) {
						foodinfo.setEnergyKcal(0f);
					} else {
						foodinfo.setEnergyKcal(Float.parseFloat(calorie));
					}

					list.add(foodinfo);
				}*/
			}
			// 删除本地
			File f = new File(path);
			f.delete();
			//
			 
			return list;

		}

	}
	
	
	
	
	
	/**
	 * 根据时间段和用户查询膳食报告
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/fooddiaryAnalysis")
	@ResponseBody
	public Result fooddiaryAnalysis(String usercode,String time) throws ParseException {
		Map<String, Object> map=new HashMap<String, Object>();

		YhFoodUserDiary selectByUserCodeAndTime = YhFoodUserDiaryServiceImpl.selectByUserCodeAndTime(usercode, time);
		 if(null==selectByUserCodeAndTime||StringUtil.isEmpty(selectByUserCodeAndTime.getfElements())){

			 return  Result.error();
		 }
		map.put("time", time);
		if(StringUtil.isEmpty(selectByUserCodeAndTime.getEnergyKcal())){
			return Result.ok(map);
		}
		BigDecimal energyLevel = new BigDecimal(selectByUserCodeAndTime.getEnergyLevel());
		BigDecimal energyKcal = new BigDecimal(selectByUserCodeAndTime.getEnergyKcal());
		map.put("energyLevel", energyLevel);
		map.put("energyKcal", energyKcal);

		if(energyKcal.compareTo(energyLevel)==1){
			map.put("exceeding", true);
			
		}else{
			map.put("exceeding", false);
		}
		BigDecimal fat = new BigDecimal("0");
		if(StringUtil.isNotEmpty(selectByUserCodeAndTime.getFat())){
			fat = new BigDecimal(selectByUserCodeAndTime.getFat());
		}
		BigDecimal protein = new BigDecimal("0");
		if(StringUtil.isNotEmpty(selectByUserCodeAndTime.getProtein())){
			  protein = new BigDecimal(selectByUserCodeAndTime.getProtein());
		}
		BigDecimal cho = new BigDecimal("0");
		if(StringUtil.isNotEmpty(selectByUserCodeAndTime.getCho())){
		  cho = new BigDecimal(selectByUserCodeAndTime.getCho());
		}





		BigDecimal fatNum = fat.multiply(new BigDecimal("9")).divide(((cho.multiply(new BigDecimal("4"))).add((protein.multiply(new BigDecimal("4")))).add((fat.multiply(new BigDecimal("9"))))).divide(new BigDecimal("100")));


		BigDecimal carbohydrateNum = cho.multiply(new BigDecimal("4")).divide(((cho.multiply(new BigDecimal("4"))).add((protein.multiply(new BigDecimal("4")))).add((fat.multiply(new BigDecimal("9"))))).divide(new BigDecimal("100")));


		BigDecimal proteinNum = protein.multiply(new BigDecimal("4")).divide(((cho.multiply(new BigDecimal("4"))).add((protein.multiply(new BigDecimal("4")))).add((fat.multiply(new BigDecimal("9"))))).divide(new BigDecimal("100")));




		map.put("protein",proteinNum.setScale(2, BigDecimal.ROUND_HALF_UP));
		map.put("cho", carbohydrateNum.setScale(2, BigDecimal.ROUND_HALF_UP));
		map.put("fat", fatNum.setScale(2, BigDecimal.ROUND_HALF_UP));




		BigDecimal proteinBfb=new BigDecimal("100").divide((fatNum.add(carbohydrateNum).add(proteinNum)).divide(proteinNum,4,BigDecimal.ROUND_HALF_UP),4,BigDecimal.ROUND_HALF_UP);
		BigDecimal fatBfb=new BigDecimal("100").divide((fatNum.add(carbohydrateNum).add(proteinNum)).divide(fatNum,4,BigDecimal.ROUND_HALF_UP),4,BigDecimal.ROUND_HALF_UP);
		BigDecimal choBfb=new BigDecimal("100").divide((fatNum.add(carbohydrateNum).add(proteinNum)).divide(carbohydrateNum,4,BigDecimal.ROUND_HALF_UP),4,BigDecimal.ROUND_HALF_UP);

		BigDecimal proteinBfbf1 = proteinBfb.setScale(1, BigDecimal.ROUND_HALF_UP);

		BigDecimal fatBfbf1 = fatBfb.setScale(1, BigDecimal.ROUND_HALF_UP);

		BigDecimal choBfbf1 = choBfb.setScale(1, BigDecimal.ROUND_HALF_UP);
		
		map.put("proteinBfbf1", proteinBfbf1);
		map.put("fatBfbf1", fatBfbf1);
		map.put("choBfbf1", choBfbf1);

		boolean tnb =false;
		YhDiseaseAssessTotalReport2web yhDiseaseAssessTotalReport2web = YhDiseaseAssessTotalReport2Mapper.selectAssessByParam(usercode);
		if(null!=yhDiseaseAssessTotalReport2web&&null!=yhDiseaseAssessTotalReport2web.getSkNames()){
			tnb = yhDiseaseAssessTotalReport2web.getSkNames().contains("糖尿病");

		}

		if(tnb){
			if(proteinBfbf1.compareTo(new BigDecimal("15"))>-1&&proteinBfbf1.compareTo(new BigDecimal("20"))<1){
				map.put("proteinBfb", "合适");
			}else if(proteinBfbf1.compareTo(new BigDecimal("15"))==1){
				map.put("proteinBfb", "偏高");
			}else{
				map.put("proteinBfb", "偏低");
			}


			if(	fatBfbf1.compareTo(new BigDecimal("20"))>-1&&fatBfbf1.compareTo(new BigDecimal("30"))<1){
				map.put("fatBfb", "合适");
			}else if(fatBfbf1.compareTo(new BigDecimal("30"))==1){
				map.put("fatBfb", "偏高");
			}else{
				map.put("fatBfb", "偏低");
			}

			if(choBfbf1.compareTo(new BigDecimal("45"))>-1&&choBfbf1.compareTo(new BigDecimal("60"))<1){
				map.put("choBfb", "合适");
			}else if(choBfbf1.compareTo(new BigDecimal("65"))==1){
				map.put("choBfb", "偏高");
			}else{
				map.put("choBfb", "偏低");
			}
		}else{
			if(proteinBfbf1.compareTo(new BigDecimal("10"))>-1&&proteinBfbf1.compareTo(new BigDecimal("15"))<1){
				map.put("proteinBfb", "合适");
			}else if(proteinBfbf1.compareTo(new BigDecimal("15"))==1){
				map.put("proteinBfb", "偏高");
			}else{
				map.put("proteinBfb", "偏低");
			}

			if(fatBfbf1.compareTo(new BigDecimal("20"))>-1&&fatBfbf1.compareTo(new BigDecimal("30"))<1){
				map.put("fatBfb", "合适");
			}else if(fatBfbf1.compareTo(new BigDecimal("30"))==1){
				map.put("fatBfb", "偏高");
			}else{
				map.put("fatBfb", "偏低");
			}

			if(choBfbf1.compareTo(new BigDecimal("50"))>-1&&choBfbf1.compareTo(new BigDecimal("65"))<1){
				map.put("choBfb", "合适");
			}else if(choBfbf1.compareTo(new BigDecimal("65"))==1){
				map.put("choBfb", "偏高");
			}else{
				map.put("choBfb", "偏低");
			}
		}


		
		//早餐热量
		BigDecimal zaoZaoKcal=new BigDecimal("0");
 		//午餐热量
		BigDecimal zaoWuKcal=new BigDecimal("0");
 		//晚餐热量
		BigDecimal zaoWanKcal=new BigDecimal("0");
 		//加餐热量
		BigDecimal zaoJiaKcal=new BigDecimal("0");
		String getfElements = selectByUserCodeAndTime.getfElements();
		if(StringUtil.isNotEmpty(getfElements)){
			
			String[] split = getfElements.split(";");
			for (String string : split) {
				
				String[] split2 = string.split("_");
				String foodtype = split2[1];
				if("10".equals(split2[0])){
					//10_1_2853_100,90178_30.0,90034_70.0;
					String[] split3 = string.split(",");
					 for (int i = 1; i < split3.length; i++) {
						//90178_30.0
						 String string2 = split3[i];
						 String[] split4 = string2.split("_");
						 //foodid
						 String string3 = split4[0];
						 //克数
						 String string4 = split4[1];
						 
						 YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(string3));
			 				if (null == foodInfo) {
								continue;
							}
						 BigDecimal energyKcal2=new BigDecimal(foodInfo.getEnergyKcal());

			 				if("1".equals(foodtype)){
			 					zaoZaoKcal=zaoZaoKcal.add(energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string4)));
			 				}
			 				if("2".equals(foodtype)){
								zaoWuKcal=zaoWuKcal.add(energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string4)));

			 				}
			 				if("3".equals(foodtype)){
								zaoWanKcal=zaoWanKcal.add(energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string4)));

			 				}
			 				if("4".equals(foodtype)){
								zaoJiaKcal=zaoJiaKcal.add(energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string4)));

			 				}
			 				
			 			     	
			 				
					}
				}else{
					//1_1_7_100;
					String[] split3 = string.split("_");
					//foodid
					String string2 = split3[2];
					//克数
					String string3 = split3[3];
					 YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(string2));
		 				if (null == foodInfo) {
							continue;
						}

					BigDecimal energyKcal2=new BigDecimal(foodInfo.getEnergyKcal());
					if("1".equals(foodtype)){
						zaoZaoKcal=zaoZaoKcal.add(energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string3)));
					}
					if("2".equals(foodtype)){
						zaoWuKcal=zaoWuKcal.add(energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string3)));

					}
					if("3".equals(foodtype)){
						zaoWanKcal=zaoWanKcal.add(energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string3)));

					}
					if("4".equals(foodtype)){
						zaoJiaKcal=zaoJiaKcal.add(energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string3)));

					}
				}
				
				
				
			}
		}
		
		map.put("zaoZaoKcal", zaoZaoKcal);
		map.put("zaoWuKcal", zaoWuKcal);
		map.put("zaoWanKcal", zaoWanKcal);
		map.put("zaoJiaKcal", zaoJiaKcal);

		float zaoZaoKcalBflbf1 =0;
		float zaoWuKcalBflbf1 = 0;
		float zaoWanKcalBflf1 = 0;
		float zaoJiaKcalBflf1 = 0;

		if(zaoZaoKcal.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal zaoZaoKcalBflb=new BigDecimal("100").divide((zaoZaoKcal.add(zaoWuKcal).add(zaoWanKcal).add(zaoJiaKcal)).divide(zaoZaoKcal),4,BigDecimal.ROUND_HALF_UP);
			zaoZaoKcalBflbf1 = zaoZaoKcalBflb.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
		}

		if(zaoWuKcal.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal zaoWuKcalBflb=new BigDecimal("100").divide((zaoZaoKcal.add(zaoWuKcal).add(zaoWanKcal).add(zaoJiaKcal)).divide(zaoWuKcal),4,BigDecimal.ROUND_HALF_UP);
			zaoWuKcalBflbf1 = zaoWuKcalBflb.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
		}

		if(zaoWanKcal.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal zaoWanKcalBflb=new BigDecimal("100").divide((zaoZaoKcal.add(zaoWuKcal).add(zaoWanKcal).add(zaoJiaKcal)).divide(zaoWanKcal),4,BigDecimal.ROUND_HALF_UP);
			zaoWanKcalBflf1 = zaoWanKcalBflb.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
		}

		if(zaoJiaKcal.compareTo(BigDecimal.ZERO)!=0){
			BigDecimal zaoJiaKcalBflb=new BigDecimal("100").divide((zaoZaoKcal.add(zaoWuKcal).add(zaoWanKcal).add(zaoJiaKcal)).divide(zaoJiaKcal),4,BigDecimal.ROUND_HALF_UP);
			zaoJiaKcalBflf1 = zaoJiaKcalBflb.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
		}








	
	map.put("zaoZaoKcalBfl", zaoZaoKcalBflbf1);
	map.put("zaoWuKcalBfl", zaoWuKcalBflbf1);
	map.put("zaoWanKcalBfl", zaoWanKcalBflf1);
	map.put("zaoJiaKcalBfl", zaoJiaKcalBflf1);
	
	if(zaoZaoKcalBflbf1>=25&&zaoZaoKcalBflbf1<=30){
		map.put("zaoStauts", "合适");
	}else if(zaoZaoKcalBflbf1>30){
		map.put("zaoStauts", "偏高");
	}else{
		map.put("zaoStauts", "偏低");
	}
	
	if(zaoWuKcalBflbf1>=35&&zaoWuKcalBflbf1<=40){
		map.put("wuStauts", "合适");
	}else if(zaoWuKcalBflbf1>40){
		map.put("wuStauts", "偏高");
	}else{
		map.put("wuStauts", "偏低");
	}
	
	if(zaoWanKcalBflf1>=25&&zaoWanKcalBflf1<=30){
		map.put("wanStauts", "合适");
	}else if(zaoWanKcalBflf1>30){
		map.put("wanStauts", "偏高");
	}else{
		map.put("wanStauts", "偏低");
	}
	
	if(zaoJiaKcalBflf1>=5&&zaoJiaKcalBflf1<=15){
		map.put("jiaStauts", "合适");
	}else if(zaoJiaKcalBflf1>15){
		map.put("jiaStauts", "偏高");
	}else{
		map.put("jiaStauts", "偏低");
	}
	
	List<String> list =new ArrayList<String>();
	list.add("慢跑:22分钟");
	list.add("快走:25分钟");
	list.add("游泳:20分钟");
	list.add("自行车:25分钟");
	list.add("打篮球:25分钟");
	list.add("爬楼:31分钟");
	map.put("sportList", list);
		
		return Result.ok(map);
	}
	/**
	 * 根据时间段和用户查询膳食报告
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/foodCategoryAllInfo")
	@ResponseBody
	public Result foodCategoryAllInfo() throws ParseException {
		List<YhFoodCategoryApi> findAllInfo = YhFoodCategoryApiMapper.findAllInfo();
		for (YhFoodCategoryApi yhFoodCategoryApi : findAllInfo) {
			Integer everydaymin = yhFoodCategoryApi.getEverydaymin();
			Integer everydaymax = yhFoodCategoryApi.getEverydaymax();
			if(null!=everydaymin&&null!=everydaymax) {
			String str=	everydaymin+"~"+everydaymax+"克/天";
			yhFoodCategoryApi.setFlagInfo(str);
			}
			
			if(null==everydaymax&&null==everydaymin) {
				yhFoodCategoryApi.setFlagInfo("");
			}
			
			if(null==everydaymax) {
				String str=everydaymin+"克以上/天";
				yhFoodCategoryApi.setFlagInfo(str);
			}
			
			
			
		}
		
		 return Result.ok(findAllInfo);
	}
		/**
		 * 根据时间段和用户查询膳食报告
		 * 
		 * @return
		 * @throws ParseException 
		 */
		@RequestMapping(value = "/findFoodForReport2")
		@ResponseBody
		public Result findFoodForReport2(String usercode,Integer id) throws ParseException {
		if(StringUtil.isEmpty(usercode)||null==id){
			return Result.error("参数异常");
		}
			boolean tnb =false;
			YhDiseaseAssessTotalReport2web yhDiseaseAssessTotalReport2web = YhDiseaseAssessTotalReport2Mapper.selectAssessByParam(usercode);
			if(null!=yhDiseaseAssessTotalReport2web&&null!=yhDiseaseAssessTotalReport2web.getSkNames()){
				 tnb = yhDiseaseAssessTotalReport2web.getSkNames().contains("糖尿病");
			}

			YhUserFoodWeekly findUserWeekLyInfoByUsercode = YhUserFoodWeeklyMapper.selectByPrimaryKey(id);
	        
	        if(null==findUserWeekLyInfoByUsercode){
	        	return Result.ok("");
	        }
	        
	        Float zao = findUserWeekLyInfoByUsercode.getZao();
	        Float wu = findUserWeekLyInfoByUsercode.getWu();
	        Float wan = findUserWeekLyInfoByUsercode.getWan();
	        Float jia = findUserWeekLyInfoByUsercode.getJia();
	        
	    
	        Float zaoKacl=100/((zao+wu+wan+jia)/zao);
	    	BigDecimal   b2   =   new   BigDecimal(zaoKacl);  
			  float   f2   =   b2.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
			  findUserWeekLyInfoByUsercode.setZao(f2);
	        Float wuKacl=100/((zao+wu+wan+jia)/wu);
	        	b2   =   new   BigDecimal(wuKacl);  
			      f2   =   b2.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
			  findUserWeekLyInfoByUsercode.setWu(f2);
	        Float wanKacl=100/((zao+wu+wan+jia)/wan);
	    	b2   =   new   BigDecimal(wanKacl);  
		      f2   =   b2.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
		  findUserWeekLyInfoByUsercode.setWan(f2);
	        Float jiaKacl=100/((zao+wu+wan+jia)/jia);
	        b2   =   new   BigDecimal(jiaKacl);  
		      f2   =   b2.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
		      findUserWeekLyInfoByUsercode.setJia(f2);
	        if(zaoKacl>=25&&zaoKacl<=30){
	    		findUserWeekLyInfoByUsercode.setZaoCheck("合适");
	    	}else if(zaoKacl>30){
	    		findUserWeekLyInfoByUsercode.setZaoCheck("偏高");
	    	}else{
	    		findUserWeekLyInfoByUsercode.setZaoCheck("偏低");
	    	}
	    	
	    	if(wuKacl>=35&&wuKacl<=40){
	    		findUserWeekLyInfoByUsercode.setWuCheck("合适");
	    	}else if(wuKacl>40){
	    		findUserWeekLyInfoByUsercode.setWuCheck("偏高");
	    	}else{
	    		findUserWeekLyInfoByUsercode.setWuCheck("偏低");
	    	}
	    	
	    	if(wanKacl>=25&&wanKacl<=30){
	    		findUserWeekLyInfoByUsercode.setWanCheck("合适");
	    	}else if(wanKacl>30){
	    		findUserWeekLyInfoByUsercode.setWanCheck("偏高");
	    	}else{
	    		findUserWeekLyInfoByUsercode.setWanCheck("偏低");
	    	}
	    	
	    	if(jiaKacl>=5&&jiaKacl<=15){
	    		findUserWeekLyInfoByUsercode.setJiaCheck("合适");
	    	}else if(jiaKacl>15){
	    		findUserWeekLyInfoByUsercode.setJiaCheck("偏高");
	    	}else{
	    		findUserWeekLyInfoByUsercode.setJiaCheck("偏低");
	    	}
	    	
	    	
	    	
	        
	        
	        
	        Float protein = findUserWeekLyInfoByUsercode.getProtein();
	        Float fat = findUserWeekLyInfoByUsercode.getFat();
	        Float carbohydrate = findUserWeekLyInfoByUsercode.getCarbohydrate();



			if(null==fat){
				fat=0f;
			}
			if(null==carbohydrate){
				carbohydrate=0f;
			}
			if(null==protein){
				protein=0f;
			}


			Float	fatb= fat * 9 / (carbohydrate * 4 + protein * 4 + fat * 9) * 100;

			Float	carbohydrateb= carbohydrate * 4 / (carbohydrate * 4 + protein * 4 + fat * 9) * 100;

			Float	pbgb = protein * 4 / (carbohydrate * 4 + protein * 4 + fat * 9) * 100;

			BigDecimal fatbbd = new BigDecimal(fatb);
			float fatbbdf = fatbbd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

			findUserWeekLyInfoByUsercode.setFat(fatbbdf);

			BigDecimal carbohydratebbd = new BigDecimal(carbohydrateb);
			float carbohydratebbdf = carbohydratebbd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

			findUserWeekLyInfoByUsercode.setCarbohydrate(carbohydratebbdf);

			BigDecimal pbgbd = new BigDecimal(pbgb);
			float pbgbdf = pbgbd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

			findUserWeekLyInfoByUsercode.setProtein(pbgbdf);

	        if(tnb){
				if(pbgb>=10&&pbgb<=20){
					findUserWeekLyInfoByUsercode.setProteincheck("合适");
				}else if(pbgb>20){
					findUserWeekLyInfoByUsercode.setProteincheck("偏高");
				}else{
					findUserWeekLyInfoByUsercode.setProteincheck("偏低");
				}

				if(fatb>=20&&fatb<=30){
					findUserWeekLyInfoByUsercode.setFatcheck("合适");
				}else if(fatb>30){
					findUserWeekLyInfoByUsercode.setFatcheck("偏高");
				}else{
					findUserWeekLyInfoByUsercode.setFatcheck("偏低");
				}

				if(carbohydrateb>=45&&carbohydrateb<=60){
					findUserWeekLyInfoByUsercode.setCarbohydratecheck("合适");
				}else if(carbohydrateb>60){
					findUserWeekLyInfoByUsercode.setCarbohydratecheck("偏高");
				}else{
					findUserWeekLyInfoByUsercode.setCarbohydratecheck("偏低");
				}
			}else{
				if(pbgb>=10&&pbgb<=15){
					findUserWeekLyInfoByUsercode.setProteincheck("合适");
				}else if(pbgb>15){
					findUserWeekLyInfoByUsercode.setProteincheck("偏高");
				}else{
					findUserWeekLyInfoByUsercode.setProteincheck("偏低");
				}

				if(fatb>=20&&fatb<=30){
					findUserWeekLyInfoByUsercode.setFatcheck("合适");
				}else if(fatb>30){
					findUserWeekLyInfoByUsercode.setFatcheck("偏高");
				}else{
					findUserWeekLyInfoByUsercode.setFatcheck("偏低");
				}

				if(carbohydrateb>=50&&carbohydrateb<=65){
					findUserWeekLyInfoByUsercode.setCarbohydratecheck("合适");
				}else if(carbohydrateb>65){
					findUserWeekLyInfoByUsercode.setCarbohydratecheck("偏高");
				}else{
					findUserWeekLyInfoByUsercode.setCarbohydratecheck("偏低");
				}
			}

	        
	        List<YhFoodCategoryApi> findAllInfo = YhFoodCategoryApiMapper.findAllInfo();
			for (YhFoodCategoryApi yhFoodCategoryApi : findAllInfo) {
				
				Integer id2 = yhFoodCategoryApi.getId();
				if(id2==1) {
					Float type1 = findUserWeekLyInfoByUsercode.getType1();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==2) {
					Float type1 = findUserWeekLyInfoByUsercode.getType2();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==3) {
					Float type1 = findUserWeekLyInfoByUsercode.getType3();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==4) {
					Float type1 = findUserWeekLyInfoByUsercode.getType4();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==5) {
					Float type1 = findUserWeekLyInfoByUsercode.getType5();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==6) {
					Float type1 = findUserWeekLyInfoByUsercode.getType6();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				
				if(id2==7) {
					Float type1 = findUserWeekLyInfoByUsercode.getType7();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==8) {
					Float type1 = findUserWeekLyInfoByUsercode.getType8();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==9) {
					Float type1 = findUserWeekLyInfoByUsercode.getType9();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==10) {
					Float type1 = findUserWeekLyInfoByUsercode.getType10();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==11) {
					Float type1 = findUserWeekLyInfoByUsercode.getType11();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==12) {
					Float type1 = findUserWeekLyInfoByUsercode.getType12();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==13) {
					Float type1 = findUserWeekLyInfoByUsercode.getType13();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==14) {
					Float type1 = findUserWeekLyInfoByUsercode.getType14();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				if(id2==15) {
					Float type1 = findUserWeekLyInfoByUsercode.getType15();
					BigDecimal   b   =   new   BigDecimal(type1/findUserWeekLyInfoByUsercode.getEcorddays());  
					  float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue(); 
					yhFoodCategoryApi.setValue(f1);
				}
				
				
				Integer everydaymin = yhFoodCategoryApi.getEverydaymin();
				Integer everydaymax = yhFoodCategoryApi.getEverydaymax();
				if(null!=everydaymin&&null!=everydaymax) {
				String str=	everydaymin+"~"+everydaymax+"克/天";
				yhFoodCategoryApi.setFlagInfo(str);
				}
				
				if(null==everydaymax&&null==everydaymin) {
					yhFoodCategoryApi.setFlagInfo("");
				}
				
				if(null==everydaymax) {
					String str=everydaymin+"克以上/天";
					yhFoodCategoryApi.setFlagInfo(str);
				}
				
				
				
			}
			
			findUserWeekLyInfoByUsercode.setFindAllInfo(findAllInfo);
		
		
		return Result.ok(findUserWeekLyInfoByUsercode);
	}
	/**
	 * 根据时间段和用户查询膳食报告
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/findFoodForReport")
	@ResponseBody
	public Result findFoodForReport(String usercode,Integer id) throws ParseException {
		if(StringUtil.isEmpty(usercode)||null==id){
			return Result.error("参数异常");
		}
		
		YhUserFoodGuide findUserFoodGuiDeByid = YhUserFoodGuideServiceImpl.findUserFoodGuiDeByid(id);
		if(null==findUserFoodGuiDeByid){
			return Result.error("id错误");
		}
		Date startTime = findUserFoodGuiDeByid.getStartTime();
		Date endTime2 = findUserFoodGuiDeByid.getEndTime();
		 SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
	        String strTime = sformat.format(startTime);
	        String endTime = sformat.format(endTime2);
		 
		//推荐集合
		List<Long> tjList=new ArrayList<Long>();
		//实际摄入集合
		List<String> sjList=new ArrayList<String>();
		//记录天数
		int jlnum=0;
		//超标天数
		int exceed=0;
		//早餐热量
		float zaoNum=0f;
		//午餐热量
		float wuNum=0f;
		//晚餐热量
		float wanNum=0f;
		//加餐热量
		float jiaNum=0f;
		//脂肪
		BigDecimal fat =new BigDecimal("0");
		//碳水化合物
		BigDecimal cho=new BigDecimal("0");
		//蛋白质
		BigDecimal protein=new BigDecimal("0");
		 List<String> endDay = DateTest.getEndDay(strTime, endTime);
		 List<String> endDayYear = DateTest.getEndYear(strTime, endTime);
		 
		 if(null!=endDay){
			 for (String string : endDayYear) {
				 YhFoodUserDiary selectByUserCodeAndTime = YhFoodUserDiaryServiceImpl.selectByUserCodeAndTime(usercode, string);
				 
				 Long tuijianNum=0l;
				 if(null==selectByUserCodeAndTime){
					//推荐摄入
						 tuijianNum = tuijianNum(usercode);
						 //实际摄入
						 sjList.add("0");
				 }else{
					//超标天数
					 BigDecimal LevelBigDecimal = new BigDecimal(selectByUserCodeAndTime.getEnergyLevel());
					 BigDecimal EnergyKcalBigDecimal = new BigDecimal(selectByUserCodeAndTime.getEnergyKcal());
					 if(LevelBigDecimal.compareTo(EnergyKcalBigDecimal)==-1){
						 exceed++;
					 }

					 //fat脂肪
					   fat = fat.add(new BigDecimal(selectByUserCodeAndTime.getFat()));
					   //碳水化合物
					   cho = cho.add(new BigDecimal(selectByUserCodeAndTime.getCho()));
					   //蛋白质
					    protein = protein.add(new BigDecimal(selectByUserCodeAndTime.getProtein()));
					//记录天数
					 jlnum++;
					 //建议摄入
					 tuijianNum = selectByUserCodeAndTime.getEnergyLevel();
					 String energyKcal = selectByUserCodeAndTime.getEnergyKcal();
					 sjList.add(energyKcal);
					 
					 //早餐
					 String getfElements = selectByUserCodeAndTime.getfElements();
					 String[] split2 = getfElements.split(";");

						// split2 xxx_xxx_xxx
						for (int i = 0; i < split2.length; i++) {

							String[] split3 = split2[i].split("_");
							if (split3.length < 3) {
								continue;
							}
							// 1代表早餐，2加餐，3中餐，4晚餐
							String type = split3[0];

							// 食材编号
							String foodid = split3[1];
							YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(foodid));
							if (null == foodInfo) {
								System.out.println("没有找到该食材" + foodid);
								continue;
								 
							}
							// 食材克数
							BigDecimal energyKcal2 = new BigDecimal(foodInfo.getEnergyKcal());
							if (null != energyKcal2) {



								BigDecimal b = energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(split3[2]));
								float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
								switch (type) {
								case "1":
									//早餐热量
									 zaoNum=zaoNum+f1;
									 
									break;
								case "2":
									 
									//午餐热量
									 
									wuNum=wuNum+f1;
									break;
								case "3":
									 
									//晚餐热量
									wanNum=wanNum+f1;
									break;
								case "4":
									 
									//加餐热量
									 
									jiaNum=jiaNum+f1;
									break;

								default:
									break;
								}
								 
							}

							

						}
				 }
				 tjList.add(tuijianNum);
				 
				 
			 } 
		 }
		 
		 //推荐
		 int averageRecommendation=0;
		 if(tjList.size()>0){
			 Long long1 =0l;
			 	for (int i = 0; i < tjList.size(); i++) {
			 		 long1 = long1+tjList.get(i);
				}
			 	averageRecommendation=(int) (long1/tjList.size());
		 } 
		 
		 //实际
		BigDecimal averageActual=new BigDecimal("0");
		 if(sjList.size()>0){
			 BigDecimal float1 =new BigDecimal("0");
			 	for (int i = 0; i < sjList.size(); i++) {
			 		  float1 = float1.add(new BigDecimal(sjList.get(i)));
				}
			 	 if(jlnum>0){
					 averageActual = float1.divide(new BigDecimal(jlnum),4,BigDecimal.ROUND_HALF_UP).setScale(0, BigDecimal.ROUND_HALF_UP);
				 }
			 	 
		 }
		 Map<String, Object> map=new HashMap<String, Object>();
		 map.put("averageRecommendation", averageRecommendation);
		 map.put("averageActual", averageActual);
			 
			 
		 
		 map.put("zaoNum", zaoNum);
		 map.put("wuNum", wuNum);
		 map.put("wanNum", wanNum);
		 map.put("jiaNum", jiaNum);
		 map.put("exceed", exceed);
		 map.put("jlnum", jlnum);
		 map.put("sjList", sjList);
		 map.put("tjList", tjList);
		 map.put("timeList", endDay);
		 fat=fat.setScale(0, BigDecimal.ROUND_HALF_UP);
		 map.put("fat", fat);
		 cho=cho.setScale(0, BigDecimal.ROUND_HALF_UP);
		 map.put("cho", cho);
		 protein=protein.setScale(0, BigDecimal.ROUND_HALF_UP);
		 map.put("protein", protein);
		 map.put("strTime", strTime);
		 map.put("endTime", endTime);
		 
		
		return Result.ok(map);
	}
	
	/**
	 * 审核状态
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findToExamine")
	@ResponseBody
	public Result findToExamine() {
		return Result.ok(YhFoodUserDiaryServiceImpl.findToExamine());
	}
	
	@RequestMapping(value = "/selectBYshowName")
	@ResponseBody
	public Result selectBYshowName(String name) {
		if (null == name || name == "") {
			return Result.error();
		}
		YhFoodCookbook2 selectByExample = YhFoodCookbook2Service.selectBYshowName(name);

		return Result.ok(selectByExample);
	}


	/**
	 * 首页膳食模块接口
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/foodOnTheFrontPage2")
	@ResponseBody
	public Result foodOnTheFrontPage2(String usercode) {
		if (StringUtil.isEmpty(usercode)) {
			return Result.error();
		}

		YhFoodUserDiary findFoodUserDiaryInfoByUserCodeForNow = YhFoodUserDiaryServiceImpl
				.findFoodUserDiaryInfoByUserCodeForNow(usercode);
		//推荐摄入
		Long tuijianNum = tuijianNum(usercode);
		if(null==findFoodUserDiaryInfoByUserCodeForNow){
			findFoodUserDiaryInfoByUserCodeForNow=new YhFoodUserDiary();
		}

		BigDecimal   b  =   new BigDecimal(findFoodUserDiaryInfoByUserCodeForNow.getProtein());
		BigDecimal bigDecimal = b.setScale(2, BigDecimal.ROUND_HALF_UP);
		findFoodUserDiaryInfoByUserCodeForNow.setProtein(bigDecimal.toString());

		BigDecimal   b2  =   new BigDecimal(findFoodUserDiaryInfoByUserCodeForNow.getCho());
		BigDecimal   f2   =  b2.setScale(2, BigDecimal.ROUND_HALF_UP);
		findFoodUserDiaryInfoByUserCodeForNow.setCho(f2.toString());


		BigDecimal   b3  =   new BigDecimal(findFoodUserDiaryInfoByUserCodeForNow.getFat());
		BigDecimal   f3   =  b3.setScale(2, BigDecimal.ROUND_HALF_UP);
		findFoodUserDiaryInfoByUserCodeForNow.setFat(f3.toString());

		findFoodUserDiaryInfoByUserCodeForNow.setEnergyLevel(tuijianNum);
		//今日摄入
		BigDecimal energyKcal =new BigDecimal("0");
		float energyKcalBFB =0f;
		String info="较低";
		if(null!=findFoodUserDiaryInfoByUserCodeForNow){
			energyKcal = new BigDecimal(findFoodUserDiaryInfoByUserCodeForNow.getEnergyKcal()).setScale(0, BigDecimal.ROUND_HALF_UP);
			findFoodUserDiaryInfoByUserCodeForNow.setEnergyKcal(energyKcal.toString());

			if(!(energyKcal.compareTo(new BigDecimal("0")) == 1)){
				info="较低";
			}else{
				// 创建一个数值格式化对象
				NumberFormat numberFormat = NumberFormat.getInstance();

				// 设置精确到小数点后2位
				numberFormat.setMaximumFractionDigits(2);

				BigDecimal result = energyKcal.divide(new BigDecimal(tuijianNum), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));

				energyKcalBFB= Float.valueOf(result.toString());
				if(energyKcalBFB<60){
					info="较低";
				}else
				if(energyKcalBFB>=60&&energyKcalBFB<=100){
					info="不错";
				}else{
					info="超标";
				}
			}

		}

		findFoodUserDiaryInfoByUserCodeForNow.setSituation(info);

		return Result.ok(findFoodUserDiaryInfoByUserCodeForNow);
	}


	/**
	 * 首页膳食模块接口
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/foodOnTheFrontPage")
	@ResponseBody
	public Result foodOnTheFrontPage(String usercode) {
		if (StringUtil.isEmpty(usercode)) {
			return Result.error();
		}

		//推荐摄入
		Long tuijianNum = tuijianNum(usercode);

		return Result.ok(tuijianNum);
	}

	/**
	 * 饮食主页
	 */
	@RequestMapping(value = "/foodPageHome")
	@ResponseBody
	public Result foodPageHome(String usercode,String foodRecommend) {
		if(StringUtil.isEmpty(usercode)){
			return Result.error("参数异常");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String secondaryFlag="";
		//查询是否妊娠期，或者是否有引起继发性骨质疏松症的疾病
		 YhQuestionCheckJson thQuestionCheckJson = YhQuestionCheckJsonServiceImpl.findThQuestionCheckJson(usercode);
		 if(null!=thQuestionCheckJson){
			 String checkJson = thQuestionCheckJson.getUserCheckAnswer();
			 if(StringUtil.isNotEmpty(checkJson)){
				 JSONObject object = JSONObject.parseObject(checkJson);
				 //正在妊娠期
				 String gestation = object.getString("female_gestation");
				 if("1".equals(gestation)){
					 secondaryFlag="因您：正在妊娠期，需要在专业医师的指导下开具膳食处方，如有疑问，请随时联系您的健管师。";
				 }
				 //患有引起继发性骨质疏松症的疾病:1
				 String secondary =JSONObject.parseObject(object.getString("secondary_op_his")).getString("disFlag");
				 //患有引起继发性骨质疏松症的疾病:1
				 String ophisDisFlag =JSONObject.parseObject(object.getString("op_his")).getString("disFlag");

				 if("1".equals(gestation)&&("1".equals(secondary)||"1".equals(ophisDisFlag))){
					 secondaryFlag="因您：正在妊娠期，患有引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、甲状旁腺功能亢进症、库欣综合征、慢性营养不良等），需要在专业医师的指导下开具膳食处方，如有疑问，请随时联系您的健管师。";
				 }

				 if((!"1".equals(gestation))&&("1".equals(secondary)||"1".equals(ophisDisFlag))){
					 secondaryFlag="因您：患有引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、甲状旁腺功能亢进症、库欣综合征、慢性营养不良等），需要在专业医师的指导下开具膳食处方，如有疑问，请随时联系您的健管师。";
				 }
			 }
		 }




		int dayNum = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		boolean flag=false;
		 if(StringUtil.isNotEmpty(foodRecommend)){
			 String[] split = foodRecommend.split(":");
			 String string = split[0];
			 if("1".equals(string)||string.equals(String.valueOf(dayNum))){
				 flag=true;
			 }
		 }
		YhFoodSrecipeRecordApp findFoodSrecipeRecordByUsercodeForNow = YhFoodSrecipeRecordAppServiceImpl
				.findFoodSrecipeRecordByUsercodeForNow(usercode);

		 if(!"".equals(secondaryFlag)&&"医生手动".equals(findFoodSrecipeRecordByUsercodeForNow.getRecordMode())){
			 secondaryFlag="";
		 }
		// 推荐摄入量
		Long recordKay = tuijianNum(usercode);
		 
		
		// 已摄入量
		BigDecimal energyKcal = new BigDecimal("0");
		// 推荐摄入率(圆形百分比图)
		BigDecimal baifenbi =new BigDecimal("0");
		// 食物种类
		int foodType = 0;
		// 记录天数
		int diaryNum = 0;
		// 连续天数
		int lxNumDay = 0;
		String getfElements2="";
		//推荐下标
		 String stringIndex=String.valueOf(dayNum);
		BigDecimal energyKcalRijiAllJia=new BigDecimal("0");
		BigDecimal energyKcalRijiAllWan=new BigDecimal("0");
		BigDecimal energyKcalRijiAllWu=new BigDecimal("0");
		 BigDecimal energyKcalRijiAllZao=new BigDecimal("0");
		 
		// 早1
		List<YhFoodCookbook2> listZao = new ArrayList<YhFoodCookbook2>();
		// 加2
		List<YhFoodCookbook2> listJia = new ArrayList<YhFoodCookbook2>();
		// 中3
		List<YhFoodCookbook2> listWu = new ArrayList<YhFoodCookbook2>();
		// 晚4
		List<YhFoodCookbook2> listWan = new ArrayList<YhFoodCookbook2>();
		 if(flag&&StringUtil.isNotEmpty(foodRecommend)){
			if(foodRecommend.split(":").length<2) {
							String mealId = findFoodSrecipeRecordByUsercodeForNow.getMealId();
							// 随机得到一个膳食推荐
							// 得到膳食推荐详情1_1210_437;1_1211_203;2_1212_496;2_1213_490;2_1214_129;3_1215_393;3_1216_127;3_1217_135;4_1218_129;
							YhFoodUserScrecipe findFoodUserScrecipeBySrecipeId = YhFoodSrecipeApiNewServiceImpl.findFoodTuiJian(mealId, dayNum);
							 getfElements2 = findFoodUserScrecipeBySrecipeId.getfElements();
			}else {
				getfElements2=foodRecommend.split(":")[1];
			}
			YhFoodUserScrecipe selectIsHaveFood = YhHealthyPlanServiceImpl.selectIsHaveFood(usercode);
			if(null!=selectIsHaveFood){
				getfElements2=selectIsHaveFood.getfElements();
			}
			String[] split2 = getfElements2.split(";");

			// split2 xxx_xxx_xxx
			for (int i = 0; i < split2.length; i++) {

				String[] split3 = split2[i].split("_");
				if (split3.length < 3) {
					continue;
				}
				// 1代表早餐，2加餐，3中餐，4晚餐
				String type = split3[0];

				// 食材编号
				String foodid = split3[1];
				YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(foodid));
				if (null == foodInfo) {
					System.out.println("没有找到该食材"+foodid);
					 continue;
				}
				// 食材克数
				String foodKeNum = split3[2];
				foodInfo.setGramNumber(Double.valueOf(foodKeNum));

				switch (type) {
				case "1":
					listZao.add(foodInfo);
					break;
				case "2":
					listWu.add(foodInfo);
					break;
				case "3":
					listWan.add(foodInfo);
					break;
				case "4":
					listJia	.add(foodInfo);
					break;

				default:
					break;
				}

			}
		}else
		// 如果膳食推荐不等于空 获取最新膳食推荐热量
		if (null != findFoodSrecipeRecordByUsercodeForNow) {
			YhFoodUserScrecipe selectIsHaveFood = YhHealthyPlanServiceImpl.selectIsHaveFood(usercode);
			if(null!=selectIsHaveFood){
				getfElements2=selectIsHaveFood.getfElements();
			}else{
				String mealId = findFoodSrecipeRecordByUsercodeForNow.getMealId();
				if (StringUtil.isEmpty(mealId)) {
					return Result.error();
				}
				YhFoodUserScrecipe findFoodUserScrecipeBySrecipeId = YhFoodSrecipeApiNewServiceImpl.findFoodTuiJian(mealId, dayNum);
				
				
				 getfElements2 = findFoodUserScrecipeBySrecipeId.getfElements();
			}
			
			
			String[] split2 = getfElements2.split(";");

			// split2 xxx_xxx_xxx
			for (int i = 0; i < split2.length; i++) {

				String[] split3 = split2[i].split("_");
				if (split3.length < 3) {
					continue;
				}
				// 1代表早餐，2加餐，3中餐，4晚餐
				String type = split3[0];

				// 食材编号
				String foodid = split3[1];
				YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(foodid));
				if (null == foodInfo) {
					System.out.println("没有找到该食材"+foodid);
					 continue;
				}
				// 食材克数
				String foodKeNum = split3[2];
				foodInfo.setGramNumber(Double.valueOf(foodKeNum));

				switch (type) {
				case "1":
					listZao.add(foodInfo);
					break;
				case "2":
					listWu.add(foodInfo);
					break;
				case "3":
					listWan.add(foodInfo);
					break;
				case "4":
					listJia	.add(foodInfo);
					break;

				default:
					break;
				}

			}

		} else {
			// 如果等于空则获取 初始化膳食热量评估 3.1等膳食推荐数据库做好改（新用户为评估的默认膳食推荐能量）
			YhFoodUserScrecipe selectIsHaveFood = YhHealthyPlanServiceImpl.selectIsHaveFood(usercode);
			if(null!=selectIsHaveFood){
				getfElements2=selectIsHaveFood.getfElements();
			}else{
				YhUserReginfoApp findYhUserReginfo = YhUserReginfoAppServiceImpl.findYhUserReginfo(usercode);
				Integer gender = findYhUserReginfo.getGender();

				YhFoodSrecipeApiNew selectByPrimaryKey = YhFoodSrecipeApiNewServiceImpl.findNewFoodSrecipeForSex(usercode,gender);
				
				YhFoodUserScrecipe findFoodUserScrecipeBySrecipeId = YhFoodSrecipeApiNewServiceImpl.findFoodTuiJian(String.valueOf(selectByPrimaryKey.getId()), dayNum);
				
			 
				 getfElements2 = findFoodUserScrecipeBySrecipeId.getfElements();
			}
			
			String[] split2 = getfElements2.split(";");

			// split2 xxx_xxx_xxx
			for (int i = 0; i < split2.length; i++) {

				String[] split3 = split2[i].split("_");
				if (split3.length < 3) {
					continue;
				}
				// 1代表早餐，2加餐，3中餐，4晚餐
				String type = split3[0];

				// 食材编号
				String foodid = split3[1];
				YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(foodid));
				if (null == foodInfo) {
					System.out.println("没有找到该食材"+foodid);
					 continue;
				}
				// 食材克数
				String foodKeNum = split3[2];
				foodInfo.setGramNumber(Double.valueOf(foodKeNum));

				switch (type) {
				case "1":
					listZao.add(foodInfo);
					break;
				case "2":
					listWu.add(foodInfo);
					break;
				case "3":
					listWan.add(foodInfo);
					break;
				case "4":
					listJia.add(foodInfo);
					break;

				default:
					break;
				}

			}

		}

		// 用户今天的膳食日记信息
		YhFoodUserDiary findFoodUserDiaryInfoByUserCodeForNow = YhFoodUserDiaryServiceImpl
				.findFoodUserDiaryInfoByUserCodeForNow(usercode);
		if (null != findFoodUserDiaryInfoByUserCodeForNow) {
			energyKcal=new BigDecimal(findFoodUserDiaryInfoByUserCodeForNow.getEnergyKcal()).setScale(0, BigDecimal.ROUND_HALF_UP);
			String getfElements = findFoodUserDiaryInfoByUserCodeForNow.getfElements();
			if (StringUtil.isNotEmpty(getfElements)) {
				// X_XXX_XXX 早中晚餐_食材id_克数;
				String[] split = getfElements.split(";");
				for (int i = 0; i < split.length; i++) {
					String string = split[i];

					String[] split2 = string.split("_");

					String rjType = split2[1];
					
					if("10".equals(split2[0])){
						 String[] split3 = split[i].split(",");
						 for (int j = 1; j < split3.length; j++) {
							 String[] split4 = split3[j].split("_");
							 String string2 = split4[0];
							 String string3 = split4[1];
							 YhFoodCookbook2 selectByExample = YhFoodCookbook2ServiceImpl
										.selectByPrimaryKey(Integer.valueOf(string2));
							 if(null==selectByExample){
									continue;
								}
							 BigDecimal energyKcal2 = new BigDecimal(selectByExample.getEnergyKcal());
								if (null != energyKcal2) {
									BigDecimal b = energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(string3));
									BigDecimal bigDecimal = b.setScale(2, BigDecimal.ROUND_HALF_UP);
									selectByExample.setEnergyKcalRiji(bigDecimal.toString());
								}
								selectByExample.setKeshu(string3);

								switch (rjType) {
								case "1":
									energyKcalRijiAllZao=energyKcalRijiAllZao.add(new BigDecimal(selectByExample.getEnergyKcalRiji()));
									break;
								case "2":
									energyKcalRijiAllWu=energyKcalRijiAllWu.add(new BigDecimal(selectByExample.getEnergyKcalRiji()));
									break;
								case "3":
									energyKcalRijiAllWan=energyKcalRijiAllWan.add(new BigDecimal(selectByExample.getEnergyKcalRiji()));
									break;
								case "4":
									energyKcalRijiAllJia=energyKcalRijiAllJia.add(new BigDecimal(selectByExample.getEnergyKcalRiji()));
									break;

								default:
									break;
								}
						}
					}else{

						YhFoodCookbook2 selectByExample = YhFoodCookbook2ServiceImpl
								.selectByPrimaryKey(Integer.valueOf(split2[2]));
						if(null==selectByExample){
							continue;
						}
						BigDecimal energyKcal2 = new BigDecimal(selectByExample.getEnergyKcal());
						if (null != energyKcal2) {
							BigDecimal b =energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(split2[3]));
							BigDecimal f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP);
							selectByExample.setEnergyKcalRiji(f1.toString());
						}
						selectByExample.setKeshu(split2[3]);

						switch (rjType) {
						case "1":
							energyKcalRijiAllZao=energyKcalRijiAllZao.add(new BigDecimal(selectByExample.getEnergyKcalRiji()));
							break;
						case "2":
							energyKcalRijiAllWu=energyKcalRijiAllWu.add(new BigDecimal(selectByExample.getEnergyKcalRiji()));
							break;
						case "3":
							energyKcalRijiAllWan=energyKcalRijiAllWan.add(new BigDecimal(selectByExample.getEnergyKcalRiji()));
							break;
						case "4":
							energyKcalRijiAllJia=energyKcalRijiAllJia.add(new BigDecimal(selectByExample.getEnergyKcalRiji()));
							break;
						default:
							break;
						}

					}
				}
			}
		}
		if (energyKcal.compareTo(new BigDecimal("0")) ==1 && recordKay > 0) {
			BigDecimal num1=new BigDecimal(recordKay);
			baifenbi =energyKcal.divide(num1,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(0,BigDecimal.ROUND_HALF_UP);
		}
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		String num = "-";
		if (month < 10) {
			num = "-0";
		}
		String time = year + num + month + "%";

		// 本月全部膳食日记
		List<YhFoodUserDiary> findFoodUserDiaryByusercodeAndTime = YhFoodUserDiaryServiceImpl
				.findFoodUserDiaryByusercodeAndTime(usercode, time);
		if (null != findFoodUserDiaryByusercodeAndTime && findFoodUserDiaryByusercodeAndTime.size() > 0) {
			// 连续天数
			lxNumDay = lxDay(findFoodUserDiaryByusercodeAndTime);

			// 记录天数
			diaryNum = findFoodUserDiaryByusercodeAndTime.size();
			List<String> foodtypeList = new ArrayList<String>();
			for (YhFoodUserDiary yhFoodUserDiary : findFoodUserDiaryByusercodeAndTime) {
				// 1_2_100;1_1_100;1_8_101;2_15_101;
				String getfElements = yhFoodUserDiary.getfElements();
				if (StringUtil.isEmpty(getfElements)) {
					continue;
				}
				// 1_2_100
				String[] split = getfElements.split(";");
				for (String string : split) {

					String[] split2 = string.split("_");
					String string2 = split2[1];
					foodtypeList.add(string2);
				}
			}

			List<String> distinctElements = foodtypeList.stream().distinct().collect(Collectors.toList());

			// 食物种类
			foodType = distinctElements.size();

		}




		map.put("recordKay", recordKay);
		map.put("energyKcal", energyKcal);
		map.put("baifenbi", baifenbi);
		map.put("foodType", foodType);
		map.put("diaryNum", diaryNum);
		map.put("lxNumDay", lxNumDay);
		map.put("listZao", listZao);
		map.put("listWu", listWu);
		map.put("listWan", listWan);
		map.put("listJia", listJia);
		map.put("energyKcalRijiAllJia", energyKcalRijiAllJia.setScale(2, BigDecimal.ROUND_HALF_UP));
		map.put("energyKcalRijiAllZao",energyKcalRijiAllZao.setScale(2, BigDecimal.ROUND_HALF_UP));
		map.put("energyKcalRijiAllWu", energyKcalRijiAllWu.setScale(2, BigDecimal.ROUND_HALF_UP));
		map.put("energyKcalRijiAllWan", energyKcalRijiAllWan.setScale(2, BigDecimal.ROUND_HALF_UP));

		map.put("getfElements", stringIndex+":"+getfElements2);
		map.put("zaoKcal", Math.round(recordKay*0.25));
		map.put("wuKcal", Math.round(recordKay*0.35));
		map.put("wanKcal", Math.round(recordKay*0.25));
		map.put("jiaKcal", Math.round(recordKay*0.15));

		//当前月
		map.put("month", month+"月");
		int findAssessTotalReportByusercodeNum = YhFoodCategoryApiMapper.findAssessTotalReportByusercodeNum(usercode);
		if (findAssessTotalReportByusercodeNum==0) {
			map.put("isAssess", false);
		} else {
			map.put("isAssess", true);
		}
		map.put("secondaryFlag", secondaryFlag);

		return Result.ok(map);
	}

	public int lxDay(List<YhFoodUserDiary> list) {

		int num = 1;
		int num2 = 1;

		for (int j = 1; j < list.size(); j++) {
			if (isYesterday(list.get(j - 1).getcTime(), list.get(j).getcTime())) {
				num++;
			} else {
				System.out.println("断了----" + num);
				if (num > num2) {
					num2 = num;
				}

				num = 1;
			}

		}

		if (num > num2) {
			num2 = num;
		}

		return num2;
	}

	/**
	 * 根据日期获取该日期是该年的多少天
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getDayNumForYear(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		return ca.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 判断传入的日期是否是昨天
	 * 
	 * @param
	 * @return true为昨天
	 */
	public static boolean isYesterday(String time1, String time2) {
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		Date date2 = null;

		try {
			date = format1.parse(time1);
			date2 = format2.parse(time2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		boolean flag = false;
		// 先获取年份
		int year = Integer.valueOf(new SimpleDateFormat("yyyy").format(date));

		// 获取当前年份 和 一年中的第几天
		int day = getDayNumForYear(date);
		int currentYear = Integer.valueOf(new SimpleDateFormat("yyyy").format(new Date()));
		Integer currentDay = getDayNumForYear(date2);
		// 计算 如果是去年的
		if (currentYear - year == 1) {
			// 如果当前正好是 1月1日 计算去年有多少天，指定时间是否是一年中的最后一天
			if (currentDay == 1) {
				int yearDay;
				if (year % 400 == 0) {
					// 世纪闰年
					yearDay = 366;
				} else if (year % 4 == 0 && year % 100 != 0) {
					// 普通闰年
					yearDay = 366;
				} else {
					// 平年
					yearDay = 365;
				}
				if (day == yearDay) {
					flag = true;
				}
			}
		} else {
			if (currentDay - day == 1) {
				flag = true;
			}
		}
		return flag;
	}

	public static void main(String[] args) throws ParseException {

		DateFormat format1 =  new  SimpleDateFormat( "yyyy-MM-dd" );
		Date date = null;
		String str = null;
		str="2020-10-10 11:11:11";
		date = format1.parse(str);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		System.out.println(dateString);
	}
	/**
	 * Map 按value值从大到小排序，并取前10
	 *
	 * @param map
	 * @return
	 */
	public static Map<String, Integer> sortMapByValue(Map<String, Integer> map) {

		Map<String, Integer> sortedMap = new LinkedHashMap();

		List<Map.Entry<String, Integer>> lists = new ArrayList(map.entrySet());
		Collections.sort(lists, (o1, o2) -> {
			double q1 = o1.getValue();
			double q2 = o2.getValue();
			double p = q2 - q1;
			if (p > 0) {
				return 1;
			} else if (p == 0) {
				return 0;
			} else {
				return -1;
			}
		});

		if (lists.size() >= 15) {
			for (Map.Entry<String, Integer> set : lists.subList(0, 15)) {
				sortedMap.put(set.getKey(), set.getValue());
			}
		} else {
			for (Map.Entry<String, Integer> set : lists) {
				sortedMap.put(set.getKey(), set.getValue());
			}
		}
		return sortedMap;
	}
	
	public  String ellipsis(final String text)
	{
	    if(text.length()>6){
	    	 return text.substring(0, 6) + "...";
	    }else{
	    	 return text;
	    }
		
	   
	}

	public static List<String> getLastTimeInterval() throws ParseException {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
		int offset1 = 1 - dayOfWeek;
		int offset2 = 7 - dayOfWeek;
		calendar1.add(Calendar.DATE, offset1 - 7);
		calendar2.add(Calendar.DATE, offset2 - 7);
		// System.out.println(sdf.format(calendar1.getTime()));// last Monday
		String lastBeginDate = sdf.format(calendar1.getTime());
		// System.out.println(sdf.format(calendar2.getTime()));// last Sunday
		String lastEndDate = sdf.format(calendar2.getTime());
		Date dBegin = sdf.parse(lastBeginDate);
		Date dEnd = sdf.parse(lastEndDate);
		List<Date> lDate = findDates(dBegin, dEnd);// 获取这周所有date
		sdf = new SimpleDateFormat("MM-dd");
		for (Date date : lDate) {
			list.add(sdf.format(date));

		}
		return list;
	}

	public static List<Date> findDates(Date dBegin, Date dEnd) {
		List lDate = new ArrayList();
		lDate.add(dBegin);
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime())) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			lDate.add(calBegin.getTime());
		}
		return lDate;
	}

	public static List<String> getTimeInterval() throws ParseException {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		// System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		// System.out.println("所在周星期日的日期：" + imptimeEnd);
		Date dBegin = sdf.parse(imptimeBegin);
		Date dEnd = sdf.parse(imptimeEnd);
		List<Date> lDate = findDates(dBegin, dEnd);// 获取这周所有date
		sdf = new SimpleDateFormat("MM-dd");
		for (Date date : lDate) {
			list.add(sdf.format(date));

		}
		return list;
	}

	/**
	 * 记录饮食 最近记录和最近收藏
	 */
	@RequestMapping(value = "/foodRrecordDiet")
	@ResponseBody
	public Result foodRrecordDiet(String usercode) {
		if (StringUtil.isEmpty(usercode)) {
			return Result.error("参数异常");
		}
		// 收藏
		List<YhFoodCookbook2> findFoodCookCollectionByUserCodeForFoodInfo = YhFoodCookbook2ServiceImpl
				.findFoodCookCollectionByUserCodeForFoodInfo(usercode);
		// 最近十条常吃
		List<YhFoodCookbook2> zuiJinEat = YhFoodCookbook2ServiceImpl.findZuiJinEat(usercode);
		List<String> findUserFoodDiaryAllInfo = YhFoodCookbook2ServiceImpl.findUserFoodDiaryAllInfo(usercode);
		List<YhFoodCookbook2> findFoodCookBookInfoByUserCodeAndDescCommon=new ArrayList<YhFoodCookbook2>();
		List<String> foodidList=new ArrayList<String>();
		if(null!=zuiJinEat&&zuiJinEat.size()>0){
			if(zuiJinEat.size()<=15){
				findFoodCookBookInfoByUserCodeAndDescCommon=zuiJinEat;
			}else{
				findFoodCookBookInfoByUserCodeAndDescCommon=zuiJinEat.subList(0,15);
			}

		}else{
			findFoodCookBookInfoByUserCodeAndDescCommon = YhFoodCookbook2ServiceImpl
					.findFoodCookBookInfoByUserCodeAndDescCommon(usercode);
		}
		 /*List<YhFoodCookbook2> findFoodCookBookInfoByUserCodeAndDescCommon = YhFoodCookbook2ServiceImpl
				.findFoodCookBookInfoByUserCodeAndDescCommon(usercode);*/ 
		if(null!=findFoodCookBookInfoByUserCodeAndDescCommon){
			for (int i = 0; i < findFoodCookBookInfoByUserCodeAndDescCommon.size(); i++) {
				findFoodCookBookInfoByUserCodeAndDescCommon.get(i).setfCName(ellipsis(findFoodCookBookInfoByUserCodeAndDescCommon.get(i).getfCName()));
			}
		} 
		if(null!=findFoodCookCollectionByUserCodeForFoodInfo){
			for (int i = 0; i < findFoodCookCollectionByUserCodeForFoodInfo.size(); i++) {
				findFoodCookCollectionByUserCodeForFoodInfo.get(i).setfCName(ellipsis(findFoodCookCollectionByUserCodeForFoodInfo.get(i).getfCName()));
			}
		}
		List<YhFoodHotSearch> findFoodHotSearchInfoLimit10 = YhFoodHotSearchServiceImpl.findFoodHotSearchInfoLimit10();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("collection", findFoodCookCollectionByUserCodeForFoodInfo);
		map.put("common", findFoodCookBookInfoByUserCodeAndDescCommon);
		map.put("hotList", findFoodHotSearchInfoLimit10);
		return Result.ok(map);
	}

	/**
	 * 根据食物id查询食物详情
	 */
	@RequestMapping(value = "/findFoodInfoByCode")
	@ResponseBody
	public Result findFoodInfoByCode(Integer foodid, String usercode) {
		if (StringUtil.isEmpty(usercode) || null == foodid) {
			return Result.error("参数异常");
		}
		YhFoodCookbook2 selectByPrimaryKey = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(foodid);
		double num=0;
		if (null != selectByPrimaryKey) {
			//菜肴
			if("10".equals(selectByPrimaryKey.getfType())){
				YhFoodCooked selectByPrimaryKey2 = YhFoodCookedMapper.selectByPrimaryKey(selectByPrimaryKey.getfCId());
				List<YhFoodCookbook2> blist=new ArrayList<YhFoodCookbook2>();
				if(null!=selectByPrimaryKey2){
					
					//90178_0.3;90034_0.7;
					String getfCookedelements = selectByPrimaryKey2.getfCookedelements();
					String[] split = getfCookedelements.split(";");
					 for (String string : split) {
						 String[] split2 = string.split("_");
						 YhFoodCookbook2 selectByPrimaryKey3 = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(split2[0]));
						 BigDecimal b1 = new BigDecimal(split2[1]);
						 BigDecimal b2 = new BigDecimal(100);
						 selectByPrimaryKey3.setComponent(b1.multiply(b2).doubleValue());
						 blist.add(selectByPrimaryKey3);
					 }
				}
				
				selectByPrimaryKey2.setBlist(blist);
				selectByPrimaryKey.setYhFoodCooked(selectByPrimaryKey2);
				
			}else{
				
			}
			String notRecommendFood = selectByPrimaryKey.getNotRecommendFood();
			if(StringUtil.isNotEmpty(notRecommendFood)){
				List<String> list=new ArrayList<>();
				String[] split = notRecommendFood.split(";");
				for (String string :split) {
					YhDiseaseCategory yhDiseaseCategory = YhDiseaseCategoryMapper.selectByPrimaryKey(Integer.valueOf(string));
					list.add(yhDiseaseCategory.getDiseaseName());
				}
				selectByPrimaryKey.setNotRecommendFoodList(list);
			}

			String findFoodInfoByIDForName = YhFoodCookbook2ServiceImpl.findFoodInfoByIDForName(selectByPrimaryKey.getfType());
			selectByPrimaryKey.setTypeName(findFoodInfoByIDForName);
			selectByPrimaryKey.setCollectOrNot(YhFoodCookbookCollectionMapper.findCookBookByFoodid(foodid, usercode));
			//名称超长处理
			String getfCName = selectByPrimaryKey.getfCName();
			selectByPrimaryKey.setAllName(getfCName);
			selectByPrimaryKey.setfCName(ellipsis(getfCName));
			
			//该类型全部食物数
			double findFoodCookBookAllNumByType = YhFoodCookbook2ServiceImpl.findFoodCookBookAllNumByType(foodid);
			//该类型比它高热量的食物数
			double findFoodCookBookNumByTypeGTFood = YhFoodCookbook2ServiceImpl.findFoodCookBookNumByTypeGTFood(foodid);
			if(findFoodCookBookAllNumByType>0&&findFoodCookBookNumByTypeGTFood>0){
				num=findFoodCookBookNumByTypeGTFood/findFoodCookBookAllNumByType;
			}
			selectByPrimaryKey.setBelowHeat(Math.round(num*100));
			String foodDiaryinfoNewCanci = YhFoodUserDiaryServiceImpl.findFoodDiaryinfoNewCanci(usercode);
			if(StringUtil.isNotEmpty(foodDiaryinfoNewCanci)){
				String[] split = foodDiaryinfoNewCanci.split(";");
				String s = split[split.length - 1];
				String[] s1 = s.split("_");
				selectByPrimaryKey.setCanci(Integer.valueOf(s1[1]));
			}

			/*if(StringUtil.isNotEmpty(selectByPrimaryKey.getFbName())){
				String[] split = selectByPrimaryKey.getFbName().split(";");
				List resultList= new ArrayList<>(Arrays.asList(split));
				selectByPrimaryKey.setFbNameList(resultList);
			}*/
			return Result.ok(selectByPrimaryKey);
		}else{
			return Result.ok();
		}
	
	}
	public static String formatDouble(double d) {
		BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
		double num = bg.doubleValue();
		if (Math.round(num) - num == 0) {
			return String.valueOf((long) num);
		}
		return String.valueOf(num);
	}
	/**
	 * 收藏or取消收藏
	 */
	@RequestMapping(value = "/collectOrNotByUsercode")
	@ResponseBody
	public Result collectOrNotByUsercode(Integer foodid, String usercode, Integer type) {
		if (StringUtil.isEmpty(usercode) || null == foodid || null == type) {
			return Result.error("参数异常");
		}
		int returnNum = 0;
		// 收藏
		if (type == 1) {
			int findCookBookByFoodid = YhFoodCookbookCollectionMapper.findCookBookByFoodid(foodid, usercode);
			if (findCookBookByFoodid == 0) {
				YhFoodCookbookCollection record = new YhFoodCookbookCollection();
				record.setFoodid(foodid);
				record.setUsercode(usercode);
				record.setInserttime(new Date());

				returnNum = YhFoodCookbookCollectionMapper.insertSelective(record);
			}
		}
		// 删除收藏
		if (type == 2) {
			returnNum = YhFoodCookbookCollectionMapper.delInfoByFoodidAndUserCode(foodid, usercode);
		}

		return Result.ok(returnNum);
	}
	/**
	 * 饮食推荐页面 左右滑动换一换功能
	 */
	@RequestMapping(value = "/dietRecommendationSlide")
	@ResponseBody
	public Result dietRecommendationSlide(String usercode,Integer typeNum,Integer index,String recommend) {

		if(StringUtil.isNotEmpty(recommend)){
			String stringIndex="1";
			String[] split5 = recommend.split(":");
			if(split5.length!=2){
				return Result.ok();
			}
			stringIndex=split5[0];
			recommend=split5[1];
			YhFoodSrecipeRecordApp findFoodSrecipeRecordByUsercodeForNow = YhFoodSrecipeRecordAppServiceImpl
					.findFoodSrecipeRecordByUsercodeForNow(usercode);

			String mealId = findFoodSrecipeRecordByUsercodeForNow.getMealId();
			if (StringUtil.isEmpty(mealId)) {
				return Result.error();
			}
			// 查询膳食推荐早中晚餐食物
			YhFoodSrecipeApiNew selectByPrimaryKey = YhFoodSrecipeApiNewServiceImpl.selectByPrimaryKey(Long.parseLong(mealId));
			// 得到膳食推荐
			// 1;2;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24;25;26;27;28;29;30;
			String getfElements = selectByPrimaryKey.getfElements();
			String[] split = getfElements.split(";");

			Random rd2 = new Random();
			int x2 = rd2.nextInt(split.length);
			stringIndex=String.valueOf(x2);
			String string = split[x2];
			// 得到膳食推荐详情1_1210_437;1_1211_203;2_1212_496;2_1213_490;2_1214_129;3_1215_393;3_1216_127;3_1217_135;4_1218_129;
			YhFoodUserScrecipe findFoodUserScrecipeBySrecipeId = yhFoodUserScrecipeMapper
					.findFoodUserScrecipeBySrecipeId(Integer.valueOf(string));

			String getfElements2 = findFoodUserScrecipeBySrecipeId.getfElements();
			//换早餐
			if(null!=typeNum){
				String newfoodInfo="";
				int indexNewFood=0;
				String[] split2 = getfElements2.split(";");
				for (int i = 0; i < split2.length; i++) {
					String[] split3 = split2[i].split("_");
					if (split3.length < 3) {
						continue;
					}
					// 1代表早餐，2加餐，3中餐，4晚餐
					String type = split3[0];
					if(type.equals(String.valueOf(typeNum))){
						newfoodInfo=newfoodInfo+split2[i]+";";
						indexNewFood++;

					}
				}
				Random rd3 = new Random();
				int x3 = rd3.nextInt(indexNewFood);
				String[] split1 = newfoodInfo.split(";");
				String newFood = split1[x3];

				String recomStr="";
				int oldindex=0;
				String[] split3 = recommend.split(";");
				for (int i = 0; i < split3.length; i++) {
					String[] split4 = split3[i].split("_");
					if (split4.length < 3) {
						continue;
					}
					// 1代表早餐，2加餐，3中餐，4晚餐
					String type = split4[0];
					if(type.equals(String.valueOf(typeNum))){
						if(index==oldindex){
							recomStr=recomStr+newFood+";";
						}else{
							recomStr=recomStr+split3[i]+";";
						}
						oldindex++;
					}else{
						recomStr=recomStr+split3[i]+";";
					}
				}
				// 早1
				List<YhFoodCookbook2> listZao = new ArrayList<YhFoodCookbook2>();
				// 加2
				List<YhFoodCookbook2> listJia = new ArrayList<YhFoodCookbook2>();
				// 中3
				List<YhFoodCookbook2> listWu = new ArrayList<YhFoodCookbook2>();
				// 晚4
				List<YhFoodCookbook2> listWan = new ArrayList<YhFoodCookbook2>();

				String[] split4 = recomStr.split(";");

				// split2 xxx_xxx_xxx
				for (int i = 0; i < split4.length; i++) {

					String[] split6 = split4[i].split("_");
					if (split6.length < 3) {
						continue;
					}
					// 1代表早餐，2加餐，3中餐，4晚餐
					String type = split6[0];

					// 食材编号
					String foodid = split6[1];
					YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(foodid));
					if (null == foodInfo) {
						System.out.println("没有找到该食材" + foodid);
						continue;
					}
					// 食材克数
					String foodKeNum = split6[2];
					foodInfo.setGramNumber(Double.valueOf(foodKeNum));

					switch (type) {
						case "1":
							listZao.add(foodInfo);
							break;
						case "2":
							listWu.add(foodInfo);
							break;
						case "3":
							listWan.add(foodInfo);
							break;
						case "4":
							listJia.add(foodInfo);
							break;

						default:
							break;
					}

				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("listZao", listZao);
				map.put("listJia", listJia);
				map.put("recommend", stringIndex+":"+recomStr);
				map.put("listWu", listWu);
				map.put("listWan", listWan);

				return Result.ok(map);
			}
		}
		return  Result.ok();
	}
	/**
	 *
	 * 首次进入食物推荐页面的状态
	 */
	@RequestMapping(value = "/firstEntryFoodState")
	@ResponseBody
	public Result firstEntryFoodState(String usercode) {
		if (StringUtil.isEmpty(usercode)) {
			return Result.error("参数异常");
		}
		return Result.ok(yhFoodUserScrecipeMapper.findEnrolinfoByFood(usercode));
	}
	/**
	 *
	 * 首次进入食物推荐页面
	 */
	@RequestMapping(value = "/firstEntryFood")
	@ResponseBody
	public Result firstEntryFood(String usercode) {
		if (StringUtil.isEmpty(usercode)) {
			return Result.error("参数异常");
		}
		return Result.ok(yhFoodUserScrecipeMapper.modifyEnrolinfo(usercode));
	}
	/**
	 * 饮食推荐页面 换一换功能
	 */
	@RequestMapping(value = "/dietRecommendation")
	@ResponseBody
	public Result dietRecommendation(String usercode,Integer typeNum,String recommend) {
		if (StringUtil.isEmpty(usercode)) {
			return Result.error("参数异常");
		}
		int dayNum = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		Long recordKay=tuijianNum(usercode);
		String getfElements2="";
		String stringIndex="1";
		YhFoodSrecipeRecordApp findFoodSrecipeRecordByUsercodeForNow = YhFoodSrecipeRecordAppServiceImpl
				.findFoodSrecipeRecordByUsercodeForNow(usercode);

		if(StringUtil.isNotEmpty(recommend)){
			String[] split = recommend.split(":");
			stringIndex=split[0];
			if(split.length<2) {
				
							String mealId = findFoodSrecipeRecordByUsercodeForNow.getMealId();
							if (StringUtil.isEmpty(mealId)) {
								return Result.error();
							}
							// 随机得到一个膳食推荐
							// 得到膳食推荐详情1_1210_437;1_1211_203;2_1212_496;2_1213_490;2_1214_129;3_1215_393;3_1216_127;3_1217_135;4_1218_129;
							YhFoodUserScrecipe findFoodUserScrecipeBySrecipeId = YhFoodSrecipeApiNewServiceImpl.findFoodTuiJian(mealId, dayNum);
							 getfElements2 = findFoodUserScrecipeBySrecipeId.getfElements();
				recommend=getfElements2;
			}else {
				recommend=split[1];
			}
			
			
			
		}
		
		// 早1
		List<YhFoodCookbook2> listZao = new ArrayList<YhFoodCookbook2>();
		// 加2
		List<YhFoodCookbook2> listJia = new ArrayList<YhFoodCookbook2>();
		// 中3
		List<YhFoodCookbook2> listWu = new ArrayList<YhFoodCookbook2>();
		// 晚4
		List<YhFoodCookbook2> listWan = new ArrayList<YhFoodCookbook2>();
		
		if(0==typeNum&&StringUtil.isNotEmpty(recommend)){
			String[] split2 = recommend.split(";");

			// split2 xxx_xxx_xxx
			for (int i = 0; i < split2.length; i++) {

				String[] split3 = split2[i].split("_");
				if (split3.length < 3) {
					continue;
				}
				// 1代表早餐，2加餐，3中餐，4晚餐
				String type = split3[0];

				// 食材编号
				String foodid = split3[1];
				YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(foodid));
				if (null == foodInfo) {
					System.out.println("没有找到该食材" + foodid);
					continue;
				}
				// 食材克数
				String foodKeNum = split3[2];
				foodInfo.setGramNumber(Double.valueOf(foodKeNum));

				switch (type) {
				case "1":
					listZao.add(foodInfo);
					break;
				case "2":
					listWu.add(foodInfo);
					break;
				case "3":
					listWan.add(foodInfo);
					break;
				case "4":
					listJia.add(foodInfo);
					break;

				default:
					break;
				}

			}
		}else
		// 如果膳食推荐不等于空
		if (null != findFoodSrecipeRecordByUsercodeForNow
				&& null != findFoodSrecipeRecordByUsercodeForNow.getRecordKay()) {
			String mealId = findFoodSrecipeRecordByUsercodeForNow.getMealId();
			if (StringUtil.isEmpty(mealId)) {
				return Result.error();
			}
			// 查询膳食推荐早中晚餐食物
			YhFoodSrecipeApiNew selectByPrimaryKey = YhFoodSrecipeApiNewServiceImpl.selectByPrimaryKey(Long.parseLong(mealId));
			// 得到膳食推荐
			// 1;2;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24;25;26;27;28;29;30;
			String getfElements = selectByPrimaryKey.getfElements();
			String[] split = getfElements.split(";");

			Random rd2 = new Random();
			int x2 = rd2.nextInt(split.length);
			String string = split[x2];
			// 随机得到一个膳食推荐
			System.out.println(string);
			// 得到膳食推荐详情1_1210_437;1_1211_203;2_1212_496;2_1213_490;2_1214_129;3_1215_393;3_1216_127;3_1217_135;4_1218_129;
			YhFoodUserScrecipe findFoodUserScrecipeBySrecipeId = yhFoodUserScrecipeMapper
					.findFoodUserScrecipeBySrecipeId(Integer.valueOf(string));
			
			 getfElements2 = findFoodUserScrecipeBySrecipeId.getfElements();
			//recommend
			 //换早餐
			 if(null!=typeNum){
				 String newfoodInfo="";
				 String[] split2 = getfElements2.split(";");
				 for (int i = 0; i < split2.length; i++) {
					 String[] split3 = split2[i].split("_");
						if (split3.length < 3) {
							continue;
						}
						// 1代表早餐，2加餐，3中餐，4晚餐
						String type = split3[0];
						if(type.equals(String.valueOf(typeNum))){
							newfoodInfo=newfoodInfo+split2[i]+";";
						}
				}
				 
				 String recomStr="";
				 String[] split3 = recommend.split(";");
				 for (int i = 0; i < split3.length; i++) {
					 String[] split4 = split3[i].split("_");
					 if (split4.length < 3) {
							continue;
						}
					// 1代表早餐，2加餐，3中餐，4晚餐
						String type = split4[0];
						if(!type.equals(String.valueOf(typeNum))){
							recomStr=recomStr+split3[i]+";";
						}
				}
				 getfElements2=recomStr+newfoodInfo;
			 }
			 
			
			 recommend= getfElements2;
			 
			 String[] split2 = getfElements2.split(";");

			// split2 xxx_xxx_xxx
			for (int i = 0; i < split2.length; i++) {

				String[] split3 = split2[i].split("_");
				if (split3.length < 3) {
					continue;
				}
				// 1代表早餐，2加餐，3中餐，4晚餐
				String type = split3[0];

				// 食材编号
				String foodid = split3[1];
				YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(foodid));
				if (null == foodInfo) {
					System.out.println("没有找到该食材" + foodid);
					continue;
				}
				// 食材克数
				String foodKeNum = split3[2];
				foodInfo.setGramNumber(Double.valueOf(foodKeNum));

				switch (type) {
				case "1":
					listZao.add(foodInfo);
					break;
				case "2":
					listWu.add(foodInfo);
					break;
				case "3":
					listWan.add(foodInfo);
					break;
				case "4":
					listJia.add(foodInfo);
					break;

				default:
					break;
				}

			}

		} else {
			YhFoodUserScrecipe selectIsHaveFood = YhHealthyPlanServiceImpl.selectIsHaveFood(usercode);
			if(null!=selectIsHaveFood){
				getfElements2=selectIsHaveFood.getfElements();
			}else {
				YhUserReginfoApp findYhUserReginfo = YhUserReginfoAppServiceImpl.findYhUserReginfo(usercode);
				Integer gender = findYhUserReginfo.getGender();

				YhFoodSrecipeApiNew selectByPrimaryKey = YhFoodSrecipeApiNewServiceImpl.findNewFoodSrecipeForSex(usercode,gender);
				
				getfElements2=selectByPrimaryKey.getfElements();
			}
			
			
			String[] split = getfElements2.split(";");

			Random rd2 = new Random();
			int x2 = rd2.nextInt(split.length);
			String string = split[x2];
			// 随机得到一个膳食推荐
			System.out.println(string);
			// 得到膳食推荐详情1_1210_437;1_1211_203;2_1212_496;2_1213_490;2_1214_129;3_1215_393;3_1216_127;3_1217_135;4_1218_129;
			YhFoodUserScrecipe findFoodUserScrecipeBySrecipeId = yhFoodUserScrecipeMapper
					.findFoodUserScrecipeBySrecipeId(Integer.valueOf(string));
			 getfElements2 = findFoodUserScrecipeBySrecipeId.getfElements();
			//recommend
			 //换早餐
			 if(null!=typeNum){
				 String newfoodInfo="";
				 String[] split2 = getfElements2.split(";");
				 for (int i = 0; i < split2.length; i++) {
					 String[] split3 = split2[i].split("_");
						if (split3.length < 3) {
							continue;
						}
						// 1代表早餐，2加餐，3中餐，4晚餐
						String type = split3[0];
						if(type.equals(String.valueOf(typeNum))){
							newfoodInfo=newfoodInfo+split2[i]+";";
						}
				}
				 
				 String recomStr="";
				 String[] split3 = recommend.split(";");
				 for (int i = 0; i < split3.length; i++) {
					 String[] split4 = split3[i].split("_");
					 if (split4.length < 3) {
							continue;
						}
					// 1代表早餐，2加餐，3中餐，4晚餐
						String type = split4[0];
						if(!type.equals(String.valueOf(typeNum))){
							recomStr=recomStr+split3[i]+";";
						}
				}
				 getfElements2=recomStr+newfoodInfo;
			 }
			 recommend=getfElements2;
			 
			String[] split2 = getfElements2.split(";");

			// split2 xxx_xxx_xxx
			for (int i = 0; i < split2.length; i++) {

				String[] split3 = split2[i].split("_");
				if (split3.length < 3) {
					continue;
				}
				// 1代表早餐，2加餐，3中餐，4晚餐
				String type = split3[0];

				// 食材编号
				String foodid = split3[1];
				YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(foodid));
				if (null == foodInfo) {
					System.out.println("没有找到该食材" + foodid);
					continue;
				}
				// 食材克数
				String foodKeNum = split3[2];
				foodInfo.setGramNumber(Double.valueOf(foodKeNum));

				switch (type) {
				case "1":
					listZao.add(foodInfo);
					break;
				case "2":
					listWu.add(foodInfo);
					break;
				case "3":
					listWan.add(foodInfo);
					break;
				case "4":
					listJia.add(foodInfo);
					break;

				default:
					break;
				}

			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("listZao", listZao);
		map.put("listJia", listJia);
		map.put("recommend", stringIndex+":"+recommend);
		map.put("listWu", listWu);
		map.put("listWan", listWan);
		map.put("recordKay", recordKay);
		return Result.ok(map);
	}

	/**
	 * 饮食记录页
	 */
	@RequestMapping(value = "/dietRecordHome")
	@ResponseBody
	public Result dietRecordHome(String usercode, String time,String foodRecommend) {
		if (StringUtil.isEmpty(usercode)) {
			return Result.error("参数异常");
		}
		 String[] split4 = time.split("-");
		 //号
		 String string2 = split4[2];
		 int dayNum=Integer.valueOf(string2);
		// 查询已经记录的所有饮食日记时间集合 赋值小程序时间插件
		List<String> timeList = YhFoodUserDiaryServiceImpl.findFoodUserDiaryForTimeList(usercode);
		// 建议摄入
		Long recordKay = 0l;
		BigDecimal energyKcal = new BigDecimal("0");
		// 早1
		List<YhFoodCookbook2> listZao = new ArrayList<YhFoodCookbook2>();
		// 午2
		List<YhFoodCookbook2> listWu = new ArrayList<YhFoodCookbook2>();
		// 晚3
		List<YhFoodCookbook2> listWan = new ArrayList<YhFoodCookbook2>();
		// 加4
		List<YhFoodCookbook2> listJia = new ArrayList<YhFoodCookbook2>();

		// 早1推荐
		List<YhFoodCookbook2> listZaoTj = new ArrayList<YhFoodCookbook2>();
		// 午2推荐
		List<YhFoodCookbook2> listWuTj = new ArrayList<YhFoodCookbook2>();
		// 晚3推荐
		List<YhFoodCookbook2> listWanTj = new ArrayList<YhFoodCookbook2>();
		// 加4推荐
		List<YhFoodCookbook2> listJiaTj = new ArrayList<YhFoodCookbook2>();
		YhFoodSrecipeRecordApp findFoodSrecipeRecordByUsercodeForNow = YhFoodSrecipeRecordAppServiceImpl
				.findFoodSrecipeRecordByUsercodeForNow(usercode);
		
 
		if(StringUtil.isNotEmpty(foodRecommend)) {
			recordKay = findFoodSrecipeRecordByUsercodeForNow.getRecordKay();
		String	getfElements2=foodRecommend.split(":")[1];
		String[] split2 = getfElements2.split(";");

		// split2 xxx_xxx_xxx
		for (int i = 0; i < split2.length; i++) {

			String[] split3 = split2[i].split("_");
			if (split3.length < 3) {
				continue;
			}
			// 1代表早餐，2加餐，3中餐，4晚餐
			String type = split3[0];

			// 食材编号
			String foodid = split3[1];
			YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(foodid));
			if (null == foodInfo) {
				System.out.println("没有找到该食材" + foodid);
				continue;
			}
			// 食材克数
			String foodKeNum = split3[2];
			foodInfo.setGramNumber(Double.valueOf(foodKeNum));

			switch (type) {
			case "1":
				listZaoTj.add(foodInfo);
				break;
			case "2":
				listWuTj.add(foodInfo);
				break;
			case "3":
				listWanTj.add(foodInfo);
				break;
			case "4":
				listJiaTj.add(foodInfo);
				break;

			default:
				break;
			}

		}
			
			
		}else if (null != findFoodSrecipeRecordByUsercodeForNow) {
			recordKay = findFoodSrecipeRecordByUsercodeForNow.getRecordKay();
			String mealId = findFoodSrecipeRecordByUsercodeForNow.getMealId();
			if (StringUtil.isNotEmpty(mealId)) {
				// 查询膳食推荐早中晚餐食物
				YhFoodSrecipe selectByPrimaryKey = YhFoodSrecipeServiceImpl
						.findFoodSrecipeByFsId(Integer.valueOf(mealId));
				// 得到膳食推荐
				// 1;2;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24;25;26;27;28;29;30;
				String getfElements = selectByPrimaryKey.getfElements();
				String[] split = getfElements.split(";");

			 
				String string="1";
				if(split.length-1>=dayNum){
					string = split[dayNum];
				}else{
					string = split[0];
				}
				// 随机得到一个膳食推荐
				System.out.println(string);
				// 得到膳食推荐详情1_1210_437;1_1211_203;2_1212_496;2_1213_490;2_1214_129;3_1215_393;3_1216_127;3_1217_135;4_1218_129;
				YhFoodUserScrecipe findFoodUserScrecipeBySrecipeId = yhFoodUserScrecipeMapper
						.findFoodUserScrecipeBySrecipeId(Integer.valueOf(string));
				String getfElements2 = findFoodUserScrecipeBySrecipeId.getfElements();
				String[] split2 = getfElements2.split(";");

				// split2 xxx_xxx_xxx
				for (int i = 0; i < split2.length; i++) {

					String[] split3 = split2[i].split("_");
					if (split3.length < 3) {
						continue;
					}
					// 1代表早餐，2加餐，3中餐，4晚餐
					String type = split3[0];

					// 食材编号
					String foodid = split3[1];
					YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(foodid));
					if (null == foodInfo) {
						System.out.println("没有找到该食材" + foodid);
						continue;
					}
					// 食材克数
					String foodKeNum = split3[2];
					foodInfo.setGramNumber(Double.valueOf(foodKeNum));

					switch (type) {
					case "1":
						listZaoTj.add(foodInfo);
						break;
					case "2":
						listWuTj.add(foodInfo);
						break;
					case "3":
						listWanTj.add(foodInfo);
						break;
					case "4":
						listJiaTj.add(foodInfo);
						break;

					default:
						break;
					}

				}
			}

		} else {
			String getfElements ="";
			YhFoodUserScrecipe selectIsHaveFood = YhHealthyPlanServiceImpl.selectIsHaveFood(usercode);
			if(null!=selectIsHaveFood){
				getfElements=selectIsHaveFood.getfElements();
			}else {
				YhUserReginfoApp findYhUserReginfo = YhUserReginfoAppServiceImpl.findYhUserReginfo(usercode);
				Integer gender = findYhUserReginfo.getGender();

				YhFoodSrecipeApiNew findNewFoodSrecipeForSex = YhFoodSrecipeApiNewServiceImpl.findNewFoodSrecipeForSex(usercode,gender);
				recordKay = Long.valueOf(findNewFoodSrecipeForSex.geteLevel());
				  getfElements = findNewFoodSrecipeForSex.getfElements();
				
			}
			
			// 得到膳食推荐
			// 1;2;4;5;6;7;8;9;10;11;12;13;14;15;16;17;18;19;20;21;22;23;24;25;26;27;28;29;30;
			
			String[] split = getfElements.split(";");

			String string="1";
			if(split.length>=dayNum){
				string = split[dayNum-1];
			}else{
				string = split[0];
			}
			// 随机得到一个膳食推荐
			System.out.println(string);
			// 得到膳食推荐详情1_1210_437;1_1211_203;2_1212_496;2_1213_490;2_1214_129;3_1215_393;3_1216_127;3_1217_135;4_1218_129;
			YhFoodUserScrecipe findFoodUserScrecipeBySrecipeId = yhFoodUserScrecipeMapper
					.findFoodUserScrecipeBySrecipeId(Integer.valueOf(string));
			String getfElements2 = findFoodUserScrecipeBySrecipeId.getfElements();
			String[] split2 = getfElements2.split(";");

			// split2 xxx_xxx_xxx
			for (int i = 0; i < split2.length; i++) {

				String[] split3 = split2[i].split("_");
				if (split3.length < 3) {
					continue;
				}
				// 1代表早餐，2加餐，3中餐，4晚餐
				String type = split3[0];

				// 食材编号
				String foodid = split3[1];
				YhFoodCookbook2 foodInfo = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(foodid));
				if (null == foodInfo) {
					 System.out.println("没有找到该食材" + foodid);
					 continue;
				}
				// 食材克数
				String foodKeNum = split3[2];
				foodInfo.setGramNumber(Double.valueOf(foodKeNum));

				switch (type) {
				case "1":
					listZaoTj.add(foodInfo);
					break;
				case "2":
					listWuTj.add(foodInfo);
					break;
				case "3":
					listWanTj.add(foodInfo);
					break;
				case "4":
					listJiaTj.add(foodInfo);
					break;

				default:
					break;
				}

			}
		}
		// 根据用户和时间查询饮食日记信息 yyyy-mm-dd 查询已摄入热量 查询当前时间膳食日记
		List<YhFoodUserDiary> findFoodUserDiaryByusercodeAndTime = YhFoodUserDiaryServiceImpl
				.findFoodUserDiaryByusercodeAndTime(usercode, time);
		if (null != findFoodUserDiaryByusercodeAndTime && findFoodUserDiaryByusercodeAndTime.size() > 0) {
			YhFoodUserDiary yhFoodUserDiary = findFoodUserDiaryByusercodeAndTime.get(0);
			if (null != yhFoodUserDiary) {
				energyKcal = new BigDecimal(yhFoodUserDiary.getEnergyKcal());
				String getfElements = yhFoodUserDiary.getfElements();
				if (StringUtil.isNotEmpty(getfElements)) {
					
					//  食物类型_早中晚餐类型_食物id_食物克数
					// 10_1_1514_100,451_55,542_45;1_1_515_200;
					 
					 
					String[] split = getfElements.split(";");
					for (int i = 0; i < split.length; i++) {
						String string = split[i];

						String[] split2 = string.split("_");
						String dishType=split2[0];
						String rjType = split2[1];
						YhFoodCookbook2 selectByExample = YhFoodCookbook2ServiceImpl
								.selectByPrimaryKey(Integer.valueOf(split2[2]));
						
						if(!"10".equals(dishType)){
							if(null==selectByExample){
								continue;
							}
							BigDecimal energyKcal2 = new BigDecimal(selectByExample.getEnergyKcal());
							if (null != energyKcal2) {
								BigDecimal b=	energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(split2[3]));
								BigDecimal f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP);
								selectByExample.setEnergyKcalRiji(f1.toString());
							}
							selectByExample.setKeshu(split2[3]);
							
						}else{
							if(null==selectByExample){
								continue;
							}
							// 10_1_1514_100,451_55,542_45;
							String[] split3 = string.split(",");
							List<YhFoodCookbook2> blist=new ArrayList<YhFoodCookbook2>();
							// 10_1_1514_100
							String string3 = split3[0];
							String[] split41 = string3.split("_");
							
							String dishfoodid=split41[2];//食物id
							String keshu=split41[3];//克数
							//食物详情
							selectByExample = YhFoodCookbook2ServiceImpl
									.selectByPrimaryKey(Integer.valueOf(dishfoodid));
							//菜肴详情
							YhFoodCooked selectByPrimaryKey2=YhFoodCookedMapper.selectByPrimaryKey(selectByExample.getfCId());
							BigDecimal energyKcal2 = new BigDecimal(selectByExample.getEnergyKcal());
							if (null != energyKcal2) {
								BigDecimal b=	energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu));
								BigDecimal f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP);
								selectByExample.setEnergyKcalRiji(f1.toString());
							}
							selectByExample.setKeshu(String.valueOf(keshu));
							BigDecimal allKacl=new BigDecimal("0");
							// 451_55,542_45;
							for (int j=1;j<split3.length;j++) {
								if(null!=selectByPrimaryKey2){
									//90178_0.3;90034_0.7;
									String getfCookedelements = selectByPrimaryKey2.getfCookedelements();
									String[] split5 = getfCookedelements.split(";");
									String string4 = split5[j-1];
									String[] split6 = string4.split("_");
									
										 String[] split12 = split3[j].split("_");
										 YhFoodCookbook2 selectByPrimaryKey3 = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(Integer.valueOf(split12[0]));
										 selectByPrimaryKey3.setComponent(Double.valueOf(split12[1]));


										BigDecimal b = new BigDecimal(selectByPrimaryKey3.getEnergyKcal()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(split12[1]));
									BigDecimal f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP);
										allKacl=allKacl.add(f1);
										 selectByPrimaryKey3.setEnergyKcalRiji(f1.toString());
										 blist.add(selectByPrimaryKey3);
								}
							}

							BigDecimal f1allKacl = allKacl.setScale(2, BigDecimal.ROUND_HALF_UP);
							selectByExample.setEnergyKcalRiji(f1allKacl.toString());
							selectByPrimaryKey2.setBlist(blist);
							selectByExample.setYhFoodCooked(selectByPrimaryKey2);
							
							 
							
							
						
						}

						
						
						

						switch (rjType) {
						case "1":
							listZao.add(selectByExample);
							break;
						case "2":
							listWu.add(selectByExample);
							break;
						case "3":
							listWan.add(selectByExample);
							break;
						case "4":
							listJia.add(selectByExample);
							break;

						default:
							break;
						}

					}
				}
			}
		}



		String secondaryFlag="";
		//查询是否妊娠期，或者是否有引起继发性骨质疏松症的疾病
		YhQuestionCheckJson thQuestionCheckJson = YhQuestionCheckJsonServiceImpl.findThQuestionCheckJson(usercode);
		if(null!=thQuestionCheckJson){
			String checkJson = thQuestionCheckJson.getUserCheckAnswer();
			if(StringUtil.isNotEmpty(checkJson)){
				JSONObject object = JSONObject.parseObject(checkJson);
				//正在妊娠期
				String gestation = object.getString("female_gestation");
				if("1".equals(gestation)){
					secondaryFlag="因您：正在妊娠期，需要在专业医师的指导下开具膳食处方，如有疑问，请随时联系您的健管师。";
				}
				//患有引起继发性骨质疏松症的疾病:1
				String secondary =JSONObject.parseObject(object.getString("secondary_op_his")).getString("disFlag");
				//患有引起继发性骨质疏松症的疾病:1
				String ophisDisFlag =JSONObject.parseObject(object.getString("op_his")).getString("disFlag");

				if("1".equals(gestation)&&("1".equals(secondary)||"1".equals(ophisDisFlag))){
					secondaryFlag="因您：正在妊娠期，患有引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、甲状旁腺功能亢进症、库欣综合征、慢性营养不良等），需要在专业医师的指导下开具膳食处方，如有疑问，请随时联系您的健管师。";
				}

				if((!"1".equals(gestation))&&("1".equals(secondary)||"1".equals(ophisDisFlag))){
					secondaryFlag="因您：患有引起继发性骨质疏松症的疾病（如长期的甲状腺机能亢进症、甲状旁腺功能亢进症、库欣综合征、慢性营养不良等），需要在专业医师的指导下开具膳食处方，如有疑问，请随时联系您的健管师。";
				}

			}
		}


		// 早餐推荐
		String listZaoTjInfo = "";

		if (listZaoTj.size() > 0&&"".equals(secondaryFlag)) {
			for (int i = 0; i < listZaoTj.size(); i++) {
				YhFoodCookbook2 yhFoodCookbook2 = listZaoTj.get(i);
				String getfCName = yhFoodCookbook2.getfCName();
				Double gramNumber = yhFoodCookbook2.getGramNumber();
				if (i == listZaoTj.size() - 1) {
					listZaoTjInfo = listZaoTjInfo + getfCName + gramNumber + "克";
				} else {
					listZaoTjInfo = listZaoTjInfo + getfCName + gramNumber + "克" + "/";
				}
			}
		}

		// 加餐推荐
		String listJiaTjInfo = "";
		if (listJiaTj.size() > 0&&"".equals(secondaryFlag)) {
			for (int i = 0; i < listJiaTj.size(); i++) {
				YhFoodCookbook2 yhFoodCookbook2 = listJiaTj.get(i);
				String getfCName = yhFoodCookbook2.getfCName();
				Double gramNumber = yhFoodCookbook2.getGramNumber();
				if (i == listJiaTj.size() - 1) {
					listJiaTjInfo = listJiaTjInfo + getfCName + gramNumber + "克";
				} else {
					listJiaTjInfo = listJiaTjInfo + getfCName + gramNumber + "克" + "/";
				}
			}
		}

		// 午餐推荐
		String listWuTjInfo = "";
		if (listWuTj.size() > 0&&"".equals(secondaryFlag)) {
			for (int i = 0; i < listWuTj.size(); i++) {
				YhFoodCookbook2 yhFoodCookbook2 = listWuTj.get(i);
				String getfCName = yhFoodCookbook2.getfCName();
				Double gramNumber = yhFoodCookbook2.getGramNumber();
				if (i == listWuTj.size() - 1) {
					listWuTjInfo = listWuTjInfo + getfCName + gramNumber + "克";
				} else {
					listWuTjInfo = listWuTjInfo + getfCName + gramNumber + "克" + "/";
				}
			}
		}

		// 午餐推荐
		String listWanTjInfo = "";
		if (listWanTj.size() > 0&&"".equals(secondaryFlag)) {
			for (int i = 0; i < listWanTj.size(); i++) {
				YhFoodCookbook2 yhFoodCookbook2 = listWanTj.get(i);
				String getfCName = yhFoodCookbook2.getfCName();
				Double gramNumber = yhFoodCookbook2.getGramNumber();
				if (i == listWanTj.size() - 1) {
					listWanTjInfo = listWanTjInfo + getfCName + gramNumber + "克";
				} else {
					listWanTjInfo = listWanTjInfo + getfCName + gramNumber + "克" + "/";
				}
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timeList", timeList);
		map.put("recordKay", recordKay);
		map.put("energyKcal", energyKcal);
		BigDecimal result =new BigDecimal("0");
		BigDecimal xiangcha =new BigDecimal("0");
		if(energyKcal.compareTo(new BigDecimal("0"))==1){


						BigDecimal num1=new BigDecimal(recordKay);
			xiangcha=num1.subtract(energyKcal).setScale(2,BigDecimal.ROUND_HALF_UP);

			result=energyKcal.divide(num1,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP);


		}
		
		map.put("bfbKay", result);
		map.put("xiangcha", xiangcha);
		map.put("listZao", listZao);
		BigDecimal energyKcalRijiAllZao =new BigDecimal("0");
		if(listZao.size()>0){
			for (int i = 0; i < listZao.size(); i++) {
				BigDecimal energyKcalRiji = new  BigDecimal(listZao.get(i).getEnergyKcalRiji());
				energyKcalRijiAllZao=energyKcalRijiAllZao.add(energyKcalRiji);
			}
		}

		BigDecimal energyKcalRijiAllZaoflat = energyKcalRijiAllZao.setScale(2, BigDecimal.ROUND_HALF_UP);
		map.put("energyKcalRijiAllZao", energyKcalRijiAllZaoflat);
		
		map.put("listJia", listJia);
		BigDecimal energyKcalRijiAllJia = new BigDecimal("0");
		if(listJia.size()>0){
			for (int i = 0; i < listJia.size(); i++) {
				BigDecimal energyKcalRiji = new  BigDecimal(listJia.get(i).getEnergyKcalRiji());
				energyKcalRijiAllJia=energyKcalRijiAllJia.add(energyKcalRiji);
			}
					
		}

		BigDecimal energyKcalRijiAllJiaflat = energyKcalRijiAllJia.setScale(2, BigDecimal.ROUND_HALF_UP);
		map.put("energyKcalRijiAllJia", energyKcalRijiAllJiaflat);
		
		map.put("listWu", listWu);
		BigDecimal energyKcalRijiAllWu =new BigDecimal("0");
		if(listWu.size()>0){
			for (int i = 0; i < listWu.size(); i++) {
				BigDecimal energyKcalRiji = new BigDecimal(listWu.get(i).getEnergyKcalRiji());
				energyKcalRijiAllWu=energyKcalRijiAllWu.add(energyKcalRiji);
			}
		}

		BigDecimal energyKcalRijiAllWuflat = energyKcalRijiAllWu.setScale(2, BigDecimal.ROUND_HALF_UP);
		map.put("energyKcalRijiAllWu", energyKcalRijiAllWuflat);
		
		map.put("listWan", listWan);
		BigDecimal energyKcalRijiAllWan=new BigDecimal("0");
		if(listWan.size()>0){
			for (int i = 0; i < listWan.size(); i++) {
				BigDecimal energyKcalRiji = new BigDecimal(listWan.get(i).getEnergyKcalRiji());
				energyKcalRijiAllWan=energyKcalRijiAllWan.add(energyKcalRiji);
			}
		}

		BigDecimal energyKcalRijiAllWanflat = energyKcalRijiAllWan.setScale(2, BigDecimal.ROUND_HALF_UP);
		map.put("energyKcalRijiAllWan", energyKcalRijiAllWanflat);
		map.put("zaoKcal", Math.round(recordKay*0.25));
		map.put("wuKcal", Math.round(recordKay*0.35));
		map.put("wanKcal", Math.round(recordKay*0.25));
		map.put("jiaKcal", Math.round(recordKay*0.15));
		map.put("listZaoTjInfo", listZaoTjInfo);
		map.put("listJiaTjInfo", listJiaTjInfo);
		map.put("listWuTjInfo", listWuTjInfo);
		map.put("listWanTjInfo", listWanTjInfo);
		 
		return Result.ok(map);
	}

	/**
	 * 饮食统计
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(value = "/dietStatistics")
	@ResponseBody
	public Result dietStatistics(String usercode, String type) throws ParseException {
		if (StringUtil.isEmpty(usercode) || StringUtil.isEmpty(type)) {
			return Result.error("参数异常");
		}
		if (type.equals("本周")) {
			int[] findFoodUserDuary = YhFoodUserDiaryServiceImpl.findFoodUserDuary(usercode);
			
			List<String> timeInterval = getTimeInterval();
			List<YhFoodUserDiary> findFoodDiaryThisWeekInfo = YhFoodUserDiaryServiceImpl.findFoodDiaryThisWeekInfo(usercode);
			//全部记录的热量
			BigDecimal energyKcalAll=new BigDecimal("0");
			//未超标天数
			int wcbNum=0;
			int cbNum=0;
			//记录天数
			int infoNum=0;
			if(null!=findFoodDiaryThisWeekInfo){
				infoNum=findFoodDiaryThisWeekInfo.size();
				for (int i = 0; i < infoNum; i++) {
					YhFoodUserDiary yhFoodUserDiary = findFoodDiaryThisWeekInfo.get(i);
					BigDecimal energyKcal = new BigDecimal(yhFoodUserDiary.getEnergyKcal());
					energyKcalAll=energyKcalAll.add(energyKcal);
					Long energyLevel = yhFoodUserDiary.getEnergyLevel();
					 if(new BigDecimal(energyLevel).compareTo(energyKcal)>-1){
						 wcbNum++;
					 }else{
						 cbNum++;
					 }
				}
				energyKcalAll=energyKcalAll.divide(new BigDecimal(infoNum),4,BigDecimal.ROUND_HALF_UP);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeList", timeInterval);
			map.put("infoList", findFoodUserDuary);
			map.put("averageHeat", energyKcalAll.setScale(0,BigDecimal.ROUND_HALF_UP));
			map.put("wcbNum", wcbNum);
			map.put("cbNum", cbNum);
			map.put("infoNum", infoNum);
			map.put("maxLx", Arrays.stream(findFoodUserDuary).max().getAsInt());
			
			return Result.ok(map);

		}
		if (type.equals("上周")) {
			int[] findFoodUserDuaryLastWeek = YhFoodUserDiaryServiceImpl.findFoodUserDuaryLastWeek(usercode);
			List<String> lastTimeInterval = getLastTimeInterval();
			List<YhFoodUserDiary> findFoodDiaryLastWeekInfo = YhFoodUserDiaryServiceImpl.findFoodDiaryLastWeekInfo(usercode);
			//全部记录的热量
			BigDecimal energyKcalAll=new BigDecimal("0");
			//未超标天数
			int wcbNum=0;
			int cbNum=0;
			//记录天数
			int infoNum=0;
			if(null!=findFoodDiaryLastWeekInfo){
				infoNum=findFoodDiaryLastWeekInfo.size();
				for (int i = 0; i < infoNum; i++) {
					YhFoodUserDiary yhFoodUserDiary = findFoodDiaryLastWeekInfo.get(i);
					BigDecimal energyKcal = new BigDecimal(yhFoodUserDiary.getEnergyKcal());
					energyKcalAll=energyKcalAll.add(energyKcal);
					Long energyLevel = yhFoodUserDiary.getEnergyLevel();
					 if(new BigDecimal(energyLevel).compareTo(energyKcal)>-1){
						 wcbNum++;
					 }else{
						 cbNum++;
					 }
				}
				energyKcalAll=energyKcalAll.divide(new BigDecimal(infoNum),4,BigDecimal.ROUND_HALF_UP);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeList", lastTimeInterval);
			map.put("infoList", findFoodUserDuaryLastWeek);
			map.put("averageHeat", energyKcalAll.setScale(0,BigDecimal.ROUND_HALF_UP));
			map.put("wcbNum", wcbNum);
			map.put("cbNum", cbNum);
			map.put("infoNum", infoNum);
			map.put("maxLx", Arrays.stream(findFoodUserDuaryLastWeek).max().getAsInt());
			return Result.ok(map);
		}
		if (type.equals("本月")) {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			int[] findFoodUserDuary = YhFoodUserDiaryServiceImpl.findFoodUserDuaryYue(usercode, String.valueOf(year),
					String.valueOf(month));
			List<YhFoodUserDiary> findFoodDiaryThisMonthInfo = YhFoodUserDiaryServiceImpl.findFoodDiaryThisMonthInfo(usercode);
			//全部记录的热量
			BigDecimal energyKcalAll=new BigDecimal("0");
			//未超标天数
			int wcbNum=0;
			int cbNum=0;
			//记录天数
			int infoNum=0;
			if(null!=findFoodDiaryThisMonthInfo){
				infoNum=findFoodDiaryThisMonthInfo.size();
				for (int i = 0; i < infoNum; i++) {
					YhFoodUserDiary yhFoodUserDiary = findFoodDiaryThisMonthInfo.get(i);
					BigDecimal energyKcal = new BigDecimal(yhFoodUserDiary.getEnergyKcal());
					energyKcalAll=energyKcalAll.add(energyKcal);
					Long energyLevel = yhFoodUserDiary.getEnergyLevel();
					if(new BigDecimal(energyLevel).compareTo(energyKcal)>-1){
						wcbNum++;
					}else{
						cbNum++;
					}
				}
				energyKcalAll=energyKcalAll.divide(new BigDecimal(infoNum),4,BigDecimal.ROUND_HALF_UP);
			}
			int[] dayNum = new int[31];
			for (int i = 1; i <= 31; i++) {
				dayNum[i - 1] = i;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeList", dayNum);
			map.put("infoList", findFoodUserDuary);
			map.put("averageHeat",  energyKcalAll.setScale(0,BigDecimal.ROUND_HALF_UP));
			map.put("wcbNum", wcbNum);
			map.put("cbNum", cbNum);
			map.put("infoNum", infoNum);
			map.put("maxLx", Arrays.stream(findFoodUserDuary).max().getAsInt());
			return Result.ok(map);
		}
		if (type.equals("上月")) {
			// 上月
			int[] findFoodUserDuary = YhFoodUserDiaryServiceImpl.findFoodUserDuaryYue(usercode,
					String.valueOf(getlastYear()), String.valueOf(getlastMonth()));
			List<YhFoodUserDiary> findFoodDiaryLastMonthInfo = YhFoodUserDiaryServiceImpl.findFoodDiaryLastMonthInfo(usercode);
			//全部记录的热量
			BigDecimal energyKcalAll=new BigDecimal("0");
			//未超标天数
			int wcbNum=0;
			int cbNum=0;
			//记录天数
			int infoNum=0;
			if(null!=findFoodDiaryLastMonthInfo){
				infoNum=findFoodDiaryLastMonthInfo.size();
				for (int i = 0; i < infoNum; i++) {
					YhFoodUserDiary yhFoodUserDiary = findFoodDiaryLastMonthInfo.get(i);
					BigDecimal energyKcal = new BigDecimal(yhFoodUserDiary.getEnergyKcal());
					energyKcalAll=energyKcalAll.add(energyKcal);
					Long energyLevel = yhFoodUserDiary.getEnergyLevel();
					if(new BigDecimal(energyLevel).compareTo(energyKcal)>-1){
						wcbNum++;
					}else{
						cbNum++;
					}
				}
				energyKcalAll=energyKcalAll.divide(new BigDecimal(infoNum),4,BigDecimal.ROUND_HALF_UP);
			}
			
			int[] dayNum = new int[31];
			for (int i = 1; i <= 31; i++) {
				dayNum[i - 1] = i;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeList", dayNum);
			map.put("infoList", findFoodUserDuary);
			map.put("averageHeat", energyKcalAll.setScale(0,BigDecimal.ROUND_HALF_UP));
			map.put("wcbNum", wcbNum);
			map.put("cbNum", cbNum);
			map.put("infoNum", infoNum);
			map.put("maxLx", Arrays.stream(findFoodUserDuary).max().getAsInt());
			return Result.ok(map);

		}
		if (type.equals("本年")) {
			Calendar cal = Calendar.getInstance();

			int year = cal.get(Calendar.YEAR);
			int[] findFoodUserDuary = YhFoodUserDiaryServiceImpl.findFoodUserDiaryByYear(usercode,
					String.valueOf(year));
			List<YhFoodUserDiary> findFoodDiaryThisYearInfo = YhFoodUserDiaryServiceImpl.findFoodDiaryThisYearInfo(usercode);
			//全部记录的热量
			BigDecimal energyKcalAll=new BigDecimal("0");
			//未超标天数
			int wcbNum=0;
			int cbNum=0;
			//记录天数
			int infoNum=0;
			if(null!=findFoodDiaryThisYearInfo){
				infoNum=findFoodDiaryThisYearInfo.size();

				for (int i = 0; i < infoNum; i++) {
					YhFoodUserDiary yhFoodUserDiary = findFoodDiaryThisYearInfo.get(i);
					BigDecimal energyKcal = new BigDecimal(yhFoodUserDiary.getEnergyKcal());
					energyKcalAll=energyKcalAll.add(energyKcal);
					Long energyLevel = yhFoodUserDiary.getEnergyLevel();
					if(new BigDecimal(energyLevel).compareTo(energyKcal)>-1){
						wcbNum++;
					}else{
						cbNum++;
					}
				}
				energyKcalAll=energyKcalAll.divide(new BigDecimal(infoNum),4,BigDecimal.ROUND_HALF_UP);
			}
			
			int[] dayNum = new int[12];
			for (int i = 1; i <= 12; i++) {
				dayNum[i - 1] = i;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeList", dayNum);
			map.put("infoList", findFoodUserDuary);
			map.put("averageHeat", energyKcalAll.setScale(0,BigDecimal.ROUND_HALF_UP));
			map.put("wcbNum", wcbNum);
			map.put("cbNum", cbNum);
			map.put("infoNum", infoNum);
			map.put("maxLx", Arrays.stream(findFoodUserDuary).max().getAsInt());
			return Result.ok(map);
		}
		if (type.equals("去年")) {
			Calendar cal = Calendar.getInstance();

			int year = cal.get(Calendar.YEAR) - 1;
			// 去年
			int[] findFoodUserDuary = YhFoodUserDiaryServiceImpl.findFoodUserDiaryByYear(usercode,
					String.valueOf(year));
			List<YhFoodUserDiary> findFoodDiaryLastYearInfo = YhFoodUserDiaryServiceImpl.findFoodDiaryLastYearInfo(usercode);
			//全部记录的热量
			BigDecimal energyKcalAll=new BigDecimal("0");
			//未超标天数
			int wcbNum=0;
			int cbNum=0;
			
			//记录天数
			int infoNum=0;
			if(null!=findFoodDiaryLastYearInfo){
				infoNum=findFoodDiaryLastYearInfo.size();
				for (int i = 0; i < infoNum; i++) {
					YhFoodUserDiary yhFoodUserDiary = findFoodDiaryLastYearInfo.get(i);
					BigDecimal energyKcal = new BigDecimal(yhFoodUserDiary.getEnergyKcal());
					energyKcalAll=energyKcalAll.add(energyKcal);
					Long energyLevel = yhFoodUserDiary.getEnergyLevel();
					if(new BigDecimal(energyLevel).compareTo(energyKcal)>-1){
						wcbNum++;
					}else{
						cbNum++;
					}
				}
				energyKcalAll=energyKcalAll.divide(new BigDecimal(infoNum),4,BigDecimal.ROUND_HALF_UP);
			}
			
			int[] dayNum = new int[12];
			for (int i = 1; i <= 12; i++) {
				dayNum[i - 1] = i;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("timeList", dayNum);
			map.put("infoList", findFoodUserDuary);
			map.put("averageHeat", energyKcalAll.setScale(0,BigDecimal.ROUND_HALF_UP));
			map.put("wcbNum", wcbNum);
			map.put("cbNum", cbNum);
			map.put("infoNum", infoNum);
			map.put("maxLx", Arrays.stream(findFoodUserDuary).max().getAsInt());
			return Result.ok(map);
		}

		return Result.error("参数异常");
	}

	public static int getlastMonth() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 设置为当前时间
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		int month = calendar.get(Calendar.MONTH) + 1;

		return month;
	}

	public static int getlastYear() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 设置为当前时间
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
		int year = calendar.get(Calendar.YEAR);
		date = calendar.getTime();

		return year;
	}
	

	
	
	/**
	 * 饮食日记假数据制作
	 */
	@RequestMapping(value = "/addJiaFoodDiaryInfo")
	@ResponseBody
	public Result addJiaFoodDiaryInfo() {
		List<String> timelist=new ArrayList<String>();
		timelist.add("2020-07-14");
		timelist.add("2020-07-15");
		timelist.add("2020-07-16");
		timelist.add("2020-07-17");
		for (String timeInfo : timelist) {
			// 根据时间查询用户是否已经存在膳食日记
			YhFoodUserDiary selectByUserCodeAndTime = YhFoodUserDiaryServiceImpl.selectByUserCodeAndTime("10131", timeInfo);
			if(null==selectByUserCodeAndTime){
				return Result.error("日记为空");
			}
	List<String> list = YhUserReginfoAppServiceImpl.findUserCodeByUserTypeFor5();
	if(null==list&&list.size()==0){
		return Result.error("集合空");
	}
	for (int i = 0; i < list.size(); i++) {
		String string = list.get(i);
		 
		if(StringUtil.isEmpty(string)){
			System.out.println("下标："+i+"为空字符或null");
			continue;
		}
		if("10131".equals(string)){
			continue;
		}
		YhFoodUserDiary selectByUserCodeAndTime2 = YhFoodUserDiaryServiceImpl.selectByUserCodeAndTime(string, timeInfo);
		if(null!=selectByUserCodeAndTime2){
			System.out.println("用户："+string+"07-14存在日记");
			continue;
		}
		System.out.println(list);
		String getfElements = selectByUserCodeAndTime.getfElements();
		String[] split = getfElements.split(";");
		for (int j = 0; j < split.length; j++) {
			String string2 = split[j];
			String[] split2 = string2.split("_");
			//早中晚类型
			String string3 = split2[0];
			//食物id
			 String string4 = split2[1];
			 //食物克数
			 String string5 = split2[2];
			 addFoodDiaryInfo(string, timeInfo, Integer.valueOf(string3), Integer.valueOf(string4), Integer.valueOf(string5));
		}
		
		
	}
		}
		
		return Result.error("参数异常");
	}

	
	/**
	 * 饮食新增
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addFoodDiaryInfozhoubao")
	@ResponseBody
	public Result addFoodDiaryInfozhoubao() {
		YhFoodSrecipeApiNewServiceImpl.addYhUserFoodWeekly2();
		return Result.ok();
	}
	/**
	 * 查询最近一次添加时间
	 */
	@RequestMapping(value = "/findFoodDiaryinfoNewTime")
	@ResponseBody
	public Result findFoodDiaryinfoNewTime(String usercode) throws java.text.ParseException{
		String foodDiaryinfoNewTime = YhFoodUserDiaryServiceImpl.findFoodDiaryinfoNewTime(usercode);
		if(StringUtil.isNotEmpty(foodDiaryinfoNewTime)){
			return Result.ok(foodDiaryinfoNewTime);
		}
		return Result.error();
	}
	/**
	 * 饮食新增
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addFoodDiaryInfo")
	@ResponseBody
	public Result addFoodDiaryInfo(String usercode, String time, int type, int foodid, int num) {
		if (StringUtil.isEmpty(usercode) || type == 0 || StringUtil.isEmpty(time) || num == 0 || foodid == 0) {
			return Result.error("参数异常");
		}
		if (!isInteger(String.valueOf(type))) {
			return Result.error("参数异常,早中晚加餐类型为数字1，2，3，4----而不是" + type);
		}
		if (!isInteger(String.valueOf(foodid))) {
			return Result.error("食物id异常：" + foodid);
		}
		if (!isInteger(String.valueOf(num))) {
			return Result.error("食物克数异常，该是正整数：" + num);
		}
		// 根据时间查询用户是否已经存在膳食日记
		YhFoodUserDiary selectByUserCodeAndTime = YhFoodUserDiaryServiceImpl.selectByUserCodeAndTime(usercode, time);

		// 根据食材id查询食材的详情
		YhFoodCookbook2 selectByPrimaryKey = YhFoodCookbook2ServiceImpl.selectByPrimaryKey(foodid);

		if (null == selectByPrimaryKey||null==selectByPrimaryKey.getfType()) {

			return Result.error("食物id不存在为: " + foodid);
		}
		/*食物类型_早中晚餐类型_食物id_食物克数
		 10_1_1514_100,451_55,542_45;1_1_515_200;*/

		// 查询饮食推荐能量
		String newElements =selectByPrimaryKey.getfType()+"_"+ type + "_" + foodid + "_" + num;
		if("10".equals(selectByPrimaryKey.getfType())){
			YhFoodCooked selectByPrimaryKey2 = YhFoodCookedMapper.selectByPrimaryKey(selectByPrimaryKey.getfCId());
			String getfCookedelements = selectByPrimaryKey2.getfCookedelements();
			//90178_0.3;90034_0.7;
			String[] split = getfCookedelements.split(";");
			for (String string : split) {
				String[] split2 = string.split("_");
				String foodidCaiYao=split2[0];
				String foodratio=split2[1];
				double ratiadoule=Double.valueOf(foodratio)*num;
				BigDecimal b = new BigDecimal(ratiadoule);
				float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
				String dishElements=","+foodidCaiYao+"_"+f1;
				newElements=newElements+dishElements;
			}
			
		}
		newElements=newElements+ ";";
		YhFoodUserDiary yhFoodUserDiary = new YhFoodUserDiary();
		yhFoodUserDiary.setUserCode(usercode);
		yhFoodUserDiary.setcTime(time);

		int returnNum = 0;
		// 为空则新增膳食日记
		if (null == selectByUserCodeAndTime) {
			Long tuijianNum = tuijianNum(usercode);
			yhFoodUserDiary.setEnergyLevel(tuijianNum);
			yhFoodUserDiary.setfElements(newElements);
			// 热量
			BigDecimal energyKcal = new BigDecimal("0");
			if (null != selectByPrimaryKey.getEnergyKcal()) {
				energyKcal = new BigDecimal(selectByPrimaryKey.getEnergyKcal()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			// 该食材该重量所含碳水化合物
			BigDecimal cho =new BigDecimal("0");
			if (null != selectByPrimaryKey.getCho()) {
				cho = new BigDecimal(selectByPrimaryKey.getCho()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}
			// 脂肪
			BigDecimal fat = new BigDecimal("0");
			if (null != selectByPrimaryKey.getFat()) {

				fat =	new BigDecimal(selectByPrimaryKey.getFat()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			// 蛋白质
			BigDecimal protein =new BigDecimal("0");
			if (null != selectByPrimaryKey.getProtein()) {

				protein = new BigDecimal(selectByPrimaryKey.getProtein()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			// 纤维素
			BigDecimal cellulose = new BigDecimal("0");
			if (null != selectByPrimaryKey.getCellulose()) {

				cellulose =new BigDecimal(selectByPrimaryKey.getCellulose()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}
			yhFoodUserDiary.setCho(cho.toString());
			yhFoodUserDiary.setFat(fat.toString());
			yhFoodUserDiary.setEnergyKcal(energyKcal.toString());
			yhFoodUserDiary.setProtein(protein.toString());
			yhFoodUserDiary.setCellulose(cellulose.toString());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//获取String类型的时间
			String createdate = sdf.format(new Date());
			yhFoodUserDiary.setDataSource(createdate);
			// 新增膳食日记
			returnNum = YhFoodUserDiaryServiceImpl.insertYhFoodUserDiary(yhFoodUserDiary);

		} else {
			// 不为空则修改
			// 之前的日记内容
			String getfElements = selectByUserCodeAndTime.getfElements();
			getfElements = getfElements + newElements;
			yhFoodUserDiary.setfElements(getfElements);
			// 之前的碳水化合物
			BigDecimal cho2 = new BigDecimal(selectByUserCodeAndTime.getCho());
			// 之前的脂肪
			BigDecimal fat2 = new BigDecimal(selectByUserCodeAndTime.getFat());
			// 之前的热量
			BigDecimal energyKcal2 = new BigDecimal(selectByUserCodeAndTime.getEnergyKcal());
			// 之前的蛋白
			BigDecimal protein2 = new BigDecimal(selectByUserCodeAndTime.getProtein());
			// 之前的纤维素
			BigDecimal cellulose2 = new BigDecimal(selectByUserCodeAndTime.getCellulose());

			// 热量
			BigDecimal energyKcal = new BigDecimal("0");
			if (null != selectByPrimaryKey.getEnergyKcal()) {
				energyKcal = new BigDecimal(selectByPrimaryKey.getEnergyKcal()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			// 该食材该重量所含碳水化合物
			BigDecimal cho = new BigDecimal("0");
			if (null != selectByPrimaryKey.getCho()) {

				cho = new BigDecimal(selectByPrimaryKey.getCho()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}
			// 脂肪
			BigDecimal fat = new BigDecimal("0");
			if (null != selectByPrimaryKey.getFat()) {

				fat = new BigDecimal(selectByPrimaryKey.getFat()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			// 蛋白质
			BigDecimal protein = new BigDecimal("0");
			if (null != selectByPrimaryKey.getProtein()) {
				protein = new BigDecimal(selectByPrimaryKey.getProtein()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			// 纤维素
			BigDecimal cellulose = new BigDecimal("0");
			if (null != selectByPrimaryKey.getCellulose()) {
				cellulose = new BigDecimal(selectByPrimaryKey.getCellulose()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(num));
			}

			yhFoodUserDiary.setCho(cho.add(cho2).toString());
			yhFoodUserDiary.setFat(fat.add(fat2).toString());
			yhFoodUserDiary.setEnergyKcal(energyKcal.add(energyKcal2).toString());
			yhFoodUserDiary.setProtein(protein.add(protein2).toString());
			yhFoodUserDiary.setCellulose(cellulose.add(cellulose2).toString());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//获取String类型的时间
			String createdate = sdf.format(new Date());
			yhFoodUserDiary.setDataSource(createdate);
			returnNum = YhFoodUserDiaryServiceImpl.updateYhFoodUserDiaryDel(yhFoodUserDiary);

		}
		// 食物常用+1
		YhFoodCookbook2 record = new YhFoodCookbook2();
		record.setfCId(foodid);
		if (null == selectByPrimaryKey.getCommon()) {
			record.setCommon(1);
		} else {
			record.setCommon(selectByPrimaryKey.getCommon() + 1);
		}
		YhFoodCookbook2ServiceImpl.updateByPrimaryKeySelective(record);
		if (returnNum == 0) {
			return Result.error();
		} else {
			return Result.ok();
		}
	}
	@RequestMapping(value = "/updateDiaryInfoNew")
	@ResponseBody
	public Result updateDiaryInfoNew(String addtime,String updatetime, Integer addtype,Integer updatetype, Integer index,int num, String usercode) {
		// 查询用户膳食日记
		YhFoodUserDiary selectByUserCodeAndTime = YhFoodUserDiaryServiceImpl.selectByUserCodeAndTime(usercode, addtime);
		if(null!=selectByUserCodeAndTime){



			//修改日记录信息10_1_154_100,8415_50,8431_50;1_1_154_100;
			String s = selectByUserCodeAndTime.getfElements();
			String zaoInfo="";
			String wuInfo="";
			String wanInfo="";
			String jiaInfo="";
			String delStr="";

			if(StringUtil.isNotEmpty(s)){
				//拆分字符串 每个餐次
				String[] split = s.split(";");
				for (int i = 0; i <split.length ; i++) {
					String s1 = split[i];
					String foodMeal = s1.split("_")[0];
					String foodtype = s1.split("_")[1];
					if(foodtype.equals("1")){
						zaoInfo=zaoInfo+s1+";";
					}
					if(foodtype.equals("2")){
						wuInfo=wuInfo+s1+";";
					}
					if(foodtype.equals("3")){
						wanInfo=wanInfo+s1+";";
					}
					if(foodtype.equals("4")){
						jiaInfo=jiaInfo+s1+";";
					}
				}

				if(addtype==1){
					String[] split1 = zaoInfo.split(";");
					String updateStr="";
					for (int i = 0; i <split1.length ; i++) {
						if(i!=index){
							updateStr=updateStr+split1[i]+";";
						}else{
							delStr=split1[i];
						}
					}
					zaoInfo=updateStr;
				}
				if(addtype==2){
					String[] split1 = wuInfo.split(";");
					String updateStr="";
					for (int i = 0; i <split1.length ; i++) {
						if(i!=index){
							updateStr=updateStr+split1[i]+";";
						}else{
							delStr=split1[i];
						}
					}
					wuInfo=updateStr;
				}
				if(addtype==3){
					String[] split1 = wanInfo.split(";");
					String updateStr="";
					for (int i = 0; i <split1.length ; i++) {
						if(i!=index){
							updateStr=updateStr+split1[i]+";";
						}else{
							delStr=split1[i];
						}
					}
					wanInfo=updateStr;
				}
				if(addtype==4){
					String[] split1 = jiaInfo.split(";");
					String updateStr="";
					for (int i = 0; i <split1.length ; i++) {
						if(i!=index){
							updateStr=updateStr+split1[i]+";";
						}else{
							delStr=split1[i];
						}
					}
					jiaInfo=updateStr;
				}
				//今天 10_1_154_100,8415_50,8431_50;1_1_154_100;
				if(addtime.equals(updatetime)){
					//菜肴
					String[] s1 = delStr.split("_");
					String foodType = s1[0];
					String foodMeal = s1[1];

					//菜肴
					if("10".equals(foodType)){
						String[] split1 = delStr.split(",");
						String s2 = split1[0];
						String[] s3 = s2.split("_");
						String foodid = s3[2];

						// 之前的碳水化合物
						BigDecimal cho2 = new BigDecimal(selectByUserCodeAndTime.getCho());
						// 之前的脂肪
						BigDecimal fat2 =  new BigDecimal(selectByUserCodeAndTime.getFat());
						// 之前的热量
						BigDecimal energyKcal2 =  new BigDecimal(selectByUserCodeAndTime.getEnergyKcal());
						// 之前的蛋白
						BigDecimal protein2 =  new BigDecimal(selectByUserCodeAndTime.getProtein());
						// 之前的纤维素
						BigDecimal cellulose2 =  new BigDecimal(selectByUserCodeAndTime.getCellulose());
						//要减去的热量
						YhFoodCookbook2 foodKarInfo = getFoodKarInfo(delStr);
						//要加的热量
						YhFoodCooked selectByPrimaryKey2 = YhFoodCookedMapper.selectByPrimaryKey(Integer.valueOf(foodid));
						//3971_0.2;90066_0.8;
						String getfCookedelements = selectByPrimaryKey2.getfCookedelements();
						String[] split2 = getfCookedelements.split(";");
						String newfoodInfo="10_"+updatetype+"_"+delStr.split("_")[2]+"_"+num;
						for (int i = 0; i < split2.length; i++) {
							//foodid
							String foodidCaiYao = split2[i].split("_")[0];
							//
							String foodratio = split2[i].split("_")[1];


							double ratiadoule=Double.valueOf(foodratio)*num;
							BigDecimal b = new BigDecimal(ratiadoule);
							float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
							String dishElements=","+foodidCaiYao+"_"+f1;
							newfoodInfo=newfoodInfo+dishElements;
						}

						YhFoodCookbook2 foodKarInfo1 = getFoodKarInfo(newfoodInfo);
						newfoodInfo=newfoodInfo+";";
						YhFoodUserDiary yhFoodUserDiary = new YhFoodUserDiary();
						yhFoodUserDiary.setUserCode(usercode);
						yhFoodUserDiary.setcTime(updatetime);

						yhFoodUserDiary.setfElements(zaoInfo+wuInfo+wanInfo+jiaInfo+newfoodInfo);

						yhFoodUserDiary.setEnergyKcal(energyKcal2.subtract(new BigDecimal(foodKarInfo.getEnergyKcal())).add(new BigDecimal(foodKarInfo1.getEnergyKcal())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
						yhFoodUserDiary.setFat(fat2.subtract(new BigDecimal(foodKarInfo.getFat())).add(new BigDecimal(foodKarInfo1.getFat())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());


						yhFoodUserDiary.setCho(cho2.subtract(new BigDecimal(foodKarInfo.getCho())).add(new BigDecimal(foodKarInfo1.getCho())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());

						yhFoodUserDiary.setProtein(protein2.subtract(new BigDecimal(foodKarInfo.getProtein())).add(new BigDecimal(foodKarInfo1.getProtein())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
						YhFoodUserDiaryServiceImpl.updateYhFoodUserDiaryDel(yhFoodUserDiary);


					}else{
						String foodid = s1[2];

						// 之前的碳水化合物
						BigDecimal cho2 =new BigDecimal(selectByUserCodeAndTime.getCho());
						// 之前的脂肪
						BigDecimal fat2 = new BigDecimal(selectByUserCodeAndTime.getFat());
						// 之前的热量
						BigDecimal energyKcal2 = new BigDecimal(selectByUserCodeAndTime.getEnergyKcal());
						// 之前的蛋白
						BigDecimal protein2 = new BigDecimal(selectByUserCodeAndTime.getProtein());
						// 之前的纤维素
						BigDecimal cellulose2 = new BigDecimal(selectByUserCodeAndTime.getCellulose());
						//要减去的热量
						YhFoodCookbook2 foodKarInfo = getFoodKarInfo(delStr);

						String newfoodInfo="1_"+updatetype+"_"+delStr.split("_")[2]+"_"+num;
						YhFoodCookbook2 foodKarInfo1 = getFoodKarInfo(newfoodInfo);
						newfoodInfo=newfoodInfo+";";
						YhFoodUserDiary yhFoodUserDiary = new YhFoodUserDiary();
						yhFoodUserDiary.setUserCode(usercode);
						yhFoodUserDiary.setcTime(updatetime);

						yhFoodUserDiary.setfElements(zaoInfo+wuInfo+wanInfo+jiaInfo+newfoodInfo);
						yhFoodUserDiary.setEnergyKcal(energyKcal2.subtract(new BigDecimal(foodKarInfo.getEnergyKcal())).add(new BigDecimal(foodKarInfo1.getEnergyKcal())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
						yhFoodUserDiary.setFat(fat2.subtract(new BigDecimal(foodKarInfo.getFat())).add(new BigDecimal(foodKarInfo1.getFat())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());


						yhFoodUserDiary.setCho(cho2.subtract(new BigDecimal(foodKarInfo.getCho())).add(new BigDecimal(foodKarInfo1.getCho())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());

						yhFoodUserDiary.setProtein(protein2.subtract(new BigDecimal(foodKarInfo.getProtein())).add(new BigDecimal(foodKarInfo1.getProtein())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
						YhFoodUserDiaryServiceImpl.updateYhFoodUserDiaryDel(yhFoodUserDiary);

					}

				}else{
					//不是今天
					//菜肴
					String[] s1 = delStr.split("_");
					String foodType = s1[0];
					String foodMeal = s1[1];

					//菜肴
					if("10".equals(foodType)){
						String[] split1 = delStr.split(",");
						String s2 = split1[0];
						String[] s3 = s2.split("_");
						String foodid = s3[2];
						// 之前的碳水化合物
						BigDecimal cho2 =new BigDecimal(selectByUserCodeAndTime.getCho());
						// 之前的脂肪
						BigDecimal fat2 = new BigDecimal(selectByUserCodeAndTime.getFat());
						// 之前的热量
						BigDecimal energyKcal2 = new BigDecimal(selectByUserCodeAndTime.getEnergyKcal());
						// 之前的蛋白
						BigDecimal protein2 = new BigDecimal(selectByUserCodeAndTime.getProtein());
						// 之前的纤维素
						BigDecimal cellulose2 = new BigDecimal(selectByUserCodeAndTime.getCellulose());
						//要减去的热量
						YhFoodCookbook2 foodKarInfo = getFoodKarInfo(delStr);
						//当前日期减去热量
						YhFoodUserDiary yhFoodUserDiary = new YhFoodUserDiary();
						yhFoodUserDiary.setUserCode(usercode);
						yhFoodUserDiary.setcTime(addtime);

						yhFoodUserDiary.setfElements(zaoInfo+wuInfo+wanInfo+jiaInfo);


						yhFoodUserDiary.setEnergyKcal(energyKcal2.subtract(new BigDecimal(foodKarInfo.getEnergyKcal())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());


						yhFoodUserDiary.setFat(fat2.subtract(new BigDecimal(foodKarInfo.getFat())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());



						yhFoodUserDiary.setCho(cho2.subtract(new BigDecimal(foodKarInfo.getCho())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());



						yhFoodUserDiary.setProtein(protein2.subtract(new BigDecimal(foodKarInfo.getProtein())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
						YhFoodUserDiaryServiceImpl.updateYhFoodUserDiaryDel(yhFoodUserDiary);

						//修改天增加字符，增加热量
						//要加的热量
						YhFoodCooked selectByPrimaryKey2 = YhFoodCookedMapper.selectByPrimaryKey(Integer.valueOf(foodid));
						//3971_0.2;90066_0.8;
						String getfCookedelements = selectByPrimaryKey2.getfCookedelements();
						String[] split2 = getfCookedelements.split(";");
						String newfoodInfo="10_"+updatetype+"_"+delStr.split("_")[2]+"_"+num;
						for (int i = 0; i < split2.length; i++) {
							//foodid
							String foodidCaiYao = split2[i].split("_")[0];
							//
							String foodratio = split2[i].split("_")[1];


							double ratiadoule=Double.valueOf(foodratio)*num;
							BigDecimal b = new BigDecimal(ratiadoule);
							float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
							String dishElements=","+foodidCaiYao+"_"+f1;
							newfoodInfo=newfoodInfo+dishElements;
						}

						YhFoodCookbook2 foodKarInfo1 = getFoodKarInfo(newfoodInfo);
						newfoodInfo=newfoodInfo+";";



						YhFoodUserDiary orther = YhFoodUserDiaryServiceImpl.selectByUserCodeAndTime(usercode, updatetime);
						if(null!=orther){
							YhFoodUserDiary oher = new YhFoodUserDiary();
							oher.setUserCode(usercode);
							oher.setcTime(updatetime);

							oher.setfElements(orther.getfElements()+newfoodInfo);



							oher.setEnergyKcal(new BigDecimal(orther.getEnergyKcal()).add(new BigDecimal(foodKarInfo1.getEnergyKcal())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());


							oher.setFat(new BigDecimal(orther.getFat()).add(new BigDecimal(foodKarInfo1.getFat())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());



							oher.setCho(new BigDecimal(orther.getCho()).add(new BigDecimal(foodKarInfo1.getCho())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());




							oher.setProtein(new BigDecimal(orther.getProtein()).add(new BigDecimal(foodKarInfo1.getProtein())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());

							YhFoodUserDiaryServiceImpl.updateYhFoodUserDiaryDel(oher);
						}else{
							YhFoodUserDiary oher = new YhFoodUserDiary();
							oher.setUserCode(usercode);
							oher.setcTime(updatetime);
							Long tuijianNum = tuijianNum(usercode);
							oher.setEnergyLevel(tuijianNum);
							oher.setfElements(newfoodInfo);

							oher.setEnergyKcal(new BigDecimal(foodKarInfo1.getEnergyKcal()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());

							oher.setFat(new BigDecimal(foodKarInfo1.getFat()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());

							oher.setCho(new BigDecimal(foodKarInfo1.getCho()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());


							oher.setProtein(new BigDecimal(foodKarInfo1.getProtein()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
							YhFoodUserDiaryServiceImpl.insertYhFoodUserDiary(oher);
						}



					}else{

						String foodid = s1[2];

						// 之前的碳水化合物
						BigDecimal cho2 = new BigDecimal(selectByUserCodeAndTime.getCho());
						// 之前的脂肪
						BigDecimal fat2 = new BigDecimal(selectByUserCodeAndTime.getFat());
						// 之前的热量
						BigDecimal energyKcal2 = new BigDecimal(selectByUserCodeAndTime.getEnergyKcal());
						// 之前的蛋白
						BigDecimal protein2 = new BigDecimal(selectByUserCodeAndTime.getProtein());
						// 之前的纤维素
						BigDecimal cellulose2 = new BigDecimal(selectByUserCodeAndTime.getCellulose());
						//要减去的热量
						YhFoodCookbook2 foodKarInfo = getFoodKarInfo(delStr);

						//当前日期减去热量
						YhFoodUserDiary yhFoodUserDiary = new YhFoodUserDiary();
						yhFoodUserDiary.setUserCode(usercode);
						yhFoodUserDiary.setcTime(addtime);

						yhFoodUserDiary.setfElements(zaoInfo+wuInfo+wanInfo+jiaInfo);

						yhFoodUserDiary.setEnergyKcal(energyKcal2.subtract(new BigDecimal(foodKarInfo.getEnergyKcal())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
						yhFoodUserDiary.setFat((fat2.subtract(new BigDecimal(foodKarInfo.getFat())).setScale(0,BigDecimal.ROUND_HALF_UP).toString()));
						yhFoodUserDiary.setCho(cho2.subtract(new BigDecimal(foodKarInfo.getCho())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());

						yhFoodUserDiary.setProtein(protein2.subtract(new BigDecimal(foodKarInfo.getProtein())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
						YhFoodUserDiaryServiceImpl.updateYhFoodUserDiaryDel(yhFoodUserDiary);


						String newfoodInfo="1_"+updatetype+"_"+delStr.split("_")[2]+"_"+num;
						YhFoodCookbook2 foodKarInfo1 = getFoodKarInfo(newfoodInfo);
						newfoodInfo=newfoodInfo+";";

						YhFoodUserDiary orther = YhFoodUserDiaryServiceImpl.selectByUserCodeAndTime(usercode, updatetime);
						if(null!=orther){
							YhFoodUserDiary oerher = new YhFoodUserDiary();
							oerher.setUserCode(usercode);
							oerher.setcTime(updatetime);

							oerher.setfElements(orther.getfElements()+newfoodInfo);




							oerher.setEnergyKcal(new BigDecimal(orther.getEnergyKcal()).subtract(new BigDecimal(foodKarInfo1.getEnergyKcal())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());




							oerher.setFat(new BigDecimal(orther.getFat()).subtract(new BigDecimal(foodKarInfo1.getFat())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
							oerher.setCho(new BigDecimal(orther.getCho()).subtract(new BigDecimal(foodKarInfo1.getCho())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
							oerher.setProtein(new BigDecimal(orther.getProtein()).subtract(new BigDecimal(foodKarInfo1.getProtein())).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
							YhFoodUserDiaryServiceImpl.updateYhFoodUserDiaryDel(oerher);
						}else{
							YhFoodUserDiary oerher = new YhFoodUserDiary();
							oerher.setUserCode(usercode);
							oerher.setcTime(updatetime);
							Long tuijianNum = tuijianNum(usercode);
							oerher.setEnergyLevel(tuijianNum);
							oerher.setfElements(newfoodInfo);


							oerher.setEnergyKcal(new BigDecimal(foodKarInfo1.getEnergyKcal()).setScale(0,BigDecimal.ROUND_HALF_UP).toString());

							oerher.setFat(new BigDecimal(foodKarInfo1.getFat()).setScale(0,BigDecimal.ROUND_HALF_UP).toString());

							oerher.setCho(new BigDecimal(foodKarInfo1.getCho()).setScale(0,BigDecimal.ROUND_HALF_UP).toString());

							oerher.setProtein(new BigDecimal(foodKarInfo1.getProtein()).setScale(0,BigDecimal.ROUND_HALF_UP).toString());
							YhFoodUserDiaryServiceImpl.insertYhFoodUserDiary(oerher);
						}
					}


				}
			}
		}

		return Result.ok();
	}

	public YhFoodCookbook2 getFoodKarInfo(String str){
		//str 10_1_154_100,8415_50,8431_50;1_1_154_100;
		String[] s = str.split("_");
		String type = s[0];
		BigDecimal foodKar=new BigDecimal("0");
		BigDecimal fat=new BigDecimal("0");
		BigDecimal cho=new BigDecimal("0");
		BigDecimal protein=new BigDecimal("0");
		//菜肴
		if("10".equals(type)){
			String[] split = str.split(",");
			for (int i = 1; i <split.length ; i++) {
				String s1 = split[i];
				String[] s2 = s1.split("_");
				String foodid = s2[0];
				String keshu = s2[1];
				// 根据foodid查询 食材详情
				YhFoodCookbook2 selectByExample = YhFoodCookbook2ServiceImpl
						.selectByPrimaryKey(Integer.valueOf(foodid));
				BigDecimal energyKcal = new BigDecimal(selectByExample.getEnergyKcal());
				BigDecimal fat1 =  new BigDecimal(selectByExample.getFat());
				BigDecimal cho1 =  new BigDecimal(selectByExample.getCho());
				BigDecimal protein1 =  new BigDecimal(selectByExample.getProtein());

				foodKar=foodKar.add(energyKcal.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));

				fat=fat.add(fat1.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));


				cho=cho.add(cho1.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));


				protein=protein.add(protein1.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));
			}
		}else{
			String foodid = s[2];
			String keshu = s[3];
			YhFoodCookbook2 selectByExample = YhFoodCookbook2ServiceImpl
					.selectByPrimaryKey(Integer.valueOf(foodid));
			BigDecimal energyKcal = new BigDecimal(selectByExample.getEnergyKcal());
			BigDecimal fat1 =  new BigDecimal(selectByExample.getFat());
			BigDecimal cho1 =  new BigDecimal(selectByExample.getCho());
			BigDecimal protein1 =  new BigDecimal(selectByExample.getProtein());

			foodKar=foodKar.add(energyKcal.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));

			fat=fat.add(fat1.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));


			cho=cho.add(cho1.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));


			protein=protein.add(protein1.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu)));

		}
		YhFoodCookbook2 book=new YhFoodCookbook2();
		book.setCho(cho.toString());
		book.setFat(fat.toString());
		book.setEnergyKcal(foodKar.toString());
		book.setProtein(protein.toString());
		return book;
	}


	/**
	 * 删除日记
	 * 
	 * @param time
	 *            日期
	 * @param type
	 *            早中晚加餐 1234
	 * @param num
	 *            第几个食物
	 * @param usercode
	 *            用户
	 * @return
	 */
	@RequestMapping(value = "/delDiaryInfo")
	@ResponseBody
	public Result delDiaryInfo(String time, Integer type, Integer num, String usercode) {
		if (StringUtil.isEmpty(usercode) || type == null || StringUtil.isEmpty(time) || num == null) {
			return Result.error("参数异常");
		}
		if (!isInteger(String.valueOf(type))) {
			return Result.error("参数异常,早中晚加餐类型为数字1，2，3，4----而不是" + type);
		}

		if (!isInteger(String.valueOf(num))) {
			return Result.error("第几个食物num该是正整数：" + num);
		}
		

		// 查询用户膳食日记
		YhFoodUserDiary selectByUserCodeAndTime = YhFoodUserDiaryServiceImpl.selectByUserCodeAndTime(usercode, time);
		// 如果为空就返回
		if (null == selectByUserCodeAndTime) {
			return Result.error();
		}

		// 如果为空就返回
		if (null == selectByUserCodeAndTime.getfElements()) {
			return Result.ok();
		}

		String getfElements = selectByUserCodeAndTime.getfElements();

		// 2_19968_100;2_2_100;3_3_100;3_13_100;1_1_100;4_2_100; 早中晚加餐_食材id_克数;
		
			 
				/* 食物类型_早中晚餐类型_食物id_食物克数
				 10_1_1514_100,451_55,542_45;1_1_515_200;
				*/ 
				
		String[] split = getfElements.split(";");
		String ele = "";
		int j = 0;
		YhFoodUserDiary record = new YhFoodUserDiary();
		for (int i = 0; i < split.length; i++) {
			// 10_1_1514_100,451_55,542_45
			String string = split[i];
			
				String[] split2 = string.split("_");
				//是否菜肴
				String cyType= split2[0];
				// 早中晚加餐类型 2
				String rjType = split2[1];
				
				if("10".equals(cyType)){
					if (Integer.valueOf(rjType) == type) {
						if (j == num) {
							//10_1_2853_100,90178_30.0,90034_70.0;1_1_100_50
							String[] split3 = string.split(",");
							String string2 = split3[0];
							String[] split5 = string2.split("_");
							
							String foodid = split5[2];// 食物id
							Float weightInt = Float.valueOf(split5[3]);//// 食物重量
						 
							// 根据foodid查询 食材详情
							YhFoodCookbook2 selectByExample = YhFoodCookbook2ServiceImpl
									.selectByPrimaryKey(Integer.valueOf(foodid));
							// 之前的碳水化合物
							BigDecimal cho2 = new BigDecimal(selectByUserCodeAndTime.getCho());
							// 之前的脂肪
							BigDecimal fat2 = new BigDecimal(selectByUserCodeAndTime.getFat());
							// 之前的热量
							BigDecimal energyKcal2 = new BigDecimal(selectByUserCodeAndTime.getEnergyKcal());
							// 之前的蛋白
							BigDecimal protein2 = new BigDecimal(selectByUserCodeAndTime.getProtein());
							// 之前的纤维素
							BigDecimal cellulose2 = new BigDecimal(selectByUserCodeAndTime.getCellulose());
							// 该食材该重量所含碳水化合物
							BigDecimal cho = new BigDecimal("0");
							if (null != selectByExample.getCho()) {

								cho = new BigDecimal(selectByExample.getCho()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
							}
							// 脂肪
							BigDecimal fat = new BigDecimal("0");
							if (null != selectByExample.getFat()) {

								fat = new BigDecimal(selectByExample.getFat()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
							}

							// 热量
							BigDecimal energyKcal = new BigDecimal("0");
							if (null != selectByExample.getEnergyKcal()) {

								energyKcal = new BigDecimal(selectByExample.getEnergyKcal()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
							}

							// 蛋白质
							BigDecimal protein = new BigDecimal("0");
							if (null != selectByExample.getProtein()) {

								protein = new BigDecimal(selectByExample.getProtein()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
							}

							// 纤维素
							BigDecimal cellulose = new BigDecimal("0");
							if (null != selectByExample.getCellulose()) {

								cellulose = new BigDecimal(selectByExample.getCellulose()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
							}
							// 如果删除到最后一条记录则能量，蛋白质等清零
							if (split.length <= 1) {
								record.setCho("0");
								record.setFat("0");
								record.setEnergyKcal("0");
								record.setProtein("0");
								record.setCellulose("0");
							} else {
								record.setCho(cho2.subtract(cho).toString());
								record.setFat(fat2.subtract(fat).toString());
								record.setEnergyKcal(energyKcal2.subtract(energyKcal).toString());
								record.setProtein(protein2.subtract(protein).toString());
								record.setCellulose(cellulose2.subtract(cellulose).toString());
							}
							j++;
							continue;
						}
						j++;
						ele = ele + split[i] + ";";
					} else {

						ele = ele + split[i] + ";";
					}
				}else{
					if (Integer.valueOf(rjType) == type) {
						if (j == num) {

							String foodid = split2[2];// foodid
							Float weightInt = Float.valueOf(split2[3]); // weightInt
							// 根据foodid查询 食材详情
							YhFoodCookbook2 selectByExample = YhFoodCookbook2ServiceImpl
									.selectByPrimaryKey(Integer.valueOf(foodid));
							// 之前的碳水化合物
							BigDecimal cho2 = new BigDecimal(selectByUserCodeAndTime.getCho());
							// 之前的脂肪
							BigDecimal fat2 = new BigDecimal(selectByUserCodeAndTime.getFat());
							// 之前的热量
							BigDecimal energyKcal2 = new BigDecimal(selectByUserCodeAndTime.getEnergyKcal());
							// 之前的蛋白
							BigDecimal protein2 = new BigDecimal(selectByUserCodeAndTime.getProtein());
							// 之前的纤维素
							BigDecimal cellulose2 = new BigDecimal(selectByUserCodeAndTime.getCellulose());
							// 该食材该重量所含碳水化合物
							BigDecimal cho  =new BigDecimal("0");
							if (null != selectByExample.getCho()) {

								cho = new BigDecimal(selectByExample.getCho()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
							}
							// 脂肪
							BigDecimal fat =new BigDecimal("0");
							if (null != selectByExample.getFat()) {
								fat = new BigDecimal(selectByExample.getFat()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
							}

							// 热量
							BigDecimal energyKcal =new BigDecimal("0");
							if (null != selectByExample.getEnergyKcal()) {
								energyKcal = new BigDecimal(selectByExample.getEnergyKcal()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
							}

							// 蛋白质
							BigDecimal protein  =new BigDecimal("0");
							if (null != selectByExample.getProtein()) {
								protein = new BigDecimal(selectByExample.getProtein()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
							}

							// 纤维素
							BigDecimal cellulose =new BigDecimal("0");
							if (null != selectByExample.getCellulose()) {
								cellulose = new BigDecimal(selectByExample.getCellulose()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
							}
							// 如果删除到最后一条记录则能量，蛋白质等清零
							if (split.length <= 1) {
								record.setCho("0");
								record.setFat("0");
								record.setEnergyKcal("0");
								record.setProtein("0");
								record.setCellulose("0");
							} else {
								record.setCho(cho2.subtract(cho).toString());
								record.setFat(fat2.subtract(fat).toString());
								record.setEnergyKcal(energyKcal2.subtract(energyKcal).toString());
								record.setProtein(protein2.subtract(protein).toString());
								record.setCellulose(cellulose2.subtract(cellulose).toString());
							}
							j++;
							continue;
						}
						j++;
						ele = ele + split[i] + ";";
					} else {

						ele = ele + split[i] + ";";
					}
				}

				
			
			
		
		}

		record.setfElements(ele);
		record.setUserCode(usercode);
		record.setcTime(time);
		int updateYhFoodUserDiaryDel = YhFoodUserDiaryServiceImpl.updateYhFoodUserDiaryDel(record);
		if (updateYhFoodUserDiaryDel == 1) {
			return Result.ok();
		} else {
			return Result.error();
		}

	}

	@RequestMapping(value = "/updateDiaryInfo")
	@ResponseBody
	public Result updateDiaryInfo(String time, Integer type, Integer num, float keshu, String usercode,String caiyaoType,int foodIndex2) {
		if (StringUtil.isEmpty(usercode) || type == 0 || StringUtil.isEmpty(time)   || keshu == 0) {
			return Result.error("参数异常");
		}
		if (!isInteger(String.valueOf(type))) {
			return Result.error("参数异常,早中晚加餐类型为数字1，2，3，4----而不是" + type);
		}

		if (!isInteger(String.valueOf(num))) {
			return Result.error("第几个食物num该是正整数：" + num);
		}

		 

		// 查询用户膳食日记
		YhFoodUserDiary selectByUserCodeAndTime = YhFoodUserDiaryServiceImpl.selectByUserCodeAndTime(usercode, time);
		// 如果为空就返回
		if (null == selectByUserCodeAndTime) {
			return Result.error();
		}

		// 如果为空就返回
		if (null == selectByUserCodeAndTime.getfElements()) {
			return Result.ok();
		}

		String getfElements = selectByUserCodeAndTime.getfElements();

		// 2_19968_100;2_2_100;3_3_100;3_13_100;1_1_100;4_2_100; 早中晚加餐_食材id_克数;
		String[] split = getfElements.split(";");
		String ele = "";
		int j = 0;
		YhFoodUserDiary record = new YhFoodUserDiary();
		for (int i = 0; i < split.length; i++) {

			String string = split[i];
			String[] split5 = string.split("_");
			// 早中晚加餐类型
			String rjType= split5[1];
			
			if("10".equals(split5[0])){
				//10_1_2853_100,90178_30.0,90034_70.0;1_1_100_50
				String[] split2 = string.split(",");
				 
			
				if (Integer.valueOf(rjType) == type) {
					if (j == num) {
						//90178_30.0
						String string3 = split2[foodIndex2+1];
						String[] split4 = string3.split("_");
						String foodid = split4[0];// 食物id
						float weightInt = Float.valueOf(split4[1]); // 食物重量
						// 根据foodid查询 食材详情
						YhFoodCookbook2 selectByExample = YhFoodCookbook2ServiceImpl
								.selectByPrimaryKey(Integer.valueOf(foodid));

						// 之前的碳水化合物
						BigDecimal cho2 = new BigDecimal(selectByUserCodeAndTime.getCho());
						// 之前的脂肪
						BigDecimal fat2 = new BigDecimal(selectByUserCodeAndTime.getFat());
						// 之前的热量
						BigDecimal energyKcal2 = new BigDecimal(selectByUserCodeAndTime.getEnergyKcal());
						// 之前的蛋白
						BigDecimal protein2 = new BigDecimal(selectByUserCodeAndTime.getProtein());
						// 之前的纤维素
						BigDecimal cellulose2 = new BigDecimal(selectByUserCodeAndTime.getCellulose());

						// 该食材该重量所含碳水化合物
						BigDecimal cho =  new BigDecimal("0");
						if (null != selectByExample.getCho()) {

							cho = new BigDecimal(selectByExample.getCho()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
						}
						// 脂肪
						BigDecimal fat =  new BigDecimal("0");
						if (null != selectByExample.getFat()) {
							fat = new BigDecimal(selectByExample.getFat()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
						}

						// 热量
						BigDecimal energyKcal =  new BigDecimal("0");
						if (null != selectByExample.getEnergyKcal()) {
							energyKcal = new BigDecimal(selectByExample.getEnergyKcal()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
						}

						// 蛋白质
						BigDecimal protein =  new BigDecimal("0");
						if (null != selectByExample.getProtein()) {
							protein = new BigDecimal(selectByExample.getProtein()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
						}

						// 纤维素
						BigDecimal cellulose = new BigDecimal("0");
						if (null != selectByExample.getCellulose()) {
							cellulose =  new BigDecimal(selectByExample.getCellulose()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
						}

						// 修改后的 该食材该重量所含碳水化合物
						BigDecimal cho3 = new BigDecimal("0");
						if (null != selectByExample.getCho()) {
							cho3 =  new BigDecimal(selectByExample.getCho()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu));
						}
						// 脂肪
						BigDecimal fat3 = new BigDecimal("0");
						if (null != selectByExample.getFat()) {
							fat3 = new BigDecimal(selectByExample.getFat()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu));
						}

						// 热量
						BigDecimal energyKcal3 =  new BigDecimal("0");
						if (null != selectByExample.getEnergyKcal()) {
							energyKcal3 =new BigDecimal(selectByExample.getEnergyKcal()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu));
						}

						// 蛋白质
						BigDecimal protein3 =  new BigDecimal("0");
						if (null != selectByExample.getProtein()) {
							protein3 = new BigDecimal(selectByExample.getProtein()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu));
						}

						// 纤维素
						BigDecimal cellulose3 = new BigDecimal("0");
						if (null != selectByExample.getCellulose()) {
							cellulose3 = new BigDecimal(selectByExample.getCellulose()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu));
						}

						
							record.setCho(cho2.subtract(cho).add(cho3).toString());
							record.setFat(fat2.subtract(fat).add(fat3).toString());
							record.setEnergyKcal(energyKcal2.subtract(energyKcal).add(energyKcal3).toString());
							record.setProtein(protein2.subtract(protein).add(protein3).toString());
							record.setCellulose(cellulose2.subtract(cellulose).add(cellulose3).toString());
						
						
						String elem="";
						float henum=0f;
						for (int k = 1; k < split2.length; k++) {
							
							if(k==foodIndex2+1){
								elem=elem+foodid + "_" + keshu+",";
							}else{
								elem=elem+split2[k]+",";
							}
							 
						}
						
						String[] split3 = elem.split(",");
						for (int k = 0; k < split3.length; k++) {
							String string2 = split3[k];
							String[] split6 = string2.split("_");
							String string4 = split6[1];
							henum=henum+Float.valueOf(string4);
						}
						
						String string2 = split2[0];
						String[] split6 = string2.split("_");
						 String fatherInfo=split6[0]+"_"+split6[1]+"_"+split6[2]+"_"+henum+",";
						
						elem = fatherInfo+elem.substring(0, elem.length() - 1);
						ele = ele + elem + ";";
						j++;
						continue;
						
					}
					j++;
					ele = ele + split[i] + ";";
				}else {

					ele = ele + split[i] + ";";
				}

			}else{
				String[] split2 = string.split("_");

				// 早中晚加餐类型
				if (Integer.valueOf(rjType) == type) {
					if (j == num) {

						String foodid = split2[2];// 食物id
						Float weightInt = Float.valueOf(split2[3]); // 食物重量

						// 根据foodid查询 食材详情
						YhFoodCookbook2 selectByExample = YhFoodCookbook2ServiceImpl
								.selectByPrimaryKey(Integer.valueOf(foodid));

						// 之前的碳水化合物
						BigDecimal cho2 = new BigDecimal(selectByUserCodeAndTime.getCho());
						// 之前的脂肪
						BigDecimal fat2 = new BigDecimal(selectByUserCodeAndTime.getFat());
						// 之前的热量
						BigDecimal energyKcal2 = new BigDecimal(selectByUserCodeAndTime.getEnergyKcal());
						// 之前的蛋白
						BigDecimal protein2 = new BigDecimal(selectByUserCodeAndTime.getProtein());
						// 之前的纤维素
						BigDecimal cellulose2 = new BigDecimal(selectByUserCodeAndTime.getCellulose());

						// 该食材该重量所含碳水化合物
						BigDecimal cho = new BigDecimal("0");
						if (null != selectByExample.getCho()) {

							cho = new BigDecimal(selectByExample.getCho()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
						}
						// 脂肪
						BigDecimal fat =new BigDecimal("0");
						if (null != selectByExample.getFat()) {
							fat = new BigDecimal(selectByExample.getFat()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
						}

						// 热量
						BigDecimal energyKcal = new BigDecimal("0");
						if (null != selectByExample.getEnergyKcal()) {
							energyKcal = new BigDecimal(selectByExample.getEnergyKcal()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
						}

						// 蛋白质
						BigDecimal protein = new BigDecimal("0");
						if (null != selectByExample.getProtein()) {
							protein = new BigDecimal(selectByExample.getProtein()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
						}

						// 纤维素
						BigDecimal cellulose = new BigDecimal("0");
						if (null != selectByExample.getCellulose()) {
							cellulose = new BigDecimal(selectByExample.getCellulose()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(weightInt));
						}

						// 修改后的 该食材该重量所含碳水化合物
						BigDecimal cho3 = new BigDecimal("0");
						if (null != selectByExample.getCho()) {
							cho3 = new BigDecimal(selectByExample.getCho()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu));
						}
						// 脂肪
						BigDecimal fat3 = new BigDecimal("0");
						if (null != selectByExample.getFat()) {
							fat3 = new BigDecimal(selectByExample.getFat()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu));
						}

						// 热量
						BigDecimal energyKcal3 = new BigDecimal("0");
						if (null != selectByExample.getEnergyKcal()) {
							energyKcal3 =  new BigDecimal(selectByExample.getEnergyKcal()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu));
						}

						// 蛋白质
						BigDecimal protein3 = new BigDecimal("0");
						if (null != selectByExample.getProtein()) {
							protein3 =  new BigDecimal(selectByExample.getProtein()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu));
						}

						// 纤维素
						BigDecimal cellulose3 = new BigDecimal("0");
						if (null != selectByExample.getCellulose()) {
							cellulose3 = new BigDecimal(selectByExample.getCellulose()).divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(keshu));
						}

						// 如果删除到最后一条记录则能量，蛋白质等清零
					 
							record.setCho(cho2.subtract(cho).add(cho3).toString());

							record.setFat(fat2.subtract(fat).add(fat3).toString());

							record.setEnergyKcal(energyKcal2.subtract(energyKcal).add(energyKcal3).toString());

							record.setProtein(protein2.subtract(protein).add(protein3).toString());

							record.setCellulose(cellulose2.subtract(cellulose).add(cellulose3).toString());
						 

						ele = ele + caiyaoType+"_"+rjType + "_" + foodid + "_" + keshu + ";";
						j++;
						continue;

					}
					j++;
					ele = ele + split[i] + ";";
				} else {

					ele = ele + split[i] + ";";
				}

			}
			
			
		}
		record.setfElements(ele);
		record.setcTime(time);
		record.setUserCode(usercode);
		YhFoodUserDiaryServiceImpl.updateYhFoodUserDiaryDel(record);
		return Result.ok();
	}

	/**
	 * 食物搜索热度+1
	 */
	@RequestMapping(value = "/queryFoodInfoHot")
	@ResponseBody
	public Result queryFoodInfo(String foodName) {
		if (StringUtil.isEmpty(foodName)) {
			return Result.ok();
		}
		YhFoodHotSearch hotInfo = YhFoodHotSearchServiceImpl.findFoodHotSearchByFoodName(foodName);
		if (null != hotInfo) {
			// 存在修改热度+1
			YhFoodHotSearchServiceImpl.hotFoodAddHeat(foodName);
		} else {
			// 不存在热度为1
			YhFoodHotSearch record = new YhFoodHotSearch();
			record.setFoodname(foodName);
			record.setHeat(1);

			YhFoodHotSearchServiceImpl.insertSelective(record);
		}
		return Result.ok();
	}

	/**
	 * 食物搜索功能
	 * 
	 * @param usercode
	 * @return
	 */
	@RequestMapping(value = "/queryFoodInfo")
	@ResponseBody
	public Result queryFoodInfo(String foodName, String usercode, Integer curPage, Integer pageSize) {
		if (StringUtil.isEmpty(foodName) || StringUtil.isEmpty(usercode) || null == curPage || null == pageSize) {
			return Result.error();
		}

		List<YhFoodCookbook2> selectFoodCookBookName = YhFoodCookbook2ServiceImpl.selectFoodCookBookName(foodName,
				usercode, (curPage - 1) * pageSize, pageSize);
		if(null!=selectFoodCookBookName){
			for (int i = 0; i < selectFoodCookBookName.size(); i++) {
				selectFoodCookBookName.get(i).setfCName(ellipsis(selectFoodCookBookName.get(i).getfCName()));
			}
		}
		return Result.ok(selectFoodCookBookName);
	}

	public Long tuijianNum(String usercode) {
		YhFoodSrecipeRecordApp findFoodSrecipeRecordByUsercodeForNow = YhFoodSrecipeRecordAppServiceImpl
				.findFoodSrecipeRecordByUsercodeForNow(usercode);
		// 推荐摄入量
		Long recordKay = 1733l;
		if (null != findFoodSrecipeRecordByUsercodeForNow
				&& null != findFoodSrecipeRecordByUsercodeForNow.getRecordKay()) {
			recordKay = findFoodSrecipeRecordByUsercodeForNow.getRecordKay();
		} else {
			// 如果等于空则获取 初始化膳食热量评估 3.1等膳食推荐数据库做好改（新用户为评估的默认膳食推荐能量）
			YhUserReginfoApp findYhUserReginfo = YhUserReginfoAppServiceImpl.findYhUserReginfo(usercode);
			if(null!=findYhUserReginfo){
				Integer gender = findYhUserReginfo.getGender();
				
				YhFoodSrecipeApiNew findNewFoodSrecipeForSex = YhFoodSrecipeApiNewServiceImpl.findNewFoodSrecipeForSex(usercode,gender);
				recordKay = Long.valueOf(findNewFoodSrecipeForSex.geteLevel());
			}
			
		}
		return recordKay;
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
}