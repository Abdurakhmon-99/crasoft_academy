package com.crasoft.academywebsite.controller;

import com.crasoft.academywebsite.documents.Admin;
import com.crasoft.academywebsite.models.ApplicantFormsResponseModel;
import com.crasoft.academywebsite.models.DashboardStatisticsResponseModel;
import com.crasoft.academywebsite.models.LoginRequestModel;
import com.crasoft.academywebsite.repository.AdminRepository;
import com.crasoft.academywebsite.repository.MentorsRepository;
import com.crasoft.academywebsite.service.AdminService;
import com.crasoft.academywebsite.service.ApplicantFormsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(value = "Documentation of the Courses of the IT academy", tags = {"Admin"})
@RestController
@RequestMapping("/")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    MentorsRepository mentorsRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    ApplicantFormsService applicantFormsService;

    @PostMapping("admin")
    public Admin createAdmin(@RequestBody LoginRequestModel adminDetails){
        return adminService.createAdmin(adminDetails);
    }
    @GetMapping("statistics")
    public ResponseEntity<DashboardStatisticsResponseModel> getApplicationStatistics(){
        DashboardStatisticsResponseModel model = applicantFormsService.getStatistics();
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
}
