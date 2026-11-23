package com.yihe.framework.ehr;

import java.nio.file.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.yihe.framework.ehr.EhrAccess.*;

/** Synthetic local configuration only; never contacts an API or reads real credentials. */
public class DigitalWorkerModelIsolationChecks {
    static int count;
    static void check(String label,boolean ok){if(!ok)throw new AssertionError(label);count++;System.out.println("PASS "+label);}
    static void point(DigitalWorkerModelClient client,Path path)throws Exception{java.lang.reflect.Field field=DigitalWorkerModelClient.class.getDeclaredField("configFile");field.setAccessible(true);field.set(client,path.toString());}
    public static void main(String[] args)throws Exception{
        Path dir=Files.createTempDirectory("worker-config-isolation-"),ocr=dir.resolve("ocr.json"),worker=dir.resolve("employee.json");ObjectMapper json=new ObjectMapper();
        ModelRecognitionSettings original=new ModelRecognitionSettings();original.configFile=ocr.toString();
        Map<String,Object> originalConfig=map("baseUrl","https://example.test/v1","apiKey","synthetic-ocr-key","model","ocr-alpha");
        try{
            Files.write(ocr,json.writeValueAsBytes(originalConfig));DigitalWorkerModelClient client=new DigitalWorkerModelClient(original);
            check("missing_dedicated_config_does_not_fall_back_to_ocr",Boolean.FALSE.equals(client.status().get("configured")));
            Map<String,Object> dedicated=new LinkedHashMap<>(originalConfig);dedicated.put("apiKey","synthetic-employee-key");dedicated.put("model","employee-alpha");Files.write(worker,json.writeValueAsBytes(dedicated));point(client,worker);
            check("dedicated_config_is_used","employee-alpha".equals(client.status().get("model")));
            originalConfig.put("model","ocr-beta");Files.write(ocr,json.writeValueAsBytes(originalConfig));check("ocr_changes_do_not_affect_employee","employee-alpha".equals(client.status().get("model")));
            dedicated.put("model","employee-beta");Files.write(worker,json.writeValueAsBytes(dedicated));check("employee_changes_do_not_affect_ocr","ocr-beta".equals(original.read().get("model"))&&"employee-beta".equals(client.status().get("model")));
            Files.write(worker,bytes("invalid-json"));check("invalid_dedicated_config_never_uses_ocr",Boolean.FALSE.equals(client.status().get("configured")));
            Files.delete(worker);check("deleted_dedicated_config_never_uses_ocr",Boolean.FALSE.equals(client.status().get("configured")));
            System.out.println("PASS Digital worker model isolation checks: "+count+"; synthetic files only; no network");
        }finally{Files.deleteIfExists(worker);Files.deleteIfExists(ocr);Files.deleteIfExists(dir);}
    }
}
