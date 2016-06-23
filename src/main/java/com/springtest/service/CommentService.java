package com.springtest.service;

import com.springtest.model.entity.Comment;
import com.springtest.model.entity.User;

/**
 * Created by vano on 04.03.16.
 */
public interface CommentService {

    void saveComment (Comment comment);

    void deleteComment (int idComment, User user);

}
