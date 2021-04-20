package com.stoom.addressbook.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleMapsServicesConfig {

    private final String googleApiKey;

    public GoogleMapsServicesConfig(@Value("${integration.services.google-maps.apikey}") String googleApiKey) {
        this.googleApiKey = googleApiKey;
    }

    @Bean public GeoApiContext geoApiContext() {
        return new GeoApiContext.Builder().apiKey(googleApiKey).maxRetries(3).build();
    }
}
