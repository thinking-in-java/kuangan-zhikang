package com.yihe.web.controller.system;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserPersonageNonimmune;
import com.yihe.system.domain.YhUserPersonagePharmacy;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.service.YhUserPersonageNonimmuneService;
import com.yihe.system.service.YhUserPersonagePharmacyService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhUserPersonageNonimmune")
public class YhUserPersonageNonimmuneController {

	@Autowired
	YhUserPersonageNonimmuneService yhUserPersonageNonimmuneService;
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@ApiOperation(value = "查询非免疫史")
	@RequestMapping(value = "/selectNonimmune", method = RequestMethod.GET)
	@ResponseBody
	public List<YhUserPersonageNonimmune> selectYhUserPersonageNonimmune(String userCode) {
		 List<YhUserPersonageNonimmune> list = yhUserPersonageNonimmuneService.selectByPrimaryKey(userCode);
 		 	return list;

	}
		
		@ApiOperation(value = "添加修改非免疫史")
		@RequestMapping(value = "/compileNonimmune", method = RequestMethod.POST)
		@ResponseBody
		public AjaxResult compileYhUserPersonagePharmacy(@RequestBody String submitData) {

			int row=0;
			String data = submitData.toString();
			JSONObject json = JSON.parseObject(data);// 转换成object
			String ponimmuneData = json.getString("ponimmuneData");
			JSONArray ponimmuneArray = JSONArray.parseArray(ponimmuneData);
			for (int i = 0; i < ponimmuneArray.size(); i++) {
				YhUserPersonageNonimmune yhUserPersonageNonimmune = new YhUserPersonageNonimmune();
				Integer id = JSONObject.parseObject(JSONObject.toJSONString(ponimmuneArray.get(i))).getInteger("id");
				String userCode = JSONObject.parseObject(JSONObject.toJSONString(ponimmuneArray.get(i))).getString("userCode");
				String immunename = JSONObject.parseObject(JSONObject.toJSONString(ponimmuneArray.get(i))).getString("immunename");
				String organization = JSONObject.parseObject(JSONObject.toJSONString(ponimmuneArray.get(i))).getString("organization");
				String timedate = JSONObject.parseObject(JSONObject.toJSONString(ponimmuneArray.get(i))).getString("timedate");
				
				yhUserPersonageNonimmune.setId(id);
				yhUserPersonageNonimmune.setUserCode(userCode);
				yhUserPersonageNonimmune.setImmunename(immunename);
				yhUserPersonageNonimmune.setOrganization(organization);
				yhUserPersonageNonimmune.setTimedate(timedate);
				
				// 添加到对象
				if(yhUserPersonageNonimmune.getId()==null || "".equals(yhUserPersonageNonimmune.getId())) {

					YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
					yhUserEnrolinfo.setEnergyUptime(new Date());
					yhUserEnrolinfo.setUserCode(userCode);
					yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
					row=yhUserPersonageNonimmuneService.insert(yhUserPersonageNonimmune);
				}else {
					YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
					yhUserEnrolinfo.setEnergyUptime(new Date());
					yhUserEnrolinfo.setUserCode(userCode);
					yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
					row = yhUserPersonageNonimmuneService.updateByPrimaryKey(yhUserPersonageNonimmune);
				}		
			}
			return AjaxResult.success(row);
		}
		
		@ApiOperation(value = "删除非免疫史")
		@RequestMapping(value = "/deleteNonimmune", method = RequestMethod.DELETE)
		@ResponseBody
		public int deleteYhUserPersonageNonimmune(Integer id) {
			int delete= yhUserPersonageNonimmuneService.deleteByPrimaryKey(id);
	 		 	return delete;
	
		}
	
}
