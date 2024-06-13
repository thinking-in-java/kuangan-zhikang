/**
 * 
 */
package com.yihe.web.controller.system;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yihe.system.domain.SportInterveneVojo;
import com.yihe.system.domain.YhSportFrequencyWeb;
import com.yihe.system.domain.YhSportFrequencyWebVojo;
import com.yihe.system.domain.YhSportRecommendRecord;
import com.yihe.system.domain.YhSportSetmeal;
import com.yihe.system.service.YhSportFrequencyWebService;
import com.yihe.system.service.YhSportRecommendRecordService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.ApiOperation;

/**
 * 运动频率
 * 
 * @author dong
 * @date 2020-4-20 11:49:43
 */
@Controller
@RequestMapping("/yhSportFrequencyWebController")
public class YhSportFrequencyWebController {
	
	@Autowired
	private YhSportFrequencyWebService yhSportFrequencyWebService;
	
	@ApiOperation(value = "处理频率")
    @RequestMapping(value = "/batchinsertIndividual", method = RequestMethod.POST)
	@ResponseBody
    public Result  batchinsertIndividual(String usercode,String period) {
		YhSportFrequencyWeb fre=yhSportFrequencyWebService.showUserFrequency(usercode);
		int i=0;
		if(!StringUtils.isEmpty(fre)) {//修改
			fre.setFrFrequency("每周"+period);
			fre.setFrType(3);
			i=yhSportFrequencyWebService.updateByPrimaryKeySelective(fre);
		}else {
			YhSportFrequencyWeb web=new YhSportFrequencyWeb();
			web.setFrFrequency("每周"+period);
			web.setUserCode(usercode);
			web.setFrType(3);
			i=yhSportFrequencyWebService.insertSelective(web);
		}
        return Result.ok(i);
    }
	
	
	@ApiOperation(value = "批量处理频率")
    @RequestMapping(value = "/batchinsertSelectiveplu", method = RequestMethod.POST)
	@ResponseBody
    public Result  batchdeleteByPrimaryKey(@RequestBody YhSportFrequencyWebVojo info) {
		 if (null != info && info.getSportarrayList().size() > 0) {
				for (int i = 0; i < info.getSportarrayList().size(); i++) {
					YhSportFrequencyWeb fre=yhSportFrequencyWebService.showUserFrequency(info.getSportarrayList().get(i));
					if(!StringUtils.isEmpty(fre)) {//修改
						fre.setFrFrequency("每周"+info.getPeriod());
						fre.setFrType(3);
						yhSportFrequencyWebService.updateByPrimaryKeySelective(fre);
					}else {
						YhSportFrequencyWeb web=new YhSportFrequencyWeb();
						web.setFrFrequency("每周"+info.getPeriod());
						web.setUserCode(info.getSportarrayList().get(i));
						web.setFrType(3);
						yhSportFrequencyWebService.insertSelective(web);
					}
			}
		}
		 Map<String, Object> map = new HashMap<String, Object>();
			map.put("add", 1);
			map.put("noadd", 2);
        return Result.ok(map);
    }

}
