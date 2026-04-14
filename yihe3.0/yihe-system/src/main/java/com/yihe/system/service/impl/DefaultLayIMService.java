package com.yihe.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhUserChatRecordWed;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.layim.BigGroup;
import com.yihe.system.domain.layim.ChatRecordResult;
import com.yihe.system.domain.layim.FriendGroup;
import com.yihe.system.domain.layim.GroupMemberResult;
import com.yihe.system.domain.layim.InitResult;
import com.yihe.system.domain.layim.JsonResult;
import com.yihe.system.domain.layim.MsgRecord;
import com.yihe.system.domain.layim.User;
import com.yihe.system.domain.layim.UserFriendGroup;
import com.yihe.system.mapper.YhUserChatRecordWedMapper;
import com.yihe.system.mapper.layim.BigGroupMapper;
import com.yihe.system.mapper.layim.FriendGroupMapper;
import com.yihe.system.mapper.layim.MsgRecordMapper;
import com.yihe.system.mapper.layim.UserBigGroupMapper;
import com.yihe.system.mapper.layim.UserFriendGroupMapper;
import com.yihe.system.mapper.layim.UserMapper;
import com.yihe.system.service.LayIMService;
import com.yihe.system.utils.StringUtil;
import com.yihe.system.utils.layim.Utils;

import static com.yihe.system.domain.layim.LayIMConstants.*;

@Service
public class DefaultLayIMService implements LayIMService {

	@Autowired
	UserMapper userMapper; 
	@Autowired
	FriendGroupMapper friendGroupMapper;
	@Autowired
	BigGroupMapper bigGroupMapper; 
	@Autowired
	UserBigGroupMapper userBigGroupMapper;
	@Autowired
	UserFriendGroupMapper userFriendGroupMapper;
	@Autowired
	MsgRecordMapper msgRecordMapper;
	@Autowired
	YhUserChatRecordWedMapper YhUserChatRecordWedMapper;

	public User getByUserId(String userId) {
		return userMapper.getUser(userId);
	}

	public User findUserNameAndImg(String usercode) {
		return userMapper.findUserNameAndImg(usercode);
	}

	public JsonResult getInitResult(String userId) {

		InitResult initResult = new InitResult();
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		User findUserNameAndImg = userMapper.findUserNameAndImg(userId);
		User user = new User();
		user.setUsername(principal.getName());
		user.setId(principal.getUserCode());
		user.setSign("");
		user.setAvatar(findUserNameAndImg.getAvatar());
		initResult.setMine(user);

		List<FriendGroup> friendGroups = new ArrayList<FriendGroup>();
		;
		FriendGroup finfo = new FriendGroup();
		finfo.setGroupname("我的客户");
		finfo.setId("1");

		List<User> findUserInfoByDoctor = userMapper.findUserInfoByDoctor(userId);
		finfo.setList(findUserInfoByDoctor);
		friendGroups.add(finfo);
		initResult.setFriend(friendGroups);

		/*
		 * if (!friendGroups.isEmpty()) { List<String> friendGroupIds = friendGroups
		 * .stream() .map(x -> x.getId()) .collect(Collectors.toList());
		 * 
		 * List<UserFriendGroup> userFriendGroups =
		 * userFriendGroupMapper.getUserIdsByGroupIds(friendGroupIds);
		 * //鏍规嵁鎵�鏈夌殑鐢ㄦ埛ID鏌ヨ鍑虹敤鎴蜂俊鎭� if (!userFriendGroups.isEmpty()) { List<String>
		 * friendIds = userFriendGroups .stream() .map(x -> x.getUserId())
		 * .collect(Collectors.toList()); List<User> friends =
		 * userMapper.getUsersByIds(friendIds);
		 * 
		 * friendGroups.stream().forEach(x -> { List<String> userIdInGroup =
		 * userFriendGroups.stream().filter(u -> u.getGroupId() .equals(x.getId())
		 * ).map(m -> m.getUserId()).collect(Collectors.toList());
		 * 
		 * x.setList(friends.stream().filter(f ->
		 * userIdInGroup.contains(f.getId())).collect(Collectors.toList())); }); }
		 * 
		 * 
		 * }
		 */

		List<BigGroup> myGroups = bigGroupMapper.getGroupsByDoctor(userId);
		initResult.setGroup(myGroups);
		return JsonResult.success(initResult);

	}

