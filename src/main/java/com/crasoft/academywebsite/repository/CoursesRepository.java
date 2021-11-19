package com.crasoft.academywebsite.repository;

import com.crasoft.academywebsite.documents.Courses;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends MongoRepository<Courses, String> {

}
