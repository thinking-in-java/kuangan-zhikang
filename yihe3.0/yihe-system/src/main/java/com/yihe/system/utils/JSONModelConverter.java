package com.yihe.system.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONModelConverter
{
    private static final Logger logger = LoggerFactory.getLogger(JSONModelConverter.class);
    
    public static String modelToJSONString(Object model)
    {
        String strJson="";
        ObjectMapper mapper = new ObjectMapper();  
        try
        {
            strJson =  mapper.writeValueAsString(model);
        }
        catch (IOException e)
        {
            logger.error(e.getMessage());
        }
        
        return strJson;
    }
    
    public static <T> T  jSONStringToModel(String strJson, Class<T> clasz)
    { 
        ObjectMapper mapper = new ObjectMapper();  
        T obj = null;
        try
        {
            obj =  mapper.readValue(strJson,clasz);
        }
        catch (IOException e)
        {
            logger.error(e.getMessage());
        } 
        return obj;
    }
}
