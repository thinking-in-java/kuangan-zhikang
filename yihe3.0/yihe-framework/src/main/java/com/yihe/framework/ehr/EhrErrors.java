package com.yihe.framework.ehr;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import static com.yihe.framework.ehr.EhrAccess.map;
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages="com.yihe.framework.ehr")
public class EhrErrors {
    @ExceptionHandler(EhrException.class) public ResponseEntity<?> business(EhrException e){return error(e.status,e.getMessage());}
    @ExceptionHandler(DataIntegrityViolationException.class) public ResponseEntity<?> conflict(DataIntegrityViolationException e){return error(409,"工号、用户关联、幂等键已存在，或数据状态不允许此操作");}
    @ExceptionHandler({HttpMessageNotReadableException.class,MissingRequestHeaderException.class,IllegalArgumentException.class}) public ResponseEntity<?> input(Exception e){return error(400,"请求格式或必要请求头不正确");}
    @ExceptionHandler(MaxUploadSizeExceededException.class) public ResponseEntity<?> tooLarge(Exception e){return error(413,"上传文件超过请求大小限制");}
    @ExceptionHandler(Exception.class) public ResponseEntity<?> other(Exception e){org.slf4j.LoggerFactory.getLogger(EhrErrors.class).error("EHR request failed",e);return error(500,"处理失败，请稍后重试或检查服务日志");}
    private ResponseEntity<?> error(int status,String msg){return ResponseEntity.status(status).body(map("code",status,"msg",msg));}
}
