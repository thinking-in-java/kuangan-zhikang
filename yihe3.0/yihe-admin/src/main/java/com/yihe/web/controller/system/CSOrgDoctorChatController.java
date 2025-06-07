package com.yihe.web.controller.system;

import com.yihe.system.domain.CSDoctorChatResVo;
import com.yihe.system.domain.CSOrgDoctorChat;
import com.yihe.system.domain.YhUserReginfo;
import com.yihe.system.service.CSOrgDoctorChatService;
import com.yihe.system.utils.Result;
import com.yihe.system.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/doctorChatBinding")
@Api(value = "咨询医生绑定列表")
public class CSOrgDoctorChatController {

    @Resource
    CSOrgDoctorChatService CSOrgDoctorChatService;

    @ApiOperation(value = "查询所有服务")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody List<CSDoctorChatResVo> CSDoctorChatResVo) {

        if (CollectionUtils.isEmpty(CSDoctorChatResVo)) {
            return Result.error();
        }
        YhUserReginfo CSUserRegInfo = UpmsJumpPageController.getUserInfo();
        if (1 != CSUserRegInfo.getUserType()) {
            return Result.error();
        }
        for (CSDoctorChatResVo CSDoctorChatResVo1 : CSDoctorChatResVo) {
            Integer type = CSDoctorChatResVo1.getType();
            String userCode = CSDoctorChatResVo1.getUserCode();
            String orgId = CSUserRegInfo.getUserCode();
            if (null != type && StringUtil.isNotEmpty(userCode)) {
                CSOrgDoctorChat CSOrgDoctorChat = CSOrgDoctorChatService.selectListByOrgAndType(orgId, type);
                if (null != CSOrgDoctorChat) {
                    CSOrgDoctorChatService.deleteByOrgAndType(orgId, type);
                    CSOrgDoctorChat record = new CSOrgDoctorChat();
                    record.setDocId(userCode);
                    record.setOrgId(orgId);
                    record.setType(type);
                    CSOrgDoctorChatService.insertSelective(record);
                } else {
                    CSOrgDoctorChat record = new CSOrgDoctorChat();
                    record.setDocId(userCode);
                    record.setOrgId(orgId);
                    record.setType(type);
                    CSOrgDoctorChatService.insertSelective(record);
                }
            }
        }

        return Result.ok();
    }


}
