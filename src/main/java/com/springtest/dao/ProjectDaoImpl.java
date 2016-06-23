package com.springtest.dao;

import com.springtest.model.dto.Page;
import com.springtest.model.entity.Project;
import com.springtest.model.entity.Task;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by vano on 25.03.16.
 */
@Repository
public class ProjectDaoImpl implements ProjectDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Project> getAllProjects() {
        return entityManager.createQuery("select distinct p from Project p " +
                "left join fetch p.tasks " +
                "left join fetch p.createdBy ", Project.class)
                .getResultList();
    }

    @Override
    public void saveProject(Project project) {
        entityManager.persist(project);
    }

    @Override
    public void updateProject(Project project) {
        entityManager.merge(project);
    }

    @Override
    public void deleteProject(int projectId) {
        entityManager.remove(entityManager.getReference(Project.class, projectId));
    }

    @Override
    public Page<Task> getTasksByProjectId(int projectId, int from) {

        Page<Task> page = new Page<Task>();

        page.list = entityManager
                .createQuery("select distinct t from Task t " +
                        "left join fetch t.createdBy " +
                        "left join fetch t.performer " +
                        "where t.project.id = :id", Task.class)
                .setParameter("id", projectId)
                .setFirstResult(from)
                .setMaxResults(10)
                .getResultList();
        page.total = entityManager
                .createQuery("select distinct  count(t.id) from Task t " +
                        "where t.project.id = :id", Long.class)
                .setParameter("id", projectId).getSingleResult();
        return page;
    }

    @Override
    public Project getProjectById(int projectId) {
        return entityManager.createQuery("select distinct p from Project p left join fetch p.createdBy where p.id = :id"
                , Project.class)
                .setParameter("id", projectId).getSingleResult();
    }

    @Override
    public Page<Project> getProjectsByQuery(int count, int from, String direction, String field) {

        Page<Project> page = new Page<Project>();

        //TODO jpql injection attack
        page.list = entityManager
                .createQuery("select distinct" +
                        " p from Project p left join fetch p.createdBy order by p." + field + " " + direction, Project.class)
                .setFirstResult(from)
                .setMaxResults(count)
                .getResultList();
        page.total = entityManager
                .createQuery("select distinct  count(p.id) from Project p ", Long.class)
                .getSingleResult();
        return page;


    }
}
