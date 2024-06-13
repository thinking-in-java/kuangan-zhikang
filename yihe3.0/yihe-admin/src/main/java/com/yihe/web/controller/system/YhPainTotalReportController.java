package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yihe.common.utils.file.FileUtils;
import com.yihe.system.domain.YhPainTotalReport;
import com.yihe.system.domain.YhPainTotalReportRecord;
import com.yihe.system.domain.YhUserMakeCard;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhOrgTreeService;
import com.yihe.system.service.YhPainTotalReportService;
import com.yihe.system.service.YhUserApiService;
import com.yihe.system.service.YhUserMakeCardService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import com.yihe.web.controller.tool.HtmlToPdf;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.SheetUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/YhPainTotalReport")
public class YhPainTotalReportController {
	@Autowired
	YhPainTotalReportService yhPainTotalReportService;
	@Autowired
	YhUserMakeCardService yhUserMakeCardService;
	@Autowired
	YhOrgTreeService yhOrgTreeService;
	@Autowired
	private YhUserApiService yhUserApiService;
	
	@ApiOperation(value = "分页查询症状评估数据")
    @ResponseBody
    @RequestMapping(value="/page",method=RequestMethod.GET)
    public Map<String,Object> queryYhPainTotalReport(String userCode,int page,int limit,String qtime){
		String qtstrTime = "";
		String qtendTime = "";
		if (StringUtil.isNotEmpty(qtime)) {
			String[] split = qtime.split(" - ");
			qtstrTime = split[0];
			qtendTime = split[1];
		}
		
       //查询集合
      PageInfo<YhPainTotalReport> data = yhPainTotalReportService.queryUserByCriteriaWithPage(userCode,qtstrTime,qtendTime,page, limit);
       Map<String,Object> result = new HashMap<String, Object>();
       result.put("data",data.getList());
       result .put("count", data.getTotal());
       result.put("msg", "");
       result.put("code", 20000);
          return result;
    }
    @ResponseBody
    @RequestMapping(value="/allPage",method=RequestMethod.GET)
    public Map<String,Object> queryAllYhPainTotalReport(String symptom,String orgCode,String name,int page,int limit,String qtime){
    	YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
    	String userCode = yhUserReginfo.getUserCode();
		String qtstrTime = "";
		String qtendTime = "";
		if (StringUtil.isNotEmpty(qtime)) {
			String[] split = qtime.split(" - ");
			qtstrTime = split[0];
			qtendTime = split[1];
		}
		
       //查询集合
      PageInfo<YhPainTotalReport> data = yhPainTotalReportService.queryAllUserByCriteriaWithPage(symptom,orgCode,name,userCode, qtstrTime, 
    		  qtendTime, page, limit);
       Map<String,Object> result = new HashMap<String, Object>();
       result.put("data",data.getList());
       result .put("count", data.getTotal());
       result.put("msg", "");
       result.put("code", 20000);
          return result;
    }
	
	//往字符串数组追加新数据
	private static String[] insert(String[] arr, String str) {
	    int size = arr.length;  //获取数组长度
	    String[] tmp = new String[size + 1];  //新建临时字符串数组，在原来基础上长度加一
	    for (int i = 0; i < size; i++){  //先遍历将原来的字符串数组数据添加到临时字符串数组
	        tmp[i] = arr[i];
	    }
	    tmp[size] = str;  //在最后添加上需要追加的数据
	    return tmp;  //返回拼接完成的字符串数组
	}

	public static void main(String[] args) {
		System.out.println("666");
	}
	
