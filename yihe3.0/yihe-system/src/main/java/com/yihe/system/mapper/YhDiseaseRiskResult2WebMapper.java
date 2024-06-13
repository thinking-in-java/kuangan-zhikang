package com.yihe.system.mapper;

import com.yihe.system.domain.YhDiseaseRiskResult2Web;

public interface YhDiseaseRiskResult2WebMapper {
	
	YhDiseaseRiskResult2Web selectByPrimaryByRs(String rs);
	
    int deleteByPrimaryKey(Integer id);

    int insert(YhDiseaseRiskResult2Web record);

    int insertSelective(YhDiseaseRiskResult2Web record);

    YhDiseaseRiskResult2Web selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhDiseaseRiskResult2Web record);

    int updateByPrimaryKey(YhDiseaseRiskResult2Web record);
    
    YhDiseaseRiskResult2Web selectUserDiseaseRiskByParam(String userCode);
}