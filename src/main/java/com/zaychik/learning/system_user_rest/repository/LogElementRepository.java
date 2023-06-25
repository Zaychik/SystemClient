package com.zaychik.learning.system_user_rest.repository;


import com.arangodb.springframework.repository.ArangoRepository;
import com.zaychik.learning.system_user_rest.entity.LogElement;
import com.zaychik.learning.system_user_rest.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogElementRepository extends ArangoRepository<LogElement, String> {
    List<LogElement> findAllByUserEmail(String userEmail);
}
