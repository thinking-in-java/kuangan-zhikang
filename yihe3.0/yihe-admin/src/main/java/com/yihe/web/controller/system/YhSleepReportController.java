/**
 * 
 */
package com.yihe.web.controller.system;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.util.Calendar;
import com.yihe.common.utils.file.FileUtils;
import com.yihe.system.domain.SleepVoJo;
import com.yihe.system.domain.SubtitleVoJO;
import com.yihe.system.domain.YhMyLifeHabitAssessResultWebApp;
import com.yihe.system.domain.YhSleepReport;
import com.yihe.system.domain.YhSleepReportVojo;
import com.yihe.system.domain.YhSportDiary;
import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhOrgTreeService;
import com.yihe.system.service.YhSleepReportService;
import com.yihe.system.service.YhUserApiService;
import com.yihe.system.service.YhUserMakeCardService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;
import com.yihe.web.controller.tool.HtmlToPdf;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 睡眠控制层
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhSleepReportController")
public class YhSleepReportController {

	@Autowired
	private YhSleepReportService yhSleepReportService;
	
	@Autowired
	YhUserReginfoService yhUserReginfoService;
	
	@Autowired
	YhOrgTreeService yhOrgTreeService;
	
	@Autowired
	YhUserMakeCardService yhUserMakeCardService;
	
	@Autowired
	private YhUserApiService yhUserApiService;
	
