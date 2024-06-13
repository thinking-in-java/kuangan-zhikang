package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.SportInterveneVojo;
import com.yihe.system.domain.YhSportRecommendRecord;
import com.yihe.system.domain.YhSportRecommendRecordVojo;

public interface YhSportRecommendRecordMapper {
	List<SportInterveneVojo> SportInterveneInfo(Map<String, Object> map);
	
    int deleteByPrimaryKey(Integer id);

    int insert(YhSportRecommendRecord record);

    int insertSelective(YhSportRecommendRecord record);

    YhSportRecommendRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhSportRecommendRecord record);

    int updateByPrimaryKey(YhSportRecommendRecord record);
    
    List<YhSportRecommendRecord> selectPersonageSport(Map<String, Object> map);
    
    int selectPersonageSportConut(Map<String, Object> map);
    
    List<YhSportRecommendRecord> seelctCode(String userCode);
    
    int seelctCodeCount(Map<String, Object> map);
    
 List<YhSportRecommendRecordVojo> showSportRecommended(String userCode);
 
 List<YhSportRecommendRecordVojo> showfitness();
    
    YhSportRecommendRecord selectByRecord(Map<String, Object> map);
    /*
    增
     */
    int insertYhSportRecommendRecord(YhSportRecommendRecord yhSportRecommendRecord);
    /*
    根据疾病id与userCode查询是否已存在推荐，存在则改为过期状态
     */
    int uptYhSportRecommendRecord(YhSportRecommendRecord yhSportRecommendRecord);
    /*
    根据疾病id与userCode查询是否已存在推荐
     */
    int selYhSportRecordCount(YhSportRecommendRecord yhSportRecommendRecord);
}