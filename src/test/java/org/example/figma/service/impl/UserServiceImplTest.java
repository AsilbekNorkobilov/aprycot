package org.example.figma.service.impl;

import org.example.figma.config.AuditorAwareImpl;
import org.example.figma.dto.AddressDTO;
import org.example.figma.entity.Address;
import org.example.figma.entity.User;
import org.example.figma.mappers.AddressMapper;
import org.example.figma.mappers.UserMapper;
import org.example.figma.model.dto.request.UserReqDTO;
import org.example.figma.model.dto.response.UserResDto;
import org.example.figma.repo.AddressRepository;
import org.example.figma.repo.AttachmentRepository;
import org.example.figma.repo.RoleRepository;
import org.example.figma.repo.UserRepository;
import org.example.figma.service.AttachmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class UserServiceImplTest {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private AuditorAwareImpl auditorAware;
    private PasswordEncoder passwordEncoder;
    private AttachmentRepository attachmentRepository;
    private RoleRepository roleRepo;
    private AddressRepository addressRepository;
    private AddressMapper addressMapper;
    private AttachmentService attachmentService;
    private UserServiceImpl userService;

    @BeforeEach
    public void before(){
        userRepository= Mockito.mock(UserRepository.class);
        userMapper=Mockito.mock(UserMapper.class);
        auditorAware=new AuditorAwareImpl();
        passwordEncoder=Mockito.mock(PasswordEncoder.class);
        attachmentRepository=Mockito.mock(AttachmentRepository.class);
        roleRepo=Mockito.mock(RoleRepository.class);
        addressRepository=Mockito.mock(AddressRepository.class);
        addressMapper=Mockito.mock(AddressMapper.class);
        attachmentService=new AttachmentServiceImpl(attachmentRepository);
        userService=new UserServiceImpl(userRepository,userMapper,attachmentService,auditorAware,passwordEncoder,attachmentRepository,roleRepo,addressRepository,addressMapper);

    }

    @Test
    void getMangers() {
        UUID id=UUID.randomUUID();
        List<User> list=new ArrayList<>(List.of(User.builder().id(id).build()));
        Mockito.when(userRepository.findAllManagers()).thenReturn(new ArrayList<>(List.of(User.builder().id(id).build())));
        ResponseEntity<List<UserResDto>> temp = userService.getMangers();
        List<UserResDto> body = temp.getBody();
        Assertions.assertEquals(list.size(),body.size());
    }

    @Test
    void changeUserDetails() {
        UserReqDTO user=new UserReqDTO("a","b","a","a","a",null);
        Mockito.when(userRepository.save(new User())).thenReturn(User.builder().firstName("a").build());
        ResponseEntity<User> temp = userService.changeUserDetails(user);
        User body = temp.getBody();
        Assertions.assertEquals(user.getFirstName(),body.getFirstName());
    }

    @Test
    void savePhoto() {

    }

    @Test
    void saveManager() {

    }


    @Test
    void getAddresses() {
        UUID id=UUID.randomUUID();
        List<Address> list=new ArrayList<>(List.of(Address.builder().house("a").build()));
        Mockito.when(addressRepository.findAllByUserId(id)).thenReturn(new ArrayList<>(List.of(Address.builder().house("a").build())));
        ResponseEntity<List<AddressDTO>> temp = userService.getAddresses();
        List<AddressDTO> body = temp.getBody();
        Assertions.assertEquals(list.size(),body.size());
    }

    @Test
    void saveAddress() {
        AddressDTO addressDTO=new AddressDTO("a","a","a","a",12d,12d);
        Address address=Address.builder().district("a").build();
        Mockito.when(addressRepository.save(address)).thenReturn(Address.builder().district("a").build());
        ResponseEntity<Address> temp = userService.saveAddress(addressDTO);
        Address body = temp.getBody();
        Assertions.assertEquals(address.getDistrict(),body.getDistrict());
    }
}