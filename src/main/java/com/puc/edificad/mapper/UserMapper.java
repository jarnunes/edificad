package com.puc.edificad.mapper;

import com.puc.edificad.commons.utils.AuthUtils;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.edsuser.RoleUserRepository;
import com.puc.edificad.services.edsuser.UserRepository;
import com.puc.edificad.services.edsuser.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserMapper {

    UserRepository userRepository;
    RoleUserRepository roleUserRepository;


    @Autowired
    public void setUserRepository(UserRepository repositoryIn) {
        this.userRepository = repositoryIn;
    }

    @Autowired
    public void setRoleUserRepository(RoleUserRepository repositoryIn) {
        this.roleUserRepository = repositoryIn;
    }

    public UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setFullName(entity.getFullName());
        // usuários que usam a app-web possuem apenas um perfil. Revisar e melhorar lógica
        // usando dessa forma devido a limitação do thymeleaf em adicioar valores do option em uma lista ao selecionar na tela
        dto.setRole(AuthUtils.firstRole(entity.getUserRoles()));
        dto.setEnabled(entity.isEnabled());
        return dto;
    }

    public User toEntity(UserDto dto) {
        if (dto == null)
            return null;

        User entity = obterUsuario(dto.getId());
        entity.setId(dto.getId());
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        roleUserRepository.deleteAll(entity.getUserRoles());
        entity.getUserRoles().clear();
        entity.getUserRoles().add(AuthUtils.of(dto.getRole()));
        entity.setEnabled(dto.isEnabled());
        return entity;
    }

    private User obterUsuario(Long id) {
        return Optional.ofNullable(id).flatMap(userRepository::findById).orElse(new User());
    }
}
