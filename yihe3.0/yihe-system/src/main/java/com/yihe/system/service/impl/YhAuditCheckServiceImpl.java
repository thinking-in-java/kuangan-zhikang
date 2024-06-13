/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhAuditCheck;
import com.yihe.system.mapper.YhAuditCheckMapper;
import com.yihe.system.service.YhAuditCheckService;

/**
 * @author dong
 *
 */
@Service
public class YhAuditCheckServiceImpl implements YhAuditCheckService{

	  @Autowired
	  YhAuditCheckMapper yhAuditCheckMapper;

	@Override
	public List<YhAuditCheck> selectaudit(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhAuditCheckMapper.selectaudit(map);
	}

	@Override
	public int selectauditConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhAuditCheckMapper.selectauditConut(map);
	}

	@Override
	public int updateByPrimaryKeySelective(YhAuditCheck record) {
		// TODO Auto-generated method stub
		return yhAuditCheckMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int showfoodcount() {
		// TODO Auto-generated method stub
		return yhAuditCheckMapper.showfoodcount();
	}

	@Override
	public int showfoodcountDispose() {
		// TODO Auto-generated method stub
		return yhAuditCheckMapper.showfoodcountDispose();
	}

	@Override
	public int showExercisecount() {
		// TODO Auto-generated method stub
		return yhAuditCheckMapper.showExercisecount();
	}

	@Override
	public int showExercisecountDispose() {
		// TODO Auto-generated method stub
		return yhAuditCheckMapper.showExercisecountDispose();
	}
}
