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
            Students studentDTO = studentsRepository.findById(model.getId()).get();
            ArrayList<String> coursesID = studentDTO.getEnrolledCoursesId();
            coursesID.forEach(courseID -> {
                Courses courseDto = coursesRepository.findById(courseID).get();
                model.setEnrolledCourses(new ArrayList<>());
                model.addEnrolledCourses(modelMapper.map(courseDto,EnrolledCourses.class));
            });
        });

        return models;
    }

    public Students createStudent(Students student) {
        return studentsRepository.save(student);
    }

    public Students updateStudent(String id, Students student) {
        student.setId(id);
        return studentsRepository.save(student);
    }

    public void deleteStudent(String id) {
        studentsRepository.deleteById(id);
    }

    public Students getStudentById(String id) {
        return studentsRepository.findById(id).get();
    }
}
