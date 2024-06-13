package com.yihe.applet.api.util.request;


import com.yihe.applet.api.util.common.util.HttpClientUtil;
import com.yihe.applet.api.util.common.util.JsonUtil;
import com.yihe.applet.api.util.enums.Version;

/**
 * 使用RSA对称加密访问API
 * @author yangsongbo
 */
public class DefaultHealthAiClient extends AbstractHealthAiClient implements HealthAiClient {

    private String privateKey;


    public DefaultHealthAiClient(String appId, String privateKey, Version version) {
        super.appId = appId;
        this.privateKey = privateKey;
        super.version = version.getValue();
    }

    @Override
    protected String execute(ServiceRequest request, String url) {

        String sign = SignHelper.sign(new SignHelper.SignFieldBean(
                appId, request.getNonceStr(), request.getTimestamp(), version), privateKey);
        request.setSign(sign);

        String reqMessage = JsonUtil.toJson(request);

        //set last request json
        super.setLastRequestJson(reqMessage);
        return HttpClientUtil.post(url, reqMessage, HttpClientUtil.CONTENT_TYPE_JSON);
    }

}
