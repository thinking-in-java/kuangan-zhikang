package com.yihe.applet.api.service.impl;


import com.yihe.applet.api.domain.YhSleepUserMusiclistApp;
import com.yihe.applet.api.mapper.YhSleepUserMusiclistAppMapper;
import com.yihe.applet.api.service.YhSleepUserMusiclistAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YhSleepUserMusiclistAppServiceImpl implements YhSleepUserMusiclistAppService {
	
	@Autowired
	private YhSleepUserMusiclistAppMapper yhSleepUserMusiclistAppMapper;
	@Override
	public YhSleepUserMusiclistApp selAidListByUserCode(String userCode) {
		// TODO Auto-generated method stub
		return yhSleepUserMusiclistAppMapper.selAidListByUserCode(userCode);
	}

	@Override
	public int addAidList(YhSleepUserMusiclistApp yhSleepUserMusiclist) {
		// TODO Auto-generated method stub
		return yhSleepUserMusiclistAppMapper.addAidList(yhSleepUserMusiclist);
	}

	@Override
	public int updAidList(YhSleepUserMusiclistApp yhSleepUserMusiclist) {
		// TODO Auto-generated method stub
		return yhSleepUserMusiclistAppMapper.updAidList(yhSleepUserMusiclist);
	}

	@Override
	public int delAidList(YhSleepUserMusiclistApp yhSleepUserMusiclist) {
		// TODO Auto-generated method stub
		return yhSleepUserMusiclistAppMapper.delAidList(yhSleepUserMusiclist);
	}

}
