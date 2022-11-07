package com.siskin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.siskin.entity.SpiderLink;
import com.siskin.service.SpiderLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Data")
public class SpiderLinkController {

    @Autowired
    SpiderLinkService spiderLinkService;

    @RequestMapping("/getSpiderData")
    public String getSpiderData(@RequestBody SpiderLink spiderLink) throws ParseException {

        SimpleDateFormat SpiderLinks = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = SpiderLinks.parse(spiderLink.getStart_time());
        Date endDate = SpiderLinks.parse(spiderLink.getEnd_time());

        JSONObject json = new JSONObject();
        JSONArray xAxis = new JSONArray();
        JSONArray yAxis = new JSONArray();
        List<SpiderLink> spiderData = spiderLinkService.getSpiderData(spiderLink);

        HashMap<String, Integer> SpiderLinkCount = new HashMap<>();
        for (SpiderLink datum : spiderData) {
            SpiderLinkCount.put(datum.getStatistic_time(), datum.getStatistic_count());
        }

        for (long i = startDate.getTime(); i <= endDate.getTime(); i+=1 * 60 * 60 * 1000) {
            String dateTime = SpiderLinks.format(new Date(i));
            xAxis.add(dateTime);
            yAxis.add(SpiderLinkCount.getOrDefault(dateTime, 0));
        }

        json.put("xAxis",xAxis);
        json.put("yAxis",yAxis);
        return json.toJSONString();
    }

//    @RequestMapping("/terminate")
//    public void terminate() {
//
//        int ruleId = 0;
//        terminate(ruleId);
//
//    }
//
//    public void terminate(int Id) {
//        // ..
//    }

}
