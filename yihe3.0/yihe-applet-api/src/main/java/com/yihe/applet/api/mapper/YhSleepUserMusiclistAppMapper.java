package com.yihe.applet.api.mapper;
/**
 * @version: shayu3.0
 * @Description:用户音乐列表mapper 
 * @author: lhh
 * @date: 2020年5月9日 下午2:11:22
 */


import com.yihe.applet.api.domain.YhSleepUserMusiclistApp;
import org.apache.ibatis.annotations.Param;

public interface YhSleepUserMusiclistAppMapper {
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
