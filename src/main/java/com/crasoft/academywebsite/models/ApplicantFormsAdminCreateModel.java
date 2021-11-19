package com.crasoft.academywebsite.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.Date;

@Data
public class ApplicantFormsAdminCreateModel {
    @Id
    private String id;

    @Field(name = "first_name")
    private String firstName;
    @Field(name = "last_name")
    private String lastName;
    private String phone;
    private String email;
    @Field(name = "interested_courses" ,targetType = FieldType.OBJECT_ID)
    private ArrayList<String> interestedCourses;

}
