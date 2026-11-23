package com.yihe.framework.ehr;

import java.util.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import static com.yihe.framework.ehr.EhrAccess.*;
import static com.yihe.framework.ehr.EhrService.*;

/** Compare preserved report originals with archive values. Never write health records. */
@Service
public class DigitalWorkerOriginalCheck {
    private final EhrRepository db;
    private final EhrService ehr;
    private final DigitalWorkerModelClient model;
    @Value("${ehr.digital-worker.pdf-python:python}") String python;
    @Value("${ehr.digital-worker.pdf-python-path:}") String pythonPath;
    @Value("${ehr.digital-worker.original-script:scripts/digital_worker_original.py}") String script;
    public DigitalWorkerOriginalCheck(EhrRepository db,EhrService ehr,DigitalWorkerModelClient model) {this.db=db;this.ehr=ehr;this.model=model;}
    public static boolean applies(Map<String,Object> input) {return "archive_completion".equals(input.get("task_type"));}
    /** Shared, authorized original reader for report interpretation and evidence-led workflows. */
    public Map<String,Object> sourceBundle(EhrActor actor,String reportId,int revision) {
        try {
            ehr.rawReport(actor,reportId,revision,false);
            List<Map<String,Object>> files=db.all("SELECT s.source_file_id id,s.sha256,s.page_count pageCount,COALESCE((SELECT u.original_file_name FROM a_report_upload_metadata u WHERE u.source_file_id=s.source_file_id ORDER BY u.uploaded_at LIMIT 1),'体检报告原件') name FROM a_report_revision_sources r JOIN a_report_sources s ON s.source_file_id=r.source_file_id WHERE r.report_id=? AND r.revision=?",reportId,revision);
            if(files.isEmpty())return map("available",false,"reason","报告未关联原件","sources",Collections.emptyList(),"pages",Collections.emptyList());
            for(Map<String,Object> file:files){file.put("path",ehr.download(actor,text(file,"id")).toString());file.put("sha256",hex((byte[])file.get("sha256")));}
            Map<String,Object> bundle=readOriginal(files);bundle.put("available",true);return bundle;
        }catch(Exception error){return map("available",false,"reason","当前服务器无法读取或校验报告原件，请恢复原件后重试","sources",Collections.emptyList(),"pages",Collections.emptyList());}
    }
    private static String norm(Object value) {return str(value).replaceAll("[\\s\\u00a0\\u3000]+","");}
    private static boolean containsLiteral(String quote,String value) {
        if(value.isEmpty())return true;
        String before=Character.isDigit(value.charAt(0))?"(?<![0-9.])":"";
        String after=Character.isDigit(value.charAt(value.length()-1))?"(?![0-9.])":"";
        return java.util.regex.Pattern.compile(before+java.util.regex.Pattern.quote(value)+after).matcher(quote).find();
    }
    private static boolean samePrint(Object left,Object right) {return norm(left).replace('–','-').replace('—','-').replace('－','-').equals(norm(right).replace('–','-').replace('—','-').replace('－','-'));}
    private static boolean labelInQuote(String label,String quote) {
        if(quote.contains(label))return true;
        java.util.regex.Matcher aliases=java.util.regex.Pattern.compile("[（(]([A-Za-z][A-Za-z0-9/%+.-]{1,20})[）)]").matcher(label);
        while(aliases.find())if(java.util.regex.Pattern.compile("(?<![A-Za-z])"+java.util.regex.Pattern.quote(aliases.group(1))+"(?![A-Za-z])").matcher(quote).find())return true;
        return false;
    }
    private Map<String,Object> blocked(String reason) {return map("kind","original_check","state","blocked","summary",reason,"checkedAt",java.time.OffsetDateTime.now(java.time.ZoneOffset.ofHours(8)).toString(),"checks",Collections.emptyList(),"sources",Collections.emptyList(),"total",0,"matched",0,"differences",0,"unverified",0);}

