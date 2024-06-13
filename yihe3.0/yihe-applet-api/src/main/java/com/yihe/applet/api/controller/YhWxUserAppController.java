package com.yihe.applet.api.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.ibm.icu.util.Calendar;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yihe.applet.api.domain.YhUserFileApp;
import com.yihe.applet.api.domain.YhWxSetting;
import com.yihe.applet.api.domain.YhWxUser;
import com.yihe.applet.api.service.YhWxSettingService;
import com.yihe.applet.api.service.YhWxUserAppService;
import com.yihe.applet.api.util.common.util.DateUtil;
import com.yihe.applet.api.util.common.util.LoginDTO;
import com.yihe.applet.api.util.common.util.SessionDTOApp;
import com.yihe.applet.api.util.common.util.User;
import com.yihe.applet.api.util.common.util.WechatAdapterApp;
import com.yihe.common.config.Global;
import com.yihe.common.utils.http.HttpUtils;
import com.yihe.system.utils.FileUtils;
import com.yihe.system.utils.Result;

import net.sf.json.JSONObject;

import com.yihe.system.domain.YhSysConfig;
import com.yihe.system.service.YhSysConfigService;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * YhWxUsercontroller
 * Created by dong on 2020/2/25.
 */
@Controller
@RequestMapping("/api/YhWxUser")
public class YhWxUserAppController{

    private static final Logger LOGGER = LoggerFactory.getLogger(YhWxUserAppController.class);
     
	@Autowired
	YhWxSettingService YhWxSettingService;
	
    @Autowired
       YhWxUserAppService YhWxUserService;
    @Autowired
    YhWxSettingService yhWxSettingService;
    @Autowired
  	 private YhSysConfigService yhSysConfigService;
    
    /**
     * 上线状态modifyWXuserStatusInfo
     */
    @RequestMapping(value = "/wxuserStatus")
   	@ResponseBody
   	public Result wxuserStatus(String usercode,Integer status) {
    	
    	int modifyWXuserStatusInfo = YhWxUserService.modifyWXuserStatusInfo(usercode, status);
    	
       	return Result.ok(modifyWXuserStatusInfo);
    }
    /**
     * 获得项目信息
     */
    @RequestMapping(value = "/globalGetName")
    @ResponseBody
    public Result globalGetName() {
        return Result.ok(Global.getName());
    }

        /**
         * 授权api
         */
    @RequestMapping(value = "/authorize")
	@ResponseBody
	public Result authorize(LoginDTO loginDTO) {
    	
    	
		YhWxSetting selectByPrimaryKey = yhWxSettingService.selectByPrimaryKey(1);
		WechatAdapterApp wechatAdapter=new WechatAdapterApp(selectByPrimaryKey.getAppid(),selectByPrimaryKey.getAppsecret());
		SessionDTOApp jscode2session = wechatAdapter.jscode2session(loginDTO.getCode());
		 User user = JSON.parseObject(loginDTO.getRawData(),  User.class);
    	//先将这条数据解析为JSONObject
        //JSONObject outJson = JSONObject.parseObject(loginDTO.getRawData());
    	 
    	List<YhWxUser> users = YhWxUserService.selectByUsercode(loginDTO.getUserCode());
    	YhWxUser wxuser=new YhWxUser();
    	 wxuser.setUserId(loginDTO.getUserCode());
    	wxuser.setOppenId(jscode2session.getOpenid());
    	wxuser.setRealname(user.getNickName());
    	//wxuser.setHeadImg(user.getAvatarUrl());
//    	YhWxUser yhWxUser = YhWxUserService.selectHeadImgbyParam(loginDTO.getUserCode());
//    	if(yhWxUser!=null) {
//        	wxuser.setHeadImg(yhWxUser.getHeadImg()); 
//        	wxuser.setStatus(yhWxUser.getStatus());
//    	}
    	
    	String changeDateTOStr = DateUtil.changeDateTOStr(new Date());
    	wxuser.setAddTime(changeDateTOStr);
    	 // 先查看是否有，如果有更新，没有创建	
    	int jieguo=0;
        if (users != null && users.size() != 0) {
        	//  
        	jieguo=YhWxUserService.updateByPrimaryKey(wxuser);
        } else {
        	jieguo=YhWxUserService.insertSelective(wxuser);
        }
    	/*
    	int insertSelective = YhWxUserService.insertSelective(user);*/
        System.out.println(jscode2session.getOpenid());
		return Result.ok(jieguo);
	}
    /**
     * 查询是否存在头像
     * @param userId
     * @return
     */
    @RequestMapping(value = "/selectHeadImgbyParam")
   	@ResponseBody
    public YhWxUser selectHeadImgbyParam(String userId) {
    	YhWxUser selectHeadImgbyParam = YhWxUserService.selectHeadImgbyParam(userId);
		return selectHeadImgbyParam;
    	
    }
    
