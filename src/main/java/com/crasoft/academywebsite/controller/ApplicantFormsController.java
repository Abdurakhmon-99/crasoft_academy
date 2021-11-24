package com.crasoft.academywebsite.controller;

import com.crasoft.academywebsite.documents.subdocuments.EnrolledCourses;
import com.crasoft.academywebsite.models.ApplicantFormsAdminCreateModel;
import com.crasoft.academywebsite.models.ApplicantFormsResponseModel;
import com.crasoft.academywebsite.models.DiscardCommentModel;
import com.crasoft.academywebsite.models.StudentsResponseModel;
import com.crasoft.academywebsite.service.ApplicantFormsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "Documentation of the Courses of the IT academy", tags = {"ApplicantForms"})
@RestController
@RequestMapping("/applications")
public class ApplicantFormsController {

    @Autowired
    ApplicantFormsService applicantFormsService;

    @GetMapping
    public ResponseEntity<List<ApplicantFormsResponseModel>> getAllActiveApplications(){
        List<ApplicantFormsResponseModel> applications = applicantFormsService.getAllActiveApplications();
        return ResponseEntity.status(HttpStatus.OK).body(applications);
    }
    @GetMapping("/cancelled")
    public ResponseEntity<List<ApplicantFormsResponseModel>> getAllCancelledApplications(){
        List<ApplicantFormsResponseModel> applications = applicantFormsService.getAllCancelledApplications();
        return ResponseEntity.status(HttpStatus.OK).body(applications);
    }
    @GetMapping("/enrolled")
    public ResponseEntity<List<ApplicantFormsResponseModel>> getAllEnrolledApplications(){
        List<ApplicantFormsResponseModel> applications = applicantFormsService.getAllEnrolledApplications();
        return ResponseEntity.status(HttpStatus.OK).body(applications);
    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApplicantFormsResponseModel> cancelApplication(@PathVariable("id") String id, @RequestBody(required = false) DiscardCommentModel commentModel){
        ApplicantFormsResponseModel model = applicantFormsService.cancelApplicationById(id, commentModel);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @PutMapping("/{id}/contact")
    public ResponseEntity<ApplicantFormsResponseModel> contactApplication(@PathVariable("id") String id){
        ApplicantFormsResponseModel model = applicantFormsService.contactApplicationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @PostMapping("/{id}/enroll")
    public ResponseEntity<StudentsResponseModel> enrollApplication(@PathVariable("id") String id, @RequestBody List<EnrolledCourses> coursesToEnroll){
        StudentsResponseModel model = applicantFormsService.enrollApplicationById(id, coursesToEnroll);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @PostMapping
    public ResponseEntity<ApplicantFormsResponseModel> createApplication(@RequestBody ApplicantFormsAdminCreateModel applicantForm){
        ApplicantFormsResponseModel response = applicantFormsService.createNewApplication(applicantForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
