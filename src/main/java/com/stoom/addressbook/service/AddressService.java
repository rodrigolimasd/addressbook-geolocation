package com.stoom.addressbook.service;

import com.stoom.addressbook.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {
    Address create(Address address) throws Exception;
    Address getById(String id);
    Page<Address> getByZipcode(String zipcode, Pageable pageable);
    Address update(Address address) throws Exception;
    void delete(String id);
}
