package org.example.figma.service;

import org.example.figma.dto.AddressDTO;
import org.example.figma.entity.Address;
import org.example.figma.entity.User;
import org.example.figma.model.dto.request.ManagerReqDto;
import org.example.figma.model.dto.request.UserReqDTO;
import org.example.figma.model.dto.response.UserResDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    ResponseEntity<List<UserResDto>> getMangers();

    ResponseEntity<User> changeUserDetails(UserReqDTO userReqDTO);

    void savePhoto(MultipartFile photo);

    String saveManager(ManagerReqDto managerReqDto, MultipartFile multipartFile) throws IOException;

    ResponseEntity<List<AddressDTO>> getAddresses();

    ResponseEntity<Address> saveAddress(AddressDTO addressDTO);
}
