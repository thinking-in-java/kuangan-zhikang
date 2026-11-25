package com.yihe.framework.ehr;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import static com.yihe.framework.ehr.EhrController.ok;
@RestController
@RequestMapping("/ehr/api/communication/analytics")
public class CommunicationAnalyticsController {
 final EhrAccess access;final CommunicationAnalytics analytics;
 public CommunicationAnalyticsController(EhrAccess a,CommunicationAnalytics s){access=a;analytics=s;}
 @ModelAttribute public void headers(HttpServletResponse r){r.setHeader("Cache-Control","private, no-store");r.setHeader("X-Content-Type-Options","nosniff");}
 @GetMapping public Object dashboard(HttpServletRequest r,@RequestParam Map<String,String> q){return ok(analytics.dashboard(access.web(r),q));}
 @GetMapping("/export") public ResponseEntity<byte[]> export(HttpServletRequest r,@RequestParam Map<String,String> q){return ResponseEntity.ok().header("Cache-Control","no-store").header("Content-Disposition","attachment; filename=communication-analytics.csv").contentType(new MediaType("text","csv",java.nio.charset.StandardCharsets.UTF_8)).body(analytics.export(access.web(r),q));}
}
