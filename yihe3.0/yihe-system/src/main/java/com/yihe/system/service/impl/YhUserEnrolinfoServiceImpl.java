package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserEnrolinfoExample;
import com.yihe.system.mapper.YhUserEnrolinfoMapper;
import com.yihe.system.service.YhUserEnrolinfoService;

/**
* YhUserEnrolinfoService实现
* Created by dyb on 2020/3/19.
*/
@Service
public class YhUserEnrolinfoServiceImpl implements YhUserEnrolinfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserEnrolinfoServiceImpl.class);

    @Autowired
    YhUserEnrolinfoMapper yhUserEnrolinfoMapper;

	@Override
	public int updateByExampleByusercodeForEval(YhUserEnrolinfo record) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.updateByExampleByusercodeForEval(record);
	}

	@Override
	public long countByExample(YhUserEnrolinfoExample example) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(YhUserEnrolinfoExample example) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer eUserId) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.deleteByPrimaryKey(eUserId);
	}

	@Override
	public int insert(YhUserEnrolinfo record) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.insert(record);
	}

	@Override
	public int insertSelective(YhUserEnrolinfo record) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.insertSelective(record);
	}

	@Override
	public List<YhUserEnrolinfo> selectByExample(YhUserEnrolinfoExample example) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.selectByExample(example);
	}

	@Override
	public YhUserEnrolinfo selectByPrimaryKey(Integer eUserId) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.selectByPrimaryKey(eUserId);
	}

	@Override
	public int updateByExampleSelective(YhUserEnrolinfo record, YhUserEnrolinfoExample example) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(YhUserEnrolinfo record, YhUserEnrolinfoExample example) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserEnrolinfo record) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhUserEnrolinfo record) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public YhUserEnrolinfo selectByPrimaryKeyByUsercode(String usercode) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.selectByPrimaryKeyByUsercode(usercode);
	}

	@Override
	public int modifyInfoByEva(YhUserEnrolinfo record) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.modifyInfoByEva(record);
	}

	@Override
	public int updateByExampleSelectiveByusercode(Map<String, String> map) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.updateByExampleSelectiveByusercode(map);
	}

	@Override
	public List<YhUserEnrolinfo> findUserEnrolinfoByDoctorInfo(String doctor,String ginfo) {
		// TODO Auto-generated method stub
		return yhUserEnrolinfoMapper.findUserEnrolinfoByDoctorInfo(doctor,ginfo);
	}

}