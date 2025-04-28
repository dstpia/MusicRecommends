package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.services.AsyncLogService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import lombok.Getter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Getter
public class AsyncLogServiceImpl implements AsyncLogService {
    private final Map<String, String> logFiles = new ConcurrentHashMap<>();
    private final Map<String, String> taskStatus = new ConcurrentHashMap<>();

    @Async
    public CompletableFuture<String> generateLogFileForDateAsync(String date) {
        String taskId = UUID.randomUUID().toString();
        taskStatus.put(taskId, "PROCESSING");

        CompletableFuture.runAsync(() -> {
            try {
                specialWait();

                Path sourcePath = Paths.get("logs/app.log");
                String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                if (!(date.equals(currentDate))) {
                    String filename = String.format("logs/app-%s.log", date);
                    sourcePath = Paths.get(filename);
                }

                if (!Files.exists(sourcePath)) {
                    throw new IllegalStateException("Log file not found");
                }

                List<String> filteredLines;
                try (Stream<String> lines = Files.lines(sourcePath)) {
                    filteredLines = lines
                            .filter(line -> line.startsWith(date))
                            .toList();
                }

                if (filteredLines.isEmpty()) {
                    throw new IllegalStateException("No logs found for date");
                }

                Files.createDirectories(Paths.get("logs/"));
                String filename = String.format("logs/app-%s.log", date);
                Files.write(Paths.get(filename), filteredLines);

                logFiles.put(taskId, filename);
                taskStatus.put(taskId, "COMPLETED");
            } catch (Exception e) {
                String errorMsg = e.getMessage();
                taskStatus.put(taskId, "FAILED: " + errorMsg);
            }
        });

        return CompletableFuture.completedFuture(taskId);
    }

    public void specialWait() throws InterruptedException {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new InterruptedException(e.getMessage());
        }
    }

    public String getLogFilePath(String taskId) {
        return logFiles.get(taskId);
    }

    public String getTaskStatus(String taskId) {
        return taskStatus.getOrDefault(taskId, "NOT_FOUND");
    }
}