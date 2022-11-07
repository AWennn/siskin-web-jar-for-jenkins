package com.siskin.mapper;


import com.siskin.entity.Link;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LinkMapper {
    List<Link> getData(Link link);

}