    @SuppressWarnings("unchecked")
    public Map<String,Object> verify(Map<String,Object> input) {
        Map<String,Object> result;
        try {result=perform(input);}
        catch(EhrException error) {result=blocked(error.getMessage().startsWith("MODEL_")?"原件已读取，但比对服务未返回完整结果，请稍后重新核对；本次未生成通过结论。":error.getMessage());}
        catch(Exception error) {result=blocked("原件核对未完成，请检查原件和识别服务后重试；未生成核对通过结论。");}
        return map("model","original-check/"+text(model.status(),"model"),"output",result.get("summary"),"usage","{}","verification",result);
    }

    @SuppressWarnings("unchecked")
    private Map<String,Object> perform(Map<String,Object> input) throws Exception {
        String scope=text(input,"scope_code"),record=text(input,"record_id");
        if(record.isEmpty()) return blocked("请先关联职工档案，再读取体检报告原件进行核对。");
        EhrActor actor=new EhrActor(text(input,"requested_by"),scope.substring(4),text(input,"data_mode"),true,false);
        Map<String,Object> snapshot=(Map<String,Object>)input.get("archive_snapshot");
        Map<String,Object> selected=(Map<String,Object>)snapshot.get("report");
        if(selected==null) return blocked("关联档案没有体检报告，请先上传报告原件。");
        String reportId=text(selected,"reportId");int revision=num(selected.get("revision"));
        Map<String,Object> header=ehr.rawReport(actor,reportId,revision,false);
        String reportDigest=hex(digest(bytes(ehr.encode(ehr.snapshot(reportId,revision)))));
        List<Map<String,Object>> files=db.all("SELECT s.source_file_id id,s.sha256,s.page_count pageCount,COALESCE((SELECT u.original_file_name FROM a_report_upload_metadata u WHERE u.source_file_id=s.source_file_id ORDER BY u.uploaded_at LIMIT 1),'体检报告原件') name "+
            "FROM a_report_revision_sources r JOIN a_report_sources s ON s.source_file_id=r.source_file_id WHERE r.report_id=? AND r.revision=?",reportId,revision);
        if(files.isEmpty()) return blocked("报告未关联原件，无法核对；请上传原版体检报告。");
        for(Map<String,Object> file:files) {
            Path path;
            try {path=ehr.download(actor,text(file,"id"));}
            catch(EhrException error) {return blocked("体检报告原件在当前服务器不可读取，请恢复原件或重新上传；数据库完整度不能替代原件核对。");}
            file.put("path",path.toString());file.put("sha256",hex((byte[])file.get("sha256")));
        }
        Map<String,Object> original=readOriginal(files);
        List<Map<String,Object>> pages=(List<Map<String,Object>>)original.get("pages");
        if(pages==null||pages.isEmpty()) return blocked("原件未读取到可核对页面，请检查 PDF 或图片文件。");
        Map<String,Object> profile=ehr.profile(actor,record,false);
        List<Map<String,Object>> fields=new ArrayList<>();
        fields.add(map("id","identity-name","group","identity","label","受检者姓名","value",profile.get("employee_name")));
        fields.add(map("id","exam-date","group","identity","label","体检日期","value",str(header.get("exam_date_verified"))));
        fields.add(map("id","institution","group","identity","label","体检机构","value",header.get("institution_raw")));
        fields.addAll(db.all("SELECT item_id id,'item' `group`,original_field_name label,result_raw value,unit_raw unit,reference_range_raw referenceRange,abnormal_flag_raw abnormal FROM a_health_items WHERE report_id=? AND revision=? ORDER BY ordinal",reportId,revision));
        fields.addAll(db.all("SELECT conclusion_id id,'conclusion' `group`,original_field_name label,original_text value FROM a_health_conclusions WHERE report_id=? AND revision=? ORDER BY conclusion_id",reportId,revision));
        fields.addAll(db.all("SELECT advice_id id,'advice' `group`,'医师建议' label,text_raw value FROM a_health_advice WHERE report_id=? AND revision=? ORDER BY advice_id",reportId,revision));
        if(fields.size()>600) return blocked("报告项目超过单次核对上限，请拆分报告后核对。");
        Map<String,Map<String,Object>> byId=new HashMap<>();List<Map<String,Object>> uncertain=new ArrayList<>();
        List<Object> extras=new ArrayList<>();List<String> modelErrors=new ArrayList<>();boolean reused=false;
        Map<String,Object> saved=db.one("SELECT verification_json FROM a_digital_worker_ai_attempts WHERE task_id=? AND verification_json IS NOT NULL ORDER BY created_at DESC,attempt_id DESC LIMIT 1",input.get("task_id"));
        if(saved!=null) {
            Map<String,Object> previous=(Map<String,Object>)ehr.decode(saved.get("verification_json"));
            if(reportDigest.equals(previous.get("reportDigest"))&&Objects.equals(original.get("sources"),previous.get("sources"))&&previous.get("checks") instanceof List&&(!(previous.get("modelErrors") instanceof List)||((List<?>)previous.get("modelErrors")).isEmpty())) {
                // Re-open/hash the originals above and revalidate each quote below. No hidden stale-data reuse.
                reused=true;
                for(Object raw:(List<?>)previous.get("checks"))if(raw instanceof Map) {
                    Map<String,Object> row=(Map<String,Object>)raw;
                    if("extra".equals(row.get("group")))extras.add(row);else byId.put(text(row,"id"),row);
                }
            }
        }
        for(Map<String,Object> field:fields) {
            Map<String,Object> literal=literalMatch(field,fields,pages);
            if(literal==null){if(!reused)uncertain.add(field);}else byId.put(text(field,"id"),literal);
        }
        long deadline=System.currentTimeMillis()+360000;
        java.util.concurrent.ExecutorService pool=java.util.concurrent.Executors.newFixedThreadPool(2,r->{Thread t=new Thread(r,"original-check-batch");t.setDaemon(true);return t;});
        List<java.util.concurrent.Callable<Map<String,Object>>> batches=new ArrayList<>();
        for(int offset=0;offset<uncertain.size();offset+=20) {
            final List<Map<String,Object>> batch=new ArrayList<>(uncertain.subList(offset,Math.min(offset+20,uncertain.size())));
            batches.add(()->{
                try{return model.compareOriginal(map("fields",batch,"pages",pages,"deadline",deadline,"knownLabels",fields.stream().map(f->f.get("label")).collect(java.util.stream.Collectors.toList())));}
                catch(EhrException error){return map("checks",Collections.emptyList(),"batchError",error.getMessage().matches("MODEL_[A-Z0-9_]+")?error.getMessage():"MODEL_ERROR");}
            });
        }
        try {
            for(java.util.concurrent.Future<Map<String,Object>> future:pool.invokeAll(batches,360,TimeUnit.SECONDS)) {
                if(future.isCancelled()){modelErrors.add("MODEL_DEADLINE");continue;}
                Map<String,Object> compared=future.get();
                if(compared.get("batchError")!=null){modelErrors.add(text(compared,"batchError"));org.slf4j.LoggerFactory.getLogger(getClass()).warn("Original comparison batch incomplete: {}",compared.get("batchError"));}
                Object returned=compared.get("checks");
                if(returned instanceof List)for(Object row:(List<?>)returned)if(row instanceof Map)byId.put(text((Map<String,Object>)row,"id"),(Map<String,Object>)row);
                if(compared.get("extraItems") instanceof List)extras.addAll((List<?>)compared.get("extraItems"));
            }
        } finally {pool.shutdownNow();}
        List<Map<String,Object>> checks=new ArrayList<>();int matched=0,different=0,unverified=0,formatting=0,extraCount=0;
        for(Map<String,Object> field:fields) {
            Map<String,Object> found=byId.get(text(field,"id"));
            Map<String,Object> checked=validate(field,found,pages);
            checks.add(checked);
            if("matched".equals(checked.get("status"))) matched++;
            else if("different".equals(checked.get("status"))) different++;
            else if("formatting".equals(checked.get("status"))) formatting++;
            else unverified++;
        }
        // Original-only rows are suggestions until their literal source quote is independently verified.
        Object extra=extras;
        Set<String> extraSeen=new HashSet<>();
        if(extra instanceof List) for(Object row:(List<?>)extra) {
            if(!(row instanceof Map)||checks.size()>800) continue;
            Map<String,Object> candidate=(Map<String,Object>)row;
            String extraKey=norm(candidate.get("label"))+"|"+norm(candidate.get("originalValue"));if(extraSeen.contains(extraKey))continue;
            Map<String,Object> field=map("id","original-only-"+checks.size(),"group","extra","label",text(candidate,"label"),"value","");
            Map<String,Object> checked=validate(field,candidate,pages);
            if(!"unverified".equals(checked.get("status"))&&!text(candidate,"originalValue").isEmpty()&&fields.stream().noneMatch(f->norm(f.get("label")).equals(norm(candidate.get("label"))))) {
                extraSeen.add(extraKey);checked.put("status","suspected");checked.put("reason","原件存在该项目，当前档案未发现同名项目；可能使用其他名称，尚不能认定为漏录。");checks.add(checked);extraCount++;
            }
        }
        String currentDigest=hex(digest(bytes(ehr.encode(ehr.snapshot(reportId,revision)))));
        if(!reportDigest.equals(currentDigest)||db.count("SELECT COUNT(*) FROM a_report_series WHERE report_id=? AND current_revision=?",reportId,revision)==0) return blocked("核对过程中档案已更新，请重新读取最新报告后核对。");
        String summary="已读取 "+pages.size()+" 页报告原件，核对 "+fields.size()+" 条档案记录。另有 "+extraCount+" 项名称未匹配，需确认是否为同一项目。";
        return map("kind","original_check","state",matched+different+formatting==0?"blocked":unverified+extraCount>0?"partial":"completed","summary",summary,"checkedAt",java.time.OffsetDateTime.now(java.time.ZoneOffset.ofHours(8)).toString(),
            "reportId",reportId,"revision",revision,"reportDigest",reportDigest,"sources",original.get("sources"),"pageCount",pages.size(),"total",fields.size(),"matched",matched,"differences",different,"unverified",unverified,
            "formatting",formatting,"extraCount",extraCount,"reusedComparison",reused,"checks",checks,"modelErrors",modelErrors,"reviewRequired",true,"method","原件逐页读取、逐字匹配、疑难项目分批模型比对；扫描页识别结果需要人工复核");
    }

