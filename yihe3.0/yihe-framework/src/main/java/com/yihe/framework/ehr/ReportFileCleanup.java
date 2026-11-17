package com.yihe.framework.ehr;

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Durable, post-commit erasure. Only app-owned relative objects and exact job cache prefixes. */
@Component
public class ReportFileCleanup {
    private final EhrRepository db;private final ModelRecognitionSettings settings;
    @Value("${ehr.storage-root:F:/upload}") String storage;
    @Value("${ehr.data-mode:real}") String mode;
    public ReportFileCleanup(EhrRepository db,ModelRecognitionSettings settings){this.db=db;this.settings=settings;}
    public synchronized void run(){
        for(Map<String,Object> task:db.all("SELECT * FROM a_report_file_cleanup_tasks WHERE data_mode=? AND (status='pending' OR (status='failed' AND updated_at<UTC_TIMESTAMP(6)-INTERVAL 30 SECOND) OR (target_kind='model_artifacts' AND status='done' AND updated_at<UTC_TIMESTAMP(6)-INTERVAL 10 MINUTE)) ORDER BY updated_at LIMIT 30",mode)){
            try{
                String ref=new String((byte[])task.get("target_ref"),StandardCharsets.UTF_8);
                if("source".equals(task.get("target_kind"))){
                    if(!ref.matches("[0-9a-fA-F-]{36}\\.source"))throw new IllegalArgumentException("INVALID_OBJECT_REF");
                    deleteInside(Paths.get(storage),ref);
                }else{
                    if(!UUID.fromString(ref).toString().equalsIgnoreCase(ref))throw new IllegalArgumentException("INVALID_JOB_REF");
                    Path root=Paths.get(settings.configFile).toAbsolutePath().normalize().getParent().resolve("model-results");
                    if(Files.isDirectory(root)){
                        try(DirectoryStream<Path> files=Files.newDirectoryStream(root,ref+".*")){
                            for(Path file:files)deleteInside(root,file.getFileName().toString());
                        }
                    }
                }
                db.exec("UPDATE a_report_file_cleanup_tasks SET status='done',attempts=attempts+1,last_error=NULL,updated_at=UTC_TIMESTAMP(6) WHERE cleanup_id=?",task.get("cleanup_id"));
            }catch(Exception failure){
                db.exec("UPDATE a_report_file_cleanup_tasks SET status='failed',attempts=attempts+1,last_error=?,updated_at=UTC_TIMESTAMP(6) WHERE cleanup_id=?",failure.getClass().getSimpleName(),task.get("cleanup_id"));
            }
        }
    }
    static void deleteInside(Path configuredRoot,String name)throws Exception{
        Path root=configuredRoot.toAbsolutePath().normalize(),target=root.resolve(name).normalize();
        if(!target.startsWith(root)||target.equals(root)||!target.getParent().equals(root))throw new IllegalArgumentException("INVALID_PATH");
        try{
            if(!Files.exists(target,LinkOption.NOFOLLOW_LINKS))return;
            if(Files.isSymbolicLink(target)||!Files.isRegularFile(target,LinkOption.NOFOLLOW_LINKS)||!target.toRealPath().startsWith(root.toRealPath()))throw new IllegalArgumentException("UNSAFE_OBJECT");
            Files.deleteIfExists(target);
        }catch(NoSuchFileException alreadyRemoved){/* Another instance completed the same durable task. */}
    }
}
