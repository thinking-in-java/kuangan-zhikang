package com.yihe.framework.ehr;

import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Organisation-scoped dispatch policy. Does not enable global OCR, notification or health-record writes. */
@Service
public class DigitalWorkerSettings {
    private final EhrRepository db;private final EhrService ehr;
    static final List<String> WORKERS=Arrays.asList("archive","assess","follow","plan","care");
    static final Set<String> FIELDS=new HashSet<>(Arrays.asList("acceptingTasks","aiEnabled","defaultUseAi","allowedTypes","defaultType","defaultPriority","maxActiveTasks","instructions"));
    public DigitalWorkerSettings(EhrRepository db){this.db=db;this.ehr=new EhrService(db);}
    private void worker(String worker){if(!WORKERS.contains(worker))throw new EhrException(422,"数字员工无效");}
    private void backend(EhrActor actor){if(actor.mobile)throw new EhrException(403,"配置仅供后台管理使用");}
    public Map<String,Object> defaults(String worker){
        worker(worker);List<String> types=new ArrayList<>();for(Map<String,Object> p:DigitalWorkerTaskProfiles.all())types.add(text(p,"type"));
        String type=map("archive","archive_completion","assess","report_interpretation","follow","routine_followup","plan","plan_adjustment","care","visit_invitation").get(worker).toString();
        return map("acceptingTasks",true,"aiEnabled",true,"defaultUseAi",true,"allowedTypes",types,"defaultType",type,"defaultPriority","normal","maxActiveTasks",500,"instructions","");
    }
    @SuppressWarnings("unchecked") private Map<String,Object> view(String worker,Map<String,Object> row){Map<String,Object> result=new LinkedHashMap<>(defaults(worker));if(row!=null)result.putAll((Map<String,Object>)ehr.decode(row.get("settings_json")));result.putAll(map("worker",worker,"version",row==null?0:row.get("row_version"),"updatedAt",row==null?null:row.get("updated_at"),"updatedBy",row==null?null:row.get("updated_by")));return result;}
    public Map<String,Object> get(EhrActor actor,String worker){backend(actor);worker(worker);return view(worker,db.one("SELECT * FROM a_digital_worker_settings WHERE scope_code=? AND data_mode=? AND worker_id=?",actor.scope,actor.mode,worker));}
    /** Caller owns a transaction; serialises admission and updates for this worker. */
    Map<String,Object> lock(EhrActor actor,String worker){backend(actor);worker(worker);db.exec("INSERT IGNORE INTO a_digital_worker_settings(scope_code,data_mode,worker_id,settings_json,row_version,updated_at) VALUES(?,?,?,?,0,UTC_TIMESTAMP(6))",actor.scope,actor.mode,worker,ehr.encode(defaults(worker)));return view(worker,db.one("SELECT * FROM a_digital_worker_settings WHERE scope_code=? AND data_mode=? AND worker_id=? FOR UPDATE",actor.scope,actor.mode,worker));}
    private long active(EhrActor actor,String worker){return db.count("SELECT COUNT(*) FROM a_digital_worker_tasks WHERE scope_code=? AND data_mode=? AND worker_id=? AND task_status IN ('queued','in_progress','pending_review')",actor.scope,actor.mode,worker);}
    @Transactional(readOnly=true) public Map<String,Object> all(EhrActor actor){backend(actor);List<Object> items=new ArrayList<>();for(String worker:WORKERS){Map<String,Object> item=get(actor,worker);item.put("activeTasks",active(actor,worker));item.put("defaults",defaults(worker));items.add(item);}return map("items",items,"canConfigure",actor.doctor,"reviewRequired",true,"profiles",DigitalWorkerTaskProfiles.all());}
    @SuppressWarnings("unchecked") private Map<String,Object> validate(Map<String,Object> body){
        for(String key:body.keySet())if(!FIELDS.contains(key)&&!key.equals("version"))throw new EhrException(422,"配置包含不支持的字段");
        Map<String,Object> result=new LinkedHashMap<>();for(String key:Arrays.asList("acceptingTasks","aiEnabled","defaultUseAi")){if(!(body.get(key) instanceof Boolean))throw new EhrException(422,"开关参数无效");result.put(key,body.get(key));}
        if(!(body.get("allowedTypes") instanceof List))throw new EhrException(422,"请选择允许的任务类型");List<String> allowed=new ArrayList<>();for(Object type:(List<?>)body.get("allowedTypes")){if(!(type instanceof String))throw new EhrException(422,"任务类型无效");DigitalWorkerTaskProfiles.get((String)type);if(allowed.contains(type))throw new EhrException(422,"任务类型重复");allowed.add((String)type);}if(allowed.isEmpty())throw new EhrException(422,"至少保留一种任务类型；暂停派活请关闭接收开关");
        String type=text(body,"defaultType"),priority=text(body,"defaultPriority");if(!allowed.contains(type))throw new EhrException(422,"默认类型必须属于可接收任务类型");if(!Arrays.asList("normal","high","urgent").contains(priority))throw new EhrException(422,"默认优先级无效");
        Object max=body.get("maxActiveTasks");if(!(max instanceof Number)||((Number)max).doubleValue()!=((Number)max).intValue()||((Number)max).intValue()<1||((Number)max).intValue()>500)throw new EhrException(422,"活跃派活上限应为1至500的整数");
        Object instructions=body.get("instructions");if(!(instructions instanceof String)||((String)instructions).length()>2000||((String)instructions).indexOf(0)>=0)throw new EhrException(422,"处理要求最多2000字");if(Boolean.TRUE.equals(result.get("defaultUseAi"))&&!Boolean.TRUE.equals(result.get("aiEnabled")))throw new EhrException(422,"未允许AI处理时不能默认勾选AI");
        result.putAll(map("allowedTypes",allowed,"defaultType",type,"defaultPriority",priority,"maxActiveTasks",((Number)max).intValue(),"instructions",((String)instructions).trim()));return result;
    }
    @Transactional public Map<String,Object> save(EhrActor actor,String worker,Map<String,Object> body){
        backend(actor);if(!actor.doctor)throw new EhrException(403,"修改岗位配置需要医生权限");Map<String,Object> config=validate(body),before=lock(actor,worker);Object version=body.get("version");if(!(version instanceof Number)||((Number)version).doubleValue()!=((Number)before.get("version")).intValue())throw new EhrException(409,"配置已被更新，请重新读取后再保存");
        if(Boolean.TRUE.equals(before.get("aiEnabled"))&&!Boolean.TRUE.equals(config.get("aiEnabled"))&&db.count("SELECT COUNT(*) FROM a_digital_worker_ai_attempts a JOIN a_digital_worker_tasks t ON t.task_id=a.task_id WHERE t.scope_code=? AND t.data_mode=? AND t.worker_id=? AND a.active_task_id IS NOT NULL",actor.scope,actor.mode,worker)>0)throw new EhrException(409,"该员工仍有排队或运行中的AI请求，请等待结束后再关闭AI");
        db.exec("UPDATE a_digital_worker_settings SET settings_json=?,row_version=row_version+1,updated_by=?,updated_at=UTC_TIMESTAMP(6) WHERE scope_code=? AND data_mode=? AND worker_id=?",ehr.encode(config),actor.user,actor.scope,actor.mode,worker);
        db.insert("a_digital_worker_settings_events",map("event_id",id(),"scope_code",actor.scope,"data_mode",actor.mode,"worker_id",worker,"actor",actor.user,"before_json",ehr.encode(before),"after_json",ehr.encode(config),"created_at",now()));Map<String,Object> saved=get(actor,worker);saved.put("activeTasks",active(actor,worker));return saved;
    }
    @SuppressWarnings("unchecked") void admit(EhrActor actor,String worker,String type,Map<String,Object> config){
        if(!Boolean.TRUE.equals(config.get("acceptingTasks")))throw new EhrException(409,"该数字员工已暂停接收新派活，请调整配置或选择其他员工");
        if(!((List<String>)config.get("allowedTypes")).contains(type))throw new EhrException(422,"该员工未配置接收此类任务，请调整任务类型或岗位配置");
        int count=db.all("SELECT task_id FROM a_digital_worker_tasks WHERE scope_code=? AND data_mode=? AND worker_id=? AND task_status IN ('queued','in_progress','pending_review') FOR UPDATE",actor.scope,actor.mode,worker).size();if(count>=((Number)config.get("maxActiveTasks")).intValue())throw new EhrException(409,"该员工的活跃派活已达配置上限，请处理既有任务或调整上限");
    }
    void requireAi(Map<String,Object> config){if(!Boolean.TRUE.equals(config.get("aiEnabled")))throw new EhrException(409,"该数字员工的AI处理已在岗位配置中关闭");}
}
