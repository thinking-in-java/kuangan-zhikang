package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserAwardMedal;

public interface YhUserAwardMedalService {
	List<YhUserAwardMedal> selectYhUserAwardMedal(String userCode);
}
