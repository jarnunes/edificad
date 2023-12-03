package com.puc.edificad;

import com.puc.edificad.services.DatabaseService;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PrepararAmbienteTestes {

    @Autowired
    private DatabaseService databaseService;

//    @BeforeAll
//    public void init() {
//        // cadastrar usuário para obter o token a cada requisição (nos testes)
//        databaseService.criarUsuarioRequisicoes();
//    }
}
