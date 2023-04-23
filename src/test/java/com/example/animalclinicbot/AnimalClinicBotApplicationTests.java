package com.example.animalclinicbot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import liquibase.repackaged.net.sf.jsqlparser.statement.update.Update;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.mockito.ArgumentMatchers.any;
import static org.xmlunit.util.Linqy.asList;
@SpringBootTest
class AnimalClinicBotApplicationTests {

    @Test
    void contextLoads() {
    }

}
