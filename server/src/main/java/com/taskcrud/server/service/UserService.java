package com.taskcrud.server.service;


import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.net.ssl.SSLException;

import com.taskcrud.server.dtos.UserDTO;
import com.userservice.protos.UserServiceProto.EmptyUser;
import com.userservice.protos.UserServiceProto.ResponseUser;
import com.userservice.protos.UserServiceGrpc;
import com.userservice.protos.UserServiceGrpc.UserServiceBlockingStub;
import com.userservice.protos.UserServiceProto.ListUser;
import com.userservice.protos.UserServiceProto.User;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;


public class UserService {

    private final UserServiceBlockingStub blockingStub;

    public UserService(String target){
        InputStream certStream = UserService.class.getClassLoader().getResourceAsStream("certs/cert.crt");

        if (certStream == null) {
            throw new IllegalStateException("Certificado no encontrado en certs.");
        }

        ManagedChannel channel = null;
        try {
            channel = NettyChannelBuilder.forAddress("localhost", 50051)
                    .sslContext(GrpcSslContexts.forClient().trustManager(certStream).build())
                    .build();
        } catch (SSLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        /*ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
            .sslContext(GrpcSslContexts.forClient().trustManager(certStream).build())
            .build();*/

        blockingStub = UserServiceGrpc.newBlockingStub(channel);
    }
    
    public List<UserDTO> getUsers(){
        EmptyUser empty = EmptyUser.newBuilder().build();
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

        ResponseUser res;

         try {

            res = blockingStub.createUser(user);

            return res.getMessage();
            
         }catch (StatusRuntimeException e) {
            System.out.println("RPC failed: " + e.getStatus());
        }

        return null;

    }
}
