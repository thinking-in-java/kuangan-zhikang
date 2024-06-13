/**
 *
 */
package com.yihe.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.ibm.icu.math.BigDecimal;
import com.yihe.system.domain.*;
import com.yihe.system.domain.YhDiseaseCategoryExample.Criteria;
import com.yihe.system.mapper.*;
import com.yihe.system.service.YhFoodCookBookService;
import com.yihe.system.service.YhFoodSrecipeRecordService;
import com.yihe.system.service.YhFoodSrecipeService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.service.impl.YhFoodCookBookServiceImpl;
import com.yihe.system.service.impl.YhFoodUserScrecipeWebNewServiceImpl;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户查看膳食管理
 *
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhFoodSrecipeRecordContriller")
@Api(value = "YhFoodSrecipeRecordContriller控制器", description = "YhFoodSrecipeRecordContriller管理")
public class YhFoodSrecipeRecordContriller {

	@Autowired
	private YhFoodSrecipeRecordService yhFoodSrecipeRecordService;

	@Autowired
	private YhFoodSrecipeService hFoodSrecipeService;

	@Autowired
	private YhFoodUserScrecipeMapper yhFoodUserScrecipeMapper;

	@Autowired
	private YhFoodCookBookMapper yhFoodCookBookMapper;

	@Autowired
	private YhFoodSrecipeMapper yhFoodSrecipeMapper;

	@Autowired
	YHDocGroupMapper YHDocGroupMapper;

	@Autowired
	YhDiseaseCategoryMapper YhDiseaseCategoryMapper;

	@Autowired
	YhFoodSrecipeNewWebMapper YhFoodSrecipeNewWebMapper;

	@Autowired
	YhFoodUserScrecipeWebNewServiceImpl YhFoodUserScrecipeWebNewServiceImpl;

	@Autowired
	YhUserReginfoService yhUserReginfoService;
	@Autowired
	private YhFoodCookBookService yhFoodCookBookService;

	@Autowired
	YhFodClassifenergyWebMapper YhFodClassifenergyWebMapper;
	@Autowired
	YhFoodCookBookMapper YhFoodCookBookMapper;

	@Autowired
	YhFoodCookBookServiceImpl YhFoodCookBookServiceImpl;
	@Autowired
	YhFoodCookedWebMapper YhFoodCookedMapper;
	@Autowired
	YhFoodCategoryWebMapper YhFoodCategoryWebMapper;

	@ApiOperation(value = "渲染新增食谱页面")
	@RequestMapping(value = "/findGroupInfoByname", method = RequestMethod.POST)
	@ResponseBody
	public Result findGroupInfoByname(String gname) {
		 return Result.ok(YHDocGroupMapper.findGroupListByName(gname));
	}

	@ApiOperation(value = "渲染新增食谱页面")
	@RequestMapping(value = "/findYhFoodUserScrecipeWebNewBykey", method = RequestMethod.POST)
	@ResponseBody
	public Result findYhFoodUserScrecipeWebNewBykey(Integer uFoodErecipeId) {

		List<YhFoodCookBook> zao = new ArrayList<YhFoodCookBook>();
		List<YhFoodCookBook> wu = new ArrayList<YhFoodCookBook>();
		List<YhFoodCookBook> wan = new ArrayList<YhFoodCookBook>();
		List<YhFoodCookBook> jia = new ArrayList<YhFoodCookBook>();
		YhFoodUserScrecipeWebNew selectByPrimaryKey = YhFoodUserScrecipeWebNewServiceImpl
				.selectByPrimaryKey(uFoodErecipeId);
		String getfElements = selectByPrimaryKey.getfElements();
		String[] split = getfElements.split(";");
		for (String string : split) {
			String[] split2 = string.split("_");
			YhFoodCookBook foodInfo = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(split2[1]));

			foodInfo.setRecipesNumber(Float.valueOf(split2[2]));
			if ("1".equals(split2[0])) {
				zao.add(foodInfo);
			}
			if ("2".equals(split2[0])) {
				wu.add(foodInfo);
			}
			if ("3".equals(split2[0])) {
				wan.add(foodInfo);
			}
			if ("4".equals(split2[0])) {
				jia.add(foodInfo);
			}
		}

		selectByPrimaryKey.setZao(zao);
		selectByPrimaryKey.setWu(wu);
		selectByPrimaryKey.setWan(wan);
		selectByPrimaryKey.setJia(jia);


