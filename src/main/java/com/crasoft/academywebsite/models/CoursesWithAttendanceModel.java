package com.crasoft.academywebsite.models;

import com.crasoft.academywebsite.documents.subdocuments.CourseModules;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

public class CoursesWithAttendanceModel {
    @Id
    private String id;

}
