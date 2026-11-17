package com.yihe.framework.ehr;
import java.nio.file.*;
import java.util.concurrent.*;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/** Java owns durable orchestration; a supervised local adapter renders PDF pages. */
@Configuration
public class ModelWorkerProcess {
    private final ModelRecognitionSettings settings;
    @Value("${server.port:18080}") int port;
    @Value("${ehr.data-mode:real}") String mode;
    @Value("${ehr.recognition.python:python}") String python;
    @Value("${ehr.recognition.script:scripts/model_recognition_worker.py}") String script;
    private volatile Process process;
    public ModelWorkerProcess(ModelRecognitionSettings settings){this.settings=settings;}
    @Bean(destroyMethod="shutdownNow") public ScheduledExecutorService modelAdapterSupervisor(){ScheduledExecutorService ex=Executors.newSingleThreadScheduledExecutor(r->{Thread t=new Thread(r,"model-adapter-supervisor");t.setDaemon(true);return t;});ex.scheduleWithFixedDelay(()->{try{ensureStarted();}catch(Exception e){org.slf4j.LoggerFactory.getLogger(getClass()).warn("Model adapter could not start; check private configuration and Python runtime");}},8,15,TimeUnit.SECONDS);return ex;}
    synchronized void ensureStarted()throws Exception{
        if(!settings.enabled()){if(process!=null&&process.isAlive())process.destroy();return;}
        if(process!=null&&process.isAlive())return;
        Path entry=Paths.get(script).toAbsolutePath().normalize();if(!Files.isRegularFile(entry)||settings.token().length()<32)return;
        Path logs=Paths.get(settings.configFile).toAbsolutePath().getParent().resolve("model-logs");Files.createDirectories(logs);
        String parentPid=java.lang.management.ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        ProcessBuilder pb=new ProcessBuilder(python,entry.toString(),"--base-url","http://127.0.0.1:"+port,"--mode",mode,"--config",settings.configFile,"--worker-token-file",settings.tokenFile,"--parent-pid",parentPid);
        pb.environment().put("PYTHONIOENCODING","utf-8");pb.environment().put("PYTHONUNBUFFERED","1");pb.redirectErrorStream(true);pb.redirectOutput(ProcessBuilder.Redirect.appendTo(logs.resolve("adapter-"+mode+".log").toFile()));process=pb.start();org.slf4j.LoggerFactory.getLogger(getClass()).info("Local model recognition adapter started for {}",mode);
    }
    @PreDestroy public synchronized void stop(){if(process!=null&&process.isAlive()){process.destroy();try{if(!process.waitFor(5,TimeUnit.SECONDS))process.destroyForcibly();}catch(InterruptedException e){Thread.currentThread().interrupt();}}}
}
