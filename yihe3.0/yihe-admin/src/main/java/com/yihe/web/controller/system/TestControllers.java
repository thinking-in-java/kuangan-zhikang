package com.yihe.web.controller.system;

import com.yihe.web.controller.tool.VerifyImageUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Controller
@RequestMapping("util/img")
public class TestControllers {
    @Autowired
    public HttpServletRequest request;

    @RequestMapping("get_img_verify")
    @ResponseBody
    public HashMap testImg() throws Exception {
        Map<String, byte[]> pictureMap;
        File templateFile;  //模板图片
        File targetFile;  //
        Random random = new Random();
        int templateNo = random.nextInt(4) + 1;
        int targetNo = random.nextInt(20) + 1;


        InputStream stream = getClass().getClassLoader().getResourceAsStream("static/template/123.png");
        templateFile = new File( "123.png");
        FileUtils.copyInputStreamToFile(stream, templateFile);

        stream = getClass().getClassLoader().getResourceAsStream("static/template/111.jpg");
        targetFile = new File("456.jpeg");
        FileUtils.copyInputStreamToFile(stream, targetFile);


        //获取抠图坐标
        HashMap xyMap = VerifyImageUtil.generateCutoutCoordinates();
        request.getSession().setAttribute("imgXyMap", xyMap);

        pictureMap = VerifyImageUtil.pictureTemplatesCut(templateFile, targetFile, "png", "png", Integer.valueOf(xyMap.get("X").toString()), Integer.valueOf(xyMap.get("Y").toString()));
        byte[] oriCopyImages = pictureMap.get("oriCopyImage");
        byte[] newImages = pictureMap.get("newImage");


        String newImage = Base64Utils.encodeToString(newImages);
        String oriCopyImage = Base64Utils.encodeToString(oriCopyImages);

        System.out.println(newImage);

        HashMap resultMap = new HashMap();
        resultMap.put("newImage", newImage);
        resultMap.put("oriCopyImage", oriCopyImage);
        resultMap.put("Y", xyMap.get("Y"));

        return resultMap;
    }

    /**
     * @description
     * @author SQW
     * @date 2019年5月5日
     */
    @PostMapping("check")
    @ResponseBody
    public Boolean javaPost(String X, String Y) {
        HashMap imgXyMap = (HashMap) request.getSession().getAttribute("imgXyMap");
        if (imgXyMap != null) {
            if (X.equals(imgXyMap.get("X").toString()) && Y.equals(imgXyMap.get("Y").toString())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}