package com.stoom.addressbook.service.impl;

import com.google.maps.model.LatLng;
import com.stoom.addressbook.exception.AddressNotFoundException;
import com.stoom.addressbook.exception.RegisteredZipcodeException;
import com.stoom.addressbook.model.Address;
import com.stoom.addressbook.repository.AddressRepository;
import com.stoom.addressbook.service.AddressService;
import com.stoom.addressbook.service.GoogleApiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final GoogleApiService googleApiService;

    public AddressServiceImpl(AddressRepository addressRepository, GoogleApiService googleApiService) {
        this.addressRepository = addressRepository;
        this.googleApiService = googleApiService;
    }

    @Override
    public Address create(Address address) throws Exception {
        valid(address);
        retrieveGeolocation(address);
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
    public Address update(Address address) throws Exception {
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


        valid(address);
        retrieveGeolocation(address);
        return addressRepository.save(addressDb);
    }

    @Override
    public void delete(String id) {
        Address addressDb = getAddressById(id);
        addressRepository.delete(addressDb);
    }

    private void valid(Address address) {
        String id = address.getId()!=null ? address.getId() : "";
        if(addressRepository.findFirstByZipcodeAndIdNotIn(address.getZipcode(), id).isPresent()){
            throw new RegisteredZipcodeException("This zip code is already in use");
        }
    }

    private Address getAddressById(String id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found"));
    }

    private void retrieveGeolocation(Address address) throws Exception {
        if(address.getLatitude()==null || address.getLongitude()==null){
            LatLng latLng =  googleApiService.retrieveLocation(address);
            address.setLatitude(latLng.lat);
            address.setLongitude(latLng.lng);
        }
    }
}
