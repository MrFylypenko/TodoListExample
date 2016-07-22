package com.springtest.dao;

import com.springtest.model.Status;
import com.springtest.model.entity.Comment;
import com.springtest.model.entity.Task;
import com.springtest.model.dto.Page;
import com.springtest.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Vano on 23.02.2016.
 */

@Repository
public class TaskDaoImpl implements TaskDao {

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public List<Task> getAllTasks() {
        return entityManager.createQuery("select t from Task t ").getResultList();
    }

    @Override
    public Page<Task> getAllTasksQuery(int count, int page, String direction, String field) {

        Page<Task> page1 = new Page();

        //TODO jpql injection attack
        page1.list = entityManager
                .createQuery("select t from Task t " +
                        "left join fetch t.createdBy " +
                        "left join fetch t.performer " +
                        "order by t." + field + " " + direction, Task.class)
                .setFirstResult(page)
                .setMaxResults(count)
                .getResultList();
        page1.total = entityManager.createQuery("select count(t.id) from Task t ", Long.class)
                .getSingleResult();
        return page1;
    }

    @Override
    public List<Task> getMyTasks() {
        //TODO
        return entityManager.createQuery("from Task ").getResultList();
    }

    @Override
    public void addTask(Task task) {
        entityManager.persist(task);
    }

    @Override
    public void updateTask(Task task) {
        entityManager.merge(task);
    }

    @Override
    public void deleteTask(Integer idTask) {
        entityManager.remove(entityManager.getReference(Task.class, idTask));
    }

    @Override
    public Task findTaskById(Integer idTask) {
        Task task = entityManager
                 .createQuery("select t from Task t" +
                         " left join fetch t.files f " +
                         " left join fetch t.project " +
                         " left join fetch t.createdBy" +
                         " left join fetch t.performer" +
                         " left join fetch f.createdBy" +
                         //" left join fetch c.createdBy " +
                         " where t.id = :id", Task.class)
                .setParameter("id", idTask).getSingleResult();



        task.setComments(new HashSet<Comment>(entityManager.createQuery("select c from Comment c " +
                " left join fetch c.createdBy where c.task.id = :id", Comment.class)
                .setParameter("id", task.getId())
                .getResultList()));



        return task;
    }

    @Override
    public Task getTaskReference(Integer idTask) {
        return entityManager.getReference(Task.class, idTask);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Page<Task> getUserTasksByStatus(User user, Status status, int page) {
        Page<Task> page1 = new Page();

        page1.list = entityManager.createQuery("select t from Task t left join fetch t.createdBy " +
                "where t.performer = :performer and t.status = :status", Task.class)
                .setParameter("performer", user)
                .setParameter("status", status)
                .setFirstResult(page)
                .setMaxResults(20)
                .getResultList();
        page1.total = entityManager.createQuery("select count(t.id) from Task t  " +
                "where t.performer = :performer and t.status = :status", Long.class)
                .setParameter("performer", user)
                .setParameter("status", status)
                .getSingleResult();
        return page1;
    }
}
