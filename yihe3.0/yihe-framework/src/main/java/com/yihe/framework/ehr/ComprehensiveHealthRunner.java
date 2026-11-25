package com.yihe.framework.ehr;
import java.util.*;import java.util.concurrent.Semaphore;import org.springframework.stereotype.Service;import static com.yihe.framework.ehr.EhrAccess.*;import static com.yihe.framework.ehr.EhrService.*;
@Service public class ComprehensiveHealthRunner {
 final ComprehensiveHealthService service;final DigitalWorkerModelClient client;final Semaphore capacity=new Semaphore(2);
 public ComprehensiveHealthRunner(ComprehensiveHealthService service,DigitalWorkerModelClient client){this.service=service;this.client=client;}
 public Map<String,Object> status(){return map("configured",client.status().get("configured"),"active",2-capacity.availablePermits(),"concurrency",2);}
 public Object generate(EhrActor a,String record,Map<String,Object> body){require(a.doctor&&!a.mobile,403,"综合分析需要医生权限");require(Boolean.TRUE.equals(client.status().get("configured")),503,"独立AI服务尚未配置");require(capacity.tryAcquire(),429,"已有2份综合分析运行，请稍后重试");try{Map<String,Object> task=service.begin(a,record,body);String id=str(task.get("analysis_id"));if(Boolean.TRUE.equals(task.get("replayed")))return service.detail(a,record,id);String output=null,error=null;try{output=str(client.generate(service.modelInput(task)).get("output"));}catch(EhrException e){error=e.getMessage().matches("[A-Z0-9_]{1,80}")?e.getMessage():"ANALYSIS_SERVICE_UNAVAILABLE";}catch(RuntimeException e){error="ANALYSIS_REQUEST_FAILED";}service.finish(a,record,id,output,error);return service.detail(a,record,id);}finally{capacity.release();}}
}
