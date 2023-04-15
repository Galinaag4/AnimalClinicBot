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
     *
     * @param ration
     * @param health
     * @param habits
     * @param filePath
     * @param dateSendMessage
     * @param daysOfReports
     * @throws IOException
     * @see ReportService
     */
    public void uploadReportData(Long personId, byte[] pictureFile, File file, String ration, String health,
                                 String habits, String filePath, Date dateSendMessage, long daysOfReports) throws IOException {
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
    public void uploadReportData(Long personId, byte[] pictureFile, File file,
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
    public Report findByChatId(long chatId) {
        logger.info("Был вызван метод поиска отчета по id чата={}", chatId);

        return this.reportRepository.findByChatId(chatId);
    }

    /**
     * метод поиска отчетов по id чата
     * @param chatId
     * @return {@link ReportRepository#findListByChatId(Long)}
     * @see ReportService
     */
    public Collection<Report> findListByChatId(long chatId) {
        logger.info("Был вызван метод поиска отчетов по id чата={}", chatId);

        return this.reportRepository.findListByChatId(chatId);
    }

    /**
     * метод сохранения отчета
     * @param report
     * @return {@link ReportRepository#findListByChatId(Long)}
     * @see ReportService
     */
    public Report save(Report report) {
        logger.info("Был вызван метод сохранения отчета");

        return this.reportRepository.save(report);
    }

    /**
     * метод удаления отчета по id
     * @param id
     * @see ReportService
     */
    public void remove(Long id) {
        logger.info("Был вызван метод удаления отчета по id={}", id);

        this.reportRepository.deleteById(id);
    }

    /**
     * метод получения всех отчетов
     * @return {@link ReportRepository#findAll()}
     * @see ReportService
     */
    public List<Report> getAll() {
        logger.info("метод получения всех отчетов");

        return this.reportRepository.findAll();
    }

    /**
     * метод получения всех отчетов по номеру страницы
     * @param pageNumber
     * @param pageSize
     * @return {@link ReportRepository#findAll()}
     * @see ReportService
     */
    public List<Report> getAllReports(Integer pageNumber, Integer pageSize) {
        logger.info("Был вызван метод получения всех отчетов по номеру страницы");

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return this.reportRepository.findAll(pageRequest).getContent();
    }

    /**
     * метод получения расширения
     * @param fileName
     * @see ReportService
     */
    private String getExtensions(String fileName) {
        logger.info("Был вызван метод получения расширения ");

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}