    /** A literal row match needs the label and every populated column in the same original row span. */
    static Map<String,Object> literalMatch(Map<String,Object> field,List<Map<String,Object>> fields,List<Map<String,Object>> pages) {
        if(!"item".equals(field.get("group")))return null;
        String label=norm(field.get("label")),value=norm(field.get("value"));if(label.length()<2||value.isEmpty())return null;
        for(Map<String,Object> page:pages) {
            if(page.containsKey("image"))continue;
            String text=norm(page.get("text"));int from=0,start;
            while((start=text.indexOf(label,from))>=0) {
                int end=Math.min(text.length(),start+800);from=start+label.length();
                for(Map<String,Object> other:fields) {
                    String next=norm(other.get("label"));if(next.length()<2||next.equals(label)||label.contains(next))continue;
                    int pos=text.indexOf(next,from);if(pos>=from&&pos<end)end=pos;
                }
                String quote=text.substring(start,end);boolean present=true;
                for(String key:Arrays.asList("value","unit","referenceRange","abnormal")) {
                    String expected=norm(field.get(key));
                    if(!containsLiteral(quote,expected)) {present=false;break;}
                }
                if(!present)continue;
                return map("id",field.get("id"),"originalValue",field.get("value"),"originalUnit",str(field.get("unit")),"originalRange",str(field.get("referenceRange")),"originalAbnormal",str(field.get("abnormal")),"sourceId",page.get("sourceId"),"page",page.get("page"),"quote",quote);
            }
        }
        return null;
    }

