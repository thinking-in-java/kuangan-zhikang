package com.yihe.system.service.impl;

import com.yihe.common.core.text.Convert;
import com.yihe.system.domain.YhSmokingReportWeb;
import com.yihe.system.mapper.YhSmokingReportWebMapper;
import com.yihe.system.service.IYhSmokingReportWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 尼古丁评估结果Service业务层处理
 * 
 * @author dengyongbo
 * @date 2022-05-24
 */
@Service
public class YhSmokingReportWebServiceImpl implements IYhSmokingReportWebService
{
    @Autowired
    private YhSmokingReportWebMapper yhSmokingReportMapper;

    @Override
    public List<YhSmokingReportWeb> selectYhSmokingReportListByWeb(YhSmokingReportWeb yhSmokingReport) {
        return yhSmokingReportMapper.selectYhSmokingReportListByWeb(yhSmokingReport);
    }

    /**
     * 查询尼古丁评估结果
     * 
     * @param smokingReportId 尼古丁评估结果ID
     * @return 尼古丁评估结果
     */
    @Override
    public YhSmokingReportWeb selectYhSmokingReportById(Long smokingReportId)
    {
        return yhSmokingReportMapper.selectYhSmokingReportById(smokingReportId);
    }

    /**
     * 查询尼古丁评估结果列表
     * 
     * @param yhSmokingReport 尼古丁评估结果
     * @return 尼古丁评估结果
     */
    @Override
    public List<YhSmokingReportWeb> selectYhSmokingReportList(YhSmokingReportWeb yhSmokingReport)
    {
        return yhSmokingReportMapper.selectYhSmokingReportList(yhSmokingReport);
    }

    /**
     * 新增尼古丁评估结果
     * 
     * @param yhSmokingReport 尼古丁评估结果
     * @return 结果
     */
    @Override
    public int insertYhSmokingReport(YhSmokingReportWeb yhSmokingReport)
    {
        return yhSmokingReportMapper.insertYhSmokingReport(yhSmokingReport);
    }

    /**
     * 修改尼古丁评估结果
     * 
     * @param yhSmokingReport 尼古丁评估结果
     * @return 结果
     */
    @Override
    public int updateYhSmokingReport(YhSmokingReportWeb yhSmokingReport)
    {
        return yhSmokingReportMapper.updateYhSmokingReport(yhSmokingReport);
    }

    /**
     * 删除尼古丁评估结果对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYhSmokingReportByIds(String ids)
    {
        return yhSmokingReportMapper.deleteYhSmokingReportByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除尼古丁评估结果信息
     * 
     * @param smokingReportId 尼古丁评估结果ID
     * @return 结果
     */
    @Override
    public int deleteYhSmokingReportById(Long smokingReportId)
    {
        return yhSmokingReportMapper.deleteYhSmokingReportById(smokingReportId);
    }
}
