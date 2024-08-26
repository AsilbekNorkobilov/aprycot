package org.example.figma.model.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for {@link org.example.figma.entity.User}
 */

@Getter
@Setter
public class ManagerReqDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
}
