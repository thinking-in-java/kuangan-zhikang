package com.yihe.system.service.impl;

import java.util.*;

import com.yihe.system.domain.YhHealthStarweb;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.domain.YhUserStepWeb;
import com.yihe.system.service.YhHealthStarwebService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.service.YhUserStepWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhScoreEventCategoryWeb;
import com.yihe.system.mapper.YhScoreEventCategoryWebMapper;
import com.yihe.system.service.YhScoreEventCategoryWebService;
@Service
public class YhScoreEventCategoryWebServiceImpl implements YhScoreEventCategoryWebService{
	private static final Logger LOGGER = LoggerFactory.getLogger(YhScoreEventCategoryWebServiceImpl.class);



	@Autowired
	YhScoreEventCategoryWebMapper yhScoreEventCategoryWebMapper;
	@Autowired
	YhUserReginfoService yhUserReginfoApp;
	@Autowired
	YhHealthStarwebService yhHealthStarService;

	@Autowired
	// 总积分
			YhUserStepWebService yhUserStepService;


	@Override
	public PageInfo<YhScoreEventCategoryWeb> queryUserByCriteriaWithPage(Integer type, Integer putaway, String eventName, int page, int limit, String orgCode) {
		// TODO Auto-generated method stub
		 PageHelper.startPage(page,limit);
		 List<YhScoreEventCategoryWeb> list = yhScoreEventCategoryWebMapper.selectYhScoreEventCategoryPage(type,putaway,eventName,orgCode);
		 if(list.size()==0){
			 List<YhScoreEventCategoryWeb> listAdd = new ArrayList<>();
			 Date date1 = new Date();
			 listAdd.add(new YhScoreEventCategoryWeb("用户签到", 1, "user_signin", 1, 2, 1, date1, null, "http://qiniuyun.yhy.ren/xiaocengxu/签到.png", null,orgCode));
			 listAdd.add(new YhScoreEventCategoryWeb("记录膳食日记", 6, "food_log", 1, 1, 1, date1, null, "http://qiniuyun.yhy.ren/xiaocengxu/记录饮食.png", null,orgCode));
			 listAdd.add(new YhScoreEventCategoryWeb("记录运动日记", 2, "sport_log", 1, 1, 1, date1, null, "http://qiniuyun.yhy.ren/xiaocengxu/运动.png", null,orgCode));
			 listAdd.add(new YhScoreEventCategoryWeb("风险评估", 20, "health_assess", 2, 1, 1, date1, null, "http://xcxqny.yhy.ren/wxImage/personalCenter/score/健康评估.png", null,orgCode));
			 listAdd.add(new YhScoreEventCategoryWeb("习惯评估", 10, "habit_assess", 2, 1, 1, date1, null, "http://xcxqny.yhy.ren/wxImage/personalCenter/score/习惯评估.png", null,orgCode));
			 listAdd.add(new YhScoreEventCategoryWeb("睡眠评估", 5, "sleep_assess", 2, 1, 1, date1, null, "http://xcxqny.yhy.ren/wxImage/personalCenter/score/睡眠评估.png", null,orgCode));
			 listAdd.add(new YhScoreEventCategoryWeb("压力评估", 3, "pressure_assess", 2, 2, 1, date1, null, "http://xcxqny.yhy.ren/wxImage/personalCenter/score/压力评估.png", null,orgCode));
			 listAdd.add(new YhScoreEventCategoryWeb("记录睡眠", 5, "record_sleep", 1, 1, 1, date1, null, "http://xcxqny.yhy.ren/wxImage/personalCenter/score/记录睡眠.png", null,orgCode));
			 listAdd.add(new YhScoreEventCategoryWeb("记录习惯", 6, "record_habits", 1, 1, 1, date1, null, "http://xcxqny.yhy.ren/wxImage/personalCenter/score/记录习惯.png", null,orgCode));
			 listAdd.add(new YhScoreEventCategoryWeb("发表课堂评论与回复", 1, "publish_classroom", 1, 1, 1, date1, null, "http://xcxqny.yhy.ren/wxImage/personalCenter/score/发表课堂评论与回复.png", null,orgCode));
			 listAdd.add(new YhScoreEventCategoryWeb("发表习惯评论与回复", 1, "publish_habit", 1, 1, 1, date1, null, "http://xcxqny.yhy.ren/wxImage/personalCenter/score/发表习惯评论与回复.png", null,orgCode));
			 listAdd.add(new YhScoreEventCategoryWeb("常见症状评估与解决方案评估", 10, "symptom_assess", 2, 1, 1, date1, null, "http://xcxqny.yhy.ren/疼痛科.png", null,orgCode));
			 for (YhScoreEventCategoryWeb item:listAdd){
				 yhScoreEventCategoryWebMapper.insertSelective(item);
			 }
		 }
		list= yhScoreEventCategoryWebMapper.selectYhScoreEventCategoryPage(type,putaway,eventName,orgCode);

		 return new PageInfo<YhScoreEventCategoryWeb>(list);
	}

