package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhSleepMusicMealApp;
import com.yihe.applet.api.mapper.YhSleepMusicMealAppMapper;
import com.yihe.applet.api.service.YhSleepMusicMealAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version: shayu3.0
 * @Description: 助眠音乐实现层
 * @author: lhh
 * @date: 2020年5月10日 上午10:32:05
 */
@Service
public class YhSleepMusicMealAppServiceImpl implements YhSleepMusicMealAppService{
	@Autowired
	private YhSleepMusicMealAppMapper yhSleepMusicMealAppMapper;
	@Override
	public List<YhSleepMusicMealApp> selAllMusicMeal() {
		// TODO Auto-generated method stub
		return yhSleepMusicMealAppMapper.selAllMusicMeal();
	}
	@Override
	public YhSleepMusicMealApp selTypeMusicMeal(String mealTitle) {
		// TODO Auto-generated method stub
		return yhSleepMusicMealAppMapper.selTypeMusicMeal(mealTitle);
	}
	
}
