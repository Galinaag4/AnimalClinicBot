package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.*;

import com.example.animalclinicbot.repository.PersonCatRepository;
import com.example.animalclinicbot.service.PersonCatService;


import com.example.animalclinicbot.service.PersonDogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonCatController.class)
public class PersonCatControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonCatService service;

    @Test
    void getById() throws Exception {
        PersonCat personCat = new PersonCat();
        personCat.setId(1L);
        service.create(personCat);
        when(service.getById(anyLong())).thenReturn(personCat);
        mockMvc.perform(
                        get("/person_cat/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        verify(service).getById(1L);
    }


    @Test
    void getAll() throws Exception {
        when(service.getAll()).thenReturn(List.of(new PersonCat()));
        mockMvc.perform(
                        get("/perso_cat/all"))
                .andExpect(status().isOk());
    }

    @Test
    void save() throws Exception {
        PersonCat personCat = new PersonCat();
        personCat.setId(1L);
        personCat.setName("Jane");
        org.json.JSONObject userObject = new org.json.JSONObject();
        userObject.put("id", 1L);
        userObject.put("name", "Jane");
        when(service.create(personCat)).thenReturn(personCat);
        mockMvc.perform(
                        post("/person_cat")
                                .content(userObject.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userObject.toString()));
        verify(service).create(personCat);
    }

    @Test
    void update() throws Exception {
        PersonCat personCat = new PersonCat();
        personCat.setId(1L);
        personCat.setName("Jane");
        org.json.JSONObject userObject = new JSONObject();
        userObject.put("id", 1L);
        userObject.put("name", "Jane");
        when(service.create(personCat)).thenReturn(personCat);
        mockMvc.perform(
                        post("/person_cat")
                                .content(userObject.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userObject.toString()));
        verify(service).create(personCat);
    }

    @Test
    void remove() throws Exception {
        mockMvc.perform(
                        delete("/person_cat/{id}", 1))
                .andExpect(status().isOk());
        verify(service).removeById(1L);
    }
}