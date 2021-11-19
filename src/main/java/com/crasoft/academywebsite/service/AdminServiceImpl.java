package com.crasoft.academywebsite.service;

import com.crasoft.academywebsite.documents.Admin;
import com.crasoft.academywebsite.documents.Mentors;
import com.crasoft.academywebsite.models.LoginRequestModel;
import com.crasoft.academywebsite.repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AdminRepository adminRepository;

    public Admin createAdmin(LoginRequestModel adminDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Admin admin = modelMapper.map(adminDetails, Admin.class);
        admin.setEncryptedPassword(bCryptPasswordEncoder.encode(adminDetails.getPassword()));
        return adminRepository.save(admin);
    }

    @Override
    public Admin getAdminDetailsByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username);
        if(admin == null) throw new UsernameNotFoundException(username);

        //use modelmapper to hide some fields
        return admin;
    }
    @Override
    public boolean isAdmin(String username){
        if(adminRepository.findByUsername(username)==null){
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if(admin == null) throw new UsernameNotFoundException(username);

        return new User(admin.getUsername(),admin.getEncryptedPassword(), true,true,true,true, new ArrayList<>());
    }
}
