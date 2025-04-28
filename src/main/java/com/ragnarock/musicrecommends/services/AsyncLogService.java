package com.ragnarock.musicrecommends.services;

import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Service;

@Service
public interface AsyncLogService {

    CompletableFuture<String> generateLogFileForDateAsync(String date);

    String getLogFilePath(String taskId);

    String getTaskStatus(String taskId);
}
