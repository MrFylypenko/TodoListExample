package com.springtest.dao;

import com.springtest.model.entity.Comment;

/**
 * Created by vano on 04.03.16.
 */
public interface CommentDao {
     void saveComment (Comment comment);
     void deleteComment (Comment comment);
     Comment getCommentById (int id);
}
