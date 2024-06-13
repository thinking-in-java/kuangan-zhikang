package com.yihe.applet.api.controller;

import com.qiniu.common.QiniuException;
import com.yihe.applet.api.domain.YhHealthLectureCommentApp;
import com.yihe.applet.api.domain.YhUserNotifyApp;
import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.domain.YhWxSetting;
import com.yihe.applet.api.service.YhHealthLectureCommentAppService;
import com.yihe.applet.api.service.YhUserNotifyAppService;
import com.yihe.applet.api.service.YhUserReginfoAppService;
import com.yihe.applet.api.service.YhWxSettingService;
import com.yihe.applet.api.util.common.util.QiniuCloudUtil;
import com.yihe.common.utils.http.HttpUtils;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * YhHealthLectureCommentcontroller
 * Created by dyb on 2020/3/13.
 */
@Controller
@RequestMapping("/api/YhHealthLectureComment")
@Api(value = "YhHealthLectureComment鎺у埗鍣�", description = "YhHealthLectureComment绠＄悊")
public class YhHealthLectureCommentController  {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhHealthLectureCommentController.class);
    @Autowired
    YhUserNotifyAppService yhUserNotifyAppService;

	@Autowired
	YhWxSettingService YhWxSettingService;
	@Autowired
	YhHealthLectureCommentAppService yhHealthLectureCommentService;
	@Autowired
	YhUserReginfoAppService YhUserReginfoService;

	@RequestMapping(value = "/selecTcontAndName")
	@ResponseBody
	public Result selecTcontAndName (Integer commentid) {
		YhHealthLectureCommentApp andName = yhHealthLectureCommentService.selecTcontAndName(commentid);
		return Result.ok(andName);

	}


	/**
	 * 鏂板璇勮
	 */
	@RequestMapping(value = "/addComment")
	@ResponseBody
	public Result addComment(YhHealthLectureCommentApp record) {
		System.out.println("评论进来了");
		if (null == record) {
			return Result.error();
		}
		record.setCommenttime(NowString());
		if (!checkContent(record.getCommentinfo())) {
			return Result.error("文字内容违规");
		}

		YhHealthLectureCommentApp comment = yhHealthLectureCommentService.ifclock(record);
		if (comment!=null){
			record.setCommentid(comment.getCommentid());
			yhHealthLectureCommentService.updClock(record);
		}else yhHealthLectureCommentService.insertSelective(record);

		YhHealthLectureCommentApp commentApp = yhHealthLectureCommentService.findCommentPuserCodeByParam(record.getPid());
		if(commentApp!=null) {
		YhUserNotifyApp yhUserNotifyApp = new YhUserNotifyApp();
		yhUserNotifyApp.setUserId(commentApp.getUsercode());
		yhUserNotifyApp.setNewsId(record.getPid());
		yhUserNotifyApp.setLectureinfoid(record.getLectureinfoid());
		yhUserNotifyApp.setStandby1(String.valueOf(record.getCommentid()));
		yhUserNotifyApp.setTitle("课堂评论");
		yhUserNotifyApp.setContent("回复了你的课堂评论，立即查看详情>>");
		yhUserNotifyApp.setToServiceId(6);
		yhUserNotifyApp.setTyepe(2);
		yhUserNotifyApp.setIsRead(false);
		yhUserNotifyApp.setSendDate(new Date());
		yhUserNotifyAppService.insert(yhUserNotifyApp);
		YhUserReginfoApp findYhUserReginfo = YhUserReginfoService.findYhUserReginfo(record.getUsercode());
		record.setName(findYhUserReginfo.getName());
		}
		return Result.ok(record);
	}

	/**
	 * 每次打卡主表都能自增天数
	 */
	@RequestMapping(value = "/updateTotalDay")
	@ResponseBody
	public Boolean updateTotalDay(@Param("userCode") String userCode,@Param("habitId") Integer habitId){
		return yhHealthLectureCommentService.updateTotalDay(userCode,habitId);
	}

	/**
	 * 添加习惯评论
	 */
	@RequestMapping(value = "/addHabitComment")
	@ResponseBody
	public Result addHabitComment(YhHealthLectureCommentApp record) {
		if (null == record) {
			return Result.error();
		}
		record.setCommenttime(NowString());
		if (!checkContent(record.getCommentinfo())) {
			return Result.error("文字内容违规");
		}
		int insertSelective = yhHealthLectureCommentService.insertSelective(record);

		YhHealthLectureCommentApp commentApp = yhHealthLectureCommentService.findCommentPuserCodeByParam(record.getPid());
		if(commentApp!=null) {
			YhUserNotifyApp yhUserNotifyApp = new YhUserNotifyApp();
			yhUserNotifyApp.setUserId(commentApp.getUsercode());
			yhUserNotifyApp.setNewsId(record.getPid());
			yhUserNotifyApp.setLectureinfoid(record.getLectureinfoid());
			yhUserNotifyApp.setStandby1(String.valueOf(record.getCommentid()));
			yhUserNotifyApp.setTitle("习惯评论");
			yhUserNotifyApp.setContent("回复了你的习惯评论，立即查看详情>>");
			yhUserNotifyApp.setToServiceId(4);
			yhUserNotifyApp.setTyepe(2);
			yhUserNotifyApp.setIsRead(false);
			yhUserNotifyApp.setSendDate(new Date());
			yhUserNotifyAppService.insert(yhUserNotifyApp);
			YhUserReginfoApp findYhUserReginfo = YhUserReginfoService.findYhUserReginfo(record.getUsercode());
			record.setName(findYhUserReginfo.getName());
		}
		return Result.ok(record);
	}

	public String getAccessToken()
	{
		YhWxSetting selectByPrimaryKey = YhWxSettingService.selectByPrimaryKey(1);
	    String accessToken="https://api.weixin.qq.com/cgi-bin/token";
	    String turl = String.format(
	    		"grant_type=client_credential&appid=%s&secret=%s",
	            selectByPrimaryKey.getAppid(),
	            selectByPrimaryKey.getAppsecret());
	    String result = HttpUtils.sendPost(accessToken, turl);
	    JSONObject res = JSONObject.fromObject(result);
	    return res.getString("access_token");
	}


	/**
	 * 验证文字是否违规
	 * @param content
	 * @return
	 */
	public  Boolean checkContent(String content) {
	    try {

	        CloseableHttpClient httpclient = HttpClients.createDefault();

	        CloseableHttpResponse response = null;

	        HttpPost request = new HttpPost("https://api.weixin.qq.com/wxa/msg_sec_check?access_token=" + getAccessToken());
	        request.addHeader("Content-Type", "application/json");
	        Map<String, String> map = new HashMap<>();
	        map.put("content",content);
	        String body = com.alibaba.fastjson.JSONObject.toJSONString(map);
	        request.setEntity((HttpEntity) new StringEntity(body,ContentType.create("text/json", "UTF-8")));
	        response = httpclient.execute(request);
	        HttpEntity httpEntity = response.getEntity();
	        String result = EntityUtils.toString(httpEntity, "UTF-8");// 转成string
	        JSONObject jso = JSONObject.fromObject(result);
	        return getResult(jso);
	    } catch (Exception e) {
	        e.printStackTrace();
	        LOGGER.info("----------------调用腾讯内容过滤系统出错------------------");
	        return true;
	    }
	}

	/**
     *  恶意图片过滤
     * @param multipartFile
     * @return
     */
    private  Boolean checkPic(MultipartFile multipartFile,String contentType) {
        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();

            CloseableHttpResponse response = null;

            HttpPost request = new HttpPost("https://api.weixin.qq.com/wxa/img_sec_check?access_token=" + getAccessToken());
            request.addHeader("Content-Type", "application/octet-stream");

            InputStream inputStream = multipartFile.getInputStream();

            byte[] byt = new byte[inputStream.available()];
            inputStream.read(byt);
            request.setEntity(new ByteArrayEntity(byt, ContentType.create(contentType)));

            response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "UTF-8");// 转成string
            JSONObject jso = JSONObject.fromObject(result);
            System.out.println(jso + "-------------验证效果");
	        Object errcode = jso.get("errcode");
	        int errCode = (int) errcode;
	        if (errCode == 0) {
	        	System.out.println("图片内容正常-----------");
	            return true;
	        } else if (errCode == 87014) {
	            System.out.println("图片内容违规-----------");
	            return false;
	        }
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("----------------调用腾讯内容过滤系统出错------------------");
	        return true;
	    }
    }



	/**
	 * 通过图片url返回图片Bitmap
	 * @param path
	 * @return
	 */
	public  static InputStream returnBitMap(String path) {
	    URL url = null;
	    InputStream is =null;
	    try {
	        url = new URL(path);
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    }
	    try {
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.
	        conn.setDoInput(true);
	        conn.connect();
	        is = conn.getInputStream(); //得到网络返回的输入流

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return is;
	}

	private static Boolean  getResult(JSONObject jso){
	    Object errcode = jso.get("errcode");
	    int errCode = (int) errcode;
	    if (errCode == 0) {
	    	LOGGER.info("文字内容正常-----------");
	        return true;
	    } else if (errCode == 87014) {
	    	LOGGER.info("文字内容违规-----------");
	        return false;
	    }
	    return true;
	}

	/**
	 * 妤间腑妤艰瘎璁�
	 */

	@RequestMapping(value = "/addCommentByComment")
	@ResponseBody
	public Result addCommentByComment(YhHealthLectureCommentApp record) {

		if (null == record) {
			return Result.error();
		}
		record.setCommenttime(NowString());

		int insertSelective = yhHealthLectureCommentService.insertSelective(record);

		return Result.ok(record);
	}



	@RequestMapping(value = "/addcommunityPostFile", method = RequestMethod.POST)
	@ResponseBody
	public Result addcommunityPostFile(HttpSession session,
			@RequestParam(value = "file", required = false) MultipartFile  file) throws QiniuException {
		System.out.println("Get into  銆嬨�嬨�嬨��/addcommunityPostFile");
		String contentType = file.getContentType();
		if(!checkPic(file,contentType)) {
			return Result.error("图片内容违规");
		}
		QiniuCloudUtil QiniuUploadImageUtil = new QiniuCloudUtil();
		String url = "";
		try {
			String path = session.getServletContext().getRealPath("/") + file.getOriginalFilename();

			file.transferTo(new File(path));
			url = QiniuUploadImageUtil.fileUpload(path);
			System.out.println(url);
			// 鍒犻櫎鏈湴
			File f = new File(path);
			f.delete();

		} catch (Exception e) {
			System.out.println(e);
		}
		return Result.ok(url);

	}


	/**
	 * 鑾峰彇褰撳墠绯荤粺鏃堕棿String绫诲瀷杩斿洖鍊�
	 *
	 * @return
	 */
	public String NowString() {
		SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");// 璁剧疆鏃ユ湡鏍煎紡
		return df.format(new Date());// new Date()涓鸿幏鍙栧綋鍓嶇郴缁熸椂闂�
	}

	/**
	 * 修改习惯
	 */
	@RequestMapping(value = "/updateComment")
	@ResponseBody
	public Result updateComment(YhHealthLectureCommentApp record) {
		System.out.println("评论进来了");
		if (null == record) {
			return Result.error();
		}
		record.setCommenttime(NowString());
		if (!checkContent(record.getCommentinfo())) {
			return Result.error("文字内容违规");
		}

		int insertSelective = yhHealthLectureCommentService.updateSelective(record);

		YhHealthLectureCommentApp commentApp = yhHealthLectureCommentService.findCommentPuserCodeByParam(record.getPid());
		if(commentApp!=null) {
			YhUserNotifyApp yhUserNotifyApp = new YhUserNotifyApp();
			yhUserNotifyApp.setUserId(commentApp.getUsercode());
			yhUserNotifyApp.setNewsId(record.getPid());
			yhUserNotifyApp.setLectureinfoid(record.getLectureinfoid());
			yhUserNotifyApp.setStandby1(String.valueOf(record.getCommentid()));
			yhUserNotifyApp.setTitle("习惯评论");
			yhUserNotifyApp.setContent("回复了你的习惯评论，立即查看详情>>");
			yhUserNotifyApp.setToServiceId(4);
			yhUserNotifyApp.setTyepe(2);
			yhUserNotifyApp.setIsRead(false);
			yhUserNotifyApp.setSendDate(new Date());
			yhUserNotifyAppService.insert(yhUserNotifyApp);
			YhUserReginfoApp findYhUserReginfo = YhUserReginfoService.findYhUserReginfo(record.getUsercode());
			record.setName(findYhUserReginfo.getName());
		}
		return Result.ok(record);
	}

}
