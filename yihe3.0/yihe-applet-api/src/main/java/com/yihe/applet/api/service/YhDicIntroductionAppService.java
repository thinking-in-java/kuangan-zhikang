package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhDicIntroductionApp;

public interface YhDicIntroductionAppService {

	    int insertSelective(YhDicIntroductionApp record);

	    YhDicIntroductionApp selectByPrimaryKey(Integer code);

	    int updateByPrimaryKeySelective(YhDicIntroductionApp record);

	    int updateByPrimaryKey(YhDicIntroductionApp record);
	    YhDicIntroductionApp selectBmi (String name);
	    YhDicIntroductionApp selectWHR(String name);
	    YhDicIntroductionApp selectExercise (String name);
	    YhDicIntroductionApp selecTnutrient (String name);
	    YhDicIntroductionApp selectEverydayConsumption (String name);
}
