package com.crasoft.academywebsite.service;

import com.crasoft.academywebsite.documents.Attendance;
import com.crasoft.academywebsite.models.AttendanceRequestModel;
import com.crasoft.academywebsite.models.AttendanceResponseModel;
import com.crasoft.academywebsite.repository.AttendanceRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    @Autowired
    AttendanceRespository attendanceRespository;
    @Autowired
    ModelMapper modelMapper;

    public AttendanceResponseModel createAttendance(String id, AttendanceRequestModel attendance) {
        Attendance newAttendance = modelMapper.map(attendance, Attendance.class);
        newAttendance.setCourseId(id);
        long createdTime = System.currentTimeMillis();
        Date dateToSave = new Date(createdTime);

        newAttendance.setDate(dateToSave);
        return modelMapper.map(attendanceRespository.save(newAttendance),AttendanceResponseModel.class);
    }

    public List<AttendanceResponseModel> getAttendanceByCourse(String id, String group) {
        List<Attendance> attendances = attendanceRespository.findByCourseIdAndGroup(id, group);

        List<AttendanceResponseModel> attendanceList = attendances.stream().map(attendance -> modelMapper.map(attendance, AttendanceResponseModel.class)).collect(Collectors.toList());
        return attendanceList;
    }
}
