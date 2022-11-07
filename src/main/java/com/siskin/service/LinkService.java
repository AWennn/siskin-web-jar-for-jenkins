package com.siskin.service;


import com.siskin.entity.Link;
import com.siskin.mapper.LinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {

    @Autowired
    LinkMapper linkMapper;

    public List<Link> getData(Link link){
        return linkMapper.getData(link);
    }

}
