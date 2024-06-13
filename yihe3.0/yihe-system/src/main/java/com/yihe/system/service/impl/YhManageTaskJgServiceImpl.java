package com.yihe.system.service.impl;

import com.yihe.system.domain.YhManageTotalTask;
import com.yihe.system.domain.YhServicePage;
import com.yihe.system.mapper.YhManageTaskJgMapper;
import com.yihe.system.service.YhManageTaskJgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YhManageTaskJgServiceImpl implements YhManageTaskJgService {
    @Autowired
    YhManageTaskJgMapper yhManageTaskJgMapper;
    @Override
    public int selectcountpage(YhServicePage Yhservicepage) {
        // TODO Auto-generated method stub
        if(Yhservicepage.getTime()!=null && Yhservicepage.getTime()!="") {
            Yhservicepage.setBegintime(Yhservicepage.getTime().subSequence(0, 10).toString());
            Yhservicepage.setEndtime(Yhservicepage.getTime().subSequence(13,23)+" 23:59:59");
        }
        return yhManageTaskJgMapper.selectcountpage(Yhservicepage);
    }

    @Override
    public YhManageTotalTask selectservicecount(YhServicePage Yhservicepage) {
        // TODO Auto-generated method stub
        if(Yhservicepage.getTime()!=null && Yhservicepage.getTime()!="") {
            Yhservicepage.setBegintime(Yhservicepage.getTime().subSequence(0, 10).toString());
            Yhservicepage.setEndtime(Yhservicepage.getTime().subSequence(13,23)+" 23:59:59");
        }
        return yhManageTaskJgMapper.selectservicecount(Yhservicepage);
    }

    @Override
    public List<YhManageTotalTask> selectlist(YhServicePage Yhservicepage) {
        // TODO Auto-generated method stub
        Yhservicepage.setBeginpage((Yhservicepage.getPage()-1)*20);
        if(Yhservicepage.getTime()!="" && Yhservicepage.getTime()!=null) {
            Yhservicepage.setBegintime(Yhservicepage.getTime().subSequence(0, 10).toString());
            Yhservicepage.setEndtime(Yhservicepage.getTime().subSequence(13,23)+" 23:59:59");
        }
        List<YhManageTotalTask> managetask=yhManageTaskJgMapper.selectlist(Yhservicepage);
        return managetask;
    }

    @Override
    public YhServicePage selectfenxi(YhServicePage YhServicePage) {
        return yhManageTaskJgMapper.selectfenxi(YhServicePage);
    }

    @Override
    public List<YhServicePage> getmundoctorby(YhServicePage YhServicePage) {
        return yhManageTaskJgMapper.getmundoctorby(YhServicePage);
    }

    @Override
    public List<YhServicePage> getmundoctorby2(YhServicePage YhServicePage) {
        return yhManageTaskJgMapper.getmundoctorby2(YhServicePage);
    }
}
