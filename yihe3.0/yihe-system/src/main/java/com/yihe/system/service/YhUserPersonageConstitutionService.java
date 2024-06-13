package com.yihe.system.service;

import com.yihe.system.domain.YhUserPersonageConstitution;

public interface YhUserPersonageConstitutionService {

    YhUserPersonageConstitution selectByPrimaryKey(String userCode);
    
    int updateByPrimaryKey(YhUserPersonageConstitution yhUserPersonageConstitution);
    
    int insertSelective(YhUserPersonageConstitution yhUserPersonageConstitution);
}
