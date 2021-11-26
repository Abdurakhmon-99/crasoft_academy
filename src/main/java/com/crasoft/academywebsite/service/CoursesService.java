package com.crasoft.academywebsite.service;

import com.crasoft.academywebsite.documents.Courses;
import com.crasoft.academywebsite.models.CoursesAdminPopUpModel;
import com.crasoft.academywebsite.documents.Mentors;
import com.crasoft.academywebsite.documents.subdocuments.AdminPopUpMentor;
import com.crasoft.academywebsite.models.CoursesAdminListModel;
import com.crasoft.academywebsite.models.CoursesAdminPopUpRequestModel;
import com.crasoft.academywebsite.repository.ApplicantFormsRepository;
import com.crasoft.academywebsite.repository.MentorsRepository;
import com.crasoft.academywebsite.repository.StudentsRepository;
import com.crasoft.academywebsite.repository.CoursesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CoursesService {

    @Autowired
    CoursesRepository coursesRepository;
    @Autowired
    ApplicantFormsRepository applicantFormsRepository;
    @Autowired
    StudentsRepository studentsRepository;
    @Autowired
    MentorsRepository mentorsRepository;
    @Autowired
    ModelMapper modelMapper;

    public List<CoursesAdminListModel> getAllCourses() {
        List<Courses> coursesDto = coursesRepository.findAll();
        List<CoursesAdminListModel> courses = coursesDto.stream().map(coursedto -> modelMapper.map(coursedto, CoursesAdminListModel.class)).collect(Collectors.toList());
        courses.forEach(course -> {
            Courses temp = coursesRepository.findById(course.getId()).get();
            course.setMentorsName(mentorsRepository.findById(temp.getMentorsId()).get().getName());
        });
        return courses;
    }

    public Courses createCourse(CoursesAdminPopUpRequestModel newCourse) {
        Courses courseDto = modelMapper.map(newCourse, Courses.class);
        Mentors existingMentor = mentorsRepository.findById(newCourse.getMentorsId()).get();
        if(existingMentor != null){
            long createdTime = System.currentTimeMillis();
            Date dateToSave = new Date(createdTime);
            courseDto.setCreatedAt(dateToSave);
            Courses responseCourse = coursesRepository.save(courseDto);
            if(existingMentor.getCourses() == null){
                existingMentor.setCourses(new ArrayList<>());
            }
            existingMentor.addCourse(responseCourse.getId());
            mentorsRepository.save(existingMentor);

            return responseCourse;
        }
        return null;
    }
    public Courses updateCourse(String id, CoursesAdminPopUpRequestModel updatedCourse){
        updatedCourse.setId(id);
        Courses courseDto = modelMapper.map(updatedCourse,Courses.class);
        Courses existingCourse = coursesRepository.findById(id).get();
        if(updatedCourse.getMentorsId()  != existingCourse.getMentorsId()){
            Mentors oldMentor = mentorsRepository.findById(existingCourse.getMentorsId()).get();
            Mentors newMentor = mentorsRepository.findById(updatedCourse.getMentorsId()).get();
            oldMentor.removeCourse(id);
            newMentor.addCourse(id);
            mentorsRepository.save(oldMentor);
            mentorsRepository.save(newMentor);
        }
        courseDto.setCreatedAt(existingCourse.getCreatedAt());
        Courses responseModel = coursesRepository.save(courseDto);
        return responseModel;
    }

    public void deleteCourse(String id) {
        String mentorsID = coursesRepository.findById(id).get().getMentorsId();
        Mentors mentor = mentorsRepository.findById(mentorsID).get();
        mentor.removeCourse(id);
        mentorsRepository.save(mentor);
        coursesRepository.deleteById(id);
    }

    public CoursesAdminPopUpModel getCourseById(String id) {
        try {
            Courses existingCourse = coursesRepository.findById(id).get();
            CoursesAdminPopUpModel adminPopUpModel = modelMapper.map(existingCourse,CoursesAdminPopUpModel.class);
            Mentors mentor = mentorsRepository.findById(existingCourse.getMentorsId()).get();
            AdminPopUpMentor courseMentor = modelMapper.map(mentor, AdminPopUpMentor.class);
            adminPopUpModel.setMentor(courseMentor);
            return adminPopUpModel;
        } catch (Exception ex){
            throw new NoSuchElementException("Course with provided ID do not exist!");
        }

    }
}
