package com.yihe.web.controller.system;
import java.util.*;
import javax.servlet.http.*;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.yihe.framework.ehr.EhrAccess;
import com.yihe.framework.util.ShiroUtils;
import com.yihe.system.domain.*;
import com.yihe.system.service.YhUserApiService;
import static com.yihe.framework.ehr.EhrAccess.map;

/** Current-account projection only. This UI does not grant authentication permissions. */
@Controller @RequiresUser @RequestMapping("/manage/system-center")
public class SystemManagementController {
 private final EhrAccess access;private final YhUserApiService users;
 public SystemManagementController(EhrAccess access,YhUserApiService users){this.access=access;this.users=users;}
 @GetMapping({"","/users"}) public String users(HttpServletResponse response){response.setHeader("Cache-Control","no-store");return "system-center/users";}
 @GetMapping("/dictionary") public String dictionary(HttpServletResponse response){response.setHeader("Cache-Control","no-store");return "system-center/dictionary";}
 @GetMapping("/ocr-templates") public String ocrTemplates(HttpServletResponse response){response.setHeader("Cache-Control","no-store");return "system-center/ocr";}
 @GetMapping("/assessment-rules") public String assessmentRules(HttpServletResponse response){response.setHeader("Cache-Control","no-store");return "system-center/rules";}
 @GetMapping("/intervention-templates") public String interventionTemplates(HttpServletResponse response){response.setHeader("Cache-Control","no-store");return "system-center/intervention-templates";}
 @GetMapping("/message-templates") public String messageTemplates(HttpServletResponse response){response.setHeader("Cache-Control","no-store");return "system-center/messages";}
 @GetMapping("/ai-models") public String aiModels(HttpServletResponse response){response.setHeader("Cache-Control","no-store");return "system-center/ai-models";}
 @GetMapping("/ai-tools") public String aiTools(HttpServletResponse response){response.setHeader("Cache-Control","no-store");return "system-center/ai-tools";}
 @GetMapping("/audit-logs") public String auditLogs(HttpServletResponse response){response.setHeader("Cache-Control","no-store");return "system-center/audit-logs";}
 @GetMapping("/context") @ResponseBody public Object context(HttpServletRequest request,HttpServletResponse response){
  access.web(request);response.setHeader("Cache-Control","private, no-store");YhUserReginfo user=ShiroUtils.getSysUser();
  List<Object> roles=new ArrayList<>(),permissions=new ArrayList<>();
  List<YhUserRole> currentRoles=users.selectUserRoleByUpmsUserId(user.getUserCode());
  if(currentRoles!=null)for(YhUserRole role:currentRoles)roles.add(map("id",role.getRoleId(),"name",role.getRoleName(),"description",role.getRoleDescription()));
  List<YhUserPermission> currentPermissions=users.selectUserPermissionByUpmsUserId(user.getUserCode());
  if(currentPermissions!=null)for(YhUserPermission p:currentPermissions)permissions.add(map("id",p.getPermissionId(),"parentId",p.getPid(),"name",p.getName(),"type",p.getType(),"path",p.getUri(),"permission",p.getPermissionValue()));
  return map("code",0,"data",map("user",map("id",user.getUserCode(),"username",user.getUserName(),"name",user.getUserName(),"organization","当前账号所属组织","roles",roles),"permissions",permissions,"canManageLiveUsers",false,"capturedAt",EhrAccess.now()+"Z"));
 }
}
