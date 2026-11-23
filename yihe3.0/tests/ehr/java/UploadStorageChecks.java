package com.yihe.framework.ehr;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.support.TransactionTemplate;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Actual upload/download against the explicitly selected storage root; synthetic SQL rolls back. */
public class UploadStorageChecks {
    static int count;
    static void check(String name,boolean ok){if(!ok)throw new AssertionError(name);count++;System.out.println("PASS "+name);}
    @SuppressWarnings("unchecked") public static void main(String[] args)throws Exception{
        String configured=System.getenv("EHR_UPLOAD_TEST_ROOT");if(configured==null)throw new IllegalArgumentException("Explicit EHR_UPLOAD_TEST_ROOT required");Path root=Paths.get(configured).toAbsolutePath().normalize();if(!Files.isDirectory(root))throw new IllegalArgumentException("Storage must exist");
        BufferedImage image=new BufferedImage(24,24,BufferedImage.TYPE_INT_RGB);image.setRGB(1,1,new Random().nextInt());ByteArrayOutputStream buffer=new ByteArrayOutputStream();ImageIO.write(image,"png",buffer);final byte[] raw=buffer.toByteArray();
        MultipartFile upload=new MultipartFile(){public String getName(){return "file";}public String getOriginalFilename(){return "synthetic-storage-check.png";}public String getContentType(){return "image/png";}public boolean isEmpty(){return false;}public long getSize(){return raw.length;}public byte[] getBytes(){return raw;}public InputStream getInputStream(){return new ByteArrayInputStream(raw);}public void transferTo(File file)throws IOException{Files.write(file.toPath(),raw);}};
        DriverManagerDataSource ds=new DriverManagerDataSource(System.getenv("EHR_JDBC_URL"),System.getenv("EHR_DB_USER"),System.getenv("EHR_DB_PASSWORD"));EhrRepository db=new EhrRepository(ds);EhrService ehr=new EhrService(db);ehr.storage=root.toString();EhrActor actor=new EhrActor("storage-fixture","STORAGE_"+id().substring(0,8),"synthetic",true,false);final Path[] stored={null};
        new TransactionTemplate(new DataSourceTransactionManager(ds)).execute(tx->{try{
            String record=text(HealthAgentChecks.fixture(ehr,actor,"ST_"+id().substring(0,8)),"id"),key=id();Map<String,Object> report=ehr.upload(actor,record,upload,key,"backend_file");Map<String,Object> file=((List<Map<String,Object>>)report.get("files")).get(0);String source=text(file,"id");stored[0]=ehr.download(actor,source);
            check("new_upload_stored_in_selected_root",stored[0].getParent().equals(root));check("new_upload_bytes_and_digest_preserved",Arrays.equals(raw,Files.readAllBytes(stored[0])));
            String ref=text(db.one("SELECT CONVERT(object_ref USING utf8mb4) ref FROM a_report_sources WHERE source_file_id=?",source),"ref");check("stored_reference_remains_relative",!Paths.get(ref).isAbsolute()&&Paths.get(ref).getNameCount()==1&&!ref.contains(":"));
            check("upload_still_queued_and_unreviewed","queued".equals(((Map<?,?>)report.get("ocrTask")).get("state"))&&"unreviewed".equals(report.get("reviewStatus")));
            check("idempotent_upload_reuses_report",report.get("id").equals(ehr.upload(actor,record,upload,key,"backend_file").get("id")));
            try{ehr.download(new EhrActor(actor.user,"STORAGE_OTHER","synthetic",true,false),source);throw new AssertionError("cross-org download");}catch(EhrException error){check("storage_move_preserves_download_authorization",error.status==404);}
            return null;
        }catch(IOException error){throw new RuntimeException(error);}finally{tx.setRollbackOnly();}});
        check("rollback_removed_only_synthetic_source",stored[0]!=null&&!Files.exists(stored[0]));check("synthetic_upload_records_rolled_back",db.count("SELECT COUNT(*) FROM a_report_sources WHERE scope_code=?",actor.scope)==0);System.out.println("PASS Upload storage checks: "+count+"; synthetic upload rolled back; no model requests");
    }
}
