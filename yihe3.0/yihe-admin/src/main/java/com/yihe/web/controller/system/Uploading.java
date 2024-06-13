/**
 * 
 */
package com.yihe.web.controller.system;

import com.google.gson.Gson;
import com.ibm.icu.util.Calendar;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yihe.system.domain.YhSysConfig;
import com.yihe.system.service.YhSysConfigService;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 上传文件
 * @author dong
 *
 */
@Controller
@RequestMapping("/Uploading")
public class Uploading {
	
    @Autowired
	 private YhSysConfigService yhSysConfigService;
	
    
    
    /**
     * 
     * @Title:图片上传返回路径   
     * @Description
     * @param MultipartFile，request
     * @return  服务器保存的图片地址url
     * @throws
     */
//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String,Object> upload(@RequestParam("file") MultipartFile files,HttpServletRequest request) {
//      //  OperationResult result = new OperationResult(OperationResultTypeEnum.ERROR, "增加记录失败");
//        String dicName="UpFile";//服务器上传文件夹名称
//        String newFileName=files.getOriginalFilename();    
//        newFileName=newFileName.substring(0,newFileName.lastIndexOf("."));
//        String picurl = saveFile(files,newFileName,dicName, request);
//        Map<String,Object> map = new HashMap<String, Object>();
//        map.put("files", picurl);
////        result.setResulttype(OperationResultTypeEnum.SUCCESS);
////        result.setMsg("上传成功");
////        result.setAppenddata(picurl);
//        return map;
//    }


	//富文本图片上传
	@RequestMapping(value="/upload2")
	@ResponseBody
	public Object upload2(MultipartFile file,HttpServletRequest request) throws IOException {

		Map<String,Object> map2 = new HashMap<String,Object>();
		Map<String, Object> map=new HashMap<String, Object>();

		YhSysConfig conf=yhSysConfigService.showConfig();

		if(conf==null) {//等于0调用本地上传图片
			String dicName="UpFile";//服务器上传文件夹名称
			String newFileName=file.getOriginalFilename();
			newFileName=newFileName.substring(0,newFileName.lastIndexOf("."));
			String picurl = saveFile(file,newFileName,dicName, request);
			map.put("code", 0);	//0表示上传成功
			map.put("msg","上传成功"); //提示消息
			map2.put("src", picurl); //src返回图片上传成功后的下载路径，这里直接给绝对路径
			map2.put("title", "");
			map.put("data", map2);
		}else {
			//先把String对象转换成json对象
			JSONObject object=JSONObject.fromObject(conf.getValue());

			//MultipartFile转换File完整版
			InputStream ins = file.getInputStream();


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
					if(file.getSize()==0){
						//news.setImg("");
					}else{
						String img=(String) object.get("qiniuDomain")+"/"+putRet.key;
						map.put("code", 0);	//0表示上传成功
						map.put("msg","上传成功"); //提示消息
						map2.put("src", img); //src返回图片上传成功后的下载路径，这里直接给绝对路径
						map2.put("title", "");
						System.out.println((String) object.get("qiniuDomain")+"/"+putRet.key);
					}

				} catch (QiniuException ex) {
					Response r = ex.response;
					System.err.println(r.toString());
				}
			} catch (Exception ex) {
				map.put("code", 0);	//0表示上传成功
				map.put("msg","上传失败"); //提示消息
			}


