package com.yihe.system.service;

import com.yihe.system.domain.YhSmokingReportWeb;

import java.util.List;

/**
 * 尼古丁评估结果Service接口
 * 
 * @author dengyongbo
 * @date 2022-05-24
 */
public interface IYhSmokingReportWebService
{
    /**
     * web端尼古丁成瘾评估列表
     * @param yhSmokingReport
     * @return
     */
    public List<YhSmokingReportWeb> selectYhSmokingReportListByWeb(YhSmokingReportWeb yhSmokingReport);
    /**
     * 查询尼古丁评估结果
     * 
     * @param smokingReportId 尼古丁评估结果ID
     * @return 尼古丁评估结果
     */
    public YhSmokingReportWeb selectYhSmokingReportById(Long smokingReportId);

    /**
     * 查询尼古丁评估结果列表
     * 
     * @param yhSmokingReport 尼古丁评估结果
     * @return 尼古丁评估结果集合
     */
    public List<YhSmokingReportWeb> selectYhSmokingReportList(YhSmokingReportWeb yhSmokingReport);

    /**
     * 新增尼古丁评估结果
     * 
     * @param yhSmokingReport 尼古丁评估结果
     * @return 结果
     */
    public int insertYhSmokingReport(YhSmokingReportWeb yhSmokingReport);

    /**
     * 修改尼古丁评估结果
     * 
     * @param yhSmokingReport 尼古丁评估结果
     * @return 结果
     */
    public int updateYhSmokingReport(YhSmokingReportWeb yhSmokingReport);

    /**
     * 批量删除尼古丁评估结果
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYhSmokingReportByIds(String ids);

    /**
     * 删除尼古丁评估结果信息
     * 
     * @param smokingReportId 尼古丁评估结果ID
     * @return 结果
     */
    public int deleteYhSmokingReportById(Long smokingReportId);
}
