package com.yihe.framework.ehr;
import java.io.*;import java.nio.charset.StandardCharsets;import com.fasterxml.jackson.databind.*;import static com.yihe.framework.ehr.EhrAccess.*;
/** Accept only a complete terminal Responses event, never a partial text delta. */
final class ResponsesEventStream {
 static byte[] read(InputStream input)throws IOException{ObjectMapper mapper=new ObjectMapper();BufferedReader reader=new BufferedReader(new InputStreamReader(input,StandardCharsets.UTF_8));StringBuilder data=new StringBuilder();String line;int bytes=0;long deadline=System.nanoTime()+300_000_000_000L;
  while((line=reader.readLine())!=null){bytes+=line.getBytes(StandardCharsets.UTF_8).length+1;if(bytes>2_000_000)throw new EhrException(503,"MODEL_OUTPUT_TOO_LARGE");if(System.nanoTime()>deadline)throw new EhrException(503,"MODEL_TIMEOUT");if(line.isEmpty()){byte[] completed=event(mapper,data.toString());data.setLength(0);if(completed!=null)return completed;}else if(line.startsWith("data:")){if(data.length()>0)data.append('\n');data.append(line.substring(5).trim());}}
  byte[] completed=event(mapper,data.toString());if(completed!=null)return completed;throw new EhrException(503,"MODEL_INCOMPLETE");
 }
 static byte[] event(ObjectMapper mapper,String data)throws IOException{if(data.isEmpty()||"[DONE]".equals(data))return null;JsonNode event;try{event=mapper.readTree(data);}catch(Exception bad){throw new EhrException(503,"MODEL_INVALID_RESPONSE");}String type=event.path("type").asText();if("error".equals(type)||"response.failed".equals(type))throw new EhrException(503,"MODEL_STREAM_FAILED");if("response.completed".equals(type)||"response.incomplete".equals(type)){JsonNode response=event.path("response");if(!response.isObject())throw new EhrException(503,"MODEL_INVALID_RESPONSE");return mapper.writeValueAsBytes(response);}return null;}
}
