package com.yihe.system.service.impl;

import com.yihe.system.domain.yhWxNotify;
import com.yihe.system.mapper.YhWxNotifyMapper;
import com.yihe.system.service.YhWxNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YhWxNotifyServiceImpl implements YhWxNotifyService {
    @Autowired
    YhWxNotifyMapper yhWxNotifyMapper;

    @Override
    public int insertyhWxNotify(yhWxNotify notify) {
        return yhWxNotifyMapper.insertyhnotify(notify);
    }

    @Override
    public yhWxNotify selectyhweno(yhWxNotify yhwxnotify) {
        return yhWxNotifyMapper.selectyhweno(yhwxnotify);
    }

    @Override
    public List<yhWxNotify> selectznxbyusercode(yhWxNotify yhwxnotify) {
        return yhWxNotifyMapper.selectznxbyusercode(yhwxnotify);
    }

    @Override
    public int changedate(yhWxNotify notify) {
        return yhWxNotifyMapper.changedate(notify);
    }
}