	@RequestMapping(value = "/gaoxueyaDataInfo2", method = RequestMethod.GET)
	@ResponseBody
	public Result gaoxueyaDataInfo2(String reportId,String usercode,HttpServletResponse response) {
		YhUserReginfo selectUserRegInfoByUserCode = yhUserApiService.selectUserRegInfoByUserCode(usercode);
		String fileName=selectUserRegInfoByUserCode.getName()+"常见症状评估报告与解决方案.pdf";
		System.out.println(HtmlToPdf.TEMP_DIR_PATH+fileName);
		String s = "http://localhost/manage/usertosee/symptomAssessment?reportId="+reportId;
		boolean convert = HtmlToPdf.convert(s.replace(" ",""), HtmlToPdf.TEMP_DIR_PATH+fileName);//本地
		if(convert){
			return Result.ok(fileName);
		}else{
			return Result.error();
		}
		
			
		
	}
	
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) {
  //这里文件名称是通过参数传递过来的，要是不需要，直接可以写在这里。String fileName = "a.doc";
		String path = HtmlToPdf.TEMP_DIR_PATH;
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
	
	
	
	@RequestMapping(value = "/symptomExportXls", method = RequestMethod.GET)
	@ResponseBody
	public void exportXls(HttpServletResponse response, String userCode, Integer reportId)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet =workbook.createSheet("健康评估结果");
		// 设置文件名
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String[] headers = new String[] { "用户编码", "姓名", "性别", "单位", "评估时间", "颈椎对照疼痛量表（NRS），您的疼痛等级接近于？",
				"肩颈对照疼痛量表（NRS），您的疼痛等级接近于？", "手腕对照疼痛量表（NRS），您的疼痛等级接近于？", "上背部对照疼痛量表（NRS），您的疼痛等级接近于？",
				"腰部对照疼痛量表（NRS），您的疼痛等级接近于？", "上身端坐，两腿并拢，颈椎自然挺直，自测耳垂垂直向下对应的身体部位", 
				"1.上身端坐，两腿并拢，颈椎自然挺直，首先轻轻的低头把下巴贴近胸口方向；2.然后缓慢抬头，将头往后背靠，眼睛尽量直视天花板；3.其次轻轻转头，鼻尖尽量与肩峰对齐，两侧都要测试。根据自测结果，请选择（注意此项测试切忌量力而行，不要生拉硬拽）",
				"您有以下哪种症状？", "通过自我触摸，您肩关节的疼痛位置在？", "您有以下哪种症状？", "如图所示，您上背部经常出现疼痛的位置是？（多选）？", 
				"上背部疼痛现象持续多长时间了？", "您有以下哪种症状？","通过自我触摸，您腰部的疼痛位置在？（多选）","腰部疼痛现象持续多长时间了？",
				"您有以下哪种症状？","您有以下哪种症状？","手腕疼痛现象持续多长时间了？"};
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
		row.setHeightInPoints(40);
		// 设置列宽度
		
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 10 * 256);
		sheet.setColumnWidth(2, 10 * 256);

		//设置列宽度

		sheet.setColumnWidth(0,10*256);
		sheet.setColumnWidth(1,10*256);
		sheet.setColumnWidth(2,10*256);
	
		
		// 在excel表中添加表头
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			sheet.setColumnWidth(i,25*512);
			//设置为居中加粗
			HSSFCellStyle style = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			HSSFPalette palette = workbook.getCustomPalette();
			palette.setColorAtIndex((short) 9, (byte) (0xff & 204), (byte) (0xff & 255), (byte) (0xff & 204));
			style.setFillForegroundColor((short) 9);// 设置背景色
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
		
		ArrayList<YhPainTotalReportRecord> exportDataList = new ArrayList<YhPainTotalReportRecord>(); 
		// 在表中存放查询到的数据放入对应的列
		YhPainTotalReportRecord reportRecord = yhPainTotalReportService.selectYhPainTotalReportParam(userCode, reportId);
		exportDataList.add(reportRecord);
		String time =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime());
		String fileName = userCode+"_"+reportRecord.getName()+"_"+time+"_"+"常见症状评估问卷" + ".xls";// 设置要导出的文件的名字
		

		YhUserMakeCard userMakeCard = yhUserMakeCardService.selectOrgIdbyParam(userCode);

		String orgName = "";
		if (userMakeCard.getOrgId() != null) {
			orgName = yhOrgTreeService.selectFullOrgName(userMakeCard.getOrgId().toString(), orgName);

		}

		String checkJson = reportRecord.getAssessImproved();
		JSONObject object = JSONObject.parseObject(checkJson);
		// 创建excel中的行
		HSSFRow row1 = sheet.createRow(rowNum);
		// 在表中存放查询到的数据放入对应的列
		String cervical1 = object.getString("cervical1");
		String shoulder1 = object.getString("shoulder1");
		String ontheback1 = object.getString("ontheback1");
		String waist1 = object.getString("waist1");
		String wrist1 = object.getString("wrist1");
		//颈椎题目cervical
		String cervical2 = object.getString("cervical2");
		String cervical3 = object.getString("cervical3");
		String cervical4 = object.getString("cervical4");
		//肩关节题目
		String shoulder2 = object.getString("shoulder2");
		String shoulder3 = object.getString("shoulder3");
		//上背部题目
		boolean isTrue = false;
		String ontheback = object.getString("ontheback2");
		String ontheback3 = object.getString("ontheback3");
		String ontheback4 = object.getString("ontheback4");
		row1.createCell(0).setCellValue(reportRecord.getUserCode());
		row1.createCell(1).setCellValue(reportRecord.getName());
		row1.createCell(2).setCellValue(reportRecord.getGender() == 1 ? "男" : "女");
		row1.createCell(3).setCellValue(orgName);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date resultTime = reportRecord.getResultTime();
		if(resultTime!=null) {
			String createdate = sdf2.format(resultTime);
			row1.createCell(4).setCellValue(createdate);
		}
		
		row1.createCell(5).setCellValue(cervical1.equals("0") ? "0级":cervical1.equals("2") ? "2级":cervical1.equals("5") ? "5级":cervical1.equals("7") ? "7级":"9级");
		row1.createCell(6).setCellValue(shoulder1.equals("0") ? "0级":shoulder1.equals("2") ? "2级":shoulder1.equals("5") ? "5级":shoulder1.equals("7") ? "7级":"9级");
		row1.createCell(7).setCellValue(wrist1.equals("0") ? "0级":wrist1.equals("2") ? "2级":wrist1.equals("5") ? "5级":wrist1.equals("7") ? "7级":"9级");
		row1.createCell(8).setCellValue(ontheback1.equals("0") ? "0级":ontheback1.equals("2") ? "2级":ontheback1.equals("5") ? "5级":ontheback1.equals("7") ? "7级":"9级");
		row1.createCell(9).setCellValue(waist1.equals("0") ? "0级":waist1.equals("2") ? "2级":waist1.equals("5") ? "5级":waist1.equals("7") ? "7级":"9级");

		if(cervical1.equals("0")){
			row1.createCell(10).setCellValue("耳垂对应肩膀中间");
			row1.createCell(11).setCellValue("都可以达标，但活动时明显有牵拉感或疼痛");
			row1.createCell(12).setCellValue("以上均无");
		}else{
			row1.createCell(10).setCellValue(cervical2.equals("0") ? "耳垂对应肩膀后方":cervical2.equals("1") ? "耳垂对应肩膀中间":"耳垂对应肩膀前方");
			row1.createCell(11).setCellValue(cervical3.equals("4") ? "四个方向都无法达到标准角度":cervical3.equals("3") ? "有两个或两个以上的方向无法达到标准角度":cervical3.equals("1") ? "都可以达标，但活动时明显有牵拉感或疼痛":"有疼痛牵拉感，并伴随头晕，心慌等症状");
			row1.createCell(12).setCellValue(cervical4.equals("2") ? "头晕，头痛，心慌，视物模糊或伴有失眠；":cervical4.equals("3") ? "颈部有疼痛，同时手和胳膊有疼痛、麻木的感觉；":
					cervical4.equals("1") ? "后颈有痛感时，抬头时痛感减轻，低头加压时痛感加重；":cervical4.equals("4") ? "两腿无力，不能走直线，偶尔会突然摔倒":"以上均无");
		}

		if(shoulder1.equals("0")){
			row1.createCell(13).setCellValue("以上部位都无");
			row1.createCell(14).setCellValue("以上均无");
		}else{
			row1.createCell(13).setCellValue(shoulder2.equals("3") ? "肩峰周围":shoulder2.equals("4") ? "三角肌":shoulder2.equals("2") ? "肩井穴及冈上肌部位":shoulder2.equals("5") ? "以上部位都有":"以上部位都无");
			row1.createCell(14).setCellValue(shoulder3.equals("2") ? "肩部疼痛，气候变化或劳累后症状加重":shoulder3.equals("3") ? "肩膀怕冷，有明显压痛":
					shoulder3.equals("4") ? "肩关节活动受限，以外展、上举、内外旋更为明显":shoulder3.equals("5") ? "两种以上症状":"以上均无");
		}

		if(ontheback1.equals("0")){
			row1.createCell(15).setCellValue("以上均无");
			row1.createCell(16).setCellValue("");
			row1.createCell(17).setCellValue("以上均无");
		}else{
			String[] ontheback2 = ontheback.substring(1,ontheback.length()-1).split(",");
			//判断数组是否包含0
			boolean flag = Arrays.asList(ontheback2).contains( "\"0\"");
			//拿到数组最后一个值，判断是否是选择的以上均无
			String last = ontheback2[ontheback2.length -1];
			//新建字符串数组
			String[] upperback = new String[ontheback2.length];
			if(last.equals("\"0\"")) {//0  以上均无
				row1.createCell(15).setCellValue("以上均无");
			}else { //不是0将数据追加到数组中
				for (int i = 0; i < ontheback2.length; i++) {
					if(ontheback2[i].equals("\"2\"")) {
						upperback = insert(upperback, "肩胛提肌");
					}else if(ontheback2[i].equals("\"3\"")) {
						upperback = insert(upperback, "上斜方肌");
					}else if(ontheback2[i].equals("\"5\"")) {
						upperback = insert(upperback, "大小菱形肌");
					}else if(ontheback2[i].equals("\"4\"")) {
						upperback = insert(upperback, "冈上肌");
					}
				}
				StringBuffer str5 = new StringBuffer();
				for (String s : upperback) {
					if(s!=null && s.length()!=0){
						str5.append(s+"；");
					}
				}
				row1.createCell(15).setCellValue(str5.toString());
			}
			if(ontheback3.equals("-1")) {
				row1.createCell(16).setCellValue("");
			}else {
				row1.createCell(16).setCellValue(ontheback3.equals("2") ? "一周内":ontheback3.equals("3") ? "一月左右":
						ontheback3.equals("4") ? "一月左右":"三年以上");
			}
			row1.createCell(17).setCellValue(ontheback4.equals("2") ? "单纯性局部疼痛，无放射感":ontheback4.equals("3") ? "疼痛伴随手臂或头部放射痛":
					ontheback4.equals("4") ? "疼痛伴随心慌或下蹲动作障碍":ontheback4.equals("5") ? "疼痛时感觉呼吸困难，头晕等不适":"以上均无");
		}

		
		//腰部题目
		String waist = object.getString("waist2");
		String waist3 = object.getString("waist3");
		String waist4 = object.getString("waist4");
		if(waist1.equals("0")){
			row1.createCell(18).setCellValue("以上部位都无");
			row1.createCell(19).setCellValue("");
			row1.createCell(20).setCellValue("以上均无");
		}else{
			String[] waist2 = waist.substring(1,waist.length()-1).split(",");
			//判断数组是否包含0
			//boolean flag = Arrays.asList(ontheback2).contains( "\"0\"");
			//拿到数组最后一个值，判断是否是选择的以上均无
			String waistLast = waist2[waist2.length -1];

			//新建字符串数组
			String[] waistArr = new String[waist2.length];
			if(waistLast.equals("\"0\"")) {
				row1.createCell(18).setCellValue("以上部位都无");
			}else {
				for (int i = 0; i < waist2.length; i++) {
					if(waist2[i].equals("\"3\"")) {
						waistArr = insert(waistArr, "腰部脊柱中间");
					}
					if(waist2[i].equals("\"4\"")) {
						waistArr = insert(waistArr, "脊柱左侧或右侧");
					}
					if(waist2[i].equals("\"2\"")) {
						waistArr = insert(waistArr, "腰骶部两侧");
					}
					if(waist2[i].equals("\"5\"")) {
						waistArr = insert(waistArr, "臀部左侧或右侧");
					}
				}
				StringBuffer waiststr = new StringBuffer();
				for (String s : waistArr) {
					if(s!=null && s.length()!=0){
						waiststr.append(s+"；");
					}
				}
				row1.createCell(18).setCellValue(waiststr.toString());
			}
			if(waist3.equals("-1")) {
				row1.createCell(19).setCellValue("");
			}else {
				row1.createCell(19).setCellValue(waist3.equals("2") ? "一周内":waist3.equals("3") ? "一月左右":
						waist3.equals("4") ? "一年左右":"三年以上");
			}
			row1.createCell(20).setCellValue(waist4.equals("3") ? "腰痛伴随腿部酸麻胀":waist4.equals("4") ? "腰痛伴随小腹部阵痛":
					waist4.equals("5") ? "腰痛伴随尿痛，血尿等尿路症状":waist4.equals("2") ? "偶尔腰痛，休息一晚后症状减轻":"以上均无");
		}
		//手腕题目
		String wrist2 = object.getString("wrist2");
		String wrist3 = object.getString("wrist3");
		if(wrist1.equals("0")){
			row1.createCell(21).setCellValue("以上均无");
			row1.createCell(22).setCellValue("");
		}else{
			row1.createCell(21).setCellValue(wrist2.equals("3") ? "手腕部疼痛，休息后减轻":wrist2.equals("4") ? "手指有放射痛，端、拿东西有障碍":
					wrist2.equals("5") ? "整个手臂疼痛，有时睡眠中痛醒":wrist2.equals("2") ? "大拇指肌肉萎缩，手指屈伸不利，麻木。":"以上均无");
			if(wrist3.equals("-1")) {
				row1.createCell(22).setCellValue("");
			}else {
				row1.createCell(22).setCellValue(wrist3.equals("2") ? "一周内":wrist3.equals("3") ? "一月左右":
						wrist3.equals("4") ? "一年左右":"三年以上");
			}
		}

		// 必须在单元格设值以后进行 
		// 设置为根据内容自动调整列宽
		for (int k = 0; k < exportDataList.size(); k++) {
			sheet.autoSizeColumn(k);
		}
		// 处理中文不能自动调整列宽的问题
		setColumnWidthByType(sheet, exportDataList.size());

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		/*
		 * response.setHeader("Content-disposition", "attachment;filename=" +
		 * fileName);
		 */
		response.flushBuffer();
		workbook.write(response.getOutputStream());
	}
	



	//根据类型指定excel文件的列宽
    private static void setColumnWidthByType(HSSFSheet sheet,int titleLength) {
        
        for(int i=0;i<titleLength;i++){
            int columnWidth = sheet.getRow(0).getCell(i).getStringCellValue().length();//获取表头的宽度
            int autowidth=(int)SheetUtil.getColumnWidth(sheet,i,false,1,sheet.getLastRowNum());
            if(columnWidth>autowidth){
                sheet.setColumnWidth(i, (int)256.0D*(columnWidth+1));
            }else{
                sheet.autoSizeColumn(i);
            }
        }
    }
}
