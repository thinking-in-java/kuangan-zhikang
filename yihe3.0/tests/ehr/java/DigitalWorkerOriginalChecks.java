package com.yihe.framework.ehr;

import java.util.*;
import static com.yihe.framework.ehr.EhrAccess.*;

/** Literal source-evidence tests. No database, patient data or model calls. */
public class DigitalWorkerOriginalChecks {
    static int checks;
    static void check(String label,boolean ok){if(!ok)throw new AssertionError(label);checks++;System.out.println("PASS "+label);}
    public static void main(String[] args){
        Map<String,Object> field=map("id","fixture-item","group","item","label","合成项目","value","8.2","unit","mmol/L","referenceRange","3.9-6.1","abnormal","H");
        Map<String,Object> found=map("id","fixture-item","sourceId","fixture-source","page",1,"quote","合成项目 8.2 mmol/L 3.9-6.1 H","originalValue","8.2","originalUnit","mmol/L","originalRange","3.9-6.1","originalAbnormal","H");
        List<Map<String,Object>> pages=Arrays.asList(map("sourceId","fixture-source","page",1,"text","标题\n合成项目 8.2 mmol/L 3.9-6.1 H\n页脚"));
        check("literal_row_proof_matches","matched".equals(DigitalWorkerOriginalCheck.validate(field,found,pages).get("status")));
        Map<String,Object> changed=new LinkedHashMap<>(field);changed.put("value","9.2");
        check("different_value_detected","different".equals(DigitalWorkerOriginalCheck.validate(changed,found,pages).get("status")));
        changed=new LinkedHashMap<>(field);changed.put("unit","mg/dL");
        check("unit_not_silently_converted","different".equals(DigitalWorkerOriginalCheck.validate(changed,found,pages).get("status")));
        changed=new LinkedHashMap<>(field);changed.put("referenceRange","4-8");
        check("reference_range_compared","different".equals(DigitalWorkerOriginalCheck.validate(changed,found,pages).get("status")));
        Map<String,Object> invalid=new LinkedHashMap<>(found);invalid.put("quote","模型编造的 合成项目 8.2 mmol/L 3.9-6.1 H");
        check("invented_quote_cannot_pass","unverified".equals(DigitalWorkerOriginalCheck.validate(field,invalid,pages).get("status")));
        invalid=new LinkedHashMap<>(found);invalid.put("page",2);
        check("wrong_page_cannot_pass","unverified".equals(DigitalWorkerOriginalCheck.validate(field,invalid,pages).get("status")));
        invalid=new LinkedHashMap<>(found);invalid.put("sourceId","other-source");
        check("wrong_original_cannot_pass","unverified".equals(DigitalWorkerOriginalCheck.validate(field,invalid,pages).get("status")));
        invalid=new LinkedHashMap<>(found);invalid.put("originalValue","9.2");
        check("value_not_in_quote_cannot_pass","unverified".equals(DigitalWorkerOriginalCheck.validate(field,invalid,pages).get("status")));
        List<Map<String,Object>> scanned=Arrays.asList(map("sourceId","fixture-source","page",1,"text","","image","fixture-image"));
        check("scan_reading_requires_manual_confirmation","unverified".equals(DigitalWorkerOriginalCheck.validate(field,found,scanned).get("status")));
        check("missing_model_row_stays_unverified","unverified".equals(DigitalWorkerOriginalCheck.validate(field,null,pages).get("status")));
        changed=new LinkedHashMap<>(field);changed.put("label","其他项目");
        check("wrong_project_mapping_cannot_pass","unverified".equals(DigitalWorkerOriginalCheck.validate(changed,found,pages).get("status")));
        check("literal_match_reads_the_original_row",DigitalWorkerOriginalCheck.literalMatch(field,Arrays.asList(field),pages)!=null);
        Map<String,Object> neighbor=map("id","neighbor","group","item","label","相邻项目","value","8.2");
        List<Map<String,Object>> separated=Arrays.asList(map("sourceId","fixture-source","page",1,"text","合成项目 9.9 mmol/L 3.9-6.1 H 相邻项目 8.2"));
        check("cannot_borrow_neighbor_value",DigitalWorkerOriginalCheck.literalMatch(field,Arrays.asList(field,neighbor),separated)==null);
        List<Map<String,Object>> numeric=Arrays.asList(map("sourceId","fixture-source","page",1,"text","合成项目 18.2 mmol/L 3.9-6.1 H"));
        check("numeric_substring_cannot_match",DigitalWorkerOriginalCheck.literalMatch(field,Arrays.asList(field),numeric)==null);
        changed=new LinkedHashMap<>(field);changed.put("referenceRange","3.9–6.1");
        check("print_separator_not_content_error","formatting".equals(DigitalWorkerOriginalCheck.validate(changed,found,pages).get("status")));
        changed=new LinkedHashMap<>(field);changed.put("group","conclusion");changed.put("value","完整结论，不能用一个原文片段代替整段");
        check("partial_paragraph_not_confirmed_difference","unverified".equals(DigitalWorkerOriginalCheck.validate(changed,found,pages).get("status")));
        System.out.println("PASS Original evidence checks: "+checks);
    }
}
