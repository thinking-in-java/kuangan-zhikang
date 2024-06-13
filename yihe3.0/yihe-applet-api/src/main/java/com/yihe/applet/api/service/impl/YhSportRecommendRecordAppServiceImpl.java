package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhSportRecommendRecordApp;
import com.yihe.applet.api.mapper.YhSportRecommendRecordAppMapper;
import com.yihe.applet.api.service.YhSportRecommendRecordAppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 * Created by Administrator on 2020/5/28 14:10
 */
@Service
public class YhSportRecommendRecordAppServiceImpl implements YhSportRecommendRecordAppService {
    @Resource
    private YhSportRecommendRecordAppMapper yhSportRecommendRecordAppMapper;
    @Override
    public int insertYhSportRecommendRecord(YhSportRecommendRecordApp yhSportRecommendRecordApp) {
        return yhSportRecommendRecordAppMapper.insertYhSportRecommendRecord(yhSportRecommendRecordApp);
    }

    @Override
    public int uptYhSportRecommendRecord(YhSportRecommendRecordApp yhSportRecommendRecordApp) {
        return yhSportRecommendRecordAppMapper.uptYhSportRecommendRecord(yhSportRecommendRecordApp);
    }

    @Override
    public int selYhSportRecordCount(YhSportRecommendRecordApp yhSportRecommendRecordApp) {
        return yhSportRecommendRecordAppMapper.selYhSportRecordCount(yhSportRecommendRecordApp);
    }
}
