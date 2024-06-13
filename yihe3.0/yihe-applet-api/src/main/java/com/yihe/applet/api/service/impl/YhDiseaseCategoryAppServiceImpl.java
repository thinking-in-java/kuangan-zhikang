package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhDiseaseCategoryApp;
import com.yihe.applet.api.mapper.YhDiseaseCategoryAppMapper;
import com.yihe.applet.api.service.YhDiseaseCategoryAppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 * Created by Administrator on 2020/5/26 14:08
 */
@Service
public class YhDiseaseCategoryAppServiceImpl implements YhDiseaseCategoryAppService {
    @Resource
    private YhDiseaseCategoryAppMapper yhDiseaseCategoryAppMapper;
    @Override
    public List<YhDiseaseCategoryApp> selTopTenData(int diseaseCode) {
        return yhDiseaseCategoryAppMapper.selTopTenData(diseaseCode);
    }

    @Override
    public YhDiseaseCategoryApp selCategoryByCode(int diseaseCode) {
        return yhDiseaseCategoryAppMapper.selCategoryByCode(diseaseCode);
    }

    @Override
    public List<YhDiseaseCategoryApp> newFindDis(int sex) {
        return yhDiseaseCategoryAppMapper.newFindDis(sex);
    }
}
