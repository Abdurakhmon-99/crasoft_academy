package com.crasoft.academywebsite.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.Date;

@Data
@Document(collection = "mentors")
public class Mentors {
    @Id
    private String id;

    private String name;
    private String title;
    private String biography;
    @Field(name = "registered_at")
    private Date registeredAt;
    @Field(targetType = FieldType.OBJECT_ID)
    private ArrayList<String> courses;
    private String image;
    @Field(name = "phone_number")
    private String phoneNumber;
    private String email;
    private String username;
    @Field(name = "encrypted_password")
    private String encryptedPassword;
    public void addCourse(String courseId){
        courses.add(courseId);
    }
    public void removeCourse(String courseId){
        courses.remove(courseId);
    }
}
