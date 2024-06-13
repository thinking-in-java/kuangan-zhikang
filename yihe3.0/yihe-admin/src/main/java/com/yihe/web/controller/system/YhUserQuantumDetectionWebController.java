package com.yihe.web.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserCellDetectionWeb;
import com.yihe.system.domain.YhUserQuantumDetectionWeb;
import com.yihe.system.mapper.YhUserQuantumDetectionWebMapper;
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
@RequestMapping("/YhUserQuantumDetectionWeb")
public class YhUserQuantumDetectionWebController {
    @Autowired
    YhUserQuantumDetectionWebMapper YhUserQuantumDetectionWebMapper;
    @ApiOperation(value = "分页查询")
    @ResponseBody
    @RequestMapping(value="/page",method= RequestMethod.GET)
    public Map<String,Object> queryUserEcg(String usercode, int page, int limit){
        //设置分页参数
        PageHelper.startPage(page,limit);
        //查询集合
        List<YhUserQuantumDetectionWeb> byusercode = YhUserQuantumDetectionWebMapper.findByusercode(usercode);
        PageInfo<YhUserQuantumDetectionWeb> yhUserCellDetectionWebPageInfo = new PageInfo<>(byusercode);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("data",yhUserCellDetectionWebPageInfo.getList());
        result .put("count", yhUserCellDetectionWebPageInfo.getTotal());
        result.put("msg", "");
        result.put("code", 20000);
        return result;
    }

    @ResponseBody
    @RequestMapping(value="/addInfo",method= RequestMethod.GET)
    public Map<String,Object> addInfo(String usercode,String img,Integer qid,Integer isDel,String resurtText){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        if(null==qid){
            YhUserQuantumDetectionWeb record=new YhUserQuantumDetectionWeb();
            record.setImg(img);
            record.setUsercode(usercode);
            record.setQtime(df.format(new Date()));
            record.setIsdel(2);
            record.setResurtText(resurtText);
            Map<String,Object> result = new HashMap<String, Object>();
            result.put("data",YhUserQuantumDetectionWebMapper.insertSelective(record));
            result.put("msg", "");
            result.put("code", 20000);
            return result;
        }else{
            //修改
            if(null==isDel){
                YhUserQuantumDetectionWeb record=new YhUserQuantumDetectionWeb();
                record.setQid(qid);
                record.setImg(img);
                record.setUsercode(usercode);
                record.setResurtText(resurtText);
                Map<String,Object> result = new HashMap<String, Object>();
                result.put("data", YhUserQuantumDetectionWebMapper.updateByPrimaryKeySelective(record));
                result.put("msg", "");
                result.put("code", 20000);
                return result;
            }else{
                //删除
                YhUserQuantumDetectionWeb record=new YhUserQuantumDetectionWeb();
                record.setIsdel(1);
                record.setQid(qid);
                Map<String,Object> result = new HashMap<String, Object>();
                result.put("data",  YhUserQuantumDetectionWebMapper.updateByPrimaryKeySelective(record));
                result.put("msg", "");
                result.put("code", 20000);
                return result;
            }
        }

    }
}
