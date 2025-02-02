package com.study.wallet.controllers;

import com.study.wallet.domain.user.User;
import com.study.wallet.dtos.UserDTO;
import com.study.wallet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody  UserDTO user){
        User newUser = service.createUer(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
       List<User> users = this.service.getAllUsers();

       return new ResponseEntity<>(users,HttpStatus.OK);
    }
}
