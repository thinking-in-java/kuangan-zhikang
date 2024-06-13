package com.yihe.applet.api.mapper;
import  com.yihe.applet.api.domain.YhUserPersonageSymptomApp;
public interface YhUserPersonageSymptomAppMapper {
   
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonageSymptomApp record);

    int insertSelective(YhUserPersonageSymptomApp record);

    YhUserPersonageSymptomApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonageSymptomApp record);

    int updateByPrimaryKey(YhUserPersonageSymptomApp record);

    YhUserPersonageSymptomApp showsymptom(String usercode);
    
  
}