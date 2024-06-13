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
import com.yihe.system.domain.YhUserPersonageHospitalized;
import com.yihe.system.domain.YhUserPersonagePharmacy;
import com.yihe.system.service.YhUserEnrolinfoService;
import com.yihe.system.service.YhUserPersonagePharmacyService;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/YhUserPersonagePharmacy")
public class YhUserPersonagePharmacyController {
	@Autowired
	YhUserEnrolinfoService yhUserEnrolinfoService;
	@Autowired
	YhUserPersonagePharmacyService yhUserPersonagePharmacyService;

	@ApiOperation(value = "查询用药情况")
	@RequestMapping(value = "/selectPharmacy", method = RequestMethod.GET)
	@ResponseBody
	public List<YhUserPersonagePharmacy> selectYhUserPersonagePharmacy(String userCode) {
		 List<YhUserPersonagePharmacy> list = yhUserPersonagePharmacyService.selectByPrimaryKey(userCode);
 		 	return list;

	}
	
	@ApiOperation(value = "添加修用药情况")
	@RequestMapping(value = "/compilePharmacy", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult compileYhUserPersonagePharmacy(@RequestBody String submitData) {
		
		int row=0;
		String data = submitData.toString();
		JSONObject json = JSON.parseObject(data);// 转换成object
		String pharmacyData = json.getString("pharmacyData");
		JSONArray pharmacyArray = JSONArray.parseArray(pharmacyData);
		for (int i = 0; i < pharmacyArray.size(); i++) {
			YhUserPersonagePharmacy yhUserPersonagePharmacy = new YhUserPersonagePharmacy();
			Integer id = JSONObject.parseObject(JSONObject.toJSONString(pharmacyArray.get(i))).getInteger("id");
			String userCode = JSONObject.parseObject(JSONObject.toJSONString(pharmacyArray.get(i))).getString("userCode");
			String drugname = JSONObject.parseObject(JSONObject.toJSONString(pharmacyArray.get(i))).getString("drugname");
			String usagefa = JSONObject.parseObject(JSONObject.toJSONString(pharmacyArray.get(i))).getString("usagefa");
			String dosage = JSONObject.parseObject(JSONObject.toJSONString(pharmacyArray.get(i))).getString("dosage");
			String timedata = JSONObject.parseObject(JSONObject.toJSONString(pharmacyArray.get(i))).getString("timedata");
			String comply = JSONObject.parseObject(JSONObject.toJSONString(pharmacyArray.get(i))).getString("comply");
		
			
			yhUserPersonagePharmacy.setId(id);
			yhUserPersonagePharmacy.setUserCode(userCode);
			yhUserPersonagePharmacy.setDrugname(drugname);
			yhUserPersonagePharmacy.setUsagefa(usagefa);
			yhUserPersonagePharmacy.setDosage(dosage);
			yhUserPersonagePharmacy.setTimedata(timedata);
			yhUserPersonagePharmacy.setComply(comply);
			
			// 添加到对象
			if(yhUserPersonagePharmacy.getId()==null || "".equals(yhUserPersonagePharmacy.getId())) {
				YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
				yhUserEnrolinfo.setEnergyUptime(new Date());
				yhUserEnrolinfo.setUserCode(userCode);
				yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
				row=yhUserPersonagePharmacyService.insert(yhUserPersonagePharmacy);
			}else {
				YhUserEnrolinfo yhUserEnrolinfo = new YhUserEnrolinfo();
				yhUserEnrolinfo.setEnergyUptime(new Date());
				yhUserEnrolinfo.setUserCode(userCode);
				yhUserEnrolinfoService.updateByPrimaryKeySelective(yhUserEnrolinfo);
				row = yhUserPersonagePharmacyService.updateByPrimaryKey(yhUserPersonagePharmacy);
			}		
		}
		return AjaxResult.success(row);
	}
	
	@ApiOperation(value = "删除用药情况")
	@RequestMapping(value = "/deletePharmacy", method = RequestMethod.DELETE)
	@ResponseBody
	public int deleteYhUserPersonagePharmacy(Integer id) {
		int delete= yhUserPersonagePharmacyService.deleteByPrimaryKey(id);
 		 	return delete;

	}
	
}
