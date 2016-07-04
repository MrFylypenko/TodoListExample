package com.springtest.comtroller;


import com.springtest.model.entity.Project;
import com.springtest.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by vano on 25.03.16.
 */
@RestController
@RequestMapping("api/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "rest", method = RequestMethod.GET)
    public Object getAllProjects() {
        return projectService.getAllProjects();
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Object getProjectsQuery(
            @RequestParam(value = "from", defaultValue = "0") int from,
            @RequestParam(value = "count", defaultValue = "15") int count,
            @RequestParam(value = "field", defaultValue = "name") String field,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        return projectService.getProjectsByQuery(count, from, direction, field);
    }

    @RequestMapping(value = "rest/{projectId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Object getProjectById(@PathVariable int projectId) {
        return projectService.getProjectById(projectId);
    }

    @RequestMapping(value = "rest", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProject(@RequestBody Project project) {
        projectService.saveProject(project);
    }

    @RequestMapping(value = "rest", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void updateProject(@RequestBody Project project) {
        projectService.updateProject(project);
    }

    @RequestMapping(value = "rest/{projectId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteProject(@PathVariable int projectId) {
        projectService.deleteProject(projectId);
    }

    @RequestMapping(value = "getTasksByProjectId/{projectId}/{from}", method = RequestMethod.GET)
    public Object getTasksByProjectId(@PathVariable int projectId, @PathVariable int from) {
        return projectService.getTasksByProjectId(projectId, from);
    }


}
