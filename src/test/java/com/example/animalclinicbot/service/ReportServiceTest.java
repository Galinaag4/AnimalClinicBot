package com.example.animalclinicbot.service;

import com.example.animalclinicbot.exceptions.ReportException;
import com.example.animalclinicbot.model.Report;
import com.example.animalclinicbot.repository.ReportRepository;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {


    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportRepository reportRepository;


    @Test
    void uploadReportData() {
    }

    @Test
    void testUploadReportData() {
    }

    @Test
    void findById()  //следует вернуть отчет,
    // когда он будет найден по идентификатору записи
    {
        Long recordId = Long.valueOf(1);
        Report report = new Report(1, 1, "сухой корм", "здоров", "любит мячики",
                7, "", 1, new byte[]{1, 2, 3}, "11.11.2022", new Date(2022 - 12 - 12));
        when(reportRepository.findById(recordId)).thenReturn(Optional.of(report));
        assertEquals(report, reportService.findById(recordId));
    }
    @Test
    public void findByIdExceptionTest() {
        Mockito.when(reportRepository.findById(any(Long.class))).thenThrow(ReportException.class);

        org.junit.jupiter.api.Assertions.assertThrows(ReportException.class, () -> reportService.findById(1L));
    }

    @Test
    void findByChatId()//следует вернуть отчет,
    // когда он будет найден по идентификатору чата
    {
        Long chatId = Long.valueOf(1);
        Report report = new Report(1, 1, "сухой корм", "здоров", "любит мячики",
                7, "", 1, new byte[]{1, 2, 3}, "11.11.2022", new Date(2022 - 12 - 12));

        when(reportRepository.findByChatId(chatId)).thenReturn(report);
        assertEquals(report, reportService.findByChatId(chatId));
    }

    @Test
    void findListByChatId() //следует вернуть коллекцию отчетов, когда они
    // будут найдены по идентификатору чата
    {
        Long id = 1L;
        Collection<Report> reportList = Set.of(new Report(1, 1, "сухой корм", "здоров", "любит мячики",
                7, "", 1, new byte[]{1, 2, 3}, "11.11.2022", new Date(2022 - 12 - 12)));

        when(reportRepository.findListByChatId(id)).thenReturn(reportList);
        assertEquals(reportList, reportService.findListByChatId(id));
    }

    @Test
    void save() //следует вызвать метод репозитория сохранения отчета
    {
        Long id = Long.valueOf(1);
        Report report = new Report(1, 1, "сухой корм", "здоров", "любит мячики",
                7, "", 1, new byte[]{1, 2, 3}, "11.11.2022", new Date(2022 - 12 - 12));
        when(reportRepository.save(report)).thenReturn(report);
        assertEquals(report, reportService.save(report));
    }

    @Test
    void remove() //следует вызвать метод репозитория удаления отчета по идентификатору
    {
        Long id = Long.valueOf(1);
        Report report = new Report(1, 1, "сухой корм", "здоров", "любит мячики",
                7, "", 1, new byte[]{1, 2, 3}, "11.11.2022", new Date(2022 - 12 - 12));

        doNothing().when(reportRepository).deleteById(id);
        reportService.remove(id);
    }

    @Test
    void getAll() //следует вызвать метод репозитория получения всех отчетов
    {
        List<Report> reportList = new ArrayList<>();
        reportList.add(new Report());
        when(reportRepository.findAll()).thenReturn(reportList);
        assertEquals(reportList, reportService.getAll());
    }

    @Test
    void getAllReports() //следует вызвать метод репозитория получения всех отчетов по номеру страницы
     {

    }
}