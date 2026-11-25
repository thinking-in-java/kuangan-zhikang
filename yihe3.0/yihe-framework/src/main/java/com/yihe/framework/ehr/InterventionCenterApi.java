package com.yihe.framework.ehr;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrController.ok;

/** Scoped, versioned intervention workflow. No notification or clinical conclusion is inferred. */
@RestController
@RequestMapping("/ehr/api/intervention")
public class InterventionCenterApi {
    private final EhrAccess access;private final EhrService ehr;
    private final ObjectMapper json=new ObjectMapper();
    private static final List<String> KEYS=Arrays.asList("plans","schedules","tasks","revisits","alerts","care","projects","templates","settings");
    @Value("${ehr.storage-root:${EHR_STORAGE_ROOT:D:/update}}") private String storage;
    public InterventionCenterApi(EhrAccess access,EhrService ehr){this.access=access;this.ehr=ehr;}
    @ModelAttribute public void privateHeaders(HttpServletResponse response){response.setHeader("Cache-Control","private, no-store");}
    private Path file(EhrActor actor){return Paths.get(storage,".health-intervention",Base64.getUrlEncoder().withoutPadding().encodeToString(digest(bytes(actor.scope+":"+actor.mode)))+".json");}
    @SuppressWarnings("unchecked") private Map<String,Object> read(EhrActor actor)throws IOException {
        Path path=file(actor);if(!Files.exists(path)){Map<String,Object> state=map("version",0,"audit",new ArrayList<>());for(String key:KEYS)state.put(key,new ArrayList<>());return state;}
        if(Files.size(path)>4194304)throw new EhrException(500,"干预中心资料过大，请联系管理员");return json.readValue(Files.readAllBytes(path),Map.class);
    }
    @GetMapping("/state") public synchronized Object state(HttpServletRequest request)throws IOException{return ok(read(access.web(request)));}
    private String value(Map<?,?> row,String key){Object v=row.get(key);return v==null?"":String.valueOf(v);}
    @SuppressWarnings("unchecked") private void validate(EhrActor actor,String key,Map<String,Object> row,Map<String,Object> previous,Set<String> checked){
        String id=text(row,"id"),record=text(row,"recordId");if(!id.matches("[A-Za-z0-9_-]{1,96}"))throw new EhrException(422,"记录标识无效");
        if(!record.isEmpty()&&checked.add(record))ehr.profile(actor,record,false);
        if(previous!=null&&!text(previous,"recordId").isEmpty()&&!Objects.equals(text(previous,"recordId"),record))throw new EhrException(422,"已关联的职工不能变更，请另建记录");
        if(!record.isEmpty()&&"tasks".equals(key)&&"completed".equals(text(row,"status"))&&text(row,"completionEvidence").isEmpty())throw new EhrException(422,"任务完成需填写实际完成依据");
        if(!record.isEmpty()&&"alerts".equals(key)&&Arrays.asList("resolved","closed").contains(text(row,"status"))&&text(row,"handlingEvidence").isEmpty())throw new EhrException(422,"预警关闭需记录实际处置依据");
        if(!"plans".equals(key)||record.isEmpty())return;
        String state=text(row,"status"),before=previous==null?"":text(previous,"status");
        if(!Arrays.asList("draft","pending_review","awaiting_confirmation","active","completed","paused","rejected").contains(state))throw new EhrException(422,"方案状态无效");
        if(previous==null&&!"draft".equals(state))throw new EhrException(422,"新方案必须先保存为草稿");
        if(previous!=null&&!Arrays.asList("draft","rejected").contains(before)&&!"draft".equals(state)&&(!Objects.equals(previous.get("content"),row.get("content"))||!Objects.equals(previous.get("title"),row.get("title"))))throw new EhrException(409,"修改已提交的方案内容需先转回草稿");
        if(before.equals(state))return;
        Map<String,List<String>> transitions=new HashMap<>();
        transitions.put("draft",Arrays.asList("pending_review"));transitions.put("pending_review",Arrays.asList("awaiting_confirmation","rejected"));transitions.put("rejected",Arrays.asList("draft","pending_review"));transitions.put("awaiting_confirmation",Arrays.asList("active","draft"));transitions.put("active",Arrays.asList("paused","completed","draft"));transitions.put("paused",Arrays.asList("active","draft"));transitions.put("completed",Collections.emptyList());
        if(previous!=null&&!transitions.getOrDefault(before,Collections.emptyList()).contains(state))throw new EhrException(409,"方案状态不允许直接跳转，请按审核流程操作");
        if("awaiting_confirmation".equals(state)&&(!actor.doctor||text(row,"reviewNote").isEmpty()))throw new EhrException(403,"方案需医生审核并填写意见");
        if("active".equals(state)&&text(row,"confirmationNote").isEmpty())throw new EhrException(422,"请记录职工实际确认的时间、方式和反馈");
    }
    @PutMapping("/state") public synchronized Object save(HttpServletRequest request,@RequestBody Map<String,Object> body)throws IOException {
        EhrActor actor=access.web(request);Map<String,Object> old=read(actor);
        if(EhrService.num(body.get("version"))!=EhrService.num(old.get("version")))throw new EhrException(409,"资料已被其他人员修改，请刷新后重试");
        Map<String,Object> next=map("version",EhrService.num(old.get("version"))+1);List<Object> audit=new ArrayList<>();Object priorAudit=old.get("audit");if(priorAudit instanceof List)audit.addAll((List<?>)priorAudit);Set<String> checked=new HashSet<>();
        for(String key:KEYS){Object raw=body.get(key);if(!(raw instanceof List)||((List<?>)raw).size()>3000)throw new EhrException(422,"资料列表无效或超过3000条");
            Map<String,Map<String,Object>> previous=new HashMap<>();for(Object o:(List<?>)old.get(key))if(o instanceof Map){@SuppressWarnings("unchecked")Map<String,Object> row=(Map<String,Object>)o;previous.put(text(row,"id"),row);}
            Set<String> ids=new HashSet<>();for(Object o:(List<?>)raw){if(!(o instanceof Map))throw new EhrException(422,"记录格式错误");@SuppressWarnings("unchecked")Map<String,Object> row=(Map<String,Object>)o;String id=text(row,"id");if(!ids.add(id))throw new EhrException(422,"记录标识重复");validate(actor,key,row,previous.get(id),checked);if(!Objects.equals(previous.get(id),row))audit.add(map("module",key,"recordId",id,"title",row.getOrDefault("title",row.getOrDefault("name","记录")),"actor",actor.user,"time",now(),"action",previous.containsKey(id)?"updated":"created","status",row.get("status")));}
            if(!ids.containsAll(previous.keySet()))throw new EhrException(422,"历史记录不能直接删除，请使用停用或关闭操作");next.put(key,raw);
        }
        if(audit.size()>500)audit=new ArrayList<>(audit.subList(audit.size()-500,audit.size()));next.put("audit",audit);next.put("updatedAt",now());
        byte[] bytes=json.writeValueAsBytes(next);if(bytes.length>4194304)throw new EhrException(422,"干预资料超过4 MiB，请归档后再保存");Path target=file(actor);Files.createDirectories(target.getParent());Path temp=Files.createTempFile(target.getParent(),"intervention-",".tmp");
        try{Files.write(temp,bytes);try{Files.move(temp,target,StandardCopyOption.ATOMIC_MOVE,StandardCopyOption.REPLACE_EXISTING);}catch(AtomicMoveNotSupportedException error){Files.move(temp,target,StandardCopyOption.REPLACE_EXISTING);}}finally{Files.deleteIfExists(temp);}return ok(next);
    }
}
