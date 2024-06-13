package com.yihe.system.service;

import com.yihe.system.domain.yhWxNotify;

import java.util.List;

public interface YhWxNotifyService {
    int insertyhWxNotify(yhWxNotify notify);
    yhWxNotify selectyhweno(yhWxNotify yhwxnotify);
    List<yhWxNotify> selectznxbyusercode(yhWxNotify yhwxnotify);
    int changedate(yhWxNotify yhwxnotify);
}
