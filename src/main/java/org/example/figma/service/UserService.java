package org.example.figma.service;

import org.example.figma.model.dto.response.UserResDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<List<UserResDto>> getMangers();
}
