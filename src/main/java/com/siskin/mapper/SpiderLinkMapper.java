package com.siskin.mapper;

import com.siskin.entity.SpiderLink;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpiderLinkMapper {
    List<SpiderLink> getSpiderData(SpiderLink spiderLink);
}
