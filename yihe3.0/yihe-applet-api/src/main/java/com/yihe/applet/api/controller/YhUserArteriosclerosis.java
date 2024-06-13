package com.yihe.applet.api.controller;

import com.yihe.applet.api.domain.YhUserLiverfunctionApp;
import com.yihe.applet.api.domain.YhUserReginfoApp;
import com.yihe.applet.api.service.YhUserReginfoAppService;
import com.yihe.system.domain.YhUserArtery;
import com.yihe.system.service.YhUserArteryService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 数据监测动脉硬化 Created by dyb on 2019/12/19.
 */
@Controller
@RequestMapping("/api/YhUserArtery")
@Api(value = "动脉硬化控制器", description = "动脉硬化管理")
public class YhUserArteriosclerosis {
    @Autowired
    YhUserArteryService yhUserArteryService; //动脉硬化
    @Autowired
    YhUserReginfoAppService YhUserReginfoService;

    @RequestMapping(value = "/dataAnalysis")
    @ResponseBody
    public Result dataAnalysis(String usercode, Integer timeType, String time) throws java.text.ParseException {
        Map<String, Object> map = new HashMap<>();
        Set<String> timeList = new HashSet<>();
        YhUserReginfoApp info = YhUserReginfoService.findYhUserReginfo(usercode);
        double k = 0;
        if(0==timeType) {
            String[] split = time.split("~");

            List<YhUserArtery> list = yhUserArteryService.findArteryAllByUserCode(usercode,timeType,split[0],split[1]);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserArtery newInfoByUserCode = list.get(i);
                    String entryTime = newInfoByUserCode.getTime();

                    String dateString =  transferFormat2(entryTime);
                    timeList.add(dateString);
                    int ageByBirth = getAgeByBirth(info.getBirthDate());
                    Integer avi = newInfoByUserCode.getAvi();
                    int aviStatus = 0;
                    Integer api = newInfoByUserCode.getApi();
                    int apiStatus = 0;
                    int avizhibiao = 0;
                    int apizhibiao = 0;

                    if (null != avi && null != api) {

                        if (ageByBirth <= 23) {
                            avizhibiao = 15;
                        } else if (ageByBirth >= 24 && ageByBirth <= 26) {
                            avizhibiao = 16;
                        } else if (ageByBirth >= 27 && ageByBirth <= 28) {
                            avizhibiao = 17;
                        } else if (ageByBirth >= 29 && ageByBirth <= 35) {
                            avizhibiao = 18;
                        } else if (ageByBirth >= 36 && ageByBirth <= 37) {
                            avizhibiao = 19;
                        } else if (ageByBirth >= 38 && ageByBirth <= 40) {
                            avizhibiao = 20;
                        } else if (ageByBirth >= 41 && ageByBirth <= 43) {
                            avizhibiao = 21;
                        } else if (ageByBirth >= 44 && ageByBirth <= 46) {
                            avizhibiao = 22;
                        } else if (ageByBirth >= 47 && ageByBirth <= 49) {
                            avizhibiao = 23;
                        } else if (ageByBirth >= 50 && ageByBirth <= 52) {
                            avizhibiao = 24;
                        } else if (ageByBirth >= 53 && ageByBirth <= 54) {
                            avizhibiao = 25;
                        } else if (ageByBirth >= 55 && ageByBirth <= 57) {
                            avizhibiao = 26;
                        } else if (ageByBirth >= 58 && ageByBirth <= 59) {
                            avizhibiao = 27;
                        } else if (ageByBirth >= 60 && ageByBirth <= 61) {
                            avizhibiao = 28;
                        } else if (ageByBirth >= 62 && ageByBirth <= 63) {
                            avizhibiao = 29;
                        } else if (ageByBirth >= 64 && ageByBirth <= 65) {
                            avizhibiao = 30;
                        } else if (ageByBirth >= 66 && ageByBirth <= 67) {
                            avizhibiao = 31;
                        } else if (ageByBirth >= 68 && ageByBirth <= 69) {
                            avizhibiao = 32;
                        } else if (ageByBirth >= 70 && ageByBirth <= 72) {
                            avizhibiao = 33;
                        } else if (ageByBirth == 73) {
                            avizhibiao = 34;
                        } else if (ageByBirth >= 74 && ageByBirth <= 75) {
                            avizhibiao = 35;
                        } else if (ageByBirth == 76) {
                            avizhibiao = 36;
                        } else if (ageByBirth >= 77 && ageByBirth <= 78) {
                            avizhibiao = 37;
                        } else if (ageByBirth == 79) {
                            avizhibiao = 38;
                        } else if (ageByBirth > 80) {
                            avizhibiao = 39;
                        }

                        if (avi > avizhibiao && avi < (avizhibiao * 0.2)) {
                            aviStatus = 1;
                        }
                        if (avi >= (avizhibiao * 0.2) && avi <= (avizhibiao * 0.5)) {
                            aviStatus = 2;
                        }
                        if (avi > (avizhibiao * 0.5)) {
                            aviStatus = 3;
                        }


                        if (ageByBirth <= 26) {
                            apizhibiao = 22;
                        } else if (ageByBirth >= 27 && ageByBirth <= 32) {
                            apizhibiao = 23;
                        } else if (ageByBirth >= 33 && ageByBirth <= 37) {
                            apizhibiao = 24;
                        } else if (ageByBirth >= 38 && ageByBirth <= 43) {
                            apizhibiao = 25;
                        } else if (ageByBirth >= 44 && ageByBirth <= 47) {
                            apizhibiao = 26;
                        } else if (ageByBirth >= 48 && ageByBirth <= 54) {
                            apizhibiao = 27;
                        } else if (ageByBirth >= 55 && ageByBirth <= 58) {
                            apizhibiao = 28;
                        } else if (ageByBirth >= 59 && ageByBirth <= 62) {
                            apizhibiao = 29;
                        } else if (ageByBirth >= 63 && ageByBirth <= 66) {
                            apizhibiao = 30;
                        } else if (ageByBirth >= 67 && ageByBirth <= 71) {
                            apizhibiao = 31;
                        } else if (ageByBirth >= 72 && ageByBirth <= 77) {
                            apizhibiao = 32;
                        } else if (ageByBirth >= 78 && ageByBirth <= 79) {
                            apizhibiao = 33;
                        } else if (ageByBirth >= 80) {
                            apizhibiao = 33;
                        }
                        if (api > apizhibiao && avi < (apizhibiao * 0.2)) {
                            apiStatus = 1;
                        }
                        if (api >= (apizhibiao * 0.2) && api <= (apizhibiao * 0.5)) {
                            apiStatus = 2;
                        }
                        if (api > (apizhibiao * 0.5)) {
                            apiStatus = 3;
                        }

                        if (apiStatus == 0 && aviStatus == 0) {
                            k++;
                        }


                    }
                }

                map.put("dayNum", timeList.size());
                map.put("size", list.size());
                double j = k / list.size() * 100;
                map.put("avg", Math.round(j));

            }
        }else{

            List<YhUserArtery> list = yhUserArteryService.findArteryAllByUserCode(usercode,timeType,time,null);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserArtery newInfoByUserCode = list.get(i);
                    String entryTime = newInfoByUserCode.getTime();

                    String dateString =  transferFormat2(entryTime);
                    timeList.add(dateString);
                    int ageByBirth = getAgeByBirth(info.getBirthDate());
                Integer avi = newInfoByUserCode.getAvi();
                int aviStatus = 0;
                Integer api = newInfoByUserCode.getApi();
                int apiStatus = 0;
                int avizhibiao = 0;
                int apizhibiao = 0;

                if (null != avi && null != api) {

                    if (ageByBirth <= 23) {
                        avizhibiao = 15;
                    } else if (ageByBirth >= 24 && ageByBirth <= 26) {
                        avizhibiao = 16;
                    } else if (ageByBirth >= 27 && ageByBirth <= 28) {
                        avizhibiao = 17;
                    } else if (ageByBirth >= 29 && ageByBirth <= 35) {
                        avizhibiao = 18;
                    } else if (ageByBirth >= 36 && ageByBirth <= 37) {
                        avizhibiao = 19;
                    } else if (ageByBirth >= 38 && ageByBirth <= 40) {
                        avizhibiao = 20;
                    } else if (ageByBirth >= 41 && ageByBirth <= 43) {
                        avizhibiao = 21;
                    } else if (ageByBirth >= 44 && ageByBirth <= 46) {
                        avizhibiao = 22;
                    } else if (ageByBirth >= 47 && ageByBirth <= 49) {
                        avizhibiao = 23;
                    } else if (ageByBirth >= 50 && ageByBirth <= 52) {
                        avizhibiao = 24;
                    } else if (ageByBirth >= 53 && ageByBirth <= 54) {
                        avizhibiao = 25;
                    } else if (ageByBirth >= 55 && ageByBirth <= 57) {
                        avizhibiao = 26;
                    } else if (ageByBirth >= 58 && ageByBirth <= 59) {
                        avizhibiao = 27;
                    } else if (ageByBirth >= 60 && ageByBirth <= 61) {
                        avizhibiao = 28;
                    } else if (ageByBirth >= 62 && ageByBirth <= 63) {
                        avizhibiao = 29;
                    } else if (ageByBirth >= 64 && ageByBirth <= 65) {
                        avizhibiao = 30;
                    } else if (ageByBirth >= 66 && ageByBirth <= 67) {
                        avizhibiao = 31;
                    } else if (ageByBirth >= 68 && ageByBirth <= 69) {
                        avizhibiao = 32;
                    } else if (ageByBirth >= 70 && ageByBirth <= 72) {
                        avizhibiao = 33;
                    } else if (ageByBirth == 73) {
                        avizhibiao = 34;
                    } else if (ageByBirth >= 74 && ageByBirth <= 75) {
                        avizhibiao = 35;
                    } else if (ageByBirth == 76) {
                        avizhibiao = 36;
                    } else if (ageByBirth >= 77 && ageByBirth <= 78) {
                        avizhibiao = 37;
                    } else if (ageByBirth == 79) {
                        avizhibiao = 38;
                    } else if (ageByBirth > 80) {
                        avizhibiao = 39;
                    }

                    if (avi > avizhibiao && avi < (avizhibiao * 0.2)) {
                        aviStatus = 1;
                    }
                    if (avi >= (avizhibiao * 0.2) && avi <= (avizhibiao * 0.5)) {
                        aviStatus = 2;
                    }
                    if (avi > (avizhibiao * 0.5)) {
                        aviStatus = 3;
                    }


                    if (ageByBirth <= 26) {
                        apizhibiao = 22;
                    } else if (ageByBirth >= 27 && ageByBirth <= 32) {
                        apizhibiao = 23;
                    } else if (ageByBirth >= 33 && ageByBirth <= 37) {
                        apizhibiao = 24;
                    } else if (ageByBirth >= 38 && ageByBirth <= 43) {
                        apizhibiao = 25;
                    } else if (ageByBirth >= 44 && ageByBirth <= 47) {
                        apizhibiao = 26;
                    } else if (ageByBirth >= 48 && ageByBirth <= 54) {
                        apizhibiao = 27;
                    } else if (ageByBirth >= 55 && ageByBirth <= 58) {
                        apizhibiao = 28;
                    } else if (ageByBirth >= 59 && ageByBirth <= 62) {
                        apizhibiao = 29;
                    } else if (ageByBirth >= 63 && ageByBirth <= 66) {
                        apizhibiao = 30;
                    } else if (ageByBirth >= 67 && ageByBirth <= 71) {
                        apizhibiao = 31;
                    } else if (ageByBirth >= 72 && ageByBirth <= 77) {
                        apizhibiao = 32;
                    } else if (ageByBirth >= 78 && ageByBirth <= 79) {
                        apizhibiao = 33;
                    } else if (ageByBirth >= 80) {
                        apizhibiao = 33;
                    }
                    if (api > apizhibiao && avi < (apizhibiao * 0.2)) {
                        apiStatus = 1;
                    }
                    if (api >= (apizhibiao * 0.2) && api <= (apizhibiao * 0.5)) {
                        apiStatus = 2;
                    }
                    if (api > (apizhibiao * 0.5)) {
                        apiStatus = 3;
                    }

                    if (apiStatus == 0 && aviStatus == 0) {
                        k++;
                    }


                }
            }

                map.put("dayNum", timeList.size());
                map.put("size", list.size());
                double j = k / list.size() * 100;
                map.put("avg", Math.round(j));

            }
        }

        return Result.ok(map);
    }


    @RequestMapping(value = "/pieChartAssignment")
    @ResponseBody
    public Result pieChartAssignment(String usercode, Integer timetype, String time) {
        YhUserReginfoApp info = YhUserReginfoService.findYhUserReginfo(usercode);
        int zhengchang = 0;
        int qingdu = 0;
        int zhongdu = 0;
        int zhongzdu = 0;
        if (0 == timetype) {
            String[] split = time.split("~");
            List<YhUserArtery> list = yhUserArteryService.findArteryAllByUserCode(usercode, timetype, split[0], split[1]);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserArtery newInfoByUserCode = list.get(i);
                    int ageByBirth = getAgeByBirth(info.getBirthDate());
                    Integer avi = newInfoByUserCode.getAvi();
                    int aviStatus = 0;
                    Integer api = newInfoByUserCode.getApi();
                    int apiStatus = 0;
                    int avizhibiao = 0;
                    int apizhibiao = 0;
                    if (null != avi && null != api) {
                        if (ageByBirth <= 23) {
                            avizhibiao = 15;
                        } else if (ageByBirth >= 24 && ageByBirth <= 26) {
                            avizhibiao = 16;
                        } else if (ageByBirth >= 27 && ageByBirth <= 28) {
                            avizhibiao = 17;
                        } else if (ageByBirth >= 29 && ageByBirth <= 35) {
                            avizhibiao = 18;
                        } else if (ageByBirth >= 36 && ageByBirth <= 37) {
                            avizhibiao = 19;
                        } else if (ageByBirth >= 38 && ageByBirth <= 40) {
                            avizhibiao = 20;
                        } else if (ageByBirth >= 41 && ageByBirth <= 43) {
                            avizhibiao = 21;
                        } else if (ageByBirth >= 44 && ageByBirth <= 46) {
                            avizhibiao = 22;
                        } else if (ageByBirth >= 47 && ageByBirth <= 49) {
                            avizhibiao = 23;
                        } else if (ageByBirth >= 50 && ageByBirth <= 52) {
                            avizhibiao = 24;
                        } else if (ageByBirth >= 53 && ageByBirth <= 54) {
                            avizhibiao = 25;
                        } else if (ageByBirth >= 55 && ageByBirth <= 57) {
                            avizhibiao = 26;
                        } else if (ageByBirth >= 58 && ageByBirth <= 59) {
                            avizhibiao = 27;
                        } else if (ageByBirth >= 60 && ageByBirth <= 61) {
                            avizhibiao = 28;
                        } else if (ageByBirth >= 62 && ageByBirth <= 63) {
                            avizhibiao = 29;
                        } else if (ageByBirth >= 64 && ageByBirth <= 65) {
                            avizhibiao = 30;
                        } else if (ageByBirth >= 66 && ageByBirth <= 67) {
                            avizhibiao = 31;
                        } else if (ageByBirth >= 68 && ageByBirth <= 69) {
                            avizhibiao = 32;
                        } else if (ageByBirth >= 70 && ageByBirth <= 72) {
                            avizhibiao = 33;
                        } else if (ageByBirth == 73) {
                            avizhibiao = 34;
                        } else if (ageByBirth >= 74 && ageByBirth <= 75) {
                            avizhibiao = 35;
                        } else if (ageByBirth == 76) {
                            avizhibiao = 36;
                        } else if (ageByBirth >= 77 && ageByBirth <= 78) {
                            avizhibiao = 37;
                        } else if (ageByBirth == 79) {
                            avizhibiao = 38;
                        } else if (ageByBirth > 80) {
                            avizhibiao = 39;
                        }

                        if (avi > avizhibiao && avi < (avizhibiao * 0.2)) {
                            aviStatus = 1;
                        }
                        if (avi >= (avizhibiao * 0.2) && avi <= (avizhibiao * 0.5)) {
                            aviStatus = 2;
                        }
                        if (avi > (avizhibiao * 0.5)) {
                            aviStatus = 3;
                        }
                        if (ageByBirth <= 26) {
                            apizhibiao = 22;
                        } else if (ageByBirth >= 27 && ageByBirth <= 32) {
                            apizhibiao = 23;
                        } else if (ageByBirth >= 33 && ageByBirth <= 37) {
                            apizhibiao = 24;
                        } else if (ageByBirth >= 38 && ageByBirth <= 43) {
                            apizhibiao = 25;
                        } else if (ageByBirth >= 44 && ageByBirth <= 47) {
                            apizhibiao = 26;
                        } else if (ageByBirth >= 48 && ageByBirth <= 54) {
                            apizhibiao = 27;
                        } else if (ageByBirth >= 55 && ageByBirth <= 58) {
                            apizhibiao = 28;
                        } else if (ageByBirth >= 59 && ageByBirth <= 62) {
                            apizhibiao = 29;
                        } else if (ageByBirth >= 63 && ageByBirth <= 66) {
                            apizhibiao = 30;
                        } else if (ageByBirth >= 67 && ageByBirth <= 71) {
                            apizhibiao = 31;
                        } else if (ageByBirth >= 72 && ageByBirth <= 77) {
                            apizhibiao = 32;
                        } else if (ageByBirth >= 78 && ageByBirth <= 79) {
                            apizhibiao = 33;
                        } else if (ageByBirth >= 80) {
                            apizhibiao = 33;
                        }
                        if (api > apizhibiao && avi < (apizhibiao * 0.2)) {
                            apiStatus = 1;
                        }
                        if (api >= (apizhibiao * 0.2) && api <= (apizhibiao * 0.5)) {
                            apiStatus = 2;
                        }
                        if (api > (apizhibiao * 0.5)) {
                            apiStatus = 3;
                        }

                       if(aviStatus>=apiStatus){
                           if(0==aviStatus){
                               zhengchang++;
                           }
                           if(1==aviStatus){
                               qingdu++;
                           }
                           if(2==aviStatus){
                               zhongdu++;
                           }
                           if(3==aviStatus){
                               zhongzdu++;
                           }
                       }else{
                           if(0==apiStatus){
                               zhengchang++;
                           }
                           if(1==apiStatus){
                               qingdu++;
                           }
                           if(2==apiStatus){
                               zhongdu++;
                           }
                           if(3==apiStatus){
                               zhongzdu++;
                           }
                       }
                    }
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("qingdu", qingdu);
            map.put("zhongdu", zhongdu);
            map.put("zhongzdu", zhongzdu);
            map.put("zhengchang", zhengchang);
            return Result.ok(map);
        } else if (1 == timetype) {

            List<YhUserArtery> list = yhUserArteryService.findArteryAllByUserCode(usercode, timetype, time, null);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserArtery newInfoByUserCode = list.get(i);
                    int ageByBirth = getAgeByBirth(info.getBirthDate());
                    Integer avi = newInfoByUserCode.getAvi();
                    int aviStatus = 0;
                    Integer api = newInfoByUserCode.getApi();
                    int apiStatus = 0;
                    int avizhibiao = 0;
                    int apizhibiao = 0;
                    if (null != avi && null != api) {
                        if (ageByBirth <= 23) {
                            avizhibiao = 15;
                        } else if (ageByBirth >= 24 && ageByBirth <= 26) {
                            avizhibiao = 16;
                        } else if (ageByBirth >= 27 && ageByBirth <= 28) {
                            avizhibiao = 17;
                        } else if (ageByBirth >= 29 && ageByBirth <= 35) {
                            avizhibiao = 18;
                        } else if (ageByBirth >= 36 && ageByBirth <= 37) {
                            avizhibiao = 19;
                        } else if (ageByBirth >= 38 && ageByBirth <= 40) {
                            avizhibiao = 20;
                        } else if (ageByBirth >= 41 && ageByBirth <= 43) {
                            avizhibiao = 21;
                        } else if (ageByBirth >= 44 && ageByBirth <= 46) {
                            avizhibiao = 22;
                        } else if (ageByBirth >= 47 && ageByBirth <= 49) {
                            avizhibiao = 23;
                        } else if (ageByBirth >= 50 && ageByBirth <= 52) {
                            avizhibiao = 24;
                        } else if (ageByBirth >= 53 && ageByBirth <= 54) {
                            avizhibiao = 25;
                        } else if (ageByBirth >= 55 && ageByBirth <= 57) {
                            avizhibiao = 26;
                        } else if (ageByBirth >= 58 && ageByBirth <= 59) {
                            avizhibiao = 27;
                        } else if (ageByBirth >= 60 && ageByBirth <= 61) {
                            avizhibiao = 28;
                        } else if (ageByBirth >= 62 && ageByBirth <= 63) {
                            avizhibiao = 29;
                        } else if (ageByBirth >= 64 && ageByBirth <= 65) {
                            avizhibiao = 30;
                        } else if (ageByBirth >= 66 && ageByBirth <= 67) {
                            avizhibiao = 31;
                        } else if (ageByBirth >= 68 && ageByBirth <= 69) {
                            avizhibiao = 32;
                        } else if (ageByBirth >= 70 && ageByBirth <= 72) {
                            avizhibiao = 33;
                        } else if (ageByBirth == 73) {
                            avizhibiao = 34;
                        } else if (ageByBirth >= 74 && ageByBirth <= 75) {
                            avizhibiao = 35;
                        } else if (ageByBirth == 76) {
                            avizhibiao = 36;
                        } else if (ageByBirth >= 77 && ageByBirth <= 78) {
                            avizhibiao = 37;
                        } else if (ageByBirth == 79) {
                            avizhibiao = 38;
                        } else if (ageByBirth > 80) {
                            avizhibiao = 39;
                        }

                        if (avi > avizhibiao && avi < (avizhibiao * 0.2)) {
                            aviStatus = 1;
                        }
                        if (avi >= (avizhibiao * 0.2) && avi <= (avizhibiao * 0.5)) {
                            aviStatus = 2;
                        }
                        if (avi > (avizhibiao * 0.5)) {
                            aviStatus = 3;
                        }
                        if (ageByBirth <= 26) {
                            apizhibiao = 22;
                        } else if (ageByBirth >= 27 && ageByBirth <= 32) {
                            apizhibiao = 23;
                        } else if (ageByBirth >= 33 && ageByBirth <= 37) {
                            apizhibiao = 24;
                        } else if (ageByBirth >= 38 && ageByBirth <= 43) {
                            apizhibiao = 25;
                        } else if (ageByBirth >= 44 && ageByBirth <= 47) {
                            apizhibiao = 26;
                        } else if (ageByBirth >= 48 && ageByBirth <= 54) {
                            apizhibiao = 27;
                        } else if (ageByBirth >= 55 && ageByBirth <= 58) {
                            apizhibiao = 28;
                        } else if (ageByBirth >= 59 && ageByBirth <= 62) {
                            apizhibiao = 29;
                        } else if (ageByBirth >= 63 && ageByBirth <= 66) {
                            apizhibiao = 30;
                        } else if (ageByBirth >= 67 && ageByBirth <= 71) {
                            apizhibiao = 31;
                        } else if (ageByBirth >= 72 && ageByBirth <= 77) {
                            apizhibiao = 32;
                        } else if (ageByBirth >= 78 && ageByBirth <= 79) {
                            apizhibiao = 33;
                        } else if (ageByBirth >= 80) {
                            apizhibiao = 33;
                        }
                        if (api > apizhibiao && avi < (apizhibiao * 0.2)) {
                            apiStatus = 1;
                        }
                        if (api >= (apizhibiao * 0.2) && api <= (apizhibiao * 0.5)) {
                            apiStatus = 2;
                        }
                        if (api > (apizhibiao * 0.5)) {
                            apiStatus = 3;
                        }

                        if(aviStatus>=apiStatus){
                            if(0==aviStatus){
                                zhengchang++;
                            }
                            if(1==aviStatus){
                                qingdu++;
                            }
                            if(2==aviStatus){
                                zhongdu++;
                            }
                            if(3==aviStatus){
                                zhongzdu++;
                            }
                        }else{
                            if(0==apiStatus){
                                zhengchang++;
                            }
                            if(1==apiStatus){
                                qingdu++;
                            }
                            if(2==apiStatus){
                                zhongdu++;
                            }
                            if(3==apiStatus){
                                zhongzdu++;
                            }
                        }
                    }
                }
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("qingdu", qingdu);
            map.put("zhongdu", zhongdu);
            map.put("zhongzdu", zhongzdu);
            map.put("zhengchang", zhengchang);
            return Result.ok(map);
        } else if (2 == timetype) {

            List<YhUserArtery> list = yhUserArteryService.findArteryAllByUserCode(usercode, timetype, time, null);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    YhUserArtery newInfoByUserCode = list.get(i);
                    int ageByBirth = getAgeByBirth(info.getBirthDate());
                    Integer avi = newInfoByUserCode.getAvi();
                    int aviStatus = 0;
                    Integer api = newInfoByUserCode.getApi();
                    int apiStatus = 0;
                    int avizhibiao = 0;
                    int apizhibiao = 0;
                    if (null != avi && null != api) {
                        if (ageByBirth <= 23) {
                            avizhibiao = 15;
                        } else if (ageByBirth >= 24 && ageByBirth <= 26) {
                            avizhibiao = 16;
                        } else if (ageByBirth >= 27 && ageByBirth <= 28) {
                            avizhibiao = 17;
                        } else if (ageByBirth >= 29 && ageByBirth <= 35) {
                            avizhibiao = 18;
                        } else if (ageByBirth >= 36 && ageByBirth <= 37) {
                            avizhibiao = 19;
                        } else if (ageByBirth >= 38 && ageByBirth <= 40) {
                            avizhibiao = 20;
                        } else if (ageByBirth >= 41 && ageByBirth <= 43) {
                            avizhibiao = 21;
                        } else if (ageByBirth >= 44 && ageByBirth <= 46) {
                            avizhibiao = 22;
                        } else if (ageByBirth >= 47 && ageByBirth <= 49) {
                            avizhibiao = 23;
                        } else if (ageByBirth >= 50 && ageByBirth <= 52) {
                            avizhibiao = 24;
                        } else if (ageByBirth >= 53 && ageByBirth <= 54) {
                            avizhibiao = 25;
                        } else if (ageByBirth >= 55 && ageByBirth <= 57) {
                            avizhibiao = 26;
                        } else if (ageByBirth >= 58 && ageByBirth <= 59) {
                            avizhibiao = 27;
                        } else if (ageByBirth >= 60 && ageByBirth <= 61) {
                            avizhibiao = 28;
                        } else if (ageByBirth >= 62 && ageByBirth <= 63) {
                            avizhibiao = 29;
                        } else if (ageByBirth >= 64 && ageByBirth <= 65) {
                            avizhibiao = 30;
                        } else if (ageByBirth >= 66 && ageByBirth <= 67) {
                            avizhibiao = 31;
                        } else if (ageByBirth >= 68 && ageByBirth <= 69) {
                            avizhibiao = 32;
                        } else if (ageByBirth >= 70 && ageByBirth <= 72) {
                            avizhibiao = 33;
                        } else if (ageByBirth == 73) {
                            avizhibiao = 34;
                        } else if (ageByBirth >= 74 && ageByBirth <= 75) {
                            avizhibiao = 35;
                        } else if (ageByBirth == 76) {
                            avizhibiao = 36;
                        } else if (ageByBirth >= 77 && ageByBirth <= 78) {
                            avizhibiao = 37;
                        } else if (ageByBirth == 79) {
                            avizhibiao = 38;
                        } else if (ageByBirth > 80) {
                            avizhibiao = 39;
                        }

                        if (avi > avizhibiao && avi < (avizhibiao * 0.2)) {
                            aviStatus = 1;
                        }
                        if (avi >= (avizhibiao * 0.2) && avi <= (avizhibiao * 0.5)) {
                            aviStatus = 2;
                        }
                        if (avi > (avizhibiao * 0.5)) {
                            aviStatus = 3;
                        }
                        if (ageByBirth <= 26) {
                            apizhibiao = 22;
                        } else if (ageByBirth >= 27 && ageByBirth <= 32) {
                            apizhibiao = 23;
                        } else if (ageByBirth >= 33 && ageByBirth <= 37) {
                            apizhibiao = 24;
                        } else if (ageByBirth >= 38 && ageByBirth <= 43) {
                            apizhibiao = 25;
                        } else if (ageByBirth >= 44 && ageByBirth <= 47) {
                            apizhibiao = 26;
                        } else if (ageByBirth >= 48 && ageByBirth <= 54) {
                            apizhibiao = 27;
                        } else if (ageByBirth >= 55 && ageByBirth <= 58) {
                            apizhibiao = 28;
                        } else if (ageByBirth >= 59 && ageByBirth <= 62) {
                            apizhibiao = 29;
                        } else if (ageByBirth >= 63 && ageByBirth <= 66) {
                            apizhibiao = 30;
                        } else if (ageByBirth >= 67 && ageByBirth <= 71) {
                            apizhibiao = 31;
                        } else if (ageByBirth >= 72 && ageByBirth <= 77) {
                            apizhibiao = 32;
                        } else if (ageByBirth >= 78 && ageByBirth <= 79) {
                            apizhibiao = 33;
                        } else if (ageByBirth >= 80) {
                            apizhibiao = 33;
                        }
                        if (api > apizhibiao && avi < (apizhibiao * 0.2)) {
                            apiStatus = 1;
                        }
                        if (api >= (apizhibiao * 0.2) && api <= (apizhibiao * 0.5)) {
                            apiStatus = 2;
                        }
                        if (api > (apizhibiao * 0.5)) {
                            apiStatus = 3;
                        }

                        if(aviStatus>=apiStatus){
                            if(0==aviStatus){
                                zhengchang++;
                            }
                            if(1==aviStatus){
                                qingdu++;
                            }
                            if(2==aviStatus){
                                zhongdu++;
                            }
                            if(3==aviStatus){
                                zhongzdu++;
                            }
                        }else{
                            if(0==apiStatus){
                                zhengchang++;
                            }
                            if(1==apiStatus){
                                qingdu++;
                            }
                            if(2==apiStatus){
                                zhongdu++;
                            }
                            if(3==apiStatus){
                                zhongzdu++;
                            }
                        }
                    }
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("qingdu", qingdu);
            map.put("zhongdu", zhongdu);
            map.put("zhongzdu", zhongzdu);
            map.put("zhengchang", zhengchang);
            return Result.ok(map);
        }
        return Result.error();
    }

    /**
     * 新增记录
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/addInfo")
    @ResponseBody
    public Result addInfo(YhUserArtery record, HttpServletRequest request) {
        System.out.println(record);
        // 修改
        if (null != record.getId() && 0 != record.getId()) {
            record.setTime(null);
            int updateByExampleSelective = yhUserArteryService.updateByPrimaryKeySelective(record);
            return Result.ok(updateByExampleSelective);
        } else {
            record.setIsDel(2);
            int insertSelective = yhUserArteryService.insertSelective(record);
            if (insertSelective > 0) {
                return Result.ok(insertSelective);
            }
            return Result.error();
        }

    }
    /**
     * 查询最新肝功能
     *
     * @param record
     * @return
     */
    @RequestMapping(value = "/findLiverfunctionNew")
    @ResponseBody
    public Result findLiverfunctionNew(String eid) {
        YhUserArtery selectByPrimaryKeyNewest7 = yhUserArteryService.selectByPrimaryKeyNewest(eid);// 肝功能
        return Result.ok(selectByPrimaryKeyNewest7);
    }

    /**
     * 根据主键查询肝功能信息
     */
    @RequestMapping(value = "/findInfoByKey")
    @ResponseBody
    public Result findInfoByKey(Integer id) {
        if (null == id) {
            return Result.error();
        }

        return Result.ok(yhUserArteryService.selectByPrimaryKey(id));
    }


    /**
     * 查询已经录入的日期集合selectByPrimaryKeyFindEntryTime
     */

    @RequestMapping(value = "/selectByPrimaryKeyFindEntryTime")
    @ResponseBody
    public Result selectByPrimaryKeyFindEntryTime(String usercode) throws ParseException{
        if (null == usercode) {
            Result.error();
        }

        List<YhUserArtery> selectByPrimaryKeyFindEntryTime = yhUserArteryService
                .selectByPrimaryKeyFindEntryTime(usercode);
        for (int i = 0; i < selectByPrimaryKeyFindEntryTime.size(); i++) {
            String time = selectByPrimaryKeyFindEntryTime.get(i).getTime();

            String dateString = transferFormat2(time);
            selectByPrimaryKeyFindEntryTime.get(i).setInserttime(dateString);
        }
        return Result.ok(selectByPrimaryKeyFindEntryTime);
    }



    /**
     * 查询当前日记录
     */
    @RequestMapping(value = "/selectByPrimaryKeyFindEntryTimeAnduserid")
    @ResponseBody
    public Result selectByPrimaryKeyFindEntryTimeAnduserid(String usercode, String time)throws ParseException {
        if (null == usercode || null == time) {
            Result.error();
        }
        YhUserReginfoApp info = YhUserReginfoService.findYhUserReginfo(usercode);
        List<YhUserArtery> list = yhUserArteryService
                .selectByPrimaryKeyFindEntryTimeAnduserid(time, usercode);
        if(null!=list){
            for (int i = 0; i < list.size(); i++) {
                YhUserArtery newInfoByUserCode = list.get(i);
                String infotime = newInfoByUserCode.getTime();


                list.get(i).setInserttime(transferFormat2(infotime));
                newInfoByUserCode.setTiwen("正常");
                newInfoByUserCode.setOpenNo(false);
                if (i == 0) {
                    newInfoByUserCode.setOpenNo(true);
                }
                int ageByBirth = getAgeByBirth(info.getBirthDate());
                Integer avi = newInfoByUserCode.getAvi();
                int aviStatus = 0;
                Integer api = newInfoByUserCode.getApi();
                int apiStatus = 0;
                int avizhibiao = 0;
                int apizhibiao = 0;
                if (null != avi && null != api) {
                    if (ageByBirth <= 23) {
                        avizhibiao = 15;
                    } else if (ageByBirth >= 24 && ageByBirth <= 26) {
                        avizhibiao = 16;
                    } else if (ageByBirth >= 27 && ageByBirth <= 28) {
                        avizhibiao = 17;
                    } else if (ageByBirth >= 29 && ageByBirth <= 35) {
                        avizhibiao = 18;
                    } else if (ageByBirth >= 36 && ageByBirth <= 37) {
                        avizhibiao = 19;
                    } else if (ageByBirth >= 38 && ageByBirth <= 40) {
                        avizhibiao = 20;
                    } else if (ageByBirth >= 41 && ageByBirth <= 43) {
                        avizhibiao = 21;
                    } else if (ageByBirth >= 44 && ageByBirth <= 46) {
                        avizhibiao = 22;
                    } else if (ageByBirth >= 47 && ageByBirth <= 49) {
                        avizhibiao = 23;
                    } else if (ageByBirth >= 50 && ageByBirth <= 52) {
                        avizhibiao = 24;
                    } else if (ageByBirth >= 53 && ageByBirth <= 54) {
                        avizhibiao = 25;
                    } else if (ageByBirth >= 55 && ageByBirth <= 57) {
                        avizhibiao = 26;
                    } else if (ageByBirth >= 58 && ageByBirth <= 59) {
                        avizhibiao = 27;
                    } else if (ageByBirth >= 60 && ageByBirth <= 61) {
                        avizhibiao = 28;
                    } else if (ageByBirth >= 62 && ageByBirth <= 63) {
                        avizhibiao = 29;
                    } else if (ageByBirth >= 64 && ageByBirth <= 65) {
                        avizhibiao = 30;
                    } else if (ageByBirth >= 66 && ageByBirth <= 67) {
                        avizhibiao = 31;
                    } else if (ageByBirth >= 68 && ageByBirth <= 69) {
                        avizhibiao = 32;
                    } else if (ageByBirth >= 70 && ageByBirth <= 72) {
                        avizhibiao = 33;
                    } else if (ageByBirth == 73) {
                        avizhibiao = 34;
                    } else if (ageByBirth >= 74 && ageByBirth <= 75) {
                        avizhibiao = 35;
                    } else if (ageByBirth == 76) {
                        avizhibiao = 36;
                    } else if (ageByBirth >= 77 && ageByBirth <= 78) {
                        avizhibiao = 37;
                    } else if (ageByBirth == 79) {
                        avizhibiao = 38;
                    } else if (ageByBirth > 80) {
                        avizhibiao = 39;
                    }

                    if (avi > avizhibiao && avi < (avizhibiao * 0.2)) {
                        aviStatus = 1;
                    }
                    if (avi >= (avizhibiao * 0.2) && avi <= (avizhibiao * 0.5)) {
                        aviStatus = 2;
                    }
                    if (avi > (avizhibiao * 0.5)) {
                        aviStatus = 3;
                    }
                    if (ageByBirth <= 26) {
                        apizhibiao = 22;
                    } else if (ageByBirth >= 27 && ageByBirth <= 32) {
                        apizhibiao = 23;
                    } else if (ageByBirth >= 33 && ageByBirth <= 37) {
                        apizhibiao = 24;
                    } else if (ageByBirth >= 38 && ageByBirth <= 43) {
                        apizhibiao = 25;
                    } else if (ageByBirth >= 44 && ageByBirth <= 47) {
                        apizhibiao = 26;
                    } else if (ageByBirth >= 48 && ageByBirth <= 54) {
                        apizhibiao = 27;
                    } else if (ageByBirth >= 55 && ageByBirth <= 58) {
                        apizhibiao = 28;
                    } else if (ageByBirth >= 59 && ageByBirth <= 62) {
                        apizhibiao = 29;
                    } else if (ageByBirth >= 63 && ageByBirth <= 66) {
                        apizhibiao = 30;
                    } else if (ageByBirth >= 67 && ageByBirth <= 71) {
                        apizhibiao = 31;
                    } else if (ageByBirth >= 72 && ageByBirth <= 77) {
                        apizhibiao = 32;
                    } else if (ageByBirth >= 78 && ageByBirth <= 79) {
                        apizhibiao = 33;
                    } else if (ageByBirth >= 80) {
                        apizhibiao = 33;
                    }
                    if (api > apizhibiao && avi < (apizhibiao * 0.2)) {
                        apiStatus = 1;
                    }
                    if (api >= (apizhibiao * 0.2) && api <= (apizhibiao * 0.5)) {
                        apiStatus = 2;
                    }
                    if (api > (apizhibiao * 0.5)) {
                        apiStatus = 3;
                    }


                    if(aviStatus>=apiStatus){
                        if(0==aviStatus){
                            newInfoByUserCode.setTiwen("正常");
                            newInfoByUserCode.setJiedu("动脉硬化是是随着年龄增长而出现的血管疾病，要注意防治：戒烟；防止三高，高血压、高血脂、高血糖；控制体重；加强锻炼；进而起到预防动脉硬化的发生。");
                        }
                        if(1==aviStatus){
                            newInfoByUserCode.setTiwen("轻度偏高");
                            newInfoByUserCode.setJiedu("做心血管方面的检查（如超声波、心电图等）来进一步了解您的健康状态，同时采取多做户外运动，戒烟酒、多食低脂肪低糖高维生素高纤维食品，控制体重等措施。");
                        }
                        if(2==aviStatus){
                            newInfoByUserCode.setTiwen("中度偏高");
                            newInfoByUserCode.setJiedu("进行心血管系统全面检查，并在医生的指导下制定适宜的治疗方案。");
                        }
                        if(3==aviStatus){
                            newInfoByUserCode.setTiwen("重度偏高");
                            newInfoByUserCode.setJiedu("一般提示有血管系统可查见的器官或组织病变，需及时就医。");
                        }
                    }else{
                        if(0==apiStatus){
                            newInfoByUserCode.setTiwen("正常");
                            newInfoByUserCode.setJiedu("动脉硬化是是随着年龄增长而出现的血管疾病，要注意防治：戒烟；防止三高，高血压、高血脂、高血糖；控制体重；加强锻炼；进而起到预防动脉硬化的发生。");
                        }
                        if(1==apiStatus){
                            newInfoByUserCode.setTiwen("轻度偏高");
                            newInfoByUserCode.setJiedu("做心血管方面的检查（如超声波、心电图等）来进一步了解您的健康状态，同时采取多做户外运动，戒烟酒、多食低脂肪低糖高维生素高纤维食品，控制体重等措施。");
                        }
                        if(2==apiStatus){
                            newInfoByUserCode.setTiwen("中度偏高");
                            newInfoByUserCode.setJiedu("进行心血管系统全面检查，并在医生的指导下制定适宜的治疗方案。");
                        }
                        if(3==apiStatus){
                            newInfoByUserCode.setTiwen("重度偏高");
                            newInfoByUserCode.setJiedu("一般提示有血管系统可查见的器官或组织病变，需及时就医。");
                        }
                    }



                }
            }
        }
        return Result.ok(list);
    }




    public static String transferFormat2(String inTime) throws ParseException {
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");

        Date tempDate = null;
        String outTime = null;
        tempDate = s1.parse(inTime);
        outTime = s1.format(tempDate);
        return outTime;
    }

    /**
     * 根据出生算年龄
     *
     * @param birthday
     * @return
     */
    private int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {// 如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {// 兼容性更强,异常后返回数据
            return 0;
        }
    }


}
