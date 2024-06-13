package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhFoodCookbookCollection;

public interface YhFoodCookbookCollectionMapper {
	
	int delInfoByFoodidAndUserCode(@Param("foodid")Integer foodid,@Param("usercode")String usercode);
	
	int findCookBookByFoodid(@Param("foodid")Integer foodid,@Param("usercode")String usercode);
	
    int deleteByPrimaryKey(Integer cid);

    int insert(YhFoodCookbookCollection record);

    int insertSelective(YhFoodCookbookCollection record);

    YhFoodCookbookCollection selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(YhFoodCookbookCollection record);

    int updateByPrimaryKey(YhFoodCookbookCollection record);
}