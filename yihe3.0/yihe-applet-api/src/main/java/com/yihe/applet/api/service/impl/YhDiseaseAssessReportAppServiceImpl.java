package com.yihe.applet.api.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yihe.applet.api.mapper.YhDiseaseAssessReportAppMapper;
import com.yihe.applet.api.service.YhDiseaseAssessReportAppService;
import com.yihe.system.domain.YhDiseaseAssessReport;

/**
 * Description:
 * Created by Administrator on 2020/5/26 15:13
 */
@Service
public class YhDiseaseAssessReportAppServiceImpl implements YhDiseaseAssessReportAppService {
    @Resource
    private YhDiseaseAssessReportAppMapper yhDiseaseAssessReportAppMapper;
    @Override
    public int insertYhDiseaseAssessReport(YhDiseaseAssessReport yhDiseaseAssessReportApp) {
        return yhDiseaseAssessReportAppMapper.insertYhDiseaseAssessReport(yhDiseaseAssessReportApp);
    }

    @Override
    public int selMyAssessCount(String userCode) {
        return yhDiseaseAssessReportAppMapper.selMyAssessCount(userCode);
    }

	@Override
	public YhDiseaseAssessReport selectAssessReport(Integer diseaseid, String requestNum) {
		return yhDiseaseAssessReportAppMapper.selectAssessReport(diseaseid, requestNum);
	}
}
