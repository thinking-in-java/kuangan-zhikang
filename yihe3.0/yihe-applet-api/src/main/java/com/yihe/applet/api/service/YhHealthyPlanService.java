package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhFoodCookbook2;
import com.yihe.applet.api.domain.YhHealthyPlanApp;
import com.yihe.applet.api.domain.YhHealthyPlandetail;
import com.yihe.applet.api.domain.YhUserHealthyPlanApp;
import com.yihe.system.domain.YhFoodSrecipe;
import com.yihe.system.domain.YhFoodSrecipeRecord;
import com.yihe.system.domain.YhFoodUserScrecipe;

import java.util.List;

/**
* YhHealthyPlanService接口
* Created by dong on 2020/4/13.
*/
public interface YhHealthyPlanService {
	List<YhHealthyPlanApp> yhhealthyplan(Integer curPage, Integer pageSize);
	int selectyhhealthycount();
	int yhhealthplanwatch(Integer id);
	YhHealthyPlanApp selectyhhealthyplanbyid(Integer id);
	int addHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan);
	int boolenaddHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan);
	int updateHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan);
	List<YhUserHealthyPlanApp> selectyhuserhealthplanisbegin(YhUserHealthyPlanApp yhuserhealthyplan);
	YhUserHealthyPlanApp selectalreadybegun(YhUserHealthyPlanApp yhuserhealthyplan);
	YhHealthyPlandetail plandetail(Integer id);
	int tofinishoneajax(YhUserHealthyPlanApp yhUserhealthypaln);
	List<YhUserHealthyPlanApp> selectreportsteptwo(YhUserHealthyPlanApp YhUserHealthyPlan);
	int selectbloodpressurenowday(String UserCode);
	int selectbminowday(String UserCode);
	int selectbloodsugernowday(String UserCode);
	int selectfoodnowday(String UserCode);
	YhFoodSrecipeRecord selectrecode(String Usercode);
	Integer selectday(String Usercode);
	YhFoodSrecipe selectsrecipe(String id);
	YhFoodUserScrecipe selectscrecipe(String ids);
	YhFoodCookbook2 selectcookbook(String foodsid);
	YhFoodUserScrecipe selectIsHaveFood(String userCode);
	int delHealthPlanDetail(YhUserHealthyPlanApp yhuserhealthyplan);
}