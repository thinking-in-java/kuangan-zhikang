package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhUserPersonageConditionApp;

public interface YhUserPersonageConditionAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageConditionApp record);

    int insertSelective(YhUserPersonageConditionApp record);

    YhUserPersonageConditionApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonageConditionApp record);

    int updateByPrimaryKey(YhUserPersonageConditionApp record);
    YhUserPersonageConditionApp showcondition(String userCode);


    YhUserPersonageConditionApp selectByHeightAndWeight(String userCode);


}