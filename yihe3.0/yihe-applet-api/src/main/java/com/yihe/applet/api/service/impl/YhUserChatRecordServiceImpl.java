package com.yihe.applet.api.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.yihe.applet.api.domain.DoctorXcxHomePageChat;
import com.yihe.applet.api.domain.YhUserChatRecordPayloadVOJO;
import com.yihe.applet.api.domain.YhUserChatRecordVOJO;
import com.yihe.common.config.Global;
import com.yihe.framework.manager.AsyncManager;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.utils.Data;
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

	@Autowired
	YhUserReginfoService yhUserReginfoService;
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
	public List<YhUserChatRecord> findInfoList(List<String> id, int pagenum, int pagesize) {
		// TODO Auto-generated method stub
		return YhUserChatRecordMapper.findInfoList(id, pagenum, pagesize);
	}

	@Override
	public String findUserGroupByInfo(String usercode) {
		// TODO Auto-generated method stub
		return YhUserChatRecordMapper.findUserGroupByInfo(usercode);
	}

	@Override
	public List<DoctorXcxHomePageChat> latestChat(String doctor, int limit) {
		final List<DoctorXcxHomePageChat> doctorXcxHomePageChats = YhUserChatRecordMapper.latestChat(doctor, limit);
		for (int i = 0; i < doctorXcxHomePageChats.size(); i++) {
			doctorXcxHomePageChats.get(i).setAddTimeInfo(Data.CalculateTime(doctorXcxHomePageChats.get(i).getAddTimeInfo()));
		}
		return doctorXcxHomePageChats;
	}

	@Override
	public int doctorUnreadNum(String doctor) {
		return YhUserChatRecordMapper.doctorUnreadNum(doctor);
	}

	@Override
	public List<YhUserChatRecordVOJO> getChatInfo(List<String> id, int pagenum, int pagesize) {

		List<YhUserChatRecord> infoList = findInfoList(id, (pagenum - 1) * pagesize, pagesize);
		List<YhUserChatRecordVOJO> list = new ArrayList<>();
		if (null != infoList) {
			for (int i = 0; i < infoList.size(); i++) {
				YhUserChatRecordVOJO info = new YhUserChatRecordVOJO();
				info.setMessageId(String.valueOf(infoList.get(i).getId()));
				info.setType(String.valueOf(infoList.get(i).getMessageType()));
				info.setRead(infoList.get(i).getRead() == 1);
				info.setSenderId(Global.getName() + infoList.get(i).getUserCode());
				info.setReceiverId(Global.getName() + infoList.get(i).getToUserCode());
				YhUserChatRecordPayloadVOJO pay = new YhUserChatRecordPayloadVOJO();
				//1文字2图片3文件4视频
				switch (infoList.get(i).getMessageType()) {
					case 1:
						pay.setText(infoList.get(i).getMessage());
						break;
					case 2:
						pay.setImage(infoList.get(i).getMessage());
						break;
					case 3:
					case 4:
						pay.setFile(infoList.get(i).getMessage());
						pay.setFilename(infoList.get(i).getFilename());
						pay.setFilesize(infoList.get(i).getFilesize());
						break;
				}
				info.setPayload(pay);
				info.setTimestamp(Data.gettimeStemp(infoList.get(i).getAddtimeinfo(), ""));
				list.add(info);
			}
		}
		return list;
	}

	@Override
	public int addChatMessage(YhUserChatRecord record) {
		String usercode = record.getUserCode().replace(Global.getName(), "");
		String tousercode = record.getToUserCode().replace(Global.getName(), "");
		String rooomid = "";
		YhUserReginfo yhUserReginfo = yhUserReginfoService.selectByPrimaryKey(usercode);
		if (null == yhUserReginfo) {
			return 0;
		}
		Integer userType = yhUserReginfo.getUserType();
		//是用户
		if (userType == 5) {
			rooomid = tousercode + usercode;
		} else {
			rooomid = usercode + tousercode;
		}
		record.setRoomid(rooomid);
		record.setUserCode(usercode);
		record.setToUserCode(tousercode);
		record.setAddtimeinfo(Data.Date2Str(new Date()));
		final int i = YhUserChatRecordMapper.insertSelective(record);

		//只接收私聊
		/*if (1 == record.getMtype()) {
			//异步发送离线消息
			AsyncManager.me().execute(new TimerTask() {
				@Override
				public void run() {
					try {
						final String s = sendMessage(usercode, tousercode, record);
						System.out.println(s);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			});
		}*/

		return i;
	}


}
