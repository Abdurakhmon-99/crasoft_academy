package com.crasoft.academywebsite.service;

import com.crasoft.academywebsite.documents.Admin;
import com.crasoft.academywebsite.models.LoginRequestModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AdminService extends UserDetailsService {
    Admin createAdmin(LoginRequestModel adminDetails);
    Admin getAdminDetailsByUsername(String username);
    boolean isAdmin(String username);
}
