package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhQuestionCheckJson2Web;
import com.yihe.system.mapper.YhQuestionCheckJson2WebMapper;
import com.yihe.system.service.YhQuestionCheckJson2WebService;
@Service
public class YhQuestionCheckJson2WebServiceImpl implements YhQuestionCheckJson2WebService{
	@Autowired
	YhQuestionCheckJson2WebMapper yhQuestionCheckJson2WebMapper;
	@Override
	public YhQuestionCheckJson2Web selectUserByParam(String userCode) {
		// TODO Auto-generated method stub
		return yhQuestionCheckJson2WebMapper.selectUserByParam(userCode);
	}
 
}
