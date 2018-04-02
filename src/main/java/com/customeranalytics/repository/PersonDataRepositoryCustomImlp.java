package com.customeranalytics.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import com.customeranalytics.web.rest.vm.reports.GenderReportDTO;

@Transactional
public class PersonDataRepositoryCustomImlp implements PersonDataRepositoryCustom{


    @PersistenceContext
    EntityManager entityManager;
	
	@Override
	public List<GenderReportDTO> getGenderReport(Date startDate, Date endDate, String camera) {
		
		String sql=" SELECT COUNT(*) as count, gender FROM person_data p where p.insertDate BETWEEN :startDate AND :endDate ";
		if(camera!="ALL")
			sql=sql+" AND p.camera.name=:camera";
		sql=sql+" GROUP BY gender";
		
		Query query = entityManager.createNativeQuery(sql, GenderReportDTO.class);
        query.setParameter("startDate", startDate, TemporalType.DATE);  
        query.setParameter("endDate", endDate, TemporalType.DATE);  
       
        if(camera!="ALL")
        	query.setParameter("camera",camera);  
        
		
        
        return (List<GenderReportDTO>)query.getResultList();
        
	}

}
