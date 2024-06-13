/**
 * 
 */
package com.yihe.web.controller.system;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhHealthLectureComment;
import com.yihe.system.domain.YhHealthLectureInfo;
import com.yihe.system.domain.YhLifeHabitDict;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhHealthLectureInfoMapper;
import com.yihe.system.mapper.YhLifeHabitDictMapper;
import com.yihe.system.service.YhHealthLectureCommentService;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 评论
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhHealthLectureComment")
@Api(value = "YhHealthLectureComment控制器", description = "YhHealthLectureComment管理")
public class YhHealthLectureCommentController {

	 @Autowired
	 YhHealthLectureCommentService yhHealthLectureCommentService;
	 
	 @Autowired
	 YhHealthLectureInfoMapper yhHealthLectureInfoMapper;
	 
	 @Autowired
	 YhLifeHabitDictMapper yhLifeHabitDictMapper;
	 
	 
	  @ApiOperation(value = "习惯大类查询")
	    @RequestMapping(value = "/showhabitDictCodenull", method = RequestMethod.POST)
		@ResponseBody
	    public Result  showhabitDictCodenull() {
		  List<YhLifeHabitDict> list=yhLifeHabitDictMapper.showhabitDictCodenull();
	        return Result.ok(list);
	    }
	  
	 
	 @ApiOperation(value = "习惯导出")
	    @RequestMapping(value = "/exportXls", method = RequestMethod.GET)
		@ResponseBody
	    public void  exportXls(HttpServletResponse response,String title,String entryTime,String orgCode) throws IOException {
		 Map<String,Object> map1 = new HashMap<String,Object>();
	 		map1.put("title", title);
	 		map1.put("orgCode", orgCode);
	 		if(entryTime!=null && !entryTime.equals("")) {
	 			String[] strArr = entryTime.split(" - ");
	 		map1.put("entryTime", strArr[0]);
	 		map1.put("entryTime1", strArr[1]);
	 		}
	 		
	 		
	 		HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet =workbook.createSheet("健康习惯");

			List<YhHealthLectureComment> list = yhHealthLectureCommentService.showHabitExport(map1);//
			
			// 设置文件名
			String[] headers = new String[] { "序号", "习惯主题", "用户编码", "用户姓名", "手机号码", "所属单位", "打卡时间", "打卡内容", "点赞", "被回复"};
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
			String time =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime());
			String fileName = "健康习惯打卡记录导出-"+time+".xls";// 设置要导出的文件的名字
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");


			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			// 在表中存放查询到的数据放入对应的列
			for (YhHealthLectureComment yhHealthLectureComment : list) {
				HSSFRow row1 = sheet.createRow(rowNum);
				
				// 在表中存放查询到的数据放入对应的列
				row1.createCell(0).setCellValue(rowNum+1);
				row1.createCell(1).setCellValue(yhHealthLectureComment.getTitle());
				row1.createCell(2).setCellValue(yhHealthLectureComment.getUsercode());
				row1.createCell(3).setCellValue(yhHealthLectureComment.getUserName());
				row1.createCell(4).setCellValue(yhHealthLectureComment.getMobile());
				row1.createCell(5).setCellValue(yhHealthLectureComment.getOrgName());
				row1.createCell(6).setCellValue(yhHealthLectureComment.getCommenttime());
				if(yhHealthLectureComment.getCommentinfo()==null || yhHealthLectureComment.getCommentinfo().equals("")) {
					row1.createCell(7).setCellValue(yhHealthLectureComment.getComimg());
				}else if(yhHealthLectureComment.getComimg()==null || yhHealthLectureComment.getComimg().equals("")) {
					row1.createCell(7).setCellValue(yhHealthLectureComment.getCommentinfo());
				}else {
					row1.createCell(7).setCellValue(yhHealthLectureComment.getCommentinfo()+""+yhHealthLectureComment.getComimg());
				}
				
	 			row1.createCell(8).setCellValue(0);
	 			int pi=0;
	 			if(yhHealthLectureComment.getCommentid()!=null) {
	 				pi=yhHealthLectureCommentService.selectnumber(yhHealthLectureComment.getCommentid());
	 			}
				row1.createCell(9).setCellValue(pi);
				rowNum++;
			}
			
