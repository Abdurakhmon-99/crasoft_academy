package com.crasoft.academywebsite.controller;

import com.crasoft.academywebsite.documents.Mentors;
import com.crasoft.academywebsite.models.MentorsAdminListModel;
import com.crasoft.academywebsite.models.MentorsAdminPopUpCreateModel;
import com.crasoft.academywebsite.models.MentorsAdminPopUpUpdateModel;
import com.crasoft.academywebsite.models.MentorsUpdateResponseModel;
import com.crasoft.academywebsite.service.MentorsServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Documentation of the Mentors of the IT academy", tags = {"Mentors"})
@RestController
@RequestMapping("/mentors")
public class MentorsController {

    @Autowired
    MentorsServiceImpl mentorsService;

    @PostMapping
    public ResponseEntity<Mentors> createMentor(@RequestBody MentorsAdminPopUpCreateModel mentor){
        Mentors savedMentor = mentorsService.createMentor(mentor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMentor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorsAdminPopUpUpdateModel> getMentorById(@PathVariable String id){
        MentorsAdminPopUpUpdateModel model= mentorsService.getMentorById(id);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @GetMapping
    public ResponseEntity<List<MentorsAdminListModel>> getAllMentors(){
        List<MentorsAdminListModel> mentors = mentorsService.getAllMentors();
        return ResponseEntity.status(HttpStatus.OK).body(mentors);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MentorsUpdateResponseModel> updateMentor(@PathVariable String id, @RequestBody MentorsAdminPopUpUpdateModel newMentor){
        MentorsUpdateResponseModel model = mentorsService.updateMentor(id, newMentor);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMentor(@PathVariable String id){
        mentorsService.deleteMentor(id);
        return ResponseEntity.noContent().build();
    }

}
