package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.domain.YhWxUser;
import com.yihe.applet.api.service.YhWxUserAppService;
@Service
public class YhWxUserAppServiceImpl implements YhWxUserAppService {
	@Autowired
	com.yihe.applet.api.mapper.YhWxUserAppMapper YhWxUserAppMapper;

	@Override
	public int deleteByPrimaryKey(String userId, String oppenId) {
		// TODO Auto-generated method stub
		return YhWxUserAppMapper.deleteByPrimaryKey(userId, oppenId);
	}

	@Override
	public int insert(YhWxUser record) {
		// TODO Auto-generated method stub
		return YhWxUserAppMapper.insert(record);
	}

	@Override
	public int insertSelective(YhWxUser record) {
		// TODO Auto-generated method stub
		return YhWxUserAppMapper.insertSelective(record);
	}

	@Override
	public YhWxUser selectByPrimaryKey(String userId, String oppenId) {
		// TODO Auto-generated method stub
		return YhWxUserAppMapper.selectByPrimaryKey(userId, oppenId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhWxUser record) {
		// TODO Auto-generated method stub
		return YhWxUserAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhWxUser record) {
		// TODO Auto-generated method stub
		return YhWxUserAppMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<YhWxUser> selectByUsercode(String usercode) {
		// TODO Auto-generated method stub
		return YhWxUserAppMapper.selectByUsercode(usercode);
	}

	@Override
	public YhWxUser selectHeadImgbyParam(String userId) {
		// TODO Auto-generated method stub
		return YhWxUserAppMapper.selectHeadImgbyParam(userId);
	}

	@Override
	public List<YhUserReginfoApp> selectRandomheandImg(int num) {
		// TODO Auto-generated method stub
		return YhWxUserAppMapper.selectRandomheandImg(num);
	}

	@Override
	public int modifyWXuserStatusInfo(String usercode, int status) {
		// TODO Auto-generated method stub
		return YhWxUserAppMapper.modifyWXuserStatusInfo(usercode, status);
	}

}
