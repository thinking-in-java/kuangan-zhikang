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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.common.utils.file.FileUtils;
import com.yihe.system.domain.YhMyLifeHabitAssessResultWebApp;
import com.yihe.system.domain.YhPressureEvaluate;
import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhMyLifeHabitAssessResultWebAppService;
import com.yihe.system.service.YhOrgTreeService;
import com.yihe.system.service.YhUserApiService;
import com.yihe.system.service.YhUserMakeCardService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.utils.Result;
import com.yihe.web.controller.tool.HtmlToPdf;

/**
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhMyLifeHabitAssessResultWebApp")
public class YhMyLifeHabitAssessResultWebAppController {
	
	 @Autowired
	    private YhMyLifeHabitAssessResultWebAppService yhMyLifeHabitAssessResultWebAppService;

		@Autowired
		YhUserReginfoService yhUserReginfoService;
		
		@Autowired
		YhOrgTreeService yhOrgTreeService;
		
		@Autowired
		YhUserMakeCardService yhUserMakeCardService;
		@Autowired
		private YhUserApiService yhUserApiService;
		
		
		@RequestMapping(value = "/gaoxueyaDataInfo2", method = RequestMethod.GET)
		@ResponseBody
		public Result gaoxueyaDataInfo2(String id,String usercode,HttpServletResponse response) {
			String path = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1)+"static/template/";
			YhUserReginfo selectUserRegInfoByUserCode = yhUserApiService.selectUserRegInfoByUserCode(usercode);
			String fileName=selectUserRegInfoByUserCode.getName()+"健康习惯评估报告";
			System.out.println(path+fileName+".pdf");
			boolean convert = HtmlToPdf.convert("http://localhost/manage/usertosee/habitpdf?id="+id, path+fileName+".pdf");//本地
			if(convert){
				return Result.ok(fileName);
			}else{
				return Result.error();
			}
			
				
			
		}
		
		
		@RequestMapping(value = "/download", method = RequestMethod.GET)
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
		 
		
		
	 
		@RequestMapping(value = "/exportXls", method = RequestMethod.GET)
		@ResponseBody
		public void exportXls(HttpServletResponse response, String usercode, Integer dictId)
				throws Exception {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet =workbook.createSheet("健康习惯评估问卷");
			YhUserReginfo userReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
			String curUserName = userReginfo.getUserName();

			List<YhUserReginfo> exportDataList = yhUserReginfoService.exportYhUserReginfoData(curUserName);
			// 设置文件名
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String[] headers = new String[] { "用户编码", "姓名", "性别", "评估年龄", "单位", "评估时间", "你每天睡觉的时间是？", "您平均每天的睡眠时长为？", "您每天喝酒量（白酒）", "您每天吃多少新鲜蔬菜？一个中等大小碗盛着的炒熟青菜≈300克",
					"您每天抽烟的数量是？", "您每天吃多少新鲜水果？（一个中等大小苹果≈200克）", "您每周运动的频率是？", "您每次运动时间多长（平均）？", "您吃宵夜吗？", "您按时一日三餐吗？"};
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
			String fileName = usercode+"_"+yhUserReginfo.getName()+"_"+time+"_"+"健康习惯评估问卷" + ".xls";// 设置要导出的文件的名字
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

			YhMyLifeHabitAssessResultWebApp yhPressureEvaluate = yhMyLifeHabitAssessResultWebAppService.selectByResultAssess(dictId);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(yhPressureEvaluate.getResultTime());
			String[] evaluateData = yhPressureEvaluate.getAssessImproved().split(",");
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
			row1.createCell(7).setCellValue(evaluateData[1]);
			row1.createCell(8).setCellValue(evaluateData[2]);
			row1.createCell(9).setCellValue(evaluateData[3]);
			row1.createCell(10).setCellValue(evaluateData[4]);
			row1.createCell(11).setCellValue(evaluateData[5]);
			row1.createCell(12).setCellValue(evaluateData[6]);
			row1.createCell(13).setCellValue(evaluateData[7]);
			row1.createCell(14).setCellValue(evaluateData[8]);
			row1.createCell(15).setCellValue(evaluateData[9]);
			
			
			
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
}
