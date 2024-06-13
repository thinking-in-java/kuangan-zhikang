package com.yihe.system.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.mapper.TcmConstitutionIdentificationMapper;
import com.yihe.system.service.TcmConstitutionIdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: 贺平
 * @Date: 2021/11/1
 * @ClassName: com.yihe.system.service.impl
 * @Description:
 **/
@Service
public class TcmConstitutionIdentificationServiceImpl implements TcmConstitutionIdentificationService {

    @Autowired
    private TcmConstitutionIdentificationMapper tcmConstitutionIdentificationmapper;

    @Override
    public PageInfo<YhUserReginfo> tcmConstitutionIdentificationUserAll(Map<String,Object> map) {
        Integer pagenum = (Integer) map.get("pagenum");
        Integer pagesize = (Integer) map.get("pagesize");
        PageHelper.startPage(pagenum,pagesize);
        List<YhUserReginfo> yhUserReginfos = tcmConstitutionIdentificationmapper.tcmConstitutionIdentificationUserAll(map);
        if(null!=yhUserReginfos){
            for (int i = 0; i < yhUserReginfos.size(); i++) {
                //是
                String strIs="";
                //倾向是
                String tendencyIs="";
                if(null!=yhUserReginfos.get(i)&&null!=yhUserReginfos.get(i).getbIstype()){
                    if(1==yhUserReginfos.get(i).getbIstype()){
                        strIs+="气虚质;";
                    }else if(2==yhUserReginfos.get(i).getbIstype()){
                        tendencyIs+="气虚质;";
                    }
                }
                if(null!=yhUserReginfos.get(i)&&null!=yhUserReginfos.get(i).getcIstype()){
                    if(1==yhUserReginfos.get(i).getcIstype()){
                        strIs+="阳虚质;";
                    }else if(2==yhUserReginfos.get(i).getcIstype()){
                        tendencyIs+="阳虚质;";
                    }
                }

                if(null!=yhUserReginfos.get(i)&&null!=yhUserReginfos.get(i).getdIstype()){
                    if(1==yhUserReginfos.get(i).getdIstype()){
                        strIs+="阴虚质;";
                    }else if(2==yhUserReginfos.get(i).getdIstype()){
                        tendencyIs+="阴虚质;";
                    }
                }
                if(null!=yhUserReginfos.get(i)&&null!=yhUserReginfos.get(i).geteIstype()){
                    if(1==yhUserReginfos.get(i).geteIstype()){
                        strIs+="痰湿质;";
                    }else if(2==yhUserReginfos.get(i).geteIstype()){
                        tendencyIs+="痰湿质;";
                    }
                }

                if(null!=yhUserReginfos.get(i)&&null!=yhUserReginfos.get(i).getfIstype()){
                    if(1==yhUserReginfos.get(i).getfIstype()){
                        strIs+="湿热质;";
                    }else if(2==yhUserReginfos.get(i).getfIstype()){
                        tendencyIs+="湿热质;";
                    }
                }

                if(null!=yhUserReginfos.get(i)&&null!=yhUserReginfos.get(i).getgIstype()){
                    if(1==yhUserReginfos.get(i).getgIstype()){
                        strIs+="血瘀质;";
                    }else if(2==yhUserReginfos.get(i).getgIstype()){
                        tendencyIs+="血瘀质;";
                    }
                }

                if(null!=yhUserReginfos.get(i)&&null!=yhUserReginfos.get(i).gethIstype()){
                    if(1==yhUserReginfos.get(i).gethIstype()){
                        strIs+="气郁质;";
                    }else if(2==yhUserReginfos.get(i).gethIstype()){
                        tendencyIs+="气郁质;";
                    }
                }
                if(null!=yhUserReginfos.get(i)&&null!=yhUserReginfos.get(i).getiIstype()){
                    if(1==yhUserReginfos.get(i).getiIstype()){
                        strIs+="特禀质;";
                    }else if(2==yhUserReginfos.get(i).getiIstype()){
                        tendencyIs+="特禀质;";
                    }
                }


                yhUserReginfos.get(i).setStrIs(strIs);
                yhUserReginfos.get(i).setTendencyIs(tendencyIs);

            }
        }

        return new PageInfo<>(yhUserReginfos);
    }

    @Override
    public List<YhUserReginfo> tcmConstitutionIdentificationConditionUser() {
        return tcmConstitutionIdentificationmapper.tcmConstitutionIdentificationConditionUser();
    }

    @Override
    public String selectDoctorIdByUserCode(String userCode) {
        return tcmConstitutionIdentificationmapper.selectDoctorIdByUserCode(userCode);
    }
}
