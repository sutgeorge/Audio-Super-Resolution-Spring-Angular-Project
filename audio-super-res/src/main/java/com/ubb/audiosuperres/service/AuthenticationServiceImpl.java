package com.ubb.audiosuperres.service;

import com.ubb.audiosuperres.model.User;
import com.ubb.audiosuperres.model.UserDto;
import com.ubb.audiosuperres.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationRepository repository;

    public User getUserBasedOnId(Integer id) throws IllegalArgumentException{
        return this.repository.findOne(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public boolean checkUserCredentials(UserDto userDto) {
        try {
            User user = this.getUserBasedOnId(userDto.getId());
            return user.getEmail().equals(userDto.getEmail()) && user.getPassword().equals(userDto.getPassword());
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public UserDto createAccount(UserDto userDto) {
        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
        if (!this.doesTheUsernameExist(userDto)) {
            repository.save(user);
            return userDto;
        }
        return new UserDto(null, null, null);
    }

    @Override
    public boolean doesTheUsernameExist(UserDto userDto) {
        return repository.findByUsername(userDto.getUsername()).isPresent();
    }
}
