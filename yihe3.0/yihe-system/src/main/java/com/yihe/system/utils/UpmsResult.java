package com.yihe.system.utils;


/**
 * upms系统常量枚举类
 * Created by lllon 2019/12/05
 */
public class UpmsResult extends BaseResult {

    public UpmsResult(UpmsResultConstant upmsResultConstant, Object data) {
        super(upmsResultConstant.getCode(), upmsResultConstant.getMessage(), data);
    }

}
