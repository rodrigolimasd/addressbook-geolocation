package com.stoom.addressbook;

import com.google.maps.GeoApiContext;
import com.stoom.addressbook.config.GoogleMapsServicesConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AddressbookApplicationTests {

	@MockBean
	private GoogleMapsServicesConfig googleMapsServicesConfig;

	@MockBean
	private GeoApiContext geoApiContext;

	@Test
	void contextLoads() {
	}

}
