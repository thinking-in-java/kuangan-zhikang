package com.yihe.web.controller.system;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.alibaba.fastjson.JSON;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.DocumentException;
import com.yihe.system.utils.EchartsUtil;
import com.yihe.system.utils.FreemarkerUtil;

import freemarker.template.TemplateException;
import net.sf.jasperreports.engine.JRException;
import sun.misc.BASE64Decoder;
@Controller
@RequestMapping("/YhJasperController")
public class YhJasperController {
	@GetMapping("/testJasper")
    public void createPdfs(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException, DocumentException, com.itextpdf.text.DocumentException {
		Document document = new Document();
		response.setContentType("application/x-msdownload;charset=UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=iTextPDF.pdf");
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
		ITextRenderer renderer = new ITextRenderer();
		ITextFontResolver fontResolver = renderer.getFontResolver(); 
		fontResolver.addFont("C:/Windows/Fonts/SimSun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        fontResolver.addFont("C:/Windows/Fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        fontResolver.addFont("C:/Windows/Fonts/simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		OutputStream out = null;
		out = response.getOutputStream();

		 
		        String url = "http://localhost/manage/YhJasperController/test";
		        System.out.println("Loading page now-----------------------------------------------: "+url);
		        
		        // HtmlUnit 模拟浏览器
		        WebClient webClient  = new WebClient(BrowserVersion.CHROME); 
		        webClient.getOptions().setJavaScriptEnabled(true);
		        webClient.getOptions().setCssEnabled(false);
		        webClient.getOptions().setTimeout(20000);          
		        HtmlPage page = webClient.getPage("http://localhost/manage/YhJasperController/test");
		        String pageXml = page.asXml();
		        webClient.waitForBackgroundJavaScript(30* 1000);
		        String pageAsXml = page.asXml();
		        System.out.println(pageAsXml);
		        org.jsoup.nodes.Document doc= Jsoup.parse(pageAsXml,"http://localhost/manage/YhJasperController/test");
		        String htmlstr = "<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"<style type=\"text/css\">\r\n" + 
				"body{font-family: SimSun;}\r\n" + 
				"</style>"+
				"<title>Insert title here</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"<div style='color: red'>123456</div>\r\n" + 
				"<h1>测试</h1>\r\n" + 
				"</body>\r\n" + 
				"</html>";
		htmlstr=doc.toString();
		System.out.println(htmlstr);
		document.add(new Paragraph(renderer.toString()));
		renderer.setDocumentFromString(htmlstr);
		renderer.layout();
		renderer.createPDF(out);
		renderer.finishPDF();
		renderer = null;
		out.close();  
    }
	
	public static void main(String[] args) throws ClientProtocolException, IOException, TemplateException {
        // 变量
        String title = "水果";
        String[] categories = new String[] { "苹果", "香蕉", "西瓜" };
        int[] values = new int[] { 3, 2, 111 };
        JsonArray array=new JsonArray();
        JsonObject language1=new JsonObject();
        language1.addProperty("name","正常");
        language1.addProperty("value", 125276);
        JsonObject language2=new JsonObject();
        language2.addProperty("name","销户");
        language2.addProperty("value", 22957);
        JsonObject language3=new JsonObject();
        language3.addProperty("name","报停");
        language3.addProperty("value", 41536);
        JsonObject language4=new JsonObject();
        language4.addProperty("name","拆表");
        language4.addProperty("value", 35783);
        JsonObject language5=new JsonObject();
        language5.addProperty("name","关阀");
        language5.addProperty("value", 5542);
        JsonObject language6=new JsonObject();
        language6.addProperty("name","待拆迁");
        language6.addProperty("value", 5542);
        JsonObject language7=new JsonObject();
        language7.addProperty("name","其他");
        language7.addProperty("value", 63504);
        array.add(language2);
        array.add(language1);
        array.add(language3);
        array.add(language4);
        array.add(language5);
        array.add(language6);
        array.add(language7);
        String val=" [{ 'name': '正常','value': 125276}, {'name': '销户','value': 22957}, "
        		+ "{'name': '报停','value': 41536}, "
        		+ "{'name': '拆表','value': 35783}, "
        		+ "{'name': '关阀','value':5542}, "
        		+ "{'name': '待拆迁','value':5542},"
        		+ "{'name': '其他','value': 63504 }],}]";
        // 模板参数
        HashMap<String, Object> datas = new HashMap<>();
        datas.put("categories", JSON.toJSONString(categories));
        datas.put("values", JSON.toJSONString(values));
        //datas.put("values", array);
        datas.put("title", title);

        // 生成option字符串
        String option = FreemarkerUtil.generateString("test.ftl", "/static/ftl", datas);

        // 根据option参数
        String base64 = EchartsUtil.generateEchartsBase64(option);
        
        System.out.println("BASE64:" + base64);
        generateImage(base64, "C:/Users/Royiwen/Desktop/test.png");
    }

    public static void generateImage(String base64, String path) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        try (OutputStream out = new FileOutputStream(path)){
            // 解密
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            out.write(b);
            out.flush();
        } 
    }

}
