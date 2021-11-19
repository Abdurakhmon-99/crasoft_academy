package com.crasoft.academywebsite.models;

import com.crasoft.academywebsite.documents.subdocuments.AdminPopUpMentor;
import com.crasoft.academywebsite.documents.subdocuments.CourseModules;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

@Data
public class CoursesAdminPopUpModel {
    @Id
    private String id;
    private String title;

    private AdminPopUpMentor mentor;
    private String description;
    private int tuition;
    private String image;
    @Field(name = "video_link")
    private String videoLink;
    @Field(name = "created_at")
    private Date createdAt;
    @Field(name = "course_content")
    private List<CourseModules> courseContent;
    private String slug;

}
