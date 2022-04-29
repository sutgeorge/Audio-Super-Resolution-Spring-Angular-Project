package com.ubb.audiosuperres.controller;

import com.ubb.audiosuperres.model.UserDto;
import com.ubb.audiosuperres.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

@CrossOrigin
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto) {
        if (authenticationService.checkUserCredentials(userDto))
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        return new ResponseEntity<>(new UserDto(null, null, null), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto returnedUserDto = authenticationService.createAccount(userDto);
        if (returnedUserDto.getUsername() != null)
            return new ResponseEntity<>(returnedUserDto, HttpStatus.OK);
        return new ResponseEntity<>(new UserDto(null, null, null), HttpStatus.UNAUTHORIZED);
    }
}