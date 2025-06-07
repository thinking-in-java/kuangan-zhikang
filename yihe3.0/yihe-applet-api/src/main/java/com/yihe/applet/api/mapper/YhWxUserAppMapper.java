package com.yihe.applet.api.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.domain.YhWxUser;

public interface YhWxUserAppMapper {

	
	int modifyWXuserStatusInfo(@Param("usercode") String usercode, @Param("status") int status);
	
    int deleteByPrimaryKey(@Param("userId") String userId, @Param("oppenId") String oppenId);

    int insert(YhWxUser record);

    int insertSelective(YhWxUser record);

    List<YhWxUser>  selectByUsercode(@Param("usercode")String usercode);
    
    YhWxUser selectByPrimaryKey(@Param("userId") String userId, @Param("oppenId") String oppenId);


    int updateByPrimaryKeySelective(YhWxUser record);

    int updateByPrimaryKey(YhWxUser record);
    
    YhWxUser selectHeadImgbyParam(String userId);
    
    List<String> selectRandomheandImg(@Param("type") String type);
    
}