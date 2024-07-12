package org.example.figma.model.dto.request;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * DTO for {@link org.example.figma.entity.User}
 */
@Value
public class UserReqDTO implements Serializable {
    String firstName;
    String lastName;
    String phone;
    String email;
    String password;
    MultipartFile attachmentFullImage;
}