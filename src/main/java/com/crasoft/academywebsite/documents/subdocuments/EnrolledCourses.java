package com.crasoft.academywebsite.documents.subdocuments;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class EnrolledCourses {
    @Id
    private String id;
    private String title;
    private String group;
}
