package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.Cat;
import com.example.animalclinicbot.model.Dog;
import com.example.animalclinicbot.service.CatService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (CatController.class)
class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatService catService;



    @Test
    void getById() {
    }

    @Test
    void createCat() {

    }

    @Test
    void updateCat() {
    }

    @Test
    void delete() {

    }

    @Test
    void getAll() {
    }

    @Test
    void findCatsByPersonCatId() {
    }
}