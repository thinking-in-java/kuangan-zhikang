/**
 * 
 */
package com.yihe.system.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.rmi.ConnectException;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.yihe.system.domain.WechatMould;

import net.sf.json.JSONException;

/**
 * @author dong
 *
 */
public class WechatMouldUtil {
	private static final String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN";
	
	
	
    //这里我把模板消息的参数封装在了WechatMould中，data为上段代码的json数据，我的WechatMould中的data属性是MouldData data，data也是一个对象
	public static String sendWechatmsgToUser(WechatMould mould){
        String url = TEMPLATE_URL.replace("ACCESS_TOKEN", mould.getToken());
        JSONObject json = new JSONObject();
        try {
            json.put("touser", mould.getTouser());
            
            json.put("template_id",mould.getTemplate_id());
            
            json.put("page",mould.getPage());
            	
            json.put("lang","zh_CN");
            
            json.put("data", mould.getData());
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
}
