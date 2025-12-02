package com.pregnant.health.management.service;

import com.pregnant.health.management.entity.Knowledge;
import com.pregnant.health.management.entity.PageResult;

public interface KnowledgeService {
    
    Knowledge getById(Long id);
    
    PageResult<Knowledge> getKnowledgeList(Integer page, Integer size);
    
    // 按标题模糊查询知识列表
    PageResult<Knowledge> getKnowledgeListByTitle(String title, Integer page, Integer size);
    
    PageResult<Knowledge> getPublishedKnowledgeList(Integer page, Integer size);
    
    boolean saveKnowledge(Knowledge knowledge);
    
    boolean updateKnowledge(Knowledge knowledge);
    
    boolean deleteKnowledge(Long id);
}