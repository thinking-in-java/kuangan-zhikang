/**
 * 
 */
package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONException;
import com.aliyuncs.exceptions.ClientException;
import com.ibm.icu.text.SimpleDateFormat;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.*;
import com.yihe.system.utils.Result;
import com.yihe.web.controller.tool.WechatMouldUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * 发送微信小程序消息
 * @author dong
 *
 */
@Controller
@RequestMapping("/ProgramController")
public class ProgramController {

	@Autowired
	YhManageTaskMapper Yhmanagetaskmapper;
	
	@Autowired
	 private YhManageTaskMapper yhManageTaskMapper;
	
	@Autowired
	YhUserNotifyMapper yhUserNotifyMapper;	
	
	@Autowired
	YhWxNotifyMapper yhWxNotifyMapper;
	
	@Autowired
	YhWxUserWebMapper yhWxUserMapper;
	
	@Autowired
	YhWxInformationMapper yhWxInformationMapper;
	
	@Autowired
	YhUserReginfoMapper yhUserReginfoMapper;
	
	
	
	
	
	@ApiOperation(value = "发送公众号消息模板")
	   @RequestMapping(value = "/gzhmessage", method = RequestMethod.POST)
		@ResponseBody
	   public Result  gzhmessage(String WxText,String checked) throws ClientException { 
		String[] array2 = checked.split(",");//分割服务内容id
		Set<String> set=new HashSet<String>();
		Set<String> set1=new HashSet<String>();
		yhWxNotify tify=new yhWxNotify();//站内信
		YhUserNotify notify=new YhUserNotify();//通知通告发送记录
		int finish=0;
		int fail=0;
		String text="";
		//发送小程序模板
		for(int i=0;i<array2.length;i++){
			 String userCode=yhManageTaskMapper.selectplanCode(Integer.valueOf(array2[i]));//取user_code
			 YhWxUserWeb user=yhWxUserMapper.selectByPrimaryKey(userCode);
			 if(user!=null) {
				 set1.add(userCode);//去重oppenid
			 }else {
				 YhUserReginfo info=yhUserReginfoMapper.findYhUserReginfo(userCode);
				 text+=info.getName()+":("+info.getMobile()+"),";
				 fail++;
			 }
		 }
		
		if(set1.size()!=0) {
		YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		String userName = yhUserReginfo.getName();

		
     YhWxInformation tion=yhWxInformationMapper.selectbyWxType("taskGZH");//去公众号的配置
     
		String s=sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid="+tion.getXcAppid()+"&secret="+tion.getXcAppsecret()+"");
     JSONObject resultJson = new JSONObject(s);
     String token = (String) resultJson.get("access_token");
     
     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
     
     //模板内容
     JSONObject data = new JSONObject();
		JSONObject data1 = new JSONObject();
		data1.put("value", "健管师给您发送了一条健康管理服务信息");
		
		
		JSONObject data2 = new JSONObject();
		data2.put("value",userName);
		
		
		JSONObject data3 = new JSONObject();
		data3.put("value", df.format(new Date()));
		
		JSONObject data4 = new JSONObject();
		data4.put("value", "站内信已送达，请及时查阅，祝您健康！");
		
		
		data.put(tion.getVariate1(), data1);
		data.put(tion.getVariate2(), data2);
		data.put(tion.getVariate3(), data3);
		data.put(tion.getVariate4(), data4);
		
		
		for (String string1 : set1) {
			
			tify.setWxNotifyId(fail);
			tify.setWxNotifyContent(WxText);
			tify.setWxNotifyBackup("服务通知");
			int wxNotifyId=yhWxNotifyMapper.insertyhnotify(tify);
			
			
			YhWxUserWeb user=yhWxUserMapper.showRegInfoByWx(string1);
			Date date = new Date();
			
			notify.setTitle("站内信");
			notify.setContent(WxText);
			notify.setUser_id(user.getUserId());
			notify.setSend_date(date);
			notify.setType(2);
			notify.setTo_service_id("5");
			notify.setNews_id(tify.getWxNotifyId());
			int i=yhUserNotifyMapper.insertnotify(notify);
			
			
			YhUserNotifyVo vo=yhUserNotifyMapper.selectByPrimaryKeyVo(Long.valueOf(notify.getId()));
			
			String pagepath="/personalCenterMent/pages/infomation/messege?id="+vo.getNewsId()+"&pid="+vo.getId()+"&newsid="+vo.getNewsId();
			
			JSONObject data6 = new JSONObject();
			data6.put("appid", tion.getXcAppid());//小程序appid
			data6.put("path", pagepath);//小程序跳转页面
			JSONObject json1 = new JSONObject();
			json1.put("appid", tion.getGzAppid());//公众号appid
			json1.put("template_id", tion.getBoardId());//公众号模板id
			json1.put("url", "http://weixin.qq.com/download");
			json1.put("miniprogram",data6);
			json1.put("data",data);
			WechatMould mould=new WechatMould();
			mould.setData(json1);
			mould.setToken(token);
			mould.setTouser(user.getOppenId());
			mould.setTemplate_id(tion.getBoardId());
			String errmsg=WechatMouldUtil.sendWechatmsgGZH(mould);
			 
			if(errmsg.equals("ok")) {//成功
				 set.add(user.getUserId());//去重成功usercode
				finish++;
				
				
				
			
			}else {
				text+=user.getName()+"(:"+user.getMobile()+"),";
				fail++;
				//失败
			}
		}
		
		
		
		if(set.size()!=0) {
		//发送站内信
		YhServicePage Yhservicepage=new YhServicePage();
		int ret=0;
		for(int i=0;i<array2.length;i++){
			String userCode=yhManageTaskMapper.selectplanCode(Integer.valueOf(array2[i]));
			for (String string : set) {
					Yhservicepage.setId(Integer.valueOf(array2[i]));
					Yhservicepage.setState(3);
					Yhservicepage.setReturnvisit(7);
					Yhservicepage.setAcceptsituation("使用微信消息通知的医嘱提醒模板，发送信息:"+WxText);
					Yhservicepage.setSatisfaction(2);
					Date date = new Date();
					SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
					Yhservicepage.setEndtime(dateFormat.format(date));
					ret=ret+Yhmanagetaskmapper.updatestate(Yhservicepage);
			}
		
		 }
		}
		}
		Servefulfill fill=new Servefulfill();
		fill.setFail(fail);
		fill.setFinish(finish);
		fill.setText(text);
	       return Result.ok(fill);
	   }



