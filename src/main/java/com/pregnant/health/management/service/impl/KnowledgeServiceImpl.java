package com.pregnant.health.management.service.impl;

import com.pregnant.health.management.entity.Knowledge;
import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.mapper.KnowledgeMapper;
import com.pregnant.health.management.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    
    @Override
    public Knowledge getById(Long id) {
        return knowledgeMapper.selectById(id);
    }
    
    @Override
    public PageResult<Knowledge> getKnowledgeList(Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Knowledge> knowledgeList = knowledgeMapper.selectAll(offset, size);
        Long total = knowledgeMapper.countAll();
        return new PageResult<>(knowledgeList, total, page, size);
    }
    
    @Override
    public PageResult<Knowledge> getKnowledgeListByTitle(String title, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Knowledge> knowledgeList = knowledgeMapper.selectByTitle(title, offset, size);
        Long total = knowledgeMapper.countByTitle(title);
        return new PageResult<>(knowledgeList, total, page, size);
    }
    
    @Override
    public PageResult<Knowledge> getKnowledgeList(Integer page, Integer size, String title, String category, Integer status) {
        int offset = (page - 1) * size;
        List<Knowledge> knowledgeList = knowledgeMapper.selectByConditions(title, category, status, offset, size);
        Long total = knowledgeMapper.countByConditions(title, category, status);
        return new PageResult<>(knowledgeList, total, page, size);
    }
    
    @Override
    public PageResult<Knowledge> getPublishedKnowledgeList(Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Knowledge> knowledgeList = knowledgeMapper.selectPublished(offset, size);
        Long total = knowledgeMapper.countPublished();
        return new PageResult<>(knowledgeList, total, page, size);
    }
    
    @Override
    public boolean saveKnowledge(Knowledge knowledge) {
        return knowledgeMapper.insert(knowledge) > 0;
    }
    
    @Override
    public boolean updateKnowledge(Knowledge knowledge) {
        return knowledgeMapper.update(knowledge) > 0;
    }
    
    @Override
    public boolean deleteKnowledge(Long id) {
        return knowledgeMapper.deleteById(id) > 0;
    }
}