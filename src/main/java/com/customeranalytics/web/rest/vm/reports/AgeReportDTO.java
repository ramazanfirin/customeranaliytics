package com.customeranalytics.web.rest.vm.reports;

import com.customeranalytics.domain.enumeration.AGE;

public class AgeReportDTO {

	Long count;
	AGE age;
	
	
	
	public AgeReportDTO(Long count, AGE age) {
		super();
		this.count = count;
		this.age = age;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public AGE getAge() {
		return age;
	}
	public void setAge(AGE age) {
		this.age = age;
	}
	
	
	
}
