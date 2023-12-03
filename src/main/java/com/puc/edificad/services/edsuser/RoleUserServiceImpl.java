package com.puc.edificad.services.edsuser;

import com.puc.edificad.model.edsuser.RoleUser;
import com.puc.edificad.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleUserServiceImpl extends BaseServiceImpl<RoleUser> implements RoleUserService {

    private RoleUserRepository repository;

    @Autowired
    public void setRepository(RoleUserRepository repositoryIn) {
        this.repository = repositoryIn;
    }


}
