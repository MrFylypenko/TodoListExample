package com.springtest.dao;

import com.springtest.model.auth.AuthUser;
import com.springtest.model.auth.LocalAuthUser;
import com.springtest.model.auth.VkAuthUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vano on 05.07.16.
 */
@Repository
public class AuthDaoImpl implements AuthDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public VkAuthUser getVkAuthUserByVkId(String vkId) {

        List<VkAuthUser> vkAuthUsers = new ArrayList<VkAuthUser>();
        vkAuthUsers.addAll(entityManager
                .createQuery("select vk from VkAuthUser vk join fetch vk.user u join fetch u.roles where vk.vkId = :vkId", VkAuthUser.class)
                .setParameter("vkId", vkId)
                .getResultList());

        if(vkAuthUsers.size() > 0){
            return vkAuthUsers.get(0);
        }
        return null;
    }

    @Override
    public LocalAuthUser getLocalAuthUserByUsername(String username) {
        return entityManager
                .createQuery("select l from LocalAuthUser l where l.username = :username", LocalAuthUser.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void saveAuth(AuthUser authUser) {
        entityManager.persist(authUser);
    }
}
