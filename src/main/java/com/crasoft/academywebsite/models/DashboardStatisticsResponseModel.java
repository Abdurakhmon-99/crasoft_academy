package com.crasoft.academywebsite.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class DashboardStatisticsResponseModel {

    private long numberOfNewApplications;
    private long numberOfEnrolledApplications;
    private long numberOfCancelledApplications;

}
