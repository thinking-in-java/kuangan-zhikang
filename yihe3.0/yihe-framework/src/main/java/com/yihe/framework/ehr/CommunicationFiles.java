package com.yihe.framework.ehr;

import java.util.*;
import java.nio.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.io.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

@Service
public class CommunicationFiles {
    final CommunicationService service;final EhrRepository db;
    @Value("${ehr.storage-root:${EHR_STORAGE_ROOT:D:/update}}") String storage;
    public CommunicationFiles(CommunicationService service,EhrRepository db){this.service=service;this.db=db;}
    Path root()throws IOException {Path base=Paths.get(storage).toAbsolutePath().normalize();Files.createDirectories(base);Path root=base.resolve("communication");require(!Files.isSymbolicLink(root),422,"附件存储目录不可用");Files.createDirectories(root);return root.toRealPath();}
    Path path(String id)throws IOException{return root().resolve(CommunicationService.uuid(id)+".source");}
    static String type(byte[] data,String name)throws IOException{
        String lower=name.toLowerCase(Locale.ROOT);String mime=null;
        if(data.length>=5&&new String(data,0,5,StandardCharsets.US_ASCII).equals("%PDF-")&&lower.endsWith(".pdf"))mime="application/pdf";
        if(data.length>=8&&(data[0]&255)==137&&data[1]==80&&data[2]==78&&data[3]==71&&lower.endsWith(".png"))mime="image/png";
        if(data.length>=3&&(data[0]&255)==255&&(data[1]&255)==216&&(data[2]&255)==255&&(lower.endsWith(".jpg")||lower.endsWith(".jpeg")))mime="image/jpeg";
        if(lower.endsWith(".txt")){try{String text=StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(data)).toString();require(text.indexOf('\0')<0,422,"文本文件内容无效");mime="text/plain";}catch(CharacterCodingException e){throw new EhrException(422,"文本附件须为UTF-8编码");}}
        require(mime!=null,422,"仅支持PDF、JPG、PNG和UTF-8文本，文件内容须与扩展名一致");
        if(mime.startsWith("image/")){try(javax.imageio.stream.ImageInputStream in=javax.imageio.ImageIO.createImageInputStream(new ByteArrayInputStream(data))){Iterator<javax.imageio.ImageReader> readers=javax.imageio.ImageIO.getImageReaders(in);require(readers.hasNext(),422,"图片内容不可读取");javax.imageio.ImageReader reader=readers.next();try{reader.setInput(in);require((long)reader.getWidth(0)*reader.getHeight(0)<=40000000,422,"图片像素过大，请压缩后上传");}finally{reader.dispose();}}catch(IOException e){throw new EhrException(422,"图片内容不完整或不可读取");}}
        return mime;
    }
    @Transactional public Map<String,Object> upload(EhrActor a,String cid,MultipartFile file)throws IOException{
        Map<String,Object> c=service.owned(a,cid,true);if(a.mobile&&"closed".equals(c.get("service_status"))){db.exec("UPDATE a_service_conversations SET service_status='open',row_version=row_version+1 WHERE conversation_id=?",cid);c.put("service_status","open");}service.writable(a,c);require(!file.isEmpty()&&file.getSize()<=20971520,413,"附件不能为空，单个文件最大20 MB");require(db.count("SELECT COUNT(*) FROM a_service_attachments WHERE conversation_id=? AND uploader_user=? AND message_id IS NULL",cid,a.user)<10,409,"有较多未发送附件，请先发送或移除");
        String name=file.getOriginalFilename()==null?"":file.getOriginalFilename().replaceAll("[\\\\/\\p{Cntrl}]","_");require(name.length()>0&&name.length()<=180,422,"文件名须为1至180个字符");byte[] content=file.getBytes();String mime=type(content,name),fid=id();Path path=path(fid);Files.write(path,content,StandardOpenOption.CREATE_NEW);
        if(TransactionSynchronizationManager.isSynchronizationActive())TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter(){public void afterCompletion(int status){if(status!=STATUS_COMMITTED)try{Files.deleteIfExists(path);}catch(IOException ignored){}}});
        db.insert("a_service_attachments",map("attachment_id",fid,"conversation_id",cid,"uploader_user",a.user,"original_name",name,"mime_type",mime,"byte_size",content.length,"sha256",hex(digest(content)),"created_at",now()));return map("id",fid,"name",name,"mime",mime,"size",content.length);
    }
    Map<String,Object> metadata(EhrActor a,String cid,String fid){service.owned(a,cid,false);Map<String,Object> f=db.one("SELECT * FROM a_service_attachments WHERE attachment_id=? AND conversation_id=?",CommunicationService.uuid(fid),cid);require(f!=null&&(f.get("message_id")!=null||a.user.equals(f.get("uploader_user"))),404,"附件不存在或没有访问权限");return f;}
    Path download(EhrActor a,String cid,String fid)throws IOException{Map<String,Object> f=metadata(a,cid,fid);Path p=path(fid);require(!Files.isSymbolicLink(p)&&Files.isRegularFile(p)&&Files.size(p)<=20971520,404,"附件原件不可用");require(hex(digest(Files.readAllBytes(p))).equals(f.get("sha256")),409,"附件校验失败，请联系管理员");return p;}
    @Transactional public Map<String,Object> remove(EhrActor a,String cid,String fid)throws IOException{Map<String,Object> c=service.owned(a,cid,true);service.writable(a,c);Map<String,Object> f=metadata(a,cid,fid);require(f.get("message_id")==null&&a.user.equals(f.get("uploader_user")),409,"已发送附件不能从消息中移除");Path p=path(fid);db.exec("DELETE FROM a_service_attachments WHERE attachment_id=?",fid);TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter(){public void afterCommit(){try{Files.deleteIfExists(p);}catch(IOException ignored){}}});return map("removed",true);}
}
