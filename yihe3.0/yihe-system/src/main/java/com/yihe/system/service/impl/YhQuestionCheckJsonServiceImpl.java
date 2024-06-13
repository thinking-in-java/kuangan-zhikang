package com.yihe.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yihe.system.mapper.YhQuestionCheckJsonMapper;
import com.yihe.system.domain.YhQuestionCheckJson;
import com.yihe.system.service.IYhQuestionCheckJsonService;
import com.yihe.common.core.text.Convert;

/**
 * 用户问卷答案Service业务层处理
 * 
 * @author yihe
 * @date 2020-04-07
 */
@Service
public class YhQuestionCheckJsonServiceImpl implements IYhQuestionCheckJsonService 
{
    @Autowired
    private YhQuestionCheckJsonMapper yhQuestionCheckJsonMapper;

    /**
     * 查询用户问卷答案
     * 
     * @param id 用户问卷答案ID
     * @return 用户问卷答案
     */
    @Override
    public YhQuestionCheckJson selectYhQuestionCheckJsonById(Long id)
    {
        return yhQuestionCheckJsonMapper.selectYhQuestionCheckJsonById(id);
    }

    /**
     * 查询用户问卷答案列表
     * 
     * @param yhQuestionCheckJson 用户问卷答案
     * @return 用户问卷答案
     */
    @Override
    public List<YhQuestionCheckJson> selectYhQuestionCheckJsonList(YhQuestionCheckJson yhQuestionCheckJson)
    {
        return yhQuestionCheckJsonMapper.selectYhQuestionCheckJsonList(yhQuestionCheckJson);
    }

    /**
     * 新增用户问卷答案
     * 
     * @param yhQuestionCheckJson 用户问卷答案
     * @return 结果
     */
    @Override
    public int insertYhQuestionCheckJson(YhQuestionCheckJson yhQuestionCheckJson)
    {
        return yhQuestionCheckJsonMapper.insertYhQuestionCheckJson(yhQuestionCheckJson);
    }

    /**
     * 修改用户问卷答案
     * 
     * @param yhQuestionCheckJson 用户问卷答案
     * @return 结果
     */
    @Override
    public int updateYhQuestionCheckJson(YhQuestionCheckJson yhQuestionCheckJson)
    {
        return yhQuestionCheckJsonMapper.updateYhQuestionCheckJson(yhQuestionCheckJson);
    }

    /**
     * 删除用户问卷答案对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYhQuestionCheckJsonByIds(String ids)
    {
        return yhQuestionCheckJsonMapper.deleteYhQuestionCheckJsonByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户问卷答案信息
     * 
     * @param id 用户问卷答案ID
     * @return 结果
     */
    @Override
    public int deleteYhQuestionCheckJsonById(Long id)
    {
        return yhQuestionCheckJsonMapper.deleteYhQuestionCheckJsonById(id);
    }

	@Override
	public YhQuestionCheckJson findThQuestionCheckJson(String usercode) {
		// TODO Auto-generated method stub
		return yhQuestionCheckJsonMapper.findThQuestionCheckJson(usercode);
	}
}
