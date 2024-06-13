package com.yihe.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhSystemUpdate;
import com.yihe.system.mapper.YhSystemUpdateMapper;
import com.yihe.system.service.YhSystemUpdateService;
@Service
public class YhSystemUpdateServiceImpl implements YhSystemUpdateService{
	@Autowired
	YhSystemUpdateMapper updatemapper;
	@Override
	public YhSystemUpdate selectsystemupdate() {
		// TODO Auto-generated method stub
		return updatemapper.selectsystemupdate();
	}
	@Override
	public int updatedate(YhSystemUpdate yhsystemupdate) {
		// TODO Auto-generated method stub
		if(yhsystemupdate.getUpdate_id()==null) {
			return	updatemapper.insertupdate(yhsystemupdate);
		}else {
			return updatemapper.updatedate(yhsystemupdate);
		}
	}
	@Override
	public int updatechexiao(YhSystemUpdate yhsystemupdate) {
		// TODO Auto-generated method stub
		return updatemapper.updatechexiao(yhsystemupdate);
	}
	@Override
	public int updatefabu(YhSystemUpdate yhsystemupdate) {
		// TODO Auto-generated method stub
		if(yhsystemupdate.getUpdate_id()==null) {
			return	updatemapper.insertupdatefabu(yhsystemupdate);
		}else {
			return updatemapper.updatedatefabu(yhsystemupdate);
		}
	}

}
