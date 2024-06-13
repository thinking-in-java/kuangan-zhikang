package com.yihe.system.utils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Excel
 * 
 * @author YX
 *
 */
public class ExcelUtil {
	private final static String excel2003L = ".xls";
	private final static String excel2007U = ".xlsx";

	/**
	 * 
	 * @param in
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static List<List<Object>> getBankListByExcel(InputStream in, String fileName) throws Exception {
		List<List<Object>> list = null;
		Workbook work = getWorkbook(in, fileName);
		//判断excel是否为空
		if (work == null) {
			throw new Exception("文件导入异常");
		}
		//定义工作铺、行、列
		Sheet sheet = null;// 
		Row row = null; // 
		Cell cell = null; // 
		
		list = new ArrayList<List<Object>>();
		
		int lastCellNum = 0;
		//获取excel的sheet页的数量
		if(work.getNumberOfSheets() == 0) {
			throw new Exception("请使用模板进行导入：只能识别系统提供的模板文件，不要对模板的列和单元格的格式进行调整");
		}
		for (int i = 0; i < work.getNumberOfSheets(); i++) {
			sheet = work.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			//获取sheet页的行数，从第二行开始，（忽略表头）
			for (int j = sheet.getFirstRowNum() + 1; j <= sheet.getLastRowNum(); j++) {//1
				row = sheet.getRow(j);
//				if (row == null || row.getFirstCellNum() == j) {
//					continue;
//				}
				
				List<Object> li = new ArrayList<Object>();
				
				if (j == sheet.getFirstRowNum()) {
					
					lastCellNum = row.getLastCellNum();
				} else {
					lastCellNum = lastCellNum > row.getLastCellNum() ? lastCellNum : row.getLastCellNum();
				}
				for (int y = row.getFirstCellNum(); y < lastCellNum; y++) {
					cell = row.getCell(y);
					Object val = getCellValue(cell);
					if("".equals(val.toString()) && li.size() == 0  && rowIsNull(row)) {
						break;
					}
					li.add(val);
				}
				if(li.size()>0) {
					list.add(li);
				}
			}
		}
		return list;

	}
	
	private static boolean rowIsNull(Row row) {
		boolean flag = true;
		for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
			Object val = getCellValue(row.getCell(y));
			if(!"".equals(val.toString())) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * 
	 * @param in
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	private  static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
		Workbook wb = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (excel2003L.equals(fileType)) {
			wb = new HSSFWorkbook(inStr);// 2003-
		} else if (excel2007U.equals(fileType)) {
			wb = new XSSFWorkbook(inStr); // 2007+
		} else {
			throw new Exception("uu");
		}
		return wb;
	}

	public static Object getCellValue(Cell cell) {
		  String value = "";  
	        if(null==cell){  
	            return value;  
	        }  
	        switch (cell.getCellType()) {  
	        //数值型  
	        case Cell.CELL_TYPE_NUMERIC:  
	            if (HSSFDateUtil.isCellDateFormatted(cell)) {  
	                //如果是date类型则 ，获取该cell的date值  
	                Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());  
	                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	                value = format.format(date);
	            }else {// 纯数字
	                Double big=new Double(cell.getNumericCellValue());
	                value = big.toString();
	                //解决1234.0  去掉后面的.0  

				  if(null!=value&&!"".equals(value.trim())){ String[] item =
				  value.split("[.]"); if(1<item.length&&"0".equals(item[1])){ value=item[0]; }
				  }

	               
	            }  
	            break;  
	            //字符串类型   
	        case Cell.CELL_TYPE_STRING:
	        	if(cell.getStringCellValue().toString().split("/").length==3){
	        		String[] date=cell.getStringCellValue().toString().split("/");
					value=date[0]+"-"+date[1]+"-"+date[2];
				}else value = cell.getStringCellValue().toString();
	            break;  
	        // 公式类型  
	        case Cell.CELL_TYPE_FORMULA:  
	            //读公式计算值  
	            value = String.valueOf(cell.getNumericCellValue());  
	            if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串  
	                value = cell.getStringCellValue().toString();  
	            }  
	            break;  
	        // 布尔类型  
	        case Cell.CELL_TYPE_BOOLEAN:  
	            value = " "+ cell.getBooleanCellValue();  
	            break;   
	        default:  
	            value = cell.getStringCellValue().toString();  
	    }  
	    if("null".endsWith(value.trim())){  
	        value="";  
	    }  
	  return value;  
	    }

}
