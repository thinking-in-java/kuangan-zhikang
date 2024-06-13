package com.yihe.web.controller.system;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.InterveneVOJO;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserEnrolinfoVOJO;
import com.yihe.system.service.impl.YHDocGroupServiceImpl;
import com.yihe.system.service.impl.YhUserEnrolinfoServiceImpl;
import com.yihe.system.service.impl.YhUserReginfoServiceImpl;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;

@Controller
@RequestMapping("/YhUserEnrolinfo")
public class YhUserEnrolinfoController {
	@Autowired
	YhUserEnrolinfoServiceImpl YhUserEnrolinfoServiceImpl;
	@Autowired
	YhUserReginfoServiceImpl YhUserReginfoServiceImpl;
	
	@Autowired
	YHDocGroupServiceImpl YHDocGroupServiceImpl;
	
	

	@RequestMapping(value = "/modifyGYInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result modifyGYInfo(String usercode,String gid) {
		System.out.println(usercode);
		if(null==usercode||null==gid){
			return Result.error();
		}
		//查询干预情况
		InterveneVOJO findinterveneByUserCode = YhUserReginfoServiceImpl.findinterveneByUserCode(usercode);
		//1,195,
		String grouping = findinterveneByUserCode.getGrouping();
		String groupingnew="";
		if(StringUtil.isNotEmpty(grouping)){
			String[] split = grouping.split(",");
			for (String string : split) {
				if(StringUtil.isNotEmpty(string)){
					if(string.equals(gid)){
						
					}else{
						groupingnew=groupingnew+string+",";
					}
				}
			}
			if (groupingnew.endsWith(",")) {
				groupingnew = groupingnew.substring(0, groupingnew.length() - 1);
			}
			int modifyinterveneByUserCodeAndGroupInfo = YhUserReginfoServiceImpl.modifyinterveneByUserCodeAndGroupInfo(groupingnew, usercode);
			
		}
		
		return Result.ok();
	}
	
	
	
	@RequestMapping(value = "/modifyGY", method = RequestMethod.POST)
	@ResponseBody
	public Result modifyGY(String usercode,Boolean yesOrNo) {
		System.out.println(usercode);
		YhUserEnrolinfo record=new YhUserEnrolinfo();
		record.setUserCode(usercode);
		if(yesOrNo){
			//干预
			record.setEvaluateflag(1);
			YhUserEnrolinfoServiceImpl.updateByExampleByusercodeForEval(record);
		}else{
			 //取消干预
			record.setEvaluateflag(0);
			//查询干预情况
			InterveneVOJO findinterveneByUserCode = YhUserReginfoServiceImpl.findinterveneByUserCode(usercode);
			if(null!=findinterveneByUserCode){
				String grouping = findinterveneByUserCode.getGrouping();
				if(StringUtil.isNotEmpty(grouping)){
					String[] split = grouping.split(",");
					 for (String string : split) {
						if(StringUtil.isNotEmpty(string)){
							YHDocGroupServiceImpl.modifyInfoUserCountJianYi(string);
						}
					}
				}
			}
			
			
			//取消干预 删除干预分组
			YhUserReginfoServiceImpl.modifyinterveneByUserCode(usercode);
			//分组人数减一
			YhUserEnrolinfoServiceImpl.updateByExampleByusercodeForEval(record);
			
		}
		
		
		return Result.ok();
	}
	
	@RequestMapping(value = "/modifyGYList", method = RequestMethod.POST)
	@ResponseBody
	public Result modifyGYList(@RequestBody YhUserEnrolinfoVOJO info) {
		System.out.println(info);
		if(null==info){
			return Result.error();
		}
		List<String> usercode = info.getUsercode();
		Boolean yesOrNo = info.getYesOrNo();
		for (String string : usercode) {
			YhUserEnrolinfo record=new YhUserEnrolinfo();
			record.setUserCode(string);
			if(yesOrNo){
				record.setEvaluateflag(1);
			}else{
				record.setEvaluateflag(0);
			}
			YhUserEnrolinfoServiceImpl.updateByExampleByusercodeForEval(record);
		}
		
		
		return Result.ok();
	}
}
