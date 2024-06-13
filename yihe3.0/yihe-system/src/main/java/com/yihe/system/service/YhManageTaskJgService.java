package com.yihe.system.service;

import com.yihe.system.domain.YhManageTotalTask;
import com.yihe.system.domain.YhServicePage;

import java.util.List;

public interface YhManageTaskJgService {
    int selectcountpage(YhServicePage Yhservicepage);
    YhManageTotalTask selectservicecount(YhServicePage Yhservicepage);
    List<YhManageTotalTask> selectlist(YhServicePage Yhservicepage);
    YhServicePage selectfenxi(YhServicePage YhServicePage);
    List<YhServicePage> getmundoctorby(YhServicePage YhServicePage);
    List<YhServicePage> getmundoctorby2(YhServicePage YhServicePage);
}