    /**
     * 修改头像信息
     * @param userId
     * @return
     */
    @RequestMapping(value = "/updateImg")
   	@ResponseBody
    public int updateImg(YhWxUser yhWxUser) {
    	yhWxUser.setStatus(yhWxUser.getStatus());
    	 int update= YhWxUserService.updateByPrimaryKeySelective(yhWxUser);
		return update;
    	
    }

  
    /**
     * * 小程序上传头像
	 * @author 阳雄
	 * @createtime 2020年5月14日10:34:55
     * @param request
     * @param file
     * @return 上传成功返回“success”，上传失败返回“error”
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/uploadImg")
    public String upload(HttpServletRequest request,@RequestParam(value="file") MultipartFile file,String userId) throws IOException {
    	
		request.setCharacterEncoding("UTF-8");
		LOGGER.info("执行文件上传");
		if(!file.isEmpty()) { 
		  LOGGER.info("成功获取文件");
		  //创建文件名
		  String fileName = file.getOriginalFilename();
		  LOGGER.info("文件名3"+file.getOriginalFilename());
		   String path = null; 
		   //获取文件类型
		   String type = null;
		   type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
		   LOGGER.info("文件初始名称为：" + fileName + " 类型为：" + type);
		   // 保存路径
           String realPath = System.getProperty("user.dir");
           // 自定义的文件名称                
           String trueFileName = fileName;
           // 设置存放图片文件的路径
           path = realPath + File.separator + "uploads" + File.separator + trueFileName;
          // String   path2 = "/root/wellcare/soft/apache-tomcat-8.5.40/bin/" + "uploads" + File.separator + trueFileName;
           String   path2 = realPath + "/uploads" + File.separator + trueFileName;
           //LOGGER.info("存放文件:" + path2);
           LOGGER.info("存放图片文件的路径:" + path);
           file.transferTo(new File(path));
           LOGGER.info("文件成功上传到指定目录下");
           //保存数据到数据库
           YhWxUser yhWxUser = new YhWxUser();
           yhWxUser.setUserId(userId);
           yhWxUser.setHeadImg(path);	
           yhWxUser.setMemberTime("1");
           YhWxUserService.updateByPrimaryKeySelective(yhWxUser);
          
		}
		return "success";
    }
	@RequestMapping("/downloadheadportrait")
	@ResponseBody
	public void downloadExcel(String userId,HttpServletResponse response,HttpServletRequest request) {
		try {
		// 根据文件ID查询信息
		 YhWxUser yhWxUser = YhWxUserService.selectHeadImgbyParam(userId);
			System.out.println(yhWxUser);
		// ------------------
		// 得到文件路径 取数据库
		String url = yhWxUser.getHeadImg();	
		// 取得文件名。 取数据库
        String realName = yhWxUser.getRealname();
        String fileUrl = url;
        System.out.println("数据库地址"+fileUrl);
        // 以流的形式下载文件。
        InputStream fis = new BufferedInputStream(new FileInputStream(fileUrl));
			//vnd.ms-excel浏览器返回的类型 这个类型改了 返回xxx。png  这样子吗 这个接口应该返回一个文件类型 而不是二进制的数据
        	
        FileUtils.downloadFile(request, response, fis, "application/octet-stream", realName);//查查java 这个
        } catch (Exception ex) {
        	LOGGER.error("下载失败，原因是：", ex);
        }
		

	}


	 /**
	 * @throws IOException 
     * 
     * @Title:图片上传返回路径   
     * @Description
     * @param MultipartFile，request
     * @return  服务器保存的图片地址url
     * @throws
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody 
    public Map<String,Object> upload(@RequestParam("file") MultipartFile files,HttpServletRequest request,String userId) throws IOException {
    	Map<String, Object> map=new HashMap<String, Object>();
		YhSysConfig conf=yhSysConfigService.showConfig();
		String contentType = files.getContentType();
		if(!checkPic(files,contentType)) {
			map.put("code", "20001");
			map.put("message", "图片内容违规");
			return map;
		}  
		if(conf==null) {//等于0调用本地上传图片
    	  String dicName="UpFile";//服务器上传文件夹名称
          String newFileName=files.getOriginalFilename();    
          newFileName=newFileName.substring(0,newFileName.lastIndexOf("."));
          String picurl = saveFile(files,newFileName,dicName, request);
          map.put("files", picurl);
    	}else {
		  //先把String对象转换成json对象
        JSONObject object=JSONObject.fromObject(conf.getValue());
		 
		//MultipartFile转换File完整版
	    InputStream ins = files.getInputStream();
	    
	    //构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.autoZone());
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		//...生成上传凭证，然后准备上传
		String accessKey = (String) object.get("qiniuAccessKey");
		String secretKey = (String) object.get("qiniuSecretKey");
		String bucket = (String) object.get("qiniuBucketName");
//		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = UUID.randomUUID().toString().replaceAll("\\-", "");
		try {
		    Auth auth = Auth.create(accessKey, secretKey);
		    String upToken = auth.uploadToken(bucket);
		    try {
		        Response response = uploadManager.put(ins,key,upToken,null, null);
		        //解析上传成功的结果
		        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		        
		        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		        if(files.getSize()==0){
		        	 //news.setImg("");
		        }else{
		        	String img=(String) object.get("qiniuDomain")+"/"+putRet.key;
		        	map.put("files", img);
		        	System.out.println((String) object.get("qiniuDomain")+"/"+putRet.key);
		        }
		       
		    } catch (QiniuException ex) {
		        Response r = ex.response;
		        System.err.println(r.toString());
		    }
		    //sysuser=1;
		} catch (Exception ex) {
			//sysuser=0;
		    //ignore
			map.put("files", "");
		}			
		
    	}
		
		
		return map;
    }
    
    
    public static String saveFile(MultipartFile filedata,String newFileName,String dicName, HttpServletRequest request) {
        // TODO Auto-generated method stub
        
      //获取Tomcat服务器所在的路径
        String pic_path;
        String tomcat_path = System.getProperty("catalina.home");
        System.out.println(tomcat_path);
        //获取Tomcat服务器所在路径的最后一个文件目录
        String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("\\") + 1, tomcat_path.length());
        System.out.println(bin_path+"--------------获取Tomcat服务器所在路径的最后一个文件目录");
        
        //若最后一个文件目录为bin目录，则服务器为手动启动
        if (("bin").equals(bin_path)) {//手动启动Tomcat时获取路径为：D:\Software\Tomcat-8.5\bin
            //获取保存上传图片的文件路径
            pic_path = tomcat_path.substring(0, System.getProperty("catalina.home").lastIndexOf("/")) + "/webapps/";
        } else {//服务中自启动Tomcat时获取路径为：D:\Software\Tomcat-8.5
            pic_path = tomcat_path + "/webapps/";
        }
        
        
        Calendar cale = null;  
       cale = Calendar.getInstance();  
       String year = String.valueOf(cale.get(Calendar.YEAR));  
       String month = String.valueOf(cale.get(Calendar.MONTH) + 1); 
        String saveFilePath = dicName+"/"+year+"/"+month+"/";
        /* 构建文件目录 */
        File fileDir = new File(pic_path + saveFilePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        // 上传的文件名
        String filename = filedata.getOriginalFilename();
        // 文件的扩张名
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
        try {
        	String uuid=UUID.randomUUID().toString().replaceAll("-", "");
            String imgPath = saveFilePath + uuid + "." + extensionName;
            
            String url2=request.getScheme()+"://"+ request.getServerName()+":"+ request.getLocalPort();//+request.getRequestURI();
            String imgPath1=url2+"/"+imgPath;
            
            // System.out.println(pathval + imgPath);打印图片位置
            FileOutputStream out = new FileOutputStream(pic_path + imgPath);
            // 写入文件
            out.write(filedata.getBytes());
            out.flush();
            out.close();
            return imgPath1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
}