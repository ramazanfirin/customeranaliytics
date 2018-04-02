package com.customeranalytics.web.rest.vm;

import java.time.LocalDate;
import java.util.Date;

public class GenderQueryVM {

	LocalDate startDate;
	LocalDate endDate;
	String camera;
	
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getCamera() {
		return camera;
	}
	public void setCamera(String camera) {
		this.camera = camera;
	}
	
	
}
