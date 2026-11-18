package com.yihe.framework.ehr;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Responses API only. Provider errors never return keys, raw request data or headers. */
@Component
public class HealthAgentClient {
    final HealthAgentSettings settings; final ObjectMapper json=new ObjectMapper();
    final java.util.concurrent.Semaphore modelSlots=new java.util.concurrent.Semaphore(3,true);
    public HealthAgentClient(HealthAgentSettings settings){this.settings=settings;json.configure(com.fasterxml.jackson.core.JsonGenerator.Feature.ESCAPE_NON_ASCII,true);}
    @SuppressWarnings("unchecked") public Map<String,Object> respond(List<Object> input,List<Object> tools,String instructions,long deadline){
        boolean acquired=false;
        try{try{acquired=modelSlots.tryAcquire(Math.max(1,deadline-System.currentTimeMillis()),java.util.concurrent.TimeUnit.MILLISECONDS);}catch(InterruptedException e){Thread.currentThread().interrupt();throw new EhrException(503,"智能体处理已中断，请稍后重试");}
        require(acquired,429,"模型服务繁忙，请稍后重试");return request(input,tools,instructions,deadline);
        }finally{if(acquired)modelSlots.release();}
    }
    @SuppressWarnings("unchecked") Map<String,Object> request(List<Object> input,List<Object> tools,String instructions,long deadline){
        Map<String,Object> c=settings.validated();String base=text(c,"baseUrl").replaceAll("/+$","");
        String url=base.endsWith("/responses")?base:base+"/responses";
        Map<String,Object> body=map("model",c.get("model"),"input",input,"instructions",instructions,"tools",tools,"tool_choice","auto","parallel_tool_calls",false,"store",false,"max_output_tokens",4096,"include",Arrays.asList("reasoning.encrypted_content"));
        if(!text(c,"serviceTier").isEmpty())body.put("service_tier",c.get("serviceTier"));
        if(!text(c,"reasoningEffort").isEmpty())body.put("reasoning",map("effort",c.get("reasoningEffort")));
        for(int attempt=0;attempt<2;attempt++){
        HttpURLConnection conn=null;long started=System.currentTimeMillis();
        try{
            int remaining=(int)Math.min(60000,deadline-System.currentTimeMillis());require(remaining>500,504,"分析超时，请缩小查询范围后重试");
            conn=(HttpURLConnection)new URL(url).openConnection();conn.setInstanceFollowRedirects(false);conn.setConnectTimeout(Math.min(10000,remaining));conn.setReadTimeout(remaining);
            conn.setRequestMethod("POST");conn.setDoOutput(true);conn.setRequestProperty("Content-Type","application/json; charset=utf-8");conn.setRequestProperty("Accept","application/json");conn.setRequestProperty("User-Agent","KuanganZhikang-Agent/1.0");conn.setRequestProperty("Authorization","Bearer "+text(c,"apiKey"));
            try(OutputStream out=conn.getOutputStream()){out.write(json.writeValueAsBytes(body));}
            int status=conn.getResponseCode();if(status!=200){org.slf4j.LoggerFactory.getLogger(getClass()).warn("Agent provider HTTP {} after {}ms, attempt {}",status,System.currentTimeMillis()-started,attempt+1);if(attempt==0&&Arrays.asList(429,502,503,504).contains(status)&&deadline-System.currentTimeMillis()>12000)continue;throw new EhrException(502,"智能体模型接口返回HTTP "+status+"，请检查模型权限及Responses工具调用支持");}
            byte[] raw;try(InputStream in=conn.getInputStream();ByteArrayOutputStream out=new ByteArrayOutputStream()){byte[] b=new byte[8192];int n;while((n=in.read(b))!=-1){require(out.size()+n<=2097152,502,"模型返回超出大小限制");out.write(b,0,n);}raw=out.toByteArray();}
            Map<String,Object> result=json.readValue(raw,Map.class);
            require(result.get("output") instanceof List,502,"模型未返回Responses output，请核对中转站协议");
            require(!"incomplete".equals(result.get("status"))&&result.get("error")==null,502,"模型回复不完整，请缩小查询范围后重试");return result;
        }catch(SocketTimeoutException e){if(attempt==0&&deadline-System.currentTimeMillis()>12000)continue;throw new EhrException(504,"大模型响应超时，请稍后重试");}
        catch(IOException e){if(attempt==0&&deadline-System.currentTimeMillis()>12000)continue;throw new EhrException(502,"无法连接智能体模型服务，请检查网络和配置");}
        finally{if(conn!=null)conn.disconnect();}
        }
        throw new EhrException(502,"模型服务暂时不可用，请稍后重试");
    }
    @SuppressWarnings("unchecked") static String answer(List<Object> output){StringBuilder s=new StringBuilder();for(Object item:output){if(!(item instanceof Map))continue;Map<String,Object> m=(Map<String,Object>)item;if(!"message".equals(m.get("type"))||!(m.get("content") instanceof List))continue;for(Object part:(List<?>)m.get("content")){if(!(part instanceof Map))continue;Map<?,?> p=(Map<?,?>)part;if("output_text".equals(p.get("type")))s.append(str(p.get("text")));if("refusal".equals(p.get("type")))s.append(str(p.get("refusal")));}}return s.toString();}
}
