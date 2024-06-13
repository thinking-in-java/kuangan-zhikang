/**
 * 
 */
package com.yihe.quartz.task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.exceptions.ClientException;
import com.ibm.icu.text.SimpleDateFormat;
import com.yihe.quartz.util.WechatMouldUtil;
import com.yihe.system.domain.SleepRemindWeb;
import com.yihe.system.domain.WechatMould;
import com.yihe.system.domain.YhLifeHabitDict;
import com.yihe.system.domain.YhLifeHabitPersonDict;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhWxInformation;
import com.yihe.system.domain.YhWxUserWeb;
import com.yihe.system.mapper.SleepRemindWebMapper;
import com.yihe.system.mapper.YhLifeHabitDictMapper;
import com.yihe.system.mapper.YhLifeHabitPersonDictMapper;
import com.yihe.system.mapper.YhUserReginfoMapper;
import com.yihe.system.mapper.YhWxInformationMapper;
import com.yihe.system.mapper.YhWxUserWebMapper;
import com.yihe.system.utils.Result;

/**
 * 习惯定时器
 * @author dong
 *
 */
//1.主要用于标记配置类，兼备Component的效果。
@Component("TimerSend")
public class HabitTimer {
	@Autowired
	YhLifeHabitPersonDictMapper YhLifeHabitPersonDictMapper;
	@Autowired
	SleepRemindWebMapper sleepRemindWebMapper;
	@Autowired
	YhLifeHabitDictMapper yhLifeHabitDictMapper;
	
	
	@Autowired
	YhWxInformationMapper yhWxInformationMapper;
	
	@Autowired
	YhWxUserWebMapper yhWxUserMapper;
	
	@Autowired
	YhUserReginfoMapper yhUserReginfoMapper;
	
	
	/**
	 * 健康习惯提醒
	 * @throws ParseException
	 * @throws ClientException 
	 */
	public void HebdomadRecordHabit(String id) throws ParseException, ClientException {
//		Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.MINUTE, (calendar.get(Calendar.MINUTE) - 1));
//        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
//        
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.set(Calendar.MINUTE, (calendar1.get(Calendar.MINUTE) + 1));
//        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
//        
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("signidDate", df.format(calendar.getTime()));
//		map.put("signidDate1", df1.format(calendar1.getTime()));
		YhLifeHabitPersonDict person=YhLifeHabitPersonDictMapper.selectByPrimaryKey(Integer.valueOf(id));
		if(person!=null) {
			YhLifeHabitDict habit=yhLifeHabitDictMapper.selectByPrimaryKey(person.getHabitId());
			xxcmessage(person.getUserCode(), habit.getHabitName()+"，打卡时间到了哦!","/pages/healthHabits/homepage"); 
		}
		
	}
	   
	 /**
	 * 睡眠提醒
	 * @throws ParseException
	 * @throws ClientException 
	 */
	public void sleepremind(String userCode) throws ParseException, ClientException {
//		Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.MINUTE, (calendar.get(Calendar.MINUTE) - 1));
//        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
//        
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.set(Calendar.MINUTE, (calendar1.get(Calendar.MINUTE) + 1));
//        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
//        
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("signidDate", df.format(calendar.getTime()));
//		map.put("signidDate1", df1.format(calendar1.getTime()));
//		//YhLifeHabitPersonDict person=YhLifeHabitPersonDictMapper.selectByDictStandby(map);
//		SleepRemindWeb remindWeb = sleepRemindWebMapper.selectSleepRemindByParam(map);
			xxcmessage(userCode,"设置的睡眠提醒时间到了，洗洗睡吧~","/healthManagement/pages/sleepModule/index/index"); 
		
	}
	
	
	/**
	 * 发送微信消息
	 * @param userCode
	 * @param habitName
	 * @return
	 * @throws ClientException
	 */
	   public Result  xxcmessage(String userCode,String habitName,String pagepath) throws ClientException { 
		
//		YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
//		String userName = yhUserReginfo.getName();
		   
		   YhUserReginfo info= yhUserReginfoMapper.finddoctorHderer1(userCode);
		 YhWxInformation tion=yhWxInformationMapper.selectbyWxType("task");
		JSONObject data = new JSONObject();
		JSONObject data1 = new JSONObject();
		data1.put("value", info.getUserName());
		
		
		JSONObject data2 = new JSONObject();
		data2.put("value", habitName);
		
	        
		data.put(tion.getVariate1(), data1);
		data.put(tion.getVariate2(), data2);
		

       
        
		String s=sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid="+tion.getXcAppid()+"&secret="+tion.getXcAppsecret()+"");
        JSONObject resultJson = new JSONObject(s);
        String token = (String) resultJson.get("access_token");
        
			
			YhWxUserWeb user=yhWxUserMapper.showRegInfoByWx(userCode);
			
			//YhUserNotifyVo vo=yhUserNotifyMapper.selectByPrimaryKeyVo(Long.valueOf(notify.getId()));
			
			
			WechatMould mould=new WechatMould();
			mould.setData(data);
			mould.setToken(token);
			mould.setTouser(user.getOppenId());
			mould.setTemplate_id(tion.getBoardId());
			mould.setPage(pagepath);
			String errmsg=WechatMouldUtil.sendWechatmsgToUser(mould);
			
			if(errmsg.equals("ok")) {//成功
				return Result.ok(errmsg);
			}else {
				return gzhmessage(userCode, habitName,pagepath);
			}
		
	       
	   }
	
	

	/**
	 * 发送公众号消息
	 * @param userCode
	 * @param habitName
	 * @return
	 * @throws ClientException
	 */
	   public Result  gzhmessage(String userCode,String habitName,String pagepath) throws ClientException { 
     YhWxInformation tion=yhWxInformationMapper.selectbyWxType("taskGZH");//去公众号的配置
		String s=sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid="+tion.getXcAppid()+"&secret="+tion.getXcAppsecret()+"");
     JSONObject resultJson = new JSONObject(s);
     String token = (String) resultJson.get("access_token");
     
     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
     
//     YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
//		String userName = yhUserReginfo.getName();
     YhUserReginfo info= yhUserReginfoMapper.finddoctorHderer1(userCode);
		
     //模板内容
     JSONObject data = new JSONObject();
		JSONObject data1 = new JSONObject();
		data1.put("value", "健管师给您发送了一条健康管理服务信息");
		
		
		JSONObject data2 = new JSONObject();
		data2.put("value",info.getUserName());
		
		
		JSONObject data3 = new JSONObject();
		data3.put("value", df.format(new Date()));
		
		JSONObject data4 = new JSONObject();
		data4.put("value", habitName);
		
		
		data.put(tion.getVariate1(), data1);
		data.put(tion.getVariate2(), data2);
		data.put(tion.getVariate3(), data3);
		data.put(tion.getVariate4(), data4);
		
		
		
			
		
			YhWxUserWeb user=yhWxUserMapper.showRegInfoByWx(userCode);
			Date date = new Date();
			
			
			
			
			
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
			
			}
		
		
	       return Result.ok(errmsg);
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
    
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");//可以方便地修改日期格式
		String hehe = dateFormat.format(now); 
		System.out.println(hehe);
		
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, (calendar.get(Calendar.MINUTE) - 1));
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        System.out.println(df.format(calendar.getTime()));
        
        
        Calendar calendar1 = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, (calendar1.get(Calendar.MINUTE) + 1));
        SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
        System.out.println(df1.format(calendar.getTime()));

		
	}
}
