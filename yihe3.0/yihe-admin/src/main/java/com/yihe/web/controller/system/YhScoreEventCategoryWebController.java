package com.yihe.web.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 积分设置
 * @author 阳雄
 * @date 2021年1月11日08:35:52
 *
 */
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhScoreEventCategoryWeb;
import com.yihe.system.service.YhScoreEventCategoryWebService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.ApiOperation;
@Controller
@RequestMapping("/YhScoreEventCategoryWeb")
public class YhScoreEventCategoryWebController {
	@Autowired
	YhScoreEventCategoryWebService yhScoreEventCategoryWebService;
	
	@ApiOperation(value = "分页查询积分设置")
    @ResponseBody
    @RequestMapping(value="/YhScoreEventCategoryPage",method=RequestMethod.GET)
    public Map<String,Object> queryYhScoreEventCategoryPage(Integer type,Integer putaway,String eventName,int page,int limit){
       //查询集合
       PageInfo<YhScoreEventCategoryWeb> data = yhScoreEventCategoryWebService.queryUserByCriteriaWithPage(type,putaway,eventName,page, limit);
       Map<String,Object> result = new HashMap<String, Object>();
       result.put("data",data.getList());
       result .put("count", data.getTotal());
       result.put("msg", "");
       result.put("code", 20000);
       return result;
    }
	@ApiOperation(value = "设置积分是否开启连续奖励")
    @ResponseBody
    @RequestMapping(value="/updateContinuousStatus",method=RequestMethod.GET)
	public Result updateContinuousStatus(YhScoreEventCategoryWeb yhScoreEventCategoryWeb) {
		yhScoreEventCategoryWeb.setUpdateDate(new Date());
		int selective = yhScoreEventCategoryWebService.updateByPrimaryKeySelective(yhScoreEventCategoryWeb);
		return Result.ok(selective);
		
	}
}
