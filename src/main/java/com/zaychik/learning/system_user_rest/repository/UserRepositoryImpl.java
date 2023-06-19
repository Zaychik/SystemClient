package com.zaychik.learning.system_user_rest.repository;

import com.zaychik.learning.system_user_rest.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<User> findAll() {
        return entityManager.createQuery("from User u order by u.id desc", User.class)
                .getResultList();
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
            return true;
        }
        else
            return false;
    }
}
