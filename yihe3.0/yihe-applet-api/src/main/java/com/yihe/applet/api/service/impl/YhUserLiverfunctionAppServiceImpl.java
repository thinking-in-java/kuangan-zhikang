package com.yihe.applet.api.service.impl;
 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.applet.api.domain.YhUserLiverfunctionApp;
import com.yihe.applet.api.mapper.YhUserLiverfunctionAppMapper;
import com.yihe.applet.api.service.YhUserLiverfunctionAppService;

/**
* YhUserLiverfunctionService实现
* Created by dyb on 2019/12/24.
*/
@Service
public class YhUserLiverfunctionAppServiceImpl  implements YhUserLiverfunctionAppService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserLiverfunctionAppServiceImpl.class);

    @Autowired
    YhUserLiverfunctionAppMapper yhUserLiverfunctionMapper;

	@Override
	public List<YhUserLiverfunctionApp> selectByPrimaryKeyBYeid(String eid) {
		// TODO Auto-generated method stub
		return yhUserLiverfunctionMapper.selectByPrimaryKeyBYeid(eid);
	}

	@Override
	public YhUserLiverfunctionApp selectByPrimaryKeyNewest(String eid) {
		// TODO Auto-generated method stub
		return yhUserLiverfunctionMapper.selectByPrimaryKeyNewest(eid);
	}

	@Override
	public List<YhUserLiverfunctionApp> selectByPrimaryKeyFindEntryTime(String E_User_Id) {
		// TODO Auto-generated method stub
		return yhUserLiverfunctionMapper.selectByPrimaryKeyFindEntryTime(E_User_Id);
	}

	@Override
	public List<YhUserLiverfunctionApp> selectByPrimaryKeyFindEntryTimeAnduserid(String time, String usercode) {
		// TODO Auto-generated method stub
		return yhUserLiverfunctionMapper.selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
	}

	@Override
	public List<YhUserLiverfunctionApp> findLiverAllByUserCode(String usercode,Integer timetype,String strTime,String endTime) {
		return yhUserLiverfunctionMapper.findLiverAllByUserCode(usercode, timetype, strTime, endTime);
	}

	@Override
	public List<YhUserLiverfunctionApp> findLiverByTimeType(String usercode, Integer timetype, String strTime, String endTime) {
		return yhUserLiverfunctionMapper.findLiverByTimeType(usercode, timetype, strTime, endTime);
	}

	@Override
	public int deleteByPrimaryKey(Integer liverid) {
		// TODO Auto-generated method stub
		return yhUserLiverfunctionMapper.deleteByPrimaryKey(liverid);
	}

	@Override
	public int insertSelective(YhUserLiverfunctionApp record) {
		// TODO Auto-generated method stub
		return yhUserLiverfunctionMapper.insertSelective(record);
	}

	@Override
	public YhUserLiverfunctionApp selectByPrimaryKey(Integer liverid) {
		// TODO Auto-generated method stub
		return yhUserLiverfunctionMapper.selectByPrimaryKey(liverid);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserLiverfunctionApp record) {
		// TODO Auto-generated method stub
		return yhUserLiverfunctionMapper.updateByPrimaryKeySelective(record);
	}

}