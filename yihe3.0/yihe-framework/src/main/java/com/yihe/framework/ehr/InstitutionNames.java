package com.yihe.framework.ehr;

import java.util.*;
import java.util.regex.*;

/** Select a literal candidate spelling, without inventing aliases or changing evidence. */
public final class InstitutionNames {
    private InstitutionNames(){}
    private static final Pattern HOSPITAL=Pattern.compile("(.+医院)(体检中心|健康体检中心|健康管理中心|体检科|体检部)?");
    public static String resolve(Collection<String> values){
        Map<String,String> raw=new TreeMap<>();
        for(String value:values){
            if(value==null||value.trim().isEmpty())continue;
            String key=value.replaceAll("(?U)\\s+",""),text=value.trim();
            if(!raw.containsKey(key)||text.length()<raw.get(key).length())raw.put(key,text);
        }
        if(raw.size()==1)return raw.values().iterator().next();
        Set<String> bases=new HashSet<>(),departments=new HashSet<>();
        for(String name:raw.keySet()){
            Matcher match=HOSPITAL.matcher(name);if(!match.matches())return null;
            bases.add(match.group(1));if(match.group(2)!=null)departments.add(name);
        }
        return bases.size()==1&&departments.size()==1?raw.get(departments.iterator().next()):null;
    }
    public static String fromFields(Object fields){
        List<String> values=new ArrayList<>();
        if(fields instanceof List)for(Object row:(List<?>)fields)if(row instanceof Map){
            Map<?,?> field=(Map<?,?>)row;
            if("institution".equals(field.get("field_key"))&&field.get("value_raw") instanceof String)values.add((String)field.get("value_raw"));
        }
        return resolve(values);
    }
}
