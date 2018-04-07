package com.customeranalytics.repository;

import java.util.Date;
import java.util.List;

import com.customeranalytics.web.rest.vm.reports.AgeReportDTO;
import com.customeranalytics.web.rest.vm.reports.GenderReportDTO;

public interface PersonDataRepositoryCustom {

	List<GenderReportDTO> getGenderReport(Date startDate, Date endDate, String camera);
	List<AgeReportDTO> getAgeReport(Date startDate, Date endDate, String camera);

}
