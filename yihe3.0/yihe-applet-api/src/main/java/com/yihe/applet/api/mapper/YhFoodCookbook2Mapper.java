package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhFoodCookbook2;

public interface YhFoodCookbook2Mapper {

    List<String> findZuiJinEat(@Param("usercode")String usercode,@Param("page")Integer page);

	List<String> findUserFoodDiaryAllInfo(String usercode);
	
	String findFoodInfoByIDForName(String id);
	
	int findFoodCookBookAllNumByType(Integer fcid);
	
	 int findFoodCookBookNumByTypeGTFood(Integer fcid);
	
	List<YhFoodCookbook2> selectFoodCookBookName(@Param("foodName")String foodName,@Param("usercode")String usercode,@Param("curPage")Integer curPage,@Param("pageSize") Integer pageSize);
	
	List<YhFoodCookbook2> findFoodCookCollectionByUserCodeForFoodInfo(@Param("usercode")String usercode);
	
	List<YhFoodCookbook2> findFoodCookBookInfoByUserCodeAndDescCommon(@Param("usercode")String usercode);
	
    int deleteByPrimaryKey(Integer fCId);

    int insert(YhFoodCookbook2 record);

    int insertSelective(YhFoodCookbook2 record);

    YhFoodCookbook2 selectByPrimaryKey(Integer fCId);

    int updateByPrimaryKeySelective(YhFoodCookbook2 record);

    int updateByPrimaryKey(YhFoodCookbook2 record);
    
    YhFoodCookbook2 selectBYshowName(String fCName);
    
    List<YhFoodCookbook2> findFoodName(@Param("fCName") String fCName);
    
}