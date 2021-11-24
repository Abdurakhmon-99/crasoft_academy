package com.crasoft.academywebsite.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

@Data
public class AttendanceRequestModel {
    private String group;
    @Field(name = "attended_students" ,targetType = FieldType.OBJECT_ID)
    private List<String> attendedStudents;
}
