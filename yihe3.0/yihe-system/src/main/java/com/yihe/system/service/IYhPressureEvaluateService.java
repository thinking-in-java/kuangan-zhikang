package com.yihe.system.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.PressureVOJO;
import com.yihe.system.domain.YhPressureEvaluate;

/**
 * 压力管理三大评估问卷数据Service接口
 * 
 * @author yihe
 * @date 2020-04-07
 */
public interface IYhPressureEvaluateService 
{
	
	PageInfo<PressureVOJO>   findStressAssessmentRecordByUser(String type,String usercode,String strTime,String endTime,int pageNum,int pageSize);
    
	
	int findPressureEvaluateByuserCodeForCount(String usercode);
	
	String selectYhPressureEvaluateByTimeAndUserCode(String usercode);
	
	PageInfo<PressureVOJO>   findStressAssessment(String orgid,String nameLike,String usercode,int pageNum,int pageSize);
    /**
     * 查询压力管理三大评估问卷数据
     * 
     * @param id 压力管理三大评估问卷数据ID
     * @return 压力管理三大评估问卷数据
     */
    public YhPressureEvaluate selectYhPressureEvaluateById(Long id);

    /**
     * 查询压力管理三大评估问卷数据列表
     * 
     * @param yhPressureEvaluate 压力管理三大评估问卷数据
     * @return 压力管理三大评估问卷数据集合
     */
    public List<YhPressureEvaluate> selectYhPressureEvaluateList(YhPressureEvaluate yhPressureEvaluate);

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
     * 批量删除压力管理三大评估问卷数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYhPressureEvaluateByIds(String ids);


    YhPressureEvaluate selectByUserCodeAndType(String userCode);

    /**
     * 删除压力管理三大评估问卷数据信息
     * 
     * @param id 压力管理三大评估问卷数据ID
     * @return 结果
     */
    public int deleteYhPressureEvaluateById(Long id);
    
    String selectYhPressureEvaluateListType(int type,String usercode);
    
    PageInfo<PressureVOJO>   findStressAssessmentRecord(String usernameLike,String nameLike,String type,String usercode,String strTime,String endTime,int pageNum,int pageSize);
    
    public YhPressureEvaluate selectYhPressureEvaluateData(String usercode,Integer id);
}