	@RequestMapping(value = "/SleepgaoxueyaDataInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result gaoxueyaDataInfo2(String id,String usercode,HttpServletResponse response) {
		String path = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1)+"static/template/";
		YhUserReginfo selectUserRegInfoByUserCode = yhUserApiService.selectUserRegInfoByUserCode(usercode);
		String time =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime());
		String fileName = usercode+"_"+selectUserRegInfoByUserCode.getName()+"_"+time+"_"+"健康睡眠评估报告";// 设置要导出的文件的名字
		System.out.println(path+fileName+".pdf");
		boolean convert = HtmlToPdf.convert("http://localhost/manage/usertosee/sleepshowPdf?id="+id, path+fileName+".pdf");//本地
		if(convert){
			return Result.ok(fileName);
		}else{
			return Result.error();
		}
		
			
		
	}
	
	
	@RequestMapping(value = "/Sleepdownload", method = RequestMethod.GET)
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) {
  //这里文件名称是通过参数传递过来的，要是不需要，直接可以写在这里。String fileName = "a.doc";
		String path = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1)+"static/template/";
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
        	
            File file = new File(path, fileName);
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"),"iso8859-1"));
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            FileUtils.deleteFile(path+fileName);
        }catch(FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("The file not found!");
     
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	
	
	@RequestMapping(value = "/SleepexportXls", method = RequestMethod.GET)
	@ResponseBody
	public void exportXls(HttpServletResponse response, String usercode, Integer dictId)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet =workbook.createSheet("睡眠评估问卷");
		YhUserReginfo userReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		String curUserName = userReginfo.getUserName();

		List<YhUserReginfo> exportDataList = yhUserReginfoService.exportYhUserReginfoData(curUserName);
		// 设置文件名
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String[] headers = new String[] { "用户编码", "姓名", "性别", "评估年龄", "单位", "评估时间", "近1个月，我晚上上床睡觉时间通常是", "近1个月，从躺下后到入睡我通常需要", "近1个月，我早上起床时间通常是", "近1个月，我每夜通常实际睡眠时间是(实际睡眠时间，不等于卧床时间)",
				"近一个月，我有没有因下列情况影响睡眠而烦恼：入睡困难(30分钟内不能入睡)", "近一个月，我有没有因下列情况影响睡眠而烦恼：夜间易醒或早醒", "近一个月，我有没有因下列情况影响睡眠而烦恼：夜间去厕所", "近一个月，我有没有因下列情况影响睡眠而烦恼：呼吸不畅", 
				"近一个月，我有没有因下列情况影响睡眠而烦恼：咳嗽或鼾声高", "近一个月，我有没有因下列情况影响睡眠而烦恼：感觉冷","近一个月，我有没有因下列情况影响睡眠而烦恼：感觉热","近一个月，我有没有因下列情况影响睡眠而烦恼：做恶梦","近一个月，我有没有因下列情况影响睡眠而烦恼：疼痛不适",
				"近一个月，我有没有因下列情况影响睡眠而烦恼：其它影响睡眠的事情","近1个月，总的来说，我认为自己的睡眠质量:","近1个月，我用药物催眠的情况","近1个月，我感到困倦","近1个月，我感到做事的精力不足"};
		// 新增数据行，并且设置单元格数据
		int rowNum = 1;
		// 在excel表中添加表头
		HSSFRow row = sheet.createRow(0);

		// 列数
		/*
		 * int cno=exportDataList.get(0).getClass().getDeclaredFields().length;
		 */
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

		java.util.Date mydate = null;
		// 在表中存放查询到的数据放入对应的列
		YhUserReginfo yhUserReginfo = yhUserReginfoService.selectByPrimaryKey(usercode);
		String time =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime());
		String fileName = usercode+"_"+yhUserReginfo.getName()+"_"+time+"_"+"健康睡眠评估问卷" + ".xls";// 设置要导出的文件的名字
		Date birthDate = yhUserReginfo.getBirthDate();
		System.out.println(birthDate + "出生日期");
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String birthDate2 = myFormatter.format(birthDate);
		if (birthDate2 != null && !"".equals(birthDate2)) {
			mydate = myFormatter.parse(birthDate2);

		}

		YhUserMakeCard userMakeCard = yhUserMakeCardService.selectOrgIdbyParam(usercode);

		String orgName = "";
		if (userMakeCard.getOrgId() != null) {
			orgName = yhOrgTreeService.selectFullOrgName(userMakeCard.getOrgId().toString(), orgName);

		}

		YhSleepReport yhPressureEvaluate = yhSleepReportService.showSleepDetail(dictId);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(yhPressureEvaluate.getQtime());
		String[] evaluateData = yhPressureEvaluate.getReportQueselect().split(";");
		// 创建excel中的行
		HSSFRow row1 = sheet.createRow(rowNum);
		// 在表中存放查询到的数据放入对应的列
		row1.createCell(0).setCellValue(yhUserReginfo.getUserCode());
		row1.createCell(1).setCellValue(yhUserReginfo.getName());
		row1.createCell(2).setCellValue(yhUserReginfo.getGender() == 1 ? "男" : "女");
		row1.createCell(3).setCellValue(getAge(mydate));
		row1.createCell(4).setCellValue(orgName);
		row1.createCell(5).setCellValue(dateString);
		row1.createCell(6).setCellValue(evaluateData[0]);
		row1.createCell(7).setCellValue(evaluateData[1].equals("0") ? "≤15分钟":evaluateData[1].equals("1") ? "16~30分钟":evaluateData[1].equals("2") ? "31~60分钟":"≥60分钟");
		row1.createCell(8).setCellValue(evaluateData[2]);
		row1.createCell(9).setCellValue(evaluateData[3].equals("0") ? "＞7小时":evaluateData[3].equals("1") ? "6~7小时":evaluateData[3].equals("2") ? "5~6小时":"＜5小时");
		row1.createCell(10).setCellValue(evaluateData[4].equals("0") ? "无":evaluateData[4].equals("1") ? "＜1次/周":evaluateData[4].equals("2") ? "1~2次/周":"≥3次/周");
		row1.createCell(11).setCellValue(evaluateData[5].equals("0") ? "无":evaluateData[5].equals("1") ? "＜1次/周":evaluateData[5].equals("2") ? "1~2次/周":"≥3次/周");
		row1.createCell(12).setCellValue(evaluateData[6].equals("0") ? "无":evaluateData[6].equals("1") ? "＜1次/周":evaluateData[6].equals("2") ? "1~2次/周":"≥3次/周");
		row1.createCell(13).setCellValue(evaluateData[7].equals("0") ? "无":evaluateData[7].equals("1") ? "＜1次/周":evaluateData[7].equals("2") ? "1~2次/周":"≥3次/周");
		row1.createCell(14).setCellValue(evaluateData[8].equals("0") ? "无":evaluateData[8].equals("1") ? "＜1次/周":evaluateData[8].equals("2") ? "1~2次/周":"≥3次/周");
		row1.createCell(15).setCellValue(evaluateData[9].equals("0") ? "无":evaluateData[9].equals("1") ? "＜1次/周":evaluateData[9].equals("2") ? "1~2次/周":"≥3次/周");
		row1.createCell(16).setCellValue(evaluateData[10].equals("0") ? "无":evaluateData[10].equals("1") ? "＜1次/周":evaluateData[10].equals("2") ? "1~2次/周":"≥3次/周");
		row1.createCell(17).setCellValue(evaluateData[11].equals("0") ? "无":evaluateData[11].equals("1") ? "＜1次/周":evaluateData[11].equals("2") ? "1~2次/周":"≥3次/周");
		row1.createCell(18).setCellValue(evaluateData[12].equals("0") ? "无":evaluateData[12].equals("1") ? "＜1次/周":evaluateData[12].equals("2") ? "1~2次/周":"≥3次/周");
		row1.createCell(19).setCellValue(evaluateData[13].equals("0") ? "无":evaluateData[13].equals("1") ? "＜1次/周":evaluateData[13].equals("2") ? "1~2次/周":"≥3次/周");
		row1.createCell(20).setCellValue(evaluateData[14].equals("0") ? "很好":evaluateData[9].equals("1") ? "较好":evaluateData[9].equals("2") ? "较差":"很差");
		row1.createCell(21).setCellValue(evaluateData[15].equals("0") ? "无":evaluateData[9].equals("1") ? "＜1次/周":evaluateData[9].equals("2") ? "1~2次/周":"≥3次/周");
		row1.createCell(22).setCellValue(evaluateData[16].equals("0") ? "无":evaluateData[9].equals("1") ? "＜1次/周":evaluateData[9].equals("2") ? "1~2次/周":"≥3次/周");
		row1.createCell(23).setCellValue(evaluateData[17].equals("0") ? "无":evaluateData[9].equals("1") ? "＜1次/周":evaluateData[9].equals("2") ? "1~2次/周":"≥3次/周");
		
		// 必须在单元格设值以后进行
		// 设置为根据内容自动调整列宽
		for (int k = 0; k < exportDataList.size(); k++) {
			sheet.autoSizeColumn(k);
		}
		// 处理中文不能自动调整列宽的问题
		setSizeColumn(sheet, exportDataList.size());

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		/*
		 * response.setHeader("Content-disposition", "attachment;filename=" +
		 * fileName);
		 */
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
	
	public static int getAge(Date dateOfBirth) {

		int age = 0;

		Calendar born = Calendar.getInstance();

		Calendar now = Calendar.getInstance();

		if (dateOfBirth != null) {

			now.setTime(new Date());

			born.setTime(dateOfBirth);

			if (born.after(now)) {

				throw new IllegalArgumentException("年龄不能超过当前日期");

			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);

			int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);

			int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);

			System.out.println("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);

			if (nowDayOfYear < bornDayOfYear) {

				age -= 1;

			}

		}

		return age;

	}
	
	
	
	/**
	 * 计算2个日期之间相差的  以年、月、日为单位，各自计算结果是多少
	 * 比如：2011-02-02 到  2017-03-02
	 *                                以年为单位相差为：6年
	 *                                以月为单位相差为：73个月
	 *                                以日为单位相差为：2220天
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static int dayCompare(Date fromDate,Date toDate){
	    Calendar  from  =  Calendar.getInstance();
	    from.setTime(fromDate);
	    Calendar  to  =  Calendar.getInstance();
	    to.setTime(toDate);
	    //只要年月
	    int fromYear = from.get(Calendar.YEAR);
	    int fromMonth = from.get(Calendar.MONTH);

	    int toYear = to.get(Calendar.YEAR);
	    int toMonth = to.get(Calendar.MONTH);

	    int year = toYear  -  fromYear;
	    int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
	    int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));
	    return year;
	}
	
	
	@ApiOperation(value = "睡眠评估记录")
    @RequestMapping(value = "/assessRecordShow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> assessRecordShow(Integer page,Integer limit,String userName,String name,String reportLevel,String qtime){
		Map<String,Object> map1 = new HashMap<String,Object>();
		Pages pa = new Pages();
		pa.setCurPage(page);
		pa.setMaxResult(limit);
		map1.put("page", pa.getFirstRows());
		map1.put("limit", pa.getMaxResult());
		Subject subject = SecurityUtils.getSubject();
		YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		String usercode = principal.getUserCode();
		map1.put("userCode", usercode);
		map1.put("name", name);
		map1.put("userName", userName);
		map1.put("reportLevel", reportLevel);
		if(qtime!=null && !qtime.equals("")) {
 			String[] strArr = qtime.split(" - ");
 		map1.put("qtime", strArr[0]);
 		map1.put("qtime1", strArr[1]);
 		}
		Map<String,Object> map = new HashMap<String,Object>();
		List<YhSleepReportVojo> list = yhSleepReportService.assessRecordShow(map1);
		if(list!=null && !list.equals("")) {
			for (YhSleepReportVojo yhSleepReport : list) {
		    	//1.把字符串类型的json数组对象转化JSONArray
		    		JSONArray json=JSONArray.fromObject(yhSleepReport.getReportDesc());
		    		String src="";
		    		//2、循环遍历这个数组
		    		 for(int i=0;i<json.size();i++){
		    			 //3、把里面的对象转化为JSONObject
		    			  JSONObject job = json.getJSONObject(i);   
		    			  // 4、把里面想要的参数一个个用.属性名的方式获取到
		    			  System.out.println(job.get("sleepExistingProblem")) ; 
		    			 src+=job.get("sleepExistingProblem")+",";
		    		 }
		    		yhSleepReport.setResult(yhSleepReport.getReportLevel()+"("+yhSleepReport.getReportScore()+"分):"+src);
			}
		}
		
		map.put("code", 20000);
		map.put("msg", "");
		map.put("count",yhSleepReportService.assessRecordShowCount(map1));
		map.put("data", list);
		return map;
	}
	
	
	
	  @ApiOperation(value = "睡眠评估管理")
	     @RequestMapping(value = "/sleepAdministration", method = RequestMethod.POST)
	 	@ResponseBody
	 	public Map<String,Object> sleepAdministration(Integer page,Integer limit,String name,String reportLevel,String orgCode){
	 		Map<String,Object> map1 = new HashMap<String,Object>();
	 		Pages pa = new Pages();
	 		pa.setCurPage(page);
	 		pa.setMaxResult(limit);
	 		map1.put("page", pa.getFirstRows());
	 		map1.put("limit", pa.getMaxResult());
	 		Subject subject = SecurityUtils.getSubject();
			YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
			String usercode = principal.getUserCode();
	 		map1.put("userCode", usercode);
	 		map1.put("name", name);
	 		map1.put("reportLevel", reportLevel);
	 		map1.put("orgCode", orgCode);
	 		Map<String,Object> map = new HashMap<String,Object>();
	 		List<YhSleepReportVojo> list = yhSleepReportService.sleepAdministration(map1);
	 		if(list!=null && !list.equals("")) {
	 			for (YhSleepReportVojo yhSleepReport : list) {
	 		    	//1.把字符串类型的json数组对象转化JSONArray
	 		    		JSONArray json=JSONArray.fromObject(yhSleepReport.getReportDesc());
	 		    		String src="";
	 		    		//2、循环遍历这个数组
	 		    		 for(int i=0;i<json.size();i++){
	 		    			 //3、把里面的对象转化为JSONObject
	 		    			  JSONObject job = json.getJSONObject(i);   
	 		    			  // 4、把里面想要的参数一个个用.属性名的方式获取到
	 		    			  System.out.println(job.get("sleepExistingProblem")) ; 
	 		    			 src+=job.get("sleepExistingProblem")+",";
	 		    		 }
	 		    		yhSleepReport.setResult(yhSleepReport.getReportLevel()+"("+yhSleepReport.getReportScore()+"分):"+src);
	 		    		
	 		    		yhSleepReport.setFrequency(yhSleepReportService.showSleepAssessUserCount(yhSleepReport.getUserCode()));
	 		    		 
			}
	 		}
	 		
	 		map.put("code", 20000);
	 		map.put("msg", "");
	 		map.put("count",yhSleepReportService.sleepAdministrationCount(map1).size());
	 		map.put("data", list);
	 		return map;
	 	}
	     
	  
	  
	  
	
	
	/**
     * 查询用户查看运动记录内容统计图
     * @param usercode
     * @param ctime
     * @return
     */
     @ApiOperation(value = "查询睡眠详情")
     @RequestMapping(value = "/showSleepDetail", method = RequestMethod.POST)
 	@ResponseBody
 	public Result showSleepDetail(Integer id){
    	 YhSleepReport port=yhSleepReportService.showSleepDetail(id);
    	 int nut=dayCompare(port.getBirthdate(), port.getQtime());
    	 port.setAge(nut);
    	//1.把字符串类型的json数组对象转化JSONArray
    		JSONArray json=JSONArray.fromObject(port.getReportDesc());
    		String reportDesc="";//现存问题
	    		//2、循环遍历这个数组
	    		 for(int i=0;i<json.size();i++){
	    			 //3、把里面的对象转化为JSONObject
	    			  JSONObject job = json.getJSONObject(i);   
	    			  // 4、把里面想要的参数一个个用.属性名的方式获取到
	    			  System.out.println(job.get("sleepExistingProblem")) ; 
	    			  reportDesc+=job.get("sleepExistingProblem")+",";
	    		 }
	    		 port.setResult(reportDesc);
    		String src="";//大类
    		//2、循环遍历这个数组
    		 for(int i=0;i<json.size();i++){
    			 //3、把里面的对象转化为JSONObject
    			  JSONObject job = json.getJSONObject(i);   
    			  // 4、把里面想要的参数一个个用.属性名的方式获取到
    			  Object scr=job.get("dataList");
    			  JSONArray json1=JSONArray.fromObject(scr);
    			  for(int j=0;j<json1.size();j++){
        			  JSONObject job1 = json1.getJSONObject(j);  
        			  reportDesc+=job1.get("sleepExistingProblem")+",";
        			  if(job1.get("sleepTitle")!=null && !job1.get("sleepTitle").equals("")) {
        				  src+=job1.get("sleepTitle")+",";
    			  }
    			 }
    			 
	}
    		 
    	
    		 if(src!=null && !src.equals("")) {
    			 Set set = new HashSet();  
   			  String[] strArr = src.split(",");
   			  for (int j = 0; j < strArr.length; j++) {
   				set.add(strArr[j]);
   			}
   			  List list = new ArrayList(set);
   			  List<SleepVoJo> vojo=new ArrayList<SleepVoJo>();
   			  for (int i = 0; i < list.size(); i++) {
   				  SleepVoJo SleepVoJo=new SleepVoJo();
   				  SleepVoJo.setHeadline(String.valueOf(list.get(i)));
   				  vojo.add(SleepVoJo);
   			}
   			 
   			  
   			  for (SleepVoJo sleepVoJo : vojo) {
   				  List<SubtitleVoJO> listsub=new ArrayList<SubtitleVoJO>();
   					  for(int i=0;i<json.size();i++){
   			    			 //3、把里面的对象转化为JSONObject
   			    			  JSONObject job = json.getJSONObject(i);   
   			    			  // 4、把里面想要的参数一个个用.属性名的方式获取到
   			    			  Object scr=job.get("dataList");
   			    			  JSONArray json1=JSONArray.fromObject(scr);
   			    			  for(int j=0;j<json1.size();j++){
   			        			  JSONObject job1 = json1.getJSONObject(j);  
   			        			  if(String.valueOf(job1.get("sleepTitle"))==sleepVoJo.getHeadline() || String.valueOf(job1.get("sleepTitle")).equals(sleepVoJo.getHeadline())) {
   		        						SubtitleVoJO subtitle=new SubtitleVoJO();
   		        						if(String.valueOf(job1.get("sleepTitle")).equals("医生干预")) {
   		        							subtitle.setContent(String.valueOf(job1.get("sleepContent")));
   		        	    				  }else {
   		        	    					  String[] arr=String.valueOf(job1.get("sleepContent")).split("：");
   		        	    					  subtitle.setSubtitle(arr[0]);
   		        	    					  subtitle.setContent(arr[1]);
   		        	    				  }
   		        						listsub.add(subtitle);
   		        					}

   			        			  
   			        			  
   			        			  
   			    	}
   				}
   					  sleepVoJo.setSubtitle(listsub);
   			  }
   			port.setVojo(vojo);
    		 }
    		
			 
		
			  
			  return Result.ok(port);
 	}

     @ApiOperation(value = "查询用户查看睡眠记录内容")
     @RequestMapping(value = "/selectSleep", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectSleep(Integer page,Integer limit,String usercode,String qtime,String reportLevel){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		Pages pa = new Pages();
 		pa.setCurPage(page);
 		pa.setMaxResult(limit);
 		map1.put("page", pa.getFirstRows());
 		map1.put("limit", pa.getMaxResult());
 		map1.put("userCode", usercode);
 		map1.put("reportLevel", reportLevel);
 		if(qtime!=null && !qtime.equals("")) {
 			String[] strArr = qtime.split(" - ");
 		map1.put("qtime", strArr[0]);
 		map1.put("qtime1", strArr[1]);
 		}
 		Map<String,Object> map = new HashMap<String,Object>();
 		List<YhSleepReport> list = yhSleepReportService.selectSleep(map1);
 		if(list!=null && !list.equals("")) {
 			for (YhSleepReport yhSleepReport : list) {
 		    	//1.把字符串类型的json数组对象转化JSONArray
 		    		JSONArray json=JSONArray.fromObject(yhSleepReport.getReportDesc());
 		    		String src="";
 		    		//2、循环遍历这个数组
 		    		 for(int i=0;i<json.size();i++){
 		    			 //3、把里面的对象转化为JSONObject
 		    			  JSONObject job = json.getJSONObject(i);   
 		    			  // 4、把里面想要的参数一个个用.属性名的方式获取到
 		    			  System.out.println(job.get("sleepExistingProblem")) ; 
 		    			 src+=job.get("sleepExistingProblem")+",";
 		    		 }
 		    		yhSleepReport.setResult(yhSleepReport.getReportLevel()+"("+yhSleepReport.getReportScore()+"分):"+src);
 		    		 
		}
 		}
 		
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",yhSleepReportService.selectSleepConut(map1));
 		map.put("data", list);
 		return map;
 	}
     
     
     public static void main(String[] args) {
		
    	 
    	 String resultStr = "[{\"dataList\":\"[{\\\"sleepContent\\\":\\\"冥想：冥想通过转移注意力和放松身体来让你放下那些纷乱的思绪，使大脑得到休息，从而减少焦虑和身体紧张对睡眠的影响。\\\",\\\"sleepTitle\\\":\\\"助眠生活方式\\\"},{\\\"sleepContent\\\":\\\"补充钙镁食物：钙、镁是深度睡眠的重要矿物质，镁可缓解肌肉紧张，促进褪黑素分泌，有助于进入深度睡眠状态；而钙缺乏则会干扰深度睡眠，降低睡眠质量。日常可选牛奶、豆腐等充不钙质，选择菠菜生菜等补充镁。\\\",\\\"sleepTitle\\\":\\\"饮食营养建议\\\"},{\\\"sleepContent\\\":\\\"跟着呼吸节奏入睡：睡前深呼吸，帮助身体放松，大脑放空，调动人体的副交感神经\\\",\\\"sleepTitle\\\":\\\"睡眠节律调整\\\"}]\",\"sleepExistingProblem\":\"入睡困难\"},{\"dataList\":\"[{\\\"sleepContent\\\":\\\"晒太阳：若白天接受日照不足，极易引起睡眠紊乱。强光能够有效地诱导体内的昼夜节律重新设定，帮助调节和恢复生物钟。晴天可以安排20分钟晒太阳\\\",\\\"sleepTitle\\\":\\\"助眠生活方式\\\"}]\",\"sleepExistingProblem\":\"睡眠效率低\"},{\"dataList\":\"[{\\\"sleepContent\\\":\\\"需要注意去除引起睡眠障碍的基础疾病，可以在相关科室医生的指导下进行规范的治疗，减轻不适的症状，从而达到改善睡眠的目的。平时还需要注意放松心情，养成规律作息的习惯。\\\",\\\"sleepTitle\\\":\\\"医生干预\\\"}]\",\"sleepExistingProblem\":\"睡眠维持障碍\"},{\"dataList\":\"[{\\\"sleepContent\\\":\\\"午间充电：借助午休弥补由于夜间失眠造成的影响，每天午睡30分钟，在饭后半小时再入睡，不宜超过60分钟。午睡不仅能提高工作效率还能预防冠心病\\\",\\\"sleepTitle\\\":\\\"助眠生活方式\\\"}]\",\"sleepExistingProblem\":\"日间精力不足\"}]";
 		
    	 
    	//1.把字符串类型的json数组对象转化JSONArray
    		JSONArray json=JSONArray.fromObject(resultStr);
    		List<SleepVoJo> sleep=new ArrayList<SleepVoJo>();
    		String src="";
    		//2、循环遍历这个数组
    		 for(int i=0;i<json.size();i++){
    			 //3、把里面的对象转化为JSONObject
    			  JSONObject job = json.getJSONObject(i);   
    			  // 4、把里面想要的参数一个个用.属性名的方式获取到
    			  Object scr=job.get("dataList");
    			  JSONArray json1=JSONArray.fromObject(scr);
    			  for(int j=0;j<json1.size();j++){
        			  JSONObject job1 = json1.getJSONObject(j);  
        			  if(job1.get("sleepTitle")!=null && !job1.get("sleepTitle").equals("")) {
        				  src+=job1.get("sleepTitle")+",";
        				  
    			  }
    			 }
	}
			  Set set = new HashSet();  
			  String[] strArr = src.split(",");
			  for (int j = 0; j < strArr.length; j++) {
				set.add(strArr[j]);
			}
			  System.out.println("hahha ");
			  List list = new ArrayList(set);
			  List<SleepVoJo> vojo=new ArrayList<SleepVoJo>();
			  for (int i = 0; i < list.size(); i++) {
				  SleepVoJo SleepVoJo=new SleepVoJo();
				  SleepVoJo.setHeadline(String.valueOf(list.get(i)));
				  vojo.add(SleepVoJo);
			}
			 
			  
			  for (SleepVoJo sleepVoJo : vojo) {
				  List<SubtitleVoJO> listsub=new ArrayList<SubtitleVoJO>();
					  for(int i=0;i<json.size();i++){
			    			 //3、把里面的对象转化为JSONObject
			    			  JSONObject job = json.getJSONObject(i);   
			    			  // 4、把里面想要的参数一个个用.属性名的方式获取到
			    			  Object scr=job.get("dataList");
			    			  JSONArray json1=JSONArray.fromObject(scr);
			    			  for(int j=0;j<json1.size();j++){
			        			  JSONObject job1 = json1.getJSONObject(j);  
			        			  if(String.valueOf(job1.get("sleepTitle"))==sleepVoJo.getHeadline() || String.valueOf(job1.get("sleepTitle")).equals(sleepVoJo.getHeadline())) {
		        						SubtitleVoJO subtitle=new SubtitleVoJO();
		        						if(String.valueOf(job1.get("sleepTitle")).equals("医生干预")) {
		        							subtitle.setContent(String.valueOf(job1.get("sleepContent")));
		        	    				  }else {
		        	    					  String[] arr=String.valueOf(job1.get("sleepContent")).split("：");
		        	    					  subtitle.setSubtitle(arr[0]);
		        	    					  subtitle.setContent(arr[1]);
		        	    				  }
		        						listsub.add(subtitle);
		        					}

			        			  
			        			  
			        			  
			    	}
				}
					  sleepVoJo.setSubtitle(listsub);
			  }
		
			  
			  
			  
     }

     
}
