package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhHealthStarApp;
import com.yihe.applet.api.domain.YhScoreEventCategoryApp;
import com.yihe.applet.api.domain.YhUserStepApp;
import com.yihe.applet.api.mapper.YhHealthStarAppMapper;
import com.yihe.applet.api.mapper.YhScoreEventCategoryAppMapper;
import com.yihe.applet.api.mapper.YhUserStepAppMapper;
import com.yihe.applet.api.service.YhHealthStarAppService;
import com.yihe.system.mapper.YhUserRoleManageMapper;
import com.yihe.system.service.YhOrgTreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class YhHealthStarAppServiceImpl implements YhHealthStarAppService{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(YhHealthStarAppServiceImpl.class);
	
	@Autowired
	YhHealthStarAppMapper yhHealthStarAppMapper;
	
    @Autowired
    YhScoreEventCategoryAppMapper yhScoreEventCategoryMapper;
    @Autowired
    YhUserStepAppMapper yhUserStepMapper;
    
	@Override
	public String selectRecordtypeAndTime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthStarAppMapper.selectRecordtypeAndTime(map);
	}
	@Override
	public int insertSelective(YhHealthStarApp record) {
		// TODO Auto-generated method stub
		return yhHealthStarAppMapper.insertSelective(record);
	}

	@Resource
	YhOrgTreeService yhOrgTreeService;
	@Resource
	YhUserRoleManageMapper yhUserRoleManageMapper;
	@Override
	public int integralCompletioStatus(String eventType,String userCode) {
		try {
			String orgCode=yhUserRoleManageMapper.selectorgcode(userCode);
			List<String> orgs=new ArrayList<String>();
			List<String> orgCodes=yhOrgTreeService.selectparentOrg(orgs,orgCode);
			// 根据event_type查询积分事件规则表
			YhScoreEventCategoryApp yhScoreEventCategory =null;

			// 根据event_type查询积分事件规则表
			for (String item:orgCodes){
				if(yhScoreEventCategory!=null){
					yhScoreEventCategory=yhScoreEventCategoryMapper.selectEventType(eventType,item);
				}

			}
			if (yhScoreEventCategory == null) {
				return 0;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("eventType", eventType);
			map.put("userCode", userCode);
			String healthStar = yhHealthStarAppMapper.selectRecordtypeAndTime(map);
			if(healthStar != null) {
				return 0;
			}
			// 添加一条积分记录
			YhHealthStarApp yhHealthStar = new YhHealthStarApp();
			yhHealthStar.setUserCode(userCode);
			yhHealthStar.setUserType(5);
			yhHealthStar.setHealthstars(yhScoreEventCategory.getScore());
			yhHealthStar.setHealthstarsEvents(yhScoreEventCategory.getEventName());
			yhHealthStar.setEventsTime(new Date());
			// 为空添加积分记录
			int insertRecord = yhHealthStarAppMapper.insertSelective(yhHealthStar);
			YhUserStepApp YhUserStep = yhUserStepMapper.selectAggregate(userCode);
			YhUserStepApp UserStep = new YhUserStepApp();
			if (insertRecord > 0) {
				if (YhUserStep == null) {
					UserStep.setUserCode(userCode);
					UserStep.setUserType(5);
					UserStep.setHealthlevelstars(yhScoreEventCategory.getScore());
					UserStep.setHealthstars(yhScoreEventCategory.getScore());
					int insertSelective = yhUserStepMapper.insertSelective(UserStep);
					if (insertSelective > 0) {
						return insertSelective;
					}
				}

			}
			// 修改总积分
			YhUserStep.setHealthstars(YhUserStep.getHealthstars() + yhScoreEventCategory.getScore());
			YhUserStep.setHealthlevelstars(YhUserStep.getHealthlevelstars() + yhScoreEventCategory.getScore());
			return yhUserStepMapper.updateByPrimaryKey(YhUserStep);

		} catch (Exception e) {
			LOGGER.error("调用积分任务完成接口异常，原因是：{} ", e.getMessage(), e);
		}
		return 0;
	}
	@Override
	public List<YhHealthStarApp> selectYhHealthStarAppParam(Integer page, Integer pageSize, String userCode,Integer type) {
		// TODO Auto-generated method stub
		return yhHealthStarAppMapper.selectYhHealthStarAppParam(page, pageSize, userCode,type);
	}
	@Override
	public YhHealthStarApp selectNewestStar(String userCode, String healthstarsEvents) {
		// TODO Auto-generated method stub
		return yhHealthStarAppMapper.selectNewestStar(userCode, healthstarsEvents);
	}
	@Override
	public int selectUserContinuousRecord(String userCode, String healthstarsEvents) {
		// TODO Auto-generated method stub
		return yhHealthStarAppMapper.selectUserContinuousRecord(userCode, healthstarsEvents);
	}
	@Override
	public String selectRecordtypeAndTime2(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhHealthStarAppMapper.selectRecordtypeAndTime2(map);
	}
	@Override
	public List<YhHealthStarApp> selectUserYhHealthStar(String userCode,Integer type) {
		// TODO Auto-generated method stub
		return yhHealthStarAppMapper.selectUserYhHealthStar(userCode,type);
	}

	@Override
	public Integer selectUserYhHealthStarSum(String userCode, Integer type) {
		return yhHealthStarAppMapper.selectUserYhHealthStarSum(userCode, type);
	}

}
