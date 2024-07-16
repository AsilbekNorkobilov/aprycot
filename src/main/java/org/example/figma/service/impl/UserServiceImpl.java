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
import org.example.figma.model.dto.forsave.MangerUUIDPhotoDto;
import org.example.figma.model.dto.request.UserReqDTO;
import org.example.figma.model.dto.forsave.ManagerResDto;
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
            String base64Photo = Base64.getEncoder().encodeToString(attachmentService.findById(manager.getAttachment().getId()).getPressedImage());
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
            attachment.setPressedImage(attachment.compressImage(photo));
            attachmentRepository.save(attachment);
            user.setAttachment(attachment);
            userRepository.save(user);
        }
    }

    @Override
    public UUID saveManager(ManagerResDto managerResDto) {
        Role manager = roleRepo.findByRoleName(RoleName.ROLE_MANAGER.name());
        User newManager = User.builder()
                .firstName(managerResDto.getFirstName())
                .lastName(managerResDto.getLastName())
                .phone(managerResDto.getPhone())
                .email(managerResDto.getEmail())
                .password(passwordEncoder.encode(managerResDto.getPassword()))
                .roles(List.of(manager))
                .build();
        User savedManager = userRepository.save(newManager);
        return savedManager.getId();
    }

    @SneakyThrows
    @Override
    public String saveManagerPhoto(MangerUUIDPhotoDto manager){
        User currentManager = userRepository.findById(manager.getId()).get();
        Attachment attachment = attachmentService.savePhoto(manager.getMultipartFile().getBytes());
        currentManager.setAttachment(attachment);
        userRepository.save(currentManager);
        return "Manager saved!";
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
    public void saveAddress(AddressDTO addressDTO) {
        Address entity = addressMapper.toEntity(addressDTO);
        entity.setUser(auditorAware.getAuthenticatedUser());
        addressRepository.save(entity);
    }
}
