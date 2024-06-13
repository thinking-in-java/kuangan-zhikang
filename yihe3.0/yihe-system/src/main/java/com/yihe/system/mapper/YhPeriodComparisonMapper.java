package com.yihe.system.mapper;


import com.yihe.system.domain.PeriodComparison;
import com.yihe.system.domain.YhDiseaseAssessTotalReport3web;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;

import java.util.List;

@Mapper
public interface YhPeriodComparisonMapper {
    List<YhDiseaseAssessTotalReport3web> get(@Param("model") PeriodComparison model);
    List<YhDiseaseAssessTotalReport3web> getMeddle(@Param("model") PeriodComparison model);
    String selectbmi(String requestnum);
    String getrepotimg();
}
