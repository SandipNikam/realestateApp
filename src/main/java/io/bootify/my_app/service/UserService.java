package io.bootify.my_app.service;


import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.dto.UserDto;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.exception.UserAlreadyExistException;

import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto userDto) throws UserAlreadyExistException;

    public UserDto getUserById(Integer userId) throws ResourceNotFoundException;

    public UserDto updateUser(UserDto userDto , Integer userId);

   public void deleteUser(Integer userId) throws ResourceNotFoundException;

    //public List<UserDto> getUserByBrokerProfileId(Integer brokerProfileId);
}
