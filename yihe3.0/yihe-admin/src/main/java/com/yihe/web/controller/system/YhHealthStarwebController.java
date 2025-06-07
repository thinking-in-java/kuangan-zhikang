package com.yihe.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhHealthStarweb;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhHealthStarwebMapper;
import com.yihe.system.mapper.YhLifeHabitImprovementDictMapper;
import com.yihe.system.service.YhHealthStarwebService;
import com.yihe.system.service.YhUserStepWebService;
import com.yihe.system.utils.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 积分记录控制器
 * @author 阳雄
 * date 2020年12月29日14:56:16
 *
 */
@Controller
@RequestMapping("/YhHealthStarweb")
public class YhHealthStarwebController {  
	
	 private static final Logger LOGGER = LoggerFactory.getLogger("deliver_log");
	 
	@Autowired
	YhUserStepWebService YhUserStepWebService;
	@Autowired
	YhHealthStarwebService YhHealthStarwebService;
	@Autowired
	YhHealthStarwebMapper YhHealthStarwebMapper;
	@Autowired
	YhLifeHabitImprovementDictMapper yhLifeHabitImprovementDictMapper;
	@ResponseBody
    @RequestMapping(value="/YhHealthStarPage",method=RequestMethod.GET)
	public Map<String,Object> queryUserYhHealthStar(String orgCode,String name,String priceMin,String priceMax,int page,int limit,String times){
		YhUserReginfo userReginfo = (YhUserReginfo)SecurityUtils.getSubject().getPrincipal();
		if(StringUtil.isEmpty(orgCode)){
			orgCode=userReginfo.getOrgCode();
		}
		PageInfo<YhHealthStarweb> data  = YhHealthStarwebService.queryUserByCriteriaWithPage2(userReginfo.getUserType(),userReginfo.getUserCode(),orgCode, name, priceMin, priceMax, page, limit,times);

	       Map<String,Object> result = new HashMap<String, Object>();
	       result.put("data",data.getList());
	       result .put("count", data.getTotal());
	       result.put("msg", "");
	       result.put("code", 20000);
	          return result;
		
	}

	
	@RequestMapping(value = "/exportIntegralData", method = RequestMethod.GET)
	@ResponseBody
	public void exportXls(HttpServletResponse response,String orgCode,String name,String priceMin,String priceMax,String times)
			throws Exception {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet =workbook.createSheet("健康评估结果");
		// 设置文件名
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String[] headers = new String[] { "序号", "用户编码", "姓名", "积分收入", "积分支出","当前积分"};
		// 新增数据行，并且设置单元格数据
		int rowNum = 1;
		// headers表示excel表中第一行的表头
		// 在excel表中添加表头
		HSSFRow row = sheet.createRow(0);
		// 设置行高
		row.setHeightInPoints(36);
		// 设置列宽度
		/*sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 10 * 256);
		sheet.setColumnWidth(2, 10 * 256);*/
		sheet.setColumnWidth(0, 1750);
		sheet.setColumnWidth(1, 4500);
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 1750);
		sheet.setColumnWidth(4, 1750);
		sheet.setColumnWidth(5, 1750);

		// 在excel表中添加表头
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			 //设置自动列宽
			/* sheet.autoSizeColumn(i);
			 sheet.setColumnWidth(i,sheet.getColumnWidth(i)*17/10);*/
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
		
		YhUserReginfo userReginfo = (YhUserReginfo)SecurityUtils.getSubject().getPrincipal();
		// 在表中存放查询到的数据放入对应的列
		 //List<YhHealthStarweb> selectUserAllIntegral = YhHealthStarwebMapper.selectUserAllIntegral(userReginfo.getOrgCode(), null, null, null);
		List<YhHealthStarweb> selectUserAllIntegral =null;
		String time =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime());
		String fileName = "用户积分-" + time + ".xls";// 设置要导出的文件的名字
		if(StringUtil.isEmpty(orgCode)){
			orgCode=userReginfo.getOrgCode();
		}
		int page=1;
		int limit=Integer.MAX_VALUE;
		PageInfo<YhHealthStarweb> data  = YhHealthStarwebService.queryUserByCriteriaWithPage2(userReginfo.getUserType(),userReginfo.getUserCode(),orgCode, name, priceMin, priceMax, page, limit,times);
		selectUserAllIntegral=data.getList();
		// 在表中存放查询到的数据放入对应的列
		for (int i = 0; i < selectUserAllIntegral.size(); i++) {
		    	   //积分支出
			       Integer expenditure = 0;
			     //积分收入 
			       Integer income = 0;
		    	   String userCode = selectUserAllIntegral.get(i).getUserCode();
		    	   List<YhHealthStarweb> userRecord = YhHealthStarwebService.selectAllUserRecord(userCode,times);
		    	   for (int j = 0; j < userRecord.size(); j++) {
		    		   Integer userType = userRecord.get(j).getUserType();
		    		   if(userType == 4) {
		    			   if(userRecord.get(j).getHealthstars()!=null) {
		    				   expenditure = expenditure + userRecord.get(j).getHealthstars();
		    			   }
		    			   
		    		   }
		    		   if(userType == 5) {
		    			   if(userRecord.get(j).getHealthstars()!=null) {
		    				   income = income + userRecord.get(j).getHealthstars();
		    			   }
		    			   
		    		   }
				}
		    	   selectUserAllIntegral.get(i).setExpenditure(expenditure);
		    	   selectUserAllIntegral.get(i).setIncome(income);
		
			// 创建excel中的行
			HSSFRow row1 = sheet.createRow(rowNum);
			// 在表中存放查询到的数据放入对应的列
			row1.createCell(0).setCellValue(i+1);
			row1.createCell(1).setCellValue(selectUserAllIntegral.get(i).getUserCode());
			row1.createCell(2).setCellValue(selectUserAllIntegral.get(i).getName());
			row1.createCell(3).setCellValue(selectUserAllIntegral.get(i).getIncome());
			row1.createCell(4).setCellValue(selectUserAllIntegral.get(i).getExpenditure());
			row1.createCell(5).setCellValue(selectUserAllIntegral.get(i).getHealthstarsEvents());
			
			rowNum++;
		}

		// 必须在单元格设值以后进行
		// 设置为根据内容自动调整列宽
		for (int k = 0; k < selectUserAllIntegral.size(); k++) {
			 //设置自动列宽
			
           // sheet.setColumnWidth(k,sheet.getColumnWidth(k)*17/10);
			
		}
		// 处理中文不能自动调整列宽的问题
		setSizeColumn(sheet, selectUserAllIntegral.size());

		response.setContentType("application/vnd.ms-excel");	
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		
		/* response.setHeader("Content-disposition", "attachment;filename=" + fileName);*/
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
	

	@ResponseBody
    @RequestMapping(value="/userIntegralRecordPage",method=RequestMethod.GET)
	public Map<String,Object> userIntegralRecordPage(String userCode,String qtime,String validity,int page,int limit){
		String qtstrTime = "";
		String qtendTime = "";
		if (StringUtil.isNotEmpty(qtime)) {
			String[] split = qtime.split(" - ");
			qtstrTime = split[0];
			qtendTime = split[1];
		}
		  PageInfo<YhHealthStarweb> data = YhHealthStarwebService.selectAllUserRecordPage(userCode, qtstrTime, qtendTime, validity, page, limit);
	       Map<String,Object> result = new HashMap<String, Object>();
	       result.put("data",data.getList());
	       result .put("count", data.getTotal());
	       result.put("msg", "");
	       result.put("code", 20000);
	          return result;
		
	}
	
	
	
	
	
	
	
}
