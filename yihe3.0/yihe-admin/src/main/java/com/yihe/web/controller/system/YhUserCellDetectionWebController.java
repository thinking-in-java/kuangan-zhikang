package com.yihe.web.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBodyFat;
import com.yihe.system.domain.YHUserEcg;
import com.yihe.system.domain.YhUserCellDetectionWeb;
import com.yihe.system.mapper.YhUserCellDetectionWebMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/YhUserCellDetectionWeb")
public class YhUserCellDetectionWebController {
    @Autowired
    YhUserCellDetectionWebMapper YhUserCellDetectionWebMapper;
    @ApiOperation(value = "分页查询")
    @ResponseBody
    @RequestMapping(value="/page",method= RequestMethod.GET)
    public Map<String,Object> queryUserEcg(String usercode, int page, int limit){
        //设置分页参数
        PageHelper.startPage(page,limit);
        //查询集合
        List<YhUserCellDetectionWeb> byusercode = YhUserCellDetectionWebMapper.findByusercode(usercode);
        PageInfo<YhUserCellDetectionWeb> yhUserCellDetectionWebPageInfo = new PageInfo<>(byusercode);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("data",yhUserCellDetectionWebPageInfo.getList());
        result .put("count", yhUserCellDetectionWebPageInfo.getTotal());
        result.put("msg", "");
        result.put("code", 20000);
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/addInfo",method= RequestMethod.GET)
    public Map<String,Object> addInfo(String usercode,String img,String resurtText,Integer cid,Integer isDel){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        if(null==cid){
            YhUserCellDetectionWeb record=new YhUserCellDetectionWeb();
            record.setImg(img);
            record.setUsercode(usercode);
            record.setCtime(df.format(new Date()));
            record.setIsdel(2);
            record.setResurtText(resurtText);
            Map<String,Object> result = new HashMap<String, Object>();
            result.put("data",YhUserCellDetectionWebMapper.insertSelective(record));
            result.put("msg", "");
            result.put("code", 20000);
            return result;
        }else{
            //修改
            if(null==isDel){
                YhUserCellDetectionWeb record=new YhUserCellDetectionWeb();
                record.setCid(cid);
                record.setImg(img);
                record.setUsercode(usercode);
                record.setResurtText(resurtText);
                Map<String,Object> result = new HashMap<String, Object>();
                result.put("data", YhUserCellDetectionWebMapper.updateByPrimaryKeySelective(record));
                result.put("msg", "");
                result.put("code", 20000);
                return result;
            }else{
                //删除
                YhUserCellDetectionWeb record=new YhUserCellDetectionWeb();
                record.setIsdel(1);
                record.setCid(cid);

                Map<String,Object> result = new HashMap<String, Object>();
                result.put("data",  YhUserCellDetectionWebMapper.updateByPrimaryKeySelective(record));
                result.put("msg", "");
                result.put("code", 20000);
                return result;
            }
        }

    }
}
