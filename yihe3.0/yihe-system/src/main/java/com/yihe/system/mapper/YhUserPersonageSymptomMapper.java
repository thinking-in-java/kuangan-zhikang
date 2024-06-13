package com.yihe.system.mapper;

import com.yihe.system.domain.YhUserPersonageSymptom;

public interface YhUserPersonageSymptomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageSymptom record);

    int insertSelective(YhUserPersonageSymptom yhUserPersonageSymptom);

    YhUserPersonageSymptom selectByPrimaryKey(String userCode);
    
    int updateByPrimaryKeySelective(YhUserPersonageSymptom yhUserPersonageSymptom);

    int updateByPrimaryKey(YhUserPersonageSymptom yhUserPersonageSymptom);
}