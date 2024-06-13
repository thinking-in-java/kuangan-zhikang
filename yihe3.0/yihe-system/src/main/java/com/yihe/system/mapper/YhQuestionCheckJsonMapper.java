package com.yihe.system.mapper;

import java.util.List;
import com.yihe.system.domain.YhQuestionCheckJson;

/**
 * 用户问卷答案Mapper接口
 * 
 * @author yihe
 * @date 2020-04-07
 */
public interface YhQuestionCheckJsonMapper 
{
	
	YhQuestionCheckJson findThQuestionCheckJson(String usercode);
    /**
     * 查询用户问卷答案
     * 
     * @param id 用户问卷答案ID
     * @return 用户问卷答案
     */
    public YhQuestionCheckJson selectYhQuestionCheckJsonById(Long id);

    /**
     * 查询用户问卷答案列表
     * 
     * @param yhQuestionCheckJson 用户问卷答案
     * @return 用户问卷答案集合
     */
    public List<YhQuestionCheckJson> selectYhQuestionCheckJsonList(YhQuestionCheckJson yhQuestionCheckJson);

    /**
     * 新增用户问卷答案
     * 
     * @param yhQuestionCheckJson 用户问卷答案
     * @return 结果
     */
    public int insertYhQuestionCheckJson(YhQuestionCheckJson yhQuestionCheckJson);

    /**
     * 修改用户问卷答案
     * 
     * @param yhQuestionCheckJson 用户问卷答案
     * @return 结果
     */
    public int updateYhQuestionCheckJson(YhQuestionCheckJson yhQuestionCheckJson);

    /**
     * 删除用户问卷答案
     * 
     * @param id 用户问卷答案ID
     * @return 结果
     */
    public int deleteYhQuestionCheckJsonById(Long id);

    /**
     * 批量删除用户问卷答案
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYhQuestionCheckJsonByIds(String[] ids);
}
