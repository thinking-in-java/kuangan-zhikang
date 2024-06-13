package com.yihe.system.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhHeThreshold2Web;
import com.yihe.system.mapper.YhHeThreshold2WebMapper;
import com.yihe.system.service.YhHeThreshold2WebService;
@Service
public class YhHeThreshold2WebServiceImpl implements YhHeThreshold2WebService{
	@Autowired
	YhHeThreshold2WebMapper yhHeThreshold2WebMapper;
	@Override
	public YhHeThreshold2Web selectUserYhHeThreshold2(String userCode) {
		// TODO Auto-generated method stub
		return yhHeThreshold2WebMapper.selectUserYhHeThreshold2(userCode);
	}
	@Override
	public int updateByPrimaryKeySelective(YhHeThreshold2Web record) {
		// TODO Auto-generated method stub
		return yhHeThreshold2WebMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int insertSelective(YhHeThreshold2Web record) {
		// TODO Auto-generated method stub
		return yhHeThreshold2WebMapper.insertSelective(record);
	}
	@Override
	public int deleteByPrimaryKey(String userCode) {
		// TODO Auto-generated method stub
		return yhHeThreshold2WebMapper.deleteByPrimaryKey(userCode);
	}

}
