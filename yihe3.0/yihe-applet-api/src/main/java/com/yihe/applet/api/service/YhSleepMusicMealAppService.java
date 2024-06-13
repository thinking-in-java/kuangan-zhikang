package com.yihe.applet.api.service;


import com.yihe.applet.api.domain.YhSleepMusicMealApp;

import java.util.List;

/**
 * @version: shayu3.0
 * @Description: 助眠套餐service
 * @author: lhh
 * @date: 2020年5月10日 上午10:30:32
 */
public interface YhSleepMusicMealAppService {
	/*
	 * 查询所有助眠套餐
	 */
	public List<YhSleepMusicMealApp> selAllMusicMeal();
	
	public YhSleepMusicMealApp selTypeMusicMeal(String mealTitle);
}
