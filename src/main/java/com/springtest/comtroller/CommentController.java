package com.springtest.comtroller;

import com.springtest.model.entity.Comment;
import com.springtest.model.entity.User;
import com.springtest.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by vano on 04.03.16.
 */

@RestController
@RequestMapping ("api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "rest", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void addFile(@RequestBody Comment comment) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setCreatedBy(user);
        commentService.saveComment(comment);
    }

    @RequestMapping(value = "rest/{commentId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteComment(@PathVariable int commentId) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentService.deleteComment(commentId, user);
    }


}
