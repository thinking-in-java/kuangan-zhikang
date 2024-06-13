package com.yihe.web.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.IntegralShoppingGrouping;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.IntegralShoppingGroupingService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.ApiOperation;
/**
 * 商品分组控制器
 * @author 阳雄
 * date 2020年12月30日16:04:35
 *
 */
@Controller
@RequestMapping("/IntegralShoppingGrouping")
public class IntegralShoppingGroupingController { 
	 private static final Logger LOGGER = LoggerFactory.getLogger(IntegralShoppingGroupingController.class);
		
		@Autowired
		IntegralShoppingGroupingService  integralShoppingGroupingService;
		
		@ApiOperation(value = "分页查询商品分组")
	    @ResponseBody
	    @RequestMapping(value="/shoppingGroupingPage",method=RequestMethod.GET)
	    public Map<String,Object> shoppingGroupingPage(String name,int page,int limit){
	       //查询集合
			YhUserReginfo userReginfo = (YhUserReginfo)SecurityUtils.getSubject().getPrincipal();
	       PageInfo<IntegralShoppingGrouping> data = integralShoppingGroupingService.queryGroupingByCriteriaWithPage(name,userReginfo.getOrgCode(), page, limit);
	       Map<String,Object> result = new HashMap<String, Object>();
	       result.put("data",data.getList());
	       result .put("count", data.getTotal());
	       result.put("msg", "");
	       result.put("code", 20000);
	          return result;
	    }
		@ResponseBody
	    @RequestMapping(value="/addGroupingName",method=RequestMethod.GET)
		public Map<String,Object> addGroupingName(String name,String groupingId){
			YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
			 Map<String,Object> result = new HashMap<String, Object>();
			 IntegralShoppingGrouping integralShoppingGrouping = new IntegralShoppingGrouping();
			IntegralShoppingGrouping groupingName = integralShoppingGroupingService.selectGroupingName(name);
			if(groupingName!=null) {
				 result.put("msg", "分组名称已存在");
			     result.put("code", 500);
			}else if("".equals(groupingId)){
				integralShoppingGrouping.setName(name);
				integralShoppingGrouping.setAddTime(new Date());
				integralShoppingGrouping.setOrgCode(yhUserReginfo.getOrgCode());
				integralShoppingGroupingService.insertSelective(integralShoppingGrouping);
				result.put("msg", "操作成功!");
			     result.put("code", 200);
			}else if(!"".equals(groupingId)) {
				integralShoppingGrouping.setName(name);
				integralShoppingGrouping.setAddTime(new Date());
				integralShoppingGrouping.setGroupingId(Integer.valueOf(groupingId));
				integralShoppingGroupingService.updateByPrimaryKeySelective(integralShoppingGrouping);
				result.put("msg", "操作成功!");
			     result.put("code", 200);
			}
			return result; 
			
		}
		
		@ResponseBody
	    @RequestMapping(value="/deleteGroupingName",method=RequestMethod.GET)
		public Result deleteGroupingName(Integer groupingId){
			int delete = integralShoppingGroupingService.deleteByPrimaryKey(groupingId);
			return Result.ok(delete);
			
		}
		
		@ResponseBody
	    @RequestMapping(value="/updateGroupingName",method=RequestMethod.GET)
		public Result updateGroupingName(IntegralShoppingGrouping IntegralShoppingGrouping){
			int update = integralShoppingGroupingService.updateByPrimaryKeySelective(IntegralShoppingGrouping);
			return Result.ok(update);
			
		}
		
		@ResponseBody
	    @RequestMapping(value="/selectAllGrouping",method=RequestMethod.GET)
		public Result selectAllGrouping(){
			YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
			 List<IntegralShoppingGrouping> list = integralShoppingGroupingService.selectAllGrouping(yhUserReginfo.getOrgCode());
			return Result.ok(list);
			
		}
		@ResponseBody
	    @RequestMapping(value="/selectGrouping",method=RequestMethod.GET)
		public Result selectGrouping(String name){
			 List<IntegralShoppingGrouping> list = integralShoppingGroupingService.selectGrouping(name);
			return Result.ok(list);
			
		}
}
