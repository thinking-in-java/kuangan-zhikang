/**
 * 
 */
package com.yihe.system.domain;

import java.util.Map;
import org.json.JSONObject;



/**
 * @author Administrator
 *
 */
public class WechatMould {
	
	private String touser; //接收者（用户）的 openid
    private String template_id; //所需下发的模板消息的id
    private String page; //点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
    private JSONObject data; //模板内容，不填则下发空模板
    private String emphasis_keyword; //模板需要放大的关键词，不填则默认无放大
    private String token;
    
    
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public JSONObject getData() {
		return data;
	}
	public void setData(JSONObject data) {
		this.data = data;
	}
	public String getEmphasis_keyword() {
		return emphasis_keyword;
	}
	public void setEmphasis_keyword(String emphasis_keyword) {
		this.emphasis_keyword = emphasis_keyword;
	}
	    
    
    
    
}
