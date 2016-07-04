package com.springtest.comtroller;

import com.springtest.model.Status;
import com.springtest.model.entity.Task;
import com.springtest.model.entity.User;
import com.springtest.model.dto.Page;
import com.springtest.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vano on 23.02.2016.
 */

@Controller
@RequestMapping(value = "api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "rest/{count}/{page}/{direction}/{field}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Task> getAllTasksQuery(@PathVariable int count,
                                       @PathVariable int page,
                                       @PathVariable String direction,
                                       @PathVariable String field) {
        return taskService.getAllTasksQuery(count, page, direction, field);
    }

    @RequestMapping(value = "rest", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@RequestBody Task task) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        task.setCreatedBy(user);
        taskService.addTask(task);
    }

    @RequestMapping(value = "rest",method = RequestMethod.POST)
    @ResponseBody
    public void updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
    }

    @RequestMapping(value = "rest/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
    }

    @RequestMapping(value = "rest/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Task findTaskById(@PathVariable int id) {
        return taskService.findTaskById(id);
    }

    @RequestMapping(value = "rest/getmytaskstodo/{from}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Task> getMyTasksTodo(@PathVariable int from) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return taskService.getUserTasksByStatus(user, Status.TODO, from);
    }

    @RequestMapping(value = "rest/getmytasksdoing/{from}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Task> getMyTasksDoing(@PathVariable int from) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return taskService.getUserTasksByStatus(user, Status.DOING, from);
    }

    @RequestMapping(value = "rest/getmytasksdone/{from}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Task> getMyTasksDone(@PathVariable int from) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return taskService.getUserTasksByStatus(user, Status.DONE, from);
    }


}
