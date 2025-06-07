package com.yihe.web.controller.tool;

import java.io.File;

public class HtmlToPdf {

    // 临时目录的路径
    public static final String TEMP_DIR_PATH = HtmlToPdf.class.getResource("/").getPath().substring(1) + "temp/";


    //wkhtmltopdf在windows系统中的路径
    //private static final String toPdfTool = "E:\\wk\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";
    //private static String toPdfTool = "D:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";
	//wkhtmltopdf在linux系统中的路径
    private static String toPdfTool ="wkhtmltopdf";

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
            System.out.println("如果pdf保存路径不存在，则创建路径");
            parent.mkdirs();
        }

        StringBuilder cmd = new StringBuilder();
        if(System.getProperty("os.name").indexOf("Windows") == -1){
        //非windows 系统

        }else{
            toPdfTool ="D:\\tool\\html\\wkhtmltopdf\\bin\\wkhtmltopdf";
        }

        cmd.append(toPdfTool);
        cmd.append(" ");
        cmd.append("--page-size A4 ") ;
        cmd.append(" --javascript-delay 2000") ;
        cmd.append("");//页眉下面的线
        cmd.append(" ");//页眉中间内容
        //cmd.append("  --margin-top 30mm ");//设置页面上边距 (default 10mm)
        cmd.append("");//    (设置页眉和内容的距离,默认0)
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(destPath);

        boolean result = true;
        try{
            System.out.println(cmd.toString());
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


    /**
     * html转pdf
     *
     * @param srcPath  html路径，可以是硬盘上的路径，也可以是网络路径
     * @param file pdf文件
     * @return 转换成功返回true
     */
    public static boolean convert2(String srcPath,String destPath) {
        File file = new File(destPath);
        File parent = file.getParentFile();
        // 如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }
        StringBuilder cmd = new StringBuilder();
        String toPdfTool;
        if (System.getProperty("os.name").indexOf("Windows") == -1) { // 根据系统
            // 非windows 系统
            toPdfTool = "//usr//bin//wkhtmltopdf";
        } else {
            toPdfTool = "D:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe";
        }
        // 这里可以拼接页眉页脚等参数 参数详情在上方
        cmd.append(toPdfTool);
        cmd.append(" ");
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(file.getAbsolutePath());

        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInterceptor error = new HtmlToPdfInterceptor(proc.getErrorStream());
            HtmlToPdfInterceptor output = new HtmlToPdfInterceptor(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        //HtmlToPdf.convert("http://localhost:8080/TestFtl2/hello", "d:/wkhtmltopdf.pdf");//网页
        //HtmlToPdf.convert("http://localhost/manage/getriskfactors?select1=10000&sex=0&befortime=2020-08-01 - 2020-08-31&ageStart=1&ageEnd=99&endtime=2020-09-01 - 2020-09-30&rick=true&disease=true&noworgcode=10000", "D:/home2/hetong/wkhtmltopdf.pdf");//本地
        System.out.println("...");
    }


}
