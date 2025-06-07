package com.yihe.web.controller.system;


import com.itextpdf.html2pdf.HtmlConverter;

import com.itextpdf.text.pdf.PdfWriter;
import com.yihe.system.domain.NewEmployeePersonalInformationCard;
import com.yihe.system.service.NewEmployeePersonalInformationCardService;
import com.yihe.system.utils.Result;
import com.yihe.web.controller.tool.HtmlToPdf;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.jsoup.nodes.Document ;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/NewEmployeePersonalInformationCardController")
public class NewEmployeePersonalInformationCardController {

    @Resource
    NewEmployeePersonalInformationCardService newEmployeePersonalInformationCardService;

    @RequestMapping(value = "/selectPage")
    @ResponseBody
    public Result selectRegInfo(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard, @RequestParam(required = false, defaultValue = "1") int page, @RequestParam(required = false, defaultValue = "5") int limit){
        Subject subject = SecurityUtils.getSubject();
        newEmployeePersonalInformationCard.setBegin((page-1)*limit);
        newEmployeePersonalInformationCard.setLimit(limit);
        String dates=newEmployeePersonalInformationCard.getTimes();

        int begin=(page-1)*limit;
        if(dates==null||"".equals(dates)) {

        }else {
            newEmployeePersonalInformationCard.setStartTime(dates.substring(0,19));
            newEmployeePersonalInformationCard.setEndTime(dates.substring(22,dates.length()-1));
        }
        List<NewEmployeePersonalInformationCard> newMedicalExaminationRecords1= newEmployeePersonalInformationCardService.selectByPage(newEmployeePersonalInformationCard);

        Map<String, Object> rsMap = new HashMap<String, Object>();
        rsMap.put("count", newEmployeePersonalInformationCardService.selectByPageCount(newEmployeePersonalInformationCard));
        rsMap.put("code", 0);
        rsMap.put("msg", "ok");
        rsMap.put("data", newMedicalExaminationRecords1);
        return Result.ok(rsMap);
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Result selectById(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard){
        return Result.ok(newEmployeePersonalInformationCardService.selectById(newEmployeePersonalInformationCard));
    }

    @RequestMapping(value = "/selectId")
    @ResponseBody
    public Result selectId(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard){
        return Result.ok(newEmployeePersonalInformationCardService.selectId(newEmployeePersonalInformationCard));
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public Result insert(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard){
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newEmployeePersonalInformationCard.setTime(dfs.format(new Date()));
        int inserCount=newEmployeePersonalInformationCardService.inserCount(newEmployeePersonalInformationCard);
        if(inserCount>0) return Result.error("已存在该用户档案");
        return Result.ok(newEmployeePersonalInformationCardService.insetNew(newEmployeePersonalInformationCard));
    }

    @RequestMapping(value = "/updateById")
    @ResponseBody
    public Result updateById(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard){
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newEmployeePersonalInformationCard.setTime(dfs.format(new Date()));
        return Result.ok(newEmployeePersonalInformationCardService.updateNew(newEmployeePersonalInformationCard));
    }


    @RequestMapping(value = "/deleteById")
    @ResponseBody
    public Result deleteById(NewEmployeePersonalInformationCard newEmployeePersonalInformationCard){
        return Result.ok(newEmployeePersonalInformationCardService.deleteById(newEmployeePersonalInformationCard));
    }

    @RequestMapping(value = "/getpdf", method = RequestMethod.POST)
    @ResponseBody
    public Result gaoxueyaDataInfo2(@RequestBody NewEmployeePersonalInformationCard newEmployeePersonalInformationCard, HttpServletResponse response) {
        String basePath = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1) + "temp/";
        List<Path> directoriesToDelete = new ArrayList<>(); // 存储要删除的目录
        String timestamp = String.valueOf(System.currentTimeMillis());
        for (int i : newEmployeePersonalInformationCard.getIds()) {
            NewEmployeePersonalInformationCard newEmployeePersonalInformationCard1 = new NewEmployeePersonalInformationCard();
            newEmployeePersonalInformationCard1.setId(i);
            NewEmployeePersonalInformationCard newdepate = newEmployeePersonalInformationCardService.selectById(newEmployeePersonalInformationCard1);

            String name = newdepate.getName();
            String directoryPath = basePath + name + "/"; // 创建以名字为目录的路径
            System.out.println(directoryPath);

            // 创建目录
            Path directory = Paths.get(directoryPath);
            if (!Files.exists(directory)) {
                try {
                    Files.createDirectories(directory);
                    System.out.println(directoryPath+"----------------------------");
                    directoriesToDelete.add(directory); // 添加到待删除列表
                } catch (IOException e) {
                    e.printStackTrace();
                    continue; // 处理异常
                }
            }

            // 生成 PDF 文件并放入对应目录
            HtmlToPdf.convert("http://localhost/manage/newpage/1html?personalBasicInformation=" + i, directoryPath + name + "劳动者个人信息卡.pdf");

            if(newdepate.getTestingResultsOccupationalHazardsInWorkplace()!=null){
                HtmlToPdf.convert("http://localhost/manage/newpage/2html?personalBasicInformation=" + i, directoryPath + name + "工作场所职业病危害因素检测结果.pdf");
            }

            if(newdepate.getPreviousOccupationalHealthExaminationResults()!=null){
                HtmlToPdf.convert("http://localhost/manage/newpage/3html?personalBasicInformation=" + i, directoryPath + name + "职业病诊疗、伤残鉴定及处理情况.pdf");
            }
            if(newdepate.getNoticeOccupationalHealthExaminationResults()!=null){
                HtmlToPdf.convert("http://localhost/manage/newpage/4html?personalBasicInformation=" + i, directoryPath + name + "职业健康检查结果告知书.pdf");
            }
            if(newdepate.getOccupationalHealthExamination1()!=null){
                HtmlToPdf.convert("http://localhost/manage/newpage/5html?personalBasicInformation=" + i, directoryPath + name + "职业健康体检报告、职业病诊疗等健康资料.pdf");
            }
            if(newdepate.getExaminationResults()!=null){
                HtmlToPdf.convert("http://localhost/manage/newpage/6html?personalBasicInformation=" + i, directoryPath + name + "需要存入职业健康监护档案的其它有关材料.pdf");
            }
            if(newdepate.getRegardingMaterials()!=null){
                HtmlToPdf.convert("http://localhost/manage/newpage/7html?personalBasicInformation=" + i, directoryPath + name + "记事.pdf");
            }

            if(newdepate.getNotes()!=null){
                HtmlToPdf.convert("http://localhost/manage/newpage/8html?personalBasicInformation=" + i, directoryPath + name + "其它.pdf");
            }

        }
        String zipFilePath = basePath + "employee_documents_" + timestamp + ".zip";
        zipDirectories(directoriesToDelete, zipFilePath);
        deleteDirectories(directoriesToDelete);

        return Result.ok("employee_documents_" + timestamp + ".zip");
    }

    private void zipDirectories(List<Path> directoriesToDelete, String zipFilePath) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (Path dir : directoriesToDelete) {
                // 遍历目录及其所有文件
                Files.walk(dir).forEach(file -> {
                    // 计算相对路径，包括目录结构
                    Path zipEntryPath = dir.getFileName().resolve(dir.relativize(file)); // 包含文件夹名称
                    // 如果是目录，确保在 ZIP 中也添加一个目录条目
                    if (Files.isDirectory(file)) {
                        try {
                            zipOutputStream.putNextEntry(new ZipEntry(zipEntryPath + "/")); // 添加目录条目
                            zipOutputStream.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // 如果是文件，添加文件条目
                        ZipEntry zipEntry = new ZipEntry(zipEntryPath.toString());
                        try {
                            zipOutputStream.putNextEntry(zipEntry);
                            Files.copy(file, zipOutputStream);
                            zipOutputStream.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void deleteDirectories(List<Path> directoriesToDelete) {
        for (Path dir : directoriesToDelete) {
            try {
                Files.walk(dir)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
