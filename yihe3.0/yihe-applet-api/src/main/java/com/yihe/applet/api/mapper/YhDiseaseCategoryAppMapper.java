package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhDiseaseCategoryApp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Description:
 * Created by Administrator on 2020/5/26 14:08
 */
public interface YhDiseaseCategoryAppMapper {
    /*
    查询前十条记录
     */
    List<YhDiseaseCategoryApp> selTopTenData(@Param("diseaseCode")int diseaseCode);
    /*
    根据疾病id查询name
     */
    YhDiseaseCategoryApp selCategoryByCode(@Param("diseaseCode")int diseaseCode);

    List<YhDiseaseCategoryApp>  newFindDis(@Param("sex")int sex);
}
