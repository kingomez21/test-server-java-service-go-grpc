package com.taskcrud.server.dtos;

public class TaskDTO {
    
    private String id, name, description, date;
    private boolean done;


    public TaskDTO(String id, String name, String description, String date, boolean done){
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getDone(){
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
