package com.yihe.web.controller.tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @author terry.huang
 * 2017年11月6日 下午5:53:18
 */
public interface HttpCommand {
	public static String encoding = "utf-8";
	/**
	 * 处理数据
	 * @param udpServerSocket
	 * @param sourceInfo
	 * @throws IOException
	 */
	void handle(HttpServletRequest req,HttpServletResponse res,String sourceInfo) throws IOException;
}
