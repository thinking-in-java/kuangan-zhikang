package com.yihe.framework.ehr;

import java.util.*;
import static com.yihe.framework.ehr.EhrService.*;
import static com.yihe.framework.ehr.EhrAccess.map;

/** Validation of the full B-side 2.0.0 envelope. Never remaps a tenant or file. */
final class OcrResultContract {
    private final EhrRepository db;
    OcrResultContract(EhrRepository db){this.db=db;}

    List<Map<String,Object>> sourceFiles(Map<String,Object> job){
        return db.all("SELECT s.source_file_id,LOWER(HEX(s.sha256)) sha256,s.page_count FROM a_report_revision_sources r JOIN a_report_sources s ON s.source_file_id=r.source_file_id WHERE r.report_id=? AND r.revision=? ORDER BY s.source_file_id",job.get("report_id"),job.get("revision"));
    }
    static int positiveInteger(Object value,String path){
        String s=str(value);require(s.matches("[1-9][0-9]{0,8}"),422,path+"须为正整数");
        return Integer.parseInt(s);
    }
    @SuppressWarnings("unchecked") static List<Map<String,Object>> rows(Object value,String path){
        require(value instanceof List,422,path+"应为数组");List<?> rows=(List<?>)value;require(rows.size()<=2000,422,path+"记录过多");
        for(Object row:rows)require(row instanceof Map,422,path+"元素应为对象");
        return (List<Map<String,Object>>)value;
    }
    private void member(Map<String,Object> row,String key,Object expected,String path){
        if(row.get(key)!=null)require(Objects.equals(expected,row.get(key)),422,path+"."+key+"与A端任务不一致");
    }
    private void context(Map<String,Object> row,Map<String,Object> job,String path){
        member(row,"scope_code",job.get("scope_code"),path);member(row,"data_mode",job.get("data_mode"),path);
        member(row,"report_id",job.get("report_id"),path);member(row,"contract_version","2.0.0",path);
        member(row,"dictionary_version","2.0.0",path);
        if(row.get("revision")!=null)require(positiveInteger(row.get("revision"),path+".revision")==num(job.get("revision")),422,path+".revision与A端任务不一致");
    }
    private void identifiers(List<Map<String,Object>> rows,String key,String path){
        Set<String> seen=new HashSet<>();int index=0;
        for(Map<String,Object> row:rows){index++;Object value=row.get(key);if(value==null)continue;
            String id=str(value);require(id.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"),422,path+"["+index+"]."+key+"须为UUID");
            require(seen.add(id.toLowerCase(Locale.ROOT)),422,path+"存在重复"+key);
        }
    }
    void validate(Map<String,Object> job,Map<String,Object> body){
        context(body,job,"result");
        if(body.get("record_id")!=null){Map<String,Object> report=db.one("SELECT record_id FROM a_health_reports WHERE report_id=? AND revision=?",job.get("report_id"),job.get("revision"));member(body,"record_id",report.get("record_id"),"result");}
        if(body.containsKey("source_files")){
            Map<String,Map<String,Object>> expected=new HashMap<>();for(Map<String,Object> file:sourceFiles(job))expected.put(str(file.get("source_file_id")),file);
            List<Map<String,Object>> files=rows(body.get("source_files"),"source_files");require(files.size()==expected.size(),422,"source_files与A端原件数量不一致");Set<String> seen=new HashSet<>();
            for(Map<String,Object> file:files){String id=str(file.get("source_file_id"));Map<String,Object> source=expected.get(id);
                require(source!=null&&seen.add(id),422,"source_files存在未关联或重复原件");
                String hash=str(file.get("sha256"));require(hash.matches("[0-9a-fA-F]{64}")&&hash.equalsIgnoreCase(str(source.get("sha256"))),422,"source_files.sha256与已保存原件不一致");
                require(positiveInteger(file.get("page_count"),"source_files.page_count")==num(source.get("page_count")),422,"source_files.page_count与已保存原件不一致");
            }
        }
        String[] arrays={"items","conclusions","advice"};String[] keys={"item_id","conclusion_id","advice_id"};
        for(int n=0;n<arrays.length;n++){
            List<Map<String,Object>> list=rows(body.get(arrays[n]),arrays[n]);identifiers(list,keys[n],arrays[n]);Set<Integer> ordinals=new HashSet<>();int index=0;
            for(Map<String,Object> row:list){index++;String path=arrays[n]+"["+index+"]";context(row,job,path);
                if(n==0){int ordinal=row.get("ordinal")==null?index:positiveInteger(row.get("ordinal"),path+".ordinal");require(ordinals.add(ordinal),422,"items.ordinal存在重复序号");}
                if(n==2){member(row,"source_kind","pdf",path);require(row.get("author_ref")==null&&row.get("review_id")==null,422,path+"不能由OCR指定医生作者或审核记录");}
            }
        }
    }
}
