package com.crasoft.academywebsite.controller;

import com.crasoft.academywebsite.documents.Students;
import com.crasoft.academywebsite.models.StudentsResponseModel;
import com.crasoft.academywebsite.repository.StudentsRepository;
import com.crasoft.academywebsite.service.StudentsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Documentation of the Students of the IT academy", tags = {"Students"})
@RestController
@RequestMapping("/students")
public class StudentsController {

    @Autowired
    StudentsService studentsService;
    @GetMapping
    public ResponseEntity<List<StudentsResponseModel>> getAllStudents(){
        List<StudentsResponseModel> response = studentsService.getAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentsResponseModel> getStudentById(@PathVariable String id){
        StudentsResponseModel model = studentsService.getStudentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @PostMapping
    public ResponseEntity<StudentsResponseModel> createStudent(@RequestBody Students student){
        StudentsResponseModel createdStudent = studentsService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }
    @PutMapping("/{id}")
    public ResponseEntity<StudentsResponseModel> updateStudent(@PathVariable String id,@RequestBody Students student){
        StudentsResponseModel model = studentsService.updateStudent(id, student);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable String id){
        studentsService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
