package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhUserPersonagePharmacyApp;

public interface YhUserPersonagePharmacyAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhUserPersonagePharmacyApp record);

    int insertSelective(YhUserPersonagePharmacyApp record);

    YhUserPersonagePharmacyApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhUserPersonagePharmacyApp record);

    int updateByPrimaryKey(YhUserPersonagePharmacyApp record);
    
    List<YhUserPersonagePharmacyApp> showpharmacy(String userCode);
}