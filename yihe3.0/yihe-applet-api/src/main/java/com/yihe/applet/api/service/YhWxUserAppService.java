package com.yihe.applet.api.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.domain.YhWxUser;

/**
* YhWxUserService接口
* Created by dong on 2020/2/25.
*/
public interface YhWxUserAppService {
	int modifyWXuserStatusInfo(String usercode, int status);
	
	int deleteByPrimaryKey(String userId,   String oppenId);

    int insert(YhWxUser record);

    int insertSelective(YhWxUser record);

    List<YhWxUser>  selectByUsercode(String usercode);
    YhWxUser selectByPrimaryKey( String userId,   String oppenId);


    int updateByPrimaryKeySelective(YhWxUser record);

    int updateByPrimaryKey(YhWxUser record);
    
    YhWxUser selectHeadImgbyParam(String userId);
    
    List<String> selectRandomheandImg(String type);
    
    
}