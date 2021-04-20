package com.stoom.addressbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.model.LatLng;
import com.stoom.addressbook.config.GoogleMapsServicesConfig;
import com.stoom.addressbook.model.Address;
import com.stoom.addressbook.repository.AddressRepository;
import com.stoom.addressbook.service.GoogleApiService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressControllerTest {

    private static final String ADRESSES_ENDPOINT="/v1/adresses";
    //enable for apple mac m1
    //static { System.setProperty("os.arch", "i686_64"); }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoogleMapsServicesConfig googleMapsServicesConfig;

    @MockBean
    private GeoApiContext geoApiContext;

    @MockBean
    private GoogleApiService googleApiService;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    public void initEach(){
        addressRepository.deleteAll();
        addressRepository.save(getAddress());
    }

    @SneakyThrows
    @Test
    void shouldCreateAndress(){
        Address address = getAddress();
        address.setZipcode("12399999");
        LatLng latLng = new LatLng(123D, 124D);
        when(googleApiService.retrieveLocation(Mockito.any())).thenReturn(latLng);

        this.mockMvc
                .perform(post(ADRESSES_ENDPOINT)
                    .content(asJsonString(address))
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.latitude").value(123D));
    }

    @SneakyThrows
    @Test
    void shouldUpdateAndress(){
        Address address = addressRepository.findAll().get(0);
        when(googleApiService.retrieveLocation(Mockito.any())).thenReturn(new LatLng(123D, 124D));

        this.mockMvc
                .perform(put(ADRESSES_ENDPOINT)
                        .content(asJsonString(address))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @SneakyThrows
    @Test
    void shouldGetAddressById() {
        Address address = addressRepository.findAll().get(0);

        this.mockMvc
                .perform(get(ADRESSES_ENDPOINT.concat("/{id}"), address.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void shouldDeleteAddressById() {
        Address address = addressRepository.findAll().get(0);

        this.mockMvc
                .perform(delete(ADRESSES_ENDPOINT.concat("/{id}"), address.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Address getAddress(){
        return Address.builder()
                .streetName("test")
                .number(123)
                .neighbourhood("test")
                .city("test")
                .state("test")
                .country("test")
                .zipcode("123456")
                .build();
    }
}