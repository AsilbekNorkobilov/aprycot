package org.example.figma.model.dto.forsave;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManagerResDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
}
