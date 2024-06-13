package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhUserPersonagePhysicalApp;

public interface YhUserPersonagePhysicalAppService {
    int insertSelective(YhUserPersonagePhysicalApp record);

    YhUserPersonagePhysicalApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonagePhysicalApp record);
    
    YhUserPersonagePhysicalApp showphysical(String userCode);
}
