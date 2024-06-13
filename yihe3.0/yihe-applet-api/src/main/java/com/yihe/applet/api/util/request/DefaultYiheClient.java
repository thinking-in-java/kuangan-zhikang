package com.yihe.applet.api.util.request;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yihe.applet.api.util.common.util.HttpClientUtil;
import com.yihe.applet.api.util.common.util.JsonUtil;

/**
 * 使用RSA对称加密访问API
 *
 * @author lll
 */
public class DefaultYiheClient extends AbstractYiheClient implements YiheClient {

    private String privateKey;


    public DefaultYiheClient(String personId, String privateKey,String timestamp) {
        super.personId = personId;
        this.privateKey = privateKey;
        super.timestamp = timestamp;
    }

    @Override
    protected String execute2(JSONObject request, String url) {
        String sign=null;
		try {
			sign = SignHelper.signTimeId(personId, timestamp, privateKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
        String reqMessage = request.toJSONString();
     
        super.setLastRequestJson(reqMessage);
        Map<String, String> header = new HashMap<String, String>(2);
        header.put("X-DTalksTech-Date",timestamp);
        header.put("DTalksTech",sign);
        return HttpClientUtil.post(url, reqMessage, HttpClientUtil.CONTENT_TYPE_JSON,header);
    }

	@Override
	String execute(YiheServiceRequest request, String url) {
		String sign=null;
		try {
			sign = SignHelper.signTimeId(personId, timestamp, privateKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
        String reqMessage = JsonUtil.toJson(request);
        super.setLastRequestJson(reqMessage);
        Map<String, String> header = new HashMap<String, String>(2);
        header.put("X-DTalksTech-Date",timestamp);
        header.put("DTalksTech",sign);
        return HttpClientUtil.post(url, reqMessage, HttpClientUtil.CONTENT_TYPE_JSON,header);
	}


	

}
