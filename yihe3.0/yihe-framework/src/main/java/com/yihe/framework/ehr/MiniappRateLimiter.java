package com.yihe.framework.ehr;

import java.time.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import static com.yihe.framework.ehr.EhrAccess.*;

@Service
public class MiniappRateLimiter {
    private final EhrRepository db;
    public MiniappRateLimiter(EhrRepository db){this.db=db;}

    // Return the committed count; rejection is thrown by the caller afterwards.
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public int increment(String key,int seconds){
        long epoch=Instant.now().getEpochSecond();
        String start=LocalDateTime.ofEpochSecond(epoch/seconds*seconds,0,ZoneOffset.UTC).toString().replace('T',' ');
        byte[] hash=digest(bytes(key));
        db.exec("INSERT INTO a_miniapp_rate_limits(bucket_key,window_start,attempt_count) VALUES(?,?,1) ON DUPLICATE KEY UPDATE attempt_count=IF(window_start=VALUES(window_start),attempt_count+1,1),window_start=VALUES(window_start)",hash,start);
        return ((Number)db.one("SELECT attempt_count FROM a_miniapp_rate_limits WHERE bucket_key=?",hash).get("attempt_count")).intValue();
    }
}
