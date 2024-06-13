/**
 * 
 */
package com.yihe.web.controller.tool;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yihe.system.domain.WechatMould;

import net.sf.json.JSONException;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.misc.BASE64Encoder;
/**
 * @author dong
 *
 */
public class WechatMouldUtil {
	private static final String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN";
	
	private static final String TEMPLATE_URLGGZ = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN";
	
	
	private static final String TEMPLATE_URLGGZ1 = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";
	
	
	public static void main(String[] args) throws IOException {
		String s=sendGet("https://api.weixin.qq.com/cgi-bin/token", "grant_type=client_credential&appid="+"wx51ba2097b74c3854"+"&secret="+"2be93609c45b7840d25c36217bb6b398"+"");
		JSONObject resultJson = new JSONObject(s);
		String token = (String) resultJson.get("access_token");
		pullCouponByToken1(token);
	}

	
	public static Object pullCouponByToken1(String token) throws IOException {
			
			String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+token;
			
			HttpUtils client = new HttpUtils();
			Map map = Maps.newHashMap();
			map.put("scene", "usercode=10086");
			map.put("width", 280);
			String jsonString = JSON.toJSONString(map);
			
			byte[] data  = client.post(url, jsonString);
			
			// 将数组转为字符串
			BASE64Encoder encoder = new BASE64Encoder();
			String str = encoder.encode(data).trim();
			
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] imgbyte = decoder.decodeBuffer(str);
			OutputStream os = new FileOutputStream("D:/002.jpg");
			os.write(imgbyte, 0, imgbyte.length);
			os.flush();
			os.close();
			System.out.println(data.toString());
		
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


	/**
     * 获取小程序菊花码
     * @Description //TODO
     * @Author zhaowx
     * @Date 2020/3/25 15:23
     **/
//    public void getWXCode() {
//        try {
//            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=41_fEdAUObUuvhkQudBReweWJX-s26auC-HAjdIK3ew5Vr9mYiKoSlikpggVeD_6B7IDPrMfVgJKeTwMlGhtePHTSrXe-Ngz4DQMA5Fs35v9m_HPq2GNpeZDBHjrB4fcauGu9GEYnPRMchKIutUOYBeABARBE";
//            Map<String, String> param = new HashMap<>();
//            param.put("scene","yihe");
//            //这里的page如果没有的话可以不写，默认是跳主页，如果写了没有的页面的话，会返回错误信息
//            String json = JSON.toJSONString(param);
//            ByteArrayInputStream inputStream = httpClient.sendPost(url, json);
//            //这里判断的是返回的图片还是错误信息，一般错误信息不会大于200
//            if (inputStream.available() <= 200){
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                int i;
//                byte[] buffer = new byte[200];
//                while ((i = inputStream.read(buffer)) != -1){
//                    byteArrayOutputStream.write(buffer,0,i);
//                }
//                String str = new String(byteArrayOutputStream.toByteArray());
//                //错误信息的格式在官方文档里有
//                JSONObject jsonObject = JSONObject.parseObject(str);
//                if ("41030".equals(jsonObject.getString("errcode"))){
//                    msg.setResult(false);
//                    msg.setMsg("所传page页面不存在，或者小程序没有发布");
//                }else if ("45009".equals(jsonObject.getString("errcode"))){
//                    msg.setResult(false);
//                    msg.setMsg("调用分钟频率受限");
//                }
//                byteArrayOutputStream.close();
//                return msg;
//            }
//           
//            //输出到本地的代码
//             FileOutputStream fileOutputStream = new FileOutputStream("D:/123.png");
//            int i;
//            byte[] buffer = new byte[200];
//            while ((i = inputStream.read(buffer)) != -1){
//                fileOutputStream.write(buffer,0,i);
//            }
//            fileOutputStream.flush();
//            fileOutputStream.close();
//            
//            inputStream.close();
//            msg.setMsg("菊花码生成成功");
//            msg.setResult(true);
//        }catch (Exception e){
//            msg.setMsg("获取菊花码异常");
//            msg.setResult(false);
//        }
//        return msg;
//    }

    
	
	//这里我把模板消息的参数封装在了WechatMould中，data为上段代码的json数据，我的WechatMould中的data属性是MouldData data，data也是一个对象
		public static String sendWechatmsgToUser1(WechatMould mould) throws IOException{
	        String url = TEMPLATE_URLGGZ1.replace("ACCESS_TOKEN", "41_fEdAUObUuvhkQudBReweWJX-s26auC-HAjdIK3ew5Vr9mYiKoSlikpggVeD_6B7IDPrMfVgJKeTwMlGhtePHTSrXe-Ngz4DQMA5Fs35v9m_HPq2GNpeZDBHjrB4fcauGu9GEYnPRMchKIutUOYBeABARBE");
	        JSONObject json = new JSONObject();
	        try {
	            json.put("scene","yihe");
	        } catch (JSONException e) {
	        	System.out.println("json数据出错");
	        	//throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "json数据出错");
	        }
	        String result = httpsRequest(url, "POST", json.toString());
	        
	        InputStream   inputStream   =   new   ByteArrayInputStream(result.getBytes());

	        	        
	      //输出到本地的代码
            FileOutputStream fileOutputStream = new FileOutputStream("D:/123.png");
           int i;
           byte[] buffer = new byte[200];
           while ((i = inputStream.read(buffer)) != -1){
               fileOutputStream.write(buffer,0,i);
           }
           fileOutputStream.flush();
           fileOutputStream.close();
           
           inputStream.close();
	        return "success";
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
//	            if(!"ok".equals(errmsg)){//如果为errmsg为ok，则代表发送成功。
//	            	//logger.error("发送失败");
//	                return "error";
//	            }
	        } catch (JSONException e) {
	        	//throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "json数据出错");
	        }
	        return "success";
	    }
		
		
	
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
