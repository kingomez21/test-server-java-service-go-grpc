package com.taskcrud.server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskcrud.server.dtos.UserDTO;
import com.taskcrud.server.service.TaskService;

import java.util.List;

@RestController
public class TaskControllers {

    private TaskService client = new TaskService();
    
    @GetMapping("/")
    public String helloWorld(){
        String res = client.sayHello("desde java");
        return res;
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        
        return client.getUsers();
    }

    @PostMapping("/user")
    public ResponseEntity<String> crearUsuario(@RequestBody UserDTO userDTO){

        String result = client.createUser(userDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
