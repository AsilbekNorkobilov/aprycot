package org.example.figma.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.figma.entity.Address}
 */
@Value
public class AddressDTO implements Serializable {
    String district;
    String street;
    String house;
    String apartment;
    Double longitude;
    Double latitude;
}