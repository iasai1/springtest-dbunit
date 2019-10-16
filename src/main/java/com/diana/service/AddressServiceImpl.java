package com.diana.service;

import com.diana.dao.AddressDAO;
import com.diana.model.Address;
import com.diana.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@ComponentScan(basePackages = "com.diana.dao")
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressDAO addressDAO;

    @Override
    public Address findById(Long id) {
        return addressDAO.findById(id);
    }

    @Override
    public void add(Address address) {
        addressDAO.add(address);
    }
}
