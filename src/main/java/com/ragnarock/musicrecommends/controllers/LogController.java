package com.ragnarock.musicrecommends.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/logs")
@Tag(name = "Log controller", description = "API для работы с логами приложения")
public class LogController {

    private static final String TIME_FORMAT = "yyyy-MM-dd";

    @GetMapping
    @Operation(summary = "Получить список доступных лог файлов",
            description = "Возвращает список всех лог файлов в папке logs")
    @ApiResponse(responseCode = "200", description = "Список лог файлов получен")
    @ApiResponse(responseCode = "404", description = "Папка с логами не найдена")
    public ResponseEntity<String[]> listLogFiles() {
        File logDirectory = new File("logs");
        if (!logDirectory.exists() || !logDirectory.isDirectory()) {
            log.error("Log directory does not exist");
            return ResponseEntity.notFound().build();
        }
        String[] logFiles = logDirectory.list((dir, name) -> name.endsWith(".log"));
        if (logFiles == null || logFiles.length == 0) {
            log.info("No log files found in directory");
            return ResponseEntity.ok(new String[0]);
        }
        log.info("Found {} log files", logFiles.length);
        return ResponseEntity.ok(logFiles);
    }

    @GetMapping("/{date}")
    @Operation(summary = "Получить лог файл по дате",
            description = "Возвращает лог файл для указанной даты, если файл существует")
    @ApiResponse(responseCode = "200", description = "Лог файл найден и возвращен")
    @ApiResponse(responseCode = "400", description = "Некорректный формат даты")
    @ApiResponse(responseCode = "404", description = "Основной лог файл не найден")
    public ResponseEntity<Resource> getLogFile(@PathVariable String date) throws IOException {
        if (!isValidDate(date)) {
            return ResponseEntity.badRequest().build();
        }
        String baseFilePath = "logs/app.log";
        File baseFile = new File(baseFilePath);
        if (!baseFile.exists()) {
            log.error("Base file does not exist");
            return ResponseEntity.notFound().build();
        }
        String newFileName = "logs/app-" + date + ".log";
        File newFile = new File(newFileName);
        String currentDate = new SimpleDateFormat(TIME_FORMAT).format(new Date());
        if (newFile.exists() && !(date.equals(currentDate))) {
            ByteArrayResource resource = new ByteArrayResource(Files
                    .readAllBytes(newFile.toPath()));
            log.info("This log file exists, name: {}", resource.getFilename());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
                            + newFile.getName())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        String generatedLog = generateLogForDate(baseFile, date);
        if (generatedLog == null) {
            log.error("No log entries found for date: {}", date);
            return ResponseEntity.notFound().build();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
            writer.write(generatedLog);
        }
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(newFile.toPath()));
        log.info("New log file created, name: {}", newFile.getName());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
                        + newFile.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private String generateLogForDate(File baseFile, String date) throws IOException {
        StringBuilder logBuilder = new StringBuilder();
        boolean hasLogsForDate = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(baseFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() >= date.length()
                        && line.substring(0, date.length()).equals(date)) {
                    logBuilder.append(line).append("\n");
                    hasLogsForDate = true;
                }
            }
        }
        if (!hasLogsForDate) {
            return null;
        }
        logBuilder.append("\n# New log entry for date: ").append(date).append("\n");
        logBuilder.append("Generated at: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date())).append("\n");
        return logBuilder.toString();
    }

    private boolean isValidDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
            sdf.setLenient(false);
            sdf.parse(dateStr);
            return true;
        } catch (Exception e) {
            log.error("Invalid date format");
            return false;
        }
    }

    @PreDestroy
    public synchronized void destroy() throws IOException {
        String baseFilePath = "logs/app.log";
        File baseFile = new File(baseFilePath);
        if (!baseFile.exists()) {
            log.error("Base file does not exist");
            return;
        }
        String currentDate = new SimpleDateFormat(TIME_FORMAT).format(new Date());
        String newFileName = "logs/app-" + currentDate  + ".log";
        File newFile = new File(newFileName);
        String generatedLog = generateLogForDate(baseFile, currentDate);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
            writer.write(generatedLog);
        }
        log.info("New log file created, name: {}", newFile.getName());
    }
}