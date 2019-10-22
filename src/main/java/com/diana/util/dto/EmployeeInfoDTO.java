package com.diana.util.dto;

public class EmployeeInfoDTO {

    private String city;
    private String street;
    private String depName;

    public EmployeeInfoDTO(){}

    public EmployeeInfoDTO(String city, String street, String depName) {
        this.city = city;
        this.street = street;
        this.depName = depName;
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

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }
}
