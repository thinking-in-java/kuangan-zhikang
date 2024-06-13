package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhQuestionCheckJsonApp;
import org.apache.ibatis.annotations.Param;

/**
 * Description:
 * Created by Administrator on 2020/5/28 8:13
 */
public interface YhQuestionCheckJsonAppMapper {
    /*
    增
     */
    int insertYhQuestionCheckJson(YhQuestionCheckJsonApp yhQuestionCheckJsonApp);
    /*
    改
     */
    int updateYhQuestionCheckJson(YhQuestionCheckJsonApp yhQuestionCheckJsonApp);
    /*
    查
     */
    YhQuestionCheckJsonApp selectYhQuestionCheckJsonByUserCode(@Param("userCode") String userCode, @Param("userCheckTs")Integer userCheckTs);
    
    int selectYhQuestionCheckCount();

    int delYhQuestionCheckJson(String userCode);
}
