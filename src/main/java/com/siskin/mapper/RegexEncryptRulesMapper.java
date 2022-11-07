package com.siskin.mapper;

import com.siskin.entity.RegexEncryptRules;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RegexEncryptRulesMapper {
    //查询所有Encrypt
    List<RegexEncryptRules> getEncrypt();
    //根据id查Encrypt
    RegexEncryptRules getEncryptById(int rer_id);
    //添加数据Encrypt
    Integer setEncrypt(RegexEncryptRules encrypt);
    //修改数据Encrypt
    Integer updateEncrypt(RegexEncryptRules encrypt);
    //删除数据Encrypt
    Integer deleteEncrypt(RegexEncryptRules encrypt);
}
