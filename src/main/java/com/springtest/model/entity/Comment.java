package com.springtest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vano on 29.02.16.
 *
 * The Comment JPA Entity
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

    public Comment() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Date createdDate;

    @JsonIgnoreProperties({"comments", "files", "tasks"})
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @JsonIgnoreProperties({"comments", "files"})
    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    private String value;

    @PrePersist
    public void prePersist(){
        createdDate = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (id != comment.id) return false;
        return !(value != null ? !value.equals(comment.value) : comment.value != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
