package com.crasoft.academywebsite.documents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Data
@Document(collection = "admin")
public class  Admin {
    @Id
    private String id;
    @NotNull(message = "Username cannot be null")
    private String username;
    @Field(name = "encrypted_password")
    @JsonIgnore
    private String encryptedPassword;

}
