package org.example.figma.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.figma.entity.User;
import org.example.figma.mappers.UserMapper;
import org.example.figma.model.dto.response.UserResDto;
import org.example.figma.repo.UserRepository;
import org.example.figma.service.AttachmentService;
import org.example.figma.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AttachmentService attachmentService;
    @Override
    public ResponseEntity<List<UserResDto>> getMangers() {
        List<User> managers = userRepository.findAllManagers();
        List<UserResDto> managerDtos = managers.stream().map(manager ->{
            UserResDto userResDto = userMapper.toDto(manager);
            String base64Photo = Base64.getEncoder().encodeToString(attachmentService.findById(manager.getAttachment().getId()).getPressedImage());
            userResDto.setBase64Photo(base64Photo);
            return userResDto;
        }).toList();
        return ResponseEntity.status(200).body(managerDtos);
    }
}
