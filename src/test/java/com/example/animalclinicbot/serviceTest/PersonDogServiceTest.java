package com.example.animalclinicbot.serviceTest;

import com.example.animalclinicbot.model.Dog;
import com.example.animalclinicbot.model.PersonDog;
import com.example.animalclinicbot.model.Status;
import com.example.animalclinicbot.repository.DogRepository;
import com.example.animalclinicbot.repository.PersonDogRepository;
import com.example.animalclinicbot.service.PersonDogService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonDogService.class)
public class PersonDogServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonDogRepository personDogRepository;
    private PersonDog personDog;
    private JSONObject personObject;

    @Test
    public void testPersonDog() throws Exception {
        personDog = new PersonDog(1L, "Petr", 1998, "89524568974", "Pony@mail.ru", "Moscow", 111111, Status.SEARCH);
        personObject = new JSONObject();
        personObject.put("id", 1L);
        personObject.put("name", "Petr");
        personObject.put("yearOfBirth", 1998);
        personObject.put("phone", "89524568974");
        personObject.put("mail", "Pony@mail.ru");
        personObject.put("address", "Moscow");
        personObject.put("chatId", 111111);
        personObject.put("status", Status.SEARCH);
        when(personDogRepository.save(any(PersonDog.class))).thenReturn(personDog);
        when(personDogRepository.findById(eq(1L))).thenReturn(Optional.of(personDog));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/personDog/{id}",1L)
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Petr"))
                .andExpect(jsonPath("$.yearOfBirth").value(1998))
                .andExpect(jsonPath("$.phoneNumber").value("89524568974"))
                .andExpect(jsonPath("$.email").value("Pony@mail.ru"))
                .andExpect(jsonPath("$.address").value("Moscow"))
                .andExpect(jsonPath("$.passportNumber").value(111111))
                .andExpect(jsonPath("$.status").value("SEARCH"));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/personDog")
                        .content(personObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Petr"))
                .andExpect(jsonPath("$.yearOfBirth").value(1998))
                .andExpect(jsonPath("$.phoneNumber").value("89524568974"))
                .andExpect(jsonPath("$.email").value("Pony@mail.ru"))
                .andExpect(jsonPath("$.address").value("Moscow"))
                .andExpect(jsonPath("$.passportNumber").value(111111))
                .andExpect(jsonPath("$.status").value("SEARCH"));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/personDog")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Petr"))
                .andExpect(jsonPath("$.yearOfBirth").value(1998))
                .andExpect(jsonPath("$.phoneNumber").value("89524568974"))
                .andExpect(jsonPath("$.email").value("Pony@mail.ru"))
                .andExpect(jsonPath("$.address").value("Moscow"))
                .andExpect(jsonPath("$.passportNumber").value(111111))
                .andExpect(jsonPath("$.status").value("SEARCH"));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/personDog/{id}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
