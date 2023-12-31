package com.puc.edificad.model.edsuser;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jnunes.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"userRoles"})
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_user", allocationSize = 1)
@Table(name = "eds_user")
public class User extends BaseEntity implements UserDetails {

    @Serial
    private static final long serialVersionUID = 2053508908322293256L;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled = Boolean.TRUE;

    @Column(nullable = false)
    private Boolean locked = Boolean.FALSE;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RoleUser> userRoles = new HashSet<>();

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UnaryOperator<String> mapperRole = role -> "ROLE_" + role;

        List<SimpleGrantedAuthority> sga = userRoles.stream()
                .map(RoleUser::getRole)
                .map(Enum::name)
                .map(mapperRole)
                .map(SimpleGrantedAuthority::new).toList();

        return new HashSet<>(sga);
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return Boolean.FALSE.equals(getLocked());
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(getEnabled());
    }

    public List<String> getRoles(){
        return getUserRoles().stream().map(RoleUser::getRole).map(Role::name).toList();
    }
}
