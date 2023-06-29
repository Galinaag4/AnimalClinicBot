package com.example.animalclinicbot.controller;

import com.example.animalclinicbot.listener.TelegramBotUpdatesListener;
import com.example.animalclinicbot.model.Report;
import com.example.animalclinicbot.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import liquibase.ui.ConsoleUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Класс контроллер отчета по животному
 */
@RestController
@RequestMapping("photoReports")
public class ReportController {

    private final ReportService reportService;

    private final String fileType = "image/jpeg";

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Просмотр отчетов по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все отчеты",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            },
            tags = "Reports"
    )
    @GetMapping("/{id}")
    public Report downloadReport(@Parameter(description = "report id") @PathVariable Long id) {
        return this.reportService.findById(id);
    }

    @Operation(summary = "Удаление отчетов по id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Удаленный отчет",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            },
            tags = "Reports"
    )
    @DeleteMapping("/{id}")
    public void remove(@Parameter(description = "report id") @PathVariable Long id) {
        this.reportService.remove(id);
    }

    @Operation(summary = "Просмотр всех отчетов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Все отчеты, либо отчеты определенного пользователя по chat_id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                    )
            },
            tags = "Reports"
    )
    @GetMapping("/getAll")
    public ResponseEntity<Collection<Report>> getAll() {
        return ResponseEntity.ok(this.reportService.getAll());
    }


    @Operation(summary = "Просмотр фото по id отчета",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Фото, найденное по id отчета"
            ),
            tags = "Report"
    )
    @GetMapping("/{id}/photo-from-db")
    public ResponseEntity<byte[]> downloadPhotoFromDB(@Parameter(description = "report id") @PathVariable Long id) {
        Report report = this.reportService.findById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(fileType));
        headers.setContentLength(report.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(report.getData());
    }

}

