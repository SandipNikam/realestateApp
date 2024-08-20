package io.bootify.my_app.serviceImpl;


import io.bootify.my_app.domain.Lease;
import io.bootify.my_app.domain.Roles;
import io.bootify.my_app.domain.User;
import io.bootify.my_app.domain.UserRole;
import io.bootify.my_app.dto.LeaseDto;
import io.bootify.my_app.dto.UserDto;
import io.bootify.my_app.exception.LeaseNotFoundException;
import io.bootify.my_app.exception.ResourceNotFoundException;
import io.bootify.my_app.exception.UserAlreadyExistException;
import io.bootify.my_app.exception.UserNotFoundException;
import io.bootify.my_app.repos.RolesRepo;
import io.bootify.my_app.repos.UserRepo;
import io.bootify.my_app.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RolesRepo rolesRepo;

    @Override
    public UserDto createUser(UserDto userDto) throws UserAlreadyExistException {

        User existingUser = userRepo.findByEmail(userDto.getEmail());


        if (existingUser != null) {
            System.out.println("User already exists");
            throw new UserAlreadyExistException("User already exists");
        } else {

            User user = new User(userDto);

            Set<UserRole> roles = new HashSet<>();
            Roles role = new Roles();
            role.setId(44);
            role.setName("NORMAL");
            UserRole userRole = new UserRole();
            userRole.setUser(user);
            userRole.setRole(role);
            roles.add(userRole);


            for (UserRole ur : roles) {
                this.rolesRepo.save(ur.getRole());
            }

            user.getUserRoles().addAll(roles);


            User registeredUser = userRepo.save(user);


            //return new UserDto(registeredUser);
        }

        return userDto;
    }


    @Override
    public UserDto getUserById(Integer userId) throws ResourceNotFoundException {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        return new UserDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        Optional<User> optionalUser = userRepo.findById(userId);

        UserDto updatedUser = null;

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();//data from database

            user.setName(userDto.getName());
            user.setAddress(userDto.getAddress());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setMoNumber(userDto.getMoNumber());

            updatedUser = new UserDto(user);

            userRepo.save(user);

        }
        return updatedUser;
    }

    @Override
    public void deleteUser(Integer userId) throws ResourceNotFoundException {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        userRepo.delete(user);
    }

/*    @Override
    public List<UserDto> getUserByBrokerProfileId(Integer brokerProfileId) {
        List<User> users = userRepo.findByBrokerProfiles_BrokerProfileId(brokerProfileId);
        if (users.isEmpty()) {
            throw new UserNotFoundException("No Users found for broker profile ID: " + brokerProfileId);
        }
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }*/
}
