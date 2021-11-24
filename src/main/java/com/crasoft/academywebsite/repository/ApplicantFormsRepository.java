package com.crasoft.academywebsite.repository;

import com.crasoft.academywebsite.documents.ApplicantForms;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantFormsRepository extends MongoRepository<ApplicantForms, String> {
    List<ApplicantForms> findByStatusIsLike(String status);
    long countByStatusIsLike(String status);
}
