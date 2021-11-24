package com.crasoft.academywebsite.service;

import com.crasoft.academywebsite.documents.ApplicantForms;
import com.crasoft.academywebsite.documents.Courses;
import com.crasoft.academywebsite.documents.Students;
import com.crasoft.academywebsite.documents.subdocuments.EnrolledCourses;
import com.crasoft.academywebsite.documents.subdocuments.InterestedCourses;
import com.crasoft.academywebsite.models.*;
import com.crasoft.academywebsite.repository.ApplicantFormsRepository;
import com.crasoft.academywebsite.repository.CoursesRepository;
import com.crasoft.academywebsite.repository.StudentsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicantFormsService {

    @Autowired
    ApplicantFormsRepository applicantFormsRepository;
    @Autowired
    CoursesRepository coursesRepository;
    @Autowired
    StudentsRepository studentsRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<ApplicantFormsResponseModel> getAllActiveApplications() {
        List<ApplicantForms> forms = applicantFormsRepository.findByStatusIsLike("contacted");
        List<ApplicantFormsResponseModel> responseForms = forms.stream().map(form ->modelMapper.map(form,ApplicantFormsResponseModel.class)).collect(Collectors.toList());
        responseForms.forEach(responseForm -> {
            ApplicantForms applicantForm = applicantFormsRepository.findById(responseForm.getId()).get();
            ArrayList<String> courses = applicantForm.getInterestedCourses();
            responseForm.setInterestedCourses(new ArrayList<>());
            courses.forEach(course -> {
                Courses existingCourse = coursesRepository.findById(course).get();

                responseForm.addInterestedCourses(modelMapper.map(existingCourse, InterestedCourses.class));
            });
        });
        return responseForms;
    }
    public List<ApplicantFormsResponseModel> getAllCancelledApplications() {
        List<ApplicantForms> forms = applicantFormsRepository.findByStatusIsLike("cancelled");
        List<ApplicantFormsResponseModel> responseForms = forms.stream().map(form ->modelMapper.map(form,ApplicantFormsResponseModel.class)).collect(Collectors.toList());
        responseForms.forEach(responseForm -> {
            ApplicantForms applicantForm = applicantFormsRepository.findById(responseForm.getId()).get();
            ArrayList<String> courses = applicantForm.getInterestedCourses();
            responseForm.setInterestedCourses(new ArrayList<>());
            courses.forEach(course -> {
                Courses existingCourse = coursesRepository.findById(course).get();

                responseForm.addInterestedCourses(modelMapper.map(existingCourse, InterestedCourses.class));
            });
        });
        return responseForms;
    }
    public List<ApplicantFormsResponseModel> getAllEnrolledApplications() {
        List<ApplicantForms> forms = applicantFormsRepository.findByStatusIsLike("enrolled");
        List<ApplicantFormsResponseModel> responseForms = forms.stream().map(form ->modelMapper.map(form,ApplicantFormsResponseModel.class)).collect(Collectors.toList());
        responseForms.forEach(responseForm -> {
            ApplicantForms applicantForm = applicantFormsRepository.findById(responseForm.getId()).get();
            ArrayList<String> courses = applicantForm.getInterestedCourses();
            responseForm.setInterestedCourses(new ArrayList<>());
            courses.forEach(course -> {
                Courses existingCourse = coursesRepository.findById(course).get();

                responseForm.addInterestedCourses(modelMapper.map(existingCourse, InterestedCourses.class));
            });
        });
        return responseForms;
    }
    public ApplicantFormsResponseModel createNewApplication(ApplicantFormsAdminCreateModel applicantForm) {
        ApplicantForms formToSave = modelMapper.map(applicantForm,ApplicantForms.class);
        long createdTime = System.currentTimeMillis();
        Date dateToSave = new Date(createdTime);
        formToSave.setCreatedAt(dateToSave);
        formToSave.setStatus("contacted");
        ApplicantForms form = applicantFormsRepository.save(formToSave);
        ApplicantFormsResponseModel responseModel = modelMapper.map(form, ApplicantFormsResponseModel.class);
        responseModel.setInterestedCourses(new ArrayList<>());
        ArrayList<String> courses = applicantForm.getInterestedCourses();
        courses.forEach(course -> {
            Courses existingCourse = coursesRepository.findById(course).get();

            responseModel.addInterestedCourses(modelMapper.map(existingCourse, InterestedCourses.class));
        });
        return responseModel;
    }

    public ApplicantFormsResponseModel cancelApplicationById(String id, DiscardCommentModel model) {
        ApplicantForms existingApplication = applicantFormsRepository.findById(id).get();
        existingApplication.setStatus("cancelled");
        existingApplication.setComments(model.getComments());
        applicantFormsRepository.save(existingApplication);
        ApplicantFormsResponseModel responseModel = modelMapper.map(existingApplication, ApplicantFormsResponseModel.class);
        responseModel.setInterestedCourses(new ArrayList<>());
        ArrayList<String> courses = existingApplication.getInterestedCourses();
        courses.forEach(course -> {
            Courses existingCourse = coursesRepository.findById(course).get();

            responseModel.addInterestedCourses(modelMapper.map(existingCourse, InterestedCourses.class));
        });
        return responseModel;
    }

    public ApplicantFormsResponseModel contactApplicationById(String id) {
        ApplicantForms existingApplication = applicantFormsRepository.findById(id).get();
        existingApplication.setStatus("contacted");
        applicantFormsRepository.save(existingApplication);
        ApplicantFormsResponseModel responseModel = modelMapper.map(existingApplication, ApplicantFormsResponseModel.class);
        responseModel.setInterestedCourses(new ArrayList<>());
        ArrayList<String> courses = existingApplication.getInterestedCourses();
        courses.forEach(course -> {
            Courses existingCourse = coursesRepository.findById(course).get();

            responseModel.addInterestedCourses(modelMapper.map(existingCourse, InterestedCourses.class));
        });
        return responseModel;
    }

    public StudentsResponseModel enrollApplicationById(String id, List<EnrolledCourses> coursesToEnroll) {
        ApplicantForms existingApplication = applicantFormsRepository.findById(id).get();
        coursesToEnroll.forEach(course -> {
            if(course.getTitle() == null){
                course.setTitle(coursesRepository.findById(course.getId()).get().getTitle());
            }
        });
        existingApplication.setStatus("enrolled");
        Students newStudent = modelMapper.map(existingApplication,Students.class);
        newStudent.setEnrolledCourses(coursesToEnroll);
        applicantFormsRepository.save(existingApplication);
        StudentsResponseModel model = modelMapper.map(studentsRepository.save(newStudent), StudentsResponseModel.class);
        return model;
    }

    public DashboardStatisticsResponseModel getStatistics() {
        DashboardStatisticsResponseModel model = new DashboardStatisticsResponseModel();
        model.setNumberOfCancelledApplications(applicantFormsRepository.countByStatusIsLike("cancelled"));
        model.setNumberOfNewApplications(applicantFormsRepository.countByStatusIsLike("contacted"));
        model.setNumberOfEnrolledApplications(applicantFormsRepository.countByStatusIsLike("enrolled"));
        return model;
    }
}
