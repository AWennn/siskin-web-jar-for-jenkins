package com.siskin.controller;


import com.siskin.entity.RegexFilterRules;
import com.siskin.service.RegexFilterRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Filter")
public class RegexFilterRulesController {

    String message = "";

    @Autowired
    private RegexFilterRulesService regexFilterRulesService;

    @RequestMapping("/getAllFilter")
    public List<RegexFilterRules> getFilter(){
        return regexFilterRulesService.getFilter();
    }

    @RequestMapping("getFilterById/{rfr_id}")
    public RegexFilterRules getFilterById(@PathVariable int rfr_id){
        return regexFilterRulesService.getFilterById(rfr_id);
    }

    @PostMapping("/insertFilter")
    public String insertFilter(@RequestBody RegexFilterRules filter){
        try {
            regexFilterRulesService.insertFilter(filter);
            message = "Succeeded";
        }catch (Exception exception){
            message = "failed";
        }
        return message;
    }

    @PutMapping("/updateFilter")
    public String updateFilter(@RequestBody RegexFilterRules filter){
        try {
            message = regexFilterRulesService.updateFilter(filter) == 1?"Succeeded":"Does not exist,Failed";
        }catch (Exception exception){
            message = "Abnormal";
        }
        return message;
    }

    @DeleteMapping("/deleteFilter")
    public String deleteFilter(@RequestBody RegexFilterRules filter){
        try {
            message = regexFilterRulesService.deleteFilter(filter) == 1?"Succeeded":"Failed";
        }catch (Exception exception){
            message = "Abnormal";
        }
        return message;
    }


}
