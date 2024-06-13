package com.yihe.system.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import com.yihe.system.domain.YhUserReginfo;

public class ExportExcelUtil {
	 /**
     * listData为数据库里查询出来的List<Record>对象
     * @author wangjp
     * @param listData
     * @return
     */
    public static File exportExcel(List<YhUserReginfo> listData) {
        // 设置文件名
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String filename = "注册卡导出" + dateFormat.format(date) + ".xls";
        File file = new File(filename);
        // 标题
        Map<String, String> titleData = new LinkedHashMap<String, String>();// 标题，后面用到
        titleData.put("Enrol_Card_Id", "注册卡号");
        titleData.put("Org_Name", "归属");
		/* titleData.put("create_date", "申请日期");*/
        titleData.put("name", "用户姓名");
        titleData.put("user_name", "登录账号");
        titleData.put("mobile", "手机号码");
        titleData.put("reg_time", "注册时间");
        file = saveFile(titleData, listData, file);
        return file;
    }

    /**
     * excel生成
     * @param headData
     * @param yhUserReginfoList
     * @param file
     * @return
     */
    private static File saveFile(Map<String, String> headData, List<YhUserReginfo> yhUserReginfoList, File file) {
        // 创建工作薄
    	 HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        // sheet:一张表的简称
        // row:表里的行
        // 创建工作薄中的工作表
        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
        // 创建行
        HSSFRow title = null;
        HSSFRow row = null;
        // 创建单元格，设置表头 创建列
        HSSFCell cell = null;
        // 创建标题
        title = hssfSheet.createRow(0);
        title.setHeight((short) 400);
        // 创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        cell = title.createCell(0);
        // 创建字体样式
        HSSFFont titleFont = hssfWorkbook.createFont();
        titleFont.setFontName("黑体");
        titleFont.setFontHeightInPoints((short) 14);//设置字体大小
        HSSFFont rowFont = hssfWorkbook.createFont();
        rowFont.setFontName("黑体");
        rowFont.setFontHeightInPoints((short) 10);//设置字体大小
        HSSFFont contentFont = hssfWorkbook.createFont();
        contentFont.setFontHeightInPoints((short) 10);//设置字体大小
        // 设置标题样式为居中
        HSSFCellStyle titleStyle = hssfWorkbook.createCellStyle();
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        titleStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        titleStyle.setBorderBottom(BorderStyle.THIN);//下边框    
        titleStyle.setBorderLeft(BorderStyle.THIN);//左边框    
        titleStyle.setBorderTop(BorderStyle.THIN);//上边框    
        titleStyle.setBorderRight(BorderStyle.THIN);//右边框  
        titleStyle.setFont(titleFont);
        // 创建标题头样式
        HSSFCellStyle rowStyle = hssfWorkbook.createCellStyle();//水平居中
        rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        rowStyle.setBorderBottom(BorderStyle.THIN);//下边框    
        rowStyle.setBorderLeft(BorderStyle.THIN);//左边框    
        rowStyle.setBorderTop(BorderStyle.THIN);//上边框    
        rowStyle.setBorderRight(BorderStyle.THIN);//右边框  
        rowStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());// 设置背景色
        rowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        rowStyle.setFont(rowFont);
        // 创建内容样式
        HSSFCellStyle style = hssfWorkbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        style.setBorderBottom(BorderStyle.THIN);//下边框    
        style.setBorderLeft(BorderStyle.THIN);//左边框    
        style.setBorderTop(BorderStyle.THIN);//上边框    
        style.setBorderRight(BorderStyle.THIN);//右边框  
        style.setFont(contentFont);
        // 合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        hssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
        hssfSheet.setColumnWidth((short) 0, 5300); //调整第一列宽度
        hssfSheet.setColumnWidth((short) 1, 3500); //调整第二列宽度
        hssfSheet.setColumnWidth((short) 2, 3500); //调整第三列宽度
        hssfSheet.setColumnWidth((short) 3, 4000); //调整第四列宽度
        hssfSheet.setColumnWidth((short) 4, 10000); //调整第五列宽度
        hssfSheet.setColumnWidth((short) 5, 3000); //调整第六列宽度
        hssfSheet.setColumnWidth((short) 6, 3000); //调整第七列宽度
        hssfSheet.setColumnWidth((short) 7, 3000); //调整第八列宽度
        cell.setCellValue("注册卡列表");
        cell.setCellStyle(titleStyle);
        // 初始化索引
        int rowIndex = 1;
        int cellIndex = 0;

        // 创建标题行
        row = hssfSheet.createRow(rowIndex);
        row.setHeight((short) 280);

        rowIndex++;
        // 遍历标题
        for (String h : headData.keySet()) {
            // 创建列
            cell = row.createCell(cellIndex);
            // 索引递增
            cellIndex++;
            // 逐列插入标题
            cell.setCellValue(headData.get(h));
            cell.setCellStyle(rowStyle);
        }

        // 得到所有记录 行：列
        YhUserReginfo yhUserReginfo = null;

        if (yhUserReginfoList != null) {
            // 获取所有的记录 有多少条记录就创建多少行
            for (int i = 0; i < yhUserReginfoList.size(); i++) {
                row = hssfSheet.createRow(rowIndex);
                row.setHeight((short) 330);
                // 得到所有的行 一个record就代表 一行
                yhUserReginfo = yhUserReginfoList.get(i);
                // 下一行索引
                rowIndex++;
                // 刷新新行索引
                cellIndex = 0;
                // 在有所有的记录基础之上，便利传入进来的表头,再创建N行
                for (String h : headData.keySet()) {
                    cell = row.createCell(cellIndex);
                    cellIndex++;
                    // 按照每条记录匹配数据
                    cell.setCellValue(yhUserReginfo.get(h) == null ? "" : yhUserReginfo.get(h).toString());
                    cell.setCellStyle(style);
                }
            }
        }
        try {
            FileOutputStream fileOutputStreane = new FileOutputStream(file);
            hssfWorkbook.write(fileOutputStreane);
            fileOutputStreane.flush();
            fileOutputStreane.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
	/*//生成excel文件
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
		    }*/

}


