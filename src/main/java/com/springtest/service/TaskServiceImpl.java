package com.springtest.service;

import com.springtest.dao.TaskDao;
import com.springtest.model.Status;
import com.springtest.model.entity.Task;
import com.springtest.model.dto.Page;
import com.springtest.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vano on 23.02.2016.
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskDao taskDao;

    @Autowired
    UserService userService;

    @Override
    public List<Task> getAllTasks() {
        return taskDao.getAllTasks();
    }

    @Override
    public Page<Task> getAllTasksQuery(int count, int page, String direction, String field) {
        return taskDao.getAllTasksQuery(count, page, direction, field);
    }

    @Override
    public List<Task> getMyTasks() {
        return taskDao.getMyTasks();
    }

    @Override
    @Transactional
    public void addTask(Task task) {
        taskDao.addTask(task);
    }

    @Override
    public void updateTask(Task task) {
        taskDao.updateTask(task);
    }

    @Override
    public void deleteTask(Integer idTask) {
        taskDao.deleteTask(idTask);
    }

    @Override
    public Task findTaskById(Integer idTask) {
        return taskDao.findTaskById(idTask);
    }

    @Override
    public Page<Task> getUserTasksByStatus(User user, Status status, int page) {
        return taskDao.getUserTasksByStatus(user, status, page);
    }

}