	@Override
	public int updateByPrimaryKeySelective(YhScoreEventCategoryWeb record) {
		// TODO Auto-generated method stub
		return yhScoreEventCategoryWebMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Integer saveMark(String eventType, String userCode) {
		YhUserReginfo yhUserReginfo = yhUserReginfoApp.selectByPrimaryKey(userCode);

		try {
			// 根据event_type查询积分事件规则表
			YhScoreEventCategoryWeb yhScoreEventCategory = yhScoreEventCategoryWebMapper.selectEventType(eventType,yhUserReginfo.getOrgCode());
			if (yhScoreEventCategory == null) {
				System.out.println("参数错误，event_type不存在");
				return 0;

			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("eventType", eventType);
			map.put("userCode", userCode);
			//根据积分类型查询是否下架和是否开启连续积分奖励
			if(yhScoreEventCategory.getPutaway() == 2) {
				System.out.println("此奖励积分已下架");
				return 0;

			}
			String healthStar = yhHealthStarService.selectRecordtypeAndTime(map);

			String recordtypeAndTime2 = yhHealthStarService.selectRecordtypeAndTime2(map);

			String recordtypeAndTime3 = yhHealthStarService.selectRecordtypeAndTime3(map);

			if(healthStar != null || recordtypeAndTime2 != null || recordtypeAndTime3 != null) {
				System.out.println("当前用户已存在该积分记录");
				return 0;
			}

			if(yhScoreEventCategory.getPutaway() == 1) {
				// 添加一条积分记录
				YhHealthStarweb yhHealthStar = new YhHealthStarweb();
				int insertRecord = 0;
				//奖励积分
				int award = 0;
				//没有开启连续奖励
				if(yhScoreEventCategory.getContinuousStatus() == 2) {
					yhHealthStar.setUserCode(userCode);
					yhHealthStar.setUserType(5);
					yhHealthStar.setHealthstars(yhScoreEventCategory.getScore());
					award = yhScoreEventCategory.getScore();
					yhHealthStar.setHealthstarsEvents(yhScoreEventCategory.getEventName());
					yhHealthStar.setEventsTime(new Date());
					insertRecord = yhHealthStarService.insertSelective(yhHealthStar);
				}
				//开启连续奖励
				if(yhScoreEventCategory.getContinuousStatus() == 1) {
					//查询用户昨天 是否有记录数据
					YhHealthStarweb healthStarApp = yhHealthStarService.selectNewestStar(userCode, yhScoreEventCategory.getEventName());
					if(healthStarApp == null) {
						yhHealthStar.setHealthstars(yhScoreEventCategory.getScore());
						award = yhScoreEventCategory.getScore();

					}
					//用户连续记录的天数上限为7天
					int continuousRecord = yhHealthStarService.selectUserContinuousRecord(userCode, yhScoreEventCategory.getEventName());
					if(continuousRecord<7) {
						if(healthStarApp != null) {
							yhHealthStar.setHealthstars(yhScoreEventCategory.getScore() + continuousRecord);
							award = yhScoreEventCategory.getScore() + continuousRecord;
						}
					}else if(continuousRecord>=7) {
						yhHealthStar.setHealthstars(yhScoreEventCategory.getScore() + 7);
						award = yhScoreEventCategory.getScore() + 7;
					}
					yhHealthStar.setUserCode(userCode);
					yhHealthStar.setUserType(5);
					yhHealthStar.setEventsTime(new Date());
					yhHealthStar.setHealthstarsEvents(yhScoreEventCategory.getEventName());

					insertRecord = yhHealthStarService.insertSelective(yhHealthStar);
				}

				//查询总积分
				YhUserStepWeb YhUserStep = yhUserStepService.selectAggregate(userCode);
				YhUserStepWeb UserStep = new YhUserStepWeb();
				if (insertRecord > 0) {
					//没有总积分数据进行添加 反之修改
					if (YhUserStep == null) {
						UserStep.setUserCode(userCode);
						UserStep.setUserType(5);
						UserStep.setHealthlevelstars(award);
						UserStep.setHealthstars(award);
						int insertSelective = yhUserStepService.insertSelective(UserStep);
						if (insertSelective > 0) {
							return insertSelective;
						}
					}

				}
				Integer healthstars=YhUserStep.getHealthstars()==null? 0:YhUserStep.getHealthstars();
				Integer healthlevelstars=YhUserStep.getHealthlevelstars()==null? 0:YhUserStep.getHealthlevelstars();
				// 修改总积分
				YhUserStep.setHealthstars( healthstars+ award);
				YhUserStep.setHealthlevelstars(healthlevelstars + award);
				int updateAggregateScore = yhUserStepService.updateByPrimaryKey(YhUserStep);
				if (updateAggregateScore > 0) {
					return updateAggregateScore;

				}
			}




		} catch (Exception e) {
			LOGGER.error("调用积分任务完成接口异常，原因是：{} ", e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public Integer edlMark(String eventType, String userCode) {
		YhUserReginfo yhUserReginfo = yhUserReginfoApp.selectByPrimaryKey(userCode);

		try {
			// 根据event_type查询积分事件规则表
			YhScoreEventCategoryWeb yhScoreEventCategory = yhScoreEventCategoryWebMapper.selectEventType(eventType,yhUserReginfo.getOrgCode());
			if (yhScoreEventCategory == null) {
				System.out.println("参数错误，event_type不存在");
				return 0;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("eventType", eventType);
			map.put("userCode", userCode);

			YhHealthStarweb yhHealthStarweb= yhHealthStarService.selectRecordtypeAndTimeMinus(map);

			if(!Objects.isNull(yhHealthStarweb)){
				yhHealthStarService.deleteByPrimaryKey(yhHealthStarweb.getHealthstarId());
				YhUserStepWeb YhUserStep = yhUserStepService.selectAggregate(userCode);
				YhUserStepWeb UserStep = new YhUserStepWeb();
				int award = 0;
				if (Objects.isNull(YhUserStep)) {
					UserStep.setUserCode(userCode);
					UserStep.setUserType(5);
					UserStep.setHealthlevelstars(award);
					UserStep.setHealthstars(award);
					int insertSelective = yhUserStepService.insertSelective(UserStep);
					if (insertSelective > 0) {
						return insertSelective;
					}
				}else{
					Integer healthstars=YhUserStep.getHealthstars()==null? 0:YhUserStep.getHealthstars();
					Integer healthlevelstars=YhUserStep.getHealthlevelstars()==null? 0:YhUserStep.getHealthlevelstars();
					YhUserStep.setHealthstars( healthstars- award);
					YhUserStep.setHealthlevelstars(healthlevelstars - award);
					int updateAggregateScore = yhUserStepService.updateByPrimaryKey(YhUserStep);
					if (updateAggregateScore > 0) {
						return updateAggregateScore;
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("调用积分任务完成接口异常，原因是：{} ", e.getMessage(), e);
		}
		return 0;
	}

}
