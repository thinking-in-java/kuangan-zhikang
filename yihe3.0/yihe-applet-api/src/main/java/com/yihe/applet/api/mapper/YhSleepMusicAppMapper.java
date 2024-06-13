package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhSleepMusicApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @version: shayu3.0
 * @Description: 睡眠音乐mapper
 * @author: lhh
 * @date: 2020年4月15日 下午4:44:52
 */
public interface YhSleepMusicAppMapper {
	/*
	 * 获取所有音乐
	 */
	public List<YhSleepMusicApp> selAllMusic();
	/*
	 * 根据id查询音乐
	 */
	public YhSleepMusicApp selMusicById(@Param("musicId") int musicId);
	/*
	 * 听完音乐修改已听人数musicListennum+1
	 */
	public int uptMusicListenById(@Param("musicId") int musicId);
	
	public List<YhSleepMusicApp> selAllMusicByIds(@Param("idsList") List<Integer> ids);

}
