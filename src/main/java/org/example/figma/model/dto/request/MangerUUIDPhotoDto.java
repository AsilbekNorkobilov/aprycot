package org.example.figma.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MangerUUIDPhotoDto {
    private UUID id;
    private MultipartFile multipartFile;
}
