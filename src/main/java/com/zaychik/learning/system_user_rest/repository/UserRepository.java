package com.zaychik.learning.system_user_rest.repository;

import com.zaychik.learning.system_user_rest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);



}
