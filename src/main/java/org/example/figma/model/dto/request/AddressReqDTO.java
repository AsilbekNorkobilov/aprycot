package org.example.figma.model.dto.request;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link org.example.figma.entity.Address}
 */
@Value
public class AddressReqDTO implements Serializable {
    UUID id;
}