package com.org.onlineFoodDelivery.service;

import com.org.onlineFoodDelivery.dto.LoginDTO;
import com.org.onlineFoodDelivery.dto.UserDTO;
import com.org.onlineFoodDelivery.entity.User;

import java.util.List;

public interface IUserRegistrationService {
    public UserDTO register(UserDTO dto, String userType);
    public List<UserDTO> getUserList();
    public String login(LoginDTO dto);
}
