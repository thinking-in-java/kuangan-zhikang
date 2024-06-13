package com.yihe.web.controller.system;

import com.yihe.system.domain.*;
import com.yihe.system.service.YhUserSettingService;
import com.yihe.system.utils.FindChildNodes;
import com.yihe.system.utils.Result;
import io.swagger.annotations.Api;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
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

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/YhUserSetting")
@Api(value = "高级配置控制器", description = "高级配置")
public class YhUserSettingController {
	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserSettingController.class);
	@Autowired
	YhUserSettingService yhusersettingservice;
	//查询SMS信息
	@RequestMapping("/selectallsms")
	@ResponseBody
	public Result selectallsms(){
		List<YhSysConfig> config=new ArrayList<YhSysConfig>();
		config.add(yhusersettingservice.selectyhjzsyssms());
		return Result.ok(config);
	}
	
	//查询SMS信息
		@RequestMapping("/selectorgname")
		@ResponseBody
		public Result selectorgname(){
			return Result.ok(yhusersettingservice.selectorgname());
		}
		
		@RequestMapping("/selectorgname2")
		@ResponseBody
		public Result selectorgname2(){
			return Result.ok(yhusersettingservice.selectorgname2());
		}
		
		@RequestMapping("/updateOrgName")
		@ResponseBody
		public Result updateOrgName(String orgCode, String orgName){
			
			return Result.ok(yhusersettingservice.updateOrgName(orgCode, orgName));
		}
		
	//插入或更新SMS信息
	@RequestMapping("/insertsms")
	@ResponseBody
	public Result insertsms(YhJzSysSms sms){
		int resulet=yhusersettingservice.insertsms(sms);
		if(resulet!=0) {
			return Result.ok(200);
		}else {
			return Result.error();
		}
		
	}
	//插入或更新云储存信息
	@RequestMapping("/insertstore")
	@ResponseBody
	public Result insertstore(YhSysConfig sysconfig) {
		int resulet=yhusersettingservice.insertsysconfig(sysconfig);
		if(resulet!=0) {
			return Result.ok(200);
		}else {
			return Result.error();
		}
	}
	//查询机构下面所有医师
	@RequestMapping("/selectalldoc")
	@ResponseBody
	public Result selectalldoc() {
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//查询当前机构
		 List<YhOrgTree> yhorgtree=yhusersettingservice.selectyhorgtrry(yh.getUserName());
		 //查询所有机构
		 List<YhOrgTree> yhallorg=yhusersettingservice.selectallorg();
		 //创建新的List存储当前所属机构的子节点
		 List<YhOrgTree> children=new ArrayList<YhOrgTree>();
		 children.add(yhorgtree.get(0));
		 FindChildNodes fin=new FindChildNodes();
		 children=fin.getorgbycode(yhorgtree.get(0), yhallorg, children);
		 List<YhUserAdminInfo> admininfo=yhusersettingservice.selectalladmin();
		 List<YhUserAdminInfo> newadmin=new ArrayList<YhUserAdminInfo>();
		 for (int i = 0; i < children.size(); i++) {
			for (int j = 0; j < admininfo.size(); j++) {
				if(children.get(i).getOrgCode().equals(admininfo.get(j).getOrgCode())){
					newadmin.add(admininfo.get(j));
				}
					
			}
		}
		 return Result.ok(newadmin);
	}
	
	//查询注册卡
	@RequestMapping("/selectrefincard")
	@ResponseBody
	public Result selectrefincard(int page,int limit,String guishu,String yishi,String state,@RequestParam String name,String paixuming,String fangfa,String states) throws UnsupportedEncodingException {
		//name=new String(name.getBytes("iso-8859-1"),"utf-8");
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//查询当前机构
		 List<YhOrgTree> yhorgtree=yhusersettingservice.selectyhorgtrry(yh.getUserName());
		 //查询所有机构
		 List<YhOrgTree> yhallorg=yhusersettingservice.selectallorg();
		 //分页查询
		 List<YhMakeRegiCardInfo> cardinfo= yhusersettingservice.selectrefincard(yhorgtree.get(0),page,limit,guishu,yishi,state,name,paixuming,fangfa,states);
		 
		 for (int i = 0; i < cardinfo.size(); i++) {
			 cardinfo.set(i,findorgtpo(cardinfo.get(i), yhallorg)) ;
		}
		return Result.ok(cardinfo);
	}

	//导出注册卡
	@RequestMapping("/derivexls")
	public void derivexls(String guishu, String yishi, String state, @RequestParam String name, String paixuming, String fangfa, String states, HttpServletResponse response) throws Exception {
		//name=new String(name.getBytes("iso-8859-1"),"utf-8");
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//查询当前机构
		List<YhOrgTree> yhorgtree=yhusersettingservice.selectyhorgtrry(yh.getUserName());
		//查询所有机构
		List<YhOrgTree> yhallorg=yhusersettingservice.selectallorg();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("注册卡列表");
		//导出的数据
		List<YhUserReginfo> exportDataList = yhusersettingservice.derivexlscard(yhorgtree.get(0),guishu,yishi,state,name,paixuming,fangfa,states);
		// 设置文件名
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fileName = "注册卡导出" + ".xls";// 设置要导出的文件的名字
		// 新增数据行，并且设置单元格数据
		int rowNum = 1;
		// headers表示excel表中第一行的表头
		String[] headers = { "注册卡号", "归属", "状态","激活码", "用户姓名", "登录账号", "手机号码", "注册时间" };
		// 在excel表中添加表头
		HSSFRow row = sheet.createRow(0);
		// 合并单元格
		// 设置行高
		row.setHeightInPoints(36);
		// 设置列宽度
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 10 * 256);
		sheet.setColumnWidth(2, 10 * 256);
		// 在excel表中添加表头
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			// 设置为居中加粗
			HSSFCellStyle style = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());// 设置背景色
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setBorderBottom(BorderStyle.THIN); // 下边框
			style.setBorderLeft(BorderStyle.THIN);// 左边框
			style.setBorderTop(BorderStyle.THIN);// 上边框
			style.setBorderRight(BorderStyle.THIN);// 右边框
			font.setBold(true);
			style.setFont(font);
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
			cell.setCellStyle(style);
		}
		// 在表中存放查询到的数据放入对应的列
		for (YhUserReginfo yhUserReginfo : exportDataList) {
			// 创建excel中的行
			HSSFRow row1 = sheet.createRow(rowNum);
			// 在表中存放查询到的数据放入对应的列
			row1.createCell(0).setCellValue(yhUserReginfo.getEnrolCardId());
			row1.createCell(1).setCellValue(yhUserReginfo.getOrgCode());
			row1.createCell(2).setCellValue(yhUserReginfo.getUserName() == null ? (yhUserReginfo.getKey() == null? "未用":"待激活") : "已用");
			row1.createCell(3).setCellValue(yhUserReginfo.getKey());
			row1.createCell(4).setCellValue(yhUserReginfo.getName());
			row1.createCell(5).setCellValue(yhUserReginfo.getUserName());
			row1.createCell(6).setCellValue(yhUserReginfo.getMobile());
			row1.createCell(7)
					.setCellValue(yhUserReginfo.getRegTime() == null ? "" : sdf.format(yhUserReginfo.getRegTime()));
			rowNum++;
		}
		// 必须在单元格设值以后进行
		// 设置为根据内容自动调整列宽
		for (int k = 0; k < exportDataList.size(); k++) {
			sheet.autoSizeColumn(k);
		}
		// 处理中文不能自动调整列宽的问题
		setSizeColumn(sheet, exportDataList.size());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}

	private void setSizeColumn(HSSFSheet sheet, int size) {
		for (int columnNum = 0; columnNum < size; columnNum++) {
			int columnWidth = sheet.getColumnWidth(columnNum) / 256;
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				HSSFRow currentRow;
				// 当前行未被使用过
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				} else {
					currentRow = sheet.getRow(rowNum);
				}
				if (currentRow.getCell(columnNum) != null) {
					HSSFCell currentCell = currentRow.getCell(columnNum);
					if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						int length = currentCell.getStringCellValue().getBytes().length;
						if (columnWidth < length) {
							columnWidth = length;
						}
					}
				}
			}
			sheet.setColumnWidth(columnNum, columnWidth * 256);
		}

	}

	//分配医生时查询所有医生
	@RequestMapping("/selectalldoctor")
	@ResponseBody
	public Result selectalldoctor(String guishu,String name) throws UnsupportedEncodingException {
		//name=new String(name.getBytes("iso-8859-1"),"utf-8");
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//查询当前机构
		 List<YhOrgTree> yhorgtree=yhusersettingservice.selectyhorgtrry(yh.getUserName());
		 //查询所有机构
		 List<YhOrgTree> yhallorg=yhusersettingservice.selectallorg();
		 List<YhMakeRegiCardInfo> cardinfo= yhusersettingservice.selectalldoctor(yhorgtree.get(0),guishu,name);
		 for (int i = 0; i < cardinfo.size(); i++) {
			 cardinfo.set(i,findorgtpo(cardinfo.get(i), yhallorg)) ;
		}
		return Result.ok(cardinfo);
	}
	
	//分页时查询总条数
	@RequestMapping("/selectrefincardcount")
	@ResponseBody
	public Result selectrefincardcount(String guishu,String yishi,String state,String name,String paixuming,String fangfa,String states) throws UnsupportedEncodingException {
		//name=new String(name.getBytes("iso-8859-1"),"utf-8");
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
		//查询当前机构
		 List<YhOrgTree> yhorgtree=yhusersettingservice.selectyhorgtrry(yh.getUserName());
		//获取总条数
		 int count= yhusersettingservice.selectcount(yhorgtree.get(0),guishu,yishi,state,name,states);
		return Result.ok(count);
	}
	
	//分配医师
	@RequestMapping(value = "/fenpeiyishis",method=RequestMethod.POST)
	@ResponseBody
	public Result fenpeiyishis(String id, String[] shuzu) throws UnsupportedEncodingException {
		return 0 != yhusersettingservice.fenpeiyishis(id,shuzu) ? Result.ok(200) : Result.ok(400);
	}
	
	//注册分配
		@RequestMapping(value = "/zhucefenpei",method=RequestMethod.POST)
		@ResponseBody
		public Result zhucefenpei(String id, String[] shuzu) throws UnsupportedEncodingException, ParseException {
			return 0 != yhusersettingservice.zhucefenpei(id,shuzu) ? Result.ok(200) : Result.ok(400);
		}

	@RequestMapping(value = "/cardIdActivation",method=RequestMethod.POST)
	@ResponseBody
	public Result cardIdActivation(String[] shuzu) throws UnsupportedEncodingException, ParseException {
		int count=0;
		char[] a={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
				'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		for (int i=0;i<shuzu.length;i++){
			String key=""+a[(int)(Math.random()*52)]+(int)(Math.random()*9000+1000)+a[(int)(Math.random()*52)];
			count+=yhusersettingservice.cardIdActivation(key,shuzu[i]);
		}
		return 0 != count ? Result.ok(200) : Result.ok(400);
	}
	
	//查询最顶层的机构
	public YhMakeRegiCardInfo findorgtpo(YhMakeRegiCardInfo cardinfo,List<YhOrgTree> yhallorg) {
		if(cardinfo.getSuperOrgCode().equals("0")) {
			return cardinfo;
		}else {
			for (int i = 0; i < yhallorg.size(); i++) {
				if(cardinfo.getSuperOrgCode().equals(yhallorg.get(i).getOrgCode())) {
					cardinfo.setOrgName(yhallorg.get(i).getOrgName()+">"+cardinfo.getOrgName());
					cardinfo.setSuperOrgCode(yhallorg.get(i).getSuperOrgCode());
				}
			}
			return findorgtpo(cardinfo, yhallorg);
		}
		
	}
}
