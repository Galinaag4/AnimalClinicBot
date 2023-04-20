package com.example.animalclinicbot.service;

import com.example.animalclinicbot.model.Report;
import com.example.animalclinicbot.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private ReportRepository reportRepository;


    @Test
    void shouldReturnTheReportWhenItIsFoundByRecordId() //следует вернуть отчет,
    // когда он будет найден по идентификатору записи
    {
        Long recordId = Long.valueOf(1);
        Report report = new Report(1, 1, "сухой корм", "здоров", "любит мячики",
                7, "", 1, new byte[]{1, 2, 3}, "11.11.2022", new Date(2022 - 12 - 12));
        when(reportRepository.findById(recordId)).thenReturn(Optional.of(report));
        assertEquals(report, reportService.findById(recordId));
    }

    @Test
    void shouldReturnTheReportWhenItIsFoundByChatId() //следует вернуть отчет,
    // когда он будет найден по идентификатору чата
    {
        Long chatId = Long.valueOf(1);
        Report report = new Report(1, 1, "сухой корм", "здоров", "любит мячики",
                7, "", 1, new byte[]{1, 2, 3}, "11.11.2022", new Date(2022 - 12 - 12));

        when(reportRepository.findByChatId(chatId)).thenReturn(report);
        assertEquals(report, reportService.findByChatId(chatId));
    }

//    @Test
//    void shouldReturnTheCollectionOfReportsWhenItIsFoundByChatId() //следует вернуть коллекцию отчетов, когда они
//    // будут найдены по идентификатору чата
//    {
//        Long id = Long.valueOf(1);
//        Collection<Report> reportList = new ArrayList<>();
//        reportList.add(new Report(1, 1, "сухой корм", "здоров", "любит мячики",
//                7, "", 1, new byte[]{1, 2, 3}, "11.11.2022", new Date(2022 - 12 - 12)));
//        when(reportRepository.findByChatId(id)).thenReturn((Report) reportList);
//        assertEquals(reportList, reportService.findListByChatId(id));
//    }

    @Test
    void shouldCallRepositoryMethodWhenSave() //следует вызвать метод репозитория сохранения отчета
    {
        Long id = Long.valueOf(1);
        Report report = new Report(1, 1, "сухой корм", "здоров", "любит мячики",
                7, "", 1, new byte[]{1, 2, 3}, "11.11.2022", new Date(2022 - 12 - 12));
        when(reportRepository.save(report)).thenReturn(report);
        assertEquals(report, reportService.save(report));

    }

    @Test
    void shouldCallRepositoryMethodRemove() //следует вызвать метод репозитория удаления отчета по идентификатору
    {
        Long id = Long.valueOf(1);
        Report report = new Report(1, 1, "сухой корм", "здоров", "любит мячики",
                7, "", 1, new byte[]{1, 2, 3}, "11.11.2022", new Date(2022 - 12 - 12));

        doNothing().when(reportRepository).deleteById(id);
        reportService.remove(id);
    }

    @Test
    void shouldCallRepositoryMethodReceivingAllReports() //следует вызвать метод репозитория получения всех отчетов
    {
        List <Report> reportList = new ArrayList<>();
        reportList.add(new Report());
        when(reportRepository.findAll()).thenReturn(reportList);
        assertEquals(reportList, reportService.getAll());
    }


}
