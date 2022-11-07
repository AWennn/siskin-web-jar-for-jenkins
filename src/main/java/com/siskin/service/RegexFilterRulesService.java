package com.siskin.service;

import com.siskin.entity.RegexFilterRules;
import com.siskin.mapper.RegexFilterRulesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegexFilterRulesService {

    @Autowired
    private RegexFilterRulesMapper filterRulesMapper;

    public List<RegexFilterRules> getFilter(){
        return filterRulesMapper.getFilter();
    }

    public RegexFilterRules getFilterById(int rfr_id){
        return filterRulesMapper.getFilterById(rfr_id);
    }

    public Integer insertFilter(RegexFilterRules filter){
        return filterRulesMapper.setFilter(filter);
    }

    public Integer updateFilter(RegexFilterRules filter) {
        return filterRulesMapper.updateFilter(filter);
    }

    public Integer deleteFilter(RegexFilterRules filter){
        return filterRulesMapper.deleteFilter(filter);
    }

}
