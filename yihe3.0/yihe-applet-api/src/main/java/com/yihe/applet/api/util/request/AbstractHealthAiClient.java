package com.yihe.applet.api.util.request;


import com.yihe.applet.api.util.common.util.JsonUtil;
import com.yihe.applet.api.util.common.util.UUIDUtil;
import com.yihe.applet.api.util.response.AbstractHealthAiResData;
import com.yihe.applet.api.util.response.HealthAiResponse;
import com.yihe.applet.api.util.response.ServiceResponse;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;


/**
 * API 执行抽象类
 *
 * @author yangsongbo
 */
public abstract class AbstractHealthAiClient implements HealthAiClient {

    /**
     * 应用ID
     */
    protected String appId;

    /**
     * 版本号
     */
    protected String version;

    /**
     * 最后一次的请求Json
     */
    private String lastRequestJson;


    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLastRequestJson() {
        return lastRequestJson;
    }

    public void setLastRequestJson(String lastRequestJson) {
        this.lastRequestJson = lastRequestJson;
    }

    /**
     * 执行请求
     *
     * @param request 请求数据
     * @param url     请求URL
     * @return 返回数据 JSON
     */
    abstract String execute(ServiceRequest request, String url);


    public String execute(String reqDataJson, String url) {

        ServiceRequest request = new ServiceRequest(appId, UUIDUtil.getJavaUUID(),
                version, sdf.format(new Timestamp(System.currentTimeMillis())), reqDataJson);

        return this.execute(request, url);
    }


    public String execute(Map<String, Object> reqData, String url) {

        ServiceRequest request = new ServiceRequest(appId, UUIDUtil.getJavaUUID(),
                version, sdf.format(new Timestamp(System.currentTimeMillis())), JsonUtil.toJson(reqData));

        return this.execute(request, url);
    }

    public <T extends AbstractHealthAiResData> HealthAiResponse<T> execute(AbstractHealthAiReqData reqData, String url) {

        ServiceRequest request = new ServiceRequest(appId, UUIDUtil.getJavaUUID(),
                version, sdf.format(new Timestamp(System.currentTimeMillis())), JsonUtil.toJson(reqData));

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
