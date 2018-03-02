package com.customeranalytics.web.rest;

import com.customeranalytics.CustomeranalyticsApp;

import com.customeranalytics.domain.PersonData;
import com.customeranalytics.repository.PersonDataRepository;
import com.customeranalytics.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.customeranalytics.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.customeranalytics.domain.enumeration.AGE;
import com.customeranalytics.domain.enumeration.GENDER;
import com.customeranalytics.domain.enumeration.EMOTION;
/**
 * Test class for the PersonDataResource REST controller.
 *
 * @see PersonDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomeranalyticsApp.class)
public class PersonDataResourceIntTest {

    private static final LocalDate DEFAULT_INSERT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INSERT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final AGE DEFAULT_AGE = AGE.CHILD;
    private static final AGE UPDATED_AGE = AGE.YOUNG;

    private static final GENDER DEFAULT_GENDER = GENDER.MALE;
    private static final GENDER UPDATED_GENDER = GENDER.FEMALE;

    private static final EMOTION DEFAULT_EMOTION = EMOTION.HAPPY;
    private static final EMOTION UPDATED_EMOTION = EMOTION.SURPRISE;

    @Autowired
    private PersonDataRepository personDataRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonDataMockMvc;

    private PersonData personData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonDataResource personDataResource = new PersonDataResource(personDataRepository);
        this.restPersonDataMockMvc = MockMvcBuilders.standaloneSetup(personDataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonData createEntity(EntityManager em) {
        PersonData personData = new PersonData()
            .insertDate(DEFAULT_INSERT_DATE)
            .age(DEFAULT_AGE)
            .gender(DEFAULT_GENDER)
            .emotion(DEFAULT_EMOTION);
        return personData;
    }

    @Before
    public void initTest() {
        personData = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonData() throws Exception {
        int databaseSizeBeforeCreate = personDataRepository.findAll().size();

        // Create the PersonData
        restPersonDataMockMvc.perform(post("/api/person-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personData)))
            .andExpect(status().isCreated());

        // Validate the PersonData in the database
        List<PersonData> personDataList = personDataRepository.findAll();
        assertThat(personDataList).hasSize(databaseSizeBeforeCreate + 1);
        PersonData testPersonData = personDataList.get(personDataList.size() - 1);
        assertThat(testPersonData.getInsertDate()).isEqualTo(DEFAULT_INSERT_DATE);
        assertThat(testPersonData.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPersonData.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPersonData.getEmotion()).isEqualTo(DEFAULT_EMOTION);
    }

    @Test
    @Transactional
    public void createPersonDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personDataRepository.findAll().size();

        // Create the PersonData with an existing ID
        personData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonDataMockMvc.perform(post("/api/person-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personData)))
            .andExpect(status().isBadRequest());

        // Validate the PersonData in the database
        List<PersonData> personDataList = personDataRepository.findAll();
        assertThat(personDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonData() throws Exception {
        // Initialize the database
        personDataRepository.saveAndFlush(personData);

        // Get all the personDataList
        restPersonDataMockMvc.perform(get("/api/person-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personData.getId().intValue())))
            .andExpect(jsonPath("$.[*].insertDate").value(hasItem(DEFAULT_INSERT_DATE.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].emotion").value(hasItem(DEFAULT_EMOTION.toString())));
    }

    @Test
    @Transactional
    public void getPersonData() throws Exception {
        // Initialize the database
        personDataRepository.saveAndFlush(personData);

        // Get the personData
        restPersonDataMockMvc.perform(get("/api/person-data/{id}", personData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personData.getId().intValue()))
            .andExpect(jsonPath("$.insertDate").value(DEFAULT_INSERT_DATE.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.emotion").value(DEFAULT_EMOTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonData() throws Exception {
        // Get the personData
        restPersonDataMockMvc.perform(get("/api/person-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonData() throws Exception {
        // Initialize the database
        personDataRepository.saveAndFlush(personData);
        int databaseSizeBeforeUpdate = personDataRepository.findAll().size();

        // Update the personData
        PersonData updatedPersonData = personDataRepository.findOne(personData.getId());
        // Disconnect from session so that the updates on updatedPersonData are not directly saved in db
        em.detach(updatedPersonData);
        updatedPersonData
            .insertDate(UPDATED_INSERT_DATE)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .emotion(UPDATED_EMOTION);

        restPersonDataMockMvc.perform(put("/api/person-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonData)))
            .andExpect(status().isOk());

        // Validate the PersonData in the database
        List<PersonData> personDataList = personDataRepository.findAll();
        assertThat(personDataList).hasSize(databaseSizeBeforeUpdate);
        PersonData testPersonData = personDataList.get(personDataList.size() - 1);
        assertThat(testPersonData.getInsertDate()).isEqualTo(UPDATED_INSERT_DATE);
        assertThat(testPersonData.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPersonData.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPersonData.getEmotion()).isEqualTo(UPDATED_EMOTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonData() throws Exception {
        int databaseSizeBeforeUpdate = personDataRepository.findAll().size();

        // Create the PersonData

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonDataMockMvc.perform(put("/api/person-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personData)))
            .andExpect(status().isCreated());

        // Validate the PersonData in the database
        List<PersonData> personDataList = personDataRepository.findAll();
        assertThat(personDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonData() throws Exception {
        // Initialize the database
        personDataRepository.saveAndFlush(personData);
        int databaseSizeBeforeDelete = personDataRepository.findAll().size();

        // Get the personData
        restPersonDataMockMvc.perform(delete("/api/person-data/{id}", personData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonData> personDataList = personDataRepository.findAll();
        assertThat(personDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonData.class);
        PersonData personData1 = new PersonData();
        personData1.setId(1L);
        PersonData personData2 = new PersonData();
        personData2.setId(personData1.getId());
        assertThat(personData1).isEqualTo(personData2);
        personData2.setId(2L);
        assertThat(personData1).isNotEqualTo(personData2);
        personData1.setId(null);
        assertThat(personData1).isNotEqualTo(personData2);
    }
}
