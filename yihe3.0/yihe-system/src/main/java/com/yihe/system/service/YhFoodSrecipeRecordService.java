/**
 * 
 */
package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.InterventionAdjustment;
import com.yihe.system.domain.YhFoodSrecipeRecord;

/**
 * @author dong
 *
 */
public interface YhFoodSrecipeRecordService {
	//计算日需能量
	double jisuanRiXuNengLiang(int sexInt,int ageInt,double height,double weight,String sport);

	String findElemtInfoByFoodSrecipe(String usercode,String elemt);
	//查询正在执行的饮食推荐
	YhFoodSrecipeRecord findTypeoneNewInfo(String usercode);
	
	//查询该用户最新一条饮食推荐
	YhFoodSrecipeRecord findFoodSrecipeRecordByUserCodeForNew(String usercode);
	//查询该用户最新一条饮食推荐 存在年龄信息的
	YhFoodSrecipeRecord findFoodSrecipeRecordByUserCodeForNewISInfo(String usercode);
	
	//该用户的套餐全部修改为过期
	int modifyFoodSrecipeRecordByUserCodeForType2(String usercode);
	
	
	//干预调整
	PageInfo<InterventionAdjustment> findInterventionAdjustmentInfo(Map<String, Object> map,Integer pageNum,Integer pageSize);

	List<String> findInterventionAdjustmentInfo2(Map<String, Object> map);
	/**
	 * 用户查看膳食查询所有
	 * @param map
	 * @return
	 */
	 List<YhFoodSrecipeRecord> selectSrecipeRecord(Map<String, Object> map);
	    
	 /**
	  * 用户查看膳食查询总行数
	  * @param map
	  * @return
	  */
	    int selectSrecipeRecordConut(Map<String, Object> map);
	    
	    /**
	     * 根据id查询数据（记录，疾病）
	     * @param recordId
	     * @return
	     */
	    YhFoodSrecipeRecord selectByPrimaryKey(Integer recordId);
	    
	    
	    /**
	     * 查询最新一条
	     * @return
	     */
	    YhFoodSrecipeRecord selectNewest(String userCode);
	    
	    
	    int insertSelective(YhFoodSrecipeRecord record);
	    
	    int updateByPrimaryKeySelective(YhFoodSrecipeRecord record);
	    
	    int updateByPrimaryKeySelective1(YhFoodSrecipeRecord record);

}
