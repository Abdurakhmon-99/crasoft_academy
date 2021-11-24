package com.crasoft.academywebsite.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "attendance")
public class Attendance {
    @Id
    private String Id;
    @Field(name = "course_id", targetType = FieldType.OBJECT_ID)
    private String courseId;
    private String group;
    private Date date;
    @Field(name = "attended_students" ,targetType = FieldType.OBJECT_ID)
    private List<String> attendedStudents;
}
