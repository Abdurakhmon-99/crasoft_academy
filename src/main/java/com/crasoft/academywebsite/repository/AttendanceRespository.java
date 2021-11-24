package com.crasoft.academywebsite.repository;

import com.crasoft.academywebsite.documents.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AttendanceRespository extends MongoRepository<Attendance, String> {

    List<Attendance> findByCourseIdAndGroup(String id, String group);
    List<Attendance> findByAttendedStudentsAndCourseIdAndGroup (String id, String courseId, String group);
}
