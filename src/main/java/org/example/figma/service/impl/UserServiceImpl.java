package org.example.figma.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.figma.config.AuditorAwareImpl;
import org.example.figma.dto.AddressDTO;
import org.example.figma.entity.Address;
import org.example.figma.entity.Attachment;
import org.example.figma.entity.Role;
import org.example.figma.entity.User;
import org.example.figma.entity.enums.RoleName;
import org.example.figma.mappers.AddressMapper;
import org.example.figma.mappers.UserMapper;
import org.example.figma.model.dto.request.ManagerEditReqDto;
import org.example.figma.model.dto.request.UserReqDTO;
import org.example.figma.model.dto.request.ManagerReqDto;
import org.example.figma.model.dto.response.UserResDto;
import org.example.figma.repo.AddressRepository;
import org.example.figma.repo.AttachmentRepository;
import org.example.figma.repo.RoleRepository;
import org.example.figma.repo.UserRepository;
import org.example.figma.service.AttachmentService;
import org.example.figma.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AttachmentService attachmentService;
    private final AuditorAwareImpl auditorAware;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentRepository attachmentRepository;
    private final RoleRepository roleRepo;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public ResponseEntity<List<UserResDto>> getMangers() {
        List<User> managers = userRepository.findAllManagers();
        List<UserResDto> managerDtos = managers.stream().map(manager ->{
            UserResDto userResDto = userMapper.toDto(manager);
            String base64Photo = Base64.getEncoder().encodeToString(attachmentService.findById(manager.getAttachment().getId()).getFullImage());
            userResDto.setBase64Photo(base64Photo);
            return userResDto;
        }).toList();
        return ResponseEntity.status(200).body(managerDtos);
    }

    @Override
    public ResponseEntity<User> changeUserDetails(UserReqDTO userReqDTO) {
        Optional<User> opt = userRepository.findById(auditorAware.getAuthenticatedUser().getId());
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

    @SneakyThrows
    @Override
    public void savePhoto(MultipartFile photo) {
        Optional<User> opt = userRepository.findById(auditorAware.getAuthenticatedUser().getId());
        if (opt.isPresent()){
            User user = opt.get();
            Attachment attachment=new Attachment();
            attachment.setFullImage(photo.getBytes());
            attachment.compressImage();
            attachmentRepository.save(attachment);
            user.setAttachment(attachment);
            userRepository.save(user);
        }
    }

    @Override
    public String saveManager(ManagerReqDto managerReqDto, MultipartFile multipartFile) throws IOException {
        Role manager = roleRepo.findByRoleName(RoleName.ROLE_MANAGER.name());
        Attachment attachment = attachmentService.savePhoto(multipartFile.getBytes());
        User newManager = User.builder()
                .firstName(managerReqDto.getFirstName())
                .lastName(managerReqDto.getLastName())
                .phone(managerReqDto.getPhone())
                .email(managerReqDto.getEmail())
                .password(passwordEncoder.encode(managerReqDto.getPassword()))
                .roles(List.of(manager))
                .attachment(attachment)
                .build();
        User savedManager = userRepository.save(newManager);
        return "Manager saved! "+savedManager.getFirstName();
    }

    @Override
    public String editManager(ManagerEditReqDto managerEdit, MultipartFile multipartFile) throws IOException {
        User currentManager = userRepository.findById(managerEdit.getId()).get();
        UUID currentAttachmentId = currentManager.getAttachment().getId();
        Attachment attachment = attachmentService.savePhoto(multipartFile.getBytes());
        currentManager.setFirstName(managerEdit.getFirstName());
        currentManager.setLastName(managerEdit.getLastName());
        currentManager.setPhone(managerEdit.getPhone());
        currentManager.setEmail(managerEdit.getEmail());
        currentManager.setPassword(passwordEncoder.encode(managerEdit.getPassword()));
        currentManager.setAttachment(attachment);
        userRepository.save(currentManager);

        attachmentService.deleteAttachment(currentAttachmentId);
        return "Manager is edited "+currentManager.getFirstName();
    }

    @Override
    public ResponseEntity<List<AddressDTO>> getAddresses() {
        List<Address> userAddress = addressRepository.findAllByUserId(auditorAware.getAuthenticatedUser().getId());
        List<AddressDTO> dtos=new ArrayList<>();
        for (Address address : userAddress) {
            dtos.add(addressMapper.toDto(address));
        }
        return ResponseEntity.status(200).body(dtos);
    }

    @Override
    public ResponseEntity<Address> saveAddress(AddressDTO addressDTO) {
        Address entity = addressMapper.toEntity(addressDTO);
        entity.setUser(auditorAware.getAuthenticatedUser());
        Address save = addressRepository.save(entity);
        return ResponseEntity.status(200).body(save);
    }
}
