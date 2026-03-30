package com.app.quantitymeasurement.mapper.impl;

import com.app.quantitymeasurement.dto.UserDto;
import com.app.quantitymeasurement.mapper.Mapper;
import com.app.quantitymeasurement.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserResponseMapper implements Mapper<User, UserDto> {
    private final ModelMapper modelMapper;
    @Override
    public UserDto mapTo(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