		return Result.ok(selectByPrimaryKey);
	}

	@ApiOperation(value = "渲染新增食谱页面")
	@RequestMapping(value = "/updateDishInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result updateDishInfo(@RequestBody YhFoodCookBook foodinfo) {
		foodinfo.setOneself(2);
		foodinfo.setCommon(0);
		foodinfo.setfType("10");
		foodinfo.setIsDel(2);
		if (StringUtil.isNotEmpty(foodinfo.getNotRecommendFood())) {
			String replace = foodinfo.getNotRecommendFood().replace(",", ";");
			foodinfo.setNotRecommendFood(replace);
		}

		String[][] foodList = foodinfo.getFoodList();
		List<String> foodKeshu = foodinfo.getFoodKeshu();
		Float Moisture = 0f;
		Float energyKcal = 0f;
		Float cho = 0f;
		Float fat = 0f;
		Float protein = 0f;
		Float insolubleDietaryFiber = 0f;
		Float cholesterol = 0f;
		Float vitamina = 0f;
		Float cellulose = 0f;
		Float vitaminc = 0f;
		Float vitamine = 0f;
		Float thiamine = 0f;
		Float riboflavin = 0f;
		Float niacin = 0f;
		Float magnesium = 0f;
		Float calcium = 0f;
		Float rron = 0f;
		Float zinc = 0f;
		Float copper = 0f;
		Float manganese = 0f;
		Float potassium = 0f;
		Float phosphorus = 0f;
		Float sodium = 0f;
		Float selenium = 0f;
		Float saturatedFat = 0f;
		Float monounsaturatedFattyAcids = 0f;
		Float polyunsaturatedFattyAcids = 0f;
		String fCookedElements = "";
		for (int i = 0; i < foodList.length; i++) {
			String foodid = foodList[i][0];
			String foodkeshu = foodKeshu.get(i);
			double foodkeshuDouble = Double.valueOf(foodkeshu);
			double bili = foodkeshuDouble / 100;
			fCookedElements = fCookedElements + foodid + "_" + bili + ";";
			YhFoodCookBook selectByPrimaryKey = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(foodid));
			if (null == selectByPrimaryKey) {
				continue;
			}
			// 水分
			if (null != selectByPrimaryKey.getMoisture()) {
				Moisture = Moisture + selectByPrimaryKey.getMoisture() / 100 * Float.valueOf(foodkeshu);
			}

			// 每个分成的热量
			if (null != selectByPrimaryKey.getEnergyKcal()) {
				energyKcal = energyKcal + selectByPrimaryKey.getEnergyKcal() / 100 * Float.valueOf(foodkeshu);
			}

			// 碳水化合物
			if (null != selectByPrimaryKey.getCho()) {
				cho = cho + selectByPrimaryKey.getCho() / 100 * Float.valueOf(foodkeshu);
			}

			// 脂肪
			if (null != selectByPrimaryKey.getFat()) {
				fat = fat + selectByPrimaryKey.getFat() / 100 * Float.valueOf(foodkeshu);
			}

			// 蛋白质
			if (null != selectByPrimaryKey.getProtein()) {
				protein = protein + selectByPrimaryKey.getProtein() / 100 * Float.valueOf(foodkeshu);
			}

			// 不溶性膳食纤维
			if (null != selectByPrimaryKey.getInsolubleDietaryFiber()) {
				insolubleDietaryFiber = insolubleDietaryFiber
						+ selectByPrimaryKey.getInsolubleDietaryFiber() / 100 * Float.valueOf(foodkeshu);
			}

			// 胆固醇
			if (null != selectByPrimaryKey.getCholesterol()) {
				cholesterol = cholesterol + selectByPrimaryKey.getCholesterol() / 100 * Float.valueOf(foodkeshu);
			}
			// 维生素A
			if (null != selectByPrimaryKey.getVitamina()) {
				vitamina = vitamina + selectByPrimaryKey.getVitamina() / 100 * Float.valueOf(foodkeshu);
			}

			// 纤维素
			if (null != selectByPrimaryKey.getCellulose()) {
				cellulose = cellulose + selectByPrimaryKey.getCellulose() / 100 * Float.valueOf(foodkeshu);
			}

			// 维生素C
			if (null != selectByPrimaryKey.getVitaminc()) {
				vitaminc = vitaminc + selectByPrimaryKey.getVitaminc() / 100 * Float.valueOf(foodkeshu);
			}
			// 维生素e
			if (null != selectByPrimaryKey.getVitamine()) {
				vitamine = vitamine + selectByPrimaryKey.getVitamine() / 100 * Float.valueOf(foodkeshu);
			}

			// 硫胺素
			if (null != selectByPrimaryKey.getThiamine()) {
				thiamine = thiamine + selectByPrimaryKey.getThiamine() / 100 * Float.valueOf(foodkeshu);
			}

			// 核黄素
			if (null != selectByPrimaryKey.getRiboflavin()) {
				riboflavin = riboflavin + selectByPrimaryKey.getRiboflavin() / 100 * Float.valueOf(foodkeshu);
			}
			// 烟酸
			if (null != selectByPrimaryKey.getNiacin()) {
				niacin = niacin + selectByPrimaryKey.getNiacin() / 100 * Float.valueOf(foodkeshu);
			}
			// 镁
			if (null != selectByPrimaryKey.getMagnesium()) {
				magnesium = magnesium + selectByPrimaryKey.getMagnesium() / 100 * Float.valueOf(foodkeshu);
			}
			// 钙
			if (null != selectByPrimaryKey.getCalcium()) {
				calcium = calcium + selectByPrimaryKey.getCalcium() / 100 * Float.valueOf(foodkeshu);
			}
			// 铁
			if (null != selectByPrimaryKey.getRron()) {
				rron = rron + selectByPrimaryKey.getRron() / 100 * Float.valueOf(foodkeshu);
			}
			// 锌
			if (null != selectByPrimaryKey.getZinc()) {
				zinc = zinc + selectByPrimaryKey.getZinc() / 100 * Float.valueOf(foodkeshu);
			}
			// 铜
			if (null != selectByPrimaryKey.getCopper()) {
				copper = copper + selectByPrimaryKey.getCopper() / 100 * Float.valueOf(foodkeshu);
			}
			// 锰
			if (null != selectByPrimaryKey.getManganese()) {
				manganese = manganese + selectByPrimaryKey.getManganese() / 100 * Float.valueOf(foodkeshu);
			}
			// 钾
			if (null != selectByPrimaryKey.getPotassium()) {
				potassium = potassium + selectByPrimaryKey.getPotassium() / 100 * Float.valueOf(foodkeshu);
			}
			// 磷
			if (null != selectByPrimaryKey.getPhosphorus()) {
				phosphorus = phosphorus + selectByPrimaryKey.getPhosphorus() / 100 * Float.valueOf(foodkeshu);
			}
			// 钠
			if (null != selectByPrimaryKey.getSodium()) {
				sodium = sodium + selectByPrimaryKey.getSodium() / 100 * Float.valueOf(foodkeshu);
			}
			// 硒
			if (null != selectByPrimaryKey.getSelenium()) {
				selenium = selenium + selectByPrimaryKey.getSelenium() / 100 * Float.valueOf(foodkeshu);
			}
			// 饱和脂肪酸
			if (null != selectByPrimaryKey.getSaturatedFat()) {
				saturatedFat = saturatedFat + selectByPrimaryKey.getSaturatedFat() / 100 * Float.valueOf(foodkeshu);
			}
			// 单不饱和脂肪酸
			if (null != selectByPrimaryKey.getMonounsaturatedFattyAcids()) {
				monounsaturatedFattyAcids = monounsaturatedFattyAcids
						+ selectByPrimaryKey.getMonounsaturatedFattyAcids() / 100 * Float.valueOf(foodkeshu);
			}
			// 多不饱和脂肪酸
			if (null != selectByPrimaryKey.getPolyunsaturatedFattyAcids()) {
				polyunsaturatedFattyAcids = polyunsaturatedFattyAcids
						+ selectByPrimaryKey.getPolyunsaturatedFattyAcids() / 100 * Float.valueOf(foodkeshu);
			}

		}
		foodinfo.setMoisture(Moisture);
		foodinfo.setEnergyKcal(energyKcal);
		foodinfo.setCho(cho);
		foodinfo.setFat(fat);
		foodinfo.setProtein(protein);
		foodinfo.setInsolubleDietaryFiber(insolubleDietaryFiber);

		foodinfo.setCholesterol(cholesterol);
		foodinfo.setVitamina(vitamina);
		foodinfo.setCellulose(cellulose);

		foodinfo.setVitaminc(vitaminc);
		foodinfo.setVitamine(vitamine);
		foodinfo.setThiamine(thiamine);

		foodinfo.setRiboflavin(riboflavin);
		foodinfo.setNiacin(niacin);
		foodinfo.setMagnesium(magnesium);
		foodinfo.setCalcium(calcium);
		foodinfo.setRron(rron);
		foodinfo.setZinc(zinc);
		foodinfo.setCopper(copper);
		foodinfo.setManganese(manganese);
		foodinfo.setPotassium(potassium);
		foodinfo.setPhosphorus(phosphorus);
		foodinfo.setSodium(sodium);
		foodinfo.setSelenium(selenium);
		foodinfo.setSaturatedFat(polyunsaturatedFattyAcids);
		foodinfo.setMonounsaturatedFattyAcids(monounsaturatedFattyAcids);
		foodinfo.setPolyunsaturatedFattyAcids(polyunsaturatedFattyAcids);
		int insertSelective = yhFoodCookBookMapper.updateByPrimaryKeySelective(foodinfo);
		if (insertSelective > 0) {
			Integer getfCId = foodinfo.getfCId();
			YhFoodCookedWeb record = new YhFoodCookedWeb();
			record.setfCookedid(getfCId);
			record.setFoodmethod(foodinfo.getFoodMethod());
			record.setfCookedelements(fCookedElements);

			YhFoodCookedMapper.updateByPrimaryKeySelective(record);
		}

		return Result.ok();
	}

	@ApiOperation(value = "渲染新增食谱页面")
	@RequestMapping(value = "/addDishInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result addDishInfo(@RequestBody YhFoodCookBook foodinfo) {
		foodinfo.setOneself(2);
		foodinfo.setCommon(0);
		foodinfo.setfType("10");
		foodinfo.setIsDel(2);
		if (StringUtil.isNotEmpty(foodinfo.getNotRecommendFood())) {
			String replace = foodinfo.getNotRecommendFood().replace(",", ";");
			foodinfo.setNotRecommendFood(replace);
		}

		String[][] foodList = foodinfo.getFoodList();
		List<String> foodKeshu = foodinfo.getFoodKeshu();
		Float Moisture = 0f;
		Float energyKcal = 0f;
		Float cho = 0f;
		Float fat = 0f;
		Float protein = 0f;
		Float insolubleDietaryFiber = 0f;
		Float cholesterol = 0f;
		Float vitamina = 0f;
		Float cellulose = 0f;
		Float vitaminc = 0f;
		Float vitamine = 0f;
		Float thiamine = 0f;
		Float riboflavin = 0f;
		Float niacin = 0f;
		Float magnesium = 0f;
		Float calcium = 0f;
		Float rron = 0f;
		Float zinc = 0f;
		Float copper = 0f;
		Float manganese = 0f;
		Float potassium = 0f;
		Float phosphorus = 0f;
		Float sodium = 0f;
		Float selenium = 0f;
		Float saturatedFat = 0f;
		Float monounsaturatedFattyAcids = 0f;
		Float polyunsaturatedFattyAcids = 0f;
		String fCookedElements = "";
		for (int i = 0; i < foodList.length; i++) {
			String foodid = foodList[i][0];
			String foodkeshu = foodKeshu.get(i);
			double foodkeshuDouble = Double.valueOf(foodkeshu);
			double bili = foodkeshuDouble / 100;
			fCookedElements = fCookedElements + foodid + "_" + bili + ";";
			YhFoodCookBook selectByPrimaryKey = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(foodid));
			if (null == selectByPrimaryKey) {
				continue;
			}
			// 水分
			if (null != selectByPrimaryKey.getMoisture()) {
				Moisture = Moisture + selectByPrimaryKey.getMoisture() / 100 * Float.valueOf(foodkeshu);
			}

			// 每个分成的热量
			if (null != selectByPrimaryKey.getEnergyKcal()) {
				energyKcal = energyKcal + selectByPrimaryKey.getEnergyKcal() / 100 * Float.valueOf(foodkeshu);
			}

			// 碳水化合物
			if (null != selectByPrimaryKey.getCho()) {
				cho = cho + selectByPrimaryKey.getCho() / 100 * Float.valueOf(foodkeshu);
			}

			// 脂肪
			if (null != selectByPrimaryKey.getFat()) {
				fat = fat + selectByPrimaryKey.getFat() / 100 * Float.valueOf(foodkeshu);
			}

			// 蛋白质
			if (null != selectByPrimaryKey.getProtein()) {
				protein = protein + selectByPrimaryKey.getProtein() / 100 * Float.valueOf(foodkeshu);
			}

			// 不溶性膳食纤维
			if (null != selectByPrimaryKey.getInsolubleDietaryFiber()) {
				insolubleDietaryFiber = insolubleDietaryFiber
						+ selectByPrimaryKey.getInsolubleDietaryFiber() / 100 * Float.valueOf(foodkeshu);
			}

			// 胆固醇
			if (null != selectByPrimaryKey.getCholesterol()) {
				cholesterol = cholesterol + selectByPrimaryKey.getCholesterol() / 100 * Float.valueOf(foodkeshu);
			}
			// 维生素A
			if (null != selectByPrimaryKey.getVitamina()) {
				vitamina = vitamina + selectByPrimaryKey.getVitamina() / 100 * Float.valueOf(foodkeshu);
			}

			// 纤维素
			if (null != selectByPrimaryKey.getCellulose()) {
				cellulose = cellulose + selectByPrimaryKey.getCellulose() / 100 * Float.valueOf(foodkeshu);
			}

			// 维生素C
			if (null != selectByPrimaryKey.getVitaminc()) {
				vitaminc = vitaminc + selectByPrimaryKey.getVitaminc() / 100 * Float.valueOf(foodkeshu);
			}
			// 维生素e
			if (null != selectByPrimaryKey.getVitamine()) {
				vitamine = vitamine + selectByPrimaryKey.getVitamine() / 100 * Float.valueOf(foodkeshu);
			}

			// 硫胺素
			if (null != selectByPrimaryKey.getThiamine()) {
				thiamine = thiamine + selectByPrimaryKey.getThiamine() / 100 * Float.valueOf(foodkeshu);
			}

			// 核黄素
			if (null != selectByPrimaryKey.getRiboflavin()) {
				riboflavin = riboflavin + selectByPrimaryKey.getRiboflavin() / 100 * Float.valueOf(foodkeshu);
			}
			// 烟酸
			if (null != selectByPrimaryKey.getNiacin()) {
				niacin = niacin + selectByPrimaryKey.getNiacin() / 100 * Float.valueOf(foodkeshu);
			}
			// 镁
			if (null != selectByPrimaryKey.getMagnesium()) {
				magnesium = magnesium + selectByPrimaryKey.getMagnesium() / 100 * Float.valueOf(foodkeshu);
			}
			// 钙
			if (null != selectByPrimaryKey.getCalcium()) {
				calcium = calcium + selectByPrimaryKey.getCalcium() / 100 * Float.valueOf(foodkeshu);
			}
			// 铁
			if (null != selectByPrimaryKey.getRron()) {
				rron = rron + selectByPrimaryKey.getRron() / 100 * Float.valueOf(foodkeshu);
			}
			// 锌
			if (null != selectByPrimaryKey.getZinc()) {
				zinc = zinc + selectByPrimaryKey.getZinc() / 100 * Float.valueOf(foodkeshu);
			}
			// 铜
			if (null != selectByPrimaryKey.getCopper()) {
				copper = copper + selectByPrimaryKey.getCopper() / 100 * Float.valueOf(foodkeshu);
			}
			// 锰
			if (null != selectByPrimaryKey.getManganese()) {
				manganese = manganese + selectByPrimaryKey.getManganese() / 100 * Float.valueOf(foodkeshu);
			}
			// 钾
			if (null != selectByPrimaryKey.getPotassium()) {
				potassium = potassium + selectByPrimaryKey.getPotassium() / 100 * Float.valueOf(foodkeshu);
			}
			// 磷
			if (null != selectByPrimaryKey.getPhosphorus()) {
				phosphorus = phosphorus + selectByPrimaryKey.getPhosphorus() / 100 * Float.valueOf(foodkeshu);
			}
			// 钠
			if (null != selectByPrimaryKey.getSodium()) {
				sodium = sodium + selectByPrimaryKey.getSodium() / 100 * Float.valueOf(foodkeshu);
			}
			// 硒
			if (null != selectByPrimaryKey.getSelenium()) {
				selenium = selenium + selectByPrimaryKey.getSelenium() / 100 * Float.valueOf(foodkeshu);
			}
			// 饱和脂肪酸
			if (null != selectByPrimaryKey.getSaturatedFat()) {
				saturatedFat = saturatedFat + selectByPrimaryKey.getSaturatedFat() / 100 * Float.valueOf(foodkeshu);
			}
			// 单不饱和脂肪酸
			if (null != selectByPrimaryKey.getMonounsaturatedFattyAcids()) {
				monounsaturatedFattyAcids = monounsaturatedFattyAcids
						+ selectByPrimaryKey.getMonounsaturatedFattyAcids() / 100 * Float.valueOf(foodkeshu);
			}
			// 多不饱和脂肪酸
			if (null != selectByPrimaryKey.getPolyunsaturatedFattyAcids()) {
				polyunsaturatedFattyAcids = polyunsaturatedFattyAcids
						+ selectByPrimaryKey.getPolyunsaturatedFattyAcids() / 100 * Float.valueOf(foodkeshu);
			}

		}
		foodinfo.setMoisture(Moisture);
		foodinfo.setEnergyKcal(energyKcal);
		foodinfo.setCho(cho);
		foodinfo.setFat(fat);
		foodinfo.setProtein(protein);
		foodinfo.setInsolubleDietaryFiber(insolubleDietaryFiber);

		foodinfo.setCholesterol(cholesterol);
		foodinfo.setVitamina(vitamina);
		foodinfo.setCellulose(cellulose);

		foodinfo.setVitaminc(vitaminc);
		foodinfo.setVitamine(vitamine);
		foodinfo.setThiamine(thiamine);

		foodinfo.setRiboflavin(riboflavin);
		foodinfo.setNiacin(niacin);
		foodinfo.setMagnesium(magnesium);
		foodinfo.setCalcium(calcium);
		foodinfo.setRron(rron);
		foodinfo.setZinc(zinc);
		foodinfo.setCopper(copper);
		foodinfo.setManganese(manganese);
		foodinfo.setPotassium(potassium);
		foodinfo.setPhosphorus(phosphorus);
		foodinfo.setSodium(sodium);
		foodinfo.setSelenium(selenium);
		foodinfo.setSaturatedFat(polyunsaturatedFattyAcids);
		foodinfo.setMonounsaturatedFattyAcids(monounsaturatedFattyAcids);
		foodinfo.setPolyunsaturatedFattyAcids(polyunsaturatedFattyAcids);
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		foodinfo.setUserCode(usercode);
		int insertSelective = yhFoodCookBookMapper.insertSelective(foodinfo);
		if (insertSelective > 0) {
			Integer getfCId = foodinfo.getfCId();
			YhFoodCookedWeb record = new YhFoodCookedWeb();
			record.setfCookedid(getfCId);
			record.setFoodmethod(foodinfo.getFoodMethod());
			record.setfCookedelements(fCookedElements);

			YhFoodCookedMapper.insertSelective(record);
		}

		return Result.ok();
	}

	@ApiOperation(value = "每日食谱管理")
	@RequestMapping(value = "/findFoodCookBookByNo10", method = RequestMethod.GET)
	@ResponseBody
	public Result findFoodPackageManagement(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int limit, String val) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("code", 0);
		PageInfo<YhFoodCookBook> findFoodCookBookByNo10 = YhFoodCookBookServiceImpl.findFoodCookBookByNo10(val, page,
				limit);
		List<YhFoodCookBook> list = findFoodCookBookByNo10.getList();
		List<xm_selectVOJO> xlist = new ArrayList<>();
		if (null != list && list.size() > 0) {
			for (YhFoodCookBook yhFoodCookBook : list) {
				xm_selectVOJO xm = new xm_selectVOJO();
				xm.setName(yhFoodCookBook.getfCName());
				xm.setValue(String.valueOf(yhFoodCookBook.getfCId()));
				xlist.add(xm);
			}
		}

		rsMap.put("count", findFoodCookBookByNo10.getTotal());
		rsMap.put("msg", "ok");
		rsMap.put("data", xlist);
		return Result.ok(rsMap);

	}

	@ApiOperation(value = "每日食谱管理")
	@RequestMapping(value = "/findFoodPackageManagement", method = RequestMethod.GET)
	@ResponseBody
	public Result findFoodPackageManagement(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false) Integer ftype, @RequestParam(required = false) Integer oneself,
			@RequestParam(required = false) String usercode, @RequestParam(required = false) Integer elevel,
			@RequestParam(required = false) Integer diseaseid, @RequestParam(required = false) String fsDescribe) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("code", 0);
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		Integer userType = yh.getUserType();
		String orgCode = yh.getOrgCode();
		PageInfo<YhFoodSrecipe> findFoodPackageManagement = hFoodSrecipeService.findFoodPackageManagement(oneself,
				yh.getUserCode(), ftype, elevel, diseaseid, fsDescribe, page, limit);
		rsMap.put("count", findFoodPackageManagement.getTotal());

		List<YhFoodSrecipe> list = findFoodPackageManagement.getList();
		if (null != list && list.size() > 0) {
			for (YhFoodSrecipe yhFoodSrecipe : list) {
				Integer oneself2 = yhFoodSrecipe.getOneself();
				String getfElements = yhFoodSrecipe.getfElements();
				String[] split = getfElements.split(";");
				yhFoodSrecipe.setYhFoodSrecipeNum(split.length);
				String[] split1 = yhFoodSrecipe.getDiseaseid().split(";");
				String disStr="";
				for (int i = 0; i < split1.length; i++) {
					String s = split1[i];
					YhDiseaseCategoryExample example = new YhDiseaseCategoryExample();
					Criteria createCriteria = example.createCriteria();
					createCriteria.andDiseaseCodeEqualTo(Integer.valueOf(s));
					YhDiseaseCategory yhDiseaseCategory = YhDiseaseCategoryMapper.selectByExample(example).get(0);
					disStr=disStr+yhDiseaseCategory.getDiseaseName()+";";
				}
				yhFoodSrecipe.setDiseaseName(disStr);

				// 不是系统提供
				if (1 != oneself2) {
					String userCode = yhFoodSrecipe.getUsercode();
					if (StringUtil.isNotEmpty(userCode)) {
						YhUserReginfo yhUserReginfo = yhUserReginfoService.selectYhUserReginfoData(userCode);
						if(null!=yhUserReginfo){
							yhFoodSrecipe.setOneselfStr(yhUserReginfo.getUserName());
						}
						if(userType==1){
							List<String> strings = yhFoodCookBookService.findjigouInfo(yh.getOrgCode());
							for (int j = 0; j < strings.size(); j++) {
								if(userCode.equals(strings.get(j))){
									yhFoodSrecipe.setQuanxuan(1);
								}
							}
							List<String> doctorInfo = yhFoodCookBookService.findDoctorInfo(yh.getOrgCode());
							for (int k = 0; k < doctorInfo.size(); k++) {
								if(userCode.equals(doctorInfo.get(k))){
									yhFoodSrecipe.setQuanxuan(1);
								}
							}
						}
					}

				} else {
					yhFoodSrecipe.setOneselfStr("系统默认提供");
				}

				yhFoodSrecipe.setDoctor(yh.getUserCode());
			}
		}
		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}

	@ApiOperation(value = "删除自己添加的饮食食谱")
	@RequestMapping(value = "/renderingDelRecipes", method = RequestMethod.POST)
	@ResponseBody
	public Result renderingDelRecipes(Integer uFoodErecipeId) {
		YhFoodUserScrecipeWebNew record = new YhFoodUserScrecipeWebNew();
		record.setIsDel(1);
		record.setuFoodErecipeId(uFoodErecipeId);
		YhFoodUserScrecipeWebNewServiceImpl.updateByPrimaryKeySelective(record);
		return Result.ok();
	}

	@ApiOperation(value = "删除自己添加的饮食套餐")
	@RequestMapping(value = "/delSetMeal", method = RequestMethod.POST)
	@ResponseBody
	public Result delSetMeal(Integer id) {

		YhFoodSrecipe record = new YhFoodSrecipe();
		record.setId(id);
		record.setIsDel(1);
		hFoodSrecipeService.updateByPrimaryKeySelective(record);
		return Result.ok();
	}

	@RequestMapping(value = "/findFoodByid", method = RequestMethod.GET)
	@ResponseBody
	public Result findFoodByid(int foodid, double num) {

		YhFoodCookBook selectByPrimaryKey = yhFoodCookBookMapper.selectByPrimaryKey(foodid);



		float v = (float) (Math.round((100 / selectByPrimaryKey.getEnergyKcal() * num) * 100)) / 100;



		return Result.ok(Math.round(v));
	}

	@ApiOperation(value = "每日食谱管理")
	@RequestMapping(value = "/findFoodTypeInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result findFoodTypeInfo(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit,
			@RequestParam(required = false) Integer ftype, @RequestParam(required = false) Integer index,
			@RequestParam(required = false) String val, @RequestParam(required = false) Integer flevel,
	@RequestParam(required = false, defaultValue = "1") String did
	) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("code", 0);
		String findZaoFenInfo = "";
		if (1 == ftype) {
			findZaoFenInfo = YhFodClassifenergyWebMapper.findZaoFenInfo(flevel);
		}

		if (2 == ftype) {
			findZaoFenInfo = YhFodClassifenergyWebMapper.findWuFenInfo(flevel);
		}

		if (3 == ftype) {
			findZaoFenInfo = YhFodClassifenergyWebMapper.findWanFenInfo(flevel);
		}

		if (4 == ftype) {
			findZaoFenInfo = YhFodClassifenergyWebMapper.findJiaFenInfo(flevel);
		}

		String[] split = findZaoFenInfo.split(";");
		String string = split[index];
		String[] split2 = string.split("_");

		PageInfo<YhFoodCookBook> findCookBookInfoByType = YhFoodCookBookServiceImpl
				.findCookBookInfoByType(Integer.valueOf(split2[0]), val, page, limit);
		List<YhFoodCookBook> list = findCookBookInfoByType.getList();
		List<xm_selectVOJO> xlist = new ArrayList<>();
		if (null != list && list.size() > 0) {
			for (YhFoodCookBook yhFoodCookBook : list) {
				xm_selectVOJO xm = new xm_selectVOJO();
				xm.setName(yhFoodCookBook.getfCName());
				xm.setValue(String.valueOf(yhFoodCookBook.getfCId()));
				//不推荐人群
				String notRecommendFood = yhFoodCookBook.getNotRecommendFood();
				boolean flag=false;
				if(StringUtil.isNotEmpty(notRecommendFood)){
					List<String> notRecommendFoodList = Arrays.asList(notRecommendFood.split(";"));
					if(StringUtil.isNotEmpty(did)){
						List<String> strings = Arrays.asList(did.split(";"));
						for (int i = 0; i < notRecommendFoodList.size(); i++) {
							String s = notRecommendFoodList.get(i);
							for (int j = 0; j < strings.size(); j++) {
								String s1 = strings.get(j);
								if(s.equals(s1)){
									flag=true;
									break;
								}
							}
						}
					}
				}
				if(flag){
					xm.setDisabled(flag);
				}

				xlist.add(xm);
			}
		}

		rsMap.put("count", findCookBookInfoByType.getTotal());

		rsMap.put("msg", "ok");
		rsMap.put("data", xlist);
		return Result.ok(rsMap);
	}

	@ApiOperation(value = "重新选择能量渲染新增食谱页面")
	@RequestMapping(value = "/renderingAddRecipes", method = RequestMethod.POST)
	@ResponseBody
	public Map renderingAddRecipes(String flevel) {
		YhFodClassifenergyWeb findFenergyByFlevel = YhFodClassifenergyWebMapper.findFenergyByFlevel(flevel);
		// 早餐 1_90;1_90;4_18;3_90;2_90;
		String getfBreakfast = findFenergyByFlevel.getfBreakfast();
		// 午餐1_90;1_45;4_12;4_12;4_12;5_45;12_45;12_45;7_90;
		String getfLunch = findFenergyByFlevel.getfLunch();
		// 晚餐1_90;1_45;4_18;4_18;5_45;14_45;7_90;
		String getfDinner = findFenergyByFlevel.getfDinner();
		// 加餐13_90;
		String getfExtrameal = findFenergyByFlevel.getfExtrameal();
		String[] split = getfBreakfast.split(";");
		String[] split3 = getfLunch.split(";");
		String[] split4 = getfDinner.split(";");
		String[] split5 = getfExtrameal.split(";");

		findFenergyByFlevel.setZaoSplit(split);
		findFenergyByFlevel.setWuSplit(split3);
		findFenergyByFlevel.setWanSplit(split4);
		findFenergyByFlevel.setJiaSplit(split5);
		Map<String, Object> map = new HashMap<>();
		map.put("bookInfo", findFenergyByFlevel.getZaoSplit());
		map.put("wubookInfo", findFenergyByFlevel.getWuSplit());
		map.put("wanbookInfo", findFenergyByFlevel.getWanSplit());
		map.put("jiabookInfo", findFenergyByFlevel.getJiaSplit());
		return map;

	}

	public String disString(addRecipesInfoVojo reinfo) {
		String disStr = "";
		if ("on".equals(reinfo.getDiseaseCode1())) {
			disStr = disStr + "1;";
		}
		if ("on".equals(reinfo.getDiseaseCode2())) {
			disStr = disStr + "2;";
		}
		if ("on".equals(reinfo.getDiseaseCode3())) {
			disStr = disStr + "3;";
		}
		if ("on".equals(reinfo.getDiseaseCode4())) {
			disStr = disStr + "4;";
		}
		if ("on".equals(reinfo.getDiseaseCode5())) {
			disStr = disStr + "5;";
		}
		if ("on".equals(reinfo.getDiseaseCode6())) {
			disStr = disStr + "6;";
		}
		if ("on".equals(reinfo.getDiseaseCode7())) {
			disStr = disStr + "7;";
		}
		if ("on".equals(reinfo.getDiseaseCode8())) {
			disStr = disStr + "8;";
		}
		if ("on".equals(reinfo.getDiseaseCode9())) {
			disStr = disStr + "9;";
		}
		if ("on".equals(reinfo.getDiseaseCode10())) {
			disStr = disStr + "10;";
		}
		if ("on".equals(reinfo.getDiseaseCode11())) {
			disStr = disStr + "11;";
		}
		if ("on".equals(reinfo.getDiseaseCode12())) {
			disStr = disStr + "12;";
		}
		if ("on".equals(reinfo.getDiseaseCode13())) {
			disStr = disStr + "13;";
		}

		return disStr;
	}

	@ApiOperation(value = "渲染新增食谱页面")
	@RequestMapping(value = "/addSetMeal", method = RequestMethod.POST)
	@ResponseBody
	public Result addSetMeal(@RequestBody addRecipesInfoVojo reinfo) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		System.out.println(reinfo);
		YhFoodSrecipeNewWeb record = new YhFoodSrecipeNewWeb();
		record.setfType(3);
		record.seteLevel(Integer.valueOf(reinfo.getEfLevel()));
		record.setIsDel(2);
		record.setOneself(3);
		record.setStatus(0);
		record.setFsDescribe(reinfo.getFsDescribe());
		record.setfElements(forFElements(reinfo.getUserList()));
		record.setUserCode(yh.getUserCode());
		record.setDiseaseid(StrDiseaseid(reinfo.getArr()));
		record.setUpdateUsercode(yh.getUserCode());
		if(null!=reinfo.getuFoodErecipeId()&&reinfo.getuFoodErecipeId()!=0){
			record.setId(Long.valueOf(reinfo.getuFoodErecipeId()));
			record.setUserCode(null);
			int updateByPrimaryKeySelective = YhFoodSrecipeNewWebMapper.updateByPrimaryKeySelective(record);
		}else{
			int insertSelective = YhFoodSrecipeNewWebMapper.insertSelective(record);
		}

		return Result.ok(yh.getUserType());
	}

	public String StrDiseaseid(List<String> arr) {
		String disInfo = "";
		if (null != arr && arr.size() > 0) {
			for (String string : arr) {
				disInfo = disInfo + string + ";";
			}
		}
		return disInfo;
	}

	public String forFElements(List<String> foodList) {
		String foodInfo = "";
		if (null != foodList && foodList.size() > 0) {
			for (String string : foodList) {
				foodInfo = foodInfo + string + ";";
			}
		}
		return foodInfo;
	}

	@ApiOperation(value = "渲染新增食谱页面")
	@RequestMapping(value = "/renderingRecipes", method = RequestMethod.POST)
	@ResponseBody
	public Result renderingRecipes(@RequestBody addRecipesInfoVojo reinfo) {
		System.err.println(reinfo.toString());

		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		YhFoodUserScrecipeWebNew record = new YhFoodUserScrecipeWebNew();
		record.setUserCode(yh.getUserCode());
		record.seteLevel(Integer.valueOf(reinfo.getEfLevel()));
		record.setIsDel(2);
		record.setOneself(2);
		record.setcTime(new Date());
		record.setStartTime(new Date());
		record.setStateFlag(2);
		record.setSrecipeId(YhFoodUserScrecipeWebNewServiceImpl.findMaxSrecipeId() + 1);
		record.setDiseaseid(disString(reinfo));
		record.setfElements(YhFoodUserScrecipeWebNewServiceImpl.fElements(reinfo));

		if(null!=reinfo.getuFoodErecipeId()&&reinfo.getuFoodErecipeId()!=0){
			record.setuFoodErecipeId(reinfo.getuFoodErecipeId());
			record.setUpdateUsercode(yh.getUserCode());
			record.setUserCode(null);
			YhFoodUserScrecipeWebNewServiceImpl.updateByPrimaryKeySelective(record);
		}else{
			YhFoodUserScrecipeWebNewServiceImpl.insertSelective(record);
		}

		return Result.ok(yh.getUserType());

	}
	@ApiOperation(value = "每日食谱管理")
	@RequestMapping(value = "/editFoodRecipesFind", method = RequestMethod.GET)
	@ResponseBody
	public Result editFoodRecipesFind(String id) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		YhFoodSrecipeNewWeb selectByPrimaryKey = YhFoodSrecipeNewWebMapper.selectByPrimaryKey(Long.valueOf(id));
		String getfElements = selectByPrimaryKey.getfElements();
		List<Integer> list=new ArrayList<>();
		String[] split = getfElements.split(";");
		for (int i = 0; i < split.length; i++) {
			list.add(Integer.valueOf(split[i]));
		}
		List<YhFoodUserScrecipeWebNew> findFoodUserScrecipe2ByIdList = YhFoodUserScrecipeWebNewServiceImpl.findFoodUserScrecipe2ByIdList(list);
		for (int i = 0; i < findFoodUserScrecipe2ByIdList.size(); i++) {

			findFoodUserScrecipe2ByIdList.get(i).setDoctor(yh.getUserCode());
			YhFoodUserScrecipeWebNew yhFoodUserScrecipeWebNew = findFoodUserScrecipe2ByIdList.get(i);
			// 根据疾病id获得疾病名称
			String diseaseid2 = yhFoodUserScrecipeWebNew.getDiseaseid();
			findFoodUserScrecipe2ByIdList.get(i).setDiseaseidStr(fordiseaseid(diseaseid2));
			String getfElements2 = yhFoodUserScrecipeWebNew.getfElements();
			findFoodUserScrecipe2ByIdList.get(i).setfElementsStr(forgetfElements(getfElements2));
			Integer oneself2 = yhFoodUserScrecipeWebNew.getOneself();

			// 不是系统提供

			if (null!=oneself2&&1 != oneself2) {
				String userCode = yhFoodUserScrecipeWebNew.getUserCode();
				if (StringUtil.isNotEmpty(userCode)) {
					YhUserReginfo yhUserReginfo = yhUserReginfoService.selectYhUserReginfoData(userCode);
					findFoodUserScrecipe2ByIdList.get(i).setOneselfStr(yhUserReginfo.getUserName());
				}

			} else {
				findFoodUserScrecipe2ByIdList.get(i).setOneselfStr("系统默认提供");
			}

		}
		return Result.ok(findFoodUserScrecipe2ByIdList);
	}

	@ApiOperation(value = "每日食谱管理")
	@RequestMapping(value = "/adddailyRecipes2", method = RequestMethod.GET)
	@ResponseBody
	public Result adddailyRecipes2(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false) Integer oneself, @RequestParam(required = false) Integer eLevel,
			@RequestParam(required = false) String diseaseidArray,@RequestParam(required = false) String id) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("code", 0);
		JSONArray jsonArr = JSONArray.fromObject(diseaseidArray);
		List<String> dlist = new ArrayList<String>();
		if (null != jsonArr) {
			for (int i = 0; i < jsonArr.size(); i++) {
				dlist.add("%;" + String.valueOf(jsonArr.get(i)) + ";%");
			}
		}
		YhFoodSrecipeNewWeb selectByPrimaryKey = YhFoodSrecipeNewWebMapper.selectByPrimaryKey(Long.valueOf(id));
		String getfElements2 = selectByPrimaryKey.getfElements();
		List<Integer> listdis=new ArrayList<>();
		String[] split = getfElements2.split(";");
		for (int i = 0; i < split.length; i++) {
			listdis.add(Integer.valueOf(split[i]));
		}

		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		String diseaseidStr = null;
		System.out.println(diseaseidArray);

		PageInfo<YhFoodUserScrecipeWebNew> findTodayScrecipeInfo = YhFoodUserScrecipeWebNewServiceImpl
				.findTodayScrecipeInfo3(oneself, yh.getUserCode(), eLevel, dlist, page, limit,listdis);
		List<YhFoodUserScrecipeWebNew> list = findTodayScrecipeInfo.getList();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setDoctor(yh.getUserCode());
				YhFoodUserScrecipeWebNew yhFoodUserScrecipeWebNew = list.get(i);
				// 根据疾病id获得疾病名称
				String diseaseid2 = yhFoodUserScrecipeWebNew.getDiseaseid();
				list.get(i).setDiseaseidStr(fordiseaseid(diseaseid2));
				String getfElements = yhFoodUserScrecipeWebNew.getfElements();
				list.get(i).setfElementsStr(forgetfElements(getfElements));
				Integer oneself2 = yhFoodUserScrecipeWebNew.getOneself();
				// 不是系统提供
				if (1 != oneself2) {
					String userCode = yhFoodUserScrecipeWebNew.getUserCode();
					if (StringUtil.isNotEmpty(userCode)) {
						YhUserReginfo yhUserReginfo = yhUserReginfoService.selectYhUserReginfoData(userCode);
						list.get(i).setOneselfStr(yhUserReginfo.getUserName());
					}

				} else {
					list.get(i).setOneselfStr("系统默认提供");
				}
			}
		}

		rsMap.put("count", findTodayScrecipeInfo.getTotal());

		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}


	@ApiOperation(value = "每日食谱管理")
	@RequestMapping(value = "/adddailyRecipes", method = RequestMethod.GET)
	@ResponseBody
	public Result adddailyRecipes(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false) Integer oneself, @RequestParam(required = false) Integer eLevel,
			@RequestParam(required = false) String diseaseidArray) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("code", 0);
		JSONArray jsonArr = JSONArray.fromObject(diseaseidArray);
		List<String> dlist = new ArrayList<String>();
		if (null != jsonArr) {
			for (int i = 0; i < jsonArr.size(); i++) {
				dlist.add("%;" + String.valueOf(jsonArr.get(i)) + ";%");
			}
		}

		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		String diseaseidStr = null;
		System.out.println(diseaseidArray);

		PageInfo<YhFoodUserScrecipeWebNew> findTodayScrecipeInfo = YhFoodUserScrecipeWebNewServiceImpl
				.findTodayScrecipeInfo2(oneself, yh.getUserCode(), eLevel, dlist, page, limit);
		List<YhFoodUserScrecipeWebNew> list = findTodayScrecipeInfo.getList();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setDoctor(yh.getUserCode());
				YhFoodUserScrecipeWebNew yhFoodUserScrecipeWebNew = list.get(i);
				// 根据疾病id获得疾病名称
				String diseaseid2 = yhFoodUserScrecipeWebNew.getDiseaseid();
				list.get(i).setDiseaseidStr(fordiseaseid(diseaseid2));
				String getfElements = yhFoodUserScrecipeWebNew.getfElements();
				list.get(i).setfElementsStr(forgetfElements(getfElements));
				Integer oneself2 = yhFoodUserScrecipeWebNew.getOneself();
				// 不是系统提供
				if (null!=oneself2&&1 != oneself2) {
					String userCode = yhFoodUserScrecipeWebNew.getUserCode();
					if (StringUtil.isNotEmpty(userCode)) {
						YhUserReginfo yhUserReginfo = yhUserReginfoService.selectYhUserReginfoData(userCode);
						if(null==yhUserReginfo){
							list.get(i).setOneselfStr("未知用户");
						}else{
							list.get(i).setOneselfStr(yhUserReginfo.getUserName());
						}

					}

				} else {
					list.get(i).setOneselfStr("系统默认提供");
				}
			}
		}

		rsMap.put("count", findTodayScrecipeInfo.getTotal());

		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}

	@ApiOperation(value = "每日食谱管理")
	@RequestMapping(value = "/dailyRecipes", method = RequestMethod.GET)
	@ResponseBody
	public Result dailyRecipes(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit,
			@RequestParam(required = false) Integer oneself, @RequestParam(required = false) Integer eLevel,
			@RequestParam(required = false) Integer diseaseid) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("code", 0);

		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		String diseaseidStr = null;
		if (null != diseaseid) {
			diseaseidStr = diseaseid + ";";
		}
		PageInfo<YhFoodUserScrecipeWebNew> findTodayScrecipeInfo = YhFoodUserScrecipeWebNewServiceImpl
				.findTodayScrecipeInfo(oneself, yh.getUserCode(), eLevel, diseaseidStr, page, limit);
		List<YhFoodUserScrecipeWebNew> list = findTodayScrecipeInfo.getList();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setDoctor(yh.getUserCode());
				YhFoodUserScrecipeWebNew yhFoodUserScrecipeWebNew = list.get(i);
				System.out.println(yhFoodUserScrecipeWebNew.toString());
				// 根据疾病id获得疾病名称
				String diseaseid2 = yhFoodUserScrecipeWebNew.getDiseaseid();
				list.get(i).setDiseaseidStr(fordiseaseid(diseaseid2));
				String getfElements = yhFoodUserScrecipeWebNew.getfElements();
				list.get(i).setfElementsStr(forgetfElements(getfElements));
				Integer oneself2 = yhFoodUserScrecipeWebNew.getOneself();
				System.out.println("oneself2-----------------------------------------------------"+oneself2);
				System.out.println("oneself2-----------------------------------------------------"+oneself2);
				System.out.println("oneself2-----------------------------------------------------"+oneself2);
				System.out.println("oneself2-----------------------------------------------------"+oneself2);

				// 不是系统提供
				if (null != oneself2&&1 != oneself2) {
					String userCode = yhFoodUserScrecipeWebNew.getUserCode();
					if (StringUtil.isNotEmpty(userCode)) {
						YhUserReginfo yhUserReginfo = yhUserReginfoService.selectYhUserReginfoData(userCode);
						if(null!=yhUserReginfo){
							list.get(i).setOneselfStr(yhUserReginfo.getUserName());
						}

					}

				} else {
					list.get(i).setOneselfStr("系统默认提供");
				}
			}
		}

		rsMap.put("count", findTodayScrecipeInfo.getTotal());

		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}

	@ApiOperation(value = "每日食谱管理机构端")
	@RequestMapping(value = "/dailyRecipesMechanism", method = RequestMethod.GET)
	@ResponseBody
	public Result dailyRecipesMechanism(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit,
			@RequestParam(required = false) Integer oneself, @RequestParam(required = false) Integer eLevel,
			@RequestParam(required = false) Integer diseaseid) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("code", 0);

		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		Integer userType = yh.getUserType();
		String diseaseidStr = null;
		if (null != diseaseid) {
			diseaseidStr = diseaseid + ";";
		}
		PageInfo<YhFoodUserScrecipeWebNew> findTodayScrecipeInfo = YhFoodUserScrecipeWebNewServiceImpl
				.findTodayScrecipeInfo(oneself, yh.getUserCode(), eLevel, diseaseidStr, page, limit);
		List<YhFoodUserScrecipeWebNew> list = findTodayScrecipeInfo.getList();
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setDoctor(yh.getUserCode());
				YhFoodUserScrecipeWebNew yhFoodUserScrecipeWebNew = list.get(i);
				// 根据疾病id获得疾病名称
				String diseaseid2 = yhFoodUserScrecipeWebNew.getDiseaseid();
				list.get(i).setDiseaseidStr(fordiseaseid(diseaseid2));
				String getfElements = yhFoodUserScrecipeWebNew.getfElements();
				list.get(i).setfElementsStr(forgetfElements(getfElements));
				Integer oneself2 = yhFoodUserScrecipeWebNew.getOneself();
				// 不是系统提供
				if (null!=oneself2&&1 != oneself2) {
					String userCode = yhFoodUserScrecipeWebNew.getUserCode();
					if (StringUtil.isNotEmpty(userCode)) {
						YhUserReginfo yhUserReginfo = yhUserReginfoService.selectYhUserReginfoData(userCode);
						list.get(i).setOneselfStr(yhUserReginfo.getUserName());

						 if(userType==1){
							 List<String> strings = yhFoodCookBookService.findjigouInfo(yh.getOrgCode());
							 for (int j = 0; j < strings.size(); j++) {
								 if(userCode.equals(strings.get(j))){
									 list.get(i).setQuanxuan(1);
								 }
							 }
							 List<String> doctorInfo = yhFoodCookBookService.findDoctorInfo(yh.getOrgCode());
							 for (int k = 0; k < doctorInfo.size(); k++) {
								 if(userCode.equals(doctorInfo.get(k))){
									 list.get(i).setQuanxuan(1);
								 }
							 }
						 }


					}

				} else {
					list.get(i).setOneselfStr("系统默认提供");
				}
			}
		}

		rsMap.put("count", findTodayScrecipeInfo.getTotal());

		rsMap.put("msg", "ok");
		rsMap.put("data", list);
		return Result.ok(rsMap);
	}

	public String forgetfElements(String getfElements) {
		// 1_85504_144;1_82698_125;1_83064_310;2_83411_475;2_86719_102;2_84305_175;3_82715_299;3_85817_25;3_86720_42;3_84350_205;4_82551_107;
		String getfElementsStr = "";
		String infoZao = "早餐：";
		if (StringUtil.isNotEmpty(getfElements)) {
			String[] split = getfElements.split(";");
			for (String string : split) {
				// 1_85504_144
				String[] split2 = string.split("_");
				// 早中晚类型
				String string2 = split2[0];
				// 食物id
				String string3 = split2[1];
				YhFoodCookBook selectByPrimaryKey = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(string3));
				if (null == selectByPrimaryKey) {
					continue;
				}
				// 克数
				String string4 = split2[2];
				switch (string2) {
				case "1":
					// 语句
					infoZao = infoZao + selectByPrimaryKey.getfCName() + "_" + string4 + "g" + ";";
					break; // 可选
				/*
				 * case "2" : //语句
				 * //infoWu=infoWu+selectByPrimaryKey.getfCName()+"_"+string4+
				 * "g"+";"; break; //可选 case "3" : //语句
				 * //infoWan=infoWan+selectByPrimaryKey.getfCName()+"_"+string4+
				 * "g"+";"; break; //可选 case "4" : //语句
				 * //infoJia=infoJia+selectByPrimaryKey.getfCName()+"_"+string4+
				 * "g"+";"; break; //可选
				 */ // 你可以有任意数量的case语句
				default: // 可选
					// 语句
				}

			}

			getfElementsStr = infoZao + "...";
		}
		return getfElementsStr;
	}

	public String fordiseaseid(String diseaseid) {
		String disStr = "";
		if (StringUtil.isNotEmpty(diseaseid)) {
			String[] split = diseaseid.split(";");
			for (String string : split) {
				YhDiseaseCategoryExample example = new YhDiseaseCategoryExample();
				Criteria createCriteria = example.createCriteria();
				createCriteria.andDiseaseCodeEqualTo(Integer.valueOf(string));
				YhDiseaseCategory yhDiseaseCategory = YhDiseaseCategoryMapper.selectByExample(example).get(0);
				disStr = disStr + yhDiseaseCategory.getDiseaseName() + ",";
			}
		}
		return disStr;
	}

	@ApiOperation(value = "更换套餐")
	@RequestMapping(value = "/changeSetmealCheck", method = RequestMethod.POST)
	@ResponseBody
	public Result changeSetmealCheck(@RequestBody InterventionAdjustment info) {
		String message = "";
		if (null == info) {
			message = "未选用户";
			return Result.error();
		}
		List<String> usercodeList = info.getUsercodeList();
		Set hashSetMode = new HashSet();
		Set hashSetDis = new HashSet();
		String fistMode = "";
		if (null != usercodeList && usercodeList.size() > 0) {
			for (String usercode : usercodeList) {

				YhFoodSrecipeRecord findFoodSrecipeRecordByUserCodeForNew = yhFoodSrecipeRecordService
						.findFoodSrecipeRecordByUserCodeForNew(usercode);
				if (null == findFoodSrecipeRecordByUserCodeForNew) {
					continue;
				}
				String recordMode = findFoodSrecipeRecordByUserCodeForNew.getRecordMode();
				hashSetMode.add(recordMode);
				hashSetDis.add(findFoodSrecipeRecordByUserCodeForNew.getIllnessId());

			}
		}

		// 如果全是健康计划的人 就看他是不是一种疾病
		boolean flag = true;
		if (hashSetMode.size() == 1) {
			Iterator<String> it = hashSetMode.iterator();
			if (it.hasNext()) {
				String next = it.next();
				if ("健康计划".equals(next)) {
					if (hashSetDis.size() > 1) {
						flag = false;
						message = "操作提示：不能对参与不同健康计划的人，同时更换套餐，请分类进行";
					}
				}
			}
		}
		// 健康计划，系统自动，医师手动 如果没有健康计划也能通过

		if (hashSetMode.size() > 1) {
			Iterator<String> it = hashSetMode.iterator();
			while (it.hasNext()) {
				String str = it.next();
				if ("健康计划".equals(str)) {
					flag = false;
					message = "操作提示：不能对参与健康计划和不参与健康计划的人，同时更换套餐，请分步进行";

				}
			}
		}

		return Result.ok(message, flag);
	}

	@ApiOperation(value = "更换套餐")
	@RequestMapping(value = "/changeSetmeal", method = RequestMethod.POST)
	@ResponseBody
	public Result changeSetmeal(@RequestBody InterventionAdjustment info) {
		System.out.println(info.getUsercodeList());
		System.out.println(info.getSrecipeId());
		List<String> addList = new ArrayList<String>();
		List<String> noaddList = new ArrayList<String>();
		if (null != info && info.getUsercodeList().size() > 0) {
			for (int i = 0; i < info.getUsercodeList().size(); i++) {
				YhFoodSrecipeRecord record = new YhFoodSrecipeRecord();
				String string = info.getUsercodeList().get(i);
				// 查询膳食推荐
				YhFoodSrecipeNewWeb selectByPrimaryKey = YhFoodSrecipeNewWebMapper.findSrecipeById(info.getSrecipeId());
				if (null == selectByPrimaryKey) {
					continue;
				}
				record.setRecordData(new Date());
				record.setRecordName(selectByPrimaryKey.getDiseaseName());
				record.setRecordKay(selectByPrimaryKey.geteLevel());
				record.setRecordType(1);
				record.setRecordMode("医师手动");
				record.setUserCode(string);
				record.setMealId(String.valueOf(info.getSrecipeId()));
				record.setIllnessId(String.valueOf(selectByPrimaryKey.getDiseaseid()));
				// 最新饮食评估
				YhFoodSrecipeRecord findFoodSrecipeRecordByUserCodeForNew = yhFoodSrecipeRecordService
						.findFoodSrecipeRecordByUserCodeForNew(string);
				if (null != findFoodSrecipeRecordByUserCodeForNew) {
					if ("健康计划".equals(findFoodSrecipeRecordByUserCodeForNew.getRecordMode())) {
						record.setRecordMode("健康计划");
					}
					if (StringUtil.isEmpty(findFoodSrecipeRecordByUserCodeForNew.getAge())) {
						findFoodSrecipeRecordByUserCodeForNew = yhFoodSrecipeRecordService
								.findFoodSrecipeRecordByUserCodeForNewISInfo(string);
					}
					if(null!=findFoodSrecipeRecordByUserCodeForNew&&StringUtil.isNotEmpty(findFoodSrecipeRecordByUserCodeForNew.getAge())&&StringUtil.isNotEmpty(findFoodSrecipeRecordByUserCodeForNew.getHeight())
							&&StringUtil.isNotEmpty(findFoodSrecipeRecordByUserCodeForNew.getWeight())&&StringUtil.isNotEmpty(findFoodSrecipeRecordByUserCodeForNew.getStrength())&&StringUtil.isNotEmpty(findFoodSrecipeRecordByUserCodeForNew.getSex())){
						record.setAge(findFoodSrecipeRecordByUserCodeForNew.getAge());
						record.setHeight(findFoodSrecipeRecordByUserCodeForNew.getHeight());
						record.setWeight(findFoodSrecipeRecordByUserCodeForNew.getWeight());
						record.setStrength(findFoodSrecipeRecordByUserCodeForNew.getStrength());
						record.setSex(findFoodSrecipeRecordByUserCodeForNew.getSex());
					}

				}

				yhFoodSrecipeRecordService.modifyFoodSrecipeRecordByUserCodeForType2(string);

				int insertSelective = yhFoodSrecipeRecordService.insertSelective(record);
				if (insertSelective > 0) {
					addList.add(string);
				} else {
					noaddList.add(string);
				}

			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("add", addList);
		map.put("noadd", noaddList);
		return Result.ok(map);
	}

	@ApiOperation(value = "膳食推荐套餐集合")
	@RequestMapping(value = "/foodSetMeal2", method = RequestMethod.POST)
	@ResponseBody
	public Result foodSetMeal2(@RequestParam(required = false) Integer ftype,
			@RequestParam(required = false) Integer eLevel, @RequestParam(required = false) Integer diseaseid) {
		List<YhFoodSrecipeRecord> findFoodSreCipeByAllInfo = YhFoodSrecipeNewWebMapper.findFoodSreCipeByAllInfo(ftype,
				eLevel, diseaseid);
		return Result.ok(findFoodSreCipeByAllInfo);
	}

	@ApiOperation(value = "膳食推荐更换套餐")
	@RequestMapping(value = "/foodChangePackage", method = RequestMethod.POST)
	@ResponseBody
	public Result foodSetMeal(String usercode, Integer ftype, Integer eLevel, Integer diseaseid) {
		List<YhFoodSrecipeRecord> findFoodSreCipeByAllInfo = new ArrayList<YhFoodSrecipeRecord>();
		YhFoodSrecipeRecord findTypeoneNewInfo = yhFoodSrecipeRecordService.findTypeoneNewInfo(usercode);
		if (null != findTypeoneNewInfo) {
			if ("健康计划".equals(findTypeoneNewInfo.getRecordMode())) {
				findFoodSreCipeByAllInfo = YhFoodSrecipeNewWebMapper.findFoodSreCipeByAllInfo(2, eLevel,
						Integer.valueOf(findTypeoneNewInfo.getIllnessId()));
				return Result.ok(findTypeoneNewInfo.getIllnessId(), findFoodSreCipeByAllInfo);
			} else {
				findFoodSreCipeByAllInfo = YhFoodSrecipeNewWebMapper.findFoodSreCipeByAllInfo(ftype, eLevel, diseaseid);
				return Result.ok("0", findFoodSreCipeByAllInfo);
			}

		} else {
			findFoodSreCipeByAllInfo = YhFoodSrecipeNewWebMapper.findFoodSreCipeByAllInfo(ftype, eLevel, diseaseid);
			return Result.ok("0", findFoodSreCipeByAllInfo);
		}

	}

	@ApiOperation(value = "膳食推荐套餐集合")
	@RequestMapping(value = "/foodSetMeal", method = RequestMethod.POST)
	@ResponseBody
	public Result foodSetMeal(@RequestBody InterventionAdjustment info) {
		List<String> usercodeList = info.getUsercodeList();
		Set hashSetMode = new HashSet();
		Set hashSetDis = new HashSet();
		if (null != usercodeList && usercodeList.size() > 0) {
			for (int i = 0; i < usercodeList.size(); i++) {
				String usercode = usercodeList.get(i);
				YhFoodSrecipeRecord findFoodSrecipeRecordByUserCodeForNew = yhFoodSrecipeRecordService
						.findFoodSrecipeRecordByUserCodeForNew(usercode);
				if (null == findFoodSrecipeRecordByUserCodeForNew) {
					continue;
				}

				String recordMode = findFoodSrecipeRecordByUserCodeForNew.getRecordMode();
				hashSetMode.add(recordMode);
				hashSetDis.add(findFoodSrecipeRecordByUserCodeForNew.getIllnessId());

			}
			if (hashSetMode.size() == 1) {
				Iterator<String> it = hashSetMode.iterator();
				if (it.hasNext()) {
					String next = it.next();
					if ("健康计划".equals(next)) {
						if (hashSetDis.size() == 1) {
							Iterator<String> it2 = hashSetDis.iterator();
							if (it2.hasNext()) {
								String next2 = it2.next();

								List<YhFoodSrecipeRecord> findFoodSreCipeByAllInfo = YhFoodSrecipeNewWebMapper
										.findFoodSreCipeByAllInfo(2, info.getELevel(), Integer.valueOf(next2));
								return Result.ok(next2, findFoodSreCipeByAllInfo);
							}
						}

					} else {
						List<YhFoodSrecipeRecord> findFoodSreCipeByAllInfo = YhFoodSrecipeNewWebMapper
								.findFoodSreCipeByAllInfo(info.getFtype(), info.getELevel(), info.getDiseaseid());
						return Result.ok("0", findFoodSreCipeByAllInfo);
					}
				}
			}

			if (hashSetMode.size() == 2) {
				List<YhFoodSrecipeRecord> findFoodSreCipeByAllInfo = YhFoodSrecipeNewWebMapper
						.findFoodSreCipeByAllInfo(info.getFtype(), info.getELevel(), info.getDiseaseid());
				return Result.ok("0", findFoodSreCipeByAllInfo);
			}

		}

		return Result.error();

	}



	@ApiOperation(value = "干预调整")
	@RequestMapping(value = "/adjustment", method = RequestMethod.GET)
	@ResponseBody
	public Result adjustment(String usercode, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "5") int limit, @RequestParam(required = false) String orgid,
			@RequestParam(required = false) String groupid, @RequestParam(required = false) String mode,
			@RequestParam(required = false) String sex, @RequestParam(required = false) String strength,
			@RequestParam(required = false) String ageStr, @RequestParam(required = false) String ageEnd,
			@RequestParam(required = false) String heightStr, @RequestParam(required = false) String heightEnd,
			@RequestParam(required = false) String weightStr, @RequestParam(required = false) String weightEnd,
			@RequestParam(required = false) String kayStr, @RequestParam(required = false) String kayEnd,
			@RequestParam(required = false) String recordname, @RequestParam(required = false) String username

	) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("code", 0);
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh = (YhUserReginfo) subject.getPrincipal();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgid", orgid);
		if (StringUtil.isNotEmpty(groupid)) {
			groupid = "f" + groupid + "_";
		}
		map.put("groupid", groupid);
		map.put("mode", mode);
		map.put("sex", sex);
		map.put("strength", strength);
		map.put("ageStr", ageStr);
		map.put("ageEnd", ageEnd);
		map.put("heightStr", heightStr);
		map.put("heightEnd", heightEnd);
		map.put("weightStr", weightStr);
		map.put("weightEnd", weightEnd);
		map.put("kayStr", kayStr);
		map.put("kayEnd", kayEnd);
		map.put("recordname", recordname);
		map.put("username", username);
		map.put("usercode", yh.getUserCode());
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println("key=" + entry.getKey() + " value=" + entry.getValue());
		}

		PageInfo<InterventionAdjustment> list = yhFoodSrecipeRecordService.findInterventionAdjustmentInfo(map, page,
				limit);
		List<String> userlist = yhFoodSrecipeRecordService.findInterventionAdjustmentInfo2(map);
		List<InterventionAdjustment> list2 = list.getList();
		if (null != list2 && list2.size() > 0) {
			for (int i = 0; i < list2.size(); i++) {
				InterventionAdjustment interventionAdjustment = list2.get(i);
				String flag_Dgroup = interventionAdjustment.getFlag_Dgroup();
				String forGroup = forGroup(flag_Dgroup);
				interventionAdjustment.setFlag_Dgroup(forGroup);
				/*
				 * String age = interventionAdjustment.getAge();
				 * if(StringUtil.isEmpty(age)){ String
				 * findElemtInfoByFoodSrecipe =
				 * yhFoodSrecipeRecordService.findElemtInfoByFoodSrecipe(
				 * interventionAdjustment.getUsercode(), "age");
				 * interventionAdjustment.setAge(findElemtInfoByFoodSrecipe); }
				 * String height = interventionAdjustment.getHeight();
				 * if(StringUtil.isEmpty(height)){ String
				 * findElemtInfoByFoodSrecipe =
				 * yhFoodSrecipeRecordService.findElemtInfoByFoodSrecipe(
				 * interventionAdjustment.getUsercode(), "height");
				 * interventionAdjustment.setHeight(findElemtInfoByFoodSrecipe);
				 * }
				 *
				 * String weight = interventionAdjustment.getWeight();
				 * if(StringUtil.isEmpty(weight)){ String
				 * findElemtInfoByFoodSrecipe =
				 * yhFoodSrecipeRecordService.findElemtInfoByFoodSrecipe(
				 * interventionAdjustment.getUsercode(), "weight");
				 * interventionAdjustment.setWeight(findElemtInfoByFoodSrecipe);
				 * }
				 */
				String age = interventionAdjustment.getAge();
				String sex1=interventionAdjustment.getSex();
				String height = interventionAdjustment.getHeight();
				String weight = interventionAdjustment.getWeight();
				String strength1 = interventionAdjustment.getStrength();
				if(StringUtil.isNotEmpty(age)&&StringUtil.isNotEmpty(sex1)&&StringUtil.isNotEmpty(height)&&StringUtil.isNotEmpty(weight)&&StringUtil.isNotEmpty(strength1)){

					double v = yhFoodSrecipeRecordService.jisuanRiXuNengLiang(Integer.valueOf(sex1), Integer.valueOf(age), Double.valueOf(height), Double.valueOf(weight), strength1);
					interventionAdjustment.setRecord_kay(String.valueOf(v));
				}else{
					interventionAdjustment.setRecord_kay("");
				}
//int sexInt,
//                           int ageInt,
//                           double height,
//                           double weight,
//                           String sport


			}
		}

		rsMap.put("count", list.getTotal());
		rsMap.put("usercodeList", userlist);
		rsMap.put("msg", "ok");
		rsMap.put("data", list2);
		return Result.ok(rsMap);
	}

	public String forGroup(String str) {
		if (StringUtil.isEmpty(str) || "0".equals(str)) {
			return "未分组";
		}
		String groupInfo = "";
		// f224_g1;f226_g2;f221_g5;
		String[] split = str.split(";");
		for (String string : split) {
			if (StringUtil.isEmpty(string)) {
				continue;
			}
			// f224_g1
			String[] split2 = string.split("_");
			String string2 = split2[0];
			String substring = string2.substring(1);
			YHDocGroup gInfo = YHDocGroupMapper.selectByPrimaryKey(Integer.valueOf(substring));
			if (null != gInfo) {
				groupInfo = groupInfo + gInfo.getGname() + ";";
			}
		}
		return groupInfo;
	}

	@ApiOperation(value = "增加内容")
	@RequestMapping(value = "/insertSelective", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteByPrimaryKey(String order, String userCode) {
		if (StringUtil.isEmpty(userCode) || StringUtil.isEmpty(order)) {
			return Result.error();
		}

		YhFoodSrecipeRecord record = new YhFoodSrecipeRecord();

		// 查询膳食推荐
		YhFoodSrecipeNewWeb selectByPrimaryKey = YhFoodSrecipeNewWebMapper.findSrecipeById(Integer.valueOf(order));
		if (null == selectByPrimaryKey) {
			return Result.error();
		}
		record.setRecordData(new Date());
		record.setRecordName(selectByPrimaryKey.getDiseaseName());
		record.setRecordKay(selectByPrimaryKey.geteLevel());
		record.setRecordType(1);

		record.setRecordMode("医师手动");
		record.setUserCode(userCode);
		record.setMealId(order);
		record.setIllnessId(String.valueOf(selectByPrimaryKey.getDiseaseid()));
		// 最新饮食评估
		YhFoodSrecipeRecord findFoodSrecipeRecordByUserCodeForNew = yhFoodSrecipeRecordService
				.findFoodSrecipeRecordByUserCodeForNew(userCode);
		if (null != findFoodSrecipeRecordByUserCodeForNew) {
			if ("健康计划".equals(findFoodSrecipeRecordByUserCodeForNew.getRecordMode())) {
				record.setRecordMode("健康计划");
			}
			if (StringUtil.isEmpty(findFoodSrecipeRecordByUserCodeForNew.getAge())) {
				findFoodSrecipeRecordByUserCodeForNew = yhFoodSrecipeRecordService
						.findFoodSrecipeRecordByUserCodeForNewISInfo(userCode);
			}
			record.setAge(findFoodSrecipeRecordByUserCodeForNew.getAge());
			record.setHeight(findFoodSrecipeRecordByUserCodeForNew.getHeight());
			record.setWeight(findFoodSrecipeRecordByUserCodeForNew.getWeight());
			record.setStrength(findFoodSrecipeRecordByUserCodeForNew.getStrength());
			record.setSex(findFoodSrecipeRecordByUserCodeForNew.getSex());
		}

		yhFoodSrecipeRecordService.modifyFoodSrecipeRecordByUserCodeForType2(userCode);

		int insertSelective = yhFoodSrecipeRecordService.insertSelective(record);

		return Result.ok(insertSelective);
	}

	@ApiOperation(value = "查询是否存在该套餐描述")
	@RequestMapping(value = "/findYhFoodFsdescribe", method = RequestMethod.POST)
	@ResponseBody
	public Result findYhFoodFsdescribe(String fsDescribe) {
		return Result.ok(yhFoodSrecipeMapper.findSrecipeByfsDescribeCount(fsDescribe));
	}

	@ApiOperation(value = "查找膳食套餐所有")
	@RequestMapping(value = "/findYhFoodCategory", method = RequestMethod.POST)
	@ResponseBody
	public Result findYhFoodCategory() {
		return Result.ok(YhFoodCategoryWebMapper.findYhFoodCategoryAll());
	}

	@ApiOperation(value = "查找膳食套餐所有")
	@RequestMapping(value = "/selectSrecipeshow", method = RequestMethod.POST)
	@ResponseBody
	public Result showheadline(String setmealMame, String sex, String taste) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", setmealMame);
		map.put("sex", sex);
		map.put("taste", taste);
		List<YhFoodSrecipe> list = yhFoodSrecipeMapper.selectSrecipeshow(map);
		for (YhFoodSrecipe yhFoodSrecipe : list) {
			String dType = "";// 口味
			String genter = "";// 男女
			if (yhFoodSrecipe.getdType() == 1) {
				dType = "清淡";
			} else if (yhFoodSrecipe.getdType() == 2) {
				dType = "甜";
			} else if (yhFoodSrecipe.getdType() == 3) {
				dType = "咸";
			} else if (yhFoodSrecipe.getdType() == 4) {
				dType = "辣";
			}
			if (yhFoodSrecipe.getGenter() == 1) {
				genter = "男性";
			} else {
				genter = "女性";
			}
			yhFoodSrecipe.setName(yhFoodSrecipe.getName());
			yhFoodSrecipe.setMuxin(
					genter + "  " + dType + "  " + yhFoodSrecipe.getName() + "  " + "热量:" + yhFoodSrecipe.geteLevel());
		}

		return Result.ok(list);
	}

	@ApiOperation(value = "查找膳食")
	@RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectByPrimaryKey(String fElements) {
		// 10_1_2834_245.0,90342_120.0,90472_75.0,91201_50;10_1_2841_100,3971_20.0,90066_80.0;
		String[] arr1 = fElements.split(";");
		List<YhFoodCookBook> breakfast = new ArrayList<YhFoodCookBook>();
		List<YhFoodCookBook> lunch = new ArrayList<YhFoodCookBook>();
		List<YhFoodCookBook> supper = new ArrayList<YhFoodCookBook>();
		List<YhFoodCookBook> extra = new ArrayList<YhFoodCookBook>();
		for (int k = 0; k < arr1.length; k++) {

			String[] arr2 = arr1[k].split("_");
			if ("10".equals(arr2[0])) {
				// 10_1_2834_245.0,90342_120.0,90472_75.0,91201_50
				String[] split = arr1[k].split(",");
				// 10_1_2834_245.0
				String string2 = split[0];

				String[] split3 = string2.split("_");

					YhFoodCookBook cook = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(split3[2]));
					BigDecimal energyKcal2 = new BigDecimal(cook.getEnergyKcal());
					BigDecimal b=	energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(split3[3]));
					BigDecimal f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP);



				BigDecimal allKacl=new BigDecimal("0");
				List<YhFoodCookBook> breakfastSon = new ArrayList<YhFoodCookBook>();
				List<YhFoodCookBook> lunchSon = new ArrayList<YhFoodCookBook>();
				List<YhFoodCookBook> supperSon = new ArrayList<YhFoodCookBook>();
				List<YhFoodCookBook> extraSon = new ArrayList<YhFoodCookBook>();
				for (int i = 1; i < split.length; i++) {
					String string = split[i];
					String[] split2 = string.split("_");
					YhFoodCookBook cookSon = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(split2[0]));
					BigDecimal energyKcalSon = new BigDecimal(cookSon.getEnergyKcal());
					BigDecimal bSon=	energyKcalSon.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(split2[1]));
					BigDecimal f1Son = bSon.setScale(2, BigDecimal.ROUND_HALF_UP);
					cookSon.setEnergyKcalRiji(f1Son.toString());
					allKacl=allKacl.add(f1Son);
					cookSon.setChengfen(true);
					switch (arr2[1]) {
						case "1":
							breakfastSon.add(cookSon);
							break;
						case "2":
							lunchSon.add(cookSon);
							break;
						case "3":
							supperSon.add(cookSon);
							break;
						case "4":
							extraSon.add(cookSon);
							break;

						default:
							break;
					}
				}
				BigDecimal f1allKacl = allKacl.setScale(2, BigDecimal.ROUND_HALF_UP);
				cook.setEnergyKcalRiji(f1allKacl.toString());

				switch (arr2[1]) {
					case "1":
						breakfast.add(cook);
						breakfast.addAll(breakfastSon);
						breakfastSon=new ArrayList<>();
						break;
					case "2":
						lunch.add(cook);
						lunch.addAll(lunchSon);
						lunchSon=new ArrayList<>();
						break;
					case "3":
						supper.add(cook);
						supper.addAll(supperSon);
						supperSon=new ArrayList<>();
						break;
					case "4":
						extra.add(cook);
						extra.addAll(extraSon);
						extraSon=new ArrayList<>();
						break;

					default:
						break;
				}



			} else {
				if (arr2[1] == "1" || arr2[1].equals("1")) {// 增加早餐
					YhFoodCookBook cook = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(arr2[2]));
					BigDecimal energyKcal2 = new BigDecimal(cook.getEnergyKcal());
					BigDecimal b=	energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(arr2[3]));
					BigDecimal f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP);
					cook.setEnergyKcalRiji(f1.toString());
					breakfast.add(cook);
				} else if (arr2[1] == "2" || arr2[1].equals("2")) {// 增加中餐
					YhFoodCookBook cook = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(arr2[2]));
					BigDecimal energyKcal2 = new BigDecimal(cook.getEnergyKcal());
					BigDecimal b=	energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(arr2[3]));
					BigDecimal f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP);
					cook.setEnergyKcalRiji(f1.toString());
					lunch.add(cook);
				} else if (arr2[1] == "3" || arr2[1].equals("3")) {// 增加晚餐
					YhFoodCookBook cook = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(arr2[2]));
					BigDecimal energyKcal2 = new BigDecimal(cook.getEnergyKcal());
					BigDecimal b=	energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(arr2[3]));
					BigDecimal f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP);
					cook.setEnergyKcalRiji(f1.toString());
					supper.add(cook);
				} else if (arr2[1] == "4" || arr2[1].equals("4")) {// 增加加餐
					YhFoodCookBook cook = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(arr2[2]));
					BigDecimal energyKcal2 = new BigDecimal(cook.getEnergyKcal());
					BigDecimal b=	energyKcal2.divide(new BigDecimal("100"),4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(arr2[3]));
					BigDecimal f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP);
					cook.setEnergyKcalRiji(f1.toString());
					extra.add(cook);
				}
			}

		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("zao", breakfast);
		map.put("zhong", lunch);
		map.put("wan", supper);
		map.put("jia", extra);
		return map;
	}

	@ApiOperation(value = "查询用户查看食物套餐详情")
	@RequestMapping(value = "/recordfoodshow", method = RequestMethod.POST)
	@ResponseBody
	public Result recordfoodshow(int id) {

		// 总记录表
		YhFoodSrecipeVoJo vojo = new YhFoodSrecipeVoJo();

		YhFoodSrecipeRecord i = yhFoodSrecipeRecordService.selectByPrimaryKey(id);// 用户推荐膳食处方记录表

		if (i != null) {
			YhFoodSrecipe cipe = hFoodSrecipeService.selectByPrimaryKey(Integer.valueOf(i.getMealId()));// 用户推荐膳食处方表
			String disStr = "";
			String diseaseid = cipe.getDiseaseid();
			if (StringUtil.isNotEmpty(diseaseid)) {
				String[] split = diseaseid.split(";");
				for (String string : split) {
					YhDiseaseCategory selectByPrimaryKey = YhDiseaseCategoryMapper
							.selectByPrimaryKey(Integer.valueOf(string));
					disStr = disStr + selectByPrimaryKey.getDiseaseName() + ";";
				}
				vojo.setTryout(i.getName() + "患者");// 试用人群
			}

			vojo.setDescribe(cipe.getFsDescribe());// 添加描述
			vojo.setKilocalorie(String.valueOf(cipe.geteLevel()));// 每天热量
			// vojo.setPackagename(i.getName() + "饮食套餐");
			String[] arr = cipe.getfElements().split(";");
			vojo.setNumber(arr.length);// 总天数
			if (arr != null || arr.length != 0) {
				List<YhFoodrecordVoJo> cordvojo = new ArrayList<YhFoodrecordVoJo>();
				for (int j = 0; j < arr.length; j++) {// 循环套餐次数
					// 用户膳食处方详细表
					YhFoodUserScrecipeWebNew foodcipe = YhFoodUserScrecipeWebNewServiceImpl
							.selectByPrimaryKey(Integer.valueOf(arr[j]));
					String[] arr1 = foodcipe.getfElements().split(";");
					List<YhFoodCookBook> breakfast = new ArrayList<YhFoodCookBook>();
					List<YhFoodCookBook> lunch = new ArrayList<YhFoodCookBook>();
					List<YhFoodCookBook> supper = new ArrayList<YhFoodCookBook>();
					List<YhFoodCookBook> extra = new ArrayList<YhFoodCookBook>();
					YhFoodrecordVoJo food = new YhFoodrecordVoJo();
					for (int k = 0; k < arr1.length; k++) {
						String[] arr2 = arr1[k].split("_");
						if (arr2[0] == "1" || arr2[0].equals("1")) {// 增加早餐
							YhFoodCookBook cook = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(arr2[1]));
							cook.setEnergyKcal(Float.valueOf(arr2[2]));
							breakfast.add(cook);
						} else if (arr2[0] == "2" || arr2[0].equals("2")) {// 增加中餐
							YhFoodCookBook cook = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(arr2[1]));
							cook.setEnergyKcal(Float.valueOf(arr2[2]));
							lunch.add(cook);
						} else if (arr2[0] == "3" || arr2[0].equals("3")) {// 增加晚餐
							YhFoodCookBook cook = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(arr2[1]));
							cook.setEnergyKcal(Float.valueOf(arr2[2]));
							supper.add(cook);
						} else if (arr2[0] == "4" || arr2[0].equals("4")) {// 增加加餐
							YhFoodCookBook cook = yhFoodCookBookMapper.selectByPrimaryKey(Integer.valueOf(arr2[1]));
							cook.setEnergyKcal(Float.valueOf(arr2[2]));
							extra.add(cook);
						}
					}

					food.setBreakfast(breakfast);// 早
					food.setLunch(lunch);// 中
					food.setSupper(supper);// 晚
					food.setExtra(extra);// 加
					food.setBreakfastkilocalorie(String.valueOf(cipe.geteLevel() * 0.25));
					food.setLunchkilocalorie(String.valueOf(cipe.geteLevel() * 0.3));
					food.setBsupperkilocalorie(String.valueOf(cipe.geteLevel() * 0.3));
					food.setExtrakilocalorie(String.valueOf(cipe.geteLevel() * 0.15));
					cordvojo.add(food);
				}
				vojo.setVojo(cordvojo);

			}
		}

		return Result.ok(vojo);
	}

	/**
	 * 查询用户查看食物套餐内容
	 *
	 * @param page
	 * @param limit
	 * @return
	 */
	@ApiOperation(value = "查询用户查看食物套餐内容")
	@RequestMapping(value = "/selectSrecipeRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectSrecipeRecord(Integer page, Integer limit, String usercode) {
		Map<String, Object> map1 = new HashMap<String, Object>();
		Pages pa = new Pages();
		pa.setCurPage(page);
		pa.setMaxResult(limit);
		map1.put("page", pa.getFirstRows());
		map1.put("limit", pa.getMaxResult());
		map1.put("userCode", usercode);
		Map<String, Object> map = new HashMap<String, Object>();
		List<YhFoodSrecipeRecord> list = yhFoodSrecipeRecordService.selectSrecipeRecord(map1);
		for (int i = 0; i < list.size(); i++) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//获取String类型的时间
			String createdate = sdf.format(list.get(i).getRecordData());
			list.get(i).setRecordDataStr(createdate);
		}
		map.put("code", 20000);
		map.put("msg", "");
		map.put("count", yhFoodSrecipeRecordService.selectSrecipeRecordConut(map1));
		map.put("data", list);
		return map;
	}

}
