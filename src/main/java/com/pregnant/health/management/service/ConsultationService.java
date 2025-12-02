package com.pregnant.health.management.service;

import com.pregnant.health.management.entity.Consultation;
import com.pregnant.health.management.entity.PageResult;

public interface ConsultationService {
    
    Consultation getById(Long id);
    
    PageResult<Consultation> getConsultationList(Integer page, Integer size);
    
    // 按标题模糊查询咨询列表
    PageResult<Consultation> getConsultationListByTitle(String title, Integer page, Integer size);
    
    // 按状态查询咨询列表
    PageResult<Consultation> getConsultationListByStatus(Integer status, Integer page, Integer size);
    
    // 按标题和状态查询咨询列表
    PageResult<Consultation> getConsultationListByTitleAndStatus(String title, Integer status, Integer page, Integer size);
    
    PageResult<Consultation> getConsultationListByDoctor(Long doctorId, Integer page, Integer size);
    
    Long getUnrepliedCount();
    
    boolean saveConsultation(Consultation consultation);
    
    boolean replyConsultation(Long id, String reply);
    
    boolean deleteConsultation(Long id);
}