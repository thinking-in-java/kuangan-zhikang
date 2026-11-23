package com.yihe.framework.config;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.catalina.*;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

/** Real embedded Tomcat multipart parsing; no business database or model calls. */
public class UploadTempDirectoryChecks {
    private static int checks;
    private static void check(boolean valid, String name) {
        if (!valid) throw new AssertionError(name);
        checks++;
        System.out.println("PASS " + name);
    }
    private static void awaitCleanup(Path uploads) throws Exception {
        for(int attempt=0;attempt<200;attempt++) {
            try(Stream<Path> files=Files.list(uploads)) { if(files.count()==0)return; }
            Thread.sleep(10);
        }
        throw new AssertionError("Multipart part cleanup did not finish");
    }
    private static String post(int port, int expected) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL("http://127.0.0.1:"+port+"/upload").openConnection();
        connection.setRequestMethod("POST"); connection.setDoOutput(true);
        connection.setConnectTimeout(5000); connection.setReadTimeout(5000);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=UploadRecoveryBoundary");
        byte[] body=("--UploadRecoveryBoundary\r\nContent-Disposition: form-data; name=\"file\"; filename=\"probe.bin\"\r\nContent-Type: application/octet-stream\r\n\r\nupload-directory-recovery\r\n--UploadRecoveryBoundary--\r\n").getBytes(StandardCharsets.UTF_8);
        connection.setFixedLengthStreamingMode(body.length);
        try (OutputStream out=connection.getOutputStream()) { out.write(body); }
        check(connection.getResponseCode()==expected, "HTTP status " + expected);
        try (InputStream in=expected<400?connection.getInputStream():connection.getErrorStream(); ByteArrayOutputStream out=new ByteArrayOutputStream()) {
            byte[] buffer=new byte[1024]; int n; while((n=in.read(buffer))!=-1)out.write(buffer,0,n);
            return new String(out.toByteArray(),StandardCharsets.UTF_8);
        } finally { connection.disconnect(); }
    }
    public static void main(String[] args) throws Exception {
        Path root=Files.createTempDirectory("upload-temp-check-"),uploads=root.resolve("multipart");
        Tomcat tomcat=new Tomcat(); tomcat.setBaseDir(root.resolve("tomcat").toString()); tomcat.setPort(0);
        try {
            Context context=tomcat.addContext("",root.toString());
            Wrapper servlet=Tomcat.addServlet(context,"upload",new HttpServlet(){
                @Override protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {
                    Part part=request.getPart("file");
                    try(InputStream in=part.getInputStream();OutputStream out=response.getOutputStream()){
                        byte[] buf=new byte[1024];int n;while((n=in.read(buf))!=-1)out.write(buf,0,n);
                    } finally { part.delete(); }
                }
            });
            servlet.setMultipartConfigElement(new MultipartConfigElement(uploads.toString(),4096,8192,0));
            context.addServletMappingDecoded("/upload","upload");
            FilterDef definition=new FilterDef();definition.setFilterName("upload-temp");definition.setFilter(new UploadTempDirectoryFilter(uploads.toString()));context.addFilterDef(definition);
            FilterMap mapping=new FilterMap();mapping.setFilterName("upload-temp");mapping.addURLPattern("/*");context.addFilterMapBefore(mapping);
            check(Files.isDirectory(uploads),"startup creates private multipart staging");
            tomcat.getConnector();tomcat.start();int port=tomcat.getConnector().getLocalPort();
            check(post(port,200).equals("upload-directory-recovery"),"real multipart bytes preserved");
            awaitCleanup(uploads);
            Files.delete(uploads);check(!Files.exists(uploads),"reproduce missing directory during runtime");
            check(post(port,200).equals("upload-directory-recovery")&&Files.isDirectory(uploads),"request recreates missing directory before Tomcat parsing");
            awaitCleanup(uploads);
            Files.delete(uploads);Files.write(uploads,new byte[]{1});
            String error=post(port,503);check(error.contains("\"code\":503")&&!error.contains(root.toString()),"unavailable storage returns structured error without server path");
            Files.delete(uploads);check(post(port,200).equals("upload-directory-recovery"),"upload recovers after storage restored");
            awaitCleanup(uploads);
            try(Stream<Path> files=Files.list(uploads)){check(files.count()==0,"multipart parts cleaned after request");}
            System.out.println("PASS Upload temporary directory checks: "+checks);
        } finally {
            try { tomcat.stop();tomcat.destroy(); } finally {
                try(Stream<Path> paths=Files.walk(root)){for(Path path:(Iterable<Path>)paths.sorted(Comparator.reverseOrder())::iterator)Files.deleteIfExists(path);}
            }
        }
    }
}
