package com.yihe.system.service.impl;

import com.yihe.system.domain.ChatListByUserResqVo;
import com.yihe.system.domain.YhUserChatRecordWed;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.YhDoctorChatListMapper;
import com.yihe.system.service.YhUserChatRecordWedService;
import com.yihe.system.service.YhUserDialogueListService;
import com.yihe.system.service.YhUserReginfoService;
import com.yihe.system.utils.RelativeDateFormat;
import com.yihe.system.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class YhUserDialogueListServiceImpl implements YhUserDialogueListService {
    @Autowired
    YhDoctorChatListMapper yhDoctorChatListMapper;
    @Autowired
    YhUserReginfoService yhUserReginfoService;
    @Autowired
    YhUserChatRecordWedService yhUserChatRecordWedService;

    /**
     * 查询用户聊天会话列表
     *
     * @param usercode
     */
    @Override
    public List<ChatListByUserResqVo> findSessionList(String usercode) {
        List<ChatListByUserResqVo> sessionList = yhDoctorChatListMapper.findSessionList(usercode);
        if(CollectionUtils.isEmpty(sessionList)){
            sessionList=  yhDoctorChatListMapper.findSessionListByorgCode();
        }
        String userForDoctor = yhDoctorChatListMapper.findUserForDoctor(usercode);
        if(StringUtil.isNotEmpty(userForDoctor)){
            ChatListByUserResqVo chatListByUserResqVo=new ChatListByUserResqVo();
            YhUserReginfo yhUserReginfo = yhUserReginfoService.findYhUserReginfo(userForDoctor);
            String postCode = yhUserReginfo.getPostCode();
            if(StringUtil.isNotEmpty(postCode)){
                chatListByUserResqVo.setPostCode(postCode);
            }else{
                Integer gender = yhUserReginfo.getGender();
                if(gender==1){
                    chatListByUserResqVo.setPostCode("https://yjyqny.yhy.ren/new/maleDoctor.png");
                }else{
                    chatListByUserResqVo.setPostCode("https://yjyqny.yhy.ren/new/femaleDoctor.png");
                }
            }

            chatListByUserResqVo.setType("健管师");
            chatListByUserResqVo.setDocId(userForDoctor);
            chatListByUserResqVo.setInfo("从营养方面健康的检测、分析、评估以及健康咨询、指导和危险因素干预等工作的专业人员。");
            sessionList.add(chatListByUserResqVo);
        }

        Iterator<ChatListByUserResqVo> iterator = sessionList.iterator();
        while(iterator.hasNext()){
            ChatListByUserResqVo chatListByUserResqVo = iterator.next();
            String docId = chatListByUserResqVo.getDocId();
            String type = chatListByUserResqVo.getType();
            if (StringUtil.isNotEmpty(userForDoctor) && userForDoctor.equals(docId)&& !type.equals("健管师")&& sessionList.size()>1) {
                iterator.remove();
                continue;
            }
            YhUserChatRecordWed chatInfoByUserCodeByOne = yhUserChatRecordWedService.findChatInfoByUserCodeByOne(usercode, docId);
            if(null!=chatInfoByUserCodeByOne){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date =null;
                try {
                    date = format.parse(chatInfoByUserCodeByOne.getAddtimeinfo());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String timeInfo = RelativeDateFormat.format(date);
                chatInfoByUserCodeByOne.setAddtimeinfo(timeInfo);
                chatListByUserResqVo.setChat(chatInfoByUserCodeByOne);
            }

        }



        return sessionList;
    }
}
