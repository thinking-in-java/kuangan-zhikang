package com.yihe.web.controller.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yihe.system.mapper.YhWxUserWebMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.github.pagehelper.PageInfo;
import com.yihe.framework.shiro.service.SysPasswordService;
import com.yihe.system.domain.YHDocGroup;
import com.yihe.system.domain.YhDiseaseAssessReportVOJO;
import com.yihe.system.domain.YhUserAdminInfo;
import com.yihe.system.domain.YhUserAdminInfoExample;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhWxInformation;
import com.yihe.system.domain.YhWxUserWeb;
import com.yihe.system.domain.goeasyUsers;
import com.yihe.system.domain.EquipmentInfoVOJO;
import com.yihe.system.domain.InterveneIntelligentGroupingVOJO;
import com.yihe.system.domain.InterveneVOJO;
import com.yihe.system.domain.InterveneVOJO2;
import com.yihe.system.domain.WechatMould;
import com.yihe.system.domain.YhUserAdminInfoExample.Criteria;
import com.yihe.system.domain.layim.JsonResult;
import com.yihe.system.domain.layim.User;
import com.yihe.system.mapper.YhWxInformationMapper;
import com.yihe.system.domain.YhUserChatRecordWed;
import com.yihe.system.service.WeChatService;
import com.yihe.system.service.YhUserAdminInfoService;
import com.yihe.system.service.impl.DefaultLayIMService;
import com.yihe.system.service.impl.YHDocGroupServiceImpl;
import com.yihe.system.service.impl.YHUserBloodPressureServiceImpl;
import com.yihe.system.service.impl.YhDiseaseAssessTotalReportServiceImpl;
import com.yihe.system.service.impl.YhSleepUserDiaryServiceImpl;
import com.yihe.system.service.impl.YhUserChatRecordWedServiceImpl;
import com.yihe.system.service.impl.YhUserEnrolinfoServiceImpl;
import com.yihe.system.service.impl.YhUserReginfoServiceImpl;
import com.yihe.system.utils.Data;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import com.yihe.system.utils.layim.Utils;
import com.yihe.web.controller.tool.WechatMouldUtil;

import java.util.Date;
import org.json.JSONObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.ConnectException;

