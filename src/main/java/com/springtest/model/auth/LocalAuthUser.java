package com.springtest.model.auth;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by vano on 05.07.16.
 */

@Entity
@Table (name = "local_auth_user")
public class LocalAuthUser extends AuthUser{


    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
