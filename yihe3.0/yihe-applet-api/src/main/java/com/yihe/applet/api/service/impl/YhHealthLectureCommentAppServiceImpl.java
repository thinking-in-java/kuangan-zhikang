package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.controller.YhLifeHabitPersonDictAppController;
import com.yihe.applet.api.domain.YhHealthLectureCommentApp;
import com.yihe.applet.api.domain.YhLifeHabitPersonDictApp;
import com.yihe.applet.api.mapper.YhHealthLectureCommentAppMapper;
import com.yihe.applet.api.service.YhHealthLectureCommentAppService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class YhHealthLectureCommentAppServiceImpl implements YhHealthLectureCommentAppService{
	@Autowired
	YhHealthLectureCommentAppMapper  yhHealthLectureCommentAppMapper;

	@Resource
	private YhLifeHabitPersonDictAppController yhLifeHabitPersonDictAppController;

	@Override
	public List<YhHealthLectureCommentApp> findCommentByPid(Integer pid) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentAppMapper.findCommentByPid(pid);
	}

	@Override
	public List<YhHealthLectureCommentApp> selectByPrimaryKeyByPid(Integer pid) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentAppMapper.selectByPrimaryKeyByPid(pid);
	}

	@Override
	public List<YhHealthLectureCommentApp> selectAllparam(Integer categoryId) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentAppMapper.selectAllparam(categoryId);
	}

	@Override
	public int insertSelective(YhHealthLectureCommentApp record) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentAppMapper.insertSelective(record);
	}

	@Override
	public YhHealthLectureCommentApp ifclock(YhHealthLectureCommentApp record) {
		return yhHealthLectureCommentAppMapper.ifclock(record);
	}

	@Override
	public int updClock(YhHealthLectureCommentApp record) {
		return yhHealthLectureCommentAppMapper.updClock(record);
	}

	@Override
	public YhHealthLectureCommentApp findCommentPuserCodeByParam(Integer pid) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentAppMapper.findCommentPuserCodeByParam(pid);
	}

	@Override
	public YhHealthLectureCommentApp selecTcontAndName(Integer commentid) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentAppMapper.selecTcontAndName(commentid);
	}

	@Override
	public List<YhHealthLectureCommentApp> findCommentByPid2(Integer pid) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentAppMapper.findCommentByPid2(pid);
	}

	@Override
	public List<YhHealthLectureCommentApp> findCommentByPid1(Integer pid) {
		return yhHealthLectureCommentAppMapper.findCommentByPid1(pid);
	}

	@Override
	public List<YhHealthLectureCommentApp> findCommentBylId1(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentAppMapper.findCommentBylId1(map);
	}

	@Override
	public List<YhHealthLectureCommentApp> findCommentBylId(Integer lid, Integer status) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentAppMapper.findCommentBylId(lid, status);
	}

	@Override
	public int updateSelective(YhHealthLectureCommentApp record) {
		// TODO Auto-generated method stub
		return yhHealthLectureCommentAppMapper.updateSelective(record);
	}

	@Override
	public Boolean updateTotalDay(@Param("userCode") String userCode, @Param("habitId") Integer habitId) {
		//任意健康习惯当天打卡后不再进行次数累加
		if (yhHealthLectureCommentAppMapper.selectCurrentDay(userCode, habitId) > 0) return false;
		//先查询出本来累计天数
		YhLifeHabitPersonDictApp yhLifeHabitPersonDictApp = yhHealthLectureCommentAppMapper.selectTotalDay(userCode, habitId);
		if(null == yhLifeHabitPersonDictApp){
			yhLifeHabitPersonDictAppController.addhabit(userCode,habitId,null,null,null);
		}
		yhLifeHabitPersonDictApp = yhHealthLectureCommentAppMapper.selectTotalDay(userCode, habitId);
		Integer totalDay = yhLifeHabitPersonDictApp.getTotalDay()+1;

		return yhHealthLectureCommentAppMapper.updateTotalDay(userCode, habitId, totalDay) == 1;
	}
}
