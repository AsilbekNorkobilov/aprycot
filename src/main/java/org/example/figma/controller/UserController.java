package org.example.figma.controller;


import lombok.RequiredArgsConstructor;
import org.example.figma.dto.AddressDTO;
import org.example.figma.entity.Address;
import org.example.figma.entity.User;
import org.example.figma.model.dto.request.UserReqDTO;
import org.example.figma.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping
    private ResponseEntity<User> changeUserCredentials(@ModelAttribute UserReqDTO userReqDTO, @RequestParam(value = "attachmentFullImage") MultipartFile file){
        return userService.changeUserDetails(userReqDTO,file);
    }

    @PostMapping(value = "photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void savePhoto(@RequestParam("photo") MultipartFile photo){
        userService.savePhoto(photo);
    }

    @GetMapping("address")
    private ResponseEntity<List<AddressDTO>> getUserAddresses(){
        return userService.getAddresses();
    }

    @PostMapping("address")
    private ResponseEntity<Address> saveAddress(@RequestBody AddressDTO addressDTO){
        return userService.saveAddress(addressDTO);
    }
}
