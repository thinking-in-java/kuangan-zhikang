package com.yihe.applet.api.mapper;
/**
 * @version: shayu3.0
 * @Description: 助眠套餐mapper
 * @author: lhh
 * @date: 2020年5月10日 上午10:30:05
 */

import com.yihe.applet.api.domain.YhSleepMusicMealApp;

import java.util.List;

public interface YhSleepMusicMealAppMapper {
	/*
	 * 查询所有助眠套餐
	 */
	public List<YhSleepMusicMealApp> selAllMusicMeal();
	/**
	 * 分类查询音乐
	 */
	public YhSleepMusicMealApp selTypeMusicMeal(String mealTitle);
}
