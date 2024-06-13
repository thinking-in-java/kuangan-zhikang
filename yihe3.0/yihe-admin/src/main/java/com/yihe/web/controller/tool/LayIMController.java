package com.yihe.web.controller.tool;


import com.google.gson.Gson;
import com.ibm.icu.util.Calendar;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yihe.system.domain.YhSysConfig;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.layim.JsonResult;
import com.yihe.system.domain.layim.LayuiResult;
import com.yihe.system.service.LayIMService;
import com.yihe.system.service.YhSysConfigService;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/layim")
public class LayIMController{

	@Autowired
	private YhSysConfigService yhSysConfigService;
    @Autowired
    private  LayIMService layIMService;

	/**
	 * 判断文件大小
	 *
	 * @param len
	 *            文件长度
	 * @param size
	 *            限制大小
	 * @param unit
	 *            限制单位（B,K,M,G）
	 * @return
	 */
	public static boolean checkFileSize(Long len, int size, String unit) {
//        long len = file.length();
		double fileSize = 0;
		if ("B".equals(unit.toUpperCase())) {
			fileSize = (double) len;
		} else if ("K".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1024;
		} else if ("M".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1048576;
		} else if ("G".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1073741824;
		}
		if (fileSize > size) {
			return false;
		}
		return true;
	}
	private String size2string(long size){
		DecimalFormat df = new DecimalFormat("0.00");
		String mysize = "";
		if( size > 1024*1024){
			mysize = df.format( size / 1024f / 1024f ) +"M";
		}else if( size > 1024 ){
			mysize = df.format( size / 1024f ) +"K";
		}else{
			mysize = size + "B";
		}
		return mysize;
	}
	@PostMapping("file")
	public LayuiResult getFileData(@RequestParam("file") MultipartFile file, HttpServletRequest request)throws IOException {
		Map<String, Object> map=new HashMap<String, Object>();

		// 文件不大于100M
		boolean m = checkFileSize(file.getSize(), 10, "M");
		if(!m){
			return LayuiResult.error("文件超过最大限制10M");
		}

		YhSysConfig conf=yhSysConfigService.showConfig();
		String files="";
		if(conf==null) {//等于0调用本地上传图片
			String dicName="UpFile";//服务器上传文件夹名称
			String newFileName=file.getOriginalFilename();
			newFileName=newFileName.substring(0,newFileName.lastIndexOf("."));
			files= saveFile(file,newFileName,dicName, request);

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
				String s = size2string(file.getSize());
				String fileInfoName="yhyfileStr/"+file.getOriginalFilename()+"yhyfileEnd/";
				fileInfoName=fileInfoName+"sizeStr/"+s+"sizeEnd/";
			String	type = file.getOriginalFilename().indexOf(".") != -1 ? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length()) : null;
				try {
					Response response = uploadManager.put(ins,fileInfoName+key+type,upToken,null, null);
					//解析上传成功的结果
					DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					if(file.getSize()==0){
						//news.setImg("");
					}else{
						files=(String) object.get("qiniuDomain")+"/"+putRet.key;

						System.out.println((String) object.get("qiniuDomain")+"/"+putRet.key);
						map.put("src", (String) object.get("qiniuDomain")+"/"+putRet.key);
						map.put("name", file.getOriginalFilename());
						map.put("size", s);
					}

				} catch (QiniuException ex) {
					Response r = ex.response;
					System.err.println(r.toString());
				}
				//sysuser=1;
			} catch (Exception ex) {
				//sysuser=0;
				//ignore
				map.put("src", "");
				map.put("name", "");
			}

		}


		return LayuiResult.ok().data(map);
	}
	@PostMapping("image")
	public LayuiResult getImageData(@RequestParam("file") MultipartFile file, HttpServletRequest request)throws IOException {
		Map<String, Object> map=new HashMap<String, Object>();
		YhSysConfig conf=yhSysConfigService.showConfig();
		String files="";
		if(conf==null) {//等于0调用本地上传图片
			String dicName="UpFile";//服务器上传文件夹名称
			String newFileName=file.getOriginalFilename();
			newFileName=newFileName.substring(0,newFileName.lastIndexOf("."));
			files= saveFile(file,newFileName,dicName, request);

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
						files=(String) object.get("qiniuDomain")+"/"+putRet.key;

						System.out.println((String) object.get("qiniuDomain")+"/"+putRet.key);

						map.put("src", (String) object.get("qiniuDomain")+"/"+putRet.key);
					}

				} catch (QiniuException ex) {
					Response r = ex.response;
					System.err.println(r.toString());
				}
				//sysuser=1;
			} catch (Exception ex) {
				//sysuser=0;
				//ignore
				map.put("src", "");
			}

		}


		return LayuiResult.ok().data(map);
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


		com.ibm.icu.util.Calendar cale = null;
		cale = com.ibm.icu.util.Calendar.getInstance();
		String year = String.valueOf(cale.get(com.ibm.icu.util.Calendar.YEAR));
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
     * LayIM初始化
     * */
    @GetMapping("init")
    public JsonResult getInitData(HttpServletRequest request){
    	Subject subject = SecurityUtils.getSubject();
    	YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
        return layIMService.getInitResult(principal.getUserCode());
    }

    /**
     * 获取群员列表
     * */
    @GetMapping("member")
    public JsonResult getGroupMembers(String id){
        return layIMService.getMembersByGroupId(id);
    }

    /**
     * 获取消息记录
     * */
    @GetMapping("chat/history")
    public JsonResult getChatHistory(HttpServletRequest request,String id,String type,Integer lastId){
    	Subject subject = SecurityUtils.getSubject();
    	YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
    	
    	if("friend".equals(type)) {
    		 return layIMService.getChatRecords(principal.getUserCode() ,id,1,lastId);
    	}else {
    		 return layIMService.getChatRecords(principal.getUserCode() ,id,2,lastId);
    	}
      
    }
    
   


	@ApiOperation(value = "")
	@RequestMapping(value = "chat/history2", method = RequestMethod.GET)
	public JsonResult history3(String id,String type,Integer lastId) {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();

		if("friend".equals(type)) {
			return layIMService.getChatRecords(principal.getUserCode() ,id,1,lastId);
		}else {
			return layIMService.getChatRecords(principal.getUserCode() ,id,2,lastId);
		}
	}
    
}
