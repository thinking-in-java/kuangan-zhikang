/**
 * 
 */
package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhHabitPraise;
import com.yihe.applet.api.mapper.YhHabitPraiseMapper;
import com.yihe.applet.api.service.YhHabitPraiseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 习惯点赞
 * @author dong
 *
 */
@Service
public class YhHabitPraiseServiceImpl implements YhHabitPraiseService{
	  
	  @Resource
	  YhHabitPraiseMapper YhHabitPraiseMapper;
	
	@Override
	public int insertSelective(YhHabitPraise record) {
		// TODO Auto-generated method stub
		return YhHabitPraiseMapper.insertSelective(record);
	}

	@Override
	public int showCountParise(String commentid) {
		// TODO Auto-generated method stub
		return YhHabitPraiseMapper.showCountParise(commentid);
	}

	@Override
	public int showCountPariseCode(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return YhHabitPraiseMapper.showCountPariseCode(map);
	}

	@Override
	public int deleteByPrimaryKey(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return YhHabitPraiseMapper.deleteByPrimaryKey(map);
	}

	@Override
	public List<YhHabitPraise> selectCodeByCommentId(int id) {
		return YhHabitPraiseMapper.selectCodeByCommentId(id);
	}

	@Override
	public YhHabitPraise selectprId(YhHabitPraise record) {
		System.out.println("---------"+record);
		return YhHabitPraiseMapper.selectprId(record);
	}

}
