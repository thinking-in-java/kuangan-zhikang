package com.yihe.web.controller.system;

import com.yihe.common.utils.file.FileUtils;
import com.yihe.system.domain.PeriodComparison;
import com.yihe.system.domain.YhDiseaseAssessTotalReport3web;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.YhPeriodComparisonService;
import com.yihe.system.utils.Result;
import com.yihe.web.controller.tool.HtmlToPdf;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/YhPeriodComparisonController")
public class YhPeriodComparisonController {
    @Autowired
    private YhPeriodComparisonService userService;



    @RequestMapping("/returnname")
    @ResponseBody
    public Result ghetarr(PeriodComparison model){
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1)+"static/template/";
        String time =  new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date().getTime());
        String fileName=time+"时间段对比分析";
        Subject subject = SecurityUtils.getSubject();
        YhUserReginfo yh=(YhUserReginfo) subject.getPrincipal();
            String url="select1="+model.getSelect1()+"&sex="+model.getSex()+"&befortime="+URLEncoder.encode(model.getBefortime())+"&ageStart="+model.getAgeStart()+"&ageEnd="+model.getAgeEnd()+"&endtime="+URLEncoder.encode(model.getEndtime())+"&rick="+(model.getRick()==null?"false":model.getRick())+"&disease="+(model.getDisease()==null?"false":model.getDisease())+"&noworgcode="+yh.getOrgCode();
        url="http://localhost/manage/getriskfactors?"+url;
        boolean convert = HtmlToPdf.convert(url, path+fileName+".pdf");//本地
        if(convert){
            return Result.ok(fileName);
        }else{
            return Result.error();
        }
    }


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        //这里文件名称是通过参数传递过来的，要是不需要，直接可以写在这里。String fileName = "a.doc";
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1)+"static/template/";
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {

            File file = new File(path, fileName);
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("GB2312"),"iso8859-1"));
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            FileUtils.deleteFile(path+fileName);
        }catch(FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("The file not found!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("/ceshi")

    public String ghetarra(){

        return "exportdata/timealysis";
    }

    public static void main(String[] args) {
        HtmlToPdf.convert("http://localhost/manage/getriskfactors?select1=10000&sex=0&befortime=2020-08-01%20-%202020-08-31&ageStart=1&ageEnd=99&endtime=2020-09-01%20-%202020-09-30&rick=true&disease=true&noworgcode=10000", "D:/home2/hetong/wkhtmltopdf.pdf");//本地
    }
}

