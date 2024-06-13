package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.HealthPlanVOJO;
import com.yihe.system.domain.YhUserHealthyPlan;

import java.util.List;

/**
 * 用户健康计划日志Service接口
 * 
 * @author yihe
 * @date 2020-04-27
 */
public interface IYhUserHealthyPlanService 
{
	

	public PageInfo<HealthPlanVOJO> findUserHealthyPlanInfoByDoctor( String usercode,
			 String nameLike,String usernameLike,  String planName,  String strTime,
			String endTime,int pageNum,int pageSize);
	
	int findUserHealthyPlanGroupByPlanId( String usercode);
	
	int findUserHealthyPlanByuserCodeForCount( String usercode);
	
	
	public List<HealthPlanVOJO> findUserPlanTongJiTuByUsercodeAndPlanUnique(String usercode,Integer planUnique);

	
	List<YhUserHealthyPlan> findYhUserHealthyPlanByplanUnique(Integer planUnique);
	
	HealthPlanVOJO findYhUserHealthyPlanByplanUniqueByHealthPlanVOJO(Integer planUnique,String usercode);
	String findPlanDetailTitleById( String id);
	String findPlanDetailByid( Integer id);

	String findPlanDetailByPlanIdAndPlanDay( Integer planId,  Integer planDay);
	
	
	int findPlanCumulativeInfo(String planUnique,String usercode);
	int findPlanContinuityInfo(String planUnique,String usercode);
	
	int findPlanByTimeAndUserCodeAndPlanUnique(String planStrTime,String usercode,String planUnique);
	
	int findPlanByPlanId(Integer planId);
	
	
	
	public List<HealthPlanVOJO> findUserPlanTongJiTu(String usercode);
	
	public PageInfo<HealthPlanVOJO> findUserPlanInfoByUserCode(String usercode,String complete,String planName,String strTime,String endTime,int pageNum,int pageSize);
	
	public PageInfo<HealthPlanVOJO> findHealthyPlanUserByuserCode(String usercode,int pageNum,int pageSize);
	
	
	public HealthPlanVOJO findHealthyPlanUserIsNum(String usercode);

	public HealthPlanVOJO findHealthyPlanUserIsNum2(String usercode);
    /**
     * 查询用户健康计划日志
     * 
     * @param id 用户健康计划日志ID
     * @return 用户健康计划日志
     */
    public YhUserHealthyPlan selectYhUserHealthyPlanById(Long id);

    /**
     * 查询用户健康计划日志列表
     * 
     * @param yhUserHealthyPlan 用户健康计划日志
     * @return 用户健康计划日志集合
     */
    public List<YhUserHealthyPlan> selectYhUserHealthyPlanList(YhUserHealthyPlan yhUserHealthyPlan);

    /**
     * 新增用户健康计划日志
     * 
     * @param yhUserHealthyPlan 用户健康计划日志
     * @return 结果
     */
    public int insertYhUserHealthyPlan(YhUserHealthyPlan yhUserHealthyPlan);

    /**
     * 修改用户健康计划日志
     * 
     * @param yhUserHealthyPlan 用户健康计划日志
     * @return 结果
     */
    public int updateYhUserHealthyPlan(YhUserHealthyPlan yhUserHealthyPlan);

    /**
     * 批量删除用户健康计划日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYhUserHealthyPlanByIds(String ids);

    /**
     * 删除用户健康计划日志信息
     * 
     * @param id 用户健康计划日志ID
     * @return 结果
     */
    public int deleteYhUserHealthyPlanById(Long id);
}
