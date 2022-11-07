package com.siskin.service;

import com.siskin.entity.SpiderLink;
import com.siskin.mapper.SpiderLinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpiderLinkService {

    @Autowired
    SpiderLinkMapper spiderLinkMapper;
    public List<SpiderLink> getSpiderData(SpiderLink spiderLink){
        return spiderLinkMapper.getSpiderData(spiderLink);
    }
}
