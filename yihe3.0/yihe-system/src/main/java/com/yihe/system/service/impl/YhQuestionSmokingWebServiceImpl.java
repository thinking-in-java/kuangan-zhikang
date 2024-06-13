package com.yihe.system.service.impl;

import com.yihe.common.core.text.Convert;
import com.yihe.system.domain.YhQuestionSmokingWeb;
import com.yihe.system.mapper.YhQuestionSmokingWebMapper;
import com.yihe.system.service.IYhQuestionSmokingWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 吸烟问卷Service业务层处理
 * 
 * @author dengyongbo
 * @date 2022-05-24
 */
@Service
public class YhQuestionSmokingWebServiceImpl implements IYhQuestionSmokingWebService
{
    @Autowired
    private YhQuestionSmokingWebMapper yhQuestionSmokingWebMapper;

    /**
     * 查询吸烟问卷
     * 
     * @param questionId 吸烟问卷ID
     * @return 吸烟问卷
     */
    @Override
    public YhQuestionSmokingWeb selectYhQuestionSmokingWebById(Long questionId)
    {
        return yhQuestionSmokingWebMapper.selectYhQuestionSmokingWebById(questionId);
    }

    /**
     * 查询吸烟问卷列表
     * 
     * @param yhQuestionSmokingWeb 吸烟问卷
     * @return 吸烟问卷
     */
    @Override
    public List<YhQuestionSmokingWeb> selectYhQuestionSmokingWebList(YhQuestionSmokingWeb yhQuestionSmokingWeb)
    {
        return yhQuestionSmokingWebMapper.selectYhQuestionSmokingWebList(yhQuestionSmokingWeb);
    }

    /**
     * 新增吸烟问卷
     * 
     * @param yhQuestionSmokingWeb 吸烟问卷
     * @return 结果
     */
    @Override
    public int insertYhQuestionSmokingWeb(YhQuestionSmokingWeb yhQuestionSmokingWeb)
    {
        return yhQuestionSmokingWebMapper.insertYhQuestionSmokingWeb(yhQuestionSmokingWeb);
    }

    /**
     * 修改吸烟问卷
     * 
     * @param yhQuestionSmokingWeb 吸烟问卷
     * @return 结果
     */
    @Override
    public int updateYhQuestionSmokingWeb(YhQuestionSmokingWeb yhQuestionSmokingWeb)
    {
        return yhQuestionSmokingWebMapper.updateYhQuestionSmokingWeb(yhQuestionSmokingWeb);
    }

    /**
     * 删除吸烟问卷对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYhQuestionSmokingWebByIds(String ids)
    {
        return yhQuestionSmokingWebMapper.deleteYhQuestionSmokingWebByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除吸烟问卷信息
     * 
     * @param questionId 吸烟问卷ID
     * @return 结果
     */
    @Override
    public int deleteYhQuestionSmokingWebById(Long questionId)
    {
        return yhQuestionSmokingWebMapper.deleteYhQuestionSmokingWebById(questionId);
    }
}
