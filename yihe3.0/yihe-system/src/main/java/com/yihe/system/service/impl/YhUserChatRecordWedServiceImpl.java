package com.yihe.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserFoodDiaryVOJO;
import com.yihe.system.domain.YhUserChatRecordWed;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhWxUserWeb;
import com.yihe.system.domain.goeasyUsers;
import com.yihe.system.mapper.YhUserChatRecordWedMapper;
import com.yihe.system.mapper.YhWxUserWebMapper;
import com.yihe.system.service.YhUserChatRecordWedService;
@Service
public class YhUserChatRecordWedServiceImpl implements YhUserChatRecordWedService {
 @Autowired
 YhUserChatRecordWedMapper YhUserChatRecordWedMapper;
 
 
 @Autowired
 YhWxUserWebMapper   YhWxUserWebMapper;
 
 public goeasyUsers findDocTorInfo(){
	 Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		goeasyUsers doctor=new goeasyUsers();
		doctor.setUuid(principal.getUserCode());
		doctor.setName(principal.getName());
		doctor.setPassword(principal.getUserPwd());
		doctor.setAvatar("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
		
		 return doctor;
 }
 
 public List<goeasyUsers> findUserAllInfo(){
	 List<goeasyUsers> list=new  ArrayList<goeasyUsers>();
	 Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		goeasyUsers doctor=new goeasyUsers();
		doctor.setUuid(principal.getUserCode());
		doctor.setName(principal.getName());
		doctor.setPassword(principal.getUserPwd());
		doctor.setAvatar("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
		list.add(doctor);
		 List<YhUserChatRecordWed> findChatInfo = YhUserChatRecordWedMapper.findChatInfo(principal.getUserCode(), null, null);
		 if(null!=findChatInfo){
			 for (YhUserChatRecordWed yhUserChatRecordWed : findChatInfo) {
				 goeasyUsers user=new goeasyUsers();
				 user.setUuid(yhUserChatRecordWed.getUserCode());
				 user.setName(yhUserChatRecordWed.getName());
				 user.setPassword("");
				 YhWxUserWeb selectByPrimaryKey = YhWxUserWebMapper.selectByPrimaryKey(yhUserChatRecordWed.getUserCode());
				   if(null==selectByPrimaryKey){
					   user.setAvatar("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
				   }else{
					   user.setAvatar(selectByPrimaryKey.getHeadImg());
				   }
					list.add(user);
			}
		 }
		 return list;
 }
 

@Override
public PageInfo<YhUserChatRecordWed> findChatInfo(String usercode,Integer read,String name,Integer pageNum,Integer pageSize) {
	
	// TODO Auto-generated method stub
	PageHelper.startPage(pageNum, pageSize);
	 List<YhUserChatRecordWed> findChatInfo = YhUserChatRecordWedMapper.findChatInfo(usercode, read, name);
	return new PageInfo<>(findChatInfo);
}
public List<YhUserChatRecordWed> findChatInfoList(String doctor){
	 List<YhUserChatRecordWed> findChatInfo = YhUserChatRecordWedMapper.findChatInfo(doctor, null, null);
   if(null!=findChatInfo&&findChatInfo.size()>0){
	   int i=1;
	   for (YhUserChatRecordWed yhUserChatRecordWed : findChatInfo) {
		   String userCode = yhUserChatRecordWed.getUserCode();
		   YhWxUserWeb selectByPrimaryKey = YhWxUserWebMapper.selectByPrimaryKey(userCode);
		   if(null==selectByPrimaryKey){
			   yhUserChatRecordWed.setHeadImg("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
		   }else{
			   yhUserChatRecordWed.setHeadImg(selectByPrimaryKey.getHeadImg());
		   }
		   yhUserChatRecordWed.setIndex("person"+i);
		   i++;
	}
	   return findChatInfo;
   }
	 return findChatInfo;
}




@Override
public List<YhUserChatRecordWed> findChatInfoByUserCode(String usercode, String tousercode) {
	// TODO Auto-generated method stub
	return YhUserChatRecordWedMapper.findChatInfoByUserCode(usercode, tousercode);
}

public Map<String, Object> findAllChatInfo(String tousercode){
	List<YhUserChatRecordWed> list = YhUserChatRecordWedMapper.findChatInfo(tousercode, null, null);
	Map<String, Object> map=new HashMap<String, Object>();
	if(null!=list&&list.size()>0){
		for (int i = 0; i < list.size(); i++) {
			YhUserChatRecordWed yhUserChatRecordWed = list.get(i);
			String userCode = yhUserChatRecordWed.getUserCode();
			List<YhUserChatRecordWed> findChatInfoByUserCode = findChatInfoByUserCode(userCode,tousercode);
			map.put(userCode, findChatInfoByUserCode);
		}
		return map;
	}
	return null;
}

@Override
public int insertSelective(YhUserChatRecordWed record) {
	// TODO Auto-generated method stub
	return YhUserChatRecordWedMapper.insertSelective(record);
}

@Override
public int modifyRead1(String usercode, String tousercode) {
	// TODO Auto-generated method stub
	return YhUserChatRecordWedMapper.modifyRead1(usercode, tousercode);
}

@Override
public Integer findWXuserStatus(String usercode) {
	// TODO Auto-generated method stub 
	return YhUserChatRecordWedMapper.findWXuserStatus(usercode);
}


 
 
}
