package com.crasoft.academywebsite.service;

import com.crasoft.academywebsite.documents.Admin;
import com.crasoft.academywebsite.documents.Mentors;
import com.crasoft.academywebsite.models.MentorsAdminListModel;
import com.crasoft.academywebsite.models.MentorsAdminPopUpCreateModel;
import com.crasoft.academywebsite.models.MentorsAdminPopUpUpdateModel;
import com.crasoft.academywebsite.models.MentorsUpdateResponseModel;
import com.crasoft.academywebsite.repository.CoursesRepository;
import com.crasoft.academywebsite.repository.MentorsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MentorsServiceImpl implements MentorsService{
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    MentorsRepository mentorsRepository;
    @Autowired
    CoursesRepository coursesRepository;
    @Autowired
    ModelMapper modelMapper;

    public Mentors createMentor(MentorsAdminPopUpCreateModel mentor) {
        Mentors mentorToBeCreated = modelMapper.map(mentor,Mentors.class);
        mentorToBeCreated.setEncryptedPassword(bCryptPasswordEncoder.encode(mentor.getPassword()));
        long createdTime = System.currentTimeMillis();
        Date dateToSave = new Date(createdTime);
        mentorToBeCreated.setRegisteredAt(dateToSave);
        return mentorsRepository.save(mentorToBeCreated);
    }

    public MentorsAdminPopUpUpdateModel getMentorById(String id) {
        Mentors mentor = mentorsRepository.findById(id).get();
        MentorsAdminPopUpUpdateModel model = modelMapper.map(mentor, MentorsAdminPopUpUpdateModel.class);
        return model;
    }

    public List<MentorsAdminListModel> getAllMentors() {
        List<Mentors> mentorsDto = mentorsRepository.findAll();
        List<MentorsAdminListModel> mentorsEntity = mentorsDto.stream().map(mentorDto -> modelMapper.map(mentorDto, MentorsAdminListModel.class)).collect(Collectors.toList());
        return mentorsEntity;
    }

    public MentorsUpdateResponseModel updateMentor(String id, MentorsAdminPopUpUpdateModel newMentor) {
        newMentor.setId(id);
        Mentors existingMentor = mentorsRepository.findById(id).get();
        Mentors mentorToSave = modelMapper.map(newMentor, Mentors.class);
        mentorToSave.setRegisteredAt(existingMentor.getRegisteredAt());
        mentorToSave.setCourses(existingMentor.getCourses());
        mentorToSave.setUsername(existingMentor.getUsername());
        mentorToSave.setEncryptedPassword(existingMentor.getEncryptedPassword());
        MentorsUpdateResponseModel responseModel = modelMapper.map(mentorsRepository.save(mentorToSave), MentorsUpdateResponseModel.class);
        return responseModel;
    }

    public void deleteMentor(String id) {
        mentorsRepository.deleteById(id);
    }

    public List<Mentors> getAllWithPagination(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber -1,pageSize);
        return mentorsRepository.findAll(pageable).getContent();
    }

    public List<Mentors> getAllSorted() {
        Sort sort = Sort.by(Sort.Direction.ASC,"firstName");
        return mentorsRepository.findAll(sort);
    }
    @Override
    public Mentors getMentorDetailsByUsername(String username) {
        Mentors mentor = mentorsRepository.findByUsername(username);
        if(mentor == null) throw new UsernameNotFoundException(username);

        //use modelmapper to hide some fields
        return mentor;
    }

    @Override
    public boolean isMentor(String username) {
        if(mentorsRepository.findByUsername(username)==null){
            return false;
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Mentors mentor = mentorsRepository.findByUsername(username);
        if(mentor == null) throw new UsernameNotFoundException(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_MENTOR"));
        return new User(mentor.getUsername(),mentor.getEncryptedPassword(), true,true,true,true, authorities);
    }

}
