package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.Cat;
import com.example.animalclinicbot.repository.CatRepository;
import com.example.animalclinicbot.service.CatService;
import net.minidev.json.JSONObject;
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
class CatControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CatRepository catRepository;

    @MockBean
    private CatService catService;



    @Test
    void getById() throws Exception {
        Cat cat = new Cat();
        cat.setId(1L);

        when(catService.getByIdCat(anyLong())).thenReturn(cat);

        mockMvc.perform(
                        get("/cat/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(catService).getByIdCat(1L);
    }
    @Test
    void save() throws Exception {
        Cat cat = new Cat();
        cat.setId(1L);
        cat.setNameCat("cat");
        cat.setBreedCat("aaa");
        cat.setYearOfBirthCat(2010);
        JSONObject userObject = new JSONObject();
        userObject.put("id", 1L);
        userObject.put("nameCat", "cat");
        userObject.put("breedCat", "aaa");
        userObject.put("yearOfBirthCat", 2010);

        when(catService.createCat(cat)).thenReturn(cat);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cat")
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nameCat").value("cat"))
                .andExpect(jsonPath("$.breedCat").value("aaa"))
                .andExpect(jsonPath("$.yearOfBirthCat").value(2010));
    }
   @Test
    void update() throws Exception {
       Cat cat = new Cat();
       cat.setId(1L);
       cat.setNameCat("cat");
       cat.setBreedCat("aaa");
       cat.setYearOfBirthCat(2010);
       JSONObject userObject = new JSONObject();
       userObject.put("id", 1L);
       userObject.put("nameCat", "cat");
       userObject.put("breedCat", "aaa");
       userObject.put("yearOfBirthCat", 2010);


       when(catService.updateCat(cat)).thenReturn(cat);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/cat")
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userObject.toString()));
        verify(catService).updateCat(cat);



    }
    @Test
    void remove() throws Exception {
        mockMvc.perform(
                        delete("/cat/{id}", 1))
                .andExpect(status().isOk());
        verify(catService).deleteCatById(1L);
    }
    @Test
    void getAll() throws Exception {
        when(catService.getAll()).thenReturn(List.of(new Cat()));

        mockMvc.perform(
                        get("/cat/all"))
                .andExpect(status().isOk());
    }

}

