package org.example.figma.service;

import org.example.figma.entity.User;
import org.example.figma.model.dto.request.UserReqDTO;
import org.example.figma.model.dto.response.UserResDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<List<UserResDto>> getMangers();

    ResponseEntity<User> changeUserDetails(UserReqDTO userReqDTO);
}
