package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhplanDetail;

public interface YhPlanDetailService {
    int selectfoodcount(String usercode);
    int selectsportcount(String usercode);
    int changefoodse(YhplanDetail yhplanDetail);
}
