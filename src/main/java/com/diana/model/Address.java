package com.diana.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Address {

    @Id
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Employee employee;

    public Address(){};

    public Address(String city, String street){
        this.city = city;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
