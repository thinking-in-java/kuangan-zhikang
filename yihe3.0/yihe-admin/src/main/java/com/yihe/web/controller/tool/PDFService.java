package com.yihe.web.controller.tool;

import com.yihe.common.utils.StringUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;

public class PDFService {
    // 临时目录的路径
    public static final String TEMP_DIR_PATH = PDFService.class.getResource("/").getPath().substring(1) + "temp/";

    static {
        // 生成临时目录
        new File(TEMP_DIR_PATH).mkdirs();
    }

    public static void main(String[] args) throws Exception {
        htmlToPdf("www.baidu.com",  "d:\\1.pdf");
    }

    /**
     * 将HTML文件内容输出为PDF文件
     *
     * @param htmlFilePath HTML文件路径
     * @param pdfFilePath  PDF文件路径
     */
    public static boolean htmlToPdf(String htmlFilePath, String pdfFilePath) {
        boolean result = true;
        try {
            Process process = Runtime.getRuntime().exec(getCommand(htmlFilePath, TEMP_DIR_PATH));
            new Thread(new ClearBufferThread(process.getInputStream())).start();
            new Thread(new ClearBufferThread(process.getErrorStream())).start();
            process.waitFor();

        } catch (Exception e) {
            result = false;
            throw new RuntimeException(e);
        }
        return  result;
    }


    /**
     * 将HTML字符串转换为HTML文件
     *
     * @param htmlStr HTML字符串
     * @return HTML文件的绝对路径
     */
    public static String strToHtmlFile(String htmlStr) {
        OutputStream outputStream = null;
        try {
            String htmlFilePath = TEMP_DIR_PATH + UUID.randomUUID().toString() + ".html";
            outputStream = new FileOutputStream(htmlFilePath);
            outputStream.write(htmlStr.getBytes("UTF-8"));
            return htmlFilePath;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获得HTML转PDF的命令语句
     *
     * @param htmlFilePath HTML文件路径
     * @param pdfFilePath  PDF文件路径
     * @return HTML转PDF的命令语句
     */
    private static String getCommand(String htmlFilePath, String pdfFilePath) {
        String osName = System.getProperty("os.name");
        // Windows
        if (osName.startsWith("Windows")) {
            return String.format("D:/Program Files/wkhtmltopdf/bin/wkhtmltopdf.exe %s %s", htmlFilePath, pdfFilePath);
        }
        // Linux
        else {
            return String.format("wkhtmltopdf %s %s", htmlFilePath, pdfFilePath);
        }
    }

}

