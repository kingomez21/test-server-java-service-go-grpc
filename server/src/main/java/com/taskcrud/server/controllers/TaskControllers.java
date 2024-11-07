package com.taskcrud.server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskcrud.server.dtos.TaskDTO;
import com.taskcrud.server.service.TaskService;

import java.util.List;

@RestController
public class TaskControllers {

    private TaskService tasks = new TaskService("localhost:50051");
    private TaskService tasksLogic = new TaskService("localhost:5038");
    

    @GetMapping("/tasks")
    public List<TaskDTO> getUsers(){
        
        return tasks.getTask();
    }

    @GetMapping("/logic/task")
    public List<TaskDTO> logicTask(){
        //System.out.println(tasksLogic.getTask());
        return tasksLogic.getTask();
    }

    @PostMapping("/task")
    public ResponseEntity<String> crearUsuario(@RequestBody TaskDTO taskDTO){

        String result = tasks.createTask(taskDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
