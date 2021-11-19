package com.crasoft.academywebsite.documents.subdocuments;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.Date;

@Data
public class StudentAttendance {
    @Field(targetType = FieldType.OBJECT_ID)
    private String CourseId;
    private ArrayList<Date> attendedDays;
}
