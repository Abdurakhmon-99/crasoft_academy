package com.crasoft.academywebsite.documents.subdocuments;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.List;
@Data
public class CourseModules {
    private ArrayList<ModuleLessons> lessons;
}
