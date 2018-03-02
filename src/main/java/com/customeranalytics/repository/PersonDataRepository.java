package com.customeranalytics.repository;

import com.customeranalytics.domain.PersonData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PersonData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonDataRepository extends JpaRepository<PersonData, Long> {

}
