package com.crasoft.academywebsite.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class CourseStatisticsResponseModel {

    private String title;
    private long numberOfApplications;
    private long numberOfStudents;

}
