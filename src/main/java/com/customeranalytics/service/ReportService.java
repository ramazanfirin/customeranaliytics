package com.customeranalytics.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Service;

import com.customeranalytics.web.rest.vm.reports.GenderReportDTO;

@Service
public class ReportService {

	@PersistenceContext
    EntityManager entityManager;
	
public List<GenderReportDTO> getGenderReport(LocalDate startDate, LocalDate endDate, String camera) {
		
		String sql=" SELECT new com.customeranalytics.web.rest.vm.reports.GenderReportDTO(COUNT(*) , p.gender) FROM PersonData p where p.insertDate BETWEEN :startDate AND :endDate";
		if(!camera.equals("ALL"))
			sql=sql+" AND p.camera.name=:camera";
		sql=sql+" GROUP BY p.gender";
		
		Query query = entityManager.createQuery(sql);
        query.setParameter("startDate", startDate);  
        query.setParameter("endDate", endDate);  
       
        if(!camera.equals("ALL"))
        	query.setParameter("camera",camera);  
        
		
        
        return (List<GenderReportDTO>)query.getResultList();
        
	}
}
