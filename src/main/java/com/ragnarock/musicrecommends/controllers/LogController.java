package com.ragnarock.musicrecommends.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
@Tag(name = "Log controller", description = "API для работы с логами приложения")
public class LogController {

    @GetMapping("/{date}")
    @Operation(summary = "Получить лог файл по дате",
            description = "Возвращает лог файл для указанной даты, если файл существует")
    @ApiResponse(responseCode = "200", description = "Лог файл найден и возвращен")
    @ApiResponse(responseCode = "404", description = "Лог файл не найден")
    public ResponseEntity<Resource> getLogFile(@PathVariable String date) throws IOException {
        String baseFilePath = "logs/app.log";
        File baseFile = new File(baseFilePath);
        if (!baseFile.exists()) {
            return ResponseEntity.notFound().build();
        }
        String newFileName = "logs/app-" + date + ".log";
        File newFile = new File(newFileName);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (newFile.exists() && !(date.equals(currentDate))) {
            ByteArrayResource resource = new ByteArrayResource(Files
                    .readAllBytes(newFile.toPath()));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
                            + newFile.getName())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        String generatedLog = generateLogForDate(baseFile, date);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
            writer.write(generatedLog);
        }
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(newFile.toPath()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
                        + newFile.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private String generateLogForDate(File baseFile, String date) throws IOException {
        StringBuilder logBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(baseFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(date)) {
                    logBuilder.append(line).append("\n");
                }
            }
        }
        logBuilder.append("\n# New log entry for date: ").append(date).append("\n");
        logBuilder.append("Generated at: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date())).append("\n");
        return logBuilder.toString();
    }
}
