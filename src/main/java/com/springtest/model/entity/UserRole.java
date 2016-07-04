package com.springtest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by vano on 19.02.16.
 *
 * The UserRole JPA Entity
 */
@Entity
public class UserRole {

    public UserRole() {
    }

    @Id
    @GeneratedValue
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

        if (id != userRole.id) return false;
        return !(role != null ? !role.equals(userRole.role) : userRole.role != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }



}
