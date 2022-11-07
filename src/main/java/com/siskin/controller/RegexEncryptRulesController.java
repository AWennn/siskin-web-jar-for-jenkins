package com.siskin.controller;

import com.siskin.entity.RegexEncryptRules;
import com.siskin.service.RegexEncryptRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Encrypt")
public class RegexEncryptRulesController {

    String message = "";
    @Autowired
    private RegexEncryptRulesService regexEncryptRulesService;

    @RequestMapping("/getAllEncrypt")
    public List<RegexEncryptRules> getEncrypt(){
        return regexEncryptRulesService.getEncrypt();
    }

    @RequestMapping("getEncryptById/{rer_id}")
    public RegexEncryptRules getEncryptById(@PathVariable int rer_id){
        return regexEncryptRulesService.getEncryptById(rer_id);
    }

    @PostMapping("/insertEncrypt")
    public String insertEncrypt(@RequestBody RegexEncryptRules encrypt){
        try {
            regexEncryptRulesService.insertEncrypt(encrypt);
            message = "Succeeded";
        }catch (Exception exception){
            message = "failed";
        }
        return message;
    }

    @PutMapping("/updateEncrypt")
    public String updateEncrypt(@RequestBody RegexEncryptRules encrypt){
        try {
            message = regexEncryptRulesService.updateEncrypt(encrypt) == 1?"Succeeded":"Does not exist,Failed";
        }catch (Exception exception){
            message = "Abnormal";
        }
        return message;
    }

    @DeleteMapping("/deleteEncrypt")
    public String deleteEncrypt(@RequestBody RegexEncryptRules encrypt){
        try {
            message = regexEncryptRulesService.deleteEncrypt(encrypt) == 1?"Succeeded":"Failed";
        }catch (Exception exception){
            message = "Abnormal";
        }
        return message;
    }
}
