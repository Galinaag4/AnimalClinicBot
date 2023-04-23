package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.model.Cat;
import com.example.animalclinicbot.model.PersonCat;

import com.example.animalclinicbot.model.Report;
import com.example.animalclinicbot.model.Status;
import com.example.animalclinicbot.repository.PersonCatRepository;
import com.example.animalclinicbot.service.PersonCatService;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void save() {}

    @Test
    public void testDeletePersonCat() throws Exception {
        mockMvc.perform(delete("/personCat/{id}",1))
                .andExpect(status().isOk());
        verify(personCatService).deleteByIdPersonCat(1L);
    }
    @Test
    void update()  {}

    @Test
    public void testGetAllPersonCats() throws Exception {
        when(personCatService.getAllPersonCat()).thenReturn(List.of(new PersonCat(1L, "Murka", 1998, "89524568974", "Pony@mail.ru", "Moscow", 111111, Status.SEARCH)));

        mockMvc.perform(
                        get("/personCat/all"))
                .andExpect(status().isOk());
    }
    @Test
    void getById() throws Exception {
        PersonCat personCat = new PersonCat();
        personCat.setId(1L);

        when(personCatService.getByIdPersonCat(anyLong())).thenReturn(personCat);

        mockMvc.perform(
                        get("/person-cat/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(personCatService).getByIdPersonCat(1L);
    }
}
