package com.crasoft.academywebsite.models;

import com.crasoft.academywebsite.documents.subdocuments.AdminPopUpMentor;
import com.crasoft.academywebsite.documents.subdocuments.CourseModules;
import com.crasoft.academywebsite.documents.subdocuments.CoursesGroups;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;
import java.util.List;

@Data
public class CoursesAdminPopUpRequestModel {
    @Id
    private String id;
    private String title;
    @Field(targetType = FieldType.OBJECT_ID)
    private String mentorsId;
    private String description;
    private int tuition;
    private String image;
    @Field(name = "video_link")
    private String videoLink;
    @Field(name = "created_at")
    private Date createdAt;
    @Field(name = "course_content")
    private List<CourseModules> courseContent;
    private List<CoursesGroups> groups;
    private String slug;
}
