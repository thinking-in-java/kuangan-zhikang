package com.yihe.applet.api.controller;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yihe.applet.api.domain.YhSysConfigApp;
import com.yihe.applet.api.domain.YhUserFileApp;
import com.yihe.applet.api.service.YhSysConfigAppService;
import com.yihe.applet.api.service.YhUserFileAppService;
import com.yihe.system.domain.YhUserFile;
import com.yihe.system.utils.FileUtils;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qiniu.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;
/**
 * YhUserFilecontroller Created by yx on 2020/5/28.
 * @param <R>
 * @param <E>
 * @param <V>
 */
@Controller
@RequestMapping("/api/YhUserFile")
@Api(value = "YhUserFile控制器", description = "YhUserFile管理")
public class YhUserFileController<R>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserFileController.class);

	
	@Autowired
	 private YhSysConfigAppService yhSysConfigService;
	@Autowired
	  YhUserFileAppService yhUserFileService;
	
	
	@ApiOperation(value = "查询文件")
    @RequestMapping(value = "/selectfile", method = RequestMethod.POST)
	@ResponseBody
	public YhUserFileApp selectByPrimaryKey(Long fileId) {
		YhUserFileApp yhUserFileApp = yhUserFileService.selectByPrimaryKey(fileId);
		return yhUserFileApp;
		
	}
	
	 @ApiOperation(value = "上传图片/视频")
	    @RequestMapping(value = "/upload", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showlive(@RequestParam(value="content") MultipartFile content,HttpServletRequest request) throws IOException{
	    	Map<String, Object> map=new HashMap<String, Object>();
			YhSysConfigApp conf=yhSysConfigService.selectYhSysConfig();
			  //先把String对象转换成json对象
	        JSONObject object=JSONObject.fromObject(conf.getValue());
	        String userCode = request.getParameter("userCode");
	        LOGGER.info("用户code"+userCode);
			//MultipartFile转换File完整版
		    InputStream ins = content.getInputStream();
		    //获取文件名文件名
			   String fileName = content.getOriginalFilename();
			 //获取文件大小
			   long size = content.getSize();
			   LOGGER.info("获取文件大小"+size);
			   String path = null;
			   //获取文件类型
			   String type = null;
			   //创建时间
			   Date date = new Date();
			   LOGGER.info("获取创建时间"+date);
			   type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
			   LOGGER.info("图片初始名称为：" + fileName + " 类型为：" + type);
		    
		    
		    //构造一个带指定Zone对象的配置类
			Configuration cfg = new Configuration(Zone.autoZone());
			//...其他参数参考类注释
			UploadManager uploadManager = new UploadManager(cfg);
			//...生成上传凭证，然后准备上传
			String accessKey = (String) object.get("qiniuAccessKey");
			String secretKey = (String) object.get("qiniuSecretKey");
			String bucket = (String) object.get("qiniuBucketName");
//			//默认不指定key的情况下，以文件内容的hash值作为文件名
			String key = UUID.randomUUID().toString().replaceAll("\\-", "");
			try {
			    Auth auth = Auth.create(accessKey, secretKey);
			    String upToken = auth.uploadToken(bucket);
			    try {
			        Response response = uploadManager.put(ins,key,upToken,null, null);
			        //解析上传成功的结果
			        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
			        
			        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			        if(content.getSize()==0){
			        	 //news.setImg("");
			        }else{
			        	String img=(String) object.get("qiniuDomain")+"/"+putRet.key;
			        	map.put("files", img);
			        	map.put("fileName", fileName);
			        	map.put("size", size);
			        	map.put("type", type);
			        	map.put("userCode", userCode);
			        	map.put("date", date);
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
				map.put("fileName", "");
				map.put("size", "");
				map.put("type", "");
				map.put("userCode", "");
				map.put("date", "");
			}				
			
			return map;
		}
	 
	 
	 /*
	　　　　普通上传
	　　　　multipartFile : form表单传过来的文件,
	　　　　key: 文件名
	　　*/
	 @ApiOperation(value = "上传文件")
	    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
		@ResponseBody
	    public Map<String, Object>  upload(@RequestParam(value="content") MultipartFile content,HttpServletRequest request) throws IOException{
	    	Map<String, Object> map=new HashMap<String, Object>();
			YhSysConfigApp conf=yhSysConfigService.selectYhSysConfig();
			 String userCode = request.	getParameter("userCode");
			 String fileName = request.getParameter("fileName"); 
	  	  //先把String对象转换成json对象
	        JSONObject object=JSONObject.fromObject(conf.getValue());
	    	String ACCESS_KEY = (String) object.get("qiniuAccessKey");
			String SECRET_KEY = (String) object.get("qiniuSecretKey");
			String bucketname = (String) object.get("qiniuBucketName");
	        // 密钥配置
	        Auth auth = Auth.create(ACCESS_KEY,SECRET_KEY);
	        //创建上传对象
	    	Configuration configuration = new Configuration(Zone.autoZone());
	        UploadManager uploadManager = new UploadManager(configuration);
	 
	        String result = "";
	       // Response response = uploadManager.put(ins,key,upToken,null, null);
	      //解析上传成功的结果
	      //创建时间
			   Date date = new Date();
	     
	      //MultipartFile转换File完整版
		    InputStream byteInputStream = content.getInputStream();
	        try {
	            //调用put方法上传
	            Response res = uploadManager.put(byteInputStream,fileName,auth.uploadToken(bucketname),null, null);
	            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
	            String files=(String) object.get("qiniuDomain")+"/"+putRet.key;
	            map.put("files", files);
	            map.put("date", date);
	            //打印返回的信息
	            result = fileName;
	        } catch (QiniuException e) {
	            e.printStackTrace();
	            // 请求失败时打印的异常的信息
	            result = "no";
	        }
	        return  map;
	 
	    }
	 
	
	 
	 
	 
	 	@ApiOperation(value = "添加文件")
	    @RequestMapping(value = "/addfile", method = RequestMethod.POST)
		@ResponseBody
		public Result addfile(YhUserFileApp yhUserFile) {
	 		yhUserFile.setFileCreateDate(new Date());
			 int adddate = yhUserFileService.insertSelective(yhUserFile);
			 return Result.ok(adddate);
		}
	 	
	 	@ApiOperation(value = "修改文件名")
	    @RequestMapping(value = "/updatefileName", method = RequestMethod.POST)
		@ResponseBody
		public Result updatefileName(YhUserFileApp yhUserFile) {
	 		yhUserFile.setFileCreateDate(new Date());
			 int updatefileName = yhUserFileService.updateByPrimaryKeySelective(yhUserFile);
			 return Result.ok(updatefileName);
		}
	 	
	 	@ApiOperation(value = "查询用户存储的数据")
	    @RequestMapping(value = "/selectAlldata", method = RequestMethod.POST)
		@ResponseBody
		public Result selectFileByParam(String userCode) {
	 		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<Date> dateList = yhUserFileService.selectfilCreateDate(userCode);
			YhUserFileApp yhUserFile = new YhUserFileApp();
			yhUserFile.setUserCode(userCode);
			for (Date date : dateList) {
				yhUserFile.setFileCreateDate(date);
				List<YhUserFileApp> FileByParam = yhUserFileService.selectFileByParam(yhUserFile);	
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("date", sdf.format(date));
				map.put("files", FileByParam);
				result.add(map);
			}
			 
			 return Result.ok(result);
		}
	
		@ApiOperation(value = "根据id删除")
	    @RequestMapping(value = "/delectImg", method = RequestMethod.POST)
		@ResponseBody
		public Result delectImg(Integer fileId) {
			 int del = yhUserFileService.delete(fileId);
			 return Result.ok(del);
		}
		@ApiOperation(value = "查询用户文件内容")
	    @RequestMapping(value = "/selectUserFile", method = RequestMethod.POST)
		@ResponseBody
		public Result selectUserFile(Integer page,Integer pageSize,String fileParentFolder, String userCode) {
			  List<YhUserFileApp> list = yhUserFileService.selectUserFile( (page-1)*pageSize,pageSize,fileParentFolder, userCode);
			 return Result.ok(list);
		}
		
		@ApiOperation(value = "模糊查询")
	    @RequestMapping(value = "/selectFileByName", method = RequestMethod.POST)
		@ResponseBody
		public Result selectUserFileByParam(Integer page,Integer pageSize,String fileParentFolder, String userCode,String fileName) {
			  List<YhUserFileApp> list = yhUserFileService.selectUserFileByParam( (page-1)*pageSize,pageSize,fileParentFolder, userCode,fileName);
			  System.out.println(list);
			 return Result.ok(list);
		}
	
	
		
		@RequestMapping("/downloadFile")
		@ResponseBody
		public void downloadExcel(Long fileId,HttpServletResponse response,HttpServletRequest request) {
			try {
			// 根据文件ID查找文件信息
				YhUserFileApp fileApp = yhUserFileService.selectByPrimaryKey(fileId);
				System.out.println(fileId);
			// ------------------
			// 得到文件路径 取数据库
			String url = fileApp.getFileUrl();
			// 取得文件名。 取数据库
            String realName = fileApp.getFileName();
            String fileUrl = url;
            System.out.println("数据库地址"+fileUrl);
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(fileUrl));
				//vnd.ms-excel浏览器返回的类型
	        FileUtils.downloadFile(request, response, fis, "application/octet-stream", realName);
	        } catch (Exception ex) {
	        	LOGGER.error("下载失败，原因是：", ex);
	        }

		}

		/**
		 * 上传
		 * @return
		 * @throws IOException
		 */
		
			@ResponseBody
			@RequestMapping("/fileUpload2")
			public String upload(HttpServletRequest request,@RequestParam(value="file") MultipartFile file,YhUserFileApp yhUserFileApp) throws IOException {
			System.out.println(yhUserFileApp);
			request.setCharacterEncoding("UTF-8");
			LOGGER.info("执行文件上传");
			
			if(!file.isEmpty()) { 
			  LOGGER.info("成功获取文件");
			  //创建文件名
			  // String fileName = file.getOriginalFilename();
			  LOGGER.info("文件名3"+file.getOriginalFilename());
			  
			 //获取文件大小
			   long size = file.getSize();
			   LOGGER.info("获取文件大小"+size);
			
			String  fileName = yhUserFileApp.getFileName();
			
			   LOGGER.info("文件名"+fileName);
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
	           yhUserFileApp.setFileCreateDate(new Date());
	           yhUserFileApp.setFileUrl(path);
	           yhUserFileApp.setFileName(fileName);
	           yhUserFileService.insertSelective(yhUserFileApp);
	          
			}
			return "success";
		}
		
	
	
}
