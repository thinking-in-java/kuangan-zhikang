package com.yihe.web.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.StringUtils;
import com.yihe.common.core.domain.AjaxResult;
import com.yihe.system.domain.YhUserEnrolinfo;
import com.yihe.system.domain.YhUserPersonageHospitalized;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.service.YhUserPersonageHospitalizedService;
import com.yihe.system.utils.StringUtil;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhUserPersonageHospitalized")
public class YhUserPersonageHospitalizedController {
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@Autowired
	YhUserPersonageHospitalizedService yhUserPersonageHospitalizedService;

	@ApiOperation(value = "查询住院情况")
	@RequestMapping(value = "/selectHospitalized", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<YhUserPersonageHospitalized>> selectYhUserPersonageHospitalized(String userCode) {
		List<YhUserPersonageHospitalized> list = yhUserPersonageHospitalizedService.selectByPrimaryKey(userCode);
		Map<String, List<YhUserPersonageHospitalized>> map = list.stream()
				.collect(Collectors.groupingBy(YhUserPersonageHospitalized::getHospitalizedtype));

		System.out.println(map);
		return map;

	}


	@ApiOperation(value = "添加修改住院情况")
	@RequestMapping(value = "/compileHospitalized", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult compileYhUserPersonageHospitalized(@RequestBody String submitData) {
		// 解析json数据
        int row=0;
		String data = submitData.toString();
		JSONObject json = JSON.parseObject(data);// 转换成object
		String personData = json.getString("personData");
		String familyData = json.getString("familyData");
		
		JSONArray personArray = JSONArray.parseArray(personData);
		List<YhUserPersonageHospitalized> listHospitalized = new ArrayList<YhUserPersonageHospitalized>();
		for (int i = 0; i < personArray.size(); i++) {
			YhUserPersonageHospitalized yhUserPersonageHospitalized = new YhUserPersonageHospitalized();
			Integer id = JSONObject.parseObject(JSONObject.toJSONString(personArray.get(i))).getInteger("id");
			String userCode = JSONObject.parseObject(JSONObject.toJSONString(personArray.get(i))).getString("userCode");
			String timedeta = JSONObject.parseObject(JSONObject.toJSONString(personArray.get(i))).getString("timedeta");
			String timedeta2 = timedeta.substring(0, timedeta.length() - 12);
			String dischargetime = timedeta.substring(12, timedeta.length());
			System.out.println(dischargetime + "------------");
			String cause = JSONObject.parseObject(JSONObject.toJSONString(personArray.get(i))).getString("cause");
			String yiyuanname = JSONObject.parseObject(JSONObject.toJSONString(personArray.get(i))).getString("yiyuanname");
			String disease = JSONObject.parseObject(JSONObject.toJSONString(personArray.get(i))).getString("disease");
			String hospitalizedtype = JSONObject.parseObject(JSONObject.toJSONString(personArray.get(i))).getString("hospitalizedtype");
			
			yhUserPersonageHospitalized.setId(id);
			yhUserPersonageHospitalized.setUserCode(userCode);
			yhUserPersonageHospitalized.setTimedeta(timedeta2);
			yhUserPersonageHospitalized.setCause(cause);
			yhUserPersonageHospitalized.setYiyuanname(yiyuanname);
			yhUserPersonageHospitalized.setDischargetime(dischargetime);
			yhUserPersonageHospitalized.setDisease(disease);
			yhUserPersonageHospitalized.setHospitalizedtype("1");
			
			// 添加到对象
			if(yhUserPersonageHospitalized.getId()==null || "".equals(yhUserPersonageHospitalized.getId())) {
				YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
				yhUserEnrolinfo.setEnergyUptime(new Date());
				yhUserEnrolinfo.setUserCode(userCode);
				yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
				row=yhUserPersonageHospitalizedService.insert(yhUserPersonageHospitalized);
			}else {
				YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
				yhUserEnrolinfo.setEnergyUptime(new Date());
				yhUserEnrolinfo.setUserCode(userCode);
				yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
				row = yhUserPersonageHospitalizedService.updateByPrimaryKey(yhUserPersonageHospitalized);
			}		
		}
		
		
		
		
			JSONArray familyArray = JSONArray.parseArray(familyData);
			for (int i = 0; i < familyArray.size(); i++) {
				YhUserPersonageHospitalized yhUserPersonageHospitalized2 = new YhUserPersonageHospitalized();
				Integer id = JSONObject.parseObject(JSONObject.toJSONString(familyArray.get(i))).getInteger("id");
				String userCode = JSONObject.parseObject(JSONObject.toJSONString(familyArray.get(i))).getString("userCode");
				String timedeta = JSONObject.parseObject(JSONObject.toJSONString(familyArray.get(i))).getString("timedeta");
				String timedeta2 = timedeta.substring(0, timedeta.length() - 12);
				String dischargetime = timedeta.substring(12, timedeta.length());
				System.out.println(dischargetime + "------------");
				String cause = JSONObject.parseObject(JSONObject.toJSONString(familyArray.get(i))).getString("cause");
				String yiyuanname = JSONObject.parseObject(JSONObject.toJSONString(familyArray.get(i))).getString("yiyuanname");
				String disease = JSONObject.parseObject(JSONObject.toJSONString(familyArray.get(i))).getString("disease");
				String hospitalizedtype = JSONObject.parseObject(JSONObject.toJSONString(familyArray.get(i))).getString("hospitalizedtype");
				
				yhUserPersonageHospitalized2.setId(id);
				yhUserPersonageHospitalized2.setUserCode(userCode);
				yhUserPersonageHospitalized2.setTimedeta(timedeta2);
				yhUserPersonageHospitalized2.setCause(cause);
				yhUserPersonageHospitalized2.setYiyuanname(yiyuanname);
				yhUserPersonageHospitalized2.setDischargetime(dischargetime);
				yhUserPersonageHospitalized2.setDisease(disease);
				yhUserPersonageHospitalized2.setHospitalizedtype("2");
				listHospitalized.add(yhUserPersonageHospitalized2);
				
				if(yhUserPersonageHospitalized2.getId()==null || "".equals(yhUserPersonageHospitalized2.getId())) {
					row=yhUserPersonageHospitalizedService.insert(yhUserPersonageHospitalized2);
				}else {
					
					row = yhUserPersonageHospitalizedService.updateByPrimaryKey(yhUserPersonageHospitalized2);
				}
			}
	
		
		return AjaxResult.success(row);
	}

	@ApiOperation(value = "删除住院情况")
	@RequestMapping(value = "/deleteHospitalized", method = RequestMethod.DELETE)
	@ResponseBody
	public  int  deleteYhUserPersonageHospitalized(Integer id) {
		int deleteData = yhUserPersonageHospitalizedService.deleteByPrimaryKey(id);
		 	return deleteData;
	
	}
	

}













