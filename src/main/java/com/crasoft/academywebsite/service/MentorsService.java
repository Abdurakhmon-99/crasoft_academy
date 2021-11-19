package com.crasoft.academywebsite.service;

import com.crasoft.academywebsite.documents.Mentors;
import com.crasoft.academywebsite.models.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MentorsService extends UserDetailsService {
    Mentors createMentor(MentorsAdminPopUpCreateModel mentor);
    MentorsAdminPopUpUpdateModel getMentorById(String id);
    List<MentorsAdminListModel> getAllMentors();
    MentorsUpdateResponseModel updateMentor(String id, MentorsAdminPopUpUpdateModel newMentor);
    void deleteMentor(String id);
    List<Mentors> getAllSorted();
    Mentors getMentorDetailsByUsername(String username);
}
