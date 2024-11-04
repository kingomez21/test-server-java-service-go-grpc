package com.taskcrud.server.service;

import java.util.List;
import java.util.stream.Collectors;

import com.taskcrud.server.dtos.TaskDTO;
import com.taskservice.protos.TaskServiceGrpc;
import com.taskservice.protos.TaskServiceGrpc.TaskServiceBlockingStub;
import com.taskservice.protos.TaskServiceProto.Empty;
import com.taskservice.protos.TaskServiceProto.ListTask;
import com.taskservice.protos.TaskServiceProto.Response;
import com.taskservice.protos.TaskServiceProto.TaskCreate;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;


public class TaskService {
    
    private final TaskServiceBlockingStub blockingStub;

    public TaskService(){
        String target = "localhost:50051"; // Dirección del servidor gRPC
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext() // Desactiva SSL para la conexión (útil para desarrollo)
                .build();

        blockingStub = TaskServiceGrpc.newBlockingStub(channel);
    }

    public List<TaskDTO> getTask(){
        Empty empty = Empty.newBuilder().build();
        ListTask res;

        try {
            
            res = blockingStub.getTask(empty);

            return res.getTasksList().stream()
            .map(task -> new TaskDTO(task.getId(), task.getName(), task.getDescription(), task.getDate(), task.getDone()))
            .collect(Collectors.toList());

        } catch (StatusRuntimeException e) {
            System.err.println("error" + e.getMessage());
        }

        return null;

    } 

    public String createTask(TaskDTO taskDTO){

        TaskCreate taskCreate = TaskCreate.newBuilder()
            .setName(taskDTO.getName())
            .setDescription(taskDTO.getDescription())
            .setDate(taskDTO.getDate())
            .setDone(taskDTO.getDone())
            .build();

        Response res;

        try {

            res = blockingStub.createTask(taskCreate);

            return res.getMessage();
            
        } catch (StatusRuntimeException e) {
            System.err.println("error" + e.getMessage());
        }

        return null;
    }

    
}
