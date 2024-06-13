package com.yihe.web.controller.system;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yihe.web.controller.tool.QiNiuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.ibm.icu.util.Calendar;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.system.domain.YhSysConfig;
import com.yihe.system.domain.YhUserFile;
import com.yihe.system.domain.YhUserFolderWeb;
import com.yihe.system.service.YhSysConfigService;
import com.yihe.system.service.YhUserFileService;
import com.yihe.system.utils.FileUtils;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.X509TrustUtil;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
/**
 * 上传附件控制器
 * @author 阳雄
 * 2020年6月1日16:52:31
 *
 */ 
@Controller
@RequestMapping("/YhUserFile")
public class YhUserFileController {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(YhUserAdminInfoController.class);
	
	@Autowired
	YhUserFileService  yhUserFileService;
    @Autowired
	 private YhSysConfigService yhSysConfigService;
	
	@ApiOperation(value = "查询用户存储的数据")
    @RequestMapping(value = "/selectAlldata", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult selectFileByParam(String userCode) {
 		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
 		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Date> dateList = yhUserFileService.selectfilCreateDate(userCode);
		YhUserFile yhUserFile = new YhUserFile();
		yhUserFile.setUserCode(userCode);
		for (Date date : dateList) {
			yhUserFile.setFileCreateDate(date);
			List<YhUserFile> FileByParam = yhUserFileService.selectFileByParam(yhUserFile);	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("date", sdf.format(date));
			map.put("files", FileByParam);
			result.add(map);
		}
		
		 return AjaxResult.success(result);
	}
	@ApiOperation(value = "分页查询文件")
    @ResponseBody
    @RequestMapping(value="/UserFilePage",method=RequestMethod.GET)
    public Map<String,Object> queryYhUserFile(String userCode,String fileParentFolder,int page,int limit){
       //查询集合
       PageInfo<YhUserFile> data = yhUserFileService.queryUserByCriteriaWithPage(userCode,fileParentFolder,page, limit);
       Map<String,Object> result = new HashMap<String, Object>();
       result.put("data",data.getList());
       result .put("count", data.getTotal());
       result.put("msg", "");
       result.put("code", 20000);
          return result;
    }
	@RequestMapping(value = "/deletefile")
	@ResponseBody
	public Result deletefile(Long fileId) {
		 int deleteByPrimaryKey2 = yhUserFileService.deleteByPrimaryKey(fileId);
		return Result.ok(deleteByPrimaryKey2);
		
	}
	@RequestMapping(value = "/selectFileName")
	@ResponseBody
	public Result selectFileName(String userCode, String fileName) {
		YhUserFile  name = yhUserFileService.selectFileName(userCode, fileName);
		return Result.ok(name);
		
	}
	
	@RequestMapping(value = "/selectFileId")
	@ResponseBody
	public Result selectFileId(Long fileId) {
		YhUserFile  YhUserFile = yhUserFileService.selectByPrimaryKey(fileId);
		return Result.ok(YhUserFile);
		
	}
	
	@RequestMapping(value = "/updateFileName")
	@ResponseBody
	public Result updateFileName(YhUserFile yhUserFile) {
		yhUserFile.setFileCreateDate(new Date());
			int keySelective = yhUserFileService.updateByPrimaryKeySelective(yhUserFile);
		return Result.ok(keySelective);
		
	}
	
	
	@RequestMapping("/downloadFile")
	@ResponseBody
	public void downloadExcel(Long fileId,HttpServletResponse response,HttpServletRequest request) {
		try {
		// 根据文件ID查找文件信息
			YhUserFile fileApp = yhUserFileService.selectByPrimaryKey(fileId);
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
	
	@ResponseBody
	@RequestMapping("/fileUpload2")
	public Map<String,Object> upload(HttpServletRequest request,@RequestParam(value="file") MultipartFile file,YhUserFile yhUserFile) throws IOException {
	System.out.println(yhUserFile.getFileParentFolder());
	request.setCharacterEncoding("UTF-8");
	LOGGER.info("执行文件上传");
	 DecimalFormat df = new DecimalFormat("#.00");
	 String fileSizeString = "";
	Map<String,Object> map = new HashMap<String, Object>();
	if(!file.isEmpty()) {
	  LOGGER.info("成功获取文件");
	  //创建文件名
	   String fileName = file.getOriginalFilename();
	   LOGGER.info("文件名"+fileName);
	 //获取文件大小
	   long size = file.getSize();
	   LOGGER.info("获取文件大小"+size);
	   if (size != 0) {
		   if (size < 1024) {
			   fileSizeString = df.format((double) size) + "B";
	       } else if (size < 1048576) {
	    	   fileSizeString = df.format((double) size / 1024) + "KB";
	       } else if (size < 1073741824) {
	    	   fileSizeString = df.format((double) size / 1048576) + "MB";
	       } else {
	    	   fileSizeString = df.format((double) size / 1073741824) + "GB";
	       }
       }
      System.out.println("文件大小"+fileSizeString);
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
       String   path2 = realPath + "uploads" + File.separator + trueFileName;
       LOGGER.info("存放文件:" + path2);
       LOGGER.info("存放图片文件的路径:" + path);
       file.transferTo(new File(path));
       LOGGER.info("文件成功上传到指定目录下");
       //保存数据到数据库
       yhUserFile.setFileCreateDate(new Date());
       if(type=="jpg" || type=="png" || type=="JPG") {
    	   yhUserFile.setFileType(2);
       }else {
    	   yhUserFile.setFileType(1);
       }
       yhUserFile.setFileName(fileName);
       yhUserFile.setFileSize(fileSizeString);
       yhUserFile.setFileUrl(path);
       yhUserFileService.insertSelective(yhUserFile);
       
       map.put("msg","ok");
       map.put("code",0);
	}
	return map;
}
	
	/**
     * TODO 下载文件到本地
     *
     * @param fileUrl   远程地址
     * @param fileLocal 本地路径
     * @throws Exception
     */
	@ResponseBody
	@RequestMapping("/downloadFile2")
    public static void downloadFile(String fileUrl) throws Exception {
		/*	File file=new File("C:\\file2");
			if(!file.exists()){//如果文件夹不存在
				file.mkdir();//创建文件夹
			}try{//异常处理
				//如果Qiju_Li文件夹下没有Qiju_Li.txt就会创建该文件
				BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\file2"));
				bw.write("Hello I/O!");//在创建好的文件中写入"Hello I/O"
				bw.close();//一定要关闭文件
			}catch(IOException e){
				e.printStackTrace();
			}*/
    	  // 保存路径
        String realPath = System.getProperty("user.dir");
 	   // 设置存放图片文件的路径
        //path = realPath + File.separator + "uploads" + File.separator + trueFileName;
        String   fileLocal = realPath + File.separator + "uploads";
        LOGGER.info("存放图片文件的路径:" + fileLocal);
        SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
        sslcontext.init(null, new TrustManager[] { new X509TrustUtil() }, new java.security.SecureRandom());
        URL url = new URL(fileUrl);
        HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
            public boolean verify(String s, SSLSession sslsession) {
                System.out.println("WARNING: Hostname is not matched for cert.");
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
        HttpsURLConnection urlCon = (HttpsURLConnection) url.openConnection();
        urlCon.setConnectTimeout(6000);
        urlCon.setReadTimeout(6000);
        int code = urlCon.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new Exception("文件读取失败");
        }
        // 读文件流
        DataInputStream in = new DataInputStream(urlCon.getInputStream());
        DataOutputStream out = new DataOutputStream(new FileOutputStream(fileLocal));
        byte[] buffer = new byte[2048];
        int count = 0;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        out.close();
        in.close();
    }

	/**
	 * @Author：
	 * @Description：获取某个目录下所有直接下级文件，不包括目录下的子目录的下的文件，所以不用递归获取
	 * @Date：
	 */
	public static List<String> getFiles(String path) {
		List<String> files = new ArrayList<String>();
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				System.out.println("第"+i+1+"张");
				files.add(tempList[i].toString());
				//文件名，不包含路径
				String fileName = tempList[i].getName();
				System.out.println(path+"\\"+fileName);
				QiNiuUtil.upload(path+"\\"+fileName);

				File tmpFile=new File(path+"\\"+fileName);
				String fileName2=tmpFile.getName();
				String foodid = fileName2.substring(0, fileName2.lastIndexOf("."));


			}
			if (tempList[i].isDirectory()) {
				//这里就不递归了，
			}
		}
		return files;
	}

	public static void main(String[] args) {
		getFiles("E:\\foodImg");

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
    public Map<String,Object> upload(@RequestParam("file") MultipartFile files,HttpServletRequest request,YhUserFile yhUserFile) throws IOException {
    	Map<String, Object> map=new HashMap<String, Object>();
		YhSysConfig conf=yhSysConfigService.showConfig();
		String picurl="";
		//获取文件类型
		   String type = null;
		String newFileName=files.getOriginalFilename(); 
		 type = newFileName.indexOf(".") != -1 ? newFileName.substring(newFileName.lastIndexOf("."), newFileName.length()) : null;
		if(conf==null) {//等于0调用本地上传图片
    	  String dicName="UpFile";//服务器上传文件夹名称
          newFileName=newFileName.substring(0,newFileName.lastIndexOf("."));
           picurl = saveFile(files,newFileName,dicName, request);
           yhUserFile.setFileUrl(picurl);
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
		String key = UUID.randomUUID().toString().replaceAll("\\-", "")+ type;
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
		        	 yhUserFile.setFileUrl(img);
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
      //创建文件名
 	   String fileName = files.getOriginalFilename();
 	   type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
        String fileSizeString = "";
        
        DecimalFormat df = new DecimalFormat("#.00");
   	 //获取文件大小
 	   long size = files.getSize();
 	   LOGGER.info("获取文件大小"+size);
 	   if (size != 0) {
 		   if (size < 1024) {
 			   fileSizeString = df.format((double) size) + "B";
 	       } else if (size < 1048576) {
 	    	   fileSizeString = df.format((double) size / 1024) + "KB";
 	       } else if (size < 1073741824) {
 	    	   fileSizeString = df.format((double) size / 1048576) + "MB";
 	       } else {
 	    	   fileSizeString = df.format((double) size / 1073741824) + "GB";
 	       }
        }
       System.out.println("文件大小"+fileSizeString);
     //保存数据到数据库
       yhUserFile.setFileCreateDate(new Date());
       if("jpg".equals(type) || "png".equals(type) || "JPG".equals(type)) {
    	   yhUserFile.setFileType(2);
       }else if("mp4".equals(type) || "mp3".equals(type)){
    	   yhUserFile.setFileType(3);
       }else {
    	   yhUserFile.setFileType(1);
       }
       yhUserFile.setFileName(fileName);
       yhUserFile.setFileSize(fileSizeString);
      
       yhUserFileService.insertSelective(yhUserFile);
        map.put("msg","ok");
        map.put("code",0);
//        result.setResulttype(OperationResultTypeEnum.SUCCESS);
//        result.setMsg("上传成功");
//        result.setAppenddata(picurl);
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
	
	
}
