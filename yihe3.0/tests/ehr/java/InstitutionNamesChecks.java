package com.yihe.framework.ehr;
import java.util.*;
public class InstitutionNamesChecks {
    static void check(boolean test){if(!test)throw new AssertionError();}
    public static void main(String[] args){
        check("神木市中医医院体检中心".equals(InstitutionNames.resolve(Arrays.asList("神木市中医医院","神木市中医医院体检中心"))));
        check(InstitutionNames.resolve(Arrays.asList("第一人民医院","第二人民医院"))==null);
        check(InstitutionNames.resolve(Arrays.asList("测试医院体检中心","测试医院健康管理中心"))==null);
        check(InstitutionNames.resolve(Arrays.asList("测试医院","另一测试医院体检中心"))==null);
        check("测试 医院体检中心".equals(InstitutionNames.resolve(Arrays.asList("测试 医院","测试 医院体检中心"))));
        Map<String,Object> a=new HashMap<>();a.put("field_key","institution");a.put("value_raw","测试医院");
        Map<String,Object> b=new HashMap<>();b.put("field_key","institution");b.put("value_raw","测试医院体检中心");
        check("测试医院体检中心".equals(InstitutionNames.fromFields(Arrays.asList(a,b))));
        check(InstitutionNames.resolve(Collections.<String>emptyList())==null);
        System.out.println("PASS 7 raw institution resolution checks");
    }
}