	@Override
	public JsonResult getMembersByGroupId(String groupId) {
		GroupMemberResult result = new GroupMemberResult();
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		if (StringUtil.isEmpty(groupId)) {
			return null;
		}
		groupId = "f" + groupId + "_";
		List<User> findUserNameAndImgBygroup = userMapper.findUserNameAndImgBygroup(principal.getUserCode(), groupId);
		User user = new User();
		user.setUsername(principal.getName());
		user.setId(principal.getUserCode());
		user.setSign("");
		user.setAvatar("https://qny.guyijk.com/xiaochengxu/toxiangnan.png");

		result.setOwner(user);

		result.setList(findUserNameAndImgBygroup);
		return JsonResult.success(result);
	}

	@Override
	public List<String> getGroupIds(String userId) {
		return userBigGroupMapper.getGroupsByUserId(userId);
	}

	@Override
	public JsonResult addRecord(MsgRecord record) {
		/*
		 * if (record.getContents() == null || record.getContents().isEmpty()) { return
		 * JsonResult.fail("绌虹殑娑堟伅鍐呭"); }
		 * record.setCreateAt(System.currentTimeMillis());
		 * record.setRoom(Utils.generateRoomId(record.getFrom(), record.getTo(),
		 * record.getMsgType() == 2 ? CHAT_TYPE_GROUP : CHAT_TYPE_OTHER));
		 * msgRecordMapper.addRecord(record);
		 */
		return JsonResult.success();
	}

	public static void main(String[] args) {
		String str="http://qiniuyun.yhy.ren/详情规划 (1).docx)[文件测试]";

		int i = str.indexOf(")[");
		String substring = str.substring(0, i);

		System.out.println(substring);
	}

	@Override
	public JsonResult getChatRecords(String userId, String id, Integer type, Integer lastId) {

		boolean isChatGroup = type == 2;
		List<ChatRecordResult> records = new ArrayList<>(20);
		List<YhUserChatRecordWed> list = YhUserChatRecordWedMapper.getList(Utils.generateRoomId(userId, id, 1),Utils.generateRoomId(userId, id, 2),
				lastId);

		if (list.isEmpty()) {
			return JsonResult.success(records);
		}
		List<String> userIds;
		if (isChatGroup) {
			userIds = list.stream().map(x -> x.getUserCode()).distinct().collect(Collectors.toList());
		} else {
			userIds = new ArrayList<>(2);
			userIds.add(userId);
			userIds.add(id);
		}

		for (YhUserChatRecordWed record : list) {
			ChatRecordResult res = new ChatRecordResult();
			res.setId(record.getId());
			User findUserNameAndImg = userMapper.findUserNameAndImg(record.getUserCode());
			res.setAvatar(findUserNameAndImg.getAvatar());
			res.setUsername(findUserNameAndImg.getUsername());

			if(null!=record.getMessageType()){
				if(2==record.getMessageType()){
					res.setContent("img["+record.getMessage()+"]");
				}else if(1==record.getMessageType()){
					res.setContent(record.getMessage());
				}else if(3==record.getMessageType()){
					/* 找出指定的2个字符在 该字符串里面的 位置 */
					int strStartIndex = record.getMessage().indexOf("yhyfileStr/");
					int strEndIndex = record.getMessage().indexOf("yhyfileEnd/");
					int strStartIndex2 = record.getMessage().indexOf("sizeStr/");
					int strEndIndex2 = record.getMessage().indexOf("sizeEnd/");


					/* 开始截取 */
					String result = record.getMessage().substring(strStartIndex, strEndIndex).substring("yhyfileStr/".length());
					String result2 = record.getMessage().substring(strStartIndex2, strEndIndex2).substring("sizeStr/".length());
					res.setContent("file("+record.getMessage()+")"+"["+result+"]"+result2);
				}else if(4==record.getMessageType()){
					/* 找出指定的2个字符在 该字符串里面的 位置 */
					int strStartIndex = record.getMessage().indexOf("yhyfileStr/");
					int strEndIndex = record.getMessage().indexOf("yhyfileEnd/");
					int strStartIndex2 = record.getMessage().indexOf("sizeStr/");
					int strEndIndex2 = record.getMessage().indexOf("sizeEnd/");


					/* 开始截取 */
					String result = record.getMessage().substring(strStartIndex, strEndIndex).substring("yhyfileStr/".length());
					String result2 = record.getMessage().substring(strStartIndex2, strEndIndex2).substring("sizeStr/".length());
					res.setContent("video["+record.getMessage()+"]"+result+"&nbsp;&nbsp;"+result2);
				}
			}else{
				res.setContent(record.getMessage());
			}

			res.setSelf(userId.equals(record.getUserCode()));
			res.setTimestamp(record.getAddtimeinfo());
			res.setUsercode(record.getUserCode());
			res.setRoomid(record.getRoomid());
			res.setTousercode(record.getToUserCode());
			res.setMessageType(record.getMessageType());
			records.add(res);
		}
		Collections.sort(records);
		return JsonResult.success(records);

	}
}
