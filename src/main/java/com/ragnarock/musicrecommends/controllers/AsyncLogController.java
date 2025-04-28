package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.services.AsyncLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/asyncLogs")
@Tag(name = "AsyncLog controller", description = "API for asyncLogs")
@RequiredArgsConstructor
public class AsyncLogController {
    private final AsyncLogService asyncLogService;

    @GetMapping("/{date}")
    @Operation(summary = "Create log")
    public CompletableFuture<ResponseEntity<Map<String, String>>> generateLogs(
            @PathVariable String date) {
        if (!isValidDate(date)) {
            return CompletableFuture.completedFuture(
                    ResponseEntity.badRequest().body(Map.of(
                            "message", "Invalid date format",
                            "entered date", date
                    ))
            );
        }
        CompletableFuture<String> future = asyncLogService.generateLogFileForDateAsync(date);
        return future.thenApply(taskId ->
                ResponseEntity.accepted().body(Map.of(
                        "taskId", taskId,
                        "statusUrl", "/api/asyncLogs/status/" + taskId
                ))
        );
    }

    @GetMapping("/status/{taskId}")
    @Operation(summary = "Get task status")
    public ResponseEntity<Map<String, String>> getTaskStatus(@PathVariable String taskId) {
        String status = asyncLogService.getTaskStatus(taskId);
        if ("NOT_FOUND".equals(status)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("status", status));
    }

    @GetMapping("/file/{taskId}")
    @Operation(summary = "Download log file")
    public ResponseEntity<Resource> downloadLogFile(@PathVariable String taskId) {
        try {
            String status = asyncLogService.getTaskStatus(taskId);

            if (!status.startsWith("COMPLETED")) {
                return ResponseEntity.status(status.startsWith("FAILED")
                        ? HttpStatus.NOT_FOUND : HttpStatus.TOO_EARLY).build();
            }

            String filePath = asyncLogService.getLogFilePath(taskId);
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new InputStreamResource(Files.newInputStream(path));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=logs-" + taskId + ".log")
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private boolean isValidDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}