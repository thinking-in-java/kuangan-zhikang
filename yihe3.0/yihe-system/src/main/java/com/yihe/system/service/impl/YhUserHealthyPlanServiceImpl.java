package com.yihe.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.common.core.text.Convert;
import com.yihe.system.domain.HealthPlanVOJO;
import com.yihe.system.domain.YhUserHealthyPlan;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhUserHealthyPlanMapper;
import com.yihe.system.service.IYhUserHealthyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户健康计划日志Service业务层处理
 * 
 * @author yihe
 * @date 2020-04-27
 */
@Service
public class YhUserHealthyPlanServiceImpl implements IYhUserHealthyPlanService 
{
    @Autowired
    private YhUserHealthyPlanMapper yhUserHealthyPlanMapper;
    
    @Autowired
    YhUserApiServiceImpl YhUserApiServiceImpl;

    /**
     * 查询用户健康计划日志
     * 
     * @param id 用户健康计划日志ID
     * @return 用户健康计划日志
     */
    @Override
    public YhUserHealthyPlan selectYhUserHealthyPlanById(Long id)
    {
        return yhUserHealthyPlanMapper.selectYhUserHealthyPlanById(id);
    }

    /**
     * 查询用户健康计划日志列表
     * 
     * @param yhUserHealthyPlan 用户健康计划日志
     * @return 用户健康计划日志
     */
    @Override
    public List<YhUserHealthyPlan> selectYhUserHealthyPlanList(YhUserHealthyPlan yhUserHealthyPlan)
    {
        return yhUserHealthyPlanMapper.selectYhUserHealthyPlanList(yhUserHealthyPlan);
    }

    /**
     * 新增用户健康计划日志
     * 
     * @param yhUserHealthyPlan 用户健康计划日志
     * @return 结果
     */
    @Override
    public int insertYhUserHealthyPlan(YhUserHealthyPlan yhUserHealthyPlan)
    {
        return yhUserHealthyPlanMapper.insertYhUserHealthyPlan(yhUserHealthyPlan);
    }

    /**
     * 修改用户健康计划日志
     * 
     * @param yhUserHealthyPlan 用户健康计划日志
     * @return 结果
     */
    @Override
    public int updateYhUserHealthyPlan(YhUserHealthyPlan yhUserHealthyPlan)
    {
        return yhUserHealthyPlanMapper.updateYhUserHealthyPlan(yhUserHealthyPlan);
    }

    /**
     * 删除用户健康计划日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYhUserHealthyPlanByIds(String ids)
    {
        return yhUserHealthyPlanMapper.deleteYhUserHealthyPlanByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户健康计划日志信息
     * 
     * @param id 用户健康计划日志ID
     * @return 结果
     */
    @Override
    public int deleteYhUserHealthyPlanById(Long id)
    {
        return yhUserHealthyPlanMapper.deleteYhUserHealthyPlanById(id);
    }

