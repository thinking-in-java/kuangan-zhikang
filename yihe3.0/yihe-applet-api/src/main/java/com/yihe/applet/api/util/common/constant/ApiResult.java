package com.yihe.applet.api.util.common.constant;


import com.yihe.system.utils.BaseResult;

/**
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2019年12月5日 上午10:29:53 
 *@version 1.0 
 */
public class ApiResult extends BaseResult {

    public ApiResult(int code, String message, Object data) {
        super(code, message, data);
    }

    public ApiResult(ApiResultConstant apiResultConstant, Object data) {
        super(apiResultConstant.getCode(), apiResultConstant.getMessage(), data);
    }
    
    public ApiResult(ApiResultConstant apiResultConstant) {
        super(apiResultConstant.getCode(), apiResultConstant.getMessage());
    }
}
