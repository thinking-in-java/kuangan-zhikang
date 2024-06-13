/**
 * 
 */
package com.yihe.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.YhAuditCheck;
import com.yihe.system.domain.YhFoodCookBook;
import com.yihe.system.domain.YhHealthLectureComment;
import com.yihe.system.domain.YhSportMotion;
import com.yihe.system.service.YhAuditCheckService;
import com.yihe.system.service.YhFoodCookBookService;
import com.yihe.system.service.YhSportMotionService;
import com.yihe.system.utils.Pages;
import com.yihe.system.utils.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 审核
 * @author dong
 *
 */
@Controller
@RequestMapping("/YhAuditCheckController")
@Api(value = "YhAuditCheckController控制器", description = "YhAuditCheckController管理")
public class YhAuditCheckController {

	@Autowired
	YhAuditCheckService yhAuditCheckService;
	
	
	@Autowired
	 private YhFoodCookBookService yhFoodCookBookService;
	
	@Autowired
	 private YhSportMotionService yhSportMotionService;
	
	 @ApiOperation(value = "审核同意")
	   @RequestMapping(value = "/updateCheck", method = RequestMethod.POST)
		@ResponseBody
	   public Result Infodele(YhAuditCheck check){
		 if(check.getAuditType()==1) {
			 YhFoodCookBook book=new YhFoodCookBook();
			 book.setfCId(check.getAuditId());
			 book.setIsDel(2);
			 yhFoodCookBookService.updateByPrimaryKeySelective(book);
		 }else {
			 YhSportMotion tion=new YhSportMotion();
			 tion.setId(check.getAuditId());
			 tion.setIsDel(2);
			 yhSportMotionService.updateByPrimaryKeySelective(tion);
		 }
		 check.setAuditStatus(3);
	   	int i=yhAuditCheckService.updateByPrimaryKeySelective(check);
	       return Result.ok(i);
	   }
	 
	 
	 @ApiOperation(value = "审核拒绝")
	   @RequestMapping(value = "/updateCheckrefuse", method = RequestMethod.POST)
		@ResponseBody
	   public Result updateCheckrefuse(YhAuditCheck check){
		 check.setAuditStatus(2);
	   	int i=yhAuditCheckService.updateByPrimaryKeySelective(check);
	       return Result.ok(i);
	   }
	   
	
	
	 @ApiOperation(value = "查询所有审核内容")
     @RequestMapping(value = "/selectaudit", method = RequestMethod.POST)
 	@ResponseBody
 	public Map<String,Object> selectaudit(Integer page,Integer limit,String auditStatus,String auditType,String auditTitle,String entryTime){
 		Map<String,Object> map1 = new HashMap<String,Object>();
 		Pages pa = new Pages();
 		pa.setCurPage(page);
 		pa.setMaxResult(limit);
 		map1.put("page", pa.getFirstRows());
 		map1.put("limit", pa.getMaxResult());
 		if(auditStatus!=null && !auditStatus.equals("")) {
 			map1.put("auditStatus", Integer.valueOf(auditStatus));
 		}
 		if(auditType!=null && !auditType.equals("")) {
 			map1.put("auditType",  Integer.valueOf(auditType));
 		}
 		if(entryTime!=null && !entryTime.equals("")) {
 			String[] strArr = entryTime.split(" - ");
 		map1.put("entryTime", strArr[0]);
 		map1.put("entryTime1", strArr[1]);
 		}
 		map1.put("auditTitle", auditTitle);
 		List<YhAuditCheck> list = yhAuditCheckService.selectaudit(map1);
 	
 		Map<String,Object> map = new HashMap<String,Object>();
 		map.put("code", 20000);
 		map.put("msg", "");
 		map.put("count",yhAuditCheckService.selectauditConut(map1));
 		map.put("data", list);
 		return map;
 	}
}
