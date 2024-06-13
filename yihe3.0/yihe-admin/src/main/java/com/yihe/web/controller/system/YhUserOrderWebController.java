package com.yihe.web.controller.system;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhHealthStarweb;
import com.yihe.system.domain.YhJzSysSmsWeb;
import com.yihe.system.domain.YhUserCommodityData;
import com.yihe.system.domain.YhUserOrderWeb;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserShippingAddressWeb;
import com.yihe.system.domain.YhUserStepWeb;
import com.yihe.system.mapper.YhUserShippingAddressWebMapper;
import com.yihe.system.service.YhHealthStarwebService;
import com.yihe.system.service.YhJzSysSmsWebService;
import com.yihe.system.service.YhUserCommodityDataService;
import com.yihe.system.service.YhUserOrderWebService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.service.YhUserStepWebService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.ApiOperation;
/**
 * 订单记录控制器
 * @author 阳雄
 * @date 2021年1月19日16:21:43
 *
 */
@Controller
@RequestMapping("/YhUserOrderWeb")
public class YhUserOrderWebController {
	private static final Logger LOGGER = LoggerFactory.getLogger(YhUserOrderWebController.class);
	@Autowired
	YhUserOrderWebService yhUserOrderWebService;
	@Autowired 
	YhUserCommodityDataService yhUserCommodityDataService;
	@Autowired
	YhUserStepWebService yhUserStepWebService;
	@Autowired
	YhUserReginfoService yhUserReginfoService;
	@Autowired
	YhUserShippingAddressWebMapper yhUserShippingAddressWebMapper;
	@Autowired
	YhJzSysSmsWebService yhJzSysSmsWebService;
	@Autowired
	YhHealthStarwebService yhHealthStarAppService;
	@ApiOperation(value = "分页兑换记录")
    @ResponseBody
    @RequestMapping(value="/YhUserOrderWebPage",method=RequestMethod.GET)
    public Map<String,Object> queryYhUserFile(Integer merchandiseType,Integer groupingId,
       		Integer orderStatus,String conversionTime, String merchandiseName,String name,int page, int limit){
		YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		PageInfo<YhUserOrderWeb> data = null;
		String qtstrTime = "";
		String qtendTime = "";
		if (StringUtil.isNotEmpty(conversionTime)) {
			String[] split = conversionTime.split(" - ");
			qtstrTime = split[0]; 
			qtendTime = split[1];
		}
       //查询集合
		if(yhUserReginfo.getUserType() == 1) {
			   data = yhUserOrderWebService.queryUserByCriteriaWithPage(yhUserReginfo.getOrgCode(),merchandiseType, groupingId, orderStatus, 
		    		   qtstrTime, qtendTime, merchandiseName, name, page, limit);	
		}else {
			data = yhUserOrderWebService.queryUserByCriteriaWithPage2(yhUserReginfo.getUserCode(),merchandiseType, groupingId, orderStatus, 
		    		   qtstrTime, qtendTime, merchandiseName, name, page, limit);	
		}
    
       List<YhUserOrderWeb> list  =  data.getList();
       for (int i = 0; i < list.size(); i++) {
    	   list.get(i).setType(yhUserReginfo.getUserType());
	}
       System.out.println("订单"+ list);
       Map<String,Object> result = new HashMap<String, Object>();
       result.put("data",list);
       result .put("count", data.getTotal());
       result.put("msg", "");
       result.put("code", 20000);
          return result;
    }
	@ApiOperation(value = "撤销兑换记录")
    @ResponseBody
    @RequestMapping(value="/revocationUserOrder",method=RequestMethod.GET)
	public Result revocationUserOrder(Integer merchandiseId,Integer orderId,Integer standby,Integer consumeIntegral,String userCode) {
		YhUserCommodityData yhUserCommodityData = new YhUserCommodityData();
		YhUserCommodityData commodityData = yhUserCommodityDataService.selectByPrimaryKey(merchandiseId);
		//返回商品库存
		Integer inventory = commodityData.getInventory();
		yhUserCommodityData.setInventory(inventory + standby);
		yhUserCommodityData.setMerchandiseId(merchandiseId);
		yhUserCommodityDataService.updateByPrimaryKeySelective(yhUserCommodityData);
		//添加积分返回记录
		YhHealthStarweb yhHealthStarweb = new YhHealthStarweb();
		yhHealthStarweb.setUserCode(userCode);
		yhHealthStarweb.setUserType(5);
		yhHealthStarweb.setHealthstars(consumeIntegral);
		yhHealthStarweb.setHealthstarsEvents("返回用户积分");
		yhHealthStarweb.setEventsTime(new Date());
		yhHealthStarAppService.insertSelective(yhHealthStarweb);
	
		
		//查询用户积分 修改总积分
		YhUserStepWeb yhUserStepWeb = new YhUserStepWeb();
		YhUserStepWeb userIntegral = yhUserStepWebService.selectUserIntegral(userCode);
		Integer healthstars = userIntegral.getHealthstars();
		Integer healthlevelstars = userIntegral.getHealthlevelstars();
		yhUserStepWeb.setHealthlevelstars(healthlevelstars + consumeIntegral);
		yhUserStepWeb.setHealthstars(healthstars+consumeIntegral);
		yhUserStepWeb.setUserCode(userCode);
		yhUserStepWebService.updateByPrimaryKeySelective(yhUserStepWeb);
		
		int byPrimaryKey = yhUserOrderWebService.deleteByPrimaryKey(orderId);
		return Result.ok(byPrimaryKey);
		
	}
    @ResponseBody
    @RequestMapping(value="/selectYhUserAddress",method=RequestMethod.GET)
	public Result selectYhUserAddress (Integer orderId) {
    	YhUserShippingAddressWeb addressWeb = yhUserShippingAddressWebMapper.selectByPrimaryKey(orderId);
		return Result.ok(addressWeb);
		
	}
    @ResponseBody
    @RequestMapping(value="/redactYhUserAddress",method=RequestMethod.GET)
	public Result redactYhUserAddress (YhUserShippingAddressWeb yhUserShippingAddressWeb) {
    	int addressWeb = yhUserShippingAddressWebMapper.updateByPrimaryKeySelective(yhUserShippingAddressWeb);
    	
    		YhUserOrderWeb yhUserOrderWeb = new YhUserOrderWeb();
    		yhUserOrderWeb.setSsm(yhUserShippingAddressWeb.getSsm());
    		yhUserOrderWeb.setLogisticsCompany(yhUserShippingAddressWeb.getLogisticsCompany());
    		if(yhUserShippingAddressWeb.getLogisticsOdd()!=null && !"".equals(yhUserShippingAddressWeb.getLogisticsOdd())
    				&& yhUserShippingAddressWeb.getLogisticsCompany()!=null && !"".equals(yhUserShippingAddressWeb.getLogisticsCompany())) {
    			yhUserOrderWeb.setOrderStatus(1);
    		}
    		yhUserOrderWeb.setLogisticsOdd(yhUserShippingAddressWeb.getLogisticsOdd());
    		yhUserOrderWeb.setOrderId(yhUserShippingAddressWeb.getOrderId());
    		yhUserOrderWebService.updateByPrimaryKeySelective(yhUserOrderWeb);
    		if(yhUserShippingAddressWeb.getLogisticsOdd()!=null && !"".equals(yhUserShippingAddressWeb.getLogisticsOdd())
    				&& yhUserShippingAddressWeb.getLogisticsCompany()!=null && !"".equals(yhUserShippingAddressWeb.getLogisticsCompany())
    				&& yhUserShippingAddressWeb.getSsm() == 1) {
    			YhUserReginfo userReginfo = yhUserReginfoService.selectByPrimaryKey(yhUserShippingAddressWeb.getUserCode());
    			//查询物流短信模板
    			YhJzSysSmsWeb smsWeb = yhJzSysSmsWebService.selectYhJzSysSmslogistics();
    			try {
    				LogisticsSmsUtils.sendSms(userReginfo.getMobile(),yhUserShippingAddressWeb.getOrderNumber(), smsWeb.getAccesskey(), smsWeb.getAccesskeysecret(), smsWeb.getSignname(), smsWeb.getTemplateId(), "order","company",yhUserShippingAddressWeb.getLogisticsCompany(),"number",yhUserShippingAddressWeb.getLogisticsOdd());
    			} catch (ClientException e) {
    				LOGGER.error("发送短信接口异常，原因是：{} ", e.getMessage(), e);
    			}
    		}
    		
    	
		return Result.ok(addressWeb);
		
	}
	@RequestMapping(value = "/orderExport", method = RequestMethod.GET)
	@ResponseBody
	public void exportXls(HttpServletResponse response)
			throws Exception {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet =workbook.createSheet("兑换订单");
		// 设置文件名
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String[] headers = new String[] { "序号", "兑换人编码", "兑换人姓名", "兑换商品类型", "兑换商品","商品数量","消耗积分", "收件人地址","收件人姓名"
				,"收件人联系电话","物流公司", "物流单号","兑换时间"};
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
		sheet.setColumnWidth(6, 1750);
		sheet.setColumnWidth(7, 1750);
		sheet.setColumnWidth(8, 1750);
		sheet.setColumnWidth(9, 1750);
		sheet.setColumnWidth(10, 1750);
		sheet.setColumnWidth(11, 1750);
		sheet.setColumnWidth(12, 1750);

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
		List<YhUserOrderWeb> orderExport = yhUserOrderWebService.selectAllYhUserOrderExport();
		
		String time =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date().getTime());
		String fileName = "兑换订单管理-" + time + ".xls";// 设置要导出的文件的名字
		if(orderExport.size()>0) {
			
			// 在表中存放查询到的数据放入对应的列
			for (int i = 0; i < orderExport.size(); i++) {
				// 创建excel中的行
				HSSFRow row1 = sheet.createRow(rowNum);
				// 在表中存放查询到的数据放入对应的列
				row1.createCell(0).setCellValue(i+1);
				row1.createCell(1).setCellValue(orderExport.get(i).getUserCode());
				row1.createCell(2).setCellValue(orderExport.get(i).getName());
				row1.createCell(3).setCellValue(orderExport.get(i).getMerchandiseType()==1?"实物商品":"虚拟商品");
				row1.createCell(4).setCellValue(orderExport.get(i).getMerchandiseName());
				row1.createCell(5).setCellValue(orderExport.get(i).getStandby());
				row1.createCell(6).setCellValue(orderExport.get(i).getConsumeIntegral());
				row1.createCell(7).setCellValue(orderExport.get(i).getSite() + orderExport.get(i).getDetailedSite());
				row1.createCell(8).setCellValue(orderExport.get(i).getConsigneeName());
				row1.createCell(9).setCellValue(orderExport.get(i).getConsigneePhone());
				row1.createCell(10).setCellValue((orderExport.get(i).getLogisticsCompany()==null || "".equals(orderExport.get(i).getLogisticsCompany()))?" ":orderExport.get(i).getLogisticsCompany());
				row1.createCell(11).setCellValue((orderExport.get(i).getLogisticsOdd()==null || "".equals(orderExport.get(i).getLogisticsOdd()))?" ":orderExport.get(i).getLogisticsOdd());
				//获取String类型的时间
				String createdate = sdf.format(orderExport.get(i).getAddDate());
				row1.createCell(12).setCellValue(createdate);
				
				rowNum++;
			}	
		}
	

		// 必须在单元格设值以后进行
		// 设置为根据内容自动调整列宽
		for (int k = 0; k < orderExport.size(); k++) {
			 //设置自动列宽
			
           // sheet.setColumnWidth(k,sheet.getColumnWidth(k)*17/10);
			
		}
		// 处理中文不能自动调整列宽的问题
		setSizeColumn(sheet, orderExport.size());

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
}
