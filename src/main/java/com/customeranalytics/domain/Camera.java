package com.customeranalytics.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.customeranalytics.domain.enumeration.CameraType;


/**
 * A Camera.
 */
@Entity
@Table(name = "camera")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Camera implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ipaddress")
    private String ipaddress;

    @Column(name = "location")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private CameraType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Camera name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public Camera ipaddress(String ipaddress) {
        this.ipaddress = ipaddress;
        return this;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getLocation() {
        return location;
    }

    public Camera location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CameraType getType() {
        return type;
    }

    public Camera type(CameraType type) {
        this.type = type;
        return this;
    }

    public void setType(CameraType type) {
        this.type = type;
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
        Camera camera = (Camera) o;
        if (camera.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), camera.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Camera{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ipaddress='" + getIpaddress() + "'" +
            ", location='" + getLocation() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
