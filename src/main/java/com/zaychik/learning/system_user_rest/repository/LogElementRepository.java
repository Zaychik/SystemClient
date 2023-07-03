package com.zaychik.learning.system_user_rest.repository;


import com.arangodb.springframework.repository.ArangoRepository;
import com.zaychik.learning.system_user_rest.model.LogElement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogElementRepository extends ArangoRepository<LogElement, String> {
    List<LogElement> findAllByUserEmail(String userEmail);
}
