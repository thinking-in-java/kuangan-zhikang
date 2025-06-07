package com.yihe.applet.api.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.ibm.icu.util.Calendar;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yihe.applet.api.service.YhUserChatRecordService;
import com.yihe.common.config.Global;
import com.yihe.system.domain.YhSysConfig;
import com.yihe.system.service.YhSysConfigService;
import com.yihe.system.service.YhUserDialogueListService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.applet.api.domain.YhUserChatRecord;
import com.yihe.applet.api.mapper.YhUserChatRecordMapper;
import com.yihe.applet.api.mapper.YhUserDialogueListMapper;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/chatrecord")
@Api(value = "YhUserEcg控制器", description = "YhUserEcg管理")
public class YhUserChatRecordController {
	@Autowired
	private YhUserDialogueListMapper YhUserDialogueListMapper;
	@Autowired
	private YhUserDialogueListService yhUserDialogueListService;
	@Autowired
	private YhUserChatRecordMapper  YhUserChatRecordMapper;
	@Autowired
	private YhSysConfigService yhSysConfigService;
	@Autowired
	YhUserChatRecordService yhUserChatRecordService;

	/**
	 * 健管师端首页列表
	 */
	@RequestMapping(value = "/doctorHomePage")
	@ResponseBody
	public Result doctorHomePage(String doctor,@RequestParam(defaultValue="3") int pagenum){
		if(StringUtil.isEmpty(doctor)){
			return Result.error();
		}
		Map<String,Object> map =new HashMap<>();
		map.put("list",yhUserChatRecordService.latestChat(doctor, pagenum));
		map.put("unread",yhUserChatRecordService.doctorUnreadNum(doctor));

		return Result.ok(map);
	}

	/**
	 * 根据用户查询他的会话列表
	 */
	@RequestMapping(value = "/findChatList")
	@ResponseBody
	public Result findChatList(String usercode) {

		return Result.ok(yhUserDialogueListService.findSessionList(usercode));
	}

	/**
	 * 获取对话列表YhUserDialogueListMapper
	 */
	@RequestMapping(value = "/findList")
	@ResponseBody
	public Result findList(String usercode) {
		return Result.ok(YhUserDialogueListMapper.findListInfo(usercode));
	}
	
	/**
	 * 接收到消息 改变已读状态
	 */
	@RequestMapping(value = "/readInfo")
	@ResponseBody
	public Result readInfo(String usercode,String tousercode) {
		int modifyRead1 = YhUserChatRecordMapper.modifyRead1(usercode.replace(Global.getName(), ""), tousercode.replace(Global.getName(), ""));
		return Result.ok(modifyRead1);
	}

	/**
	 * 获取历史聊天记录
	 */
	@RequestMapping(value = "/findInfoList")
	@ResponseBody
	public Result findInfoList(String roomid,String usercode,String doctor,Integer pagenum,Integer pagesize) {
		List<String> list=new ArrayList<String>();

		String groupByInfo=YhUserChatRecordMapper.findUserGroupByInfo(usercode.replace(Global.getName(), ""));
		if(StringUtil.isNotEmpty(groupByInfo)) {
			String[] split = groupByInfo.split(";");
			if(split.length>0) {
				for (String string : split) {
					if(StringUtil.isNotEmpty(string)) {
						String[] split2 = string.split("_");
						String group=split2[0];
						group = group.substring(1);
						list.add(doctor.replace(Global.getName(), "")+group);
					}
				}
			}
		}
		list.add(roomid.replace(Global.getName(), ""));
		list.add(usercode.replace(Global.getName(), "")+doctor.replace(Global.getName(), ""));
		return Result.ok(yhUserChatRecordService.getChatInfo(list,pagenum,pagesize));
	}
	
	/**
	 * 插入聊天记录YhUserChatRecordMapper.insertMessage(id, to, message);
	 */
	@RequestMapping(value = "/addChatMessage")
	@ResponseBody
	public Result addChatMessage(YhUserChatRecord record) {
		if(null==record){
			return Result.error("参数错误");
		}
		if(StringUtil.isEmpty(record.getUserCode())||StringUtil.isEmpty(record.getToUserCode())||null==record.getMessage()||null==record.getMessageType()){
			return Result.error("参数错误");
		}
		int insertSelective = yhUserChatRecordService.addChatMessage(record);
		if(insertSelective>0){
			return Result.ok();
		}
		return Result.error("发送错误");
	}
	
	
	/**
	 * 查询用户的医生idfindDoctorIdByUserCode
	 */
	@RequestMapping(value = "/findDoctorId")
	@ResponseBody
	public Result findDoctorId(String usercode) {
		if(StringUtil.isEmpty(usercode)){
			return Result.error("参数错误");
		}
		String doctorid=YhUserChatRecordMapper.findDoctorIdByUserCode(usercode);
		 
		return Result.ok(doctorid);
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
	/**
	 * @throws IOException
	 *
	 * @Title:文件上传返回路径
	 * @Description
	 * @param MultipartFile，request
	 * @return  服务器保存的图片地址url
	 * @throws
	 */
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> uploadfile(@RequestParam("file") MultipartFile files,HttpServletRequest request,String userId) throws IOException {
		Map<String, Object> map=new HashMap<String, Object>();
		YhSysConfig conf=yhSysConfigService.showConfig();

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
				String s = size2string(files.getSize());
				String fileInfoName="yhyfileStr/"+files.getOriginalFilename()+"yhyfileEnd/";
				fileInfoName=fileInfoName+"sizeStr/"+s+"sizeEnd/";
				String	type = files.getOriginalFilename().indexOf(".") != -1 ? files.getOriginalFilename().substring(files.getOriginalFilename().lastIndexOf("."), files.getOriginalFilename().length()) : null;

				try {
					Response response = uploadManager.put(ins,fileInfoName+key+type,upToken,null, null);
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


	/**
	 * @throws IOException
	 *
	 * @Title:图片上传返回路径
	 * @Description
	 * @param  ，request
	 * @return  服务器保存的图片地址url
	 * @throws
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
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


		return Result.ok(files);
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
	 * 查询用户所有分组信息
	 */
	@RequestMapping(value = "/findUserGroupByInfo")
	@ResponseBody
	public Result findUserGroupByInfo(String usercode,String doctor) {
		if(StringUtil.isEmpty(usercode)){
			return Result.error("参数错误");
		}
		List<String> list=new ArrayList<String>();
		
		String groupByInfo=YhUserChatRecordMapper.findUserGroupByInfo(usercode);
		if(StringUtil.isNotEmpty(groupByInfo)) {
			String[] split = groupByInfo.split(";");
			if(split.length>0) {
				for (String string : split) {
					if(StringUtil.isNotEmpty(string)) {
						String[] split2 = string.split("_");
						String group=split2[0];
						group = group.substring(1);
						list.add(Global.getName()+doctor+group);
					}
				}
			}
		}
		
		return Result.ok(list);
	}
	
	
}
