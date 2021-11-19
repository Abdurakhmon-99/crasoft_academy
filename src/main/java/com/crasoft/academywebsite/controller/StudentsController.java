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
    public Students getStudentById(@PathVariable String id){
        return studentsService.getStudentById(id);
    }

    @PostMapping
    public Students createStudent(@RequestBody Students student){
        return studentsService.createStudent(student);
    }
    @PutMapping("/{id}")
    public Students updateStudent(@PathVariable String id,@RequestBody Students student){
        return studentsService.updateStudent(id, student);
    }
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id){
        studentsService.deleteStudent(id);
    }
}
