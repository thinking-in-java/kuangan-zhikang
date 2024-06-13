package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhPainResolveApp;

public interface YhPainResolveAppMapper {
    int deleteByPrimaryKey(Integer resolveId);

    int updateByPrimaryKey(YhPainResolveApp record);
     
    List<YhPainResolveApp> selectAppraisalreportByParam(String userCode);
}