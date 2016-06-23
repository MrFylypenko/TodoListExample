package com.springtest.service;

import com.springtest.dao.ProjectDao;
import com.springtest.model.dto.Page;
import com.springtest.model.entity.Project;
import com.springtest.model.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vano on 25.03.16.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectDao projectDao;


    @Override
    public List<Project> getAllProjects() {
        return projectDao.getAllProjects();
    }

    @Override
    @Transactional
    public void saveProject(Project project) {
        projectDao.saveProject(project);
    }

    @Override
    @Transactional
    public void updateProject(Project project) {
        projectDao.updateProject(project);
    }

    @Override
    @Transactional
    public void deleteProject(int projectId) {
        projectDao.deleteProject(projectId);
    }

    @Override
    public Page<Task> getTasksByProjectId(int projectId, int from) {
        return projectDao.getTasksByProjectId(projectId, from);
    }

    @Override
    public Project getProjectById(int projectId) {
        return projectDao.getProjectById(projectId);
    }

    @Override
    public Page<Project> getProjectsByQuery(int count, int from, String direction, String field) {
        return projectDao.getProjectsByQuery(count, from,direction,field);
    }
}
