package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhDiseaseRiskResultApp;
import com.yihe.applet.api.domain.YhDiseaseRiskResultNewApp;
import com.yihe.applet.api.mapper.YhDiseaseRiskResultAppMapper;
import com.yihe.applet.api.service.YhDiseaseRiskResultAppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 * Created by Administrator on 2020/5/27 14:12
 */
@Service
public class YhDiseaseRiskResultAppServiceImpl implements YhDiseaseRiskResultAppService {
    @Resource
    private YhDiseaseRiskResultAppMapper yhDiseaseRiskResultAppMapper;

    @Override
    public int insertYhDiseaseRiskResult(YhDiseaseRiskResultApp yhDiseaseRiskResultApp) {
        return yhDiseaseRiskResultAppMapper.insertYhDiseaseRiskResult(yhDiseaseRiskResultApp);
    }

	@Override
	public YhDiseaseRiskResultApp selectRiskResulByRequestNum(String RequestNum) {
		// TODO Auto-generated method stub
		return yhDiseaseRiskResultAppMapper.selectRiskResulByRequestNum(RequestNum);
	}

    @Override
    public int insertYhDiseaseRiskResultNew(YhDiseaseRiskResultNewApp record) {
        int count = yhDiseaseRiskResultAppMapper.selectCount(record);
        if(count == 0){
            return yhDiseaseRiskResultAppMapper.insertYhDiseaseRiskResultNew(record);
        }
        return 0;
    }
}
