/**
 * 
 */
package com.yihe.web.controller.tool;

import java.io.File;

/**
 * @author Administrator
 *
 */
public class PDFUtil {
	
	//wkhtmltopdf在windows系统中的路径
    private static final String toPdfTool = "E:\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";
	//private static final String toPdfTool = "D:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";
	//wkhtmltopdf在linux系统中的路径
	//private static final String toPdfTool ="//usr//bin//wkhtmltopdf";
    
    
	/**
     * html转pdf
     * @param srcPath html路径，可以是硬盘上的路径，也可以是网络路径
     * @param destPath pdf保存路径
     * @return 转换成功返回true
     */
    public static boolean convert(String srcPath, String destPath){
        File file = new File(destPath);
        File parent = file.getParentFile();
        //如果pdf保存路径不存在，则创建路径
        if(!parent.exists()){
            parent.mkdirs();
        }
        
        StringBuilder cmd = new StringBuilder();
        cmd.append(toPdfTool);
        cmd.append(" ");
        cmd.append("--page-size A4") ;
        cmd.append("");//页眉下面的线
        cmd.append(" ");//页眉中间内容
        //cmd.append("  --margin-top 30mm ");//设置页面上边距 (default 10mm) 
        cmd.append("");//    (设置页眉和内容的距离,默认0)
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(destPath);
        
        boolean result = true;
        try{
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
        }catch(Exception e){
            result = false;
            e.printStackTrace();
        }
        
        return result;
    }
    public static void main(String[] args) {
        //HtmlToPdf.convert("http://localhost:8080/TestFtl2/hello", "d:/wkhtmltopdf.pdf");//网页
    	PDFUtil.convert("http://localhost/manage/usertosee/sleepshow?id=378", "D:/home2/hetong/wkhtmltopdf.pdf");//本地
    }
}
