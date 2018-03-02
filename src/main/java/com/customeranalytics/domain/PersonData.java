package com.customeranalytics.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.customeranalytics.domain.enumeration.AGE;

import com.customeranalytics.domain.enumeration.GENDER;

import com.customeranalytics.domain.enumeration.EMOTION;

/**
 * A PersonData.
 */
@Entity
@Table(name = "person_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "insert_date")
    private LocalDate insertDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "age")
    private AGE age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GENDER gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "emotion")
    private EMOTION emotion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInsertDate() {
        return insertDate;
    }

    public PersonData insertDate(LocalDate insertDate) {
        this.insertDate = insertDate;
        return this;
    }

    public void setInsertDate(LocalDate insertDate) {
        this.insertDate = insertDate;
    }

    public AGE getAge() {
        return age;
    }

    public PersonData age(AGE age) {
        this.age = age;
        return this;
    }

    public void setAge(AGE age) {
        this.age = age;
    }

    public GENDER getGender() {
        return gender;
    }

    public PersonData gender(GENDER gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public EMOTION getEmotion() {
        return emotion;
    }

    public PersonData emotion(EMOTION emotion) {
        this.emotion = emotion;
        return this;
    }

    public void setEmotion(EMOTION emotion) {
        this.emotion = emotion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonData personData = (PersonData) o;
        if (personData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonData{" +
            "id=" + getId() +
            ", insertDate='" + getInsertDate() + "'" +
            ", age='" + getAge() + "'" +
            ", gender='" + getGender() + "'" +
            ", emotion='" + getEmotion() + "'" +
            "}";
    }
}
