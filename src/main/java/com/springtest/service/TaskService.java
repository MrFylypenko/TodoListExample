package com.springtest.service;

import com.springtest.model.Status;
import com.springtest.model.entity.Task;
import com.springtest.model.dto.Page;
import com.springtest.model.entity.User;

import java.util.List;

/**
 * Created by Vano on 23.02.2016.
 */
public interface TaskService {

    List<Task> getAllTasks();

    Page<Task> getAllTasksQuery(int count, int page, String direction, String field);

    List<Task> getMyTasks();

    void addTask(Task task);

    void updateTask(Task task);

    void deleteTask(Integer idTask);

    Task findTaskById(Integer idTask);

    Page<Task> getUserTasksByStatus (User user, Status status, int page);


}
