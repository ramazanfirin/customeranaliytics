package com.customeranalytics.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customeranalytics.domain.PersonData;
import com.customeranalytics.web.rest.vm.reports.GenderReportDTO;


/**
 * Spring Data JPA repository for the PersonData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonDataRepository extends JpaRepository<PersonData, Long>,PersonDataRepositoryCustom {

	/*
	
	SELECT 
   COUNT(*), 
   insert_date as "MyDateField",
   age,
   gender

FROM person_data

GROUP BY insert_date,age,gender

ORDER BY insert_date desc
	
	**/
	
	
}
