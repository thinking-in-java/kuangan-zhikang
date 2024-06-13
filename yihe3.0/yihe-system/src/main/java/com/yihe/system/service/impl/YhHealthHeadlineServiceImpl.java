/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhHealthHeadline;
import com.yihe.system.mapper.YhHealthHeadlineMapper;
import com.yihe.system.service.YhHealthHeadlineService;

/**
 * @author Administrator
 *
 */
@Service
public class YhHealthHeadlineServiceImpl implements YhHealthHeadlineService{
	
	@Autowired
	YhHealthHeadlineMapper yhHealthHeadlineMapper;

	@Override
	public YhHealthHeadline selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhHealthHeadlineMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<YhHealthHeadline> selectByExample() {
		// TODO Auto-generated method stub
		return yhHealthHeadlineMapper.selectByExample();
	}
}
