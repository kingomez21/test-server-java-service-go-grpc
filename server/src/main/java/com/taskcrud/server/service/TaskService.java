package com.taskcrud.server.service;

import com.taskcrud.protos.HelloGrpc;
import com.taskcrud.protos.HelloGrpc.HelloBlockingStub;
import com.taskcrud.protos.TaskService.Empty;
import com.taskcrud.protos.TaskService.ListUser;
import com.taskcrud.protos.TaskService.Request;
import com.taskcrud.protos.TaskService.Response;
import com.taskcrud.protos.TaskService.User;
import com.taskcrud.server.dtos.UserDTO;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.*;
import java.util.stream.Collectors;

public class TaskService {
    
    private final HelloBlockingStub blockingStub;

    public TaskService(){
        String target = "localhost:50051"; // Dirección del servidor gRPC
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext() // Desactiva SSL para la conexión (útil para desarrollo)
                .build();

        blockingStub = HelloGrpc.newBlockingStub(channel);
    }

    public String sayHello(String saludo){

        Request req = Request.newBuilder().setName(saludo).build();
        Response res;

        try {
            res = blockingStub.sayHello(req);
            return res.getMessage();
        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed: " + e.getStatus());
        }

        return "";
    }

    public List<UserDTO> getUsers(){
        Empty empty = Empty.newBuilder().build();
        ListUser res;
        
        try {
            res = blockingStub.getUsers(empty);
            
            return res.getUsersList().stream()
                .map(usuario -> new UserDTO(usuario.getId(), usuario.getFirstname(), usuario.getLastname(), usuario.getEmail(), usuario.getAddress()))
                .collect(Collectors.toList());
        } catch (StatusRuntimeException e) {
            System.out.println("RPC failed: " + e.getStatus());
        }

        return null;
        
    }

    public String createUser(UserDTO userDTO){

        User user = User.newBuilder()
         .setFirstname(userDTO.getFirstname())
         .setLastname(userDTO.getLastname())
         .setEmail(userDTO.getEmail())
         .setAddress(userDTO.getAddress())
         .build();

        Response res;

         try {

            res = blockingStub.createUser(user);

            return res.getMessage();
            
         }catch (StatusRuntimeException e) {
            System.out.println("RPC failed: " + e.getStatus());
        }

        return null;

    }
}
