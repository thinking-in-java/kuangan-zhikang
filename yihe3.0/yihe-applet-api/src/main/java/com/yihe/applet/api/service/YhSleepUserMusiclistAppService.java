package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhSleepUserMusiclistApp;
import org.apache.ibatis.annotations.Param;

/**
 * @version: shayu3.0
 * @Description: 用户助眠音乐列表service
 * @author: lhh
 * @date: 2020年5月9日 下午3:09:42
 */
public interface YhSleepUserMusiclistAppService {
	/*
	 * 根据用户code查询音乐列表
	 */
	public YhSleepUserMusiclistApp selAidListByUserCode(@Param("userCode") String userCode);
	/*
	 * 添加用户音乐列表
	 */
	public int addAidList(YhSleepUserMusiclistApp yhSleepUserMusiclist);
	/*
	 * 修改用户音乐列表
	 */
	public int updAidList(YhSleepUserMusiclistApp yhSleepUserMusiclist);
	/*
	 * 清空用户音乐列表
	 */
	public int delAidList(YhSleepUserMusiclistApp yhSleepUserMusiclist);
}
