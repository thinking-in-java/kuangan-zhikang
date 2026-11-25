package com.yihe.web.controller.system;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequiresUser
@RequestMapping("/manage/analytics")
public class AnalyticsCenterController {
 @GetMapping({"", "/overview"}) public String overview(HttpServletResponse response) {
  response.setHeader("Cache-Control", "no-store"); return "analytics-center/overview";
 }
 @GetMapping("/occupational") public String occupational(HttpServletResponse response) {
  response.setHeader("Cache-Control", "no-store"); return "analytics-center/occupational";
 }
 @GetMapping("/disease") public String disease(HttpServletResponse response) {
  response.setHeader("Cache-Control", "no-store"); return "analytics-center/disease";
 }
 @GetMapping("/population") public String population(HttpServletResponse response) {
  response.setHeader("Cache-Control", "no-store"); return "analytics-center/population";
 }
 @GetMapping("/intervention-effect") public String interventionEffect(HttpServletResponse response) {
  response.setHeader("Cache-Control", "no-store"); return "analytics-center/intervention-effect";
 }
 @GetMapping("/followup-effect") public String followupEffect(HttpServletResponse response) {
  response.setHeader("Cache-Control", "no-store"); return "analytics-center/followup-effect";
 }
 @GetMapping("/worker-efficiency") public String workerEfficiency(HttpServletResponse response) {
  response.setHeader("Cache-Control", "no-store"); return "analytics-center/worker-efficiency";
 }
}
