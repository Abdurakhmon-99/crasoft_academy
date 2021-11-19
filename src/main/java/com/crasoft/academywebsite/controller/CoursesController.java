package com.crasoft.academywebsite.controller;

import com.crasoft.academywebsite.documents.Courses;
import com.crasoft.academywebsite.models.CourseStatisticsResponseModel;
import com.crasoft.academywebsite.models.CoursesAdminListModel;
import com.crasoft.academywebsite.models.CoursesAdminPopUpModel;
import com.crasoft.academywebsite.service.CoursesService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Documentation of the Courses of the IT academy", tags = {"Courses"})
@RestController
@RequestMapping("/courses")
public class CoursesController {

    @Autowired
    CoursesService coursesService;

    @GetMapping
    public ResponseEntity<List<CoursesAdminListModel>> getAllCourses(){
        List<CoursesAdminListModel> courses = coursesService.getAllCourses();
        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }
    @GetMapping({"/{id}"})
    public ResponseEntity<CoursesAdminPopUpModel> getCourseById(@PathVariable String id){
        CoursesAdminPopUpModel adminPopUpModel = coursesService.getCourseById(id);
        return ResponseEntity.status(HttpStatus.OK).body(adminPopUpModel);
    }

    @PostMapping
    public ResponseEntity<Courses> createCourse(@RequestBody CoursesAdminPopUpModel newCourse){
        Courses savedCourse = coursesService.createCourse(newCourse);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Courses> updateCourse(@PathVariable String id, @RequestBody CoursesAdminPopUpModel course){
        Courses updatedCourse = coursesService.updateCourse(id, course);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCourse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCourse(@PathVariable String id){
        coursesService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
