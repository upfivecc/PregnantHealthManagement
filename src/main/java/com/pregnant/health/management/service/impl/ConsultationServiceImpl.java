package com.pregnant.health.management.service.impl;

import com.pregnant.health.management.entity.Consultation;
import com.pregnant.health.management.entity.PageResult;
import com.pregnant.health.management.mapper.ConsultationMapper;
import com.pregnant.health.management.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultationServiceImpl implements ConsultationService {
    
    @Autowired
    private ConsultationMapper consultationMapper;
    
    @Override
    public Consultation getById(Long id) {
        return consultationMapper.selectById(id);
    }
    
    @Override
    public PageResult<Consultation> getConsultationList(Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Consultation> consultations = consultationMapper.selectAll(offset, size);
        Long total = consultationMapper.countAll();
        return new PageResult<>(consultations, total, page, size);
    }
    
    @Override
    public PageResult<Consultation> getConsultationListByDoctor(Long doctorId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Consultation> consultations = consultationMapper.selectByDoctorId(doctorId, offset, size);
        Long total = consultationMapper.countByDoctorId(doctorId);
        return new PageResult<>(consultations, total, page, size);
    }
    
    @Override
    public Long getUnrepliedCount() {
        return consultationMapper.countUnreplied();
    }
    
    @Override
    public boolean saveConsultation(Consultation consultation) {
        return consultationMapper.insert(consultation) > 0;
    }
    
    @Override
    public boolean replyConsultation(Long id, String reply) {
        return consultationMapper.reply(id, reply) > 0;
    }
    
    @Override
    public boolean deleteConsultation(Long id) {
        return consultationMapper.deleteById(id) > 0;
    }
}