	@ApiOperation(value = "发送公众号消息模板3")
	@RequestMapping(value = "/gzhmessage3", method = RequestMethod.POST)
	@ResponseBody
	public Result  gzhmessage3(String WxText,String checked) throws ClientException {
		String[] array2 = checked.split(",");//分割服务内容id
		Set<String> set=new HashSet<String>();
		Set<String> set1=new HashSet<String>();
		yhWxNotify tify=new yhWxNotify();//站内信
		YhUserNotify notify=new YhUserNotify();//通知通告发送记录
		int finish=0;
		int fail=0;
		String text="";
		//发送小程序模板
		for(int i=0;i<array2.length;i++){
			String userCode=array2[i];//取user_code
			YhWxUserWeb user=yhWxUserMapper.selectByPrimaryKey(userCode);
			if(user!=null) {
				set1.add(userCode);//去重oppenid
			}else {
				YhUserReginfo info=yhUserReginfoMapper.findYhUserReginfo(userCode);
				text+=info.getName()+":("+info.getMobile()+"),";
				fail++;
			}
		}

		if(set1.size()!=0) {
			YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
			String userName = yhUserReginfo.getName();


			YhWxInformation tion=yhWxInformationMapper.selectbyWxType("taskGZH");//去公众号的配置

			String s=sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid="+tion.getXcAppid()+"&secret="+tion.getXcAppsecret()+"");
			JSONObject resultJson = new JSONObject(s);
			String token = (String) resultJson.get("access_token");

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

			//模板内容
			JSONObject data = new JSONObject();
			JSONObject data1 = new JSONObject();
			data1.put("value", "健管师给您发送了一条健康管理服务信息");


			JSONObject data2 = new JSONObject();
			data2.put("value",userName);


			JSONObject data3 = new JSONObject();
			data3.put("value", df.format(new Date()));

			JSONObject data4 = new JSONObject();
			data4.put("value", "站内信已送达，请及时查阅，祝您健康！");


			data.put(tion.getVariate1(), data1);
			data.put(tion.getVariate2(), data2);
			data.put(tion.getVariate3(), data3);
			data.put(tion.getVariate4(), data4);


			for (String string1 : set1) {

				tify.setWxNotifyId(fail);
				tify.setWxNotifyContent(WxText);
				tify.setWxNotifyBackup("服务通知");
				int wxNotifyId=yhWxNotifyMapper.insertyhnotify(tify);


				YhWxUserWeb user=yhWxUserMapper.showRegInfoByWx(string1);
				Date date = new Date();

				notify.setTitle("站内信");
				notify.setContent(WxText);
				notify.setUser_id(user.getUserId());
				notify.setSend_date(date);
				notify.setType(2);
				notify.setTo_service_id("5");
				notify.setNews_id(tify.getWxNotifyId());
				int i=yhUserNotifyMapper.insertnotify(notify);


				YhUserNotifyVo vo=yhUserNotifyMapper.selectByPrimaryKeyVo(Long.valueOf(notify.getId()));

				String pagepath="/personalCenterMent/pages/infomation/messege?id="+vo.getNewsId()+"&pid="+vo.getId()+"&newsid="+vo.getNewsId();

				JSONObject data6 = new JSONObject();
				data6.put("appid", tion.getXcAppid());//小程序appid
				data6.put("path", pagepath);//小程序跳转页面
				JSONObject json1 = new JSONObject();
				json1.put("appid", tion.getGzAppid());//公众号appid
				json1.put("template_id", tion.getBoardId());//公众号模板id
				json1.put("url", "http://weixin.qq.com/download");
				json1.put("miniprogram",data6);
				json1.put("data",data);
				WechatMould mould=new WechatMould();
				mould.setData(json1);
				mould.setToken(token);
				mould.setTouser(user.getOppenId());
				mould.setTemplate_id(tion.getBoardId());
				String errmsg=WechatMouldUtil.sendWechatmsgGZH(mould);

				if(errmsg.equals("ok")) {//成功
					set.add(user.getUserId());//去重成功usercode
					finish++;




				}else {
					text+=user.getName()+"(:"+user.getMobile()+"),";
					fail++;
					//失败
				}
			}



			if(set.size()!=0) {
				//发送站内信
				YhServicePage Yhservicepage=new YhServicePage();
				int ret=0;
				for(int i=0;i<array2.length;i++){
					String userCode=yhManageTaskMapper.selectplanCode(Integer.valueOf(array2[i]));
					for (String string : set) {
						Yhservicepage.setId(Integer.valueOf(array2[i]));
						Yhservicepage.setState(3);
						Yhservicepage.setReturnvisit(7);
						Yhservicepage.setAcceptsituation("使用微信消息通知的医嘱提醒模板，发送信息:"+WxText);
						Yhservicepage.setSatisfaction(2);
						Date date = new Date();
						SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
						Yhservicepage.setEndtime(dateFormat.format(date));
						ret=ret+Yhmanagetaskmapper.updatestate(Yhservicepage);
					}

				}
			}
		}
		Servefulfill fill=new Servefulfill();
		fill.setFail(fail);
		fill.setFinish(finish);
		fill.setText(text);
		return Result.ok(fill);
	}
	
