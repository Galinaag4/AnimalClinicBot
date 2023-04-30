package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.Cat;
import com.example.animalclinicbot.model.PersonCat;

import com.example.animalclinicbot.model.Report;
import com.example.animalclinicbot.model.Status;
import com.example.animalclinicbot.repository.PersonCatRepository;
import com.example.animalclinicbot.service.PersonCatService;


import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
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
    private PersonCatRepository personCatRepository;

    @MockBean
    private PersonCatService personCatService;

    @Test
    public void testSavePersonCat() throws Exception {
        PersonCat personCat = new PersonCat();
        personCat.setId(1L);
        personCat.setNamePersonCat("John");
        personCat.setYearOfBirthPersonCat(30);
        when(personCatService.createPersonCat(any(PersonCat.class))).thenReturn(personCat);

        mockMvc.perform(post("/person-cat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personCat)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.namePersonCat").value("John"))
                .andExpect(jsonPath("$.yearOfBirthPersonCat").value(30));


        verify(personCatService).createPersonCat(any(PersonCat.class));
    }


    @Test
    public void testDeletePersonCat() throws Exception {
        mockMvc.perform(delete("/person-cat/{id}",1))
                .andExpect(status().isOk());
        verify(personCatService).deleteByIdPersonCat(1L);
    }
    @Test
    public void testUpdatePersonCat() throws Exception {
        PersonCat personCat = new PersonCat();
        personCat.setId(1L);
        personCat.setNamePersonCat("John");
        personCat.setYearOfBirthPersonCat(30);
        when(personCatService.update(any(PersonCat.class))).thenReturn(personCat);

        mockMvc.perform(put("/person-cat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(personCat)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.namePersonCat").value("John"))
                .andExpect(jsonPath("$.yearOfBirthPersonCat").value(30));


        verify(personCatService).update(any(PersonCat.class));
    }


    @Test
    public void testGetAllPersonCats() throws Exception {
        when(personCatService.getAllPersonCat()).thenReturn(List.of(new PersonCat(1L, "Murka", 1998, "89524568974", "Pony@mail.ru", "Moscow", 111111, Status.SEARCH)));

        mockMvc.perform(
                        get("/person-cat/all"))
                .andExpect(status().isOk());
    }
    @Test
    void getById() throws Exception {
        PersonCat personCat = new PersonCat("Murzik");
        personCat.setId(1L);

        when(personCatService.getByIdPersonCat(anyLong())).thenReturn(personCat);

        mockMvc.perform(
                        get("/person-cat/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(personCatService).getByIdPersonCat(1L);
    }
}
