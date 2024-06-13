package com.yihe.common.utils.poi;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jdk.nashorn.internal.runtime.regexp.joni.Region;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;


public class ExcelUtilQ {
	/**
	 * 
	 * @param  实体list
	 * @param response 对应的title list
	 * @throws Exception
	 */
	public static  void exportExcel(HttpServletResponse response,HSSFWorkbook workbook) throws Exception {
	        //String fileName = "导出excel例子.xls";
	        String fileName = "健康课堂访问导出" + ".xls";// 设置要导出的文件的名字

	        //生成excel文件
	        buildExcelFile(fileName, workbook);

	        //浏览器下载excel
	        buildExcelDocument(fileName,workbook,response);
	}
	//创建表头
	public static void createTitle(HSSFWorkbook workbook,HSSFSheet sheet,List<String> title,int index){
	    //合并单元格
	    //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
	    HSSFRow row = sheet.createRow(0);
	    //设置行高
	    row.setHeightInPoints(36);
	    //设置列宽度

	    sheet.setColumnWidth(0,10*256);
	    sheet.setColumnWidth(1,10*256);
	    sheet.setColumnWidth(2,10*256);
	    for (int i = 1; i < title.size(); i++) {
	    	sheet.setColumnWidth(i,25*256);
	    	if(title.get(i).equals("单位")) {
	    		sheet.setColumnWidth(i,25*512);
	    	}
		}
	    //设置为居中加粗
	    HSSFCellStyle style = workbook.createCellStyle();
	    HSSFFont font = workbook.createFont();
	    HSSFPalette palette = workbook.getCustomPalette();
	    palette.setColorAtIndex((short) 9, (byte) (0xff & 204), (byte) (0xff & 255), (byte) (0xff & 204));
	    style.setFillForegroundColor((short) 9);// 设置背景色
	    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	    style.setBorderBottom(BorderStyle.THIN); //下边框  
	    style.setBorderLeft(BorderStyle.THIN);//左边框  
	    style.setBorderTop(BorderStyle.THIN);//上边框  
	    style.setBorderRight(BorderStyle.THIN);//右边框  

	    font.setBold(true);
	    style.setFont(font);
	    style.setAlignment(HorizontalAlignment.CENTER);
	    style.setVerticalAlignment(VerticalAlignment.CENTER);
	    HSSFCell cell ;
	    for (int i = 0; i < title.size(); i++) {
	    	
	 	    cell = row.createCell(i);
	 	    cell.setCellValue(title.get(i));
	 	    cell.setCellStyle(style);
		}
	   
	}

	//创建表头
	public static void createTitles(HSSFWorkbook workbook,HSSFSheet sheet,List<String> title,int index){
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
        sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
        sheet.addMergedRegion(new CellRangeAddress(0,1,2,2));
        sheet.addMergedRegion(new CellRangeAddress(0,1,3,3));

		//参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
		HSSFRow row = sheet.createRow(1);
        HSSFRow row0 = sheet.createRow(0);
		//设置行高
		row.setHeightInPoints(36);
        row0.setHeightInPoints(36);
		//设置列宽度

		sheet.setColumnWidth(0,10*256);
		sheet.setColumnWidth(1,10*256);
		sheet.setColumnWidth(2,10*256);
		for (int i = 1; i < title.size(); i++) {
			sheet.setColumnWidth(i,25*256);
			if(title.get(i).equals("单位")) {
				sheet.setColumnWidth(i,25*512);
			}
		}
		//设置为居中加粗
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		HSSFPalette palette = workbook.getCustomPalette();
		palette.setColorAtIndex((short) 9, (byte) (0xff & 204), (byte) (0xff & 255), (byte) (0xff & 204));
		style.setFillForegroundColor((short) 9);// 设置背景色
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		style.setBorderBottom(BorderStyle.THIN); //下边框
		style.setBorderLeft(BorderStyle.THIN);//左边框
		style.setBorderTop(BorderStyle.THIN);//上边框
		style.setBorderRight(BorderStyle.THIN);//右边框

		font.setBold(true);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		HSSFCell cell ;
        HSSFCell cells ;
		for (int i = 0; i < title.size(); i++) {

            cells=row0.createCell(i);
            if(i<5){
                cells.setCellValue(title.get(i));
            }
            if(title.get(i).equals("血糖")){
                sheet.addMergedRegion(new CellRangeAddress(0,0,i,i+1));
                cells.setCellValue("血糖");
            }
            if(title.get(i).equals("基础代谢")){
                sheet.addMergedRegion(new CellRangeAddress(0,0,i,i+8));
                cells.setCellValue("体脂");
            }
            if(title.get(i).equals("舒张压")){
                sheet.addMergedRegion(new CellRangeAddress(0,0,i,i+3));
                cells.setCellValue("血压");
            }
            if(title.get(i).equals("身高")){
                sheet.addMergedRegion(new CellRangeAddress(0,0,i,i+3));
                cells.setCellValue("BMI");
            }
            if(title.get(i).equals("总胆固醇")){
                sheet.addMergedRegion(new CellRangeAddress(0,0,i,i+3));
                cells.setCellValue("血脂四项");
            }
            if(title.get(i).equals("谷丙转氨酶")){
                sheet.addMergedRegion(new CellRangeAddress(0,0,i,i+3));
                cells.setCellValue("肝功能");
            }
			if(title.get(i).equals("血肌酐")){
				sheet.addMergedRegion(new CellRangeAddress(0,0,i,i+3));
				cells.setCellValue("肾功能");
			}
            if(title.get(i).equals("葡萄糖")){
                sheet.addMergedRegion(new CellRangeAddress(0,0,i,i+11));
                cells.setCellValue("尿检");
            }
            if(title.get(i).equals("尿微量蛋白")){
                sheet.addMergedRegion(new CellRangeAddress(0,0,i,i+3));
                cells.setCellValue("其它");
            }
            cells.setCellStyle(style);
			cell = row.createCell(i);
			cell.setCellValue(title.get(i));
			cell.setCellStyle(style);

		}

	}
	//生成excel文件
	 public static void buildExcelFile(String filename,HSSFWorkbook workbook) throws Exception{
	        FileOutputStream fos = new FileOutputStream(filename);
	        workbook.write(fos);
	        fos.flush();
	        fos.close();
	  }

	  //浏览器下载excel
	  public static void buildExcelDocument(String filename,HSSFWorkbook workbook,HttpServletResponse response) throws Exception{
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(filename, "utf-8"));
	        OutputStream outputStream = response.getOutputStream();
	        workbook.write(outputStream);
	        outputStream.flush();
	        outputStream.close();
	    }
}
