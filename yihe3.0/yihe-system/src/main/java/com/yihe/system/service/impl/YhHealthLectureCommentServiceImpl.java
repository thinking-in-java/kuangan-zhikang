package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhHealthLectureComment;
import com.yihe.system.mapper.YhHealthLectureCommentMapper;
import com.yihe.system.service.YhHealthLectureCommentService;

/**
* YhHealthLectureCommentService实现
* Created by dong on 2020/3/28.
*/
@Service
@Transactional
public class YhHealthLectureCommentServiceImpl implements YhHealthLectureCommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhHealthLectureCommentServiceImpl.class);

    @Autowired
    YhHealthLectureCommentMapper yhHealthLectureCommentMapper;

	@Override
	public int selectByCount(int lectureinfoid) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentMapper.selectByCount(lectureinfoid);
	}

	@Override
	public List<YhHealthLectureComment> selectComment(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentMapper.selectComment(map);
	}

	@Override
	public int selectCommentConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentMapper.selectCommentConut(map);
	}

	@Override
	public List<YhHealthLectureComment> selectsecondComment(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentMapper.selectsecondComment(map);
	}

	@Override
	public int selectsecondCommentConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentMapper.selectsecondCommentConut(map);
	}

	@Override
	public int findCommentByUserCodeForCount(String usercode) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentMapper.findCommentByUserCodeForCount(usercode);
	}

	@Override
	public int selectnumber(int pid) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentMapper.selectnumber(pid);
	}

	@Override
	public int insertSelective(YhHealthLectureComment record) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKeyPid(int pid) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentMapper.deleteByPrimaryKeyPid(pid);
	}

	@Override
	public int deleteByPrimaryKey(Integer commentid) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentMapper.deleteByPrimaryKey(commentid);
	}

	@Override
	public List<YhHealthLectureComment> showHabitExport(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentMapper.showHabitExport(map);
	}

}