package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.Cat;
import com.example.animalclinicbot.repository.CatRepository;
import com.example.animalclinicbot.service.CatService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(CatController.class)
public class CatControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CatService service;
    @Test
    void getCatByIdTest() throws Exception {
        Cat cat = new Cat();
        cat.setId(1L);
        when(service.getById(anyLong())).thenReturn(cat);
        mockMvc.perform(
                        get("/cat/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        verify(service).getById(1L);
    }
    @Test
    void addCatTest() throws Exception {
        Cat cat = new Cat();
        cat.setId(1L);
        cat.setName("CatTest1");
        org.json.JSONObject userObject = new org.json.JSONObject();
        userObject.put("id", 1L);
        userObject.put("name", "CatTest1");
        when(service.addCat(cat)).thenReturn(cat);
        mockMvc.perform(
                        post("/cat")
                                .content(userObject.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userObject.toString()));
        verify(service).addCat(cat);
    }
    @Test
    void updateCatByIdTest() throws Exception {
        Cat cat = new Cat();
        cat.setId(1L);
        cat.setName("CatTest1");
        org.json.JSONObject userObject = new JSONObject();
        userObject.put("id", 1L);
        userObject.put("name", "CatTest1");
        when(service.update(cat)).thenReturn(cat);
        mockMvc.perform(
                        put("/cat")
                                .content(userObject.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userObject.toString()));
        verify(service).update(cat);
    }
    @Test
    void removeCatTest() throws Exception {
        mockMvc.perform(
                        delete("/cat/{id}", 1))
                .andExpect(status().isOk());
        verify(service).removeById(1L);
    }}
