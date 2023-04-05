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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
public class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogRepository dogRepository;

    @SpyBean
    private DogService dogService;

    @InjectMocks
    private DogController dogController;
    @Test
    public void saveDog() throws Exception {
        Long id = 1L;
        String name = "Tuzik";

        JSONObject userObject = new JSONObject();
        userObject.put("name", name);

        Dog dog = new Dog();
        dog.setId(id);
        dog.setNameDog(name);

        when(dogRepository.save(any(Dog.class))).thenReturn(dog);
        when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(dog));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/dog") //send
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }
    @Test
    public void testDeleteDog() throws Exception {
        mockMvc.perform(delete("/dog"))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateDog() throws Exception {

        String name = "Bobik";

        JSONObject userObject = new JSONObject();
        userObject.put("name", name);

        Dog dog = new Dog();
        dog.setNameDog(name);
        when(dogService.update(dog)).thenReturn(dog);

        mockMvc.perform(put("/dog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bobik"));
    }
    @Test
    public void testGetAllDogs() throws Exception {
        List<Dog> dogs = Arrays.asList(new Dog("Fido"), new Dog("Buddy"));
        when(dogService.getAll()).thenReturn(dogs);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/dog")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Fido"))
                .andExpect(jsonPath("$.[1].name").value("Buddy"));
    }
}

