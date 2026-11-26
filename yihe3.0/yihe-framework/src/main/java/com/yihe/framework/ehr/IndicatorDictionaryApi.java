package com.yihe.framework.ehr;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import java.nio.file.*;
import java.util.*;
import java.io.IOException;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrController.ok;
/** Scoped dictionary configuration; source dictionaries and clinical records remain immutable. */
@RestController @RequestMapping("/ehr/api/indicator-dictionary")
public class IndicatorDictionaryApi {
 private final EhrAccess access;private final EhrRepository db;private final ObjectMapper json=new ObjectMapper();
 @Value("${ehr.storage-root:${EHR_STORAGE_ROOT:D:/update}}") private String storage;
 public IndicatorDictionaryApi(EhrAccess access,EhrRepository db){this.access=access;this.db=db;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");}
 private Path file(EhrActor a){return Paths.get(storage,".indicator-dictionary",Base64.getUrlEncoder().withoutPadding().encodeToString(digest(bytes(a.scope+":"+a.mode)))+".json");}
 private String str(Object v){return v==null?"":String.valueOf(v);}
 private boolean enabled(Object v){return v==null||"0".equals(str(v));}
 private String gender(Object v){return "1".equals(str(v))?"male":"2".equals(str(v))?"female":"all";}
 private Map<String,Object> initial(){
  List<Object> groups=new ArrayList<>(),items=new ArrayList<>(),units=new ArrayList<>(),templates=new ArrayList<>();Set<String> names=new HashSet<>(),unitNames=new LinkedHashSet<>();
  for(Map<String,Object> g:db.all("SELECT typeid id,typename name,state FROM yh_data_detection_type ORDER BY COALESCE(point,9999),typeid"))groups.add(map("id","legacy-group-"+g.get("id"),"name",g.get("name"),"enabled",enabled(g.get("state")),"description","","source","legacy"));
  groups.add(map("id","medical","name","医学检查项目","enabled",true,"description","现有医学检查项目字典","source","medical"));
  for(Map<String,Object> r:db.all("SELECT p.projectid,p.projectname name,p.tid,p.state,p.applicable_gender gender,r.referenceid,r.unit,r.reference_range referenceRange,r.applicable_gender referenceGender,r.state referenceState FROM yh_data_detection_project p LEFT JOIN yh_data_detection_reference r ON r.pid=p.projectid ORDER BY (r.reference_range IS NULL OR TRIM(r.reference_range)=''),COALESCE(p.point,9999),p.projectid,r.referenceid")){
   String name=str(r.get("name")),unit=str(r.get("unit"));names.add(name);if(!unit.isEmpty())unitNames.add(unit);
   String id="legacy-"+r.get("projectid")+(r.get("referenceid")==null?"":"-"+r.get("referenceid"));
   items.add(map("id",id,"code",id.toUpperCase(Locale.ROOT),"name",name,"groupId","legacy-group-"+r.get("tid"),"unit",unit,"referenceRange",str(r.get("referenceRange")),"gender",gender(r.get("referenceGender")==null?r.get("gender"):r.get("referenceGender")),"enabled",enabled(r.get("state"))&&enabled(r.get("referenceState")),"source","legacy","sourceCode",r.get("projectid")));
   if(!str(r.get("referenceRange")).trim().isEmpty())templates.add(map("id","template-"+id,"name",name+" · "+id.toUpperCase(Locale.ROOT),"itemId",id,"referenceRange",str(r.get("referenceRange")),"gender",gender(r.get("referenceGender")==null?r.get("gender"):r.get("referenceGender")),"applicability","","enabled",enabled(r.get("state"))&&enabled(r.get("referenceState"))));
  }
  Map<String,String> observed=new HashMap<>();
  for(Map<String,Object> r:db.all("SELECT o.item_code,GROUP_CONCAT(DISTINCT m.unit_raw ORDER BY m.unit_raw SEPARATOR ' / ') unit FROM a_medical_unit_observations o JOIN a_medical_unit_mapping m ON m.unit_mapping_key=o.unit_mapping_key AND m.mapping_version=o.unit_mapping_version WHERE o.dictionary_version='2.0.0' GROUP BY o.item_code"))observed.put(str(r.get("item_code")),str(r.get("unit")));
  for(Map<String,Object> r:db.all("SELECT item_code code,standard_name name,status FROM a_medical_item_dictionary WHERE dictionary_version='2.0.0' AND status<>'retired' ORDER BY item_code")){
   if(names.contains(str(r.get("name"))))continue;String unit=observed.getOrDefault(str(r.get("code")),"");if(!unit.isEmpty())unitNames.add(unit);
   items.add(map("id","medical-"+r.get("code"),"code",r.get("code"),"name",r.get("name"),"groupId","medical","unit",unit,"referenceRange","","gender","all","enabled",true,"source","medical","sourceCode",r.get("code")));
  }
  int i=0;for(String unit:unitNames)units.add(map("id","unit-"+(++i),"name",unit,"symbol",unit,"description","","enabled",true));
  return map("version",0,"items",items,"groups",groups,"units",units,"templates",templates,"updatedAt",now());
 }
 @SuppressWarnings("unchecked") private Map<String,Object> read(EhrActor a)throws IOException{Path p=file(a);if(!Files.exists(p))return initial();if(Files.size(p)>8388608)throw new EhrException(500,"字典配置过大");return json.readValue(Files.readAllBytes(p),Map.class);}
 @GetMapping public synchronized Object get(HttpServletRequest request)throws IOException{return ok(read(access.web(request)));}
 @SuppressWarnings("unchecked") private List<Map<String,Object>> rows(Map<String,Object> b,String key,int max){Object value=b.get(key);if(!(value instanceof List)||((List<?>)value).size()>max)throw new EhrException(422,"字典列表格式无效或超过数量限制");List<Map<String,Object>> result=new ArrayList<>();for(Object v:(List<?>)value){if(!(v instanceof Map))throw new EhrException(422,"字典行格式无效");result.add((Map<String,Object>)v);}return result;}
 private void text(Map<String,Object> r,String key,int max,boolean required){String value=str(r.get(key));if(value.length()>max||(required&&value.trim().isEmpty()))throw new EhrException(422,"字段 "+key+" 不能为空或超过长度限制");}
 @PutMapping public synchronized Object save(HttpServletRequest request,@RequestBody Map<String,Object> body)throws IOException{
  EhrActor actor=access.web(request);Map<String,Object> old=read(actor);if(EhrService.num(body.get("version"))!=EhrService.num(old.get("version")))throw new EhrException(409,"字典已被其他人员修改，请刷新后重试");
  List<Map<String,Object>> groups=rows(body,"groups",2000),items=rows(body,"items",10000),units=rows(body,"units",3000),templates=rows(body,"templates",5000);Set<String> gids=new HashSet<>(),codes=new HashSet<>(),ids=new HashSet<>(),unitSymbols=new HashSet<>();
  for(Map<String,Object> g:groups){text(g,"id",100,true);text(g,"name",150,true);if(!gids.add(str(g.get("id"))))throw new EhrException(422,"分组ID重复");}
  for(Map<String,Object> i:items){text(i,"id",150,true);text(i,"code",100,true);text(i,"name",255,true);text(i,"unit",1000,false);text(i,"referenceRange",2000,false);if(!ids.add(str(i.get("id")))||!codes.add(str(i.get("code")).toLowerCase(Locale.ROOT)))throw new EhrException(422,"指标ID或编码重复");if(!gids.contains(str(i.get("groupId"))))throw new EhrException(422,"指标所属分组不存在");if(!Arrays.asList("all","male","female").contains(str(i.get("gender"))))throw new EhrException(422,"适用性别无效");}
  Set<String> rowIds=new HashSet<>();for(Map<String,Object> u:units){text(u,"id",100,true);text(u,"name",255,true);text(u,"symbol",1000,true);if(!unitSymbols.add(str(u.get("symbol")))||!rowIds.add(str(u.get("id"))))throw new EhrException(422,"单位符号或ID重复");}
  rowIds.clear();for(Map<String,Object> t:templates){text(t,"id",100,true);text(t,"name",255,true);text(t,"referenceRange",2000,true);if(!rowIds.add(str(t.get("id")))||!ids.contains(str(t.get("itemId"))))throw new EhrException(422,"模板ID重复或关联指标不存在");if(!Arrays.asList("all","male","female").contains(str(t.get("gender"))))throw new EhrException(422,"模板适用性别无效");}
  Map<String,Object> next=map("version",EhrService.num(old.get("version"))+1,"items",items,"groups",groups,"units",units,"templates",templates,"updatedAt",now(),"updatedBy",actor.user);byte[] data=json.writeValueAsBytes(next);if(data.length>8388608)throw new EhrException(422,"字典配置超过8MB限制");Path p=file(actor);Files.createDirectories(p.getParent());Path temp=Files.createTempFile(p.getParent(),"dictionary-",".tmp");try{Files.write(temp,data);try{Files.move(temp,p,StandardCopyOption.ATOMIC_MOVE,StandardCopyOption.REPLACE_EXISTING);}catch(AtomicMoveNotSupportedException ex){Files.move(temp,p,StandardCopyOption.REPLACE_EXISTING);}}finally{Files.deleteIfExists(temp);}return ok(next);
 }
}
