package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhQuestionAssessTitleFrontApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 * Created by Administrator on 2020/5/22 17:57
 */
public interface YhQuestionAssessTitleFrontAppMapper {
    List<YhQuestionAssessTitleFrontApp> selAllQuestion(@Param("gender") int gender);
}
