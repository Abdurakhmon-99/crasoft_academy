package com.crasoft.academywebsite.repository;

import com.crasoft.academywebsite.documents.Attendance;
import com.crasoft.academywebsite.documents.Students;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsRepository extends MongoRepository<Students, String> {

}
