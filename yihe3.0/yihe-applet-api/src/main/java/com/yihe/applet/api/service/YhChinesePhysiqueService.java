package com.yihe.applet.api.service;

import com.yihe.applet.api.controller.Yhhealthplan1;
import com.yihe.applet.api.domain.YhChineseAnwser2;
import com.yihe.applet.api.domain.YhChinesePhysique;

import java.util.List;

public interface YhChinesePhysiqueService {
    List<YhChinesePhysique> selectallinfo(String Usercode);
    int addhinesePhysique(Integer type, String list, String userCode);
    YhChineseAnwser2 selectanwser(String Usercode);
    Boolean isfirstzy(String Usercode);
    YhChineseAnwser2 isfirstzy2(String Usercode);
    List<Yhhealthplan1> getallhelplan(String Usercode);
}
