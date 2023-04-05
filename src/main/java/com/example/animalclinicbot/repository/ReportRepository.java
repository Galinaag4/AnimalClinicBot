package com.example.animalclinicbot.repository;

import com.example.animalclinicbot.model.PersonDog;
import com.example.animalclinicbot.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Set<Report> findListByChatId(Long id);

    Report findByChatId(Long id);
}
