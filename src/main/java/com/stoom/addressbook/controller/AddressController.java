package com.stoom.addressbook.controller;

import com.stoom.addressbook.dto.AddressFilterDTO;
import com.stoom.addressbook.model.Address;
import com.stoom.addressbook.service.AddressService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Api
@Log4j2
@RestController
@RequestMapping("/v1/adresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable String id) {
        log.info( "get address by id {}", id);
        Address address = addressService.getById(id);
        log.info("address {}", address);
        return ResponseEntity.ok(address);
    }

    @GetMapping
    public ResponseEntity<?> filter(AddressFilterDTO addressFilterDTO, Pageable pageable) {
        log.info("filtering addresses {}", addressFilterDTO);
        Page<Address> list = addressService.getByZipcode(addressFilterDTO.getZipcode(), pageable);
        log.info("filtered addresses, total: {}", list.getSize());
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Address address) throws Exception {
        log.info("creating address {}", address);
        Address newAddress = addressService.create(address);
        log.info("address created {}", newAddress);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(newAddress.getId())
                .toUri();
        return ResponseEntity.created(uri).body(newAddress);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid Address address) throws Exception {
        log.info("updating address {}", address);
        addressService.update(address);
        log.info("address updates {}", address);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id){
        log.info("deleting address by id: {}", id);
        addressService.delete(id);
        log.info("address deleted. id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
