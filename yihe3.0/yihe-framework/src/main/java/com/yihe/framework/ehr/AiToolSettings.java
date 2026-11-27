package com.yihe.framework.ehr;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.file.*;import java.util.*;import java.io.*;
import static com.yihe.framework.ehr.EhrAccess.*;
@Service
public class AiToolSettings {
 @Value("${ehr.storage-root:${EHR_STORAGE_ROOT:D:/update}}") private String storage;
 private final ObjectMapper json=new ObjectMapper();
 private Path file(EhrActor a){return Paths.get(storage,".ai-tools",Base64.getUrlEncoder().withoutPadding().encodeToString(digest(bytes(a.scope+":"+a.mode)))+".json");}
 @SuppressWarnings("unchecked") synchronized Map<String,Object> read(EhrActor a){try{Path p=file(a);if(!Files.exists(p))return map("version",0,"tools",new ArrayList<>(),"categories",new ArrayList<>(),"tests",new ArrayList<>());if(Files.size(p)>2097152)throw new IOException();return json.readValue(Files.readAllBytes(p),Map.class);}catch(Exception e){throw new EhrException(503,"工具配置读取失败，请检查存储目录");}}
 synchronized void write(EhrActor a,Map<String,Object> state){try{byte[] data=json.writeValueAsBytes(state);if(data.length>2097152)throw new EhrException(422,"工具配置超过2MB限制");Path p=file(a);Files.createDirectories(p.getParent());Path tmp=Files.createTempFile(p.getParent(),"tools-",".tmp");try{Files.write(tmp,data);try{Files.move(tmp,p,StandardCopyOption.ATOMIC_MOVE,StandardCopyOption.REPLACE_EXISTING);}catch(AtomicMoveNotSupportedException e){Files.move(tmp,p,StandardCopyOption.REPLACE_EXISTING);}}finally{Files.deleteIfExists(tmp);}}catch(IOException e){throw new EhrException(503,"工具配置保存失败");}}
 @SuppressWarnings("unchecked") Map<String,Object> override(EhrActor a,String name){for(Map<String,Object> row:(List<Map<String,Object>>)read(a).get("tools"))if(name.equals(row.get("function")))return row;return Collections.emptyMap();}
 boolean allowed(EhrActor a,String name){Map<String,Object> c=override(a,name);return !Boolean.FALSE.equals(c.get("enabled"))&&(!(c.get("roles") instanceof List)||((List<?>)c.get("roles")).contains(a.doctor?"doctor":"member"));}
 void enforce(EhrActor a,String name,boolean confirmed){if(a.mobile||!allowed(a,name))throw new EhrException(403,"此工具已停用或当前账号不在允许范围");Map<String,Object> c=override(a,name);String policy=String.valueOf(c.getOrDefault("confirmation","auto"));if(!name.startsWith("prepare_")&&!"auto".equals(policy)&&!confirmed)throw new EhrException(409,"此工具需人工确认，请在AI工具管理的测试调用中确认；智能体不会自动执行");}
}
