package com.example.animalclinicbot;


import com.example.animalclinicbot.controller.DogController;
import com.example.animalclinicbot.model.Dog;
import com.example.animalclinicbot.repository.DogRepository;
import com.example.animalclinicbot.service.DogService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
public class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
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
        userObject.put("id", id);

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
    public void testGetDog() throws Exception {
        Long id = 1L;
        String name = "Tuzik";

        Dog dog = new Dog();
        dog.setId(id);
        dog.setNameDog(name);

        when(dogRepository.findById(eq(id))).thenReturn(Optional.of(dog));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dog/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));

    }

    @Test
    public void testDeleteDog() throws Exception {
        Long id = 1L;
        String name = "Tuzik";
        Dog dog = new Dog();
        dog.setId(id);
        dog.setNameDog(name);

        when(dogRepository.findById(eq(id))).thenReturn(Optional.of(dog));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/dog/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    public void testUpdateDog() throws Exception {


        Long id = 1L;
        String oldName = "Tuzik";
        String newName = "Bobik";

        JSONObject userObject = new JSONObject();
        userObject.put("id", id);
        userObject.put("name", newName);

        Dog dog = new Dog(id, oldName);
        Dog updatedDog = new Dog(id, newName);

        when(dogRepository.save(any(Dog.class))).thenReturn(updatedDog);
        when(dogRepository.findById(any(Long.class))).thenReturn(Optional.of(dog));


        mockMvc.perform(MockMvcRequestBuilders
                        .put("/dog")
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(newName));
    }

    @Test
    public void testGetAllDogs() throws Exception {
//        List<Dog> dogs = Arrays.asList(new Dog1("Fido"), new Dog2("Buddy"));

        Long id1 = 1L;
        String name1 = "Tuzik";
        Long id2 = 2L;
        String name2 = "Bobik";

//        JSONObject userObject = new JSONObject();
//        userObject.put("id", id);
//        userObject.put("name", newName);

        Dog dog1 = new Dog(id1, name1);
        Dog dog2 = new Dog(id2, name2);
        List<Dog> dogs = Arrays.asList(dog1, dog2);
        when(dogRepository.save(any(Dog.class))).thenReturn(dog1);
        when(dogRepository.save(any(Dog.class))).thenReturn(dog2);

        when(dogRepository.findAll()).thenReturn(dogs);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/dog/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(dog1, dog2))));
    }
}


