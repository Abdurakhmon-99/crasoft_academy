package com.crasoft.academywebsite.models;

import lombok.Data;

@Data
public class LoginRequestModel {
    private String username;
    private String password;
}
