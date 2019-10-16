package com.diana.service;

import com.diana.model.Address;
import com.diana.model.Employee;

public interface AddressService {

    Address findById(Long id);

    void add(Address address);

}
