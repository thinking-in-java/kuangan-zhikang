package com.yihe.web.controller.tool;

import com.yihe.system.service.YhInterventiongroupingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CreatPlanController {
    @Autowired
    YhInterventiongroupingService service;
    @Scheduled(cron = "0 0 7 * * ?")
    public void changeplancontroller(){
        service.insertservice();
    }

    //每天查询需要生成久未使用的服务任务提醒
    // @Scheduled(cron = "0/200 * *  * * ?")
    @Scheduled(cron = "0 0 7 * * ?")
    public void wattingservice(){
        service.wattingservice();
    }

}