    /** A model cannot mark a row matched without a literal quote from the selected original page. */
    static Map<String,Object> validate(Map<String,Object> field,Map<String,Object> found,List<Map<String,Object>> pages) {
        Map<String,Object> row=map("id",field.get("id"),"group",field.get("group"),"label",field.get("label"),"archiveValue",field.get("value"),"archiveUnit",field.get("unit"),"archiveRange",field.get("referenceRange"),"archiveAbnormal",field.get("abnormal"),
            "originalValue",found==null?"":found.get("originalValue"),"originalUnit",found==null?"":found.get("originalUnit"),"originalRange",found==null?"":found.get("originalRange"),"originalAbnormal",found==null?"":found.get("originalAbnormal"),
            "sourceId",found==null?"":found.get("sourceId"),"page",found==null?null:found.get("page"),"quote",found==null?"":found.get("quote"),"status","unverified","reason","未取得可验证的原文证据，请人工复核。");
        if(found==null) return row;
        Map<String,Object> page=pages.stream().filter(p->Objects.equals(p.get("sourceId"),found.get("sourceId"))&&num(p.get("page"))==num(found.get("page"))).findFirst().orElse(null);
        String quote=norm(found.get("quote")),value=norm(found.get("originalValue"));
        if(page==null||quote.length()<2||quote.length()>2500) return row;
        if(page.containsKey("image")) {row.put("reason","该页为扫描识别，原文定位与识别值需要人工确认。");return row;}
        if(!norm(page.get("text")).contains(quote)||value.isEmpty()||!containsLiteral(quote,value)) return row;
        if(!Arrays.asList("identity","conclusion","advice").contains(field.get("group"))&&!labelInQuote(norm(field.get("label")),quote)) return row;
        // Match every populated column, not only the numerical result. No unit conversions.
        boolean same=norm(field.get("value")).equals(value),printOnly=samePrint(field.get("value"),found.get("originalValue"));
        if("exam-date".equals(field.get("id"))) same=norm(field.get("value")).replaceAll("年|月","-").replace("日","").equals(value.replaceAll("年|月","-").replace("日",""));
        for(String[] pair:new String[][]{{"unit","originalUnit"},{"referenceRange","originalRange"},{"abnormal","originalAbnormal"}}) {
            String originalValue=norm(found.get(pair[1]));
            if(!originalValue.isEmpty()&&!quote.contains(originalValue)) return row;
            if(!norm(field.get(pair[0])).equals(originalValue)) same=false;
            if(!samePrint(field.get(pair[0]),originalValue))printOnly=false;
        }
        if(!same&&Arrays.asList("conclusion","advice").contains(field.get("group"))) {row.put("reason","长段文字可能涉及分段、跨页或栏目归属，当前片段不足以证明整段存在差异，请按原件复核。");return row;}
        row.put("status",same?"matched":printOnly?"formatting":"different");row.put("reason",same?"档案值与原件原文一致。":printOnly?"数值相同，仅连接符等排版格式不同，未视为内容差异。":"档案值与原件不一致，请核对原文后处理。");return row;
    }

