package com.springtest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springtest.model.Priority;
import com.springtest.model.Status;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Vano on 23.02.2016.
 * <p>
 * The Task JPA Entity
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    public Task() {
    }

    @Id
    @GeneratedValue
    private int id;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Comment> comments = new LinkedHashSet<Comment>();

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<File> files = new LinkedHashSet<File>();

    private String name;

    private String description;

    @Column(updatable = false)
    private Date createdDate;


    @JsonIgnoreProperties({"myTasks", "tasks"})
    @JoinColumn(updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @JsonIgnoreProperties({"myTasks", "tasks"})
    @JoinColumn(updatable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private User performer;

    @JsonIgnoreProperties({"tasks"})
    @JoinColumn(updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;


    @Formula(value = "(select count(f.id) from File f where f.task_id = id)")
    private int countFiles;

    @Formula(value = "(select count(c.id) from Comment c where c.task_id = id)")
    private int countComments;

    @Enumerated(value = EnumType.ORDINAL)
    private Priority priority;

    @Enumerated(value = EnumType.ORDINAL)
    private Status status;

    @PrePersist
    public void prepersist() {
        createdDate = new Date();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdDate=" + createdDate +
                ", user=" + (createdBy == null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;
        if (id != task.id) return false;

        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        return !(description != null ? !description.equals(task.description) : task.description != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public int getCountFiles() {
        return countFiles;
    }

    public void setCountFiles(int countFiles) {
        this.countFiles = countFiles;
    }

    public int getCountComments() {
        return countComments;
    }

    public void setCountComments(int countComments) {
        this.countComments = countComments;
    }

    public User getPerformer() {
        return performer;
    }

    public void setPerformer(User performer) {
        this.performer = performer;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
