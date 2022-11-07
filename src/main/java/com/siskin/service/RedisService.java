package com.siskin.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RedisService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String get(final String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public Set<Object> getHashKey(final String key){
        return stringRedisTemplate.opsForHash().keys(key);
    }

    public List<Object> getHashValue(final String value){
        return stringRedisTemplate.opsForHash().values(value);
    }

    public HashMap<Object,Object> getHash(String key){
        HashMap<Object, Object> hashMap = new HashMap<>();
        Set<Object> keys = stringRedisTemplate.opsForHash().keys(key);
        List<Object> values = stringRedisTemplate.opsForHash().values(key);
        Iterator<Object> it = keys.iterator();
        for (int i = 0; i < keys.size(); i++) {
            hashMap.put(it.next(), values.get(i));
        }
        return hashMap;
    }

    public boolean set(final String key,String value){
        boolean flag = false;
        stringRedisTemplate.opsForValue().set(key, value);
        flag = true;
        return flag;
    }

    public boolean upDate(final String key,String value){
        boolean flag = false;
        stringRedisTemplate.opsForValue().getAndSet(key, value);
        flag = true;
        return flag;
    }

    public boolean delete(final String key){
        boolean flag = false;
        stringRedisTemplate.delete(key);
        flag = true;
        return flag;
    }

    public String getCurLink(String key){
        JSONObject result = new JSONObject();
        JSONArray normal = new JSONArray();
        JSONArray spider = new JSONArray();
        Set<Object> keys = stringRedisTemplate.opsForHash().keys(key);
        Object normalKey = keys.toArray()[0];
        Object spiderKey = keys.toArray()[1];
        List<Object> values = stringRedisTemplate.opsForHash().values(key);
        Object normalValue = values.toArray()[0];
        Object spiderValue = values.toArray()[1];
        normal.add(normalValue);
        spider.add(spiderValue);
        result.put((String) normalKey,normal);
        result.put((String) spiderKey,spider);

        return result.toJSONString();
    }


}
