package com.example.animalclinicbot.serviceTest;

import com.example.animalclinicbot.repository.ReportRepository;
import com.example.animalclinicbot.service.PersonDogService;
import com.example.animalclinicbot.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReportService.class)
public class ReportServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportRepository reportRepository;

    @Test
    void testUploadReportData() {



    }




}
