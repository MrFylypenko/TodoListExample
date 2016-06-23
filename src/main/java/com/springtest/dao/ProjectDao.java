package com.springtest.dao;

import com.springtest.model.dto.Page;
import com.springtest.model.entity.Project;
import com.springtest.model.entity.Task;

import java.util.List;

/**
 * Created by vano on 25.03.16.
 */
public interface ProjectDao {

    List<Project> getAllProjects();

    void saveProject(Project project);

    void updateProject(Project project);

    void deleteProject(int projectId);

    Page<Task> getTasksByProjectId(int projectId, int from);

    Project getProjectById(int projectId);

    Page<Project> getProjectsByQuery(int count, int from, String direction, String field);


}
