package com.stoom.addressbook.service;

import com.google.maps.model.LatLng;
import com.stoom.addressbook.model.Address;

public interface GoogleApiService {

    LatLng retrieveLocation(Address address) throws Exception;
}
