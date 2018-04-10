package com.customeranalytics.web.rest.vm.reports;

import java.time.LocalDate;
import java.util.Date;

import com.customeranalytics.domain.enumeration.AGE;
import com.customeranalytics.domain.enumeration.GENDER;

public class CountDateDTO {

	Long count;
	LocalDate date;
	GENDER gender;
	AGE age;
	
	
	
	public CountDateDTO(Long count, LocalDate date, GENDER gender, AGE age) {
		super();
		this.count = count;
		this.date = date;
		this.gender = gender;
		this.age = age;
	}
	
	
	public CountDateDTO(Long count, LocalDate date) {
		super();
		this.count = count;
		this.date = date;
	}


	public CountDateDTO(Long count, LocalDate date, GENDER gender) {
		super();
		this.count = count;
		this.date = date;
		this.gender = gender;
	}


	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public GENDER getGender() {
		return gender;
	}
	public void setGender(GENDER gender) {
		this.gender = gender;
	}
	public AGE getAge() {
		return age;
	}
	public void setAge(AGE age) {
		this.age = age;
	}
	
	
}
