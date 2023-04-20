package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.PersonDog;
import com.example.animalclinicbot.model.Status;
import com.example.animalclinicbot.service.PersonDogService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonDogController.class)
public class PersonDogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDogService personDogService;
    @Test
    public void savePersonDog() throws Exception {
        PersonDog personDog = new PersonDog(1L, "Petr", 1998, "89524568974", "Pony@mail.ru", "Moscow", 111111, Status.SEARCH);
        personDog.setId(1L);
        personDog.setName("petr");
        JSONObject userObject = new JSONObject();
        userObject.putIfAbsent("id", 1L);
        userObject.putIfAbsent("name", "petr");
        when(personDogService.create(personDog)).thenReturn(personDog);
        mockMvc.perform(
                        post("/person-dog")
                                .content(userObject.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userObject.toString()));

        verify(personDogService).create(personDog);
    }
    @Test
    public void testDeletePersonDog() throws Exception {
        mockMvc.perform(delete("/person-dog/{id}",1))
                .andExpect(status().isOk());
        verify(personDogService).removeById(1L);
    }
    @Test
    public void testUpdatePersonDog() throws Exception {
        PersonDog personDog = new PersonDog(1L, "Petr", 1998, "89524568974", "Pony@mail.ru", "Moscow", 111111, Status.SEARCH);
        personDog.setId(1L);
        personDog.setName("petr");
        JSONObject userObject = new JSONObject();
        userObject.putIfAbsent("id", 1L);
        userObject.putIfAbsent("name", "petr");
        when(personDogService.update(personDog)).thenReturn(personDog);

        mockMvc.perform(put("/person-dog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userObject.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(userObject.toString()));
        verify(personDogService).update(personDog);
    }
    @Test
    public void testGetAllPersonDogs() throws Exception {
        when(personDogService.getAll()).thenReturn(List.of(new PersonDog(1L, "Petr", 1998, "89524568974", "Pony@mail.ru", "Moscow", 111111, Status.SEARCH)));

        mockMvc.perform(
                        get("/person-dog/all"))
                .andExpect(status().isOk());
    }

}

