package com.yihe.system.mapper;

import com.yihe.system.domain.YhChineseAnwserr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface YhcheidentificationMapper {
    YhChineseAnwserr selectByPrimaryKey(Integer reportId);
    List<YhChineseAnwserr> selectUserYhPainTotalReportParam(@Param("userCode") String userCode, @Param("qtstrTime") String qtstrTime, @Param("qtendTime")String qtendTime);
    List<YhChineseAnwserr> selectUserAllYhPainTotalReportParam(@Param("symptom") String symptom, @Param("orgCode") String orgCode, @Param("name") String name, @Param("userCode") String userCode, @Param("qtstrTime") String qtstrTime, @Param("qtendTime")String qtendTime);
}