			map.put("data", map2);
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
        String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("/") + 1, tomcat_path.length());
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
            
            // String url2=request.getScheme()+"://"+ request.getServerName()+":"+ request.getLocalPort();//+request.getRequestURI();
            String url2=request.getScheme()+"://"+ request.getServerName();//+request.getRequestURI();
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
	
    
    
  //富文本图片上传
    @RequestMapping(value="/upload")
    @ResponseBody
    public Object upload1(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file,HttpServletRequest request) throws IOException {
    	
    	Map<String,Object> map2 = new HashMap<String,Object>();
    	Map<String, Object> map=new HashMap<String, Object>();
    	
    	YhSysConfig conf=yhSysConfigService.showConfig();
    	
    	if(conf==null) {//等于0调用本地上传图片
    	  String dicName="UpFile";//服务器上传文件夹名称
          String newFileName=file.getOriginalFilename();    
          newFileName=newFileName.substring(0,newFileName.lastIndexOf("."));
          String picurl = saveFile(file,newFileName,dicName, request);
	  		map.put("url", picurl); //src返回图片上传成功后的下载路径，这里直接给绝对路径
	  		map.put("success", 1);//这里还不能返回"1"，即字符串1，好坑啊，不知道为什么...
	  		map.put("message", "上传成功！");
    	}else {
		  //先把String对象转换成json对象
        JSONObject object=JSONObject.fromObject(conf.getValue());
		 
		//MultipartFile转换File完整版
	    InputStream ins = file.getInputStream();
	    
	    
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
		        if(file.getSize()==0){
		        	 //news.setImg("");
		        }else{
		        	String img=(String) object.get("qiniuDomain")+"/"+putRet.key;
		        	
		    		map.put("url", img); //src返回图片上传成功后的下载路径，这里直接给绝对路径
			  		map.put("success", 1);//这里还不能返回"1"，即字符串1，好坑啊，不知道为什么...
			  		map.put("message", "上传成功！");
		        	System.out.println((String) object.get("qiniuDomain")+"/"+putRet.key);
		        }
		       
		    } catch (QiniuException ex) {
		        Response r = ex.response;
		        System.err.println(r.toString());
		    }
		} catch (Exception ex) {
	  		map.put("success", 0);//这里还不能返回"1"，即字符串1，好坑啊，不知道为什么...
	  		map.put("message", "文件上传失败！");
		}		
	
    	}
		return map;
    }
    
    
    
    
	 @ApiOperation(value = "上传图片/视频")
	    @RequestMapping(value = "/addEnrollImg", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> showlive(@RequestParam(value="file") MultipartFile file,HttpServletRequest request) throws IOException{
		 Map<String, Object> map=new HashMap<String, Object>();
		 long size = file.getSize() / 1024; //kb
		 if((size/1024)>300) {
			 map.put("message", "视频大小不能超过300MB");
			 return map;
		 }
	    	
			YhSysConfig conf=yhSysConfigService.showConfig();

			if(conf==null) {//等于0调用本地上传图片
	    	  String dicName="UpFile";//服务器上传文件夹名称
	          String newFileName=file.getOriginalFilename();    
	          newFileName=newFileName.substring(0,newFileName.lastIndexOf("."));
	          String picurl = saveFile(file,newFileName,dicName, request);
	          map.put("files", picurl);
	    	}else {
			  //先把String对象转换成json对象
	        JSONObject object=JSONObject.fromObject(conf.getValue());
			 
			//MultipartFile转换File完整版
		    InputStream ins = file.getInputStream();
		    
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
			        if(file.getSize()==0){
			        	 //news.setImg("");
			        }else{
			        	String img=(String) object.get("qiniuDomain")+"/"+putRet.key;
			        	map.put("files", img);
			        	map.put("message", "上传成功");
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
				map.put("message", "上传失败");
			}			
			
	    	}
			
			
			return map;
		}
	 
	 
	 
	 /**
		 * 七牛上将文件删除
		 * @param fileName
		 * @uesr "xinzhifu@knet.cn"
		 * @date 2016年11月22日下午1:29:13
		 */
		public void delete(String fileName) {
			YhSysConfig conf=yhSysConfigService.showConfig();
			  //先把String对象转换成json对象
	        JSONObject object=JSONObject.fromObject(conf.getValue());
			String accessKey = (String) object.get("qiniuAccessKey");
			String secretKey = (String) object.get("qiniuSecretKey");
			String bucket = (String) object.get("qiniuBucketName");
			Auth auth = Auth.create(accessKey, secretKey);
			Zone z = Zone.zone0();
			Configuration c = new Configuration(z);
			// 实例化一个BucketManager对象
			BucketManager bucketManager = new BucketManager(auth, c);
			// 要测试的空间和key，并且这个key在你空间中存在
			String key = "3cbbd6ceae1844dc9fd4179a11a6456c";
			try {
				// 调用delete方法移动文件
				bucketManager.delete(bucket, key);
				System.out.println("文件删除成功!.[fileName={}]"+fileName);
			} catch (QiniuException e) {
				System.out.println("文件删除失败!.[fileName={}]"+fileName);
				// 捕获异常信息
				Response r = e.response;
				System.out.println(r.toString());
			}
		}
		
}