/**
 * 聊天web
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/instantChat")
public class YhUserChatRecordWedController {
private static final String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN";
	
	private static final String TEMPLATE_URLGGZ = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN";
	
	
	@Autowired
	YhUserChatRecordWedServiceImpl  YhUserChatRecordWedServiceImpl;
	
	@Autowired
    private WeChatService weChatService;
	@Autowired
	com.yihe.system.mapper.YhWxUserWebMapper YhWxUserWebMapper;

	@Autowired
		com.yihe.system.service.impl.YhWxUserWebServiceImpl  YhWxUserWebServiceImpl;
		@Autowired
		YhWxInformationMapper yhWxInformationMapper;
		@Autowired
	    private  DefaultLayIMService defaultLayIMService;
		
		/**
	     * 获取用户姓名头像
	     * */
	    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
		@ResponseBody
	    public Result userInfo(String usercode){
	    	 
			User findUserNameAndImg = defaultLayIMService.findUserNameAndImg(usercode);
	    	return Result.ok(findUserNameAndImg);
	    }
	@RequestMapping(value = "/delWxUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result delWxUserInfo(String usercode){
		if(StringUtil.isNotEmpty(usercode)){
			return Result.ok(YhWxUserWebMapper.deleteByPrimaryKey(usercode));
		}

		return Result.error();
	}
	    
	@RequestMapping(value = "/findChatListInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result healthAssessment2( 
		  @RequestParam(required = false) String name,
		  @RequestParam(required = false) Integer read,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int limit) {
		 
		// 查询用户信息
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		PageInfo<YhUserChatRecordWed> findChatInfo = YhUserChatRecordWedServiceImpl.findChatInfo(principal.getUserCode(), read, name, page, limit);
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		rsMap.put("count", findChatInfo.getTotal());
		rsMap.put("code", 0);
		rsMap.put("msg", "ok");
		rsMap.put("data", findChatInfo.getList());
		return Result.ok(rsMap);
	}
	

	@RequestMapping(value = "/addChatListInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result addChatListInfo(@RequestBody YhUserChatRecordWed record) {
		 
		// 查询用户信息
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		if(null==record){
			return Result.error("参数异常");
		}
		if(StringUtil.isEmpty(record.getToUserCode())||StringUtil.isEmpty(record.getMessage())){
			return Result.error("参数异常");
		}
		int type=1;
		if("friend".equals(record.getMtypestr())) {
			type=1;
		}else {
			type=2;
		}
		String generateRoomId = Utils.generateRoomId(principal.getUserCode(),record.getToUserCode(),type);
		record.setRoomid(generateRoomId);
		record.setMtype(type);
		
		record.setUserCode(principal.getUserCode());
			if(3==record.getMessageType()||4==record.getMessageType()){

				/* 找出指定的2个字符在 该字符串里面的 位置 */
				int strStartIndex = record.getMessage().indexOf("yhyfileStr/");
				int strEndIndex = record.getMessage().indexOf("yhyfileEnd/");
				int strStartIndex2 = record.getMessage().indexOf("sizeStr/");
				int strEndIndex2 = record.getMessage().indexOf("sizeEnd/");

				/* 开始截取 */
				String result = record.getMessage().substring(strStartIndex, strEndIndex).substring("yhyfileStr/".length());
				String result2 = record.getMessage().substring(strStartIndex2, strEndIndex2).substring("sizeStr/".length());
				record.setFilename(result);
				record.setFilesize(result2);

			}

		  int insertSelective = YhUserChatRecordWedServiceImpl.insertSelective(record);
		if(insertSelective>0){
			return Result.ok();
		}
		return Result.error("添加聊天记录失败");
	}
	
	
	@RequestMapping(value = "/addQunChatListInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result addQunChatListInfo(@RequestBody YhUserChatRecordWed record) {
		
		// 查询用户信息
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		if(null==record){
			return Result.error("参数异常");
		}
		if(StringUtil.isEmpty(record.getToUserCode())||StringUtil.isEmpty(record.getMessage())){
			return Result.error("参数异常");
		}
		int type=1;
		if("friend".equals(record.getMtypestr())) {
			type=1;
		}else {
			type=2;
		}
		 
		record.setMtype(type);
		if(3==record.getMessageType()||4==record.getMessageType()){

			/* 找出指定的2个字符在 该字符串里面的 位置 */
			int strStartIndex = record.getMessage().indexOf("yhyfileStr/");
			int strEndIndex = record.getMessage().indexOf("yhyfileEnd/");
			int strStartIndex2 = record.getMessage().indexOf("sizeStr/");
			int strEndIndex2 = record.getMessage().indexOf("sizeEnd/");

			/* 开始截取 */
			String result = record.getMessage().substring(strStartIndex, strEndIndex).substring("yhyfileStr/".length());
			String result2 = record.getMessage().substring(strStartIndex2, strEndIndex2).substring("sizeStr/".length());
			record.setFilename(result);
			record.setFilesize(result2);

		}
		record.setUserCode(principal.getUserCode());
		int insertSelective = YhUserChatRecordWedServiceImpl.insertSelective(record);
		if(insertSelective>0){
			return Result.ok();
		}
		return Result.error("添加聊天记录失败");
	}
	
	/**
	 * 接收到消息 改变已读状态
	 */
	@RequestMapping(value = "/readInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result readInfo(String usercode) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		
		int modifyRead1 = YhUserChatRecordWedServiceImpl.modifyRead1(usercode, principal.getUserCode());
		return Result.ok(modifyRead1);
	}
	
	/**
	 * 发送订阅消息
	 */
	
	@RequestMapping(value = "/sendDingyue", method = RequestMethod.POST)
	@ResponseBody
	public Result sendDingyue(@RequestBody YhUserChatRecordWed record) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		record.setUserCode(principal.getUserCode());
		
		
		return Result.ok(record);
	}
	/**
	 * 在线状态
	 */
	
	@RequestMapping(value = "/statusWXUser", method = RequestMethod.POST)
	@ResponseBody
	public Result sendDingyue(String usercode) {
		 
		Integer findWXuserStatus = YhUserChatRecordWedServiceImpl.findWXuserStatus(usercode);
		
		return Result.ok(findWXuserStatus);
	}
	
	/**
	 * 发送消息
	 */
	
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	@ResponseBody
	public Result sendMessage(String usercode,String message) {
		YhWxUserWeb selectByPrimaryKey = YhWxUserWebServiceImpl.selectByPrimaryKey(usercode);
		if(null==selectByPrimaryKey){
			return Result.error();
		}
		
		YhWxInformation selectbyWxType = yhWxInformationMapper.selectbyWxType("reply");
		
	        
			String s=sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid="+selectbyWxType.getXcAppid()+"&secret="+selectbyWxType.getXcAppsecret()+"");
	        JSONObject resultJson = new JSONObject(s);
	        String token = (String) resultJson.get("access_token");
	        
	        
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		WechatMould mould =new WechatMould();
		mould.setTouser(selectByPrimaryKey.getOppenId());
		mould.setToken(token);
		mould.setPage("immediateChat/pages/dialogue/dialogue");
		mould.setTemplate_id(selectbyWxType.getBoardId());
		JSONObject data = new JSONObject();  //这个只是模板中的data属性，因为他是嵌套型的所以把他单独拿出来，这个data也是一个单独的类，具体的属性就是下面的这些
		try {
			JSONObject data1 = new JSONObject();
			data1.put("value", "您的健康咨询已回复请及时查阅，祝您愉快！");
			
			
			JSONObject data2 = new JSONObject();
			data2.put("value", principal.getName());
			
			 
			data.put("thing2", data1);
			data.put("name3", data2);
        } catch (JSONException e) {
        	//throw new ApplicationException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "json数据出错");
        }
		mould.setData(data);
		
		return Result.ok(WechatMouldUtil.sendWechatmsgToUser(mould));
	}
	@RequestMapping(value = "/sendQunMessage", method = RequestMethod.POST)
	@ResponseBody
	public Result sendQunMessage(String groupId) {
		YhWxInformation selectbyWxType = yhWxInformationMapper.selectbyWxType("reply");
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		if(StringUtil.isEmpty(groupId)){
			return Result.error();
		}
		 List<String> findUseroppenIdByDoctorAll = YhWxUserWebServiceImpl.findUseroppenIdByDoctorAll(principal.getUserCode(),"f"+groupId+"_");
		if(null==findUseroppenIdByDoctorAll||findUseroppenIdByDoctorAll.size()==0){
			return Result.error();
		}
		
		
		
		String s=sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid="+selectbyWxType.getXcAppid()+"&secret="+selectbyWxType.getXcAppsecret()+"");
		JSONObject resultJson = new JSONObject(s);
		String token = (String) resultJson.get("access_token");
		
		
		 
	
		WechatMould mould =new WechatMould();
		
		mould.setToken(token);
		mould.setPage("immediateChat/pages/dialogue/dialogue");
		mould.setTemplate_id(selectbyWxType.getBoardId());
		JSONObject data = new JSONObject();  //这个只是模板中的data属性，因为他是嵌套型的所以把他单独拿出来，这个data也是一个单独的类，具体的属性就是下面的这些
		try {
			JSONObject data1 = new JSONObject();
			data1.put("value", "回复了你的健康咨询，请查看");
			
			
			JSONObject data2 = new JSONObject();
			data2.put("value", principal.getName());
			
			
			data.put("thing2", data1);
			data.put("name3", data2);
		} catch (JSONException e) {
			//throw new ApplicationException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "json数据出错");
		}
		mould.setData(data);
		
		for (String string : findUseroppenIdByDoctorAll) {
			mould.setTouser(string);
			WechatMouldUtil.sendWechatmsgToUser(mould);
		}
		
		
		
		return Result.ok();
	}
	
	
	
	/**
	 * 
	 */
	@RequestMapping(value = "/findAllUser", method = RequestMethod.POST)
	@ResponseBody
	public Result findAllUser(String usercode) {
		 List<goeasyUsers> findUserAllInfo = YhUserChatRecordWedServiceImpl.findUserAllInfo();
		
		return Result.ok(findUserAllInfo);
	}
	
	@RequestMapping(value = "/sendGongzhonghao", method = RequestMethod.POST)
	@ResponseBody
	public Result sendGongzhonghao(String usercode,String textInfo) {
		YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		String userName = yhUserReginfo.getName();
		YhWxInformation tion=yhWxInformationMapper.selectbyWxType("replyGZH");//去公众号的配置
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String s=sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid="+tion.getXcAppid()+"&secret="+tion.getXcAppsecret()+"");
     JSONObject resultJson = new JSONObject(s);
     String token = (String) resultJson.get("access_token");
     
     //模板内容
     JSONObject data = new JSONObject();
		JSONObject data1 = new JSONObject();
		data1.put("value", "健管师回复了您");
		
		
		JSONObject data2 = new JSONObject();
		data2.put("value",textInfo);
		
		
		JSONObject data3 = new JSONObject();
		data3.put("value", userName);
		
		JSONObject data4 = new JSONObject();
		data4.put("value", df.format(new Date())+ "回复内容已送达，请及时查阅，祝您愉快！");


		data.put(tion.getVariate1(), data1);
		data.put(tion.getVariate2(), data2);
		data.put(tion.getVariate3(), data3);
		data.put(tion.getVariate4(), data4);

		
		JSONObject data6 = new JSONObject();
		data6.put("appid", tion.getXcAppid());//小程序appid
		data6.put("pagepath", "immediateChat/pages/dialogue/dialogue");//小程序跳转页面
		JSONObject json1 = new JSONObject();
		json1.put("appid", tion.getGzAppid());//公众号appid
		json1.put("template_id", tion.getBoardId());//公众号模板id
		json1.put("url", "http://weixin.qq.com/download");
		json1.put("miniprogram",data6);
		json1.put("data",data);
		WechatMould mould=new WechatMould();
		mould.setData(json1);
		mould.setToken(token);
		YhWxUserWeb selectByPrimaryKey = YhWxUserWebServiceImpl.selectByPrimaryKey(usercode);
		mould.setTouser(selectByPrimaryKey.getOppenId());
		mould.setTemplate_id(tion.getBoardId());
		String errmsg=WechatMouldUtil.sendWechatmsgGZH(mould);
		
     
		return Result.ok(errmsg);
	}
	@RequestMapping(value = "/sendQunGongzhonghao", method = RequestMethod.POST)
	@ResponseBody
	public Result sendQunGongzhonghao(String groupId) {
		if(StringUtil.isEmpty(groupId)){
			return Result.error();
		}
		YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		String userName = yhUserReginfo.getName();
		YhWxInformation tion=yhWxInformationMapper.selectbyWxType("taskGZH");//去公众号的配置
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String s=sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid="+tion.getXcAppid()+"&secret="+tion.getXcAppsecret()+"");
     JSONObject resultJson = new JSONObject(s);
     String token = (String) resultJson.get("access_token");
     
     //模板内容
     JSONObject data = new JSONObject();
		JSONObject data1 = new JSONObject();
		data1.put("value", "您好，您有新的健管师信息提醒。");
		
		
		JSONObject data2 = new JSONObject();
		data2.put("value",userName);
		
		
		JSONObject data3 = new JSONObject();
		data3.put("value", df.format(new Date()));
		
		JSONObject data4 = new JSONObject();
		data4.put("value", "健管师给您发送了1条新的站内信，请查阅");
		
		
		data.put(tion.getVariate1(), data1);
		data.put(tion.getVariate2(), data2);
		data.put(tion.getVariate3(), data3);
		data.put(tion.getVariate4(), data4);
		
		JSONObject data6 = new JSONObject();
		data6.put("appid", tion.getXcAppid());//小程序appid
		data6.put("pagepath", "immediateChat/pages/dialogue/dialogue");//小程序跳转页面
		JSONObject json1 = new JSONObject();
		json1.put("appid", tion.getGzAppid());//公众号appid
		json1.put("template_id", tion.getBoardId());//公众号模板id
		json1.put("url", "http://weixin.qq.com/download");
		json1.put("miniprogram",data6);
		json1.put("data",data);
		WechatMould mould=new WechatMould();
		mould.setData(json1);
		mould.setToken(token);
		int num=0;
		 List<String> findUseroppenIdByDoctorAll = YhWxUserWebServiceImpl.findUseroppenIdByDoctorAll(yhUserReginfo.getUserCode(),"f"+groupId+"_");
	
		 if(null==findUseroppenIdByDoctorAll||findUseroppenIdByDoctorAll.size()==0){
		 return Result.error();
	 }
		 num=findUseroppenIdByDoctorAll.size();
		 int num2=0;
		 int num3=0;
		 for (int i = 0; i < findUseroppenIdByDoctorAll.size(); i++) {
			 String string = findUseroppenIdByDoctorAll.get(i);
			  mould.setTouser(string);
				mould.setTemplate_id(tion.getBoardId());
				String errmsg=WechatMouldUtil.sendWechatmsgGZH(mould);
				if(!"ok".equals(errmsg)){
					String sendxcxDingyue = sendxcxDingyue(string,token);
					if(!"ok".equals(sendxcxDingyue)){
						num3++;
					}
					num2++;
				}
		}
		 
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("不在线人数", num);
		map.put("公众号发送失败人数，发送订阅人数", num2);
		map.put("发送订阅失败人数", num3);
     
		return Result.ok(map);
	}
	
	
	
	public String sendxcxDingyue(String oppenId,String token){
		YhWxInformation selectbyWxType = yhWxInformationMapper.selectbyWxType("reply");
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		WechatMould mould =new WechatMould();
		mould.setTouser(oppenId);
		mould.setToken(token);
		mould.setPage("immediateChat/pages/dialogue/dialogue");
		mould.setTemplate_id(selectbyWxType.getBoardId());
		JSONObject data = new JSONObject();  //这个只是模板中的data属性，因为他是嵌套型的所以把他单独拿出来，这个data也是一个单独的类，具体的属性就是下面的这些
		try {
			JSONObject data1 = new JSONObject();
			data1.put("value", "回复了你的健康咨询，请查看");
			
			
			JSONObject data2 = new JSONObject();
			data2.put("value", principal.getName());
			
			 
			data.put("thing2", data1);
			data.put("name3", data2);
        } catch (JSONException e) {
        	//throw new ApplicationException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "json数据出错");
        }
		mould.setData(data);
		
		return WechatMouldUtil.sendWechatmsgToUser(mould);
	}
	 
	/**
	 * 
	 */
	@RequestMapping(value = "/findDoctor", method = RequestMethod.POST)
	@ResponseBody
	public Result findDoctor() {
		 goeasyUsers findUserAllInfo = YhUserChatRecordWedServiceImpl.findDocTorInfo();
		
		return Result.ok(findUserAllInfo);
	}
	
	
	
	 
	@GetMapping("login")
	 public String login(Model model, HttpServletResponse response) throws IOException {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
	        Cookie cookie = new Cookie("layim_uid", principal.getUserCode());
	        cookie.setMaxAge(7200);
	        cookie.setPath("/");
	        response.addCookie(cookie);
	        response.sendRedirect("/");
	        model.addAttribute("usercode", principal.getUserCode());
	        return "goeasy/test1";
	    }
	
	
	
	 //这里我把模板消息的参数封装在了WechatMould中，data为上段代码的json数据，我的WechatMould中的data属性是MouldData data，data也是一个对象
	public static String sendWechatmsgGZH(WechatMould mould){
        String url = TEMPLATE_URLGGZ.replace("ACCESS_TOKEN", mould.getToken());
        JSONObject json = new JSONObject();
        try {
            json.put("touser", mould.getTouser());
            
            json.put("mp_template_msg",mould.getData());
        } catch (JSONException e) {
        	System.out.println("json数据出错");
        	//throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "json数据出错");
        }
        String result = httpsRequest(url, "POST", json.toString());
        try {
            JSONObject resultJson = new JSONObject(result);
            String errmsg = (String) resultJson.get("errmsg");
            return errmsg;
//            if(!"ok".equals(errmsg)){//如果为errmsg为ok，则代表发送成功。
//            	//logger.error("发送失败");
//                return "error";
//            }
        } catch (JSONException e) {
        	//throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "json数据出错");
        }
        return "success";
    }
	
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr){
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
        	System.out.println("连接超时：{}");
        } catch (Exception e) {
        	System.out.println("https请求异常：{}");
        }
        return null;
    }
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
	 
}
