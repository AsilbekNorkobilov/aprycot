package org.example.figma.service;

import org.example.figma.entity.User;
import org.example.figma.model.dto.request.UserReqDTO;
import org.example.figma.model.dto.response.UserResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    ResponseEntity<List<UserResDto>> getMangers();

    ResponseEntity<User> changeUserDetails(UserReqDTO userReqDTO);

    void savePhoto(MultipartFile photo);

    UUID saveManager(ManagerResDto managerResDto);

    @SneakyThrows
    String saveManagerPhoto(MangerUUIDPhotoDto manager) throws IOException;

    ResponseEntity<List<AddressDTO>> getAddresses();

    void saveAddress(AddressDTO addressDTO);
}
