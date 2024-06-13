package com.yihe.applet.api.util;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.File;

public class QiNiuUtil {
    public static String getUpToken(){
        Auth auth = Auth.create("nrUqYAKlixX71I3EwMZ14EzcgYcRZDD3yOD2TB_S", "4dOXiUFHu7uUlnndD0QBFfm2w97FeCJK2wAVi_eU");
        String uptoken = auth.uploadToken("shayuxcx");
        return uptoken;
    }

    public static String  upload(String localPath) {
        Configuration cfg = new Configuration(Zone.zone2());//设置华南的服务器
        UploadManager uploadManager = new UploadManager(cfg);
        File tmpFile=new File(localPath);
        String fileName=tmpFile.getName();
        String foodid = fileName.substring(0, fileName.lastIndexOf("."));
String foodImg="";
        String upToken = getUpToken();
        try {
            Response response = uploadManager.put(localPath, "addFoodImg/"+foodid, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            foodImg="http://xcxqny.yhy.ren/"+putRet.key;
            System.out.println("http://xcxqny.yhy.ren/"+putRet.key);
            System.out.println(putRet.hash);

        }catch (QiniuException e){
            e.printStackTrace();
        }
        return foodImg;
    }

    public static void main(String[] args) throws Exception{
        upload("E:\\foodImg\\92037.jpg");
    }
}
