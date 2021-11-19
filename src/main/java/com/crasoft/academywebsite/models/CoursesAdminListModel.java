package com.crasoft.academywebsite.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CoursesAdminListModel {
    @Id
    private String Id;
    private String title;
    private String mentorsName;
    private String image;

}
