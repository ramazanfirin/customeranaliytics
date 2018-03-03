package com.customeranalytics.service;

import java.io.IOException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.customeranalytics.domain.PersonData;
import com.customeranalytics.domain.enumeration.AGE;
import com.customeranalytics.domain.enumeration.GENDER;
import com.customeranalytics.repository.PersonDataRepository;
import com.customeranalytics.web.rest.AccountResource;


@Service
public class NotifyService {

    private final Logger log = LoggerFactory.getLogger(NotifyService.class);
	
	private final  PersonDataRepository personDataRepository;
	
	
	
	public NotifyService(PersonDataRepository personDataRepository) {
		super();
		this.personDataRepository = personDataRepository;
	}

	@Async
	public void sendNotify(Float age,Float genderValue) throws IOException{
		GENDER gender = getGenderEnum(genderValue);
		AGE ageEnum = getAgeGroup(age);
		String result = ageEnum.toString()+"_"+gender.toString();
		System.out.println(result);
		
		PersonData personData = new PersonData();
		personData.setAge(ageEnum);
		personData.setEmotion(null);
		personData.setGender(gender);
		personData.setInsertDate(LocalDate.now());
		
		
		personDataRepository.save(personData);
		log.info("persondata kaydi yapildi."+personData.getAge()+","+personData.getGender().toString());
	}
	
	private AGE getAgeGroup(Float age){
		if(age<15)
			return AGE.CHILD;
		else if(age>15 && age<35)
			return AGE.YOUNG;
		else if(age>35 && age<55)
			return AGE.MIDDLE;
		else if(age>55)
			return AGE.OLDER;
		else
			return AGE.MIDDLE;
	}
	
	private GENDER getGenderEnum(Float genderValue){
		if(genderValue<0)
			return GENDER.MALE;
		else
			return GENDER.FEMALE;
		
	}

	
	
}
