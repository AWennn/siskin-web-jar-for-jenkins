package com.siskin.controller;

import com.siskin.entity.SpiderIdentifiedRules;
import com.siskin.service.SpiderIdentifiedRulesService;
import org.esni.flink.rest.api.FlinkRestAPI;
import org.esni.flink.rest.api.bean.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.siskin.config.SiskinWebConfig.FLINK_REST_URL;
import static com.siskin.config.SiskinWebConfig.JOB_NAME;

@RestController
@RequestMapping("/Spider")
public class SpiderIdentifiedRulesController {

    String message = "";

    @Autowired
    private SpiderIdentifiedRulesService spiderIdentifiedRulesService;

    @Autowired
    private FlinkRestApiController flinkRestApiController;

    @RequestMapping("/getAllSpider")
    public List<SpiderIdentifiedRules> getSpider(){
        return spiderIdentifiedRulesService.getSpider();
    }

    @RequestMapping("getSpiderById/{sir_id}")
    public SpiderIdentifiedRules getSpiderById(@PathVariable int sir_id){
        return spiderIdentifiedRulesService.getSpiderById(sir_id);
    }

    @PostMapping("/insertSpider")
    public String insertSpider(@RequestBody SpiderIdentifiedRules spider){
        try{
            spiderIdentifiedRulesService.insertSpider(spider);
            message = "Succeeded";
        }catch (Exception exception){
            message = "Failed";
        }
        return message;
    }

    @PutMapping("/updateSpider")
    public String updateSpider(@RequestBody SpiderIdentifiedRules spider){
        try {
            Integer ruleId = spider.getSir_id();
            terminate(ruleId);
            Map<String,Integer> params = new HashMap<>();
            params.put("ruleId",ruleId);
            message = spiderIdentifiedRulesService.updateSpider(spider) == 1?"Succeeded":"Does not exist,Failed";
            flinkRestApiController.runRuleJob(params);
        }catch (Exception exception){
            message = "Abnormal";
        }
        return message;
    }

    @DeleteMapping("/deleteSpider")
    public String deleteSpider(@RequestBody SpiderIdentifiedRules spider){
        try {
            Integer ruleId = spider.getSir_id();
            terminate(ruleId);
            message = spiderIdentifiedRulesService.deleteSpider(spider) == 1?"Succeeded":"Failed";
        }catch (Exception exception){
            message = "Failed";
        }
        return message;
    }

    @RequestMapping("/terminate")
    public String terminateById(@RequestBody Map<String,Integer> map){
        return terminate(map.get("ruleId"));
    }


    public String terminate(int ruleId){
        FlinkRestAPI api = FlinkRestAPI.create(FLINK_REST_URL);
        for (Job job : api.getJobs()) {
            Job richJob = api.getJob(job.getJid());
            if ((JOB_NAME + ruleId).equals(richJob.getName())) {
                api.terminateJob(richJob.getJid());
                return "Terminated successfully";
            }

        }
        return "No Such Job!";
    }

}
