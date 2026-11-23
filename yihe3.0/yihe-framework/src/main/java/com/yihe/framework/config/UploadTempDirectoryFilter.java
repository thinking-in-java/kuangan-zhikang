package com.yihe.framework.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Keep multipart staging independent of Windows' disposable Tomcat temp directory. */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UploadTempDirectoryFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(UploadTempDirectoryFilter.class);
    private final Path directory;

    public UploadTempDirectoryFilter(
            @Value("${spring.servlet.multipart.location:${ehr.storage-root:${EHR_STORAGE_ROOT:D:/update}}/.tmp/uploads}") String location) {
        directory = Paths.get(location).toAbsolutePath().normalize();
        try {
            ensureDirectory();
        } catch (IOException error) {
            throw new IllegalStateException("Cannot initialize multipart upload directory", error);
        }
    }

    private void ensureDirectory() throws IOException {
        Files.createDirectories(directory);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IOException("Multipart upload directory is not writable");
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String type = request.getContentType();
        if (type != null && type.toLowerCase(Locale.ROOT).startsWith("multipart/")) {
            try {
                // Tomcat 9.0.13 does not recreate a removed multipart location itself.
                ensureDirectory();
            } catch (IOException error) {
                LOG.error("Cannot prepare multipart upload directory", error);
                response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().write("{\"code\":503,\"msg\":\"上传临时目录不可用，请检查服务存储空间和写入权限后重试\"}");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
