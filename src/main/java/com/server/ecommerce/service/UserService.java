package com.server.ecommerce.service;

import com.server.ecommerce.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);
    UserDto update(UserDto userDto,int userId);
    void delete(int userId);
    List<UserDto> getAll();
    UserDto getByUserId(int userId);
    UserDto getByEmailId(String email);
}
