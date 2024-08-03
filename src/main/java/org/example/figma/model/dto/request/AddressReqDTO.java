package org.example.figma.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link org.example.figma.entity.Address}
 */
@Value
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AddressReqDTO {
    UUID id;
}