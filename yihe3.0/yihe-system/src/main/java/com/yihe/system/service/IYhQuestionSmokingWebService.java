package com.yihe.system.service;

import com.yihe.system.domain.YhQuestionSmokingWeb;

import java.util.List;

/**
 * 吸烟问卷Service接口
 * 
 * @author dengyongbo
 * @date 2022-05-24
 */
public interface IYhQuestionSmokingWebService 
{
    /**
     * 查询吸烟问卷
     * 
     * @param questionId 吸烟问卷ID
     * @return 吸烟问卷
     */
    public YhQuestionSmokingWeb selectYhQuestionSmokingWebById(Long questionId);

    /**
     * 查询吸烟问卷列表
     * 
     * @param yhQuestionSmokingWeb 吸烟问卷
     * @return 吸烟问卷集合
     */
    public List<YhQuestionSmokingWeb> selectYhQuestionSmokingWebList(YhQuestionSmokingWeb yhQuestionSmokingWeb);

    /**
     * 新增吸烟问卷
     * 
     * @param yhQuestionSmokingWeb 吸烟问卷
     * @return 结果
     */
    public int insertYhQuestionSmokingWeb(YhQuestionSmokingWeb yhQuestionSmokingWeb);

    /**
     * 修改吸烟问卷
     * 
     * @param yhQuestionSmokingWeb 吸烟问卷
     * @return 结果
     */
    public int updateYhQuestionSmokingWeb(YhQuestionSmokingWeb yhQuestionSmokingWeb);

    /**
     * 批量删除吸烟问卷
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYhQuestionSmokingWebByIds(String ids);

    /**
     * 删除吸烟问卷信息
     * 
     * @param questionId 吸烟问卷ID
     * @return 结果
     */
    public int deleteYhQuestionSmokingWebById(Long questionId);
}
