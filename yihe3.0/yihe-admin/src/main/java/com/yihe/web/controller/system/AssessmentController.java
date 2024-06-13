package com.yihe.web.controller.system;

import com.yihe.system.service.AssessmentService;
import com.yihe.system.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/YhAssessmentcontroller")
public class AssessmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssessmentController.class);
    @Resource
    AssessmentService assessmentservice;


    @RequestMapping("downloadExcel")
    public void downloadExcel(HttpServletResponse response, HttpServletRequest request) {
        try {
            // 将项目的文件转成了流
            InputStream storeName = this.getClass().getClassLoader()
                    .getResourceAsStream("static/template/import健康风险评估问卷-批量导入 自动评估模板.xls");
            // String storeName =
            // ResourceUtils.getFile("classpath:static/template/import用户档案新建.xls").getPath();
            // vnd.ms-excel浏览器返回的类型
            FileUtils.downloadFile(request, response, storeName, "vnd.ms-excel", "import健康风险评估问卷-批量导入 自动评估模板.xls");
        } catch (Exception ex) {
            LOGGER.error("用户管理-下载导入模板失败，原因是：", ex);
        }

    }


    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> importData(HttpServletRequest request) {
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("file");
        InputStream in = null;
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            in = file.getInputStream();
           result = assessmentservice.ajaxUploadExcel(in, file,request);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            result.put("msg", "导入异常！请联系管理员");
            try {
                in.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return result;

    }


    @RequestMapping(value = "/importTest", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> importTest(HttpServletRequest request) {
        MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
        MultipartFile file = multipart.getFile("file");
        InputStream in = null;
        Map<String, Object> result =new HashMap<>();
        try {
            in = file.getInputStream();
            result = assessmentservice.ajaxUploadExcel2(in, file,request);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            System.out.println("导入异常！请联系管理员");
            try {
                in.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return result;

    }




}