			// 必须在单元格设值以后进行
			// 设置为根据内容自动调整列宽
			for (int k = 0; k < list.size(); k++) {
				 //设置自动列宽
				
	           // sheet.setColumnWidth(k,sheet.getColumnWidth(k)*17/10);
				
			}
			// 处理中文不能自动调整列宽的问题
			setSizeColumn(sheet, list.size());

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
				if(columnWidth>255) {
					columnWidth=254;
				}
				sheet.setColumnWidth(columnNum, columnWidth*256);
			}

		}
	 
	  @ApiOperation(value = "评论删除")
	    @RequestMapping(value = "/deleteByPrimaryKeyPid", method = RequestMethod.POST)
		@ResponseBody
	    public Result  deleteByPrimaryKeyPid(int id,int type) {
		  if(type==1) {
			  yhHealthLectureCommentService.deleteByPrimaryKeyPid(id);
		  }
	    	int i=yhHealthLectureCommentService.deleteByPrimaryKey(id);
	        return Result.ok(i);
	    }
	  
	 
	 @ApiOperation(value = "增加评论内容")
     @RequestMapping(value = "/insertcomment", method = RequestMethod.POST)
 	@ResponseBody
	 public Result commenint(YhHealthLectureComment com) {
		 Subject subject = SecurityUtils.getSubject();
			YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
			String usercode = yh.getUserCode();
			Date now = new Date(); 
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
			com.setUsercode(usercode);
			com.setCommenttime(dateFormat.format(now));
			int i=yhHealthLectureCommentService.insertSelective(com);
		return Result.ok(i);
	 }
	 
	 
	     @ApiOperation(value = "查询所有一级评论内容")
	     @RequestMapping(value = "/selectcomment", method = RequestMethod.POST)
	 	@ResponseBody
	 	public Map<String,Object> selectAll(Integer page,Integer limit,String title,String status,String entryTime,String orgCode){
	 		Map<String,Object> map1 = new HashMap<String,Object>();
	 		Pages pa = new Pages();
	 		pa.setCurPage(page);
	 		pa.setMaxResult(limit);
	 		map1.put("page", pa.getFirstRows());
	 		map1.put("limit", pa.getMaxResult());
	 		map1.put("title", title);
	 		map1.put("status", status);
	 		map1.put("orgCode", orgCode);
	 		if(entryTime!=null && !entryTime.equals("")) {
	 			String[] strArr = entryTime.split(" - ");
	 		map1.put("entryTime", strArr[0]);
	 		map1.put("entryTime1", strArr[1]);
	 		}
	 		List<YhHealthLectureComment> list = yhHealthLectureCommentService.selectComment(map1);
	 		for (YhHealthLectureComment yhHealthLectureComment : list) {
	 			int pi=yhHealthLectureCommentService.selectnumber(yhHealthLectureComment.getCommentid());
	 			if(pi==0) {
	 				yhHealthLectureComment.setNumber(0);
	 			}else {
		 			yhHealthLectureComment.setNumber(pi);
	 			}
	 			if(yhHealthLectureComment.getStatus()==1) {//课程
	 				YhHealthLectureInfo info=yhHealthLectureInfoMapper.selectByPrimaryKey(yhHealthLectureComment.getLectureinfoid());
	 				if(info==null) {
	 					yhHealthLectureComment.setTitle("--");
	 				}else {
	 					yhHealthLectureComment.setTitle(info.getTitle());
	 				}
	 				
	 			}else {
	 				 YhLifeHabitDict dict=yhLifeHabitDictMapper.selectByPrimaryKey(yhHealthLectureComment.getLectureinfoid());
	 				 if(dict==null) {
	 					yhHealthLectureComment.setTitle("--");
	 				 }else {
	 					yhHealthLectureComment.setTitle(dict.getHabitName());
	 				 }
	 				
	 			}
			}	 	
	 		Map<String,Object> map = new HashMap<String,Object>();
	 		map.put("code", 20000);
	 		map.put("msg", "");
	 		map.put("count",yhHealthLectureCommentService.selectCommentConut(map1));
	 		map.put("data", list);
	 		return map;
	 	}
	     
	     
	     @ApiOperation(value = "查询所有二级评论内容")
	     @RequestMapping(value = "/selectsecondComment", method = RequestMethod.POST)
	 	@ResponseBody
	 	public Map<String,Object> selectsecondComment(Integer page,Integer limit,int pid){
	 		Map<String,Object> map1 = new HashMap<String,Object>();
	 		Pages pa = new Pages();
	 		pa.setCurPage(page);
	 		pa.setMaxResult(limit);
	 		map1.put("page", pa.getFirstRows());
	 		map1.put("limit", pa.getMaxResult());
	 		map1.put("pid", pid);
	 		List<YhHealthLectureComment> list = yhHealthLectureCommentService.selectsecondComment(map1);
	 	
	 		Map<String,Object> map = new HashMap<String,Object>();
	 		map.put("code", 20000);
	 		map.put("msg", "");
	 		map.put("count",yhHealthLectureCommentService.selectsecondCommentConut(map1));
	 		map.put("data", list);
	 		return map;
	 	}
	    
}
