package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.system.domain.YhFoodCookBook;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhFoodCookbook2;

public interface YhFoodCookbook2Service {

    List<YhFoodCookbook2> findZuiJinEat(String usercode);

	List<String> findUserFoodDiaryAllInfo(String usercode);
	 
	String findFoodInfoByIDForName(String id);
	
	int findFoodCookBookAllNumByType(Integer fcid);
	
	 int findFoodCookBookNumByTypeGTFood(Integer fcid);
	
	List<YhFoodCookbook2> selectFoodCookBookName(String foodName,String usercode,Integer curPage, Integer pageSize);
	
	List<YhFoodCookbook2> findFoodCookCollectionByUserCodeForFoodInfo(String usercode);
	
	List<YhFoodCookbook2> findFoodCookBookInfoByUserCodeAndDescCommon(String usercode);
	
    int deleteByPrimaryKey(Integer fCId);

    int insert(YhFoodCookbook2 record);

    int insertSelective(YhFoodCookbook2 record);

    YhFoodCookbook2 selectByPrimaryKey(Integer fCId);

    int updateByPrimaryKeySelective(YhFoodCookbook2 record);

    int updateByPrimaryKey(YhFoodCookbook2 record);
    YhFoodCookbook2 selectBYshowName(String fCName);
    List<YhFoodCookbook2> findFoodName(String fCName);
}
