package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhDiseaseAssessTotalReportApp;
import com.yihe.applet.api.domain.YhDiseaseAssessTotalReportNewApp;
import com.yihe.applet.api.mapper.YhDiseaseAssessTotalReportAppMapper;
import com.yihe.applet.api.service.YhDiseaseAssessTotalReportAppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 * Created by Administrator on 2020/5/27 9:49
 */
@Service
public class YhDiseaseAssessTotalReportAppServiceImpl implements YhDiseaseAssessTotalReportAppService {
    @Resource
    private YhDiseaseAssessTotalReportAppMapper yhDiseaseAssessTotalReportAppMapper;

    @Override
    public int insertIntoTotalReport(YhDiseaseAssessTotalReportApp yhDiseaseAssessTotalReportApp) {
        return yhDiseaseAssessTotalReportAppMapper.insertIntoTotalReport(yhDiseaseAssessTotalReportApp);
    }

	@Override
	public YhDiseaseAssessTotalReportApp selectTotalReportByuserCode(String userCode) {
		return yhDiseaseAssessTotalReportAppMapper.selectTotalReportByuserCode(userCode);
	}

	@Override
	public YhDiseaseAssessTotalReportApp selectTotalReportByuserCodeByNewest(String userCode) {
		// TODO Auto-generated method stub
		return yhDiseaseAssessTotalReportAppMapper.selectTotalReportByuserCodeByNewest(userCode);
	}

	@Override
	public YhDiseaseAssessTotalReportApp queryOaUserAssessInfo(String userCode) {
		return yhDiseaseAssessTotalReportAppMapper.queryOaUserAssessInfo(userCode);
	}

	@Override
	public int insertIntoTotalReportNew(YhDiseaseAssessTotalReportNewApp record) {
		return yhDiseaseAssessTotalReportAppMapper.insertIntoTotalReportNew(record);
	}
}
