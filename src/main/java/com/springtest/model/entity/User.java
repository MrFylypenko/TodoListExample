package com.springtest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springtest.model.auth.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * Created by vano on 15.02.16.
 *
 * The User JPA Entity
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {

    public User() {
    }

    public User(String username, String firstName) {
        this.username = username;
        this.firstName = firstName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username = "";

    private String password = "";

    private String firstName;

    private String lastName;

    private String imageUrl;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<AuthUser> authUsers = new HashSet<AuthUser>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserRole> roles = new HashSet<UserRole>();

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Task> tasks = new HashSet<Task>();

    @OneToMany(mappedBy = "performer", fetch = FetchType.LAZY)
    private Set<Task> myTasks = new HashSet<Task>();

    @OneToMany (mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<Comment>();

    @OneToMany (mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<File> files = new HashSet<File>();

    @OneToMany (mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Project> projects = new HashSet<Project>();


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        for (UserRole userRole : roles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(
                setAuths);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        return !(lastName != null ? !lastName.equals(user.lastName) : user.lastName != null);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
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


    public Set<Task> getMyTasks() {
        return myTasks;
    }

    public void setMyTasks(Set<Task> myTasks) {
        this.myTasks = myTasks;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @JsonIgnore
    public Set<AuthUser> getAuthUsers() {
        return authUsers;
    }

    public void setAuthUsers(Set<AuthUser> authUsers) {
        this.authUsers = authUsers;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
