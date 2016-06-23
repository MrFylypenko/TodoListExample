package com.springtest.dao;


import com.springtest.model.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public User findByUserName(String username) {
        return entityManager.createQuery("from User u join fetch u.roles where u.username = :username", User.class)
                .setParameter("username", username).getSingleResult();
    }

    @Override
    public List<User> getUsersLikeUsername(String username) {
        System.out.println("username = " + username);
        if (username == null || username.length() == 0) {
            System.out.println("1");
            return entityManager.createQuery("from User u ", User.class)
                    .setMaxResults(20).getResultList();
        } else {
            System.out.println("2");
            return entityManager.createQuery("from User u where u.username like :username", User.class)
                    .setParameter("username", '%' + username + '%').setMaxResults(10).getResultList();
        }


    }

}
