package com.taskcrud.server.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskcrud.server.dtos.UserDTO;
import com.taskcrud.server.service.UserService;

@RestController
public class UserControllers {
    
    private UserService users = new UserService();


    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        
        return users.getUsers();
    }

    @PostMapping("/user")
    public ResponseEntity<String> crearUsuario(@RequestBody UserDTO userDTO){

        String result = users.createUser(userDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
