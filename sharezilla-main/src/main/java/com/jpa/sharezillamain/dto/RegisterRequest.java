// com.jpa.sharezillamain.dto.RegisterRequest.java
package com.jpa.sharezillamain.dto;

import com.jpa.sharezillamain.model.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private RoleType role;

    // Getters and Setters
}
