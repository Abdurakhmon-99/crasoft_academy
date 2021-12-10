package com.crasoft.academywebsite.documents;

import com.crasoft.academywebsite.documents.subdocuments.InterestedCourses;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "applicant_forms")
public class ApplicantForms {
    @Id
    private String id;

    @Field(name = "first_name")
    @Size(min = 2, max = 18)
    private String firstName;
    @Field(name = "last_name")
    @Size(min = 2, max = 18)
    private String lastName;
    private String phone;
    private String email;
    @Field(name = "interested_courses" ,targetType = FieldType.OBJECT_ID)
    private ArrayList<String> interestedCourses;
    private String comments;
    @Field(name = "created_at")
    @NotNull
    private Date createdAt;
    private String status;
}
