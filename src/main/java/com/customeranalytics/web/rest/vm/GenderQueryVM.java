package com.customeranalytics.web.rest.vm;

import java.time.LocalDate;
import java.util.Date;

public class GenderQueryVM {

	Date startDate;
	Date endDate;
	String camera;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getCamera() {
		return camera;
	}
	public void setCamera(String camera) {
		this.camera = camera;
	}
	
	
}
