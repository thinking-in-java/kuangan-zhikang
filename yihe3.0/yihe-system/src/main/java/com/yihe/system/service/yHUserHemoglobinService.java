package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserHemoglobin;

import java.util.Map;

public interface yHUserHemoglobinService {
    //根据用户编号查询用户最新的血红蛋白信息
    Map<String,Object> selectUserHemoglobin(String userCode);
    //列表数据查询
    public PageInfo<YhUserHemoglobin> queryUserByCriteriaWithPage(String userCode, int page, int limit);
    //图表数据查询
    PageInfo<YhUserHemoglobin> selectHemoglobin(String userCode, int page, int limit);

    YhUserHemoglobin selectByPrimaryKeyByUsercodeLimit(String userCode);
    int insertSelective(YhUserHemoglobin record);
}
