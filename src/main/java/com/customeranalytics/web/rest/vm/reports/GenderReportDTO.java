package com.customeranalytics.web.rest.vm.reports;

import com.customeranalytics.domain.enumeration.GENDER;

public class GenderReportDTO {

	Long count;
	GENDER gender;
	
	
	
	public GenderReportDTO(Long count, GENDER gender) {
		super();
		this.count = count;
		this.gender = gender;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public GENDER getGender() {
		return gender;
	}
	public void setGender(GENDER gender) {
		this.gender = gender;
	}
	
	
}
