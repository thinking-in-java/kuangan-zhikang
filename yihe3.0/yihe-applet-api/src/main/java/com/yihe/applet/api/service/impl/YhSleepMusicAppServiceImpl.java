package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhSleepMusicApp;
import com.yihe.applet.api.mapper.YhSleepMusicAppMapper;
import com.yihe.applet.api.service.YhSleepMusicAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version: shayu3.0
 * @Description: 睡眠助眠service实现层
 * @author: lhh
 * @date: 2020年4月15日 下午5:08:19
 */
@Service
@Transactional
public class YhSleepMusicAppServiceImpl implements YhSleepMusicAppService {
	@Autowired
	private YhSleepMusicAppMapper yhSleepMusicAppMapper;
	@Override
	public List<YhSleepMusicApp> selAllMusic() {
		// TODO Auto-generated method stub
		return yhSleepMusicAppMapper.selAllMusic();
	}
	@Override
	public YhSleepMusicApp selMusicById(int musicId) {
		// TODO Auto-generated method stub
		return yhSleepMusicAppMapper.selMusicById(musicId);
	}
	@Override
	public int uptMusicListenById(int musicId) {
		// TODO Auto-generated method stub
		return yhSleepMusicAppMapper.uptMusicListenById(musicId);
	}
	@Override
	public List<YhSleepMusicApp> selAllMusicByIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		return yhSleepMusicAppMapper.selAllMusicByIds(ids);
	}
	

}
