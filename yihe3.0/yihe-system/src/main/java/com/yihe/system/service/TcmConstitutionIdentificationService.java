package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserReginfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: 贺平
 * @Date: 2021/11/1
 * @ClassName: com.yihe.system.service
 * @Description:
 **/
public interface TcmConstitutionIdentificationService {

    //查询所有用户的中医体质辨识信息
    PageInfo<YhUserReginfo> tcmConstitutionIdentificationUserAll(Map<String,Object> map);

    //根据筛选条件查询指定用户的中医体质辨识信息
    List<YhUserReginfo> tcmConstitutionIdentificationConditionUser();

    //根据UserCode查询医生编号
    String selectDoctorIdByUserCode(String userCode);
}
