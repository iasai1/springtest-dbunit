package com.diana.util;

import com.diana.model.Address;
import com.diana.model.Employee;

public class EmployeeDTO {

    private String name;
    private String phone;
    private String city;
    private String street;
    private String depName;

    public EmployeeDTO(String name, String phone, String city, String street, String depName) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.street = street;
        this.depName = depName;
    }

    public EmployeeDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Address getAddress(){
        return new Address(this.city, this.street);
    }

    public Employee getEmployee(){
        Employee e = new Employee();
        e.setName(this.name);
        e.setPhone(this.phone);
        return e;
    }

}
