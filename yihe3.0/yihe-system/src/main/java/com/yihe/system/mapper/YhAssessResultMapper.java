package com.yihe.system.mapper;

import com.yihe.system.domain.DataExportVojo;
import com.yihe.system.domain.YhAssessConclusion;
import com.yihe.system.domain.YhAssessResult;
import com.yihe.system.domain.YhAssessResultVojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhAssessResultMapper {
    /** 查询是否存在暂存问卷 */
    String findByTs(@Param("userCode") String userCode, @Param("assess") Integer assess);

    int insertSelective(YhAssessResult record);
    /** 删除已存在的暂存问卷 */
    int updByTs(String userCode);

    YhAssessResult findByUser(@Param("userCode") String userCode, @Param("assess") Integer assess);

    List<Map<String, Object>> findConcent();

    int assessCount(@Param("userCode") String userCode, @Param("assess") Integer assess);

    YhAssessResult findByAssess(@Param("userCode") String userCode, @Param("assess") String assess);

    List<String> findNames();

    String findName(Integer id);

    YhAssessResult findById(Integer id);

    List<YhAssessResultVojo> findByList(YhAssessResultVojo record);

    List<Map<String,Object>> findAssess();

    List<Map<String,Object>> findAssessAll();

    YhAssessResult findAssessInfo(@Param("userCode")String userCode, @Param("assess")Integer assess);

    List<YhAssessResult> findUserAssList(YhAssessResultVojo record);

    List<YhAssessResult> selPsychologyAssess(DataExportVojo record);
}
