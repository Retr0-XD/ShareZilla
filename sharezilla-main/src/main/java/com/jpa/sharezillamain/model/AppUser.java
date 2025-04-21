package com.jpa.sharezillamain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

// model/AppUser.java
@Entity
@Data
public class AppUser {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;

    // com.jpa.sharezillamain.model.AppUser.java
    @Enumerated(EnumType.STRING)
    private RoleType role;



    public AppUser(String username, String password, List<Object> of) {
    }

    public AppUser() {

    }
    // getters and setters
}
