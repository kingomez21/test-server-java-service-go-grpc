package com.taskcrud.server.service;


import java.util.List;
import java.util.stream.Collectors;

import com.taskcrud.server.dtos.UserDTO;
import com.userservice.protos.UserServiceProto.Empty;
import com.userservice.protos.UserServiceProto.Response;
import com.userservice.protos.UserServiceGrpc;
import com.userservice.protos.UserServiceGrpc.UserServiceBlockingStub;
import com.userservice.protos.UserServiceProto.ListUser;
import com.userservice.protos.UserServiceProto.User;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;


public class UserService {

    private final UserServiceBlockingStub blockingStub;

    public UserService(String target){
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
            .usePlaintext()
            .build();

        blockingStub = UserServiceGrpc.newBlockingStub(channel);
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
