package org.example.figma.controller;


import lombok.RequiredArgsConstructor;
import org.example.figma.dto.AddressDTO;
import org.example.figma.entity.Address;
import org.example.figma.entity.User;
import org.example.figma.model.dto.request.UserReqDTO;
import org.example.figma.service.UserService;
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
    private ResponseEntity<User> changeUserCredentials(@RequestBody UserReqDTO userReqDTO){
        return userService.changeUserDetails(userReqDTO);
    }

    @PostMapping("photo")
    public void savePhoto(@RequestParam("photo") MultipartFile photo){
        userService.savePhoto(photo);
    }

    @GetMapping("address")
    private ResponseEntity<List<AddressDTO>> getUserAddresses(){
        return userService.getAddresses();
    }

    @PostMapping("address")
    private void saveAddress(@RequestBody AddressDTO addressDTO){
        userService.saveAddress(addressDTO);
    }
}
