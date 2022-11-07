package com.siskin;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.siskin.controller.FlinkRestApiController;
import com.siskin.entity.RegexFilterRules;
import com.siskin.entity.SpiderIdentifiedRules;
import com.siskin.mapper.RegexFilterRulesMapper;
import com.siskin.mapper.SpiderIdentifiedRulesMapper;
import com.siskin.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;


import javax.annotation.Resource;
import java.util.*;


@SpringBootTest
class SiskinApplicationTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisService redisService;

    @Autowired
    RegexFilterRulesMapper regexFilterRulesMapper;

    @Resource
    SpiderIdentifiedRulesMapper spiderIdentifiedRulesMapper;

    @Resource
    FlinkRestApiController flinkRestApiController;

    @Test
    public void testInit(){
        //检测是否连接成功 返回Pong表示连接成功
        String pong = redisTemplate.getConnectionFactory().getConnection().ping();
        System.out.println("pong=" + pong);
    }

    @Test
    public void redisTest() {
        Set<Object> keys = stringRedisTemplate.opsForHash().keys("siskin-spiders");
        for (Object key : keys) {
            System.out.println(key);
        }
    }

//    @Test
//    public void toTest(){
//        RegexFilterRules filterRules = new RegexFilterRules();
//        filterRules.setRfr_id(5);
//        filterRules.setField_name("method");
//        filterRules.setRegex_rule("POST");
//        regexFilterRulesMapper.insertFilter(filterRules);
//    }
    @Test
    public void testSet(){
        SpiderIdentifiedRules ss = new SpiderIdentifiedRules();

        ss.setIdentified_rules("hello");
        ss.setStart_time("2020-10-10");
        ss.setEnd_time("2020-10-10");
        spiderIdentifiedRulesMapper.setSpider(ss);
    }

//    @Test
//    public void toTest2(){
//        RegexFilterRules filterRules = new RegexFilterRules();
//        filterRules.setRfr_id(5);
//        filterRules.setField_name("method");
//        filterRules.setRegex_rule("GET");
//        regexFilterRulesMapper.updateFilter(filterRules);
//    }


    @Test
    public void testGetHash(){
        Set<Object> keys = stringRedisTemplate.opsForHash().keys("spider-identified-rules");

        List<Object> reslut = new ArrayList<>(keys);
        for (Object key : reslut) {
            String string = key.toString();
            flinkRestApiController.runRuleJob(Integer.valueOf(string));
        }
//        reslut.forEach(System.out::println);
//        List<Object> values = stringRedisTemplate.opsForHash().values("regex-filter-rules");
//        values.forEach(System.out::println);
    }

    @Test
    public void testGetSpider(){
        List<SpiderIdentifiedRules> spider = spiderIdentifiedRulesMapper.getSpider();
        for (SpiderIdentifiedRules spiderIdentifiedRules : spider) {
            System.out.println(spiderIdentifiedRules);
        }
    }


    @Test
    public void testMysqlToRedis(){
        List<RegexFilterRules> filter = regexFilterRulesMapper.getFilter();
        for (RegexFilterRules filterRules : filter) {
            System.out.println(filterRules);
        }

//        RegexFilterRules byId = regexFilterRulesMapper.getFilterById(1);
//        String id = String.valueOf(byId.getRfr_id());
//        String field_name = byId.getField_name();
//        String regex_rule = byId.getRegex_rule();
//        JSONObject json = new JSONObject();
//        json.put("regex_rule",regex_rule);
//        json.put("field_name",field_name);
//        String result = json.toJSONString();
//
//
//        stringRedisTemplate.opsForHash().put("regex-filter-rules",id,result);
    }


    @Test
    public void testCurToRedis(){
        JSONObject result = new JSONObject();
        JSONArray normal = new JSONArray();
        JSONArray spider = new JSONArray();
        Set<Object> keys = stringRedisTemplate.opsForHash().keys("siskin-cur-link-count");
        Object normalKey = keys.toArray()[0];
        Object spiderKey = keys.toArray()[1];
        List<Object> values = stringRedisTemplate.opsForHash().values("siskin-cur-link-count");
        Object normalValue = values.toArray()[0];
        Object spiderValue = values.toArray()[1];
        normal.add(normalValue);
        spider.add(spiderValue);
        result.put((String) normalKey,normal);
        result.put((String) spiderKey,spider);



    }


}
