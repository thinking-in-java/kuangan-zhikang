package com.yihe.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yihe.common.utils.StringUtils;
import com.yihe.system.domain.YhDicIntroduction;
import com.yihe.system.mapper.YhOrgTreeMapper;
import com.yihe.system.mapper.YhUserPageSettingMapper;
import com.yihe.system.service.YhUserPageSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YhUserPageSettingImpl implements YhUserPageSettingService {
    @Autowired
    YhUserPageSettingMapper yhuserpagesettingmapper;
    @Autowired
    private YhOrgTreeMapper yhOrgTreeMapper;

    @Override
    public List<YhDicIntroduction> selectallduction() {
        // TODO Auto-generated method stub
        List<YhDicIntroduction> introductions = yhuserpagesettingmapper.selectallduction();
        String orgNameInfo = yhOrgTreeMapper.getOrgNameInfo(null);
        if (StringUtils.isNotEmpty(orgNameInfo)) {
            JSONObject jsonObject = JSONObject.parseObject(orgNameInfo);
            introductions.forEach(item -> {
                if (item.getCode() == 19 || item.getCode() == 20 ||
                        item.getCode() == 21 || item.getCode() == 22) {
                    item.setExplain(jsonObject.getString(String.valueOf(item.getCode())));
                }
            });
        }
        return introductions;
    }

    @Override
    public int updateyhdicintroduction(YhDicIntroduction yhdicintroduction) {
        // TODO Auto-generated method stub
        if (yhdicintroduction.getCode() == 19 || yhdicintroduction.getCode() == 20 ||
                yhdicintroduction.getCode() == 21 || yhdicintroduction.getCode() == 22) {
            String orgNameInfo = yhOrgTreeMapper.getOrgNameInfo(null);
            JSONObject jsonObject;
            if (StringUtils.isNotEmpty(orgNameInfo)) {
                jsonObject = JSONObject.parseObject(orgNameInfo);
            } else {
                jsonObject = JSONObject.parseObject("{\"19\":\"\",\"20\":\"\",\"21\":\"\",\"22\":\"\",\"name\":\"\",\"name1\":\"\"}");
            }
            jsonObject.put(String.valueOf(yhdicintroduction.getCode()), yhdicintroduction.getExplain());
            return yhOrgTreeMapper.updateOrg(jsonObject.toString());
        } else {
            return yhuserpagesettingmapper.updateyhdicintroduction(yhdicintroduction);
        }
    }

    @Override
    public String getimg(Integer id) {
        String orgNameInfo = yhOrgTreeMapper.getOrgNameInfo(null);
        if (StringUtils.isNotEmpty(orgNameInfo)) {
            return JSONObject.parseObject(orgNameInfo).getString(id.toString());
        }
        return "";
        //return yhuserpagesettingmapper.getimg(id);
    }

    @Override
    public String getOrgNameInfo(String orgName) {
        return yhOrgTreeMapper.getOrgNameInfo(orgName);
    }
}
