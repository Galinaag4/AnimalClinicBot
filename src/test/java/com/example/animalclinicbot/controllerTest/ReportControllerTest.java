package com.example.animalclinicbot.controllerTest;

import com.example.animalclinicbot.controller.ReportController;
import com.example.animalclinicbot.model.PersonDog;
import com.example.animalclinicbot.model.Report;
import com.example.animalclinicbot.service.PersonDogService;
import com.example.animalclinicbot.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
public class ReportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    public void testGetAllReports() throws Exception {
        when(reportService.getAll()).thenReturn(List.of(new Report()));

        mockMvc.perform(
                        get("/photoReports/getAll"))
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteReport() throws Exception {
        mockMvc.perform(delete("/photoReports/{id}",1))
                .andExpect(status().isOk());
        verify(reportService).remove(1L);
    }
}
