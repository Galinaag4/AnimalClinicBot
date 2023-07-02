package com.example.animalclinicbot.service;

import com.example.animalclinicbot.exceptions.ReportException;
import com.example.animalclinicbot.model.Report;
import com.example.animalclinicbot.repository.ReportRepository;
import com.pengrad.telegrambot.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
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
    private final ReportRepository reportRepository;
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    /**
     * метод получения отчета
     * @param personId
     * @param pictureFile
     * @param file
     * @param ration
     * @param health
     * @param habits
     * @param filePath
     * @param dateSendMessage
     * @param timeDate
     * @param daysOfReports
     * @throws IOException
     * @see ReportService
     */
    public void uploadReport(Long personId, byte[] pictureFile, File file, String ration, String health,
                                 String habits, String filePath, Date dateSendMessage, Long timeDate, long daysOfReports) throws IOException {
        logger.info("Был вызван метод загрузки отчета");

        Report report = new Report();

        report.setLastMessage(dateSendMessage);
        report.setDays(daysOfReports);
        report.setFilePath(filePath);
        report.setFileSize(file.fileSize());
        report.setChatId(personId);
        report.setData(pictureFile);
        report.setRation(ration);
        report.setHealth(health);
        report.setHabits(habits);
        this.reportRepository.save(report);
    }

    /**
     * Был вызван метод загрузки отчета
     *
     * @param personId
     * @param pictureFile
     * @param file
     * @param caption
     * @param filePath
     * @param dateSendMessage
     * @param daysOfReports
     * @throws IOException
     * @see ReportService
     */
    public void uploadReport(Long personId, byte[] pictureFile, File file,
                                 String caption, String filePath, Date dateSendMessage, long daysOfReports) throws IOException {
        logger.info("Был вызван метод загрузки отчета");

        Report report = new Report();
        report.setLastMessage(dateSendMessage);
        report.setDays(daysOfReports);
        report.setFilePath(filePath);
        report.setChatId(personId);
        report.setFileSize(file.fileSize());
        report.setData(pictureFile);
        report.setCaption(caption);
        this.reportRepository.save(report);
    }
    /**
     * метод получения отчета по идентификатору записи
     * @param id
     * @return {@link ReportRepository#findById(Object)}
     * @see ReportService
     * @exception ReportException
     */
    public Report findById(Long id) {
        logger.info("Был вызван метод получения отчета по идентификатору записи={}", id);

        return this.reportRepository.findById(id)
                .orElseThrow(ReportException::new);
    }
    /**
     * метод поиска отчета по id чата
     * @param chatId
     * @return {@link ReportRepository#findByChatId(Long)}
     * @see ReportService
     */
    public Report findByChatId(Long chatId) {
        return this.reportRepository.findByChatId(chatId);
    }
    /**
     * метод поиска отчетов по id чата
     * @param chatId
     * @return {@link ReportRepository#findListByChatId(Long)}
     * @see ReportService
     */
    public Collection<Report> findListByChatId(Long chatId) {
        return this.reportRepository.findListByChatId(chatId);
    }
    /**
     * метод сохранения отчета
     * @param report
     * @return {@link ReportRepository#findListByChatId(Long)}
     * @see ReportService
     */
    public Report save(Report report) {
        return this.reportRepository.save(report);
    }
    /**
     * метод удаления отчета по id
     * @param id
     * @see ReportService
     */
    public void remove(Long id) {
        this.reportRepository.deleteById(id);
    }
    /**
     * метод получения всех отчетов
     * @return {@link ReportRepository#findAll()}
     * @see ReportService
     */
    public List<Report> getAll() {
        return this.reportRepository.findAll();
    }

}

