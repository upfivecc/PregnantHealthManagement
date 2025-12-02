package com.pregnant.health.management.service;

import com.pregnant.health.management.entity.Consultation;
import com.pregnant.health.management.entity.PageResult;

public interface ConsultationService {
    
    Consultation getById(Long id);
    
    PageResult<Consultation> getConsultationList(Integer page, Integer size);
    
    PageResult<Consultation> getConsultationListByDoctor(Long doctorId, Integer page, Integer size);
    
    Long getUnrepliedCount();
    
    boolean saveConsultation(Consultation consultation);
    
    boolean replyConsultation(Long id, String reply);
    
    boolean deleteConsultation(Long id);
}