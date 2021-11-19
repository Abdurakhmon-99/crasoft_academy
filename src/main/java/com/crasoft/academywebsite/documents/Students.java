package com.crasoft.academywebsite.documents;

import com.crasoft.academywebsite.documents.subdocuments.CourseAttendance;
import com.crasoft.academywebsite.documents.subdocuments.EnrolledCourses;
import com.crasoft.academywebsite.documents.subdocuments.InterestedCourses;
import com.crasoft.academywebsite.documents.subdocuments.StudentAttendance;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.Date;
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
    private ArrayList<String> enrolledCoursesId;
    private List<StudentAttendance> attendances;
}
