package com.springtest.model.auth;

import com.springtest.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by vano on 05.07.16.
 */

@Entity
@Table(name = "auth_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class AuthUser implements Serializable, Authentication {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AuthorityType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Transient
    private Object details;

    @Transient
    private boolean authenticated = true;


    public AuthUser (){}

    public AuthUser(User user, Object details) {
        this.user = user;
        this.details = details;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return String.valueOf(user.getId());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityType getType() {
        return type;
    }

    public void setType(AuthorityType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDetails(Object details) {
        this.details = details;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthUser authUser = (AuthUser) o;

        return !(id != null ? !id.equals(authUser.id) : authUser.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
