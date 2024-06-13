package com.yihe.applet.api.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserChatRecord;
import com.yihe.applet.api.mapper.YhUserChatRecordMapper;
import com.yihe.applet.api.service.YhUserChatRecordService;
@Service
public class YhUserChatRecordServiceImpl implements YhUserChatRecordService {
	@Autowired
	 private YhUserChatRecordMapper  YhUserChatRecordMapper;
	

	@Override
	public int insertSelective(YhUserChatRecord record) {
		// TODO Auto-generated method stub
		return YhUserChatRecordMapper.insertSelective(record);
	}

	@Override
	public int modifyRead1(String usercode, String tousercode) {
		// TODO Auto-generated method stub
		return YhUserChatRecordMapper.modifyRead1(usercode, tousercode);
	}

	@Override
	public String findDoctorIdByUserCode(String usercode) {
		// TODO Auto-generated method stub
		return YhUserChatRecordMapper.findDoctorIdByUserCode(usercode);
	}

	@Override
	public List<YhUserChatRecord> findInfoList(List<String> id) {
		// TODO Auto-generated method stub
		return YhUserChatRecordMapper.findInfoList(id);
	}

	@Override
	public String findUserGroupByInfo(String usercode) {
		// TODO Auto-generated method stub
		return YhUserChatRecordMapper.findUserGroupByInfo(usercode);
	}

}
