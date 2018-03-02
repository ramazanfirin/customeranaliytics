package com.customeranalytics.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.customeranalytics.service.enums.AgeEnum;
import com.customeranalytics.service.enums.GenderEnum;

@Service
public class NotifyService {


	long lastNotifyDate;
	Boolean notifyDone=false;
	
	
	@Async
	public void sendNotify(Float age,Float genderValue) throws IOException{
		GenderEnum gender = getGenderEnum(genderValue);
		AgeEnum ageEnum = getAgeGroup(age);
		String result = ageEnum.toString()+"_"+gender.toString();
		System.out.println(result);
		
		
		
		
	}
	
	private AgeEnum getAgeGroup(Float age){
		if(age<15)
			return AgeEnum.CHILD;
		else if(age>15 && age<35)
			return AgeEnum.YOUNG;
		else if(age>35 && age<55)
			return AgeEnum.MIDDLE_AGE;
		else if(age>55)
			return AgeEnum.OLDER;
		else
			return AgeEnum.MIDDLE_AGE;
	}
	
	private GenderEnum getGenderEnum(Float genderValue){
		if(genderValue<0)
			return GenderEnum.MALE;
		else
			return GenderEnum.FEMALE;
		
	}

	
	public static void main(String[] args) throws IOException {
//		NotifyService notifyService = new NotifyService();
//		Map<String,String> playListMap  = notifyService.getPlayLists();		
//		System.out.println("bitti");
	}

	public long getLastNotifyDate() {
		return lastNotifyDate;
	}

	public void setLastNotifyDate(long lastNotifyDate) {
		this.lastNotifyDate = lastNotifyDate;
	}

	public Boolean getNotifyDone() {
		return notifyDone;
	}

	public void setNotifyDone(Boolean notifyDone) {
		this.notifyDone = notifyDone;
	}
	
}
