package com.example.animalclinicbot.service;

import com.example.animalclinicbot.exceptions.ReportNotFoundException;
import com.example.animalclinicbot.model.Report;
import com.example.animalclinicbot.repository.ReportRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
/**
 * Класс сервис отчет
 */
@Service
@Transactional
public class ReportService {
    private final ReportRepository repository;
    public ReportService(ReportRepository reportRepository) {
        this.repository = reportRepository;
    }
    /**
     * Был вызван метод загрузки отчета
     *
     * @param chatId
     * @param name
     * @param pictureFile
     * @param ration
     * @param health
     * @param behaviour
     * @param lastMessage
     * @throws IOException
     * @see ReportService
     */
    public void uploadReport(Long chatId,String name, byte[] pictureFile,
                                 String ration, String health, String behaviour,
                                 Date lastMessage) throws IOException {
        Report report = new Report();
        report.setChatId(chatId);
        report.setName(name);
        report.setData(pictureFile);
        report.setRation(ration);
        report.setHealth(health);
        report.setBehaviour(behaviour);
        report.setLastMessage(lastMessage);
        this.repository.save(report);
    }
    /**
     * метод получения отчета по идентификатору записи
     * @param id
     * @return {@link ReportRepository#findById(Object)}
     * @see ReportService
     * @exception ReportNotFoundException
     */
    public Report findById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(()->new ReportNotFoundException("Data not found exceptions"));
    }
    /**
     * метод поиска отчета по id чата
     * @param chatId
     * @return {@link ReportRepository#findByChatId(Long)}
     * @see ReportService
     */
    public Report findByChatId(Long chatId) {
        return this.repository.findByChatId(chatId);
    }
    /**
     * метод поиска отчетов по id чата
     * @param chatId
     * @return {@link ReportRepository#findListByChatId(Long)}
     * @see ReportService
     */
    public Collection<Report> findListByChatId(Long chatId) {
        return this.repository.findListByChatId(chatId);
    }
    /**
     * метод сохранения отчета
     * @param report
     * @return {@link ReportRepository#findListByChatId(Long)}
     * @see ReportService
     */
    public Report save(Report report) {
        return this.repository.save(report);
    }
    /**
     * метод удаления отчета по id
     * @param id
     * @see ReportService
     */
    public void remove(Long id) {
        this.repository.deleteById(id);
    }
    /**
     * метод получения всех отчетов
     * @return {@link ReportRepository#findAll()}
     * @see ReportService
     */
    public List<Report> getAll() {
        return this.repository.findAll();
    }

}

