package com.example.animalclinicbot.service;

import com.example.animalclinicbot.exceptions.ReportException;
import com.example.animalclinicbot.model.Report;
import com.example.animalclinicbot.repository.ReportRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @Mock
    private ReportRepository repository;

    @InjectMocks
    private ReportService service;

    @Test
    void findById() {
        long testId = 1;
        Report report = new Report();
        report.setId(testId);
        Mockito.when(repository.findById(testId)).thenReturn(Optional.of(report));
        Report result = service.findById(testId);
        Assertions.assertEquals(report, result);
    }

    @Test
    void findByChatId() {
        long testId = 1;
        long testChatId = 3243242;
        Report report = new Report();
        report.setId(testId);
        report.setChatId(testChatId);
        Mockito.when(repository.findByChatId(testChatId)).thenReturn(report);
        Report result = service.findByChatId(testChatId);
        Assertions.assertEquals(report, result);
    }

    @Test
    void findListByChatId() {
        long testId = 1;
        long testChatId = 3243242;
        Report report = new Report();
        report.setId(testId);
        report.setChatId(testChatId);

        Set<Report> list = new HashSet<>();
        list.add(report);

        Mockito.when(repository.findListByChatId(testChatId)).thenReturn(list);
        Collection<Report> result = service.findListByChatId(testChatId);
        Assertions.assertEquals(list, result);
    }

    @Test
    void save() {
        long testId = 1;
        Report report = new Report();
        report.setId(testId);
        Mockito.when(repository.save(report)).thenReturn(report);
        Report createdReportData = service.save(report);
        Mockito.verify(repository, Mockito.times(1)).save(report);
        Assertions.assertEquals(report.getId(), createdReportData.getId());
    }

    @Test
    void removeById() {
        long testId = 1;
        Report report = new Report();
        report.setId(testId);

        ReportRepository mockRepository = mock(ReportRepository.class);
        doNothing().when(mockRepository).deleteById(testId);
        ReportService service = new ReportService(mockRepository);
        service.remove(testId);
        Mockito.verify(mockRepository, Mockito.times(1)).deleteById(testId);
    }

    @Test
    void getAll() {
        List<Report> reportDataList =  new ArrayList<>();
        Report report1 = new Report();
        report1.setId(1);
        Report report2 = new Report();
        report2.setId(2);
        Mockito.when(repository.findAll()).thenReturn(reportDataList);
        ReportService service = new ReportService(repository);
        Collection<Report> result = service.getAll();
        Assertions.assertEquals(reportDataList, result);
        Mockito.verify(repository, Mockito.times(1)).findAll();
    }
}