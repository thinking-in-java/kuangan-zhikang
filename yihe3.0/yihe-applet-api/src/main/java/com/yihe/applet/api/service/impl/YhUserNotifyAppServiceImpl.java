package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhUserNotifyApp;
import com.yihe.applet.api.mapper.YhUserNotifyAppMapper;
import com.yihe.applet.api.service.YhUserNotifyAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class YhUserNotifyAppServiceImpl implements YhUserNotifyAppService{
	@Autowired
	YhUserNotifyAppMapper yhUserNotifyAppMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return yhUserNotifyAppMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(YhUserNotifyApp record) {
		// TODO Auto-generated method stub
		record.setSendDate(new Date());
		String title = record.getTitle();
		record.setContent("您的评估报告，已生成立即查看详情>>");
		//yhUserNotify.setType(Boolean.valueOf("1"));
		record.setTyepe(1);
		if(title == "运动报告") {
			record.setTitle("运动报告");
		}
		if(title == "膳食报告") {
			record.setTitle("膳食报告");
		}
		if(title == "习惯报告") {
			record.setTitle("习惯报告");
		}
		if(title == "睡眠报告") {
			record.setTitle("睡眠报告");
		}
		return yhUserNotifyAppMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(YhUserNotifyApp record) {
		// TODO Auto-generated method stub
		return yhUserNotifyAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<YhUserNotifyApp> showInteraction(String userId, Integer type) {
		// TODO Auto-generated method stub
		return yhUserNotifyAppMapper.showInteraction(userId, type);
	}

	@Override
	public List<YhUserNotifyApp> showNotifyType(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhUserNotifyAppMapper.showNotifyType(map);
	}

	@Override
	public List<YhUserNotifyApp> selectAwardInform(String userId, Integer type) {
		// TODO Auto-generated method stub
		return yhUserNotifyAppMapper.selectAwardInform(userId, type);
	}

	@Override
	public int deleteByid(Long id) {
		// TODO Auto-generated method stub
		return yhUserNotifyAppMapper.deleteByid(id);
	}

	@Override
	public int selectIsRead(Integer isRead, String userId) {
		// TODO Auto-generated method stub
		return yhUserNotifyAppMapper.selectIsRead(isRead, userId);
	}

	@Override
	public int insert(YhUserNotifyApp record) {
		// TODO Auto-generated method stub
		return yhUserNotifyAppMapper.insert(record);
	}

	@Override
	public List<YhUserNotifyApp> selectcomment(String userId) {
		// TODO Auto-generated method stub
		return yhUserNotifyAppMapper.selectcomment(userId);
	}

	@Override
	public List<YhUserNotifyApp> selecthabit(String userId) {
		return yhUserNotifyAppMapper.selecthabit(userId);
	}

	@Override
	public List<YhUserNotifyApp> selectdianzan(String userId) {
		return yhUserNotifyAppMapper.selectdianzan(userId);
	}
}
