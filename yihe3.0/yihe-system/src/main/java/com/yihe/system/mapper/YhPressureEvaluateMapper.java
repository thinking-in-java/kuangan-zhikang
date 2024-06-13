package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.PressureVOJO;
import com.yihe.system.domain.YhPressureEvaluate;

/**
 * 压力管理三大评估问卷数据Mapper接口
 * 
 * @author yihe
 * @date 2020-04-07
 */
public interface YhPressureEvaluateMapper 
{
    /**
     * 查询压力管理三大评估问卷数据
     * 
     * @param id 压力管理三大评估问卷数据ID
     * @return 压力管理三大评估问卷数据
     */
    public YhPressureEvaluate selectYhPressureEvaluateById(Long id);
    
    /**
     * 根据usercode查询用户一共评估了多少次
     */
    int findPressureEvaluateByuserCodeForCount(@Param("usercode")String usercode);

    /**
     * 查询压力管理三大评估问卷数据列表
     * 
     * @param yhPressureEvaluate 压力管理三大评估问卷数据
     * @return 压力管理三大评估问卷数据集合
     */
    public List<YhPressureEvaluate> selectYhPressureEvaluateList(YhPressureEvaluate yhPressureEvaluate);

    /**
     * 查询压力管理
     */
     List<PressureVOJO>   findStressAssessment(@Param("orgid")String orgid,@Param("nameLike")String nameLike,@Param("usercode")String usercode);
     
     List<PressureVOJO>   findStressAssessmentRecord(@Param("usernameLike")String usernameLike,@Param("nameLike")String nameLike,@Param("type")String type,@Param("usercode")String usercode,@Param("strTime")String strTime,@Param("endTime")String endTime);
     List<PressureVOJO>   findStressAssessmentRecordByUser(@Param("type")String type,@Param("usercode")String usercode,@Param("strTime")String strTime,@Param("endTime")String endTime);
     
     String selectYhPressureEvaluateListType(@Param("type")int type,@Param("usercode")String usercode);
     
     String selectYhPressureEvaluateByTimeAndUserCode(@Param("usercode")String usercode);
    /**
     * 新增压力管理三大评估问卷数据
     * 
     * @param yhPressureEvaluate 压力管理三大评估问卷数据
     * @return 结果
     */
    public int insertYhPressureEvaluate(YhPressureEvaluate yhPressureEvaluate);

    /**
     * 修改压力管理三大评估问卷数据
     * 
     * @param yhPressureEvaluate 压力管理三大评估问卷数据
     * @return 结果
     */
    public int updateYhPressureEvaluate(YhPressureEvaluate yhPressureEvaluate);

    /**
     * 删除压力管理三大评估问卷数据
     * 
     * @param id 压力管理三大评估问卷数据ID
     * @return 结果
     */
    public int deleteYhPressureEvaluateById(Long id);

    /**
     * 批量删除压力管理三大评估问卷数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYhPressureEvaluateByIds(String[] ids);
    
    
    public int showEvaluateCount();
    
    public YhPressureEvaluate selectYhPressureEvaluateData(@Param("usercode")String usercode,@Param("id") Integer id);
}
