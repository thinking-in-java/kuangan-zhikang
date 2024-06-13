/**
 * 
 */
package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhHabitPraise;
import com.yihe.applet.api.domain.YhUserNotifyApp;
import com.yihe.applet.api.service.YhHabitPraiseService;
import com.yihe.applet.api.service.YhUserNotifyAppService;
import com.yihe.applet.api.service.YhUserReginfoAppService;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/YhHabitPraiseController")
@Api(value = "YhHabitPraiseController控制器", description = "YhHabitPraiseController管理")
public class YhHabitPraiseController {

	
	 @Autowired
	 YhHabitPraiseService YhHabitPraiseService;
	@Autowired
	YhUserNotifyAppService yhUserNotifyAppService;
	@Autowired
	YhUserReginfoAppService YhUserReginfoService;
	 
	 /**
	     * 点赞
	     *
	     * @param commentid
	     * @return
	     */
	    @RequestMapping(value = "/insertHabitPraise")
	    @ResponseBody
	    public Result insertHabitPraise(String commentid, String userCode,String code,int id,int habitid) {
	    	YhHabitPraise pra=new YhHabitPraise();
	    	pra.setCommentid(commentid);
	    	pra.setUserCode(userCode);
	        int findFabulousByInfoId = YhHabitPraiseService.insertSelective(pra);

	        if(findFabulousByInfoId>0) {
	        	if(code.equals(userCode)){
					System.out.println("123");
				}else {
					YhHabitPraise record = new YhHabitPraise();
					record.setUserCode(userCode);
					record.setCommentid(commentid);
					YhHabitPraise yhHabitPraise = YhHabitPraiseService.selectprId(record);
					System.out.println(yhHabitPraise.getPrId());
					int prId = yhHabitPraise.getPrId();

					System.out.println("参数:commentid--" + commentid + "--userCode" + userCode + "--code--" + code);
					System.out.println("id: " + id + "--habitid：" + habitid);
					//获取点赞者的用户信息
//			YhUserReginfoApp userinfo=YhUserReginfoService.findYhUserReginfo(userCode);
//			String title=userinfo.getUserName();
//			String content=userinfo.getPostCode();
//			System.out.println("postCode:"+content);
					//添加点赞提醒功能，将点赞信息插入yh_user_notify表中
					YhUserNotifyApp yhUserNotifyApp = new YhUserNotifyApp();
					yhUserNotifyApp.setTitle("点赞");
					yhUserNotifyApp.setUserId(code);
					yhUserNotifyApp.setContent("给你的习惯动态点了1个赞");
					yhUserNotifyApp.setToServiceId(7);
					yhUserNotifyApp.setTyepe(2);
					yhUserNotifyApp.setIsRead(false);
					yhUserNotifyApp.setSendDate(new Date());
					yhUserNotifyApp.setStandby2(prId);
					yhUserNotifyApp.setNewsId(id);
					yhUserNotifyApp.setLectureinfoid(habitid);
					System.out.println(yhUserNotifyApp);
					yhUserNotifyAppService.insert(yhUserNotifyApp);
				}
			}
	        return Result.ok(findFabulousByInfoId);
	    }

	    
	    
	    /**
	     * 取消点赞
	     *
	     * @param commentid
	     * @return
	     */
	    @RequestMapping(value = "/deleteHabitPraise")
	    @ResponseBody
	    public Result deleteHabitPraise(String commentid, String userCode) {
	    	Map<String, Object> map=new HashMap<String, Object>();
	    	map.put("commentid", commentid);
	    	map.put("userCode", userCode);
	        int findFabulousByInfoId = YhHabitPraiseService.deleteByPrimaryKey(map);
	        return Result.ok(findFabulousByInfoId);
	    }
	 
}
