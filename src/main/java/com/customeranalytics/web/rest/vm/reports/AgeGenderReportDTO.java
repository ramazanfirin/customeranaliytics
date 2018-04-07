package com.customeranalytics.web.rest.vm.reports;

import com.customeranalytics.domain.enumeration.AGE;
import com.customeranalytics.domain.enumeration.GENDER;

public class AgeGenderReportDTO {

	Long count;
	AGE age;
	GENDER gender;
	
	public AgeGenderReportDTO(Long count, AGE age, GENDER gender) {
		super();
		this.count = count;
		this.age = age;
		this.gender = gender;
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
	public GENDER getGender() {
		return gender;
	}
	public void setGender(GENDER gender) {
		this.gender = gender;
	}
	
	
	
}
