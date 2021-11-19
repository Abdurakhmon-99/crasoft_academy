package com.crasoft.academywebsite.controller;

import com.crasoft.academywebsite.documents.Admin;
import com.crasoft.academywebsite.models.LoginRequestModel;
import com.crasoft.academywebsite.repository.AdminRepository;
import com.crasoft.academywebsite.repository.MentorsRepository;
import com.crasoft.academywebsite.service.AdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(value = "Documentation of the Courses of the IT academy", tags = {"Admin"})
@RestController
@RequestMapping("/")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    MentorsRepository mentorsRepository;
    @Autowired
    AdminRepository adminRepository;

    @PostMapping("admin")
    public Admin createAdmin(@RequestBody LoginRequestModel adminDetails){
        return adminService.createAdmin(adminDetails);
    }
    @RequestMapping("/login")
    public Admin checkIfLoginIsWorking(Principal user){
        if(adminService.isAdmin(user.getName()) || mentorsRepository.findByUsername(user.getName()) != null){
            return adminRepository.findByUsername(user.getName());
        }
        return null;
    }

}
