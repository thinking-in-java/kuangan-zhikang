package com.yihe.system.service.impl;

import com.yihe.system.domain.*;
import com.yihe.system.mapper.YHDocGroupMapper;
import com.yihe.system.mapper.YhGroupInterventionMapper;
import com.yihe.system.service.YhGroupInterventionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class YhGroupInterventionServiceImpl implements YhGroupInterventionService{
    @Autowired
    YhGroupInterventionMapper yhGroupInterventionMapper;

    @Autowired
    com.yihe.system.mapper.YHDocGroupMapper YHDocGroupMapper;
    @Override
    public int updategroupintervention(YHDocGroup docgrop) {
        return yhGroupInterventionMapper.updategroupintervention(docgrop);
    }

    @Override
    public int deletegroupintervention(YHDocGroup docgrop) {
        String fenzuid="f"+docgrop.getuDocgroupId()+"_";
        List<YhUserEnrolinfo> enroinfo=yhGroupInterventionMapper.selectenroinfo(fenzuid);
        if(enroinfo!=null){
            for (int i=0;i<enroinfo.size();i++){
                String [] temptname=enroinfo.get(i).getFlagDgroup().split(fenzuid);
                String tempnames=temptname[0]+temptname[1].substring(3);
                enroinfo.get(i).setFlagDgroup(tempnames);
                yhGroupInterventionMapper.updateYhUserEnrolinfo(enroinfo.get(i));
            }
        }

        return yhGroupInterventionMapper.deletegropvention(docgrop);
    }

    @Override
    public List<Yhgroupintervention> selectgroppage(YHDocGroup Yhdocgrop) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        Yhdocgrop.setStart((Yhdocgrop.getPage()-1)*Yhdocgrop.getLimit());
        Yhdocgrop.setUsercode(yh.getUserCode());
        Yhdocgrop.setuDocgroupIdJoining("f"+Yhdocgrop.getuDocgroupId()+"_");
        Calendar mycalendar=Calendar.getInstance();
        String year=String.valueOf(mycalendar.get(Calendar.YEAR));
        if(Yhdocgrop.getMinage()!=null && !Yhdocgrop.getMinage().equals("")){
            Yhdocgrop.setMinage((Integer.valueOf(year)-Integer.valueOf(Yhdocgrop.getMinage()))+"");
        }
        if(Yhdocgrop.getMaxage()!=null && !Yhdocgrop.getMaxage().equals("")){
            Yhdocgrop.setMaxage((Integer.valueOf(year)-Integer.valueOf(Yhdocgrop.getMaxage()))+"");
        }
        if(Yhdocgrop.getLeves()!=null && !Yhdocgrop.getLeves().equals("")){
            Yhdocgrop.setLeves("f"+Yhdocgrop.getuDocgroupId()+"_"+Yhdocgrop.getLeves());
        }

        return yhGroupInterventionMapper.selectgroppage(Yhdocgrop);
    }

    @Override
    public int deletegroup(YHDocGroup docgrop) {
        List<YhUserEnrolinfo> needdelete=yhGroupInterventionMapper.needdelete(docgrop);
        docgrop.setuDocgroupIdJoining("f"+docgrop.getIds()+"_");
        for (int i=0;i<needdelete.size();i++){
            String []twos=needdelete.get(i).getFlagDgroup().split(docgrop.getuDocgroupIdJoining());
            needdelete.get(i).setFlagDgroup(twos[0]+twos[1].substring(3));
            yhGroupInterventionMapper.updateYhUserEnrolinfo(needdelete.get(i));
        }
        if(null!=docgrop.getIds()){
            YHDocGroupMapper.updateByPrimaryKeyByUserCount(new YHDocGroup(Integer.valueOf(docgrop.getIds())));
        }
        return 1;
    }

    @Override
    public int updategroup(YHDocGroup docgrop) {
        List<YhUserEnrolinfo> needdelete=yhGroupInterventionMapper.needdelete(docgrop);
        docgrop.setuDocgroupIdJoining("f"+docgrop.getIds()+"_");
        for (int i=0;i<needdelete.size();i++){
            String []twos=needdelete.get(i).getFlagDgroup().split(docgrop.getuDocgroupIdJoining());
            twos[0]=twos[0]+"f"+docgrop.getIds()+"_"+docgrop.getLeves();
            needdelete.get(i).setFlagDgroup(twos[0]+twos[1].substring(2));
            yhGroupInterventionMapper.updateYhUserEnrolinfo(needdelete.get(i));
        }
        return 1;
    }

    @Override
    public int selectgroppagecount(YHDocGroup Yhdocgrop) {
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
        Yhdocgrop.setStart((Yhdocgrop.getPage()-1)*Yhdocgrop.getLimit());
        Yhdocgrop.setUsercode(yh.getUserCode());
        Yhdocgrop.setuDocgroupIdJoining("f"+Yhdocgrop.getuDocgroupId()+"_");
        Calendar mycalendar=Calendar.getInstance();
        String year=String.valueOf(mycalendar.get(Calendar.YEAR));
        if(Yhdocgrop.getMinage()!=null && !Yhdocgrop.getMinage().equals("")){
            Yhdocgrop.setMinage((Integer.valueOf(year)-Integer.valueOf(Yhdocgrop.getMinage()))+"");
        }
        if(Yhdocgrop.getMaxage()!=null && !Yhdocgrop.getMaxage().equals("")){
            Yhdocgrop.setMaxage((Integer.valueOf(year)-Integer.valueOf(Yhdocgrop.getMaxage()))+"");
        }
        List<Integer> integr=yhGroupInterventionMapper.selectgroppagecount(Yhdocgrop);

        return integr.size();
    }

    @Override
    public List<YhTemplateDetail> selectplanbyuser(YhTemplateDetail yhTemplateDetail) {
        if(yhTemplateDetail.getTimes()!=null && !("").equals(yhTemplateDetail.getTimes())) {
            yhTemplateDetail.setBegintime(yhTemplateDetail.getTimes().substring(0, 10));
            yhTemplateDetail.setEndtime(yhTemplateDetail.getTimes().substring(13));
        }
        yhTemplateDetail.setStart((yhTemplateDetail.getPage()-1)*yhTemplateDetail.getLimit());
        return yhGroupInterventionMapper.selectplanbyuser(yhTemplateDetail);
    }

    @Override
    public int selectcount(YhTemplateDetail yhTemplateDetail) {
        if(yhTemplateDetail.getTimes()!=null && !("").equals(yhTemplateDetail.getTimes())) {
            yhTemplateDetail.setBegintime(yhTemplateDetail.getTimes().substring(0, 10));
            yhTemplateDetail.setEndtime(yhTemplateDetail.getTimes().substring(13));
        }
        yhTemplateDetail.setStart((yhTemplateDetail.getPage()-1)*yhTemplateDetail.getLimit());
        return yhGroupInterventionMapper.selectcount(yhTemplateDetail);
    }

    @Override
    public int deletesplanbyid(YhTemplateDetail yhTemplateDetail) {
        System.out.println(yhTemplateDetail.getDeleteids().length);
        yhGroupInterventionMapper.jianfa(yhTemplateDetail.getDeleteids().length,yhTemplateDetail.getUserCode());
        yhGroupInterventionMapper.deletesplanbyid(yhTemplateDetail);
        return yhGroupInterventionMapper.updatelasttime(yhTemplateDetail);
    }

    @Override
    public List<YhProgramsDictionary> selectplancount() {
        return yhGroupInterventionMapper.selectplancount();
    }


}
