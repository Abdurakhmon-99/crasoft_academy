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
    @GetMapping("/discarded")
    public ResponseEntity<List<ApplicantFormsResponseModel>> getAllDiscardedApplications(){
        List<ApplicantFormsResponseModel> applications = applicantFormsService.getAllDiscardedApplications();
        return ResponseEntity.status(HttpStatus.OK).body(applications);
    }
    @PutMapping("/discard/{id}")
    public ResponseEntity<ApplicantFormsResponseModel> discardApplication(@PathVariable("id") String id, @RequestBody DiscardCommentModel commentModel){
        ApplicantFormsResponseModel model = applicantFormsService.discardApplicationById(id, commentModel);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @PutMapping("/contact/{id}")
    public ResponseEntity<ApplicantFormsResponseModel> contactApplication(@PathVariable("id") String id){
        ApplicantFormsResponseModel model = applicantFormsService.contactApplicationById(id);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @PostMapping("/enroll/{id}")
    public ResponseEntity<StudentsResponseModel> enrollApplication(@PathVariable("id") String id, @RequestBody ArrayList<String> coursesToEnroll){
        StudentsResponseModel model = applicantFormsService.enrollApplicationById(id, coursesToEnroll);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @PostMapping
    public ResponseEntity<ApplicantFormsResponseModel> createApplication(@RequestBody ApplicantFormsAdminCreateModel applicantForm){
        ApplicantFormsResponseModel response = applicantFormsService.createNewApplication(applicantForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
