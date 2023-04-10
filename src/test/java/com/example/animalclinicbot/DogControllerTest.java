package com.example.animalclinicbot;


import com.example.animalclinicbot.controller.DogController;
import com.example.animalclinicbot.model.Dog;
import com.example.animalclinicbot.repository.DogRepository;
import com.example.animalclinicbot.service.DogService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(DogController.class)
public class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @MockBean
    private DogService dogService;


    @Test
    public void saveDog() throws Exception {
        Dog dog = new Dog();
        dog.setId(1L);
        dog.setNameDog("Tuzik");
        JSONObject userObject = new JSONObject();
        userObject.put("id", 1L);
        userObject.put("nameDog", "Tuzik");



        when(dogService.create(dog)).thenReturn(dog);

        mockMvc.perform(
                        post("/dog")
                                .content(userObject.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userObject.toString()));

        verify(dogService).create(dog);
    }
    @Test
    public void testDeleteDog() throws Exception {
        mockMvc.perform(delete("/dog/{id}",1))
                .andExpect(status().isOk());
        verify(dogService).removeById(1L);
    }
    @Test
    public void testUpdateDog() throws Exception {
        Dog dog = new Dog();
        dog.setId(1L);
        dog.setNameDog("Bobik");
        JSONObject userObject = new JSONObject();
        userObject.put("id", 1L);
        userObject.put("nameDog", "Bobik");
        when(dogService.update(dog)).thenReturn(dog);

        mockMvc.perform(put("/dog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(userObject.toString()));
        verify(dogService).update(dog);
    }
    @Test
    public void testGetAllDogs() throws Exception {
            when(dogService.getAll()).thenReturn(List.of(new Dog()));

            mockMvc.perform(
                            get("/dog/all"))
                    .andExpect(status().isOk());
        }

    }


