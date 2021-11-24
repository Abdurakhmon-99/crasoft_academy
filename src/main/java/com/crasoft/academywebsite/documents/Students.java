package com.crasoft.academywebsite.documents;

import com.crasoft.academywebsite.documents.subdocuments.EnrolledCourses;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "students")
public class Students {
    @Id
    private String id;

    @Field(name = "first_name")
    private String firstName;
    @Field(name = "last_name")
    private String lastName;
    private String phone;
    private String email;
    @Field(name = "enrolled_courses")
    private List<EnrolledCourses> enrolledCourses;
    public void addEnrolledCourses(EnrolledCourses course){
        enrolledCourses.add((course));
    }
}
