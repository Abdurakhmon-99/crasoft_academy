package com.crasoft.academywebsite.repository;

import com.crasoft.academywebsite.documents.Mentors;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorsRepository extends MongoRepository<Mentors, String> {
    Mentors findByUsername(String username);
}
