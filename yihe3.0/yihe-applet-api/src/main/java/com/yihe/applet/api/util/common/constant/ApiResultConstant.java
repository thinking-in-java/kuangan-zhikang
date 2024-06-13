package com.yihe.applet.api.util.common.constant;

/** 
 *@author lll 
 *@Email:2982564698@qq.com 
 *@phone:17608493351
 *@date 创建时间：2019年12月5日 上午10:30:24 
 *@version 1.0 
 *api系统接口结果常量枚举类
 */
public enum  ApiResultConstant {
	 /**
     * 成功
	 * @return 
     */
    SUCCESS(1, "success"),
    
    FAIL(2,"FAIL");
	
	 /**
     * 成功
	 * @return 
     */
    

    public int code;

    public String message;

    ApiResultConstant(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
