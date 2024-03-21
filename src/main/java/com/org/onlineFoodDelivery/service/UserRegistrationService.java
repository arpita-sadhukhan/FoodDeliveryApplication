package com.org.onlineFoodDelivery.service;

import com.org.onlineFoodDelivery.dto.LoginDTO;
import com.org.onlineFoodDelivery.dto.UserDTO;
import com.org.onlineFoodDelivery.entity.Role;
import com.org.onlineFoodDelivery.entity.User;
import com.org.onlineFoodDelivery.exception.InvalidRequestException;
import com.org.onlineFoodDelivery.exception.ObjectCreationException;
import com.org.onlineFoodDelivery.respository.RoleRepository;
import com.org.onlineFoodDelivery.respository.UserRegistrationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserRegistrationService implements IUserRegistrationService{

    @Autowired
    UserRegistrationRepository repo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public UserDTO register(UserDTO dto, String userType) {

        if(repo.existsByUsername(dto.getUsername()))
            throw new ObjectCreationException("User already exists with username : "+dto.getUsername());

        if(repo.existsByEmail(dto.getEmail()))
            throw new ObjectCreationException("User already exists with email : "+dto.getEmail());

        User user = populateUser(dto, userType);
        User savedUser = repo.save(user);
        if(savedUser.getId() < 0)
            throw new ObjectCreationException("Error registering new user");
        dto.setId(savedUser.getId());
        dto.setCreatedDate(savedUser.getCreatedDate());
        return dto;
    }

    @Override
    public List<UserDTO> getUserList() {

        List<UserDTO> userListDto = new ArrayList<>();
        repo.findAll().forEach(user -> {
            UserDTO dto = populateDto(user);
            userListDto.add(dto);
        });
        return userListDto;
    }

    @Override
    public String login(LoginDTO dto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User successfully logged in !!";
    }

    private User populateUser(UserDTO dto, String roleName){
        User user = modelMapper.map(dto, User.class);
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new InvalidRequestException("No roles exist with name : "+roleName));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        /*user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        //user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUsername(dto.getUsername());

        Address address = new Address();
        AddressDTO addressDto = dto.getAddress();
        address.setHouseNo(addressDto.getHouseNo());
        address.setStreet(addressDto.getStreet());
        address.setPincode(addressDto.getPincode());
        address.setState(addressDto.getState());
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());

        user.setAddress(address);*/
        return user;
    }
    private UserDTO populateDto(User user){
        UserDTO dto = modelMapper.map(user, UserDTO.class);/*
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());

        AddressDTO addressDto = new AddressDTO();
        Address address = user.getAddress();
        addressDto.setHouseNo(address.getHouseNo());
        addressDto.setStreet(address.getStreet());
        addressDto.setPincode(address.getPincode());
        addressDto.setState(address.getState());
        addressDto.setCity(address.getCity());
        addressDto.setCountry(address.getCountry());
        dto.setAddress(addressDto);
*/
        return dto;
    }
}
