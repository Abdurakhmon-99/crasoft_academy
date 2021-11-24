package com.crasoft.academywebsite.documents.subdocuments;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.Date;

@Data
public class StudentsAttendance {
    @Field(targetType = FieldType.OBJECT_ID)
    private String CourseId;
    private String group;
    private ArrayList<Date> attendedDays;
    public void addAttendedDays(Date date){
        attendedDays.add(date);
    }
}
