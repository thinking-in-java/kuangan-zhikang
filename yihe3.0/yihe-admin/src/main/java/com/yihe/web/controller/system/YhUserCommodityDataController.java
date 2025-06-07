package com.yihe.web.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhOrgTree;
import com.yihe.system.domain.YhUserCommodityData;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhOrgTreeService;
import com.yihe.system.service.YhUserCommodityDataService;
import com.yihe.system.utils.Result;

import io.swagger.annotations.ApiOperation;

/**
 * 商品管理控制器
 * 
 * @author 阳雄
 * @date 2021年1月6日09:54:41
 *
 */
@Controller
@RequestMapping("/YhUserCommodityData")
public class YhUserCommodityDataController {
	@Autowired
	YhUserCommodityDataService YhUserCommodityDataService;
	@Autowired
	YhOrgTreeService YhOrgTreeService;

	@RequestMapping(value = "/compileCommodity")
	@ResponseBody
	public Result compileCommodity(@RequestBody YhUserCommodityData yhUserCommodityData) {
		int row = 0;
		YhUserReginfo yhUserReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		if (yhUserCommodityData.getPutaway() == null) {
			yhUserCommodityData.setPutaway(2);
		}
		if (yhUserCommodityData.getMerchandiseId() != null && !"".equals(yhUserCommodityData.getMerchandiseId())) {
			yhUserCommodityData.setUpdateDate(new Date());
			row = YhUserCommodityDataService.updateByPrimaryKeySelective(yhUserCommodityData);
		} else {
			yhUserCommodityData.setOrgCode(yhUserReginfo.getOrgCode());
			yhUserCommodityData.setAddDate(new Date());
			yhUserCommodityData.setStandby("1");
			row = YhUserCommodityDataService.insertSelective(yhUserCommodityData);
		}

		return Result.ok(row);

	}

	@ApiOperation(value = "分页查询商品信息")
	@ResponseBody
	@RequestMapping(value = "/pageCommodity", method = RequestMethod.GET)
	public Map<String, Object> queryYhUserCommodityData(Integer merchandiseType, Integer groupingId, Integer putaway,
			Integer conversion, String merchandiseName, int page, int limit) {
		YhUserReginfo userReginfo = (YhUserReginfo) SecurityUtils.getSubject().getPrincipal();
		ArrayList<YhUserCommodityData> arrayList = new  ArrayList<YhUserCommodityData>();
		List<String> orgs=new ArrayList<String>();
		List<String> orgList = YhOrgTreeService.selectparentOrg(orgs,userReginfo.getOrgCode());
		System.out.println(orgList);
		for (int i = 0; i < orgList.size(); i++) {
			// 查询集合
			PageInfo<YhUserCommodityData> data = YhUserCommodityDataService.queryUserByCriteriaWithPage(merchandiseType, groupingId, putaway, conversion,
					merchandiseName,orgList.get(i), page, limit);
			for (int j = 0; j < data.getList().size(); j++) {
				arrayList.add(data.getList().get(j));
			}
		}


		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", arrayList);
		result.put("count", arrayList.size());
		result.put("msg", "");
		result.put("code", 20000);
		return result;
	}

	@RequestMapping(value = "/soldoutCommodity")
	@ResponseBody
	public Result soldoutCommodity(Integer[] ids, Integer flag, Integer groupingId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<YhUserCommodityData> list = new ArrayList<YhUserCommodityData>();
		int row = 0;
		for (int i = 0; i < ids.length; i++) {
			YhUserCommodityData yhUserCommodityData = new YhUserCommodityData();
			yhUserCommodityData.setAddDate(new Date());
			yhUserCommodityData.setUpdateDate(new Date());
			yhUserCommodityData.setMerchandiseId(ids[i]);
			list.add(yhUserCommodityData);

		}
		map.put("list", list);
		map.put("flag", flag);
		map.put("groupingId", groupingId);
		row = YhUserCommodityDataService.batchUpdateYhUserCommodityData(map);
		return Result.ok(row);

	}

	@RequestMapping(value = "/batchDeleteCommodity")
	@ResponseBody
	public Result batchDeleteYhUserCommodityData(Integer[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		ArrayList<YhUserCommodityData> list = new ArrayList<YhUserCommodityData>();
		int row = 0;
		for (int i = 0; i < ids.length; i++) {
			YhUserCommodityData yhUserCommodityData = new YhUserCommodityData();
			yhUserCommodityData.setMerchandiseId(ids[i]);
			list.add(yhUserCommodityData);

		}
		map.put("list", list);
		row = YhUserCommodityDataService.batchDeleteYhUserCommodityData(map);
		return Result.ok(row);

	}

	@RequestMapping(value = "/selectCommodity")
	@ResponseBody
	public Result selectCommodity(Integer merchandiseId) {
		YhUserCommodityData commodityData = YhUserCommodityDataService.selectByPrimaryKey(merchandiseId);
		return Result.ok(commodityData);

	}

	@RequestMapping(value = "/selectAll")
	@ResponseBody
	public Result selectAll() {
		List<YhUserCommodityData> list = YhUserCommodityDataService.selectAll();
		return Result.ok(list);

	}

}
