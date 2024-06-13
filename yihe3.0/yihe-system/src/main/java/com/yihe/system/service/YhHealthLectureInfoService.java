package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhHealthLectureInfo;

/**
* YhHealthLectureInfoService接口
* Created by dong on 2020/3/27.
*/
public interface YhHealthLectureInfoService  {
	
	/**
	 * 根据类型id查询课堂次数
	 * @param categoryId
	 * @return
	 */
    int selectBylectureCount(Integer categoryId);
    
    /**
     * 查询所有课堂模糊查询
     * @param map
     * @return
     */
    List<YhHealthLectureInfo> selectLectureInfo(Map<String, Object> map);
    /**
     * 查询所有课堂模糊查询总行数
     * @param map
     * @return
     */
    int selectLectureInfoConut(Map<String, Object> map);
    
    /**
     * 增加课堂
     * @param record
     * @return
     */
    int insertSelective(YhHealthLectureInfo record);
    
    YhHealthLectureInfo selectByPrimaryKey(Integer infoid);
    
    int updateByPrimaryKeySelective(YhHealthLectureInfo record);
    
    int deleteByPrimaryKey(Integer infoid);
    
    List<YhHealthLectureInfo> selectByExample();
    
    int selctTitleRepetition(Map<String, Object> map);
}