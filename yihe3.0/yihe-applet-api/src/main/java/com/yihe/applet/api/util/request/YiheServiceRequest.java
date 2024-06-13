package com.yihe.applet.api.util.request;

/**
 * Api Request
 *
 * @author lll
 */
public class YiheServiceRequest {
    
    /**
     * 业务数据
     */
    private String reqData;

    public YiheServiceRequest() {
    }

    public YiheServiceRequest(String reqData) {
       
        this.reqData = reqData;
    }



	public String getReqData() {
        return reqData;
    }

    public void setReqData(String reqData) {
        this.reqData = reqData;
    }


}
