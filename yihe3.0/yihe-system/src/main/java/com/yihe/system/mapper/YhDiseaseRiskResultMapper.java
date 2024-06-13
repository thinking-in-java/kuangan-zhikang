package com.yihe.system.mapper;

import com.yihe.system.domain.YhDiseaseRiskResult;
import com.yihe.system.domain.YhDiseaseRiskResult2;
import com.yihe.system.domain.YhDiseaseRiskResultExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhDiseaseRiskResultMapper {


	YhDiseaseRiskResult2 selectRiskResulByRequestNum(String RequestNum);

    long countByExample(YhDiseaseRiskResultExample example);

    int deleteByExample(YhDiseaseRiskResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(YhDiseaseRiskResult record);

    int insertSelective(YhDiseaseRiskResult record);

    List<YhDiseaseRiskResult> selectByExample(YhDiseaseRiskResultExample example);

    YhDiseaseRiskResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") YhDiseaseRiskResult record, @Param("example") YhDiseaseRiskResultExample example);

    int updateByExample(@Param("record") YhDiseaseRiskResult record, @Param("example") YhDiseaseRiskResultExample example);

    int updateByPrimaryKeySelective(YhDiseaseRiskResult record);

    int updateByPrimaryKey(YhDiseaseRiskResult record);

    List<YhDiseaseRiskResult2> selectByImprovability(Map<String, Object> map);

    Map<String,Long> selectByImprovabilityNew(@Param("orgCode")String orgCode,@Param("flag")String flag);

    Map<String,Long> selectByImprovabilityNew2(@Param("orgCode")String orgCode,@Param("flag")String flag);

    List<YhDiseaseRiskResult> selectHasimproved(Map<String, Object> map);
}