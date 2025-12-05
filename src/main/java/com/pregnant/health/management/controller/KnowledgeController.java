package com.pregnant.health.management.controller;

import com.pregnant.health.management.entity.Knowledge;
import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeController {
    
    @Autowired
    private KnowledgeService knowledgeService;
    
    @GetMapping("/{id}")
    public Result<Knowledge> getKnowledgeById(@PathVariable Long id) {
        Knowledge knowledge = knowledgeService.getById(id);
        if (knowledge != null) {
            return Result.success(knowledge);
        } else {
            return Result.error("知识不存在");
        }
    }
    
    @GetMapping("/page")
    public Result<PageResult<Knowledge>> getKnowledgeList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer status) {
        PageResult<Knowledge> result = knowledgeService.getKnowledgeList(page, size, title, category, status);
        return Result.success(result);
    }
    
    @GetMapping
    public Result<PageResult<Knowledge>> getKnowledgeList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "false") Boolean published,
            @RequestParam(required = false) String title) {
        PageResult<Knowledge> result;
        if (title != null && !title.isEmpty()) {
            // 按标题查询
            result = knowledgeService.getKnowledgeListByTitle(title, page, size);
        } else if (published) {
            result = knowledgeService.getPublishedKnowledgeList(page, size);
        } else {
            result = knowledgeService.getKnowledgeList(page, size);
        }
        return Result.success(result);
    }
    
    @PostMapping
    public Result<String> saveKnowledge(@RequestBody Knowledge knowledge) {
        boolean success = knowledgeService.saveKnowledge(knowledge);
        if (success) {
            return Result.success("保存成功");
        } else {
            return Result.error("保存失败");
        }
    }
    
    @PutMapping("/{id}")
    public Result<String> updateKnowledge(@PathVariable Long id, @RequestBody Knowledge knowledge) {
        knowledge.setId(id);
        boolean success = knowledgeService.updateKnowledge(knowledge);
        if (success) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteKnowledge(@PathVariable Long id) {
        boolean success = knowledgeService.deleteKnowledge(id);
        if (success) {
            return Result.success("删除成功");
        } else {
            return Result.error("删除失败");
        }
    }
}