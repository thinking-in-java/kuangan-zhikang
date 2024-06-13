/**
 * 
 */
package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhFoodCookBook;

/**
 * 食物接口
 * @author dong
 *
 */
public interface YhFoodCookBookService {
	List<String> findDoctorInfo(String org);
	List<String> findjigouInfo(String org);
	PageInfo<YhFoodCookBook> findFoodCookBookByNo10(String val,Integer pageNum,Integer pageSize);
	 PageInfo<YhFoodCookBook> findCookBookInfoByType(Integer ftype,String val,Integer pageNum,Integer pageSize);
	 
	 YhFoodCookBook selectByPrimaryKeyOld(Integer fCId);
	    
	/**
	 * 查询所有食物模糊查询
	 * @param map
	 * @return
	 */
	List<YhFoodCookBook> selectCookBook(Map<String,Object> map);
    
	/**
	 * 查询所有食物模糊查询总行数
	 * @param map
	 * @return
	 */
    int selectCookBookConut(Map<String,Object> map);
    
    /**
     * 增加食物
     * @param record
     * @return
     */
    int insertSelective(YhFoodCookBook record);
    
    /**
     * 修改食物
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(YhFoodCookBook record);
    
    /**
     * 删除食物
     * @param fCId
     * @return
     */
    int deleteByPrimaryKey(Integer fCId);
    
    /**
     * 根据id查询
     * @param fCId
     * @return
     */
    YhFoodCookBook selectByPrimaryKey(Integer fCId);
    
    int selctFcNameRepetition(Map<String,Object> map);
}
