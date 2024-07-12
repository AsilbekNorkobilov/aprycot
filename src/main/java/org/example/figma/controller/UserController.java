package org.example.figma.controller;


import lombok.RequiredArgsConstructor;
import org.example.figma.entity.User;
import org.example.figma.model.dto.request.UserReqDTO;
import org.example.figma.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping
    private ResponseEntity<User> changeUserCredentials(@RequestBody UserReqDTO userReqDTO){
        return userService.changeUserDetails(userReqDTO);
    }
}
