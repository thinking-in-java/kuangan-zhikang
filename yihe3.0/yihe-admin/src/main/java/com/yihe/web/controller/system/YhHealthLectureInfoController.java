package com.yihe.web.controller.system;

import com.yihe.common.core.controller.BaseController;
import com.yihe.common.utils.poi.ExcelUtilQ;
import com.yihe.system.domain.YhHealthLectureCategory;
import com.yihe.system.domain.YhHealthLectureInfo;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhHealthLectureWatchMapper;
import com.yihe.system.service.*;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * YhHealthLectureInfocontroller Created by dong on 2020/3/27.
 */
@Controller
@RequestMapping("/YhHealthLectureInfo")
@Api(value = "YhHealthLectureInfo控制器", description = "YhHealthLectureInfo管理")
public class YhHealthLectureInfoController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(YhHealthLectureInfoController.class);

	@Autowired
	private YhHealthLectureInfoService yhHealthLectureInfoService;

	@Autowired
	private YhHealthLectureCommentService yhHealthLectureCommentService;

	@Autowired
	private YhHealthLectureCategoryService yhHealthLectureCategoryService;
	@Autowired
	YhHealthLectureWatchService yhHealthLectureWatchService;
	
	@Autowired
	YhHealthLectureWatchMapper yhHealthLectureWatchMapper;
	
	@Autowired
	 private YhFoodCookBookService yhFoodCookBookService;

	@ApiOperation(value = "查询课程名称是否存在")
	@RequestMapping(value = "/selctTitleRepetition", method = RequestMethod.GET)
	@ResponseBody
	public int selctTitleRepetition(String title, int infoid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("infoid", infoid);
		int show = yhHealthLectureInfoService.selctTitleRepetition(map);
		return show;
	}

	@ApiOperation(value = "查询id查询分类")
	@RequestMapping(value = "/showpaidselectByPrimaryKey", method = RequestMethod.POST)
	@ResponseBody
	public Result showpaidselectByPrimaryKey(int categoryid) {
		YhHealthLectureCategory list = yhHealthLectureCategoryService.selectByPrimaryKey(categoryid);
		return Result.ok(list);
	}

	@ApiOperation(value = "查询课堂分类一级分类")
	@RequestMapping(value = "/showpaid", method = RequestMethod.POST)
	@ResponseBody
	public Result showpaid() {
		List<YhHealthLectureCategory> list = yhHealthLectureCategoryService.showpaid();
		return Result.ok(list);
	}

	@ApiOperation(value = "查询课堂分类二级分类")
	@RequestMapping(value = "/showclassify", method = RequestMethod.POST)
	@ResponseBody
	public Result showclassify(String parentId) {
		List<YhHealthLectureCategory> list = yhHealthLectureCategoryService.showcategoryparentId(parentId);
		return Result.ok(list);
	}

	@ApiOperation(value = "课堂删除")
	@RequestMapping(value = "/Infodele", method = RequestMethod.POST)
	@ResponseBody
	public Result Infodele(int id) {
		int i = yhHealthLectureInfoService.deleteByPrimaryKey(id);
		return Result.ok(i);
	}

	@ApiOperation(value = "根据id查询课堂")
	@RequestMapping(value = "/Infoselect", method = RequestMethod.POST)
	@ResponseBody
	public Result Infoselect(int id) {
		YhHealthLectureInfo info = yhHealthLectureInfoService.selectByPrimaryKey(id);
		return Result.ok(info);
	}

	/**
	 * 增加课堂
	 * 
	 * @param parentId
	 * @return
	 * @throws ParseException
	 */
	@ApiOperation(value = "增加课堂")
	@RequestMapping(value = "/insertInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result insertInfo(YhHealthLectureInfo info) throws ParseException {
		
		if(info.getThreelevel() == null || info.getThreelevel().equals("")) {//三级
			if (info.getCategoryIdxiao() == null || info.getCategoryIdxiao().equals("")) {//二级
				info.setCategoryId(Integer.valueOf(info.getCategoryIdda()));
			}else {
				info.setCategoryId(Integer.valueOf(info.getCategoryIdxiao()));
			}
		}else {
			info.setCategoryId(Integer.valueOf(info.getThreelevel()));
		}
		
		if (info.getState() == null || info.getState() == 0) {
			info.setState(1);
		}
		Date data = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// yyyy-mm-dd, 会出现时间不对, 因为小写的mm是代表: 秒
		Date utilDate =null;
		if(info.getUpdate()==null || info.getUpdate().equals("")) {
			utilDate = data;
		}else {
			utilDate = sdf.parse(info.getUpdate());
		}
		System.out.println(utilDate);// 查看utilDate的值
		Date date = new java.sql.Date(utilDate.getTime());
		info.setUpdateDate(date);
		info.setAddinfotime(sdf.format(data));
		YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		String userName = yhUserReginfo.getName();
		//info.setAuthor(userName);
		
		Subject subject = SecurityUtils.getSubject();
		 YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();
		 String usercode = principal.getUserCode();
		 info.setUserCode(usercode);
		int i;
		if (info.getInfoid() == 0) {
			i = yhHealthLectureInfoService.insertSelective(info);
		} else {
			i = yhHealthLectureInfoService.updateByPrimaryKeySelective(info);
		}

		return Result.ok(i);
	}

	/**
	 * 查询所有课堂内容
	 * 
	 * @param page
	 * @param limit
	 * @param price
	 * @param prie
	 * @return
	 */
	@ApiOperation(value = "查询所有课堂内容")
	@RequestMapping(value = "/selectLectureInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectAll(String author,Integer page, Integer limit,String typeid, String price, String doctors, String classify, String classify1) {
		Map<String, Object> map1 = new HashMap<String, Object>();
		Pages pa = new Pages();
		pa.setCurPage(page);
		pa.setMaxResult(limit);
		map1.put("page", pa.getFirstRows());
		
		if (classify1 == null || classify1.equals("")) {//三级分类
			if(classify != null && !classify.equals("")) {//二级分类
				YhHealthLectureCategory ory=yhHealthLectureCategoryService.selectByPrimaryKey(Integer.valueOf(classify));
				if(ory.getParentId().equals("0")) {
					map1.put("categoryId", classify);
        		}else {
        			String add = "and (info.category_id =" + classify + "  ";
					List<YhHealthLectureCategory> gor = yhHealthLectureCategoryService.showcategoryparentId(classify);
					for (YhHealthLectureCategory yhHealthLectureCategory : gor) {
						add += "or info.category_id =" + yhHealthLectureCategory.getCategoryid() + "  ";
					}
					add+=")";
					map1.put("superaddition", add);
        		}
				
			}else {
				if (doctors != null && !doctors.equals("")) {//一级分类
					if(doctors.equals("0")) {
						map1.put("doctors", doctors);
					}else {
						String add = "and (info.category_id =" + doctors + "  ";
						List<YhHealthLectureCategory> gor = yhHealthLectureCategoryService.showcategoryparentId(doctors);
						for (YhHealthLectureCategory yhHealthLectureCategory : gor) {
							add += "or info.category_id =" + yhHealthLectureCategory.getCategoryid() + "  ";
						}
						add+=")";
						map1.put("superaddition", add);
					}
				}
			}
		}else {
			map1.put("categoryId", classify1);
		}

		map1.put("limit", pa.getMaxResult());
		map1.put("title", price);
		map1.put("typeid", typeid);
		map1.put("author", author);
		 Subject subject = SecurityUtils.getSubject();
		 YhUserReginfo principal = (YhUserReginfo) subject.getPrincipal();

		 String orgCode = principal.getOrgCode();
		 if(StringUtil.isNotEmpty(orgCode)&&!"10000".equals(orgCode)){
			 map1.put("orgCode", principal.getUserCode());
		 }
		 if(StringUtil.isEmpty(orgCode)){
			 orgCode="10000";
		 }
		map1.put("orgCode2", orgCode);
		List<YhHealthLectureInfo> list = yhHealthLectureInfoService.selectLectureInfo(map1);
		for (YhHealthLectureInfo yhHealthLectureInfo : list) {
			if(yhHealthLectureInfo.getParentId()==null) {
				yhHealthLectureInfo.setName("--");
			}else if (!yhHealthLectureInfo.getParentId().equals("0")) {
				YhHealthLectureCategory ory = yhHealthLectureCategoryService
						.selectByPrimaryKey(Integer.valueOf(yhHealthLectureInfo.getParentId()));
				yhHealthLectureInfo.setName(ory.getName() + ">" + yhHealthLectureInfo.getName());
			}


		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 20000);
		map.put("msg", "");
		map.put("count", yhHealthLectureInfoService.selectLectureInfoConut(map1));
		map.put("data", list);
		return map;
	}

	@RequestMapping(value = "/exportXls", method = RequestMethod.GET)
	@ResponseBody
	public void exportXls(HttpServletResponse response, Integer infoid, Integer categoryId,Integer typeid) throws Exception {
		System.out.println("进来了！！！！！！！！！！"+infoid+"!!!"+categoryId);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = null;
		String[] headers =null; 
		String fileName =null;
		if(typeid==1 ) {
			 sheet = workbook.createSheet("视频观看列表");
			  headers = new String[]{"序号","课堂名称", "用户编码", "用户姓名", "手机号码", "年龄", "所属单位", "开始观看时间", "完成观看时间" };
		}
		if(typeid==5 ) {
			 sheet = workbook.createSheet("文章访问列表");
			  headers = new String[]{"序号","课堂名称", "用户编码", "用户姓名", "手机号码", "年龄", "所属单位", "访问时间", "离开时间" };
		}
		 
		//String fileName = "健康课堂访问导出" + ".xls";// 设置要导出的文件的名字
		List<Map<String, Object>> classroomData = yhHealthLectureWatchService.selectexportClassroomData(infoid,categoryId);
		// System.out.println("课堂数据："+classroomData);
		// 设置文件名
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//String fileName = null;// 设置要导出的文件的名字
		// 新增数据行，并且设置单元格数据
		int rowNum = 1;
		// headers表示excel表中第一行的表头
		
		// 在excel表中添加表头
		HSSFRow row = sheet.createRow(0);

		// 列数
		/*int cno=exportDataList.get(0).getClass().getDeclaredFields().length;*/
		// 合并单元格

		// 设置行高
		row.setHeightInPoints(36);
		// 设置列宽度
		/*sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 10 * 256);
		sheet.setColumnWidth(2, 10 * 256);*/
		sheet.setColumnWidth(0, 1750);
		sheet.setColumnWidth(1, 9350);
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 4500);
		sheet.setColumnWidth(4, 4500);
		sheet.setColumnWidth(5, 1500);
		sheet.setColumnWidth(6, 11000);
		sheet.setColumnWidth(7, 6350);
		sheet.setColumnWidth(8, 6350);
		

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
		// 在表中存放查询到的数据放入对应的列

		for (int i = 0; i < classroomData.size(); i++) {
			System.out.println("---" + classroomData.get(i));
			Object userCode = classroomData.get(i).get("user_code");
			System.out.println("---" + userCode);

			// 创建excel中的行
			HSSFRow row1 = sheet.createRow(rowNum);
			
			fileName = "健康课堂（"+classroomData.get(i).get("headline")+"）"+"访问导出" + ".xls";// 设置要导出的文件的名字
			// 在表中存放查询到的数据放入对应的列
			row1.createCell(0).setCellValue(i+1);
			if(classroomData.get(i).get("headline")!=null) {
				row1.createCell(1).setCellValue(classroomData.get(i).get("headline").toString());
			}
			if(classroomData.get(i).get("user_code")!=null) {
				row1.createCell(2).setCellValue(classroomData.get(i).get("user_code").toString());
			}
			if(classroomData.get(i).get("name")!=null) {
				row1.createCell(3).setCellValue(classroomData.get(i).get("name").toString());
			}
			if(classroomData.get(i).get("mobile")!=null) {
				row1.createCell(4).setCellValue(classroomData.get(i).get("mobile").toString());
			}
			
			if(classroomData.get(i).get("age")!=null) {
				row1.createCell(5).setCellValue(classroomData.get(i).get("age").toString());
			}
			if(classroomData.get(i).get("org_name")!=null) {
				row1.createCell(6).setCellValue(classroomData.get(i).get("org_name").toString());
			}
			if(classroomData.get(i).get("watch_date")!=null) {
				row1.createCell(7).setCellValue(classroomData.get(i).get("watch_date").toString());
			}
			if(classroomData.get(i).get("end_time")!=null) {
				row1.createCell(8).setCellValue(classroomData.get(i).get("end_time").toString());
			}
			
			rowNum++;
		}

		// 必须在单元格设值以后进行
		// 设置为根据内容自动调整列宽
		for (int k = 0; k < classroomData.size(); k++) {
			 //设置自动列宽
			
           // sheet.setColumnWidth(k,sheet.getColumnWidth(k)*17/10);
			
		}
		// 处理中文不能自动调整列宽的问题
		setSizeColumn(sheet, classroomData.size());

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
	
	@RequestMapping(value = "/exportAllData", method = RequestMethod.GET)
	@ResponseBody
	public void exportAllData(HttpServletResponse response,String type) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		String fileName = "健康课堂访问导出" + ".xls";// 设置要导出的文件的名字
		// 创建sheet名
		List<String> sheetname = new ArrayList<String>();
		// 创建标题  
		List<List<String>> titles = new ArrayList<List<String>>();
		List<Map<String, Object>> AllclassroomData= new ArrayList<Map<String,Object>>();
		if("today".equals(type)) {
			AllclassroomData = yhHealthLectureWatchService.selecteAllClassroomData();
		}
		if("yesterday".equals(type)) {
			AllclassroomData = yhHealthLectureWatchService.selecteYesterdayAllClassroomData();
		}
		if("thisweek".equals(type)) {
			AllclassroomData = yhHealthLectureWatchService.selecteThisweekAllClassroomData();
		}
		if("laskweek".equals(type)) {
			AllclassroomData = yhHealthLectureWatchService.selectelaskweekAllClassroomData();
		}
		
		List<String> videoTitle = Arrays.asList("序号","课堂名称", "用户编码", "用户姓名", "手机号码", "年龄", "所属单位", "开始观看时间", "完成观看时间" );
		sheetname.add("视频观看列表");
		titles.add(videoTitle);
		List<String> articleTitle = Arrays.asList("序号","课堂名称", "用户编码", "用户姓名", "手机号码", "年龄", "所属单位", "访问时间", "离开时间");
		sheetname.add("文章访问列表");
		titles.add(articleTitle);
		for (int j = 0; j < sheetname.size(); j++) {
			HSSFSheet sheet = workbook.createSheet(sheetname.get(j));
			int dd = 1;
			int bb = 1;
			int rowNum = 1;
			int rowNum2 = 1;
		for (int i = 0; i < AllclassroomData.size(); i++) {
			// 循环标题（多页结构）
						ExcelUtilQ.createTitle(workbook, sheet, titles.get(j),j);
					// 创建sheet
					
					
					if(sheetname.get(j).equals("视频观看列表") && Integer.valueOf(AllclassroomData.get(i).get("typeid").toString())==1) {
						// 新增数据行，并且设置单元格数据
						
						HSSFRow row = sheet.createRow(rowNum);
						// 在表中存放查询到的数据放入对应的列
						row.createCell(0).setCellValue(dd);
						row.createCell(1).setCellValue(AllclassroomData.get(i).get("title").toString());
						row.createCell(2).setCellValue(AllclassroomData.get(i).get("user_code").toString());
						row.createCell(3).setCellValue(AllclassroomData.get(i).get("name").toString());
						row.createCell(4).setCellValue(AllclassroomData.get(i).get("mobile").toString());
						if(AllclassroomData.get(i).get("age")!=null) {
							row.createCell(5).setCellValue(AllclassroomData.get(i).get("age").toString());
						}
						
						
						row.createCell(6).setCellValue(AllclassroomData.get(i).get("org_name").toString());
						row.createCell(7).setCellValue(AllclassroomData.get(i).get("watch_date").toString());
						if(AllclassroomData.get(i).get("end_time")!=null) {
						row.createCell(8).setCellValue(AllclassroomData.get(i).get("end_time").toString());
						}
						rowNum++;
						dd++;
					}
				if(sheetname.get(j).equals("文章访问列表") && Integer.valueOf(AllclassroomData.get(i).get("typeid").toString())==5) {
					// 新增数据行，并且设置单元格数据
					HSSFRow row = sheet.createRow(rowNum2);
					// 在表中存放查询到的数据放入对应的列
					row.createCell(0).setCellValue(bb+1);
					row.createCell(1).setCellValue(AllclassroomData.get(i).get("title").toString());
					row.createCell(2).setCellValue(AllclassroomData.get(i).get("user_code").toString());
					row.createCell(3).setCellValue(AllclassroomData.get(i).get("name").toString());
					row.createCell(4).setCellValue(AllclassroomData.get(i).get("mobile").toString());
					if(AllclassroomData.get(i).get("age")!=null) {
						row.createCell(5).setCellValue(AllclassroomData.get(i).get("age").toString());
					}
				
					row.createCell(6).setCellValue(AllclassroomData.get(i).get("org_name").toString());
					row.createCell(7).setCellValue(AllclassroomData.get(i).get("watch_date").toString());
					if(AllclassroomData.get(i).get("end_time")!=null) {
						row.createCell(8).setCellValue(AllclassroomData.get(i).get("end_time").toString());	
					}
					
					rowNum2++;
					bb++;
				}
			}
				
			
		
					
	}
		ExcelUtilQ.exportExcel(response, workbook);		
		
		
}

	/* public static void main(String[] args) {
	
	     List<Map<String, Object>> listMaps = new ArrayList<Map<String, Object>>();
	
	     Map<String, Object> map1 = new HashMap<String, Object>();
	     map1.put("1", "a");
	     map1.put("2", "b");
	     map1.put("3", "c");
	     listMaps.add(map1);
	
	     Map<String, Object> map2 = new HashMap<String, Object>();
	     map2.put("11", "aa");
	     map2.put("22", "bb");
	     map2.put("33", "cc");
	     listMaps.add(map2);
	
	     for (Map<String, Object> map : listMaps) {
	         for (String s : map.keySet()) {
	             System.out.print(map.get(s) + "  ");
	         }
	     }
	     System.out.println();
	     System.out.println("========================");
	     for (int i = 0; i < listMaps.size(); i++) {
	         Map<String, Object> map = listMaps.get(i);
	         Iterator iterator = map.keySet().iterator();
	         while (iterator.hasNext()) {
	             String string = (String) iterator.next();
	             System.out.println(map.get(string));
	         }
	     }
	     System.out.println("++++++++++++++++++++++++++++");
	     for (Map<String, Object> map : listMaps) {
	         for (Map.Entry<String, Object> m : map.entrySet()) {
	             System.out.print(m.getKey() + "    ");
	             System.out.println(m.getValue());
	         }
	     }
	     System.out.println("-----------------------------");
	 }
	*/

}