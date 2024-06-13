package com.yihe.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yihe.system.mapper.YhPressureEvaluateMapper;
import com.yihe.system.domain.PressureVOJO;
import com.yihe.system.domain.YhPressureEvaluate;
import com.yihe.system.service.IYhPressureEvaluateService;
import com.yihe.system.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.common.core.text.Convert;

/**
 * 压力管理三大评估问卷数据Service业务层处理
 * 
 * @author yihe
 * @date 2020-04-07
 */
@Service
public class YhPressureEvaluateServiceImpl implements IYhPressureEvaluateService 
{
    @Autowired
    private YhPressureEvaluateMapper yhPressureEvaluateMapper;

    /**
     * 查询压力管理三大评估问卷数据
     * 
     * @param id 压力管理三大评估问卷数据ID
     * @return 压力管理三大评估问卷数据
     */
    @Override
    public YhPressureEvaluate selectYhPressureEvaluateById(Long id)
    {
        return yhPressureEvaluateMapper.selectYhPressureEvaluateById(id);
    }

    /**
     * 查询压力管理三大评估问卷数据列表
     * 
     * @param yhPressureEvaluate 压力管理三大评估问卷数据
     * @return 压力管理三大评估问卷数据
     */
    @Override
    public List<YhPressureEvaluate> selectYhPressureEvaluateList(YhPressureEvaluate yhPressureEvaluate)
    {
        return yhPressureEvaluateMapper.selectYhPressureEvaluateList(yhPressureEvaluate);
    }

    /**
     * 新增压力管理三大评估问卷数据
     * 
     * @param yhPressureEvaluate 压力管理三大评估问卷数据
     * @return 结果
     */
    @Override
    public int insertYhPressureEvaluate(YhPressureEvaluate yhPressureEvaluate)
    {
        return yhPressureEvaluateMapper.insertYhPressureEvaluate(yhPressureEvaluate);
    }

    /**
     * 修改压力管理三大评估问卷数据
     * 
     * @param yhPressureEvaluate 压力管理三大评估问卷数据
     * @return 结果
     */
    @Override
    public int updateYhPressureEvaluate(YhPressureEvaluate yhPressureEvaluate)
    {
        return yhPressureEvaluateMapper.updateYhPressureEvaluate(yhPressureEvaluate);
    }

    /**
     * 删除压力管理三大评估问卷数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteYhPressureEvaluateByIds(String ids)
    {
        return yhPressureEvaluateMapper.deleteYhPressureEvaluateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除压力管理三大评估问卷数据信息
     * 
     * @param id 压力管理三大评估问卷数据ID
     * @return 结果
     */
    @Override
    public int deleteYhPressureEvaluateById(Long id)
    {
        return yhPressureEvaluateMapper.deleteYhPressureEvaluateById(id);
    }

	@Override
	public PageInfo<PressureVOJO> findStressAssessment(String orgid, String nameLike,String usercode,int pageNum,int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<PressureVOJO> findStressAssessment = yhPressureEvaluateMapper.findStressAssessment(orgid, nameLike,usercode);
		return new PageInfo<>(findStressAssessment);
	}

	@Override
	public String selectYhPressureEvaluateListType(int type, String usercode) {
		// TODO Auto-generated method stub
		return yhPressureEvaluateMapper.selectYhPressureEvaluateListType(type, usercode);
	}

	@Override
	public PageInfo<PressureVOJO> findStressAssessmentRecord(String usernameLike, String nameLike, String type,
			String usercode, String strTime, String endTime, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<PressureVOJO> list = yhPressureEvaluateMapper.findStressAssessmentRecord(usernameLike, nameLike, type, usercode, strTime, endTime);
		return new PageInfo<>(list);
	}

	@Override
	public String selectYhPressureEvaluateByTimeAndUserCode(String usercode) {
		// TODO Auto-generated method stub
		return yhPressureEvaluateMapper.selectYhPressureEvaluateByTimeAndUserCode(usercode);
	}

	@Override
	public int findPressureEvaluateByuserCodeForCount(String usercode) {
		// TODO Auto-generated method stub
		return yhPressureEvaluateMapper.findPressureEvaluateByuserCodeForCount(usercode);
	}

	@Override
	public PageInfo<PressureVOJO> findStressAssessmentRecordByUser(String type, String usercode, String strTime,
			String endTime, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<PressureVOJO> list = yhPressureEvaluateMapper.findStressAssessmentRecordByUser(type, usercode, strTime, endTime);
		for (PressureVOJO pressureVOJO : list) {
			String evaluateScore = pressureVOJO.getEvaluateScore();// 评估分
			String assessmentType = pressureVOJO.getAssessmentType();// 评估类型
			String sex = pressureVOJO.getSex();
			if(StringUtil.isNotEmpty(evaluateScore)&&StringUtil.isNotEmpty(assessmentType)&&StringUtil.isNotEmpty(sex)){
				String assessmentResults = assessmentResults(assessmentType, evaluateScore, sex);
				pressureVOJO.setAssessmentResults(assessmentResults);
			}
			
		}
		return new PageInfo<PressureVOJO>(list);
	}
	
	String assessmentResults(String type, String scoreString, String sex) {


		if ("1".equals(type)) {
			Integer s_score = 0; 
			// 获取评估分数 
			String score[] = scoreString.split(";");
			for (int i = 0; i < score.length; i++) {
				if(StringUtil.isEmpty(score[i])){
					continue;
				}
				s_score=s_score+Integer.valueOf(score[i]);
			}
			if (score.length == 3) {
				
				if (s_score < 20) {
					return "B型";
				} else if (s_score < 27) {
					return "中间型偏B型";
				} else if (s_score < 30) {
					return "中间型";
				} else if (s_score < 37) {
					return "中间型偏A型";
				} else {
					return "A型";
				}
			}

		}

		if ("2".equals(type)) {
			Integer score = Integer.valueOf(scoreString);
			if (score < 43) {
				return "压力过低";
			} else if (score <= 65) {
				return "压力适中";
			} else {
				return "压力过高";
			}
		}

		if ("3".equals(type)) {

			Integer score = Integer.valueOf(scoreString);
			switch (sex) {
			case "女":
				if (score < 25) {
					return "疑似心理疾患";
				} else if (score <= 45) {
					return "橙色预警";
				} else {
					return "正常";

				}
			default:
				if (score < 10) {
					return "疑似心理疾患";
				} else if (score <= 65) {
					return "橙色预警";
				} else {
					return "正常";
				}
			}

		}

		return "";
	
	}

	@Override
	public YhPressureEvaluate selectYhPressureEvaluateData(String usercode, Integer id) {
		// TODO Auto-generated method stub
		return yhPressureEvaluateMapper.selectYhPressureEvaluateData(usercode, id);
	}

}
