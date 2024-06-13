package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhHealthStarApp;
import com.yihe.applet.api.domain.YhUserCommodityDataApp;
import com.yihe.applet.api.domain.YhUserOrderApp;
import com.yihe.applet.api.domain.YhUserStepApp;
import com.yihe.applet.api.service.YhHealthStarAppService;
import com.yihe.applet.api.service.YhUserCommodityDataAppService;
import com.yihe.applet.api.service.YhUserOrderAppService;
import com.yihe.applet.api.service.YhUserStepAppService;
import com.yihe.system.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 订单记录控制器
 *
 * @author 阳雄
 * @date 2021年1月14日11:21:56
 */
@Controller
@RequestMapping("/api/YhUserOrderApp")
public class YhUserOrderAppController {
	@Autowired
	YhUserOrderAppService yhUserOrderAppService;
	@Autowired
	YhHealthStarAppService YhHealthStarAppService;
	@Autowired
	YhUserStepAppService yhUserStepAppService;
	@Autowired
	YhUserCommodityDataAppService yhUserCommodityDataAppService;
	
    @RequestMapping(value = "/addOrderHistory")
	@ResponseBody
	public Result addOrderHistory(YhUserOrderApp yhUserOrderApp) {
    	YhHealthStarApp healthStarApp = new YhHealthStarApp();
    	//生成6位数的订单编号
    	char a = (char) (Math.random() * 26 + 'a');// 这里的26是字母的个数
    	int order =  (int)(Math.random()*9+1)*12345;
    	yhUserOrderApp.setOrderNumber(String.valueOf(a) + String.valueOf(order));
    	yhUserOrderApp.setAddDate(new Date());
		int add = yhUserOrderAppService.insertSelective(yhUserOrderApp);
		//往积分记录表插入数据
		healthStarApp.setUserCode(yhUserOrderApp.getUserCode());
		healthStarApp.setUserType(4);
		healthStarApp.setHealthstars(yhUserOrderApp.getConsumeIntegral());
		healthStarApp.setHealthstarsEvents("积分商城兑换礼品");
		healthStarApp.setEventsTime(new Date());
		int starApp = YhHealthStarAppService.insertSelective(healthStarApp);
		//修改总积分
		YhUserStepApp yhUserStepApp = new YhUserStepApp();
		YhUserStepApp userStepApp = yhUserStepAppService.selectAggregate(yhUserOrderApp.getUserCode());
		if(userStepApp != null) {
			int integral = userStepApp.getHealthlevelstars() - yhUserOrderApp.getConsumeIntegral();
			yhUserStepApp.setHealthlevelstars(integral);
			//yhUserStepApp.setHealthstars(integral);
			yhUserStepApp.setUserCode(yhUserOrderApp.getUserCode());
			yhUserStepAppService.updateByPrimaryKey(yhUserStepApp);
		}
		//减少商品库存
		YhUserCommodityDataApp dataApp = new YhUserCommodityDataApp();
		YhUserCommodityDataApp commodityDataApp = yhUserCommodityDataAppService.selectByPrimaryKey(yhUserOrderApp.getMerchandiseId());
		Integer inventory = commodityDataApp.getInventory() - Integer.valueOf(yhUserOrderApp.getStandby());
		dataApp.setInventory(inventory);
		dataApp.setMerchandiseId(commodityDataApp.getMerchandiseId());
		yhUserCommodityDataAppService.updateByPrimaryKeySelective(dataApp);
		return Result.ok(add);
		
	}
	public static void main(String[] args) {
		char a = (char) (Math.random() * 26 + 'a');// 这里的26是字母的个数
		char A = (char) (Math.random() * 26 + 'A');//
		char o = (char) (Math.random() * 8 + '0');// 49是0开始,8是0-9之间的个数
		System.out.println(a + " " + A + " " + o);
		System.out.println((int)(Math.random()*9+1)*12345);
	}
    @RequestMapping(value = "/selectUserAllOrderPage")
	@ResponseBody
	public Result selectUserAllOrderbyParam(Integer page, Integer pageSize, Integer month, Integer type,String userCode) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<YhUserOrderApp> list = yhUserOrderAppService.selectUserAllOrderbyParam((page-1)*pageSize, pageSize, month, type, userCode);
			for (int i = 0; i < list.size(); i++) {
				Date addDate = list.get(i).getAddDate();
				String orderDate = sdf.format(addDate);
				list.get(i).setOrderDate(orderDate);
			}
		return Result.ok(list);
		
	}
}
