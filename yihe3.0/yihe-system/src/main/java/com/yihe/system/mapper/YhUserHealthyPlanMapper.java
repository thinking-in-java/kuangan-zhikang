package com.yihe.system.mapper;

import com.yihe.system.domain.HealthPlanVOJO;
import com.yihe.system.domain.YhUserHealthyPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户健康计划日志Mapper接口
 * 
 * @author yihe
 * @date 2020-04-27
 */
public interface YhUserHealthyPlanMapper {
	
	
	int findUserHealthyPlanGroupByPlanId(@Param("usercode") String usercode);
	
	int findUserHealthyPlanByuserCodeForCount(@Param("usercode") String usercode);
	
	String findPlanDetailTitleById(@Param("id") String id);
	
	String findPlanDetailByid(@Param("id") Integer id);

	String findPlanDetailByPlanIdAndPlanDay(@Param("planId") Integer planId, @Param("planDay") Integer planDay);

	int findPlanCumulativeInfo(@Param("planUnique") String planUnique,@Param("usercode") String usercode);

	int findPlanContinuityInfo(@Param("planUnique") String planUnique,@Param("usercode") String usercode);

	int findPlanByTimeAndUserCodeAndPlanUnique(@Param("planStrTime") String planStrTime,
			@Param("usercode") String usercode, @Param("planUnique") String planUnique);

	Integer findPlanByPlanId(@Param("planId") Integer planId);

	public HealthPlanVOJO findHealthyPlanUserIsNum(@Param("usercode") String usercode);

	public HealthPlanVOJO findHealthyPlanUserIsNum2(@Param("usercode") String usercode);

	public List<HealthPlanVOJO> findUserPlanTongJiTu(@Param("usercode") String usercode);
	
	public List<HealthPlanVOJO> findUserPlanTongJiTuByUsercodeAndPlanUnique(@Param("usercode") String usercode,@Param("planUnique") Integer planUnique);

	public List<HealthPlanVOJO> findUserPlanInfoByUserCode(@Param("usercode") String usercode,
			@Param("complete") String complete, @Param("planName") String planName, @Param("strTime") String strTime,
			@Param("endTime") String endTime);
	public List<HealthPlanVOJO> findUserHealthyPlanInfoByDoctor(@Param("usercode") String usercode,
			@Param("nameLike") String nameLike,@Param("usernameLike") String usernameLike, @Param("planName") String planName, @Param("strtime") String strTime,
			@Param("endtime") String endTime);

	public List<HealthPlanVOJO> findHealthyPlanUserByuserCode(@Param("usercode") String usercode);
	public List<HealthPlanVOJO> findHealthyPlanUserByuserCode2(@Param("usercode") String usercode);

	/**
	 * 查询用户健康计划日志
	 * 
	 * @param id
	 *            用户健康计划日志ID
	 * @return 用户健康计划日志
	 */
	public YhUserHealthyPlan selectYhUserHealthyPlanById(Long id);

	/**
	 * 查询用户健康计划日志列表
	 * 
	 * @param yhUserHealthyPlan
	 *            用户健康计划日志
	 * @return 用户健康计划日志集合
	 */
	public List<YhUserHealthyPlan> selectYhUserHealthyPlanList(YhUserHealthyPlan yhUserHealthyPlan);

	
	List<YhUserHealthyPlan> findYhUserHealthyPlanByplanUnique(@Param("planUnique")Integer planUnique);
	/**
	 * 新增用户健康计划日志
	 * 
	 * @param yhUserHealthyPlan
	 *            用户健康计划日志
	 * @return 结果
	 */
	public int insertYhUserHealthyPlan(YhUserHealthyPlan yhUserHealthyPlan);

	/**
	 * 修改用户健康计划日志
	 * 
	 * @param yhUserHealthyPlan
	 *            用户健康计划日志
	 * @return 结果
	 */
	public int updateYhUserHealthyPlan(YhUserHealthyPlan yhUserHealthyPlan);

	/**
	 * 删除用户健康计划日志
	 * 
	 * @param id
	 *            用户健康计划日志ID
	 * @return 结果
	 */
	public int deleteYhUserHealthyPlanById(Long id);

	/**
	 * 批量删除用户健康计划日志
	 * 
	 * @param ids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int deleteYhUserHealthyPlanByIds(String[] ids);
	
	public int showPlanCount();
}
