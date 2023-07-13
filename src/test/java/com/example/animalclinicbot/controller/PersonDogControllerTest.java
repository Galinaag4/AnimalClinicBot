package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.PersonDog;
import com.example.animalclinicbot.model.Status;
import com.example.animalclinicbot.service.PersonDogService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonDogController.class)
class PersonDogControllerTest {

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private PersonDogService service;

        @Test
        void getById() throws Exception {
            PersonDog personDog = new PersonDog();
            personDog.setId(1L);
            service.save(personDog);
            when(service.getById(anyLong())).thenReturn(personDog);
            mockMvc.perform(
                            get("/person_dog/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1));
            verify(service).getById(1L);
        }


        @Test
        void getAll() throws Exception {
            when(service.getAll()).thenReturn(List.of(new PersonDog()));
            mockMvc.perform(
                            get("/perso_dog/all"))
                    .andExpect(status().isOk());
        }

        @Test
        void save() throws Exception {
            PersonDog personDog = new PersonDog();
            personDog.setId(1L);
            personDog.setName("John");
            JSONObject userObject = new JSONObject();
            userObject.put("id", 1L);
            userObject.put("name", "John");
            when(service.save(personDog)).thenReturn(personDog);
            mockMvc.perform(
                            post("/person_dog")
                                    .content(userObject.toString())
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(userObject.toString()));
            verify(service).save(personDog);
        }

        @Test
        void update() throws Exception {
            PersonDog personDog = new PersonDog();
            personDog.setId(1L);
            personDog.setName("John");
            JSONObject userObject = new JSONObject();
            userObject.put("id", 1L);
            userObject.put("name", "John");
            when(service.save(personDog)).thenReturn(personDog);
            mockMvc.perform(
                            post("/person_dog")
                                    .content(userObject.toString())
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(userObject.toString()));
            verify(service).save(personDog);
        }

        @Test
        void remove() throws Exception {
            mockMvc.perform(
                            delete("/person_dog/{id}", 1))
                    .andExpect(status().isOk());
            verify(service).delete(1L);
        }
    }