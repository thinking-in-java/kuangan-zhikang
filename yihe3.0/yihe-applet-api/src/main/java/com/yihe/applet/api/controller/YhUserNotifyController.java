package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhHabitPraise;
import com.yihe.applet.api.domain.YhHealthLectureCommentApp;
import com.yihe.applet.api.domain.YhUserNotifyApp;
import com.yihe.applet.api.service.YhHabitPraiseService;
import com.yihe.applet.api.service.YhHealthLectureCommentAppService;
import com.yihe.applet.api.service.YhUserNotifyAppService;
import com.yihe.applet.api.service.YhWxUserAppService;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * YhUserNotifycontroller
 * Created by yx on 2020/2/27.
 */
@Controller
@RequestMapping("/api/YhUserNotify")
@Api(value = "YhUserNotify控制器", description = "YhUserNotify管理")
public class YhUserNotifyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhUserNotifyController.class);
//	@Autowired
//	YhHealthLectureCommentAppService yhHealthLectureCommentService;

    @Autowired
    YhUserNotifyAppService yhUserNotifyService;
    @Autowired
    YhHealthLectureCommentAppService yhHealthLectureCommentAppService;
    @Autowired
    YhWxUserAppService YhWxUserService;
    @Autowired
    YhUserReginfoService YhUserReginfoService;
    @Autowired
	YhHabitPraiseService yhHabitPraiseService;
    /**
     * 根据类型查询消息 
     * @param usercode
     * @param type
     * @return
     */
	@RequestMapping(value = "/showNotifyType")
	@ResponseBody
	public Result showNotifyType(String usercode,int type) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("userId", usercode);
		map.put("type", type);
		List<YhUserNotifyApp> list=yhUserNotifyService.showNotifyType(map);
		
	return Result.ok(list);
	}
	
	
	/**
	 * 删除消息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteNotifyType")
	@ResponseBody
	public Result deleteNotifyType(int id) {
		int i=yhUserNotifyService.deleteByid(Long.valueOf(id));
	return Result.ok(i);
	}
	/**
	 * 查询消息互动
	 * @param userId
	 * @param type  
	 * @return
	 */
	@RequestMapping(value = "/showInteraction")
	@ResponseBody
	public Result showInteraction(String userId,Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		//查询站内信
		List<YhUserNotifyApp> listMaps = yhUserNotifyService.showInteraction(userId, type);

		//查询课堂评论
		List<YhUserNotifyApp> selectcomment = yhUserNotifyService.selectcomment(userId);
		List<YhHealthLectureCommentApp> comList2=new ArrayList<YhHealthLectureCommentApp>();
		for (int i = 0; i < selectcomment.size(); i++) {
			Integer pid = selectcomment.get(i).getNewsId();
			List<YhHealthLectureCommentApp> comList = yhHealthLectureCommentAppService.findCommentByPid2(pid);
			//将数据放入comList2
			for (YhHealthLectureCommentApp yhHealthLectureCommentApp : comList) {
				//判断头像是否为空，为空则加上默认头像
				if(yhHealthLectureCommentApp.getHeadImg()==null) {
					if(yhHealthLectureCommentApp.getGender()==1) {
						yhHealthLectureCommentApp.setHeadImg("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
					}else if(yhHealthLectureCommentApp.getGender()==2){
						yhHealthLectureCommentApp.setHeadImg("http://xcxqny.yhy.ren/xiaochengxu/toxiangnv.png");
					}
				}
				comList2.add(yhHealthLectureCommentApp);
			}
		}

		//查询习惯评论
		List<YhUserNotifyApp> selecthabitcomment = yhUserNotifyService.selecthabit(userId);
		//习惯评论查询
		List<YhHealthLectureCommentApp> comList1=new ArrayList<YhHealthLectureCommentApp>();
		for (int i = 0; i < selecthabitcomment.size(); i++) {
			Integer pid = selecthabitcomment.get(i).getNewsId();
			//查询楼中评论
			List<YhHealthLectureCommentApp> comList = yhHealthLectureCommentAppService.findCommentByPid1(pid);
			//获取头像
			for (YhHealthLectureCommentApp yhHealthLectureCommentApp : comList) {
				if(yhHealthLectureCommentApp.getHeadImg()==null) {
					if(yhHealthLectureCommentApp.getGender()==1) {
						yhHealthLectureCommentApp.setHeadImg("http://xcxqny.yhy.ren/xiaochengxu/toxiangnan.png");
					}else if(yhHealthLectureCommentApp.getGender()==2){
						yhHealthLectureCommentApp.setHeadImg("http://xcxqny.yhy.ren/xiaochengxu/toxiangnv.png");
					}
				}
				comList1.add(yhHealthLectureCommentApp);
			}
		}

		//互动--点赞通知
		//查询该用户被点赞的所有信息
		List<YhHealthLectureCommentApp> habitpraise=new ArrayList<YhHealthLectureCommentApp>();
//		try {
			List<YhUserNotifyApp> selectdianzan=yhUserNotifyService.selectdianzan(userId);
			System.out.println("所有点赞信息：");
			System.out.println(selectdianzan);
			for(int i = 0; i < selectdianzan.size(); i++){
				//获取点赞内容id
				int id=selectdianzan.get(i).getStandby2();
				Long priid=selectdianzan.get(i).getId();
				System.out.println("id:"+id);
				//获取头像，昵称
				List<YhHabitPraise> comList=yhHabitPraiseService.selectCodeByCommentId(id);
				System.out.println("comList.size()--"+comList.size());

				YhHealthLectureCommentApp yhHealthLectureCommentApp = new YhHealthLectureCommentApp();

				int serviceId = selectdianzan.get(i).getToServiceId();
				String content = selectdianzan.get(i).getContent();
				Date date = selectdianzan.get(i).getSendDate();
				boolean isread = selectdianzan.get(i).getIsRead();
				int titleid = selectdianzan.get(i).getNewsId();
				int habitid = selectdianzan.get(i).getLectureinfoid();

				yhHealthLectureCommentApp.setCommentinfo(content);
				yhHealthLectureCommentApp.setToServiceId(serviceId);
				yhHealthLectureCommentApp.setSendDate(date);
				yhHealthLectureCommentApp.setIsRead(isread);
				yhHealthLectureCommentApp.setId(titleid);
				yhHealthLectureCommentApp.setPid(habitid);
				yhHealthLectureCommentApp.setPrid(priid);

				for(YhHabitPraise yhHabitPraise: comList) {   //int j=0; j< comList.size(); j++
					//获取点赞者的code
					String code = yhHabitPraise.getUserCode();
					//获取点赞者的头像，昵称
					List<YhUserReginfo> userinfo = YhUserReginfoService.selectAllUserinfoByuserCode(code);
					for(int k=0; k < userinfo.size(); k++) {
						String name = userinfo.get(k).getUserName();
						String image = userinfo.get(k).getPostCode();
						System.out.println("name:"+name+"--image:"+image);
						yhHealthLectureCommentApp.setHeadImg(image);
						yhHealthLectureCommentApp.setName(name);

						System.out.println("点赞人姓名： ");
						System.out.println(yhHealthLectureCommentApp.getName());
						habitpraise.add(yhHealthLectureCommentApp);
					}
				}
			}
//		}catch (Exception e){
//			System.out.println(e);
//		}



		map.put("comList", comList2);  //课堂评论
	    map.put("listMaps", listMaps);  //站内信
		map.put("habitMaps",comList1);  //习惯评论
		map.put("dianzan",habitpraise); //点赞通知信息
		return Result.ok(map);
	}
	/**
	 * 查询奖励通知
	 * @param userId
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/selectAwardInform")
	@ResponseBody
	public Result selectAwardInform(String userId,Integer type) {
	 List<YhUserNotifyApp> list = yhUserNotifyService.selectAwardInform(userId, type);
		return Result.ok(list);
	}


	
	
//	@RequestMapping(value = "/selectcomment")
//	@ResponseBody
//	public Result selectcomment(String userId) {
//	 List<YhHealthLectureCommentApp> list = yhUserNotifyService.selectcomment(userId);
//	 
//	 List<commentVojo> listcomment = new  ArrayList<commentVojo>();
//	 for (int i = 0; i < list.size(); i++) {
//		 commentVojo commentVojo2 = new commentVojo();
//		 String userCode = list.get(i).getUsercode();
//		 String commenttime = list.get(i).getCommenttime();
//		 YhWxUser yhWxUser = YhWxUserService.selectHeadImgbyParam(userCode);
//		 String headImg = yhWxUser.getHeadImg();
//		 YhUserReginfo yhUserReginfo = YhUserReginfoService.selectByPrimaryKey(userCode);
//		 String userName = yhUserReginfo.getUserName();
//		 commentVojo2.setCommentDate(commenttime);
//		 commentVojo2.setHeadImg(headImg);
//		 commentVojo2.setUserName(userName);
//		 listcomment.add(commentVojo2);
//	}
//	  Collections.reverse(listcomment);
//		return Result.ok(listcomment);
//	}
	
	@RequestMapping(value = "/selectIsRead")
	@ResponseBody
	public Result selectIsRead(Integer isRead,String userId) {
	 int read = yhUserNotifyService.selectIsRead(isRead,userId);
		return Result.ok(read);
	}
	/**
	 * 添加消息报告通知接口
	 * @param yhUserNotify
	 * @return
	 */
	@RequestMapping(value = "/addMessageNotification")
	@ResponseBody
	public Result addMessageNotification(YhUserNotifyApp yhUserNotify) {
		int row = 0;
		row = yhUserNotifyService.insertSelective(yhUserNotify);
		return Result.ok(row);
	}
	
	/**
	 * 修改已读状态
	 * @param yhUserNotify
	 * @return
	 */
	@RequestMapping(value = "/updateIsread")
	@ResponseBody
	public int updateIsread(YhUserNotifyApp yhUserNotify) {
		yhUserNotify.setTyepe(1);
		int updateByPrimaryKeySelective = yhUserNotifyService.updateByPrimaryKeySelective(yhUserNotify);
		return updateByPrimaryKeySelective;
		
	}
	
	/**
	 * 添加课堂评论数据
	 * @param yhUserNotify
	 * @return
	 */
	@RequestMapping(value = "/addYhUserNotify")
	@ResponseBody
	public int addYhUserNotify(YhUserNotifyApp yhUserNotify) {
		yhUserNotify.setContent("回复了你的课堂评论，立即查看详情>>");
		 int insertSelective = yhUserNotifyService.insertSelective(yhUserNotify);
		return insertSelective;
	}


}