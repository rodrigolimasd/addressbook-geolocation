package com.stoom.addressbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Document(collection = "adresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable  {
    @Id
    private String id;
    @NotEmpty
    private String streetName;
    @NotNull
    private Integer number;
    private String complement;
    @NotEmpty
    private String neighbourhood;
    @NotEmpty
    private String city;
    @NotEmpty
    private String state;
    @NotEmpty
    private String  country;
    @NotEmpty
    private String zipcode;
    private Double latitude;
    private Double longitude;
}
