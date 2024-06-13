package com.yihe.applet.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YHDiseaseAssessResolve;

/**
 * Description:
 * Created by Administrator on 2020/5/26 17:59
 */
public interface YhDiseaseAssessResolveAppMapper {
    /*
                  批量插入改善因素
     */
    int insertBatch(List<YHDiseaseAssessResolve> record);
    
    List<YHDiseaseAssessResolve> selectResolves(@Param("diseaseid") Integer diseaseid, @Param("requestNum") String requestNum);
    /*
     * 查询改善图文
     */
    String selectImagetext(String improve); 
}
