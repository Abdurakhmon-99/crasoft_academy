package com.crasoft.academywebsite.service;

import com.crasoft.academywebsite.documents.Courses;
import com.crasoft.academywebsite.documents.Students;
import com.crasoft.academywebsite.documents.subdocuments.EnrolledCourses;
import com.crasoft.academywebsite.models.StudentsResponseModel;
import com.crasoft.academywebsite.repository.CoursesRepository;
import com.crasoft.academywebsite.repository.StudentsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentsService {
    @Autowired
    StudentsRepository studentsRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CoursesRepository coursesRepository;

    public List<StudentsResponseModel> getAllStudents() {
        List<Students> studentsList = studentsRepository.findAll();
        List<StudentsResponseModel> models = studentsList.stream().map(student -> modelMapper.map(student,StudentsResponseModel.class)).collect(Collectors.toList());
        models.forEach(model -> {
            model.setEnrolledCourses(new ArrayList<>());
            Students studentDTO = studentsRepository.findById(model.getId()).get();
            ArrayList<String> coursesID = studentDTO.getEnrolledCoursesId();
            coursesID.forEach(courseID -> {
                Courses courseDto = coursesRepository.findById(courseID).get();
                model.addEnrolledCourses(modelMapper.map(courseDto,EnrolledCourses.class));
            });
        });

        return models;
    }

    public StudentsResponseModel createStudent(Students student) {
        Students studentCreated = studentsRepository.save(student);
        StudentsResponseModel model = modelMapper.map(studentCreated, StudentsResponseModel.class);
        model.setEnrolledCourses(new ArrayList<>());
        studentCreated.getEnrolledCoursesId().forEach(courseId -> {
            Courses course = coursesRepository.findById(courseId).get();
            EnrolledCourses enrolledCourses = modelMapper.map(course, EnrolledCourses.class);
            model.addEnrolledCourses(enrolledCourses);
        });
        return model;
    }

    public StudentsResponseModel updateStudent(String id, Students student) {
        student.setId(id);
        Students existingStudent = studentsRepository.findById(id).get();
        student.setAttendances(existingStudent.getAttendances());
        studentsRepository.save(student);
        StudentsResponseModel model = modelMapper.map(student, StudentsResponseModel.class);
        model.setEnrolledCourses(new ArrayList<>());
        student.getEnrolledCoursesId().forEach(courseId -> {
            Courses course = coursesRepository.findById(courseId).get();
            EnrolledCourses enrolledCourses = modelMapper.map(course, EnrolledCourses.class);
            model.addEnrolledCourses(enrolledCourses);
        });
        return model;
    }

    public void deleteStudent(String id) {
        studentsRepository.deleteById(id);
    }

    public StudentsResponseModel getStudentById(String id) {
        Students student = studentsRepository.findById(id).get();
        StudentsResponseModel model = modelMapper.map(student, StudentsResponseModel.class);
        model.setEnrolledCourses(new ArrayList<>());
        student.getEnrolledCoursesId().forEach(courseId -> {
            Courses course = coursesRepository.findById(courseId).get();
            EnrolledCourses enrolledCourses = modelMapper.map(course, EnrolledCourses.class);
            model.addEnrolledCourses(enrolledCourses);
        });
        return model;
    }
}
