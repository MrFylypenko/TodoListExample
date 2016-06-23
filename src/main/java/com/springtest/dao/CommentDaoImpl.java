package com.springtest.dao;

import com.springtest.model.entity.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by vano on 04.03.16.
 */
@Repository
public class CommentDaoImpl implements CommentDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void saveComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        entityManager.remove(entityManager.getReference(Comment.class, comment.getId()));
    }

    @Override
    public Comment getCommentById(int id) {
        return entityManager.find(Comment.class, id);
    }

}
