package com.siskin.service;


import com.siskin.entity.RegexEncryptRules;
import com.siskin.mapper.RegexEncryptRulesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegexEncryptRulesService {

    @Autowired
    private RegexEncryptRulesMapper encryptRulesMapper;

    public List<RegexEncryptRules> getEncrypt(){
        return encryptRulesMapper.getEncrypt();
    }

    public RegexEncryptRules getEncryptById(int rer_id){
        return encryptRulesMapper.getEncryptById(rer_id);
    }

    public Integer insertEncrypt(RegexEncryptRules encrypt){
        return encryptRulesMapper.setEncrypt(encrypt);
    }

    public Integer updateEncrypt(RegexEncryptRules encrypt){
        return encryptRulesMapper.updateEncrypt(encrypt);
    }

    public Integer deleteEncrypt(RegexEncryptRules encrypt){
        return encryptRulesMapper.deleteEncrypt(encrypt);
    }
}