	/**
	 * 发送微信消息
	 * @param
	 * @param checked
	 * @return
	 * @throws ClientException
	 */
	@ApiOperation(value = "发送小程序消息模板")
	   @RequestMapping(value = "/xxcmessage", method = RequestMethod.POST)
		@ResponseBody
	   public Result  xxcmessage(String WxText,String checked) throws ClientException { 
		String[] array2 = checked.split(",");//分割服务内容id
		Set<String> set=new HashSet<String>();
		Set<String> set1=new HashSet<String>();
		yhWxNotify tify=new yhWxNotify();//站内信
		YhUserNotify notify=new YhUserNotify();//通知通告发送记录
		int finish=0;
		int fail=0;
		String text="";
		//发送小程序模板
		for(int i=0;i<array2.length;i++){
			 String userCode=yhManageTaskMapper.selectplanCode(Integer.valueOf(array2[i]));//取user_code
			 YhWxUserWeb user=yhWxUserMapper.selectByPrimaryKey(userCode);
			 if(user!=null) {
				 set1.add(userCode);//去重oppenid
			 }else {
				 YhUserReginfo info=yhUserReginfoMapper.findYhUserReginfo(userCode);
				 text+=info.getName()+":("+info.getMobile()+"),";
				 fail++;
			 }
		 }
		
		if(set1.size()!=0) {
		YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		String userName = yhUserReginfo.getName();
		 YhWxInformation tion=yhWxInformationMapper.selectbyWxType("task");
		JSONObject data = new JSONObject();
		JSONObject data1 = new JSONObject();
		data1.put("value", userName);
		
		
		JSONObject data2 = new JSONObject();
		data2.put("value", "给您发送了一条健康管理服务信息，请查阅!");
		
	        
		data.put(tion.getVariate1(), data1);
		data.put(tion.getVariate2(), data2);
		

       
        
		String s=sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid="+tion.getXcAppid()+"&secret="+tion.getXcAppsecret()+"");
        JSONObject resultJson = new JSONObject(s);
        String token = (String) resultJson.get("access_token");
        
		for (String string1 : set1) {
			tify.setWxNotifyId(fail);
			tify.setWxNotifyContent(WxText);
			tify.setWxNotifyBackup("服务通知");
			int wxNotifyId=yhWxNotifyMapper.insertyhnotify(tify);
			
			 YhWxUserWeb user=yhWxUserMapper.showRegInfoByWx(string1);
			Date date = new Date();
			notify.setTitle("站内信");
			notify.setContent(WxText);
			notify.setUser_id(user.getUserId());
			notify.setSend_date(date);
			notify.setType(2);
			notify.setTo_service_id("5");
			notify.setNews_id(tify.getWxNotifyId());
			int i=yhUserNotifyMapper.insertnotify(notify);
			
			YhUserNotifyVo vo=yhUserNotifyMapper.selectByPrimaryKeyVo(Long.valueOf(notify.getId()));
			
			String pagepath="/personalCenterMent/pages/infomation/messege?id="+vo.getNewsId()+"&pid="+vo.getId()+"&newsid="+vo.getNewsId();
			
			WechatMould mould=new WechatMould();
			mould.setData(data);
			mould.setToken(token);
			mould.setTouser(user.getOppenId());
			mould.setTemplate_id(tion.getBoardId());
			mould.setPage(pagepath);
			String errmsg=WechatMouldUtil.sendWechatmsgToUser(mould);
			
			if(errmsg.equals("ok")) {//成功
				 set.add(user.getUserId());//去重成功usercode
				finish++;
			
				
				
			}else {
				text+=user.getName()+"(:"+user.getMobile()+"),";
				fail++;
				//失败
			}
		}
		
		
		
		if(set.size()!=0) {
		//发送站内信
		YhServicePage Yhservicepage=new YhServicePage();
		int ret=0;
		for(int i=0;i<array2.length;i++){
			String userCode=yhManageTaskMapper.selectplanCode(Integer.valueOf(array2[i]));
			for (String string : set) {
					Yhservicepage.setId(Integer.valueOf(array2[i]));
					Yhservicepage.setState(3);
					Yhservicepage.setReturnvisit(6);
					Yhservicepage.setAcceptsituation("使用微信消息通知的医嘱提醒模板，发送信息:"+WxText);
					Yhservicepage.setSatisfaction(2);
					Date date = new Date();
					SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
					Yhservicepage.setEndtime(dateFormat.format(date));
					ret=ret+Yhmanagetaskmapper.updatestate(Yhservicepage);
			}
		
		 }
		}
		}
		Servefulfill fill=new Servefulfill();
		fill.setFail(fail);
		fill.setFinish(finish);
		fill.setText(text);
	       return Result.ok(fill);
	   }



