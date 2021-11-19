package com.crasoft.academywebsite.repository;

import com.crasoft.academywebsite.documents.Students;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends MongoRepository<Students, String> {
}
