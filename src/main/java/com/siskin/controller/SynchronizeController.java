package com.siskin.controller;

import com.alibaba.fastjson.JSONObject;
import com.siskin.entity.RegexEncryptRules;
import com.siskin.entity.RegexFilterRules;
import com.siskin.entity.SpiderIdentifiedRules;
import com.siskin.mapper.RegexEncryptRulesMapper;
import com.siskin.mapper.RegexFilterRulesMapper;
import com.siskin.mapper.SpiderIdentifiedRulesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/")
public class SynchronizeController {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RegexFilterRulesMapper regexFilterRulesMapper;
    @Autowired
    RegexEncryptRulesMapper regexEncryptRulesMapper;
    @Autowired
    SpiderIdentifiedRulesMapper spiderIdentifiedRulesMapper;

    @RequestMapping("Synchronize")
    public String synchronizeData(){
        try {
            List<RegexFilterRules> allFilter = regexFilterRulesMapper.getFilter();
            for (RegexFilterRules filterRules : allFilter) {
                int rfr_id = filterRules.getRfr_id();
                RegexFilterRules filterById = regexFilterRulesMapper.getFilterById(rfr_id);
                String rfrId = String.valueOf(filterById.getRfr_id());
                String filterFieldName = filterById.getField_name();
                String filterRegexRule = filterById.getRegex_rule();
                JSONObject filter = new JSONObject();
                filter.put("field_name", filterFieldName);
                filter.put("regex_rule", filterRegexRule);
                String filterJson = filter.toJSONString();
                stringRedisTemplate.opsForHash().put("regex-filter-rules", rfrId, filterJson);
            }
            List<RegexEncryptRules> allEncrypt = regexEncryptRulesMapper.getEncrypt();
            for (RegexEncryptRules encryptRules : allEncrypt) {
                int rer_id = encryptRules.getRer_id();
                RegexEncryptRules encryptById = regexEncryptRulesMapper.getEncryptById(rer_id);
                String rerId = String.valueOf(encryptById.getRer_id());
                String encryptFieldName = encryptById.getField_name();
                String encryptMethod = encryptById.getEncrypt_method();
                JSONObject encrypt = new JSONObject();
                encrypt.put("field_name",encryptFieldName);
                encrypt.put("encrypt_method",encryptMethod);
                String encryptJson = encrypt.toJSONString();
                stringRedisTemplate.opsForHash().put("regex_encrypt_rules",rerId,encryptJson);
            }
            List<SpiderIdentifiedRules> allSpider = spiderIdentifiedRulesMapper.getSpider();
            for (SpiderIdentifiedRules spiderIdentifiedRules : allSpider) {
                int sir_id = spiderIdentifiedRules.getSir_id();
                SpiderIdentifiedRules spiderById = spiderIdentifiedRulesMapper.getSpiderById(sir_id);
                String spiderId = String.valueOf(spiderById.getSir_id());
                String identified_rules = spiderById.getIdentified_rules();
                stringRedisTemplate.opsForHash().put("spider-identified-rules",spiderId,identified_rules);
            }

        }catch (Exception e){
            return "插入失败";
        }
        return "插入成功";
    }
}