	/**
	 * 发送微信消息3
	 * @param
	 * @param checked
	 * @return
	 * @throws ClientException
	 */
	@ApiOperation(value = "发送小程序消息模板3")
	@RequestMapping(value = "/xxcmessage3", method = RequestMethod.POST)
	@ResponseBody
	public Result  xxcmessage3(String WxText,String checked) throws ClientException {
		String[] array2 = checked.split(",");//分割服务内容id
		Set<String> set=new HashSet<String>();
		Set<String> set1=new HashSet<String>();
		yhWxNotify tify=new yhWxNotify();//站内信
		YhUserNotify notify=new YhUserNotify();//通知通告发送记录
		int finish=0;
		int fail=0;
		String text="";
		//发送小程序模板
		for(int i=0;i<array2.length;i++){
			String userCode=array2[i];//取user_code
			YhWxUserWeb user=yhWxUserMapper.selectByPrimaryKey(userCode);
			if(user!=null) {
				set1.add(userCode);//去重oppenid
			}else {
				YhUserReginfo info=yhUserReginfoMapper.findYhUserReginfo(userCode);
				text+=info.getName()+":("+info.getMobile()+"),";
				fail++;
			}
		}

		if(set1.size()!=0) {
			YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
			String userName = yhUserReginfo.getName();
			YhWxInformation tion=yhWxInformationMapper.selectbyWxType("task");
			JSONObject data = new JSONObject();
			JSONObject data1 = new JSONObject();
			data1.put("value", userName);


			JSONObject data2 = new JSONObject();
			data2.put("value", "给您发送了一条健康管理服务信息，请查阅!");


			data.put(tion.getVariate1(), data1);
			data.put(tion.getVariate2(), data2);




			String s=sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid="+tion.getXcAppid()+"&secret="+tion.getXcAppsecret()+"");
			JSONObject resultJson = new JSONObject(s);
			String token = (String) resultJson.get("access_token");

			for (String string1 : set1) {
				tify.setWxNotifyId(fail);
				tify.setWxNotifyContent(WxText);
				tify.setWxNotifyBackup("服务通知");
				int wxNotifyId=yhWxNotifyMapper.insertyhnotify(tify);

				YhWxUserWeb user=yhWxUserMapper.showRegInfoByWx(string1);
				Date date = new Date();
				notify.setTitle("站内信");
				notify.setContent(WxText);
				notify.setUser_id(user.getUserId());
				notify.setSend_date(date);
				notify.setType(2);
				notify.setTo_service_id("5");
				notify.setNews_id(tify.getWxNotifyId());
				int i=yhUserNotifyMapper.insertnotify(notify);

				YhUserNotifyVo vo=yhUserNotifyMapper.selectByPrimaryKeyVo(Long.valueOf(notify.getId()));

				String pagepath="/personalCenterMent/pages/infomation/messege?id="+vo.getNewsId()+"&pid="+vo.getId()+"&newsid="+vo.getNewsId();

				WechatMould mould=new WechatMould();
				mould.setData(data);
				mould.setToken(token);
				mould.setTouser(user.getOppenId());
				mould.setTemplate_id(tion.getBoardId());
				mould.setPage(pagepath);
				String errmsg=WechatMouldUtil.sendWechatmsgToUser(mould);

				if(errmsg.equals("ok")) {//成功
					set.add(user.getUserId());//去重成功usercode
					finish++;



				}else {
					text+=user.getName()+"(:"+user.getMobile()+"),";
					fail++;
					//失败
				}
			}



			if(set.size()!=0) {
				//发送站内信
				YhServicePage Yhservicepage=new YhServicePage();
				int ret=0;
				for(int i=0;i<array2.length;i++){
					String userCode=yhManageTaskMapper.selectplanCode(Integer.valueOf(array2[i]));
					for (String string : set) {
						Yhservicepage.setId(Integer.valueOf(array2[i]));
						Yhservicepage.setState(3);
						Yhservicepage.setReturnvisit(6);
						Yhservicepage.setAcceptsituation("使用微信消息通知的医嘱提醒模板，发送信息:"+WxText);
						Yhservicepage.setSatisfaction(2);
						Date date = new Date();
						SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
						Yhservicepage.setEndtime(dateFormat.format(date));
						ret=ret+Yhmanagetaskmapper.updatestate(Yhservicepage);
					}

				}
			}
		}
		Servefulfill fill=new Servefulfill();
		fill.setFail(fail);
		fill.setFinish(finish);
		fill.setText(text);
		return Result.ok(fill);
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
    

	public static void getWechatMould(String orderId) {
				JSONObject data = new JSONObject();  //这个只是模板中的data属性，因为他是嵌套型的所以把他单独拿出来，这个data也是一个单独的类，具体的属性就是下面的这些
				try {
					JSONObject data1 = new JSONObject();
					data1.put("value", "123123");
					
					
					JSONObject data2 = new JSONObject();
					data2.put("value", "商品");
					
					
					JSONObject data3 = new JSONObject();
					data3.put("value", "39.8元");
					
					JSONObject data4 = new JSONObject();
					data4.put("value", "2014年9月22日");
					
					
					JSONObject data5 = new JSONObject();
					data5.put("value", "冠心病患者在睡觉时可以采取头高脚低并且侧卧的方式来睡觉，这样可以8、缓解全身肌肉，从而让全身心放松。");
					
					data.put("first", data1);
					data.put("keyword1", data2);
					data.put("keyword2", data3);
					data.put("keyword3", data4);
					data.put("remark", data5);
					
					JSONObject data6 = new JSONObject();
					data6.put("appid", "wx51ba2097b74c3854");
					data6.put("page", "/pages/dialogue/dialogue");
					JSONObject json1 = new JSONObject();
					json1.put("appid", "wx205c72f97947a0ad");
					json1.put("template_id", "87HmNm5N8XSOtVytOUpzghq-bng360e6pTqKBz418FA");
					json1.put("url", "http://weixin.qq.com/download");
					json1.put("miniprogram",data6);
					json1.put("data",data);
					
					JSONObject json = new JSONObject();
					json.put("touser", "oamrF5OcKYwkh3tn0VkAuEjEfmus");
		            
		            json.put("mp_template_msg",json1);

		            String result = json.toString();
					  
		        } catch (JSONException e) {
		        	//throw new ApplicationException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "json数据出错");
		        }
				//WechatMouldUtil.sendWechatmsgToUser(orderId,data);
		}
	
	
	public static void main(String[] args) {
		getWechatMould("oamrF5OcKYwkh3tn0VkAuEjEfmus");
	}
	
}
