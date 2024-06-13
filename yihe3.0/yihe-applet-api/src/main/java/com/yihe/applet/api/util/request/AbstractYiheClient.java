package com.yihe.applet.api.util.request;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.alibaba.fastjson.JSONObject;
import com.yihe.applet.api.util.common.util.JsonUtil;
import com.yihe.applet.api.util.response.AbstractHealthAiResData;
import com.yihe.applet.api.util.response.HealthAiResponse;
import com.yihe.applet.api.util.response.ServiceResponse;

/**
 * API 执行抽象类
 *
 * @author yangsongbo
 */
public abstract class AbstractYiheClient implements YiheClient {
	/**
     * 应用ID
     */
	protected String personId;
	/**
     * 应用ID
     */
	protected String timestamp;
    /**
     * 最后一次的请求Json
     */
    private String lastRequestJson;


    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);


    public String getLastRequestJson() {
        return lastRequestJson;
    }

    public void setLastRequestJson(String lastRequestJson) {
        this.lastRequestJson = lastRequestJson;
    }

    
    
    public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	protected String getPersonId() {
		return personId;
	}

    protected void setPersonId(String personId) {
		this.personId = personId;
	}

	/**
     * 执行请求
     * @param request 请求数据
     * @param url     请求URL
     * @return 返回数据 JSON
     */
    abstract String execute(YiheServiceRequest request, String url);
    
    /**
     * 执行请求
     * @param request 请求数据
     * @param url     请求URL
     * @return 返回数据 JSON
     */
    abstract String execute2(JSONObject request, String url);


    public String execute(String reqDataJson, String url) {

    	YiheServiceRequest request = new YiheServiceRequest(reqDataJson);

        return this.execute(request, url);
    }
     

    public String execute(JSONObject reqData, String url) {

    	//YiheServiceRequest request = new YiheServiceRequest(JsonUtil.toJson(reqData));

        return this.execute2(reqData, url);
    }

    public <T extends AbstractHealthAiResData> HealthAiResponse<T> execute(AbstractHealthAiReqData reqData, String url) {

    	YiheServiceRequest request = new YiheServiceRequest(JsonUtil.toJson(reqData));

        String result = this.execute(request, url);

        ServiceResponse response;
        try {
            response = JsonUtil.fromJson(result, ServiceResponse.class);
        } catch (Exception e) {
            response = null;
        }

        T trs = null;
        if (response != null && response.getResData() != null && response.getResData().length() > 0) {
            trs = (T) JsonUtil.fromJson(response.getResData(), reqData.getResponseClass());
        }

        HealthAiResponse<T> healthAiResponse = new HealthAiResponse<T>();
        healthAiResponse.setResData(trs);
        healthAiResponse.setResponseJson(result);
        return healthAiResponse;
    }

	

}
