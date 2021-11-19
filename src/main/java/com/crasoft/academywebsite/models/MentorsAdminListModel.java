package com.crasoft.academywebsite.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class MentorsAdminListModel {
    @Id
    private String Id;
    private String name;
    private String title;
    private String image;
}
