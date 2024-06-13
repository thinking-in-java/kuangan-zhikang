package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhUserPersonageConstitutionApp;

public interface YhUserPersonageConstitutionAppService {

	    int insertSelective(YhUserPersonageConstitutionApp record);

	    YhUserPersonageConstitutionApp selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(YhUserPersonageConstitutionApp record);

	    YhUserPersonageConstitutionApp showconstitution(String userCode);
}
