package com.yihe.system.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Map;

public interface AssessmentService {
    public Map<String, Object> ajaxUploadExcel(InputStream in, MultipartFile file, HttpServletRequest request) throws Exception;
    public Map<String, Object> ajaxUploadExcel2(InputStream in, MultipartFile file, HttpServletRequest request) throws Exception;
}