    private Map<String,Object> readOriginal(List<Map<String,Object>> files) throws Exception {
        Path output=Files.createTempFile("kuangan-original-",".json"),error=Files.createTempFile("kuangan-original-",".log");Process process=null;
        try {
            ProcessBuilder builder=new ProcessBuilder(python,Paths.get(script).toAbsolutePath().normalize().toString());
            if(!pythonPath.trim().isEmpty())builder.environment().put("PYTHONPATH",pythonPath);
            builder.environment().put("PYTHONIOENCODING","utf-8");builder.redirectOutput(output.toFile());builder.redirectError(error.toFile());
            process=builder.start();try(java.io.OutputStream stream=process.getOutputStream()){stream.write(ehr.encode(map("files",files)).getBytes(StandardCharsets.UTF_8));}
            if(!process.waitFor(90,TimeUnit.SECONDS)){process.destroyForcibly();throw new EhrException(503,"原件读取超时，请检查文件后重试。");}
            if(process.exitValue()!=0||Files.size(output)>80_000_000)throw new EhrException(503,"原件读取或指纹校验失败，请恢复与归档记录一致的原件。");
            return (Map<String,Object>)ehr.decode(new String(Files.readAllBytes(output),StandardCharsets.UTF_8));
        } finally {if(process!=null&&process.isAlive())process.destroyForcibly();Files.deleteIfExists(output);Files.deleteIfExists(error);}
    }
}
