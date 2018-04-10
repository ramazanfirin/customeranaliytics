package com.customeranalytics.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.customeranalytics.domain.PersonData;
import com.customeranalytics.repository.PersonDataRepository;
import com.customeranalytics.service.ReportService;
import com.customeranalytics.web.rest.errors.BadRequestAlertException;
import com.customeranalytics.web.rest.util.HeaderUtil;
import com.customeranalytics.web.rest.vm.GenderQueryVM;
import com.customeranalytics.web.rest.vm.reports.AgeGenderReportDTO;
import com.customeranalytics.web.rest.vm.reports.AgeReportDTO;
import com.customeranalytics.web.rest.vm.reports.CountDateDTO;
import com.customeranalytics.web.rest.vm.reports.GenderReportDTO;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing PersonData.
 */
@RestController
@RequestMapping("/api")
public class PersonDataResource {

    private final Logger log = LoggerFactory.getLogger(PersonDataResource.class);

    private static final String ENTITY_NAME = "personData";

    private final PersonDataRepository personDataRepository;

    private final ReportService reportService;
    
    public PersonDataResource(PersonDataRepository personDataRepository,ReportService reportService) {
        this.personDataRepository = personDataRepository;
        this.reportService = reportService;
    }

    /**
     * POST  /person-data : Create a new personData.
     *
     * @param personData the personData to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personData, or with status 400 (Bad Request) if the personData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-data")
    @Timed
    public ResponseEntity<PersonData> createPersonData(@RequestBody PersonData personData) throws URISyntaxException {
        log.debug("REST request to save PersonData : {}", personData);
        if (personData.getId() != null) {
            throw new BadRequestAlertException("A new personData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonData result = personDataRepository.save(personData);
        return ResponseEntity.created(new URI("/api/person-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-data : Updates an existing personData.
     *
     * @param personData the personData to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personData,
     * or with status 400 (Bad Request) if the personData is not valid,
     * or with status 500 (Internal Server Error) if the personData couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-data")
    @Timed
    public ResponseEntity<PersonData> updatePersonData(@RequestBody PersonData personData) throws URISyntaxException {
        log.debug("REST request to update PersonData : {}", personData);
        if (personData.getId() == null) {
            return createPersonData(personData);
        }
        PersonData result = personDataRepository.save(personData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personData.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-data : get all the personData.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personData in body
     */
    @GetMapping("/person-data")
    @Timed
    public List<PersonData> getAllPersonData() {
        log.debug("REST request to get all PersonData");
        return personDataRepository.findAll();
        }

    /**
     * GET  /person-data/:id : get the "id" personData.
     *
     * @param id the id of the personData to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personData, or with status 404 (Not Found)
     */
    @GetMapping("/person-data/{id}")
    @Timed
    public ResponseEntity<PersonData> getPersonData(@PathVariable Long id) {
        log.debug("REST request to get PersonData : {}", id);
        PersonData personData = personDataRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personData));
    }

    /**
     * DELETE  /person-data/:id : delete the "id" personData.
     *
     * @param id the id of the personData to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-data/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonData(@PathVariable Long id) {
        log.debug("REST request to delete PersonData : {}", id);
        personDataRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    
    @PostMapping("/person-data/reports")
    @Timed
    public List<GenderReportDTO> getGenderReports(@RequestBody GenderQueryVM genderQueryVM) {
        //log.debug("REST request to get PersonData : {}", id);
        List<GenderReportDTO> result =reportService.getGenderReport(genderQueryVM.getStartDate(), genderQueryVM.getEndDate(), genderQueryVM.getCamera());
        
        return result;
    }
    
    @PostMapping("/person-data/agereports")
    @Timed
    public List<AgeReportDTO> getAgeReports(@RequestBody GenderQueryVM genderQueryVM) {
        //log.debug("REST request to get PersonData : {}", id);
        List<AgeReportDTO> result =reportService.getAgeReport(genderQueryVM.getStartDate(), genderQueryVM.getEndDate(), genderQueryVM.getCamera());
        
        return result;
    }
    
    @PostMapping("/person-data/agegenderreports")
    @Timed
    public List<AgeGenderReportDTO> getAgeGenderReports(@RequestBody GenderQueryVM genderQueryVM) {
        //log.debug("REST request to get PersonData : {}", id);
        List<AgeGenderReportDTO> result =reportService.getGenderAgeReport(genderQueryVM.getStartDate(), genderQueryVM.getEndDate(), genderQueryVM.getCamera());
        
        return result;
    }
    
    @PostMapping("/person-data/timeSeriesGenderReport")
    @Timed
    public List<CountDateDTO> getTimeSeriesGenderReport(@RequestBody GenderQueryVM genderQueryVM) {
        //log.debug("REST request to get PersonData : {}", id);
        List<CountDateDTO> result =reportService.getTimeSeriesGenderReport(genderQueryVM.getStartDate(), genderQueryVM.getEndDate(), genderQueryVM.getCamera(),genderQueryVM.getGender());
        
        return result;
    }
    
    @PostMapping("/person-data/timeSeriesGenderReportAll")
    @Timed
    public List<CountDateDTO> getTimeSeriesGenderReportAll(@RequestBody GenderQueryVM genderQueryVM) {
        //log.debug("REST request to get PersonData : {}", id);
        List<CountDateDTO> result =reportService.getTimeSeriesGenderReportAll(genderQueryVM.getStartDate(), genderQueryVM.getEndDate(), genderQueryVM.getCamera());
        
        return result;
    }
    
    @PostMapping("/person-data/timeSeriesAgeAndGenderReportAll")
    @Timed
    public List<CountDateDTO> getTimeSeriesAgeAndGenderReport(@RequestBody GenderQueryVM genderQueryVM) {
        //log.debug("REST request to get PersonData : {}", id);
        List<CountDateDTO> result =reportService.getTimeSeriesAgeAndGenderReport(genderQueryVM.getStartDate(), genderQueryVM.getEndDate(), genderQueryVM.getCamera(),genderQueryVM.getGender(),genderQueryVM.getAge());
        
        return result;
    }
    
}
