package com.yihe.applet.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yihe.applet.api.mapper.YhDiseaseAssessResolveAppMapper;
import com.yihe.applet.api.service.YhDiseaseAssessResolveAppService;
import com.yihe.system.domain.YHDiseaseAssessResolve;

/**
 * Description:
 * Created by Administrator on 2020/5/26 18:00
 */
@Service
public class YhDiseaseAssessResolveAppServiceImpl implements YhDiseaseAssessResolveAppService {
    @Resource
    private YhDiseaseAssessResolveAppMapper yhDiseaseAssessResolveAppMapper;
    
    @Override
    public int insertBatch(List<YHDiseaseAssessResolve> record) {
        return yhDiseaseAssessResolveAppMapper.insertBatch(record);
    }
    
	@Override
	public List<YHDiseaseAssessResolve> selectResolves(Integer diseaseid, String requestNum) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessResolveAppMapper.selectResolves(diseaseid, requestNum);
	}
	
	@Override
	public String selectImagetext(String improve) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessResolveAppMapper.selectImagetext(improve);
	}
}