	@Override
	public HealthPlanVOJO findHealthyPlanUserIsNum(String usercode) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findHealthyPlanUserIsNum(usercode);
	}

	@Override
	public HealthPlanVOJO findHealthyPlanUserIsNum2(String usercode) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findHealthyPlanUserIsNum2(usercode);
	}

	@Override
	public PageInfo<HealthPlanVOJO> findHealthyPlanUserByuserCode(String usercode, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		//List<HealthPlanVOJO> list = yhUserHealthyPlanMapper.findHealthyPlanUserByuserCode(usercode);
		List<HealthPlanVOJO> list = yhUserHealthyPlanMapper.findHealthyPlanUserByuserCode2(usercode);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<HealthPlanVOJO> findUserPlanInfoByUserCode(String usercode, String complete, String planName,
			String strTime, String endTime, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<HealthPlanVOJO> list = yhUserHealthyPlanMapper.findUserPlanInfoByUserCode(usercode, complete, planName, strTime, endTime);
		return new PageInfo<>(list);
	}

	@Override
	public List<HealthPlanVOJO> findUserPlanTongJiTu(String usercode) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findUserPlanTongJiTu(usercode);
	}

	@Override
	public int findPlanByTimeAndUserCodeAndPlanUnique(String planStrTime, String usercode, String planUnique) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findPlanByTimeAndUserCodeAndPlanUnique(planStrTime, usercode, planUnique);
	}

	@Override
	public int findPlanByPlanId(Integer planId) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findPlanByPlanId(planId);
	}

	@Override
	public int findPlanCumulativeInfo(String planUnique,String usercode) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findPlanCumulativeInfo(planUnique,usercode);
	}

	@Override
	public int findPlanContinuityInfo(String planUnique,String usercode) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findPlanContinuityInfo(planUnique,usercode);
	}

	@Override
	public String findPlanDetailByid(Integer id) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findPlanDetailByid(id);
	}

	@Override
	public String findPlanDetailByPlanIdAndPlanDay(Integer planId, Integer planDay) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findPlanDetailByPlanIdAndPlanDay(planId, planDay);
	}

	@Override
	public String findPlanDetailTitleById(String id) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findPlanDetailTitleById(id);
	}

	@Override
	public List<YhUserHealthyPlan> findYhUserHealthyPlanByplanUnique(Integer planUnique) {
		// TODO Auto-generated method stub
		
		return yhUserHealthyPlanMapper.findYhUserHealthyPlanByplanUnique(planUnique);
	}
	/**
	 * 根据出生年月计算年龄
	 * 
	 * @param birthday
	 * @return
	 */
	private int getAgeByBirth(Date birthday) {
		int age = 0;
		try {
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());// 当前时间

			Calendar birth = Calendar.getInstance();
			birth.setTime(birthday);

			if (birth.after(now)) {// 如果传入的时间，在当前时间的后面，返回0岁
				age = 0;
			} else {
				age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
				if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
					age += 1;
				}
			}
			return age;
		} catch (Exception e) {// 兼容性更强,异常后返回数据
			return 0;
		}
	}

	@Override
	public HealthPlanVOJO findYhUserHealthyPlanByplanUniqueByHealthPlanVOJO(Integer planUnique,String usercode) {
		// TODO Auto-generated method stub
		//用户信息
		HealthPlanVOJO vojo=new HealthPlanVOJO();
		vojo.setUsercode(usercode);
		YhUserReginfo selectUserRegInfoByUserCode = YhUserApiServiceImpl.selectUserRegInfoByUserCode(usercode);
		if(null!=selectUserRegInfoByUserCode.getGender()){
			Integer gender = selectUserRegInfoByUserCode.getGender();
			if(gender==1){
				vojo.setSex("男");
			}else{
				vojo.setSex("女");
			}
		}
		
		
		if(null!=selectUserRegInfoByUserCode.getName()){
			vojo.setName(selectUserRegInfoByUserCode.getName());	
		}
		
		if(null!=selectUserRegInfoByUserCode.getBirthDate()){
			int ageByBirth = getAgeByBirth(selectUserRegInfoByUserCode.getBirthDate());
			vojo.setAge(ageByBirth);
		}
		
		 List<YhUserHealthyPlan> list = yhUserHealthyPlanMapper.findYhUserHealthyPlanByplanUnique(planUnique);
		 
		
		 if(null==list||list.size()==0){
			 return vojo;
		 }
		 
		 //当前计划全部计划任务数
		 Integer numAllInfo = yhUserHealthyPlanMapper.findPlanByPlanId(list.get(0).getPlanId());
		 vojo.setCompleteNum(numAllInfo);
		 //执行天数
		 vojo.setPlanDay(list.get(list.size()-1).getPlanDay());
		 
		 //计划状态
		 vojo.setPlanState(String.valueOf(list.get(0).getPlanState()));
		 
		 int planwcNum=0;
		 
		 for (int i = 0; i <list.size(); i++) {
			 YhUserHealthyPlan yhUserHealthyPlan = list.get(i);
			 //当日已完成计划数
			 Integer courseseq = yhUserHealthyPlan.getCourseseq();
			 planwcNum=planwcNum+courseseq;
			 if(yhUserHealthyPlan.getPlanDay()==1 && vojo.getPlanStrTime()==null){
				//第一天加入计划时间
				 String planStartdate = yhUserHealthyPlan.getPlanStartdate();
				 vojo.setPlanStrTime(planStartdate);
			 }
			 //如果已结束或已取消 获取结束时间
			 if(yhUserHealthyPlan.getPlanState()==2||yhUserHealthyPlan.getPlanState()==3){
				 if(i==list.size()-1){
					 String planEnddate = yhUserHealthyPlan.getPlanEnddate();
					 vojo.setPlanendTime(planEnddate);
				 }
			 }
			 
			 
		}
		 
		 List<HealthPlanVOJO> tjList = yhUserHealthyPlanMapper.findUserPlanTongJiTuByUsercodeAndPlanUnique(usercode, planUnique);
		 String[] cTime = new String[tjList.size()];
	 		String[] energyLevel = new String[tjList.size()];
	 		String[] energyKcal = new String[tjList.size()];
	 		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	 		Date date = null;   
	 		int i=0;
	 		for (HealthPlanVOJO yhFoodUserDiary : tjList) {
	 			i++;
	 			 try {
					date = format1.parse(yhFoodUserDiary.getPlanStrTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	 			String dateTime = format1.format(date);
	 			cTime[i-1]=""+dateTime+"";
	 			energyLevel[i-1]=""+yhFoodUserDiary.getCourseseq()+"";
	 			energyKcal[i-1]=""+yhFoodUserDiary.getPlanNum()+"";
			}
		 
	 		
	 		//柱状图统计
	 		vojo.setTimeInfo(cTime);
	 		vojo.setData1(energyKcal);
	 		vojo.setData2(energyLevel);
		 //计划结束时间
		 //执行状态
		 vojo.setPlanState(String.valueOf(list.get(0).getPlanState()));
		 
		 //已完成任务数
		 vojo.setCompletedNum(planwcNum);
		 
		 return vojo;
	}

	@Override
	public List<HealthPlanVOJO> findUserPlanTongJiTuByUsercodeAndPlanUnique(String usercode, Integer planUnique) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findUserPlanTongJiTuByUsercodeAndPlanUnique(usercode, planUnique);
	}

	@Override
	public int findUserHealthyPlanGroupByPlanId(String usercode) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findUserHealthyPlanGroupByPlanId(usercode);
	}

	@Override
	public int findUserHealthyPlanByuserCodeForCount(String usercode) {
		// TODO Auto-generated method stub
		return yhUserHealthyPlanMapper.findUserHealthyPlanByuserCodeForCount(usercode);
	}

	@Override
	public PageInfo<HealthPlanVOJO> findUserHealthyPlanInfoByDoctor(String usercode, String nameLike,
			String usernameLike, String planName, String strTime, String endTime, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<HealthPlanVOJO> list = yhUserHealthyPlanMapper.findUserHealthyPlanInfoByDoctor(usercode, nameLike, usernameLike, planName, strTime, endTime);
		return new PageInfo<>(list);
	}
}
