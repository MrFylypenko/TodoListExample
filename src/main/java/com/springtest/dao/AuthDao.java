package com.springtest.dao;

import com.springtest.model.auth.AuthUser;
import com.springtest.model.auth.LocalAuthUser;
import com.springtest.model.auth.VkAuthUser;

/**
 * Created by vano on 05.07.16.
 */
public interface AuthDao {

    VkAuthUser getVkAuthUserByVkId (String vkId);

    LocalAuthUser getLocalAuthUserByUsername (String username);

    void saveAuth (AuthUser authUser);

}
