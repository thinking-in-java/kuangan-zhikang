package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhJzSysSms;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhForgotPasswordMapper;
import com.yihe.system.service.YhForgotPasswordService;
@Service
public class YhForgotPasswordServiceImpl implements  YhForgotPasswordService{
	@Autowired
	YhForgotPasswordMapper yhforgotpasswordmapper;
	@Override
	public int selectcount(String name, String phone) {
		// TODO Auto-generated method stub
		return yhforgotpasswordmapper.selectcount(name, phone);
	}
	@Override
	public int updatepwd(YhUserReginfo regin) {
		// TODO Auto-generated method stub
		return yhforgotpasswordmapper.updatepwd(regin);
	}
	@Override
	public YhJzSysSms sms() {
		// TODO Auto-generated method stub
		return yhforgotpasswordmapper.sms();
	}
	

}
