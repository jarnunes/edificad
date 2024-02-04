package com.puc.edificad.commons.object_mother;

import com.jnunes.spgauth.model.User;
import com.puc.edificad.commons.builder.UserBuilder;
import com.jnunes.spgcore.commons.utils.JsonUtils;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjMotherUser extends ObjMotherBase {

    public static User criar() {
        return criarComIndice(1);
    }

    public static User criar(Integer indice) {
        return criarComIndice(indice);
    }

    public static List<User> criarLista() {
        return criarInstancias(ObjMotherUser::criarComIndice);
    }

    public static String criarJson() {
        User user = ObjMotherUser.criar();
        return JsonUtils.toJsonString(user);
    }

    private static User criarComIndice(Integer i) {
        return new UserBuilder()
                .comFullName("User Test Temp" + i)
                .comUsername("userunittest" + i)
                .comEmail("userunittest" + i + "@email.com")
                .comPassword("userunittest")
                .isEnabled(true)
                .isLocked(false)
                //.comUserRoles(Stream.of(Role.ADMIN, Role.WEBSERVICES).collect(Collectors.toSet()))
                .build();
    }

}
