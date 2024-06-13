package com.yihe.web.controller.system;

import com.yihe.system.domain.PushMessage;
import com.yihe.system.utils.Result;
import com.yihe.web.controller.netty.SocketIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/messge")
public class MessgeController {



        @Autowired
        private SocketIOService ioService;
            @RequestMapping(value = "/send")
            @ResponseBody
        public Result sendmessges( PushMessage message){

            ioService.pushMessageToUser(message);
            return Result.ok("发送信息");
        }


}
