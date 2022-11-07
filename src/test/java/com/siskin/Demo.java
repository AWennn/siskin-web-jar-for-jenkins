package com.siskin;

import com.siskin.config.SiskinWebConfig;
import com.siskin.controller.RedisController;
import com.siskin.service.RedisService;
import org.esni.flink.rest.api.FlinkRestAPI;
import org.esni.flink.rest.api.bean.Jar;
import org.esni.flink.rest.api.bean.Job;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.siskin.config.SiskinWebConfig.*;

public class Demo {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public void testDemo() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String startDT = "2022-06-16 00:00:00";
        String endDT = "2022-06-20 00:00:00";

        Date startDate = sdf.parse(startDT);
        Date endDate = sdf.parse(endDT);

        for (long i = startDate.getTime(); i <= endDate.getTime(); i+=1 * 60 * 60 * 1000) {
            System.out.println(sdf.format(new Date(i)));
        }

    }

    public void testDemo2() {

        String route = "http://www.baidu.com/*";

        if (route.endsWith("*")) {
            route = route.substring(0, route.length() - 1);
        }

        if (route.endsWith("/")) {
            route = route.substring(0, route.length() - 1);
        }

        System.out.println(route);

    }

    public void testRest() {

        System.out.println(FlinkRestAPI.create("http://1.15.135.178:8181").getJobs());

    }

//    @Test
    public void getState() {

        FlinkRestAPI api = FlinkRestAPI.create("http://1.15.135.178:8181");
        int ruleId = 1;

        for (Job job : api.getJobs()) {
            Job richJob = api.getJob(job.getJid());
            if (("siskin-identified-application-" + ruleId).equals(richJob.getName())) {
                System.out.println("============================================================");
                System.out.println(job.getState().toString());
            }

        }


    }


    public void terminateJob() {

        FlinkRestAPI api = FlinkRestAPI.create("http://1.15.135.178:8181");
        int ruleId = 1;

        for (Job job : api.getJobs()) {
            Job richJob = api.getJob(job.getJid());
            if (("siskin-identified-application-" + ruleId).equals(richJob.getName())) {
                api.terminateJob(richJob.getJid());
            }

        }

    }

//    @Test
    public void uploadJar() {

        FlinkRestAPI api = FlinkRestAPI.create("http://1.15.135.178:8181");
        api.uploadJar("");

    }

//    @Test
    public void runETLJob() {

        FlinkRestAPI api = FlinkRestAPI.create("http://1.15.135.178:8181");
        List<Jar> jars = api.getJars();

        boolean hasJar = jars.stream().map(new Function<Jar, String>() {
            @Override
            public String apply(Jar jar) {
                return jar.getName();
            }
        }).collect(Collectors.toList()).contains("siskin-core-1.0-SNAPSHOT-jar-with-dependencies.jar");

        if (hasJar) {
            for (Jar jar : jars) {
                if ("siskin-core-1.0-SNAPSHOT-jar-with-dependencies.jar".equals(jar.getName())) {
                    api.runJar(jar.getId(), "org.esni.siskin_core.app.ETLApplication", 4);

                    break;
                }
            }
        }
        else{
            // 上传
        }

    }
    public void runRuleJob() {

        FlinkRestAPI api = FlinkRestAPI.create(SiskinWebConfig.FLINK_REST_URL);
        int ruleId = 1;

        List<Jar> jars = api.getJars();

        boolean hasJar = jars.stream().map(new Function<Jar, String>() {
            @Override
            public String apply(Jar jar) {
                return jar.getName();
            }
        }).collect(Collectors.toList()).contains("siskin-core-1.0-SNAPSHOT-jar-with-dependencies.jar");

        if (hasJar) {
            for (Jar jar : jars) {
                if ("siskin-core-1.0-SNAPSHOT-jar-with-dependencies.jar".equals(jar.getName())) {
                    api.runJar(jar.getId(), "org.esni.siskin_core.app.IdentifiedRuleApplication", 4, String.valueOf(ruleId));

                    break;
                }
            }
        }
        else{
            // 上传
        }

    }

    public void testConf() {

        FlinkRestAPI api = FlinkRestAPI.create(FLINK_REST_URL);
        List<Jar> jars = api.getJars();
        for (Jar jar : jars) {
            System.out.println(jar.getName());
        }

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


    public void isNameExistInJobsTest() {

        FlinkRestAPI api = FlinkRestAPI.create(FLINK_REST_URL);
        String name = "siskin-identified-application-1";

        System.out.println(isNameExistInJobs(api, name));

    }

    public void demodemo() {

        FlinkRestAPI api = FlinkRestAPI.create(FLINK_REST_URL);
//        for (Job job : api.getJobs()) {
//            System.out.println(job);
//        }

        System.out.println(api.getJob("2a1656992271fe82a29ed60bc7e65e97"));

    }

    @Test
    public void isNameExistTest(){
        int ruleId = 1;
        FlinkRestAPI api = FlinkRestAPI.create(FLINK_REST_URL);
        for (Job job : api.getJobs()) {
            Job richJob = api.getJob(job.getJid());
            System.out.println(richJob.getName());
            if(richJob.getName().equals(JAR_NAME+ruleId)){
                System.out.println("运行成功");
            }else {
                System.out.println("失败");
            }
        }

    }

}
