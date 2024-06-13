/**
 * 
 */
package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.InterventionAdjustment;
import com.yihe.system.domain.SportInterveneVojo;
import com.yihe.system.domain.YhSportRecommendRecord;
import com.yihe.system.domain.YhSportRecommendRecordVojo;

/**
 * @author dong
 *
 */
public interface YhSportRecommendRecordService {

	/**
	 * 运动干预
	 * @param map
	 * @return
	 */
		PageInfo<SportInterveneVojo> SportInterveneInfo(Map<String, Object> map,Integer pageNum,Integer pageSize);
		
	/**
	 * 用户查看运动套餐
	 * @param map
	 * @return
	 */
	List<YhSportRecommendRecord> selectPersonageSport(Map<String, Object> map);
    
	/**
	 * 用户查看运动套餐总行数
	 * @param map
	 * @return
	 */
    int selectPersonageSportConut(Map<String, Object> map);
    
    int insertSelective(YhSportRecommendRecord record);
    
    List<YhSportRecommendRecord> seelctCode(String userCode);
    
    int updateByPrimaryKeySelective(YhSportRecommendRecord record);
    
    int seelctCodeCount(Map<String, Object> map);
    
    /**
	 * 根据code查询运动推荐类型
	 * @param userCode
	 * @return
	 */
	List<YhSportRecommendRecordVojo> showSportRecommended(String userCode);
	
	/**
	 * 查询是否打卡
	 * @param finishid
	 * @return
	 */
	YhSportRecommendRecord selectByRecord(Map<String, Object> map);
	/**
    增
     */
    int insertYhSportRecommendRecord(YhSportRecommendRecord yhSportRecommendRecord);
    /**
    根据疾病id与userCode查询是否已存在推荐，存在则改为过期状态
     */
    int uptYhSportRecommendRecord(YhSportRecommendRecord yhSportRecommendRecord);
    /**
    根据疾病id与userCode查询是否已存在推荐
     */
    int selYhSportRecordCount(YhSportRecommendRecord yhSportRecommendRecord);
    
    YhSportRecommendRecord selectByPrimaryKey(Integer id);
    
    List<YhSportRecommendRecordVojo> showfitness();
    
    
}
