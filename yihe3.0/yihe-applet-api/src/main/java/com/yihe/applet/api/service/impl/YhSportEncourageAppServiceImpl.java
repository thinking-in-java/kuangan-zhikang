/**
 * 
 */
package com.yihe.applet.api.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhSportEncourageApp;
import com.yihe.applet.api.domain.YhUserSportChallengeApp;
import com.yihe.applet.api.mapper.YhSportEncourageAppMapper;
import com.yihe.applet.api.mapper.YhUserSportChallengeAppMapper;
import com.yihe.applet.api.service.YhSportEncourageAppService;

/**
 * @author dong
 *
 */
@Service
public class YhSportEncourageAppServiceImpl implements YhSportEncourageAppService{

	@Autowired
	YhUserSportChallengeAppMapper yhUserSportChallengeAppMapper;
	
	@Autowired
	YhSportEncourageAppMapper yhSportEncourageAppMapper;

	@Override
	public List<YhUserSportChallengeApp> selectByDnow(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhUserSportChallengeAppMapper.selectByDnow(map);
	}

	@Override
	public int showEncourageNum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSportEncourageAppMapper.showEncourageNum(map);
	}

	@Override
	public List<YhSportEncourageApp> selectByChidSall(int chId) {
		// TODO Auto-generated method stub
		return yhSportEncourageAppMapper.selectByChidSall(chId);
	}

	@Override
	public int insertSelective(YhSportEncourageApp record) {
		// TODO Auto-generated method stub
		return yhSportEncourageAppMapper.insertSelective(record);
	}
	
}
