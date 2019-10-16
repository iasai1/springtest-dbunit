package com.diana.dao;

import com.diana.model.Address;

public interface AddressDAO {

    Address findById(Long id);

    void add(Address address);


}
