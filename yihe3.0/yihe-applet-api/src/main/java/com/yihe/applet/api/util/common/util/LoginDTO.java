package com.yihe.applet.api.util.common.util;

 
/**
 * Created by codedrinker on 2018/11/24.
 */
public class LoginDTO {
    // 用户信息原始数据
    private String rawData;

    // 用于验证用户信息是否被篡改过
    private String signature;

    // 用户获取 session_key 的 code
    private String code;
    
    //用户id
    private String userCode;

    private String iv;
    private String encryptedData;

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getRawData() {
		return rawData;
	}

	public void setRawData(String rawData) {
		this.rawData = rawData;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    
}
