package com.yihe.web.controller.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.collections.Buffer;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * 测试httpclient 4.0 1. 重新设计了HttpClient 4.0 API架构，彻底从内部解决了所有 HttpClient 3.x 已知的架构缺陷代码。 2. HttpClient 4.0 提供了更简洁，更灵活，更明确的API。 3. HttpClient 4.0 引入了很多模块化的结构。 4.
 * HttpClient 4.0性能方面得到了不小的提升，包括更少的内存使用，通过使用HttpCore模块更高效完成HTTP传输。 5. 通过使用 协议拦截器(protocol interceptors), HttpClient 4.0实现了 交叉HTTP（cross-cutting HTTP protocol）
 * 协议 6. HttpClient 4.0增强了对连接的管理，更好的处理持久化连接，同时HttpClient 4.0还支持连接状态 7. HttpClient 4.0增加了插件式（可插拔的）的 重定向（redirect） 和 验证（authentication）处理。 8. HttpClient
 * 4.0支持通过代理发送请求，或者通过一组代理发送请求。 9. 更灵活的SSL context 自定义功能在HttpClient 4.0中得以实现。 10. HttpClient 4.0减少了在省城HTTP请求 和 解析HTTP响应 过程中的垃圾信息。 11. HttpClient团队鼓励所有的项目升级成
 * HttpClient 4.0
 *
 * 
 */
public class HttpUtils {

	/* 发送 post请求 用HTTPclient 发送请求*/
	public byte[] post(String URL, String json) {
		String obj = null;
		InputStream inputStream = null;
		Buffer reader = null;
		byte[] data = null;
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(URL);
		httppost.addHeader("Content-type", "application/json; charset=utf-8");
		httppost.setHeader("Accept", "application/json");
		try {
			StringEntity s = new StringEntity(json, Charset.forName("UTF-8")); 
			s.setContentEncoding("UTF-8");
			httppost.setEntity(s);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				// 获取相应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					inputStream = entity.getContent();
					data = readInputStream(inputStream);
				}
				return data;
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}
 
    /**  将流 保存为数据数组
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}
}