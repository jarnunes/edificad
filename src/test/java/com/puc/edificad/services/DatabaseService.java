package com.puc.edificad.services;

import com.puc.edificad.model.edsuser.Role;
import com.puc.edificad.model.edsuser.RoleUser;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.edsuser.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DatabaseService {

    @Autowired
    private DistribuicaoCestaService distribuicaoCestaService;

    @Autowired
    private CestaService cestaService;

    @Autowired
    private VoluntarioService voluntarioService;

    @Autowired
    private BeneficiarioService beneficiarioService;

    @Autowired
    private UserService userService;

    public void cleanDatabase(){
        distribuicaoCestaService.findAll().forEach(distribuicaoCestaService::delete);
        cestaService.findAll().forEach(cestaService::delete);
        voluntarioService.findAll().forEach(voluntarioService::delete);
        beneficiarioService.findAll().forEach(beneficiarioService::delete);
    }

    public void cleanUserTables(){
        userService.findAll().forEach(userService::delete);
    }


    public User criarUsuarioRequisicoes(){
        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@email.com");
        user.setPassword("123");
        user.setFullName("System Administrator Test");
        user.setUserRoles(Stream.of(Role.ADMIN, Role.WEBSERVICES).map(this::obterRoles).collect(Collectors.toSet()));
        userService.save(user);

        user.setPassword("123");
        return user;
    }

    private RoleUser obterRoles(Role role){
        RoleUser roleUser = new RoleUser();
        roleUser.setRole(role);
        return roleUser;
    }

}
