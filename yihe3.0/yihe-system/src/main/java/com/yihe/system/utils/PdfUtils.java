package com.yihe.system.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class PdfUtils {
	// 利用模板生成pdf
	public static void pdfout(Map<String, Object> o,ServletOutputStream out,String templatePath) {
		// 模板路径
		//String templatePath = "C:/mytest.pdf";
		// 生成的新文件路径
		String newPDFPath = "C:/testout1.pdf";

		 PdfReader reader;
	        ByteArrayOutputStream bos;	
	        PdfStamper stamper;
		try {
			BaseFont bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
			//BaseFont bf = BaseFont.createFont("c://windows//fonts//simsun.ttc,1", BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
			Font FontChinese = new Font(bf, 5, Font.NORMAL);
			//out = new FileOutputStream(newPDFPath);// 输出流
			 reader = new PdfReader(templatePath);// 读取pdf模板// 读取pdf模板
			bos = new ByteArrayOutputStream();
			stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();
			// 文字类的内容处理
			Map<String, String> datemap = (Map<String, String>) o.get("datemap");
			form.addSubstitutionFont(bf);
			for (String key : datemap.keySet()) {
				String value = datemap.get(key);
				if(key.equals("title")){
					form.setFieldProperty(key, "textsize", 18f, null);
					BaseColor bc  =new BaseColor(89,89,89);
					form.setFieldProperty(key, "textcolor", bc, null);
				}
				
				form.setField(key, value);
			}
			// 图片类的内容处理
			Map<String, String> imgmap = (Map<String, String>) o.get("imgmap");
			for (String key : imgmap.keySet()) {
				String value = imgmap.get(key);
				String imgpath = value;
				int pageNo = form.getFieldPositions(key).get(0).page;
				Rectangle signRect = form.getFieldPositions(key).get(0).position;
				float x = signRect.getLeft();
				float y = signRect.getBottom();
				// 根据路径读取图片
				System.out.println(imgpath+"----------------------------------");
				Image image = Image.getInstance(imgpath);
				// 获取图片页面
				PdfContentByte under = stamper.getOverContent(pageNo);
				// 图片大小自适应
				image.scaleToFit(400f, 220f);
				image.scaleAbsolute(178f, 65f); // 将图像缩放到绝对宽度和绝对高度。


				// 添加图片
				image.setAbsolutePosition(x, y);
				under.addImage(image);
			}
			stamper.setFormFlattening(true);// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
			stamper.close();
			Document doc = new Document();
			Font font = new Font(bf, 22);

			PdfCopy copy = new PdfCopy(doc, out);
			doc.open();
			PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
			copy.addPage(importPage);
			
			doc.close();

		} catch (IOException e) {
			System.out.println(e);
		} catch (DocumentException e) {
			System.out.println(e);
		}

	}
	public static void main(String[] args) {
        Map<String,String> map = new HashMap();
        map.put("title","PSTRI压力评估报告");
        map.put("usercode","101749");
        map.put("username","晴朗");
        map.put("gender","女");
        map.put("age","23");
        map.put("time","2020-06-30");
        map.put("result","压力过高");

        Map<String,String> map2 = new HashMap();
        map2.put("img","img195170e6f083d8.png");

        Map<String,Object> o=new HashMap();
        o.put("datemap",map);
        /* o.put("imgmap",map2);*/
        //pdfout(o);
        System.out.println(new File("src/static/template/mytest.pdf").getAbsolutePath());
        
    }

}