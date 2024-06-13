package com.yihe.applet.api.domain;

import lombok.Data;

@Data
public class YhDiseaseRiskResultNewApp {
    private static final long serialVersionUID = 1L;

    private Integer id;
    /** 请求序列号 */
    private String requestSerialNum;
    /** 用户id */
    private String userCode;

    private String resultName;

    private String resultJson;
}
