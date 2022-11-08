package com.siskin.controller;

import org.esni.flink.rest.api.FlinkRestAPI;
import org.esni.flink.rest.api.bean.Jar;
import org.esni.flink.rest.api.bean.Job;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.siskin.config.SiskinWebConfig.*;

@RequestMapping("/flinkRest")
@RestController
public class FlinkRestApiController {

    @RequestMapping("/getStatus")
    public String getStatus(@RequestBody Map<String,Integer> params){
        Integer ruleId = params.get("ruleId");
        FlinkRestAPI api = FlinkRestAPI.create(FLINK_REST_URL);
        for (Job job : api.getJobs()) {
            Job richJob = api.getJob(job.getJid());
            if ((JOB_NAME + ruleId).equals(richJob.getName())) {
                return job.getState().toString();
            }
        }
        return "No Such Job!";
    }




    @RequestMapping("/runRuleJob")
    public String runRuleJob(@RequestBody Map<String,Integer> params){
        Integer ruleId = params.get("ruleId");
        String message = "";
        FlinkRestAPI api = FlinkRestAPI.create(FLINK_REST_URL);
        List<Jar> jars = api.getJars();
        boolean hasJar = jars.stream().map(new Function<Jar, String>() {
            @Override
            public String apply(Jar jar) {
                return jar.getName();
            }
        }).collect(Collectors.toList()).contains(JAR_NAME);

        // 无jar包就上传
        if (!hasJar) {
            api.uploadJar(JAR_PATH);
        }
        Jar jar = getJarByName(api, JAR_NAME);
        if(!isNameExistInJobs(api,ETL_NAME)){
            api.runJar(jar.getId(),"org.esni.siskin_core.app.ETLApplication",PARALLELISM);
        }
        // 判断rule是否运行
        if (isNameExistInJobs(api, JOB_NAME + ruleId)) {
            // close
            terminate(ruleId);
        }
        // start
        api.runJar(jar.getId(), "org.esni.siskin_core.app.IdentifiedRuleApplication", PARALLELISM, String.valueOf(ruleId));
        if (isNameExistInJobs(api, JOB_NAME + ruleId)) {
            message = "run successfully";
        }else {
            message = "run Failed";
        }
        return message;
    }



    public void runETLJob() {

        FlinkRestAPI api = FlinkRestAPI.create(FLINK_REST_URL);
        List<Jar> jars = api.getJars();
        boolean hasJar = jars.stream().map(new Function<Jar, String>() {
            @Override
            public String apply(Jar jar) {
                return jar.getName();
            }
        }).collect(Collectors.toList()).contains(JAR_NAME);


        if(!hasJar){
            api.uploadJar(JAR_PATH);
        }
        Jar jar = getJarByName(api, JAR_NAME);
        for(Job job:api.getJobs()){
            Job richJob = api.getJob(job.getJid());
            if(isNameExistInJobs(api,ETL_NAME)){
                //close
                api.terminateJob(richJob.getJid());
            }
        }
        api.runJar(jar.getId(),"org.esni.siskin_core.app.ETLApplication",PARALLELISM);
    }



    public Jar getJarByName(FlinkRestAPI api, String jarName) {

        if (jarName == null || api == null) return null;

        List<Jar> jars = api.getJars();
        for (Jar jar : jars) {
            if (jarName.equals(jar.getName())) {
                return jar;
            }
        }
        return null;
    }

    public boolean isNameExistInJobs(FlinkRestAPI api, String name) {

        return api.getJobs().stream().map(new Function<Job, String>() {
            @Override
            public String apply(Job job) {
                Job newJob = api.getJob(job.getJid());
                if (newJob == null) return "";
                return newJob.getName();
            }
        }).collect(Collectors.toList()).contains(name);
    }




    public void terminate(int ruleId){
        FlinkRestAPI api = FlinkRestAPI.create(FLINK_REST_URL);
        for (Job job : api.getJobs()) {
            Job richJob = api.getJob(job.getJid());
            if ((JOB_NAME + ruleId).equals(richJob.getName())) {
                api.terminateJob(richJob.getJid());
            }
        }
    }

}
