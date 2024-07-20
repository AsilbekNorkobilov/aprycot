package org.example.figma.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerReqDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
}
