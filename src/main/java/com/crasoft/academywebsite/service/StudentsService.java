package com.crasoft.academywebsite.service;

import com.crasoft.academywebsite.documents.Attendance;
import com.crasoft.academywebsite.documents.Students;
import com.crasoft.academywebsite.documents.subdocuments.StudentsAttendance;
import com.crasoft.academywebsite.models.StudentsResponseModel;
import com.crasoft.academywebsite.repository.AttendanceRespository;
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
    AttendanceRespository attendanceRespository;

    public List<StudentsResponseModel> getAllStudents() {
        List<Students> studentsList = studentsRepository.findAll();
        List<StudentsResponseModel> models = studentsList.stream().map(student -> modelMapper.map(student,StudentsResponseModel.class)).collect(Collectors.toList());
        return models;
    }
    public StudentsResponseModel getStudentById(String id) {
        Students student = studentsRepository.findById(id).get();
        StudentsResponseModel model = modelMapper.map(student, StudentsResponseModel.class);
        return model;
    }
    public StudentsResponseModel createStudent(Students student) {
        Students studentCreated = studentsRepository.save(student);
        StudentsResponseModel model = modelMapper.map(studentCreated, StudentsResponseModel.class);
        return model;
    }

    public StudentsResponseModel updateStudent(String id, Students student) {
        student.setId(id);
        Students existingStudent = studentsRepository.findById(id).get();
        studentsRepository.save(student);
        StudentsResponseModel model = modelMapper.map(student, StudentsResponseModel.class);
        return model;
    }

    public void deleteStudent(String id) {
        studentsRepository.deleteById(id);
    }

    public StudentsAttendance getStudentAttendanceByCourseAndGroup(String id, String courseId, String group) {
        List<Attendance> attendanceList = attendanceRespository.findByAttendedStudentsAndCourseIdAndGroup(id, courseId, group);
        StudentsAttendance attendance = new StudentsAttendance();
        attendance.setCourseId(courseId);
        attendance.setGroup(group);
        attendance.setAttendedDays(new ArrayList<>());
        attendanceList.forEach(attendance1 -> attendance.addAttendedDays(attendance1.getDate()));
        return attendance;
    }
}
