package com.springtest.model.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by vano on 05.07.16.
 */

@Entity
@Table (name = "vk_auth_user")
public class VkAuthUser extends AuthUser{

    @Column(name = "email")
    private String email;

    @Column(name = "token")
    private String accessToken;

    @Column
    private String vkId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
    }
}
