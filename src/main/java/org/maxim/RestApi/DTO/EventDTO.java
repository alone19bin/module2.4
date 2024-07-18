package org.maxim.RestApi.DTO;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.time.LocalDateTime;



public class EventDTO {
    @Expose
    private Integer id;
    @Expose
    private String name;
    @Expose
    private LocalDateTime created;
    @Expose
    private UserDTO user;
    @Expose
    private FileDTO file;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public FileDTO getFile() {
        return file;
    }

    public void setFile(FileDTO file) {
        this.file = file;
    }
}

