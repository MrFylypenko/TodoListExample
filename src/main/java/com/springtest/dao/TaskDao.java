package com.springtest.dao;

import com.springtest.model.Status;
import com.springtest.model.dto.Page;
import com.springtest.model.entity.Task;
import com.springtest.model.entity.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Vano on 23.02.2016.
 */
public interface TaskDao {

    List<Task> getAllTasks();

    Page<Task> getAllTasksQuery(int count, int page, String direction, String field);

    List<Task> getMyTasks();

    void addTask(Task task);

    void updateTask(Task task);

    void deleteTask(Integer idTask);

    Task findTaskById(Integer idTask);

    Task getTaskReference(Integer idTask);

    EntityManager getEntityManager();

    Page<Task> getUserTasksByStatus (User user, Status status, int page);


}
