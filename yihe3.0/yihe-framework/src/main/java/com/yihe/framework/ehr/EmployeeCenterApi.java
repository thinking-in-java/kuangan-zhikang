package com.yihe.framework.ehr;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrController.ok;

/** Employee-center metadata is scoped and versioned; existing EHR remains the roster source. */
@RestController
@RequestMapping("/ehr/api/employee-center")
public class EmployeeCenterApi {
    private final EhrAccess access; private final EhrService ehr; private final EhrRepository db;
    private final ObjectMapper json=new ObjectMapper();
    @Value("${ehr.storage-root:${EHR_STORAGE_ROOT:D:/update}}") private String storage;
    public EmployeeCenterApi(EhrAccess access,EhrService ehr,EhrRepository db){this.access=access;this.ehr=ehr;this.db=db;}
    @ModelAttribute public void headers(HttpServletResponse response){response.setHeader("Cache-Control","private, no-store");}
    private Path file(EhrActor actor){String key=Base64.getUrlEncoder().withoutPadding().encodeToString(digest(bytes(actor.scope+":"+actor.mode)));return Paths.get(storage,".employee-center",key+".json");}
    @SuppressWarnings("unchecked") private Map<String,Object> read(EhrActor actor)throws IOException {
        Path path=file(actor);if(!Files.exists(path))return map("version",0,"groups",new ArrayList<>(),"tags",new ArrayList<>(),"departments",new ArrayList<>(),"positions",new ArrayList<>(),"assignments",new ArrayList<>(),"history",new ArrayList<>());
        if(Files.size(path)>2097152)throw new EhrException(500,"职工中心配置过大，请联系管理员");
        return json.readValue(Files.readAllBytes(path),Map.class);
    }
    @GetMapping("/configuration") public synchronized Object configuration(HttpServletRequest request)throws IOException{return ok(read(access.web(request)));}
    @PutMapping("/configuration") public synchronized Object save(HttpServletRequest request,@RequestBody Map<String,Object> body)throws IOException {
        EhrActor actor=access.web(request);Map<String,Object> old=read(actor);
        if(EhrService.num(body.get("version"))!=EhrService.num(old.get("version")))throw new EhrException(409,"配置已被其他人员修改，请刷新后重试");
        Map<String,Object> next=map("version",EhrService.num(old.get("version"))+1);
        for(String key:Arrays.asList("groups","tags","departments","positions","assignments","history")){
            Object rows=body.get(key);if(!(rows instanceof List)||((List<?>)rows).size()>5000)throw new EhrException(422,"配置列表无效或超过5000条");
            for(Object row:(List<?>)rows)if(!(row instanceof Map))throw new EhrException(422,"配置数据格式错误");
            next.put(key,rows);
        }
        next.put("updatedAt",now());next.put("updatedBy",actor.user);
        byte[] content=json.writeValueAsBytes(next);if(content.length>2097152)throw new EhrException(422,"配置超过2 MiB，请减少数据量");
        Path target=file(actor);Files.createDirectories(target.getParent());Path temp=Files.createTempFile(target.getParent(),"employee-",".tmp");
        try{Files.write(temp,content);try{Files.move(temp,target,StandardCopyOption.ATOMIC_MOVE,StandardCopyOption.REPLACE_EXISTING);}catch(AtomicMoveNotSupportedException ignored){Files.move(temp,target,StandardCopyOption.REPLACE_EXISTING);}}finally{Files.deleteIfExists(temp);}
        return ok(next);
    }
    @GetMapping("/people") public Object people(HttpServletRequest request){
        EhrActor actor=access.web(request);List<Map<String,Object>> people=new ArrayList<>();
        long total=db.count("SELECT COUNT(*)"+ehr.baseSql(),actor.scope,actor.mode);
        people.addAll(db.all(ehr.projection()+" ORDER BY p.created_at DESC,r.record_id DESC LIMIT 5000",actor.scope,actor.mode));
        Map<String,Map<String,Object>> bindings=new HashMap<>();
        for(Map<String,Object> row:db.all("SELECT b.record_id recordId,DATE_FORMAT(b.confirmed_at,'%Y-%m-%dT%H:%i:%sZ') confirmedAt,DATE_FORMAT(u.last_login_at,'%Y-%m-%dT%H:%i:%sZ') lastLoginAt FROM a_miniapp_employee_bindings b JOIN a_miniapp_users u ON u.mini_user_id=b.mini_user_id AND u.data_mode=b.data_mode WHERE b.scope_code=? AND b.data_mode=? AND b.binding_status='active'",actor.scope,actor.mode))bindings.put(String.valueOf(row.get("recordId")),row);
        for(Map<String,Object> row:people){Map<String,Object> binding=bindings.get(String.valueOf(row.get("id")));row.put("bound",binding!=null);if(binding!=null){row.put("boundAt",binding.get("confirmedAt"));row.put("lastLoginAt",binding.get("lastLoginAt"));}}
        return ok(map("items",people,"total",total,"truncated",people.size()<total));
    }
    @GetMapping("/import/template") public void template(HttpServletRequest request,HttpServletResponse response)throws IOException {
        access.web(request);response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");response.setHeader("Content-Disposition","attachment; filename=employee-import-template.xlsx");
        try(Workbook book=new org.apache.poi.xssf.usermodel.XSSFWorkbook()){
            Sheet sheet=book.createSheet("职工信息");String[] columns={"职工姓名","职工工号","手机号","所属单位","班组","岗位","性别","出生日期","职业危害因素","身份证号"};Row head=sheet.createRow(0);
            CellStyle textStyle=book.createCellStyle();textStyle.setDataFormat(book.createDataFormat().getFormat("@"));
            for(int i=0;i<columns.length;i++){head.createCell(i).setCellValue(columns[i]);sheet.setColumnWidth(i,6000);sheet.setDefaultColumnStyle(i,textStyle);}sheet.createFreezePane(0,1);book.write(response.getOutputStream());
        }
    }
    @PostMapping("/import/preview") public Object preview(HttpServletRequest request,@RequestParam("file") MultipartFile file)throws IOException {
        access.web(request);String name=file.getOriginalFilename();
        if(file.isEmpty()||file.getSize()>10485760||name==null||!name.toLowerCase(Locale.ROOT).matches(".*\\.(xlsx|xls)$"))throw new EhrException(422,"请选择10 MiB以内的 XLSX 或 XLS 文件");
        try(InputStream input=file.getInputStream();Workbook book=WorkbookFactory.create(input)){
            if(book.getNumberOfSheets()==0)throw new EhrException(422,"文件没有工作表");Sheet sheet=book.getSheetAt(0);
            if(sheet.getPhysicalNumberOfRows()>20000)throw new EhrException(422,"工作表空白行过多，请清理后重试");
            Row head=sheet.getRow(sheet.getFirstRowNum());if(head==null||head.getLastCellNum()<1||head.getLastCellNum()>100)throw new EhrException(422,"首行应包含1至100个字段名");
            DataFormatter formatter=new DataFormatter(Locale.CHINA);List<String> headers=new ArrayList<>();for(int i=0;i<head.getLastCellNum();i++)headers.add(formatter.formatCellValue(head.getCell(i)).trim());
            List<List<String>> rows=new ArrayList<>();List<Integer> rowNumbers=new ArrayList<>();for(int n=sheet.getFirstRowNum()+1;n<=sheet.getLastRowNum();n++){Row row=sheet.getRow(n);if(row==null)continue;List<String> values=new ArrayList<>();boolean nonempty=false;for(int c=0;c<headers.size();c++){Cell cell=row.getCell(c);String v=formatter.formatCellValue(cell).trim();if(cell!=null&&cell.getCellTypeEnum()==CellType.NUMERIC&&DateUtil.isCellDateFormatted(cell))v=new java.text.SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());if(v.length()>4000)throw new EhrException(422,"第"+(n+1)+"行单元格内容过长");if(cell!=null&&cell.getCellTypeEnum()==CellType.FORMULA)throw new EhrException(422,"请将第"+(n+1)+"行公式转换为值后导入");values.add(v);nonempty|=!v.isEmpty();}if(nonempty){rows.add(values);rowNumbers.add(n+1);if(rows.size()>5000)throw new EhrException(422,"单次最多导入5000位职工，请拆分文件");}}
            if(rows.isEmpty())throw new EhrException(422,"文件没有可导入的职工数据");return ok(map("headers",headers,"rows",rows,"rowNumbers",rowNumbers,"fileName",name));
        }catch(EhrException error){throw error;}catch(Exception error){throw new EhrException(422,"无法读取Excel，请确认文件格式和内容完整");}
    }
}
