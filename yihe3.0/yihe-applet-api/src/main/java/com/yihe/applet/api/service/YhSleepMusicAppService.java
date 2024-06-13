package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhSleepMusicApp;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * @version: shayu3.0
 * @Description: 睡眠助眠音service
 * @author: lhh
 * @date: 2020年4月15日 下午5:04:59
 */
public interface YhSleepMusicAppService {
	/*
	 * 获取所有音乐
	 */
	public List<YhSleepMusicApp> selAllMusic();
	/*
	 * 根据id查询音乐
	 */
	public YhSleepMusicApp selMusicById(int musicId);
	/*
	 * 听完音乐修改已听人数musicListennum+1
	 */
	public int uptMusicListenById(int musicId);
	
	public List<YhSleepMusicApp> selAllMusicByIds( List<Integer> ids);
}
