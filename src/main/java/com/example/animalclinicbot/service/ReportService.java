package com.example.animalclinicbot.service;

import com.example.animalclinicbot.exceptions.ReportException;
import com.example.animalclinicbot.exceptions.ReportNotFoundException;
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
    private final ReportRepository repository;
    public ReportService(ReportRepository reportRepository) {
        this.repository = reportRepository;
    }
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
    public Report findById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(()->new ReportNotFoundException("Data not found exceptions"));
    }
    public Report findByChatId(Long chatId) {
        return this.repository.findByChatId(chatId);
    }
    public Collection<Report> findListByChatId(Long chatId) {
        return this.repository.findListByChatId(chatId);
    }
    public Report save(Report report) {
        return this.repository.save(report);
    }
    public void remove(Long id) {
        this.repository.deleteById(id);
    }
    public List<Report> getAll() {
        return this.repository.findAll();
    }

}


