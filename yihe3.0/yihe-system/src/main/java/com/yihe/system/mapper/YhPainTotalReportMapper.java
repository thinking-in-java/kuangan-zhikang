package com.yihe.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhPainTotalReport;
import com.yihe.system.domain.YhPainTotalReportRecord;

public interface YhPainTotalReportMapper {
    int deleteByPrimaryKey(Integer reportId);

    int insert(YhPainTotalReport record);

    int insertSelective(YhPainTotalReport record);

    YhPainTotalReport selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(YhPainTotalReport record);
    YhPainTotalReport selectYhPainTotalReportParm();
    YhPainTotalReportRecord selectYhPainTotalReportParam(@Param("userCode") String userCode,@Param("reportId") Integer reportId);
    List<YhPainTotalReport> selectUserYhPainTotalReportParam(@Param("userCode") String userCode,@Param("qtstrTime") String qtstrTime,@Param("qtendTime")String qtendTime);
    List<YhPainTotalReport> selectUserAllYhPainTotalReportParam(@Param("symptom") String symptom,@Param("orgCode") String orgCode,@Param("name") String name,@Param("userCode") String userCode,@Param("qtstrTime") String qtstrTime,@Param("qtendTime")String qtendTime);
}