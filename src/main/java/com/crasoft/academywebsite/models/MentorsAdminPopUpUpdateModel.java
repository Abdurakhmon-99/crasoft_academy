package com.crasoft.academywebsite.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.Date;

@Data
public class MentorsAdminPopUpUpdateModel {
    @Id
    private String id;

    private String name;
    private String title;
    private String biography;
    private String image;
    @Field(name = "phone_number")
    private String phoneNumber;
    private String email;

}