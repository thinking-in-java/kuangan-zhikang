package com.yihe.web.controller.system;

import com.google.gson.Gson;
import com.ibm.icu.util.Calendar;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yihe.common.core.controller.BaseController;
import com.yihe.common.utils.poi.ExcelUtilQ;
import com.yihe.system.domain.*;
import com.yihe.system.mapper.YhEventRegisterMapper;
import com.yihe.system.mapper.YhOrgTreeMapper;
import com.yihe.system.service.YhEventEnrollService;
import com.yihe.system.service.YhSysConfigService;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * YhEventEnrollcontroller
 * Created by dong on 2020/3/24.
 */
@Controller
@RequestMapping("/YhEventEnroll")
@Api(value = "YhEventEnroll控制器", description = "YhEventEnroll管理")
public class YhEventEnrollController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YhEventEnrollController.class);
	
    @Autowired
	 private YhEventEnrollService yhEventEnrollService;

    @Autowired
	 private YhSysConfigService yhSysConfigService;
    
    @Autowired
	 private YhEventRegisterMapper yhEventRegisterMapper;
	@Autowired
	private YhOrgTreeMapper yhOrgTreeMapper;
    
    
    /**
     * 导出精彩活动报名
     * @param request
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping("/exportOperateLog")
    @ResponseBody
    public void exportOperateLog(HttpServletRequest request, HttpServletResponse response,int id) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        response.reset();
        response.setContentType("text/html;charset=GBK");// 设置response内容的类型
		response.setHeader("Content-disposition", "attachment;filename=SysteamLog" + dateFormat.format(new Date()) + ".xls;");
		HSSFWorkbook workbook = new HSSFWorkbook();
		List<List<String>> titles = new ArrayList<List<String>>();
		List<String> titi = Arrays.asList("用户编码", "用户姓名", "年龄", "手机号码", "性别", "报名时间");
		titles.add(titi);
		
		YhEventEnroll enroll=yhEventEnrollService.selectByPrimaryKey(id);
		
		List<YhEventRegister> list=yhEventRegisterMapper.showActivityExport(id);
		
		HSSFSheet sheet = workbook.createSheet(enroll.getEventName());
		ExcelUtilQ.createTitle(workbook,sheet,titi,0);

		int j=0;
		// 循环表数据
		for (YhEventRegister yhEventRegister : list) {
			j++;
			HSSFRow row = sheet.createRow(j);
			//row.setHeightInPoints(18);
			row.createCell(0).setCellValue(yhEventRegister.getUserCode());
			row.createCell(1).setCellValue(yhEventRegister.getUserName());
			row.createCell(2).setCellValue(yhEventRegister.getAge());
			row.createCell(3).setCellValue(yhEventRegister.getPhone());
			if(yhEventRegister.getGender()==1) {
				row.createCell(4).setCellValue("男");
			}else {
				row.createCell(4).setCellValue("女");
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(yhEventRegister.getRegisterDate());
			row.createCell(5).setCellValue(dateString);
		}
		 String fileName = "精彩活动（"+enroll.getEventName()+"）报名导出-"+dateFormat.format(new Date())+".xls";// 设置要导出的文件的名字
		ExcelUtilQ.buildExcelDocument(fileName,workbook, response);
    }
        
        
    
   
    
    @ApiOperation(value = "查询活动名称是否存在")
	@RequestMapping(value = "/selctEventNmaeRepetition", method = RequestMethod.GET)
	@ResponseBody
	public int selctEventNmaeRepetition(String eventName,int id) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("eventName", eventName);
		map.put("id", id);
		int show = yhEventEnrollService.selctEventNmaeRepetition(map);
		return show;
	}
    
    
    @ApiOperation(value = "增加精彩活动")
    @RequestMapping(value = "/enrolladd", method = RequestMethod.POST)
	@ResponseBody
    public Result clientadd(YhEventEnroll roll) throws ParseException {
    	String data=roll.getShij();
    	if(data!=null && !data.equals("")) {
    		String[] arr=data.split("到");
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    		Date utilDate = sdf.parse(arr[0]);
    		roll.setStartDate(utilDate);
    		Date utilDate1 = sdf.parse(arr[1]);
    		roll.setRegisterEndDate(utilDate1);
    	}
    	Date date=new Date();
    	roll.setPublicDate(date);
    	if(roll.getEventImg()==null || roll.getEventImg().equals("")) {
    		roll.setEventType(2);
    	}
    	int i;
    	if(roll.getId()==0) {
    		//查询当前添加精彩活动用户的机构编号
			Subject subject = SecurityUtils.getSubject();
			YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
			String orgCode = yh.getOrgCode();
			roll.setOrgCode(orgCode);
    		i=yhEventEnrollService.insertSelective(roll);
    	}else {
    		i=yhEventEnrollService.updateByPrimaryKeySelective(roll);
    	}
        return Result.ok(i);
    }
    
    
    @ApiOperation(value = "精彩活动删除")
    @RequestMapping(value = "/clientdele", method = RequestMethod.POST)
	@ResponseBody
    public Result clientdele(int id){
    	int i=yhEventEnrollService.deleteByPrimaryKey(id);
        return Result.ok(i);
    }
    
    
    @ApiOperation(value = "根据id查询精彩活动")
    @RequestMapping(value = "/clientselect", method = RequestMethod.POST)
	@ResponseBody
    public Result clientselect(int id){
    	YhEventEnroll i=yhEventEnrollService.selectByPrimaryKey(id);
        return Result.ok(i);
    }
    
    @ApiOperation(value = "上传精彩活动图片")
    @RequestMapping(value = "/addEnrollImg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> showlive(@RequestParam(value="file") MultipartFile file,HttpServletRequest request) throws IOException{
    	Map<String, Object> map=new HashMap<String, Object>();
		YhSysConfig conf=yhSysConfigService.showConfig();
		if(conf==null) {//等于0调用本地上传图片
			String dicName="UpFile";//服务器上传文件夹名称
			String newFileName=file.getOriginalFilename();
			newFileName=newFileName.substring(0,newFileName.lastIndexOf("."));
			String files=saveFile(file,newFileName,dicName, request);
			System.out.println(files);
			map.put("files", files);
		}else{
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
						map.put("files", img);
						System.out.println("http://"+(String) object.get("qiniuDomain")+"/"+putRet.key);
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
     * 查询所有精彩活动
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "查询所有精彩活动")
    @RequestMapping(value = "/selectEnrollAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> selectAll(Integer page,Integer limit,String eventName,String eventType,String center){
		Map<String,Object> map1 = new HashMap<String,Object>();
		//查询当前用户属于哪个机构
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		String orgCode = yh.getOrgCode();
		//查询它下面的所有子机构编号
		List<String> yhOrgTreeCode = yhOrgTreeMapper.selectallOrgLowerLevel(orgCode);

		Pages pa = new Pages();
		pa.setCurPage(page);
		pa.setMaxResult(limit);
		map1.put("page", pa.getFirstRows());
		map1.put("eventName", eventName);
		map1.put("limit", pa.getMaxResult());
		map1.put("eventType", eventType);
		map1.put("center", center);
		map1.put("orgCodes", yhOrgTreeCode);
		List<YhEventEnroll> list = yhEventEnrollService.selectEnrollAll(map1);
		for (YhEventEnroll yhEventEnroll : list) {
			yhEventEnroll.setTotal(yhEventRegisterMapper.showCountEvent(yhEventEnroll.getId()));
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", 20000);
		map.put("msg", "");
		map.put("count",yhEventEnrollService.selectEnrollAllConut(map1));
		map.put("data", list);
		return map;
	}

}