package com.puc.edificad.commons.builder;

import com.puc.edificad.model.edsuser.Role;
import com.puc.edificad.model.edsuser.RoleUser;
import com.puc.edificad.model.edsuser.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserBuilder {

    User user;

    public UserBuilder() {
        this.user = new User();
    }

    public UserBuilder comFullName(String fullName) {
        user.setFullName(fullName);
        return this;
    }

    public UserBuilder comUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder comEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder comPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder isEnabled(Boolean enabled) {
        user.setEnabled(enabled);
        return this;
    }

    public UserBuilder isLocked(Boolean locked) {
        user.setLocked(locked);
        return this;
    }

    public UserBuilder comUserRoles(Set<Role> roles) {
        user.setUserRoles(roles.stream().map(this::criarRoleUser).collect(Collectors.toSet()));
        return this;
    }

    private RoleUser criarRoleUser(Role role) {
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(role);
        return roleUser;
    }

    public User build() {
        return user;
    }


}
