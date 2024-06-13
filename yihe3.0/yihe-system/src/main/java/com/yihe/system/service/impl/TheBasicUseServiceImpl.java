package com.yihe.system.service.impl;

import com.yihe.system.domain.TheBasicUse;
import com.yihe.system.mapper.TheBasicUseMapper;
import com.yihe.system.service.TheBasicUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheBasicUseServiceImpl implements TheBasicUseService {
    @Autowired
    TheBasicUseMapper theBasicUseMapper;
    @Override
    public TheBasicUse jiankangfengxian(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectjiankang(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectjiangkangtimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse changjian(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectchangjian(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectchangjiantimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse Axing(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectAxing(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectAxingtimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse PSTRI(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectPSTRI(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectPSTRItimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse xinlijiankang(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectxinlijiankang(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectxinlijiankangtimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse sleeprepout(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectsleeprepout(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectsleeprepouttimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse xiguanpinggu(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectxiguanpinggu(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectxiguanpinggutimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse tiwen(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecttiwen(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        int s=theBasicUseMapper.selecttiwentimes(theBasicUse);
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selecttiwentimes(theBasicUse));
        return theBasicUse;
    }


    @Override
    public TheBasicUse tizhong(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectizhong(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selecttizhongtimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse xueya(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecxueya(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectxueyatimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse xuetang(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecxuetang(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectxuetangtimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse xueyang(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecxueyang(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectxueyangtimes(theBasicUse));
        return theBasicUse;
    }
    @Override
    public TheBasicUse xinlv(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecxinlv(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectxinlvtimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse tizhi(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectizhi(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selecttizhitimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse xuezhi(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecxuezhi(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectxuezhitimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse shengongneg(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecshengongneg(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectshengongnegtimes(theBasicUse));
        return theBasicUse;
    }


    @Override
    public TheBasicUse gangongneng(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecgangongneng(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectgangongtimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse xindaintu(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecxindaintu(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectxindaintutimes(theBasicUse));
        return theBasicUse;
    }
    @Override
    public TheBasicUse nianjian(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecnianjian(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectnianjiantimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse qitazhibiao(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecqitazhibiao(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectqitazhibiaotimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse yinshiriji(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecyinshiriji(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectyinshirijitimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse yundongriji(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecyundongriji(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectyundongrijitimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse shuimianriji(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecshuimianriji(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectshuimianrijitimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse gaoxueya(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selecgaoxueya(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectgaoxueyatimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse gaoxuetang(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectgaoxuetang(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectgaoxuetangtimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse gaoxuezhi(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectgaoxuezhi(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectgaoxuezhitimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse gaoniansuan(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectgaoniansuan(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectgaoniansuantimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse jianzhi(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectjianzhi(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectjianzhitimes(theBasicUse));
        return theBasicUse;
    }

    @Override
    public TheBasicUse xiguanriji(TheBasicUse theBasicUse) {
        //查询总人数
        theBasicUse.setAllcount(theBasicUseMapper.allprople(theBasicUse));
        //查询健康风险评估的人数
        theBasicUse.setUsecount(theBasicUseMapper.selectxiguanriji(theBasicUse));
        //计算出未评估的人数
        theBasicUse.setAllcount(theBasicUse.getAllcount()-theBasicUse.getUsecount());
        //查询出评估的次数
        theBasicUse.setTimes(theBasicUseMapper.selectxiguanrijitimes(theBasicUse));
        return theBasicUse;
    }
}
