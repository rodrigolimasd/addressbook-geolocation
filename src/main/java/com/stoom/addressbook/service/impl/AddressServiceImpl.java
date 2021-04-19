package com.stoom.addressbook.service.impl;

import com.stoom.addressbook.model.Address;
import com.stoom.addressbook.repository.AddressRepository;
import com.stoom.addressbook.service.AddressService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address create(Address address) {
        //TODO: validation
        //TODO: geolocation google api
        return addressRepository.save(address);
    }

    @Override
    public Address getById(String id) {
        return getAddressById(id);
    }

    @Override
    public Page<Address> getByZipcode(String zipcode, Pageable pageable) {
        return addressRepository.getByZipcode(zipcode, pageable);
    }

    @Override
    public Address update(Address address) {
        Address addressDb = getAddressById(address.getId());

        //setter values
        addressDb.setStreetName(address.getStreetName());
        addressDb.setNumber(address.getNumber());
        addressDb.setComplement(address.getComplement());
        addressDb.setNeighbourhood(address.getNeighbourhood());
        addressDb.setCity(address.getCity());
        addressDb.setState(address.getState());
        addressDb.setCountry(address.getCountry());
        addressDb.setZipcode(address.getZipcode());
        addressDb.setLatitude(address.getLatitude());
        addressDb.setLongitude(address.getLongitude());

        //TODO: valid
        //TODO: geolocation
        return addressRepository.save(addressDb);
    }

    @Override
    public void delete(String id) {
        Address addressDb = getAddressById(id);
        addressRepository.delete(addressDb);
    }

    private Address getAddressById(String id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }
}
