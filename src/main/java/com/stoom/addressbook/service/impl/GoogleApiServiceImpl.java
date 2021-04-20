package com.stoom.addressbook.service.impl;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.stoom.addressbook.exception.GeolocationNotFound;
import com.stoom.addressbook.model.Address;
import com.stoom.addressbook.service.GoogleApiService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GoogleApiServiceImpl implements GoogleApiService {

    private final GeoApiContext geoApiContext;

    public GoogleApiServiceImpl(GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
    }

    @Override
    public LatLng retrieveLocation(Address address) throws Exception {

        Set<GeocodingResult> results = Arrays.stream(GeocodingApi.geocode(geoApiContext, getQueryAddress(address))
                .await()).collect(Collectors.toSet());

        return results.stream().findFirst()
                .orElseThrow(() -> new GeolocationNotFound("Geolocation not found."))
                .geometry.location;
    }

    private String getQueryAddress(Address address){
        return address.getNumber().toString()
                .concat(address.getStreetName().replace(" ","+"))
                .concat(",+")
                .concat(address.getCity().replace(" ","+"))
                .concat(",+").concat(address.getState());
    }
}
