package com.stoom.addressbook.repository;

import com.stoom.addressbook.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {
    Page<Address> getByZipcode(String zipcode, Pageable pageable);
    Optional<Address> findFirstByZipcodeAndIdNotIn(String zipcode, String id);
}
