package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhEventEnroll;
import com.yihe.system.domain.YhEventEnrollExample;

/**
* YhEventEnrollService接口
* Created by dong on 2020/3/24.
*/
public interface YhEventEnrollService  {
	
	/**
	 * 查询所有精彩活动模糊查询
	 * @param map
	 * @return
	 */
	  List<YhEventEnroll> selectEnrollAll(Map<String, Object> map);
	  
	  /**
		 * 查询所有精彩活动模糊查询总行数
		 * @param map
		 * @return
		 */
	  int selectEnrollAllConut(Map<String, Object> map);
	  
	  int insertSelective(YhEventEnroll record);
	  
	  int deleteByPrimaryKey(Integer id);
	  
	  YhEventEnroll selectByPrimaryKey(Integer id);
	  
	  int updateByPrimaryKeySelective(YhEventEnroll record);
	   List<YhEventEnroll> selectByExample();
	   int selctEventNmaeRepetition(Map<String, Object> map);
}