/**
 * 
 */
package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhFoodSrecipe;

/**
 * 食物套餐模板
 * @author dong
 *
 */
public interface YhFoodSrecipeService {
	int updateByPrimaryKeySelective(YhFoodSrecipe record);

	PageInfo<YhFoodSrecipe> findFoodPackageManagement(Integer oneself,String usercode,Integer ftype,Integer elevel,Integer diseaseid,String fsDescribe,Integer pageNum,Integer pageSize);
	
	int insertAll(Map<String, Object> map);
	
	List<YhFoodSrecipe> findSrecipeAllInfo();
	
	YhFoodSrecipe findFoodSrecipeByFsId(Integer fsId);
	
	YhFoodSrecipe findYhFoodSrecipeByDtypeAndSexAndNumAndDis(Integer type,Integer sex,Integer number,String diseaseid);
	
	YhFoodSrecipe findYhFoodSrecipeByDtypeAndSexAndNumAndDisNew(Integer type,Integer sex,Integer number,String diseaseid,int isUse);
	
	/**
	 * 查询所有食物套餐模糊查询
	 * @param map
	 * @return
	 */
	List<YhFoodSrecipe> selectSrecipe(Map<String, Object> map);
    
	/**
	 * 查询所有食物套餐模糊查询总行数
	 * @param map
	 * @return
	 */
    int selectSrecipeConut(Map<String, Object> map);
    
    YhFoodSrecipe selectByPrimaryKey(Integer fsId);
}
