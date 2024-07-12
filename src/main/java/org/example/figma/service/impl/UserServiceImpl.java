package org.example.figma.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.figma.entity.User;
import org.example.figma.mappers.UserMapper;
import org.example.figma.model.dto.request.UserReqDTO;
import org.example.figma.model.dto.response.UserResDto;
import org.example.figma.repo.UserRepository;
import org.example.figma.service.AttachmentService;
import org.example.figma.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AttachmentService attachmentService;
    private final User authenticatedUser;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public ResponseEntity<User> changeUserDetails(UserReqDTO userReqDTO) {
        Optional<User> opt = userRepository.findById(authenticatedUser.getId());
        if (opt.isPresent()){
            User user = opt.get();
            if (userReqDTO.getPhone()!=null){
                user.setPhone(userReqDTO.getPhone());
            }
            if (userReqDTO.getEmail()!=null){
                user.setEmail(userReqDTO.getEmail());
            }
            if (userReqDTO.getFirstName()!=null){
                user.setFirstName(userReqDTO.getFirstName());
            }
            if (userReqDTO.getLastName()!=null){
                user.setLastName(userReqDTO.getLastName());
            }
            if (userReqDTO.getPassword()!=null){
                user.setPassword(passwordEncoder.encode(userReqDTO.getPassword()));
            }
            userRepository.save(user);
            return ResponseEntity.status(200).body(user);
        }
        return ResponseEntity.status(500).body(null);
    }
}
