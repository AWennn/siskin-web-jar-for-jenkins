package com.siskin.tools;

import com.siskin.controller.FlinkRestApiController;
import com.siskin.controller.RedisController;
import com.siskin.controller.SynchronizeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    SynchronizeController synchronizeController;

    @Autowired
    FlinkRestApiController flinkRestApiController;

    @Autowired
    RedisController redisController;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(String... args) throws Exception {
//        synchronizeController.synchronizeData();
//        flinkRestApiController.runETLJob();
//        Set<Object> keys = stringRedisTemplate.opsForHash().keys("spider-identified-rules");
//        List<Object> reslut = new ArrayList<>(keys);
//        for (Object key : reslut) {
//            String string = key.toString();
//            flinkRestApiController.runRuleJob(Integer.valueOf(string));
//        }

    }
}
