package com.siskin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.siskin.entity.Link;
import com.siskin.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Data")
public class LinkController {

    @Autowired
    LinkService linkService;

    @RequestMapping(value = "/getData")
    public String getData(@RequestBody Link link) throws ParseException {

        SimpleDateFormat links = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = links.parse(link.getStart_time());
        Date endDate = links.parse(link.getEnd_time());

        JSONObject json = new JSONObject();
        JSONArray xAxis = new JSONArray();
        JSONArray yAxis = new JSONArray();
        List<Link> data = linkService.getData(link);

        HashMap<String, Integer> linkCount = new HashMap<>();
        for (Link datum : data) {
            linkCount.put(datum.getStatistic_time(), datum.getStatistic_count());
        }

        for (long i = startDate.getTime(); i <= endDate.getTime(); i+=1 * 60 * 60 * 1000) {
            String dateTime = links.format(new Date(i));
            xAxis.add(dateTime);
            yAxis.add(linkCount.getOrDefault(dateTime, 0));
        }

        json.put("xAxis",xAxis);
        json.put("yAxis",yAxis);
        return json.toJSONString();
    }

}
