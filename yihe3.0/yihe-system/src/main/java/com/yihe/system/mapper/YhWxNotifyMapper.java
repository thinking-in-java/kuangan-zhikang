package com.yihe.system.mapper;

import com.yihe.system.domain.yhWxNotify;

import java.util.List;

public interface YhWxNotifyMapper {
    int insertyhnotify(yhWxNotify notify);
    yhWxNotify selectyhweno(yhWxNotify yhwxnotify);
    List<yhWxNotify> selectznxbyusercode(yhWxNotify yhwxnotify);
    int changedate(yhWxNotify yhwxnotify);
}
