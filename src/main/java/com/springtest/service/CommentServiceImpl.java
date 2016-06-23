package com.springtest.service;

import com.springtest.dao.CommentDao;
import com.springtest.dao.TaskDao;
import com.springtest.model.entity.Comment;
import com.springtest.model.entity.Task;
import com.springtest.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vano on 04.03.16.
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    TaskDao taskDao;

    @Override
    @Transactional
    public void saveComment(Comment comment) {
        commentDao.saveComment(comment);
    }

    @Override
    @Transactional
    public void deleteComment(int idComment, User user) {
        Comment comment = commentDao.getCommentById(idComment);
        if(!comment.getCreatedBy().equals(user)) {
            throw new AccessDeniedException("You don't have access to delete this comment");
        }
        commentDao.deleteComment(comment);
    }
}
