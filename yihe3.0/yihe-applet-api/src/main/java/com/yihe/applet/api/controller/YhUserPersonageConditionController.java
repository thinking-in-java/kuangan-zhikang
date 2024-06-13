package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhUserPersonageConditionApp;
import com.yihe.applet.api.domain.YhWxSetting;
import com.yihe.applet.api.service.YhUserPersonageConditionAppService;
import com.yihe.applet.api.service.YhWxSettingService;
import com.yihe.applet.api.util.common.util.ApiOrcUtil;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.service.YhUserEnrolinfoService;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * YhUserPersonageConditioncontroller Created by dong on 2020/2/15.
 */
@Controller
@RequestMapping("/api/YhUserPersonageCondition")
@Api(value = "YhUserPersonageCondition控制器", description = "YhUserPersonageCondition管理")
public class YhUserPersonageConditionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserPersonageConditionController.class);
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@Autowired
	YhUserPersonageConditionAppService yhUserPersonageConditionService;
	@Autowired
	YhWxSettingService yhWxSettingService;

	/**
	 * 增加修改一般状况
	 * 
	 * @param name
	 * @param usercode
	 * @return
	 */
	@RequestMapping(value = "/conditionadd")
	@ResponseBody
	public int conditionadd(YhUserPersonageConditionApp cond) {
		int i = 0;
		YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
		yhUserEnrolinfo.setEnergyUptime(new Date());
		yhUserEnrolinfo.setUserCode(cond.getUserCode());
		if (cond == null) {
			i = 0;
		} else {
			YhUserPersonageConditionApp list = yhUserPersonageConditionService.showcondition(cond.getUserCode());

			if (list == null) {
				i = yhUserPersonageConditionService.insertSelective(cond);
				yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			} else {
				cond.setId(list.getId());
				
				i = yhUserPersonageConditionService.updateByPrimaryKeySelective(cond);
				yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
			}
		}
		return i;
	}

	/**
	 * 查询一般状况
	 * 
	 * @param name
	 * @param usercode
	 * @return
	 */
	@RequestMapping(value = "/showcondition")
	@ResponseBody
	public YhUserPersonageConditionApp showcondition(String usercode) {
		YhUserPersonageConditionApp list = yhUserPersonageConditionService.showcondition(usercode);
		return list;
	}

	/*@RequestMapping(value = "/getIdCard")
	@ResponseBody
	public JSONObject getIdCardFront(String imgUrl) throws IOException {
		imgUrl="https://kityminder-img.gz.bcebos.com/a16343ae8009dd9341cde6444fc50b2a6a3ff8bf";
	    String inputLine=null;
	    String frontUrl = "https://api.weixin.qq.com/cv/ocr/idcard?type=MODE&img_url="+imgUrl
	            +"&access_token="+getDetailByUrl();
	    System.out.println(frontUrl+"地址");
	    HttpURLConnection conn = null;
	    URL url = new URL(frontUrl);
	    conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("POST");//GET和POST必须全大写
	    conn.setConnectTimeout(10000);//连接的超时时间
	    conn.setReadTimeout(10000);//读数据的超时时间
	    conn.setDoOutput(true);//必须设置此方法  允许输出
	    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
	    JSONObject jsonObject = null;
	    while((inputLine = br.readLine()) != null){
	        jsonObject = JSONObject.fromObject(inputLine);
	    }
	    System.out.println(jsonObject);
	    return jsonObject;
	}
	*/

	@RequestMapping(value = "/getIdCard")
	@ResponseBody
	public Map<String, Object> getDetailByUrl() {
		YhWxSetting selectByPrimaryKey = yhWxSettingService.selectByPrimaryKey(1);
		Map<String, Object> map = null;
		String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?" + "grant_type=client_credential"
				+ "&appid=" + selectByPrimaryKey.getAppid() + "&secret=" + selectByPrimaryKey.getAppsecret();

		try {
			HttpClient client = HttpClientBuilder.create().build();// 构建一个Client
			HttpPost post = new HttpPost(access_token_url.toString()); // 构建一个GET请求
			HttpResponse response = client.execute(post);// 提交GET请求
			HttpEntity result = response.getEntity();// 拿到返回的HttpResponse的"实体"
			String content = EntityUtils.toString(result);
			JSONObject res = JSONObject.fromObject(content);
			map = (Map<String, Object>) JSONObject.toBean(res, Map.class);
			// return map.get("access_token").toString();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取信息失败");
		}
		return map;
	}
	
	public static void main(String[] args){//主程序，程序入口
		File file=new File("C:\\file1");
		if(!file.exists()){//如果文件夹不存在
			file.mkdir();//创建文件夹
		}
		try{//异常处理
			//如果Qiju_Li文件夹下没有Qiju_Li.txt就会创建该文件
			BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\file1\\qqqqqqqq.txt"));
			bw.write("Hello I/O!");//在创建好的文件中写入"Hello I/O"
			bw.close();//一定要关闭文件
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	

		@ResponseBody
		@RequestMapping("/upload")
		public String upload(HttpServletRequest request,  MultipartFile content) throws IOException {
			System.out.println("执行upload");
			request.setCharacterEncoding("UTF-8");
			LOGGER.info("执行图片上传");
			String pictureString = null;
			String path = null;
			String user = request.getParameter("user");
			LOGGER.info("user:"+user);
			if(!content.isEmpty()) {
			  LOGGER.info("成功获取照片");
			  //创建文件名
			   String fileName = content.getOriginalFilename();
			  
	           // 自定义的文件名称                
	           String trueFileName = fileName; 
			 //获取文件大小
			   long size = content.getSize();
			   LOGGER.info("获取文件大小"+size);   
			   //获取文件类型
			   String type = null;
			   //创建时间
			   Date date = new Date();
			   LOGGER.info("获取创建时间"+date);
			   type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
			   LOGGER.info("图片初始名称为：" + fileName + " 类型为：" + type);
			   if (type != null) {
				   String canonicalType = "GIF;PNG;JPG;JPEG";
			       if (canonicalType.contains(type.toUpperCase())) {
			    	   // 保存路径
			           String realPath = System.getProperty("user.dir");
			    	   // 设置存放图片文件的路径
			           //path = realPath + File.separator + "uploads" + File.separator + trueFileName;
			           String   path2 = realPath + File.separator + trueFileName;
					/*File file=new File(path);
					if(!file.exists()){//如果文件夹不存在
						file.mkdir();//创建文件夹
					}*/
			           LOGGER.info("存放图片文件的路径:" + path2);
			           content.transferTo(new File(path2));
			           LOGGER.info("文件成功上传到指定目录下");
		            pictureString = ApiOrcUtil.getPictureString(path2);
		           LOGGER.info("文件成功上传到指定目录下");
		       }else {
		     	  LOGGER.info("不是我们想要的文件类型,请按要求重新上传");
		           return "error";
		       }
		   }else {
		 	  LOGGER.info("文件类型为空");
		       return "error";
		   }
		}else {
		  LOGGER.info("没有找到相对应的文件");
		   return "error";
		}
		return pictureString;
		}

	/*	public Idcard getIdCardFront(String imgUrl) throws IOException {
			 YhWxSetting selectByPrimaryKey = yhWxSettingService.selectByPrimaryKey(1);
		    Idcard idcard = new Idcard();
		    String inputLine=null;
		    String frontUrl = "https://api.weixin.qq.com/cv/ocr/idcard?type=Front&img="+imgUrl
		            +"&access_token="+getDetailByUrl();
		    HttpURLConnection conn = null;
		    URL url = new URL(frontUrl);
		    conn = (HttpURLConnection) url.openConnection();
		    conn.setRequestMethod("POST");//GET和POST必须全大写
			conn.setConnectTimeout(CONNECT_TIME_OUT);//连接的超时时间
			conn.setReadTimeout(READ_OUT_TIME);//读数据的超时时间
				    conn.setDoOutput(true);//必须设置此方法  允许输出
		    int responseCode = conn.getResponseCode();
		    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		    while((inputLine = br.readLine()) != null){
		        JSONObject jsonObject = JSONObject.fromObject(inputLine);
		        idcard.setAddress(jsonObject.get("addr").toString());
		        idcard.setId_card_number(jsonObject.get("id").toString());
		        idcard.setRace(jsonObject.get("nationality").toString());
		        idcard.setGender(jsonObject.get("gender").toString());
		        idcard.setName(jsonObject.get("name").toString());
		        idcard.setSide(jsonObject.get("type").toString());
		        idcard.setBirthday(jsonObject.get("id").toString().substring(6,14));
		    }
		    return idcard;
		}*/

}
